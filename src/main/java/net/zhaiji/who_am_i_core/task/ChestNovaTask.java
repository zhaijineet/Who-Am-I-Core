package net.zhaiji.who_am_i_core.task;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCraneToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.task.ISerializableTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.mixinapi.IEntityUmvuthanaFollowerToPlayer;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.util.OrganUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 胸中新星任务
 * TODO 目前只有玩家可以召唤，后续考虑让其他实体也能召唤
 * TODO AI写的，待审查、待优化
 */
public class ChestNovaTask implements ISerializableTask {
    /**
     * 任务类型标识
     */
    public static final ResourceLocation TYPE = WhoAmICore.of("chest_nova");
    /**
     * 面具槽位 -> 追随者生物的映射
     */
    private final Map<Integer, EntityUmvuthanaFollowerToPlayer> followers = new HashMap<>();
    /**
     * 面具槽位 -> 冷却剩余时间的映射
     */
    private final Map<Integer, Integer> cooldowns = new HashMap<>();
    /**
     * 待恢复的追随者 UUID 列表
     * 面具槽位 -> 追随者 UUID 的映射
     */
    private final Map<Integer, UUID> pendingFollowers = new HashMap<>();
    /**
     * 已尝试恢复的次数记录（避免无限尝试）
     */
    private final Map<Integer, Integer> recoveryAttempts = new HashMap<>();
    /**
     * 胸腔数据引用
     */
    private final ChestCavityData data;

    /**
     * 胸中新星器官所在的槽位
     */
    private final int novaSlot;

    private final List<Integer> adjacentSlots;

    /**
     * 是否应该被移除
     */
    private boolean shouldRemove = false;

    public ChestNovaTask(ChestCavityData data, int novaSlot) {
        this.data = data;
        this.novaSlot = novaSlot;
        adjacentSlots = OrganUtil.getAdjacentSlots(novaSlot, data.getSlots());
    }

    /**
     * 反序列化构造函数
     * 用于从 NBT 数据恢复 ChestNovaTask
     *
     * @param data     胸腔数据
     * @param provider HolderLookup.Provider
     * @param nbt      NBT 数据
     */
    public ChestNovaTask(ChestCavityData data, HolderLookup.Provider provider, CompoundTag nbt) {
        this.data = data;
        this.novaSlot = nbt.getInt("novaSlot");
        this.adjacentSlots = OrganUtil.getAdjacentSlots(novaSlot, data.getSlots());
        // 反序列化 cooldowns
        CompoundTag cooldownsTag = nbt.getCompound("cooldowns");
        for (String key : cooldownsTag.getAllKeys()) {
            int slot = Integer.parseInt(key);
            int cooldown = cooldownsTag.getInt(key);
            cooldowns.put(slot, cooldown);
        }
        // 反序列化 followers - 延迟恢复机制
        LivingEntity owner = data.getOwner();
        if (owner != null && owner.level() instanceof ServerLevel serverLevel) {
            ListTag followersList = nbt.getList("followers", 10);
            for (int i = 0; i < followersList.size(); i++) {
                CompoundTag followerTag = followersList.getCompound(i);
                int maskSlot = followerTag.getInt("maskSlot");
                UUID followerUUID = followerTag.getUUID("followerUUID");

                // 首先尝试立即恢复实体（可能成功）
                Entity entity = serverLevel.getEntity(followerUUID);
                if (entity instanceof EntityUmvuthanaFollowerToPlayer follower && follower.isAlive()) {
                    followers.put(maskSlot, follower);
                } else {
                    // 实体未加载，添加到待恢复列表
                    pendingFollowers.put(maskSlot, followerUUID);
                    recoveryAttempts.put(maskSlot, 0);
                }
            }
        }
    }

    /**
     * 检测槽位是否匹配
     */
    public boolean isSlotEquals(int index) {
        return novaSlot == index;
    }

    @Override
    public void tick(LivingEntity entity) {
        // 恢复待恢复的追随者
        recoverPendingFollowers(entity);

        // 更新冷却时间
        cooldowns.entrySet().removeIf(entry -> {
            int newCooldown = entry.getValue() - 1;
            if (newCooldown <= 0) {
                return true;
            }
            entry.setValue(newCooldown);
            return false;
        });
        // 检查所有追随者是否存活
        followers.entrySet().removeIf(entry -> {
            LivingEntity follower = entry.getValue();
            if (follower.isRemoved() || !follower.isAlive()) {
                // 追随者死亡，进入冷却
                cooldowns.put(entry.getKey(), getCooldownTicks());
                return true;
            }
            return false;
        });
        // 检查胸腔中是否还有胸中新星器官
        if (data.getStackInSlot(novaSlot).isEmpty()) {
            shouldRemove = true;
            return;
        }
        // 检查周围8个位置是否有面具，并召唤追随者
        checkAndSpawnFollowers(entity);
        // 根据面具类型，为主人以及追随者添加其效果
        addMaskEffect(entity);
    }

    @Override
    public void onRemoved(LivingEntity entity) {
        // 清理所有召唤的生物
        for (EntityUmvuthanaFollowerToPlayer follower : followers.values()) {
            if (!follower.isRemoved() && follower instanceof IEntityUmvuthanaFollowerToPlayer iFollower) {
                iFollower.setDeactivate();
            }
        }
        followers.clear();
        cooldowns.clear();
        pendingFollowers.clear();
        recoveryAttempts.clear();
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        return shouldRemove;
    }

    @Override
    public ResourceLocation getType() {
        return TYPE;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        // 序列化 novaSlot
        tag.putInt("novaSlot", novaSlot);
        // 序列化 cooldowns
        CompoundTag cooldownsTag = new CompoundTag();
        for (Map.Entry<Integer, Integer> entry : cooldowns.entrySet()) {
            cooldownsTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("cooldowns", cooldownsTag);
        // 序列化 followers（仅保存槽位和UUID）
        ListTag followersList = new ListTag();
        for (Map.Entry<Integer, EntityUmvuthanaFollowerToPlayer> entry : followers.entrySet()) {
            CompoundTag followerTag = new CompoundTag();
            followerTag.putInt("maskSlot", entry.getKey());
            followerTag.putUUID("followerUUID", entry.getValue().getUUID());
            followersList.add(followerTag);
        }
        tag.put("followers", followersList);
        return tag;
    }

    /**
     * 恢复待恢复的追随者
     * 在 tick 中调用，逐步恢复反序列化时未加载的实体
     */
    private void recoverPendingFollowers(LivingEntity entity) {
        if (pendingFollowers.isEmpty()) return;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        pendingFollowers.entrySet().removeIf(pendingEntry -> {
            int maskSlot = pendingEntry.getKey();
            UUID followerUUID = pendingEntry.getValue();

            Entity loadedEntity = serverLevel.getEntity(followerUUID);

            if (loadedEntity instanceof EntityUmvuthanaFollowerToPlayer follower && follower.isAlive()) {
                followers.put(maskSlot, follower);
                recoveryAttempts.remove(maskSlot);
                return true;
            } else {
                int attempts = recoveryAttempts.get(maskSlot) + 1;
                recoveryAttempts.put(maskSlot, attempts);

                // 最大尝试100次
                if (attempts >= 100) {
                    recoveryAttempts.remove(maskSlot);
                    cooldowns.put(maskSlot, getCooldownTicks());
                    return true;
                }
            }
            return false;
        });
    }

    /**
     * 检查周围8个位置是否有面具，并召唤追随者
     */
    private void checkAndSpawnFollowers(LivingEntity entity) {
        if (!(entity instanceof Player player)) return;
        if (player.getCooldowns().isOnCooldown(MowziesMobOrgans.CHEST_NOVA.get())) return;
        // 周围8个位置的槽位索引
        for (int slot : adjacentSlots) {
            ItemStack stack = data.getStackInSlot(slot);
            // 检查是否为乌姆塔纳面具
            if (stack.getItem() instanceof ItemUmvuthanaMask maskItem) {
                // 检查是否已经有该槽位的追随者
                if (followers.containsKey(slot)) continue;
                if (pendingFollowers.containsKey(slot)) continue;
                // 检查是否在冷却中
                if (cooldowns.containsKey(slot)) continue;
                // 召唤追随者
                spawnFollower(player, maskItem, slot);
            }
        }
    }

    private void addMaskEffect(LivingEntity entity) {
        for (int slot : adjacentSlots) {
            if (slot < 0 || slot >= data.getSlots()) continue;
            ItemStack maskStack = data.getStackInSlot(slot);
            // 检查是否为乌姆塔纳面具
            if (maskStack.getItem() instanceof ItemUmvuthanaMask maskItem) {
                // 添加面具效果
                if (entity.tickCount % 50 == 0) {
                    // 暂时隐藏药水，后续看是否需要显示
                    entity.addEffect(new MobEffectInstance(maskItem.getPotion(), 60, 0, false, false));
                    if (isControlRodNearby()) {
                        for (EntityUmvuthanaFollowerToPlayer follower : followers.values()) {
                            follower.addEffect(new MobEffectInstance(maskItem.getPotion(), 60, 0, false, false));
                        }
                    }
                }
            }
        }
    }

    /**
     * 召唤追随者
     */
    private void spawnFollower(Player player, ItemUmvuthanaMask maskItem, int maskSlot) {
        Level level = player.level();
        MaskType maskType = maskItem.getMaskType();
        level.playSound(
            null,
            player.getOnPos(),
            MMSounds.ENTITY_UMVUTHI_BELLY.get(),
            SoundSource.PLAYERS,
            1.5f,
            1
        );
        level.playSound(
            null,
            player.getOnPos(),
            MMSounds.ENTITY_UMVUTHANA_BLOWDART.get(),
            SoundSource.PLAYERS,
            1.5f,
            0.5f
        );
        double angle = player.getYHeadRot();
        if (angle < 0) angle = angle + 360;

        EntityUmvuthanaFollowerToPlayer umvuthana;
        if (maskType == MaskType.FAITH) {
            umvuthana = new EntityUmvuthanaCraneToPlayer(
                EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(),
                level,
                player
            );
        } else {
            umvuthana = new EntityUmvuthanaFollowerToPlayer(
                EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(),
                level,
                player
            );
        }
        if (!level.isClientSide()) {
            if (maskType != MaskType.FAITH) {
                int weapon = maskType != MaskType.FURY
                             ? umvuthana.randomizeWeapon()
                             : 0;
                umvuthana.setWeapon(weapon);
            }
            umvuthana.absMoveTo(
                player.getX() + 1 * Math.sin(-angle * (Math.PI / 180)),
                player.getY() + 1.5,
                player.getZ() + 1 * Math.cos(-angle * (Math.PI / 180)),
                (float) angle,
                0
            );
            umvuthana.setActive(false);
            umvuthana.active = false;
            level.addFreshEntity(umvuthana);
            double vx = 0.5 * Math.sin(-angle * Math.PI / 180);
            double vy = 0.5;
            double vz = 0.5 * Math.cos(-angle * Math.PI / 180);
            umvuthana.setDeltaMovement(vx, vy, vz);
            umvuthana.setHealth(umvuthana.getMaxHealth());
            umvuthana.setMask(maskType);
            umvuthana.setStoredMask(maskItem.getDefaultInstance());
            ((IEntityUmvuthanaFollowerToPlayer) umvuthana).setOrganSummon();
            // 添加到追随者列表
            followers.put(maskSlot, umvuthana);
        }
    }

    /**
     * 当面具被移除时调用
     *
     * @param maskSlot 面具所在的槽位
     */
    public void onMaskRemoved(int maskSlot) {
        LivingEntity follower = followers.remove(maskSlot);
        if (follower != null && !follower.isRemoved() && follower instanceof IEntityUmvuthanaFollowerToPlayer iFollower) {
            iFollower.setDeactivate();
        }
        cooldowns.remove(maskSlot);
        pendingFollowers.remove(maskSlot);
        recoveryAttempts.remove(maskSlot);
    }

    public void setRemove() {
        shouldRemove = true;
    }

    /**
     * 获取当前的冷却时间（检测胸中新星附近是否有制御棒）
     */
    private int getCooldownTicks() {
        if (isControlRodNearby()) {
            return 20 * 10; // 10秒冷却
        } else {
            return 20 * 30; // 30秒冷却
        }
    }

    /**
     * 检查制御棒是否在胸中新星的3x3范围内
     */
    public boolean isControlRodNearby() {
        // 检查相邻槽位中是否有制御棒
        for (int slot : adjacentSlots) {
            if (slot < 0 || slot >= data.getSlots()) continue;
            if (data.getStackInSlot(slot).is(MowziesMobOrgans.CONTROL_ROD.get())) {
                return true;
            }
        }
        return false;
    }
}

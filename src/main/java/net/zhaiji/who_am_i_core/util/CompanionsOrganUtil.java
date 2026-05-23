package net.zhaiji.who_am_i_core.util;

import dev.xylonity.companions.common.entity.projectile.HolinessStartProjectile;
import dev.xylonity.companions.common.entity.projectile.PontiffFireRingProjectile;
import dev.xylonity.companions.registry.CompanionsEntities;
import dev.xylonity.companions.registry.CompanionsSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.register.WAICEffect;

import java.util.ArrayList;
import java.util.List;

public class CompanionsOrganUtil {

    /**
     * 教宗心脏 — 圣化变身（hurt 回调）
     * <p>
     * 受伤后血量 ≤ 30% 时触发：
     * 回复 50% 最大生命，获得力量II + 抗性II + 速度II（15秒）
     * 冷却 3 分钟（3600 tick），通过玩家物品冷却机制管理
     */
    public static void pontiffHeartHurt(ChestCavitySlotContext context, DamageSource damageSource, DamageContainer damageContainer) {
        LivingEntity entity = context.entity();
        if (OrganUtil.isSelfDamage(entity, damageSource)) return;
        if (!(entity instanceof Player player)) return;
        if (OrganSkillUtil.hasCooldown(player, context.stack())) return;
        if (entity.getHealth() > entity.getMaxHealth() * 0.3F) return;
        // 触发圣化变身
        entity.heal(entity.getMaxHealth() * 0.5F);
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 1));

        // 设置 3 分钟冷却
        OrganSkillUtil.addCooldown(player, context.stack(), 3 * 60 * 20);
    }

    /**
     * 教宗脾脏 — 圣火环阵（skill 回调）
     * <p>
     * 以自身为中心释放向外扩展的火环
     */
    public static boolean pontiffSpleenSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        Vec3 spawnPos = entity.position();

        PontiffFireRingProjectile ring = CompanionsEntities.PONTIFF_FIRE_RING.get().create(level);
        if (ring == null) return false;

        ring.setOwner(entity);
        ring.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, entity.getYRot(), entity.getXRot());
        level.addFreshEntity(ring);

        if (level instanceof ServerLevel serverLevel) {
            RandomSource random = serverLevel.getRandom();
            for (int i = 0; i < 7; ++i) {
                double velX = random.nextFloat() / 2.0;
                double velY = 5.0E-5;
                double velZ = random.nextFloat() / 2.0;
                serverLevel.sendParticles(ParticleTypes.LAVA, spawnPos.x, spawnPos.y + 0.05, spawnPos.z, 1, velX, velY, velZ, 0.0);
            }
            serverLevel.sendParticles(ParticleTypes.CLOUD, spawnPos.x, spawnPos.y + 0.1, spawnPos.z, 6, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, spawnPos.x, spawnPos.y + 0.1, spawnPos.z, 2, 0.01, 0.01, 0.01, 0.05);
        }

        level.playSound(
            null,
            entity.getOnPos(),
            CompanionsSounds.PONTIFF_FRONT_ATTACK.get(),
            SoundSource.PLAYERS,
            3F,
            1F
        );

        return true;
    }

    /**
     * 教宗阑尾 — 圣星裁决（skill 回调）
     * <p>
     * 投掷追踪最近敌人的星弹（使用 Companions 的 HolinessStartProjectile）
     * 正温度 → 红色星（点燃）；负温度/无温度 → 蓝色星（冻结）
     */
    public static boolean pontiffAppendixSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        // 视线索敌：沿视线方向射线检测，只命中玩家正在看的实体
        int distance = 16;
        HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(entity, checkEntity -> checkEntity != entity, distance);
        if (!(hitResult instanceof EntityHitResult entityHitResult)) return false;
        if (!(entityHitResult.getEntity() instanceof LivingEntity target)) return false;

        boolean isPositiveTemp = OrganUtil.getEffectiveTemperature(entity) >= 0;

        HolinessStartProjectile star = CompanionsEntities.HOLINESS_STAR.get().create(level);
        if (star == null) return false;

        star.setPos(entity.getEyePosition());
        star.setOwner(entity);
        star.setTarget(target);
        star.setRed(isPositiveTemp);
        star.setNoGravity(true);

        Vec3 dir = target.getEyePosition().subtract(entity.getEyePosition()).normalize().scale(HolinessStartProjectile.SPEED);
        star.setDeltaMovement(dir);
        level.addFreshEntity(star);

        return true;
    }

    /**
     * 蛋糕胃：食用食物时给予甜蜜效果，等级 = 蛋糕器官数量，可叠加，每次重置 30 秒
     */
    public static void cakeStomachEatEffect(LivingEntity entity, ChestCavityData data) {
        if (!data.hasOrgan(CompanionsOrgans.CAKE_STOMACH.get())) return;
        int cakeOrganCount = data.getOrganCount(WAICItemTagManager.CAKE);
        if (cakeOrganCount <= 0) return;

        MobEffectInstance currentSweetness = entity.getEffect(WAICEffect.SWEETNESS);
        int newAmplifier;
        if (currentSweetness != null) {
            // 已有甜蜜：叠加等级，重置时长
            newAmplifier = currentSweetness.getAmplifier() + cakeOrganCount;
        } else {
            // 没有甜蜜：初始等级 = 蛋糕器官数量 - 1（因为 amplifier 从 0 开始）
            newAmplifier = cakeOrganCount - 1;
        }
        entity.addEffect(new MobEffectInstance(WAICEffect.SWEETNESS, 600, newAmplifier));
    }

    // ==================== 布织泰迪熊 ====================

    /**
     * 布织泰迪熊 — 胸腔关闭回调
     * <p>
     * 单次遍历收集空槽位和羊毛信息，然后按类型处理：
     * - 单个羊毛（count==1）：原位替换为随机布织器官
     * - 多个羊毛（count>1）：尽可能消耗羊毛填满空槽位
     * 若空槽位 >= count-1：完全消耗，最后1个原位替换
     * 若空槽位 < count-1：只消耗空槽位数量个羊毛
     * </p>
     *
     * @param context 胸腔槽位上下文
     */
    public static void clothTeddyBearChestCavityClose(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = data.getOwner();
        Level level = entity.level();
        if (level.isClientSide()) return;

        // 1. 单次遍历：收集空槽位 + 羊毛信息
        List<Integer> emptySlots = new ArrayList<>();
        List<int[]> woolSlots = new ArrayList<>();

        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (stack.isEmpty()) {
                emptySlots.add(i);
            } else if (stack.is(ItemTags.WOOL)) {
                woolSlots.add(new int[]{
                    i,
                    stack.getCount()
                });
            }
        }

        if (woolSlots.isEmpty()) return;

        // 布织器官候选列表（11种）
        List<Item> clothOrgans = List.of(
            CompanionsOrgans.CLOTH_HEART.get(),
            CompanionsOrgans.CLOTH_LUNG.get(),
            CompanionsOrgans.CLOTH_LIVER.get(),
            CompanionsOrgans.CLOTH_INTESTINE.get(),
            CompanionsOrgans.CLOTH_STOMACH.get(),
            CompanionsOrgans.CLOTH_KIDNEY.get(),
            CompanionsOrgans.CLOTH_SPLEEN.get(),
            CompanionsOrgans.CLOTH_SPINE.get(),
            CompanionsOrgans.CLOTH_RIB.get(),
            CompanionsOrgans.CLOTH_MUSCLE.get(),
            CompanionsOrgans.CLOTH_APPENDIX.get()
        );

        int emptyIdx = 0;

        // 2. 处理羊毛
        for (int[] info : woolSlots) {
            int slotIdx = info[0];
            int count = info[1];
            ItemStack stack = data.getStackInSlot(slotIdx);

            if (count == 1) {
                // 单个羊毛：原位替换
                Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                data.setStackInSlot(slotIdx, organ.getDefaultInstance());
            } else {
                // 多个羊毛：尽可能消耗填满空槽位
                int availableEmpty = emptySlots.size() - emptyIdx;
                if (availableEmpty <= 0) continue;

                if (availableEmpty >= count - 1) {
                    // 可以完全消耗：前 (count-1) 个放空槽位，最后1个原位替换
                    for (int j = 0; j < count - 1; j++) {
                        stack.consume(1, entity);
                        Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                        data.setStackInSlot(emptySlots.get(emptyIdx++), organ.getDefaultInstance());
                    }
                    // 最后1个：原位替换
                    stack.consume(1, entity);
                    Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                    data.setStackInSlot(slotIdx, organ.getDefaultInstance());
                } else {
                    // 只能消耗 availableEmpty 个
                    for (int j = 0; j < availableEmpty; j++) {
                        stack.consume(1, entity);
                        Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                        data.setStackInSlot(emptySlots.get(emptyIdx++), organ.getDefaultInstance());
                    }
                }
            }
        }
    }

    /**
     * 布织泰迪熊技能：缝补
     * <p>
     * 消耗收纳袋中的羊毛回复生命值。
     * 每个羊毛治疗 4 + clothCount 点生命（clothCount = 胸腔中布织器官数量）。
     * 自动计算最低消耗以尽可能恢复至满血。
     * 5秒冷却（100 tick）。
     * </p>
     *
     * @param context 胸腔槽位上下文
     * @return true 触发冷却
     */
    public static boolean clothTeddyBearSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return false;

        int missingHP = (int) (entity.getMaxHealth() - entity.getHealth());
        if (missingHP <= 0) return false;

        int clothCount = context.data().getOrganCount(WAICItemTagManager.CLOTH);

        BundleContents contents = context.stack().getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        int totalWool = 0;
        for (int i = 0; i < contents.size(); i++) {
            ItemStack stack = contents.getItemUnsafe(i);
            if (stack.is(ItemTags.WOOL)) {
                totalWool += stack.getCount();
            }
        }

        if (totalWool <= 0) return false;

        // 每个羊毛治疗 4 + clothCount 点
        int healPerWool = 4 + clothCount;

        // 计算恢复满血所需的羊毛数量（向上取整）
        int woolNeeded = (missingHP + healPerWool - 1) / healPerWool;
        int woolToUse = Math.min(woolNeeded, totalWool);

        int actualHeal = woolToUse * healPerWool;

        // 扣除羊毛
        for (int i = 0; i < contents.size() && woolToUse > 0; i++) {
            ItemStack stack = contents.getItemUnsafe(i);
            if (stack.is(ItemTags.WOOL)) {
                int consume = Math.min(woolToUse, stack.getCount());
                stack.consume(consume, entity);
                woolToUse -= consume;
            }
        }

        entity.heal(actualHeal);
        return true;
    }
}

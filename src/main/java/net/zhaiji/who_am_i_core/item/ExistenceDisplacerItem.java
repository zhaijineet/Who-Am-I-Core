package net.zhaiji.who_am_i_core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySize;
import net.zhaiji.chestcavitybeyond.api.TargetResolver;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.config.WhoAmIClientConfig;
import net.zhaiji.who_am_i_core.mixinapi.IChestCavityData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExistenceDisplacerItem extends Item {
    /**
     * 玩家 UUID 到交换目标 UUID 的临时缓存，仅在 use 与 finishUsingItem/releaseUsing 之间持有
     */
    private static final Map<UUID, UUID> SWAP_TARGETS = new HashMap<>();

    public ExistenceDisplacerItem(Properties properties) {
        super(properties);
    }

    /**
     * 判断是否应取消实体交互
     */
    public static boolean shouldCancelEntityInteract(Player player, InteractionHand hand, Entity target) {
        return player.getItemInHand(hand).getItem() instanceof ExistenceDisplacerItem && TargetResolver.resolve(target) instanceof LivingEntity;
    }

    /**
     * 沿视线寻找有效交换目标
     */
    @Nullable
    private static LivingEntity findValidTarget(Player player, double range) {
        HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(
            player,
            entity -> entity != player && TargetResolver.resolve(entity) instanceof LivingEntity,
            range
        );
        if (!(hitResult instanceof EntityHitResult entityHitResult)) return null;
        if (!(TargetResolver.resolve(entityHitResult.getEntity()) instanceof LivingEntity target)) return null;
        if (!target.isAlive()) return null;
        return target;
    }

    /**
     * 敌对/玩家且非创造且非驯服时校验血量上限，过高则弹消息返回 false
     */
    private static boolean checkHealthLimit(Player player, LivingEntity target) {
        if (!(target instanceof Enemy || target instanceof Player) || player.isCreative()) return true;
        boolean isOwned = target instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null;
        if (isOwned) return true;
        if (target.getHealth() > target.getMaxHealth() * 0.3F) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(
                    Component.translatable(
                        "message.who_am_i_core.existence_displacer.hostile_health_too_high",
                        target.getDisplayName()
                    ), true
                );
            }
            return false;
        }
        return true;
    }

    /**
     * 交换玩家与目标的胸腔容量、龙血标记位与全部器官槽位内容，胸腔类型不交换。
     */
    private static void swapChestCavities(Player player, LivingEntity target) {
        ChestCavityData playerChestCavityData = ChestCavityUtil.getData(player);
        ChestCavityData targetChestCavityData = ChestCavityUtil.getData(target);
        IChestCavityData iPlayerChestCavityData = (IChestCavityData) playerChestCavityData;
        IChestCavityData iTargetChestCavityData = (IChestCavityData) targetChestCavityData;

        int playerFlags = iPlayerChestCavityData.getDragonBloodFlags();
        int targetFlags = iTargetChestCavityData.getDragonBloodFlags();
        ChestCavitySize playerSize = playerChestCavityData.getSize();
        ChestCavitySize targetSize = targetChestCavityData.getSize();

        int playerSlots = playerChestCavityData.getSlots();
        int targetSlots = targetChestCavityData.getSlots();
        ItemStack[] playerOrgans = new ItemStack[playerSlots];
        ItemStack[] targetOrgans = new ItemStack[targetSlots];
        for (int i = 0; i < playerSlots; i++) {
            playerOrgans[i] = playerChestCavityData.getStackInSlot(i).copy();
        }
        for (int i = 0; i < targetSlots; i++) {
            targetOrgans[i] = targetChestCavityData.getStackInSlot(i).copy();
        }

        clearChestCavity(playerChestCavityData);
        clearChestCavity(targetChestCavityData);

        playerChestCavityData.resize(targetSize);
        targetChestCavityData.resize(playerSize);

        iPlayerChestCavityData.setDragonBloodFlags(targetFlags);
        iTargetChestCavityData.setDragonBloodFlags(playerFlags);

        for (int i = 0; i < targetOrgans.length && i < playerChestCavityData.getSlots(); i++) {
            playerChestCavityData.setStackInSlot(i, targetOrgans[i]);
        }
        for (int i = 0; i < playerOrgans.length && i < targetChestCavityData.getSlots(); i++) {
            targetChestCavityData.setStackInSlot(i, playerOrgans[i]);
        }

        playerChestCavityData.sync();
        targetChestCavityData.sync();
    }

    /**
     * 清空胸腔所有槽位，触发 organRemoved 回调
     */
    private static void clearChestCavity(ChestCavityData chestCavityData) {
        // TODO getSlots() 在循环条件中重复调用，若未来 organRemoved 回调改变 size 会导致边界漂移；CCB 的 resize 等路径存在相同模式，后续本项目与 CCB 两端统一改为 slot 数快照
        for (int i = 0; i < chestCavityData.getSlots(); i++) {
            if (!chestCavityData.getStackInSlot(i).isEmpty()) {
                chestCavityData.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    /**
     * 施加 3 秒黑暗、虚弱、反胃的演出效果
     */
    private static void addSwapDebuff(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 0));
        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0));
        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0));
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide()) {
            int particleCount = WhoAmIClientConfig.EXISTENCE_DISPLACER_PARTICLE_COUNT.get();
            for (int i = 0; i < particleCount; i++) {
                level.addParticle(
                    ParticleTypes.ENCHANT,
                    livingEntity.getX() + (level.random.nextDouble() - 0.5) * 10.0,
                    livingEntity.getY() + (level.random.nextDouble() - 0.5) * 10.0,
                    livingEntity.getZ() + (level.random.nextDouble() - 0.5) * 10.0,
                    0,
                    0,
                    0
                );
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        LivingEntity target = findValidTarget(player, player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE));
        if (target == null || !checkHealthLimit(player, target)) return InteractionResultHolder.fail(heldItem);
        if (!level.isClientSide()) SWAP_TARGETS.put(player.getUUID(), target.getUUID());
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(heldItem);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player) || level.isClientSide()) return stack;
        UUID storedTargetUUID = SWAP_TARGETS.remove(player.getUUID());
        if (storedTargetUUID == null) return stack;
        LivingEntity currentTarget = findValidTarget(player, player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE) * 1.5);
        if (currentTarget == null || !currentTarget.getUUID().equals(storedTargetUUID)) {
            player.displayClientMessage(Component.translatable("message.who_am_i_core.existence_displacer.target_lost"), true);
            return stack;
        }
        if (!checkHealthLimit(player, currentTarget)) return stack;
        swapChestCavities(player, currentTarget);
        addSwapDebuff(player);
        addSwapDebuff(currentTarget);
        player.getCooldowns().addCooldown(stack.getItem(), 60);
        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 60;
    }

    /**
     * 玩家中途松手或切换物品栏时清理目标缓存
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) SWAP_TARGETS.remove(player.getUUID());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.who_am_i_core.existence_displacer.0").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.who_am_i_core.existence_displacer.1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("tooltip.who_am_i_core.existence_displacer.2").withStyle(ChatFormatting.GOLD));
    }
}

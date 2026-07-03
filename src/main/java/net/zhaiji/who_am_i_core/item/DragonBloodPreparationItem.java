package net.zhaiji.who_am_i_core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySize;
import net.zhaiji.chestcavitybeyond.api.TargetResolver;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.mixinapi.IChestCavityData;

import java.util.List;

public class DragonBloodPreparationItem extends Item {
    private final int dragonBloodBit;

    public DragonBloodPreparationItem(Properties properties, int dragonBloodBit) {
        super(properties);
        this.dragonBloodBit = dragonBloodBit;
    }

    /**
     * 判断是否应取消实体交互，让 Item.use() 通过射线检测接管
     */
    public static boolean shouldCancelEntityInteract(Player player, InteractionHand hand, Entity target) {
        if (!player.isShiftKeyDown()) return false;
        ItemStack stack = player.getItemInHand(hand);
        return stack.getItem() instanceof DragonBloodPreparationItem && TargetResolver.resolve(target) instanceof LivingEntity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            // 潜行：射线检测视线中的实体，对目标使用
            HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(
                player,
                entity -> entity != player && TargetResolver.resolve(entity) instanceof LivingEntity,
                player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)
            );
            if (hitResult instanceof EntityHitResult entityHitResult
                && TargetResolver.resolve(entityHitResult.getEntity()) instanceof LivingEntity target) {
                if (level.isClientSide()) return InteractionResultHolder.success(heldItem);
                return applyToTarget(player, target, heldItem);
            }
            return InteractionResultHolder.fail(heldItem);
        }

        // 不潜行：对自己使用
        if (level.isClientSide()) return InteractionResultHolder.success(heldItem);
        return applyToSelf(player, heldItem);
    }

    private InteractionResultHolder<ItemStack> applyToSelf(Player player, ItemStack heldItem) {
        ChestCavityData chestCavityData = ChestCavityUtil.getData(player);
        IChestCavityData iChestCavityData = (IChestCavityData) chestCavityData;

        if (iChestCavityData.getExpansionLevel() >= IChestCavityData.MAX_EXPANSION_LEVEL) {
            player.displayClientMessage(Component.translatable("message.who_am_i_core.dragon_blood.max_level"), true);
            return InteractionResultHolder.fail(heldItem);
        }

        if (dragonBloodBit == 0) {
            iChestCavityData.setDragonBloodFlags(IChestCavityData.ALL_DRAGON_BLOOD_BITS);
            chestCavityData.resize(ChestCavitySize.byId(IChestCavityData.MAX_EXPANSION_LEVEL));
            player.displayClientMessage(Component.translatable("message.who_am_i_core.dragon_blood.group_draw"), true);
        } else {
            if (iChestCavityData.isDragonBloodUsed(dragonBloodBit)) {
                player.displayClientMessage(Component.translatable("message.who_am_i_core.dragon_blood.already_used"), true);
                return InteractionResultHolder.fail(heldItem);
            }
            iChestCavityData.setDragonBloodFlags(iChestCavityData.getDragonBloodFlags() | dragonBloodBit);
            chestCavityData.resize(ChestCavitySize.byId(iChestCavityData.getExpansionLevel()));
            player.displayClientMessage(Component.translatable("message.who_am_i_core.dragon_blood.power_draw"), true);
        }

        heldItem.shrink(1);
        return InteractionResultHolder.consume(heldItem);
    }

    private InteractionResultHolder<ItemStack> applyToTarget(Player player, LivingEntity target, ItemStack heldItem) {
        ChestCavityData chestCavityData = ChestCavityUtil.getData(target);
        IChestCavityData iChestCavityData = (IChestCavityData) chestCavityData;

        if (iChestCavityData.getExpansionLevel() >= IChestCavityData.MAX_EXPANSION_LEVEL) {
            player.displayClientMessage(Component.translatable(
                "message.who_am_i_core.dragon_blood.other_max_level",
                target.getDisplayName()
            ), true);
            return InteractionResultHolder.fail(heldItem);
        }

        if (dragonBloodBit == 0) {
            iChestCavityData.setDragonBloodFlags(IChestCavityData.ALL_DRAGON_BLOOD_BITS);
            chestCavityData.resize(ChestCavitySize.byId(IChestCavityData.MAX_EXPANSION_LEVEL));
            player.displayClientMessage(Component.translatable(
                "message.who_am_i_core.dragon_blood.other_group_draw",
                target.getDisplayName()
            ), true);
        } else {
            if (iChestCavityData.isDragonBloodUsed(dragonBloodBit)) {
                player.displayClientMessage(Component.translatable(
                    "message.who_am_i_core.dragon_blood.other_already_used",
                    target.getDisplayName()
                ), true);
                return InteractionResultHolder.fail(heldItem);
            }
            iChestCavityData.setDragonBloodFlags(iChestCavityData.getDragonBloodFlags() | dragonBloodBit);
            chestCavityData.resize(ChestCavitySize.byId(iChestCavityData.getExpansionLevel()));
            player.displayClientMessage(Component.translatable(
                "message.who_am_i_core.dragon_blood.other_power_draw",
                target.getDisplayName()
            ), true);
        }

        heldItem.shrink(1);
        return InteractionResultHolder.consume(heldItem);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        String base = dragonBloodBit == 0
            ? "tooltip.who_am_i_core.dragon_blood.group_draw"
            : "tooltip.who_am_i_core.dragon_blood.draw";
        tooltipComponents.add(Component.translatable(base + ".0").withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable(base + ".1").withStyle(ChatFormatting.GOLD));
    }
}

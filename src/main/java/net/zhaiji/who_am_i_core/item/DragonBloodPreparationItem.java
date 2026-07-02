package net.zhaiji.who_am_i_core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySize;
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (level.isClientSide()) return InteractionResultHolder.success(heldItem);

        ChestCavityData chestCavityData = ChestCavityUtil.getData(player);
        IChestCavityData iChestCavityData = (IChestCavityData) chestCavityData;

        if (iChestCavityData.getExpansionLevel() >= IChestCavityData.MAX_EXPANSION_LEVEL) {
            player.displayClientMessage(Component.translatable("message.who_am_i_core.dragon_blood.max_level"), true);
            return InteractionResultHolder.fail(heldItem);
        }

        if (dragonBloodBit == 0) {
            // 组合版：一次性置满
            iChestCavityData.setDragonBloodFlags(IChestCavityData.ALL_DRAGON_BLOOD_BITS);
            chestCavityData.resize(ChestCavitySize.byId(IChestCavityData.MAX_EXPANSION_LEVEL));
            player.displayClientMessage(Component.translatable("message.who_am_i_core.dragon_blood.group_draw"), true);
        } else {
            // 单色版：置一个 bit
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (dragonBloodBit == 0) {
            tooltipComponents.add(Component.translatable("tooltip.who_am_i_core.dragon_blood.group_draw").withStyle(ChatFormatting.GOLD));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.who_am_i_core.dragon_blood.draw").withStyle(ChatFormatting.GOLD));
        }
    }
}

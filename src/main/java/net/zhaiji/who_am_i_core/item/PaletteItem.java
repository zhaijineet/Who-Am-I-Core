package net.zhaiji.who_am_i_core.item;

import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;

/**
 * 调色盘物品
 * <p>
 * 继承自BundleItem，但限制只能放入染料
 * </p>
 * TODO 增加容量
 */
public class PaletteItem extends BundleItem {
    public PaletteItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack target = slot.getItem();
        if (target.isEmpty() || target.is(Tags.Items.DYES)) {
            return super.overrideStackedOnOther(stack, slot, action, player);
        }
        return false;
    }

    @Override
    public boolean overrideOtherStackedOnMe(
        ItemStack stack,
        ItemStack other,
        Slot slot,
        ClickAction action,
        Player player,
        SlotAccess access
    ) {
        if (other.isEmpty() || other.is(Tags.Items.DYES)) {
            return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
        }
        return false;
    }
}

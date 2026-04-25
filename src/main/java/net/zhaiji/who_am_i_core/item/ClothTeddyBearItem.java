package net.zhaiji.who_am_i_core.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class ClothTeddyBearItem extends BundleItem {
    public ClothTeddyBearItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack target = slot.getItem();
        if (target.isEmpty() || target.is(ItemTags.WOOL) || target.is(Items.STRING)) {
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
        if (other.isEmpty() || other.is(ItemTags.WOOL) || other.is(Items.STRING)) {
            return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
        }
        return false;
    }
}

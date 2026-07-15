package net.zhaiji.who_am_i_core.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * 剑骨头物品，使其可在附魔台附魔并支持铁砧合并附魔
 */
public class SwordBoneItem extends Item {
    public SwordBoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 14;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getMaxStackSize() == 1;
    }
}

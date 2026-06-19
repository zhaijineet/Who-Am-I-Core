package net.zhaiji.who_am_i_core.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * 可附魔器官物品
 */
public class EnchantableOrganItem extends Item {
    public EnchantableOrganItem(Properties properties) {
        super(properties);
    }

    /**
     * 让物品接受所有附魔
     */
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return true;
    }
}

package net.zhaiji.who_am_i_core.manager;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 电磁炮弹药管理器
 * 维护可发射物品与对应基础伤害的映射
 */
public class RailgunAmmoManager {
    private static final LinkedHashMap<Item, Float> AMMO_MAP = new LinkedHashMap<>();

    static {
        AMMO_MAP.put(Items.IRON_NUGGET, 4.0f);
        AMMO_MAP.put(Items.GOLD_NUGGET, 6.0f);
    }

    public static boolean isValidAmmo(ItemStack stack) {
        return !stack.isEmpty() && AMMO_MAP.containsKey(stack.getItem());
    }

    public static float getBaseDamage(ItemStack stack) {
        return AMMO_MAP.getOrDefault(stack.getItem(), 0f);
    }

    public static Set<Map.Entry<Item, Float>> getAmmoEntries() {
        return AMMO_MAP.entrySet();
    }
}

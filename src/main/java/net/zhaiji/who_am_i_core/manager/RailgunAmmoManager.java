package net.zhaiji.who_am_i_core.manager;

import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.init.item.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * 电磁炮弹药管理器
 * 维护可发射金属粒与对应基础伤害的映射，优先使用 c 标签匹配以覆盖跨 mod 重复注册的同种粒
 */
public class RailgunAmmoManager {
    public record AmmoEntry(Item displayItem, float baseDamage, Ingredient ingredient) {}

    private static final List<AmmoEntry> AMMO_LIST = new ArrayList<>();

    static {
        register(ModItems.COPPER_NUGGET.get(), 3.0f, ModItemTags.COPPER_NUGGETS);
        register(ModItems.TIN_NUGGET.get(), 3.0f, ModItemTags.TIN_NUGGETS);
        register(ModItems.ZINC_NUGGET.get(), 3.0f, ModItemTags.ZINC_NUGGETS);
        register(Items.IRON_NUGGET, 4.0f, Tags.Items.NUGGETS_IRON);
        register(ModItems.LEAD_NUGGET.get(), 5.0f, ModItemTags.LEAD_NUGGETS);
        register(Items.GOLD_NUGGET, 6.0f, Tags.Items.NUGGETS_GOLD);
        register(ModItems.BRASS_NUGGET.get(), 6.0f, ModItemTags.BRASS_NUGGETS);
        register(ModItems.BRONZE_NUGGET.get(), 6.0f, ModItemTags.BRONZE_NUGGETS);
        register(ModItems.SILVER_NUGGET.get(), 6.0f, ModItemTags.SILVER_NUGGETS);
        register(ModItems.ROYAL_STEEL_NUGGET.get(), 7.0f);
        register(ModItems.CURSED_GOLD_NUGGET.get(), 7.0f);
        register(com.github.L_Ender.cataclysm.init.ModItems.ANCIENT_METAL_NUGGET.get(), 8.0f);
        register(com.github.L_Ender.cataclysm.init.ModItems.BLACK_STEEL_NUGGET.get(), 8.0f);
        register(ModItems.TITANIUM_NUGGET.get(), 9.0f, ModItemTags.TITANIUM_NUGGETS);
        register(ModItems.FROST_METAL_NUGGET.get(), 9.0f, ModItemTags.FROST_METAL_NUGGETS);
        register(ModItems.TUNGSTEN_NUGGET.get(), 10.0f, ModItemTags.TUNGSTEN_NUGGETS);
        register(ModItems.URANIUM_NUGGET.get(), 11.0f, ModItemTags.URANIUM_NUGGETS);
        register(ModItems.EMBER_METAL_NUGGET.get(), 12.0f);
        register(ModItems.PLUTONIUM_NUGGET.get(), 14.0f, ModItemTags.PLUTONIUM_NUGGETS);
        register(ModItems.NEGATIVE_MATTER_NUGGET.get(), 20.0f);
        register(ModItems.TRANSCENDIUM_NUGGET.get(), 30.0f, ModItemTags.TRANSCENDIUM_NUGGETS);
    }

    private static void register(Item displayItem, float baseDamage, Ingredient ingredient) {
        AMMO_LIST.add(new AmmoEntry(displayItem, baseDamage, ingredient));
    }

    private static void register(Item displayItem, float baseDamage, TagKey<Item> tag) {
        register(displayItem, baseDamage, Ingredient.of(tag));
    }

    private static void register(Item displayItem, float baseDamage) {
        register(displayItem, baseDamage, Ingredient.of(displayItem));
    }

    public static boolean isValidAmmo(ItemStack stack) {
        return getBaseDamage(stack) > 0;
    }

    public static float getBaseDamage(ItemStack stack) {
        if (stack.isEmpty()) return 0f;
        for (AmmoEntry entry : AMMO_LIST) {
            if (entry.ingredient().test(stack)) {
                return entry.baseDamage();
            }
        }
        return 0f;
    }

    public static List<AmmoEntry> getAmmoList() {
        return AMMO_LIST;
    }
}

package net.zhaiji.who_am_i_core.manager;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICItemTagManager {
    // 魔法
    public static final TagKey<Item> MAGIC = create("magic");

    // 机械
    public static final TagKey<Item> MECHANICAL = create("mechanical");

    // 召唤
    public static final TagKey<Item> SUMMON = create("summon");

    // 唯一
    public static final TagKey<Item> UNIQUE = create("unique");

    // 火龙
    public static final TagKey<Item> FIRE_DRAGON = create("fire_dragon");

    // 冰龙
    public static final TagKey<Item> ICE_DRAGON = create("ice_dragon");

    // 电龙
    public static final TagKey<Item> LIGHTNING_DRAGON = create("lightning_dragon");

    // 冰霜
    public static final TagKey<Item> ICE = create("ice");

    // 炽焰
    public static final TagKey<Item> FIRE = create("fire");

    // 病变
    public static final TagKey<Item> LESION = create("lesion");

    // 诅咒金
    public static final TagKey<Item> CURSED_GOLD = create("cursed_gold");

    // 余烬金属
    public static final TagKey<Item> EMBER_METAL = create("ember_metal");

    // 布织器官
    public static final TagKey<Item> CLOTH_ORGAN = create("cloth_organ");

    // 巨兽
    public static final TagKey<Item> MONSTROSITY = create("monstrosity");

    // 教宗
    public static final TagKey<Item> PONTIFF = create("pontiff");

    public static TagKey<Item> create(String name) {
        return ItemTags.create(WhoAmICore.of(name));
    }
}

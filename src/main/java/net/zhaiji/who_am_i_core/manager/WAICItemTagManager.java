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

    public static TagKey<Item> create(String name) {
        return ItemTags.create(WhoAmICore.of(name));
    }
}

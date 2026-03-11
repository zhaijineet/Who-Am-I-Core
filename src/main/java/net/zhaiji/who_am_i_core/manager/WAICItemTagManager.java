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

    // 传说
    public static final TagKey<Item> LEGEND = create("legend");

    // 唯一
    public static final TagKey<Item> UNIQUE = create("unique");

    public static TagKey<Item> create(String name) {
        return ItemTags.create(WhoAmICore.of(name));
    }
}

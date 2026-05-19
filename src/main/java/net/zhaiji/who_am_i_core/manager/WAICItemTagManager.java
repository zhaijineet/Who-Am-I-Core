package net.zhaiji.who_am_i_core.manager;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICItemTagManager {
    // 魔法
    public static final TagKey<Item> MAGIC = register("magic", 0xFF55FFFF, 90);
    // 机械
    public static final TagKey<Item> MECHANICAL = register("mechanical", 0xFFAAAAAA, 80);
    // 召唤
    public static final TagKey<Item> SUMMON = register("summon", 0xFF5555FF, 70);
    // 唯一
    public static final TagKey<Item> UNIQUE = register("unique", 0xFFFF55FF, 100);
    // 火龙
    public static final TagKey<Item> FIRE_DRAGON = register("fire_dragon", 0xFFFF5555, 60);
    // 冰龙
    public static final TagKey<Item> ICE_DRAGON = register("ice_dragon", 0xFF55FFFF, 59);
    // 电龙
    public static final TagKey<Item> LIGHTNING_DRAGON = register("lightning_dragon", 0xFFFFFF55, 58);
    // 炽焰
    public static final TagKey<Item> FIRE = register("fire", 0xFFFFAA00, 50);
    // 冰霜
    public static final TagKey<Item> ICE = register("ice", 0xFF00AAAA, 49);
    // 病变
    public static final TagKey<Item> LESION = register("lesion", 0xFF00AA00, 40);
    // 诅咒
    public static final TagKey<Item> CURSED = register("cursed", 0xFFFFAA00, 30);
    // 余烬
    public static final TagKey<Item> EMBER = register("ember", 0xFFAA0000, 29);
    // 布织
    public static final TagKey<Item> CLOTH = register("cloth", 0xFFFFFFFF, 20);
    // 巨兽
    public static final TagKey<Item> MONSTROSITY = register("monstrosity", 0xFF555555, 15);
    // 蛋糕
    public static final TagKey<Item> CAKE = register("cake", 0xFFFF55FF, 10);
    // 教宗
    public static final TagKey<Item> PONTIFF = register("pontiff", 0xFFFFAA00, 5);
    // 九狱
    public static final TagKey<Item> NINE_HELL = register("nine_hell", 0xFFAA00AA, 1);

    /**
     * 创建 WAIC namespace 的 TagKey 并注册到 CCB 的 ItemTagManager
     */
    private static TagKey<Item> register(String name, int color, int priority) {
        TagKey<Item> tagKey = ItemTags.create(WhoAmICore.of(name));
        ItemTagManager.register(tagKey, color, priority);
        return tagKey;
    }
}

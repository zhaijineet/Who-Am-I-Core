package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.function.Supplier;

public class FDBossesOrgans {
    // 火焰王国战士之心
    public static final Supplier<Item> FIRE_MALKUTH_WARRIOR_HEART = WAICItem.ITEM.register(
        "fire_malkuth_warrior_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1.5)
            .build()
    );

    // 冰霜王国战士之心
    public static final Supplier<Item> ICE_MALKUTH_WARRIOR_HEART = WAICItem.ITEM.register(
        "ice_malkuth_warrior_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1.5)
            .build()
    );

    // 王国
    public static final Supplier<Item> MALKUTH = WAICItem.ITEM.register(
        "malkuth",
        () -> Organ.builder()
            .build()
    );

    // 慈悲
    public static final Supplier<Item> CHESED = WAICItem.ITEM.register(
        "chesed",
        () -> Organ.builder()
            .build()
    );

    // 严厉
    public static final Supplier<Item> GEBURAH = WAICItem.ITEM.register(
        "geburah",
        () -> Organ.builder()
            .build()
    );

    public static void register() {
    }
}

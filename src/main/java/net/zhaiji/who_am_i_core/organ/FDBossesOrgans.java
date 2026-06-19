package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;

import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.FDBossesOrganUtil;

import java.util.function.Supplier;

public class FDBossesOrgans {
    // 火焰王国战士之心
    public static final Supplier<Item> FIRE_MALKUTH_WARRIOR_HEART = WAICItem.ITEM.register(
        "fire_malkuth_warrior_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.FIRE_RESISTANCE, 2)
            .build()
    );

    // 冰霜王国战士之心
    public static final Supplier<Item> ICE_MALKUTH_WARRIOR_HEART = WAICItem.ITEM.register(
        "ice_malkuth_warrior_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.FROST_RESISTANCE, 2)
            .build()
    );

    // 王国
    public static final Supplier<Item> MALKUTH = WAICItem.ITEM.register(
        "malkuth",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(InitAttribute.FIRE_RESISTANCE, 5)
            .addValueAttribute(InitAttribute.FROST_RESISTANCE, 5)
            .build()
    );

    // 慈悲
    public static final Supplier<Item> CHESED = WAICItem.ITEM.register(
        "chesed",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .attack(FDBossesOrganUtil::chesedAttack)
            .build()
    );

    // 严厉
    public static final Supplier<Item> GEBURAH = WAICItem.ITEM.register(
        "geburah",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .attack(FDBossesOrganUtil::geburahAttack)
            .build()
    );

    public static void register() {
    }
}

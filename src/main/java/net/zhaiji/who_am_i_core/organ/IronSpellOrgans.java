package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.function.Supplier;

public class IronSpellOrgans {
    // ==================== 死灵法师器官 ====================

    // 死灵法师脊柱
    public static final Supplier<Item> NECROMANCER_SPINE = WAICItem.ITEM.register(
        "necromancer_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .build()
    );

    // 死灵法师肋骨
    public static final Supplier<Item> NECROMANCER_RIB = WAICItem.ITEM.register(
        "necromancer_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .build()
    );

    // ==================== 死者之王器官 ====================

    // 死者之王脊柱
    public static final Supplier<Item> DEAD_KING_SPINE = WAICItem.ITEM.register(
        "dead_king_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 死者之王肋骨
    public static final Supplier<Item> DEAD_KING_RIB = WAICItem.ITEM.register(
        "dead_king_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .build()
    );

    public static void register() {
    }
}

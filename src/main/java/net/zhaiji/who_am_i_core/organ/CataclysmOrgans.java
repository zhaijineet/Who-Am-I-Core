package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class CataclysmOrgans {
    // ==================== 利维坦器官 ====================

    // 利维坦心脏
    public static final Supplier<Item> LEVIATHAN_HEART = WAICItem.ITEM.register(
        "leviathan_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .addValueAttribute(InitAttribute.WATER_BREATH, 2)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 利维坦肌肉
    public static final Supplier<Item> LEVIATHAN_MUSCLE = WAICItem.ITEM.register(
        "leviathan_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2.25)
            .addValueAttribute(InitAttribute.SPEED, 1)
            .baseMultipliedAttribute(NeoForgeMod.SWIM_SPEED, 0.3)
            .build()
    );

    // 利维坦肠子
    public static final Supplier<Item> LEVIATHAN_INTESTINE = WAICItem.ITEM.register(
        "leviathan_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 利维坦胃
    public static final Supplier<Item> LEVIATHAN_STOMACH = WAICItem.ITEM.register(
        "leviathan_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 利维坦鳃
    public static final Supplier<Item> LEVIATHAN_GILL = WAICItem.ITEM.register(
        "leviathan_gill",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.WATER_BREATH, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .build()
    );

    // 利维坦脊柱
    public static final Supplier<Item> LEVIATHAN_SPINE = WAICItem.ITEM.register(
        "leviathan_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0)
            .build()
    );

    // 利维坦鱼骨
    public static final Supplier<Item> LEVIATHAN_FISHBONE = WAICItem.ITEM.register(
        "leviathan_fishbone",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.25)
            .build()
    );

    public static void register() {
    }
}

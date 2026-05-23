package net.zhaiji.who_am_i_core.organ;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.item.ClothTeddyBearItem;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.CompanionsOrganUtil;

import java.util.function.Supplier;

public class CompanionsOrgans {
    // 教宗心脏
    public static final Supplier<Item> PONTIFF_HEART = WAICItem.ITEM.register(
        "pontiff_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 4)
            .hurt(CompanionsOrganUtil::pontiffHeartHurt)
            .build()
    );

    // 教宗肺脏
    public static final Supplier<Item> PONTIFF_LUNG = WAICItem.ITEM.register(
        "pontiff_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.5)
            .build()
    );

    // 教宗胃
    public static final Supplier<Item> PONTIFF_STOMACH = WAICItem.ITEM.register(
        "pontiff_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 4)
            .build()
    );

    // 教宗肠子
    public static final Supplier<Item> PONTIFF_INTESTINE = WAICItem.ITEM.register(
        "pontiff_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 4)
            .build()
    );

    // 教宗肾脏
    public static final Supplier<Item> PONTIFF_KIDNEY = WAICItem.ITEM.register(
        "pontiff_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 4)
            .build()
    );

    // 教宗脾脏
    public static final Supplier<Item> PONTIFF_SPLEEN = WAICItem.ITEM.register(
        "pontiff_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 4)
            .skill(CompanionsOrganUtil::pontiffSpleenSkill)
            .cooldown(200)
            .build()
    );

    // 教宗肝脏
    public static final Supplier<Item> PONTIFF_LIVER = WAICItem.ITEM.register(
        "pontiff_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 4)
            .addValueAttribute(InitAttribute.FIRE_RESISTANCE, 10)
            .addValueAttribute(InitAttribute.FROST_RESISTANCE, 10)
            .build()
    );

    // 教宗阑尾
    public static final Supplier<Item> PONTIFF_APPENDIX = WAICItem.ITEM.register(
        "pontiff_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 2)
            .skill(CompanionsOrganUtil::pontiffAppendixSkill)
            .cooldown(160)
            .build()
    );

    // 教宗肌肉
    public static final Supplier<Item> PONTIFF_MUSCLE = WAICItem.ITEM.register(
        "pontiff_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 1.5)
            .addValueAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.08)
            .build()
    );

    // ==================== 蛋糕器官 ====================

    // 蛋糕心脏
    public static final Supplier<Item> CAKE_HEART = WAICItem.ITEM.register(
        "cake_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .build()
    );

    // 蛋糕肺脏
    public static final Supplier<Item> CAKE_LUNG = WAICItem.ITEM.register(
        "cake_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.5)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.5)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.5)
            .build()
    );

    // 蛋糕胃
    public static final Supplier<Item> CAKE_STOMACH = WAICItem.ITEM.register(
        "cake_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 蛋糕肝脏
    public static final Supplier<Item> CAKE_LIVER = WAICItem.ITEM.register(
        "cake_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .build()
    );

    // ==================== 布织器官系列 ====================

    // 布织泰迪熊
    public static final Supplier<Item> CLOTH_TEDDY_BEAR = WAICItem.ITEM.register(
        "cloth_teddy_bear",
        () -> Organ.builder(ClothTeddyBearItem::new)
            .properties(properties -> properties.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY))
            .cooldown(100)
            .skill(CompanionsOrganUtil::clothTeddyBearSkill)
            .chestCavityClose(CompanionsOrganUtil::clothTeddyBearChestCavityClose)
            .build()
    );

    // 布织心脏
    public static final Supplier<Item> CLOTH_HEART = WAICItem.ITEM.register(
        "cloth_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1.0)
            .build()
    );

    // 布织肺脏
    public static final Supplier<Item> CLOTH_LUNG = WAICItem.ITEM.register(
        "cloth_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.0)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.0)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.0)
            .build()
    );

    // 布织肝脏
    public static final Supplier<Item> CLOTH_LIVER = WAICItem.ITEM.register(
        "cloth_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 1.0)
            .build()
    );

    // 布织肠子
    public static final Supplier<Item> CLOTH_INTESTINE = WAICItem.ITEM.register(
        "cloth_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 1.0)
            .build()
    );

    // 布织胃
    public static final Supplier<Item> CLOTH_STOMACH = WAICItem.ITEM.register(
        "cloth_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 1.0)
            .build()
    );

    // 布织肾脏
    public static final Supplier<Item> CLOTH_KIDNEY = WAICItem.ITEM.register(
        "cloth_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 1.0)
            .build()
    );

    // 布织脾脏
    public static final Supplier<Item> CLOTH_SPLEEN = WAICItem.ITEM.register(
        "cloth_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 1.0)
            .build()
    );

    // 布织脊柱
    public static final Supplier<Item> CLOTH_SPINE = WAICItem.ITEM.register(
        "cloth_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.0)
            .addValueAttribute(InitAttribute.DEFENSE, 0.5)
            .build()
    );

    // 布织肋骨
    public static final Supplier<Item> CLOTH_RIB = WAICItem.ITEM.register(
        "cloth_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.0)
            .build()
    );

    // 布织肌肉
    public static final Supplier<Item> CLOTH_MUSCLE = WAICItem.ITEM.register(
        "cloth_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1.0)
            .addValueAttribute(InitAttribute.SPEED, 1.0)
            .build()
    );

    // 布织阑尾
    public static final Supplier<Item> CLOTH_APPENDIX = WAICItem.ITEM.register(
        "cloth_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 1.0)
            .build()
    );

    public static void register() {
    }
}

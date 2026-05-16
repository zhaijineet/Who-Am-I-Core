package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
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

    public static void register() {
    }
}

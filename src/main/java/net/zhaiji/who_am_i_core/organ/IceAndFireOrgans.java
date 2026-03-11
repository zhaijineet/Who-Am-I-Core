package net.zhaiji.who_am_i_core.organ;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.function.Supplier;

public class IceAndFireOrgans {
    // ==================== 火龙器官 ====================
    // 火龙心脏（器官注册在IceAndFireManager中）
    public static final Supplier<Item> FIRE_DRAGON_HEART = IafItems.FIRE_DRAGON_HEART;

    // 火龙肺脏
    public static final Supplier<Item> FIRE_DRAGON_LUNG = WAICItem.ITEM.register(
        "fire_dragon_lung",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .build()
    );

    // 火龙脊柱
    public static final Supplier<Item> FIRE_DRAGON_SPINE = WAICItem.ITEM.register(
        "fire_dragon_spine",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 火龙胃
    public static final Supplier<Item> FIRE_DRAGON_STOMACH = WAICItem.ITEM.register(
        "fire_dragon_stomach",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 火龙肠子
    public static final Supplier<Item> FIRE_DRAGON_INTESTINE = WAICItem.ITEM.register(
        "fire_dragon_intestine",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 火龙肾脏
    public static final Supplier<Item> FIRE_DRAGON_KIDNEY = WAICItem.ITEM.register(
        "fire_dragon_kidney",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .build()
    );

    // 火龙脾脏
    public static final Supplier<Item> FIRE_DRAGON_SPLEEN = WAICItem.ITEM.register(
        "fire_dragon_spleen",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .build()
    );

    // 火龙肝脏
    public static final Supplier<Item> FIRE_DRAGON_LIVER = WAICItem.ITEM.register(
        "fire_dragon_liver",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .build()
    );

    // 火龙宝玉
    public static final Supplier<Item> FIRE_DRAGON_GEM = WAICItem.ITEM.register(
        "fire_dragon_gem",
        () -> OrganBuilder.builder()
            .build()
    );

    // 火龙吐息袋
    public static final Supplier<Item> FIRE_DRAGON_BREATH_SAC = WAICItem.ITEM.register(
        "fire_dragon_breath_sac",
        () -> OrganBuilder.builder()
            .build()
    );

    // 火龙肋骨
    public static final Supplier<Item> FIRE_DRAGON_RIB = WAICItem.ITEM.register(
        "fire_dragon_rib",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .build()
    );

    // 火龙肌肉
    public static final Supplier<Item> FIRE_DRAGON_MUSCLE = WAICItem.ITEM.register(
        "fire_dragon_muscle",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 2)
            .build()
    );

    // ==================== 冰龙器官 ====================
    // 冰龙心脏（器官注册在IceAndFireManager中）
    public static final Supplier<Item> ICE_DRAGON_HEART = IafItems.ICE_DRAGON_HEART;

    // 冰龙肺脏
    public static final Supplier<Item> ICE_DRAGON_LUNG = WAICItem.ITEM.register(
        "ice_dragon_lung",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .build()
    );

    // 冰龙脊柱
    public static final Supplier<Item> ICE_DRAGON_SPINE = WAICItem.ITEM.register(
        "ice_dragon_spine",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 冰龙胃
    public static final Supplier<Item> ICE_DRAGON_STOMACH = WAICItem.ITEM.register(
        "ice_dragon_stomach",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 冰龙肠子
    public static final Supplier<Item> ICE_DRAGON_INTESTINE = WAICItem.ITEM.register(
        "ice_dragon_intestine",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 冰龙肾脏
    public static final Supplier<Item> ICE_DRAGON_KIDNEY = WAICItem.ITEM.register(
        "ice_dragon_kidney",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .build()
    );

    // 冰龙脾脏
    public static final Supplier<Item> ICE_DRAGON_SPLEEN = WAICItem.ITEM.register(
        "ice_dragon_spleen",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .build()
    );

    // 冰龙肝脏
    public static final Supplier<Item> ICE_DRAGON_LIVER = WAICItem.ITEM.register(
        "ice_dragon_liver",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .build()
    );

    // 冰龙宝玉
    public static final Supplier<Item> ICE_DRAGON_GEM = WAICItem.ITEM.register(
        "ice_dragon_gem",
        () -> OrganBuilder.builder()
            .build()
    );

    // 冰龙吐息袋
    public static final Supplier<Item> ICE_DRAGON_BREATH_SAC = WAICItem.ITEM.register(
        "ice_dragon_breath_sac",
        () -> OrganBuilder.builder()
            .build()
    );

    // 冰龙肋骨
    public static final Supplier<Item> ICE_DRAGON_RIB = WAICItem.ITEM.register(
        "ice_dragon_rib",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .build()
    );

    // 冰龙肌肉
    public static final Supplier<Item> ICE_DRAGON_MUSCLE = WAICItem.ITEM.register(
        "ice_dragon_muscle",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 2)
            .build()
    );

    // ==================== 电龙器官 ====================
    // 电龙心脏（器官注册在IceAndFireManager中）
    public static final Supplier<Item> LIGHTNING_DRAGON_HEART = IafItems.LIGHTNING_DRAGON_HEART;

    // 电龙肺脏
    public static final Supplier<Item> LIGHTNING_DRAGON_LUNG = WAICItem.ITEM.register(
        "lightning_dragon_lung",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .build()
    );

    // 电龙脊柱
    public static final Supplier<Item> LIGHTNING_DRAGON_SPINE = WAICItem.ITEM.register(
        "lightning_dragon_spine",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 电龙胃
    public static final Supplier<Item> LIGHTNING_DRAGON_STOMACH = WAICItem.ITEM.register(
        "lightning_dragon_stomach",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 电龙肠子
    public static final Supplier<Item> LIGHTNING_DRAGON_INTESTINE = WAICItem.ITEM.register(
        "lightning_dragon_intestine",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 电龙肾脏
    public static final Supplier<Item> LIGHTNING_DRAGON_KIDNEY = WAICItem.ITEM.register(
        "lightning_dragon_kidney",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .build()
    );

    // 电龙脾脏
    public static final Supplier<Item> LIGHTNING_DRAGON_SPLEEN = WAICItem.ITEM.register(
        "lightning_dragon_spleen",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .build()
    );

    // 电龙肝脏
    public static final Supplier<Item> LIGHTNING_DRAGON_LIVER = WAICItem.ITEM.register(
        "lightning_dragon_liver",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .build()
    );

    // 电龙宝玉
    public static final Supplier<Item> LIGHTNING_DRAGON_GEM = WAICItem.ITEM.register(
        "lightning_dragon_gem",
        () -> OrganBuilder.builder()
            .build()
    );

    // 电龙吐息袋
    public static final Supplier<Item> LIGHTNING_DRAGON_BREATH_SAC = WAICItem.ITEM.register(
        "lightning_dragon_breath_sac",
        () -> OrganBuilder.builder()
            .build()
    );

    // 电龙肋骨
    public static final Supplier<Item> LIGHTNING_DRAGON_RIB = WAICItem.ITEM.register(
        "lightning_dragon_rib",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .build()
    );

    // 电龙肌肉
    public static final Supplier<Item> LIGHTNING_DRAGON_MUSCLE = WAICItem.ITEM.register(
        "lightning_dragon_muscle",
        () -> OrganBuilder.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 2)
            .build()
    );

    public static void register() {
    }
}

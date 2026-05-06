package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.AnvilCraftOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class AnvilCraftOrgans {
    // 皇家钢器官description翻译键
    public static final String ROYAL_STEEL_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.royal_steel";

    // 诅咒金器官description翻译键
    public static final String CURSED_GOLD_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.cursed_gold";

    // 余烬金属器官description翻译键
    public static final String EMBER_ABSORPTION_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.ember_absorption";

    // 浮霜器官description翻译键
    public static final String MERCILESS_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.merciless";

    // 超限合金器官description翻译键
    public static final String LOOTING_BURST_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.looting_burst";

    // ==================== 皇家钢器官 ====================
    // 皇家钢肋骨
    public static final Supplier<Item> ROYAL_STEEL_RIB = WAICItem.ITEM.register(
        "royal_steel_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.0)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(ROYAL_STEEL_TRANSLATION))
            .build()
    );

    // 皇家钢肌肉
    public static final Supplier<Item> ROYAL_STEEL_MUSCLE = WAICItem.ITEM.register(
        "royal_steel_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1.5)
            .addValueAttribute(InitAttribute.SPEED, 1.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(ROYAL_STEEL_TRANSLATION))
            .build()
    );

    // 皇家钢脊柱
    public static final Supplier<Item> ROYAL_STEEL_SPINE = WAICItem.ITEM.register(
        "royal_steel_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 1.0)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(ROYAL_STEEL_TRANSLATION))
            .build()
    );

    // 皇家钢阑尾
    public static final Supplier<Item> ROYAL_STEEL_APPENDIX = WAICItem.ITEM.register(
        "royal_steel_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 2.0)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(ROYAL_STEEL_TRANSLATION))
            .build()
    );

    // ==================== 诅咒金器官 ====================
    // 诅咒金心脏
    public static final Supplier<Item> CURSED_GOLD_HEART = WAICItem.ITEM.register(
        "cursed_gold_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2.0)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(CURSED_GOLD_TRANSLATION))
            .build()
    );

    // 诅咒金肺脏
    public static final Supplier<Item> CURSED_GOLD_LUNG = WAICItem.ITEM.register(
        "cursed_gold_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.5)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.5)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.5)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(CURSED_GOLD_TRANSLATION))
            .build()
    );

    // 诅咒金肝脏
    public static final Supplier<Item> CURSED_GOLD_LIVER = WAICItem.ITEM.register(
        "cursed_gold_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2.0)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(CURSED_GOLD_TRANSLATION))
            .build()
    );

    // 诅咒金肠子
    public static final Supplier<Item> CURSED_GOLD_INTESTINE = WAICItem.ITEM.register(
        "cursed_gold_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2.0)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(CURSED_GOLD_TRANSLATION))
            .build()
    );

    // ==================== 余烬金属器官 ====================
    // 余烬肋骨
    public static final Supplier<Item> EMBER_METAL_RIB = WAICItem.ITEM.register(
        "ember_metal_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.5)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(EMBER_ABSORPTION_TRANSLATION))
            .build()
    );

    // 余烬肌肉
    public static final Supplier<Item> EMBER_METAL_MUSCLE = WAICItem.ITEM.register(
        "ember_metal_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2.0)
            .addValueAttribute(InitAttribute.SPEED, 2.0)
            .addValueAttribute(WAICAttribute.MELEE_DAMAGE, 0.5)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(EMBER_ABSORPTION_TRANSLATION))
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 余烬脊柱
    public static final Supplier<Item> EMBER_METAL_SPINE = WAICItem.ITEM.register(
        "ember_metal_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2.0)
            .addValueAttribute(InitAttribute.DEFENSE, 1.0)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(EMBER_ABSORPTION_TRANSLATION))
            .build()
    );

    // 余烬阑尾
    public static final Supplier<Item> EMBER_METAL_APPENDIX = WAICItem.ITEM.register(
        "ember_metal_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 2.5)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(EMBER_ABSORPTION_TRANSLATION))
            .build()
    );

    // ==================== 浮霜器官 ====================
    // 浮霜心脏
    public static final Supplier<Item> FROST_METAL_HEART = WAICItem.ITEM.register(
        "frost_metal_heart",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalHeartModifier)
            .build()
    );

    // 浮霜肺脏
    public static final Supplier<Item> FROST_METAL_LUNG = WAICItem.ITEM.register(
        "frost_metal_lung",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalLungModifier)
            .build()
    );

    // 浮霜脊柱
    public static final Supplier<Item> FROST_METAL_SPINE = WAICItem.ITEM.register(
        "frost_metal_spine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalSpineModifier)
            .build()
    );

    // 浮霜胃
    public static final Supplier<Item> FROST_METAL_STOMACH = WAICItem.ITEM.register(
        "frost_metal_stomach",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalStomachModifier)
            .build()
    );

    // 浮霜肠子
    public static final Supplier<Item> FROST_METAL_INTESTINE = WAICItem.ITEM.register(
        "frost_metal_intestine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalIntestineModifier)
            .build()
    );

    // 浮霜肾脏
    public static final Supplier<Item> FROST_METAL_KIDNEY = WAICItem.ITEM.register(
        "frost_metal_kidney",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalKidneyModifier)
            .build()
    );

    // 浮霜脾脏
    public static final Supplier<Item> FROST_METAL_SPLEEN = WAICItem.ITEM.register(
        "frost_metal_spleen",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalSpleenModifier)
            .build()
    );

    // 浮霜肝脏
    public static final Supplier<Item> FROST_METAL_LIVER = WAICItem.ITEM.register(
        "frost_metal_liver",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalLiverModifier)
            .build()
    );

    // 浮霜阑尾
    public static final Supplier<Item> FROST_METAL_APPENDIX = WAICItem.ITEM.register(
        "frost_metal_appendix",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalAppendixModifier)
            .build()
    );

    // 浮霜肋骨
    public static final Supplier<Item> FROST_METAL_RIB = WAICItem.ITEM.register(
        "frost_metal_rib",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalRibModifier)
            .build()
    );

    // 浮霜肌肉
    public static final Supplier<Item> FROST_METAL_MUSCLE = WAICItem.ITEM.register(
        "frost_metal_muscle",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::frostMetalMuscleModifier)
            .build()
    );

    // ==================== 超限合金器官 ====================
    // 超限合金心脏
    public static final Supplier<Item> TRANSCENDIUM_HEART = WAICItem.ITEM.register(
        "transcendium_heart",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumHeartModifier)
            .build()
    );

    // 超限合金肺脏
    public static final Supplier<Item> TRANSCENDIUM_LUNG = WAICItem.ITEM.register(
        "transcendium_lung",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumLungModifier)
            .build()
    );

    // 超限合金脊柱
    public static final Supplier<Item> TRANSCENDIUM_SPINE = WAICItem.ITEM.register(
        "transcendium_spine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumSpineModifier)
            .build()
    );

    // 超限合金胃
    public static final Supplier<Item> TRANSCENDIUM_STOMACH = WAICItem.ITEM.register(
        "transcendium_stomach",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumStomachModifier)
            .build()
    );

    // 超限合金肠子
    public static final Supplier<Item> TRANSCENDIUM_INTESTINE = WAICItem.ITEM.register(
        "transcendium_intestine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumIntestineModifier)
            .build()
    );

    // 超限合金肾脏
    public static final Supplier<Item> TRANSCENDIUM_KIDNEY = WAICItem.ITEM.register(
        "transcendium_kidney",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumKidneyModifier)
            .build()
    );

    // 超限合金脾脏
    public static final Supplier<Item> TRANSCENDIUM_SPLEEN = WAICItem.ITEM.register(
        "transcendium_spleen",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumSpleenModifier)
            .build()
    );

    // 超限合金肝脏
    public static final Supplier<Item> TRANSCENDIUM_LIVER = WAICItem.ITEM.register(
        "transcendium_liver",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumLiverModifier)
            .build()
    );

    // 超限合金阑尾
    public static final Supplier<Item> TRANSCENDIUM_APPENDIX = WAICItem.ITEM.register(
        "transcendium_appendix",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumAppendixModifier)
            .build()
    );

    // 超限合金肋骨
    public static final Supplier<Item> TRANSCENDIUM_RIB = WAICItem.ITEM.register(
        "transcendium_rib",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumRibModifier)
            .build()
    );

    // 超限合金肌肉
    public static final Supplier<Item> TRANSCENDIUM_MUSCLE = WAICItem.ITEM.register(
        "transcendium_muscle",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier(AnvilCraftOrganUtil::transcendiumMuscleModifier)
            .build()
    );

    public static void register() {
    }
}

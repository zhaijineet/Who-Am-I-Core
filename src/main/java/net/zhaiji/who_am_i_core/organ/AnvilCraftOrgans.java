package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.item.EnchantableOrganItem;
import net.zhaiji.who_am_i_core.manager.WAICTooltipManager;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.AnvilCraftOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICGoalSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICPlayerSkillUtil;

import java.util.function.Supplier;

public class AnvilCraftOrgans {
    // 皇家钢肋骨
    public static final Supplier<Item> ROYAL_STEEL_RIB = WAICItem.ITEM.register(
        "royal_steel_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.0)
            .build()
    );

    // 皇家钢肌肉
    public static final Supplier<Item> ROYAL_STEEL_MUSCLE = WAICItem.ITEM.register(
        "royal_steel_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 2)
            .build()
    );

    // 皇家钢脊柱
    public static final Supplier<Item> ROYAL_STEEL_SPINE = WAICItem.ITEM.register(
        "royal_steel_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // 皇家钢阑尾
    public static final Supplier<Item> ROYAL_STEEL_APPENDIX = WAICItem.ITEM.register(
        "royal_steel_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 2)
            .build()
    );

    // 皇家钢心脏
    public static final Supplier<Item> ROYAL_STEEL_HEART = WAICItem.ITEM.register(
        "royal_steel_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .build()
    );

    // 皇家钢肺脏
    public static final Supplier<Item> ROYAL_STEEL_LUNG = WAICItem.ITEM.register(
        "royal_steel_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .build()
    );

    // 皇家钢胃
    public static final Supplier<Item> ROYAL_STEEL_STOMACH = WAICItem.ITEM.register(
        "royal_steel_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 皇家钢肠子
    public static final Supplier<Item> ROYAL_STEEL_INTESTINE = WAICItem.ITEM.register(
        "royal_steel_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 皇家钢肾脏
    public static final Supplier<Item> ROYAL_STEEL_KIDNEY = WAICItem.ITEM.register(
        "royal_steel_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .build()
    );

    // 皇家钢脾脏
    public static final Supplier<Item> ROYAL_STEEL_SPLEEN = WAICItem.ITEM.register(
        "royal_steel_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .build()
    );

    // 皇家钢肝脏
    public static final Supplier<Item> ROYAL_STEEL_LIVER = WAICItem.ITEM.register(
        "royal_steel_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .build()
    );

    // 诅咒金心脏
    public static final Supplier<Item> CURSED_GOLD_HEART = WAICItem.ITEM.register(
        "cursed_gold_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2.0)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .tooltip(WAICTooltipManager.CURSED_GOLD_TOOLTIP)
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
            .tooltip(WAICTooltipManager.CURSED_GOLD_TOOLTIP)
            .build()
    );

    // 诅咒金肝脏
    public static final Supplier<Item> CURSED_GOLD_LIVER = WAICItem.ITEM.register(
        "cursed_gold_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2.0)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .tooltip(WAICTooltipManager.CURSED_GOLD_TOOLTIP)
            .build()
    );

    // 诅咒金肠子
    public static final Supplier<Item> CURSED_GOLD_INTESTINE = WAICItem.ITEM.register(
        "cursed_gold_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2.0)
            .addValueAttribute(WAICAttribute.LOOTING, 0.5)
            .addValueAttribute(WAICAttribute.FORTUNE, 0.5)
            .tooltip(WAICTooltipManager.CURSED_GOLD_TOOLTIP)
            .build()
    );

    // 余烬肋骨
    public static final Supplier<Item> EMBER_METAL_RIB = WAICItem.ITEM.register(
        "ember_metal_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 3)
            .build()
    );

    // 余烬肌肉
    public static final Supplier<Item> EMBER_METAL_MUSCLE = WAICItem.ITEM.register(
        "ember_metal_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 3)
            .addValueAttribute(InitAttribute.SPEED, 3)
            .build()
    );

    // 余烬脊柱
    public static final Supplier<Item> EMBER_METAL_SPINE = WAICItem.ITEM.register(
        "ember_metal_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 3)
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .build()
    );

    // 余烬阑尾
    public static final Supplier<Item> EMBER_METAL_APPENDIX = WAICItem.ITEM.register(
        "ember_metal_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 3)
            .build()
    );

    // 余烬心脏
    public static final Supplier<Item> EMBER_METAL_HEART = WAICItem.ITEM.register(
        "ember_metal_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .build()
    );

    // 余烬肺脏
    public static final Supplier<Item> EMBER_METAL_LUNG = WAICItem.ITEM.register(
        "ember_metal_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 3)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 3)
            .addValueAttribute(InitAttribute.ENDURANCE, 3)
            .build()
    );

    // 余烬胃
    public static final Supplier<Item> EMBER_METAL_STOMACH = WAICItem.ITEM.register(
        "ember_metal_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 3)
            .build()
    );

    // 余烬肠子
    public static final Supplier<Item> EMBER_METAL_INTESTINE = WAICItem.ITEM.register(
        "ember_metal_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 3)
            .build()
    );

    // 余烬肾脏
    public static final Supplier<Item> EMBER_METAL_KIDNEY = WAICItem.ITEM.register(
        "ember_metal_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 3)
            .build()
    );

    // 余烬脾脏
    public static final Supplier<Item> EMBER_METAL_SPLEEN = WAICItem.ITEM.register(
        "ember_metal_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 3)
            .build()
    );

    // 余烬肝脏
    public static final Supplier<Item> EMBER_METAL_LIVER = WAICItem.ITEM.register(
        "ember_metal_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 3)
            .build()
    );

    // 浮霜心脏
    public static final Supplier<Item> FROST_METAL_HEART = WAICItem.ITEM.register(
        "frost_metal_heart",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalHeartModifier)
            .build()
    );

    // 浮霜肺脏
    public static final Supplier<Item> FROST_METAL_LUNG = WAICItem.ITEM.register(
        "frost_metal_lung",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalLungModifier)
            .build()
    );

    // 浮霜脊柱
    public static final Supplier<Item> FROST_METAL_SPINE = WAICItem.ITEM.register(
        "frost_metal_spine",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalSpineModifier)
            .build()
    );

    // 浮霜胃
    public static final Supplier<Item> FROST_METAL_STOMACH = WAICItem.ITEM.register(
        "frost_metal_stomach",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalStomachModifier)
            .build()
    );

    // 浮霜肠子
    public static final Supplier<Item> FROST_METAL_INTESTINE = WAICItem.ITEM.register(
        "frost_metal_intestine",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalIntestineModifier)
            .build()
    );

    // 浮霜肾脏
    public static final Supplier<Item> FROST_METAL_KIDNEY = WAICItem.ITEM.register(
        "frost_metal_kidney",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalKidneyModifier)
            .build()
    );

    // 浮霜脾脏
    public static final Supplier<Item> FROST_METAL_SPLEEN = WAICItem.ITEM.register(
        "frost_metal_spleen",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalSpleenModifier)
            .build()
    );

    // 浮霜肝脏
    public static final Supplier<Item> FROST_METAL_LIVER = WAICItem.ITEM.register(
        "frost_metal_liver",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalLiverModifier)
            .build()
    );

    // 浮霜阑尾
    public static final Supplier<Item> FROST_METAL_APPENDIX = WAICItem.ITEM.register(
        "frost_metal_appendix",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalAppendixModifier)
            .build()
    );

    // 浮霜肋骨
    public static final Supplier<Item> FROST_METAL_RIB = WAICItem.ITEM.register(
        "frost_metal_rib",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalRibModifier)
            .build()
    );

    // 浮霜肌肉
    public static final Supplier<Item> FROST_METAL_MUSCLE = WAICItem.ITEM.register(
        "frost_metal_muscle",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::frostMetalMuscleModifier)
            .build()
    );

    // 超限合金心脏
    public static final Supplier<Item> TRANSCENDIUM_HEART = WAICItem.ITEM.register(
        "transcendium_heart",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumHeartModifier)
            .build()
    );

    // 超限合金肺脏
    public static final Supplier<Item> TRANSCENDIUM_LUNG = WAICItem.ITEM.register(
        "transcendium_lung",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumLungModifier)
            .build()
    );

    // 超限合金脊柱
    public static final Supplier<Item> TRANSCENDIUM_SPINE = WAICItem.ITEM.register(
        "transcendium_spine",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumSpineModifier)
            .build()
    );

    // 超限合金胃
    public static final Supplier<Item> TRANSCENDIUM_STOMACH = WAICItem.ITEM.register(
        "transcendium_stomach",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumStomachModifier)
            .build()
    );

    // 超限合金肠子
    public static final Supplier<Item> TRANSCENDIUM_INTESTINE = WAICItem.ITEM.register(
        "transcendium_intestine",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumIntestineModifier)
            .build()
    );

    // 超限合金肾脏
    public static final Supplier<Item> TRANSCENDIUM_KIDNEY = WAICItem.ITEM.register(
        "transcendium_kidney",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumKidneyModifier)
            .build()
    );

    // 超限合金脾脏
    public static final Supplier<Item> TRANSCENDIUM_SPLEEN = WAICItem.ITEM.register(
        "transcendium_spleen",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumSpleenModifier)
            .build()
    );

    // 超限合金肝脏
    public static final Supplier<Item> TRANSCENDIUM_LIVER = WAICItem.ITEM.register(
        "transcendium_liver",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumLiverModifier)
            .build()
    );

    // 超限合金阑尾
    public static final Supplier<Item> TRANSCENDIUM_APPENDIX = WAICItem.ITEM.register(
        "transcendium_appendix",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumAppendixModifier)
            .build()
    );

    // 超限合金肋骨
    public static final Supplier<Item> TRANSCENDIUM_RIB = WAICItem.ITEM.register(
        "transcendium_rib",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumRibModifier)
            .build()
    );

    // 超限合金肌肉
    public static final Supplier<Item> TRANSCENDIUM_MUSCLE = WAICItem.ITEM.register(
        "transcendium_muscle",
        () -> Organ.builder(EnchantableOrganItem::new)
            .modifier(AnvilCraftOrganUtil::transcendiumMuscleModifier)
            .build()
    );

    // 电磁炮
    public static final Supplier<Item> RAILGUN = WAICItem.ITEM.register(
        "railgun",
        () -> Organ.builder()
            .skill(WAICPlayerSkillUtil::railgun)
            .goalSkill(WAICGoalSkillUtil.railgunGoalSkill())
            .cooldown(context -> WAICOrganUtil.isOverloadMode(context.entity()) ? 10 : 20)
            .tooltip(WAICTooltipManager.RAILGUN_TOOLTIP)
            .build()
    );

    public static void register() {
    }
}

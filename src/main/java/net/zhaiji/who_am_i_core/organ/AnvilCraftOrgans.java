package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class AnvilCraftOrgans {
    // 浮霜器官description翻译键
    public static final String MERCILESS_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.merciless";

    // ==================== 浮霜器官 ====================
    // 浮霜心脏
    public static final Supplier<Item> FROST_METAL_HEART = WAICItem.ITEM.register(
        "frost_metal_heart",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜肺脏
    public static final Supplier<Item> FROST_METAL_LUNG = WAICItem.ITEM.register(
        "frost_metal_lung",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.BREATH_RECOVERY, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.BREATH_CAPACITY, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.ENDURANCE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜脊柱
    public static final Supplier<Item> FROST_METAL_SPINE = WAICItem.ITEM.register(
        "frost_metal_spine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double bonus = WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.NERVES, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0 + bonus));
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), 0.5 + bonus));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -(1.0 + bonus)));
            })
            .build()
    );

    // 浮霜胃
    public static final Supplier<Item> FROST_METAL_STOMACH = WAICItem.ITEM.register(
        "frost_metal_stomach",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.DIGESTION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜肠子
    public static final Supplier<Item> FROST_METAL_INTESTINE = WAICItem.ITEM.register(
        "frost_metal_intestine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.NUTRITION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜肾脏
    public static final Supplier<Item> FROST_METAL_KIDNEY = WAICItem.ITEM.register(
        "frost_metal_kidney",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.FILTRATION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜脾脏
    public static final Supplier<Item> FROST_METAL_SPLEEN = WAICItem.ITEM.register(
        "frost_metal_spleen",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.METABOLISM, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜肝脏
    public static final Supplier<Item> FROST_METAL_LIVER = WAICItem.ITEM.register(
        "frost_metal_liver",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.DETOXIFICATION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜阑尾
    public static final Supplier<Item> FROST_METAL_APPENDIX = WAICItem.ITEM.register(
        "frost_metal_appendix",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(Attributes.LUCK, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜肋骨
    public static final Supplier<Item> FROST_METAL_RIB = WAICItem.ITEM.register(
        "frost_metal_rib",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 浮霜肌肉
    public static final Supplier<Item> FROST_METAL_MUSCLE = WAICItem.ITEM.register(
        "frost_metal_muscle",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.TEMPERATURE, OrganAttributeUtil.createAddValueModifier(context.id(), -value));
            })
            .build()
    );

    // 超限合金器官description翻译键
    public static final String LOOTING_BURST_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".tooltips.looting_burst";

    // ==================== 超限合金器官 ====================
    // 超限合金心脏
    public static final Supplier<Item> TRANSCENDIUM_HEART = WAICItem.ITEM.register(
        "transcendium_heart",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金肺脏
    public static final Supplier<Item> TRANSCENDIUM_LUNG = WAICItem.ITEM.register(
        "transcendium_lung",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.BREATH_RECOVERY, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.BREATH_CAPACITY, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.ENDURANCE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金脊柱
    public static final Supplier<Item> TRANSCENDIUM_SPINE = WAICItem.ITEM.register(
        "transcendium_spine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.NERVES, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金胃
    public static final Supplier<Item> TRANSCENDIUM_STOMACH = WAICItem.ITEM.register(
        "transcendium_stomach",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.DIGESTION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金肠子
    public static final Supplier<Item> TRANSCENDIUM_INTESTINE = WAICItem.ITEM.register(
        "transcendium_intestine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.NUTRITION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金肾脏
    public static final Supplier<Item> TRANSCENDIUM_KIDNEY = WAICItem.ITEM.register(
        "transcendium_kidney",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.FILTRATION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金脾脏
    public static final Supplier<Item> TRANSCENDIUM_SPLEEN = WAICItem.ITEM.register(
        "transcendium_spleen",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.METABOLISM, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金肝脏
    public static final Supplier<Item> TRANSCENDIUM_LIVER = WAICItem.ITEM.register(
        "transcendium_liver",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.DETOXIFICATION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金阑尾
    public static final Supplier<Item> TRANSCENDIUM_APPENDIX = WAICItem.ITEM.register(
        "transcendium_appendix",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(Attributes.LUCK, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金肋骨
    public static final Supplier<Item> TRANSCENDIUM_RIB = WAICItem.ITEM.register(
        "transcendium_rib",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    // 超限合金肌肉
    public static final Supplier<Item> TRANSCENDIUM_MUSCLE = WAICItem.ITEM.register(
        "transcendium_muscle",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip(MERCILESS_TRANSLATION, LOOTING_BURST_TRANSLATION))
            .modifier((context, modifiers) -> {
                double value = 5.0 + WAICOrganUtil.mercilessBonus(context);
                modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
                modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
            })
            .build()
    );

    public static void register() {
    }
}

package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class AnvilCraftOrgans {
    // 浮霜器官skill描述翻译键
    private static final String FROST_METAL_MERCILESS_SKILL = "organ." + WhoAmICore.MOD_ID + ".tooltips.frost_metal_merciless";

    // ==================== 浮霜器官 ====================
    // 浮霜心脏
    public static final Supplier<Item> FROST_METAL_HEART = WAICItem.ITEM.register(
        "frost_metal_heart",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜肺脏
    public static final Supplier<Item> FROST_METAL_LUNG = WAICItem.ITEM.register(
        "frost_metal_lung",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.BREATH_RECOVERY, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.BREATH_CAPACITY, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.ENDURANCE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜脊柱
    public static final Supplier<Item> FROST_METAL_SPINE = WAICItem.ITEM.register(
        "frost_metal_spine",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double bonus = WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.NERVES, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0 + bonus));
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), 0.5 + bonus));
            })
            .build()
    );

    // 浮霜胃
    public static final Supplier<Item> FROST_METAL_STOMACH = WAICItem.ITEM.register(
        "frost_metal_stomach",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.DIGESTION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜肠子
    public static final Supplier<Item> FROST_METAL_INTESTINE = WAICItem.ITEM.register(
        "frost_metal_intestine",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.NUTRITION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜肾脏
    public static final Supplier<Item> FROST_METAL_KIDNEY = WAICItem.ITEM.register(
        "frost_metal_kidney",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.FILTRATION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜脾脏
    public static final Supplier<Item> FROST_METAL_SPLEEN = WAICItem.ITEM.register(
        "frost_metal_spleen",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.METABOLISM, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜肝脏
    public static final Supplier<Item> FROST_METAL_LIVER = WAICItem.ITEM.register(
        "frost_metal_liver",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.DETOXIFICATION, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜阑尾
    public static final Supplier<Item> FROST_METAL_APPENDIX = WAICItem.ITEM.register(
        "frost_metal_appendix",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(Attributes.LUCK, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜肋骨
    public static final Supplier<Item> FROST_METAL_RIB = WAICItem.ITEM.register(
        "frost_metal_rib",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    // 浮霜肌肉
    public static final Supplier<Item> FROST_METAL_MUSCLE = WAICItem.ITEM.register(
        "frost_metal_muscle",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip(FROST_METAL_MERCILESS_SKILL))
            .modifier((context, modifiers) -> {
                double value = 1.0 + WAICOrganUtil.frostMetalBonus(context);
                modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), value));
                modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(context.id(), value));
            })
            .build()
    );

    public static void register() {
    }
}

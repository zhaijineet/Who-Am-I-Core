package net.zhaiji.who_am_i_core.organ;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.CustomData;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.item.ClothTeddyBearItem;
import net.zhaiji.who_am_i_core.item.FrankensteinItem;
import net.zhaiji.who_am_i_core.item.PaletteItem;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.OrganUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.List;
import java.util.function.Supplier;

public class WAICOrgans {
    public static final String INK_BOTTLE_INK_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".ink_bottle.ink";
    public static final String PALETTE_DYE_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".palette.dye";
    public static final String ENERGY_MODULE_CHARGE_TRANSLATION = "organ.who_am_i_core.energy_module.charge";

    // 神圣核心
    public static final Supplier<Item> DIVINE_CORE = WAICItem.ITEM.register(
        "divine_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1)
            .addValueAttribute(AttributeRegistry.HOLY_SPELL_POWER, 1)
            .build()
    );

    // 冰霜核心
    public static final Supplier<Item> FROST_CORE = WAICItem.ITEM.register(
        "frost_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1)
            .addValueAttribute(AttributeRegistry.ICE_SPELL_POWER, 1)
            .build()
    );

    // 炽焰核心
    public static final Supplier<Item> FLAME_CORE = WAICItem.ITEM.register(
        "flame_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1)
            .addValueAttribute(AttributeRegistry.FIRE_SPELL_POWER, 1)
            .build()
    );

    // 自然核心
    public static final Supplier<Item> NATURE_CORE = WAICItem.ITEM.register(
        "nature_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1)
            .addValueAttribute(AttributeRegistry.NATURE_SPELL_POWER, 1)
            .build()
    );

    // ==================== 墨水器官 ====================
    // 墨水心脏
    public static final Supplier<Item> INK_HEART = WAICItem.ITEM.register(
        "ink_heart",
        () -> Organ.builder()
            .build()
    );

    // 墨水肺脏
    public static final Supplier<Item> INK_LUNG = WAICItem.ITEM.register(
        "ink_lung",
        () -> Organ.builder()
            .build()
    );

    // 墨水脊柱
    public static final Supplier<Item> INK_SPINE = WAICItem.ITEM.register(
        "ink_spine",
        () -> Organ.builder()
            .build()
    );

    // 墨水胃
    public static final Supplier<Item> INK_STOMACH = WAICItem.ITEM.register(
        "ink_stomach",
        () -> Organ.builder()
            .build()
    );

    // 墨水肠子
    public static final Supplier<Item> INK_INTESTINE = WAICItem.ITEM.register(
        "ink_intestine",
        () -> Organ.builder()
            .build()
    );

    // 墨水肾脏
    public static final Supplier<Item> INK_KIDNEY = WAICItem.ITEM.register(
        "ink_kidney",
        () -> Organ.builder()
            .build()
    );

    // 墨水脾脏
    public static final Supplier<Item> INK_SPLEEN = WAICItem.ITEM.register(
        "ink_spleen",
        () -> Organ.builder()
            .build()
    );

    // 墨水肝脏
    public static final Supplier<Item> INK_LIVER = WAICItem.ITEM.register(
        "ink_liver",
        () -> Organ.builder()
            .build()
    );

    // 墨水肋骨
    public static final Supplier<Item> INK_RIB = WAICItem.ITEM.register(
        "ink_rib",
        () -> Organ.builder()
            .build()
    );

    // 墨水瓶
    public static final Supplier<Item> INK_BOTTLE = WAICItem.ITEM.register(
        "ink_bottle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 1)
            .skillTooltip((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                int value = tag.contains("ink") ? tag.getInt("ink") : 0;
                List<Component> add = List.of(
                    Component.translatable(INK_BOTTLE_INK_TRANSLATION, value)
                );
                TooltipUtil.simpleTooltipAdd(tooltipComponents, add);
            })
            .build()
    );

    // 墨水阑尾
    public static final Supplier<Item> INK_APPENDIX = WAICItem.ITEM.register(
        "ink_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 1)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .skill(WAICOrganUtil::inkAppendixSkill)
            .build()
    );

    // 墨水肌肉
    public static final Supplier<Item> INK_MUSCLE = WAICItem.ITEM.register(
        "ink_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1)
            .addValueAttribute(InitAttribute.SPEED, 1)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .hurt(WAICOrganUtil::inkMuscleSkill)
            .build()
    );

    // 钢笔尖
    public static final Supplier<Item> NIB = WAICItem.ITEM.register(
        "nib",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .build()
    );

    // ==================== 颜料器官 ====================
    // 颜料心脏
    public static final Supplier<Item> PIGMENT_HEART = WAICItem.ITEM.register(
        "pigment_heart",
        () -> Organ.builder()
            .build()
    );

    // 颜料肺脏
    public static final Supplier<Item> PIGMENT_LUNG = WAICItem.ITEM.register(
        "pigment_lung",
        () -> Organ.builder()
            .build()
    );

    // 颜料脊柱
    public static final Supplier<Item> PIGMENT_SPINE = WAICItem.ITEM.register(
        "pigment_spine",
        () -> Organ.builder()
            .build()
    );

    // 颜料胃
    public static final Supplier<Item> PIGMENT_STOMACH = WAICItem.ITEM.register(
        "pigment_stomach",
        () -> Organ.builder()
            .build()
    );

    // 颜料肠子
    public static final Supplier<Item> PIGMENT_INTESTINE = WAICItem.ITEM.register(
        "pigment_intestine",
        () -> Organ.builder()
            .build()
    );

    // 颜料肾脏
    public static final Supplier<Item> PIGMENT_KIDNEY = WAICItem.ITEM.register(
        "pigment_kidney",
        () -> Organ.builder()
            .build()
    );

    // 颜料脾脏
    public static final Supplier<Item> PIGMENT_SPLEEN = WAICItem.ITEM.register(
        "pigment_spleen",
        () -> Organ.builder()
            .build()
    );

    // 颜料肝脏
    public static final Supplier<Item> PIGMENT_LIVER = WAICItem.ITEM.register(
        "pigment_liver",
        () -> Organ.builder()
            .build()
    );

    // 颜料阑尾
    public static final Supplier<Item> PIGMENT_APPENDIX = WAICItem.ITEM.register(
        "pigment_appendix",
        () -> Organ.builder()
            .build()
    );

    // 颜料肋骨
    public static final Supplier<Item> PIGMENT_RIB = WAICItem.ITEM.register(
        "pigment_rib",
        () -> Organ.builder()
            .build()
    );

    // 颜料肌肉
    public static final Supplier<Item> PIGMENT_MUSCLE = WAICItem.ITEM.register(
        "pigment_muscle",
        () -> Organ.builder()
            .build()
    );

    // 调色盘
    public static final Supplier<Item> PALETTE = WAICItem.ITEM.register(
        "palette",
        () -> Organ.builder(PaletteItem::new)
            .properties(properties -> properties.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY))
            .skillTooltip(WAICTooltipUtil.paletteDyeTooltip())
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .build()
    );

    // ==================== 木质器官 ====================
    // 木质心脏
    public static final Supplier<Item> WOODEN_HEART = WAICItem.ITEM.register(
        "wooden_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质肺脏
    public static final Supplier<Item> WOODEN_LUNG = WAICItem.ITEM.register(
        "wooden_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 0.75)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 0.75)
            .addValueAttribute(InitAttribute.ENDURANCE, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质胃
    public static final Supplier<Item> WOODEN_STOMACH = WAICItem.ITEM.register(
        "wooden_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质肠子
    public static final Supplier<Item> WOODEN_INTESTINE = WAICItem.ITEM.register(
        "wooden_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质肾脏
    public static final Supplier<Item> WOODEN_KIDNEY = WAICItem.ITEM.register(
        "wooden_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质脾脏
    public static final Supplier<Item> WOODEN_SPLEEN = WAICItem.ITEM.register(
        "wooden_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质肝脏
    public static final Supplier<Item> WOODEN_LIVER = WAICItem.ITEM.register(
        "wooden_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质阑尾
    public static final Supplier<Item> WOODEN_APPENDIX = WAICItem.ITEM.register(
        "wooden_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // 木质肌肉
    public static final Supplier<Item> WOODEN_MUSCLE = WAICItem.ITEM.register(
        "wooden_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 0.75)
            .addValueAttribute(InitAttribute.SPEED, 0.75)
            .addValueAttribute(InitAttribute.PHOTOSYNTHESIS, 1)
            .build()
    );

    // ==================== 弗兰肯斯坦器官 ====================
    // 弗兰肯斯坦心脏（收纳袋式 - 继承内部心脏器官的属性）
    public static final Supplier<Item> FRANKENSTEIN_HEART = WAICItem.ITEM.register(
        "frankenstein_heart",
        () -> Organ.builder(properties -> new FrankensteinItem(properties, ItemTagManager.HEART))
            .properties(properties -> properties.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY))
            .modifier(OrganUtil::aggregateFrankensteinHeartAttributes)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .build()
    );

    // 弗兰肯斯坦肺脏
    public static final Supplier<Item> FRANKENSTEIN_LUNG = WAICItem.ITEM.register(
        "frankenstein_lung",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦胃
    public static final Supplier<Item> FRANKENSTEIN_STOMACH = WAICItem.ITEM.register(
        "frankenstein_stomach",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦肠子
    public static final Supplier<Item> FRANKENSTEIN_INTESTINE = WAICItem.ITEM.register(
        "frankenstein_intestine",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦肾脏
    public static final Supplier<Item> FRANKENSTEIN_KIDNEY = WAICItem.ITEM.register(
        "frankenstein_kidney",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦脾脏
    public static final Supplier<Item> FRANKENSTEIN_SPLEEN = WAICItem.ITEM.register(
        "frankenstein_spleen",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦肝脏
    public static final Supplier<Item> FRANKENSTEIN_LIVER = WAICItem.ITEM.register(
        "frankenstein_liver",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦阑尾
    public static final Supplier<Item> FRANKENSTEIN_APPENDIX = WAICItem.ITEM.register(
        "frankenstein_appendix",
        () -> Organ.builder()
            .build()
    );

    // 弗兰肯斯坦肌肉
    public static final Supplier<Item> FRANKENSTEIN_MUSCLE = WAICItem.ITEM.register(
        "frankenstein_muscle",
        () -> Organ.builder()
            .build()
    );

    // ==================== 病变器官 ====================
    // 病变心脏
    public static final Supplier<Item> LESION_HEART = WAICItem.ITEM.register(
        "lesion_heart",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::lesionHeartModifier)
            .skill(WAICOrganUtil::lesionHeartSkill)
            .cooldown(10 * 20)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 病变肺脏
    public static final Supplier<Item> LESION_LUNG = WAICItem.ITEM.register(
        "lesion_lung",
        () -> Organ.builder()
            .build()
    );

    // 病变胃
    public static final Supplier<Item> LESION_STOMACH = WAICItem.ITEM.register(
        "lesion_stomach",
        () -> Organ.builder()
            .build()
    );

    // 病变肠子
    public static final Supplier<Item> LESION_INTESTINE = WAICItem.ITEM.register(
        "lesion_intestine",
        () -> Organ.builder()
            .build()
    );

    // 病变肾脏
    public static final Supplier<Item> LESION_KIDNEY = WAICItem.ITEM.register(
        "lesion_kidney",
        () -> Organ.builder()
            .build()
    );

    // 病变脾脏
    public static final Supplier<Item> LESION_SPLEEN = WAICItem.ITEM.register(
        "lesion_spleen",
        () -> Organ.builder()
            .build()
    );

    // 病变肝脏
    public static final Supplier<Item> LESION_LIVER = WAICItem.ITEM.register(
        "lesion_liver",
        () -> Organ.builder()
            .build()
    );

    // 病变阑尾
    public static final Supplier<Item> LESION_APPENDIX = WAICItem.ITEM.register(
        "lesion_appendix",
        () -> Organ.builder()
            .build()
    );

    // 病变肌肉
    public static final Supplier<Item> LESION_MUSCLE = WAICItem.ITEM.register(
        "lesion_muscle",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::lesionMuscleModifier)
            .attack(WAICOrganUtil::lesionMuscleAttack)
            .skillTooltip(WAICTooltipUtil.skillTooltip(1))
            .build()
    );

    // ==================== 九狱器官 ====================
    // 灵薄（阑尾）— 属性 = 2 - N；效果：每秒获得经验
    public static final Supplier<Item> LIMBO = WAICItem.ITEM.register(
        "limbo",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::limboModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .tick(WAICOrganUtil::limboTick)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 色欲（肠子）— 属性 = 2 - N；效果：攻击回复生命
    public static final Supplier<Item> LUST = WAICItem.ITEM.register(
        "lust",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::lustModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .attack(WAICOrganUtil::lustAttack)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 暴食（胃）— 属性 = 2 - N；效果：食用任何食物 + 食用额外效果
    public static final Supplier<Item> GLUTTONY = WAICItem.ITEM.register(
        "gluttony",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::gluttonyModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 贪婪（肺脏）— 属性 = 2 - N；效果：抢夺 + 时运
    public static final Supplier<Item> GREED = WAICItem.ITEM.register(
        "greed",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::greedModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 愤怒（肝脏）— 属性 = 2 - N；效果：力量 + 速度
    public static final Supplier<Item> WRATH = WAICItem.ITEM.register(
        "wrath",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::wrathModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 异端（脾脏）— 属性 = 2 - N；效果：药水持续延长 + 等级提升
    public static final Supplier<Item> HERESY = WAICItem.ITEM.register(
        "heresy",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::heresyModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 暴力（肌肉）— 属性 = 2 - N；效果：暴击倍率 + 永远暴击
    public static final Supplier<Item> VIOLENCE = WAICItem.ITEM.register(
        "violence",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::violenceModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 欺诈（肾脏）— 属性 = 2 - N；效果：交易经验/打折/不缺货
    public static final Supplier<Item> FRAUD = WAICItem.ITEM.register(
        "fraud",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::fraudModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 背叛（心脏）— 属性 = 2 - N；效果：额外造成目标最大生命%伤害
    public static final Supplier<Item> TREACHERY = WAICItem.ITEM.register(
        "treachery",
        () -> Organ.builder()
            .modifier(WAICOrganUtil::treacheryModifier)
            .otherChange(WAICOrganUtil::nineHellOtherChange)
            .attack(WAICOrganUtil::treacheryAttack)
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // ==================== 双子魔眼器官 ====================
    // 奇怪的眼球
    public static final Supplier<Item> STRANGE_EYEBALL = WAICItem.ITEM.register(
        "strange_eyeball",
        () -> Organ.builder()
            .build()
    );

    // 诡异的眼球
    public static final Supplier<Item> EERIE_EYEBALL = WAICItem.ITEM.register(
        "eerie_eyeball",
        () -> Organ.builder()
            .build()
    );

    // 奇怪的机械眼球
    public static final Supplier<Item> STRANGE_MECHANICAL_EYEBALL = WAICItem.ITEM.register(
        "strange_mechanical_eyeball",
        () -> Organ.builder()
            .build()
    );

    // 诡异的机械眼球
    public static final Supplier<Item> EERIE_MECHANICAL_EYEBALL = WAICItem.ITEM.register(
        "eerie_mechanical_eyeball",
        () -> Organ.builder()
            .build()
    );

    // ==================== 拟态器官 ====================
    // 拟态心脏
    public static final Supplier<Item> MIMIC_HEART = WAICItem.ITEM.register(
        "mimic_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 3)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .heal(WAICOrganUtil::mimicHealBoost)
            .build()
    );

    // 拟态肝脏
    public static final Supplier<Item> MIMIC_LIVER = WAICItem.ITEM.register(
        "mimic_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 3)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .heal(WAICOrganUtil::mimicHealBoost)
            .build()
    );

    // 拟态肺脏
    public static final Supplier<Item> MIMIC_LUNG = WAICItem.ITEM.register(
        "mimic_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 3)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 3)
            .addValueAttribute(InitAttribute.ENDURANCE, 3)
            .addValueAttribute(InitAttribute.WATER_BREATH, 3)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .heal(WAICOrganUtil::mimicHealBoost)
            .build()
    );

    // ==================== 幻想种器官 ====================
    // 幻想种心脏
    public static final Supplier<Item> FANTASTICAL_HEART = WAICItem.ITEM.register(
        "fantastical_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1.25)
            .build()
    );

    // 幻想种肺脏
    public static final Supplier<Item> FANTASTICAL_LUNG = WAICItem.ITEM.register(
        "fantastical_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.25)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.25)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.25)
            .build()
    );

    // 幻想种脊柱
    public static final Supplier<Item> FANTASTICAL_SPINE = WAICItem.ITEM.register(
        "fantastical_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.25)
            .addValueAttribute(InitAttribute.DEFENSE, 0.625)
            .build()
    );

    // 幻想种胃
    public static final Supplier<Item> FANTASTICAL_STOMACH = WAICItem.ITEM.register(
        "fantastical_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 1.25)
            .build()
    );

    // 幻想种肠子
    public static final Supplier<Item> FANTASTICAL_INTESTINE = WAICItem.ITEM.register(
        "fantastical_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 1.25)
            .build()
    );

    // 幻想种肾脏
    public static final Supplier<Item> FANTASTICAL_KIDNEY = WAICItem.ITEM.register(
        "fantastical_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 1.25)
            .build()
    );

    // 幻想种脾脏
    public static final Supplier<Item> FANTASTICAL_SPLEEN = WAICItem.ITEM.register(
        "fantastical_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 1.25)
            .build()
    );

    // 幻想种肝脏
    public static final Supplier<Item> FANTASTICAL_LIVER = WAICItem.ITEM.register(
        "fantastical_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 1.25)
            .build()
    );

    // 幻想种阑尾
    public static final Supplier<Item> FANTASTICAL_APPENDIX = WAICItem.ITEM.register(
        "fantastical_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 1.25)
            .build()
    );

    // 幻想种肋骨
    public static final Supplier<Item> FANTASTICAL_RIB = WAICItem.ITEM.register(
        "fantastical_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.25)
            .build()
    );

    // 幻想种肌肉
    public static final Supplier<Item> FANTASTICAL_MUSCLE = WAICItem.ITEM.register(
        "fantastical_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1.25)
            .addValueAttribute(InitAttribute.SPEED, 1.25)
            .build()
    );

    // ==================== 布织泰迪熊器官系列 ====================
    // 布织泰迪熊
    public static final Supplier<Item> CLOTH_TEDDY_BEAR = WAICItem.ITEM.register(
        "cloth_teddy_bear",
        () -> Organ.builder(ClothTeddyBearItem::new)
            .properties(properties -> properties.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY))
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .cooldown(100)
            .skill(WAICOrganUtil::clothTeddyBearSkill)
            .chestCavityClose(WAICOrganUtil::clothTeddyBearChestCavityClose)
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

    // ==================== 独立器官 ====================
    // 闹鬼的骨头
    public static final Supplier<Item> HAUNTED_BONE = WAICItem.ITEM.register(
        "haunted_bone",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .chestCavityOpen(WAICOrganUtil::hauntedBoneChestCavityOpen)
            .chestCavityClose(WAICOrganUtil::hauntedBoneChestCavityClose)
            .addValueAttribute(InitAttribute.SPEED, 1)
            .addValueAttribute(Attributes.LUCK, 1)
            .build()
    );

    // 剑骨头
    public static final Supplier<Item> SWORD_BONE = WAICItem.ITEM.register(
        "sword_bone",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .properties(properties -> properties.attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)))
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .build()
    );

    // 直肠子
    public static final Supplier<Item> STRAIGHT_INTESTINE = WAICItem.ITEM.register(
        "straight_intestine",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .addValueAttribute(InitAttribute.NUTRITION, 1)
            .build()
    );

    // 窝瓜
    public static final Supplier<Item> SQUASH = WAICItem.ITEM.register(
        "squash",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .incomingDamage(WAICOrganUtil::squashIncomingDamage)
            .build()
    );

    // 经验之心
    public static final Supplier<Item> EXPERIENCE_HEART = WAICItem.ITEM.register(
        "experience_heart",
        () -> Organ.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .modifier(WAICOrganUtil::experienceHeartModifier)
            .build()
    );

    // ==================== 猩红器官 ====================
    // 猩红心脏
    public static final Supplier<Item> CRIMSON_HEART = WAICItem.ITEM.register(
        "crimson_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(AttributeRegistry.BLOOD_SPELL_POWER, 1)
            .heal(WAICOrganUtil::crimsonHeartHeal)
            .added(WAICOrganUtil::crimsonHeartAdded)
            .removed(WAICOrganUtil::crimsonHeartRemoved)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 猩红肺脏
    public static final Supplier<Item> CRIMSON_LUNG = WAICItem.ITEM.register(
        "crimson_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.5)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.5)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.5)
            .build()
    );

    // 猩红胃
    public static final Supplier<Item> CRIMSON_STOMACH = WAICItem.ITEM.register(
        "crimson_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .build()
    );

    // 猩红肠子
    public static final Supplier<Item> CRIMSON_INTESTINE = WAICItem.ITEM.register(
        "crimson_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .build()
    );

    // 猩红肾脏
    public static final Supplier<Item> CRIMSON_KIDNEY = WAICItem.ITEM.register(
        "crimson_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .build()
    );

    // 猩红脾脏
    public static final Supplier<Item> CRIMSON_SPLEEN = WAICItem.ITEM.register(
        "crimson_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .build()
    );

    // 猩红肝脏
    public static final Supplier<Item> CRIMSON_LIVER = WAICItem.ITEM.register(
        "crimson_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .build()
    );

    // 猩红阑尾
    public static final Supplier<Item> CRIMSON_APPENDIX = WAICItem.ITEM.register(
        "crimson_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 2)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .skill(WAICOrganUtil::crimsonAppendixSkill)
            .cooldown(30 * 20)
            .build()
    );

    // 猩红肌肉
    public static final Supplier<Item> CRIMSON_MUSCLE = WAICItem.ITEM.register(
        "crimson_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 1.5)
            .build()
    );

    public static void register() {
    }

    // ==================== 电磁义体器官 ====================

    // 演算核心
    public static final Supplier<Item> COMPUTING_CORE = WAICItem.ITEM.register(
        "computing_core",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.5)
            .tick(WAICOrganUtil::computingCoreTick)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .build()
    );

    // 导流肋骨
    public static final Supplier<Item> CURRENT_RIB = WAICItem.ITEM.register(
        "current_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .build()
    );

    // 充能肌束
    public static final Supplier<Item> CHARGED_MUSCLE = WAICItem.ITEM.register(
        "charged_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1.5)
            .addValueAttribute(InitAttribute.SPEED, 1.5)
            .tick(WAICOrganUtil::chargedMuscleTick)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .build()
    );

    // 传导链节
    public static final Supplier<Item> CONDUCTIVE_SPINE = WAICItem.ITEM.register(
        "conductive_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .skill(WAICOrganUtil::conductiveSpineSkill)
            .cooldown(20 * 20)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 蓄能模块
    public static final Supplier<Item> ENERGY_MODULE = WAICItem.ITEM.register(
        "energy_module",
        () -> Organ.builder()
            .tick(WAICOrganUtil::energyModuleTick)
            .skillTooltip((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                float charge = WAICOrganUtil.getModuleCharge(stack);
                List<Component> add = List.of(
                    Component.translatable(ENERGY_MODULE_CHARGE_TRANSLATION, (int) charge, 500)
                );
                TooltipUtil.simpleTooltipAdd(tooltipComponents, add);
            })
            .build()
    );
}

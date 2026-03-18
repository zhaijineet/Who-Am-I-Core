package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.WAICOrganSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class WAICOrgans {
    // 神圣核心
    public static final Supplier<Item> DIVINE_CORE = WAICItem.ITEM.register(
        "divine_core",
        () -> OrganBuilder.builder()
            .build()
    );

    // 冰霜核心
    public static final Supplier<Item> FROST_CORE = WAICItem.ITEM.register(
        "frost_core",
        () -> OrganBuilder.builder()
            .build()
    );

    // 炽焰核心
    public static final Supplier<Item> FLAME_CORE = WAICItem.ITEM.register(
        "flame_core",
        () -> OrganBuilder.builder()
            .build()
    );

    // 自然核心
    public static final Supplier<Item> NATURE_CORE = WAICItem.ITEM.register(
        "nature_core",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 墨水器官 ====================
    // 墨水心脏
    public static final Supplier<Item> INK_HEART = WAICItem.ITEM.register(
        "ink_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水肺脏
    public static final Supplier<Item> INK_LUNG = WAICItem.ITEM.register(
        "ink_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水脊柱
    public static final Supplier<Item> INK_SPINE = WAICItem.ITEM.register(
        "ink_spine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水胃
    public static final Supplier<Item> INK_STOMACH = WAICItem.ITEM.register(
        "ink_stomach",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水肠子
    public static final Supplier<Item> INK_INTESTINE = WAICItem.ITEM.register(
        "ink_intestine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水肾脏
    public static final Supplier<Item> INK_KIDNEY = WAICItem.ITEM.register(
        "ink_kidney",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水脾脏
    public static final Supplier<Item> INK_SPLEEN = WAICItem.ITEM.register(
        "ink_spleen",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水肝脏
    public static final Supplier<Item> INK_LIVER = WAICItem.ITEM.register(
        "ink_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水阑尾
    public static final Supplier<Item> INK_APPENDIX = WAICItem.ITEM.register(
        "ink_appendix",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水肋骨
    public static final Supplier<Item> INK_RIB = WAICItem.ITEM.register(
        "ink_rib",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水肌肉
    public static final Supplier<Item> INK_MUSCLE = WAICItem.ITEM.register(
        "ink_muscle",
        () -> OrganBuilder.builder()
            .build()
    );

    // 墨水瓶
    public static final Supplier<Item> INK_BOTTLE = WAICItem.ITEM.register(
        "ink_bottle",
        () -> OrganBuilder.builder()
            .build()
    );

    // 钢笔尖
    public static final Supplier<Item> NIB = WAICItem.ITEM.register(
        "nib",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 颜料器官 ====================
    // 颜料心脏
    public static final Supplier<Item> PIGMENT_HEART = WAICItem.ITEM.register(
        "pigment_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料肺脏
    public static final Supplier<Item> PIGMENT_LUNG = WAICItem.ITEM.register(
        "pigment_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料脊柱
    public static final Supplier<Item> PIGMENT_SPINE = WAICItem.ITEM.register(
        "pigment_spine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料胃
    public static final Supplier<Item> PIGMENT_STOMACH = WAICItem.ITEM.register(
        "pigment_stomach",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料肠子
    public static final Supplier<Item> PIGMENT_INTESTINE = WAICItem.ITEM.register(
        "pigment_intestine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料肾脏
    public static final Supplier<Item> PIGMENT_KIDNEY = WAICItem.ITEM.register(
        "pigment_kidney",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料脾脏
    public static final Supplier<Item> PIGMENT_SPLEEN = WAICItem.ITEM.register(
        "pigment_spleen",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料肝脏
    public static final Supplier<Item> PIGMENT_LIVER = WAICItem.ITEM.register(
        "pigment_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料阑尾
    public static final Supplier<Item> PIGMENT_APPENDIX = WAICItem.ITEM.register(
        "pigment_appendix",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料肋骨
    public static final Supplier<Item> PIGMENT_RIB = WAICItem.ITEM.register(
        "pigment_rib",
        () -> OrganBuilder.builder()
            .build()
    );

    // 颜料肌肉
    public static final Supplier<Item> PIGMENT_MUSCLE = WAICItem.ITEM.register(
        "pigment_muscle",
        () -> OrganBuilder.builder()
            .build()
    );

    // 调色盘
    public static final Supplier<Item> PALETTE = WAICItem.ITEM.register(
        "palette",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 木质器官 ====================
    // 木质心脏
    public static final Supplier<Item> WOODEN_HEART = WAICItem.ITEM.register(
        "wooden_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质肺脏
    public static final Supplier<Item> WOODEN_LUNG = WAICItem.ITEM.register(
        "wooden_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质胃
    public static final Supplier<Item> WOODEN_STOMACH = WAICItem.ITEM.register(
        "wooden_stomach",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质肠子
    public static final Supplier<Item> WOODEN_INTESTINE = WAICItem.ITEM.register(
        "wooden_intestine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质肾脏
    public static final Supplier<Item> WOODEN_KIDNEY = WAICItem.ITEM.register(
        "wooden_kidney",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质脾脏
    public static final Supplier<Item> WOODEN_SPLEEN = WAICItem.ITEM.register(
        "wooden_spleen",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质肝脏
    public static final Supplier<Item> WOODEN_LIVER = WAICItem.ITEM.register(
        "wooden_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质阑尾
    public static final Supplier<Item> WOODEN_APPENDIX = WAICItem.ITEM.register(
        "wooden_appendix",
        () -> OrganBuilder.builder()
            .build()
    );

    // 木质肌肉
    public static final Supplier<Item> WOODEN_MUSCLE = WAICItem.ITEM.register(
        "wooden_muscle",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 弗兰肯斯坦器官 ====================
    // 弗兰肯斯坦心脏
    public static final Supplier<Item> FRANKENSTEIN_HEART = WAICItem.ITEM.register(
        "frankenstein_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦肺脏
    public static final Supplier<Item> FRANKENSTEIN_LUNG = WAICItem.ITEM.register(
        "frankenstein_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦胃
    public static final Supplier<Item> FRANKENSTEIN_STOMACH = WAICItem.ITEM.register(
        "frankenstein_stomach",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦肠子
    public static final Supplier<Item> FRANKENSTEIN_INTESTINE = WAICItem.ITEM.register(
        "frankenstein_intestine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦肾脏
    public static final Supplier<Item> FRANKENSTEIN_KIDNEY = WAICItem.ITEM.register(
        "frankenstein_kidney",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦脾脏
    public static final Supplier<Item> FRANKENSTEIN_SPLEEN = WAICItem.ITEM.register(
        "frankenstein_spleen",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦肝脏
    public static final Supplier<Item> FRANKENSTEIN_LIVER = WAICItem.ITEM.register(
        "frankenstein_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦阑尾
    public static final Supplier<Item> FRANKENSTEIN_APPENDIX = WAICItem.ITEM.register(
        "frankenstein_appendix",
        () -> OrganBuilder.builder()
            .build()
    );

    // 弗兰肯斯坦肌肉
    public static final Supplier<Item> FRANKENSTEIN_MUSCLE = WAICItem.ITEM.register(
        "frankenstein_muscle",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 肿瘤器官 ====================
    // 肿瘤心脏
    public static final Supplier<Item> TUMOR_HEART = WAICItem.ITEM.register(
        "tumor_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤肺脏
    public static final Supplier<Item> TUMOR_LUNG = WAICItem.ITEM.register(
        "tumor_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤胃
    public static final Supplier<Item> TUMOR_STOMACH = WAICItem.ITEM.register(
        "tumor_stomach",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤肠子
    public static final Supplier<Item> TUMOR_INTESTINE = WAICItem.ITEM.register(
        "tumor_intestine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤肾脏
    public static final Supplier<Item> TUMOR_KIDNEY = WAICItem.ITEM.register(
        "tumor_kidney",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤脾脏
    public static final Supplier<Item> TUMOR_SPLEEN = WAICItem.ITEM.register(
        "tumor_spleen",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤肝脏
    public static final Supplier<Item> TUMOR_LIVER = WAICItem.ITEM.register(
        "tumor_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤阑尾
    public static final Supplier<Item> TUMOR_APPENDIX = WAICItem.ITEM.register(
        "tumor_appendix",
        () -> OrganBuilder.builder()
            .build()
    );

    // 肿瘤肌肉
    public static final Supplier<Item> TUMOR_MUSCLE = WAICItem.ITEM.register(
        "tumor_muscle",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 九狱器官 ====================
    // 灵薄（阑尾）
    public static final Supplier<Item> LIMBO = WAICItem.ITEM.register(
        "limbo",
        () -> OrganBuilder.builder()
            .build()
    );

    // 色欲（肠子）
    public static final Supplier<Item> LUST = WAICItem.ITEM.register(
        "lust",
        () -> OrganBuilder.builder()
            .build()
    );

    // 暴食（胃）
    public static final Supplier<Item> GLUTTONY = WAICItem.ITEM.register(
        "gluttony",
        () -> OrganBuilder.builder()
            .build()
    );

    // 贪婪（肺脏）
    public static final Supplier<Item> GREED = WAICItem.ITEM.register(
        "greed",
        () -> OrganBuilder.builder()
            .build()
    );

    // 愤怒（肝脏）
    public static final Supplier<Item> WRATH = WAICItem.ITEM.register(
        "wrath",
        () -> OrganBuilder.builder()
            .build()
    );

    // 异端（脾脏）
    public static final Supplier<Item> HERESY = WAICItem.ITEM.register(
        "heresy",
        () -> OrganBuilder.builder()
            .build()
    );

    // 暴力（肌肉）
    public static final Supplier<Item> VIOLENCE = WAICItem.ITEM.register(
        "violence",
        () -> OrganBuilder.builder()
            .build()
    );

    // 欺诈（肾脏）
    public static final Supplier<Item> FRAUD = WAICItem.ITEM.register(
        "fraud",
        () -> OrganBuilder.builder()
            .build()
    );

    // 背叛（心脏）
    public static final Supplier<Item> TREASON = WAICItem.ITEM.register(
        "treason",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 双子魔眼器官 ====================
    // 奇怪的眼球
    public static final Supplier<Item> STRANGE_EYEBALL = WAICItem.ITEM.register(
        "strange_eyeball",
        () -> OrganBuilder.builder()
            .build()
    );

    // 诡异的眼球
    public static final Supplier<Item> EERIE_EYEBALL = WAICItem.ITEM.register(
        "eerie_eyeball",
        () -> OrganBuilder.builder()
            .build()
    );

    // 奇怪的机械眼球
    public static final Supplier<Item> STRANGE_MECHANICAL_EYEBALL = WAICItem.ITEM.register(
        "strange_mechanical_eyeball",
        () -> OrganBuilder.builder()
            .build()
    );

    // 诡异的机械眼球
    public static final Supplier<Item> EERIE_MECHANICAL_EYEBALL = WAICItem.ITEM.register(
        "eerie_mechanical_eyeball",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 拟态器官 ====================
    // 拟态心脏
    public static final Supplier<Item> MIMIC_HEART = WAICItem.ITEM.register(
        "mimic_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 拟态肝脏
    public static final Supplier<Item> MIMIC_LIVER = WAICItem.ITEM.register(
        "mimic_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 拟态肺脏
    public static final Supplier<Item> MIMIC_LUNG = WAICItem.ITEM.register(
        "mimic_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // ==================== 单个器官 ====================
    // 闹鬼的骨头
    public static final Supplier<Item> HAUNTED_BONE = WAICItem.ITEM.register(
        "haunted_bone",
        () -> OrganBuilder.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .chestCavityOpen(WAICOrganSkillUtil::hauntedBoneChestCavityOpen)
            .chestCavityClose(WAICOrganSkillUtil::hauntedBoneChestCavityClose)
            .addValueAttribute(InitAttribute.SPEED, 1)
            .addValueAttribute(Attributes.LUCK, 1)
            .build()
    );

    // 剑骨头
    public static final Supplier<Item> SWORD_BONE = WAICItem.ITEM.register(
        "sword_bone",
        () -> OrganBuilder.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .properties(properties -> properties.attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)))
            .addValueAttribute(InitAttribute.NERVES, 1.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.75)
            .build()
    );

    // 直肠子
    public static final Supplier<Item> STRAIGHT_INTESTINE = WAICItem.ITEM.register(
        "straight_intestine",
        () -> OrganBuilder.builder()
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .build()
    );

    // 窝瓜
    public static final Supplier<Item> SQUASH = WAICItem.ITEM.register(
        "squash",
        () -> OrganBuilder.builder()
            .build()
    );

    public static void register() {
    }
}

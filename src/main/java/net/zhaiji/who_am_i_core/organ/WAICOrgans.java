package net.zhaiji.who_am_i_core.organ;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.CustomData;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganSkillUtil;
import net.zhaiji.who_am_i_core.item.PaletteItem;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.WAICOrganSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class WAICOrgans {
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

    // 墨水阑尾
    public static final Supplier<Item> INK_APPENDIX = WAICItem.ITEM.register(
        "ink_appendix",
        () -> Organ.builder()
            .build()
    );

    // 墨水肋骨
    public static final Supplier<Item> INK_RIB = WAICItem.ITEM.register(
        "ink_rib",
        () -> Organ.builder()
            .build()
    );

    // 墨水肌肉
    public static final Supplier<Item> INK_MUSCLE = WAICItem.ITEM.register(
        "ink_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1)
            .addValueAttribute(InitAttribute.SPEED, 1)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .hurt(WAICOrganSkillUtil::inkMuscleSkill)
            .build()
    );

    // 墨水瓶
    public static final Supplier<Item> INK_BOTTLE = WAICItem.ITEM.register(
        "ink_bottle",
        () -> Organ.builder()
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
                int value = tag.contains("ink") ? tag.getInt("ink") : 0;
                List<Component> add = List.of(
                    // TODO 应该写入语言键
                    Component.translatable("墨水:%1$s", value)
                );
                TooltipUtil.simpleTooltipAdd(tooltipComponents, add);
            })
            .build()
    );

    // 钢笔尖
    public static final Supplier<Item> NIB = WAICItem.ITEM.register(
        "nib",
        () -> Organ.builder()
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
        () -> Organ.builder(
                new PaletteItem(
                    new Item.Properties()
                        .stacksTo(1)
                        .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)
                )
            )
            // TODO 应该简化
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                BundleContents contents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
                Map<SchoolType, Integer> dyeCount = new HashMap<>();
                // 统计各流派对应的染料数量
                for (ItemStack itemStack : contents.itemsCopy()) {
                    if (itemStack.is(Items.RED_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.BLOOD.get(),
                            dyeCount.getOrDefault(SchoolRegistry.BLOOD.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.ORANGE_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.FIRE.get(),
                            dyeCount.getOrDefault(SchoolRegistry.FIRE.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.YELLOW_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.HOLY.get(),
                            dyeCount.getOrDefault(SchoolRegistry.HOLY.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.LIGHT_BLUE_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.ICE.get(),
                            dyeCount.getOrDefault(SchoolRegistry.ICE.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.BLUE_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.LIGHTNING.get(),
                            dyeCount.getOrDefault(SchoolRegistry.LIGHTNING.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.GREEN_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.NATURE.get(),
                            dyeCount.getOrDefault(SchoolRegistry.NATURE.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.CYAN_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.ELDRITCH.get(),
                            dyeCount.getOrDefault(SchoolRegistry.ELDRITCH.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.PURPLE_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.ENDER.get(),
                            dyeCount.getOrDefault(SchoolRegistry.ENDER.get(), 0) + itemStack.getCount()
                        );
                    } else if (itemStack.is(Items.GRAY_DYE)) {
                        dyeCount.put(
                            SchoolRegistry.EVOCATION.get(),
                            dyeCount.getOrDefault(SchoolRegistry.EVOCATION.get(), 0) + itemStack.getCount()
                        );
                    }
                }
                // 构建工具提示
                List<Component> add = new java.util.ArrayList<>();
                for (var entry : dyeCount.entrySet()) {
                    if (entry.getValue() > 0) {
                        SchoolType school = entry.getKey();
                        int count = entry.getValue();
                        ResourceLocation id = school.getId();
                        String schoolName = switch (id.getPath()) {
                            case "blood" -> "猩红";
                            case "fire" -> "炽焰";
                            case "holy" -> "神圣";
                            case "ice" -> "冰霜";
                            case "lightning" -> "雷霆";
                            case "nature" -> "自然";
                            case "eldritch" -> "邪术";
                            case "ender" -> "末影";
                            case "evocation" -> "唤魔";
                            default -> id.getPath();
                        };
                        add.add(Component.literal("%s:%s".formatted(schoolName, count)));
                    }
                }
                if (!add.isEmpty()) {
                    TooltipUtil.simpleTooltipAdd(tooltipComponents, add);
                }
            })
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
    // 弗兰肯斯坦心脏
    public static final Supplier<Item> FRANKENSTEIN_HEART = WAICItem.ITEM.register(
        "frankenstein_heart",
        () -> Organ.builder()
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

    // ==================== 肿瘤器官 ====================
    // 肿瘤心脏
    public static final Supplier<Item> TUMOR_HEART = WAICItem.ITEM.register(
        "tumor_heart",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤肺脏
    public static final Supplier<Item> TUMOR_LUNG = WAICItem.ITEM.register(
        "tumor_lung",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤胃
    public static final Supplier<Item> TUMOR_STOMACH = WAICItem.ITEM.register(
        "tumor_stomach",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤肠子
    public static final Supplier<Item> TUMOR_INTESTINE = WAICItem.ITEM.register(
        "tumor_intestine",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤肾脏
    public static final Supplier<Item> TUMOR_KIDNEY = WAICItem.ITEM.register(
        "tumor_kidney",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤脾脏
    public static final Supplier<Item> TUMOR_SPLEEN = WAICItem.ITEM.register(
        "tumor_spleen",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤肝脏
    public static final Supplier<Item> TUMOR_LIVER = WAICItem.ITEM.register(
        "tumor_liver",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤阑尾
    public static final Supplier<Item> TUMOR_APPENDIX = WAICItem.ITEM.register(
        "tumor_appendix",
        () -> Organ.builder()
            .build()
    );

    // 肿瘤肌肉
    public static final Supplier<Item> TUMOR_MUSCLE = WAICItem.ITEM.register(
        "tumor_muscle",
        () -> Organ.builder()
            .build()
    );

    // ==================== 九狱器官 ====================
    // 灵薄（阑尾）
    public static final Supplier<Item> LIMBO = WAICItem.ITEM.register(
        "limbo",
        () -> Organ.builder()
            .build()
    );

    // 色欲（肠子）
    public static final Supplier<Item> LUST = WAICItem.ITEM.register(
        "lust",
        () -> Organ.builder()
            .build()
    );

    // 暴食（胃）
    public static final Supplier<Item> GLUTTONY = WAICItem.ITEM.register(
        "gluttony",
        () -> Organ.builder()
            .build()
    );

    // 贪婪（肺脏）
    public static final Supplier<Item> GREED = WAICItem.ITEM.register(
        "greed",
        () -> Organ.builder()
            .build()
    );

    // 愤怒（肝脏）
    public static final Supplier<Item> WRATH = WAICItem.ITEM.register(
        "wrath",
        () -> Organ.builder()
            .build()
    );

    // 异端（脾脏）
    public static final Supplier<Item> HERESY = WAICItem.ITEM.register(
        "heresy",
        () -> Organ.builder()
            .build()
    );

    // 暴力（肌肉）
    public static final Supplier<Item> VIOLENCE = WAICItem.ITEM.register(
        "violence",
        () -> Organ.builder()
            .build()
    );

    // 欺诈（肾脏）
    public static final Supplier<Item> FRAUD = WAICItem.ITEM.register(
        "fraud",
        () -> Organ.builder()
            .build()
    );

    // 背叛（心脏）
    public static final Supplier<Item> TREACHERY = WAICItem.ITEM.register(
        "treachery",
        () -> Organ.builder()
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
            .heal(WAICOrganSkillUtil::mimicHealBoost)
            .build()
    );

    // 拟态肝脏
    public static final Supplier<Item> MIMIC_LIVER = WAICItem.ITEM.register(
        "mimic_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 3)
            .heal(WAICOrganSkillUtil::mimicHealBoost)
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
            .heal(WAICOrganSkillUtil::mimicHealBoost)
            .build()
    );

    // ==================== 单个器官 ====================
    // 闹鬼的骨头
    public static final Supplier<Item> HAUNTED_BONE = WAICItem.ITEM.register(
        "haunted_bone",
        () -> Organ.builder()
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
            .build()
    );

    public static void register() {
    }
}

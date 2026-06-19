package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.api.TooltipsKeyContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WAICTooltipUtil {
    /**
     * 全部染料物品有序列表（用于 detailed 模式遍历显示）
     */
    public static final List<Item> ALL_DYES = List.of(
        Items.RED_DYE,
        Items.ORANGE_DYE,
        Items.YELLOW_DYE,
        Items.LIGHT_BLUE_DYE,
        Items.BLUE_DYE,
        Items.GREEN_DYE,
        Items.CYAN_DYE,
        Items.PURPLE_DYE,
        Items.GRAY_DYE
    );

    /**
     * 将染料物品映射为对应的法术流派
     */
    public static SchoolType dyeToSchool(Item dye) {
        if (dye == Items.RED_DYE) return SchoolRegistry.BLOOD.get();
        if (dye == Items.ORANGE_DYE) return SchoolRegistry.FIRE.get();
        if (dye == Items.YELLOW_DYE) return SchoolRegistry.HOLY.get();
        if (dye == Items.LIGHT_BLUE_DYE) return SchoolRegistry.ICE.get();
        if (dye == Items.BLUE_DYE) return SchoolRegistry.LIGHTNING.get();
        if (dye == Items.GREEN_DYE) return SchoolRegistry.NATURE.get();
        if (dye == Items.CYAN_DYE) return SchoolRegistry.ELDRITCH.get();
        if (dye == Items.PURPLE_DYE) return SchoolRegistry.ENDER.get();
        if (dye == Items.GRAY_DYE) return SchoolRegistry.EVOCATION.get();
        return null;
    }

    /**
     * 调色盘染料统计
     * <p>
     * Simple 模式：只显示数量 > 0 的染料
     * Detailed 模式（按 Shift 或配置开启）：显示全部 9 种染料，数量为 0 的也显示
     * </p>
     */
    public static List<Component> paletteDyeSection(
        ChestCavityData data,
        int index,
        ItemStack stack,
        TooltipsKeyContext keyContext,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        BundleContents contents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        Map<SchoolType, Integer> dyeCount = new HashMap<>();
        for (ItemStack itemStack : contents.itemsCopy()) {
            SchoolType school = dyeToSchool(itemStack.getItem());
            if (school != null) {
                dyeCount.merge(school, itemStack.getCount(), Integer::sum);
            }
        }
        List<Component> result = new ArrayList<>();
        if (TooltipUtil.isDetailedMode(keyContext)) {
            // 详细模式：显示全部 9 种染料，数量为 0 的也显示
            for (Item dye : ALL_DYES) {
                SchoolType school = dyeToSchool(dye);
                if (school == null) continue;
                int count = dyeCount.getOrDefault(school, 0);
                result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                    .append(Component.translatable(WAICOrgans.PALETTE_DYE_TRANSLATION, school.getDisplayName(), count)));
            }
        } else {
            // 简略模式：只显示已有的染料
            for (Map.Entry<SchoolType, Integer> entry : dyeCount.entrySet()) {
                result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                    .append(Component.translatable(WAICOrgans.PALETTE_DYE_TRANSLATION, entry.getKey().getDisplayName(), entry.getValue())));
            }
        }
        return result;
    }

    /**
     * 调色盘 ShiftHint — 染料种类未满 9 种时显示"按住[Shift]查看详细说明"
     */
    public static List<Component> paletteShiftHint(
        ChestCavityData data,
        int index,
        ItemStack stack,
        TooltipsKeyContext keyContext,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        BundleContents contents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        Set<Item> dyeTypes = new HashSet<>();
        for (ItemStack itemStack : contents.itemsCopy()) {
            if (ALL_DYES.contains(itemStack.getItem())) dyeTypes.add(itemStack.getItem());
        }
        if (dyeTypes.size() < 9) {
            return List.of(
                Component.empty()
                    .append(Component.translatable(TooltipUtil.PREFIX + "tooltip.hint.0").withStyle(ChatFormatting.GRAY))
                    .append(Component.translatable(TooltipUtil.PREFIX + "tooltip.hint.1")
                        .withStyle(keyContext.isKeyShiftDown() ? ChatFormatting.YELLOW : ChatFormatting.DARK_GRAY))
                    .append(Component.translatable(TooltipUtil.PREFIX + "tooltip.hint.2").withStyle(ChatFormatting.GRAY))
            );
        }
        return List.of();
    }

    /**
     * 纯注入工具：将 FormulaValue 注入到描述文本的 %s 占位符中
     * <p>
     * 内部仅根据 detailed 判断使用 simple 还是 detailed/active_skill 翻译键前缀，
     * 然后按 values 的行号注入对应 FormulaValue。
     * 调用方负责根据 detailed 构造对应的 Map（simple 和 detailed 描述结构可能不对称）。
     * </p>
     *
     * @param stack    物品栈
     * @param baseType 段落类型 "passive_effect" 或 "active_skill"
     * @param detailed 是否详细模式
     * @param entity   实体（null 则 FormulaValue 显示 "?"）
     * @param ctrl 是否按下 Ctrl（展开公式）
     * @param values   行号 → FormulaValue 列表，由调用方按当前描述结构构造，可为 null
     */
    public static List<Component> dynamicEffectLines(
        ItemStack stack,
        String baseType,
        boolean detailed,
        LivingEntity entity,
        boolean ctrl,
        Map<Integer, List<FormulaValue>> values
    ) {
        boolean useSimple = !detailed && TooltipUtil.hasTranslation(
            TooltipUtil.getBaseKey(stack) + "." + baseType + ".simple.0"
        );
        String effectiveType = useSimple ? baseType + ".simple" : baseType;
        String baseKey = TooltipUtil.getBaseKey(stack) + "." + effectiveType + ".";

        List<Component> result = new ArrayList<>();
        int i = 0;
        String key;
        while (TooltipUtil.hasTranslation(key = baseKey + i)) {
            List<FormulaValue> lineValues = values != null ? values.get(i) : null;
            if (lineValues != null && !lineValues.isEmpty()) {
                Object[] componentArgs = lineValues.stream()
                    .map(formulaValue -> formulaValue.buildComponent(entity, ctrl))
                    .toArray();
                result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                    .append(Component.translatable(key, componentArgs)));
            } else {
                result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                    .append(Component.translatable(key)));
            }
            i++;
        }
        return result;
    }

    /**
     * Ctrl 提示行 — 显示「按住 [Ctrl] 查看公式」
     * <p>
     * 参考 CCB ShiftHint 的三段式实现，颜色随 Ctrl 状态变化。
     * </p>
     */
    public static List<Component> ctrlHint(TooltipsKeyContext keyContext) {
        boolean ctrl = keyContext.isKeyCtrlDown();
        return List.of(
            Component.empty()
                .append(Component.translatable("tooltip.who_am_i_core.ctrl_hint.0").withStyle(ChatFormatting.GRAY))
                .append(Component.translatable("tooltip.who_am_i_core.ctrl_hint.1")
                    .withStyle(ctrl ? ChatFormatting.YELLOW : ChatFormatting.DARK_GRAY))
                .append(Component.translatable("tooltip.who_am_i_core.ctrl_hint.2").withStyle(ChatFormatting.GRAY))
        );
    }

    /**
     * 不换行空格操作符
     */
    public static MutableComponent formulaOperator(String operator) {
        return Component.literal("\u00A0" + operator + "\u00A0");
    }

    /**
     * 属性名
     */
    public static MutableComponent attributeName(Holder<Attribute> attribute) {
        return Component.translatable(attribute.value().getDescriptionId());
    }

    /**
     * 标签名
     */
    public static MutableComponent tagName(TagKey<Item> tag) {
        return ItemTagManager.getTagDisplayName(tag);
    }

    /**
     * 标签器官数量名（包裹 tag 显示名，形如「机械器官数量」）
     */
    public static MutableComponent tagOrganCountName(TagKey<Item> tag) {
        return Component.translatable("formula.who_am_i_core.tag_organ_count", tagName(tag));
    }
}

package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.api.OrganTooltip;
import net.zhaiji.chestcavitybeyond.api.TooltipsKeyContext;
import net.zhaiji.chestcavitybeyond.api.function.OrganTooltipConsumer;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WAICTooltipUtil {
    /**
     * 完全覆盖器官工具提示，仅显示「仍未完成」信息
     */
    public static final OrganTooltipConsumer UNFINISHED_TOOLTIP = (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
        List<Component> components = List.of(Component.literal(TooltipUtil.DEFAULT_PREFIX)
            .append(Component.translatable("organ.who_am_i_core.unfinished"))
            .withStyle(ChatFormatting.GRAY));
        TooltipUtil.simpleTooltipAdd(tooltipComponents, components);
    };

    /**
     * 九狱器官工具提示
     * <p>
     * 根据胸腔中九狱器官数量动态显示被动效果的激活状态：
     * 已激活效果白色，未激活效果暗灰色。顶部有共用提示行说明激活条件。
     * </p>
     */
    public static final OrganTooltipConsumer NINE_HELL_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> result = new ArrayList<>();
            // 计算九狱器官数量
            int count = data.getOrganCount(WAICItemTagManager.NINE_HELL);
            if (index == -1) count++;
            // 共用提示行（灰色 + • 前缀）
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX).append(Component.translatable("organ.who_am_i_core.nine_hell.hint")));
            // 复用 CCB 的 detailed 分支逻辑（九狱器官无 simple 简略文本）
            List<Component> lines = TooltipUtil.addSimpleOrDetailedLines(
                stack,
                "passive_effect",
                TooltipUtil.isDetailedMode(keyContext),
                TooltipUtil.DEFAULT_PREFIX
            );
            // 按数量给未激活效果加暗灰色
            for (int i = 0; i < lines.size(); i++) {
                if (i >= count) {
                    lines.set(i, lines.get(i).copy().withStyle(ChatFormatting.DARK_GRAY));
                }
            }
            result.addAll(lines);
            return result;
        })
        .build();

    /**
     * 制御棒工具提示
     * <p>
     * 第一行为条件提示（始终灰色），后续效果行根据制御棒是否在胸中新星相邻槽位来决定颜色：
     * 已激活白色，未激活暗灰色。
     * </p>
     */
    public static final OrganTooltipConsumer CONTROL_ROD_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> result = new ArrayList<>();
            // 条件提示行
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                .append(Component.translatable("organ.who_am_i_core.control_rod.hint")));
            // 判断制御棒是否在胸中新星的相邻槽位
            boolean active = false;
            if (index >= 0) {
                for (int i = 0; i < data.getSlots(); i++) {
                    if (data.getStackInSlot(i).is(MowziesMobOrgans.CHEST_NOVA.get())) {
                        List<Integer> adjacent = OrganUtil.getAdjacentSlots(i, data.getSlots());
                        if (adjacent.contains(index)) {
                            active = true;
                            break;
                        }
                    }
                }
            }
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            // 复用 CCB 的 detailed 分支逻辑
            List<Component> lines = TooltipUtil.addSimpleOrDetailedLines(
                stack,
                "passive_effect",
                detailed,
                TooltipUtil.DEFAULT_PREFIX
            );
            // 第一行是条件提示，跳过它（上面已经手动添加了灰色的条件行）
            // 剩余行为效果行，根据激活状态着色
            for (int i = 0; i < lines.size(); i++) {
                if (!active) {
                    lines.set(i, lines.get(i).copy().withStyle(ChatFormatting.DARK_GRAY));
                }
            }
            result.addAll(lines);
            return result;
        })
        .build();

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
     * 调色盘染料统计 — TooltipSectionFunction 版本
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
}

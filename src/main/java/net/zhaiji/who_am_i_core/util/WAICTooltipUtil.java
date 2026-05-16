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
import net.zhaiji.chestcavitybeyond.api.TooltipsKeyContext;
import net.zhaiji.chestcavitybeyond.api.function.OrganTooltipConsumer;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WAICTooltipUtil {
    /**
     * 完全覆盖器官工具提示，仅显示「仍未完成」信息
     */
    public static final OrganTooltipConsumer UNFINISHED_TOOLTIP = (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
        List<Component> components = List.of(
            Component.literal(TooltipUtil.DEFAULT_PREFIX)
                .append(Component.translatable("organ." + WhoAmICore.MOD_ID + ".unfinished"))
                .withStyle(ChatFormatting.GRAY)
        );
        TooltipUtil.simpleTooltipAdd(tooltipComponents, components);
    };

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
        for (var entry : dyeCount.entrySet()) {
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX).append(
                Component.translatable(WAICOrgans.PALETTE_DYE_TRANSLATION, entry.getKey().getDisplayName(), entry.getValue())));
        }
        return result;
    }
}

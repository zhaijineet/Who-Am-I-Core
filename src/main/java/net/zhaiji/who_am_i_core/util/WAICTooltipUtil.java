package net.zhaiji.who_am_i_core.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.function.OrganTooltipConsumer;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;

import java.util.List;

public class WAICTooltipUtil {
    public static Component organSkill(Item item) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + ".skill");
    }

    public static Component organSkill(Item item, int index) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item)
            .getPath() + ".skill." + index);
    }

    public static Component organDescription(Item item) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item).getPath() + ".description");
    }

    public static Component organDescription(Item item, int index) {
        return Component.translatable("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item)
            .getPath() + ".description." + index);
    }

    /**
     * 创建一个 descriptionTooltip consumer，添加器官描述
     */
    public static OrganTooltipConsumer descriptionTooltip() {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(organDescription(stack.getItem()))
            );
        };
    }

    /**
     * 创建一个 descriptionTooltip consumer，添加带索引的器官描述
     *
     * @param to 0 ~ to 索引
     */
    public static OrganTooltipConsumer descriptionTooltip(int to) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            Component[] components = new Component[to + 1];
            for (int i = 0; i <= to; i++) {
                components[i] = organDescription(stack.getItem(), i);
            }
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(components)
            );
        };
    }

    /**
     * 创建一个 skillTooltip consumer，添加单个技能描述
     */
    public static OrganTooltipConsumer skillTooltip() {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(organSkill(stack.getItem()))
            );
        };
    }

    /**
     * 创建一个 skillTooltip consumer，添加带索引的技能描述
     *
     * @param to 0 ~ to 索引
     */
    public static OrganTooltipConsumer skillTooltip(int to) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            Component[] components = new Component[to + 1];
            for (int i = 0; i <= to; i++) {
                components[i] = organSkill(stack.getItem(), i);
            }
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(components)
            );
        };
    }

    /**
     * 创建一个使用指定翻译键的skillTooltip consumer
     *
     * @param translationKey 翻译键，例如 "organ.who_am_i_core.tooltips.frost_metal_merciless"
     */
    public static OrganTooltipConsumer skillTooltip(String translationKey) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(Component.translatable(translationKey))
            );
        };
    }
}

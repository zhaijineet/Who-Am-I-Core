package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.api.function.OrganTooltipConsumer;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 创建一个使用指定翻译键的 descriptionTooltip consumer
     *
     * @param translationKey 翻译键
     */
    public static OrganTooltipConsumer descriptionTooltip(String translationKey) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(Component.translatable(translationKey))
            );
        };
    }

    /**
     * 创建一个使用多个翻译键的 descriptionTooltip consumer
     *
     * @param keys 翻译键
     */
    public static OrganTooltipConsumer descriptionTooltip(String... keys) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> components = new ArrayList<>();
            for (String key : keys) {
                components.add(Component.translatable(key));
            }
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                components
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
     * @param translationKey 翻译键
     */
    public static OrganTooltipConsumer skillTooltip(String translationKey) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                List.of(Component.translatable(translationKey))
            );
        };
    }

    /**
     * 创建一个使用多个翻译键的 skillTooltip consumer
     */
    public static OrganTooltipConsumer skillTooltip(String... keys) {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> components = new ArrayList<>();
            for (String key : keys) {
                components.add(Component.translatable(key));
            }
            TooltipUtil.simpleTooltipAdd(
                tooltipComponents,
                components
            );
        };
    }

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
     * 创建调色盘的染料统计 skillTooltip consumer
     */
    public static OrganTooltipConsumer paletteDyeTooltip() {
        return (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            BundleContents contents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
            Map<SchoolType, Integer> dyeCount = new HashMap<>();
            for (ItemStack itemStack : contents.itemsCopy()) {
                SchoolType school = dyeToSchool(itemStack.getItem());
                if (school != null) {
                    dyeCount.merge(school, itemStack.getCount(), Integer::sum);
                }
            }
            List<Component> add = new ArrayList<>();
            for (var entry : dyeCount.entrySet()) {
                add.add(Component.translatable(WAICOrgans.PALETTE_DYE_TRANSLATION, entry.getKey().getDisplayName(), entry.getValue()));
            }
            if (!add.isEmpty()) {
                TooltipUtil.simpleTooltipAdd(tooltipComponents, add);
            }
        };
    }
}

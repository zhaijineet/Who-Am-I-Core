package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.api.TooltipsKeyContext;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.manager.RailgunAmmoManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class WAICTooltipUtil {
    // 电磁炮弹药列表
    public static final String RAILGUN_AMMO_TRANSLATION = "organ." + WhoAmICore.MOD_ID + ".railgun.ammo";

    /**
     * 配置枚举值的翻译组件，键格式为 modid.configuration.配置键.枚举小写名
     */
    public static Component configEnumTranslation(String configKey, Enum<?> value) {
        return Component.translatable(WhoAmICore.MOD_ID + ".configuration." + configKey + "." + value.name().toLowerCase(Locale.ROOT));
    }

    /**
     * 全部染料物品有序列表，用于 detailed 模式遍历显示
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
     * 调色盘染料统计段落
     * <p>
     * Simple 模式只显示数量大于 0 的染料。
     * Detailed 模式（按 Shift 或配置开启）显示全部 9 种染料，数量为 0 的也显示。
     * </p>
     */
    public static List<Component> paletteDyeSection(
        ChestCavitySlotContext slotContext,
        TooltipsKeyContext keyContext,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        ItemStack stack = slotContext.stack();
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
            for (Item dye : ALL_DYES) {
                SchoolType school = dyeToSchool(dye);
                if (school == null) continue;
                int count = dyeCount.getOrDefault(school, 0);
                result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                    .append(Component.translatable(WAICOrgans.PALETTE_DYE_TRANSLATION, school.getDisplayName(), count)));
            }
        } else {
            for (Map.Entry<SchoolType, Integer> entry : dyeCount.entrySet()) {
                result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                    .append(Component.translatable(WAICOrgans.PALETTE_DYE_TRANSLATION, entry.getKey().getDisplayName(), entry.getValue())));
            }
        }
        return result;
    }

    /**
     * 调色盘 Hint 段落，染料种类未满 9 种时显示「按住[Shift]查看详细说明」
     */
    public static List<Component> paletteHint(
        ChestCavitySlotContext slotContext,
        TooltipsKeyContext keyContext,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        BundleContents contents = slotContext.stack().getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        Set<Item> dyeTypes = new HashSet<>();
        for (ItemStack itemStack : contents.itemsCopy()) {
            if (ALL_DYES.contains(itemStack.getItem())) {
                dyeTypes.add(itemStack.getItem());
            }
        }
        if (dyeTypes.size() >= 9) {
            return List.of();
        }
        return List.of(Component.translatable(
            TooltipUtil.PREFIX + "tooltip.shift_hint",
            Component.translatable(TooltipUtil.PREFIX + "tooltip.hint.shift")
                .withStyle(keyContext.isKeyShiftDown() ? ChatFormatting.YELLOW : ChatFormatting.DARK_GRAY)
        ).withStyle(ChatFormatting.GRAY));
    }

    /**
     * 电磁炮弹药列表段落
     * <p>
     * Detailed 模式（Shift）展示所有可用弹药及计算后的最终伤害。
     * Simple 模式不显示弹药列表。
     * </p>
     */
    public static List<Component> railgunAmmoSection(
        ChestCavitySlotContext slotContext,
        TooltipsKeyContext keyContext,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (!TooltipUtil.isDetailedMode(keyContext)) return List.of();
        float multiplier = AnvilCraftOrganUtil.getRailgunDamageMultiplier(slotContext);
        List<Component> result = new ArrayList<>();
        for (RailgunAmmoManager.AmmoEntry entry : RailgunAmmoManager.getAmmoList()) {
            float finalDamage = entry.baseDamage() * multiplier;
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                .append(Component.translatable(
                    RAILGUN_AMMO_TRANSLATION,
                    entry.displayItem().getDescription(),
                    TooltipUtil.formatAttributeValue(finalDamage)
                )));
        }
        return result;
    }

    public static MutableComponent globalFireOrganCountFormula(ChestCavitySlotContext context) {
        return elementalOrganCountFormula(context, false, true);
    }

    public static MutableComponent globalIceOrganCountFormula(ChestCavitySlotContext context) {
        return elementalOrganCountFormula(context, false, false);
    }

    public static MutableComponent localFireOrganCountFormula(ChestCavitySlotContext context) {
        return elementalOrganCountFormula(context, true, true);
    }

    public static MutableComponent localIceOrganCountFormula(ChestCavitySlotContext context) {
        return elementalOrganCountFormula(context, true, false);
    }

    private static MutableComponent elementalOrganCountFormula(
        ChestCavitySlotContext context,
        boolean local,
        boolean fireFirst
    ) {
        TagKey<Item> firstTag = fireFirst ? WAICItemTagManager.FIRE : WAICItemTagManager.ICE;
        TagKey<Item> secondTag = fireFirst ? WAICItemTagManager.ICE : WAICItemTagManager.FIRE;
        Component firstName = local
                              ? Component.translatable(fireFirst
                                                       ? "formula.who_am_i_core.local_fire_count"
                                                       : "formula.who_am_i_core.local_ice_count")
                              : TooltipUtil.tagOrganCountName(firstTag);
        Component secondName = local
                               ? Component.translatable(fireFirst
                                                        ? "formula.who_am_i_core.local_ice_count"
                                                        : "formula.who_am_i_core.local_fire_count")
                               : TooltipUtil.tagOrganCountName(secondTag);
        int firstCount = getElementalOrganCountWithSelf(context, firstTag, local);
        int secondCount = getElementalOrganCountWithSelf(context, secondTag, local);
        return Component.empty()
            .append(Component.literal("("))
            .append(firstName)
            .append(Component.literal(String.valueOf(firstCount)))
            .append(TooltipUtil.formulaOperator(OrganUtil.usesAdditiveElementalOrganCounts(context) ? "+" : "-"))
            .append(secondName)
            .append(Component.literal(String.valueOf(secondCount)))
            .append(Component.literal(")"));
    }

    private static int getElementalOrganCountWithSelf(
        ChestCavitySlotContext context,
        TagKey<Item> tag,
        boolean local
    ) {
        return local
               ? OrganUtil.getLocalOrganCountWithSelf(context, tag)
               : ChestCavityUtil.getOrganCountWithSelf(context, tag);
    }
}

package net.zhaiji.who_am_i_core.manager;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.CustomData;
import net.zhaiji.chestcavitybeyond.api.OrganTooltip;
import net.zhaiji.chestcavitybeyond.api.function.OrganTooltipConsumer;
import net.zhaiji.chestcavitybeyond.api.function.TooltipSectionFunction;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.util.FormulaValue;
import net.zhaiji.who_am_i_core.util.OrganUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO FormulaValue 提为 static final 暂时搁置，需在此之后重构CCB的OrganTooltip，让传入的是ChestCavitySlotContext
 */
public class WAICTooltipManager {
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
     * 九狱器官
     * <p>
     * 根据胸腔中九狱器官数量动态显示被动效果的激活状态：
     * 已激活效果白色，未激活效果暗灰色。顶部有共用提示行说明激活条件。
     * </p>
     */
    public static final OrganTooltipConsumer NINE_HELL_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> result = new ArrayList<>();
            int count = data.getOrganCount(WAICItemTagManager.NINE_HELL);
            if (index == -1) count++;
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX).append(Component.translatable("organ.who_am_i_core.nine_hell.hint")));
            List<Component> lines = TooltipUtil.addSimpleOrDetailedLines(
                stack,
                "passive_effect",
                TooltipUtil.isDetailedMode(keyContext),
                TooltipUtil.DEFAULT_PREFIX
            );
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
     * 制御棒
     * <p>
     * 第一行为条件提示（始终灰色），后续效果行根据制御棒是否在胸中新星相邻槽位来决定颜色：
     * 已激活白色，未激活暗灰色。
     * </p>
     */
    public static final OrganTooltipConsumer CONTROL_ROD_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> result = new ArrayList<>();
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                .append(Component.translatable("organ.who_am_i_core.control_rod.hint")));
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
            List<Component> lines = TooltipUtil.addSimpleOrDetailedLines(
                stack,
                "passive_effect",
                TooltipUtil.isDetailedMode(keyContext),
                TooltipUtil.DEFAULT_PREFIX
            );
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
     * Ctrl 提示行（所有动态数值器官共用）
     */
    private static final TooltipSectionFunction CTRL_HINT_SECTION =
        (data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) ->
            WAICTooltipUtil.ctrlHint(keyContext);
    /**
     * 墨水瓶 — 根据是否为胸腔中激活的（第一个）墨水瓶决定墨水量行的显示
     */
    public static final OrganTooltipConsumer INK_BOTTLE_TOOLTIP = OrganTooltip.builder()
        .afterPassiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            float value = tag.contains("ink") ? tag.getFloat("ink") : 0;
            boolean active = data != null && WAICOrganUtil.isInkBottleActive(data, index, stack);
            String capacityDisplay = active ? String.valueOf(WAICOrganUtil.getInkCapacity(data)) : "?";
            MutableComponent line = Component.literal(TooltipUtil.DEFAULT_PREFIX)
                .append(Component.translatable(
                    WAICOrgans.INK_BOTTLE_INK_TRANSLATION,
                    TooltipUtil.formatAttributeValue(value),
                    capacityDisplay
                ));
            if (!active) {
                line.withStyle(ChatFormatting.DARK_GRAY);
            }
            return List.of(line);
        })
        .build();
    /**
     * 猩红心脏 — 血液转化率随代谢缩放
     */
    public static final OrganTooltipConsumer CRIMSON_HEART_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> Component.literal("1:" + TooltipUtil.formatAttributeValue(
                    3.0F + (float) (livingEntity.getAttributeValue(InitAttribute.METABOLISM) * 0.2F))),
                livingEntity -> Component.empty()
                    .append(Component.literal("3"))
                    .append(WAICTooltipUtil.formulaOperator("+"))
                    .append(WAICTooltipUtil.attributeName(InitAttribute.METABOLISM))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(livingEntity.getAttributeValue(InitAttribute.METABOLISM))))
                    .append(WAICTooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.2"))
            );
            // 无 simple 描述，useSimple 永远为 false，直接传 detailed Map
            return WAICTooltipUtil.dynamicEffectLines(
                stack, "passive_effect", detailed, entity, ctrl,
                Map.of(1, List.of(formulaValue))
            );
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 墨水肌肉 — 伤害转墨水随墨水器官数量缩放
     */
    public static final OrganTooltipConsumer INK_MUSCLE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> Component.literal("1:" + TooltipUtil.formatAttributeValue(
                    5.0F + OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.INK, index) * 0.5F)),
                livingEntity -> {
                    int inkCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.INK, index);
                    return Component.empty()
                        .append(Component.literal("5"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.INK))
                        .append(Component.literal(String.valueOf(inkCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.5"));
                }
            );
            // 无 simple 描述，useSimple 永远为 false，直接传 detailed Map
            return WAICTooltipUtil.dynamicEffectLines(
                stack, "passive_effect", detailed, entity, ctrl,
                Map.of(0, List.of(formulaValue))
            );
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 咒魂心脏 — 冲刺增伤随力量缩放
     */
    public static final OrganTooltipConsumer PHANTOM_HEART_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    float bonus = (float) (livingEntity.getAttributeValue(InitAttribute.STRENGTH) * 0.005 + 0.15) * 100;
                    return Component.literal(String.format("%.1f", bonus) + "%");
                },
                livingEntity -> Component.empty()
                    .append(Component.literal("("))
                    .append(Component.literal("15"))
                    .append(WAICTooltipUtil.formulaOperator("+"))
                    .append(WAICTooltipUtil.attributeName(InitAttribute.STRENGTH))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(livingEntity.getAttributeValue(InitAttribute.STRENGTH))))
                    .append(WAICTooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.5"))
                    .append(Component.literal(")%"))
            );
            // 无 simple 描述，useSimple 永远为 false，直接传 detailed Map
            return WAICTooltipUtil.dynamicEffectLines(
                stack, "passive_effect", detailed, entity, ctrl,
                Map.of(0, List.of(formulaValue))
            );
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 蓄能电芯 — 每秒回血随机械器官数量缩放
     */
    public static final OrganTooltipConsumer POWER_CELL_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> Component.literal(TooltipUtil.formatAttributeValue(
                    0.5F + OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index) * 0.05F)),
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.empty()
                        .append(Component.literal("0.5"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                        .append(Component.literal(String.valueOf(mechanicalCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.05"));
                }
            );
            // 无 simple 描述，useSimple 永远为 false，直接传 detailed Map
            return WAICTooltipUtil.dynamicEffectLines(
                stack, "passive_effect", detailed, entity, ctrl,
                Map.of(0, List.of(formulaValue))
            );
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 炽面甲 — 命中回血随温度缩放
     */
    public static final OrganTooltipConsumer BLAZING_VISAGE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    // 局部温度：index>=0 遍历九宫格，index==-1 只取自身（炽面甲 +1 已由属性计入）
                    double temperature = OrganUtil.getLocalTemperature(data, index, stack);
                    // 负温度时保底 0，完全不回血
                    return Component.literal(TooltipUtil.formatAttributeValue(
                        Math.max(0, 1.0F + (float) Math.floor(temperature * 0.5))));
                },
                livingEntity -> {
                    double temperature = OrganUtil.getLocalTemperature(data, index, stack);
                    return Component.empty()
                        .append(Component.literal("1"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(Component.literal("floor("))
                        .append(Component.translatable("formula.who_am_i_core.local_temperature"))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(temperature)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.5)"));
                }
            );
            // simple 单段含 1 个 %s 在 line 0；detailed 含动态 %s 在 line 2
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(2, List.of(formulaValue))
                                                      : Map.of(0, List.of(formulaValue));
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 风暴脊柱 — 吸收比例和上限随防御缩放
     */
    public static final OrganTooltipConsumer STORM_SPINE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue ratioFormulaValue = new FormulaValue(
                livingEntity -> {
                    double defense = livingEntity.getAttributeValue(InitAttribute.DEFENSE);
                    if (index == -1) defense += 1; // 风暴脊柱自身防御属性 +1
                    float ratio = (float) (defense * 0.005 + 0.15);
                    return Component.literal(String.format("%.1f", ratio * 100) + "%");
                },
                livingEntity -> {
                    double defense = livingEntity.getAttributeValue(InitAttribute.DEFENSE);
                    if (index == -1) defense += 1;
                    return Component.empty()
                        .append(Component.literal("("))
                        .append(Component.literal("15"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.attributeName(InitAttribute.DEFENSE))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(defense)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.5"))
                        .append(Component.literal(")%"));
                }
            );
            FormulaValue maxFormulaValue = new FormulaValue(
                livingEntity -> {
                    double defense = livingEntity.getAttributeValue(InitAttribute.DEFENSE);
                    if (index == -1) defense += 1;
                    return Component.literal(TooltipUtil.formatAttributeValue(defense * 0.5 + 5.0));
                },
                livingEntity -> {
                    double defense = livingEntity.getAttributeValue(InitAttribute.DEFENSE);
                    if (index == -1) defense += 1;
                    return Component.empty()
                        .append(Component.literal("5"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.attributeName(InitAttribute.DEFENSE))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(defense)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.5"));
                }
            );
            // simple 单段含 2 个 %s 在 line 0；detailed 含动态 %s 在 line 1
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(ratioFormulaValue, maxFormulaValue))
                                                      : Map.of(0, List.of(ratioFormulaValue, maxFormulaValue));
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 涛浪提灯 — 水浪持续随斯库拉器官数量缩放
     */
    public static final OrganTooltipConsumer TIDAL_LANTERN_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    int scyllaCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.SCYLLA, index);
                    return Component.literal(String.valueOf(60 + scyllaCount * 20));
                },
                livingEntity -> {
                    int scyllaCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.SCYLLA, index);
                    return Component.empty()
                        .append(Component.literal("60"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.SCYLLA))
                        .append(Component.literal(String.valueOf(scyllaCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("20"));
                }
            );
            // simple 单段含 1 个 %s 在 line 0；detailed 含动态 %s 在 line 1
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(formulaValue))
                                                      : Map.of(0, List.of(formulaValue));
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 教宗心脏 — 回血、效果等级、效果持续随教宗器官数量缩放
     */
    public static final OrganTooltipConsumer PONTIFF_HEART_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue healFormulaValue = new FormulaValue(
                livingEntity -> {
                    int pontiffCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.PONTIFF, index);
                    float healPercent = 0.3F + pontiffCount * 0.03F;
                    return Component.literal(String.format("%.0f%%", healPercent * 100));
                },
                livingEntity -> {
                    int pontiffCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.PONTIFF, index);
                    return Component.empty()
                        .append(Component.literal("("))
                        .append(Component.literal("30"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                        .append(Component.literal(String.valueOf(pontiffCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("3"))
                        .append(Component.literal(")%"));
                }
            );
            FormulaValue levelFormulaValue = new FormulaValue(
                livingEntity -> {
                    int pontiffCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.PONTIFF, index);
                    int amplifier = Math.min((pontiffCount - 1) / 2, 2);
                    return Component.literal(String.valueOf(amplifier + 1));
                },
                livingEntity -> {
                    int pontiffCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.PONTIFF, index);
                    return Component.empty()
                        .append(Component.literal("min(3,"))
                        .append(Component.literal("floor(("))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                        .append(Component.literal(String.valueOf(pontiffCount)))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(Component.literal("1)"))
                        .append(WAICTooltipUtil.formulaOperator("÷"))
                        .append(Component.literal("2))"));
                }
            );
            FormulaValue durationFormulaValue = new FormulaValue(
                livingEntity -> {
                    int pontiffCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.PONTIFF, index);
                    return Component.literal(String.valueOf(200 + pontiffCount * 20));
                },
                livingEntity -> {
                    int pontiffCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.PONTIFF, index);
                    return Component.empty()
                        .append(Component.literal("200"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                        .append(Component.literal(String.valueOf(pontiffCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("20"));
                }
            );
            // simple 单段含 3 个 %s 在 line 0：heal、level、duration；
            // detailed 行 0 含 heal 的 %s、行 1 含 level+duration 的 %s，行 2（冷却）为静态不注入
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(0, List.of(healFormulaValue), 1, List.of(levelFormulaValue, durationFormulaValue))
                                                      : Map.of(0, List.of(healFormulaValue, levelFormulaValue, durationFormulaValue));
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 九头蛇脊柱 — 复活回血随代谢缩放
     */
    public static final OrganTooltipConsumer HYDRA_SPINE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    float healPercent = 0.05F + (float) (livingEntity.getAttributeValue(InitAttribute.METABOLISM) * 0.005);
                    return Component.literal(TooltipUtil.formatAttributeValue(livingEntity.getMaxHealth() * healPercent));
                },
                livingEntity -> Component.empty()
                    .append(Component.translatable("formula.who_am_i_core.max_health"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(livingEntity.getMaxHealth())))
                    .append(WAICTooltipUtil.formulaOperator("×"))
                    .append(Component.literal("(0.05"))
                    .append(WAICTooltipUtil.formulaOperator("+"))
                    .append(WAICTooltipUtil.attributeName(InitAttribute.METABOLISM))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(livingEntity.getAttributeValue(InitAttribute.METABOLISM))))
                    .append(WAICTooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.005)"))
            );
            // 无 simple 描述，useSimple 永远为 false，detailed 与非 detailed 共用 detailed 行号布局
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, Map.of(1, List.of(formulaValue)));
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 九头蛇脾脏 — 治疗乘数随血量比例缩放
     */
    public static final OrganTooltipConsumer HYDRA_SPLEEN_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    float healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
                    float multiplier = (1.0F - healthRatio) * 10;
                    return Component.literal(TooltipUtil.formatAttributeValue(multiplier));
                },
                livingEntity -> {
                    float healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
                    return Component.empty()
                        .append(Component.literal("(1.0"))
                        .append(WAICTooltipUtil.formulaOperator("-"))
                        .append(Component.translatable("formula.who_am_i_core.current_health_ratio"))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(healthRatio)))
                        .append(Component.literal(")"))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("10"));
                }
            );
            // simple 单段含 1 个 %s 在 line 0；detailed 含动态 %s 在 line 1
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(formulaValue))
                                                      : Map.of(0, List.of(formulaValue));
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 悚怖脊柱 — 缓慢持续随冰霜器官数量缩放，缓慢等级随局部负温度绝对值缩放
     */
    public static final OrganTooltipConsumer DREAD_SPINE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            // %1$s：持续时间
            FormulaValue durationFormulaValue = new FormulaValue(
                livingEntity -> {
                    int iceCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.ICE, index);
                    return Component.literal(String.valueOf(40 + iceCount * 10));
                },
                livingEntity -> {
                    int iceCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.ICE, index);
                    return Component.empty()
                        .append(Component.literal("40"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.ICE))
                        .append(Component.literal(String.valueOf(iceCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("10"));
                }
            );
            // %2$s：缓慢等级（按 MC 显示惯例 = amplifier + 1）
            FormulaValue levelFormulaValue = new FormulaValue(
                livingEntity -> {
                    double localTemp = OrganUtil.getLocalTemperature(data, index, stack);
                    int amplifier = localTemp >= 0 ? 0 : (int) ((Math.abs(localTemp) - 1) / 2);
                    return Component.literal(String.valueOf(amplifier + 1));
                },
                livingEntity -> {
                    double localTemp = OrganUtil.getLocalTemperature(data, index, stack);
                    return Component.empty()
                        .append(Component.translatable("formula.who_am_i_core.local_temperature"))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(localTemp)))
                        .append(WAICTooltipUtil.formulaOperator("<"))
                        .append(Component.literal("0"))
                        .append(Component.literal("\u00A0?\u00A0"))
                        .append(Component.literal("floor((|"))
                        .append(Component.translatable("formula.who_am_i_core.local_temperature"))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(Math.abs(localTemp))))
                        .append(Component.literal("|"))
                        .append(WAICTooltipUtil.formulaOperator("-"))
                        .append(Component.literal("1)"))
                        .append(WAICTooltipUtil.formulaOperator("÷"))
                        .append(Component.literal("2)"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(Component.literal("1"))
                        .append(Component.literal("\u00A0:\u00A01"));
                }
            );
            // 无 simple 描述，useSimple 永远为 false，line 0 含两个 %s：%1$s=持续时间，%2$s=缓慢等级
            return WAICTooltipUtil.dynamicEffectLines(
                stack, "passive_effect", detailed, entity, ctrl,
                Map.of(0, List.of(durationFormulaValue, levelFormulaValue))
            );
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 尸王脊柱 — 伤害吸收上限随魔法器官数量缩放
     */
    public static final OrganTooltipConsumer DEAD_KING_SPINE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    int magicCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MAGIC, index);
                    float cap = Math.min(0.8F, 0.3F + magicCount * 0.03F);
                    return Component.literal(String.format("%.0f%%", cap * 100));
                },
                livingEntity -> {
                    int magicCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MAGIC, index);
                    return Component.empty()
                        .append(Component.literal("min(80,\u00A030"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MAGIC))
                        .append(Component.literal(String.valueOf(magicCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("3)%"));
                }
            );
            // simple 单段含 1 个 %s 在 line 0；detailed 含动态 %s 在 line 1
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(formulaValue))
                                                      : Map.of(0, List.of(formulaValue));
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 死亡透镜 — 基础伤害和生命%伤害随机械器官数量缩放
     */
    public static final OrganTooltipConsumer DEATH_LENS_TOOLTIP = OrganTooltip.builder()
        .activeSkill((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            // 基础伤害 = 4 × (1 + 机械器官数 × 0.1)
            FormulaValue damageFormulaValue = new FormulaValue(
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.literal(TooltipUtil.formatAttributeValue(4.0F * (1 + mechanicalCount * 0.1F)));
                },
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.empty()
                        .append(Component.literal("4"))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("(1"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                        .append(Component.literal(String.valueOf(mechanicalCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.1)"));
                }
            );
            // 生命百分比 = 3 × (1 + 机械器官数 × 0.1)
            FormulaValue healthPercentFormulaValue = new FormulaValue(
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.literal(String.format("%.1f", 3.0F * (1 + mechanicalCount * 0.1F)) + "%");
                },
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.empty()
                        .append(Component.literal("("))
                        .append(Component.literal("3"))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("(1"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                        .append(Component.literal(String.valueOf(mechanicalCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.1))%"));
                }
            );
            // simple 描述为单段（"%1$s 加 %2$s ..."），detailed 分两行
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(damageFormulaValue, healthPercentFormulaValue))
                                                      : Map.of(0, List.of(damageFormulaValue, healthPercentFormulaValue));
            List<Component> lines = WAICTooltipUtil.dynamicEffectLines(stack, "active_skill", detailed, entity, ctrl, values);
            if (detailed || !TooltipUtil.hasTranslation(TooltipUtil.getBaseKey(stack) + ".active_skill.simple.0")) {
                lines.addAll(TooltipUtil.cooldownLine(data, index, stack));
            }
            return lines;
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 机械之星 — 导弹伤害随机械器官数量缩放
     */
    public static final OrganTooltipConsumer MECHANICAL_STAR_TOOLTIP = OrganTooltip.builder()
        .activeSkill((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            // 伤害 = 5 × (1 + 机械器官数 × 0.1)
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.literal(TooltipUtil.formatAttributeValue(5.0F * (1 + mechanicalCount * 0.1F)));
                },
                livingEntity -> {
                    int mechanicalCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MECHANICAL, index);
                    return Component.empty()
                        .append(Component.literal("5"))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("(1"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                        .append(Component.literal(String.valueOf(mechanicalCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.1)"));
                }
            );
            // simple 和 detailed 都是单 %s，行号差异：detailed 在 line 1，simple 在 line 0
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(formulaValue))
                                                      : Map.of(0, List.of(formulaValue));
            List<Component> lines = WAICTooltipUtil.dynamicEffectLines(stack, "active_skill", detailed, entity, ctrl, values);
            if (detailed || !TooltipUtil.hasTranslation(TooltipUtil.getBaseKey(stack) + ".active_skill.simple.0")) {
                lines.addAll(TooltipUtil.cooldownLine(data, index, stack));
            }
            return lines;
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 封印石板 — 每道戟伤害随力量缩放
     */
    public static final OrganTooltipConsumer SEALING_STONE_SLAB_TOOLTIP = OrganTooltip.builder()
        .activeSkill((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            // 每道战戟伤害 = 8 + 力量属性 × 0.5（力量属性即使为 0 也完整显示）
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> Component.literal(TooltipUtil.formatAttributeValue(
                    8.0F + (float) (livingEntity.getAttributeValue(InitAttribute.STRENGTH) * 0.5))),
                livingEntity -> Component.empty()
                    .append(Component.literal("8"))
                    .append(WAICTooltipUtil.formulaOperator("+"))
                    .append(WAICTooltipUtil.attributeName(InitAttribute.STRENGTH))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(
                        livingEntity.getAttributeValue(InitAttribute.STRENGTH))))
                    .append(WAICTooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.5"))
            );
            // simple 和 detailed 都是单 %s，行号差异：detailed 在 line 1，simple 在 line 0
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(formulaValue))
                                                      : Map.of(0, List.of(formulaValue));
            List<Component> lines = WAICTooltipUtil.dynamicEffectLines(stack, "active_skill", detailed, entity, ctrl, values);
            if (detailed || !TooltipUtil.hasTranslation(TooltipUtil.getBaseKey(stack) + ".active_skill.simple.0")) {
                lines.addAll(TooltipUtil.cooldownLine(data, index, stack));
            }
            return lines;
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 虚空晶脊 — 每符文伤害随魔法器官数量缩放
     */
    public static final OrganTooltipConsumer VOID_CRYSTAL_SPINE_TOOLTIP = OrganTooltip.builder()
        .activeSkill((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            // 每符文伤害 = 10 × (1 + 魔法器官数 × 0.1)
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    int magicCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MAGIC, index);
                    return Component.literal(TooltipUtil.formatAttributeValue(10.0F * (1 + magicCount * 0.1F)));
                },
                livingEntity -> {
                    int magicCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MAGIC, index);
                    return Component.empty()
                        .append(Component.literal("10"))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("(1"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MAGIC))
                        .append(Component.literal(String.valueOf(magicCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.1)"));
                }
            );
            // simple 和 detailed 都是单 %s，行号差异：detailed 在 line 0，simple 在 line 0（两者都是第一行）
            Map<Integer, List<FormulaValue>> values = Map.of(0, List.of(formulaValue));
            List<Component> lines = WAICTooltipUtil.dynamicEffectLines(stack, "active_skill", detailed, entity, ctrl, values);
            if (detailed || !TooltipUtil.hasTranslation(TooltipUtil.getBaseKey(stack) + ".active_skill.simple.0")) {
                lines.addAll(TooltipUtil.cooldownLine(data, index, stack));
            }
            return lines;
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 巨兽回路 — 伤害随温度和自身最大生命值缩放
     */
    public static final OrganTooltipConsumer MONSTROSITY_CIRCUIT_TOOLTIP = OrganTooltip.builder()
        .activeSkill((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue damageFormulaValue = new FormulaValue(
                livingEntity -> {
                    double temperature = OrganUtil.getEffectiveTemperature(livingEntity);
                    if (index == -1) temperature += 2; // 巨兽回路自身温度属性 +2
                    return Component.literal(TooltipUtil.formatAttributeValue(
                        Math.max(0, 20 + (float) temperature * 0.01F * livingEntity.getMaxHealth())));
                },
                livingEntity -> {
                    double temperature = OrganUtil.getEffectiveTemperature(livingEntity);
                    if (index == -1) temperature += 2;
                    return Component.empty()
                        .append(Component.literal("20"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(Component.translatable("formula.who_am_i_core.effective_temperature"))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(temperature)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("1%"))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.translatable("formula.who_am_i_core.max_health"))
                        .append(Component.literal(TooltipUtil.formatAttributeValue(livingEntity.getMaxHealth())));
                }
            );
            // simple 单段含 1 个 %s 在 line 0；detailed 含动态 %s 在 line 1
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(damageFormulaValue))
                                                      : Map.of(0, List.of(damageFormulaValue));
            List<Component> lines = WAICTooltipUtil.dynamicEffectLines(stack, "active_skill", detailed, entity, ctrl, values);
            if (detailed || !TooltipUtil.hasTranslation(TooltipUtil.getBaseKey(stack) + ".active_skill.simple.0")) {
                lines.addAll(TooltipUtil.cooldownLine(data, index, stack));
            }
            return lines;
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();

    /**
     * 巨兽熔炉 — 骇人之恶时长随巨兽器官数量缩放（黄胆汁固定 100）
     */
    public static final OrganTooltipConsumer MONSTROSITY_FURNACE_TOOLTIP = OrganTooltip.builder()
        .passiveEffect((data, index, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
            boolean detailed = TooltipUtil.isDetailedMode(keyContext);
            boolean ctrl = keyContext.isKeyCtrlDown();
            LivingEntity entity = data != null ? data.getOwner() : null;
            FormulaValue formulaValue = new FormulaValue(
                livingEntity -> {
                    int monstrosityCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MONSTROSITY, index);
                    return Component.literal(String.valueOf(30 + monstrosityCount * 10));
                },
                livingEntity -> {
                    int monstrosityCount = OrganUtil.getOrganCountWithSelf(livingEntity, WAICItemTagManager.MONSTROSITY, index);
                    return Component.empty()
                        .append(Component.literal("30"))
                        .append(WAICTooltipUtil.formulaOperator("+"))
                        .append(WAICTooltipUtil.tagOrganCountName(WAICItemTagManager.MONSTROSITY))
                        .append(Component.literal(String.valueOf(monstrosityCount)))
                        .append(WAICTooltipUtil.formulaOperator("×"))
                        .append(Component.literal("10"));
                }
            );
            // simple 描述不含 %s；detailed line 1 含动态 %s（秒数）
            Map<Integer, List<FormulaValue>> values = detailed
                                                      ? Map.of(1, List.of(formulaValue))
                                                      : Map.of();
            return WAICTooltipUtil.dynamicEffectLines(stack, "passive_effect", detailed, entity, ctrl, values);
        })
        .afterShiftHint(CTRL_HINT_SECTION)
        .build();
}

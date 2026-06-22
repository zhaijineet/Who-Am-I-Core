package net.zhaiji.who_am_i_core.manager;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.component.CustomData;
import net.zhaiji.chestcavitybeyond.api.DynamicValues;
import net.zhaiji.chestcavitybeyond.api.FormulaValue;
import net.zhaiji.chestcavitybeyond.api.OrganTooltip;
import net.zhaiji.chestcavitybeyond.api.function.OrganTooltipConsumer;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.util.OrganUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * WAIC 器官工具提示集合
 * <p>
 * 动态数值器官统一通过 {@link OrganTooltip.Builder#dynamicPassiveEffect} / {@link OrganTooltip.Builder#dynamicActiveSkill}
 * 接入 CCB 的 Ctrl 公式提示管线，Ctrl 提示行由 CCB 自动挂载。
 * </p>
 */
public class WAICTooltipManager {
    /**
     * 完全覆盖器官工具提示，仅显示「仍未完成」信息
     */
    public static final OrganTooltipConsumer UNFINISHED_TOOLTIP = (slotContext, keyContext, context, tooltipComponents, tooltipFlag) -> {
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
        .passiveEffect((slotContext, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> result = new ArrayList<>();
            int count = ChestCavityUtil.getOrganCountWithSelf(slotContext, WAICItemTagManager.NINE_HELL);
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX).append(Component.translatable("organ.who_am_i_core.nine_hell.hint")));
            List<Component> lines = TooltipUtil.addSimpleOrDetailedLines(
                slotContext.stack(),
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
        .passiveEffect((slotContext, keyContext, context, tooltipComponents, tooltipFlag) -> {
            List<Component> result = new ArrayList<>();
            result.add(Component.literal(TooltipUtil.DEFAULT_PREFIX)
                .append(Component.translatable("organ.who_am_i_core.control_rod.hint")));
            boolean active = false;
            if (slotContext.index() >= 0) {
                for (int i = 0; i < slotContext.data().getSlots(); i++) {
                    if (slotContext.data().getStackInSlot(i).is(MowziesMobOrgans.CHEST_NOVA.get())) {
                        List<Integer> adjacent = ChestCavityUtil.getAdjacentSlots(i, slotContext.data().getSlots());
                        if (adjacent.contains(slotContext.index())) {
                            active = true;
                            break;
                        }
                    }
                }
            }
            List<Component> lines = TooltipUtil.addSimpleOrDetailedLines(
                slotContext.stack(),
                "passive_effect",
                TooltipUtil.isDetailedMode(keyContext),
                TooltipUtil.DEFAULT_PREFIX
            );
            if (!active) {
                for (int i = 0; i < lines.size(); i++) {
                    lines.set(i, lines.get(i).copy().withStyle(ChatFormatting.DARK_GRAY));
                }
            }
            result.addAll(lines);
            return result;
        })
        .build();

    /**
     * 墨水瓶
     * <p>
     * 根据是否为胸腔中激活的（第一个）墨水瓶决定墨水量行的显示。
     * </p>
     */
    public static final OrganTooltipConsumer INK_BOTTLE_TOOLTIP = OrganTooltip.builder()
        .afterPassiveEffect((slotContext, keyContext, context, tooltipComponents, tooltipFlag) -> {
            CompoundTag tag = slotContext.stack().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
            float value = tag.contains("ink") ? tag.getFloat("ink") : 0;
            boolean active = slotContext.data() != null && WAICOrganUtil.isInkBottleActive(slotContext.data(), slotContext.index(), slotContext.stack());
            String capacityDisplay = active ? String.valueOf(WAICOrganUtil.getInkCapacity(slotContext.data())) : "?";
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
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            1, List.of(new FormulaValue(
                context -> Component.literal("1:" + TooltipUtil.formatAttributeValue(
                    3.0F + (float) (context.entity().getAttributeValue(InitAttribute.METABOLISM) * 0.2F))),
                context -> Component.empty()
                    .append(Component.literal("3"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.attributeName(InitAttribute.METABOLISM))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(context.entity().getAttributeValue(InitAttribute.METABOLISM))))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.2"))
            ))
        )))
        .build();

    /**
     * 墨水肌肉 — 伤害转墨水随墨水器官数量缩放
     */
    public static final OrganTooltipConsumer INK_MUSCLE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            0, List.of(new FormulaValue(
                context -> Component.literal("1:" + TooltipUtil.formatAttributeValue(
                    5.0F + ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.INK) * 0.5F)),
                context -> {
                    int inkCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.INK);
                    return Component.empty()
                        .append(Component.literal("5"))
                        .append(TooltipUtil.formulaOperator("+"))
                        .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.INK))
                        .append(Component.literal(String.valueOf(inkCount)))
                        .append(TooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.5"));
                }
            ))
        )))
        .build();

    /**
     * 咒魂心脏 — 冲刺增伤随力量缩放
     */
    public static final OrganTooltipConsumer PHANTOM_HEART_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            0, List.of(new FormulaValue(
                context -> {
                    float bonus = (float) (context.entity().getAttributeValue(InitAttribute.STRENGTH) * 0.005 + 0.15) * 100;
                    return Component.literal(String.format("%.1f", bonus) + "%");
                },
                context -> Component.empty()
                    .append(Component.literal("("))
                    .append(Component.literal("15"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.attributeName(InitAttribute.STRENGTH))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(context.entity().getAttributeValue(InitAttribute.STRENGTH))))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.5"))
                    .append(Component.literal(")%"))
            ))
        )))
        .build();

    /**
     * 蓄能电芯 — 每秒回血随机械器官数量缩放
     */
    public static final OrganTooltipConsumer POWER_CELL_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            0, List.of(new FormulaValue(
                context -> Component.literal(TooltipUtil.formatAttributeValue(
                    0.5F + ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL) * 0.05F)),
                context -> {
                    int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                    return Component.empty()
                        .append(Component.literal("0.5"))
                        .append(TooltipUtil.formulaOperator("+"))
                        .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                        .append(Component.literal(String.valueOf(mechanicalCount)))
                        .append(TooltipUtil.formulaOperator("×"))
                        .append(Component.literal("0.05"));
                }
            ))
        )))
        .build();

    /**
     * 炽面甲 — 命中回血随温度缩放
     */
    public static final OrganTooltipConsumer BLAZING_VISAGE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(buildBlazingVisageFormulaValue())),
            // detailed 含动态 %s 在 line 2
            Map.of(2, List.of(buildBlazingVisageFormulaValue()))
        ))
        .build();

    /**
     * 构建炽面甲 FormulaValue（局部温度：index>=0 遍历九宫格，index==-1 只取自身）
     */
    private static FormulaValue buildBlazingVisageFormulaValue() {
        return new FormulaValue(
            context -> {
                double temperature = OrganUtil.getLocalTemperature(context);
                return Component.literal(TooltipUtil.formatAttributeValue(
                    Math.max(0, 1.0F + (float) Math.floor(temperature * 0.5))));
            },
            context -> {
                double temperature = OrganUtil.getLocalTemperature(context);
                return Component.empty()
                    .append(Component.literal("1"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(Component.literal("floor("))
                    .append(Component.translatable("formula.who_am_i_core.local_temperature"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(temperature)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.5)"));
            }
        );
    }

    /**
     * 风暴脊柱 — 吸收比例和上限随防御缩放
     */
    public static final OrganTooltipConsumer STORM_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 2 个 %s 在 line 0
            Map.of(0, List.of(buildStormSpineRatioFormulaValue(), buildStormSpineMaxFormulaValue())),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(buildStormSpineRatioFormulaValue(), buildStormSpineMaxFormulaValue()))
        ))
        .build();

    /**
     * 构建风暴脊柱吸收比例 FormulaValue（自身防御属性 +1 需在 index==-1 时补加）
     */
    private static FormulaValue buildStormSpineRatioFormulaValue() {
        return new FormulaValue(
            context -> {
                double defense = context.entity().getAttributeValue(InitAttribute.DEFENSE);
                if (context.index() == -1) defense += 1;
                float ratio = (float) (defense * 0.005 + 0.15);
                return Component.literal(String.format("%.1f", ratio * 100) + "%");
            },
            context -> {
                double defense = context.entity().getAttributeValue(InitAttribute.DEFENSE);
                if (context.index() == -1) defense += 1;
                return Component.empty()
                    .append(Component.literal("("))
                    .append(Component.literal("15"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.attributeName(InitAttribute.DEFENSE))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(defense)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.5"))
                    .append(Component.literal(")%"));
            }
        );
    }

    /**
     * 构建风暴脊柱吸收上限 FormulaValue（自身防御属性 +1 需在 index==-1 时补加）
     */
    private static FormulaValue buildStormSpineMaxFormulaValue() {
        return new FormulaValue(
            context -> {
                double defense = context.entity().getAttributeValue(InitAttribute.DEFENSE);
                if (context.index() == -1) defense += 1;
                return Component.literal(TooltipUtil.formatAttributeValue(defense * 0.5 + 5.0));
            },
            context -> {
                double defense = context.entity().getAttributeValue(InitAttribute.DEFENSE);
                if (context.index() == -1) defense += 1;
                return Component.empty()
                    .append(Component.literal("5"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.attributeName(InitAttribute.DEFENSE))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(defense)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.5"));
            }
        );
    }

    /**
     * 涛浪提灯 — 水浪持续随斯库拉器官数量缩放
     */
    public static final OrganTooltipConsumer TIDAL_LANTERN_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(buildTidalLanternFormulaValue())),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(buildTidalLanternFormulaValue()))
        ))
        .build();

    /**
     * 构建涛浪提灯 FormulaValue
     */
    private static FormulaValue buildTidalLanternFormulaValue() {
        return new FormulaValue(
            context -> {
                int scyllaCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.SCYLLA);
                return Component.literal(String.valueOf(60 + scyllaCount * 20));
            },
            context -> {
                int scyllaCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.SCYLLA);
                return Component.empty()
                    .append(Component.literal("60"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.SCYLLA))
                    .append(Component.literal(String.valueOf(scyllaCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("20"));
            }
        );
    }

    /**
     * 教宗心脏 — 回血、效果等级、效果持续随教宗器官数量缩放
     */
    public static final OrganTooltipConsumer PONTIFF_HEART_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 3 个 %s 在 line 0：heal、level、duration
            Map.of(0, List.of(
                buildPontiffHealFormulaValue(),
                buildPontiffLevelFormulaValue(),
                buildPontiffDurationFormulaValue()
            )),
            // detailed 行 0 含 heal，行 1 含 level+duration，行 2（冷却）为静态不注入
            Map.of(
                0, List.of(buildPontiffHealFormulaValue()),
                1, List.of(buildPontiffLevelFormulaValue(), buildPontiffDurationFormulaValue())
            )
        ))
        .build();

    /**
     * 构建教宗心脏 回血 FormulaValue
     */
    private static FormulaValue buildPontiffHealFormulaValue() {
        return new FormulaValue(
            context -> {
                int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
                float healPercent = 0.3F + pontiffCount * 0.03F;
                return Component.literal(String.format("%.0f%%", healPercent * 100));
            },
            context -> {
                int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
                return Component.empty()
                    .append(Component.literal("("))
                    .append(Component.literal("30"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                    .append(Component.literal(String.valueOf(pontiffCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("3"))
                    .append(Component.literal(")%"));
            }
        );
    }

    /**
     * 构建教宗心脏 效果等级 FormulaValue
     */
    private static FormulaValue buildPontiffLevelFormulaValue() {
        return new FormulaValue(
            context -> {
                int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
                int amplifier = Math.min((pontiffCount - 1) / 2, 2);
                return Component.literal(String.valueOf(amplifier + 1));
            },
            context -> {
                int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
                return Component.empty()
                    .append(Component.literal("min(3,"))
                    .append(Component.literal("floor(("))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                    .append(Component.literal(String.valueOf(pontiffCount)))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(Component.literal("1)"))
                    .append(TooltipUtil.formulaOperator("÷"))
                    .append(Component.literal("2))"));
            }
        );
    }

    /**
     * 构建教宗心脏 效果持续 FormulaValue
     */
    private static FormulaValue buildPontiffDurationFormulaValue() {
        return new FormulaValue(
            context -> {
                int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
                return Component.literal(String.valueOf(200 + pontiffCount * 20));
            },
            context -> {
                int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
                return Component.empty()
                    .append(Component.literal("200"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                    .append(Component.literal(String.valueOf(pontiffCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("20"));
            }
        );
    }

    /**
     * 九头蛇脊柱 — 复活回血随代谢缩放
     */
    public static final OrganTooltipConsumer HYDRA_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            1, List.of(new FormulaValue(
                context -> {
                    float healPercent = 0.05F + (float) (context.entity().getAttributeValue(InitAttribute.METABOLISM) * 0.005);
                    return Component.literal(TooltipUtil.formatAttributeValue(context.entity().getMaxHealth() * healPercent));
                },
                context -> Component.empty()
                    .append(Component.translatable("formula.who_am_i_core.max_health"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(context.entity().getMaxHealth())))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("(0.05"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.attributeName(InitAttribute.METABOLISM))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(context.entity().getAttributeValue(InitAttribute.METABOLISM))))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.005)"))
            ))
        )))
        .build();

    /**
     * 九头蛇脾脏 — 治疗乘数随血量比例缩放
     */
    public static final OrganTooltipConsumer HYDRA_SPLEEN_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(buildHydraSpleenFormulaValue())),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(buildHydraSpleenFormulaValue()))
        ))
        .build();

    /**
     * 构建九头蛇脾脏 FormulaValue
     */
    private static FormulaValue buildHydraSpleenFormulaValue() {
        return new FormulaValue(
            context -> {
                float healthRatio = context.entity().getHealth() / context.entity().getMaxHealth();
                float multiplier = (1.0F - healthRatio) * 10;
                return Component.literal(TooltipUtil.formatAttributeValue(multiplier));
            },
            context -> {
                float healthRatio = context.entity().getHealth() / context.entity().getMaxHealth();
                return Component.empty()
                    .append(Component.literal("(1.0"))
                    .append(TooltipUtil.formulaOperator("-"))
                    .append(Component.translatable("formula.who_am_i_core.current_health_ratio"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(healthRatio)))
                    .append(Component.literal(")"))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("10"));
            }
        );
    }

    /**
     * 悚怖脊柱 — 缓慢持续随冰霜器官数量缩放，缓慢等级随局部负温度绝对值缩放
     */
    public static final OrganTooltipConsumer DREAD_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            // line 0 含两个 %s：%1$s=持续时间，%2$s=缓慢等级
            0, List.of(buildDreadSpineDurationFormulaValue(), buildDreadSpineLevelFormulaValue())
        )))
        .build();

    /**
     * 构建 悚怖脊柱 持续时间 FormulaValue
     */
    private static FormulaValue buildDreadSpineDurationFormulaValue() {
        return new FormulaValue(
            context -> {
                int iceCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.ICE);
                return Component.literal(String.valueOf(40 + iceCount * 10));
            },
            context -> {
                int iceCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.ICE);
                return Component.empty()
                    .append(Component.literal("40"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.ICE))
                    .append(Component.literal(String.valueOf(iceCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("10"));
            }
        );
    }

    /**
     * 构建 悚怖脊柱 缓慢等级 FormulaValue（按 MC 显示惯例 = amplifier + 1）
     */
    private static FormulaValue buildDreadSpineLevelFormulaValue() {
        return new FormulaValue(
            context -> {
                double localTemp = OrganUtil.getLocalTemperature(context);
                int amplifier = localTemp >= 0 ? 0 : (int) ((Math.abs(localTemp) - 1) / 2);
                return Component.literal(String.valueOf(amplifier + 1));
            },
            context -> {
                double localTemp = OrganUtil.getLocalTemperature(context);
                return Component.empty()
                    .append(Component.translatable("formula.who_am_i_core.local_temperature"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(localTemp)))
                    .append(TooltipUtil.formulaOperator("<"))
                    .append(Component.literal("0"))
                    .append(Component.literal("\u00A0?\u00A0"))
                    .append(Component.literal("floor((|"))
                    .append(Component.translatable("formula.who_am_i_core.local_temperature"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(Math.abs(localTemp))))
                    .append(Component.literal("|"))
                    .append(TooltipUtil.formulaOperator("-"))
                    .append(Component.literal("1)"))
                    .append(TooltipUtil.formulaOperator("÷"))
                    .append(Component.literal("2)"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(Component.literal("1"))
                    .append(Component.literal("\u00A0:\u00A01"));
            }
        );
    }

    /**
     * 尸王脊柱 — 伤害吸收上限随魔法器官数量缩放
     */
    public static final OrganTooltipConsumer DEAD_KING_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(buildDeadKingSpineFormulaValue())),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(buildDeadKingSpineFormulaValue()))
        ))
        .build();

    /**
     * 构建尸王脊柱 FormulaValue
     */
    private static FormulaValue buildDeadKingSpineFormulaValue() {
        return new FormulaValue(
            context -> {
                int magicCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MAGIC);
                float cap = Math.min(0.8F, 0.3F + magicCount * 0.03F);
                return Component.literal(String.format("%.0f%%", cap * 100));
            },
            context -> {
                int magicCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MAGIC);
                return Component.empty()
                    .append(Component.literal("min(80,\u00A030"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MAGIC))
                    .append(Component.literal(String.valueOf(magicCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("3)%"));
            }
        );
    }

    /**
     * 死亡透镜 — 基础伤害和生命%伤害随机械器官数量缩放
     */
    public static final OrganTooltipConsumer DEATH_LENS_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 描述为单段（"%1$s 加 %2$s ..."）
            Map.of(0, List.of(buildDeathLensDamageFormulaValue(), buildDeathLensHealthPercentFormulaValue())),
            // detailed 分两行
            Map.of(1, List.of(buildDeathLensDamageFormulaValue(), buildDeathLensHealthPercentFormulaValue()))
        ))
        .build();

    /**
     * 构建 死亡透镜 基础伤害 FormulaValue，公式为 4 × (1 + 机械器官数 × 0.1)
     */
    private static FormulaValue buildDeathLensDamageFormulaValue() {
        return new FormulaValue(
            context -> {
                int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                return Component.literal(TooltipUtil.formatAttributeValue(4.0F * (1 + mechanicalCount * 0.1F)));
            },
            context -> {
                int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                return Component.empty()
                    .append(Component.literal("4"))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("(1"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                    .append(Component.literal(String.valueOf(mechanicalCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.1)"));
            }
        );
    }

    /**
     * 构建 死亡透镜 生命百分比 FormulaValue，公式为 3 × (1 + 机械器官数 × 0.1)
     */
    private static FormulaValue buildDeathLensHealthPercentFormulaValue() {
        return new FormulaValue(
            context -> {
                int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                return Component.literal(String.format("%.1f", 3.0F * (1 + mechanicalCount * 0.1F)) + "%");
            },
            context -> {
                int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                return Component.empty()
                    .append(Component.literal("("))
                    .append(Component.literal("3"))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("(1"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                    .append(Component.literal(String.valueOf(mechanicalCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.1))%"));
            }
        );
    }

    /**
     * 机械之星 — 导弹数量随机械器官数量动态变化（单发伤害固定 8 点）
     */
    public static final OrganTooltipConsumer MECHANICAL_STAR_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple：line 0 含 %s
            Map.of(0, List.of(buildMechanicalStarCountFormulaValue())),
            // detailed：line 0/1/2 共三行，%s 在 line 1
            Map.of(1, List.of(buildMechanicalStarCountFormulaValue()))
        ))
        .build();

    /**
     * 构建 机械之星导弹数量 FormulaValue，公式为 1 + floor(机械器官数 / 3)
     */
    private static FormulaValue buildMechanicalStarCountFormulaValue() {
        return new FormulaValue(
            context -> {
                int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                return Component.literal(TooltipUtil.formatAttributeValue(1 + mechanicalCount / 3));
            },
            context -> {
                int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
                return Component.empty()
                    .append(Component.literal("1"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(Component.literal("("))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MECHANICAL))
                    .append(Component.literal(String.valueOf(mechanicalCount)))
                    .append(TooltipUtil.formulaOperator("÷"))
                    .append(Component.literal("3)"));
            }
        );
    }

    /**
     * 封印石板 — 每道戟伤害随力量缩放
     */
    public static final OrganTooltipConsumer SEALING_STONE_SLAB_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 和 detailed 都是单 %s，行号差异：detailed 在 line 1，simple 在 line 0
            Map.of(0, List.of(buildSealingStoneSlabFormulaValue())),
            Map.of(1, List.of(buildSealingStoneSlabFormulaValue()))
        ))
        .build();

    /**
     * 构建 封印石板 FormulaValue，公式为 8 + 力量属性 × 0.5
     */
    private static FormulaValue buildSealingStoneSlabFormulaValue() {
        return new FormulaValue(
            context -> Component.literal(TooltipUtil.formatAttributeValue(
                8.0F + (float) (context.entity().getAttributeValue(InitAttribute.STRENGTH) * 0.5))),
            context -> Component.empty()
                .append(Component.literal("8"))
                .append(TooltipUtil.formulaOperator("+"))
                .append(TooltipUtil.attributeName(InitAttribute.STRENGTH))
                .append(Component.literal(TooltipUtil.formatAttributeValue(
                    context.entity().getAttributeValue(InitAttribute.STRENGTH))))
                .append(TooltipUtil.formulaOperator("×"))
                .append(Component.literal("0.5"))
        );
    }

    /**
     * 虚空晶脊 — 每符文伤害随魔法器官数量缩放
     */
    public static final OrganTooltipConsumer VOID_CRYSTAL_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.same(Map.of(
            // simple 和 detailed 都是单 %s，都在 line 0
            0, List.of(buildVoidCrystalSpineFormulaValue())
        )))
        .build();

    /**
     * 构建 虚空晶脊 FormulaValue，公式为 10 × (1 + 魔法器官数 × 0.1)
     */
    private static FormulaValue buildVoidCrystalSpineFormulaValue() {
        return new FormulaValue(
            context -> {
                int magicCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MAGIC);
                return Component.literal(TooltipUtil.formatAttributeValue(10.0F * (1 + magicCount * 0.1F)));
            },
            context -> {
                int magicCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MAGIC);
                return Component.empty()
                    .append(Component.literal("10"))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("(1"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MAGIC))
                    .append(Component.literal(String.valueOf(magicCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.1)"));
            }
        );
    }

    /**
     * 巨兽回路 — 伤害随温度和自身最大生命值缩放
     */
    public static final OrganTooltipConsumer MONSTROSITY_CIRCUIT_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(buildMonstrosityCircuitFormulaValue())),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(buildMonstrosityCircuitFormulaValue()))
        ))
        .build();

    /**
     * 构建 巨兽回路 FormulaValue，自身温度属性 +2 需在 index==-1 时补加
     */
    private static FormulaValue buildMonstrosityCircuitFormulaValue() {
        return new FormulaValue(
            context -> {
                double temperature = OrganUtil.getEffectiveTemperature(context.entity());
                if (context.index() == -1) temperature += 2;
                return Component.literal(TooltipUtil.formatAttributeValue(
                    Math.max(0, 20 + (float) temperature * 0.01F * context.entity().getMaxHealth())));
            },
            context -> {
                double temperature = OrganUtil.getEffectiveTemperature(context.entity());
                if (context.index() == -1) temperature += 2;
                return Component.empty()
                    .append(Component.literal("20"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(Component.translatable("formula.who_am_i_core.effective_temperature"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(temperature)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("1%"))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.translatable("formula.who_am_i_core.max_health"))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(context.entity().getMaxHealth())));
            }
        );
    }

    /**
     * 巨兽熔炉 — 骇人之恶时长随巨兽器官数量缩放（黄胆汁固定 100）
     */
    public static final OrganTooltipConsumer MONSTROSITY_FURNACE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 描述不含 %s
            Map.of(),
            // detailed line 1 含动态 %s（秒数）
            Map.of(1, List.of(buildMonstrosityFurnaceFormulaValue()))
        ))
        .build();

    /**
     * 构建 巨兽熔炉 FormulaValue
     */
    private static FormulaValue buildMonstrosityFurnaceFormulaValue() {
        return new FormulaValue(
            context -> {
                int monstrosityCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MONSTROSITY);
                return Component.literal(String.valueOf(30 + monstrosityCount * 10));
            },
            context -> {
                int monstrosityCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MONSTROSITY);
                return Component.empty()
                    .append(Component.literal("30"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MONSTROSITY))
                    .append(Component.literal(String.valueOf(monstrosityCount)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("10"));
            }
        );
    }
}

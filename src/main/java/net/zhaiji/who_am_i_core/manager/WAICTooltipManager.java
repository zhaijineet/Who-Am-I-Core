package net.zhaiji.who_am_i_core.manager;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
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
import net.zhaiji.who_am_i_core.task.DragonBreathCastingTask;
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
     * 猩红心脏血液转化率公式：3 + 代谢 × 0.2
     */
    private static final FormulaValue CRIMSON_HEART_FORMULA_VALUE = new FormulaValue(
        context -> Component.literal("1:" + TooltipUtil.formatAttributeValue(
            3.0F + (float) (context.entity().getAttributeValue(InitAttribute.METABOLISM) * 0.2F))),
        context -> Component.empty()
            .append(Component.literal("3"))
            .append(TooltipUtil.formulaOperator("+"))
            .append(TooltipUtil.attributeName(InitAttribute.METABOLISM))
            .append(Component.literal(TooltipUtil.formatAttributeValue(context.entity().getAttributeValue(InitAttribute.METABOLISM))))
            .append(TooltipUtil.formulaOperator("×"))
            .append(Component.literal("0.2"))
    );

    /**
     * 猩红心脏 — 血液转化率随代谢缩放
     */
    public static final OrganTooltipConsumer CRIMSON_HEART_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            1, List.of(CRIMSON_HEART_FORMULA_VALUE)
        )))
        .build();

    /**
     * 墨水肌肉伤害转墨水公式：5 + 墨水器官数 × 0.5
     */
    private static final FormulaValue INK_MUSCLE_FORMULA_VALUE = new FormulaValue(
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
    );

    /**
     * 墨水肌肉 — 伤害转墨水随墨水器官数量缩放
     */
    public static final OrganTooltipConsumer INK_MUSCLE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            0, List.of(INK_MUSCLE_FORMULA_VALUE)
        )))
        .build();

    /**
     * 咒魂心脏冲刺增伤公式：(15 + 力量 × 0.5)%
     */
    private static final FormulaValue PHANTOM_HEART_FORMULA_VALUE = new FormulaValue(
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
    );

    /**
     * 咒魂心脏 — 冲刺增伤随力量缩放
     */
    public static final OrganTooltipConsumer PHANTOM_HEART_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            0, List.of(PHANTOM_HEART_FORMULA_VALUE)
        )))
        .build();

    /**
     * 蓄能电芯每秒回血公式：0.5 + 机械器官数 × 0.05
     */
    private static final FormulaValue POWER_CELL_FORMULA_VALUE = new FormulaValue(
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
    );

    /**
     * 蓄能电芯 — 每秒回血随机械器官数量缩放
     */
    public static final OrganTooltipConsumer POWER_CELL_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            0, List.of(POWER_CELL_FORMULA_VALUE)
        )))
        .build();

    /**
     * 炽面甲命中回血公式：1 + floor(局部温度 × 0.5)（局部温度：index>=0 遍历九宫格，index==-1 只取自身）
     */
    private static final FormulaValue BLAZING_VISAGE_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 炽面甲 — 命中回血随温度缩放
     */
    public static final OrganTooltipConsumer BLAZING_VISAGE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(BLAZING_VISAGE_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 2
            Map.of(2, List.of(BLAZING_VISAGE_FORMULA_VALUE))
        ))
        .build();

    /**
     * 风暴脊柱吸收比例公式：(15 + 防御 × 0.5)%（自身防御属性 +1 需在 index==-1 时补加）
     */
    private static final FormulaValue STORM_SPINE_RATIO_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 风暴脊柱吸收上限公式：5 + 防御 × 0.5（自身防御属性 +1 需在 index==-1 时补加）
     */
    private static final FormulaValue STORM_SPINE_MAX_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 风暴脊柱 — 吸收比例和上限随防御缩放
     */
    public static final OrganTooltipConsumer STORM_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 2 个 %s 在 line 0
            Map.of(0, List.of(STORM_SPINE_RATIO_FORMULA_VALUE, STORM_SPINE_MAX_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(STORM_SPINE_RATIO_FORMULA_VALUE, STORM_SPINE_MAX_FORMULA_VALUE))
        ))
        .build();

    /**
     * 涛浪提灯水浪持续公式：60 + 斯库拉器官数 × 20
     */
    private static final FormulaValue TIDAL_LANTERN_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 涛浪提灯 — 水浪持续随斯库拉器官数量缩放
     */
    public static final OrganTooltipConsumer TIDAL_LANTERN_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(TIDAL_LANTERN_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(TIDAL_LANTERN_FORMULA_VALUE))
        ))
        .build();

    /**
     * 教宗心脏回血公式：(30 + 教宗器官数 × 3)%
     */
    private static final FormulaValue PONTIFF_HEAL_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 教宗心脏效果等级公式：min(3, floor((教宗器官数 + 1) ÷ 2))
     */
    private static final FormulaValue PONTIFF_LEVEL_FORMULA_VALUE = new FormulaValue(
        context -> {
            int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
            int amplifier = Math.min((pontiffCount - 1) / 2, 2);
            return Component.literal(String.valueOf(amplifier + 1));
        },
        context -> {
            int pontiffCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.PONTIFF);
            return Component.empty()
                .append(Component.literal("min(3,\u00A0"))
                .append(Component.literal("floor(("))
                .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.PONTIFF))
                .append(Component.literal(String.valueOf(pontiffCount)))
                .append(TooltipUtil.formulaOperator("+"))
                .append(Component.literal("1)"))
                .append(TooltipUtil.formulaOperator("÷"))
                .append(Component.literal("2))"));
        }
    );

    /**
     * 教宗心脏效果持续公式：200 + 教宗器官数 × 20
     */
    private static final FormulaValue PONTIFF_DURATION_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 教宗心脏 — 回血、效果等级、效果持续随教宗器官数量缩放
     */
    public static final OrganTooltipConsumer PONTIFF_HEART_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 3 个 %s 在 line 0：heal、level、duration
            Map.of(0, List.of(
                PONTIFF_HEAL_FORMULA_VALUE,
                PONTIFF_LEVEL_FORMULA_VALUE,
                PONTIFF_DURATION_FORMULA_VALUE
            )),
            // detailed 行 0 含 heal，行 1 含 level+duration，行 2（冷却）为静态不注入
            Map.of(
                0, List.of(PONTIFF_HEAL_FORMULA_VALUE),
                1, List.of(PONTIFF_LEVEL_FORMULA_VALUE, PONTIFF_DURATION_FORMULA_VALUE)
            )
        ))
        .build();

    /**
     * 九头蛇脊柱复活回血公式：最大生命 × (0.05 + 代谢 × 0.005)
     */
    private static final FormulaValue HYDRA_SPINE_FORMULA_VALUE = new FormulaValue(
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
    );

    /**
     * 九头蛇脊柱 — 复活回血随代谢缩放
     */
    public static final OrganTooltipConsumer HYDRA_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            1, List.of(HYDRA_SPINE_FORMULA_VALUE)
        )))
        .build();

    /**
     * 九头蛇脾脏治疗乘数公式：(1.0 - 当前血量比例) × 10
     */
    private static final FormulaValue HYDRA_SPLEEN_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 九头蛇脾脏 — 治疗乘数随血量比例缩放
     */
    public static final OrganTooltipConsumer HYDRA_SPLEEN_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(HYDRA_SPLEEN_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(HYDRA_SPLEEN_FORMULA_VALUE))
        ))
        .build();

    /**
     * 悚怖脊柱缓慢持续公式：40 + 冰霜器官数 × 10
     */
    private static final FormulaValue DREAD_SPINE_DURATION_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 悚怖脊柱缓慢等级公式：局部温度 < 0 时 floor((|局部温度| - 1) ÷ 2) + 1，否则 1（按 MC 显示惯例 = amplifier + 1）
     */
    private static final FormulaValue DREAD_SPINE_LEVEL_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 悚怖脊柱 — 缓慢持续随冰霜器官数量缩放，缓慢等级随局部负温度绝对值缩放
     */
    public static final OrganTooltipConsumer DREAD_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.same(Map.of(
            // line 0 含两个 %s：%1$s=持续时间，%2$s=缓慢等级
            0, List.of(DREAD_SPINE_DURATION_FORMULA_VALUE, DREAD_SPINE_LEVEL_FORMULA_VALUE)
        )))
        .build();

    /**
     * 尸王脊柱伤害吸收上限公式：min(80, 30 + 魔法器官数 × 3)%
     */
    private static final FormulaValue DEAD_KING_SPINE_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 尸王脊柱 — 伤害吸收上限随魔法器官数量缩放
     */
    public static final OrganTooltipConsumer DEAD_KING_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(DEAD_KING_SPINE_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(DEAD_KING_SPINE_FORMULA_VALUE))
        ))
        .build();

    /**
     * 死亡透镜基础伤害公式：4 × (1 + 机械器官数 × 0.1)
     */
    private static final FormulaValue DEATH_LENS_DAMAGE_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 死亡透镜生命百分比公式：(3 × (1 + 机械器官数 × 0.1))%
     */
    private static final FormulaValue DEATH_LENS_HEALTH_PERCENT_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 死亡透镜 — 基础伤害和生命%伤害随机械器官数量缩放
     */
    public static final OrganTooltipConsumer DEATH_LENS_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 描述为单段（"%1$s 加 %2$s ..."）
            Map.of(0, List.of(DEATH_LENS_DAMAGE_FORMULA_VALUE, DEATH_LENS_HEALTH_PERCENT_FORMULA_VALUE)),
            // detailed 分两行
            Map.of(1, List.of(DEATH_LENS_DAMAGE_FORMULA_VALUE, DEATH_LENS_HEALTH_PERCENT_FORMULA_VALUE))
        ))
        .build();

    /**
     * 机械之星导弹数量公式：1 + floor(机械器官数 ÷ 3)
     */
    private static final FormulaValue MECHANICAL_STAR_COUNT_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 机械之星 — 导弹数量随机械器官数量动态变化（单发伤害固定 8 点）
     */
    public static final OrganTooltipConsumer MECHANICAL_STAR_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple：line 0 含 %s
            Map.of(0, List.of(MECHANICAL_STAR_COUNT_FORMULA_VALUE)),
            // detailed：line 0/1/2 共三行，%s 在 line 1
            Map.of(1, List.of(MECHANICAL_STAR_COUNT_FORMULA_VALUE))
        ))
        .build();

    /**
     * 封印石板每道戟伤害公式：8 + 力量 × 0.5
     */
    private static final FormulaValue SEALING_STONE_SLAB_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 封印石板 — 每道戟伤害随力量缩放
     */
    public static final OrganTooltipConsumer SEALING_STONE_SLAB_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 和 detailed 都是单 %s，行号差异：detailed 在 line 1，simple 在 line 0
            Map.of(0, List.of(SEALING_STONE_SLAB_FORMULA_VALUE)),
            Map.of(1, List.of(SEALING_STONE_SLAB_FORMULA_VALUE))
        ))
        .build();

    /**
     * 虚空晶脊每符文伤害公式：10 × (1 + 魔法器官数 × 0.1)
     */
    private static final FormulaValue VOID_CRYSTAL_SPINE_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 虚空晶脊 — 每符文伤害随魔法器官数量缩放
     */
    public static final OrganTooltipConsumer VOID_CRYSTAL_SPINE_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.same(Map.of(
            // simple 和 detailed 都是单 %s，都在 line 0
            0, List.of(VOID_CRYSTAL_SPINE_FORMULA_VALUE)
        )))
        .build();

    /**
     * 巨兽回路伤害公式：20 + 有效温度 × 1% × 最大生命（自身温度属性 +2 需在 index==-1 时补加）
     */
    private static final FormulaValue MONSTROSITY_CIRCUIT_FORMULA_VALUE = new FormulaValue(
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

    /**
     * 巨兽回路 — 伤害随温度和自身最大生命值缩放
     */
    public static final OrganTooltipConsumer MONSTROSITY_CIRCUIT_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0
            Map.of(0, List.of(MONSTROSITY_CIRCUIT_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(MONSTROSITY_CIRCUIT_FORMULA_VALUE))
        ))
        .build();

    /**
     * 巨兽熔炉骇人之恶等级公式：巨兽器官数（amplifier = count - 1，等级 = amplifier + 1 = count）
     */
    private static final FormulaValue MONSTROSITY_FURNACE_LEVEL_FORMULA_VALUE = new FormulaValue(
        context -> {
            int monstrosityCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MONSTROSITY);
            return Component.literal(String.valueOf(monstrosityCount));
        },
        context -> {
            int monstrosityCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MONSTROSITY);
            return Component.empty()
                .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.MONSTROSITY))
                .append(Component.literal(String.valueOf(monstrosityCount)));
        }
    );

    /**
     * 巨兽熔炉 — 骇人之恶等级随巨兽器官数量缩放（时长固定 60 秒，黄胆汁固定 100）
     */
    public static final OrganTooltipConsumer MONSTROSITY_FURNACE_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0：等级
            Map.of(0, List.of(MONSTROSITY_FURNACE_LEVEL_FORMULA_VALUE)),
            // detailed line 1 含动态 %s（等级）
            Map.of(1, List.of(MONSTROSITY_FURNACE_LEVEL_FORMULA_VALUE))
        ))
        .build();

    /**
     * 火龙吐息袋伤害公式：1 + 基础值 × 通用法术强度 × 学派法术强度 × 0.75（计入器官数上限 10）
     */
    private static final FormulaValue FIRE_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE =
        buildDragonBreathSacDamageFormulaValue(DragonBreathCastingTask.BreathType.FIRE_BREATH, WAICItemTagManager.FIRE_DRAGON);

    /**
     * 冰龙吐息袋伤害公式：1 + 基础值 × 通用法术强度 × 学派法术强度 × 0.75（计入器官数上限 10）
     */
    private static final FormulaValue ICE_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE =
        buildDragonBreathSacDamageFormulaValue(DragonBreathCastingTask.BreathType.ICE_BREATH, WAICItemTagManager.ICE_DRAGON);

    /**
     * 电龙吐息袋伤害公式：1 + 基础值 × 通用法术强度 × 学派法术强度 × 0.75（计入器官数上限 10）
     */
    private static final FormulaValue LIGHTNING_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE =
        buildDragonBreathSacDamageFormulaValue(DragonBreathCastingTask.BreathType.LIGHTNING_BREATH, WAICItemTagManager.LIGHTNING_DRAGON);

    /**
     * 火龙吐息袋计入器官数公式：min(火龙器官数, 10)
     */
    private static final FormulaValue FIRE_DRAGON_BREATH_SAC_COUNT_FORMULA_VALUE =
        buildDragonBreathSacFormulaValue(WAICItemTagManager.FIRE_DRAGON);

    /**
     * 冰龙吐息袋计入器官数公式：min(冰龙器官数, 10)
     */
    private static final FormulaValue ICE_DRAGON_BREATH_SAC_COUNT_FORMULA_VALUE =
        buildDragonBreathSacFormulaValue(WAICItemTagManager.ICE_DRAGON);

    /**
     * 电龙吐息袋计入器官数公式：min(电龙器官数, 10)
     */
    private static final FormulaValue LIGHTNING_DRAGON_BREATH_SAC_COUNT_FORMULA_VALUE =
        buildDragonBreathSacFormulaValue(WAICItemTagManager.LIGHTNING_DRAGON);

    /**
     * 火龙吐息袋 — 伤害随火龙器官数量缩放，计入器官数上限 10
     */
    public static final OrganTooltipConsumer FIRE_DRAGON_BREATH_SAC_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0：伤害
            Map.of(0, List.of(FIRE_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE)),
            // detailed line 1 含伤害 %s，line 2 含器官数 %s
            Map.of(
                1, List.of(FIRE_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE),
                2, List.of(FIRE_DRAGON_BREATH_SAC_COUNT_FORMULA_VALUE)
            )
        ))
        .build();

    /**
     * 冰龙吐息袋 — 伤害随冰龙器官数量缩放，计入器官数上限 10
     */
    public static final OrganTooltipConsumer ICE_DRAGON_BREATH_SAC_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0：伤害
            Map.of(0, List.of(ICE_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE)),
            // detailed line 1 含伤害 %s，line 2 含器官数 %s
            Map.of(
                1, List.of(ICE_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE),
                2, List.of(ICE_DRAGON_BREATH_SAC_COUNT_FORMULA_VALUE)
            )
        ))
        .build();

    /**
     * 电龙吐息袋 — 伤害随电龙器官数量缩放，计入器官数上限 10
     */
    public static final OrganTooltipConsumer LIGHTNING_DRAGON_BREATH_SAC_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0：伤害
            Map.of(0, List.of(LIGHTNING_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE)),
            // detailed line 1 含伤害 %s，line 2 含器官数 %s
            Map.of(
                1, List.of(LIGHTNING_DRAGON_BREATH_SAC_DAMAGE_FORMULA_VALUE),
                2, List.of(LIGHTNING_DRAGON_BREATH_SAC_COUNT_FORMULA_VALUE)
            )
        ))
        .build();

    /**
     * 构建龙吐息袋计入器官数 FormulaValue，公式为 min(对应龙器官数, 10)
     */
    private static FormulaValue buildDragonBreathSacFormulaValue(TagKey<Item> dragonTag) {
        return new FormulaValue(
            context -> {
                int count = ChestCavityUtil.getOrganCountWithSelf(context, dragonTag);
                return Component.literal(String.valueOf(Math.min(10, count)));
            },
            context -> {
                int count = ChestCavityUtil.getOrganCountWithSelf(context, dragonTag);
                return Component.empty()
                    .append(Component.literal("min(10,\u00A0"))
                    .append(TooltipUtil.tagOrganCountName(dragonTag))
                    .append(Component.literal(String.valueOf(count)))
                    .append(Component.literal(")"));
            }
        );
    }

    /**
     * 构建龙吐息袋伤害 FormulaValue
     * <p>
     * 伤害由 Iron's Spells 法术系统的 getDamage 计算，公式为 1 + 基础值 × 通用法术强度 × 学派法术强度 × 0.75
     * </p>
     */
    private static FormulaValue buildDragonBreathSacDamageFormulaValue(DragonBreathCastingTask.BreathType breathType, TagKey<Item> dragonTag) {
        return new FormulaValue(
            context -> {
                LivingEntity entity = context.entity();
                int count = Math.min(10, ChestCavityUtil.getOrganCountWithSelf(context, dragonTag));
                float damage = DragonBreathCastingTask.getDamage(breathType, count, entity);
                return Component.literal(TooltipUtil.formatAttributeValue(damage));
            },
            context -> {
                LivingEntity entity = context.entity();
                int count = Math.min(10, ChestCavityUtil.getOrganCountWithSelf(context, dragonTag));
                double spellPower = entity.getAttributeValue(AttributeRegistry.SPELL_POWER);
                Holder<Attribute> schoolAttribute = switch (breathType) {
                    case FIRE_BREATH -> AttributeRegistry.FIRE_SPELL_POWER;
                    case ICE_BREATH -> AttributeRegistry.ICE_SPELL_POWER;
                    case LIGHTNING_BREATH -> AttributeRegistry.LIGHTNING_SPELL_POWER;
                };
                double schoolPower = entity.getAttributeValue(schoolAttribute);
                MutableComponent baseValueComponent;
                if (breathType == DragonBreathCastingTask.BreathType.LIGHTNING_BREATH) {
                    baseValueComponent = Component.empty()
                        .append(TooltipUtil.tagOrganCountName(dragonTag))
                        .append(Component.literal(String.valueOf(count)));
                } else {
                    baseValueComponent = Component.empty()
                        .append(Component.literal("("))
                        .append(TooltipUtil.tagOrganCountName(dragonTag))
                        .append(Component.literal(String.valueOf(count)))
                        .append(TooltipUtil.formulaOperator("-"))
                        .append(Component.literal("1"))
                        .append(Component.literal(")"));
                }
                return Component.empty()
                    .append(Component.literal("1"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(baseValueComponent)
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(TooltipUtil.attributeName(AttributeRegistry.SPELL_POWER))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(spellPower)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(TooltipUtil.attributeName(schoolAttribute))
                    .append(Component.literal(TooltipUtil.formatAttributeValue(schoolPower)))
                    .append(TooltipUtil.formulaOperator("×"))
                    .append(Component.literal("0.75"));
            }
        );
    }

    /**
     * 蛋糕胃甜蜜等级公式：蛋糕器官数
     */
    private static final FormulaValue CAKE_STOMACH_FORMULA_VALUE = new FormulaValue(
        context -> {
            int cakeCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.CAKE);
            return Component.literal(String.valueOf(cakeCount));
        },
        context -> {
            int cakeCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.CAKE);
            return Component.empty()
                .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.CAKE))
                .append(Component.literal(String.valueOf(cakeCount)));
        }
    );

    /**
     * 蛋糕胃 — 甜蜜效果叠加等级随蛋糕器官数量缩放
     */
    public static final OrganTooltipConsumer CAKE_STOMACH_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0：等级
            Map.of(0, List.of(CAKE_STOMACH_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(CAKE_STOMACH_FORMULA_VALUE))
        ))
        .build();

    /**
     * 布织泰迪熊回血公式：4 + 布织器官数
     */
    private static final FormulaValue CLOTH_TEDDY_BEAR_FORMULA_VALUE = new FormulaValue(
        context -> {
            int clothCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.CLOTH);
            return Component.literal(TooltipUtil.formatAttributeValue(4 + clothCount));
        },
        context -> {
            int clothCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.CLOTH);
            return Component.empty()
                .append(Component.literal("4"))
                .append(TooltipUtil.formulaOperator("+"))
                .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.CLOTH))
                .append(Component.literal(String.valueOf(clothCount)));
        }
    );

    /**
     * 布织泰迪熊 — 每个羊毛回血随布织器官数量缩放
     */
    public static final OrganTooltipConsumer CLOTH_TEDDY_BEAR_TOOLTIP = OrganTooltip.builder()
        .dynamicActiveSkill(slotContext -> DynamicValues.split(
            // simple 单段含 1 个 %s 在 line 0：回血
            Map.of(0, List.of(CLOTH_TEDDY_BEAR_FORMULA_VALUE)),
            // detailed 含动态 %s 在 line 1
            Map.of(1, List.of(CLOTH_TEDDY_BEAR_FORMULA_VALUE))
        ))
        .build();

    /**
     * 诅咒金虚弱等级公式：阈值 1，每 5 个 +1 级，floor((count - 1) ÷ 5) + 1，未达阈值显示灰色 /
     */
    private static final FormulaValue CURSED_GOLD_WEAKNESS_FORMULA_VALUE = buildCursedGoldStepFormulaValue(1, 5);

    /**
     * 诅咒金缓慢等级公式：阈值 3，每 6 个 +1 级，floor((count - 3) ÷ 6) + 1，未达阈值显示灰色 /
     */
    private static final FormulaValue CURSED_GOLD_SLOWNESS_FORMULA_VALUE = buildCursedGoldStepFormulaValue(3, 6);

    /**
     * 诅咒金饥饿等级公式：阈值 5，每 9 个 +1 级，floor((count - 5) ÷ 9) + 1，未达阈值显示灰色 /
     */
    private static final FormulaValue CURSED_GOLD_HUNGER_FORMULA_VALUE = buildCursedGoldStepFormulaValue(5, 9);

    /**
     * 诅咒金器官 — 虚弱/缓慢/饥饿等级随诅咒器官数量阶梯式递增（四个诅咒金器官共用）
     */
    public static final OrganTooltipConsumer CURSED_GOLD_TOOLTIP = OrganTooltip.builder()
        .dynamicPassiveEffect(slotContext -> DynamicValues.split(
            // simple 无动态段
            Map.of(),
            // detailed line 1/2/3 分别含虚弱/缓慢/饥饿等级 %s
            Map.of(
                1, List.of(CURSED_GOLD_WEAKNESS_FORMULA_VALUE),
                2, List.of(CURSED_GOLD_SLOWNESS_FORMULA_VALUE),
                3, List.of(CURSED_GOLD_HUNGER_FORMULA_VALUE)
            )
        ))
        .build();

    /**
     * 构建诅咒金阶梯式惩罚效果等级 FormulaValue
     *
     * @param threshold 触发阈值（诅咒器官数下限）
     * @param step      每多少个器官提升 1 级
     */
    private static FormulaValue buildCursedGoldStepFormulaValue(int threshold, int step) {
        return new FormulaValue(
            context -> {
                int cursedCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.CURSED);
                if (cursedCount < threshold) {
                    return Component.literal("/").withStyle(ChatFormatting.DARK_GRAY);
                }
                int amplifier = (cursedCount - threshold) / step;
                return Component.literal(String.valueOf(amplifier + 1));
            },
            context -> {
                int cursedCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.CURSED);
                return Component.empty()
                    .append(Component.literal("floor(("))
                    .append(TooltipUtil.tagOrganCountName(WAICItemTagManager.CURSED))
                    .append(Component.literal(String.valueOf(cursedCount)))
                    .append(TooltipUtil.formulaOperator("-"))
                    .append(Component.literal(String.valueOf(threshold)))
                    .append(Component.literal(")"))
                    .append(TooltipUtil.formulaOperator("÷"))
                    .append(Component.literal(String.valueOf(step)))
                    .append(Component.literal(")"))
                    .append(TooltipUtil.formulaOperator("+"))
                    .append(Component.literal("1"));
            }
        );
    }
}

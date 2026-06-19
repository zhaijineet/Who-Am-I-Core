package net.zhaiji.who_am_i_core.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

/**
 * 动态公式显示，封装计算值 Component 与公式 Component
 */
public record FormulaValue(
    Function<LivingEntity, MutableComponent> valueComponentProvider,
    Function<LivingEntity, MutableComponent> formulaComponentProvider
) {
    /**
     * 构建嵌入描述文本的 Component
     *
     * @param entity      实体（null 时显示 "?"）
     * @param showFormula 是否展开公式（Ctrl按下时）
     */
    public Component buildComponent(LivingEntity entity, boolean showFormula) {
        if (entity == null) {
            return Component.literal("?").withStyle(ChatFormatting.YELLOW);
        }
        MutableComponent valueComponent = valueComponentProvider.apply(entity);
        if (!showFormula) {
            return valueComponent.withStyle(ChatFormatting.YELLOW);
        }
        MutableComponent formulaComponent = formulaComponentProvider.apply(entity);
        return Component.empty()
            .append(valueComponent.withStyle(ChatFormatting.YELLOW))
            .append(Component.literal("\u00A0=\u00A0").withStyle(ChatFormatting.GRAY))
            .append(formulaComponent.withStyle(ChatFormatting.GRAY));
    }
}

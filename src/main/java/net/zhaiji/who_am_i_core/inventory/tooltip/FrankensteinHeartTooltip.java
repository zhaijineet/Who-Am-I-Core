package net.zhaiji.who_am_i_core.inventory.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.component.BundleContents;

/**
 * 弗兰肯斯坦心脏的自定义 Tooltip 组件。
 * <p>
 * 与原版 BundleTooltip 结构相同，但使用独立的类型，
 * 以便通过 NeoForge 注册自定义的 ClientTooltipComponent 渲染器。
 * </p>
 */
public record FrankensteinHeartTooltip(BundleContents contents) implements TooltipComponent {
}

package net.zhaiji.who_am_i_core.manager;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.zhaiji.who_am_i_core.api.UseCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 可使用条件管理器
 * <p>
 * 管理所有注册的可使用条件，提供统一的条件匹配接口
 * </p>
 */
public class UseConditionManager {
    private static final List<UseCondition> CONDITIONS = new ArrayList<>();

    /**
     * 注册一个可使用条件
     *
     * @param condition 要注册的条件
     */
    public static void register(UseCondition condition) {
        CONDITIONS.add(condition);
    }

    /**
     * 获取匹配的可使用条件
     * <p>
     * 收集所有匹配条件，按优先级（值越高越优先）选取最优条件。
     * 优先级相同时按注册顺序（先注册优先）。
     * </p>
     *
     * @param entity 尝试使用的实体
     * @param stack  要使用的物品
     * @return 匹配的条件，如果没有匹配则返回空
     */
    public static Optional<UseCondition> getMatchingCondition(LivingEntity entity, ItemStack stack) {
        UseCondition best = null;
        for (UseCondition condition : CONDITIONS) {
            if (!condition.canUse(entity, stack)) continue;
            if (best == null || condition.priority() > best.priority()) {
                best = condition;
            }
        }
        return Optional.ofNullable(best);
    }

    /**
     * 检查是否有任何条件允许使用此物品
     *
     * @param entity 尝试使用的实体
     * @param stack  要使用的物品
     * @return 是否可以使用
     */
    public static boolean canUse(LivingEntity entity, ItemStack stack) {
        return getMatchingCondition(entity, stack).isPresent();
    }

    /**
     * 检查物品是否可能被某个条件接受（仅检查物品，不检查实体）
     *
     * @param stack 要检查的物品
     * @return 是否可能被接受
     */
    public static boolean matchesItem(ItemStack stack) {
        for (UseCondition condition : CONDITIONS) {
            if (condition.matchesItem(stack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取可能接受此物品的条件的使用动画
     *
     * @param stack 要检查的物品
     * @return 使用动画，如果没有匹配则返回空
     */
    public static Optional<UseAnim> getUseAnimation(ItemStack stack) {
        for (UseCondition condition : CONDITIONS) {
            if (condition.matchesItem(stack)) {
                return Optional.of(condition.getUseAnimation(stack));
            }
        }
        return Optional.empty();
    }
}

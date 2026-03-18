package net.zhaiji.who_am_i_core.manager;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.zhaiji.who_am_i_core.api.IEdibleCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 可食用条件管理器
 * <p>
 * 管理所有注册的可食用条件，提供统一的条件匹配接口
 * </p>
 */
public class EdibleConditionManager {
    private static final List<IEdibleCondition> CONDITIONS = new ArrayList<>();

    /**
     * 注册一个可食用条件
     *
     * @param condition 要注册的条件
     */
    public static void register(IEdibleCondition condition) {
        CONDITIONS.add(condition);
    }

    /**
     * 获取匹配的可食用条件
     * <p>
     * 按注册顺序检查所有条件，返回第一个匹配的条件
     * </p>
     *
     * @param entity 尝试食用的实体
     * @param stack  要食用的物品
     * @return 匹配的条件，如果没有匹配则返回空
     */
    public static Optional<IEdibleCondition> getMatchingCondition(LivingEntity entity, ItemStack stack) {
        for (IEdibleCondition condition : CONDITIONS) {
            if (condition.canEat(entity, stack)) {
                return Optional.of(condition);
            }
        }
        return Optional.empty();
    }

    /**
     * 检查是否有任何条件允许食用此物品
     *
     * @param entity 尝试食用的实体
     * @param stack  要食用的物品
     * @return 是否可以食用
     */
    public static boolean canEat(LivingEntity entity, ItemStack stack) {
        return getMatchingCondition(entity, stack).isPresent();
    }

    /**
     * 检查物品是否可能被某个条件接受（仅检查物品，不检查实体）
     *
     * @param stack 要检查的物品
     * @return 是否可能被接受
     */
    public static boolean isTargetItem(ItemStack stack) {
        for (IEdibleCondition condition : CONDITIONS) {
            if (condition.isTargetItem(stack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取可能接受此物品的条件的食用动画
     *
     * @param stack 要检查的物品
     * @return 食用动画，如果没有匹配则返回空
     */
    public static Optional<UseAnim> getUseAnimation(ItemStack stack) {
        for (IEdibleCondition condition : CONDITIONS) {
            if (condition.isTargetItem(stack)) {
                return Optional.of(condition.getUseAnimation());
            }
        }
        return Optional.empty();
    }
}

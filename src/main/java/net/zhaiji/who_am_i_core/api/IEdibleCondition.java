package net.zhaiji.who_am_i_core.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

/**
 * 可食用条件接口
 * <p>
 * 器官可以实现此接口来定义哪些物品可以被食用，以及食用后的效果
 * </p>
 */
public interface IEdibleCondition {
    /**
     * 检测物品是否可被此条件允许食用
     *
     * @param entity 尝试食用的实体
     * @param stack  要食用的物品
     * @return 是否可以食用
     */
    boolean canEat(LivingEntity entity, ItemStack stack);

    /**
     * 检测物品是否可能被此条件接受（仅检查物品，不检查实体）
     * <p>
     * 用于 getUseAnimation 等无法获取实体的场景
     * </p>
     *
     * @param stack 要检查的物品
     * @return 物品是否可能被接受
     */
    boolean isTargetItem(ItemStack stack);

    /**
     * 食用完成后的效果
     *
     * @param entity 食用的实体
     * @param stack  被食用的物品
     */
    void onEat(LivingEntity entity, ItemStack stack);

    /**
     * 获取食用动画
     *
     * @return 食用动画类型，默认为 EAT
     */
    default UseAnim getUseAnimation() {
        return UseAnim.EAT;
    }

    /**
     * 获取食用时长
     *
     * @return 食用所需的 ticks，默认为 32
     */
    default int getUseDuration() {
        return 32;
    }
}

package net.zhaiji.who_am_i_core.api.function;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.who_am_i_core.api.UseCondition;

/**
 * 持续使用完成回调
 */
@FunctionalInterface
public interface FinishUsingItemFunction {
    /**
     * @param entity       使用物品的实体
     * @param stack        被使用的物品
     * @param useCondition 触发此回调的使用条件
     * @return 处理后的 ItemStack
     */
    ItemStack apply(LivingEntity entity, ItemStack stack, UseCondition useCondition);
}

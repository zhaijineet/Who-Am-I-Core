package net.zhaiji.who_am_i_core.api.function;

import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.who_am_i_core.api.UseCondition;

/**
 * 瞬发使用回调
 */
@FunctionalInterface
public interface UseFunction {
    /**
     * @param player       使用物品的玩家
     * @param stack        被使用的物品
     * @param useCondition 触发此回调的使用条件
     * @return 交互结果
     */
    InteractionResultHolder<ItemStack> apply(Player player, ItemStack stack, UseCondition useCondition);
}

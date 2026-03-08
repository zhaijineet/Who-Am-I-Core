package net.zhaiji.who_am_i_core.util;

import net.minecraft.world.item.ItemStack;
import net.zhaiji.chestcavitybeyond.api.capability.IOrgan;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;

public class HAICOrganUtil {
    /**
     * 检查物品是否为器官
     */
    public static boolean isOrgan(ItemStack stack) {
        IOrgan organCap = ChestCavityUtil.getOrganCap(stack);
        return stack.is(ItemTagManager.ORGANS) || organCap != null && organCap != OrganBuilder.EMPTY_ORGAN;
    }

    /**
     * 计算周围8个位置的槽位索引
     */
    public static int[] getAdjacentSlots(int slotIndex) {
        int posInRow = slotIndex % 9;
        int row = slotIndex / 9;

        return new int[]{
                // 左
                posInRow > 0 ? slotIndex - 1 : -1,
                // 右
                posInRow < 8 ? slotIndex + 1 : -1,
                // 上排对应位置
                row > 0 ? slotIndex - 9 : -1,
                // 下排对应位置
                row < 2 ? slotIndex + 9 : -1,
                // 斜上左
                row > 0 && posInRow > 0 ? slotIndex - 10 : -1,
                // 斜上右
                row > 0 && posInRow < 8 ? slotIndex - 8 : -1,
                // 斜下左
                row < 2 && posInRow > 0 ? slotIndex + 8 : -1,
                // 斜下右
                row < 2 && posInRow < 8 ? slotIndex + 10 : -1
        };
    }
}

package net.zhaiji.who_am_i_core.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;

import java.util.function.Function;
import java.util.function.Predicate;

public class WAICOrganUtil {
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

    /**
     * 获取物品的总附魔等级
     */
    private static int getTotalEnchantmentLevels(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        int total = 0;
        for (Holder<Enchantment> ench : enchantments.keySet()) {
            total += enchantments.getLevel(ench);
        }
        return total;
    }

    /**
     * 浮霜器官的附魔加成
     */
    public static double frostMetalBonus(ChestCavitySlotContext context) {
        return Math.floor(Math.sqrt(getTotalEnchantmentLevels(context.stack())));
    }
}

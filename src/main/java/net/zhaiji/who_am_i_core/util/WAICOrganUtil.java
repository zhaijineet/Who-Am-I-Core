package net.zhaiji.who_am_i_core.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class WAICOrganUtil {
    /**
     * 闹鬼的骨头：胸腔打开时设置可以移动的标记
     */
    public static void hauntedBoneChestCavityOpen(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        if (data == null) return;
        Level level = data.getOwner().level();
        if (level.isClientSide()) return;
        ItemStack stack = context.stack();
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putBoolean("canChange", true);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    /**
     * 闹鬼的骨头：胸腔打开时随机移动到一个空槽位
     */
    public static void hauntedBoneChestCavityClose(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        if (data == null) return;
        Level level = data.getOwner().level();
        if (level.isClientSide()) return;
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            if (data.getStackInSlot(i).isEmpty()) {
                emptySlots.add(i);
            }
        }
        if (emptySlots.isEmpty()) return;
        ItemStack stack = context.stack();
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.contains("canChange") && !tag.getBoolean("canChange")) return;
        tag.putBoolean("canChange", false);
        data.setStackInSlot(context.index(), ItemStack.EMPTY);
        int targetSlot = emptySlots.get(level.random.nextInt(emptySlots.size()));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        data.setStackInSlot(targetSlot, stack);
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
     * 计算附魔加成：√level
     */
    private static double calculateBonus(int levels) {
        return Math.floor(Math.sqrt(levels));
    }

    /**
     * 浮霜器官的附魔加成
     * 参考铁砧工艺的无情属性计算方式
     */
    public static double frostMetalBonus(ChestCavitySlotContext context) {
        return calculateBonus(getTotalEnchantmentLevels(context.stack()));
    }
}

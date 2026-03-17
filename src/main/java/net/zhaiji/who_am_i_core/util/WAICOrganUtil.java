package net.zhaiji.who_am_i_core.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;

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

    /**
     * 根据数值进行判定次数计算
     * 每5点数值获得1次判定，余数部分有额外几率获得1次判定（余数*20%）
     *
     * @param value 输入数值
     * @param level 世界对象，用于获取随机数生成器
     * @return 判定次数
     */
    public static int rollChance(int value, Level level) {
        if (value <= 0) return 0;
        int baseCount = value / 5;
        int remainder = value % 5;
        // 余数部分按每点20%几率额外获得1次判定
        if (remainder > 0 && level.random.nextFloat() < remainder * 0.2f) {
            baseCount++;
        }
        return baseCount;
    }

    /**
     * 根据实体的幸运属性进行判定次数计算
     * 每5点幸运值获得1次判定，余数部分每点有20%几率获得额外判定
     *
     * @param entity 实体对象，用于获取幸运属性
     * @return 判定次数
     */
    public static int rollChance(LivingEntity entity) {
        double luck = entity.getAttributeValue(Attributes.LUCK);
        return rollChance((int) luck, entity.level());
    }

    /**
     * 武器伤害倍率
     * TODO 不好计算攻击是主手还是副手
     *
     * @return 武器伤害倍率
     */
    public static float getWeaponDamageMultiplier(LivingEntity entity) {
        return 1;
    }
}

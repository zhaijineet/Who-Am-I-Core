package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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
     * 根据实体的幸运属性进行判定次数计算
     * 每5点幸运值获得1次判定，余数部分每点有20%几率获得额外判定
     *
     * @param entity 实体，用于获取幸运属性
     * @return 判定次数
     */
    public static int rollChance(LivingEntity entity) {
        int luck = (int) entity.getAttributeValue(Attributes.LUCK);
        if (luck <= 0) return luck;
        int count = luck / 5;
        int remainder = luck % 5;
        // 余数部分按每点20%几率额外获得1次判定
        if (remainder > 0 && entity.getRandom().nextFloat() < remainder * 0.2F) {
            count++;
        }
        return count;
    }

    /**
     * 简单判断几率是否通过判定
     *
     * @param entity 实体
     * @param chance 几率
     * @return 是否通过判定
     */
    public static boolean rollResult(LivingEntity entity, float chance) {
        int rollChance = rollChance(entity);
        if (rollChance <= 0) {
            // 幸运低，每低一点减少判定20%几率，如果够幸运，应该是有成功的可能性的
            return entity.getRandom().nextFloat() < Math.clamp(chance - rollChance * 0.2F, 0.001F, 1.0F);
        } else {
            for (int i = 0; i < rollChance; i++) {
                if (entity.getRandom().nextFloat() < chance) {
                    return true;
                }
            }
            return false;
        }
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

    /**
     * 根据法术流派获取对应的染料物品
     *
     * @param schoolType 法术流派
     * @return 对应的染料物品，如果没有对应染料则返回null
     */
    public static Item getDyeItemForSchool(SchoolType schoolType) {
        ResourceLocation id = schoolType.getId();
        if (id.equals(SchoolRegistry.BLOOD_RESOURCE)) return Items.RED_DYE;
        if (id.equals(SchoolRegistry.FIRE_RESOURCE)) return Items.ORANGE_DYE;
        if (id.equals(SchoolRegistry.HOLY_RESOURCE)) return Items.YELLOW_DYE;
        if (id.equals(SchoolRegistry.ICE_RESOURCE)) return Items.LIGHT_BLUE_DYE;
        if (id.equals(SchoolRegistry.LIGHTNING_RESOURCE)) return Items.BLUE_DYE;
        if (id.equals(SchoolRegistry.NATURE_RESOURCE)) return Items.GREEN_DYE;
        if (id.equals(SchoolRegistry.ELDRITCH_RESOURCE)) return Items.CYAN_DYE;
        if (id.equals(SchoolRegistry.ENDER_RESOURCE)) return Items.PURPLE_DYE;
        if (id.equals(SchoolRegistry.EVOCATION_RESOURCE)) return Items.GRAY_DYE;
        return Items.AIR;
    }
}

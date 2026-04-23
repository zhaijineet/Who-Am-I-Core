package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.api.capability.IOrgan;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.manager.OrganManager;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WAICOrganUtil {
    /**
     * 计算周围8个位置的槽位索引，越界槽位自动排除
     */
    public static List<Integer> getAdjacentSlots(int slotIndex) {
        List<Integer> result = new ArrayList<>();
        int col = slotIndex % 9;
        int row = slotIndex / 9;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = row + dr;
                int c = col + dc;
                if (r < 0 || r > 2 || c < 0 || c > 8) continue;
                result.add(r * 9 + c);
            }
        }
        return result;
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
     * 无情机制的附魔加成
     */
    public static double mercilessBonus(ChestCavitySlotContext context) {
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

    /**
     * 从弗兰肯斯坦心脏的 BundleContents 中聚合所有内部心脏器官的属性修饰符
     * <p>
     * 遍历收纳袋中存储的所有心脏物品，获取每个心脏的 IOrgan 属性修饰符，
     * 将相同属性+相同操作类型的修饰符合并为一个，值相加。
     * </p>
     *
     * @param context   当前弗兰肯斯坦心脏的槽位上下文
     * @param modifiers 需要填充的属性修饰符集合
     */
    public static void aggregateFrankensteinHeartAttributes(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        BundleContents contents = context.stack().getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        // 使用 Map 按 (属性, 操作类型) 分组合并值
        Map<Map.Entry<Holder<Attribute>, AttributeModifier.Operation>, Double> merged = new LinkedHashMap<>();
        for (ItemStack stack : contents.itemsCopy()) {
            IOrgan organ = ChestCavityUtil.getOrganCap(stack);
            if (organ != OrganManager.EMPTY_ORGAN) {
                ChestCavitySlotContext organContext = new ChestCavitySlotContext(
                    context.data(),
                    context.entity(),
                    context.id(),
                    context.index(),
                    stack
                );
                organ.getAttributeModifiers(organContext).forEach((attribute, modifier) ->
                    merged.merge(Map.entry(attribute, modifier.operation()), modifier.amount(), Double::sum)
                );
            }
        }
        merged.forEach((key, amount) ->
            modifiers.put(key.getKey(), new AttributeModifier(context.id(), amount, key.getValue()))
        );
    }

    /**
     * 检查指定物品是否是实体胸腔中的器官（通过引用比较）
     * <p>
     * 仅当渲染传入的 stack 与胸腔 handler 中存储的是同一个 Java 对象引用时返回 true。
     * </p>
     *
     * @param entity 实体
     * @param stack  待检查的物品
     * @return 该 stack 是否是胸腔内的原始器官引用
     */
    public static boolean isInChest(LivingEntity entity, ItemStack stack) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        for (ItemStack organ : data.getOrgans()) {
            if (organ == stack) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取实体的原始全局温度
     *
     * @param entity 实体
     * @return 实体的温度属性值，无任何修改
     */
    public static double getOriginalTemperature(LivingEntity entity) {
        return entity.getAttributeValue(WAICAttribute.TEMPERATURE);
    }

    /**
     * 获取实体的有效全局温度
     * <p>
     * 若实体拥有王国器官，温度强制为 0；否则返回原始温度。
     * </p>
     *
     * @param entity 实体
     * @return 经过器官修正后的有效温度
     */
    public static double getEffectiveTemperature(LivingEntity entity) {
        if (ChestCavityUtil.getData(entity).hasOrgan(FDBossesOrgans.MALKUTH.get())) {
            return 0;
        }
        return getOriginalTemperature(entity);
    }

    /**
     * 获取以指定槽位为中心的九宫格内局部温度
     * <p>
     * 遍历中心槽位及相邻 8 格，累加每个有效槽位中器官通过属性修饰符贡献的温度值。
     * 空槽位视为温度 0，跳过不计。
     * </p>
     *
     * @param context 当前槽位上下文
     * @return 九宫格内器官贡献的局部温度总和
     */
    public static double getLocalTemperature(ChestCavitySlotContext context) {
        if (context.data().hasOrgan(FDBossesOrgans.MALKUTH.get())) {
            return getMalkuthLocalTemperature(context);
        }
        int center = context.index();
        List<Integer> adjacent = getAdjacentSlots(center);
        double total = 0;
        // 中心 + 遍历相邻 8 格
        total += collectTemperatureFromSlot(context, center);
        for (int slot : adjacent) {
            total += collectTemperatureFromSlot(context, slot);
        }
        return total;
    }

    /**
     * 王国器官的局部温度聚合
     * <p>
     * 遍历胸腔内所有器官，根据物品标签判断温度方向：
     * </p>
     * <pre>
     *   仅有 ICE 标签 → 取负温度（温度 < 0 的部分）
     *   仅有 FIRE 标签 → 取正温度（温度 > 0 的部分）
     *   同时拥有 ICE 和 FIRE → 取绝对值最高的温度值
     *   两者皆无 → 返回原始胸腔温度
     * </pre>
     */
    private static double getMalkuthLocalTemperature(ChestCavitySlotContext context) {
        ItemStack caller = context.stack();
        boolean isIce = caller.is(WAICItemTagManager.ICE);
        boolean isFire = caller.is(WAICItemTagManager.FIRE);
        if (!isIce && !isFire) return getOriginalTemperature(context.entity());
        double iceTotal = 0;
        double fireTotal = 0;
        List<ItemStack> organs = context.data().getOrgans();
        for (int slot = 0; slot < organs.size(); slot++) {
            ItemStack stack = organs.get(slot);
            IOrgan organ = ChestCavityUtil.getOrganCap(stack);
            if (organ == OrganManager.EMPTY_ORGAN) continue;
            ChestCavitySlotContext slotContext = new ChestCavitySlotContext(
                context.data(), context.entity(), context.id(), slot, stack
            );
            double temperature = 0;
            for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : organ.getAttributeModifiers(slotContext).entries()) {
                if (entry.getKey().equals(WAICAttribute.TEMPERATURE)) {
                    temperature = entry.getValue().amount();
                    break;
                }
            }
            if (temperature < 0) iceTotal += temperature;
            if (temperature > 0) fireTotal += temperature;
        }
        if (isIce && isFire) {
            return Math.abs(iceTotal) >= fireTotal ? iceTotal : fireTotal;
        } else if (isIce) {
            return iceTotal;
        } else {
            return fireTotal;
        }
    }

    private static double collectTemperatureFromSlot(ChestCavitySlotContext context, int slot) {
        ItemStack stack = context.data().getOrgans().get(slot);
        if (stack.isEmpty()) return 0;
        IOrgan organ = ChestCavityUtil.getOrganCap(stack);
        if (organ == OrganManager.EMPTY_ORGAN) return 0;
        ChestCavitySlotContext slotContext = new ChestCavitySlotContext(
            context.data(),
            context.entity(),
            context.id(),
            slot,
            stack
        );
        for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : organ.getAttributeModifiers(slotContext).entries()) {
            if (entry.getKey().equals(WAICAttribute.TEMPERATURE)) {
                return entry.getValue().amount();
            }
        }
        return 0;
    }
}

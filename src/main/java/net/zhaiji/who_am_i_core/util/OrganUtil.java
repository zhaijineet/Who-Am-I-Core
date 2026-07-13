package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrganUtil {
    /**
     * 获取物品的总附魔等级
     */
    public static int getTotalEnchantmentLevels(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        int total = 0;
        for (Holder<Enchantment> enchantment : enchantments.keySet()) {
            total += enchantments.getLevel(enchantment);
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
     * <p>
     * 每 5 点幸运值获得 1 次判定，余数部分每点有 20% 几率获得额外判定。
     * </p>
     *
     * @param entity 实体，用于获取幸运属性
     * @return 判定次数
     */
    public static int rollChance(LivingEntity entity) {
        int luck = (int) entity.getAttributeValue(Attributes.LUCK);
        if (luck <= 0) return luck;
        int count = luck / 5;
        int remainder = luck % 5;
        // 余数部分按每点 20% 几率额外获得 1 次判定
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
            // 幸运低，每低一点减少判定 20% 几率，如果够幸运，应该是有成功的可能性的
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
     * 根据法术流派获取对应的染料物品
     *
     * @param schoolType 法术流派
     * @return 对应的染料物品，如果没有对应染料则返回 AIR
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
     * 遍历收纳袋中存储的所有心脏物品，获取每个心脏的 Organ 属性修饰符，
     * 将相同属性 + 相同操作类型的修饰符合并为一个，值相加。
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
            Organ organ = ChestCavityUtil.getOrganCap(stack);
            if (organ != Organ.EMPTY) {
                ChestCavitySlotContext organContext = new ChestCavitySlotContext(
                    context.data(),
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
        for (int i = 0; i < data.getSlots(); i++) {
            if (data.getStackInSlot(i) == stack) {
                return true;
            }
        }
        return false;
    }

    public static int getFireOrganCount(ChestCavitySlotContext context) {
        return combineElementalOrganCounts(
            context,
            ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.FIRE),
            ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.ICE)
        );
    }

    public static int getIceOrganCount(ChestCavitySlotContext context) {
        return combineElementalOrganCounts(
            context,
            ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.ICE),
            ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.FIRE)
        );
    }

    public static int getLocalFireOrganCount(ChestCavitySlotContext context) {
        return combineElementalOrganCounts(
            context,
            getLocalOrganCountWithSelf(context, WAICItemTagManager.FIRE),
            getLocalOrganCountWithSelf(context, WAICItemTagManager.ICE)
        );
    }

    public static int getLocalIceOrganCount(ChestCavitySlotContext context) {
        return combineElementalOrganCounts(
            context,
            getLocalOrganCountWithSelf(context, WAICItemTagManager.ICE),
            getLocalOrganCountWithSelf(context, WAICItemTagManager.FIRE)
        );
    }

    static int getLocalOrganCountWithSelf(ChestCavitySlotContext context, TagKey<Item> tag) {
        ChestCavityData data = context.data();
        if (data == null || context.index() < 0) {
            return context.stack().is(tag) ? 1 : 0;
        }
        return countTagInSlots(context, tag);
    }

    static boolean usesAdditiveElementalOrganCounts(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        return data != null && data.hasOrgan(FDBossesOrgans.MALKUTH.get());
    }

    private static int combineElementalOrganCounts(
        ChestCavitySlotContext context,
        int primaryOrganCount,
        int secondaryOrganCount
    ) {
        return usesAdditiveElementalOrganCounts(context)
               ? primaryOrganCount + secondaryOrganCount
               : primaryOrganCount - secondaryOrganCount;
    }

    /**
     * 统计指定槽位及其相邻 8 格中匹配某标签的器官数量
     */
    private static int countTagInSlots(ChestCavitySlotContext context, TagKey<Item> tag) {
        int center = context.index();
        int totalSlots = context.data().getSlots();
        int count = 0;
        ItemStack centerStack = context.data().getStackInSlot(center);
        if (centerStack.is(tag)) count++;
        for (int slot : ChestCavityUtil.getAdjacentSlots(center, totalSlots)) {
            ItemStack stack = context.data().getStackInSlot(slot);
            if (stack.is(tag)) count++;
        }
        return count;
    }

    /**
     * 检查是否为自伤（攻击者和被攻击者是同一实体）
     * <p>
     * 同时检测间接攻击者（{@code source.getEntity()}，如射箭的玩家）
     * 和直接攻击者（{@code source.getDirectEntity()}，如投射物本身），
     * 防止因投射物反射、药水溅射等边缘情况触发自身器官效果。
     * </p>
     *
     * @param target 被攻击目标
     * @param source 伤害源
     * @return true 表示是自伤，应跳过效果
     */
    public static boolean isSelfDamage(LivingEntity target, DamageSource source) {
        Entity sourceEntity = source.getEntity();
        if (sourceEntity instanceof LivingEntity living && living == target) return true;
        Entity directEntity = source.getDirectEntity();
        if (directEntity instanceof LivingEntity living && living == target) return true;
        return false;
    }
}

package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.api.goal.GoalCombatContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.entity.RailgunProjectileEntity;
import net.zhaiji.who_am_i_core.manager.RailgunAmmoManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.register.WAICAttribute;

public class AnvilCraftOrganUtil {
    // ==================== 浮霜器官 ====================

    /**
     * 浮霜器官的通用 modifier 模式
     * 基础值 3.0 + 附魔加成
     */
    @SafeVarargs
    private static void frostMetalBaseModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers,
        Holder<Attribute>... primaryAttributes
    ) {
        double value = 3.0 + OrganUtil.mercilessBonus(context);
        for (Holder<Attribute> attribute : primaryAttributes) {
            modifiers.put(attribute, OrganAttributeUtil.createAddValueModifier(context.id(), value));
        }
    }

    /**
     * 浮霜心脏属性修饰符
     */
    public static void frostMetalHeartModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.HEALTH);
    }

    /**
     * 浮霜肺脏属性修饰符
     */
    public static void frostMetalLungModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.BREATH_RECOVERY, InitAttribute.BREATH_CAPACITY, InitAttribute.ENDURANCE);
    }

    /**
     * 浮霜脊柱属性修饰符
     */
    public static void frostMetalSpineModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.NERVES, InitAttribute.DEFENSE);
    }

    /**
     * 浮霜胃属性修饰符
     */
    public static void frostMetalStomachModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.DIGESTION);
    }

    /**
     * 浮霜肠子属性修饰符
     */
    public static void frostMetalIntestineModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.NUTRITION);
    }

    /**
     * 浮霜肾脏属性修饰符
     */
    public static void frostMetalKidneyModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.FILTRATION);
    }

    /**
     * 浮霜脾脏属性修饰符
     */
    public static void frostMetalSpleenModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.METABOLISM);
    }

    /**
     * 浮霜肝脏属性修饰符
     */
    public static void frostMetalLiverModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.DETOXIFICATION);
    }

    /**
     * 浮霜阑尾属性修饰符
     */
    public static void frostMetalAppendixModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        frostMetalBaseModifier(context, modifiers, Attributes.LUCK);
    }

    /**
     * 浮霜肋骨属性修饰符
     */
    public static void frostMetalRibModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.DEFENSE);
    }

    /**
     * 浮霜肌肉属性修饰符
     */
    public static void frostMetalMuscleModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        frostMetalBaseModifier(context, modifiers, InitAttribute.STRENGTH, InitAttribute.SPEED);
    }

    // ==================== 超限合金器官 ====================

    /**
     * 超限合金器官的通用 modifier 模式
     * 基础值 5.0 + 附魔加成，同时添加掠夺和时运
     */
    @SafeVarargs
    private static void transcendiumBaseModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers,
        Holder<Attribute>... primaryAttributes
    ) {
        double value = 5.0 + OrganUtil.mercilessBonus(context);
        for (Holder<Attribute> attribute : primaryAttributes) {
            modifiers.put(attribute, OrganAttributeUtil.createAddValueModifier(context.id(), value));
        }
        modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
        modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), 1.0));
    }

    /**
     * 超限合金心脏属性修饰符
     */
    public static void transcendiumHeartModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.HEALTH);
    }

    /**
     * 超限合金肺脏属性修饰符
     */
    public static void transcendiumLungModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.BREATH_RECOVERY, InitAttribute.BREATH_CAPACITY, InitAttribute.ENDURANCE);
    }

    /**
     * 超限合金脊柱属性修饰符
     */
    public static void transcendiumSpineModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.NERVES, InitAttribute.DEFENSE);
    }

    /**
     * 超限合金胃属性修饰符
     */
    public static void transcendiumStomachModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.DIGESTION);
    }

    /**
     * 超限合金肠子属性修饰符
     */
    public static void transcendiumIntestineModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.NUTRITION);
    }

    /**
     * 超限合金肾脏属性修饰符
     */
    public static void transcendiumKidneyModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.FILTRATION);
    }

    /**
     * 超限合金脾脏属性修饰符
     */
    public static void transcendiumSpleenModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.METABOLISM);
    }

    /**
     * 超限合金肝脏属性修饰符
     */
    public static void transcendiumLiverModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.DETOXIFICATION);
    }

    /**
     * 超限合金阑尾属性修饰符
     */
    public static void transcendiumAppendixModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        transcendiumBaseModifier(context, modifiers, Attributes.LUCK);
    }

    /**
     * 超限合金肋骨属性修饰符
     */
    public static void transcendiumRibModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.DEFENSE);
    }

    /**
     * 超限合金肌肉属性修饰符
     */
    public static void transcendiumMuscleModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        transcendiumBaseModifier(context, modifiers, InitAttribute.STRENGTH, InitAttribute.SPEED);
    }

    /**
     * 电磁炮伤害乘数：(1 + 机械器官数 × 0.15)，超频时 ×2
     */
    public static float getRailgunDamageMultiplier(ChestCavitySlotContext context) {
        int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
        float multiplier = 1 + mechanicalCount * 0.15f;
        if (WAICOrganUtil.isOverloadMode(context.entity())) {
            multiplier *= 2;
        }
        return multiplier;
    }

    /**
     * 电磁炮 Mob AI 技能入口 — 固定使用铁粒
     */
    public static boolean railgunGoal(GoalCombatContext combatContext, ChestCavitySlotContext context) {
        return fireRailgun(context, Items.IRON_NUGGET.getDefaultInstance());
    }

    /**
     * 电磁炮发射底层逻辑 — 消耗电荷、计算伤害、生成投射物、播放音效
     */
    public static boolean fireRailgun(ChestCavitySlotContext context, ItemStack ammoStack) {
        LivingEntity entity = context.entity();
        ChestCavityData data = context.data();

        float available = WAICOrganUtil.consumeCharge(data, entity, 100, true);
        if (available < 100) return false;
        WAICOrganUtil.consumeCharge(data, entity, 100, false);

        float baseDamage = RailgunAmmoManager.getBaseDamage(ammoStack);
        float finalDamage = baseDamage * getRailgunDamageMultiplier(context);

        RailgunProjectileEntity projectile = RailgunProjectileEntity.create(entity.level(), entity, ammoStack, finalDamage);
        projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 4.0f, 1.0f);
        entity.level().addFreshEntity(projectile);

        entity.level()
            .playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.FIREWORK_ROCKET_BLAST,
                entity.getSoundSource(),
                1.0f,
                1.5f
            );
        return true;
    }
}

package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

    // ==================== 余烬金属器官 ====================

    /**
     * 判断实体是否身处火源环境（岩浆、火方块、已点燃营火、岩浆块、着火状态）
     */
    public static boolean isInFireSource(LivingEntity entity) {
        if (entity.isInLava() || entity.isOnFire()) return true;
        AABB box = entity.getBoundingBox();
        BlockPos minPos = BlockPos.containing(box.minX, box.minY, box.minZ).below();
        BlockPos maxPos = BlockPos.containing(box.maxX, box.maxY, box.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            BlockState state = entity.level().getBlockState(pos);
            if (state.is(BlockTags.FIRE)) return true;
            if (state.is(BlockTags.CAMPFIRES) && state.getValue(CampfireBlock.LIT)) return true;
            if (state.is(Blocks.MAGMA_BLOCK)) return true;
        }
        return false;
    }

    // ==================== 诅咒金器官 ====================

    /**
     * 更新单种诅咒惩罚效果
     * <p>
     * 等级降低时仅移除确认由诅咒金施加的效果（等级匹配且时长不超过诅咒金施加时长），保护外部更高等级或更长时长的同种来源
     * </p>
     *
     * @param threshold 该效果的触发阈值
     * @param step      每多少个器官提升1级
     * @param duration  诅咒金施加的效果时长
     */
    private static void updateCursedEffect(
        LivingEntity entity,
        Holder<MobEffect> effect,
        int oldCursedCount,
        int newCursedCount,
        int threshold,
        int step,
        int duration
    ) {
        int oldAmplifier = oldCursedCount >= threshold ? (oldCursedCount - threshold) / step : -1;
        int newAmplifier = newCursedCount >= threshold ? (newCursedCount - threshold) / step : -1;
        if (oldAmplifier > newAmplifier) {
            MobEffectInstance currentEffect = entity.getEffect(effect);
            if (currentEffect != null
                && currentEffect.getAmplifier() == oldAmplifier
                && currentEffect.getDuration() <= duration) {
                entity.removeEffect(effect);
            }
        }
        if (newAmplifier >= 0) {
            entity.addEffect(new MobEffectInstance(effect, duration, newAmplifier, false, false, true));
        }
    }

    /**
     * 根据诅咒器官数量施加诅咒惩罚效果（饥饿/缓慢/虚弱），等级阶梯式递增
     */
    public static void addCursedGoldEffects(LivingEntity entity, int cursedCount, int duration) {
        updateCursedEffect(entity, MobEffects.HUNGER, 0, cursedCount, 1, 2, duration);
        updateCursedEffect(entity, MobEffects.MOVEMENT_SLOWDOWN, 0, cursedCount, 3, 3, duration);
        updateCursedEffect(entity, MobEffects.WEAKNESS, 0, cursedCount, 5, 4, duration);
    }

    /**
     * 器官变更时根据诅咒器官数量变化专项更新诅咒惩罚效果
     * <p>
     * 仅对等级降低的效果执行 removeEffect，且只移除诅咒金自身施加的效果，保护外部来源
     * </p>
     */
    public static void applyCursedGoldEffects(LivingEntity entity, int cursedCount, ItemStack oldStack, ItemStack newStack, int duration) {
        int oldCursedCount = cursedCount
                             + (oldStack.is(WAICItemTagManager.CURSED) ? 1 : 0)
                             - (newStack.is(WAICItemTagManager.CURSED) ? 1 : 0);
        updateCursedEffect(entity, MobEffects.HUNGER, oldCursedCount, cursedCount, 1, 2, duration);
        updateCursedEffect(entity, MobEffects.MOVEMENT_SLOWDOWN, oldCursedCount, cursedCount, 3, 3, duration);
        updateCursedEffect(entity, MobEffects.WEAKNESS, oldCursedCount, cursedCount, 5, 4, duration);
    }

    // ==================== 电磁炮 ====================

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
        float yaw = entity instanceof Mob mob ? mob.getYHeadRot() : entity.getYRot();
        projectile.shootFromRotation(entity, entity.getXRot(), yaw, 0, 4.0f, 1.0f);
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

package net.zhaiji.who_am_i_core.entity;

import io.redspace.ironsspellbooks.entity.spells.poison_breath.PoisonBreathProjectile;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.zhaiji.who_am_i_core.register.WAICEntity;

/**
 * 九头蛇毒物吐息投射物
 * <p>
 * 释放绿色毒雾吐息，对锥形范围内的敌人造成伤害并施加中毒效果
 * </p>
 */
public class HydraVenomBreathProjectile extends PoisonBreathProjectile {

    /**
     * 原始中毒效果时长（施加给敌人）
     */
    private final int poisonDuration;

    /**
     * 原始中毒效果等级（施加给敌人）
     */
    private final int poisonAmplifier;

    /**
     * 伤害（每4 tick造成）
     */
    private final float damage;

    public HydraVenomBreathProjectile(EntityType<? extends HydraVenomBreathProjectile> entityType, Level level) {
        super(entityType, level);
        this.poisonDuration = 0;
        this.poisonAmplifier = 0;
        this.damage = 0;
    }

    /**
     * 构造函数 - 创建九头蛇毒物吐息投射物
     *
     * @param level           世界
     * @param owner           投射物所有者
     * @param poisonDuration  原始中毒效果时长（施加给敌人）
     * @param poisonAmplifier 原始中毒效果等级（施加给敌人）
     */
    public HydraVenomBreathProjectile(Level level, LivingEntity owner, int poisonDuration, int poisonAmplifier) {
        super(WAICEntity.HYDRA_VENOM_BREATH.get(), level);
        this.setOwner(owner);
        this.poisonDuration = poisonDuration;
        this.poisonAmplifier = poisonAmplifier;
        // 伤害 = 中毒等级 + 1
        this.damage = poisonAmplifier + 1;
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            // 造成伤害
            Registry<DamageType> damageTypes = this.level().damageSources().damageTypes;
            Holder.Reference<DamageType> damageType = damageTypes.getHolder(NeoForgeMod.POISON_DAMAGE)
                .orElse(damageTypes.getHolderOrThrow(DamageTypes.MAGIC));
            if (livingEntity.hurt(new DamageSource(damageType), this.damage)) {
                // 施加原始中毒效果
                livingEntity.addEffect(new MobEffectInstance(
                    MobEffects.POISON,
                    this.poisonDuration,
                    this.poisonAmplifier,
                    false,
                    true
                ));
            }
        }
    }
}

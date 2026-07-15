package net.zhaiji.who_am_i_core.entity;

import io.redspace.ironsspellbooks.entity.spells.poison_breath.PoisonBreathProjectile;
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

    private final int poisonDuration;
    private final int poisonAmplifier;

    public HydraVenomBreathProjectile(EntityType<? extends HydraVenomBreathProjectile> entityType, Level level) {
        super(entityType, level);
        this.poisonDuration = 0;
        this.poisonAmplifier = 0;
    }

    public HydraVenomBreathProjectile(Level level, LivingEntity owner, int poisonDuration, int poisonAmplifier) {
        super(WAICEntity.HYDRA_VENOM_BREATH.get(), level);
        this.setOwner(owner);
        this.poisonDuration = poisonDuration;
        this.poisonAmplifier = poisonAmplifier;
        this.setDamage(poisonAmplifier + 1);
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hurt(this.level().damageSources().source(NeoForgeMod.POISON_DAMAGE, this.getOwner()), this.damage)) {
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

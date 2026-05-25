package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.content.entities.chesed_boss.chesed_mini_ray.ChesedMiniRay;
import com.finderfeed.fdbosses.init.BossConfigs;
import com.finderfeed.fdbosses.init.BossEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChesedMiniRay.class)
public abstract class ChesedMiniRayMixin extends Entity {
    @Shadow
    private ItemStack item;

    public ChesedMiniRayMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private ChesedMiniRay whoAmICore$self() {
        return (ChesedMiniRay) (Object) this;
    }

    @Inject(
        method = "hurtTarget",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$hurtTarget(LivingEntity owner, LivingEntity target, CallbackInfo ci) {
        if (!item.is(FDBossesOrgans.CHESED.get())) return;

        // 使用玩家最大生命值 × 33% 作为伤害
        float damage = owner.getMaxHealth() * 0.33F;

        DamageSource damageSource = whoAmICore$self().level().damageSources().mobAttack(owner);

        target.setRemainingFireTicks(0);
        target.invulnerableTime = 0;

        if (target.hurt(damageSource, damage)) {
            int duration = BossConfigs.BOSS_CONFIG.get().itemConfig.lightningStrikeShockDuration;
            target.addEffect(new MobEffectInstance(BossEffects.SHOCKED, duration, 0));
            target.invulnerableTime = 0;
        }

        ci.cancel();
    }
}

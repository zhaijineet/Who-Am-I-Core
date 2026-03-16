package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.effect.PoisonMobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PoisonMobEffect.class)
public abstract class PoisonMobEffectMixin {
    @Inject(
        method = "applyEffectTick",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$applyEffectTick(LivingEntity livingEntity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
        // 九头蛇心脏免疫中毒的伤害
        if (ChestCavityUtil.getData(livingEntity).hasOrgan(IceAndFireOrgans.HYDRA_HEART.get())) {
            cir.setReturnValue(true);
        }
    }
}

package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.zhaiji.who_am_i_core.mixinapi.IHeresyMobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin implements IHeresyMobEffectInstance {
    @Unique
    private boolean whoAmICore$heresyEnhanced;

    @Override
    public boolean isHeresyEnhanced() {
        return whoAmICore$heresyEnhanced;
    }

    @Override
    public void setHeresyEnhanced(boolean heresyEnhanced) {
        whoAmICore$heresyEnhanced = heresyEnhanced;
    }

    @Inject(
        method = "setDetailsFrom",
        at = @At("RETURN")
    )
    public void whoAmICore$setDetailsFrom(MobEffectInstance effectInstance, CallbackInfo ci) {
        whoAmICore$heresyEnhanced = ((IHeresyMobEffectInstance) effectInstance).isHeresyEnhanced();
    }

    @Inject(
        method = "update",
        at = @At("RETURN")
    )
    public void whoAmICore$update(MobEffectInstance other, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && ((IHeresyMobEffectInstance) other).isHeresyEnhanced()) {
            whoAmICore$heresyEnhanced = true;
        }
    }
}

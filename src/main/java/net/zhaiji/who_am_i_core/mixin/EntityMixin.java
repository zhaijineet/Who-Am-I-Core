package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Unique
    private Entity whoAmICore$self() {
        return (Entity)(Object)this;
    }

    @Inject(
        method = "turn(DD)V",
        at = @At("HEAD"),
        cancellable = true
    )
    public void whoAmICore$turn(double yRot, double xRot, CallbackInfo ci) {
        if (whoAmICore$self() instanceof Player player
            && player.getCooldowns().isOnCooldown(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get())) {
            ci.cancel();
        }
    }
}

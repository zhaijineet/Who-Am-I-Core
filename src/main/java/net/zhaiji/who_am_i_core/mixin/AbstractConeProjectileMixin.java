package net.zhaiji.who_am_i_core.mixin;

import io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractConeProjectile.class)
public abstract class AbstractConeProjectileMixin {
    @Redirect(
        method = "rayTrace",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getYRot()F"
        )
    )
    private static float whoAmICore$rayTrace(Entity entity) {
        if (entity instanceof Mob mob) {
            return mob.getYHeadRot();
        }
        return entity.getYRot();
    }

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getYRot()F",
            ordinal = 0
        )
    )
    public float whoAmICore$tick(Entity entity) {
        if (entity instanceof Mob mob) {
            return mob.getYHeadRot();
        }
        return entity.getYRot();
    }
}

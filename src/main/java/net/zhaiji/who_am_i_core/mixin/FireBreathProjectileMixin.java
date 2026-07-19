package net.zhaiji.who_am_i_core.mixin;

import io.redspace.ironsspellbooks.entity.spells.fire_breath.FireBreathProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBreathProjectile.class)
public abstract class FireBreathProjectileMixin {
    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getLookAngle()Lnet/minecraft/world/phys/Vec3;"
        )
    )
    public Vec3 whoAmICore$tick(Entity entity) {
        if (entity instanceof Mob mob) {
            return Vec3.directionFromRotation(mob.getXRot(), mob.getYHeadRot());
        }
        return entity.getLookAngle();
    }

    @Redirect(
        method = "spawnParticles",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getLookAngle()Lnet/minecraft/world/phys/Vec3;"
        )
    )
    public Vec3 whoAmICore$spawnParticles(Entity entity) {
        if (entity instanceof Mob mob) {
            return Vec3.directionFromRotation(mob.getXRot(), mob.getYHeadRot());
        }
        return entity.getLookAngle();
    }
}

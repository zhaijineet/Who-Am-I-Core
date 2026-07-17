package net.zhaiji.who_am_i_core.mixin;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 让沙暴龙卷风跟随任意 LivingEntity 施法者
 */
@Mixin(Sandstorm_Entity.class)
public abstract class SandstormEntityMixin extends Entity {
    public SandstormEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    @Nullable
    public abstract LivingEntity getCaster();

    @Shadow
    public abstract float getOffset();

    @Inject(
        method = "updateMotion",
        at = @At("RETURN")
    )
    public void whoAmICore$updateMotion(CallbackInfo ci) {
        LivingEntity owner = getCaster();
        if (owner == null || owner instanceof Player || owner instanceof Ancient_Remnant_Entity) {
            return;
        }
        Vec3 center = owner.position();
        float radius = 6;
        float speed = tickCount * 0.04F;
        float offset = getOffset();
        Vec3 orbit = new Vec3(
            center.x + Math.cos(speed + offset) * radius,
            center.y,
            center.z + Math.sin(speed + offset) * radius
        );
        moveTo(orbit);
    }
}

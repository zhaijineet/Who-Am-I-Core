package net.zhaiji.who_am_i_core.mixin;

import com.bobmowzie.mowziesmobs.server.damage.DamageUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DamageUtil.class)
public abstract class DamageUtilMixin {
    /**
     * 修复祖传代码导致的动画播放bug（和铁砧工艺冲突了））
     */
    @Redirect(
        method = "onHit2",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V"
        )
    )
    private static void whoAmICore$onHit2(Level instance, Entity entity, byte state) {
    }
}

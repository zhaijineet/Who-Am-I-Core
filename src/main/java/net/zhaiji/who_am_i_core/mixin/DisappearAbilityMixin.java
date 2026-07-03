package net.zhaiji.who_am_i_core.mixin;

import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animation.RawAnimation;

/**
 * 通臂大师消失时掉落禅心
 */
@Mixin(EntitySculptor.DisappearAbility.class)
public abstract class DisappearAbilityMixin extends SimpleAnimationAbility<EntitySculptor> {
    public DisappearAbilityMixin(
        AbilityType<EntitySculptor, ? extends SimpleAnimationAbility<EntitySculptor>> abilityType,
        EntitySculptor user,
        RawAnimation animation,
        int duration
    ) {
        super(abilityType, user, animation, duration);
    }

    @Inject(
        method = "tickUsing",
        at = @At("RETURN")
    )
    public void whoAmICore$tickUsing(CallbackInfo ci) {
        // DisappearAbility 动画在第 60 tick 完成消失
        if (getTicksInUse() == 60) {
            EntitySculptor sculptor = getUser();
            sculptor.spawnAtLocation(MowziesMobOrgans.ZEN_HEART.get().getDefaultInstance(), 1.2F);
        }
    }
}

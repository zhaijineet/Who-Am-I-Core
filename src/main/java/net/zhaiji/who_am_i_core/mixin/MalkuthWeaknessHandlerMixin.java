package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthAttackType;
import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthWeaknessHandler;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MalkuthWeaknessHandler.class)
public class MalkuthWeaknessHandlerMixin {
    @Inject(
        method = "isWeakTo",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void whoAmICore$isWeakTo(
        Player player, MalkuthAttackType malkuthAttackType,
        CallbackInfoReturnable<Boolean> cir
    ) {
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (malkuthAttackType.isFire() && data.hasOrgan(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get())) {
            cir.setReturnValue(false);
        } else if (malkuthAttackType.isIce() && data.hasOrgan(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get())) {
            cir.setReturnValue(false);
        }
    }
}

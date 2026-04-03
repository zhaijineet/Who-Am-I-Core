package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthAttackType;
import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthWeaknessHandler;
import com.finderfeed.fdbosses.content.entities.malkuth_boss.malkuth_cannon.MalkuthCannonEntity;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MalkuthCannonEntity.class)
public class MalkuthCannonEntityMixin {

    /**
     * 允许拥有对应免疫心脏的玩家使用火炮。
     * <p>
     * 原版逻辑：{@code isWeakTo(player, cannonType)} → 玩家弱点方向匹配火炮类型才能开炮。
     * 但 {@link MalkuthWeaknessHandlerMixin} 会让免疫方向的 isWeakTo 返回 false，
     * 导致双免疫玩家两门炮都不能开。
     * </p>
     * <p>
     * 此 Redirect 在火炮交互处将条件扩展为：
     * isWeakTo(player, cannonType) || 拥有对应方向的免疫心脏
     * </p>
     */
    @Redirect(
        method = "interactAt",
        at = @At(
            value = "INVOKE",
            target = "Lcom/finderfeed/fdbosses/content/entities/malkuth_boss/MalkuthWeaknessHandler;isWeakTo(Lnet/minecraft/world/entity/player/Player;Lcom/finderfeed/fdbosses/content/entities/malkuth_boss/MalkuthAttackType;)Z"
        )
    )
    private boolean whoAmICore$allowImmuneToUseCannon(Player player, MalkuthAttackType attackType) {
        if (MalkuthWeaknessHandler.isWeakTo(player, attackType)) return true;
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (attackType.isFire() && data.hasOrgan(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get())) return true;
        return attackType.isIce() && data.hasOrgan(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get());
    }
}

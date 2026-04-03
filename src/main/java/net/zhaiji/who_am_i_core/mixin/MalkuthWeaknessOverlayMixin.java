package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.client.overlay.MalkuthWeaknessOverlay;
import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthAttackType;
import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthWeaknessHandler;
import com.finderfeed.fdlib.FDClientHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MalkuthWeaknessOverlay.class)
public abstract class MalkuthWeaknessOverlayMixin {
    /**
     * 免疫状态标记：tickClient 中设置，render 中使用。
     */
    @Unique
    private static boolean isImmune = false;

    /**
     * 在 tickClient 末尾检测免疫状态并设置标记。
     * 不修改任何 ticker，让原版的底部横条、屏幕边缘粒子全部正常显示颜色。
     */
    @Inject(
        method = "tickClient",
        at = @At("TAIL")
    )
    private static void whoAmICore$checkImmuneState(ClientTickEvent.Pre event, CallbackInfo ci) {
        Player player = FDClientHelpers.getClientPlayer();
        if (player == null) {
            isImmune = false;
            return;
        }
        ChestCavityData data = ChestCavityUtil.getData(player);
        boolean hasFire = data.hasOrgan(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get());
        boolean hasIce = data.hasOrgan(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get());
        if (!hasFire && !hasIce) {
            isImmune = false;
            return;
        }
        MalkuthAttackType weakTo = MalkuthWeaknessHandler.getWeakTo(player);
        boolean fireImmune = hasFire && weakTo.isFire();
        boolean iceImmune = hasIce && weakTo.isIce();
        isImmune = hasFire && hasIce || fireImmune || iceImmune;
    }

    @Shadow
    public static void drawCircle(
        Vector3f c1,
        Vector3f c2,
        PoseStack matrices,
        float radius,
        float innerRadius,
        float a,
        int renderAmount
    ) {
    }

    /**
     * 将基础圆环颜色从黑色(0,0,0)改为白色(1,1,1)。
     * 免疫时冰/火圆环被 Redirect 跳过，只剩下这个白色基础圆环。
     */
    @Redirect(
        method = "render",
        at = @At(
            value = "NEW",
            target = "()Lorg/joml/Vector3f;"
        )
    )
    public Vector3f whoAmICore$whiteBaseRing() {
        return new Vector3f(1.0f, 1.0f, 1.0f);
    }

    /**
     * 拦截 drawCircle 调用，当免疫时将冰/火圆环的 alpha 强制设为 0。
     * 基础圆环 renderAmount=1，冰/火圆环 renderAmount=2 或 3。
     * 只有冰/火圆环（renderAmount >= 2）被屏蔽，基础圆环保留。
     * 底部横条和屏幕边缘粒子不受影响（它们在 drawCircle 调用之前已经绘制/生成）。
     */
    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lcom/finderfeed/fdbosses/client/overlay/MalkuthWeaknessOverlay;drawCircle(Lorg/joml/Vector3f;Lorg/joml/Vector3f;Lcom/mojang/blaze3d/vertex/PoseStack;FFFI)V"
        )
    )
    public void whoAmICore$hideColoredCircles(
        Vector3f c1,
        Vector3f c2,
        PoseStack matrices,
        float radius,
        float innerRadius,
        float a,
        int renderAmount
    ) {
        if (isImmune && renderAmount >= 2) {
            // 免疫时跳过冰/火圆环绘制
            return;
        }
        drawCircle(c1, c2, matrices, radius, innerRadius, a, renderAmount);
    }
}

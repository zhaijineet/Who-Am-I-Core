package net.zhaiji.who_am_i_core.client.event;

import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.register.WAICEntity;

public class ClientEventHandler {
    /**
     * 处理FOV（视野）修饰符计算事件
     * 当玩家拥有护心镜器官时，免疫所有FOV改变效果
     *
     * @param event FOV修饰符计算事件
     */
    public static void handlerComputeFovModifierEvent(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        // 检查玩家是否有护心镜器官
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (data != null && data.hasOrgan(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get())) {
            // 将 FOV 修饰系数设置为 1.0（正常），免疫所有 FOV 改变
            event.setNewFovModifier(1.0F);
        }
    }

    /**
     * 处理无法移动，优先级为低
     *
     * @param event 移动输入更新事件
     */
    public static void handlerMovementInputUpdateEvent(MovementInputUpdateEvent event) {
        Player player = event.getEntity();
        // 只影响非乘坐情况
        if (player.getCooldowns().isOnCooldown(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get()) && !event.getEntity()
            .isPassenger()) {
            Input input = event.getInput();
            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
            input.jumping = false;
            input.forwardImpulse = 0;
            input.leftImpulse = 0;
        }
    }

    /**
     * @param event 实体渲染注册
     */
    public static void handlerEntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(WAICEntity.HYDRA_VENOM_BREATH.get(), NoopRenderer::new);
    }
}

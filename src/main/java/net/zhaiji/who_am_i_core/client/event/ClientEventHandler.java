package net.zhaiji.who_am_i_core.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.client.tooltip.ClientFrankensteinHeartTooltip;
import net.zhaiji.who_am_i_core.inventory.tooltip.FrankensteinHeartTooltip;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.register.WAICEffect;
import net.zhaiji.who_am_i_core.register.WAICEntity;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;

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

    /**
     * 注册自定义 tooltip 组件渲染器
     */
    public static void handlerRegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FrankensteinHeartTooltip.class, tooltip -> new ClientFrankensteinHeartTooltip(tooltip.contents()));
    }


    /**
     * 在客户端设置阶段注册弗兰肯斯坦心脏的 ItemProperty（贴图切换）
     * 此时所有注册表已完成，可以安全地获取物品实例
     */
    public static void handlerFMLClientSetupEvent(FMLClientSetupEvent event) {
        ItemProperties.register(
            WAICOrgans.FRANKENSTEIN_HEART.get(),
            WhoAmICore.of("frankenstein_heart_active"),
            (stack, level, entity, seed) -> {
                if (entity instanceof LivingEntity living && WAICOrganUtil.isInChest(living, stack)) {
                    return (
                               living.hasEffect(WAICEffect.FIRE_DRAGON_POWER)
                               || living.hasEffect(WAICEffect.ICE_DRAGON_POWER)
                               || living.hasEffect(WAICEffect.LIGHTNING_DRAGON_POWER)
                           ) ? 1.0F : 0.0F;
                }
                return 0.0F;
            }
        );

        ItemProperties.register(
            WAICOrgans.FRANKENSTEIN_HEART.get(),
            WhoAmICore.of("frankenstein_heart_super_active"),
            (stack, level, entity, seed) -> {
                if (entity instanceof LivingEntity living && WAICOrganUtil.isInChest(living, stack)) {
                    return
                        living.hasEffect(WAICEffect.FIRE_DRAGON_POWER) &&
                        living.hasEffect(WAICEffect.ICE_DRAGON_POWER) &&
                        living.hasEffect(WAICEffect.LIGHTNING_DRAGON_POWER) ||
                        living.hasEffect(WAICEffect.DRAGON_POWER) ? 1.0F : 0.0F;
                }
                return 0.0F;
            }
        );
    }

}

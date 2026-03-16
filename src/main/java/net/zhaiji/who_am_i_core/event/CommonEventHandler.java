package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.iafenvoy.iceandfire.registry.IafEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganChangeEvent;
import net.zhaiji.chestcavitybeyond.api.event.RegisterChestCavityEvent;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.IceAndFireChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;
import net.zhaiji.who_am_i_core.task.HydraSpleenTask;
import net.zhaiji.who_am_i_core.util.IceAndFireOrganhUtil;
import org.jetbrains.annotations.Nullable;

public class CommonEventHandler {
    public static void handlerFMLCommonSetupEvent(FMLCommonSetupEvent event) {
        IceAndFireOrgans.setupOrgans();
    }

    /**
     * 为所有实体添加默认初始化器官属性
     *
     * @param event 实体属性初始化事件
     */
    public static void handlerEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entityType -> {
            WAICAttribute.ATTRIBUTE.getEntries().forEach(attribute -> {
                event.add(entityType, attribute);
            });
        });
    }

    /**
     * 注册可序列化的任务类型
     */
    public static void handlerRegisterChestCavityEvent(RegisterChestCavityEvent event) {
        event.registerTask(ChestNovaTask.TYPE, ChestNovaTask::new);
        event.registerTask(HydraSpleenTask.TYPE, HydraSpleenTask::new);

        // 注册龙类胸腔
        event.registerEntity(IafEntities.FIRE_DRAGON.get(), IceAndFireChestCavityTypeManager.FIRE_DRAGON);
        event.registerEntity(IafEntities.ICE_DRAGON.get(), IceAndFireChestCavityTypeManager.ICE_DRAGON);
        event.registerEntity(IafEntities.LIGHTNING_DRAGON.get(), IceAndFireChestCavityTypeManager.LIGHTNING_DRAGON);

        // 注册幻想种和九头蛇胸腔
        event.registerEntity(IafEntities.PIXIE.get(), WAICChestCavityTypeManager.FANTASTICAL);
        event.registerEntity(IafEntities.HYDRA.get(), IceAndFireChestCavityTypeManager.HYDRA);
    }

    /**
     * 当胸腔中的器官被移除时，检查是否是乌姆塔纳面具
     * 如果是，通知对应的任务移除召唤的生物
     */
    public static void handlerOrganChangeEvent(OrganChangeEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        // 检查被移除的是否是乌姆塔纳面具
        if (!(event.getOldStack().getItem() instanceof ItemUmvuthanaMask)) return;
        // 通知 ChestNovaTask 移除对应槽位的生物
        for (IChestCavityTask task : event.getData().getTasks()) {
            if (task instanceof ChestNovaTask umvuthanaTask) {
                umvuthanaTask.onMaskRemoved(event.getIndex());
                // 应当有且只有一个task，提前返回
                break;
            }
        }
    }

    /**
     * 九头蛇脊柱复活机制
     * 当实体死亡时，如果具有超过10秒的中毒效果，则恢复至10%血量并取消死亡
     * 同时提升中毒等级并折半时间
     * TODO 没有复活提示，考虑加不死图腾音效
     */
    public static void handlerLivingDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 九头蛇脊柱复活技能
        if (IceAndFireOrganhUtil.hydraSpineSkill(entity, data)) {
            event.setCanceled(true);
            return;
        }
    }

    /**
     * @param event 实体将要受伤事件
     */
    public static void handlerLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 九头蛇心脏免疫中毒类型的伤害
        if (data.hasOrgan(IceAndFireOrgans.HYDRA_HEART.get()) && event.getSource().is(NeoForgeMod.POISON_DAMAGE)) {
            event.setCanceled(true);
            return;
        }
    }

    /**
     * @param event 实体受伤前事件
     */
    public static void handlerLivingDamageEvent$Pre(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        double blockValue = entity.getAttributeValue(WAICAttribute.BLOCK);
        // 应用格挡属性减伤
        if (blockValue > 0) {
            // 计算减伤后的伤害
            float reducedDamage = Math.max(0, event.getNewDamage() - (float) blockValue);
            event.setNewDamage(reducedDamage);
        }
        DamageSource damageSource = event.getSource();
        LivingEntity attacker = damageSource.getDirectEntity() instanceof LivingEntity attackerEntity
                                          ? attackerEntity
                                          : null;
        // 九头蛇肋骨效果
        float damageToReduce = IceAndFireOrganhUtil.hydraRibSkill(entity, attacker);
        if (damageToReduce > 0) {
            event.setNewDamage(Math.max(0, event.getNewDamage() - damageToReduce));
        }

        // 九头蛇肌肉效果
        if (attacker != null) {
            float extraDamage = IceAndFireOrganhUtil.hydraMuscleSkill(attacker, entity);
            if (extraDamage > 0) {
                event.setNewDamage(event.getNewDamage() + extraDamage);
            }
        }
    }
}

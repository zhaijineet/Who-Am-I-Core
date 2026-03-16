package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.iafenvoy.iceandfire.registry.IafEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
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
        // 九头蛇脊柱复活机制
        if (data.hasOrgan(IceAndFireOrgans.HYDRA_SPINE.get())) {
            MobEffectInstance poisonEffect = entity.getEffect(MobEffects.POISON);
            if (poisonEffect == null || poisonEffect.getDuration() < 200) return;
            // 回复10%血量
            entity.setHealth(entity.getMaxHealth() * 0.1F);
            // 提升中毒等级（最高5级）
            int currentAmplifier = poisonEffect.getAmplifier();
            // 如果等级本身大于5级，就保留原等级
            int newAmplifier = Math.min(currentAmplifier + 1, Math.max(currentAmplifier, 4));
            int newDuration = poisonEffect.getDuration() / 2;
            // 移除旧中毒效果，添加新效果
            // 必须先移除，否则当新效果结束后会恢复旧效果
            entity.removeEffect(MobEffects.POISON);
            entity.addEffect(new MobEffectInstance(
                MobEffects.POISON,
                newDuration,
                newAmplifier
            ));

            // 取消死亡
            event.setCanceled(true);
        }
    }

    /**
     * 九头蛇心脏免疫中毒类型的伤害
     */
    public static void handlerLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 九头蛇心脏免疫中毒类型的伤害
        if (data.hasOrgan(IceAndFireOrgans.HYDRA_HEART.get())) {
            if (event.getSource().is(NeoForgeMod.POISON_DAMAGE)) {
                event.setCanceled(true);
            }
        }
    }

    /**
     * 应用格挡属性减伤
     * 在护甲计算之后，应用格挡属性的等值减伤
     */
    public static void handlerLivingDamageEvent$Pre(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        double blockValue = entity.getAttributeValue(WAICAttribute.BLOCK);
        if (blockValue > 0) {
            // 计算减伤后的伤害
            float reducedDamage = Math.max(0, event.getNewDamage() - (float) blockValue);
            event.setNewDamage(reducedDamage);
        }
        ChestCavityData data = ChestCavityUtil.getData(entity);

        // ============ 九头蛇器官效果处理 ============

        // 九头蛇肋骨效果 - 受伤者处理
        if (data.hasOrgan(IceAndFireOrgans.HYDRA_RIB.get())) {
            MobEffectInstance victimPoison = entity.getEffect(MobEffects.POISON);
            if (victimPoison != null && victimPoison.getDuration() > 0) {
                DamageSource source = event.getSource();
                Entity sourceEntity = source.getEntity();
                if (sourceEntity instanceof LivingEntity attacker) {
                    int poisonAmplifier = victimPoison.getAmplifier();
                    int currentDuration = victimPoison.getDuration();
                    int durationToTransfer = Math.min(100, currentDuration);

                    // 更新自身中毒时长
                    int newDuration = currentDuration - durationToTransfer;
                    if (newDuration <= 0) {
                        entity.removeEffect(MobEffects.POISON);
                    } else {
                        entity.addEffect(new MobEffectInstance(
                            MobEffects.POISON, newDuration, poisonAmplifier,
                            victimPoison.isAmbient(), victimPoison.isVisible(), victimPoison.showIcon()
                        ));
                    }

                    // 将中毒效果施加到攻击者（可叠加）
                    MobEffectInstance poison = attacker.getEffect(MobEffects.POISON);
                    if (poison != null) {
                        attacker.addEffect(new MobEffectInstance(
                            MobEffects.POISON,
                            poison.getDuration() + durationToTransfer,
                            Math.max(poison.getAmplifier(), poisonAmplifier),
                            poison.isAmbient(), poison.isVisible(), poison.showIcon()
                        ));
                    } else {
                        attacker.addEffect(new MobEffectInstance(
                            MobEffects.POISON, durationToTransfer, poisonAmplifier
                        ));
                    }

                    // 抵消等同于中毒等级的伤害
                    float damageToReduce = poisonAmplifier + 1;
                    event.setNewDamage(Math.max(0, event.getNewDamage() - damageToReduce));
                }
            }
        }

        // 九头蛇肌肉效果 - 攻击者处理
        DamageSource damageSource = event.getSource();
        Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity instanceof LivingEntity attacker) {
            ChestCavityData attackerData = ChestCavityUtil.getData(attacker);
            if (attackerData.hasOrgan(IceAndFireOrgans.HYDRA_MUSCLE.get())) {
                MobEffectInstance attackerPoison = attacker.getEffect(MobEffects.POISON);
                if (attackerPoison != null && attackerPoison.getDuration() > 0) {
                    int poisonAmplifier = attackerPoison.getAmplifier();
                    int currentDuration = attackerPoison.getDuration();
                    int durationToTransfer = Math.min(100, currentDuration);
                    // 更新自身中毒时长
                    int newDuration = currentDuration - durationToTransfer;
                    if (newDuration <= 0) {
                        attacker.removeEffect(MobEffects.POISON);
                    } else {
                        attacker.addEffect(new MobEffectInstance(
                            MobEffects.POISON, newDuration, poisonAmplifier,
                            attackerPoison.isAmbient(), attackerPoison.isVisible(), attackerPoison.showIcon()
                        ));
                    }
                    // 将中毒效果施加到受害者（可叠加）
                    MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
                    if (poison != null) {
                        entity.addEffect(new MobEffectInstance(
                            MobEffects.POISON,
                            poison.getDuration() + durationToTransfer,
                            Math.max(poison.getAmplifier(), poisonAmplifier),
                            poison.isAmbient(), poison.isVisible(), poison.showIcon()
                        ));
                    } else {
                        entity.addEffect(new MobEffectInstance(
                            MobEffects.POISON, durationToTransfer, poisonAmplifier,
                            false, true, true
                        ));
                    }
                    // 造成等同于中毒等级的额外伤害
                    float extraDamage = poisonAmplifier + 1;
                    event.setNewDamage(event.getNewDamage() + extraDamage);
                }
            }
        }
    }
}

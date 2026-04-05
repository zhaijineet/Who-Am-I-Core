package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.iafenvoy.iceandfire.registry.IafEntities;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.item.InkItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganChangeEvent;
import net.zhaiji.chestcavitybeyond.api.event.RegisterChestCavityEvent;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.api.EdibleCondition;
import net.zhaiji.who_am_i_core.manager.IceAndFireChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICDamageTagManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;
import net.zhaiji.who_am_i_core.task.HydraSpleenTask;
import net.zhaiji.who_am_i_core.task.StraightIntestineTask;
import net.zhaiji.who_am_i_core.util.IceAndFireOrganhUtil;
import net.zhaiji.who_am_i_core.util.MowziesMobOrganSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;

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
        event.registerTask(StraightIntestineTask.TYPE, StraightIntestineTask::new);

        // 注册可食用条件
        // 泥峭器官可食用泥土
        EdibleCondition.builder()
            .matchesItem(MowziesMobOrganSkillUtil::isDirtItem)
            .matchesEntity(MowziesMobOrganSkillUtil::hasBluffOrgan)
            .onEat(MowziesMobOrganSkillUtil::eatDirt)
            .build();

        // 暴食可以食用任何食物，且食用速度减半
        EdibleCondition.builder()
            .matchesItem(stack -> stack.has(DataComponents.FOOD))
            .matchesEntity(entity -> ChestCavityUtil.getData(entity).hasOrgan(WAICOrgans.GLUTTONY.get()))
            // 不能直接使用stack的getUseDuration，会无限循环
            .useDuration((entity, stack) -> stack.getItem().getUseDuration(stack, entity) / 2)
            .build();

        // 墨水瓶器官可以饮用铁魔法的墨水
        EdibleCondition.builder()
            .matchesItem(stack -> stack.getItem() instanceof InkItem)
            .matchesEntity(entity -> ChestCavityUtil.getData(entity).hasOrgan(WAICOrgans.INK_BOTTLE.get()))
            .onEat(WAICOrganSkillUtil::drinkInk)
            .drinkAnimation()
            .build();

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
        event.getData().getFirstTaskIf(task -> task instanceof ChestNovaTask)
            .ifPresent(task -> ((ChestNovaTask) task).onMaskRemoved(event.getIndex()));
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
        double block = entity.getAttributeValue(WAICAttribute.BLOCK);
        double extraDamage = 0;
        float damage = event.getNewDamage();
        DamageSource source = event.getSource();
        LivingEntity attacker = source.getEntity() instanceof LivingEntity attackerEntity
                                ? attackerEntity
                                : source.getDirectEntity() instanceof LivingEntity directEntity
                                  ? directEntity
                                  : null;
        // 反击属性处理
        double counterAttack = entity.getAttributeValue(WAICAttribute.COUNTER_ATTACK);
        if (counterAttack > 0 && attacker != null) {
            // 防止我反击你反击我的反击
            if (!source.is(DamageTypes.THORNS)) {
                // 对攻击者造成荆棘类型的反击伤害
                attacker.hurt(
                    attacker.level().damageSources().thorns(entity),
                    (float) counterAttack
                );
            }
        }
        // 最终倍率乘数
        double finalMultiplier = 1;
        if (source.is(WAICDamageTagManager.IS_MELEE)) {
            // 近战伤害加伤
            extraDamage += entity.getAttributeValue(WAICAttribute.MELEE_DAMAGE) * WAICOrganUtil.getWeaponDamageMultiplier(entity);
            finalMultiplier = entity.getAttributeValue(WAICAttribute.MELEE_DAMAGE_PERCENTAGE);
        } else if (source.is(Tags.DamageTypes.IS_MAGIC)) {
            // 魔法伤害加伤
            extraDamage += entity.getAttributeValue(WAICAttribute.MAGIC_DAMAGE) * WAICOrganUtil.getWeaponDamageMultiplier(entity);
            finalMultiplier = entity.getAttributeValue(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE);
        } else if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            // 远程伤害加伤
            extraDamage += entity.getAttributeValue(WAICAttribute.RANGED_DAMAGE) * WAICOrganUtil.getWeaponDamageMultiplier(entity);
            finalMultiplier = entity.getAttributeValue(WAICAttribute.RANGED_DAMAGE_PERCENTAGE);
        }
        // 九头蛇肋骨效果（唯一）
        block += IceAndFireOrganhUtil.hydraRibSkill(entity, attacker);
        // 九头蛇肌肉效果（唯一）
        if (attacker != null) extraDamage += IceAndFireOrganhUtil.hydraMuscleSkill(attacker, entity);
        // 应用格挡属性减伤（可为负）,以及加伤
        event.setNewDamage((float) (Math.max(0, damage - block + extraDamage) * finalMultiplier));
    }

    public static void handlerLivingEntityUseItemEvent$Finish(LivingEntityUseItemEvent.Finish event) {
        // TODO 待删除/更改
    }

    /**
     * 调色盘器官：施法时消耗对应颜色染料，增加法术等级
     */
    public static void handlerSpellOnCastEvent(SpellOnCastEvent event) {
        LivingEntity entity = event.getEntity();
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(WAICOrgans.PALETTE.get())) return;
        if (WAICOrganSkillUtil.consumeDyeForSchool(entity, event.getSchoolType())) {
            // 成功消耗染料，增加1级法术等级
            event.setSpellLevel(event.getSpellLevel() + 1);
        }
    }

    /**
     * 病变心脏/肌肉：当效果添加或移除时，重新计算依赖效果的器官属性
     *
     * @param event 效果添加事件
     */
    public static void handlerMobEffectEvent$Added(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 病变心脏依赖 effect 数量
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(WAICOrgans.LESION_HEART.get()) || stack.is(WAICOrgans.LESION_MUSCLE.get())) {
                OrganAttributeUtil.updateSlotOrganAttribute(
                    ChestCavityUtil.createContext(data, entity, i, stack)
                );
            }
        }
    }

    /**
     * 病变心脏/肌肉：当效果移除时，重新计算依赖效果的器官属性
     *
     * @param event 效果移除事件
     */
    public static void handlerMobEffectEvent$Remove(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(WAICOrgans.LESION_HEART.get()) || stack.is(WAICOrgans.LESION_MUSCLE.get())) {
                OrganAttributeUtil.updateSlotOrganAttribute(
                    ChestCavityUtil.createContext(data, entity, i, stack)
                );
            }
        }
    }

    /**
     * 病变心脏/肌肉：当效果过期时，重新计算依赖效果的器官属性
     *
     * @param event 效果过期事件
     */
    public static void handlerMobEffectEvent$Expired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(WAICOrgans.LESION_HEART.get()) || stack.is(WAICOrgans.LESION_MUSCLE.get())) {
                OrganAttributeUtil.updateSlotOrganAttribute(
                    ChestCavityUtil.createContext(data, entity, i, stack)
                );
            }
        }
    }
}

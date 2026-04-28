package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.iafenvoy.iceandfire.registry.IafEntities;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.item.InkItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganChangeEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganRegisterEvent;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.api.EdibleCondition;
import net.zhaiji.who_am_i_core.manager.IceAndFireChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICDamageTagManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;
import net.zhaiji.who_am_i_core.task.HydraSpleenTask;
import net.zhaiji.who_am_i_core.task.StraightIntestineTask;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.register.WAICAttachment;
import net.zhaiji.who_am_i_core.util.IceAndFireOrganUtil;
import net.zhaiji.who_am_i_core.util.IronSpellOrganUtil;
import net.zhaiji.who_am_i_core.util.MowziesMobOrganSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganSkillUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;

public class CommonEventHandler {
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

    public static void handlerOrganRegisterEvent(OrganRegisterEvent event) {
        // 设置冰与火心脏物品为器官
        IceAndFireOrgans.setupOrgans();
    }

    public static void handlerChestCavityRegisterEvent(ChestCavityRegisterEvent event) {
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
        if (IceAndFireOrganUtil.hydraSpineSkill(entity, data)) {
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
        // 余烬金属器官火焰吸收：受到火焰伤害时取消伤害并回复等量生命值
        if (data.hasOrgan(WAICItemTagManager.EMBER_METAL) && event.getSource().is(DamageTypeTags.IS_FIRE)) {
            entity.heal(event.getAmount());
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
        block += IceAndFireOrganUtil.hydraRibSkill(entity, attacker);
        // 九头蛇肌肉效果（唯一）
        if (attacker != null) extraDamage += IceAndFireOrganUtil.hydraMuscleSkill(attacker, entity);
        // 尸王脊柱效果（唯一）
        block += IronSpellOrganUtil.deadKingSpineSkill(entity, damage);
        // 应用格挡属性减伤（可为负）,以及加伤
        event.setNewDamage((float) (Math.max(0, damage - block + extraDamage) * finalMultiplier));
    }

    /**
     * 调色盘：施法时消耗对应颜色染料，增加法术等级
     * 腐败魂灯：猩红法术消耗黑胆汁增级
     */
    public static void handlerSpellOnCastEvent(SpellOnCastEvent event) {
        LivingEntity entity = event.getEntity();
        ChestCavityData data = ChestCavityUtil.getData(entity);

        // 调色盘：消耗染料增级
        if (data.hasOrgan(WAICOrgans.PALETTE.get())) {
            if (WAICOrganSkillUtil.consumeDyeForSchool(entity, event.getSchoolType())) {
                event.setSpellLevel(event.getSpellLevel() + 1);
            }
        }

        // 腐败魂灯：猩红法术消耗黑胆汁增级
        if (data.hasOrgan(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get())) {
            if (event.getSchoolType() == SchoolRegistry.BLOOD.get()) {
                HumoursData humours = entity.getData(WAICAttachment.HUMOURS);
                if (humours.extractBlackBile(10, false) >= 10) {
                    event.setSpellLevel(event.getSpellLevel() + 2);
                }
            }
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

    /**
     * 经验之心：从经验球获取的经验 ×（胸腔中魔法器官数量 + 1）倍率
     */
    public static void handlerPlayerXpPickup(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.EXPERIENCE_HEART.get())) return;
        int magicCount = data.getOrganCount(WAICItemTagManager.MAGIC);
        int multiplier = magicCount + 1;
        ExperienceOrb orb = event.getOrb();
        orb.value = orb.value * multiplier;
    }

    /**
     * 经验之心：当玩家等级变化时，更新经验之心的健康值属性
     */
    public static void handlerPlayerLevelChange(PlayerXpEvent.LevelChange event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(player);
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (stack.isEmpty()) continue;
            if (stack.is(WAICOrgans.EXPERIENCE_HEART.get())) {
                OrganAttributeUtil.updateSlotOrganAttribute(ChestCavityUtil.createContext(data, player, i, stack));
            }
        }
    }
}

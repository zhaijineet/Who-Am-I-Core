package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.github.tartaricacid.touhoulittlemaid.api.event.MaidAndItemTransformEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import com.iafenvoy.iceandfire.entity.MultipartPartEntity;
import dev.xylonity.companions.common.entity.companion.TeddyEntity;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.item.InkItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganChangeEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganRegisterEvent;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.manager.AttributeDisplayManager;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.api.UseCondition;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.item.DragonBloodPreparationItem;
import net.zhaiji.who_am_i_core.item.ExistenceDisplacerItem;
import net.zhaiji.who_am_i_core.manager.CataclysmChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.CompanionsChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.FDBossesChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.IceAndFireChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.IronSpellChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.MowziesMobChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICDamageTagManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.mixin.AttachmentHolderAccessor;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;
import net.zhaiji.who_am_i_core.task.StraightIntestineTask;
import net.zhaiji.who_am_i_core.util.CataclysmOrganUtil;
import net.zhaiji.who_am_i_core.util.CompanionsOrganUtil;
import net.zhaiji.who_am_i_core.util.IceAndFireOrganUtil;
import net.zhaiji.who_am_i_core.util.IronSpellOrganUtil;
import net.zhaiji.who_am_i_core.util.MowziesMobOrganUtil;
import net.zhaiji.who_am_i_core.util.OrganUtil;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;

import java.util.UUID;
import java.util.function.DoublePredicate;

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
        event.registerTask(StraightIntestineTask.TYPE, StraightIntestineTask::new);

        // 注册 Ice and Fire 多碰撞箱子部件解析器
        // IaF 的 MultipartPartEntity（龙、海蟒、九头蛇、独眼巨人的子部件）不继承 NeoForge PartEntity，
        event.registerTargetResolver(entity -> {
            if (entity instanceof MultipartPartEntity part) {
                UUID parentId = part.getParentId();
                if (parentId == null) return null;
                Level level = entity.level();
                // 服务端
                if (level instanceof ServerLevel serverLevel) {
                    Entity parent = serverLevel.getEntity(parentId);
                    return parent instanceof LivingEntity livingEntity ? livingEntity : null;
                }
                // 客户端：父子实体位置相邻，遍历附近实体匹配 UUID
                AABB searchBox = entity.getBoundingBox().inflate(16);
                for (Entity nearbyEntity : level.getEntities(entity, searchBox, targetEntity -> true)) {
                    if (parentId.equals(nearbyEntity.getUUID()) && nearbyEntity instanceof LivingEntity livingEntity) {
                        return livingEntity;
                    }
                }
            }
            return null;
        });

        // 注册可食用条件
        // 泥峭器官可食用泥土
        UseCondition.builder()
            .matchesItem(MowziesMobOrganUtil::isDirtItem)
            .matchesEntity(MowziesMobOrganUtil::hasBluffOrgan)
            .eatAnimation()
            .onFinishUsingItem((entity, stack, condition) -> MowziesMobOrganUtil.eatDirt(entity, stack))
            .build();

        // 暴食可以食用任何食物，且食用速度减半
        // TODO 暴食 forceStartUsingItemWhen 绕过模组食物整个 Item.use，后续重构为仅绕过 canEat
        UseCondition.builder()
            .priority(-100)
            .matchesItem(stack -> stack.has(DataComponents.FOOD))
            .matchesEntity(entity -> ChestCavityUtil.getData(entity).hasOrgan(WAICOrgans.GLUTTONY.get()))
            // 不能直接使用stack的getUseDuration，会无限循环
            .onFinishUsingItem((entity, stack, useCondition) -> stack.getItem().finishUsingItem(stack, entity.level(), entity))
            .useDuration((entity, stack) -> stack.getItem().getUseDuration(stack, entity) / 2)
            .forceStartUsingItemWhen((entity, stack) -> ChestCavityUtil.getData(entity).getOrganCount(WAICItemTagManager.NINE_HELL) >= 3)
            .build();

        // 墨水瓶器官可以饮用铁魔法的墨水
        UseCondition.builder()
            .matchesItem(stack -> stack.getItem() instanceof InkItem)
            .matchesEntity(entity -> ChestCavityUtil.getData(entity).hasOrgan(WAICOrgans.INK_BOTTLE.get()))
            .onFinishUsingItem(WAICOrganUtil::drinkInk)
            .drinkAnimation()
            .build();

        // 巨兽熔炉可以饮用岩浆
        UseCondition.builder()
            .matchesItem(stack -> stack.is(Items.LAVA_BUCKET))
            .matchesEntity(entity -> ChestCavityUtil.getData(entity)
                                         .hasOrgan(CataclysmOrgans.MONSTROSITY_FURNACE.get()) && !entity.isShiftKeyDown())
            .onFinishUsingItem(CataclysmOrganUtil::drinkLava)
            .drinkAnimation()
            .build();

        // 各mod注册胸腔类型以及对应实体注册胸腔类型
        IceAndFireChestCavityTypeManager.registerEntities(event);
        MowziesMobChestCavityTypeManager.registerEntities(event);
        FDBossesChestCavityTypeManager.registerEntities(event);
        CataclysmChestCavityTypeManager.registerEntities(event);
        IronSpellChestCavityTypeManager.registerEntities(event);
        CompanionsChestCavityTypeManager.registerEntities(event);

        // Touhou Little Maid
        event.registerEntity(InitEntities.MAID.get(), WAICChestCavityTypeManager.FANTASTICAL);
        event.registerEntity(InitEntities.FAIRY.get(), WAICChestCavityTypeManager.FANTASTICAL);

        // 注册属性显示信息到 CCB 属性查询系统（带动态效果描述）

        // 值 ≤ 0 时隐藏（适用于"需要 > 0 才生效"的属性）
        DoublePredicate HIDE_WHEN_NOT_POSITIVE = value -> value <= 0;

        // HEAL — 每秒回复等同属性值的生命值
        AttributeDisplayManager.register(
            WAICAttribute.HEAL, 40, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.HEAL);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.HEAL),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // BLOCK — 等值减伤
        AttributeDisplayManager.register(
            WAICAttribute.BLOCK, 30, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.BLOCK);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.BLOCK),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // COUNTER_ATTACK — 受击反击
        AttributeDisplayManager.register(
            WAICAttribute.COUNTER_ATTACK, 30, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.COUNTER_ATTACK);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.COUNTER_ATTACK),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // MELEE_DAMAGE — 近战加伤
        AttributeDisplayManager.register(
            WAICAttribute.MELEE_DAMAGE, 30, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.MELEE_DAMAGE);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.MELEE_DAMAGE),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // RANGED_DAMAGE — 远程加伤
        AttributeDisplayManager.register(
            WAICAttribute.RANGED_DAMAGE, 30, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.RANGED_DAMAGE);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.RANGED_DAMAGE),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // MAGIC_DAMAGE — 魔法加伤
        AttributeDisplayManager.register(
            WAICAttribute.MAGIC_DAMAGE, 30, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.MAGIC_DAMAGE);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.MAGIC_DAMAGE),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // MELEE_DAMAGE_PERCENTAGE — 近战最终倍率
        AttributeDisplayManager.register(
            WAICAttribute.MELEE_DAMAGE_PERCENTAGE, 0, entity -> {
                double current = ChestCavityUtil.getData(entity).getCurrentValue(WAICAttribute.MELEE_DAMAGE_PERCENTAGE);
                double percent = current * 100;
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.MELEE_DAMAGE_PERCENTAGE),
                    TooltipUtil.formatAttributeValue(percent)
                );
            }
        );
        // RANGED_DAMAGE_PERCENTAGE — 远程最终倍率
        AttributeDisplayManager.register(
            WAICAttribute.RANGED_DAMAGE_PERCENTAGE, 0, entity -> {
                double current = ChestCavityUtil.getData(entity).getCurrentValue(WAICAttribute.RANGED_DAMAGE_PERCENTAGE);
                double percent = current * 100;
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.RANGED_DAMAGE_PERCENTAGE),
                    TooltipUtil.formatAttributeValue(percent)
                );
            }
        );
        // MAGIC_DAMAGE_PERCENTAGE — 魔法最终倍率
        AttributeDisplayManager.register(
            WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, 0, entity -> {
                double current = ChestCavityUtil.getData(entity).getCurrentValue(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE);
                double percent = current * 100;
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE),
                    TooltipUtil.formatAttributeValue(percent)
                );
            }
        );
        // LOOTING — 抢夺等级
        AttributeDisplayManager.register(
            WAICAttribute.LOOTING, 0, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.LOOTING);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.LOOTING),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
        // FORTUNE — 时运等级
        AttributeDisplayManager.register(
            WAICAttribute.FORTUNE, 0, HIDE_WHEN_NOT_POSITIVE, entity -> {
                double diff = ChestCavityUtil.getData(entity).getDifferenceValue(WAICAttribute.FORTUNE);
                return Component.translatable(
                    AttributeDisplayManager.getValueEffectKey(WAICAttribute.FORTUNE),
                    TooltipUtil.formatAttributeValue(diff)
                );
            }
        );
    }

    /**
     * @param event 器官更换事件
     */
    public static void handlerOrganChangeEvent(OrganChangeEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        // 乌姆塔纳面具移除通知
        if (event.getOldStack().getItem() instanceof ItemUmvuthanaMask) {
            event.getData().getFirstTaskIf(task -> task instanceof ChestNovaTask)
                .ifPresent(task -> ((ChestNovaTask) task).onMaskRemoved(event.getIndex()));
        }

        // 猩红心脏被动：血液容量 = 猩红器官数量 × 100
        WAICOrganUtil.crimsonHeartOrganChange(event.getData(), event.getEntity(), event.getOldStack(), event.getNewStack());
    }

    /**
     * @param event 实体死亡事件
     */
    public static void handlerLivingDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 九头蛇脊柱复活技能 TODO 没有复活提示，考虑加不死图腾音效
        if (IceAndFireOrganUtil.hydraSpineSkill(entity, data)) {
            event.setCanceled(true);
            return;
        }
        // 腐败魂灯
        IronSpellOrganUtil.corruptedSoulLanternSoulHarvest(entity, level);
        // 一般实体死了不复活，一般不需要清空当前体液值
        // 不确定那些可以复活宠物的mod是怎么操作的，会不会继承这个attachment，但我决定还是加一个守卫
        if (entity instanceof Player) {
            HumoursData.clearCurrentValues(entity);
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
        // 禅心 - 磐石之躯：免疫摔落伤害
        if (data.hasOrgan(MowziesMobOrgans.ZEN_HEART.get()) && event.getSource().is(DamageTypeTags.IS_FALL)) {
            event.setCanceled(true);
            return;
        }
        // 禅心 - 地卜亲和：拥有地卜术效果时减伤50%
        if (data.hasOrgan(MowziesMobOrgans.ZEN_HEART.get()) && entity.hasEffect(EffectHandler.GEOMANCY)) {
            event.setAmount(event.getAmount() * 0.50F);
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
        // 自伤排除
        boolean isSelfDamage = OrganUtil.isSelfDamage(entity, source);
        // 反击属性处理
        double counterAttack = entity.getAttributeValue(WAICAttribute.COUNTER_ATTACK);
        if (counterAttack > 0 && attacker != null && !isSelfDamage) {
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
        // TODO 实际上是有问题的，倍率应该在最低优先级施加
        double finalMultiplier = 1;
        if (source.is(WAICDamageTagManager.IS_MELEE)) {
            // 近战伤害加伤
            extraDamage += entity.getAttributeValue(WAICAttribute.MELEE_DAMAGE);
            finalMultiplier = entity.getAttributeValue(WAICAttribute.MELEE_DAMAGE_PERCENTAGE);
        } else if (source.is(Tags.DamageTypes.IS_MAGIC)) {
            // 魔法伤害加伤
            extraDamage += entity.getAttributeValue(WAICAttribute.MAGIC_DAMAGE);
            finalMultiplier = entity.getAttributeValue(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE);
        } else if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            // 远程伤害加伤
            extraDamage += entity.getAttributeValue(WAICAttribute.RANGED_DAMAGE);
            finalMultiplier = entity.getAttributeValue(WAICAttribute.RANGED_DAMAGE_PERCENTAGE);
        }
        if (!isSelfDamage && attacker != null) {
            // 九头蛇肌肉效果
            extraDamage += IceAndFireOrganUtil.hydraMuscleHurt(attacker, entity);
            // 背叛效果
            extraDamage += WAICOrganUtil.treacheryAttack(attacker, entity);
        }
        if (!isSelfDamage) {
            // 九头蛇肋骨效果
            block += IceAndFireOrganUtil.hydraRibHurt(entity, attacker);
            // 导流肋骨护盾
            block += WAICOrganUtil.currentRibShield(entity, damage);
            // 尸王脊柱效果
            block += IronSpellOrganUtil.deadKingSpineHurt(entity, damage);
            // 风暴脊柱效果
            block += CataclysmOrganUtil.stormSpineHurt(entity, damage);
        }
        // 应用格挡属性减伤（可为负）,以及加伤
        event.setNewDamage((float) (Math.max(0, damage - block + extraDamage) * finalMultiplier));
    }

    /**
     * @param event 实体受伤后事件
     */
    public static void handlerLivingDamageEvent$Post(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        DamageSource source = event.getSource();
        LivingEntity attacker = source.getEntity() instanceof LivingEntity attackerEntity
                                ? attackerEntity
                                : source.getDirectEntity() instanceof LivingEntity directEntity
                                  ? directEntity
                                  : null;
        boolean isSelfDamage = OrganUtil.isSelfDamage(entity, source);
        if (!isSelfDamage && attacker != null) {
            // 色欲效果
            WAICOrganUtil.lustAttack(attacker, event.getNewDamage());
        }
    }

    /**
     *  @param event 法术释放事件
     */
    public static void handlerSpellOnCastEvent(SpellOnCastEvent event) {
        LivingEntity entity = event.getEntity();
        ChestCavityData data = ChestCavityUtil.getData(entity);

        // ⚡ 钢笔尖：消耗墨水增级（优先于其他增级效果触发）
        if (data.hasOrgan(WAICOrgans.NIB.get())) {
            int currentLevel = event.getSpellLevel();
            int inkCost = WAICOrganUtil.getNibInkCost(currentLevel);
            if (WAICOrganUtil.extractInkToBottle(data, inkCost, true) >= inkCost) {
                WAICOrganUtil.extractInkToBottle(data, inkCost, false);
                event.setSpellLevel(currentLevel + 1);
            }
        }

        // 调色盘：消耗染料增级
        if (data.hasOrgan(WAICOrgans.PALETTE.get())) {
            if (WAICOrganUtil.consumeDyeForSchool(entity, event.getSchoolType())) {
                event.setSpellLevel(event.getSpellLevel() + 1);
            }
        }

        // 原初之火：火焰法术无条件增级
        if (data.hasOrgan(IronSpellOrgans.PRIMORDIAL_FLAME.get())) {
            if (event.getSchoolType() == SchoolRegistry.FIRE.get()) {
                event.setSpellLevel(event.getSpellLevel() + 1);
            }
        }

        // 绿宝石头骨：唤魔法术无条件增级
        if (data.hasOrgan(IronSpellOrgans.EMERALD_SKULL.get())) {
            if (event.getSchoolType() == SchoolRegistry.EVOCATION.get()) {
                event.setSpellLevel(event.getSpellLevel() + 1);
            }
        }

        // 猩红肝脏：以血炼法 — 猩红法术消耗血液增级
        if (data.hasOrgan(WAICOrgans.CRIMSON_LIVER.get())) {
            if (event.getSchoolType() == SchoolRegistry.BLOOD.get()) {
                if (HumoursData.extractBlood(entity, 10, true) >= 10) {
                    HumoursData.extractBlood(entity, 10, false);
                    event.setSpellLevel(event.getSpellLevel() + 1);
                }
            }
        }
    }

    /**
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
                    ChestCavityUtil.createContext(data, i, stack)
                );
            }
        }
        // 异端（脾脏）药水效果增强
        WAICOrganUtil.heresyMobEffectAdded(entity, event.getEffectInstance());
    }

    /**
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
                    ChestCavityUtil.createContext(data, i, stack)
                );
            }
        }
    }

    /**
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
                    ChestCavityUtil.createContext(data, i, stack)
                );
            }
        }
    }

    /**
     * @param event 拾取经验球事件
     */
    public static void handlerPlayerXpEvent$PickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.EXPERIENCE_HEART.get())) return;
        // 因为 mixin 写起来太重，于是直接给玩家额外加一份经验
        // 但如果有其他 mod 取消了事件（比本事件更低监听），就会导致经验重复获取，需要注意
        player.giveExperiencePoints(event.getOrb().value);
    }

    /**
     * @param event 实体使用物品结束事件
     */
    public static void handlerLivingEntityUseItemEvent$Finish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        ItemStack food = event.getItem();
        if (!food.has(DataComponents.FOOD)) return;

        ChestCavityData data = ChestCavityUtil.getData(entity);

        // 直肠子效果
        WAICOrganUtil.straightIntestineEffect(entity, data, food);

        // 暴食吸收生命值效果
        WAICOrganUtil.gluttonyEatEffect(entity, data, food);

        // 蛋糕胃：给予甜蜜效果
        CompanionsOrganUtil.cakeStomachEatEffect(entity, data);
    }

    /**
     * @param event 暴击事件
     */
    public static void handlerCriticalHitEvent(CriticalHitEvent event) {
        Player player = event.getEntity();
        WAICOrganUtil.violenceCriticalHit(player, event);
    }

    /**
     * @param event 玩家右键方块事件
     */
    public static void handlerPlayerInteract$RightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockState state = level.getBlockState(event.getPos());
        Player player = event.getEntity();

        // 砂轮打磨脊柱骨质器官为剑骨头
        if (state.is(Blocks.GRINDSTONE)) {
            InteractionHand hand = event.getHand();
            ItemStack heldItem = player.getItemInHand(hand);
            if (!heldItem.is(ItemTagManager.SPINE) || !heldItem.is(ItemTagManager.BONE) || heldItem.is(WAICOrgans.SWORD_BONE.get())) return;
            if (!level.isClientSide()) {
                ItemStack swordBone = WAICOrgans.SWORD_BONE.get().getDefaultInstance();
                heldItem.shrink(1);
                if (heldItem.isEmpty()) {
                    player.setItemInHand(hand, swordBone);
                } else if (!player.getInventory().add(swordBone)) {
                    player.drop(swordBone, false);
                }
                level.playSound(null, event.getPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }
    }

    /**
     * 龙血药剂与存在置换器取消精确交互，让 Item.use() 通过射线检测接管
     */
    public static void handlerPlayerInteract$EntityInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        if (DragonBloodPreparationItem.shouldCancelEntityInteract(event.getEntity(), event.getHand(), event.getTarget())
            || ExistenceDisplacerItem.shouldCancelEntityInteract(event.getEntity(), event.getHand(), event.getTarget())) {
            event.setCanceled(true);
        }
    }

    /**
     * 龙血药剂与存在置换器取消交互 + 布织泰迪熊获取
     */
    public static void handlerPlayerInteract$EntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (DragonBloodPreparationItem.shouldCancelEntityInteract(event.getEntity(), event.getHand(), event.getTarget())
            || ExistenceDisplacerItem.shouldCancelEntityInteract(event.getEntity(), event.getHand(), event.getTarget())) {
            event.setCanceled(true);
            return;
        }

        Level level = event.getLevel();
        if (level.isClientSide()) return;
        // 布织泰迪熊：未认主或主人是自己的泰迪可剪回器官
        if (!(event.getTarget() instanceof TeddyEntity teddy)) return;
        Player player = event.getEntity();
        if (teddy.isTame() && !teddy.isOwnedBy(player)) return;
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);
        if (!heldItem.is(Items.SHEARS)) return;
        teddy.spawnAtLocation(CompanionsOrgans.CLOTH_TEDDY_BEAR.get().getDefaultInstance());
        level.playSound(null, teddy.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
        teddy.discard();
        heldItem.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    /**
     * 修复神龛复活女仆时丢失的attachment
     * <p>
     * 魂符和照片也会触发本事件，但它们走Entity.load，NeoForge原生已处理attachment，此处对它们是幂等无副作用的重复反序列化
     * </p>
     */
    public static void handlerMaidAndItemTransformEvent$ToMaid(MaidAndItemTransformEvent.ToMaid event) {
        EntityMaid maid = event.getMaid();
        CompoundTag data = event.getData();
        if (!data.contains("neoforge:attachments", Tag.TAG_COMPOUND)) return;
        CompoundTag attachmentsTag = data.getCompound("neoforge:attachments");
        ((AttachmentHolderAccessor) maid).deserializeInternal(maid.registryAccess(), attachmentsTag);
    }
}

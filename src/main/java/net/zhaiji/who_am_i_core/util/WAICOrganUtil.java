package net.zhaiji.who_am_i_core.util;

import com.finderfeed.fdbosses.content.entities.geburah.sins.attachment.PlayerSins;
import com.finderfeed.fdbosses.init.BossEffects;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.item.InkItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.mixinapi.IMobEffectInstance;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.api.UseCondition;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICEffect;
import net.zhaiji.who_am_i_core.task.StraightIntestineTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WAICOrganUtil {
    /**
     * 闹鬼的骨头：胸腔打开时设置可以移动的标记
     */
    public static void hauntedBoneChestCavityOpen(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        if (data == null) return;
        Level level = data.getOwner().level();
        if (level.isClientSide()) return;
        ItemStack stack = context.stack();
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).update(tag -> {
            tag.putBoolean("canChange", true);
        });
        stack.set(DataComponents.CUSTOM_DATA, customData);
    }

    /**
     * 闹鬼的骨头：胸腔打开时随机移动到一个空槽位
     */
    public static void hauntedBoneChestCavityClose(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        if (data == null) return;
        Level level = data.getOwner().level();
        if (level.isClientSide()) return;
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            if (data.getStackInSlot(i).isEmpty()) {
                emptySlots.add(i);
            }
        }
        if (emptySlots.isEmpty()) return;
        ItemStack stack = context.stack();
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.contains("canChange") && !tag.getBoolean("canChange")) return;
        tag.putBoolean("canChange", false);
        data.setStackInSlot(context.index(), ItemStack.EMPTY);
        int targetSlot = emptySlots.get(level.random.nextInt(emptySlots.size()));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        data.setStackInSlot(targetSlot, stack);
    }

    /**
     * 直肠子器官技能
     * <p>
     * 食用食物后，30%几率添加延迟掉落任务（3秒后掉落1个食物）
     * </p>
     *
     * @param entity 食用食物的实体
     * @param data   实体的胸腔数据
     * @param food   被食用的食物物品
     */
    public static void straightIntestineEffect(LivingEntity entity, ChestCavityData data, ItemStack food) {
        // 检查是否拥有直肠子器官
        if (!data.hasOrgan(WAICOrgans.STRAIGHT_INTESTINE.get())) return;
        // 30%几率触发
        if (OrganUtil.rollResult(entity, 0.3F)) {
            // 添加延迟任务（3秒后掉落1个食物）
            data.addTask(new StraightIntestineTask(data, food.copyWithCount(1)));
        }
    }

    /**
     * 获取墨水瓶容量 = 墨水器官数量 × 1000
     */
    public static int getInkCapacity(ChestCavityData data) {
        return data.getOrganCount(WAICItemTagManager.INK) * 1000;
    }

    /**
     * 获取胸腔中第一个墨水瓶，未找到则返回 ItemStack.EMPTY
     */
    private static ItemStack getFirstInkBottle(ChestCavityData data) {
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack organ = data.getStackInSlot(i);
            if (organ.is(WAICOrgans.INK_BOTTLE.get())) {
                return organ;
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * 判断指定墨水瓶是否是胸腔中激活的（第一个）墨水瓶
     *
     * @param data  胸腔数据
     * @param index 当前墨水瓶槽位索引（-1 表示未在胸腔中）
     * @param stack 当前墨水瓶 ItemStack
     * @return true 表示该墨水瓶参与墨水存储/抽取
     */
    public static boolean isInkBottleActive(ChestCavityData data, int index, ItemStack stack) {
        if (data == null || index == -1) return false;
        return getFirstInkBottle(data) == stack;
    }

    /**
     * 向墨水瓶插入墨水，只操作第一个检测到的墨水瓶，容量为墨水器官数量×1000
     *
     * @param data     胸腔数据
     * @param amount   要插入的墨水量（必须 >= 0）
     * @param simulate 是否模拟（true 时不修改数据）
     * @return 实际插入量
     */
    public static float insertInkToBottle(ChestCavityData data, float amount, boolean simulate) {
        if (amount <= 0) return 0;
        int capacity = getInkCapacity(data);
        if (capacity <= 0) return 0;
        ItemStack inkBottle = getFirstInkBottle(data);
        if (inkBottle.isEmpty()) return 0;
        CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        float currentInk = tag.contains("ink") ? tag.getFloat("ink") : 0;
        float space = Math.max(0, capacity - currentInk);
        float toInsert = Math.max(0, Math.min(amount, space));
        if (toInsert > 0 && !simulate) {
            tag.putFloat("ink", currentInk + toInsert);
            inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
        return toInsert;
    }

    /**
     * 从墨水瓶抽取墨水，只操作第一个检测到的墨水瓶
     *
     * @param data     胸腔数据
     * @param amount   要抽取的墨水量（必须 >= 0）
     * @param simulate 是否模拟（true 时不修改数据）
     * @return 实际抽取量
     */
    public static float extractInkToBottle(ChestCavityData data, float amount, boolean simulate) {
        if (amount <= 0) return 0;
        ItemStack inkBottle = getFirstInkBottle(data);
        if (inkBottle.isEmpty()) return 0;
        CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        float currentInk = tag.contains("ink") ? tag.getFloat("ink") : 0;
        float toExtract = Math.max(0, Math.min(amount, currentInk));
        if (toExtract > 0 && !simulate) {
            tag.putFloat("ink", currentInk - toExtract);
            inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
        return toExtract;
    }

    /**
     * 墨水瓶：其他器官变化时，检查墨水是否超出容量，超出则截断
     *
     * @param context      墨水瓶自身的上下文
     * @param changedIndex 变化的器官槽位索引
     * @param oldStack     旧器官
     * @param newStack     新器官
     */
    public static void inkBottleOtherOrganChange(ChestCavitySlotContext context, int changedIndex, ItemStack oldStack, ItemStack newStack) {
        boolean oldIsInk = oldStack.is(WAICItemTagManager.INK);
        boolean newIsInk = newStack.is(WAICItemTagManager.INK);
        if (oldIsInk == newIsInk) return;
        ChestCavityData data = context.data();
        int capacity = getInkCapacity(data);
        ItemStack inkBottle = context.stack();
        CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();
        float currentInk = tag.contains("ink") ? tag.getFloat("ink") : 0;
        if (currentInk > capacity) {
            tag.putFloat("ink", capacity);
            inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
    }

    /**
     * 饮用墨水，容量为墨水器官数量×1000
     */
    public static ItemStack drinkInk(LivingEntity entity, ItemStack stack, UseCondition condition) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!(stack.getItem() instanceof InkItem inkItem)) return stack;

        float value = switch (inkItem.getRarity()) {
            case COMMON -> 1;
            case UNCOMMON -> 5;
            case RARE -> 25;
            case EPIC -> 125;
            case LEGENDARY -> 625;
        };
        insertInkToBottle(data, value, false);
        if (entity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
        }
        if (entity instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(inkItem));
            stack.consume(1, player);
            if (!player.hasInfiniteMaterials()) {
                if (stack.isEmpty()) {
                    player.gameEvent(GameEvent.DRINK);
                    return Items.GLASS_BOTTLE.getDefaultInstance();
                } else {
                    player.getInventory().add(Items.GLASS_BOTTLE.getDefaultInstance());
                }
            }
        }
        entity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    /**
     * 从调色盘器官中消耗对应流派的染料
     *
     * @param entity     实体
     * @param schoolType 法术流派
     * @return 是否成功消耗染料
     */
    public static boolean consumeDyeForSchool(LivingEntity entity, SchoolType schoolType) {
        Item targetDye = OrganUtil.getDyeItemForSchool(schoolType);
        if (targetDye == Items.AIR) return false;
        ChestCavityData data = ChestCavityUtil.getData(entity);
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack organ = data.getStackInSlot(i);
            if (organ.is(WAICOrgans.PALETTE.get())) {
                BundleContents contents = organ.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
                // 创建可变副本并找到染料索引
                int targetIndex = -1;
                for (int index = 0; index < contents.size(); index++) {
                    if (contents.getItemUnsafe(index).is(targetDye)) {
                        targetIndex = index;
                        break;
                    }
                }
                if (targetIndex >= 0) {
                    contents.getItemUnsafe(targetIndex).consume(1, entity);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 墨水肌肉技能：挨打时为墨水瓶添加墨水（伤害值的 (5 + 墨水器官数 × 0.5) 倍）
     *
     * @param context         胸腔槽位上下文
     * @param source          伤害源
     * @param damageContainer 伤害容器（用于获取伤害值）
     */
    public static void inkMuscleSkill(ChestCavitySlotContext context, DamageSource source, DamageContainer damageContainer) {
        if (OrganUtil.isSelfDamage(context.entity(), source)) return;
        float damage = damageContainer.getNewDamage();
        if (damage <= 0) return;
        float conversionRate = 5.0F + context.data().getOrganCount(WAICItemTagManager.INK) * 0.5F;
        insertInkToBottle(context.data(), damage * conversionRate, false);
    }

    /**
     * 钢笔尖墨水消耗 = 5 × 当前法术等级
     *
     * @param currentLevel 当前法术等级
     * @return 需要消耗的墨水量
     */
    public static int getNibInkCost(int currentLevel) {
        return 5 * currentLevel;
    }

    /**
     * 墨水阑尾技能：消耗墨水瓶中的墨水回复法力
     * 消耗的墨水量等于回复的法力量（1:1），尽可能填补法力差值
     * 墨水不足时有墨水就全耗，只回复实际消耗掉的墨水量
     * 没有墨水或法力已满时不触发也不冷却
     */
    public static boolean inkAppendix(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();

        // 计算需要回复的法力量
        MagicData magicData = MagicData.getPlayerMagicData(entity);
        float currentMana = magicData.getMana();
        float maxMana = (float) entity.getAttributeValue(AttributeRegistry.MAX_MANA);
        float manaToRestore = maxMana - currentMana;

        if (manaToRestore <= 0) return false; // 法力已满，不触发

        // 消耗墨水，返回实际抽取量
        float actualExtracted = extractInkToBottle(data, manaToRestore, false);
        if (actualExtracted <= 0) return false;

        // 回复法力
        magicData.addMana(actualExtracted);

        return true;
    }

    /**
     * 拟态器官共效果：生命恢复效果提升50%
     *
     * @param context 胸腔槽位上下文
     * @param event   治疗事件
     */
    public static void mimicHealBoost(ChestCavitySlotContext context, LivingHealEvent event) {
        event.setAmount(event.getAmount() * 1.5F);
    }

    /**
     * 经验之心：每10级经验等级+1健康值
     */
    public static void experienceHeartModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        LivingEntity entity = context.entity();
        int level = 0;
        if (entity instanceof Player player) {
            level = player.experienceLevel;
        }
        double healthBonus = Math.floor(level / 10.0);
        modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), healthBonus));
    }

    /**
     * 病变心脏 modifier：每有一个负面效果+1健康，每有一个正面效果-1健康
     */
    public static void lesionHeartModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        LivingEntity entity = context.entity();
        int beneficial = 0, harmful = 0;
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect().value().getCategory() == MobEffectCategory.BENEFICIAL) {
                beneficial++;
            } else if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                harmful++;
            }
        }
        modifiers.put(
            InitAttribute.HEALTH,
            OrganAttributeUtil.createAddValueModifier(context.id(), harmful - beneficial)
        );
    }

    /**
     * 病变心脏技能：将自身所有效果传播给10格范围内的所有LivingEntity
     * 冷却时间10秒（200tick）
     */
    public static boolean lesionHeart(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        if (effects.isEmpty()) return false;
        AABB aabb = entity.getBoundingBox().inflate(10);
        List<LivingEntity> targets = entity.level().getEntitiesOfClass(
            LivingEntity.class, aabb, target -> target != entity
        );
        for (LivingEntity target : targets) {
            for (MobEffectInstance instance : effects) {
                target.addEffect(new MobEffectInstance(instance));
            }
        }
        return true;
    }

    /**
     * 病变肌肉 modifier：每有一个负面效果，+1速度+1力量
     */
    public static void lesionMuscleModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers
    ) {
        LivingEntity entity = context.entity();
        int harmfulCount = 0;
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) harmfulCount++;
        }
        modifiers.put(
            InitAttribute.STRENGTH,
            OrganAttributeUtil.createAddValueModifier(context.id(), harmfulCount)
        );
        modifiers.put(
            InitAttribute.SPEED,
            OrganAttributeUtil.createAddValueModifier(context.id(), harmfulCount)
        );
    }

    /**
     * 病变肌肉攻击：对持有负面效果的目标，额外伤害等于目标所有负面效果的(amplifier + 1)之和
     */
    public static void lesionMuscleAttack(
        ChestCavitySlotContext context, LivingEntity target,
        DamageSource source, DamageContainer damageContainer
    ) {
        if (OrganUtil.isSelfDamage(target, source)) return;
        int bonusDamage = 0;
        for (MobEffectInstance instance : target.getActiveEffects()) {
            if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                bonusDamage += instance.getAmplifier() + 1;
            }
        }
        if (bonusDamage > 0) {
            damageContainer.setNewDamage(damageContainer.getNewDamage() + bonusDamage);
        }
    }

    /**
     * 猩红心脏泣血：每次受到治疗时，将治疗量 ×5 转化为血液存储
     */
    public static void crimsonHeartHeal(ChestCavitySlotContext context, LivingHealEvent event) {
        LivingEntity entity = context.entity();
        if (HumoursData.get(entity).isBloodFull()) return;
        float amount = event.getAmount();
        HumoursData.insertBlood(entity, amount * (3.0F + (float) (entity.getAttributeValue(InitAttribute.METABOLISM) * 0.2)), false);
    }

    /**
     * 猩红心脏被动：器官变化时更新血液容量
     * <p>
     * 血液容量增量 = 猩红器官数量 × 100
     * 使用先减旧值再加新值的增量方式
     * </p>
     *
     * @param data     胸腔数据
     * @param entity   实体
     * @param oldStack 变化前的器官
     * @param newStack 变化后的器官
     */
    public static void crimsonHeartOrganChange(ChestCavityData data, LivingEntity entity, ItemStack oldStack, ItemStack newStack) {
        boolean oldIsCrimson = oldStack.is(WAICItemTagManager.CRIMSON);
        boolean newIsCrimson = newStack.is(WAICItemTagManager.CRIMSON);
        if (!oldIsCrimson && !newIsCrimson) return;
        // 变化后猩红器官数量（数据已更新）
        int crimsonCountAfter = data.getOrganCount(WAICItemTagManager.CRIMSON);
        // 变化前猩红器官数量 = 变化后 - 新增 + 移除
        int crimsonCountBefore = crimsonCountAfter - (newIsCrimson ? 1 : 0) + (oldIsCrimson ? 1 : 0);
        // 变化后是否有心脏
        boolean hasHeartAfter = data.hasOrgan(WAICOrgans.CRIMSON_HEART.get());
        boolean oldIsHeart = oldStack.is(WAICOrgans.CRIMSON_HEART.get());
        boolean newIsHeart = newStack.is(WAICOrgans.CRIMSON_HEART.get());
        // 变化前是否有心脏：变后有则除非本次新增，变后无则只有本次移除
        boolean hasHeartBefore = hasHeartAfter ? !newIsHeart : oldIsHeart;
        // 先减旧容量，再加新容量
        int bonusBefore = hasHeartBefore ? crimsonCountBefore * 100 : 0;
        int bonusAfter = hasHeartAfter ? crimsonCountAfter * 100 : 0;
        int delta = bonusAfter - bonusBefore;
        if (delta != 0) {
            HumoursData.addMaxBlood(entity, delta);
        }
    }

    /**
     * 猩红阑尾技能：鲜血涌泉
     * <p>
     * 消耗 5 点血液回复 1 点生命值，尽可能填补生命差值。
     * 血液不足或已满血时不触发、不冷却。
     * 30 秒冷却（600 tick）。
     * </p>
     *
     * @param context 胸腔槽位上下文
     * @return true 触发冷却
     */
    public static boolean crimsonAppendix(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();

        float missingHealth = entity.getMaxHealth() - entity.getHealth();
        if (missingHealth <= 0) return false;

        float bloodNeeded = missingHealth * 5;
        float actualBlood = HumoursData.extractBlood(entity, bloodNeeded, false);
        if (actualBlood <= 0) return false;

        float healAmount = actualBlood / 5;
        // 使用 setHealth 直接设置，不触发 heal() → 避免心脏泣血回调将血液加回
        entity.setHealth(Math.min(entity.getHealth() + healAmount, entity.getMaxHealth()));
        return true;
    }

    /**
     * 窝瓜 - 受到摔落伤害时免疫，并将等量摔落伤害平分给周围5×5×5范围内的实体
     */
    public static void squashIncomingDamage(ChestCavitySlotContext slotContext, LivingIncomingDamageEvent event) {
        if (!event.getSource().is(DamageTypeTags.IS_FALL)) return;

        LivingEntity entity = slotContext.entity();
        Level level = entity.level();
        if (level.isClientSide()) return;

        float fallDamage = event.getAmount();

        // 5×5×5 范围搜索（半径2.5格）
        AABB searchBox = entity.getBoundingBox().inflate(2.5);
        List<LivingEntity> targets = level.getEntitiesOfClass(
            LivingEntity.class,
            searchBox,
            target -> target != entity
                      && !(target instanceof TamableAnimal tamable && entity instanceof Player player && tamable.isOwnedBy(player))
        );

        // 平分摔落伤害
        float damagePerTarget = fallDamage / targets.size();
        DamageSource fallSource = level.damageSources().fall();
        for (LivingEntity target : targets) {
            target.hurt(fallSource, damagePerTarget);
        }

        // 免疫摔落伤害
        event.setCanceled(true);
    }

    /**
     * 收集胸腔中所有蓄能模块
     */
    public static List<ItemStack> collectEnergyModules(ChestCavityData data) {
        List<ItemStack> modules = new ArrayList<>();
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (!stack.isEmpty() && stack.is(WAICOrgans.ENERGY_MODULE.get())) {
                modules.add(stack);
            }
        }
        return modules;
    }

    /**
     * 获取当前所有蓄能模块的电荷总量
     */
    public static float getCharge(ChestCavityData data) {
        return getCharge(collectEnergyModules(data));
    }

    /**
     * 获取电荷总量（已有模块列表）
     */
    public static float getCharge(List<ItemStack> modules) {
        float totalCharge = 0;
        for (ItemStack module : modules) {
            totalCharge += getModuleCharge(module);
        }
        return totalCharge;
    }

    /**
     * 获取单个蓄能模块的电荷量
     */
    public static float getModuleCharge(ItemStack module) {
        CompoundTag tag = module.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return tag.contains("charge") ? tag.getFloat("charge") : 0;
    }

    /**
     * 设置单个蓄能模块的电荷量
     */
    public static void setModuleCharge(ItemStack module, float charge) {
        CompoundTag tag = module.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putFloat("charge", Math.max(0, charge));
        module.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    /**
     * 获取最大电荷上限 = 500 × 蓄能模块数量
     */
    public static float getMaxCharge(ChestCavityData data) {
        return getMaxCharge(collectEnergyModules(data));
    }

    /**
     * 获取最大电荷上限（已有模块列表）
     */
    public static float getMaxCharge(List<ItemStack> modules) {
        return 500 * modules.size();
    }

    /**
     * 获取有效超载上限 = maxCharge × (1 + 0.5)
     */
    public static float getEffectiveMaxCharge(ChestCavityData data) {
        return getEffectiveMaxCharge(collectEnergyModules(data));
    }

    /**
     * 获取有效超载上限（已有模块列表）
     */
    public static float getEffectiveMaxCharge(List<ItemStack> modules) {
        return getMaxCharge(modules) * (1 + 0.5F);
    }

    /**
     * 向蓄能模块中插入电荷
     * <p>
     * 两阶段分配：
     * Phase 1：所有模块先填到基础容量 500
     * Phase 2（仅当 canOvercharge=true）：从第一个模块开始顺序超载充能到 750，然后下一个
     * </p>
     *
     * @param canOvercharge 是否允许超载（超出基础容量 500）
     */
    public static float insertCharge(ChestCavityData data, float amount, boolean canOvercharge, boolean simulate) {
        if (amount <= 0) return 0;
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return 0;

        float globalMax = canOvercharge ? getEffectiveMaxCharge(modules) : getMaxCharge(modules);
        float currentCharge = getCharge(modules);
        float canInsert = Math.max(0, globalMax - currentCharge);
        float toInsert = Math.min(amount, canInsert);

        if (toInsert <= 0) return 0;
        if (simulate) return toInsert;

        float remaining = toInsert;

        // Phase 1: 所有模块填到基础容量 500
        for (ItemStack module : modules) {
            if (remaining <= 0) break;
            float moduleCharge = getModuleCharge(module);
            if (moduleCharge < 500) {
                float fill = Math.min(remaining, 500 - moduleCharge);
                setModuleCharge(module, moduleCharge + fill);
                remaining -= fill;
            }
        }

        // Phase 2: 顺序超载充能（仅当允许超载）
        if (canOvercharge) {
            for (ItemStack module : modules) {
                if (remaining <= 0) break;
                float moduleCharge = getModuleCharge(module);
                if (moduleCharge < 750) {
                    float fill = Math.min(remaining, 750 - moduleCharge);
                    setModuleCharge(module, moduleCharge + fill);
                    remaining -= fill;
                }
            }
        }

        return toInsert - remaining;
    }

    /**
     * 从蓄能模块中提取电荷（优先消耗超载电荷，再消耗普通电荷）
     * 内部处理导流肋骨余电回收
     */
    public static float extractCharge(ChestCavityData data, LivingEntity entity, float amount, boolean simulate) {
        if (amount <= 0) return 0;
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return 0;

        float currentCharge = getCharge(modules);
        float toExtract = Math.min(amount, currentCharge);

        if (toExtract <= 0) return 0;
        if (simulate) return toExtract;

        float remaining = toExtract;

        // 第一阶段：优先消耗超载电荷（超出每模块500基础容量的部分）
        for (ItemStack module : modules) {
            if (remaining <= 0) break;
            float moduleCharge = getModuleCharge(module);
            float overload = Math.max(0, moduleCharge - 500);
            float extract = Math.min(remaining, overload);
            if (extract > 0) {
                setModuleCharge(module, moduleCharge - extract);
                remaining -= extract;
            }
        }

        // 第二阶段：消耗普通电荷
        for (ItemStack module : modules) {
            if (remaining <= 0) break;
            float moduleCharge = getModuleCharge(module);
            float extract = Math.min(remaining, moduleCharge);
            if (extract > 0) {
                setModuleCharge(module, moduleCharge - extract);
                remaining -= extract;
            }
        }

        float extracted = toExtract - remaining;
        // 导流肋骨余电回收
        if (extracted > 0 && data.hasOrgan(WAICOrgans.CURRENT_RIB.get())) {
            float healRate = isOverloadMode(entity) ? 0.20f : 0.10f;
            entity.heal(extracted * healRate);
        }
        return extracted;
    }

    /**
     * 消耗电荷（含导流肋骨回路返还逻辑）
     */
    public static float consumeCharge(ChestCavityData data, LivingEntity entity, float amount, boolean simulate) {
        float extracted = extractCharge(data, entity, amount, simulate);
        if (extracted > 0 && !simulate && data.hasOrgan(WAICOrgans.CURRENT_RIB.get())) {
            float refundChance = isOverloadMode(entity) ? 0.5f : 0.25f;
            if (entity.getRandom().nextFloat() < refundChance) {
                insertCharge(data, extracted, true, false);
            }
        }
        return extracted;
    }

    /**
     * 是否处于超频模式
     */
    public static boolean isOverloadMode(LivingEntity entity) {
        return entity.hasEffect(WAICEffect.OVERLOAD);
    }

    /**
     * 蓄能模块 tick：超载自衰减
     * <p>
     * 只处理自己的 ItemStack，如果自身电荷超过基础容量 500，
     * 每 tick 衰减 1 点。
     * </p>
     */
    public static void energyModuleTick(ChestCavitySlotContext context) {
        ItemStack stack = context.stack();
        float charge = getModuleCharge(stack);
        if (charge > 500) {
            float drain = Math.min(1.0F, charge - 500);
            setModuleCharge(stack, charge - drain);
        }
    }

    /**
     * 演算核心 tick：信号再生
     * <p>
     * 每 tick 回复 1 点电荷，不超过基础容量上限（500 × 蓄能模块数量）。
     * </p>
     */
    public static void computingCoreTick(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        if (isOverloadMode(entity)) return;
        float totalCharge = getCharge(data);
        float baseMax = getMaxCharge(data);
        if (totalCharge < baseMax) {
            float toRegen = Math.min(1.0f, baseMax - totalCharge);
            insertCharge(data, toRegen, false, false);
        }
    }

    /**
     * 充能肌束 tick：电流推动（冲刺产生电荷）
     */
    public static void chargedMuscleTick(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        if (!entity.isSprinting()) return;
        insertCharge(data, 1, true, false);
    }

    /**
     * 电龙器官 tick：蓄能产电
     * <p>
     * 每个电龙器官每 tick 产出 0.1 电荷。
     * </p>
     */
    public static void lightningDragonChargeTick(ChestCavitySlotContext context) {
        insertCharge(context.data(), 0.1F, true, false);
    }

    /**
     * 传导链节主动技能：激活超频模式
     * 消耗当前总电荷的一半，持续时间等于消耗电荷量（tick）
     */
    public static boolean conductiveSpine(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return false;
        float currentCharge = getCharge(modules);
        float activationCost = currentCharge / 2;
        if (activationCost <= 0) return false;
        consumeCharge(data, entity, activationCost, false);
        entity.addEffect(new MobEffectInstance(WAICEffect.OVERLOAD, (int) activationCost));
        return true;
    }

    /**
     * 检查对称位置是否存在导流肋骨
     */
    public static boolean hasSymmetricCurrentRib(ChestCavityData data, int index) {
        int symmetricIndex = ChestCavityUtil.getMirrorSlotIndex(index);
        if (symmetricIndex == index) return false;
        if (symmetricIndex < 0 || symmetricIndex >= data.getSlots()) return false;
        ItemStack symmetricStack = data.getStackInSlot(symmetricIndex);
        return !symmetricStack.isEmpty() && symmetricStack.is(WAICOrgans.CURRENT_RIB.get());
    }

    /**
     * 导流肋骨护盾：每10电荷抵消1伤害，上限4（超频8）
     */
    public static float currentRibShield(LivingEntity entity, float damage) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(WAICOrgans.CURRENT_RIB.get())) return 0;
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return 0;
        float charge = getCharge(modules);
        if (charge <= 0) return 0;
        boolean overload = isOverloadMode(entity);
        boolean hasSymmetric = false;
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (!stack.isEmpty() && stack.is(WAICOrgans.CURRENT_RIB.get())) {
                if (hasSymmetricCurrentRib(data, i)) {
                    hasSymmetric = true;
                    break;
                }
            }
        }
        float costPerPoint = hasSymmetric ? 5 : 10;
        int maxBlock = overload ? 8 : 4;
        int maxAffordable = (int) (charge / costPerPoint);
        int blockPoints = Math.min(maxBlock, Math.min(maxAffordable, (int) Math.floor(damage)));
        if (blockPoints <= 0) return 0;
        float actualCost = blockPoints * costPerPoint;
        consumeCharge(data, entity, actualCost, false);
        return blockPoints;
    }

    /**
     * 获取胸腔中九狱器官的数量
     */
    private static int getNineHellCount(ChestCavitySlotContext context) {
        return ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.NINE_HELL);
    }

    /**
     * 九狱器官共用 otherChange 回调
     * 当其他槽位的器官变化涉及九狱器官时，重新计算当前器官的属性
     */
    public static void nineHellOtherChange(ChestCavitySlotContext context, int changedIndex, ItemStack oldStack, ItemStack newStack) {
        if (newStack.is(WAICItemTagManager.NINE_HELL) || oldStack.is(WAICItemTagManager.NINE_HELL)) {
            OrganAttributeUtil.updateSlotOrganAttribute(context);
        }
    }

    /**
     * 灵薄 modifier：幸运属性动态调整（基础 2 - N）
     */
    public static void limboModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(Attributes.LUCK, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 灵薄 tick：每秒给予经验（叠加式 1/3/5）
     */
    public static void limboTick(ChestCavitySlotContext context) {
        if (!(context.entity() instanceof ServerPlayer serverPlayer)) return;
        // 每 20 tick（1秒）触发一次
        if (serverPlayer.tickCount % 20 != 0) return;
        int nineHellCount = getNineHellCount(context);
        serverPlayer.giveExperiencePoints(nineHellCount * nineHellCount);
    }

    /**
     * 色欲 modifier：营养属性动态调整（基础 2 - N）
     */
    public static void lustModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(InitAttribute.NUTRITION, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 色欲 attack：攻击回复伤害 10%/30%/60% 生命（叠加式）
     */
    public static void lustAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        if (OrganUtil.isSelfDamage(target, source)) return;
        int nineHellCount = getNineHellCount(context);
        float healPercent = nineHellCount >= 3 ? 0.6F : (nineHellCount == 2 ? 0.3F : 0.1F);
        float healAmount = damageContainer.getNewDamage() * healPercent;
        if (healAmount > 0) {
            context.entity().heal(healAmount);
        }
    }

    /**
     * 暴食 modifier：消化属性动态调整（基础 2 - N）
     */
    public static void gluttonyModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(InitAttribute.DIGESTION, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 暴食食用效果（在 LivingEntityUseItemEvent.Finish 中调用）
     * 效果2 (N≥2)：食用获得饥饿值×N的黄心（吸收生命值），上限N×20
     * 效果3 (N≥3)：食用额外回复N点生命
     */
    public static void gluttonyEatEffect(LivingEntity entity, ChestCavityData data, ItemStack food) {
        if (!data.hasOrgan(WAICOrgans.GLUTTONY.get())) return;

        int nineHellCount = data.getOrganCount(WAICItemTagManager.NINE_HELL);
        if (nineHellCount < 2) return;

        FoodProperties foodProperties = food.get(DataComponents.FOOD);
        if (foodProperties == null) return;
        int nutrition = foodProperties.nutrition();

        // 效果2：吸收生命值（黄心），上限 N × 20
        float absorptionToAdd = Math.min((float) nutrition * nineHellCount, nineHellCount * 20.0F);
        if (absorptionToAdd > 0) {
            float currentAbsorption = entity.getAbsorptionAmount();
            entity.setAbsorptionAmount(currentAbsorption + absorptionToAdd);
        }

        // 效果3：额外生命回复
        if (nineHellCount >= 3) {
            entity.heal(nineHellCount);
        }
    }

    /**
     * 贪婪 modifier：呼吸恢复/容量/耐力动态调整（基础 2 - N）+ 抢夺/时运
     */
    public static void greedModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int nineHellCount = getNineHellCount(context);
        modifiers.put(InitAttribute.BREATH_RECOVERY, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - nineHellCount));
        modifiers.put(InitAttribute.BREATH_CAPACITY, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - nineHellCount));
        modifiers.put(InitAttribute.ENDURANCE, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - nineHellCount));
        // 抢夺 + 时运（叠加式）
        int bonus = nineHellCount >= 3 ? 6 : (nineHellCount == 2 ? 3 : 1);
        modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
        modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
    }

    /**
     * 愤怒 modifier：解毒属性动态调整（基础 2 - N）+ 力量/速度（叠加式）
     */
    public static void wrathModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int nineHellCount = getNineHellCount(context);
        modifiers.put(InitAttribute.DETOXIFICATION, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - nineHellCount));
        // 力量 + 速度（叠加式）
        int bonus = nineHellCount >= 3 ? 6 : (nineHellCount == 2 ? 3 : 1);
        modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
        modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
    }

    /**
     * 异端 modifier：代谢属性动态调整（基础 2 - N）
     * 药水效果增强通过事件处理（CommonEventHandler 中 MobEffectEvent.Added）
     */
    public static void heresyModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(InitAttribute.METABOLISM, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 异端药水效果增强（在 MobEffectEvent.Added 中调用）
     * 罪业1: 药水持续时间 +50%（×1.5）
     * 罪业2: 药水持续时间 +150%（×2.5）
     * 罪业3: 药水持续时间 +150%（×2.5）+ 药水等级 +1
     */
    public static void heresyMobEffectAdded(LivingEntity entity, MobEffectInstance effectInstance) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(WAICOrgans.HERESY.get())) return;

        int nineHellCount = data.getOrganCount(WAICItemTagManager.NINE_HELL);
        IMobEffectInstance iEffect = (IMobEffectInstance) effectInstance;

        // 药水持续时间延长，罪业1：+50%（×1.5），罪业2：+150%（×2.5），罪业3：+150%（×2.5）
        iEffect.setDuration(duration -> (int) (duration * (nineHellCount >= 2 ? 2.5 : 1.5)), entity);

        // 药水等级 +1
        if (nineHellCount >= 3) {
            iEffect.setAmplifier(effectInstance.getAmplifier() + 1, entity);
        }
    }

    /**
     * 暴力 modifier：力量/速度属性动态调整（基础 2 - N）
     * 暴击效果通过事件处理（CommonEventHandler 中 CriticalHitEvent）
     */
    public static void violenceModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int nineHellCount = getNineHellCount(context);
        modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - nineHellCount));
        modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - nineHellCount));
    }

    /**
     * 暴力暴击增强（在 CriticalHitEvent 中调用）
     * N≥1: 暴击伤害 ×2（设置倍率为 3.0，原版暴击为 1.5）
     * N≥2: 暴击伤害 ×2（设置倍率为 6.0）
     * N≥3: 攻击永远暴击
     */
    public static void violenceCriticalHit(Player player, CriticalHitEvent event) {
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.VIOLENCE.get())) return;
        int nineHellCount = data.getOrganCount(WAICItemTagManager.NINE_HELL);

        if (nineHellCount >= 3) {
            // 永远暴击 + 暴击伤害 ×4
            event.setCriticalHit(true);
            event.setDamageMultiplier(6.0F);
        } else {
            // 暴击伤害 ×2
            if (event.isCriticalHit()) {
                event.setDamageMultiplier(nineHellCount == 2 ? 6F : 3F);
            }
        }
    }

    /**
     * 欺诈 modifier：过滤属性动态调整（基础 2 - N）
     */
    public static void fraudModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(InitAttribute.FILTRATION, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 背叛 modifier：健康属性动态调整（基础 2 - N）
     */
    public static void treacheryModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 背叛 attack：攻击额外造成目标最大生命值 1%/4%/9% 伤害（叠加式）
     */
    public static void treacheryAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        if (OrganUtil.isSelfDamage(target, source)) return;
        int nineHellCount = getNineHellCount(context);
        float bonusDamage = target.getMaxHealth() * (nineHellCount >= 3 ? 0.09F : (nineHellCount == 2 ? 0.04F : 0.01F));
        if (bonusDamage > 0) {
            damageContainer.setNewDamage(damageContainer.getNewDamage() + bonusDamage);
        }
    }

    /**
     * 血肉偶像主动技能：赎罪祭血
     * <p>
     * 使用迭代器逐个遍历负面效果，每有1个负面效果当前生命值折半一次并立即清除该效果。
     * 当清除的是「罪人」效果时，额外减少1层罪孽。
     * </p>
     */
    public static boolean fleshIdol(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return false;

        float health = entity.getHealth();
        List<Holder<MobEffect>> toRemove = new ArrayList<>();
        for (MobEffectInstance effect : entity.getActiveEffects()) {
            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                health /= 2;
                // 清除罪人效果时，减少1层罪孽
                if (effect.getEffect().is(BossEffects.SINNER) && entity instanceof Player player) {
                    PlayerSins sins = PlayerSins.getPlayerSins(player);
                    sins.setSinnedTimes(Math.max(0, sins.getSinnedTimes() - 1));
                    PlayerSins.setPlayerSins(player, sins);
                }
                toRemove.add(effect.getEffect());
            }
        }
        for (Holder<MobEffect> effect : toRemove) {
            entity.removeEffect(effect);
        }
        entity.setHealth(Math.max(1, health));
        return true;
    }
}

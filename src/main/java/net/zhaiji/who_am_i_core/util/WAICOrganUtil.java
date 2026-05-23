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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.trading.MerchantOffer;
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
    public static void straightIntestineSkill(LivingEntity entity, ChestCavityData data, ItemStack food) {
        // 检查是否拥有直肠子器官
        if (!data.hasOrgan(WAICOrgans.STRAIGHT_INTESTINE.get())) return;
        // 30%几率触发
        if (OrganUtil.rollResult(entity, 0.3F)) {
            // 添加延迟任务（3秒后掉落1个食物）
            data.addTask(new StraightIntestineTask(data, food.copyWithCount(1)));
        }
    }

    /**
     * 向墨水瓶插入墨水，多瓶依次填充
     *
     * @param data     胸腔数据
     * @param amount   要插入的墨水量（必须 >= 0）
     * @param capacity 墨水瓶容量
     * @param simulate 是否模拟（true 时不修改数据）
     * @return 实际插入量
     */
    public static int insertInkToBottle(ChestCavityData data, int amount, int capacity, boolean simulate) {
        if (amount <= 0 || capacity <= 0) return 0;
        List<ItemStack> inkBottles = collectInkBottles(data);
        if (inkBottles.isEmpty()) return 0;
        int inserted = 0;
        for (ItemStack inkBottle : inkBottles) {
            CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();
            int currentInk = tag.contains("ink") ? tag.getInt("ink") : 0;
            int space = Math.max(0, capacity - currentInk);
            int toInsert = Math.max(0, Math.min(amount - inserted, space));
            if (toInsert == 0) continue;
            if (!simulate) {
                tag.putInt("ink", currentInk + toInsert);
                inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
            inserted += toInsert;
            if (inserted >= amount) break;
        }
        return inserted;
    }

    /**
     * 向墨水瓶插入墨水，默认容量 1000
     *
     * @param data     胸腔数据
     * @param amount   要插入的墨水量（必须 >= 0）
     * @param simulate 是否模拟
     * @return 实际插入量
     */
    public static int insertInkToBottle(ChestCavityData data, int amount, boolean simulate) {
        return insertInkToBottle(data, amount, 1000, simulate);
    }

    /**
     * 从墨水瓶抽取墨水，多瓶依次抽取
     *
     * @param data     胸腔数据
     * @param amount   要抽取的墨水量（必须 >= 0）
     * @param capacity 墨水瓶容量
     * @param simulate 是否模拟（true 时不修改数据）
     * @return 实际抽取量
     */
    public static int extractInkToBottle(ChestCavityData data, int amount, int capacity, boolean simulate) {
        if (amount <= 0) return 0;
        List<ItemStack> inkBottles = collectInkBottles(data);
        if (inkBottles.isEmpty()) return 0;
        int extracted = 0;
        for (ItemStack inkBottle : inkBottles) {
            CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();
            int currentInk = tag.contains("ink") ? tag.getInt("ink") : 0;
            int toExtract = Math.max(0, Math.min(amount - extracted, currentInk));
            if (toExtract == 0) continue;
            if (!simulate) {
                tag.putInt("ink", currentInk - toExtract);
                inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
            extracted += toExtract;
            if (extracted >= amount) break;
        }
        return extracted;
    }

    /**
     * 从墨水瓶抽取墨水，默认容量 1000
     *
     * @param data     胸腔数据
     * @param amount   要抽取的墨水量（必须 >= 0）
     * @param simulate 是否模拟
     * @return 实际抽取量
     */
    public static int extractInkToBottle(ChestCavityData data, int amount, boolean simulate) {
        return extractInkToBottle(data, amount, 1000, simulate);
    }

    /**
     * 收集胸腔中所有墨水瓶物品
     */
    private static List<ItemStack> collectInkBottles(ChestCavityData data) {
        List<ItemStack> inkBottles = new ArrayList<>();
        for (ItemStack organ : data.getOrgans()) {
            if (organ.is(WAICOrgans.INK_BOTTLE.get())) {
                inkBottles.add(organ);
            }
        }
        return inkBottles;
    }

    /**
     * 饮用墨水，最高存储1000点
     */
    public static ItemStack drinkInk(LivingEntity entity, ItemStack stack, UseCondition condition) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!(stack.getItem() instanceof InkItem inkItem)) return stack;

        int value = switch (inkItem.getRarity()) {
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
        for (ItemStack organ : data.getOrgans()) {
            if (organ.is(WAICOrgans.PALETTE.get())) {
                BundleContents contents = organ.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
                // 创建可变副本并找到染料索引
                int targetIndex = -1;
                for (int i = 0; i < contents.size(); i++) {
                    if (contents.getItemUnsafe(i).is(targetDye)) {
                        targetIndex = i;
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
     * 墨水肌肉技能：挨打时为墨水瓶添加墨水
     *
     * @param context         胸腔槽位上下文
     * @param source          伤害源
     * @param damageContainer 伤害容器（用于获取伤害值）
     */
    public static void inkMuscleSkill(ChestCavitySlotContext context, DamageSource source, DamageContainer damageContainer) {
        if (OrganUtil.isSelfDamage(context.entity(), source)) return;
        float damage = damageContainer.getNewDamage();
        if (damage <= 0) return;
        insertInkToBottle(context.data(), (int) damage, false);
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
    public static boolean inkAppendixSkill(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();

        // 计算需要回复的法力量
        MagicData magicData = MagicData.getPlayerMagicData(entity);
        float currentMana = magicData.getMana();
        float maxMana = (float) entity.getAttributeValue(AttributeRegistry.MAX_MANA);
        float manaToRestore = maxMana - currentMana;

        if (manaToRestore <= 0) return false; // 法力已满，不触发

        // 消耗墨水，返回实际抽取量
        int actualExtracted = extractInkToBottle(data, (int) manaToRestore, false);
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

    // ==================== 病变器官 ====================

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
    public static boolean lesionHeartSkill(ChestCavitySlotContext context) {
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

    // ==================== 猩红器官 ====================

    /**
     * 猩红心脏泣血：每次受到治疗时，将治疗量 ×5 转化为血液存储
     */
    public static void crimsonHeartHeal(ChestCavitySlotContext context, LivingHealEvent event) {
        LivingEntity entity = context.entity();
        if (HumoursData.get(entity).isBloodFull()) return;
        float amount = event.getAmount();
        HumoursData.insertBlood(entity, amount * 5, false);
    }

    /**
     * 猩红心脏安装：增加 100 点血液上限
     */
    public static void crimsonHeartAdded(ChestCavitySlotContext context) {
        HumoursData.addMaxBlood(context.entity(), 100);
    }

    /**
     * 猩红心脏移除：收回 100 点血液上限
     */
    public static void crimsonHeartRemoved(ChestCavitySlotContext context) {
        HumoursData.addMaxBlood(context.entity(), -100);
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
    public static boolean crimsonAppendixSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();

        float missingHP = entity.getMaxHealth() - entity.getHealth();
        if (missingHP <= 0) return false;

        float bloodNeeded = missingHP * 5;
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

    // ==================== 电荷系统 ====================

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
        float total = 0;
        for (ItemStack module : modules) {
            total += getModuleCharge(module);
        }
        return total;
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
     * 向蓄能模块中插入电荷（按比例分配到各模块）
     */
    public static float insertCharge(ChestCavityData data, float amount, boolean simulate) {
        if (amount <= 0) return 0;
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return 0;

        float effectiveMax = getEffectiveMaxCharge(modules);
        float currentCharge = getCharge(modules);
        float canInsert = Math.max(0, effectiveMax - currentCharge);
        float toInsert = Math.min(amount, canInsert);

        if (toInsert <= 0) return 0;
        if (simulate) return toInsert;

        float maxPerModule = effectiveMax / modules.size();
        float remaining = toInsert;
        for (ItemStack module : modules) {
            float moduleCharge = getModuleCharge(module);
            float moduleCanInsert = Math.max(0, maxPerModule - moduleCharge);
            float insert = Math.min(remaining, moduleCanInsert);
            if (insert > 0) {
                setModuleCharge(module, moduleCharge + insert);
                remaining -= insert;
            }
            if (remaining <= 0) break;
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
                insertCharge(data, extracted, false);
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
     * 蓄能模块 tick：超载衰减
     */
    public static void energyModuleTick(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return;
        float charge = getCharge(modules);
        float maxCharge = getMaxCharge(modules);
        if (charge > maxCharge) {
            float drain = Math.min(1.0F, charge - maxCharge);
            extractCharge(data, entity, drain, false);
        }
    }

    /**
     * 演算核心 tick：信号再生
     */
    public static void computingCoreTick(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        if (isOverloadMode(entity)) return;
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return;
        float charge = getCharge(modules);
        float maxCharge = getMaxCharge(modules);
        if (charge < maxCharge) {
            float toRegen = Math.min(1.0f, maxCharge - charge);
            insertCharge(data, toRegen, false);
        }
    }

    /**
     * 充能肌束 tick：电流推动（冲刺产生电荷）
     */
    public static void chargedMuscleTick(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        if (!entity.isSprinting()) return;
        insertCharge(data, 1, false);
    }

    /**
     * 传导链节主动技能：激活超频模式
     * 消耗当前总电荷的一半，持续时间等于消耗电荷量（tick）
     */
    public static boolean conductiveSpineSkill(ChestCavitySlotContext context) {
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
        int symmetricIndex = OrganUtil.getSymmetricRibIndex(index);
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

    // ==================== 九狱器官通用 ====================

    /**
     * 获取胸腔中九狱器官的数量
     */
    private static int getNineHellCount(ChestCavitySlotContext context) {
        int count = context.data().getOrganCount(WAICItemTagManager.NINE_HELL);
        if (context.index() == -1) count++;
        return count;
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

    // ==================== 灵薄（阑尾）====================

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
        int n = getNineHellCount(context);
        int xp = n * n;
        serverPlayer.giveExperiencePoints(xp);
    }

    // ==================== 色欲（肠子）====================

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
        int n = getNineHellCount(context);
        float healPercent = n >= 3 ? 0.6F : (n == 2 ? 0.3F : 0.1F);
        float damage = damageContainer.getNewDamage();
        float healAmount = damage * healPercent;
        if (healAmount > 0) {
            context.entity().heal(healAmount);
        }
    }

    // ==================== 暴食（胃）====================

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

        int n = data.getOrganCount(WAICItemTagManager.NINE_HELL);
        if (n < 2) return;

        FoodProperties foodProperties = food.get(DataComponents.FOOD);
        if (foodProperties == null) return;
        int nutrition = foodProperties.nutrition();

        // 效果2：吸收生命值（黄心），上限 N × 20
        float absorptionToAdd = Math.min((float) nutrition * n, n * 20.0F);
        if (absorptionToAdd > 0) {
            float currentAbsorption = entity.getAbsorptionAmount();
            entity.setAbsorptionAmount(currentAbsorption + absorptionToAdd);
        }

        // 效果3：额外生命回复
        if (n >= 3) {
            entity.heal(n);
        }
    }

    /**
     * 贪婪 modifier：呼吸恢复/容量/耐力动态调整（基础 2 - N）+ 抢夺/时运
     */
    public static void greedModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int n = getNineHellCount(context);
        modifiers.put(InitAttribute.BREATH_RECOVERY, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - n));
        modifiers.put(InitAttribute.BREATH_CAPACITY, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - n));
        modifiers.put(InitAttribute.ENDURANCE, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - n));
        // 抢夺 + 时运（叠加式）
        int bonus = n >= 3 ? 6 : (n == 2 ? 3 : 1);
        modifiers.put(WAICAttribute.LOOTING, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
        modifiers.put(WAICAttribute.FORTUNE, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
    }

    /**
     * 愤怒 modifier：解毒属性动态调整（基础 2 - N）+ 力量/速度（叠加式）
     */
    public static void wrathModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int n = getNineHellCount(context);
        modifiers.put(InitAttribute.DETOXIFICATION, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - n));
        // 力量 + 速度（叠加式）
        int bonus = n >= 3 ? 6 : (n == 2 ? 3 : 1);
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
     * 罪业2: 药水持续时间额外 +50%（总计 +100%，×2.0）
     * 罪业3: 药水等级 +1（不再加时长）
     */
    public static void heresyMobEffectAdded(LivingEntity entity, MobEffectInstance effectInstance) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(WAICOrgans.HERESY.get())) return;

        int n = data.getOrganCount(WAICItemTagManager.NINE_HELL);
        IMobEffectInstance iEffect = (IMobEffectInstance) effectInstance;

        // 药水持续时间延长，罪业100%+50%，罪业2：100%+50%+100%
        iEffect.setDuration(duration -> (int) (duration * n >= 2 ? 2.5 : 1.5), entity);

        // 药水等级 +1
        if (n >= 3) {
            iEffect.setAmplifier(effectInstance.getAmplifier() + 1, entity);
        }
    }

    /**
     * 暴力 modifier：力量/速度属性动态调整（基础 2 - N）
     * 暴击效果通过事件处理（CommonEventHandler 中 CriticalHitEvent）
     */
    public static void violenceModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int n = getNineHellCount(context);
        modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - n));
        modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - n));
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
        int n = data.getOrganCount(WAICItemTagManager.NINE_HELL);

        if (n >= 3) {
            // 永远暴击 + 暴击伤害 ×4
            event.setCriticalHit(true);
            event.setDamageMultiplier(6.0F);
        } else {
            // 暴击伤害 ×2
            if (event.isCriticalHit()) {
                event.setDamageMultiplier(n == 2 ? 6F : 3F);
            }
        }
    }

    /**
     * 欺诈 modifier：过滤属性动态调整（基础 2 - N）
     * 交易效果通过事件处理（CommonEventHandler 中 TradeWithVillagerEvent / PlayerContainerEvent）
     */
    public static void fraudModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.put(InitAttribute.FILTRATION, OrganAttributeUtil.createAddValueModifier(context.id(), 2 - getNineHellCount(context)));
    }

    /**
     * 欺诈交易效果 - 交易完成时（在 TradeWithVillagerEvent 中调用）
     * N≥1: 交易额外经验
     * N≥3: 交易不缺货（重置使用次数）
     */
    public static void fraudTradeComplete(Player player, MerchantOffer offer) {
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.FRAUD.get())) return;
        int n = data.getOrganCount(WAICItemTagManager.NINE_HELL);

        // 额外经验
        player.giveExperiencePoints(offer.getXp() * 10);
        if (n >= 3) {
            // 不缺货：重置使用次数
            offer.resetUses();
        }
    }

    /**
     * 欺诈交易打折（在 PlayerContainerEvent.Open 中调用）
     * N≥2: 交易打折 30%×(N-1)
     * <p>
     * 仿照原版村庄英雄的打折方式：直接 addToSpecialPriceDiff 追加折扣。
     * 原版在 startTrading 时已先 resetSpecialPrices 清零，再 updateSpecialPrices 施加声望/村庄英雄折扣，
     * 此事件在之后触发，直接追加即可。关闭交易时由原版 resetSpecialPrices 自动还原。
     * </p>
     */
    public static void fraudTradeDiscount(Player player, AbstractContainerMenu container) {
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.FRAUD.get())) return;
        int n = data.getOrganCount(WAICItemTagManager.NINE_HELL);
        if (n < 2) return;

        if (container instanceof MerchantMenu merchantMenu) {
            double discountRate = 0.3 * (n - 1);
            for (MerchantOffer offer : merchantMenu.getOffers()) {
                int discount = (int) Math.floor(discountRate * offer.getBaseCostA().getCount());
                offer.addToSpecialPriceDiff(-Math.max(discount, 1));
            }
        }
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
        int n = getNineHellCount(context);
        float percent = n >= 3 ? 0.09F : (n == 2 ? 0.04F : 0.01F);
        float bonusDamage = target.getMaxHealth() * percent;
        if (bonusDamage > 0) {
            target.hurt(target.damageSources().mobAttack(context.entity()), bonusDamage);
        }
    }

    /**
     * 血肉偶像主动技能：赎罪祭血
     * <p>
     * 使用迭代器逐个遍历负面效果，每有1个负面效果当前生命值折半一次并立即清除该效果。
     * 当清除的是「罪人」效果时，额外减少1层罪孽。
     * </p>
     */
    public static boolean fleshIdolSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return false;

        float health = entity.getHealth();
        for (MobEffectInstance effect : entity.getActiveEffects()) {
            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                health /= 2;
                // 清除罪人效果时，减少1层罪孽
                if (effect.getEffect().is(BossEffects.SINNER) && entity instanceof Player player) {
                    PlayerSins sins = PlayerSins.getPlayerSins(player);
                    sins.setSinnedTimes(Math.max(0, sins.getSinnedTimes() - 1));
                    PlayerSins.setPlayerSins(player, sins);
                }
                entity.removeEffect(effect.getEffect());
            }
        }
        entity.setHealth(Math.max(1, health));
        return true;
    }
}

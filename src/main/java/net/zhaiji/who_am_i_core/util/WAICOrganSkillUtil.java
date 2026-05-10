package net.zhaiji.who_am_i_core.util;

import com.finderfeed.fdbosses.content.entities.chesed_boss.chesed_mini_ray.ChesedMiniRay;
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
import net.minecraft.world.entity.player.Player;
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
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.mixinapi.IMobEffectInstance;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICEffect;
import net.zhaiji.who_am_i_core.task.StraightIntestineTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WAICOrganSkillUtil {
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
        if (WAICOrganUtil.rollResult(entity, 0.3F)) {
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
    public static ItemStack drinkInk(LivingEntity entity, ItemStack stack) {
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
        Item targetDye = WAICOrganUtil.getDyeItemForSchool(schoolType);
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
        float damage = damageContainer.getNewDamage();
        if (damage <= 0) return;
        insertInkToBottle(context.data(), (int) damage, false);
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

        // 手动设置冷却（仅在成功消耗墨水后才冷却）
        OrganSkillUtil.addCooldown(entity, context.stack(), 200);
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
     * 布织泰迪熊：胸腔关闭时，将胸腔内的羊毛转换为随机布织器官
     * <p>
     * 单次遍历收集空槽位和羊毛信息，然后按类型处理：
     * - 单个羊毛（count==1）：原位替换为随机布织器官
     * - 多个羊毛（count>1）：尽可能消耗羊毛填满空槽位
     * 若空槽位 >= count-1：完全消耗，最后1个原位替换
     * 若空槽位 < count-1：只消耗空槽位数量个羊毛
     * </p>
     *
     * @param context 胸腔槽位上下文
     */
    public static void clothTeddyBearChestCavityClose(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = data.getOwner();
        Level level = entity.level();
        if (level.isClientSide()) return;

        // 1. 单次遍历：收集空槽位 + 羊毛信息
        List<Integer> emptySlots = new ArrayList<>();
        List<int[]> woolSlots = new ArrayList<>();

        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (stack.isEmpty()) {
                emptySlots.add(i);
            } else if (stack.is(ItemTags.WOOL)) {
                woolSlots.add(new int[]{
                    i,
                    stack.getCount()
                });
            }
        }

        if (woolSlots.isEmpty()) return;

        // 布织器官候选列表（11种）
        List<Item> clothOrgans = List.of(
            WAICOrgans.CLOTH_HEART.get(),
            WAICOrgans.CLOTH_LUNG.get(),
            WAICOrgans.CLOTH_LIVER.get(),
            WAICOrgans.CLOTH_INTESTINE.get(),
            WAICOrgans.CLOTH_STOMACH.get(),
            WAICOrgans.CLOTH_KIDNEY.get(),
            WAICOrgans.CLOTH_SPLEEN.get(),
            WAICOrgans.CLOTH_SPINE.get(),
            WAICOrgans.CLOTH_RIB.get(),
            WAICOrgans.CLOTH_MUSCLE.get(),
            WAICOrgans.CLOTH_APPENDIX.get()
        );

        int emptyIdx = 0;

        // 2. 处理羊毛
        for (int[] info : woolSlots) {
            int slotIdx = info[0];
            int count = info[1];
            ItemStack stack = data.getStackInSlot(slotIdx);

            if (count == 1) {
                // 单个羊毛：原位替换
                Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                data.setStackInSlot(slotIdx, organ.getDefaultInstance());
            } else {
                // 多个羊毛：尽可能消耗填满空槽位
                int availableEmpty = emptySlots.size() - emptyIdx;
                if (availableEmpty <= 0) continue;

                if (availableEmpty >= count - 1) {
                    // 可以完全消耗：前 (count-1) 个放空槽位，最后1个原位替换
                    for (int j = 0; j < count - 1; j++) {
                        stack.consume(1, entity);
                        Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                        data.setStackInSlot(emptySlots.get(emptyIdx++), organ.getDefaultInstance());
                    }
                    // 最后1个：原位替换
                    stack.consume(1, entity);
                    Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                    data.setStackInSlot(slotIdx, organ.getDefaultInstance());
                } else {
                    // 只能消耗 availableEmpty 个
                    for (int j = 0; j < availableEmpty; j++) {
                        stack.consume(1, entity);
                        Item organ = clothOrgans.get(level.random.nextInt(clothOrgans.size()));
                        data.setStackInSlot(emptySlots.get(emptyIdx++), organ.getDefaultInstance());
                    }
                }
            }
        }
    }

    /**
     * 布织泰迪熊技能：缝补
     * <p>
     * 消耗收纳袋中的羊毛回复生命值。
     * 每个羊毛治疗 4 + clothCount 点生命（clothCount = 胸腔中布织器官数量）。
     * 自动计算最低消耗以尽可能恢复至满血。
     * 5秒冷却（100 tick）。
     * </p>
     *
     * @param context 胸腔槽位上下文
     * @return true 触发冷却
     */
    public static boolean clothTeddyBearSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return false;

        int missingHP = (int) (entity.getMaxHealth() - entity.getHealth());
        if (missingHP <= 0) return false;

        int clothCount = context.data().getOrganCount(WAICItemTagManager.CLOTH_ORGAN);

        BundleContents contents = context.stack().getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        int totalWool = 0;
        for (int i = 0; i < contents.size(); i++) {
            ItemStack stack = contents.getItemUnsafe(i);
            if (stack.is(ItemTags.WOOL)) {
                totalWool += stack.getCount();
            }
        }

        if (totalWool <= 0) return false;

        // 每个羊毛治疗 4 + clothCount 点
        int healPerWool = 4 + clothCount;

        // 计算恢复满血所需的羊毛数量（向上取整）
        int woolNeeded = (missingHP + healPerWool - 1) / healPerWool;
        int woolToUse = Math.min(woolNeeded, totalWool);

        int actualHeal = woolToUse * healPerWool;

        // 扣除羊毛
        for (int i = 0; i < contents.size() && woolToUse > 0; i++) {
            ItemStack stack = contents.getItemUnsafe(i);
            if (stack.is(ItemTags.WOOL)) {
                int consume = Math.min(woolToUse, stack.getCount());
                stack.consume(consume, entity);
                woolToUse -= consume;
            }
        }

        entity.heal(actualHeal);
        return true;
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

    // ==================== FDBosses 器官 ====================

    /**
     * 慈悲被动：闪电射线
     * <p>
     * 攻击时召唤Chesed闪电射线，自动追踪目标并造成武器伤害100%的魔法伤害+感电效果。
     * 冷却时间1秒（20tick），通过 OrganSkillUtil 检测和设置冷却。
     * </p>
     */
    public static void chesedAttack(
        ChestCavitySlotContext context, LivingEntity target,
        DamageSource source, DamageContainer damageContainer
    ) {
        LivingEntity entity = context.entity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        // 检测冷却
        if (OrganSkillUtil.hasCooldown(entity, context.stack())) return;
        // 召唤闪电射线
        ChesedMiniRay.summon(level, target, entity.getMainHandItem(), entity);
        // 设置冷却 20 tick（1秒）
        OrganSkillUtil.addCooldown(entity, context.stack(), 20);
    }

    /**
     * 严厉被动：罪恶审判
     * <p>
     * 攻击拥有负面效果的目标时，额外造成目标最大生命值×3%×负面效果数量的伤害。
     * </p>
     */
    public static void geburahAttack(
        ChestCavitySlotContext context, LivingEntity target,
        DamageSource source, DamageContainer damageContainer
    ) {
        int harmfulCount = 0;
        for (MobEffectInstance effect : target.getActiveEffects()) {
            if (effect instanceof IMobEffectInstance instance && instance.isHarmful()) {
                harmfulCount++;
            }
        }
        if (harmfulCount > 0) {
            float bonusDamage = target.getMaxHealth() * 0.03F * harmfulCount;
            damageContainer.setNewDamage(damageContainer.getNewDamage() + bonusDamage);
        }
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
        CompoundTag tag = new CompoundTag();
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
     * 从蓄能模块中提取电荷（按比例从各模块扣除）
     * 内部处理充能肌束余电回收
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
        for (ItemStack module : modules) {
            float moduleCharge = getModuleCharge(module);
            float extract = Math.min(remaining, moduleCharge);
            if (extract > 0) {
                setModuleCharge(module, moduleCharge - extract);
                remaining -= extract;
            }
            if (remaining <= 0) break;
        }
        float extracted = toExtract - remaining;
        // 充能肌束余电回收
        if (extracted > 0 && data.hasOrgan(WAICOrgans.CHARGED_MUSCLE.get())) {
            float healRate = isOverloadMode(entity) ? 0.20f : 0.10f;
            entity.heal(extracted * healRate);
        }
        return extracted;
    }

    /**
     * 消耗电荷（含充能肌束回路返还逻辑）
     */
    public static float consumeCharge(ChestCavityData data, LivingEntity entity, float amount, boolean simulate) {
        float extracted = extractCharge(data, entity, amount, simulate);
        if (extracted > 0 && !simulate && data.hasOrgan(WAICOrgans.CHARGED_MUSCLE.get())) {
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
     */
    public static boolean conductiveSpineSkill(ChestCavitySlotContext context) {
        ChestCavityData data = context.data();
        LivingEntity entity = context.entity();
        List<ItemStack> modules = collectEnergyModules(data);
        if (modules.isEmpty()) return false;
        float maxCharge = getMaxCharge(modules);
        float activationCost = maxCharge / 2;
        float currentCharge = getCharge(modules);
        if (currentCharge < activationCost) return false;
        consumeCharge(data, entity, activationCost, false);
        entity.addEffect(new MobEffectInstance(WAICEffect.OVERLOAD, 200));
        return true;
    }

    /**
     * 检查对称位置是否存在导流肋骨
     */
    public static boolean hasSymmetricCurrentRib(ChestCavityData data, int index) {
        int symmetricIndex = WAICOrganUtil.getSymmetricRibIndex(index);
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
}

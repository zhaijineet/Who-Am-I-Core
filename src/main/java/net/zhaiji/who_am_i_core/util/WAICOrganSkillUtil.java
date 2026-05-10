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
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
}

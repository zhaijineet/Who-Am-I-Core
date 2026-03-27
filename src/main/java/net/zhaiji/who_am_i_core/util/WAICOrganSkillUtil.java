package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.item.InkItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.task.StraightIntestineTask;

import java.util.ArrayList;
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
     * 添加或减少墨水瓶中的墨水量
     *
     * @param data     胸腔数据
     * @param amount   要添加（正数）或减少（负数）的墨水量
     * @param capacity 墨水瓶容量
     * @return 返回值为正数时表示溢出量（添加过多），为负数时表示还差多少量（减少过多）
     */
    public static int addInkToBottle(ChestCavityData data, int amount, int capacity) {
        if (amount == 0) return 0;
        // 收集所有墨水瓶
        List<ItemStack> inkBottles = new ArrayList<>();
        for (ItemStack organ : data.getOrgans()) {
            if (organ.is(WAICOrgans.INK_BOTTLE.get())) {
                inkBottles.add(organ);
            }
        }
        if (inkBottles.isEmpty()) return amount;
        // 处理正数：添加墨水
        if (amount > 0) {
            for (ItemStack inkBottle : inkBottles) {
                CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag tag = customData.copyTag();
                int currentInk = tag.contains("ink") ? tag.getInt("ink") : 0;
                int space = capacity - currentInk;
                int addAmount = Math.min(space, amount);
                tag.putInt("ink", currentInk + addAmount);
                inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                amount -= addAmount;
                if (amount <= 0) break;
            }
        } else {
            // 处理负数：减少墨水
            for (ItemStack inkBottle : inkBottles) {
                CustomData customData = inkBottle.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag tag = customData.copyTag();
                int currentInk = tag.contains("ink") ? tag.getInt("ink") : 0;
                int removeAmount = Math.min(currentInk, -amount);
                tag.putInt("ink", currentInk - removeAmount);
                inkBottle.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                amount += removeAmount;
                if (amount >= 0) break;
            }
        }
        return amount; // 正数表示溢出, 负数表示还差多少，零表示正好装满
    }

    /**
     * 添加或减少墨水瓶中的墨水量
     *
     * @param data   胸腔数据
     * @param amount 要添加（正数）或减少（负数）的墨水量
     * @return 返回值为正数时表示溢出量（添加过多），为负数时表示还差多少量（减少过多）
     */
    public static int addInkToBottle(ChestCavityData data, int amount) {
        return addInkToBottle(data, amount, 1000);
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
        addInkToBottle(data, value);
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
        addInkToBottle(context.data(), (int) damage);
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
}

package net.zhaiji.who_am_i_core.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.task.DragonBreathCastingTask;
import net.zhaiji.who_am_i_core.task.HydraLungBreathTask;
import net.zhaiji.who_am_i_core.task.HydraSpleenTask;

/**
 * 龙吐息工具类
 * 使用自定义 Task 系统实现持续性施法
 * <p>
 * 实现说明：
 * - 使用 Task 系统管理持续性施法，不触发施法动作和音效
 * - 不添加移速限制
 * - 直接复用 ISNB 的投射物类进行伤害检测
 * - 客户端/服务器端分离，避免重复调用
 */
public class IceAndFireOrganhUtil {
    /**
     * 最高10级法术
     */
    private static final int MAX_SPELL_LEVEL = 10;
    /**
     * 默认持续时间（5秒 = 100 ticks）
     */
    private static final int DEFAULT_DURATION_TICKS = 100;

    /**
     * 计算法术等级（每1个器官提升1级，最高10级）
     * 1个器官=1级，2个器官=2级，...10个器官=10级
     * 0个器官则无法施法（返回0，低于最小有效等级1）
     */
    private static int getSpellLevel(ChestCavityData data, TagKey<Item> organTag) {
        return Math.min(data.getOrganCount(organTag), MAX_SPELL_LEVEL);
    }

    /**
     * 火龙吐息 - 释放火焰吐息
     */
    public static void fireDragonBreath(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        if (level.isClientSide) {
            return;  // 客户端不处理
        }

        ChestCavityData data = context.data();
        int spellLevel = getSpellLevel(data, WAICItemTagManager.FIRE_DRAGON);

        if (spellLevel < 1) {
            return;  // 0级无法施法
        }

        // 创建持续性施法任务（持续3秒 = 60 ticks）
        DragonBreathCastingTask task = new DragonBreathCastingTask(
            data,
            DragonBreathCastingTask.BreathType.FIRE_BREATH,
            spellLevel,
            DEFAULT_DURATION_TICKS
        );
        data.addTask(task);
    }

    /**
     * 冰龙吐息 - 释放寒冰锥
     */
    public static void iceDragonBreath(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        if (level.isClientSide) {
            return;  // 客户端不处理
        }

        ChestCavityData data = context.data();
        int spellLevel = getSpellLevel(data, WAICItemTagManager.ICE_DRAGON);

        if (spellLevel < 1) {
            return;  // 0级无法施法
        }

        // 创建持续性施法任务（持续3秒 = 60 ticks）
        DragonBreathCastingTask task = new DragonBreathCastingTask(
            data,
            DragonBreathCastingTask.BreathType.ICE_BREATH,
            spellLevel,
            DEFAULT_DURATION_TICKS
        );
        data.addTask(task);
    }

    /**
     * 电龙吐息 - 释放电刑
     */
    public static void lightningDragonBreath(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        if (level.isClientSide) {
            return;  // 客户端不处理
        }

        ChestCavityData data = context.data();
        int spellLevel = getSpellLevel(data, WAICItemTagManager.LIGHTNING_DRAGON);

        if (spellLevel < 1) {
            return;  // 0级无法施法
        }

        // 创建持续性施法任务（持续3秒 = 60 ticks）
        DragonBreathCastingTask task = new DragonBreathCastingTask(
            data,
            DragonBreathCastingTask.BreathType.LIGHTNING_BREATH,
            spellLevel,
            DEFAULT_DURATION_TICKS
        );
        data.addTask(task);
    }

    /**
     * 九头蛇脾脏添加回调
     * 唯一效果器官，检查是否已存在Task，避免重复创建
     */
    public static void hydraSpleenAdded(ChestCavitySlotContext slotContext) {
        ChestCavityData data = slotContext.data();
        // 检查是否已存在Task
        for (IChestCavityTask task : data.getTasks()) {
            if (task instanceof HydraSpleenTask && !task.canRemove(slotContext.entity())) {
                return;
            }
        }
        data.addTask(new HydraSpleenTask(data));
    }

    /**
     * 九头蛇肺毒物吐息 - 消耗中毒效果释放毒物吐息
     * <p>
     * 技能效果：
     * 1. 获取玩家当前的中毒效果（时长和等级）
     * 2. 移除玩家的中毒效果
     * 3. 吐息持续时间 = log10(中毒时长) 秒（转换为tick）
     * 4. 伤害频率 = 每4 tick一次
     * 5. 单次伤害 = 中毒等级 + 1
     * 6. 受影响的敌人施加原始中毒效果（时长 = 原始时长，等级 = 原始等级）
     * </p>
     *
     * @param context 胸腔槽位上下文
     */
    public static void hydraLungBreath(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        // 获取玩家当前的中毒效果
        MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
        if (poison == null || poison.getDuration() <= 0) return;

        ChestCavityData data = context.data();
        int poisonDuration = poison.getDuration();
        int poisonAmplifier = poison.getAmplifier();
        // 移除玩家的中毒效果
        entity.removeEffect(MobEffects.POISON);
        // 创建毒物吐息任务
        HydraLungBreathTask task = new HydraLungBreathTask(poisonAmplifier, poisonDuration);
        data.addTask(task);
    }
}

package net.zhaiji.who_am_i_core.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.task.DragonBreathCastingTask;

/**
 * 龙吐息工具类
 * 使用自定义 Task 系统实现持续性施法
 *
 * 实现说明：
 * - 使用 Task 系统管理持续性施法，不触发施法动作和音效
 * - 不添加移速限制
 * - 直接复用 ISNB 的投射物类进行伤害检测
 * - 客户端/服务器端分离，避免重复调用
 */
public class IceAndFireOrganhUtil {
    /** 最高10级法术 */
    private static final int MAX_SPELL_LEVEL = 10;
    /** 默认持续时间（5秒 = 100 ticks） */
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
}

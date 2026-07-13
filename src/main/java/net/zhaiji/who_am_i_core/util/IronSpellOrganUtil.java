package net.zhaiji.who_am_i_core.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;

import java.util.List;

public class IronSpellOrganUtil {

    /**
     * 腐败魂灯
     */
    public static void corruptedSoulLanternSoulHarvest(LivingEntity dead, Level level) {
        float totalBile = dead.getMaxHealth();
        if (totalBile <= 0) return;

        AABB searchBox = dead.getBoundingBox().inflate(16);
        List<LivingEntity> lanternHolders = level.getEntitiesOfClass(
            LivingEntity.class,
            searchBox,
            nearby -> nearby != dead && ChestCavityUtil.getData(nearby).hasOrgan(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get())
        );

        if (lanternHolders.isEmpty()) return;

        float share = totalBile / lanternHolders.size();
        for (LivingEntity holder : lanternHolders) {
            HumoursData.insertBlackBile(holder, share, false);
        }
    }

    /**
     * 尸王脊柱：消耗等额黑胆汁吸收最高80%伤害
     * 吸收上限随魔法器官数量缩放
     * 返回实际吸收的伤害值，应加入 block
     */
    public static float deadKingSpineHurt(LivingEntity entity, float damage) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(IronSpellOrgans.DEAD_KING_SPINE.get())) return 0;
        int magicCount = data.getOrganCount(WAICItemTagManager.MAGIC);
        float maxAbsorb = damage * Math.min(0.8F, 0.3F + magicCount * 0.03F);
        float available = HumoursData.get(entity).getBlackBile();
        float toAbsorb = Math.min(maxAbsorb, available);
        if (toAbsorb > 0) {
            HumoursData.extractBlackBile(entity, toAbsorb, false);
        }
        return toAbsorb;
    }

    /**
     * 尸王肋骨：器官安装时增加50点黑胆汁上限
     */
    public static void deadKingRibAdded(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        HumoursData.addMaxBlackBile(entity, 50);
    }

    /**
     * 尸王肋骨：器官移除时减少50点黑胆汁上限
     */
    public static void deadKingRibRemoved(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        HumoursData.addMaxBlackBile(entity, -50);
    }
}

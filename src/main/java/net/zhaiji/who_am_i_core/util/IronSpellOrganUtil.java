package net.zhaiji.who_am_i_core.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;

import java.util.ArrayList;
import java.util.List;

public class IronSpellOrganUtil {
    /**
     * 腐败魂灯灵魂收割：死亡实体 16 格内所有拥有腐败魂灯的 LivingEntity 均分黑胆汁
     */
    public static void corruptedSoulLanternSoulHarvest(LivingEntity dead, Level level) {
        float totalBile = dead.getMaxHealth();
        if (totalBile <= 0) return;

        AABB searchBox = dead.getBoundingBox().inflate(16);
        List<LivingEntity> lanternHolders = new ArrayList<>();
        for (LivingEntity nearby : level.getEntitiesOfClass(LivingEntity.class, searchBox)) {
            if (nearby.isAlive() && ChestCavityUtil.getData(nearby).hasOrgan(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get())) {
                lanternHolders.add(nearby);
            }
        }
        if (lanternHolders.isEmpty()) return;

        float share = totalBile / lanternHolders.size();
        for (LivingEntity holder : lanternHolders) {
            HumoursData.insertBlackBile(holder, share, false);
        }
    }

    /**
     * 尸王脊柱：消耗等额黑胆汁吸收最高50%伤害
     * 返回实际吸收的伤害值，应加入 block
     */
    public static float deadKingSpineHurt(LivingEntity entity, float damage) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(IronSpellOrgans.DEAD_KING_SPINE.get())) return 0;
        float maxAbsorb = damage * 0.5F;
        float available = HumoursData.get(entity).getBlackBile();
        float toAbsorb = Math.min(maxAbsorb, available);
        if (toAbsorb > 0) {
            HumoursData.extractBlackBile(entity, toAbsorb, false);
        }
        return toAbsorb;
    }

    /**
     * 尸王肋骨：器官安装时增加10点黑胆汁上限
     */
    public static void deadKingRibAdded(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        HumoursData.addMaxBlackBile(entity, 10);
    }

    /**
     * 尸王肋骨：器官移除时减少10点黑胆汁上限
     */
    public static void deadKingRibRemoved(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        HumoursData.addMaxBlackBile(entity, -10);
    }
}

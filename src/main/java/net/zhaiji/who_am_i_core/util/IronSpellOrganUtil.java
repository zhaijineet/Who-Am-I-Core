package net.zhaiji.who_am_i_core.util;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttachment;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;

import java.util.List;

public class IronSpellOrganUtil {
    /**
     * 腐败魂灯主动技能：吸收亡灵
     * - 获取半径5格内所有亡灵生物
     * - 将其生命值1:1转化为黑胆汁
     * - 溢出部分1:1扣除玩家生命值（最低剩1HP）
     * - 无亡灵时不触发也不冷却
     */
    public static boolean corruptedPhylacterySkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return false;

        // 查找半径5格内的亡灵生物（服务端专属）
        List<LivingEntity> undead = entity.level().getEntitiesOfClass(
            LivingEntity.class,
            entity.getBoundingBox().inflate(5),
            target -> target != entity && target.getType().is(EntityTypeTags.UNDEAD)
        );
        if (undead.isEmpty()) return false;

        float totalHealthAbsorbed = 0;

        for (LivingEntity target : undead) {
            totalHealthAbsorbed += target.getHealth();
            target.kill();
        }

        // 先尽可能填入黑胆汁（静态方法自动同步）
        float inserted = HumoursData.insertBlackBile(entity, totalHealthAbsorbed, false);
        float overflow = totalHealthAbsorbed - inserted;

        // 溢出部分扣除生命值（最低1HP）
        if (overflow > 0) {
            float currentHealth = entity.getHealth();
            float newHealth = Math.max(1.0F, currentHealth - overflow);
            entity.setHealth(newHealth);
        }

        return true;
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
        if (entity == null) return;
        HumoursData.setMaxBlackBile(entity, HumoursData.get(entity).getMaxBlackBile() + 10);
    }

    /**
     * 尸王肋骨：器官移除时减少10点黑胆汁上限
     */
    public static void deadKingRibRemoved(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity == null) return;
        HumoursData.setMaxBlackBile(entity, HumoursData.get(entity).getMaxBlackBile() - 10);
    }
}

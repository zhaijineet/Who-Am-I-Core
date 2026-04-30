package net.zhaiji.who_am_i_core.util;

import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;

public class CataclysmOrganUtil {
    /**
     * 涛浪提灯攻击回调
     * 1. 消耗所有当前粘液，增加等额伤害
     * 2. 消耗的粘液 >= 30 时，额外召唤水浪
     */
    public static void tidalLanternAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        LivingEntity entity = context.entity();
        // 非对自己造成伤害
        if (entity == null || entity == target) return;

        float extracted = HumoursData.extractPhlegm(entity, HumoursData.get(entity).getPhlegm(), false);
        if (extracted <= 0) return;

        // 伤害修改
        float currentDamage = damageContainer.getNewDamage();
        damageContainer.setNewDamage(currentDamage + extracted);

        Level level = entity.level();

        // 水浪召唤
        if (!level.isClientSide()) {
            if (extracted >= 30) {
                float yRot = entity.getYRot();
                double spawnX = entity.getX();
                double spawnY = entity.getY();
                double spawnZ = entity.getZ();

                // 召唤3波水浪，以攻击者朝向为中心，扇形分布
                float[] angles = {
                    -30.0F,
                    0.0F,
                    30.0F
                };
                for (float angleOffset : angles) {
                    Wave_Entity wave = new Wave_Entity(level, entity, 80, extracted);
                    float waveYRot = yRot + angleOffset;
                    wave.setPos(spawnX, spawnY, spawnZ);
                    wave.setYRot(waveYRot);
                    wave.setState(1);
                    level.addFreshEntity(wave);
                }
            }
        }
    }

    /**
     * 风暴脊柱减伤技能（唯一效果）
     * 通过 CommonEventHandler 中的全局事件调用，hasOrgan 保证多脊柱只生效一次
     * 1. 如果粘液已满，返回 0（失效）
     * 2. 吸收伤害的20%转化为粘液，单次上限10点
     *
     * @return 吸收的伤害值，应加入 block
     */
    public static float stormSpineHurt(LivingEntity entity, float damage) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(CataclysmOrgans.STORM_SPINE.get())) return 0;

        // 粘液达到上限时失效
        if (HumoursData.get(entity).isPhlegmFull()) return 0;

        float absorbAmount = Math.max(damage * 0.2F, 10.0F);

        // 转化为粘液（静态方法自动同步）
        HumoursData.insertPhlegm(entity, absorbAmount, false);

        return absorbAmount;
    }

    /**
     * 风暴肋骨安装回调：增加10点粘液上限
     */
    public static void stormRibAdded(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity == null) return;
        HumoursData.setMaxPhlegm(entity, HumoursData.get(entity).getMaxPhlegm() + 10);
    }

    /**
     * 风暴肋骨移除回调：减少10点粘液上限
     */
    public static void stormRibRemoved(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity == null) return;
        HumoursData.setMaxPhlegm(entity, HumoursData.get(entity).getMaxPhlegm() - 10);
    }
}

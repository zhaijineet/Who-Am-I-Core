package net.zhaiji.who_am_i_core.util;

import com.finderfeed.fdbosses.content.entities.chesed_boss.chesed_mini_ray.ChesedMiniRay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.mixinapi.IMobEffectInstance;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;

public class FDBossesOrganUtil {
    /**
     * 慈悲被动：闪电射线
     * <p>
     * 攻击时召唤Chesed闪电射线追踪目标，造成玩家最大生命值33%的伤害并施加感电效果。
     * 冷却3秒（60tick），通过 OrganSkillUtil 检测和设置冷却。
     * </p>
     */
    public static void chesedAttack(
        ChestCavitySlotContext context, LivingEntity target,
        DamageSource source, DamageContainer damageContainer
    ) {
        if (OrganUtil.isSelfDamage(target, source)) return;

        LivingEntity entity = context.entity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        // 检测冷却
        if (OrganSkillUtil.hasCooldown(entity, context.stack())) return;
        // 召唤闪电射线
        ChesedMiniRay.summon(level, target, context.stack(), entity);
        // 设置冷却 60 tick（3秒）
        OrganSkillUtil.addCooldown(entity, context.stack(), 60);
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
        if (OrganUtil.isSelfDamage(target, source)) return;

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

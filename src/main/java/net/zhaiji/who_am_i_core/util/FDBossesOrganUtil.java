package net.zhaiji.who_am_i_core.util;

import com.finderfeed.fdbosses.content.entities.chesed_boss.chesed_mini_ray.ChesedMiniRay;
import com.finderfeed.fdbosses.content.entities.geburah.sins.attachment.PlayerSins;
import com.finderfeed.fdbosses.init.BossEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.mixinapi.IMobEffectInstance;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;

import java.util.Iterator;

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

    /**
     * 血肉偶像主动技能：赎罪祭血
     * <p>
     * 使用迭代器逐个遍历负面效果，每有1个负面效果当前生命值折半一次并立即清除该效果。
     * 当清除的是「罪人」效果时，额外减少1层罪孽。
     * </p>
     */
    public static boolean fleshIdolSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return false;

        float health = entity.getHealth();
        Iterator<MobEffectInstance> iterator = entity.getActiveEffects().iterator();
        while (iterator.hasNext()) {
            MobEffectInstance effect = iterator.next();
            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                health /= 2;
                // 清除罪人效果时，减少1层罪孽
                if (effect.getEffect().is(BossEffects.SINNER) && entity instanceof Player player) {
                    PlayerSins sins = PlayerSins.getPlayerSins(player);
                    sins.setSinnedTimes(Math.max(0, sins.getSinnedTimes() - 1));
                    PlayerSins.setPlayerSins(player, sins);
                }
                entity.removeEffect(effect.getEffect());
            }
        }
        entity.setHealth(Math.max(1, health));
        return true;
    }
}

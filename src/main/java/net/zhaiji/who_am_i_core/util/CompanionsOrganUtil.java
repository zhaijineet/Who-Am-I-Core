package net.zhaiji.who_am_i_core.util;

import dev.xylonity.companions.common.entity.projectile.HolinessStartProjectile;
import dev.xylonity.companions.common.entity.projectile.PontiffFireRingProjectile;
import dev.xylonity.companions.registry.CompanionsEntities;
import dev.xylonity.companions.registry.CompanionsSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import net.zhaiji.who_am_i_core.util.OrganUtil;

public class CompanionsOrganUtil {

    /**
     * 教宗心脏 — 圣化变身（hurt 回调）
     * <p>
     * 受伤后血量 ≤ 30% 时触发：
     * 回复 50% 最大生命，获得力量II + 抗性II + 速度II（15秒）
     * 冷却 3 分钟（3600 tick），通过玩家物品冷却机制管理
     */
    public static void pontiffHeartHurt(ChestCavitySlotContext context, DamageSource damageSource, DamageContainer damageContainer) {
        LivingEntity entity = context.entity();
        if (OrganUtil.isSelfDamage(entity, damageSource)) return;
        if (!(entity instanceof Player player)) return;
        if (OrganSkillUtil.hasCooldown(player, context.stack())) return;
        if (entity.getHealth() > entity.getMaxHealth() * 0.3F) return;
        // 触发圣化变身
        entity.heal(entity.getMaxHealth() * 0.5F);
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 1));

        // 设置 3 分钟冷却
        OrganSkillUtil.addCooldown(player, context.stack(), 3 * 60 * 20);
    }

    /**
     * 教宗脾脏 — 圣火环阵（skill 回调）
     * <p>
     * 以自身为中心释放向外扩展的火环
     */
    public static boolean pontiffSpleenSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        Vec3 spawnPos = entity.position();

        PontiffFireRingProjectile ring = CompanionsEntities.PONTIFF_FIRE_RING.get().create(level);
        if (ring == null) return false;

        ring.setOwner(entity);
        ring.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, entity.getYRot(), entity.getXRot());
        level.addFreshEntity(ring);

        if (level instanceof ServerLevel serverLevel) {
            RandomSource random = serverLevel.getRandom();
            for (int i = 0; i < 7; ++i) {
                double velX = random.nextFloat() / 2.0;
                double velY = 5.0E-5;
                double velZ = random.nextFloat() / 2.0;
                serverLevel.sendParticles(ParticleTypes.LAVA, spawnPos.x, spawnPos.y + 0.05, spawnPos.z, 1, velX, velY, velZ, 0.0);
            }
            serverLevel.sendParticles(ParticleTypes.CLOUD, spawnPos.x, spawnPos.y + 0.1, spawnPos.z, 6, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, spawnPos.x, spawnPos.y + 0.1, spawnPos.z, 2, 0.01, 0.01, 0.01, 0.05);
        }

        level.playSound(
            null,
            entity.getOnPos(),
            CompanionsSounds.PONTIFF_FRONT_ATTACK.get(),
            SoundSource.PLAYERS,
            3F,
            1F
        );

        return true;
    }

    /**
     * 教宗阑尾 — 圣星裁决（skill 回调）
     * <p>
     * 投掷追踪最近敌人的星弹（使用 Companions 的 HolinessStartProjectile）
     * 正温度 → 红色星（点燃）；负温度/无温度 → 蓝色星（冻结）
     */
    public static boolean pontiffAppendixSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        // 视线索敌：沿视线方向射线检测，只命中玩家正在看的实体
        int distance = 16;
        HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(entity, checkEntity -> checkEntity != entity, distance);
        if (!(hitResult instanceof EntityHitResult entityHitResult)) return false;
        if (!(entityHitResult.getEntity() instanceof LivingEntity target)) return false;

        boolean isPositiveTemp = OrganUtil.getEffectiveTemperature(entity) >= 0;

        HolinessStartProjectile star = CompanionsEntities.HOLINESS_STAR.get().create(level);
        if (star == null) return false;

        star.setPos(entity.getEyePosition());
        star.setOwner(entity);
        star.setTarget(target);
        star.setRed(isPositiveTemp);
        star.setNoGravity(true);

        Vec3 dir = target.getEyePosition().subtract(entity.getEyePosition()).normalize().scale(HolinessStartProjectile.SPEED);
        star.setDeltaMovement(dir);
        level.addFreshEntity(star);

        return true;
    }
}

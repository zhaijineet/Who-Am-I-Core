package net.zhaiji.who_am_i_core.util;

import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.api.function.OrganModifierConsumer;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.EntityRelationUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import net.zhaiji.who_am_i_core.api.UseCondition;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.task.DeathLensTask;
import net.zhaiji.who_am_i_core.task.MechanicalStarTask;


public class CataclysmOrganUtil {
    // ==================== 利维坦器官 ====================

    /**
     * 利维坦鳃
     * 以自身为中心引发震荡，AoE伤害+黑暗+击退，水中释放时获得加成
     */
    public static boolean leviathanGill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        int count = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.LEVIATHAN);
        boolean inWater = entity.isInWater();
        float radius = 6.0F + count * 0.5F + (inWater ? 2.0F : 0.0F);
        float damage = (6.0F + count * 1.5F) * (inWater ? 1.5F : 1.0F);

        ScreenShake_Entity.ScreenShake(level, entity.position(), 30, 0.1F, 60, 10);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                new RoarParticleOptions(60, 102, 26, 204, 0.9F, 1F, 1.2F, 13F),
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                1,
                0,
                0,
                0,
                0
            );
        }
        level.playSound(
            null,
            entity,
            ModSounds.LEVIATHAN_ROAR.get(),
            SoundSource.PLAYERS,
            3F,
            1F
        );

        // AoE 伤害 + 黑暗 + 击退
        DamageSource damageSource = level.damageSources().mobAttack(entity);
        AABB aabb = entity.getBoundingBox().inflate(radius);
        for (LivingEntity target : level.getEntitiesOfClass(
            LivingEntity.class,
            aabb,
            target -> EntityRelationUtil.shouldAoeDamage(entity, target)
        )) {
            if (target.hurt(damageSource, damage)) {
                target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100));
                double dx = target.getX() - entity.getX();
                double dz = target.getZ() - entity.getZ();
                double dist = Math.max(dx * dx + dz * dz, 0.001D);
                target.push(dx / dist * 5.0D, 0.3D, dz / dist * 5.0D);
                target.hurtMarked = true;
            }
        }

        return true;
    }

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
        // 排除自伤
        if (OrganUtil.isSelfDamage(target, source)) return;

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

                int waveDuration = 60 + context.data().getOrganCount(WAICItemTagManager.SCYLLA) * 20;

                // 召唤3波水浪，以攻击者朝向为中心，扇形分布
                float[] angles = {
                    -30.0F,
                    0.0F,
                    30.0F
                };
                for (float angleOffset : angles) {
                    Wave_Entity wave = new Wave_Entity(level, entity, waveDuration, extracted);
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
     * 风暴脊柱减伤技能（唯一效果，通过 CommonEventHandler 全局事件调用）
     * 吸收伤害（比例与上限随防御属性缩放）转化为粘液，粘液满时失效
     *
     * @return 吸收的伤害值，应加入 block
     */
    public static float stormSpineHurt(LivingEntity entity, float damage) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (!data.hasOrgan(CataclysmOrgans.STORM_SPINE.get())) return 0;

        // 粘液达到上限时失效
        if (HumoursData.get(entity).isPhlegmFull()) return 0;

        float absorbAmount = Math.min(
            damage * (0.15F + (float) (entity.getAttributeValue(InitAttribute.DEFENSE) * 0.005)),
            5.0F + (float) (entity.getAttributeValue(InitAttribute.DEFENSE) * 0.5)
        );

        // 转化为粘液（静态方法自动同步）
        return HumoursData.insertPhlegm(entity, absorbAmount, false);
    }

    /**
     * 风暴肋骨安装回调：增加10点粘液上限
     */
    public static void stormRibAdded(ChestCavitySlotContext context) {
        HumoursData.addMaxPhlegm(context.entity(), 10);
    }

    /**
     * 风暴肋骨移除回调：减少10点粘液上限
     */
    public static void stormRibRemoved(ChestCavitySlotContext context) {
        HumoursData.addMaxPhlegm(context.entity(), -10);
    }

    // ==================== 焰魔器官 ====================

    /**
     * 不灭薪火属性修饰符 - 炽焰器官数量直接作为力量加成，冰火冲突时为负值减益
     */
    public static void undyingEmberModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int fireOrganCount = OrganUtil.getFireOrganCount(context);
        modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), fireOrganCount));
    }

    /**
     * 焰魔肋甲属性修饰符 - 以自身槽位为中心3×3范围内的炽焰器官数量直接作为防御加成，冰火冲突时为负值减益
     */
    public static void ignitedRibPlatingModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int localFireOrganCount = OrganUtil.getLocalFireOrganCount(context);
        modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), localFireOrganCount));
    }

    /**
     * 炽面甲 — 炽热烙印攻击回调
     * 近战命中施加炽热烙印效果，根据局部炽焰器官数量回血
     */
    public static void blazingVisageAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        LivingEntity entity = context.entity();
        if (OrganUtil.isSelfDamage(target, source)) return;

        // 根据局部炽焰器官数量计算回血量（冰火冲突时为负值，max 兜底为 0）
        int localFireOrganCount = OrganUtil.getLocalFireOrganCount(context);
        float healAmount = Math.max(0, 1.0F + (float) Math.floor(localFireOrganCount * 0.5));

        // 若有炽热烙印，回血量翻倍
        if (target.getEffect(ModEffect.EFFECTBLAZING_BRAND) != null) {
            healAmount *= 2;
        }

        // 施加炽热烙印效果（5秒）
        target.addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND, 5 * 20, 0));

        entity.heal(healAmount);
    }

    // ==================== 下界合金巨兽器官 ====================

    /**
     * 巨兽炉心安装回调：增加100点黄胆汁上限
     */
    public static void monstrosityCoreAdded(ChestCavitySlotContext context) {
        HumoursData.addMaxYellowBile(context.entity(), 100);
    }

    /**
     * 巨兽炉心移除回调：减少100点黄胆汁上限
     */
    public static void monstrosityCoreRemoved(ChestCavitySlotContext context) {
        HumoursData.addMaxYellowBile(context.entity(), -100);
    }

    /**
     * 巨兽炉心tick回调：每20 tick将炽焰器官数量转化为黄胆汁
     */
    public static void monstrosityCoreTick(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return;
        if (entity.tickCount % 20 != 0) return;
        int fireOrganCount = OrganUtil.getFireOrganCount(context);
        if (fireOrganCount > 0) {
            HumoursData.insertYellowBile(entity, fireOrganCount * 0.1F, false);
        }
    }

    /**
     * 巨兽熔炉 — 饮用岩浆桶或熔岩动力电池：恢复饥饿值与饱和度，固定增加 100 黄胆汁，获得骇人之恶（60秒，等级随巨兽器官数量，每个巨兽器官+1级）
     */
    public static ItemStack drinkLava(LivingEntity entity, ItemStack stack, UseCondition condition) {
        if (entity.isShiftKeyDown()) return stack;

        ChestCavityData data = ChestCavityUtil.getData(entity);
        int monstrosityCount = data.getOrganCount(WAICItemTagManager.MONSTROSITY);
        HumoursData.insertYellowBile(entity, 100, false);
        entity.addEffect(new MobEffectInstance(ModEffect.EFFECTMONSTROUS, 60 * 20, Math.max(0, monstrosityCount - 1)));

        if (entity instanceof Player player) {
            player.getFoodData().eat(20, 0.5F);
            if (player.hasInfiniteMaterials()) return stack;
            ItemStack craftingRemaining = stack.getCraftingRemainingItem();
            stack.consume(1, player);
            if (craftingRemaining.isEmpty()) return stack;
            if (!player.getInventory().add(craftingRemaining)) {
                player.drop(craftingRemaining, false);
            }
            return stack;
        }
        ItemStack remaining = stack.getCraftingRemainingItem();
        stack.consume(1, entity);
        return remaining;
    }

    /**
     * 巨兽回路 — 地震践踏
     * 消耗100点黄胆汁，AoE伤害+击飞（对齐 Cataclysm 原版下界合金巨兽撼地猛击）
     */
    public static boolean monstrosityCircuit(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();

        // 模拟提取100点黄胆汁，检查是否足够
        float simulated = HumoursData.extractYellowBile(entity, 100, true);
        if (simulated < 100) return false;

        // 实际消耗
        float consumed = HumoursData.extractYellowBile(entity, 100, false);

        Level level = entity.level();

        // 视觉效果：震屏 + 爆炸音效（对齐原版 EarthQuake）
        ScreenShake_Entity.ScreenShake(level, entity.position(), 20, 0.3f, 0, 20);
        level.playSound(
            null,
            entity.getOnPos(),
            ModSounds.EXPLOSION.get(),
            SoundSource.PLAYERS,
            1.5f,
            1.0F + entity.getRandom().nextFloat() * 0.1F
        );

        int fireOrganCount = OrganUtil.getFireOrganCount(context);
        float damage = Math.max(0, 20 + fireOrganCount * 0.05F * entity.getMaxHealth());

        // AoE伤害：半径6.25格（对齐原版 EarthQuake(6.25D)）
        DamageSource damagesource = level.damageSources().mobAttack(entity);
        AABB aabb = entity.getBoundingBox().inflate(6.25);
        for (LivingEntity target : level.getEntitiesOfClass(
            LivingEntity.class, aabb,
            target -> EntityRelationUtil.shouldAoeDamage(entity, target)
        )) {
            // 骇人之恶点燃（对齐原版 berserk 点燃，用 EFFECTMONSTROUS 替代狂暴状态）
            if (entity.getEffect(ModEffect.EFFECTMONSTROUS) != null) {
                target.igniteForSeconds(6);
            }

            boolean hurtSuccess = target.hurt(damagesource, damage);

            if (hurtSuccess) {
                // 击飞（对齐原版 launch 公式：XZpower=2.0, Ypower=0.6）
                double dx = target.getX() - entity.getX();
                double dz = target.getZ() - entity.getZ();
                double dist = Math.max(dx * dx + dz * dz, 0.001D);
                target.push(dx / dist * 2.0D, 0.6D, dz / dist * 2.0D);
                target.hurtMarked = true;
            }
        }

        return true;
    }

    // ==================== 远古工厂器官 ====================

    /**
     * 远古工厂器官 modifier - 机械器官数量的平方根加成
     *
     * @param primaryAttribute 要加成的主属性
     * @param baseValue        基础加值
     */
    public static OrganModifierConsumer ancientFactoryModifier(Holder<Attribute> primaryAttribute, double baseValue) {
        return (context, modifiers) -> {
            int count = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
            double bonus = baseValue + Math.floor(Math.sqrt(count * 2));
            modifiers.put(primaryAttribute, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
        };
    }

    /**
     * 蓄能电芯 — 自动修复
     * 每秒（20 tick），若未满血，回复 0.5 + 机械器官数量×0.05 点 HP
     */
    public static void powerCellTick(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.tickCount % 20 != 0) return;
        if (entity.getHealth() < entity.getMaxHealth() || entity instanceof Player player && player.isHurt()) {
            int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
            entity.heal(0.5F + mechanicalCount * 0.05F);
        }
    }

    /**
     * 机械之星 — 凋零追踪导弹连发（底层逻辑，追踪指定目标）
     * <p>
     * 导弹数量随机械器官数动态变化：1 + floor(机械器官数 / 3)；
     * 每枚伤害固定 8 点；每 10 tick 发射一枚。
     * </p>
     */
    public static boolean mechanicalStar(ChestCavitySlotContext context, LivingEntity target) {
        int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
        int missileCount = 1 + mechanicalCount / 3;

        // 若已有同类型任务在跑则先移除，避免创造模式/异常重叠调用导致双倍连发
        context.data().removeTaskIf(task -> task instanceof MechanicalStarTask);
        context.data().addTask(new MechanicalStarTask(missileCount, 8.0F, target));

        return true;
    }

    /**
     * 死亡透镜 — 死亡激光
     * 向视线方向发射一道死亡激光束
     * 安全特性：默认不点火（setFire=false），不破坏常规方块
     */
    public static boolean deathLens(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        // 基础伤害 = 4 × (1 + 机械器官数 × 0.1)
        // 生命百分比 = (1 + 机械器官数 × 0.1)%
        int mechanicalCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MECHANICAL);
        float seriesMultiplier = 1 + mechanicalCount * 0.1F;
        float baseDamage = 4.0F * seriesMultiplier;
        float healthPercent = seriesMultiplier;

        Death_Laser_Beam_Entity laser = new Death_Laser_Beam_Entity(
            ModEntities.DEATH_LASER_BEAM.get(),
            level,
            entity,
            entity.getX(),
            entity.getY() + OrganSkillUtil.effectiveEyeHeight(entity) * 0.69F,
            entity.getZ(),
            (float) ((entity.yHeadRot + 90) * Math.PI / 180.0D),
            (float) (-entity.getXRot() * Math.PI / 180.0D),
            40,
            baseDamage,
            healthPercent
        );
        level.addFreshEntity(laser);

        // 通过 CCB Task 系统让激光跟随玩家位置和朝向
        context.data().addTask(new DeathLensTask(laser));

        // 激光音效
        level.playSound(
            null,
            entity,
            ModSounds.DEATH_LASER.get(),
            SoundSource.PLAYERS,
            1.5F,
            1.0F
        );

        return true;
    }

    /**
     * 虚空晶脊 — 虚空践踏
     * 以自身为中心召唤三环虚空符文阵，与 Boss Ender_Guardian_Entity.StompAttack() 一致
     */
    public static boolean voidCrystalSpine(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();

        if (!entity.onGround()) return false;

        Level level = entity.level();

        // 音效
        level.playSound(
            null,
            entity,
            ModSounds.ENDER_GUARDIAN_FIST.get(),
            SoundSource.PLAYERS,
            0.3f,
            1.0F + entity.getRandom().nextFloat() * 0.1F
        );

        // 震屏
        ScreenShake_Entity.ScreenShake(level, entity.position(), 10, 0.1f, 0, 5);

        double groundY = entity.getY();
        double headY = entity.getY() + 1.0D;
        float bodyAngle = 0.01745329251F * entity.yBodyRot;

        // 符文伤害 = 10 × (1 + 魔法器官数 × 0.1)
        int magicCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.MAGIC);
        float runeDamage = 10.0F * (1 + magicCount * 0.1F);

        // 内环：6个符文，半径1.5，延迟3 tick
        for (int k = 0; k < 6; ++k) {
            float innerAngle = bodyAngle + (float) k * (float) Math.PI * 2.0F / 6.0F + ((float) Math.PI * 2F / 5F);
            spawnVoidRune(
                level, entity.getX() + Mth.cos(innerAngle) * 1.5D, entity.getZ() + Mth.sin(innerAngle) * 1.5D,
                groundY, headY, innerAngle, 0, runeDamage, entity
            );
        }

        // 中环：12个符文，半径2.5，延迟7 tick
        for (int k = 0; k < 12; ++k) {
            float middleAngle = bodyAngle + (float) k * (float) Math.PI * 2.0F / 11.0F + ((float) Math.PI * 2F / 10F);
            spawnVoidRune(
                level, entity.getX() + Mth.cos(middleAngle) * 2.5D, entity.getZ() + Mth.sin(middleAngle) * 2.5D,
                groundY, headY, middleAngle, 7, runeDamage, entity
            );
        }

        // 外环：14个符文，半3.5，延迟15 tick
        for (int k = 0; k < 14; ++k) {
            float outerAngle = bodyAngle + (float) k * (float) Math.PI * 2.0F / 14.0F + ((float) Math.PI * 2F / 20F);
            spawnVoidRune(
                level, entity.getX() + Mth.cos(outerAngle) * 3.5D, entity.getZ() + Mth.sin(outerAngle) * 3.5D,
                groundY, headY, outerAngle, 12, runeDamage, entity
            );
        }

        return true;
    }

    /**
     * 虚空符文生成辅助方法，对齐 Boss Ender_Guardian_Entity.spawnFangs()
     */
    private static void spawnVoidRune(
        Level level, double x, double z, double minY, double maxY,
        float rotation, int delay, float damage, LivingEntity caster
    ) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z);
        boolean foundGround = false;
        double groundOffset = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = level.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
                if (!level.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
                    if (!voxelshape.isEmpty()) {
                        groundOffset = voxelshape.max(Direction.Axis.Y);
                    }
                }
                foundGround = true;
                break;
            }
            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(minY));

        if (foundGround) {
            level.addFreshEntity(
                new Void_Rune_Entity(
                    level, x,
                    groundOffset + (double) blockpos.getY(),
                    z,
                    rotation,
                    delay,
                    damage,
                    caster
                ));
        }
    }

    // ==================== 咒翼灵骸器官 ====================

    /**
     * 咒魂心脏 — 咒魂战意攻击回调
     * 冲刺状态下，所有造成的伤害最终增加25%
     */
    public static void phantomHeartAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        LivingEntity entity = context.entity();
        if (OrganUtil.isSelfDamage(target, source)) return;
        if (!entity.isSprinting()) return;

        damageContainer.setNewDamage(damageContainer.getNewDamage() * (1.15F + (float) (entity.getAttributeValue(InitAttribute.STRENGTH) * 0.005)));
    }

    /**
     * 封印石板 — 破封·幻戟阵
     * 以自身为中心呈螺旋状召唤多道幻影战戟从地面依次刺出，对齐断魂战戟 StrikeWindmillHalberd
     */
    public static boolean sealingStoneSlab(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (!entity.onGround()) return false;

        Level level = entity.level();

        // 音效
        level.playSound(
            null,
            entity,
            ModSounds.PHANTOM_SPEAR.get(),
            SoundSource.PLAYERS,
            1.5F,
            1.0F + entity.getRandom().nextFloat() * 0.1F
        );

        // 震屏
        ScreenShake_Entity.ScreenShake(level, entity.position(), 15, 0.2f, 0, 10);

        // 每道战戟伤害 = 8 + 力量属性 × 0.5
        float halberdDamage = 8.0F + (float) (entity.getAttributeValue(InitAttribute.STRENGTH) * 0.5);

        // 螺旋幻影战戟阵
        strikeWindmillHalberd(level, entity, 7, 5, 1.0D, 1.0D, 0.2D, 1, halberdDamage);

        return true;
    }

    /**
     * 螺旋幻影战戟阵生成方法，对齐断魂战戟 StrikeWindmillHalberd 的螺旋扩散逻辑，额外增加动态伤害参数
     */
    private static void strikeWindmillHalberd(
        Level level,
        LivingEntity caster,
        int numberOfBranches,
        int halberdsPerBranch,
        double initialRadius,
        double radiusIncrement,
        double curveFactor,
        int delay,
        float damage
    ) {
        float angleIncrement = (float) (2 * Math.PI / numberOfBranches);
        for (int branch = 0; branch < numberOfBranches; ++branch) {
            float baseAngle = angleIncrement * branch;
            for (int i = 0; i < halberdsPerBranch; ++i) {
                double currentRadius = initialRadius + i * radiusIncrement;
                float currentAngle = (float) (baseAngle + i * angleIncrement / initialRadius + i * curveFactor);

                double xOffset = currentRadius * Math.cos(currentAngle);
                double zOffset = currentRadius * Math.sin(currentAngle);

                double spawnX = caster.getX() + xOffset;
                double spawnZ = caster.getZ() + zOffset;
                int currentDelay = delay * (i + 1);

                spawnPhantomHalberd(
                    level, spawnX,
                    caster.getY() - 5.0D, caster.getY() + 3.0D,
                    spawnZ, currentAngle, currentDelay, caster, damage
                );
            }
        }
    }

    /**
     * 幻影战戟生成辅助方法
     * 从指定位置向下搜索地面，在地面生成幻影战戟
     */
    private static void spawnPhantomHalberd(
        Level level, double x, double minY, double maxY, double z,
        float rotation, int delay, LivingEntity caster, float damage
    ) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z);
        boolean foundGround = false;
        double groundOffset = 0.0D;

        do {
            BlockPos below = blockpos.below();
            BlockState blockstate = level.getBlockState(below);
            if (blockstate.isFaceSturdy(level, below, Direction.UP)) {
                if (!level.isEmptyBlock(blockpos)) {
                    VoxelShape voxelshape = level.getBlockState(blockpos).getCollisionShape(level, blockpos);
                    if (!voxelshape.isEmpty()) {
                        groundOffset = voxelshape.max(Direction.Axis.Y);
                    }
                }
                foundGround = true;
                break;
            }
            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(minY) - 1);

        if (foundGround) {
            level.addFreshEntity(
                new Phantom_Halberd_Entity(
                    level, x,
                    groundOffset + blockpos.getY(),
                    z,
                    rotation,
                    delay,
                    caster,
                    damage
                )
            );
        }
    }

    // ==================== 远古遗魂器官 ====================

    /**
     * 沙釉心脏
     * 召唤3个沙暴龙卷风环绕自身，持续 300 + 遗魂器官数 × 100 tick
     */
    public static boolean sandGlazeHeart(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();
        int remnantCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.REMNANT);
        int lifespan = 300 + remnantCount * 100;

        for (int i = 0; i < 3; i++) {
            float angle = i * Mth.PI / 1.5F;
            double spawnX = entity.getX() + (Mth.cos(angle) * 8);
            double spawnY = entity.getY();
            double spawnZ = entity.getZ() + (Mth.sin(angle) * 8);
            Sandstorm_Entity projectile = new Sandstorm_Entity(level, spawnX, spawnY, spawnZ, lifespan, angle, entity);
            level.addFreshEntity(projectile);
        }

        level.playSound(
            null,
            entity,
            ModSounds.REMNANT_ROAR.get(),
            SoundSource.PLAYERS,
            3.0F,
            1.0F
        );

        return true;
    }

    /**
     * 沙釉心脏
     * 攻击持有沙漠诅咒的目标时，额外造成原伤害 ×（30% + 遗魂器官数 × 5%）的伤害
     */
    public static void sandGlazeHeartAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        if (OrganUtil.isSelfDamage(target, source)) return;
        if (target.getEffect(ModEffect.EFFECTCURSE_OF_DESERT) == null) return;

        int remnantCount = ChestCavityUtil.getOrganCountWithSelf(context, WAICItemTagManager.REMNANT);
        float multiplier = 0.3F + remnantCount * 0.05F;
        float currentDamage = damageContainer.getNewDamage();
        float extraDamage = currentDamage * multiplier;
        if (extraDamage > 0) {
            damageContainer.setNewDamage(currentDamage + extraDamage);
        }
    }

    // ==================== 紫水晶巨蟹器官 ====================

    /**
     * 苔化紫水晶
     * 每种不同的魔法器官提供 0.5 点防御
     */
    public static void mossyAmethystModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int distinctCount = ChestCavityUtil.getDistinctOrganTypeCountWithSelf(context, WAICItemTagManager.MAGIC);
        modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(context.id(), distinctCount * 0.5));
    }

    /**
     * 花岩核心
     * 以自身为中心环形发射 16 发紫水晶簇投射物
     */
    public static boolean bloomStoneCore(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        float damage = 3.0F + (float) (entity.getAttributeValue(InitAttribute.DEFENSE) * 0.4F);

        for (int i = 0; i < 16; i++) {
            float throwAngle = i * Mth.PI / 8F;
            double spawnX = entity.getX() + Mth.cos(throwAngle);
            double spawnY = entity.getY() + (entity.getBbHeight() * 0.5D);
            double spawnZ = entity.getZ() + Mth.sin(throwAngle);

            double velocityX = Mth.cos(throwAngle);
            double velocityY = entity.getRandom().nextFloat() * 0.3F;
            double velocityZ = Mth.sin(throwAngle);
            double horizontalSpeed = Mth.sqrt((float) (velocityX * velocityX + velocityZ * velocityZ));

            Amethyst_Cluster_Projectile_Entity projectile = new Amethyst_Cluster_Projectile_Entity(
                ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), level, entity, damage
            );
            projectile.moveTo(spawnX, spawnY, spawnZ, i * 11.25F, entity.getXRot());
            projectile.shoot(velocityX, velocityY + horizontalSpeed * 0.2F, velocityZ, 0.8F, 1.0F);
            level.addFreshEntity(projectile);
        }

        return true;
    }
}

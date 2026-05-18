package net.zhaiji.who_am_i_core.util;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;


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
     * 不灭薪火属性修饰符 - 全局温度的平方根的力量
     */
    public static void undyingEmberModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        double temp = OrganUtil.getEffectiveTemperature(context.entity());
        if (context.index() == -1) temp += 9;
        modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(context.id(), Math.floor(Math.sqrt(temp))));
    }

    /**
     * 焰魔肋甲属性修饰符 - 局部温度的平方根的格挡
     */
    public static void ignitedRibPlatingModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        double localTemp = OrganUtil.getLocalTemperature(context);
        modifiers.put(WAICAttribute.BLOCK, OrganAttributeUtil.createAddValueModifier(context.id(), Math.floor(Math.sqrt(localTemp))));
    }

    /**
     * 炽面甲 — 炽热烙印攻击回调
     * 近战命中施加炽热烙印效果，根据局部温度回血
     */
    public static void blazingVisageAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        LivingEntity entity = context.entity();
        if (OrganUtil.isSelfDamage(target, source)) return;

        // 根据局部温度计算回血量
        double localTemp = OrganUtil.getLocalTemperature(context);
        float healAmount = 2.0F + (float) Math.floor(localTemp * 0.5);

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
     * 巨兽炉心tick回调：每20 tick将温度转化为黄胆汁
     */
    public static void monstrosityCoreTick(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.level().isClientSide()) return;
        if (entity.tickCount % 20 != 0) return;
        double temp = OrganUtil.getEffectiveTemperature(entity);
        if (temp > 0) {
            HumoursData.insertYellowBile(entity, (float) (temp * 0.05), false);
        }
    }

    /**
     * 巨兽熔炉 — 饮用岩浆桶
     * 增加100点黄胆汁，根据巨兽器官数量施加巨兽之力效果，恢复饱食度
     */
    public static ItemStack drinkLava(LivingEntity entity, ItemStack stack) {
        HumoursData.insertYellowBile(entity, 100, false);
        ChestCavityData data = ChestCavityUtil.getData(entity);
        int count = data.getOrganCount(WAICItemTagManager.MONSTROSITY);
        entity.addEffect(new MobEffectInstance(ModEffect.EFFECTMONSTROUS, 60 * 20, count - 1));

        if (entity instanceof Player player) {
            player.getFoodData().eat(20, 0.5F);
            if (player.hasInfiniteMaterials()) return stack;
            return ItemUtils.createFilledResult(stack, player, Items.BUCKET.getDefaultInstance());
        }
        ItemStack remaining = stack.getCraftingRemainingItem();
        stack.consume(1, entity);
        return remaining;
    }

    /**
     * 巨兽回路 — 地震践踏
     * 消耗100点黄胆汁，AoE伤害+击飞（对齐 Cataclysm 原版下界合金巨兽撼地猛击）
     */
    public static boolean monstrosityCircuitSkill(ChestCavitySlotContext context) {
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

        double temp = OrganUtil.getEffectiveTemperature(entity);
        float baseDamage = consumed + (float) (temp * 2);

        // AoE伤害：半径6.25格（对齐原版 EarthQuake(6.25D)）
        DamageSource damagesource = level.damageSources().mobAttack(entity);
        AABB aabb = entity.getBoundingBox().inflate(6.25);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, aabb, target -> target != entity)) {
            // 不伤害主人的驯服宠物
            if (target instanceof TamableAnimal tamable && tamable.isOwnedBy(entity)) continue;

            // 骇人之恶点燃（对齐原版 berserk 点燃，用 EFFECTMONSTROUS 替代狂暴状态）
            if (entity.getEffect(ModEffect.EFFECTMONSTROUS) != null) {
                target.igniteForSeconds(6);
            }

            float bonusHpDamage = target.getMaxHealth() * 0.05F;
            boolean hurtSuccess = target.hurt(damagesource, baseDamage + bonusHpDamage);

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
     */
    public static void ancientFactoryModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers,
        Holder<Attribute> primaryAttribute
    ) {
        int count = context.data().getOrganCount(WAICItemTagManager.MECHANICAL);
        if (context.index() == -1) count++;
        double bonus = Math.floor(Math.sqrt(count * 2));
        modifiers.put(primaryAttribute, OrganAttributeUtil.createAddValueModifier(context.id(), bonus));
    }

    /**
     * 蓄能电芯 — 自动修复
     * 每 40 tick（2秒），若未满血，回复 1 点 HP
     */
    public static void powerCellTick(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.tickCount % 40 != 0) return;
        if (entity.getHealth() < entity.getMaxHealth() || entity instanceof Player player && player.isHurt()) {
            entity.heal(1.0F);
        }
    }

    /**
     * 机械之星 — 凋零榴弹
     * 向视线方向发射一枚凋零榴弹（对齐先驱者 LaunchGoal 逻辑）
     * 命中后产生爆炸 + 凋零烟雾区域
     */
    public static boolean mechanicalStarSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        Vec3 look = entity.getLookAngle();

        // 发射凋零榴弹（对齐先驱者 LaunchGoal 的 howitzer 创建方式）
        Wither_Howitzer_Entity howitzer = new Wither_Howitzer_Entity(
            ModEntities.WITHER_HOWITZER.get(), level, entity);
        howitzer.setPos(entity.getX(), entity.getEyeY() - 0.5, entity.getZ());
        howitzer.setRadius(3.0F);
        howitzer.shoot(look.x, look.y, look.z, 0.6F, 60);
        level.addFreshEntity(howitzer);

        // 发射音效
        level.playSound(null, entity, ModSounds.ROCKET_LAUNCH.get(), SoundSource.PLAYERS, 1.5F, 1.0F);

        return true;
    }

    /**
     * 死亡透镜 — 死亡激光
     * 向视线方向发射一道死亡激光束
     * 安全特性：默认不点火（setFire=false），不破坏常规方块
     */
    public static boolean deathLensSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Level level = entity.level();

        Death_Laser_Beam_Entity laser = new Death_Laser_Beam_Entity(
            ModEntities.DEATH_LASER_BEAM.get(),
            level,
            entity,
            entity.getX(),
            entity.getEyeY() - 0.5,
            entity.getZ(),
            entity.getYRot(),
            entity.getXRot(),
            12,    // duration：预热20tick后，12 tick 的活跃伤害窗口
            6.0F,          // damage：基础伤害
            6.0F           // Hpdamage：目标最大生命 6%（×0.01 后在内部使用）
        );
        level.addFreshEntity(laser);

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
    public static boolean voidCrystalSpineSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();

        if(!entity.onGround()) return false;

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

        double d0 = entity.getY();
        double d1 = entity.getY() + 1.0D;
        float angle2 = 0.01745329251F * entity.yBodyRot;

        // 内环：6个符文，半径1.5，延迟3 tick
        for (int k = 0; k < 6; ++k) {
            float f2 = angle2 + (float) k * (float) Math.PI * 2.0F / 6.0F + ((float) Math.PI * 2F / 5F);
            spawnVoidRune(level, entity.getX() + Mth.cos(f2) * 1.5D, entity.getZ() + Mth.sin(f2) * 1.5D,
                d0, d1, f2, 0, 16.0F, entity
            );
        }

        // 中环：11个符文，半径3.5，延迟10 tick
        for (int k = 0; k < 12; ++k) {
            float f3 = angle2 + (float) k * (float) Math.PI * 2.0F / 11.0F + ((float) Math.PI * 2F / 10F);
            spawnVoidRune(level, entity.getX() + Mth.cos(f3) * 2.5D, entity.getZ() + Mth.sin(f3) * 2.5D,
                d0, d1, f3, 7, 16.0F, entity
            );
        }

        // 外环：14个符文，半3.5，延迟15 tick
        for (int k = 0; k < 14; ++k) {
            float f4 = angle2 + (float) k * (float) Math.PI * 2.0F / 14.0F + ((float) Math.PI * 2F / 20F);
            spawnVoidRune(level, entity.getX() + Mth.cos(f4) * 3.5D, entity.getZ() + Mth.sin(f4) * 3.5D,
                d0, d1, f4, 12, 16.0F, entity
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
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = level.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(level, blockpos1, Direction.UP)) {
                if (!level.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(level, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }
                flag = true;
                break;
            }
            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(minY));

        if (flag) {
            level.addFreshEntity(
                new Void_Rune_Entity(
                    level, x,
                    d0 + (double) blockpos.getY(),
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

        float currentDamage = damageContainer.getNewDamage();
        damageContainer.setNewDamage(currentDamage * 1.25F);
    }

    /**
     * 封印石板 — 破封·幻戟阵
     * 在前方扇形范围召唤5道幻影战戟从地面依次刺出
     */
    public static boolean sealingStoneSlabSkill(ChestCavitySlotContext context) {
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

        // 扇形5道幻影战戟
        float baseYRot = entity.getYRot() * ((float) Math.PI / 180F);
        float[] angleOffsets = {
            -24.0F, -12.0F, 0.0F, 12.0F, 24.0F
        };

        for (int i = 0; i < angleOffsets.length; i++) {
            float angleRad = baseYRot + angleOffsets[i] * ((float) Math.PI / 180F);
            int warmupDelay = i * 3;

            // 计算戟的位置：从玩家前方2格处
            double spawnX = entity.getX() + Mth.cos(angleRad) * 2.0D;
            double spawnZ = entity.getZ() + Mth.sin(angleRad) * 2.0D;

            spawnPhantomHalberd(level, spawnX, entity.getY(), spawnZ, angleRad, warmupDelay, entity, 12.0F);
        }

        return true;
    }

    /**
     * 幻影战戟生成辅助方法
     * 从指定位置向下搜索地面，在地面生成幻影战戟
     */
    private static void spawnPhantomHalberd(
        Level level, double x, double minY, double z,
        float rotation, int delay, LivingEntity caster, float damage
    ) {
        double maxY = minY + 3.0D;
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
        } while (blockpos.getY() >= Mth.floor(minY));

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
}

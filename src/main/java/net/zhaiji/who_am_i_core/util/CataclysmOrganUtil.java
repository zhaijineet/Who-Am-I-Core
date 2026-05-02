package net.zhaiji.who_am_i_core.util;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
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
        if (entity == target) return;

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
        if (entity == target) return;

        // 根据局部温度计算回血量
        double localTemp = WAICOrganUtil.getLocalTemperature(context);
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
        double temp = WAICOrganUtil.getEffectiveTemperature(entity);
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
            player.getFoodData().eat(20, 20);
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

        double temp = WAICOrganUtil.getEffectiveTemperature(entity);
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
}

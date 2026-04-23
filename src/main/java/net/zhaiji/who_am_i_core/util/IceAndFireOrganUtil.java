package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.task.DragonBreathCastingTask;
import net.zhaiji.who_am_i_core.task.HydraLungBreathTask;
import net.zhaiji.who_am_i_core.task.HydraSpleenTask;

public class IceAndFireOrganUtil {
    /**
     * 取消指定类型的吐息任务
     */
    public static void cancelBreathTask(ChestCavityData data, DragonBreathCastingTask.BreathType breathType) {
        data.removeTaskIf(
            task -> task instanceof DragonBreathCastingTask breathTask
                    && breathTask.getBreathType() == breathType
                    && !breathTask.canRemove(data.getOwner())
        );
    }

    /**
     * 添加指定类型的吐息任务
     */
    public static boolean addBreathTask(ChestCavityData data, DragonBreathCastingTask.BreathType breathType, TagKey<Item> organTag) {
        data.addTask(
            new DragonBreathCastingTask(
                data,
                breathType,
                getBreathLevel(data, organTag)
            )
        );
        return true;
    }

    /**
     * 计算吐息等级
     */
    public static int getBreathLevel(ChestCavityData data, TagKey<Item> organTag) {
        return Math.min(data.getOrganCount(organTag), 10);
    }

    /**
     * 火龙吐息
     */
    public static boolean fireDragonBreathSacSkill(ChestCavitySlotContext context) {
        return addBreathTask(context.data(), DragonBreathCastingTask.BreathType.FIRE_BREATH, WAICItemTagManager.FIRE_DRAGON);
    }

    /**
     * 冰龙吐息
     */
    public static boolean iceDragonBreathSacSkill(ChestCavitySlotContext context) {
        return addBreathTask(context.data(), DragonBreathCastingTask.BreathType.ICE_BREATH, WAICItemTagManager.ICE_DRAGON);
    }

    /**
     * 电龙吐息
     */
    public static boolean lightningDragonBreathSacSkill(ChestCavitySlotContext context) {
        return addBreathTask(context.data(), DragonBreathCastingTask.BreathType.LIGHTNING_BREATH, WAICItemTagManager.LIGHTNING_DRAGON);
    }

    /**
     * 九头蛇脾脏
     */
    public static void hydraSpleenAdded(ChestCavitySlotContext slotContext) {
        ChestCavityData data = slotContext.data();
        if (data.hasTaskIf(task -> task instanceof HydraSpleenTask && !task.canRemove(slotContext.entity()))) return;
        data.addTask(new HydraSpleenTask(data));
    }

    /**
     * 九头蛇吐息
     * <p>
     * 消耗中毒效果，释放吐息
     * </p>
     */
    public static boolean hydraLungSkill(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
        if (poison == null || poison.getDuration() <= 0) return false;
        entity.removeEffect(MobEffects.POISON);
        context.data().addTask(
            new HydraLungBreathTask(
                poison.getAmplifier(),
                poison.getDuration()
            )
        );
        return true;
    }

    /**
     * 九头蛇脊柱复活效果
     *
     * @return 是否取消事件
     */
    public static boolean hydraSpineSkill(LivingEntity entity, ChestCavityData data) {
        if (!data.hasOrgan(IceAndFireOrgans.HYDRA_SPINE.get())) return false;
        MobEffectInstance poisonEffect = entity.getEffect(MobEffects.POISON);
        if (poisonEffect == null || poisonEffect.getDuration() < 200) return false;
        // 回复10%血量
        entity.setHealth(entity.getMaxHealth() * 0.1F);
        int currentAmplifier = poisonEffect.getAmplifier();
        // 移除旧中毒效果，添加新效果
        // 必须先移除，否则当新效果结束后会恢复旧效果
        entity.removeEffect(MobEffects.POISON);
        entity.addEffect(new MobEffectInstance(
            MobEffects.POISON,
            // 减半时间
            poisonEffect.getDuration() / 2,
            // 提升中毒等级（最高5级）
            // 如果等级本身大于5级，就保留原等级
            Math.min(currentAmplifier + 1, Math.max(currentAmplifier, 4))
        ));
        return true;
    }

    /**
     * 从实体转移部分中毒效果到另一个实体
     *
     * @param from   效果来源实体
     * @param to     效果目标实体
     * @param amount 转移的时长
     * @return 转移的中毒等级+1（用于伤害计算)
     */
    public static float transferPoison(LivingEntity from, LivingEntity to, int amount) {
        if (from == to) return 0;
        MobEffectInstance fromPoison = from.getEffect(MobEffects.POISON);
        if (fromPoison == null) return 0;
        int duration = fromPoison.getDuration();
        if (duration == 0) return 0;

        int amplifier = fromPoison.getAmplifier();
        int durationToTransfer = fromPoison.isInfiniteDuration() ? amount : Math.min(amount, duration);
        int newDuration = fromPoison.isInfiniteDuration() ? -1 : duration - durationToTransfer;

        // 更新来源实体的中毒效果
        if (!fromPoison.isInfiniteDuration()) {
            from.removeEffect(MobEffects.POISON);
        }
        if (newDuration != 0) {
            from.addEffect(
                new MobEffectInstance(
                    MobEffects.POISON,
                    newDuration,
                    amplifier,
                    fromPoison.isAmbient(),
                    fromPoison.isVisible(),
                    fromPoison.showIcon()
                )
            );
        }
        // 将中毒效果施加到目标实体
        // to可能为null
        if (to != null) {
            MobEffectInstance toPoison = to.getEffect(MobEffects.POISON);
            if (toPoison != null) {
                to.removeEffect(MobEffects.POISON);
                to.addEffect(
                    new MobEffectInstance(
                        MobEffects.POISON,
                        toPoison.mapDuration(toDuration -> toDuration + durationToTransfer),
                        Math.max(toPoison.getAmplifier(), amplifier),
                        toPoison.isAmbient(),
                        toPoison.isVisible(),
                        toPoison.showIcon()
                    )
                );
            } else {
                to.addEffect(
                    new MobEffectInstance(
                        MobEffects.POISON,
                        durationToTransfer,
                        amplifier
                    )
                );
            }
        }
        return amplifier + 1;
    }

    /**
     * 九头蛇肋骨技能
     * 从目标转移中毒效果到攻击者，抵消伤害
     */
    public static float hydraRibSkill(LivingEntity target, LivingEntity attacker) {
        ChestCavityData data = ChestCavityUtil.getData(target);
        if (!data.hasOrgan(IceAndFireOrgans.HYDRA_RIB.get())) return 0;
        return transferPoison(target, attacker, 100);
    }

    /**
     * 九头蛇肌肉技能
     * 从攻击者转移中毒效果到目标，造成额外伤害
     */
    public static float hydraMuscleSkill(LivingEntity attacker, LivingEntity target) {
        ChestCavityData data = ChestCavityUtil.getData(attacker);
        if (!data.hasOrgan(IceAndFireOrgans.HYDRA_MUSCLE.get())) return 0;
        return transferPoison(attacker, target, 100);
    }

    /**
     * 九头蛇心脏 tick - 中毒时获得再生效果
     * <p>
     * 每10tick检测一次，当实体未着火且持有中毒效果时，给予与中毒等级相同的再生效果
     * </p>
     */
    public static void hydraHeartTick(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.isOnFire() || entity.tickCount % 10 != 0) return;
        MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
        if (poison != null) {
            entity.addEffect(
                new MobEffectInstance(
                    MobEffects.REGENERATION,
                    20,
                    poison.getAmplifier()
                )
            );
        }
    }

    /**
     * 火龙吐息袋冷却回调 - 取消吐息中
     */
    public static void fireDragonBreathSacOnCooldown(ChestCavitySlotContext context) {
        cancelBreathTask(context.data(), DragonBreathCastingTask.BreathType.FIRE_BREATH);
    }

    /**
     * 冰龙吐息袋冷却回调 - 取消吐息中
     */
    public static void iceDragonBreathSacOnCooldown(ChestCavitySlotContext context) {
        cancelBreathTask(context.data(), DragonBreathCastingTask.BreathType.ICE_BREATH);
    }

    /**
     * 电龙吐息袋冷却回调 - 取消吐息中
     */
    public static void lightningDragonBreathSacOnCooldown(ChestCavitySlotContext context) {
        cancelBreathTask(context.data(), DragonBreathCastingTask.BreathType.LIGHTNING_BREATH);
    }

    /**
     * 悚怖脊柱攻击效果 - 根据局部温度负值造成缓慢
     */
    public static void dreadSpineAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        double localTemp = WAICOrganUtil.getLocalTemperature(context);
        int slownessLevel = (int) Math.max(0, (Math.abs(localTemp) - 1) / 2);
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3 * 20, slownessLevel));
    }

    /**
     * 冰魂残片属性修饰符 - 全局温度 * -0.05 的健康
     */
    public static void iceShardModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        double temp = WAICOrganUtil.getEffectiveTemperature(context.entity());
        if (context.index() == -1) temp -= 1;
        double healthBonus = temp * -0.05;
        modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), healthBonus));
    }

    /**
     * 冻结魂火属性修饰符 - 全局温度 * -0.15 的健康
     */
    public static void frostburnSoulModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        double temp = WAICOrganUtil.getEffectiveTemperature(context.entity());
        if (context.index() == -1) temp -= 2;
        double healthBonus = temp * -0.15;
        modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), healthBonus));
    }

    /**
     * 悚恐怖匣属性修饰符 - 全局温度 * -0.25 的健康
     */
    public static void dreadPhylacteryModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        double temp = WAICOrganUtil.getEffectiveTemperature(context.entity());
        if (context.index() == -1) temp -= 3;
        double healthBonus = temp * -0.25;
        modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(context.id(), healthBonus));
    }

    /**
     * 龙宝玉属性修饰符 - 根据对应龙类器官数量提供法术强度
     *
     * @param attribute 对应的法术强度属性（火/冰/电）
     * @param organTag  对应的龙类器官标签
     */
    public static void dragonGemModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers,
        Holder<Attribute> attribute,
        TagKey<Item> organTag
    ) {
        int count = context.data().getOrganCount(organTag);
        if (context.index() == -1) count++;
        modifiers.put(attribute, OrganAttributeUtil.createAddValueModifier(context.id(), count * 0.05));
    }

    /**
     * 悚恐怖匣攻击效果 - 将目标身上的缓慢效果转化为冰霜伤害
     */
    public static void dreadPhylacteryAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        MobEffectInstance slowness = target.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        if (slowness == null) return;
        if (slowness.isInfiniteDuration()) {
            target.hurt(target.level().damageSources().freeze(), Integer.MAX_VALUE);
        } else if (slowness.getDuration() > 0) {
            float freezeDamage = (slowness.getDuration() / 20F) * (slowness.getAmplifier() + 1);
            if (freezeDamage > 0) {
                target.hurt(target.level().damageSources().freeze(), freezeDamage);
            }
        }
        target.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
    }
}

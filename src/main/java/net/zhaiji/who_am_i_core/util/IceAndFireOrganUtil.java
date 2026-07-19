package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.task.DragonBreathCastingTask;
import net.zhaiji.who_am_i_core.task.HydraLungBreathTask;

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
    public static boolean fireDragonBreathSac(ChestCavitySlotContext context) {
        return addBreathTask(context.data(), DragonBreathCastingTask.BreathType.FIRE_BREATH, WAICItemTagManager.FIRE_DRAGON);
    }

    /**
     * 冰龙吐息
     */
    public static boolean iceDragonBreathSac(ChestCavitySlotContext context) {
        return addBreathTask(context.data(), DragonBreathCastingTask.BreathType.ICE_BREATH, WAICItemTagManager.ICE_DRAGON);
    }

    /**
     * 电龙吐息
     */
    public static boolean lightningDragonBreathSac(ChestCavitySlotContext context) {
        return addBreathTask(context.data(), DragonBreathCastingTask.BreathType.LIGHTNING_BREATH, WAICItemTagManager.LIGHTNING_DRAGON);
    }

    /**
     * 九头蛇吐息
     * <p>
     * 消耗中毒效果，释放吐息
     * </p>
     */
    public static boolean hydraLung(ChestCavitySlotContext context) {
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
     * 九头蛇肠子对效果时长的延长倍率
     *
     * @param duration         原始时长
     * @param hydraOrganCount  九头蛇器官总数
     */
    public static int applyIntestineMultiplier(int duration, int hydraOrganCount) {
        return (int) (duration * (1 + (0.5 * hydraOrganCount)));
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
        if (OrganSkillUtil.hasCooldown(entity, IceAndFireOrgans.HYDRA_SPINE.get())) return false;
        entity.setHealth(entity.getMaxHealth() * (0.05F + (float) (entity.getAttributeValue(InitAttribute.METABOLISM) * 0.05)));
        entity.level().playSound(null, entity.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        int currentAmplifier = poisonEffect.getAmplifier();
        entity.removeEffect(MobEffects.POISON);
        entity.addEffect(new MobEffectInstance(
            MobEffects.POISON,
            poisonEffect.getDuration() / 2,
            currentAmplifier < 4 ? currentAmplifier + 1 : currentAmplifier
        ));
        OrganSkillUtil.addCooldown(entity, IceAndFireOrgans.HYDRA_SPINE.get(), 3 * 60 * 20);
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

    public static float hydraRibHurt(LivingEntity target, LivingEntity attacker) {
        ChestCavityData data = ChestCavityUtil.getData(target);
        if (!data.hasOrgan(IceAndFireOrgans.HYDRA_RIB.get())) return 0;
        return transferPoison(target, attacker, 100);
    }

    public static float hydraMuscleHurt(LivingEntity attacker, LivingEntity target) {
        ChestCavityData data = ChestCavityUtil.getData(attacker);
        if (!data.hasOrgan(IceAndFireOrgans.HYDRA_MUSCLE.get())) return 0;
        return transferPoison(attacker, target, 100);
    }

    /**
     * 九头蛇心脏受击，中毒伤害按九头蛇器官数比例转为治疗
     */
    public static void hydraHeartIncomingDamage(LivingEntity entity, ChestCavityData data, float damageAmount) {
        int hydraOrganCount = data.getOrganCount(WAICItemTagManager.HYDRA);
        float healRatio = hydraOrganCount * 0.1F;
        entity.heal(damageAmount * healRatio);
    }

    /**
     * 九头蛇脾脏 tick，低血量时将中毒转化为治疗
     */
    public static void hydraSpleenTick(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        if (entity.tickCount % 20 != 0) return;

        MobEffectInstance poison = entity.getEffect(MobEffects.POISON);
        if (poison == null || poison.getDuration() <= 0) return;

        float healthRatio = entity.getHealth() / entity.getMaxHealth();
        if (healthRatio > 0.5) return;

        int amplifier = poison.getAmplifier() + 1;
        float healAmount = amplifier * (1.0F - healthRatio) * 10;
        int consumeDuration = Math.min((int) Math.ceil(healAmount), poison.getDuration());

        entity.heal(Math.min(healAmount, consumeDuration));

        int newDuration = poison.getDuration() - consumeDuration;
        entity.removeEffect(MobEffects.POISON);
        if (newDuration > 0) {
            entity.addEffect(new MobEffectInstance(
                MobEffects.POISON,
                newDuration,
                poison.getAmplifier(),
                poison.isAmbient(),
                poison.isVisible()
            ));
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
     * 悚怖脊柱攻击效果 - 局部冰霜器官数为 0 时施加兜底缓慢 I，否则随局部冰霜器官数提升等级
     */
    public static void dreadSpineAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        if (OrganUtil.isSelfDamage(target, source)) return;
        int localIceOrganCount = OrganUtil.getLocalIceOrganCount(context);
        int slownessLevel = localIceOrganCount <= 0 ? 0 : (localIceOrganCount - 1) / 2;
        int duration = 40 + OrganUtil.getIceOrganCount(context) * 10;
        // 原版 addEffect 不拦截 duration<=0，此处主动拦截避免施加无效效果
        if (duration <= 0) return;
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, slownessLevel));
    }

    /**
     * 冰霜系器官健康 modifier 通用方法
     *
     * @param multiplier 每个冰霜器官提供的健康乘数系数
     */
    public static void coldHealthModifier(
        ChestCavitySlotContext context,
        Multimap<Holder<Attribute>, AttributeModifier> modifiers,
        double multiplier
    ) {
        int iceOrganCount = OrganUtil.getIceOrganCount(context);
        double healthBonus = iceOrganCount * multiplier;
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
        int count = ChestCavityUtil.getOrganCountWithSelf(context, organTag);
        modifiers.put(attribute, OrganAttributeUtil.createAddValueModifier(context.id(), count * 0.01));
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
        if (OrganUtil.isSelfDamage(target, source)) return;
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

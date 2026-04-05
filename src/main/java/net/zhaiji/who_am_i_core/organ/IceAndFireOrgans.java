package net.zhaiji.who_am_i_core.organ;

import com.iafenvoy.iceandfire.registry.IafItems;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.capability.Organ;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.IceAndFireOrganhUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class IceAndFireOrgans {
    // ==================== 火龙器官 ====================
    // 火龙心脏
    public static final Supplier<Item> FIRE_DRAGON_HEART = IafItems.FIRE_DRAGON_HEART;

    // 火龙肺脏
    public static final Supplier<Item> FIRE_DRAGON_LUNG = WAICItem.ITEM.register(
        "fire_dragon_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙脊柱
    public static final Supplier<Item> FIRE_DRAGON_SPINE = WAICItem.ITEM.register(
        "fire_dragon_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙胃
    public static final Supplier<Item> FIRE_DRAGON_STOMACH = WAICItem.ITEM.register(
        "fire_dragon_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙肠子
    public static final Supplier<Item> FIRE_DRAGON_INTESTINE = WAICItem.ITEM.register(
        "fire_dragon_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙肾脏
    public static final Supplier<Item> FIRE_DRAGON_KIDNEY = WAICItem.ITEM.register(
        "fire_dragon_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙脾脏
    public static final Supplier<Item> FIRE_DRAGON_SPLEEN = WAICItem.ITEM.register(
        "fire_dragon_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙肝脏
    public static final Supplier<Item> FIRE_DRAGON_LIVER = WAICItem.ITEM.register(
        "fire_dragon_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙宝玉
    public static final Supplier<Item> FIRE_DRAGON_GEM = WAICItem.ITEM.register(
        "fire_dragon_gem",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 3)
            .modifier((context, modifiers) -> IceAndFireOrganhUtil.dragonGemModifier(
                context,
                modifiers,
                AttributeRegistry.FIRE_SPELL_POWER,
                WAICItemTagManager.FIRE_DRAGON
            ))
            .otherChange((context, changedIndex, oldStack, newStack) -> {
                if (newStack.is(WAICItemTagManager.FIRE_DRAGON) || oldStack.is(WAICItemTagManager.FIRE_DRAGON)) {
                    OrganAttributeUtil.updateSlotOrganAttribute(context);
                }
            })
            .build()
    );

    // 火龙吐息袋
    public static final Supplier<Item> FIRE_DRAGON_BREATH_SAC = WAICItem.ITEM.register(
        "fire_dragon_breath_sac",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .skill(IceAndFireOrganhUtil::fireDragonBreathSacSkill)
            .skillOnCooldown(IceAndFireOrganhUtil::fireDragonBreathSacOnCooldown)
            .cooldown(10 * 20)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙肋骨
    public static final Supplier<Item> FIRE_DRAGON_RIB = WAICItem.ITEM.register(
        "fire_dragon_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // 火龙肌肉
    public static final Supplier<Item> FIRE_DRAGON_MUSCLE = WAICItem.ITEM.register(
        "fire_dragon_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2.25)
            .addValueAttribute(InitAttribute.SPEED, 2)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 1)
            .build()
    );

    // ==================== 冰龙器官 ====================
    // 冰龙心脏
    public static final Supplier<Item> ICE_DRAGON_HEART = IafItems.ICE_DRAGON_HEART;

    // 冰龙肺脏
    public static final Supplier<Item> ICE_DRAGON_LUNG = WAICItem.ITEM.register(
        "ice_dragon_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙脊柱
    public static final Supplier<Item> ICE_DRAGON_SPINE = WAICItem.ITEM.register(
        "ice_dragon_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙胃
    public static final Supplier<Item> ICE_DRAGON_STOMACH = WAICItem.ITEM.register(
        "ice_dragon_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙肠子
    public static final Supplier<Item> ICE_DRAGON_INTESTINE = WAICItem.ITEM.register(
        "ice_dragon_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙肾脏
    public static final Supplier<Item> ICE_DRAGON_KIDNEY = WAICItem.ITEM.register(
        "ice_dragon_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙脾脏
    public static final Supplier<Item> ICE_DRAGON_SPLEEN = WAICItem.ITEM.register(
        "ice_dragon_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙肝脏
    public static final Supplier<Item> ICE_DRAGON_LIVER = WAICItem.ITEM.register(
        "ice_dragon_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙宝玉
    public static final Supplier<Item> ICE_DRAGON_GEM = WAICItem.ITEM.register(
        "ice_dragon_gem",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -3)
            .modifier((context, modifiers) -> IceAndFireOrganhUtil.dragonGemModifier(
                context,
                modifiers,
                AttributeRegistry.ICE_SPELL_POWER,
                WAICItemTagManager.ICE_DRAGON
            ))
            .otherChange((context, changedIndex, oldStack, newStack) -> {
                if (newStack.is(WAICItemTagManager.ICE_DRAGON) || oldStack.is(WAICItemTagManager.ICE_DRAGON)) {
                    OrganAttributeUtil.updateSlotOrganAttribute(context);
                }
            })
            .build()
    );

    // 冰龙吐息袋
    public static final Supplier<Item> ICE_DRAGON_BREATH_SAC = WAICItem.ITEM.register(
        "ice_dragon_breath_sac",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .skill(IceAndFireOrganhUtil::iceDragonBreathSacSkill)
            .skillOnCooldown(IceAndFireOrganhUtil::iceDragonBreathSacOnCooldown)
            .cooldown(10 * 20)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙肋骨
    public static final Supplier<Item> ICE_DRAGON_RIB = WAICItem.ITEM.register(
        "ice_dragon_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰龙肌肉
    public static final Supplier<Item> ICE_DRAGON_MUSCLE = WAICItem.ITEM.register(
        "ice_dragon_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // ==================== 电龙器官 ====================
    // 电龙心脏
    public static final Supplier<Item> LIGHTNING_DRAGON_HEART = IafItems.LIGHTNING_DRAGON_HEART;

    // 电龙肺脏
    public static final Supplier<Item> LIGHTNING_DRAGON_LUNG = WAICItem.ITEM.register(
        "lightning_dragon_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 2)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 2)
            .addValueAttribute(InitAttribute.ENDURANCE, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙脊柱
    public static final Supplier<Item> LIGHTNING_DRAGON_SPINE = WAICItem.ITEM.register(
        "lightning_dragon_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 1)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙胃
    public static final Supplier<Item> LIGHTNING_DRAGON_STOMACH = WAICItem.ITEM.register(
        "lightning_dragon_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙肠子
    public static final Supplier<Item> LIGHTNING_DRAGON_INTESTINE = WAICItem.ITEM.register(
        "lightning_dragon_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙肾脏
    public static final Supplier<Item> LIGHTNING_DRAGON_KIDNEY = WAICItem.ITEM.register(
        "lightning_dragon_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙脾脏
    public static final Supplier<Item> LIGHTNING_DRAGON_SPLEEN = WAICItem.ITEM.register(
        "lightning_dragon_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙肝脏
    public static final Supplier<Item> LIGHTNING_DRAGON_LIVER = WAICItem.ITEM.register(
        "lightning_dragon_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙宝玉
    public static final Supplier<Item> LIGHTNING_DRAGON_GEM = WAICItem.ITEM.register(
        "lightning_dragon_gem",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .modifier((context, modifiers) -> IceAndFireOrganhUtil.dragonGemModifier(
                context,
                modifiers,
                AttributeRegistry.LIGHTNING_SPELL_POWER,
                WAICItemTagManager.LIGHTNING_DRAGON
            ))
            .otherChange((context, changedIndex, oldStack, newStack) -> {
                if (newStack.is(WAICItemTagManager.LIGHTNING_DRAGON) || oldStack.is(WAICItemTagManager.LIGHTNING_DRAGON)) {
                    OrganAttributeUtil.updateSlotOrganAttribute(context);
                }
            })
            .build()
    );

    // 电龙吐息袋
    public static final Supplier<Item> LIGHTNING_DRAGON_BREATH_SAC = WAICItem.ITEM.register(
        "lightning_dragon_breath_sac",
        () -> Organ.builder()
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .skill(IceAndFireOrganhUtil::lightningDragonBreathSacSkill)
            .skillOnCooldown(IceAndFireOrganhUtil::lightningDragonBreathSacOnCooldown)
            .cooldown(10 * 20)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙肋骨
    public static final Supplier<Item> LIGHTNING_DRAGON_RIB = WAICItem.ITEM.register(
        "lightning_dragon_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build()
    );

    // 电龙肌肉
    public static final Supplier<Item> LIGHTNING_DRAGON_MUSCLE = WAICItem.ITEM.register(
        "lightning_dragon_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 2.25)
            .build()
    );

    // ==================== 悚恐怖官 ====================
    // 苦寒血肉
    public static final Supplier<Item> BITTER_FLESH = WAICItem.ITEM.register(
        "bitter_flesh",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 0.5)
            .addValueAttribute(InitAttribute.SPEED, 0.5)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 冰魂残片
    public static final Supplier<Item> ICE_SHARD = WAICItem.ITEM.register(
        "ice_shard",
        () -> Organ.builder()
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .modifier(IceAndFireOrganhUtil::iceShardModifier)
            .build()
    );

    // 冻结魂火
    public static final Supplier<Item> FROSTBURN_SOUL = WAICItem.ITEM.register(
        "frostburn_soul",
        () -> Organ.builder()
            .addValueAttribute(WAICAttribute.TEMPERATURE, -2)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .modifier(IceAndFireOrganhUtil::frostburnSoulModifier)
            .build()
    );

    // 悚恐怖匣
    public static final Supplier<Item> DREAD_PHYLACTERY = WAICItem.ITEM.register(
        "dread_phylactery",
        () -> Organ.builder()
            .addValueAttribute(WAICAttribute.TEMPERATURE, -3)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .modifier(IceAndFireOrganhUtil::dreadPhylacteryModifier)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .attack(IceAndFireOrganhUtil::dreadPhylacteryAttack)
            .build()
    );

    // 悚怖肋骨
    public static final Supplier<Item> DREAD_RIB = WAICItem.ITEM.register(
        "dread_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 0.5)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .build()
    );

    // 悚怖脊柱
    public static final Supplier<Item> DREAD_SPINE = WAICItem.ITEM.register(
        "dread_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 0.5)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -1)
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .attack(IceAndFireOrganhUtil::dreadSpineAttack)
            .build()
    );

    // ==================== 九头蛇器官 ====================
    // 九头蛇心脏
    public static final Supplier<Item> HYDRA_HEART = IafItems.HYDRA_HEART;

    // 九头蛇肺脏
    public static final Supplier<Item> HYDRA_LUNG = WAICItem.ITEM.register(
        "hydra_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.5)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.5)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip(3))
            .skill(IceAndFireOrganhUtil::hydraLungSkill)
            .cooldown(20)
            .build()
    );

    // 九头蛇脊柱
    public static final Supplier<Item> HYDRA_SPINE = WAICItem.ITEM.register(
        "hydra_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.75)
            .addValueAttribute(InitAttribute.DEFENSE, 0.875)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 九头蛇胃
    public static final Supplier<Item> HYDRA_STOMACH = WAICItem.ITEM.register(
        "hydra_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 1.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip(2))
            .build()
    );

    // 九头蛇肠子
    public static final Supplier<Item> HYDRA_INTESTINE = WAICItem.ITEM.register(
        "hydra_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 1.5)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip())
            .build()
    );

    // 九头蛇脾脏
    public static final Supplier<Item> HYDRA_SPLEEN = WAICItem.ITEM.register(
        "hydra_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 1.5)
            .skillTooltip(WAICTooltipUtil.skillTooltip(3))
            .added(IceAndFireOrganhUtil::hydraSpleenAdded)
            .build()
    );

    // 九头蛇肋骨
    public static final Supplier<Item> HYDRA_RIB = WAICItem.ITEM.register(
        "hydra_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.5)
            .skillTooltip(WAICTooltipUtil.skillTooltip(3))
            .build()
    );

    // 九头蛇肌肉
    public static final Supplier<Item> HYDRA_MUSCLE = WAICItem.ITEM.register(
        "hydra_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1.5)
            .addValueAttribute(InitAttribute.SPEED, 1.25)
            .skillTooltip(WAICTooltipUtil.skillTooltip(3))
            .build()
    );

    // ==================== 幻想种器官 ====================
    // 幻想种心脏
    public static final Supplier<Item> FANTASTICAL_HEART = WAICItem.ITEM.register(
        "fantastical_heart",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.HEALTH, 1.25)
            .build()
    );

    // 幻想种肺脏
    public static final Supplier<Item> FANTASTICAL_LUNG = WAICItem.ITEM.register(
        "fantastical_lung",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.BREATH_RECOVERY, 1.25)
            .addValueAttribute(InitAttribute.BREATH_CAPACITY, 1.25)
            .addValueAttribute(InitAttribute.ENDURANCE, 1.25)
            .build()
    );

    // 幻想种脊柱
    public static final Supplier<Item> FANTASTICAL_SPINE = WAICItem.ITEM.register(
        "fantastical_spine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NERVES, 1.25)
            .addValueAttribute(InitAttribute.DEFENSE, 0.625)
            .build()
    );

    // 幻想种胃
    public static final Supplier<Item> FANTASTICAL_STOMACH = WAICItem.ITEM.register(
        "fantastical_stomach",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DIGESTION, 1.25)
            .build()
    );

    // 幻想种肠子
    public static final Supplier<Item> FANTASTICAL_INTESTINE = WAICItem.ITEM.register(
        "fantastical_intestine",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.NUTRITION, 1.25)
            .build()
    );

    // 幻想种肾脏
    public static final Supplier<Item> FANTASTICAL_KIDNEY = WAICItem.ITEM.register(
        "fantastical_kidney",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.FILTRATION, 1.25)
            .build()
    );

    // 幻想种脾脏
    public static final Supplier<Item> FANTASTICAL_SPLEEN = WAICItem.ITEM.register(
        "fantastical_spleen",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.METABOLISM, 1.25)
            .build()
    );

    // 幻想种肝脏
    public static final Supplier<Item> FANTASTICAL_LIVER = WAICItem.ITEM.register(
        "fantastical_liver",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DETOXIFICATION, 1.25)
            .build()
    );

    // 幻想种阑尾
    public static final Supplier<Item> FANTASTICAL_APPENDIX = WAICItem.ITEM.register(
        "fantastical_appendix",
        () -> Organ.builder()
            .addValueAttribute(Attributes.LUCK, 1.25)
            .build()
    );

    // 幻想种肋骨
    public static final Supplier<Item> FANTASTICAL_RIB = WAICItem.ITEM.register(
        "fantastical_rib",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.DEFENSE, 1.25)
            .build()
    );

    // 幻想种肌肉
    public static final Supplier<Item> FANTASTICAL_MUSCLE = WAICItem.ITEM.register(
        "fantastical_muscle",
        () -> Organ.builder()
            .addValueAttribute(InitAttribute.STRENGTH, 1.25)
            .addValueAttribute(InitAttribute.SPEED, 1.25)
            .build()
    );

    public static void register() {
    }

    public static void setupOrgans() {
        // 火龙心脏
        Organ.builder(IafItems.FIRE_DRAGON_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.STRENGTH, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, 2)
            .build();
        // 冰龙心脏
        Organ.builder(IafItems.ICE_DRAGON_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.DEFENSE, 0.25)
            .addValueAttribute(WAICAttribute.TEMPERATURE, -2)
            .build();
        // 电龙心脏
        Organ.builder(IafItems.LIGHTNING_DRAGON_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .addValueAttribute(InitAttribute.SPEED, 0.25)
            .build();
        // 九头蛇心脏
        Organ.builder(IafItems.HYDRA_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 1.5)
            .addValueAttribute(InitAttribute.METABOLISM, 10)
            .descriptionTooltip(WAICTooltipUtil.descriptionTooltip())
            .skillTooltip(WAICTooltipUtil.skillTooltip(1))
            .tick(IceAndFireOrganhUtil::hydraHeartTick)
            .build();
    }
}

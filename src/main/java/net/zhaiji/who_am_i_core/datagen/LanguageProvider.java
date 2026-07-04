package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.manager.AttributeDisplayManager;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.manager.CataclysmChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.CompanionsChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.FDBossesChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.IceAndFireChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.IronSpellChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.MowziesMobChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICDamageTagManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.AnvilCraftOrgans;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICCreativeModeTab;
import net.zhaiji.who_am_i_core.register.WAICEffect;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.function.Supplier;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";

    public final String locale;

    public LanguageProvider(PackOutput output, String locale) {
        super(output, WhoAmICore.MOD_ID, locale);
        this.locale = locale;
    }

    public void English() {
        add(WAICCreativeModeTab.WHO_AM_I_CORE_TAB_TRANSLATABLE, "Who Am I");

        addItem(WAICItem.PETITE_CHEST_OPENER, "Petite Chest Opener");
        addItem(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION, "Fire Dragon Blood Preparation");
        addItem(WAICItem.ICE_DRAGON_BLOOD_PREPARATION, "Ice Dragon Blood Preparation");
        addItem(WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION, "Lightning Dragon Blood Preparation");
        addItem(WAICItem.DRAGON_BLOOD_PREPARATION_GROUP, "Dragon Blood Preparation");
        addItem(WAICItem.BODY_SWAPPER, "Body Swapper");

        addAttribute(WAICAttribute.BLOCK, "Block");
        addAttribute(WAICAttribute.COUNTER_ATTACK, "Counter Attack");
        addAttribute(WAICAttribute.HEAL, "Heal");
        addAttribute(WAICAttribute.MELEE_DAMAGE, "Melee Damage");
        addAttribute(WAICAttribute.RANGED_DAMAGE, "Ranged Damage");
        addAttribute(WAICAttribute.MAGIC_DAMAGE, "Magic Damage");
        addAttribute(WAICAttribute.MELEE_DAMAGE_PERCENTAGE, "Melee Damage Percentage");
        addAttribute(WAICAttribute.RANGED_DAMAGE_PERCENTAGE, "Ranged Damage Percentage");
        addAttribute(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, "Magic Damage Percentage");
        addAttribute(WAICAttribute.LOOTING, "Looting");
        addAttribute(WAICAttribute.FORTUNE, "Fortune");

        addChestCavityTypeName(WAICChestCavityTypeManager.FANTASTICAL, "Fantastical");

        addChestCavityTypeName(IceAndFireChestCavityTypeManager.FIRE_DRAGON, "Fire Dragon");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.ICE_DRAGON, "Ice Dragon");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.LIGHTNING_DRAGON, "Lightning Dragon");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.HYDRA, "Hydra");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.DREAD_SKELETON, "Dread Skeleton");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.DREAD_UNDEAD, "Dread Undead");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.DREAD_LICH, "Dread Lich");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.GHOST, "Ghost");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.CYCLOPS, "Cyclops");

        addChestCavityTypeName(MowziesMobChestCavityTypeManager.AGED, "Aged");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.UMVUTHI, "Umvuthi");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.BLUFF, "Bluff");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.LANTERN, "Lantern");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.FOLIAATH, "Foliaath");

        addChestCavityTypeName(FDBossesChestCavityTypeManager.MALKUTH, "Malkuth");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.GEBURAH, "Geburah");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.CHESED, "Chesed");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.FIRE_MALKUTH_WARRIOR, "Fire Malkuth Warrior");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.ICE_MALKUTH_WARRIOR, "Ice Malkuth Warrior");

        addChestCavityTypeName(CataclysmChestCavityTypeManager.LEVIATHAN, "Leviathan");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.NETHERITE_MONSTROSITY, "Netherite Monstrosity");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.NETHERITE_MINISTROSITY, "Netherite Ministrosity");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.IGNIS, "Ignis");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.CORAL_GOLEM, "Coral Golem");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.CORALSSUS, "Coralssus");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.SCYLLA, "Scylla");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.ENDER_GUARDIAN, "Ender Guardian");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.ENDER_GOLEM, "Ender Golem");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.DRAUGR, "Draugr");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.MALEDICTUS, "Maledictus");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.HARBINGER, "Harbinger");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.PROWLER, "Prowler");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.WATCHER, "Watcher");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.IGNITED_REVENANT, "Ignited Revenant");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.IGNITED_BERSERKER, "Ignited Berserker");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.CATACLYSM_AMPHIBIOUS, "Amphibious");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.REMNANT, "Remnant");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.KOBOLETON, "Koboleton");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.AMETHYST_CRAB, "Amethyst Crab");

        addChestCavityTypeName(IronSpellChestCavityTypeManager.DEAD_KING, "Dead King");
        addChestCavityTypeName(IronSpellChestCavityTypeManager.NECROMANCER, "Necromancer");
        addChestCavityTypeName(IronSpellChestCavityTypeManager.FIRE_BOSS, "Primordial Flame");
        addChestCavityTypeName(IronSpellChestCavityTypeManager.CURSED_ARMOR_STAND, "Cursed Armor Stand");

        addChestCavityTypeName(CompanionsChestCavityTypeManager.PONTIFF, "Pontiff");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.CLOTH, "Cloth");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.CAKE, "Cake");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.SHADE, "Shade");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.LIVING_CANDLE, "Living Candle");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.DINAMO, "Dinamo");

        addAttributeDescription(
            WAICAttribute.HEAL,
            "Restores health equal to attribute value every second"
        );
        addAttributeDescription(
            WAICAttribute.BLOCK,
            "Reduces incoming damage by a flat amount",
            "Can reduce damage to zero but not below"
        );
        addAttributeDescription(
            WAICAttribute.COUNTER_ATTACK,
            "Deals thorns damage to the attacker when hit",
            "Does not trigger on self-damage or thorns damage"
        );
        addAttributeDescription(
            WAICAttribute.MELEE_DAMAGE,
            "Adds flat bonus damage when hit by melee attacks"
        );
        addAttributeDescription(
            WAICAttribute.RANGED_DAMAGE,
            "Adds flat bonus damage when hit by ranged attacks"
        );
        addAttributeDescription(
            WAICAttribute.MAGIC_DAMAGE,
            "Adds flat bonus damage when hit by magic attacks"
        );
        addAttributeDescription(
            WAICAttribute.MELEE_DAMAGE_PERCENTAGE,
            "Final multiplier for melee damage, default 100%"
        );
        addAttributeDescription(
            WAICAttribute.RANGED_DAMAGE_PERCENTAGE,
            "Final multiplier for ranged damage, default 100%"
        );
        addAttributeDescription(
            WAICAttribute.MAGIC_DAMAGE_PERCENTAGE,
            "Final multiplier for magic damage, default 100%"
        );
        addAttributeDescription(
            WAICAttribute.LOOTING,
            "Adds extra levels to Looting enchantment effect"
        );
        addAttributeDescription(
            WAICAttribute.FORTUNE,
            "Adds extra levels to Fortune enchantment effect"
        );

        addAttributeValueEffect(WAICAttribute.HEAL, "Heals %s HP per second");
        addAttributeValueEffect(WAICAttribute.BLOCK, "Reduces damage by %s");
        addAttributeValueEffect(WAICAttribute.COUNTER_ATTACK, "Deals %s thorns damage to attacker");
        addAttributeValueEffect(WAICAttribute.MELEE_DAMAGE, "Adds %s melee damage");
        addAttributeValueEffect(WAICAttribute.RANGED_DAMAGE, "Adds %s ranged damage");
        addAttributeValueEffect(WAICAttribute.MAGIC_DAMAGE, "Adds %s magic damage");
        addAttributeValueEffect(WAICAttribute.MELEE_DAMAGE_PERCENTAGE, "Melee final multiplier: %s%%");
        addAttributeValueEffect(WAICAttribute.RANGED_DAMAGE_PERCENTAGE, "Ranged final multiplier: %s%%");
        addAttributeValueEffect(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, "Magic final multiplier: %s%%");
        addAttributeValueEffect(WAICAttribute.LOOTING, "Looting level: %s");
        addAttributeValueEffect(WAICAttribute.FORTUNE, "Fortune level: %s");

        add(WAICDamageTagManager.IS_MELEE, "Melee");
        addWAICTagTranslationsEN();

        addEffect(WAICEffect.FIRE_DRAGON_POWER::value, "Fire Dragon Power");
        addEffect(WAICEffect.ICE_DRAGON_POWER::value, "Ice Dragon Power");
        addEffect(WAICEffect.LIGHTNING_DRAGON_POWER::value, "Lightning Dragon Power");
        addEffect(WAICEffect.DRAGON_POWER::value, "Dragon Power");
        addEffect(WAICEffect.SWEETNESS::value, "Sweetness");
        addEffect(WAICEffect.OVERLOAD::value, "Overload");

        add("organ.who_am_i_core.unfinished", "Not yet completed");

        mowziesMobSpecialOrgansEN();
        bluffOrgansEN();
        sculptorOrgansEN();
        WAICOrgansEN();
        dragonOrgansEN();
        mimicOrgansEN();
        cyberneticOrgansEN();
        inkOrgansEN();
        clothOrgansEN();
        crimsonOrgansEN();
        elementOrgansEN();
        pigmentOrgansEN();
        woodenOrgansEN();
        frankensteinOrgansEN();
        lesionOrgansEN();
        nineHellOrgansEN();
        dreadOrgansEN();
        hydraOrgansEN();
        fdBossesOrgansEN();
        royalSteelOrgansEN();
        cursedGoldOrgansEN();
        emberMetalOrgansEN();
        frostMetalOrgansEN();
        transcendiumOrgansEN();
        railgunEN();
        fantasticalOrgansEN();
        cataclysmOrgansEN();
        ironSpellOrgansEN();
        companionsOrgansEN();

        add("message.who_am_i_core.dragon_blood.power_draw", "You injected this dragon blood preparation, chest cavity expanded");
        add("message.who_am_i_core.dragon_blood.already_used", "This dragon blood preparation has already been injected");
        add("message.who_am_i_core.dragon_blood.max_level", "Already at maximum expansion");
        add("message.who_am_i_core.dragon_blood.group_draw", "You injected all dragon blood preparation, chest cavity fully expanded");

        add("message.who_am_i_core.dragon_blood.other_power_draw", "You injected this dragon blood preparation into %s, chest cavity expanded");
        add("message.who_am_i_core.dragon_blood.other_already_used", "%s has already been injected with this dragon blood preparation");
        add("message.who_am_i_core.dragon_blood.other_max_level", "%s is already at maximum expansion");
        add("message.who_am_i_core.dragon_blood.other_group_draw", "You injected all dragon blood preparation into %s, chest cavity fully expanded");

        add("tooltip.who_am_i_core.dragon_blood.draw.0", "Right-click to inject this dragon blood preparation, expand chest cavity");
        add("tooltip.who_am_i_core.dragon_blood.draw.1", "Shift-right-click to inject into other creatures");
        add("tooltip.who_am_i_core.dragon_blood.group_draw.0", "Right-click to inject all dragon blood preparation, expand chest cavity to maximum");
        add("tooltip.who_am_i_core.dragon_blood.group_draw.1", "Shift-right-click to inject into other creatures");

        add("message.who_am_i_core.body_swapper.hostile_health_too_high", "%s's health is too high to swap");
        add("message.who_am_i_core.body_swapper.target_lost", "Target lost");
        add("tooltip.who_am_i_core.body_swapper.0", "Hold right-click on a creature in sight to swap chest cavity organs");
        add("tooltip.who_am_i_core.body_swapper.1", "Hostile creatures and players require health below 30%, tamed and other creatures have no limit");
        add("tooltip.who_am_i_core.body_swapper.2", "Both sides gain Darkness, Weakness, and Nausea for 3 seconds");
    }

    public void Chinese() {
        add(WAICCreativeModeTab.WHO_AM_I_CORE_TAB_TRANSLATABLE, "我非我");

        addItem(WAICItem.PETITE_CHEST_OPENER, "娇小开胸器");
        addItem(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION, "火龙血药剂");
        addItem(WAICItem.ICE_DRAGON_BLOOD_PREPARATION, "冰龙血药剂");
        addItem(WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION, "电龙血药剂");
        addItem(WAICItem.DRAGON_BLOOD_PREPARATION_GROUP, "龙血药剂组");
        addItem(WAICItem.BODY_SWAPPER, "躯体交换器");

        addAttribute(WAICAttribute.BLOCK, "格挡");
        addAttribute(WAICAttribute.COUNTER_ATTACK, "反击");
        addAttribute(WAICAttribute.HEAL, "治疗");
        addAttribute(WAICAttribute.MELEE_DAMAGE, "近战伤害");
        addAttribute(WAICAttribute.RANGED_DAMAGE, "远程伤害");
        addAttribute(WAICAttribute.MAGIC_DAMAGE, "魔法伤害");
        addAttribute(WAICAttribute.MELEE_DAMAGE_PERCENTAGE, "近战伤害百分比");
        addAttribute(WAICAttribute.RANGED_DAMAGE_PERCENTAGE, "远程伤害百分比");
        addAttribute(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, "魔法伤害百分比");
        addAttribute(WAICAttribute.LOOTING, "抢夺");
        addAttribute(WAICAttribute.FORTUNE, "时运");

        addChestCavityTypeName(WAICChestCavityTypeManager.FANTASTICAL, "幻想种");

        addChestCavityTypeName(IceAndFireChestCavityTypeManager.FIRE_DRAGON, "火龙");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.ICE_DRAGON, "冰龙");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.LIGHTNING_DRAGON, "电龙");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.HYDRA, "九头蛇");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.DREAD_SKELETON, "悚怖骷髅");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.DREAD_UNDEAD, "悚怖亡灵");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.DREAD_LICH, "悚怖尸巫");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.GHOST, "幽灵");
        addChestCavityTypeName(IceAndFireChestCavityTypeManager.CYCLOPS, "独眼巨人");

        addChestCavityTypeName(MowziesMobChestCavityTypeManager.AGED, "衰老");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.UMVUTHI, "太阳鸟");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.BLUFF, "泥峭");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.LANTERN, "荧光浮灯");
        addChestCavityTypeName(MowziesMobChestCavityTypeManager.FOLIAATH, "巨噬叶");

        addChestCavityTypeName(FDBossesChestCavityTypeManager.MALKUTH, "王国");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.GEBURAH, "严厉");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.CHESED, "慈悲");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.FIRE_MALKUTH_WARRIOR, "火焰王国战士");
        addChestCavityTypeName(FDBossesChestCavityTypeManager.ICE_MALKUTH_WARRIOR, "冰霜王国战士");

        addChestCavityTypeName(CataclysmChestCavityTypeManager.LEVIATHAN, "利维坦");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.NETHERITE_MONSTROSITY, "下界合金巨兽");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.NETHERITE_MINISTROSITY, "下界合金幼兽");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.IGNIS, "焰魔");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.CORAL_GOLEM, "珊瑚傀儡");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.CORALSSUS, "珊瑚巨像");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.SCYLLA, "斯库拉");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.ENDER_GUARDIAN, "末影守卫");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.ENDER_GOLEM, "末影傀儡");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.DRAUGR, "魂尸");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.MALEDICTUS, "咒翼灵骸");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.HARBINGER, "先驱者");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.PROWLER, "徘徊者");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.WATCHER, "观测者");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.IGNITED_REVENANT, "炽燃遗魂");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.IGNITED_BERSERKER, "炽燃狂魂");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.CATACLYSM_AMPHIBIOUS, "两栖");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.REMNANT, "遗魂");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.KOBOLETON, "骸龙");
        addChestCavityTypeName(CataclysmChestCavityTypeManager.AMETHYST_CRAB, "紫水晶巨蟹");

        addChestCavityTypeName(IronSpellChestCavityTypeManager.DEAD_KING, "死者之王");
        addChestCavityTypeName(IronSpellChestCavityTypeManager.NECROMANCER, "死灵法师");
        addChestCavityTypeName(IronSpellChestCavityTypeManager.FIRE_BOSS, "原初之火");
        addChestCavityTypeName(IronSpellChestCavityTypeManager.CURSED_ARMOR_STAND, "诅咒盔甲架");

        addChestCavityTypeName(CompanionsChestCavityTypeManager.PONTIFF, "教宗");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.CLOTH, "布制");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.CAKE, "蛋糕");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.SHADE, "暗影");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.LIVING_CANDLE, "活体蜡烛");
        addChestCavityTypeName(CompanionsChestCavityTypeManager.DINAMO, "电纳魔");

        addAttributeDescription(
            WAICAttribute.HEAL,
            "每秒回复等同于属性值的生命值"
        );
        addAttributeDescription(
            WAICAttribute.BLOCK,
            "等值减少受到的伤害",
            "可将伤害减至零但不会低于零"
        );
        addAttributeDescription(
            WAICAttribute.COUNTER_ATTACK,
            "受到伤害时对攻击者造成等值荆棘伤害",
            "自伤和荆棘伤害不会触发反击"
        );
        addAttributeDescription(
            WAICAttribute.MELEE_DAMAGE,
            "受到近战攻击时额外增加等值伤害"
        );
        addAttributeDescription(
            WAICAttribute.RANGED_DAMAGE,
            "受到远程攻击时额外增加等值伤害"
        );
        addAttributeDescription(
            WAICAttribute.MAGIC_DAMAGE,
            "受到魔法攻击时额外增加等值伤害"
        );
        addAttributeDescription(
            WAICAttribute.MELEE_DAMAGE_PERCENTAGE,
            "近战伤害的最终倍率，默认100%"
        );
        addAttributeDescription(
            WAICAttribute.RANGED_DAMAGE_PERCENTAGE,
            "远程伤害的最终倍率，默认100%"
        );
        addAttributeDescription(
            WAICAttribute.MAGIC_DAMAGE_PERCENTAGE,
            "魔法伤害的最终倍率，默认100%"
        );
        addAttributeDescription(
            WAICAttribute.LOOTING,
            "额外增加抢夺附魔的等效等级"
        );
        addAttributeDescription(
            WAICAttribute.FORTUNE,
            "额外增加时运附魔的等效等级"
        );

        addAttributeValueEffect(WAICAttribute.HEAL, "每秒回复%s点生命值");
        addAttributeValueEffect(WAICAttribute.BLOCK, "减免%s点伤害");
        addAttributeValueEffect(WAICAttribute.COUNTER_ATTACK, "对攻击者造成%s点反击伤害");
        addAttributeValueEffect(WAICAttribute.MELEE_DAMAGE, "增加%s点近战伤害");
        addAttributeValueEffect(WAICAttribute.RANGED_DAMAGE, "增加%s点远程伤害");
        addAttributeValueEffect(WAICAttribute.MAGIC_DAMAGE, "增加%s点魔法伤害");
        addAttributeValueEffect(WAICAttribute.MELEE_DAMAGE_PERCENTAGE, "近战最终倍率：%s%%");
        addAttributeValueEffect(WAICAttribute.RANGED_DAMAGE_PERCENTAGE, "远程最终倍率：%s%%");
        addAttributeValueEffect(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, "魔法最终倍率：%s%%");
        addAttributeValueEffect(WAICAttribute.LOOTING, "抢夺等级：%s");
        addAttributeValueEffect(WAICAttribute.FORTUNE, "时运等级：%s");

        add(WAICDamageTagManager.IS_MELEE, "近战");
        addWAICTagTranslationsZH();

        addEffect(WAICEffect.FIRE_DRAGON_POWER::value, "火龙之力");
        addEffect(WAICEffect.ICE_DRAGON_POWER::value, "冰龙之力");
        addEffect(WAICEffect.LIGHTNING_DRAGON_POWER::value, "电龙之力");
        addEffect(WAICEffect.DRAGON_POWER::value, "龙之力");
        addEffect(WAICEffect.SWEETNESS::value, "甜蜜");
        addEffect(WAICEffect.OVERLOAD::value, "超频");

        add("organ.who_am_i_core.unfinished", "仍未完成");

        mowziesMobSpecialOrgansZH();
        bluffOrgansZH();
        sculptorOrgansZH();
        WAICOrgansZH();
        dragonOrgansZH();
        mimicOrgansZH();
        cyberneticOrgansZH();
        inkOrgansZH();
        clothOrgansZH();
        crimsonOrgansZH();
        elementOrgansZH();
        pigmentOrgansZH();
        woodenOrgansZH();
        frankensteinOrgansZH();
        lesionOrgansZH();
        nineHellOrgansZH();
        dreadOrgansZH();
        hydraOrgansZH();
        fdBossesOrgansZH();
        royalSteelOrgansZH();
        cursedGoldOrgansZH();
        emberMetalOrgansZH();
        frostMetalOrgansZH();
        transcendiumOrgansZH();
        railgunZH();
        fantasticalOrgansZH();
        cataclysmOrgansZH();
        ironSpellOrgansZH();
        companionsOrgansZH();

        add("message.who_am_i_core.dragon_blood.power_draw", "你注射了此龙血药剂，胸腔扩容");
        add("message.who_am_i_core.dragon_blood.already_used", "已注射过此龙血药剂");
        add("message.who_am_i_core.dragon_blood.max_level", "已达到最大扩容等级");
        add("message.who_am_i_core.dragon_blood.group_draw", "你注射了所有龙血药剂，胸腔直接扩至最大");

        add("message.who_am_i_core.dragon_blood.other_power_draw", "你对%s注射了此龙血药剂，其胸腔扩容");
        add("message.who_am_i_core.dragon_blood.other_already_used", "%s已注射过此龙血药剂");
        add("message.who_am_i_core.dragon_blood.other_max_level", "%s已达到最大扩容等级");
        add("message.who_am_i_core.dragon_blood.other_group_draw", "你对%s注射了所有龙血药剂，其胸腔直接扩至最大");

        add("tooltip.who_am_i_core.dragon_blood.draw.0", "右键注射此龙血药剂，胸腔扩容");
        add("tooltip.who_am_i_core.dragon_blood.draw.1", "潜行右键可对其他生物注射");
        add("tooltip.who_am_i_core.dragon_blood.group_draw.0", "右键注射所有龙血药剂，胸腔直接扩至最大");
        add("tooltip.who_am_i_core.dragon_blood.group_draw.1", "潜行右键可对其他生物注射");

        add("message.who_am_i_core.body_swapper.hostile_health_too_high", "%s的血量过高，无法交换");
        add("message.who_am_i_core.body_swapper.target_lost", "目标已丢失");
        add("tooltip.who_am_i_core.body_swapper.0", "长按右键对视线内的生物交换胸腔内的器官");
        add("tooltip.who_am_i_core.body_swapper.1", "敌对生物与玩家需血量低于30%，宠物与其他生物无限制");
        add("tooltip.who_am_i_core.body_swapper.2", "交换后双方获得3秒黑暗、虚弱、反胃效果");
    }

    // ==================== Mowzie's Mobs 特殊器官 ====================

    private void mowziesMobSpecialOrgansEN() {
        addItem(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, "Ferrous Wroughtnaut Heart Mirror");
        addOrganPassiveEffect(
            MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR,
            "Removes FOV modification",
            "Grants Block equal to Strength ÷ 2",
            "Cannot move for 3 seconds after attacking"
        );
        addOrganPassiveEffectSimple(
            MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR,
            "Grants Block based on Strength ÷ 2, but restricts movement after attacking"
        );

        addItem(MowziesMobOrgans.CHEST_NOVA, "Chest Nova");
        addOrganDescription(MowziesMobOrgans.CHEST_NOVA, "It beats like a heart");
        addOrganPassiveEffect(
            MowziesMobOrgans.CHEST_NOVA,
            "Burns non-mechanical and non-magical organs in adjacent slots when chest cavity closes",
            "Umvuthana masks in adjacent slots summon corresponding followers that respawn 30 seconds after death",
            "Masks provide their potion effects to the owner"
        );
        addOrganPassiveEffectSimple(
            MowziesMobOrgans.CHEST_NOVA,
            "Burns nearby organs and summons followers from masks"
        );

        addItem(MowziesMobOrgans.CONTROL_ROD, "Control Rod");
        addOrganDescription(MowziesMobOrgans.CONTROL_ROD, "A certain hellbird's control rod");
        add("organ." + WhoAmICore.MOD_ID + ".control_rod.hint", "Effects below activate when within adjacent slots of Chest Nova");
        addOrganPassiveEffect(
            MowziesMobOrgans.CONTROL_ROD,
            "Follower respawn cooldown reduced from 30s to 10s",
            "Masks also provide effects to followers"
        );
    }

    private void mowziesMobSpecialOrgansZH() {
        addItem(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, "钢铁守护者护心镜");
        addOrganPassiveEffect(
            MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR,
            "移除FOV修改",
            "根据力量÷2提供格挡",
            "攻击后3秒内不能移动"
        );
        addOrganPassiveEffectSimple(
            MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR,
            "根据力量÷2提供格挡，但攻击后无法移动"
        );

        addItem(MowziesMobOrgans.CHEST_NOVA, "胸中新星");
        addOrganDescription(MowziesMobOrgans.CHEST_NOVA, "它像心脏一样跳动着");
        addOrganPassiveEffect(
            MowziesMobOrgans.CHEST_NOVA,
            "胸腔关闭时烧毁相邻槽位中非机械且非魔法的器官",
            "相邻槽位中的乌姆塔纳面具会召唤对应的追随者，死亡30秒后重新召唤",
            "面具会为主人提供其药水效果"
        );
        addOrganPassiveEffectSimple(
            MowziesMobOrgans.CHEST_NOVA,
            "烧毁周围器官并从面具中召唤追随者"
        );

        addItem(MowziesMobOrgans.CONTROL_ROD, "制御棒");
        addOrganDescription(MowziesMobOrgans.CONTROL_ROD, "这他妈是哪个太阳鸟？");
        add("organ.who_am_i_core.control_rod.hint", "位于胸中新星相邻槽位时，以下效果生效");
        addOrganPassiveEffect(
            MowziesMobOrgans.CONTROL_ROD,
            "追随者重新召唤冷却时间从30秒缩短为10秒",
            "面具也会给追随者提供效果"
        );
    }

    // ==================== 泥峭器官 ====================

    private void bluffOrgansEN() {
        addItem(MowziesMobOrgans.BLUFF_CORE, "Bluff Core");
        addOrganPassiveEffect(
            MowziesMobOrgans.BLUFF_CORE,
            "Can eat dirt items"
        );
        addOrganActiveSkill(
            MowziesMobOrgans.BLUFF_CORE,
            "Consume the dirt block you are looking at",
            "Restores 4 hunger when eating dirt",
            "Different dirt types grant different effects:",
            "Grass/Moss/Mycelium grant Strength II",
            "Coarse Dirt/Podzol/Mud grant Haste II",
            "Rooted Dirt/Muddy Mangrove Roots grant Resistance II"
        );
        addOrganActiveSkillSimple(
            MowziesMobOrgans.BLUFF_CORE,
            "Consume dirt blocks to gain various effects"
        );

        addItem(MowziesMobOrgans.BLUFF_TABLET, "Bluff Tablet");
        addOrganPassiveEffect(
            MowziesMobOrgans.BLUFF_TABLET,
            "Can eat dirt items",
            "Each tablet, gain 2 absorption hearts when eating dirt",
            "Maximum absorption equals mud organ count times 8"
        );

        addItem(MowziesMobOrgans.ACTIVE_BLUFF_ROD, "Active Bluff Rod");
        addOrganPassiveEffect(
            MowziesMobOrgans.ACTIVE_BLUFF_ROD,
            "Can eat dirt items",
            "Each rod, gain 4 saturation when eating dirt"
        );
    }

    private void bluffOrgansZH() {
        addItem(MowziesMobOrgans.BLUFF_CORE, "泥峭核心");
        addOrganPassiveEffect(
            MowziesMobOrgans.BLUFF_CORE,
            "能够食用泥土物品"
        );
        addOrganActiveSkill(
            MowziesMobOrgans.BLUFF_CORE,
            "食用视线方向的泥土方块",
            "食用泥土时恢复4点饥饿值",
            "不同泥土类型提供不同效果：",
            "草方块/苔藓块/菌丝提供力量II",
            "砂土/灰化土/泥巴提供急迫II",
            "缠根泥土/含泥红树根提供抗性提升II"
        );
        addOrganActiveSkillSimple(
            MowziesMobOrgans.BLUFF_CORE,
            "食用泥土方块以获得各种效果"
        );

        addItem(MowziesMobOrgans.BLUFF_TABLET, "泥峭铭文板");
        addOrganPassiveEffect(
            MowziesMobOrgans.BLUFF_TABLET,
            "能够食用泥土物品",
            "每有一块铭文板，食用泥土时获得2点吸收生命值",
            "吸收生命值上限为泥峭器官数量乘以8"
        );

        addItem(MowziesMobOrgans.ACTIVE_BLUFF_ROD, "活性泥峭棒");
        addOrganPassiveEffect(
            MowziesMobOrgans.ACTIVE_BLUFF_ROD,
            "能够食用泥土物品",
            "每有一根泥峭棒，食用泥土时增加4点饱和度"
        );
    }

    // ==================== 雕刻家—通臂大师器官 ====================

    private void sculptorOrgansEN() {
        addItem(MowziesMobOrgans.ZEN_HEART, "Zen Heart");
        addOrganPassiveEffect(
            MowziesMobOrgans.ZEN_HEART,
            "Immune to fall damage",
            "Reduces damage taken by 50% while under Geomancy effect"
        );
        addOrganPassiveEffectSimple(
            MowziesMobOrgans.ZEN_HEART,
            "Immune to fall damage and reduces damage under Geomancy"
        );

        addItem(MowziesMobOrgans.AGED_HEART, "Aged Heart");
        addItem(MowziesMobOrgans.AGED_LUNG, "Aged Lung");
        addItem(MowziesMobOrgans.AGED_SPINE, "Aged Spine");
        addItem(MowziesMobOrgans.AGED_STOMACH, "Aged Stomach");
        addItem(MowziesMobOrgans.AGED_INTESTINE, "Aged Intestine");
        addItem(MowziesMobOrgans.AGED_KIDNEY, "Aged Kidney");
        addItem(MowziesMobOrgans.AGED_SPLEEN, "Aged Spleen");
        addItem(MowziesMobOrgans.AGED_LIVER, "Aged Liver");
        addItem(MowziesMobOrgans.AGED_APPENDIX, "Aged Appendix");
        addItem(MowziesMobOrgans.AGED_RIB, "Aged Rib");
        addItem(MowziesMobOrgans.AGED_MUSCLE, "Aged Muscle");
    }

    private void sculptorOrgansZH() {
        addItem(MowziesMobOrgans.ZEN_HEART, "禅心");
        addOrganPassiveEffect(
            MowziesMobOrgans.ZEN_HEART,
            "免疫摔落伤害",
            "拥有大地祝福效果时受到伤害减免50%"
        );
        addOrganPassiveEffectSimple(
            MowziesMobOrgans.ZEN_HEART,
            "免疫摔落伤害并在大地祝福效果下减伤"
        );

        addItem(MowziesMobOrgans.AGED_HEART, "衰老心脏");
        addItem(MowziesMobOrgans.AGED_LUNG, "衰老肺脏");
        addItem(MowziesMobOrgans.AGED_SPINE, "衰老脊柱");
        addItem(MowziesMobOrgans.AGED_STOMACH, "衰老胃");
        addItem(MowziesMobOrgans.AGED_INTESTINE, "衰老肠子");
        addItem(MowziesMobOrgans.AGED_KIDNEY, "衰老肾脏");
        addItem(MowziesMobOrgans.AGED_SPLEEN, "衰老脾脏");
        addItem(MowziesMobOrgans.AGED_LIVER, "衰老肝脏");
        addItem(MowziesMobOrgans.AGED_APPENDIX, "衰老阑尾");
        addItem(MowziesMobOrgans.AGED_RIB, "衰老肋骨");
        addItem(MowziesMobOrgans.AGED_MUSCLE, "衰老肌肉");
    }

    // ==================== 龙器官 ====================

    private void dragonOrgansEN() {
        // Fire Dragon
        addItem(IceAndFireOrgans.FIRE_DRAGON_HEART, "Fire Dragon Heart");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LUNG, "Fire Dragon Lung");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPINE, "Fire Dragon Spine");
        addItem(IceAndFireOrgans.FIRE_DRAGON_STOMACH, "Fire Dragon Stomach");
        addItem(IceAndFireOrgans.FIRE_DRAGON_INTESTINE, "Fire Dragon Intestine");
        addItem(IceAndFireOrgans.FIRE_DRAGON_KIDNEY, "Fire Dragon Kidney");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPLEEN, "Fire Dragon Spleen");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LIVER, "Fire Dragon Liver");

        addItem(IceAndFireOrgans.FIRE_DRAGON_GEM, "Fire Dragon Gem");
        addOrganPassiveEffect(
            IceAndFireOrgans.FIRE_DRAGON_GEM,
            "Fire spell power increases with fire dragon organs in chest",
            "Each fire dragon organ grants +5% fire spell power"
        );
        addOrganPassiveEffectSimple(IceAndFireOrgans.FIRE_DRAGON_GEM, "Fire spell power scales with fire dragon organ count");

        addItem(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "Fire Dragon Breath Sac");
        addOrganActiveSkill(
            IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC,
            "Sprays fire forward for 5 seconds",
            "Continuously deals %s damage to enemies in range",
            "Damage scales with fire dragon organ count, caps at %s"
        );
        addOrganActiveSkillSimple(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "Sprays fire forward, continuously dealing %s damage");

        addItem(IceAndFireOrgans.FIRE_DRAGON_RIB, "Fire Dragon Rib");
        addItem(IceAndFireOrgans.FIRE_DRAGON_MUSCLE, "Fire Dragon Muscle");

        // Ice Dragon
        addItem(IceAndFireOrgans.ICE_DRAGON_HEART, "Ice Dragon Heart");
        addItem(IceAndFireOrgans.ICE_DRAGON_LUNG, "Ice Dragon Lung");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPINE, "Ice Dragon Spine");
        addItem(IceAndFireOrgans.ICE_DRAGON_STOMACH, "Ice Dragon Stomach");
        addItem(IceAndFireOrgans.ICE_DRAGON_INTESTINE, "Ice Dragon Intestine");
        addItem(IceAndFireOrgans.ICE_DRAGON_KIDNEY, "Ice Dragon Kidney");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPLEEN, "Ice Dragon Spleen");
        addItem(IceAndFireOrgans.ICE_DRAGON_LIVER, "Ice Dragon Liver");

        addItem(IceAndFireOrgans.ICE_DRAGON_GEM, "Ice Dragon Gem");
        addOrganPassiveEffect(
            IceAndFireOrgans.ICE_DRAGON_GEM,
            "Ice spell power increases with ice dragon organs in chest",
            "Each ice dragon organ grants +5% ice spell power"
        );
        addOrganPassiveEffectSimple(IceAndFireOrgans.ICE_DRAGON_GEM, "Ice spell power scales with ice dragon organ count");

        addItem(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "Ice Dragon Breath Sac");
        addOrganActiveSkill(
            IceAndFireOrgans.ICE_DRAGON_BREATH_SAC,
            "Sprays ice forward for 5 seconds",
            "Continuously deals %s damage to enemies in range",
            "Damage scales with ice dragon organ count, caps at %s"
        );
        addOrganActiveSkillSimple(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "Sprays ice forward, continuously dealing %s damage");

        addItem(IceAndFireOrgans.ICE_DRAGON_RIB, "Ice Dragon Rib");
        addItem(IceAndFireOrgans.ICE_DRAGON_MUSCLE, "Ice Dragon Muscle");

        // Lightning Dragon
        String lightningCharge = "Generates 0.1 charge/tick for Energy Modules";
        String lightningChargeOverload = "Generated charge can exceed the cap by 50%";
        String lightningChargeSimple = "Generates charge for Energy Modules";

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, "Lightning Dragon Heart");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, "Lightning Dragon Lung");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, "Lightning Dragon Spine");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, "Lightning Dragon Stomach");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, "Lightning Dragon Intestine");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, "Lightning Dragon Kidney");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, "Lightning Dragon Spleen");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, "Lightning Dragon Liver");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, lightningChargeSimple);

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_GEM, "Lightning Dragon Gem");
        addOrganPassiveEffect(
            IceAndFireOrgans.LIGHTNING_DRAGON_GEM,
            "Lightning spell power increases with lightning dragon organs in chest",
            "Each lightning dragon organ grants +5% lightning spell power",
            lightningCharge,
            lightningChargeOverload
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.LIGHTNING_DRAGON_GEM,
            "Lightning spell power scales with lightning dragon organ count",
            lightningChargeSimple
        );

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "Lightning Dragon Breath Sac");
        addOrganPassiveEffect(
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC,
            lightningCharge,
            lightningChargeOverload
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC,
            lightningChargeSimple
        );
        addOrganActiveSkill(
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC,
            "Sprays lightning forward for 5 seconds",
            "Continuously deals %s damage to enemies in range",
            "Damage scales with lightning dragon organ count, caps at %s"
        );
        addOrganActiveSkillSimple(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "Sprays lightning forward, continuously dealing %s damage");

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, "Lightning Dragon Rib");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, lightningChargeSimple);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, "Lightning Dragon Muscle");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, lightningCharge, lightningChargeOverload);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, lightningChargeSimple);
    }

    private void dragonOrgansZH() {
        // 火龙
        addItem(IceAndFireOrgans.FIRE_DRAGON_HEART, "火龙心脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LUNG, "火龙肺脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPINE, "火龙脊柱");
        addItem(IceAndFireOrgans.FIRE_DRAGON_STOMACH, "火龙胃");
        addItem(IceAndFireOrgans.FIRE_DRAGON_INTESTINE, "火龙肠子");
        addItem(IceAndFireOrgans.FIRE_DRAGON_KIDNEY, "火龙肾脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPLEEN, "火龙脾脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LIVER, "火龙肝脏");

        addItem(IceAndFireOrgans.FIRE_DRAGON_GEM, "火龙宝玉");
        addOrganPassiveEffect(
            IceAndFireOrgans.FIRE_DRAGON_GEM,
            "火焰法术强度随胸腔内火龙器官数量增加",
            "每增加一个火龙器官提升5%火焰法术强度"
        );
        addOrganPassiveEffectSimple(IceAndFireOrgans.FIRE_DRAGON_GEM, "火焰法术强度随火龙器官数量提升");

        addItem(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "火龙吐息袋");
        addOrganActiveSkill(
            IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC,
            "向前方喷射火焰，持续5秒",
            "对范围内敌人持续造成%s点伤害",
            "伤害随火龙器官数量增加，最多计入%s个"
        );
        addOrganActiveSkillSimple(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "向前方喷射火焰，持续造成%s点伤害");

        addItem(IceAndFireOrgans.FIRE_DRAGON_RIB, "火龙肋骨");
        addItem(IceAndFireOrgans.FIRE_DRAGON_MUSCLE, "火龙肌肉");

        // 冰龙
        addItem(IceAndFireOrgans.ICE_DRAGON_HEART, "冰龙心脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_LUNG, "冰龙肺脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPINE, "冰龙脊柱");
        addItem(IceAndFireOrgans.ICE_DRAGON_STOMACH, "冰龙胃");
        addItem(IceAndFireOrgans.ICE_DRAGON_INTESTINE, "冰龙肠子");
        addItem(IceAndFireOrgans.ICE_DRAGON_KIDNEY, "冰龙肾脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPLEEN, "冰龙脾脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_LIVER, "冰龙肝脏");

        addItem(IceAndFireOrgans.ICE_DRAGON_GEM, "冰龙宝玉");
        addOrganPassiveEffect(
            IceAndFireOrgans.ICE_DRAGON_GEM,
            "冰霜法术强度随胸腔内冰龙器官数量增加",
            "每增加一个冰龙器官提升5%冰霜法术强度"
        );
        addOrganPassiveEffectSimple(IceAndFireOrgans.ICE_DRAGON_GEM, "冰霜法术强度随冰龙器官数量提升");

        addItem(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "冰龙吐息袋");
        addOrganActiveSkill(
            IceAndFireOrgans.ICE_DRAGON_BREATH_SAC,
            "向前方喷射冰霜，持续5秒",
            "对范围内敌人持续造成%s点伤害",
            "伤害随冰龙器官数量增加，最多计入%s个"
        );
        addOrganActiveSkillSimple(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "向前方喷射冰霜，持续造成%s点伤害");

        addItem(IceAndFireOrgans.ICE_DRAGON_RIB, "冰龙肋骨");
        addItem(IceAndFireOrgans.ICE_DRAGON_MUSCLE, "冰龙肌肉");

        // 电龙
        String lightningChargeZH = "每tick为蓄能模块产出0.1电荷";
        String lightningChargeOverloadZH = "产生的电荷可超出上限50%";
        String lightningChargeSimpleZH = "为蓄能模块产出电荷";

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, "电龙心脏");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, "电龙肺脏");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, "电龙脊柱");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, "电龙胃");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, "电龙肠子");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, "电龙肾脏");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, "电龙脾脏");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, "电龙肝脏");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, lightningChargeSimpleZH);

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_GEM, "电龙宝玉");
        addOrganPassiveEffect(
            IceAndFireOrgans.LIGHTNING_DRAGON_GEM,
            "闪电法术强度随胸腔内电龙器官数量增加",
            "每增加一个电龙器官提升5%闪电法术强度",
            lightningChargeZH,
            lightningChargeOverloadZH
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.LIGHTNING_DRAGON_GEM,
            "闪电法术强度随电龙器官数量提升",
            lightningChargeSimpleZH
        );

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "电龙吐息袋");
        addOrganPassiveEffect(
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC,
            lightningChargeZH,
            lightningChargeOverloadZH
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC,
            lightningChargeSimpleZH
        );
        addOrganActiveSkill(
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC,
            "向前方喷射闪电，持续5秒",
            "对范围内敌人持续造成%s点伤害",
            "伤害随电龙器官数量增加，最多计入%s个"
        );
        addOrganActiveSkillSimple(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "向前方喷射闪电，持续造成%s点伤害");

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, "电龙肋骨");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, lightningChargeSimpleZH);
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, "电龙肌肉");
        addOrganPassiveEffect(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, lightningChargeZH, lightningChargeOverloadZH);
        addOrganPassiveEffectSimple(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, lightningChargeSimpleZH);
    }

    // ==================== 拟态器官 ====================

    private void mimicOrgansEN() {
        addItem(WAICOrgans.MIMIC_HEART, "Mimic Heart");
        addOrganPassiveEffect(WAICOrgans.MIMIC_HEART, "Health regeneration +50%");
        addItem(WAICOrgans.MIMIC_LIVER, "Mimic Liver");
        addOrganPassiveEffect(WAICOrgans.MIMIC_LIVER, "Health regeneration +50%");
        addItem(WAICOrgans.MIMIC_LUNG, "Mimic Lung");
        addOrganPassiveEffect(WAICOrgans.MIMIC_LUNG, "Health regeneration +50%");
    }

    private void mimicOrgansZH() {
        addItem(WAICOrgans.MIMIC_HEART, "拟态心脏");
        addOrganPassiveEffect(WAICOrgans.MIMIC_HEART, "生命恢复效果提升50%");
        addItem(WAICOrgans.MIMIC_LIVER, "拟态肝脏");
        addOrganPassiveEffect(WAICOrgans.MIMIC_LIVER, "生命恢复效果提升50%");
        addItem(WAICOrgans.MIMIC_LUNG, "拟态肺脏");
        addOrganPassiveEffect(WAICOrgans.MIMIC_LUNG, "生命恢复效果提升50%");
    }

    // ==================== 墨水器官 ====================

    private void inkOrgansEN() {
        addItem(WAICOrgans.INK_HEART, "Ink Heart");
        addItem(WAICOrgans.INK_LUNG, "Ink Lung");
        addItem(WAICOrgans.INK_SPINE, "Ink Spine");
        addItem(WAICOrgans.INK_STOMACH, "Ink Stomach");
        addItem(WAICOrgans.INK_INTESTINE, "Ink Intestine");
        addItem(WAICOrgans.INK_KIDNEY, "Ink Kidney");
        addItem(WAICOrgans.INK_SPLEEN, "Ink Spleen");
        addItem(WAICOrgans.INK_LIVER, "Ink Liver");
        addItem(WAICOrgans.INK_RIB, "Ink Rib");

        addItem(WAICOrgans.INK_MUSCLE, "Ink Muscle");
        addOrganDescription(WAICOrgans.INK_MUSCLE, "Muscle fibers steeped in dark ink");
        addOrganPassiveEffect(WAICOrgans.INK_MUSCLE, "When damaged, converts damage into ink at %s ratio");

        addItem(WAICOrgans.INK_APPENDIX, "Ink Appendix");
        addOrganDescription(WAICOrgans.INK_APPENDIX, "A vestigial organ darkened by ink");
        addOrganActiveSkill(WAICOrgans.INK_APPENDIX, "Consumes ink from the Ink Bottle to restore an equal amount of mana");

        addItem(WAICOrgans.INK_BOTTLE, "Ink Bottle");
        addOrganDescription(WAICOrgans.INK_BOTTLE, "A glass vessel filled with dark, viscous ink");
        addOrganPassiveEffect(
            WAICOrgans.INK_BOTTLE,
            "Can drink ink and store it, each ink organ in the chest cavity adds 1000 to ink capacity"
        );
        add(WAICOrgans.INK_BOTTLE_INK_TRANSLATION, "Ink: %s/%s");

        addItem(WAICOrgans.NIB, "Nib");
        addOrganDescription(WAICOrgans.NIB, "A precision-crafted pen tip");
        addOrganPassiveEffect(
            WAICOrgans.NIB,
            "When casting a spell, consumes ink to boost the spell by 1 level",
            "Ink cost: 5 × spell level"
        );
        addOrganPassiveEffectSimple(WAICOrgans.NIB, "Boosts spell level by 1 when casting, consuming ink");

    }

    private void inkOrgansZH() {
        addItem(WAICOrgans.INK_HEART, "墨水心脏");
        addItem(WAICOrgans.INK_LUNG, "墨水肺脏");
        addItem(WAICOrgans.INK_SPINE, "墨水脊柱");
        addItem(WAICOrgans.INK_STOMACH, "墨水胃");
        addItem(WAICOrgans.INK_INTESTINE, "墨水肠子");
        addItem(WAICOrgans.INK_KIDNEY, "墨水肾脏");
        addItem(WAICOrgans.INK_SPLEEN, "墨水脾脏");
        addItem(WAICOrgans.INK_LIVER, "墨水肝脏");
        addItem(WAICOrgans.INK_RIB, "墨水肋骨");

        addItem(WAICOrgans.INK_MUSCLE, "墨水肌肉");
        addOrganDescription(WAICOrgans.INK_MUSCLE, "浸泡在浓墨中的肌肉纤维");
        addOrganPassiveEffect(WAICOrgans.INK_MUSCLE, "受到伤害时，将伤害以%s的比例转化为墨水");

        addItem(WAICOrgans.INK_APPENDIX, "墨水阑尾");
        addOrganDescription(WAICOrgans.INK_APPENDIX, "一根被墨水浸染的退化器官");
        addOrganActiveSkill(WAICOrgans.INK_APPENDIX, "消耗墨水瓶中的墨水，等量回复法力");

        addItem(WAICOrgans.INK_BOTTLE, "墨水瓶");
        addOrganDescription(WAICOrgans.INK_BOTTLE, "盛满深黑粘稠墨水的玻璃容器");
        addOrganPassiveEffect(WAICOrgans.INK_BOTTLE, "可以饮用墨水并储存，胸腔内每有一个墨水器官+1000容量上限");
        add(WAICOrgans.INK_BOTTLE_INK_TRANSLATION, "墨水: %s/%s");

        addItem(WAICOrgans.NIB, "钢笔尖");
        addOrganDescription(WAICOrgans.NIB, "一枚精密打磨的笔尖");
        addOrganPassiveEffect(WAICOrgans.NIB, "释放法术时消耗墨水，将法术提升1级", "墨水消耗：5 × 法术等级");
        addOrganPassiveEffectSimple(WAICOrgans.NIB, "释放法术时消耗墨水提升法术等级");

    }

    // ==================== 颜料器官 ====================

    private void pigmentOrgansEN() {
        addItem(WAICOrgans.PALETTE, "Palette");
        addOrganPassiveEffect(WAICOrgans.PALETTE, "When casting a spell, consumes the corresponding dye to increase spell level by 1");
        add(WAICOrgans.PALETTE_DYE_TRANSLATION, "%s: %s");

        addItem(WAICOrgans.PIGMENT_HEART, "Pigment Heart");
        addItem(WAICOrgans.PIGMENT_LUNG, "Pigment Lung");
        addItem(WAICOrgans.PIGMENT_SPINE, "Pigment Spine");
        addItem(WAICOrgans.PIGMENT_STOMACH, "Pigment Stomach");
        addItem(WAICOrgans.PIGMENT_INTESTINE, "Pigment Intestine");
        addItem(WAICOrgans.PIGMENT_KIDNEY, "Pigment Kidney");
        addItem(WAICOrgans.PIGMENT_SPLEEN, "Pigment Spleen");
        addItem(WAICOrgans.PIGMENT_LIVER, "Pigment Liver");
        addItem(WAICOrgans.PIGMENT_APPENDIX, "Pigment Appendix");
        addItem(WAICOrgans.PIGMENT_RIB, "Pigment Rib");
        addItem(WAICOrgans.PIGMENT_MUSCLE, "Pigment Muscle");
    }

    private void pigmentOrgansZH() {
        addItem(WAICOrgans.PALETTE, "调色盘");
        addOrganPassiveEffect(WAICOrgans.PALETTE, "释放法术时，消耗对应染料使法术等级+1");
        add(WAICOrgans.PALETTE_DYE_TRANSLATION, "%s: %s");

        addItem(WAICOrgans.PIGMENT_HEART, "颜料心脏");
        addItem(WAICOrgans.PIGMENT_LUNG, "颜料肺脏");
        addItem(WAICOrgans.PIGMENT_SPINE, "颜料脊柱");
        addItem(WAICOrgans.PIGMENT_STOMACH, "颜料胃");
        addItem(WAICOrgans.PIGMENT_INTESTINE, "颜料肠子");
        addItem(WAICOrgans.PIGMENT_KIDNEY, "颜料肾脏");
        addItem(WAICOrgans.PIGMENT_SPLEEN, "颜料脾脏");
        addItem(WAICOrgans.PIGMENT_LIVER, "颜料肝脏");
        addItem(WAICOrgans.PIGMENT_APPENDIX, "颜料阑尾");
        addItem(WAICOrgans.PIGMENT_RIB, "颜料肋骨");
        addItem(WAICOrgans.PIGMENT_MUSCLE, "颜料肌肉");
    }

    // ==================== 元器官心 ====================

    private void elementOrgansEN() {
        addItem(WAICOrgans.DIVINE_CORE, "Divine Core");
        addOrganDescription(WAICOrgans.DIVINE_CORE, "A crystallized core resonating with holy light, emitting a pure and warm radiance");

        addItem(WAICOrgans.FROST_CORE, "Frost Core");
        addOrganDescription(WAICOrgans.FROST_CORE, "A frozen core harboring the essence of eternal winter, radiating an icy chill");

        addItem(WAICOrgans.FLAME_CORE, "Flame Core");
        addOrganDescription(WAICOrgans.FLAME_CORE, "A blazing core of molten lava, its heat never fading");

        addItem(WAICOrgans.NATURE_CORE, "Nature Core");
        addOrganDescription(
            WAICOrgans.NATURE_CORE,
            "A verdant core pulsing with the rhythm of life itself, brimming with natural vitality"
        );
    }

    private void elementOrgansZH() {
        addItem(WAICOrgans.DIVINE_CORE, "神圣核心");
        addOrganDescription(WAICOrgans.DIVINE_CORE, "凝聚圣光之力而结晶的核心，散发着纯净而温暖的光芒");

        addItem(WAICOrgans.FROST_CORE, "冰霜核心");
        addOrganDescription(WAICOrgans.FROST_CORE, "封存永冬之本质的冰冷核心，散发着刺骨的寒意");

        addItem(WAICOrgans.FLAME_CORE, "炽焰核心");
        addOrganDescription(WAICOrgans.FLAME_CORE, "流淌着熔岩的灼热核心，其热度永不消退");

        addItem(WAICOrgans.NATURE_CORE, "自然核心");
        addOrganDescription(WAICOrgans.NATURE_CORE, "脉动着生命韵律的翠绿核心，充盈着自然的活力");
    }

    // ==================== 木质器官 ====================

    private void woodenOrgansEN() {
        addItem(WAICOrgans.WOODEN_HEART, "Wooden Heart");
        addItem(WAICOrgans.WOODEN_LUNG, "Wooden Lung");
        addItem(WAICOrgans.WOODEN_STOMACH, "Wooden Stomach");
        addItem(WAICOrgans.WOODEN_INTESTINE, "Wooden Intestine");
        addItem(WAICOrgans.WOODEN_KIDNEY, "Wooden Kidney");
        addItem(WAICOrgans.WOODEN_SPLEEN, "Wooden Spleen");
        addItem(WAICOrgans.WOODEN_LIVER, "Wooden Liver");
        addItem(WAICOrgans.WOODEN_APPENDIX, "Wooden Appendix");
        addItem(WAICOrgans.WOODEN_MUSCLE, "Wooden Muscle");
    }

    private void woodenOrgansZH() {
        addItem(WAICOrgans.WOODEN_HEART, "木质心脏");
        addItem(WAICOrgans.WOODEN_LUNG, "木质肺脏");
        addItem(WAICOrgans.WOODEN_STOMACH, "木质胃");
        addItem(WAICOrgans.WOODEN_INTESTINE, "木质肠子");
        addItem(WAICOrgans.WOODEN_KIDNEY, "木质肾脏");
        addItem(WAICOrgans.WOODEN_SPLEEN, "木质脾脏");
        addItem(WAICOrgans.WOODEN_LIVER, "木质肝脏");
        addItem(WAICOrgans.WOODEN_APPENDIX, "木质阑尾");
        addItem(WAICOrgans.WOODEN_MUSCLE, "木质肌肉");
    }

    // ==================== 弗兰肯斯坦器官 ====================

    private void frankensteinOrgansEN() {
        addItem(WAICOrgans.FRANKENSTEIN_HEART, "Frankenstein Heart");
        addOrganPassiveEffect(WAICOrgans.FRANKENSTEIN_HEART, "Inherits attributes from stored hearts.");

        addItem(WAICOrgans.FRANKENSTEIN_LUNG, "Frankenstein Lung");
        addItem(WAICOrgans.FRANKENSTEIN_STOMACH, "Frankenstein Stomach");
        addItem(WAICOrgans.FRANKENSTEIN_INTESTINE, "Frankenstein Intestine");
        addItem(WAICOrgans.FRANKENSTEIN_KIDNEY, "Frankenstein Kidney");
        addItem(WAICOrgans.FRANKENSTEIN_SPLEEN, "Frankenstein Spleen");
        addItem(WAICOrgans.FRANKENSTEIN_LIVER, "Frankenstein Liver");
        addItem(WAICOrgans.FRANKENSTEIN_APPENDIX, "Frankenstein Appendix");
        addItem(WAICOrgans.FRANKENSTEIN_MUSCLE, "Frankenstein Muscle");
    }

    private void frankensteinOrgansZH() {
        addItem(WAICOrgans.FRANKENSTEIN_HEART, "弗兰肯斯坦心脏");
        addOrganPassiveEffect(WAICOrgans.FRANKENSTEIN_HEART, "继承存储心脏的属性加成。");

        addItem(WAICOrgans.FRANKENSTEIN_LUNG, "弗兰肯斯坦肺脏");
        addItem(WAICOrgans.FRANKENSTEIN_STOMACH, "弗兰肯斯坦胃");
        addItem(WAICOrgans.FRANKENSTEIN_INTESTINE, "弗兰肯斯坦肠子");
        addItem(WAICOrgans.FRANKENSTEIN_KIDNEY, "弗兰肯斯坦肾脏");
        addItem(WAICOrgans.FRANKENSTEIN_SPLEEN, "弗兰肯斯坦脾脏");
        addItem(WAICOrgans.FRANKENSTEIN_LIVER, "弗兰肯斯坦肝脏");
        addItem(WAICOrgans.FRANKENSTEIN_APPENDIX, "弗兰肯斯坦阑尾");
        addItem(WAICOrgans.FRANKENSTEIN_MUSCLE, "弗兰肯斯坦肌肉");
    }

    // ==================== 病变器官 ====================

    private void lesionOrgansEN() {
        addItem(WAICOrgans.LESION_HEART, "Lesion Heart");
        addOrganPassiveEffect(WAICOrgans.LESION_HEART, "+1 Health per harmful effect, -1 Health per beneficial effect");
        addOrganActiveSkill(WAICOrgans.LESION_HEART, "Spread all your effects to living entities within 10 blocks");

        addItem(WAICOrgans.LESION_LUNG, "Lesion Lung");
        addItem(WAICOrgans.LESION_STOMACH, "Lesion Stomach");
        addItem(WAICOrgans.LESION_INTESTINE, "Lesion Intestine");
        addItem(WAICOrgans.LESION_KIDNEY, "Lesion Kidney");
        addItem(WAICOrgans.LESION_SPLEEN, "Lesion Spleen");
        addItem(WAICOrgans.LESION_LIVER, "Lesion Liver");
        addItem(WAICOrgans.LESION_APPENDIX, "Lesion Appendix");

        addItem(WAICOrgans.LESION_MUSCLE, "Lesion Muscle");
        addOrganPassiveEffect(
            WAICOrgans.LESION_MUSCLE,
            "+1 Strength and +1 Speed per harmful effect",
            "Deals bonus damage equal to the sum of all harmful effect levels on the target"
        );
    }

    private void lesionOrgansZH() {
        addItem(WAICOrgans.LESION_HEART, "病变心脏");
        addOrganPassiveEffect(WAICOrgans.LESION_HEART, "每有一个负面效果+1健康，每有一个正面效果−1健康");
        addOrganActiveSkill(WAICOrgans.LESION_HEART, "将自身所有效果传播给10格范围内的生物");

        addItem(WAICOrgans.LESION_LUNG, "病变肺脏");
        addItem(WAICOrgans.LESION_STOMACH, "病变胃");
        addItem(WAICOrgans.LESION_INTESTINE, "病变肠子");
        addItem(WAICOrgans.LESION_KIDNEY, "病变肾脏");
        addItem(WAICOrgans.LESION_SPLEEN, "病变脾脏");
        addItem(WAICOrgans.LESION_LIVER, "病变肝脏");
        addItem(WAICOrgans.LESION_APPENDIX, "病变阑尾");

        addItem(WAICOrgans.LESION_MUSCLE, "病变肌肉");
        addOrganPassiveEffect(
            WAICOrgans.LESION_MUSCLE,
            "每有一个负面效果，+1力量、+1速度",
            "对目标额外造成等同于其所有负面效果等级之和的伤害"
        );
    }

    // ==================== 九狱器官 ====================

    private void nineHellOrgansEN() {
        add("organ.who_am_i_core.nine_hell.hint", "Each nine-hell organ activates one passive effect, all nine-hell organs' attributes -1");

        addItem(WAICOrgans.LIMBO, "Limbo");
        addOrganPassiveEffect(
            WAICOrgans.LIMBO,
            "Gain 1 XP/sec",
            "Gain 3 XP/sec",
            "Gain 5 XP/sec"
        );
        addItem(WAICOrgans.LUST, "Lust");
        addOrganPassiveEffect(
            WAICOrgans.LUST,
            "Attacks heal 10% of damage dealt",
            "Attacks heal 20% of damage dealt",
            "Attacks heal 30% of damage dealt"
        );
        addItem(WAICOrgans.GLUTTONY, "Gluttony");
        addOrganPassiveEffect(
            WAICOrgans.GLUTTONY,
            "Eat at any time ignoring hunger, eating speed doubled",
            "Eating grants absorption hearts equal to hunger value × nine-hell count, capped at count × 20",
            "Eating additionally heals HP equal to nine-hell count"
        );
        addItem(WAICOrgans.GREED, "Greed");
        addOrganPassiveEffect(
            WAICOrgans.GREED,
            "+1 Looting and +1 Fortune",
            "+2 Looting and +2 Fortune",
            "+3 Looting and +3 Fortune"
        );
        addItem(WAICOrgans.WRATH, "Wrath");
        addOrganPassiveEffect(
            WAICOrgans.WRATH,
            "+1 Strength and +1 Speed",
            "+2 Strength and +2 Speed",
            "+3 Strength and +3 Speed"
        );
        addItem(WAICOrgans.HERESY, "Heresy");
        addOrganPassiveEffect(
            WAICOrgans.HERESY,
            "Potion duration +50%",
            "Potion duration +100%",
            "Potion amplifier +1"
        );
        addItem(WAICOrgans.VIOLENCE, "Violence");
        addOrganPassiveEffect(
            WAICOrgans.VIOLENCE,
            "Critical damage ×2",
            "Critical damage ×2",
            "Attacks always crit"
        );
        addItem(WAICOrgans.FRAUD, "Fraud");
        addOrganPassiveEffect(
            WAICOrgans.FRAUD,
            "Both parties gain 10× trade experience",
            "Villager trade discount",
            "Villager trades never run out of stock"
        );
        addItem(WAICOrgans.TREACHERY, "Treachery");
        addOrganPassiveEffect(
            WAICOrgans.TREACHERY,
            "Attacks deal 1% of target max health",
            "Attacks deal 3% of target max health",
            "Attacks deal 5% of target max health"
        );
    }

    private void nineHellOrgansZH() {
        add("organ.who_am_i_core.nine_hell.hint", "每有一个九狱器官，激活一条被动效果，所有九狱器官的属性 -1");

        addItem(WAICOrgans.LIMBO, "灵薄");
        addOrganPassiveEffect(
            WAICOrgans.LIMBO,
            "每秒获得1点经验",
            "每秒获得3点经验",
            "每秒获得5点经验"
        );
        addItem(WAICOrgans.LUST, "色欲");
        addOrganPassiveEffect(
            WAICOrgans.LUST,
            "攻击回复造成伤害10%的生命",
            "攻击回复造成伤害20%的生命",
            "攻击回复造成伤害30%的生命"
        );
        addItem(WAICOrgans.GLUTTONY, "暴食");
        addOrganPassiveEffect(
            WAICOrgans.GLUTTONY,
            "无视饥饿值限制，随时可以进食，食用速度翻倍",
            "食用获得饥饿值×九狱器官数量的黄心，上限为九狱器官数量×20",
            "食用额外回复九狱器官数量的生命值"
        );
        addItem(WAICOrgans.GREED, "贪婪");
        addOrganPassiveEffect(
            WAICOrgans.GREED,
            "+1抢夺与+1时运",
            "+2抢夺与+2时运",
            "+3抢夺与+3时运"
        );
        addItem(WAICOrgans.WRATH, "愤怒");
        addOrganPassiveEffect(
            WAICOrgans.WRATH,
            "+1力量与+1速度",
            "+2力量与+2速度",
            "+3力量与+3速度"
        );
        addItem(WAICOrgans.HERESY, "异端");
        addOrganPassiveEffect(
            WAICOrgans.HERESY,
            "药水持续时间+50%",
            "药水持续时间+100%",
            "药水等级+1"
        );
        addItem(WAICOrgans.VIOLENCE, "暴力");
        addOrganPassiveEffect(
            WAICOrgans.VIOLENCE,
            "暴击伤害×2",
            "暴击伤害×2",
            "攻击必定暴击"
        );
        addItem(WAICOrgans.FRAUD, "欺诈");
        addOrganPassiveEffect(
            WAICOrgans.FRAUD,
            "交易双方获得10倍经验",
            "村民交易获得折扣",
            "村民交易不缺货"
        );
        addItem(WAICOrgans.TREACHERY, "背叛");
        addOrganPassiveEffect(
            WAICOrgans.TREACHERY,
            "攻击额外造成目标1%最大生命值伤害",
            "攻击额外造成目标3%最大生命值伤害",
            "攻击额外造成目标5%最大生命值伤害"
        );
    }

    // ==================== 布织器官 ====================

    private void clothOrgansEN() {
        addItem(CompanionsOrgans.CLOTH_TEDDY_BEAR, "Cloth Teddy Bear");
        addOrganDescription(CompanionsOrgans.CLOTH_TEDDY_BEAR, "A patchwork teddy bear stitched from cloth and thread");
        addOrganPassiveEffect(
            CompanionsOrgans.CLOTH_TEDDY_BEAR,
            "When chest cavity closes, wool items in the chest are converted into random cloth organs"
        );
        addOrganActiveSkill(
            CompanionsOrgans.CLOTH_TEDDY_BEAR,
            "Consume wool stored in the teddy bear to heal",
            "Each wool restores %s HP"
        );
        addOrganActiveSkillSimple(CompanionsOrgans.CLOTH_TEDDY_BEAR, "Consumes wool to restore %s HP");

        addItem(CompanionsOrgans.CLOTH_HEART, "Cloth Heart");
        addItem(CompanionsOrgans.CLOTH_LUNG, "Cloth Lung");
        addItem(CompanionsOrgans.CLOTH_LIVER, "Cloth Liver");
        addItem(CompanionsOrgans.CLOTH_INTESTINE, "Cloth Intestine");
        addItem(CompanionsOrgans.CLOTH_STOMACH, "Cloth Stomach");
        addItem(CompanionsOrgans.CLOTH_KIDNEY, "Cloth Kidney");
        addItem(CompanionsOrgans.CLOTH_SPLEEN, "Cloth Spleen");
        addItem(CompanionsOrgans.CLOTH_SPINE, "Cloth Spine");
        addItem(CompanionsOrgans.CLOTH_RIB, "Cloth Rib");
        addItem(CompanionsOrgans.CLOTH_MUSCLE, "Cloth Muscle");
        addItem(CompanionsOrgans.CLOTH_APPENDIX, "Cloth Appendix");
    }

    private void clothOrgansZH() {
        addItem(CompanionsOrgans.CLOTH_TEDDY_BEAR, "布织泰迪熊");
        addOrganDescription(CompanionsOrgans.CLOTH_TEDDY_BEAR, "用布料与线缝制的拼布泰迪熊");
        addOrganPassiveEffect(
            CompanionsOrgans.CLOTH_TEDDY_BEAR,
            "胸腔关闭时将羊毛转换为随机布织器官"
        );
        addOrganActiveSkill(
            CompanionsOrgans.CLOTH_TEDDY_BEAR,
            "消耗布织泰迪熊中的羊毛回复生命值",
            "每个羊毛回复%s点生命值"
        );
        addOrganActiveSkillSimple(CompanionsOrgans.CLOTH_TEDDY_BEAR, "消耗羊毛回复%s点生命值");

        addItem(CompanionsOrgans.CLOTH_HEART, "布织心脏");
        addItem(CompanionsOrgans.CLOTH_LUNG, "布织肺脏");
        addItem(CompanionsOrgans.CLOTH_LIVER, "布织肝脏");
        addItem(CompanionsOrgans.CLOTH_INTESTINE, "布织肠子");
        addItem(CompanionsOrgans.CLOTH_STOMACH, "布织胃");
        addItem(CompanionsOrgans.CLOTH_KIDNEY, "布织肾脏");
        addItem(CompanionsOrgans.CLOTH_SPLEEN, "布织脾脏");
        addItem(CompanionsOrgans.CLOTH_SPINE, "布织脊柱");
        addItem(CompanionsOrgans.CLOTH_RIB, "布织肋骨");
        addItem(CompanionsOrgans.CLOTH_MUSCLE, "布织肌肉");
        addItem(CompanionsOrgans.CLOTH_APPENDIX, "布织阑尾");
    }

    // ==================== 猩红器官 ====================

    private void crimsonOrgansEN() {
        addItem(WAICOrgans.CRIMSON_HEART, "Crimson Heart");
        addOrganPassiveEffect(
            WAICOrgans.CRIMSON_HEART,
            "Each crimson organ in chest cavity grants +100 blood capacity",
            "Converts healing into stored blood at %s conversion rate"
        );
        addItem(WAICOrgans.CRIMSON_LUNG, "Crimson Lung");
        addItem(WAICOrgans.CRIMSON_STOMACH, "Crimson Stomach");
        addItem(WAICOrgans.CRIMSON_INTESTINE, "Crimson Intestine");
        addItem(WAICOrgans.CRIMSON_KIDNEY, "Crimson Kidney");
        addItem(WAICOrgans.CRIMSON_SPLEEN, "Crimson Spleen");
        addItem(WAICOrgans.CRIMSON_LIVER, "Crimson Liver");
        addOrganPassiveEffect(
            WAICOrgans.CRIMSON_LIVER,
            "Consumes 10 blood when casting blood spells to increase spell level by 1"
        );
        addItem(WAICOrgans.CRIMSON_APPENDIX, "Crimson Appendix");
        addOrganActiveSkill(
            WAICOrgans.CRIMSON_APPENDIX,
            "Consumes blood to restore health",
            "5 blood per 1 HP healed"
        );
        addOrganActiveSkillSimple(WAICOrgans.CRIMSON_APPENDIX, "Consumes blood to heal");
        addItem(WAICOrgans.CRIMSON_MUSCLE, "Crimson Muscle");

    }

    private void crimsonOrgansZH() {
        addItem(WAICOrgans.CRIMSON_HEART, "猩红心脏");
        addOrganPassiveEffect(
            WAICOrgans.CRIMSON_HEART,
            "胸腔内每有一个猩红器官+100血液上限",
            "将治疗量以%s的转化率转化为血液存储"
        );
        addItem(WAICOrgans.CRIMSON_LUNG, "猩红肺脏");
        addItem(WAICOrgans.CRIMSON_STOMACH, "猩红胃");
        addItem(WAICOrgans.CRIMSON_INTESTINE, "猩红肠子");
        addItem(WAICOrgans.CRIMSON_KIDNEY, "猩红肾脏");
        addItem(WAICOrgans.CRIMSON_SPLEEN, "猩红脾脏");
        addItem(WAICOrgans.CRIMSON_LIVER, "猩红肝脏");
        addOrganPassiveEffect(
            WAICOrgans.CRIMSON_LIVER,
            "释放猩红法术时，消耗10点血液，使法术等级+1"
        );
        addItem(WAICOrgans.CRIMSON_APPENDIX, "猩红阑尾");
        addOrganActiveSkill(
            WAICOrgans.CRIMSON_APPENDIX,
            "消耗血液回复生命",
            "每回复1点生命消耗5点血液"
        );
        addOrganActiveSkillSimple(WAICOrgans.CRIMSON_APPENDIX, "消耗血液回复生命");
        addItem(WAICOrgans.CRIMSON_MUSCLE, "猩红肌肉");

    }

    // ==================== 电磁义体器官 ====================

    private void cyberneticOrgansEN() {
        addItem(WAICOrgans.COMPUTING_CORE, "Computing Core");
        addOrganPassiveEffect(
            WAICOrgans.COMPUTING_CORE,
            "Regenerates 1 charge per tick",
            "Cannot exceed max charge capacity"
        );
        addOrganPassiveEffectSimple(WAICOrgans.COMPUTING_CORE, "Regenerates charge over time");

        addItem(WAICOrgans.CURRENT_RIB, "Current Rib");
        addOrganPassiveEffect(
            WAICOrgans.CURRENT_RIB,
            "Consumes charge to block incoming damage",
            "10 charge blocks 1 damage, up to 4 points",
            "A paired Current Rib on the opposite side halves the cost",
            "25% chance to refund consumed charge",
            "10% of charge loss is converted to healing"
        );
        addOrganPassiveEffectSimple(WAICOrgans.CURRENT_RIB, "Consumes charge to block damage");

        addItem(WAICOrgans.CHARGED_MUSCLE, "Charged Muscle");
        addOrganPassiveEffect(
            WAICOrgans.CHARGED_MUSCLE,
            "Generates 1 charge per tick while sprinting",
            "Sprint-generated charge can exceed cap by 50%"
        );
        addOrganPassiveEffectSimple(WAICOrgans.CHARGED_MUSCLE, "Generates charge while sprinting");

        addItem(WAICOrgans.CONDUCTIVE_SPINE, "Conductive Spine");
        addOrganActiveSkill(
            WAICOrgans.CONDUCTIVE_SPINE,
            "Consumes half of current charge to enter Overload",
            "Overload max duration equals charge consumed in ticks",
            "Consumes 3 charge per tick during Overload",
            "Overload ends early when charge runs out",
            "During Overload, Computing Core stops regenerating",
            "During Overload, Current Rib block cap doubles to 8",
            "During Overload, Current Rib refund chance rises to 50%",
            "During Overload, Current Rib healing conversion rises to 20%"
        );
        addOrganActiveSkillSimple(WAICOrgans.CONDUCTIVE_SPINE, "Consumes charge to enter Overload");

        addItem(WAICOrgans.ENERGY_MODULE, "Energy Module");
        addOrganPassiveEffect(
            WAICOrgans.ENERGY_MODULE,
            "+500 charge capacity",
            "Excess charge drains at 1 per tick"
        );
        add(WAICOrgans.ENERGY_MODULE_CHARGE_TRANSLATION, "Charge: %s/%s");
    }

    private void cyberneticOrgansZH() {
        addItem(WAICOrgans.COMPUTING_CORE, "演算核心");
        addOrganPassiveEffect(
            WAICOrgans.COMPUTING_CORE,
            "每tick回复1点电荷",
            "不超过最大电荷上限"
        );
        addOrganPassiveEffectSimple(WAICOrgans.COMPUTING_CORE, "持续回复电荷");

        addItem(WAICOrgans.CURRENT_RIB, "导流肋骨");
        addOrganPassiveEffect(
            WAICOrgans.CURRENT_RIB,
            "消耗电荷抵消受到的伤害",
            "每10电荷抵消1点伤害,抵消上限4点",
            "对称位置存在另一根导流肋骨时消耗减半",
            "消耗电荷时有25%概率返还",
            "电荷流失量的10%转化为生命回复"
        );
        addOrganPassiveEffectSimple(WAICOrgans.CURRENT_RIB, "消耗电荷抵消伤害");

        addItem(WAICOrgans.CHARGED_MUSCLE, "充能肌束");
        addOrganPassiveEffect(
            WAICOrgans.CHARGED_MUSCLE,
            "冲刺时每tick产生1电荷",
            "冲刺产生的电荷可超出上限50%"
        );
        addOrganPassiveEffectSimple(WAICOrgans.CHARGED_MUSCLE, "冲刺时产生电荷");

        addItem(WAICOrgans.CONDUCTIVE_SPINE, "传导链节");
        addOrganActiveSkill(
            WAICOrgans.CONDUCTIVE_SPINE,
            "消耗当前一半电荷进入超频状态",
            "超频最大时长等于消耗电荷数",
            "超频期间每tick消耗3电荷",
            "电荷不足时超频提前结束",
            "超频时演算核心暂停回复",
            "超频时导流肋骨抵消上限翻倍至8点",
            "超频时导流肋骨返还概率提升至50%",
            "超频时导流肋骨转化率提升至20%"
        );
        addOrganActiveSkillSimple(WAICOrgans.CONDUCTIVE_SPINE, "消耗电荷进入超频");

        addItem(WAICOrgans.ENERGY_MODULE, "蓄能模块");
        addOrganPassiveEffect(
            WAICOrgans.ENERGY_MODULE,
            "+500点电荷容量",
            "超出上限的电荷每tick衰减1点"
        );
        add(WAICOrgans.ENERGY_MODULE_CHARGE_TRANSLATION, "电荷: %s/%s");
    }

    // ==================== WAIC 独立器官 ====================

    private void WAICOrgansEN() {
        addItem(WAICOrgans.HAUNTED_BONE, "Haunted Bone");
        addOrganPassiveEffect(WAICOrgans.HAUNTED_BONE, "Randomly moves to an empty slot when chest cavity closes");

        addItem(WAICOrgans.SWORD_BONE, "Sword Bone");

        addItem(WAICOrgans.STRAIGHT_INTESTINE, "Straight Intestine");
        addOrganPassiveEffect(WAICOrgans.STRAIGHT_INTESTINE, "30% chance to drop an identical food item 3 seconds after eating");

        addItem(WAICOrgans.SQUASH, "Squash");
        addOrganPassiveEffect(
            WAICOrgans.SQUASH,
            "Immune to all fall damage",
            "Distributes equal fall damage to all entities within 5×5×5"
        );

        addItem(WAICOrgans.EXPERIENCE_HEART, "Experience Heart");
        addOrganPassiveEffect(
            WAICOrgans.EXPERIENCE_HEART,
            "Every 10 experience levels grant +1 health",
            "Experience from orbs gains a multiplier",
            "Multiplier equals magic organs in chest + 1"
        );
        addOrganPassiveEffectSimple(WAICOrgans.EXPERIENCE_HEART, "Grants health from experience levels and boosts orb XP");

        addItem(WAICOrgans.FLESH_IDOL, "Flesh Idol");
        addOrganDescription(WAICOrgans.FLESH_IDOL, "A grotesque idol carved from living flesh, pulsing with vital energy");
        addOrganActiveSkill(
            WAICOrgans.FLESH_IDOL,
            "For each harmful effect, halve current HP, then remove that effect",
            "Removing Sinner effect also reduces 1 Sin layer"
        );
        addOrganActiveSkillSimple(WAICOrgans.FLESH_IDOL, "Sacrifice HP to cleanse all harmful effects");

        addItem(WAICOrgans.STRANGE_EYEBALL, "Strange Eyeball");
        addItem(WAICOrgans.EERIE_EYEBALL, "Eerie Eyeball");
        addItem(WAICOrgans.STRANGE_MECHANICAL_EYEBALL, "Strange Mechanical Eyeball");
        addItem(WAICOrgans.EERIE_MECHANICAL_EYEBALL, "Eerie Mechanical Eyeball");
    }

    private void WAICOrgansZH() {
        addItem(WAICOrgans.HAUNTED_BONE, "闹鬼的骨头");
        addOrganPassiveEffect(WAICOrgans.HAUNTED_BONE, "胸腔关闭时随机移动到一个空槽位");

        addItem(WAICOrgans.SWORD_BONE, "剑骨头");

        addItem(WAICOrgans.STRAIGHT_INTESTINE, "直肠子");
        addOrganPassiveEffect(WAICOrgans.STRAIGHT_INTESTINE, "食用食物后30%几率在3秒后掉落一份相同食物");

        addItem(WAICOrgans.SQUASH, "窝瓜");
        addOrganPassiveEffect(
            WAICOrgans.SQUASH,
            "免疫所有摔落伤害",
            "将等量的摔落伤害平分给5×5×5范围内的所有实体"
        );

        addItem(WAICOrgans.EXPERIENCE_HEART, "经验之心");
        addOrganPassiveEffect(
            WAICOrgans.EXPERIENCE_HEART,
            "每10级经验等级增加1点健康值",
            "从经验球获取的经验获得额外倍率",
            "倍率为胸腔中魔法器官数量加1"
        );
        addOrganPassiveEffectSimple(WAICOrgans.EXPERIENCE_HEART, "经验等级转化为健康值，经验球获得额外倍率");

        addItem(WAICOrgans.FLESH_IDOL, "血肉偶像");
        addOrganDescription(WAICOrgans.FLESH_IDOL, "由活肉雕琢而成的怪诞偶像，脉动着生命能量");
        addOrganActiveSkill(
            WAICOrgans.FLESH_IDOL,
            "逐个清除负面效果，每清除1个负面效果，当前生命值折半一次",
            "清除「罪人」效果时，减少1层罪孽"
        );
        addOrganActiveSkillSimple(WAICOrgans.FLESH_IDOL, "牺牲生命值清除所有负面效果");

        addItem(WAICOrgans.STRANGE_EYEBALL, "奇怪的眼球");
        addItem(WAICOrgans.EERIE_EYEBALL, "诡异的眼球");
        addItem(WAICOrgans.STRANGE_MECHANICAL_EYEBALL, "奇怪的机械眼球");
        addItem(WAICOrgans.EERIE_MECHANICAL_EYEBALL, "诡异的机械眼球");
    }

    // ==================== 悚怖器官 ====================

    private void dreadOrgansEN() {
        addItem(IceAndFireOrgans.BITTER_FLESH, "Bitter Flesh");
        addItem(IceAndFireOrgans.ICE_SHARD, "Ice Shard");
        addOrganPassiveEffect(
            IceAndFireOrgans.ICE_SHARD,
            "Provides 0.05 health per ice organ, reduced by fire organs"
        );
        addItem(IceAndFireOrgans.FROSTBURN_SOUL, "Frostburn Soul");
        addOrganPassiveEffect(
            IceAndFireOrgans.FROSTBURN_SOUL,
            "Provides 0.15 health per ice organ, reduced by fire organs"
        );
        addItem(IceAndFireOrgans.DREAD_PHYLACTERY, "Dread Phylactery");
        addOrganPassiveEffect(
            IceAndFireOrgans.DREAD_PHYLACTERY,
            "Provides 0.25 health per ice organ, reduced by fire organs",
            "On attack converts target Slowness into freeze damage and removes it",
            "Damage equals remaining Slowness seconds multiplied by level plus one",
            "Infinite Slowness results in lethal damage"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.DREAD_PHYLACTERY,
            "Ice organs grant health and convert Slowness to freeze damage, reduced by fire organs"
        );
        addItem(IceAndFireOrgans.DREAD_RIB, "Dread Rib");
        addItem(IceAndFireOrgans.DREAD_SPINE, "Dread Spine");
        addOrganPassiveEffect(
            IceAndFireOrgans.DREAD_SPINE,
            "Attacks apply Slowness for %1$s, Slowness level %2$s, reduced by fire organs, level based on ice organs within 3×3 range around own slot"
        );
    }

    private void dreadOrgansZH() {
        addItem(IceAndFireOrgans.BITTER_FLESH, "苦寒血肉");
        addItem(IceAndFireOrgans.ICE_SHARD, "冰魂残片");
        addOrganPassiveEffect(
            IceAndFireOrgans.ICE_SHARD,
            "每个冰霜器官提供0.05健康，受炽焰器官抵消"
        );
        addItem(IceAndFireOrgans.FROSTBURN_SOUL, "冻结魂火");
        addOrganPassiveEffect(
            IceAndFireOrgans.FROSTBURN_SOUL,
            "每个冰霜器官提供0.15健康，受炽焰器官抵消"
        );
        addItem(IceAndFireOrgans.DREAD_PHYLACTERY, "悚怖命匣");
        addOrganPassiveEffect(
            IceAndFireOrgans.DREAD_PHYLACTERY,
            "每个冰霜器官提供0.25健康，受炽焰器官抵消",
            "攻击时将目标的缓慢效果转化为冰霜伤害并移除",
            "伤害等于缓慢剩余秒数乘以缓慢等级加一",
            "无限时长的缓慢造成致命伤害"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.DREAD_PHYLACTERY,
            "冰霜器官提供健康并将缓慢转化为冰霜伤害，受炽焰器官抵消"
        );
        addItem(IceAndFireOrgans.DREAD_RIB, "悚怖肋骨");
        addItem(IceAndFireOrgans.DREAD_SPINE, "悚怖脊柱");
        addOrganPassiveEffect(
            IceAndFireOrgans.DREAD_SPINE,
            "攻击时施加持续%1$s的缓慢，缓慢等级%2$s，受炽焰器官抵消，等级以自身槽位为中心3×3范围的冰霜器官计算"
        );
    }

    // ==================== 九头蛇器官 ====================

    private void hydraOrgansEN() {
        addItem(IceAndFireOrgans.HYDRA_HEART, "Hydra Heart");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_HEART,
            "Immune to poison damage",
            "Gains regeneration at the same level as current poison"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_HEART,
            "Immune to poison damage and converts it into regeneration"
        );

        addItem(IceAndFireOrgans.HYDRA_LUNG, "Hydra Lung");
        addOrganActiveSkill(
            IceAndFireOrgans.HYDRA_LUNG,
            "Consumes current poison to unleash venom breath",
            "Breath duration equals log10 of original poison duration in ticks",
            "Deals damage equal to poison amplifier plus one every 4 ticks",
            "Hit enemies receive the original poison effect"
        );
        addOrganActiveSkillSimple(
            IceAndFireOrgans.HYDRA_LUNG,
            "Consumes poison to unleash venom breath"
        );

        addItem(IceAndFireOrgans.HYDRA_SPINE, "Hydra Spine");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_SPINE,
            "Prevents death when holding at least 10 seconds of poison",
            "Revives with %s HP, poison level +1, duration halved",
            "Cooldown: 180s"
        );

        addItem(IceAndFireOrgans.HYDRA_STOMACH, "Hydra Stomach");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_STOMACH,
            "Gains poison based on harmful food effects when eating",
            "Poison duration equals harmful effects duration multiplied by stomach count"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_STOMACH,
            "Gains poison from harmful food effects when eating"
        );

        addItem(IceAndFireOrgans.HYDRA_INTESTINE, "Hydra Intestine");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_INTESTINE,
            "Increases food effect duration by 50 percent per organ",
            "Each organ increases by 50 percent the duration of poison the Hydra Stomach gains from harmful food effects"
        );

        addItem(IceAndFireOrgans.HYDRA_SPLEEN, "Hydra Spleen");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_SPLEEN,
            "Converts poison into healing when health drops below 50 percent",
            "Heal equals poison level times %s"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_SPLEEN,
            "Converts poison to healing at %s factor when at low health"
        );

        addItem(IceAndFireOrgans.HYDRA_RIB, "Hydra Rib");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_RIB,
            "When taking damage transfers up to 5 seconds of poison to the attacker",
            "Damage reduction equals transferred poison level"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_RIB,
            "Transfers poison to attackers when hit"
        );

        addItem(IceAndFireOrgans.HYDRA_MUSCLE, "Hydra Muscle");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_MUSCLE,
            "On melee attack transfers up to 5 seconds of poison to the target",
            "Extra damage equals transferred poison level"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_MUSCLE,
            "Transfers poison to target on melee attack"
        );
    }

    private void hydraOrgansZH() {
        addItem(IceAndFireOrgans.HYDRA_HEART, "九头蛇心脏");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_HEART,
            "免疫中毒伤害",
            "获得与中毒相同等级的再生效果"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_HEART,
            "免疫中毒伤害并将其转化为再生"
        );

        addItem(IceAndFireOrgans.HYDRA_LUNG, "九头蛇肺脏");
        addOrganActiveSkill(
            IceAndFireOrgans.HYDRA_LUNG,
            "消耗当前中毒效果释放毒物吐息",
            "吐息持续时长等于原始中毒时长的log10",
            "每4 tick造成等于中毒等级加一的伤害",
            "被击中的敌人获得原始中毒效果"
        );
        addOrganActiveSkillSimple(
            IceAndFireOrgans.HYDRA_LUNG,
            "消耗中毒效果释放毒物吐息"
        );

        addItem(IceAndFireOrgans.HYDRA_SPINE, "九头蛇脊柱");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_SPINE,
            "持有至少10秒中毒时可阻止死亡",
            "复活时恢复%s点生命值，中毒等级+1且持续减半",
            "冷却时间：180秒"
        );

        addItem(IceAndFireOrgans.HYDRA_STOMACH, "九头蛇胃");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_STOMACH,
            "进食时根据食物的有害效果获取中毒",
            "中毒时长等于有害效果时长乘以胃的数量"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_STOMACH,
            "进食时根据有害食物效果获取中毒"
        );

        addItem(IceAndFireOrgans.HYDRA_INTESTINE, "九头蛇肠子");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_INTESTINE,
            "每个器官增加50%食物效果持续时长",
            "每个器官提高50%九头蛇胃从食物有害效果中获取的中毒时长"
        );

        addItem(IceAndFireOrgans.HYDRA_SPLEEN, "九头蛇脾脏");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_SPLEEN,
            "血量低于50%时消耗中毒时长转化为治疗",
            "治疗量等于中毒等级乘以%s"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_SPLEEN,
            "低血量时将中毒以%s的倍率转化为治疗"
        );

        addItem(IceAndFireOrgans.HYDRA_RIB, "九头蛇肋骨");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_RIB,
            "受伤时将最多5秒的中毒效果转移给攻击者",
            "伤害减免等于转移的中毒等级"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_RIB,
            "受伤时将中毒转移给攻击者"
        );

        addItem(IceAndFireOrgans.HYDRA_MUSCLE, "九头蛇肌肉");
        addOrganPassiveEffect(
            IceAndFireOrgans.HYDRA_MUSCLE,
            "近战攻击时将最多5秒的中毒效果转移给目标",
            "额外伤害等于转移的中毒等级"
        );
        addOrganPassiveEffectSimple(
            IceAndFireOrgans.HYDRA_MUSCLE,
            "近战攻击时将中毒转移给目标"
        );
    }

    // ==================== 器官标签翻译 ====================

    private void addWAICTagTranslationsEN() {
        add(WAICItemTagManager.MAGIC, "Magic");
        add(WAICItemTagManager.MECHANICAL, "Mechanical");
        add(WAICItemTagManager.SUMMON, "Summon");
        add(WAICItemTagManager.UNIQUE, "Unique");
        add(WAICItemTagManager.FIRE_DRAGON, "Fire Dragon");
        add(WAICItemTagManager.ICE_DRAGON, "Ice Dragon");
        add(WAICItemTagManager.LIGHTNING_DRAGON, "Lightning Dragon");
        add(WAICItemTagManager.CHARGE, "Charge");
        add(WAICItemTagManager.FIRE, "Fire");
        add(WAICItemTagManager.ICE, "Ice");
        add(WAICItemTagManager.LESION, "Lesion");
        add(WAICItemTagManager.CRIMSON, "Crimson");
        add(WAICItemTagManager.CURSED, "Cursed");
        add(WAICItemTagManager.INK, "Ink");
        add(WAICItemTagManager.EMBER, "Ember");
        add(WAICItemTagManager.CLOTH, "Cloth");
        add(WAICItemTagManager.MONSTROSITY, "Monstrosity");
        add(WAICItemTagManager.CAKE, "Cake");
        add(WAICItemTagManager.NINE_HELL, "Nine Hell");
        add(WAICItemTagManager.QLIPHOTH, "Qliphoth");
        add(WAICItemTagManager.PONTIFF, "Pontiff");
        add(WAICItemTagManager.SCYLLA, "Scylla");
        add(WAICItemTagManager.REMNANT, "Remnant");

        add("formula.who_am_i_core.max_health", "Max Health");
        add("formula.who_am_i_core.current_health_ratio", "Current Health Ratio");
        add("formula.who_am_i_core.fire_count", "Fire");
        add("formula.who_am_i_core.ice_count", "Ice");
        add("formula.who_am_i_core.local_fire_count", "Local Fire");
        add("formula.who_am_i_core.local_ice_count", "Local Ice");
    }

    private void addWAICTagTranslationsZH() {
        add(WAICItemTagManager.MAGIC, "魔法");
        add(WAICItemTagManager.MECHANICAL, "机械");
        add(WAICItemTagManager.SUMMON, "召唤");
        add(WAICItemTagManager.UNIQUE, "唯一");
        add(WAICItemTagManager.FIRE_DRAGON, "火龙");
        add(WAICItemTagManager.ICE_DRAGON, "冰龙");
        add(WAICItemTagManager.LIGHTNING_DRAGON, "电龙");
        add(WAICItemTagManager.CHARGE, "电荷");
        add(WAICItemTagManager.FIRE, "炽焰");
        add(WAICItemTagManager.ICE, "冰霜");
        add(WAICItemTagManager.LESION, "病变");
        add(WAICItemTagManager.CRIMSON, "猩红");
        add(WAICItemTagManager.CURSED, "诅咒");
        add(WAICItemTagManager.INK, "墨水");
        add(WAICItemTagManager.EMBER, "余烬");
        add(WAICItemTagManager.CLOTH, "布织");
        add(WAICItemTagManager.MONSTROSITY, "巨兽");
        add(WAICItemTagManager.CAKE, "蛋糕");
        add(WAICItemTagManager.NINE_HELL, "九狱");
        add(WAICItemTagManager.QLIPHOTH, "逆卡巴拉");
        add(WAICItemTagManager.PONTIFF, "教宗");
        add(WAICItemTagManager.SCYLLA, "斯库拉");
        add(WAICItemTagManager.REMNANT, "遗魂");

        add("formula.who_am_i_core.max_health", "最大生命值");
        add("formula.who_am_i_core.current_health_ratio", "当前生命值比例");
        add("formula.who_am_i_core.fire_count", "炽焰");
        add("formula.who_am_i_core.ice_count", "冰霜");
        add("formula.who_am_i_core.local_fire_count", "局部炽焰");
        add("formula.who_am_i_core.local_ice_count", "局部冰霜");
    }

    // ==================== FDBosses 逆卡巴拉器官 ====================

    private void fdBossesOrgansEN() {
        addItem(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "Fire Malkuth Warrior Heart");
        addOrganPassiveEffect(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "Immune to fire attacks when fighting Malkuth");

        addItem(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "Ice Malkuth Warrior Heart");
        addOrganPassiveEffect(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "Immune to ice attacks when fighting Malkuth");

        addItem(FDBossesOrgans.MALKUTH, "Malkuth");
        addOrganPassiveEffect(
            FDBossesOrgans.MALKUTH,
            "When counting fire organs, ice organs are also counted",
            "When counting ice organs, fire organs are also counted"
        );
        addOrganPassiveEffectSimple(FDBossesOrgans.MALKUTH, "Fire and ice organs count towards each other instead of canceling out");

        addItem(FDBossesOrgans.CHESED, "Chesed");
        addOrganPassiveEffect(
            FDBossesOrgans.CHESED,
            "Attacks summon a lightning ray that tracks the target",
            "Ray deals damage equal to 33% of own max health",
            "Applies Shocked to the target for 10 seconds",
            "Target deals 25% less damage while Shocked",
            "Cooldown: 3s"
        );
        addOrganPassiveEffectSimple(FDBossesOrgans.CHESED, "Attacks summon a lightning ray that tracks and shocks the target");

        addItem(FDBossesOrgans.GEBURAH, "Geburah");
        addOrganPassiveEffect(
            FDBossesOrgans.GEBURAH,
            "Attacks deal bonus damage equal to 3% of target max health per harmful effect on the target"
        );
    }

    private void fdBossesOrgansZH() {
        addItem(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "火焰王国战士之心");
        addOrganPassiveEffect(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "对战王国时免疫火焰攻击");

        addItem(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "冰霜王国战士之心");
        addOrganPassiveEffect(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "对战王国时免疫冰霜攻击");

        addItem(FDBossesOrgans.MALKUTH, "王国");
        addOrganPassiveEffect(
            FDBossesOrgans.MALKUTH,
            "计算炽焰器官数量时也计入冰霜器官数量",
            "计算冰霜器官数量时也计入炽焰器官数量"
        );
        addOrganPassiveEffectSimple(FDBossesOrgans.MALKUTH, "装备后炽焰与冰霜器官互相计入，不再互相抵消");

        addItem(FDBossesOrgans.CHESED, "慈悲");
        addOrganPassiveEffect(
            FDBossesOrgans.CHESED,
            "攻击时召唤闪电射线追踪目标",
            "射线造成自身最大生命值33%的伤害",
            "对目标施加10秒感电效果",
            "感电期间目标造成伤害降低25%",
            "冷却时间：3秒"
        );
        addOrganPassiveEffectSimple(FDBossesOrgans.CHESED, "攻击时召唤闪电射线追踪并感电目标");

        addItem(FDBossesOrgans.GEBURAH, "严厉");
        addOrganPassiveEffect(
            FDBossesOrgans.GEBURAH,
            "攻击时对目标每个负面效果额外造成其最大生命值3%的伤害"
        );
    }

    // ==================== AnvilCraft 皇家钢器官 ====================

    // ==================== AnvilCraft 皇家钢器官 ====================

    private void royalSteelOrgansEN() {
        addItem(AnvilCraftOrgans.ROYAL_STEEL_RIB, "Royal Steel Rib");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_MUSCLE, "Royal Steel Muscle");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_SPINE, "Royal Steel Spine");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_APPENDIX, "Royal Steel Appendix");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_HEART, "Royal Steel Heart");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_LUNG, "Royal Steel Lung");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_STOMACH, "Royal Steel Stomach");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_INTESTINE, "Royal Steel Intestine");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_KIDNEY, "Royal Steel Kidney");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_SPLEEN, "Royal Steel Spleen");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_LIVER, "Royal Steel Liver");
    }

    // ==================== AnvilCraft 诅咒金器官 ====================

    private void cursedGoldOrgansEN() {
        String effect0 = "Cursed organs in the chest impose stacking penalties";
        String effect1 = "1 or more organs inflict Weakness, currently level %s";
        String effect2 = "3 or more organs inflict Slowness, currently level %s";
        String effect3 = "5 or more organs inflict Hunger, currently level %s";
        String simple = "Cursed organs in the chest impose stacking debuffs";

        addItem(AnvilCraftOrgans.CURSED_GOLD_HEART, "Cursed Gold Heart");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_HEART, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_HEART, simple);
        addItem(AnvilCraftOrgans.CURSED_GOLD_LUNG, "Cursed Gold Lung");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_LUNG, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_LUNG, simple);
        addItem(AnvilCraftOrgans.CURSED_GOLD_LIVER, "Cursed Gold Liver");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_LIVER, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_LIVER, simple);
        addItem(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, "Cursed Gold Intestine");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, simple);
    }

    // ==================== AnvilCraft 余烬金属器官 ====================

    private void emberMetalOrgansEN() {
        String effect0 = "Each organ nullifies 25% of fire damage";
        String effect1 = "Heals for the nullified amount";
        String simple = "Each organ absorbs 25% fire damage as healing";

        addItem(AnvilCraftOrgans.EMBER_METAL_RIB, "Ember Metal Rib");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_RIB, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_RIB, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_MUSCLE, "Ember Metal Muscle");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_MUSCLE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_MUSCLE, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_SPINE, "Ember Metal Spine");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_SPINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_SPINE, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_APPENDIX, "Ember Metal Appendix");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_APPENDIX, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_APPENDIX, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_HEART, "Ember Metal Heart");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_HEART, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_HEART, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_LUNG, "Ember Metal Lung");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_LUNG, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_LUNG, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_STOMACH, "Ember Metal Stomach");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_STOMACH, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_STOMACH, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_INTESTINE, "Ember Metal Intestine");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_INTESTINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_INTESTINE, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_KIDNEY, "Ember Metal Kidney");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_KIDNEY, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_KIDNEY, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_SPLEEN, "Ember Metal Spleen");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_SPLEEN, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_SPLEEN, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_LIVER, "Ember Metal Liver");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_LIVER, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_LIVER, simple);
    }

    // ==================== AnvilCraft 浮霜金属器官 ====================

    private void frostMetalOrgansEN() {
        String effect0 = "Base attribute value is 1 plus enchantment bonus";
        String effect1 = "Enchantment bonus equals the square root of total enchantment levels rounded down";
        String simple = "Converts enchantments into organ attributes";

        addItem(AnvilCraftOrgans.FROST_METAL_HEART, "Frost Metal Heart");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_HEART, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_HEART, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_LUNG, "Frost Metal Lung");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_LUNG, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_LUNG, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_SPINE, "Frost Metal Spine");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_SPINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_SPINE, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_STOMACH, "Frost Metal Stomach");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_STOMACH, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_STOMACH, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_INTESTINE, "Frost Metal Intestine");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_INTESTINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_INTESTINE, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_KIDNEY, "Frost Metal Kidney");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_KIDNEY, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_KIDNEY, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_SPLEEN, "Frost Metal Spleen");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_SPLEEN, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_SPLEEN, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_LIVER, "Frost Metal Liver");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_LIVER, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_LIVER, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_APPENDIX, "Frost Metal Appendix");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_APPENDIX, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_APPENDIX, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_RIB, "Frost Metal Rib");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_RIB, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_RIB, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_MUSCLE, "Frost Metal Muscle");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_MUSCLE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_MUSCLE, simple);
    }

    // ==================== AnvilCraft 超限合金器官 ====================

    private void transcendiumOrgansEN() {
        String effect0 = "Base attribute value is 5 plus enchantment bonus";
        String effect1 = "Enchantment bonus equals the square root of total enchantment levels rounded down";
        String effect2 = "Also grants +1 Looting and +1 Fortune";
        String simple = "Converts enchantments into organ attributes and grants Looting and Fortune";

        addItem(AnvilCraftOrgans.TRANSCENDIUM_HEART, "Transcendium Heart");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_HEART, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_HEART, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LUNG, "Transcendium Lung");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_LUNG, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_LUNG, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPINE, "Transcendium Spine");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_SPINE, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_SPINE, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, "Transcendium Stomach");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, "Transcendium Intestine");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, "Transcendium Kidney");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, "Transcendium Spleen");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LIVER, "Transcendium Liver");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_LIVER, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_LIVER, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, "Transcendium Appendix");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_RIB, "Transcendium Rib");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_RIB, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_RIB, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, "Transcendium Muscle");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, simple);
    }

    // ==================== 电磁炮 ====================

    private void railgunEN() {
        addItem(AnvilCraftOrgans.RAILGUN, "Railgun");
        addOrganActiveSkill(
            AnvilCraftOrgans.RAILGUN,
            "Consumes 100 charge to fire a metal nugget from hand at %s damage multiplier",
            "Overload mode doubles damage and halves cooldown",
            "Hold Shift to view ammo list"
        );
        addOrganActiveSkillSimple(AnvilCraftOrgans.RAILGUN, "Fires metal nuggets from hand at %s damage multiplier, consuming 100 charge");
        add(WAICTooltipUtil.RAILGUN_AMMO_TRANSLATION, "%s: %s damage");
        add("death.attack.who_am_i_core.railgun", "%1$s was shot by %2$s with a railgun");
        add("death.attack.who_am_i_core.railgun.item", "%1$s was shot by %2$s with a railgun");
    }

    private void railgunZH() {
        addItem(AnvilCraftOrgans.RAILGUN, "电磁炮");
        addOrganActiveSkill(
            AnvilCraftOrgans.RAILGUN,
            "消耗100电荷，以%s伤害倍率将手中的金属粒发射出去",
            "超频模式下伤害翻倍，冷却减半",
            "按住Shift查看弹药列表"
        );
        addOrganActiveSkillSimple(AnvilCraftOrgans.RAILGUN, "消耗100电荷发射手中金属粒，伤害倍率%s");
        add(WAICTooltipUtil.RAILGUN_AMMO_TRANSLATION, "%s：%s伤害");
        add("death.attack.who_am_i_core.railgun", "%1$s被%2$s用电磁炮射杀");
        add("death.attack.who_am_i_core.railgun.item", "%1$s被%2$s用电磁炮射杀");
    }

    // ==================== AnvilCraft 皇家钢器官 ====================

    private void royalSteelOrgansZH() {
        addItem(AnvilCraftOrgans.ROYAL_STEEL_RIB, "皇家钢肋骨");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_MUSCLE, "皇家钢肌肉");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_SPINE, "皇家钢脊柱");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_APPENDIX, "皇家钢阑尾");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_HEART, "皇家钢心脏");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_LUNG, "皇家钢肺脏");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_STOMACH, "皇家钢胃");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_INTESTINE, "皇家钢肠子");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_KIDNEY, "皇家钢肾脏");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_SPLEEN, "皇家钢脾脏");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_LIVER, "皇家钢肝脏");
    }

    // ==================== AnvilCraft 诅咒金器官 ====================

    private void cursedGoldOrgansZH() {
        String effect0 = "胸腔中的诅咒器官会叠加惩罚效果";
        String effect1 = "1个及以上施加虚弱，当前%s级";
        String effect2 = "3个及以上施加缓慢，当前%s级";
        String effect3 = "5个及以上施加饥饿，当前%s级";
        String simple = "胸腔中诅咒器官叠加惩罚效果";

        addItem(AnvilCraftOrgans.CURSED_GOLD_HEART, "诅咒金心脏");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_HEART, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_HEART, simple);
        addItem(AnvilCraftOrgans.CURSED_GOLD_LUNG, "诅咒金肺脏");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_LUNG, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_LUNG, simple);
        addItem(AnvilCraftOrgans.CURSED_GOLD_LIVER, "诅咒金肝脏");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_LIVER, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_LIVER, simple);
        addItem(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, "诅咒金肠子");
        addOrganPassiveEffect(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, effect0, effect1, effect2, effect3);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, simple);
    }

    // ==================== AnvilCraft 余烬金属器官 ====================

    private void emberMetalOrgansZH() {
        String effect0 = "每个器官抵消25%火焰伤害";
        String effect1 = "回复抵消伤害等量的生命值";
        String simple = "每个器官将25%火焰伤害转化为治疗";

        addItem(AnvilCraftOrgans.EMBER_METAL_RIB, "余烬金属肋骨");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_RIB, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_RIB, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_MUSCLE, "余烬金属肌肉");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_MUSCLE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_MUSCLE, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_SPINE, "余烬金属脊柱");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_SPINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_SPINE, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_APPENDIX, "余烬金属阑尾");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_APPENDIX, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_APPENDIX, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_HEART, "余烬金属心脏");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_HEART, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_HEART, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_LUNG, "余烬金属肺脏");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_LUNG, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_LUNG, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_STOMACH, "余烬金属胃");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_STOMACH, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_STOMACH, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_INTESTINE, "余烬金属肠子");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_INTESTINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_INTESTINE, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_KIDNEY, "余烬金属肾脏");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_KIDNEY, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_KIDNEY, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_SPLEEN, "余烬金属脾脏");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_SPLEEN, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_SPLEEN, simple);
        addItem(AnvilCraftOrgans.EMBER_METAL_LIVER, "余烬金属肝脏");
        addOrganPassiveEffect(AnvilCraftOrgans.EMBER_METAL_LIVER, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.EMBER_METAL_LIVER, simple);
    }

    // ==================== AnvilCraft 浮霜金属器官 ====================

    private void frostMetalOrgansZH() {
        String effect0 = "基础属性值为1加上附魔加成";
        String effect1 = "附魔加成等于附魔等级总和的平方根向下取整";
        String simple = "将附魔转化为器官属性";

        addItem(AnvilCraftOrgans.FROST_METAL_HEART, "浮霜金属心脏");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_HEART, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_HEART, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_LUNG, "浮霜金属肺脏");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_LUNG, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_LUNG, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_SPINE, "浮霜金属脊柱");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_SPINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_SPINE, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_STOMACH, "浮霜金属胃");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_STOMACH, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_STOMACH, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_INTESTINE, "浮霜金属肠子");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_INTESTINE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_INTESTINE, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_KIDNEY, "浮霜金属肾脏");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_KIDNEY, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_KIDNEY, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_SPLEEN, "浮霜金属脾脏");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_SPLEEN, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_SPLEEN, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_LIVER, "浮霜金属肝脏");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_LIVER, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_LIVER, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_APPENDIX, "浮霜金属阑尾");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_APPENDIX, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_APPENDIX, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_RIB, "浮霜金属肋骨");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_RIB, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_RIB, simple);
        addItem(AnvilCraftOrgans.FROST_METAL_MUSCLE, "浮霜金属肌肉");
        addOrganPassiveEffect(AnvilCraftOrgans.FROST_METAL_MUSCLE, effect0, effect1);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.FROST_METAL_MUSCLE, simple);
    }

    // ==================== AnvilCraft 超限合金器官 ====================

    private void transcendiumOrgansZH() {
        String effect0 = "基础属性值为5加上附魔加成";
        String effect1 = "附魔加成等于附魔等级总和的平方根向下取整";
        String effect2 = "同时提供+1抢夺和+1时运";
        String simple = "将附魔转化为器官属性并提供抢夺与时运";

        addItem(AnvilCraftOrgans.TRANSCENDIUM_HEART, "超限合金心脏");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_HEART, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_HEART, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LUNG, "超限合金肺脏");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_LUNG, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_LUNG, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPINE, "超限合金脊柱");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_SPINE, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_SPINE, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, "超限合金胃");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, "超限合金肠子");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, "超限合金肾脏");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, "超限合金脾脏");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LIVER, "超限合金肝脏");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_LIVER, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_LIVER, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, "超限合金阑尾");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_RIB, "超限合金肋骨");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_RIB, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_RIB, simple);
        addItem(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, "超限合金肌肉");
        addOrganPassiveEffect(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, effect0, effect1, effect2);
        addOrganPassiveEffectSimple(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, simple);
    }

    // ==================== 幻想种器官 ====================

    private void fantasticalOrgansEN() {
        addItem(WAICOrgans.FANTASTICAL_HEART, "Fantastical Heart");
        addItem(WAICOrgans.FANTASTICAL_LUNG, "Fantastical Lung");
        addItem(WAICOrgans.FANTASTICAL_SPINE, "Fantastical Spine");
        addItem(WAICOrgans.FANTASTICAL_STOMACH, "Fantastical Stomach");
        addItem(WAICOrgans.FANTASTICAL_INTESTINE, "Fantastical Intestine");
        addItem(WAICOrgans.FANTASTICAL_KIDNEY, "Fantastical Kidney");
        addItem(WAICOrgans.FANTASTICAL_SPLEEN, "Fantastical Spleen");
        addItem(WAICOrgans.FANTASTICAL_LIVER, "Fantastical Liver");
        addItem(WAICOrgans.FANTASTICAL_APPENDIX, "Fantastical Appendix");
        addItem(WAICOrgans.FANTASTICAL_RIB, "Fantastical Rib");
        addItem(WAICOrgans.FANTASTICAL_MUSCLE, "Fantastical Muscle");
    }

    private void fantasticalOrgansZH() {
        addItem(WAICOrgans.FANTASTICAL_HEART, "幻想种心脏");
        addItem(WAICOrgans.FANTASTICAL_LUNG, "幻想种肺脏");
        addItem(WAICOrgans.FANTASTICAL_SPINE, "幻想种脊柱");
        addItem(WAICOrgans.FANTASTICAL_STOMACH, "幻想种胃");
        addItem(WAICOrgans.FANTASTICAL_INTESTINE, "幻想种肠子");
        addItem(WAICOrgans.FANTASTICAL_KIDNEY, "幻想种肾脏");
        addItem(WAICOrgans.FANTASTICAL_SPLEEN, "幻想种脾脏");
        addItem(WAICOrgans.FANTASTICAL_LIVER, "幻想种肝脏");
        addItem(WAICOrgans.FANTASTICAL_APPENDIX, "幻想种阑尾");
        addItem(WAICOrgans.FANTASTICAL_RIB, "幻想种肋骨");
        addItem(WAICOrgans.FANTASTICAL_MUSCLE, "幻想种肌肉");
    }

    // ==================== Cataclysm 器官 ====================

    private void cataclysmOrgansEN() {
        // 利维坦系列
        addItem(CataclysmOrgans.LEVIATHAN_HEART, "Leviathan Heart");
        addItem(CataclysmOrgans.LEVIATHAN_MUSCLE, "Leviathan Muscle");
        addItem(CataclysmOrgans.LEVIATHAN_INTESTINE, "Leviathan Intestine");
        addItem(CataclysmOrgans.LEVIATHAN_STOMACH, "Leviathan Stomach");
        addItem(CataclysmOrgans.LEVIATHAN_GILL, "Leviathan Gill");
        addItem(CataclysmOrgans.LEVIATHAN_SPINE, "Leviathan Spine");
        addItem(CataclysmOrgans.LEVIATHAN_FISHBONE, "Leviathan Fishbone");

        // 魂尸系列
        addItem(CataclysmOrgans.DRAUGR_SPINE, "Draugr Spine");
        addItem(CataclysmOrgans.DRAUGR_RIB, "Draugr Rib");

        // 咒翼灵骸系列
        addItem(CataclysmOrgans.MALEDICTUS_SPINE, "Maledictus Spine");
        addItem(CataclysmOrgans.MALEDICTUS_RIB, "Maledictus Rib");
        addItem(CataclysmOrgans.PHANTOM_HEART, "Phantom Heart");
        addOrganPassiveEffect(
            CataclysmOrgans.PHANTOM_HEART,
            "All damage dealt is increased by %s while sprinting"
        );
        addItem(CataclysmOrgans.PHANTOM_SHARD, "Phantom Shard");
        addItem(CataclysmOrgans.SEALING_STONE_SLAB, "Sealing Stone Slab");
        addOrganActiveSkill(
            CataclysmOrgans.SEALING_STONE_SLAB,
            "Summons a spiral array of phantom halberds around the self",
            "Each deals %s magic damage points"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.SEALING_STONE_SLAB, "Summons a spiral phantom halberd array, dealing %s damage each");

        // 斯库拉系列
        addItem(CataclysmOrgans.TIDAL_LANTERN, "Tidal Lantern");
        addOrganPassiveEffect(
            CataclysmOrgans.TIDAL_LANTERN,
            "On attack consumes all current phlegm and adds equal damage",
            "When 30 or more phlegm consumed, summons a wave lasting %stick"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.TIDAL_LANTERN, "Consumes phlegm on attack for bonus damage, at 30+ summons %stick wave");
        addItem(CataclysmOrgans.STORM_SPINE, "Storm Spine");
        addOrganPassiveEffect(
            CataclysmOrgans.STORM_SPINE,
            "On hit absorbs a portion of damage as phlegm and reduces damage accordingly",
            "Absorbs %1$s of damage as phlegm and reduces equal damage, absorption cap is %2$s points, disabled when phlegm is full"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.STORM_SPINE, "Absorbs %1$s of damage as phlegm on hit (cap %2$s points)");
        addItem(CataclysmOrgans.STORM_RIB, "Storm Rib");
        addOrganPassiveEffect(CataclysmOrgans.STORM_RIB, "+10 phlegm capacity");

        // 焰魔系列
        addItem(CataclysmOrgans.UNDYING_EMBER, "Undying Ember");
        addOrganPassiveEffect(CataclysmOrgans.UNDYING_EMBER, "Fire and ice organs cancel out, grants Strength based on the square root of the resulting fire organ count, becomes a penalty when negative");
        addItem(CataclysmOrgans.IGNITED_RIB_PLATING, "Ignited Rib Plating");
        addOrganPassiveEffect(CataclysmOrgans.IGNITED_RIB_PLATING, "Fire and ice organs cancel out, grants Defense equal to fire organ count within 3×3 range around own slot, becomes a penalty when negative");
        addItem(CataclysmOrgans.BLAZING_VISAGE, "Blazing Visage");
        addOrganPassiveEffect(
            CataclysmOrgans.BLAZING_VISAGE,
            "Melee attacks apply Blazing Brand for 5 seconds",
            "Blazing Brand reduces target armor and toughness by 20%",
            "Heals %s HP on hit based on fire organs within 3×3 range around own slot, reduced by ice organs",
            "Healing is doubled if target already has Blazing Brand"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.BLAZING_VISAGE, "Melee attacks apply Blazing Brand and heal %s HP based on fire organs within 3×3 range around own slot, reduced by ice organs");

        // 下界合金巨兽系列
        addItem(CataclysmOrgans.MONSTROSITY_CORE, "Monstrosity Core");
        addOrganPassiveEffect(
            CataclysmOrgans.MONSTROSITY_CORE,
            "+100 yellow bile capacity",
            "Generates 0.05 yellow bile per fire organ every second, reduced by ice organs"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.MONSTROSITY_CORE, "Generates yellow bile from fire organs, reduced by ice organs");
        addItem(CataclysmOrgans.MONSTROSITY_CIRCUIT, "Monstrosity Circuit");
        addOrganActiveSkill(
            CataclysmOrgans.MONSTROSITY_CIRCUIT,
            "Consumes 100 yellow bile to trigger an AoE earthquake",
            "Deals %s damage"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.MONSTROSITY_CIRCUIT, "Consumes yellow bile for %s damage earthquake");
        addItem(CataclysmOrgans.MONSTROSITY_FURNACE, "Monstrosity Furnace");
        addOrganPassiveEffect(
            CataclysmOrgans.MONSTROSITY_FURNACE,
            "Allows drinking lava buckets",
            "Restores all hunger and saturation and grants 100 yellow bile, grants Monstrous effect at level %s for 60 seconds"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.MONSTROSITY_FURNACE, "Allows drinking lava for yellow bile and level %s Monstrous buff lasting 60 seconds");

        // 远古工厂系列
        addItem(CataclysmOrgans.TACTICAL_DISK, "Tactical Disk");
        addOrganPassiveEffect(
            CataclysmOrgans.TACTICAL_DISK,
            "Each additional mechanical organ grants bonus health",
            "Bonus equals the square root of mechanical organ count times 2"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.TACTICAL_DISK, "Mechanical organs grant bonus health");
        addItem(CataclysmOrgans.REINFORCED_FRAME, "Reinforced Frame");
        addItem(CataclysmOrgans.POWER_CELL, "Power Cell");
        addOrganPassiveEffect(CataclysmOrgans.POWER_CELL, "Regenerates %s HP per second when not at full health");
        addItem(CataclysmOrgans.COMPUTE_CHIP, "Compute Chip");
        addOrganPassiveEffect(
            CataclysmOrgans.COMPUTE_CHIP,
            "Each additional mechanical organ grants bonus nerves",
            "Bonus equals the square root of mechanical organ count times 2"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.COMPUTE_CHIP, "Mechanical organs grant bonus nerves");
        addItem(CataclysmOrgans.MECHANICAL_STAR, "Mechanical Star");
        addOrganActiveSkill(
            CataclysmOrgans.MECHANICAL_STAR,
            "Locks onto a target in sight and fires homing missiles",
            "Fires a salvo of %s missiles",
            "Each missile deals 8 damage on hit"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.MECHANICAL_STAR, "Fires %s homing missiles, dealing 8 damage each on hit");
        addItem(CataclysmOrgans.DEATH_LENS, "Death Lens");
        addOrganActiveSkill(
            CataclysmOrgans.DEATH_LENS,
            "Fires a death laser beam",
            "Deals %1$s plus %2$s of target max HP damage",
            "Does not ignite or destroy blocks"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.DEATH_LENS, "Fires a death laser, dealing %1$s plus %2$s of target max HP damage");

        // 末影守卫系列
        addItem(CataclysmOrgans.GUARDIAN_STONE, "Guardian Stone");
        addItem(CataclysmOrgans.VOID_CRYSTAL_SPINE, "Void Crystal Spine");
        addOrganActiveSkill(
            CataclysmOrgans.VOID_CRYSTAL_SPINE,
            "Summons three rings of void runes around you, each dealing %s",
            "Inner ring has 6 runes at radius 1.5",
            "Middle ring has 12 runes at radius 2.5",
            "Outer ring has 14 runes at radius 3.5"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.VOID_CRYSTAL_SPINE, "Summons void rune rings, each rune dealing %s damage");

        // Ancient Remnant series
        addItem(CataclysmOrgans.SAND_GLAZE_HEART, "Sand-Glaze Heart");
        addOrganActiveSkill(
            CataclysmOrgans.SAND_GLAZE_HEART,
            "Summons 3 sandstorm tornadoes orbiting the self",
            "Lasts %s seconds, dealing 7 magic damage every 3 ticks",
            "Applies 10 seconds of Curse of the Desert on hit"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.SAND_GLAZE_HEART, "Summons 3 sandstorm tornadoes lasting %s seconds");
        addOrganPassiveEffect(
            CataclysmOrgans.SAND_GLAZE_HEART,
            "Deals %s extra damage to targets with Curse of the Desert"
        );
        addItem(CataclysmOrgans.REMNANT_SPINE, "Remnant Spine");
        addItem(CataclysmOrgans.REMNANT_RIB, "Remnant Rib");

        // 紫水晶巨蟹系列
        addItem(CataclysmOrgans.BLOOM_STONE_HEART, "Bloom Stone Heart");
        addItem(CataclysmOrgans.BLOOM_STONE_LUNG, "Bloom Stone Lung");
        addItem(CataclysmOrgans.BLOOM_STONE_STOMACH, "Bloom Stone Stomach");
        addItem(CataclysmOrgans.BLOOM_STONE_INTESTINE, "Bloom Stone Intestine");
        addItem(CataclysmOrgans.BLOOM_STONE_CAECUM, "Bloom Stone Caecum");
        addItem(CataclysmOrgans.BLOOM_STONE_MUSCLE, "Bloom Stone Muscle");
        addItem(CataclysmOrgans.BLOOM_STONE_CORE, "Bloom Stone Core");
        addOrganActiveSkill(
            CataclysmOrgans.BLOOM_STONE_CORE,
            "Fires 16 amethyst cluster projectiles in a ring",
            "Each dealing %s damage (scales with Defense)"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.BLOOM_STONE_CORE, "Fires a ring of amethyst clusters, each dealing %s damage");
        addItem(CataclysmOrgans.MOSSY_AMETHYST, "Mossy Amethyst");
        addOrganPassiveEffect(
            CataclysmOrgans.MOSSY_AMETHYST,
            "Each distinct type of magic organ grants %s defense"
        );
    }

    private void cataclysmOrgansZH() {
        // 利维坦系列
        addItem(CataclysmOrgans.LEVIATHAN_HEART, "利维坦心脏");
        addItem(CataclysmOrgans.LEVIATHAN_MUSCLE, "利维坦肌肉");
        addItem(CataclysmOrgans.LEVIATHAN_INTESTINE, "利维坦肠子");
        addItem(CataclysmOrgans.LEVIATHAN_STOMACH, "利维坦胃");
        addItem(CataclysmOrgans.LEVIATHAN_GILL, "利维坦鳃");
        addItem(CataclysmOrgans.LEVIATHAN_SPINE, "利维坦脊柱");
        addItem(CataclysmOrgans.LEVIATHAN_FISHBONE, "利维坦鱼骨");

        // 魂尸系列
        addItem(CataclysmOrgans.DRAUGR_SPINE, "魂尸脊柱");
        addItem(CataclysmOrgans.DRAUGR_RIB, "魂尸肋骨");

        // 咒翼灵骸系列
        addItem(CataclysmOrgans.MALEDICTUS_SPINE, "咒翼灵骸脊柱");
        addItem(CataclysmOrgans.MALEDICTUS_RIB, "咒翼灵骸肋骨");
        addItem(CataclysmOrgans.PHANTOM_HEART, "咒魂心脏");
        addOrganPassiveEffect(
            CataclysmOrgans.PHANTOM_HEART,
            "冲刺状态下所有造成的伤害增加%s"
        );
        addItem(CataclysmOrgans.PHANTOM_SHARD, "咒魂残片");
        addItem(CataclysmOrgans.SEALING_STONE_SLAB, "封印石板");
        addOrganActiveSkill(
            CataclysmOrgans.SEALING_STONE_SLAB,
            "以自身为中心螺旋召唤幻影战戟阵",
            "每道造成%s点魔法伤害"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.SEALING_STONE_SLAB, "螺旋召唤幻影战戟阵，每道造成%s点伤害");

        // 斯库拉系列
        addItem(CataclysmOrgans.TIDAL_LANTERN, "涛浪提灯");
        addOrganPassiveEffect(
            CataclysmOrgans.TIDAL_LANTERN,
            "攻击时消耗所有当前粘液并增加等额伤害",
            "消耗大于等于30的粘液时额外召唤持续%stick的水浪"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.TIDAL_LANTERN, "攻击时消耗粘液增加伤害，大于等于30时召唤持续%stick的水浪");
        addItem(CataclysmOrgans.STORM_SPINE, "风暴脊柱");
        addOrganPassiveEffect(
            CataclysmOrgans.STORM_SPINE,
            "受伤时吸收部分伤害转化为粘液，同时减免等额伤害",
            "吸收%1$s的伤害转化为粘液并减免等额伤害，吸收上限为%2$s点，粘液满时失效"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.STORM_SPINE, "受伤时吸收%1$s的伤害转化为粘液，吸收上限为%2$s点");
        addItem(CataclysmOrgans.STORM_RIB, "风暴肋骨");
        addOrganPassiveEffect(CataclysmOrgans.STORM_RIB, "+10粘液上限");

        // 焰魔系列
        addItem(CataclysmOrgans.UNDYING_EMBER, "不灭薪火");
        addOrganPassiveEffect(CataclysmOrgans.UNDYING_EMBER, "炽焰与冰霜互相抵消，根据抵消后的炽焰器官数平方根提供力量加成，为负值时变为减益");
        addItem(CataclysmOrgans.IGNITED_RIB_PLATING, "焰魔肋甲");
        addOrganPassiveEffect(CataclysmOrgans.IGNITED_RIB_PLATING, "炽焰与冰霜互相抵消，以自身槽位为中心3×3范围内每个炽焰器官提供1点防御，为负值时变为减益");
        addItem(CataclysmOrgans.BLAZING_VISAGE, "炽面甲");
        addOrganPassiveEffect(
            CataclysmOrgans.BLAZING_VISAGE,
            "近战攻击施加5秒炽热烙印",
            "炽热烙印降低目标20%护甲与韧性",
            "命中时根据以自身槽位为中心3×3范围内的炽焰器官数量回复%s点生命值，受冰霜器官抵消",
            "目标已有炽热烙印时回复量翻倍"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.BLAZING_VISAGE, "近战施加炽热烙印，并根据自身槽位为中心3×3范围内的炽焰器官数回复%s点生命，受冰霜器官抵消");

        // 下界合金巨兽系列
        addItem(CataclysmOrgans.MONSTROSITY_CORE, "巨兽炉心");
        addOrganPassiveEffect(
            CataclysmOrgans.MONSTROSITY_CORE,
            "+100黄胆汁上限",
            "炽焰器官每秒生成0.05黄胆汁，受冰霜器官抵消"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.MONSTROSITY_CORE, "根据炽焰器官生成黄胆汁，受冰霜器官抵消");
        addItem(CataclysmOrgans.MONSTROSITY_CIRCUIT, "巨兽回路");
        addOrganActiveSkill(
            CataclysmOrgans.MONSTROSITY_CIRCUIT,
            "消耗100黄胆汁触发范围地震",
            "造成%s点伤害"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.MONSTROSITY_CIRCUIT, "消耗黄胆汁造成%s点伤害的地震");
        addItem(CataclysmOrgans.MONSTROSITY_FURNACE, "巨兽熔炉");
        addOrganPassiveEffect(
            CataclysmOrgans.MONSTROSITY_FURNACE,
            "允许饮用岩浆桶",
            "恢复所有饥饿值与饱和度并获得100黄胆汁，获得%s级骇人之恶效果，持续60秒"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.MONSTROSITY_FURNACE, "允许饮用岩浆获取黄胆汁和%s级骇人之恶效果，持续60秒");

        // 远古工厂系列
        addItem(CataclysmOrgans.TACTICAL_DISK, "战术磁盘");
        addOrganPassiveEffect(
            CataclysmOrgans.TACTICAL_DISK,
            "每增加一个机械器官获得额外健康值",
            "额外健康值等于机械器官数乘2的平方根"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.TACTICAL_DISK, "机械器官提供额外健康值");
        addItem(CataclysmOrgans.REINFORCED_FRAME, "强化构架");
        addItem(CataclysmOrgans.POWER_CELL, "蓄能电芯");
        addOrganPassiveEffect(CataclysmOrgans.POWER_CELL, "未满血时每秒回复%s点生命值");
        addItem(CataclysmOrgans.COMPUTE_CHIP, "运算晶片");
        addOrganPassiveEffect(
            CataclysmOrgans.COMPUTE_CHIP,
            "每增加一个机械器官获得额外神经",
            "额外神经等于机械器官数乘2的平方根"
        );
        addOrganPassiveEffectSimple(CataclysmOrgans.COMPUTE_CHIP, "机械器官提供额外神经");
        addItem(CataclysmOrgans.MECHANICAL_STAR, "机械之星");
        addOrganActiveSkill(
            CataclysmOrgans.MECHANICAL_STAR,
            "锁定视线方向的敌人发射追踪导弹",
            "连续发射共%s枚导弹",
            "每枚命中造成8点伤害"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.MECHANICAL_STAR, "连续发射%s枚追踪导弹，每枚命中造成8点伤害");
        addItem(CataclysmOrgans.DEATH_LENS, "死亡透镜");
        addOrganActiveSkill(
            CataclysmOrgans.DEATH_LENS,
            "发射一道死亡激光",
            "造成%1$s加%2$s目标最大生命值的伤害",
            "不点火不破坏方块"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.DEATH_LENS, "发射死亡激光，造成%1$s加%2$s目标最大生命值的伤害");

        // 末影守卫系列
        addItem(CataclysmOrgans.GUARDIAN_STONE, "守卫石块");
        addItem(CataclysmOrgans.VOID_CRYSTAL_SPINE, "虚空晶脊");
        addOrganActiveSkill(
            CataclysmOrgans.VOID_CRYSTAL_SPINE,
            "以自身为中心召唤三环虚空符文阵，每符文造成%s点伤害",
            "内环6枚符文半径1.5",
            "中环12枚符文半径2.5",
            "外环14枚符文半径3.5"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.VOID_CRYSTAL_SPINE, "召唤虚空符文阵，每符文造成%s点伤害");

        // 远古遗魂系列
        addItem(CataclysmOrgans.SAND_GLAZE_HEART, "沙釉心脏");
        addOrganActiveSkill(
            CataclysmOrgans.SAND_GLAZE_HEART,
            "以自身为中心召唤3个沙暴龙卷风环绕",
            "持续%s秒，每3tick造成7点魔法伤害",
            "命中施加10秒沙漠诅咒效果"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.SAND_GLAZE_HEART, "召唤3个沙暴龙卷风，持续%s秒");
        addOrganPassiveEffect(
            CataclysmOrgans.SAND_GLAZE_HEART,
            "对带有沙漠诅咒效果的目标额外造成%s伤害"
        );
        addItem(CataclysmOrgans.REMNANT_SPINE, "遗魂脊柱");
        addItem(CataclysmOrgans.REMNANT_RIB, "遗魂肋骨");

        // 紫水晶巨蟹系列
        addItem(CataclysmOrgans.BLOOM_STONE_HEART, "花岩心脏");
        addItem(CataclysmOrgans.BLOOM_STONE_LUNG, "花岩肺脏");
        addItem(CataclysmOrgans.BLOOM_STONE_STOMACH, "花岩胃");
        addItem(CataclysmOrgans.BLOOM_STONE_INTESTINE, "花岩肠");
        addItem(CataclysmOrgans.BLOOM_STONE_CAECUM, "花岩盲囊");
        addItem(CataclysmOrgans.BLOOM_STONE_MUSCLE, "花岩肌肉");
        addItem(CataclysmOrgans.BLOOM_STONE_CORE, "花岩核心");
        addOrganActiveSkill(
            CataclysmOrgans.BLOOM_STONE_CORE,
            "以自身为中心发射16发紫水晶簇投射物",
            "每发造成%s伤害（随防御加成）"
        );
        addOrganActiveSkillSimple(CataclysmOrgans.BLOOM_STONE_CORE, "环形发射紫水晶簇，每发造成%s伤害");
        addItem(CataclysmOrgans.MOSSY_AMETHYST, "苔化紫水晶");
        addOrganPassiveEffect(
            CataclysmOrgans.MOSSY_AMETHYST,
            "每种不同的魔法器官提供%s点防御"
        );
    }

    // ==================== IronSpell 器官 ====================

    private void ironSpellOrgansEN() {
        // 死灵法师
        addItem(IronSpellOrgans.NECROMANCER_SPINE, "Necromancer Spine");
        addItem(IronSpellOrgans.NECROMANCER_RIB, "Necromancer Rib");

        // 原初受火者
        addItem(IronSpellOrgans.PRIMORDIAL_FLAME, "Primordial Flame");
        addOrganPassiveEffect(
            IronSpellOrgans.PRIMORDIAL_FLAME,
            "Fire spells gain +1 spell level"
        );

        // 高位唤魔者
        addItem(IronSpellOrgans.EMERALD_SKULL, "Emerald Skull");
        addOrganPassiveEffect(
            IronSpellOrgans.EMERALD_SKULL,
            "Evocation spells gain +1 spell level"
        );

        // 死者之王
        addItem(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "Corrupted Soul Lantern");
        addOrganPassiveEffect(
            IronSpellOrgans.CORRUPTED_SOUL_LANTERN,
            "Harvests souls from nearby killed entities into black bile",
            "Harvest amount equals the killed entity max health"
        );
        addOrganPassiveEffectSimple(
            IronSpellOrgans.CORRUPTED_SOUL_LANTERN,
            "Harvests souls from nearby killed entities into black bile"
        );

        addItem(IronSpellOrgans.DEAD_KING_SPINE, "Dead King Spine");
        addOrganPassiveEffect(
            IronSpellOrgans.DEAD_KING_SPINE,
            "Consumes black bile equal to damage absorbed",
            "Absorbs up to %s of damage"
        );
        addOrganPassiveEffectSimple(
            IronSpellOrgans.DEAD_KING_SPINE,
            "Consumes black bile to absorb up to %s of damage"
        );

        addItem(IronSpellOrgans.DEAD_KING_RIB, "Dead King Rib");
        addOrganPassiveEffect(
            IronSpellOrgans.DEAD_KING_RIB,
            "+10 max black bile"
        );
    }

    private void ironSpellOrgansZH() {
        // 死灵法师
        addItem(IronSpellOrgans.NECROMANCER_SPINE, "亡魂脊柱");
        addItem(IronSpellOrgans.NECROMANCER_RIB, "亡魂肋骨");

        // 原初受火者
        addItem(IronSpellOrgans.PRIMORDIAL_FLAME, "原初之火");
        addOrganPassiveEffect(
            IronSpellOrgans.PRIMORDIAL_FLAME,
            "施放火焰法术时法术等级+1"
        );

        // 高位唤魔者
        addItem(IronSpellOrgans.EMERALD_SKULL, "绿宝石头骨");
        addOrganPassiveEffect(
            IronSpellOrgans.EMERALD_SKULL,
            "施放唤魔法术时法术等级+1"
        );

        // 死者之王
        addItem(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "腐败魂灯");
        addOrganPassiveEffect(
            IronSpellOrgans.CORRUPTED_SOUL_LANTERN,
            "收割附近死亡生物的灵魂为黑胆汁",
            "收割量等于死亡生物的最大生命值"
        );
        addOrganPassiveEffectSimple(
            IronSpellOrgans.CORRUPTED_SOUL_LANTERN,
            "收割附近死亡生物灵魂为黑胆汁"
        );

        addItem(IronSpellOrgans.DEAD_KING_SPINE, "尸王脊柱");
        addOrganPassiveEffect(
            IronSpellOrgans.DEAD_KING_SPINE,
            "消耗等额黑胆汁吸收伤害",
            "吸收最多%s的伤害"
        );
        addOrganPassiveEffectSimple(
            IronSpellOrgans.DEAD_KING_SPINE,
            "消耗黑胆汁吸收最多%s的伤害"
        );

        addItem(IronSpellOrgans.DEAD_KING_RIB, "尸王肋骨");
        addOrganPassiveEffect(
            IronSpellOrgans.DEAD_KING_RIB,
            "+10黑胆汁上限"
        );
    }

    // ==================== Companions 器官 ====================

    private void companionsOrgansEN() {
        // 教宗系列
        addItem(CompanionsOrgans.PONTIFF_HEART, "Pontiff Heart");
        addOrganPassiveEffect(
            CompanionsOrgans.PONTIFF_HEART,
            "Heals %s of max health when below 30%% health",
            "Grants Strength, Resistance, Speed at level %1$s, lasting %2$stick",
            "Cooldown: 180s"
        );
        addOrganPassiveEffectSimple(
            CompanionsOrgans.PONTIFF_HEART,
            "Heals %1$s of max health and grants level %2$s buffs at low health, lasting %3$stick"
        );

        addItem(CompanionsOrgans.PONTIFF_LUNG, "Pontiff Lung");
        addItem(CompanionsOrgans.PONTIFF_STOMACH, "Pontiff Stomach");
        addItem(CompanionsOrgans.PONTIFF_INTESTINE, "Pontiff Intestine");
        addItem(CompanionsOrgans.PONTIFF_KIDNEY, "Pontiff Kidney");

        addItem(CompanionsOrgans.PONTIFF_SPLEEN, "Pontiff Spleen");
        addOrganActiveSkill(
            CompanionsOrgans.PONTIFF_SPLEEN,
            "Releases an expanding fire ring centered on self",
            "Deals magic damage and ignites entities in its path"
        );
        addOrganActiveSkillSimple(
            CompanionsOrgans.PONTIFF_SPLEEN,
            "Releases an expanding fire ring"
        );

        addItem(CompanionsOrgans.PONTIFF_LIVER, "Pontiff Liver");

        addItem(CompanionsOrgans.PONTIFF_APPENDIX, "Pontiff Appendix");
        addOrganActiveSkill(
            CompanionsOrgans.PONTIFF_APPENDIX,
            "Launches a tracking star at the target in sight",
            "Creates a red star that ignites when fire organs are not fewer than ice organs",
            "Creates a blue star that freezes when fire organs are fewer than ice organs"
        );
        addOrganActiveSkillSimple(
            CompanionsOrgans.PONTIFF_APPENDIX,
            "Launches a tracking star that ignites or freezes"
        );

        addItem(CompanionsOrgans.PONTIFF_MUSCLE, "Pontiff Muscle");

        // 蛋糕系列
        addItem(CompanionsOrgans.CAKE_HEART, "Cake Heart");
        addItem(CompanionsOrgans.CAKE_LUNG, "Cake Lung");

        addItem(CompanionsOrgans.CAKE_STOMACH, "Cake Stomach");
        addOrganPassiveEffect(
            CompanionsOrgans.CAKE_STOMACH,
            "Eating food grants Sweetness, restoring 1 HP per second to non-hostile entities within 16 blocks",
            "Each eat adds %s level(s) and resets duration",
            "Players additionally restore 1 food and 1 saturation per second"
        );
        addOrganPassiveEffectSimple(
            CompanionsOrgans.CAKE_STOMACH,
            "Eating food stacks %s level(s) of Sweetness and heals nearby entities"
        );

        addItem(CompanionsOrgans.CAKE_LIVER, "Cake Liver");
        addOrganPassiveEffect(
            CompanionsOrgans.CAKE_LIVER,
            "Sweetness level 2 or above consumes 1 level per second to remove 1 random harmful effect"
        );
    }

    private void companionsOrgansZH() {
        // 教宗系列
        addItem(CompanionsOrgans.PONTIFF_HEART, "教宗心脏");
        addOrganPassiveEffect(
            CompanionsOrgans.PONTIFF_HEART,
            "生命值降至30%%以下时回复%s的最大生命值",
            "获得%1$s级的力量、抗性、速度，持续%2$stick",
            "冷却时间：180秒"
        );
        addOrganPassiveEffectSimple(
            CompanionsOrgans.PONTIFF_HEART,
            "低血量时回复%1$s的最大生命值并获得%2$s级的增益，持续%3$stick"
        );

        addItem(CompanionsOrgans.PONTIFF_LUNG, "教宗肺脏");
        addItem(CompanionsOrgans.PONTIFF_STOMACH, "教宗胃");
        addItem(CompanionsOrgans.PONTIFF_INTESTINE, "教宗肠子");
        addItem(CompanionsOrgans.PONTIFF_KIDNEY, "教宗肾脏");

        addItem(CompanionsOrgans.PONTIFF_SPLEEN, "教宗脾脏");
        addOrganActiveSkill(
            CompanionsOrgans.PONTIFF_SPLEEN,
            "以自身为中心释放向外扩展的火环",
            "对路径上的生物造成魔法伤害并点燃"
        );
        addOrganActiveSkillSimple(
            CompanionsOrgans.PONTIFF_SPLEEN,
            "释放向外扩展的火环"
        );

        addItem(CompanionsOrgans.PONTIFF_LIVER, "教宗肝脏");

        addItem(CompanionsOrgans.PONTIFF_APPENDIX, "教宗阑尾");
        addOrganActiveSkill(
            CompanionsOrgans.PONTIFF_APPENDIX,
            "向视线中的目标发射追踪星弹",
            "炽焰数≥冰霜数时发射点燃的红色星弹",
            "炽焰数<冰霜数时发射冻结的蓝色星弹"
        );
        addOrganActiveSkillSimple(
            CompanionsOrgans.PONTIFF_APPENDIX,
            "发射点燃或冻结的追踪星弹"
        );

        addItem(CompanionsOrgans.PONTIFF_MUSCLE, "教宗肌肉");

        // 蛋糕系列
        addItem(CompanionsOrgans.CAKE_HEART, "蛋糕心脏");
        addItem(CompanionsOrgans.CAKE_LUNG, "蛋糕肺脏");

        addItem(CompanionsOrgans.CAKE_STOMACH, "蛋糕胃");
        addOrganPassiveEffect(
            CompanionsOrgans.CAKE_STOMACH,
            "食用食物获得甜蜜，每秒为周围16格内非敌对生物恢复1点生命值",
            "每次食用叠加%s级并重置持续时长",
            "玩家每秒额外恢复1点饥饿值与1点饱和度"
        );
        addOrganPassiveEffectSimple(
            CompanionsOrgans.CAKE_STOMACH,
            "食用食物叠加%s级甜蜜效果，并治疗周围生物"
        );

        addItem(CompanionsOrgans.CAKE_LIVER, "蛋糕肝脏");
        addOrganPassiveEffect(
            CompanionsOrgans.CAKE_LIVER,
            "甜蜜等级达到2时每秒消耗1级清除1个随机负面效果"
        );
    }

    private void addOrganDescription(Supplier<Item> item, String... lines) {
        String base = "organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".description.";
        for (int i = 0; i < lines.length; i++) {
            add(base + i, lines[i]);
        }
    }

    private void addOrganPassiveEffect(Supplier<Item> item, String... lines) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item.get());
        String base = "organ." + key.getNamespace() + "." + key.getPath() + ".passive_effect.";
        for (int i = 0; i < lines.length; i++) {
            add(base + i, lines[i]);
        }
    }

    private void addOrganPassiveEffectSimple(Supplier<Item> item, String... lines) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item.get());
        String base = "organ." + key.getNamespace() + "." + key.getPath() + ".passive_effect.simple.";
        for (int i = 0; i < lines.length; i++) {
            add(base + i, lines[i]);
        }
    }

    private void addOrganActiveSkill(Supplier<Item> item, String... lines) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item.get());
        String base = "organ." + key.getNamespace() + "." + key.getPath() + ".active_skill.";
        for (int i = 0; i < lines.length; i++) {
            add(base + i, lines[i]);
        }
    }

    private void addOrganActiveSkillSimple(Supplier<Item> item, String... lines) {
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item.get());
        String base = "organ." + key.getNamespace() + "." + key.getPath() + ".active_skill.simple.";
        for (int i = 0; i < lines.length; i++) {
            add(base + i, lines[i]);
        }
    }

    private void addAttribute(Holder<Attribute> attribute, String value) {
        add(attribute.value().getDescriptionId(), value);
    }

    private void addAttributeDescription(Holder<Attribute> attribute, String... lines) {
        String base = attribute.value().getDescriptionId() + ".description.";
        for (int i = 0; i < lines.length; i++) {
            add(base + i, lines[i]);
        }
    }

    private void addAttributeValueEffect(Holder<Attribute> attribute, String value) {
        add(AttributeDisplayManager.getValueEffectKey(attribute), value);
    }

    private void addChestCavityTypeName(ChestCavityType type, String name) {
        add(WAICChestCavityTypeManager.getTranslationKey(type), name);
    }

    @Override
    protected void addTranslations() {
        switch (locale) {
            case EN_US -> English();
            case ZH_CN -> Chinese();
        }
    }
}

package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.manager.WAICDamageTagManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.AnvilCraftOrgans;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.register.WAICCreativeModeTab;

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

        addItem(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, "Ferrous Wroughtnaut Heart Mirror");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 0, "Removes FOV modification");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 1, "Blocks entity attacks from the front");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 2, "Cannot move for 3 seconds after attacking");

        addItem(MowziesMobOrgans.CHEST_NOVA, "Chest Nova");
        addOrganDescription(MowziesMobOrgans.CHEST_NOVA, "It beats like a heart");
        addOrganSkill(
            MowziesMobOrgans.CHEST_NOVA,
            0,
            "When chest cavity is closed, burns organs in 3x3 area (except mechanical and magical organs)"
        );
        addOrganSkill(
            MowziesMobOrgans.CHEST_NOVA,
            1,
            "Umvuthana masks in 3x3 area summon corresponding followers, respawning 30 seconds after death"
        );
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 2, "Masks provide their potion effects to the owner");

        addItem(MowziesMobOrgans.CONTROL_ROD, "Control Rod");
        addOrganDescription(MowziesMobOrgans.CONTROL_ROD, "Which sun bird is this?");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 0, "When Control Rod is in 3x3 range of Chest Nova:");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 1, " •Reduces Umvuthana follower respawn cooldown from 30s to 10s");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 2, " •Masks also provide effects to followers");

        // 衰老器官翻译
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
        addItem(MowziesMobOrgans.ZEN_HEART, "Zen Heart");

        // 泥峭器官翻译
        addItem(MowziesMobOrgans.BLUFF_CORE, "Bluff Core");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 0, "Allows eating dirt, restores 4 hunger");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 1, "Use skill to use dirt blocks");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 2, "Different dirt types grant different effects:");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 3, " •Grass/Moss/Mycelium → Strength II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 4, " •Coarse Dirt/Podzol/Mud → Haste II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 5, " •Rooted Dirt/Muddy Mangrove Roots → Resistance II");

        addItem(MowziesMobOrgans.BLUFF_TABLET, "Bluff Tablet");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 0, "Allows eating dirt, restores 4 hunger");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 1, "Each tablet grants 2 absorption hearts (max: mud organs × 8)");

        addItem(MowziesMobOrgans.ACTIVE_BLUFF_ROD, "Active Bluff Rod");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 0, "Allows eating dirt, restores 4 hunger");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 1, "Each rod grants 4 saturation when eating dirt");

        // 龙类器官翻译
        addItem(IceAndFireOrgans.FIRE_DRAGON_HEART, "Fire Dragon Heart");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LUNG, "Fire Dragon Lung");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPINE, "Fire Dragon Spine");
        addItem(IceAndFireOrgans.FIRE_DRAGON_STOMACH, "Fire Dragon Stomach");
        addItem(IceAndFireOrgans.FIRE_DRAGON_INTESTINE, "Fire Dragon Intestine");
        addItem(IceAndFireOrgans.FIRE_DRAGON_KIDNEY, "Fire Dragon Kidney");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPLEEN, "Fire Dragon Spleen");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LIVER, "Fire Dragon Liver");
        addItem(IceAndFireOrgans.FIRE_DRAGON_GEM, "Fire Dragon Gem");
        addItem(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "Fire Dragon Breath Sac");
        addOrganSkill(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "Sprays fire forward");
        addItem(IceAndFireOrgans.FIRE_DRAGON_RIB, "Fire Dragon Rib");
        addItem(IceAndFireOrgans.FIRE_DRAGON_MUSCLE, "Fire Dragon Muscle");

        addItem(IceAndFireOrgans.ICE_DRAGON_HEART, "Ice Dragon Heart");
        addItem(IceAndFireOrgans.ICE_DRAGON_LUNG, "Ice Dragon Lung");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPINE, "Ice Dragon Spine");
        addItem(IceAndFireOrgans.ICE_DRAGON_STOMACH, "Ice Dragon Stomach");
        addItem(IceAndFireOrgans.ICE_DRAGON_INTESTINE, "Ice Dragon Intestine");
        addItem(IceAndFireOrgans.ICE_DRAGON_KIDNEY, "Ice Dragon Kidney");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPLEEN, "Ice Dragon Spleen");
        addItem(IceAndFireOrgans.ICE_DRAGON_LIVER, "Ice Dragon Liver");
        addItem(IceAndFireOrgans.ICE_DRAGON_GEM, "Ice Dragon Gem");
        addItem(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "Ice Dragon Breath Sac");
        addOrganSkill(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "Sprays ice forward");
        addItem(IceAndFireOrgans.ICE_DRAGON_RIB, "Ice Dragon Rib");
        addItem(IceAndFireOrgans.ICE_DRAGON_MUSCLE, "Ice Dragon Muscle");

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, "Lightning Dragon Heart");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, "Lightning Dragon Lung");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, "Lightning Dragon Spine");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, "Lightning Dragon Stomach");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, "Lightning Dragon Intestine");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, "Lightning Dragon Kidney");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, "Lightning Dragon Spleen");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, "Lightning Dragon Liver");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_GEM, "Lightning Dragon Gem");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "Lightning Dragon Breath Sac");
        addOrganSkill(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "Sprays lightning forward");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, "Lightning Dragon Rib");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, "Lightning Dragon Muscle");

        addItem(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "Fire Malkuth Warrior Heart");
        addItem(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "Ice Malkuth Warrior Heart");
        addItem(FDBossesOrgans.MALKUTH, "Malkuth");
        addItem(FDBossesOrgans.CHESED, "Chesed");
        addItem(FDBossesOrgans.GEBURAH, "Geburah");

        addItem(WAICOrgans.DIVINE_CORE, "Divine Core");
        addItem(WAICOrgans.FROST_CORE, "Frost Core");
        addItem(WAICOrgans.FLAME_CORE, "Flame Core");
        addItem(WAICOrgans.NATURE_CORE, "Nature Core");

        // 悚恐怖官翻译
        addItem(IceAndFireOrgans.BITTER_FLESH, "Bitter Flesh");
        addItem(IceAndFireOrgans.ICE_SHARD, "Ice Shard");
        addItem(IceAndFireOrgans.FROSTBURN_SOUL, "Frostburn Soul");
        addItem(IceAndFireOrgans.DREAD_PHYLACTERY, "Dread Phylactery");
        addItem(IceAndFireOrgans.DREAD_RIB, "Dread Rib");
        addItem(IceAndFireOrgans.DREAD_SPINE, "Dread Spine");

        // 九头蛇器官翻译
        addItem(IceAndFireOrgans.HYDRA_HEART, "Hydra Heart");
        addItem(IceAndFireOrgans.HYDRA_LUNG, "Hydra Lung");
        addOrganDescription(IceAndFireOrgans.HYDRA_LUNG, "Consumes poison effect to release hydra venom breath");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 0, "Consumes current poison effect on the player");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 1, "Breath duration = log10(poison duration) seconds");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 2, "Deals damage every 4 ticks (damage = poison level + 1)");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 3, "Hit enemies receive original poison effect");
        addItem(IceAndFireOrgans.HYDRA_SPINE, "Hydra Spine");
        addItem(IceAndFireOrgans.HYDRA_STOMACH, "Hydra Stomach");
        addItem(IceAndFireOrgans.HYDRA_INTESTINE, "Hydra Intestine");
        addItem(IceAndFireOrgans.HYDRA_SPLEEN, "Hydra Spleen");
        addOrganDescription(IceAndFireOrgans.HYDRA_SPLEEN, "Unique organ");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 0, "Converts poison to healing when health is below 50%");
        addOrganSkill(
            IceAndFireOrgans.HYDRA_SPLEEN,
            1,
            "Each second, consume poison duration to heal (heal amount = poison level × multiplier)"
        );
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 2, "Multiplier: 10 (≤10% HP), 5 (≤20% HP), 3 (≤50% HP)");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 3, "1 healing = 1 tick of poison duration consumed");
        addItem(IceAndFireOrgans.HYDRA_RIB, "Hydra Rib");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 0, "When taking damage, transfers 5s of poison to attacker");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 1, "Poison effect stacks on the attacker");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 2, "Reduces damage taken by poison level");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 3, "Example: Poison II reduces incoming damage by 2");

        addItem(IceAndFireOrgans.HYDRA_MUSCLE, "Hydra Muscle");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 0, "On melee attack, transfers 5s of poison to target");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 1, "Poison effect stacks on the target");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 2, "Deals extra damage equal to poison level");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 3, "Example: Poison II deals 2 extra damage");

        // 九头蛇心脏
        addOrganDescription(IceAndFireOrgans.HYDRA_HEART, "Converts poison into regeneration");
        addOrganSkill(IceAndFireOrgans.HYDRA_HEART, 0, "Every 10 ticks, converts current poison to regeneration (1s, same level)");
        addOrganSkill(IceAndFireOrgans.HYDRA_HEART, 1, "Immune to poison damage");

        // 九头蛇脊柱
        addOrganDescription(IceAndFireOrgans.HYDRA_SPINE, "Cheats death through poison");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 0, "When dying with >10s of poison, recover to 10% HP");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 1, "Increases poison level and halves remaining duration");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 2, "Cancels death event");

        // 九头蛇胃
        addOrganDescription(IceAndFireOrgans.HYDRA_STOMACH, "Converts food negative effects into poison");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 0, "When eating, converts harmful food effects to poison");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 1, "Duration = harmful effects duration × stomach count");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 2, "Adds existing poison duration and takes max amplifier");

        // 九头蛇肠子
        addOrganDescription(IceAndFireOrgans.HYDRA_INTESTINE, "Amplifies food effects");
        addOrganSkill(IceAndFireOrgans.HYDRA_INTESTINE, 0, "Increases duration of food effects by 50% per organ");

        // 幻想种器官翻译
        addItem(IceAndFireOrgans.FANTASTICAL_HEART, "Fantastical Heart");
        addItem(IceAndFireOrgans.FANTASTICAL_LUNG, "Fantastical Lung");
        addItem(IceAndFireOrgans.FANTASTICAL_SPINE, "Fantastical Spine");
        addItem(IceAndFireOrgans.FANTASTICAL_STOMACH, "Fantastical Stomach");
        addItem(IceAndFireOrgans.FANTASTICAL_INTESTINE, "Fantastical Intestine");
        addItem(IceAndFireOrgans.FANTASTICAL_KIDNEY, "Fantastical Kidney");
        addItem(IceAndFireOrgans.FANTASTICAL_SPLEEN, "Fantastical Spleen");
        addItem(IceAndFireOrgans.FANTASTICAL_LIVER, "Fantastical Liver");
        addItem(IceAndFireOrgans.FANTASTICAL_APPENDIX, "Fantastical Appendix");
        addItem(IceAndFireOrgans.FANTASTICAL_RIB, "Fantastical Rib");
        addItem(IceAndFireOrgans.FANTASTICAL_MUSCLE, "Fantastical Muscle");

        // 浮霜器官翻译
        addItem(AnvilCraftOrgans.FROST_METAL_HEART, "Frost Metal Heart");
        addItem(AnvilCraftOrgans.FROST_METAL_LUNG, "Frost Metal Lung");
        addItem(AnvilCraftOrgans.FROST_METAL_SPINE, "Frost Metal Spine");
        addItem(AnvilCraftOrgans.FROST_METAL_STOMACH, "Frost Metal Stomach");
        addItem(AnvilCraftOrgans.FROST_METAL_INTESTINE, "Frost Metal Intestine");
        addItem(AnvilCraftOrgans.FROST_METAL_KIDNEY, "Frost Metal Kidney");
        addItem(AnvilCraftOrgans.FROST_METAL_SPLEEN, "Frost Metal Spleen");
        addItem(AnvilCraftOrgans.FROST_METAL_LIVER, "Frost Metal Liver");
        addItem(AnvilCraftOrgans.FROST_METAL_APPENDIX, "Frost Metal Appendix");
        addItem(AnvilCraftOrgans.FROST_METAL_RIB, "Frost Metal Rib");
        addItem(AnvilCraftOrgans.FROST_METAL_MUSCLE, "Frost Metal Muscle");

        // 浮霜器官skill描述
        add("organ.who_am_i_core.tooltips.frost_metal_merciless", "Merciless: convert all enchantments into organ attributes");

        // 墨水器官翻译
        addItem(WAICOrgans.INK_HEART, "Ink Heart");
        addItem(WAICOrgans.INK_LUNG, "Ink Lung");
        addItem(WAICOrgans.INK_SPINE, "Ink Spine");
        addItem(WAICOrgans.INK_STOMACH, "Ink Stomach");
        addItem(WAICOrgans.INK_INTESTINE, "Ink Intestine");
        addItem(WAICOrgans.INK_KIDNEY, "Ink Kidney");
        addItem(WAICOrgans.INK_SPLEEN, "Ink Spleen");
        addItem(WAICOrgans.INK_LIVER, "Ink Liver");
        addItem(WAICOrgans.INK_APPENDIX, "Ink Appendix");
        addItem(WAICOrgans.INK_RIB, "Ink Rib");
        addItem(WAICOrgans.INK_MUSCLE, "Ink Muscle");
        addItem(WAICOrgans.INK_BOTTLE, "Ink Bottle");
        addItem(WAICOrgans.NIB, "Nib");

        // 颜料器官翻译
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
        addItem(WAICOrgans.PALETTE, "Palette");

        // 木质器官翻译
        addItem(WAICOrgans.WOODEN_HEART, "Wooden Heart");
        addItem(WAICOrgans.WOODEN_LUNG, "Wooden Lung");
        addItem(WAICOrgans.WOODEN_STOMACH, "Wooden Stomach");
        addItem(WAICOrgans.WOODEN_INTESTINE, "Wooden Intestine");
        addItem(WAICOrgans.WOODEN_KIDNEY, "Wooden Kidney");
        addItem(WAICOrgans.WOODEN_SPLEEN, "Wooden Spleen");
        addItem(WAICOrgans.WOODEN_LIVER, "Wooden Liver");
        addItem(WAICOrgans.WOODEN_APPENDIX, "Wooden Appendix");
        addItem(WAICOrgans.WOODEN_MUSCLE, "Wooden Muscle");

        // 弗兰肯斯坦器官翻译
        addItem(WAICOrgans.FRANKENSTEIN_HEART, "Frankenstein Heart");
        addItem(WAICOrgans.FRANKENSTEIN_LUNG, "Frankenstein Lung");
        addItem(WAICOrgans.FRANKENSTEIN_STOMACH, "Frankenstein Stomach");
        addItem(WAICOrgans.FRANKENSTEIN_INTESTINE, "Frankenstein Intestine");
        addItem(WAICOrgans.FRANKENSTEIN_KIDNEY, "Frankenstein Kidney");
        addItem(WAICOrgans.FRANKENSTEIN_SPLEEN, "Frankenstein Spleen");
        addItem(WAICOrgans.FRANKENSTEIN_LIVER, "Frankenstein Liver");
        addItem(WAICOrgans.FRANKENSTEIN_APPENDIX, "Frankenstein Appendix");
        addItem(WAICOrgans.FRANKENSTEIN_MUSCLE, "Frankenstein Muscle");

        // 肿瘤器官翻译
        addItem(WAICOrgans.TUMOR_HEART, "Tumor Heart");
        addItem(WAICOrgans.TUMOR_LUNG, "Tumor Lung");
        addItem(WAICOrgans.TUMOR_STOMACH, "Tumor Stomach");
        addItem(WAICOrgans.TUMOR_INTESTINE, "Tumor Intestine");
        addItem(WAICOrgans.TUMOR_KIDNEY, "Tumor Kidney");
        addItem(WAICOrgans.TUMOR_SPLEEN, "Tumor Spleen");
        addItem(WAICOrgans.TUMOR_LIVER, "Tumor Liver");
        addItem(WAICOrgans.TUMOR_APPENDIX, "Tumor Appendix");
        addItem(WAICOrgans.TUMOR_MUSCLE, "Tumor Muscle");

        // 九狱器官翻译
        addItem(WAICOrgans.LIMBO, "Limbo");
        addItem(WAICOrgans.LUST, "Lust");
        addItem(WAICOrgans.GLUTTONY, "Gluttony");
        addItem(WAICOrgans.GREED, "Greed");
        addItem(WAICOrgans.WRATH, "Wrath");
        addItem(WAICOrgans.HERESY, "Heresy");
        addItem(WAICOrgans.VIOLENCE, "Violence");
        addItem(WAICOrgans.FRAUD, "Fraud");
        addItem(WAICOrgans.TREASON, "Treason");

        // 双子魔眼器官翻译
        addItem(WAICOrgans.STRANGE_EYEBALL, "Strange Eyeball");
        addItem(WAICOrgans.EERIE_EYEBALL, "Eerie Eyeball");
        addItem(WAICOrgans.STRANGE_MECHANICAL_EYEBALL, "Strange Mechanical Eyeball");
        addItem(WAICOrgans.EERIE_MECHANICAL_EYEBALL, "Eerie Mechanical Eyeball");

        // 拟态器官翻译
        addItem(WAICOrgans.MIMIC_HEART, "Mimic Heart");
        addItem(WAICOrgans.MIMIC_LIVER, "Mimic Liver");
        addItem(WAICOrgans.MIMIC_LUNG, "Mimic Lung");

        // 单个器官翻译
        addItem(WAICOrgans.HAUNTED_BONE, "Haunted Bone");
        addOrganDescription(WAICOrgans.HAUNTED_BONE, "Dududa dududa");
        addItem(WAICOrgans.SWORD_BONE, "Sword Bone");
        addOrganDescription(WAICOrgans.SWORD_BONE, "As sharp as a sword");
        addItem(WAICOrgans.STRAIGHT_INTESTINE, "Straight Intestine");
        addItem(WAICOrgans.SQUASH, "Squash");

        addAttribute(WAICAttribute.TEMPERATURE, "Temperature");
        addAttribute(WAICAttribute.BLOCK, "Block");
        addAttribute(WAICAttribute.COUNTER_ATTACK, "Counter Attack");
        addAttribute(WAICAttribute.HEAL, "Heal");
        addAttribute(WAICAttribute.MELEE_DAMAGE, "Melee Damage");
        addAttribute(WAICAttribute.RANGED_DAMAGE, "Ranged Damage");
        addAttribute(WAICAttribute.MAGIC_DAMAGE, "Magic Damage");
        addAttribute(WAICAttribute.MELEE_DAMAGE_PERCENTAGE, "Melee Damage Percentage");
        addAttribute(WAICAttribute.RANGED_DAMAGE_PERCENTAGE, "Ranged Damage Percentage");
        addAttribute(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, "Magic Damage Percentage");

        // 伤害类型标签翻译
        add(WAICDamageTagManager.IS_MELEE, "Melee");

        // 物品标签翻译
        add(WAICItemTagManager.MAGIC, "Magic");
        add(WAICItemTagManager.MECHANICAL, "Mechanical");
        add(WAICItemTagManager.SUMMON, "Summon");
        add(WAICItemTagManager.UNIQUE, "Unique");
        add(WAICItemTagManager.FIRE_DRAGON, "Fire Dragon");
        add(WAICItemTagManager.ICE_DRAGON, "Ice Dragon");
        add(WAICItemTagManager.LIGHTNING_DRAGON, "Lightning Dragon");
    }

    public void Chinese() {
        add(WAICCreativeModeTab.WHO_AM_I_CORE_TAB_TRANSLATABLE, "我非我");

        addItem(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, "钢铁守护者护心镜");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 0, "移除FOV修改");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 1, "抵挡来自正面的实体的攻击");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 2, "攻击后3秒内不能移动");


        addItem(MowziesMobOrgans.CHEST_NOVA, "胸中新星");
        addOrganDescription(MowziesMobOrgans.CHEST_NOVA, "它像心脏一样跳动着");
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 0, "当胸腔关闭时，烧毁3x3范围内的器官（机械系和魔法系器官除外）");
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 1, "3x3范围内的乌姆塔纳面具会召唤对应的追随者，追随者死亡30秒后重新召唤");
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 2, "面具会为主人提供其药水效果");

        addItem(MowziesMobOrgans.CONTROL_ROD, "制御棒");
        addOrganDescription(MowziesMobOrgans.CONTROL_ROD, "这他妈是哪个太阳鸟？");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 0, "当制御棒在胸中新星的3x3范围内时：");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 1, " •乌姆塔纳追随者重新召唤冷却时间从30秒缩短为10秒");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 2, " •面具也会给追随者提供效果");

        // 衰老器官翻译
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
        addItem(MowziesMobOrgans.ZEN_HEART, "禅心");

        // 泥峭器官翻译
        addItem(MowziesMobOrgans.BLUFF_CORE, "泥峭核心");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 0, "允许食用泥土，恢复4点饥饿值");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 1, "使用技能可食用泥土方块");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 2, "不同泥土类型提供不同效果：");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 3, " •草方块/苔藓块/菌丝：力量II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 4, " •砂土/灰化土/泥巴：急迫II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 5, " •缠根泥土/含泥红树根：抗性提升II");

        addItem(MowziesMobOrgans.BLUFF_TABLET, "泥峭铭文板");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 0, "允许食用泥土，恢复4点饥饿值");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 1, "每有一个铭文板，食用泥土时获得2点吸收生命值（上限为泥峭器官数量x8）");

        addItem(MowziesMobOrgans.ACTIVE_BLUFF_ROD, "活性泥峭棒");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 0, "允许食用泥土，恢复4点饥饿值");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 1, "每有一个泥峭棒增加，食用泥土时增加4点饱和度");

        // 龙类器官翻译
        addItem(IceAndFireOrgans.FIRE_DRAGON_HEART, "火龙心脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LUNG, "火龙肺脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPINE, "火龙脊柱");
        addItem(IceAndFireOrgans.FIRE_DRAGON_STOMACH, "火龙胃");
        addItem(IceAndFireOrgans.FIRE_DRAGON_INTESTINE, "火龙肠子");
        addItem(IceAndFireOrgans.FIRE_DRAGON_KIDNEY, "火龙肾脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_SPLEEN, "火龙脾脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_LIVER, "火龙肝脏");
        addItem(IceAndFireOrgans.FIRE_DRAGON_GEM, "火龙宝玉");
        addItem(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "火龙吐息袋");
        addOrganSkill(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC, "向前方喷射火焰");
        addItem(IceAndFireOrgans.FIRE_DRAGON_RIB, "火龙肋骨");
        addItem(IceAndFireOrgans.FIRE_DRAGON_MUSCLE, "火龙肌肉");

        addItem(IceAndFireOrgans.ICE_DRAGON_HEART, "冰龙心脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_LUNG, "冰龙肺脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPINE, "冰龙脊柱");
        addItem(IceAndFireOrgans.ICE_DRAGON_STOMACH, "冰龙胃");
        addItem(IceAndFireOrgans.ICE_DRAGON_INTESTINE, "冰龙肠子");
        addItem(IceAndFireOrgans.ICE_DRAGON_KIDNEY, "冰龙肾脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_SPLEEN, "冰龙脾脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_LIVER, "冰龙肝脏");
        addItem(IceAndFireOrgans.ICE_DRAGON_GEM, "冰龙宝玉");
        addItem(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "冰龙吐息袋");
        addOrganSkill(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC, "向前方喷射冰霜");
        addItem(IceAndFireOrgans.ICE_DRAGON_RIB, "冰龙肋骨");
        addItem(IceAndFireOrgans.ICE_DRAGON_MUSCLE, "冰龙肌肉");

        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_HEART, "电龙心脏");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG, "电龙肺脏");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE, "电龙脊柱");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH, "电龙胃");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE, "电龙肠子");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY, "电龙肾脏");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN, "电龙脾脏");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER, "电龙肝脏");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_GEM, "电龙宝玉");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "电龙吐息袋");
        addOrganSkill(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC, "向前方喷射闪电");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_RIB, "电龙肋骨");
        addItem(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE, "电龙肌肉");

        addItem(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "火焰王国战士之心");
        addItem(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "冰霜王国战士之心");
        addItem(FDBossesOrgans.MALKUTH, "王国");
        addItem(FDBossesOrgans.CHESED, "慈悲");
        addItem(FDBossesOrgans.GEBURAH, "严厉");

        addItem(WAICOrgans.DIVINE_CORE, "神圣核心");
        addItem(WAICOrgans.FROST_CORE, "冰霜核心");
        addItem(WAICOrgans.FLAME_CORE, "炽焰核心");
        addItem(WAICOrgans.NATURE_CORE, "自然核心");

        // 悚恐怖官翻译
        addItem(IceAndFireOrgans.BITTER_FLESH, "苦寒血肉");
        addItem(IceAndFireOrgans.ICE_SHARD, "冰魂残片");
        addItem(IceAndFireOrgans.FROSTBURN_SOUL, "冻结魂火");
        addItem(IceAndFireOrgans.DREAD_PHYLACTERY, "悚恐怖匣");
        addItem(IceAndFireOrgans.DREAD_RIB, "悚怖肋骨");
        addItem(IceAndFireOrgans.DREAD_SPINE, "悚怖脊柱");

        // 九头蛇器官翻译
        addItem(IceAndFireOrgans.HYDRA_HEART, "九头蛇心脏");
        addItem(IceAndFireOrgans.HYDRA_LUNG, "九头蛇肺脏");
        addOrganDescription(IceAndFireOrgans.HYDRA_LUNG, "消耗中毒效果释放九头蛇毒物吐息");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 0, "消耗玩家身上的中毒效果");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 1, "吐息持续时长 = log10(中毒时长) 秒");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 2, "每4 tick造成一次伤害（伤害 = 中毒等级 + 1）");
        addOrganSkill(IceAndFireOrgans.HYDRA_LUNG, 3, "被击中的敌人获得原始中毒效果");
        addItem(IceAndFireOrgans.HYDRA_SPINE, "九头蛇脊柱");
        addItem(IceAndFireOrgans.HYDRA_STOMACH, "九头蛇胃");
        addItem(IceAndFireOrgans.HYDRA_INTESTINE, "九头蛇肠子");
        addItem(IceAndFireOrgans.HYDRA_SPLEEN, "九头蛇脾脏");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 0, "血量低于50%时，将中毒效果转化为治疗");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 1, "每秒消耗中毒时长来治疗（治疗量 = 中毒等级 × 系数）");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 2, "系数：≤10%血量时为10，≤20%时为5，≤50%时为3");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPLEEN, 3, "1点治疗 = 消耗1 tick中毒时长");
        addItem(IceAndFireOrgans.HYDRA_RIB, "九头蛇肋骨");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 0, "受伤时，将自身5秒中毒效果转移给攻击者");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 1, "中毒效果可在攻击者身上叠加");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 2, "减少等同于中毒等级的伤害");
        addOrganSkill(IceAndFireOrgans.HYDRA_RIB, 3, "例如：中毒II减少2点受到的伤害");

        addItem(IceAndFireOrgans.HYDRA_MUSCLE, "九头蛇肌肉");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 0, "近战攻击时，将自身5秒中毒效果转移给目标");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 1, "中毒效果可在目标身上叠加");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 2, "造成等同于中毒等级的额外伤害");
        addOrganSkill(IceAndFireOrgans.HYDRA_MUSCLE, 3, "例如：中毒II造成2点额外伤害");

        // 九头蛇心脏
        addOrganDescription(IceAndFireOrgans.HYDRA_HEART, "将中毒转化为再生");
        addOrganSkill(IceAndFireOrgans.HYDRA_HEART, 0, "每10 tick将当前中毒效果转化为再生效果（持续1秒，等级相同）");
        addOrganSkill(IceAndFireOrgans.HYDRA_HEART, 1, "免疫中毒类型伤害");

        // 九头蛇脊柱
        addOrganDescription(IceAndFireOrgans.HYDRA_SPINE, "通过中毒欺骗死亡");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 0, "死亡时若有超过10秒的中毒效果，恢复至10%血量");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 1, "提升中毒等级并将剩余时长折半");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 2, "取消死亡事件");

        // 九头蛇胃
        addOrganDescription(IceAndFireOrgans.HYDRA_STOMACH, "将食物负面效果转化为中毒");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 0, "进食时，将有害食物效果转化为中毒效果");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 1, "中毒时长 = 有害效果时长 × 胃数量");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 2, "加上已有中毒时长，并取最高等级");

        // 九头蛇肠子
        addOrganDescription(IceAndFireOrgans.HYDRA_INTESTINE, "放大食物效果");
        addOrganSkill(IceAndFireOrgans.HYDRA_INTESTINE, 0, "每个器官增加50%的食物效果时长");

        // 幻想种器官翻译
        addItem(IceAndFireOrgans.FANTASTICAL_HEART, "幻想种心脏");
        addItem(IceAndFireOrgans.FANTASTICAL_LUNG, "幻想种肺脏");
        addItem(IceAndFireOrgans.FANTASTICAL_SPINE, "幻想种脊柱");
        addItem(IceAndFireOrgans.FANTASTICAL_STOMACH, "幻想种胃");
        addItem(IceAndFireOrgans.FANTASTICAL_INTESTINE, "幻想种肠子");
        addItem(IceAndFireOrgans.FANTASTICAL_KIDNEY, "幻想种肾脏");
        addItem(IceAndFireOrgans.FANTASTICAL_SPLEEN, "幻想种脾脏");
        addItem(IceAndFireOrgans.FANTASTICAL_LIVER, "幻想种肝脏");
        addItem(IceAndFireOrgans.FANTASTICAL_APPENDIX, "幻想种阑尾");
        addItem(IceAndFireOrgans.FANTASTICAL_RIB, "幻想种肋骨");
        addItem(IceAndFireOrgans.FANTASTICAL_MUSCLE, "幻想种肌肉");

        // 浮霜器官翻译
        addItem(AnvilCraftOrgans.FROST_METAL_HEART, "浮霜金属心脏");
        addItem(AnvilCraftOrgans.FROST_METAL_LUNG, "浮霜金属肺脏");
        addItem(AnvilCraftOrgans.FROST_METAL_SPINE, "浮霜金属脊柱");
        addItem(AnvilCraftOrgans.FROST_METAL_STOMACH, "浮霜金属胃");
        addItem(AnvilCraftOrgans.FROST_METAL_INTESTINE, "浮霜金属肠子");
        addItem(AnvilCraftOrgans.FROST_METAL_KIDNEY, "浮霜金属肾脏");
        addItem(AnvilCraftOrgans.FROST_METAL_SPLEEN, "浮霜金属脾脏");
        addItem(AnvilCraftOrgans.FROST_METAL_LIVER, "浮霜金属肝脏");
        addItem(AnvilCraftOrgans.FROST_METAL_APPENDIX, "浮霜金属阑尾");
        addItem(AnvilCraftOrgans.FROST_METAL_RIB, "浮霜金属肋骨");
        addItem(AnvilCraftOrgans.FROST_METAL_MUSCLE, "浮霜金属肌肉");

        // 浮霜器官skill描述
        add("organ.who_am_i_core.tooltips.frost_metal_merciless", "无情：将所有魔咒转换为器官属性");

        // 墨水器官翻译
        addItem(WAICOrgans.INK_HEART, "墨水心脏");
        addItem(WAICOrgans.INK_LUNG, "墨水肺脏");
        addItem(WAICOrgans.INK_SPINE, "墨水脊柱");
        addItem(WAICOrgans.INK_STOMACH, "墨水胃");
        addItem(WAICOrgans.INK_INTESTINE, "墨水肠子");
        addItem(WAICOrgans.INK_KIDNEY, "墨水肾脏");
        addItem(WAICOrgans.INK_SPLEEN, "墨水脾脏");
        addItem(WAICOrgans.INK_LIVER, "墨水肝脏");
        addItem(WAICOrgans.INK_APPENDIX, "墨水阑尾");
        addItem(WAICOrgans.INK_RIB, "墨水肋骨");
        addItem(WAICOrgans.INK_MUSCLE, "墨水肌肉");
        addItem(WAICOrgans.INK_BOTTLE, "墨水瓶");
        addItem(WAICOrgans.NIB, "钢笔尖");

        // 颜料器官翻译
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
        addItem(WAICOrgans.PALETTE, "调色盘");

        // 木质器官翻译
        addItem(WAICOrgans.WOODEN_HEART, "木质心脏");
        addItem(WAICOrgans.WOODEN_LUNG, "木质肺脏");
        addItem(WAICOrgans.WOODEN_STOMACH, "木质胃");
        addItem(WAICOrgans.WOODEN_INTESTINE, "木质肠子");
        addItem(WAICOrgans.WOODEN_KIDNEY, "木质肾脏");
        addItem(WAICOrgans.WOODEN_SPLEEN, "木质脾脏");
        addItem(WAICOrgans.WOODEN_LIVER, "木质肝脏");
        addItem(WAICOrgans.WOODEN_APPENDIX, "木质阑尾");
        addItem(WAICOrgans.WOODEN_MUSCLE, "木质肌肉");

        // 弗兰肯斯坦器官翻译
        addItem(WAICOrgans.FRANKENSTEIN_HEART, "弗兰肯斯坦心脏");
        addItem(WAICOrgans.FRANKENSTEIN_LUNG, "弗兰肯斯坦肺脏");
        addItem(WAICOrgans.FRANKENSTEIN_STOMACH, "弗兰肯斯坦胃");
        addItem(WAICOrgans.FRANKENSTEIN_INTESTINE, "弗兰肯斯坦肠子");
        addItem(WAICOrgans.FRANKENSTEIN_KIDNEY, "弗兰肯斯坦肾脏");
        addItem(WAICOrgans.FRANKENSTEIN_SPLEEN, "弗兰肯斯坦脾脏");
        addItem(WAICOrgans.FRANKENSTEIN_LIVER, "弗兰肯斯坦肝脏");
        addItem(WAICOrgans.FRANKENSTEIN_APPENDIX, "弗兰肯斯坦阑尾");
        addItem(WAICOrgans.FRANKENSTEIN_MUSCLE, "弗兰肯斯坦肌肉");

        // 肿瘤器官翻译
        addItem(WAICOrgans.TUMOR_HEART, "肿瘤心脏");
        addItem(WAICOrgans.TUMOR_LUNG, "肿瘤肺脏");
        addItem(WAICOrgans.TUMOR_STOMACH, "肿瘤胃");
        addItem(WAICOrgans.TUMOR_INTESTINE, "肿瘤肠子");
        addItem(WAICOrgans.TUMOR_KIDNEY, "肿瘤肾脏");
        addItem(WAICOrgans.TUMOR_SPLEEN, "肿瘤脾脏");
        addItem(WAICOrgans.TUMOR_LIVER, "肿瘤肝脏");
        addItem(WAICOrgans.TUMOR_APPENDIX, "肿瘤阑尾");
        addItem(WAICOrgans.TUMOR_MUSCLE, "肿瘤肌肉");

        // 九狱器官翻译
        addItem(WAICOrgans.LIMBO, "灵薄");
        addItem(WAICOrgans.LUST, "色欲");
        addItem(WAICOrgans.GLUTTONY, "暴食");
        addItem(WAICOrgans.GREED, "贪婪");
        addItem(WAICOrgans.WRATH, "愤怒");
        addItem(WAICOrgans.HERESY, "异端");
        addItem(WAICOrgans.VIOLENCE, "暴力");
        addItem(WAICOrgans.FRAUD, "欺诈");
        addItem(WAICOrgans.TREASON, "背叛");

        // 双子魔眼器官翻译
        addItem(WAICOrgans.STRANGE_EYEBALL, "奇怪的眼球");
        addItem(WAICOrgans.EERIE_EYEBALL, "诡异的眼球");
        addItem(WAICOrgans.STRANGE_MECHANICAL_EYEBALL, "奇怪的机械眼球");
        addItem(WAICOrgans.EERIE_MECHANICAL_EYEBALL, "诡异的机械眼球");

        // 拟态器官翻译
        addItem(WAICOrgans.MIMIC_HEART, "拟态心脏");
        addItem(WAICOrgans.MIMIC_LIVER, "拟态肝脏");
        addItem(WAICOrgans.MIMIC_LUNG, "拟态肺脏");

        // 单个器官翻译
        addItem(WAICOrgans.HAUNTED_BONE, "闹鬼的骨头");
        addOrganDescription(WAICOrgans.HAUNTED_BONE, "嘟嘟哒嘟嘟哒");
        addItem(WAICOrgans.SWORD_BONE, "剑骨头");
        addOrganDescription(WAICOrgans.SWORD_BONE, "剑一般锋利");
        addItem(WAICOrgans.STRAIGHT_INTESTINE, "直肠子");
        addItem(WAICOrgans.SQUASH, "窝瓜");

        addAttribute(WAICAttribute.TEMPERATURE, "温度");
        addAttribute(WAICAttribute.BLOCK, "格挡");
        addAttribute(WAICAttribute.COUNTER_ATTACK, "反击");
        addAttribute(WAICAttribute.HEAL, "治疗");
        addAttribute(WAICAttribute.MELEE_DAMAGE, "近战伤害");
        addAttribute(WAICAttribute.RANGED_DAMAGE, "远程伤害");
        addAttribute(WAICAttribute.MAGIC_DAMAGE, "魔法伤害");
        addAttribute(WAICAttribute.MELEE_DAMAGE_PERCENTAGE, "近战伤害百分比");
        addAttribute(WAICAttribute.RANGED_DAMAGE_PERCENTAGE, "远程伤害百分比");
        addAttribute(WAICAttribute.MAGIC_DAMAGE_PERCENTAGE, "魔法伤害百分比");

        // 伤害类型标签翻译
        add(WAICDamageTagManager.IS_MELEE, "近战");

        // 物品标签翻译
        add(WAICItemTagManager.MAGIC, "魔法");
        add(WAICItemTagManager.MECHANICAL, "机械");
        add(WAICItemTagManager.SUMMON, "召唤");
        add(WAICItemTagManager.UNIQUE, "唯一");
        add(WAICItemTagManager.FIRE_DRAGON, "火龙");
        add(WAICItemTagManager.ICE_DRAGON, "冰龙");
        add(WAICItemTagManager.LIGHTNING_DRAGON, "电龙");
    }

    private void addOrganSkill(Supplier<Item> item, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".skill", value);
    }

    private void addOrganSkill(Supplier<Item> item, int index, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".skill." + index, value);
    }

    private void addOrganDescription(Supplier<Item> item, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".description", value);
    }

    private void addOrganDescription(Supplier<Item> item, int index, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".description." + index, value);
    }

    private void addAttribute(Holder<Attribute> attribute, String value) {
        add(attribute.value().getDescriptionId(), value);
    }

    @Override
    protected void addTranslations() {
        switch (locale) {
            case EN_US -> English();
            case ZH_CN -> Chinese();
        }
    }
}

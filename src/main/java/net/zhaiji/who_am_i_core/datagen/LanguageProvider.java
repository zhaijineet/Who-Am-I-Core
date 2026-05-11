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
        addOrganSkill(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "Immune to fire attacks when fighting Malkuth");
        addItem(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "Ice Malkuth Warrior Heart");
        addOrganSkill(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "Immune to ice attacks when fighting Malkuth");
        addItem(FDBossesOrgans.MALKUTH, "Malkuth");
        addOrganSkill(FDBossesOrgans.MALKUTH, "Global temperature is always 0; local temperature uses the full positive/negative temperature from all organs");
        addItem(FDBossesOrgans.CHESED, "Chesed");
        addOrganSkill(FDBossesOrgans.CHESED, "Attacks summon a lightning ray that tracks the target, dealing weapon damage and applying Shocked. Cooldown: 1s");
        addItem(FDBossesOrgans.GEBURAH, "Geburah");
        addOrganSkill(FDBossesOrgans.GEBURAH, "Attacks deal bonus damage equal to 3% max HP per harmful effect on the target");

        // 利维坦器官翻译
        addItem(CataclysmOrgans.LEVIATHAN_HEART, "Leviathan Heart");
        addItem(CataclysmOrgans.LEVIATHAN_MUSCLE, "Leviathan Muscle");
        addItem(CataclysmOrgans.LEVIATHAN_INTESTINE, "Leviathan Intestine");
        addItem(CataclysmOrgans.LEVIATHAN_STOMACH, "Leviathan Stomach");
        addItem(CataclysmOrgans.LEVIATHAN_GILL, "Leviathan Gill");
        addItem(CataclysmOrgans.LEVIATHAN_SPINE, "Leviathan Spine");
        addItem(CataclysmOrgans.LEVIATHAN_FISHBONE, "Leviathan Fishbone");

        // Cataclysm 咒骸器官翻译
        addItem(CataclysmOrgans.APTRGANGR_SPINE, "Aptrgangr Spine");
        addItem(CataclysmOrgans.APTRGANGR_RIB, "Aptrgangr Rib");

        // Cataclysm 咒翼灵骸器官翻译
        addItem(CataclysmOrgans.MALEDICTUS_SPINE, "Maledictus Spine");
        addItem(CataclysmOrgans.MALEDICTUS_RIB, "Maledictus Rib");
        addItem(CataclysmOrgans.PHANTOM_HEART, "Phantom Heart");
        addOrganDescription(CataclysmOrgans.PHANTOM_HEART, "A cursed heart of the Maledictus");
        addOrganSkill(CataclysmOrgans.PHANTOM_HEART, 1, "While sprinting, all damage dealt is increased by 25%");
        addItem(CataclysmOrgans.PHANTOM_SHARD, "Phantom Shard");
        addItem(CataclysmOrgans.SEALING_STONE_SLAB, "Sealing Stone Slab");
        addOrganSkill(CataclysmOrgans.SEALING_STONE_SLAB, 0, "Summons 5 phantom halberds in a fan shape, each dealing 12 magic damage");

        // Cataclysm 斯库拉器官翻译
        addItem(CataclysmOrgans.TIDAL_LANTERN, "Tidal Lantern");
        addOrganDescription(CataclysmOrgans.TIDAL_LANTERN, "Consume all phlegm on attack");
        addOrganSkill(CataclysmOrgans.TIDAL_LANTERN, 0, "Consumes all current phlegm and adds equal damage");
        addOrganSkill(CataclysmOrgans.TIDAL_LANTERN, 1, "When consumed phlegm >= 30, summons water waves with damage equal to this attack");
        addItem(CataclysmOrgans.STORM_SPINE, "Storm Spine");
        addOrganDescription(CataclysmOrgans.STORM_SPINE, "Absorbs damage into phlegm");
        addOrganSkill(CataclysmOrgans.STORM_SPINE, 0, "When hit, absorbs 20% of damage as phlegm (also reduces damage by 20%), capped at 10 per hit");
        addOrganSkill(CataclysmOrgans.STORM_SPINE, 1, "Becomes inactive when phlegm is full");
        addItem(CataclysmOrgans.STORM_RIB, "Storm Rib");
        addOrganSkill(CataclysmOrgans.STORM_RIB, "+10 phlegm capacity");

        // ==================== 焰魔器官翻译 ====================
        addItem(CataclysmOrgans.UNDYING_EMBER, "Undying Ember");
        addOrganDescription(CataclysmOrgans.UNDYING_EMBER, "Grants Strength based on √temperature");
        addItem(CataclysmOrgans.IGNITED_RIB_PLATING, "Ignited Rib Plating");
        addOrganDescription(CataclysmOrgans.IGNITED_RIB_PLATING, "Grants Block based on √local temperature");
        addItem(CataclysmOrgans.BLAZING_VISAGE, "Blazing Visage");
        addOrganDescription(CataclysmOrgans.BLAZING_VISAGE, "Applies Blazing Brand on melee hit. Heals based on local temperature.");
        addOrganSkill(CataclysmOrgans.BLAZING_VISAGE, 0, "Melee attacks apply Blazing Brand (-20% armor, -20% toughness)");
        addOrganSkill(CataclysmOrgans.BLAZING_VISAGE, 1, "Heal Value = 2 + local temperature x 0.5. Doubled if target already has Blazing Brand");

        // ==================== 下界合金巨兽器官翻译 ====================
        addItem(CataclysmOrgans.MONSTROSITY_CORE, "Monstrosity Core");
        addOrganDescription(CataclysmOrgans.MONSTROSITY_CORE, "Generates yellow bile from heat. +100 yellow bile capacity.");
        addItem(CataclysmOrgans.MONSTROSITY_CIRCUIT, "Monstrosity Circuit");
        addOrganSkill(CataclysmOrgans.MONSTROSITY_CIRCUIT, "Consumes all yellow bile: AoE earthquake deals (bile + 5% max HP + temp x 2) damage");
        addItem(CataclysmOrgans.MONSTROSITY_FURNACE, "Monstrosity Furnace");
        addOrganDescription(CataclysmOrgans.MONSTROSITY_FURNACE, "Allows drinking lava. Restores hunger, yellow bile, and grants Monstrous effect.");

        // Cataclysm 远古工厂器官翻译
        addItem(CataclysmOrgans.TACTICAL_DISK, "Tactical Disk");
        addOrganDescription(CataclysmOrgans.TACTICAL_DISK, "Stores combat logs from factory components. More data means better system diagnostics.");
        addItem(CataclysmOrgans.REINFORCED_FRAME, "Reinforced Frame");
        addOrganDescription(CataclysmOrgans.REINFORCED_FRAME, "Standard factory structural component. Rigid, stable, unmovable.");
        addItem(CataclysmOrgans.POWER_CELL, "Power Cell");
        addOrganDescription(CataclysmOrgans.POWER_CELL, "Standard factory power module. Slowly releases repair current.");
        addItem(CataclysmOrgans.COMPUTE_CHIP, "Compute Chip");
        addOrganDescription(CataclysmOrgans.COMPUTE_CHIP, "Co-processor. Each additional factory part opens a new data channel.");
        addItem(CataclysmOrgans.MECHANICAL_STAR, "Mechanical Star");
        addOrganSkill(CataclysmOrgans.MECHANICAL_STAR, 0, "Lock onto an enemy in sight and fire a homing missile (8.0 damage). 8 second cooldown.");
        addItem(CataclysmOrgans.DEATH_LENS, "Death Lens");
        addOrganSkill(CataclysmOrgans.DEATH_LENS, 0, "Fire a death laser (6 damage + 6%% target max HP). No fire or block damage. 15 second cooldown.");

        // Cataclysm 末影守卫器官翻译
        addItem(CataclysmOrgans.GUARDIAN_STONE, "Guardian Stone");
        addItem(CataclysmOrgans.VOID_CRYSTAL_SPINE, "Void Crystal Spine");
        addOrganDescription(CataclysmOrgans.VOID_CRYSTAL_SPINE, "The Ender Guardian's spinal power crystal, still pulsing with residual void energy.");
        addOrganSkill(CataclysmOrgans.VOID_CRYSTAL_SPINE, 0, "Summons 3 rings of void runes around you (inner 6, middle 11, outer 14). 15 second cooldown.");

        // IronSpell 亡魂器官翻译
        addItem(IronSpellOrgans.NECROMANCER_SPINE, "Necromancer Spine");
        addItem(IronSpellOrgans.NECROMANCER_RIB, "Necromancer Rib");

        // IronSpell 提洛斯回响·原初受火者器官翻译
        addItem(IronSpellOrgans.PRIMORDIAL_FLAME, "Primordial Flame");
        addOrganDescription(IronSpellOrgans.PRIMORDIAL_FLAME, "The first spark that kindled the primeval fire");
        addOrganSkill(IronSpellOrgans.PRIMORDIAL_FLAME, 0, "Fire spells gain +1 spell level");

        // IronSpell 高位唤魔者器官翻译
        addItem(IronSpellOrgans.EMERALD_SKULL, "Emerald Skull");
        addOrganDescription(IronSpellOrgans.EMERALD_SKULL, "The cursed skull of an arch-evoker, pulsing with emerald magic");
        addOrganSkill(IronSpellOrgans.EMERALD_SKULL, 0, "Evocation spells gain +1 spell level");

        // IronSpell 死者之王器官翻译
        addItem(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "Corrupted Soul Lantern");
        addOrganDescription(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "Harvests souls from killed entities into black bile");
        addOrganSkill(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "Soul Harvest: gain black bile when nearby entities die");

        addItem(IronSpellOrgans.DEAD_KING_SPINE, "Dead King Spine");
        addOrganDescription(IronSpellOrgans.DEAD_KING_SPINE, "Absorbs damage with black bile");
        addOrganSkill(IronSpellOrgans.DEAD_KING_SPINE, "Consumes black bile equal to damage absorbed (up to 50%)");

        addItem(IronSpellOrgans.DEAD_KING_RIB, "Dead King Rib");
        addOrganDescription(IronSpellOrgans.DEAD_KING_RIB, "Increases black bile capacity");
        addOrganSkill(IronSpellOrgans.DEAD_KING_RIB, "+10 max black bile");

        addItem(WAICOrgans.DIVINE_CORE, "Divine Core");
        addItem(WAICOrgans.FROST_CORE, "Frost Core");
        addItem(WAICOrgans.FLAME_CORE, "Flame Core");
        addItem(WAICOrgans.NATURE_CORE, "Nature Core");

        // ==================== Companions 神圣教宗器官翻译 ====================
        addItem(CompanionsOrgans.PONTIFF_HEART, "Pontiff Heart");
        addOrganDescription(CompanionsOrgans.PONTIFF_HEART, "Holy Transformation: when HP drops below 30%, heals 50% max HP and grants Strength II, Resistance II, Speed II for 15s. 3min cooldown");

        addItem(CompanionsOrgans.PONTIFF_LUNG, "Pontiff Lung");
        addItem(CompanionsOrgans.PONTIFF_STOMACH, "Pontiff Stomach");
        addItem(CompanionsOrgans.PONTIFF_INTESTINE, "Pontiff Intestine");
        addItem(CompanionsOrgans.PONTIFF_KIDNEY, "Pontiff Kidney");

        addItem(CompanionsOrgans.PONTIFF_SPLEEN, "Pontiff Spleen");
        addOrganSkill(CompanionsOrgans.PONTIFF_SPLEEN, "Releases expanding fire rings. Layers = min(1 + Pontiff organs / 4, 3). 10s cooldown");

        addItem(CompanionsOrgans.PONTIFF_LIVER, "Pontiff Liver");

        addItem(CompanionsOrgans.PONTIFF_APPENDIX, "Pontiff Appendix");
        addOrganSkill(CompanionsOrgans.PONTIFF_APPENDIX, "Launches a tracking star. Positive temp = Red (fire), Negative = Blue (freeze). 8s cooldown");

        addItem(CompanionsOrgans.PONTIFF_MUSCLE, "Pontiff Muscle");
        addOrganSkill(CompanionsOrgans.PONTIFF_MUSCLE, "Knockback Resistance +8%");

        // ==================== Companions 蛋糕器官翻译 ====================
        addItem(CompanionsOrgans.CAKE_HEART, "Cake Heart");
        addItem(CompanionsOrgans.CAKE_LUNG, "Cake Lung");
        addItem(CompanionsOrgans.CAKE_STOMACH, "Cake Stomach");
        addOrganSkill(CompanionsOrgans.CAKE_STOMACH, "Eating food grants Sweetness effect. Stacks and resets duration on each eat");
        addItem(CompanionsOrgans.CAKE_LIVER, "Cake Liver");
        addOrganSkill(CompanionsOrgans.CAKE_LIVER, "Sweetness level >= 2: consumes 1 level per second to remove 1 random harmful effect");

        // 悚恐怖官翻译
        addItem(IceAndFireOrgans.BITTER_FLESH, "Bitter Flesh");
        addItem(IceAndFireOrgans.ICE_SHARD, "Ice Shard");
        addItem(IceAndFireOrgans.FROSTBURN_SOUL, "Frostburn Soul");
        addItem(IceAndFireOrgans.DREAD_PHYLACTERY, "Dread Phylactery");
        addItem(IceAndFireOrgans.DREAD_RIB, "Dread Rib");
        addItem(IceAndFireOrgans.DREAD_SPINE, "Dread Spine");

        // 冰魂残片效果描述
        addOrganDescription(IceAndFireOrgans.ICE_SHARD, "Global temperature × -0.05 → bonus health (colder = more health)");
        // 冻结魂火效果描述
        addOrganDescription(IceAndFireOrgans.FROSTBURN_SOUL, "Global temperature × -0.15 → bonus health (colder = more health)");
        // 悚恐怖匣效果描述
        addOrganDescription(IceAndFireOrgans.DREAD_PHYLACTERY, "Global temperature × -0.25 → bonus health (colder = more health)");
        addOrganSkill(IceAndFireOrgans.DREAD_PHYLACTERY, "Converts target's Slowness into freeze damage");
        // 悚怖脊柱技能描述
        addOrganSkill(IceAndFireOrgans.DREAD_SPINE, "On attack, applies Slowness based on local temperature (3 seconds)");

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
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 0, "On death with >10s of poison, recover to 10% HP");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 1, "Increases poison level and halves remaining duration");
        addOrganSkill(IceAndFireOrgans.HYDRA_SPINE, 2, "Cancels death event");

        // 九头蛇胃
        addOrganDescription(IceAndFireOrgans.HYDRA_STOMACH, "Converts food negative effects into poison");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 0, "When eating, converts harmful food effects to poison");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 1, "Duration = harmful effects duration × stomach count");
        addOrganSkill(IceAndFireOrgans.HYDRA_STOMACH, 2, "Adds existing poison duration and takes max amplifier");

        // 九头蛇肠子
        addOrganDescription(IceAndFireOrgans.HYDRA_INTESTINE, "Amplifies food effects");
        addOrganSkill(IceAndFireOrgans.HYDRA_INTESTINE, "Increases duration of food effects by 50% per organ");

        // 幻想种器官翻译
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

        // 布织泰迪熊器官翻译
        addItem(WAICOrgans.CLOTH_TEDDY_BEAR, "Cloth Teddy Bear");
        addOrganDescription(WAICOrgans.CLOTH_TEDDY_BEAR, "When chest cavity closes, converts wool items in the chest cavity into random cloth organs");
        addOrganSkill(WAICOrgans.CLOTH_TEDDY_BEAR, "Mending: consume wool to restore health");
        addItem(WAICOrgans.CLOTH_HEART, "Cloth Heart");
        addItem(WAICOrgans.CLOTH_LUNG, "Cloth Lung");
        addItem(WAICOrgans.CLOTH_LIVER, "Cloth Liver");
        addItem(WAICOrgans.CLOTH_INTESTINE, "Cloth Intestine");
        addItem(WAICOrgans.CLOTH_STOMACH, "Cloth Stomach");
        addItem(WAICOrgans.CLOTH_KIDNEY, "Cloth Kidney");
        addItem(WAICOrgans.CLOTH_SPLEEN, "Cloth Spleen");
        addItem(WAICOrgans.CLOTH_SPINE, "Cloth Spine");
        addItem(WAICOrgans.CLOTH_RIB, "Cloth Rib");
        addItem(WAICOrgans.CLOTH_MUSCLE, "Cloth Muscle");
        addItem(WAICOrgans.CLOTH_APPENDIX, "Cloth Appendix");

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

        // 浮霜器官description描述
        add(AnvilCraftOrgans.MERCILESS_TRANSLATION, "Merciless: convert all enchantments into organ attributes");

        // 超限合金器官翻译
        addItem(AnvilCraftOrgans.TRANSCENDIUM_HEART, "Transcendium Heart");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LUNG, "Transcendium Lung");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPINE, "Transcendium Spine");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, "Transcendium Stomach");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, "Transcendium Intestine");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, "Transcendium Kidney");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, "Transcendium Spleen");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LIVER, "Transcendium Liver");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, "Transcendium Appendix");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_RIB, "Transcendium Rib");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, "Transcendium Muscle");

        // 超限合金器官description描述
        add("organ.who_am_i_core.tooltips.looting_burst", "Loot Burst: +1 Looting and +1 Fortune");

        // 诅咒金器官翻译
        addItem(AnvilCraftOrgans.CURSED_GOLD_HEART, "Cursed Gold Heart");
        addItem(AnvilCraftOrgans.CURSED_GOLD_LUNG, "Cursed Gold Lung");
        addItem(AnvilCraftOrgans.CURSED_GOLD_LIVER, "Cursed Gold Liver");
        addItem(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, "Cursed Gold Intestine");
        add(AnvilCraftOrgans.CURSED_GOLD_TRANSLATION, "Curse: the more cursed gold organs in your chest, the heavier the penalty");

        // 余烬金属器官翻译
        addItem(AnvilCraftOrgans.EMBER_METAL_RIB, "Ember Metal Rib");
        addItem(AnvilCraftOrgans.EMBER_METAL_MUSCLE, "Ember Metal Muscle");
        addOrganSkill(AnvilCraftOrgans.EMBER_METAL_MUSCLE, "Ember Forging: +0.5 Melee Damage");
        addItem(AnvilCraftOrgans.EMBER_METAL_SPINE, "Ember Metal Spine");
        addItem(AnvilCraftOrgans.EMBER_METAL_APPENDIX, "Ember Metal Appendix");
        add(AnvilCraftOrgans.EMBER_ABSORPTION_TRANSLATION, "Ember Absorption: fire damage heals instead of hurting");

        // 皇家钢器官翻译
        addItem(AnvilCraftOrgans.ROYAL_STEEL_RIB, "Royal Steel Rib");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_MUSCLE, "Royal Steel Muscle");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_SPINE, "Royal Steel Spine");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_APPENDIX, "Royal Steel Appendix");
        add(AnvilCraftOrgans.ROYAL_STEEL_TRANSLATION, "Solid Foundation: simple fixed attribute bonuses");

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
        addOrganSkill(WAICOrgans.INK_APPENDIX, "Consumes ink to restore equivalent mana");
        addItem(WAICOrgans.INK_RIB, "Ink Rib");
        addItem(WAICOrgans.INK_MUSCLE, "Ink Muscle");
        addOrganSkill(WAICOrgans.INK_MUSCLE, "Adds ink equal to damage taken to ink bottle");
        addItem(WAICOrgans.INK_BOTTLE, "Ink Bottle");
        add(WAICOrgans.INK_BOTTLE_INK_TRANSLATION, "Ink: %s/1000");
        addItem(WAICOrgans.NIB, "Nib");
        addOrganDescription(WAICOrgans.NIB, "Consumes ink (5×level) to boost spell level by 1 on cast (triggers first)");

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
        add(WAICOrgans.PALETTE_DYE_TRANSLATION, "%s: %s");

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
        addOrganDescription(WAICOrgans.FRANKENSTEIN_HEART, "A stitched-together heart. Inherits attributes from stored hearts.");
        addItem(WAICOrgans.FRANKENSTEIN_LUNG, "Frankenstein Lung");
        addItem(WAICOrgans.FRANKENSTEIN_STOMACH, "Frankenstein Stomach");
        addItem(WAICOrgans.FRANKENSTEIN_INTESTINE, "Frankenstein Intestine");
        addItem(WAICOrgans.FRANKENSTEIN_KIDNEY, "Frankenstein Kidney");
        addItem(WAICOrgans.FRANKENSTEIN_SPLEEN, "Frankenstein Spleen");
        addItem(WAICOrgans.FRANKENSTEIN_LIVER, "Frankenstein Liver");
        addItem(WAICOrgans.FRANKENSTEIN_APPENDIX, "Frankenstein Appendix");
        addItem(WAICOrgans.FRANKENSTEIN_MUSCLE, "Frankenstein Muscle");

        // 病变器官翻译
        addItem(WAICOrgans.LESION_HEART, "Lesion Heart");
        addOrganDescription(WAICOrgans.LESION_HEART, "A festering heart that thrives on corruption");
        addOrganSkill(WAICOrgans.LESION_HEART, "Spread all your effects to living entities within 10 blocks");
        addItem(WAICOrgans.LESION_LUNG, "Lesion Lung");
        addItem(WAICOrgans.LESION_STOMACH, "Lesion Stomach");
        addItem(WAICOrgans.LESION_INTESTINE, "Lesion Intestine");
        addItem(WAICOrgans.LESION_KIDNEY, "Lesion Kidney");
        addItem(WAICOrgans.LESION_SPLEEN, "Lesion Spleen");
        addItem(WAICOrgans.LESION_LIVER, "Lesion Liver");
        addItem(WAICOrgans.LESION_APPENDIX, "Lesion Appendix");
        addItem(WAICOrgans.LESION_MUSCLE, "Lesion Muscle");
        addOrganSkill(WAICOrgans.LESION_MUSCLE, 0, "+1 Strength and +1 Speed per harmful effect on yourself");
        addOrganSkill(WAICOrgans.LESION_MUSCLE, 1, "+bonus damage equal to the sum of (amplifier + 1) of all harmful effects on the target");

        // 九狱器官翻译
        addItem(WAICOrgans.LIMBO, "Limbo");
        addOrganSkill(WAICOrgans.LIMBO, 0, "Sin 1: Gain 1 XP/sec");
        addOrganSkill(WAICOrgans.LIMBO, 1, "Sin 2: Gain 3 XP/sec");
        addOrganSkill(WAICOrgans.LIMBO, 2, "Sin 3: Gain 5 XP/sec");
        addItem(WAICOrgans.LUST, "Lust");
        addOrganSkill(WAICOrgans.LUST, 0, "Sin 1: Attacks heal 10% of damage dealt");
        addOrganSkill(WAICOrgans.LUST, 1, "Sin 2: Attacks heal 20% of damage dealt");
        addOrganSkill(WAICOrgans.LUST, 2, "Sin 3: Attacks heal 30% of damage dealt");
        addItem(WAICOrgans.GLUTTONY, "Gluttony");
        addOrganSkill(WAICOrgans.GLUTTONY, 0, "Can eat any food item");
        addOrganSkill(WAICOrgans.GLUTTONY, 1, "Eating grants absorption hearts equal to hunger value × N (cap N×20)");
        addOrganSkill(WAICOrgans.GLUTTONY, 2, "Eating additionally heals N HP");
        addItem(WAICOrgans.GREED, "Greed");
        addOrganSkill(WAICOrgans.GREED, 0, "Sin 1: +1 Looting, +1 Fortune");
        addOrganSkill(WAICOrgans.GREED, 1, "Sin 2: +2 Looting, +2 Fortune");
        addOrganSkill(WAICOrgans.GREED, 2, "Sin 3: +3 Looting, +3 Fortune");
        addItem(WAICOrgans.WRATH, "Wrath");
        addOrganSkill(WAICOrgans.WRATH, 0, "Sin 1: +1 Strength, +1 Speed");
        addOrganSkill(WAICOrgans.WRATH, 1, "Sin 2: +2 Strength, +2 Speed");
        addOrganSkill(WAICOrgans.WRATH, 2, "Sin 3: +3 Strength, +3 Speed");
        addItem(WAICOrgans.HERESY, "Heresy");
        addOrganSkill(WAICOrgans.HERESY, 0, "Sin 1: Potion duration +50%");
        addOrganSkill(WAICOrgans.HERESY, 1, "Sin 2: Potion duration +100%");
        addOrganSkill(WAICOrgans.HERESY, 2, "Sin 3: Potion amplifier +1");
        addItem(WAICOrgans.VIOLENCE, "Violence");
        addOrganSkill(WAICOrgans.VIOLENCE, 0, "Sin 1: Critical damage ×2");
        addOrganSkill(WAICOrgans.VIOLENCE, 1, "Sin 2: Critical damage ×2");
        addOrganSkill(WAICOrgans.VIOLENCE, 2, "Sin 3: Attacks always crit");
        addItem(WAICOrgans.FRAUD, "Fraud");
        addOrganSkill(WAICOrgans.FRAUD, 0, "Sin 1: Villager trade bonus experience");
        addOrganSkill(WAICOrgans.FRAUD, 1, "Sin 2: Villager trade discount");
        addOrganSkill(WAICOrgans.FRAUD, 2, "Sin 3: Villager trades never run out of stock");
        addItem(WAICOrgans.TREACHERY, "Treachery");
        addOrganSkill(WAICOrgans.TREACHERY, 0, "Sin 1: Attacks deal 1% of target max health");
        addOrganSkill(WAICOrgans.TREACHERY, 1, "Sin 2: Attacks deal 3% of target max health");
        addOrganSkill(WAICOrgans.TREACHERY, 2, "Sin 3: Attacks deal 5% of target max health");

        // 双子魔眼器官翻译
        addItem(WAICOrgans.STRANGE_EYEBALL, "Strange Eyeball");
        addItem(WAICOrgans.EERIE_EYEBALL, "Eerie Eyeball");
        addItem(WAICOrgans.STRANGE_MECHANICAL_EYEBALL, "Strange Mechanical Eyeball");
        addItem(WAICOrgans.EERIE_MECHANICAL_EYEBALL, "Eerie Mechanical Eyeball");

        // 拟态器官翻译
        addItem(WAICOrgans.MIMIC_HEART, "Mimic Heart");
        addOrganSkill(WAICOrgans.MIMIC_HEART, "Mimicry: Health regeneration +50%");
        addItem(WAICOrgans.MIMIC_LIVER, "Mimic Liver");
        addOrganSkill(WAICOrgans.MIMIC_LIVER, "Mimicry: Health regeneration +50%");
        addItem(WAICOrgans.MIMIC_LUNG, "Mimic Lung");
        addOrganSkill(WAICOrgans.MIMIC_LUNG, "Mimicry: Health regeneration +50%");

        // 单个器官翻译
        addItem(WAICOrgans.HAUNTED_BONE, "Haunted Bone");
        addOrganDescription(WAICOrgans.HAUNTED_BONE, "Dududa dududa");
        addItem(WAICOrgans.SWORD_BONE, "Sword Bone");
        addOrganDescription(WAICOrgans.SWORD_BONE, "As sharp as a sword");
        addItem(WAICOrgans.STRAIGHT_INTESTINE, "Straight Intestine");
        addOrganDescription(WAICOrgans.STRAIGHT_INTESTINE, "30% chance to drop consumed food after 3 seconds");
        addItem(WAICOrgans.SQUASH, "Squash");
        addItem(WAICOrgans.EXPERIENCE_HEART, "Experience Heart");
        addOrganDescription(WAICOrgans.EXPERIENCE_HEART, "A heart pulsating with accumulated experience");
        addOrganDescription(WAICOrgans.EXPERIENCE_HEART, 0, "Every 10 experience levels grant +1 health");
        addOrganDescription(WAICOrgans.EXPERIENCE_HEART, 1, "Experience from orbs × (magic organs + 1)");

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
        addAttribute(WAICAttribute.LOOTING, "Looting");
        addAttribute(WAICAttribute.FORTUNE, "Fortune");

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

        // 龙血药剂翻译
        addItem(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION, "Fire Dragon Blood Preparation");
        addItem(WAICItem.ICE_DRAGON_BLOOD_PREPARATION, "Ice Dragon Blood Preparation");
        addItem(WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION, "Lightning Dragon Blood Preparation");
        addItem(WAICItem.DRAGON_BLOOD_PREPARATION_GROUP, "Dragon Blood Preparation");

        // 龙之力效果翻译
        addEffect(WAICEffect.FIRE_DRAGON_POWER::value, "Fire Dragon Power");
        addEffect(WAICEffect.ICE_DRAGON_POWER::value, "Ice Dragon Power");
        addEffect(WAICEffect.LIGHTNING_DRAGON_POWER::value, "Lightning Dragon Power");
        addEffect(WAICEffect.DRAGON_POWER::value, "Dragon Power");

        // 甜蜜效果翻译
        addEffect(WAICEffect.SWEETNESS::value, "Sweetness");

        // 超频效果翻译
        addEffect(WAICEffect.OVERLOAD::value, "Overload");

        // 电磁义体器官翻译
        addItem(WAICOrgans.COMPUTING_CORE, "Computing Core");
        addOrganDescription(WAICOrgans.COMPUTING_CORE, "Central processor managing charge regeneration");
        addOrganSkill(WAICOrgans.COMPUTING_CORE, 0, "Signal Regen: +1 charge/tick (capped at max). Paused during Overload");
        addItem(WAICOrgans.CURRENT_RIB, "Current Rib");
        addOrganDescription(WAICOrgans.CURRENT_RIB, "Diverts current to shield against damage");
        addOrganSkill(WAICOrgans.CURRENT_RIB, 0, "Parallel: symmetric Current Rib halves shield cost");
        addOrganSkill(WAICOrgans.CURRENT_RIB, 1, "Shield: 10 charge per 1 damage blocked, max 4 (8 in Overload)");
        addItem(WAICOrgans.CHARGED_MUSCLE, "Charged Muscle");
        addOrganDescription(WAICOrgans.CHARGED_MUSCLE, "Cylindrical capacitor optimizing energy efficiency");
        addOrganSkill(WAICOrgans.CHARGED_MUSCLE, 0, "Circuit Return: 25% chance to refund consumed charge (50% in Overload)");
        addOrganSkill(WAICOrgans.CHARGED_MUSCLE, 1, "Residual Recovery: 10% of lost charge converted to healing (20% in Overload)");
        addOrganSkill(WAICOrgans.CHARGED_MUSCLE, 2, "Current Push: Generates 1 charge per tick while sprinting");
        addItem(WAICOrgans.CONDUCTIVE_SPINE, "Conductive Spine");
        addOrganDescription(WAICOrgans.CONDUCTIVE_SPINE, "Signal channel triggering Overload mode");
        addOrganSkill(WAICOrgans.CONDUCTIVE_SPINE, "Overload: consume maxCharge/2 to enter 10s Overload mode. Cooldown 20s");
        addItem(WAICOrgans.ENERGY_MODULE, "Energy Module");
        addOrganDescription(WAICOrgans.ENERGY_MODULE, "Physical battery storing all charge. +500 capacity each, allows 50% overload");
        add(WAICOrgans.ENERGY_MODULE_CHARGE_TRANSLATION, "Charge: %s/%s");
    }

    public void Chinese() {
        add(WAICCreativeModeTab.WHO_AM_I_CORE_TAB_TRANSLATABLE, "我非我");

        addItem(WAICItem.PETITE_CHEST_OPENER, "娇小开胸器");

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
        addOrganSkill(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART, "对战王国时，免疫火焰攻击");
        addItem(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "冰霜王国战士之心");
        addOrganSkill(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART, "对战王国时，免疫冰霜攻击");
        addItem(FDBossesOrgans.MALKUTH, "王国");
        addOrganSkill(FDBossesOrgans.MALKUTH, "全局温度始终为0；获取局部温度时，使用所有器官的全局正/负温度");
        addItem(FDBossesOrgans.CHESED, "慈悲");
        addOrganSkill(FDBossesOrgans.CHESED, "攻击时召唤闪电射线追踪目标，造成武器伤害并施加感电效果。冷却：1秒");
        addItem(FDBossesOrgans.GEBURAH, "严厉");
        addOrganSkill(FDBossesOrgans.GEBURAH, "攻击时，对目标每个负面效果额外造成其最大生命值3%的伤害");

        // 利维坦器官翻译
        addItem(CataclysmOrgans.LEVIATHAN_HEART, "利维坦心脏");
        addItem(CataclysmOrgans.LEVIATHAN_MUSCLE, "利维坦肌肉");
        addItem(CataclysmOrgans.LEVIATHAN_INTESTINE, "利维坦肠子");
        addItem(CataclysmOrgans.LEVIATHAN_STOMACH, "利维坦胃");
        addItem(CataclysmOrgans.LEVIATHAN_GILL, "利维坦鳃");
        addItem(CataclysmOrgans.LEVIATHAN_SPINE, "利维坦脊柱");
        addItem(CataclysmOrgans.LEVIATHAN_FISHBONE, "利维坦鱼骨");

        // 咒骸器官翻译
        addItem(CataclysmOrgans.APTRGANGR_SPINE, "咒骸脊柱");
        addItem(CataclysmOrgans.APTRGANGR_RIB, "咒骸肋骨");

        // 咒翼灵骸器官翻译
        addItem(CataclysmOrgans.MALEDICTUS_SPINE, "咒翼灵骸脊柱");
        addItem(CataclysmOrgans.MALEDICTUS_RIB, "咒翼灵骸肋骨");
        addItem(CataclysmOrgans.PHANTOM_HEART, "咒魂心脏");
        addOrganDescription(CataclysmOrgans.PHANTOM_HEART, "咒翼灵骸的诅咒心脏");
        addOrganSkill(CataclysmOrgans.PHANTOM_HEART, 1, "冲刺状态下，所有造成的伤害最终增加25%");
        addItem(CataclysmOrgans.PHANTOM_SHARD, "咒魂残片");
        addItem(CataclysmOrgans.SEALING_STONE_SLAB, "封印石板");
        addOrganSkill(CataclysmOrgans.SEALING_STONE_SLAB, 0, "在前方扇形范围召唤5道幻影战戟，每道造成12点咒翼魔法伤害");

        // 斯库拉器官翻译
        addItem(CataclysmOrgans.TIDAL_LANTERN, "涛浪提灯");
        addOrganDescription(CataclysmOrgans.TIDAL_LANTERN, "攻击时消耗粘液");
        addOrganSkill(CataclysmOrgans.TIDAL_LANTERN, 0, "攻击时，消耗所有当前粘液并增加等额伤害");
        addOrganSkill(CataclysmOrgans.TIDAL_LANTERN, 1, "消耗的粘液大于等于30时，额外召唤伤害等同于本次伤害的水浪");
        addItem(CataclysmOrgans.STORM_SPINE, "风暴脊柱");
        addOrganDescription(CataclysmOrgans.STORM_SPINE, "吸收伤害转化为粘液");
        addOrganSkill(CataclysmOrgans.STORM_SPINE, 0, "受伤时，吸收伤害的20%转化为粘液（同时减伤20%），单次上限10点");
        addOrganSkill(CataclysmOrgans.STORM_SPINE, 1, "粘液达到上限时失效");
        addItem(CataclysmOrgans.STORM_RIB, "风暴肋骨");
        addOrganSkill(CataclysmOrgans.STORM_RIB, "+10 粘液上限");

        // 焰魔器官翻译
        addItem(CataclysmOrgans.UNDYING_EMBER, "不灭薪火");
        addOrganDescription(CataclysmOrgans.UNDYING_EMBER, "根据全局正温度提供力量加成");
        addItem(CataclysmOrgans.IGNITED_RIB_PLATING, "焰魔肋甲");
        addOrganDescription(CataclysmOrgans.IGNITED_RIB_PLATING, "根据局部正温度提供格挡加成，格挡 = √局部温度");
        addItem(CataclysmOrgans.BLAZING_VISAGE, "炽面甲");
        addOrganDescription(CataclysmOrgans.BLAZING_VISAGE, "近战命中施加炽烙，根据局部温度回血");
        addOrganSkill(CataclysmOrgans.BLAZING_VISAGE, 0, "近战攻击施加炽烙（-20%护甲、-20%韧性）");
        addOrganSkill(CataclysmOrgans.BLAZING_VISAGE, 1, "回血量 = 2 + 局部温度×0.5，已有炽烙则翻倍");

        // 下界合金巨兽器官翻译
        addItem(CataclysmOrgans.MONSTROSITY_CORE, "巨兽炉心");
        addOrganDescription(CataclysmOrgans.MONSTROSITY_CORE, "从热量中生成黄胆汁。+100黄胆汁上限。");
        addItem(CataclysmOrgans.MONSTROSITY_CIRCUIT, "巨兽回路");
        addOrganSkill(CataclysmOrgans.MONSTROSITY_CIRCUIT, "消耗全部黄胆汁：AoE地震造成(消耗量 + 5%最大HP + 温度×2)伤害");
        addItem(CataclysmOrgans.MONSTROSITY_FURNACE, "巨兽熔炉");
        addOrganDescription(CataclysmOrgans.MONSTROSITY_FURNACE, "允许饮用岩浆。恢复饥饿值、黄胆汁，并赋予骇人之恶效果。");

        // Cataclysm 远古工厂器官翻译
        addItem(CataclysmOrgans.TACTICAL_DISK, "战术磁盘");
        addOrganDescription(CataclysmOrgans.TACTICAL_DISK, "磁盘存储所有工厂组件的运行日志。数据越完整，系统自检越精确——对机械而言，数据即生命。");
        addItem(CataclysmOrgans.REINFORCED_FRAME, "强化构架");
        addOrganDescription(CataclysmOrgans.REINFORCED_FRAME, "工厂标准结构件。也许过刚则折，但至少现在不会。");
        addItem(CataclysmOrgans.POWER_CELL, "蓄能电芯");
        addOrganDescription(CataclysmOrgans.POWER_CELL, "持续输出微弱修复电流——虽然缓慢，但永不停歇。");
        addItem(CataclysmOrgans.COMPUTE_CHIP, "运算晶片");
        addOrganDescription(CataclysmOrgans.COMPUTE_CHIP, "协处理器。每增加一个工厂零件，晶片就多一条数据通道，系统响应就越快。");
        addItem(CataclysmOrgans.MECHANICAL_STAR, "机械之星");
        addOrganSkill(CataclysmOrgans.MECHANICAL_STAR, 0, "锁定视线方向的敌人，发射一枚追踪导弹（伤害 8.0）。冷却 8 秒。");
        addItem(CataclysmOrgans.DEATH_LENS, "死亡透镜");
        addOrganSkill(CataclysmOrgans.DEATH_LENS, 0, "发射一道死亡激光（伤害 6 + 目标最大生命 6%%）。不点火不破坏方块。冷却 15 秒。");

        // Cataclysm 末影守卫器官翻译
        addItem(CataclysmOrgans.GUARDIAN_STONE, "守卫石块");
        addItem(CataclysmOrgans.VOID_CRYSTAL_SPINE, "虚空晶脊");
        addOrganDescription(CataclysmOrgans.VOID_CRYSTAL_SPINE, "末影守卫的脊柱动力晶核，残余的虚空能量仍在其中脉动。");
        addOrganSkill(CataclysmOrgans.VOID_CRYSTAL_SPINE, 0, "以自身为中心召唤三环虚空符文阵（内6+中11+外14）。冷却15秒。");

        // IronSpell 亡魂器官翻译
        addItem(IronSpellOrgans.NECROMANCER_SPINE, "亡魂脊柱");
        addItem(IronSpellOrgans.NECROMANCER_RIB, "亡魂肋骨");

        // IronSpell 提洛斯回响·原初受火者器官翻译
        addItem(IronSpellOrgans.PRIMORDIAL_FLAME, "原初之火");
        addOrganDescription(IronSpellOrgans.PRIMORDIAL_FLAME, "点燃原初之火的余烬，回荡着首位受火者的力量");
        addOrganSkill(IronSpellOrgans.PRIMORDIAL_FLAME, 0, "施放火焰法术时，法术等级+1");

        // IronSpell 高位唤魔者器官翻译
        addItem(IronSpellOrgans.EMERALD_SKULL, "绿宝石头骨");
        addOrganDescription(IronSpellOrgans.EMERALD_SKULL, "高位唤魔者的诅咒颅骨，涌动着翡翠色的唤魔之力");
        addOrganSkill(IronSpellOrgans.EMERALD_SKULL, 0, "施放唤魔法术时，法术等级+1");

        // IronSpell 死者之王器官翻译
        addItem(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "腐败魂灯");
        addOrganDescription(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "收割死亡生物的灵魂为黑胆汁");
        addOrganSkill(IronSpellOrgans.CORRUPTED_SOUL_LANTERN, "灵魂收割：附近有生物死亡时，获取黑胆汁");

        addItem(IronSpellOrgans.DEAD_KING_SPINE, "尸王脊柱");
        addOrganDescription(IronSpellOrgans.DEAD_KING_SPINE, "以黑胆汁吸收伤害");
        addOrganSkill(IronSpellOrgans.DEAD_KING_SPINE, "受伤时，消耗等额黑胆汁吸收最高50%的伤害");

        addItem(IronSpellOrgans.DEAD_KING_RIB, "尸王肋骨");
        addOrganDescription(IronSpellOrgans.DEAD_KING_RIB, "增加黑胆汁容量");
        addOrganSkill(IronSpellOrgans.DEAD_KING_RIB, "+10 黑胆汁上限");

        addItem(WAICOrgans.DIVINE_CORE, "神圣核心");
        addItem(WAICOrgans.FROST_CORE, "冰霜核心");
        addItem(WAICOrgans.FLAME_CORE, "炽焰核心");
        addItem(WAICOrgans.NATURE_CORE, "自然核心");

        // ==================== Companions 神圣教宗器官翻译 ====================
        addItem(CompanionsOrgans.PONTIFF_HEART, "教宗心脏");
        addOrganDescription(CompanionsOrgans.PONTIFF_HEART, "圣化变身：生命值降至30%以下时，回复50%最大生命，获得力量II、抗性II、速度II（15秒）。冷却3分钟");

        addItem(CompanionsOrgans.PONTIFF_LUNG, "教宗肺脏");
        addItem(CompanionsOrgans.PONTIFF_STOMACH, "教宗胃");
        addItem(CompanionsOrgans.PONTIFF_INTESTINE, "教宗肠子");
        addItem(CompanionsOrgans.PONTIFF_KIDNEY, "教宗肾脏");

        addItem(CompanionsOrgans.PONTIFF_SPLEEN, "教宗脾脏");
        addOrganSkill(CompanionsOrgans.PONTIFF_SPLEEN, "释放向外扩展的火环。层数 = min(1 + 教宗器官数/4, 3)。冷却10秒");

        addItem(CompanionsOrgans.PONTIFF_LIVER, "教宗肝脏");

        addItem(CompanionsOrgans.PONTIFF_APPENDIX, "教宗阑尾");
        addOrganSkill(CompanionsOrgans.PONTIFF_APPENDIX, "发射追踪星弹。正温度→红星（点燃），负温度→蓝星（冻结）。冷却8秒");

        addItem(CompanionsOrgans.PONTIFF_MUSCLE, "教宗肌肉");
        addOrganSkill(CompanionsOrgans.PONTIFF_MUSCLE, "击退抗性+8%");

        // ==================== Companions 蛋糕器官翻译 ====================
        addItem(CompanionsOrgans.CAKE_HEART, "蛋糕心脏");
        addItem(CompanionsOrgans.CAKE_LUNG, "蛋糕肺脏");
        addItem(CompanionsOrgans.CAKE_STOMACH, "蛋糕胃");
        addOrganSkill(CompanionsOrgans.CAKE_STOMACH, "食用食物时给予甜蜜效果，已有甜蜜时叠加等级并重置时长");
        addItem(CompanionsOrgans.CAKE_LIVER, "蛋糕肝脏");
        addOrganSkill(CompanionsOrgans.CAKE_LIVER, "甜蜜等级>=2时：每秒消耗1级甜蜜清除1个随机负面效果");

        // 悚恐怖官翻译
        addItem(IceAndFireOrgans.BITTER_FLESH, "苦寒血肉");
        addItem(IceAndFireOrgans.ICE_SHARD, "冰魂残片");
        addItem(IceAndFireOrgans.FROSTBURN_SOUL, "冻结魂火");
        addItem(IceAndFireOrgans.DREAD_PHYLACTERY, "悚怖命匣");
        addItem(IceAndFireOrgans.DREAD_RIB, "悚怖肋骨");
        addItem(IceAndFireOrgans.DREAD_SPINE, "悚怖脊柱");

        // 冰魂残片效果描述
        addOrganDescription(IceAndFireOrgans.ICE_SHARD, "全局温度 × -0.05 → 额外健康值（温度越低，健康值越高）");
        // 冻结魂火效果描述
        addOrganDescription(IceAndFireOrgans.FROSTBURN_SOUL, "全局温度 × -0.15 → 额外健康值（温度越低，健康值越高）");
        // 悚恐怖匣效果描述
        addOrganDescription(IceAndFireOrgans.DREAD_PHYLACTERY, "全局温度 × -0.25 → 额外健康值（温度越低，健康值越高）");
        addOrganSkill(IceAndFireOrgans.DREAD_PHYLACTERY, "将目标身上的缓慢效果转化为冰霜伤害");
        // 悚怖脊柱技能描述
        addOrganSkill(IceAndFireOrgans.DREAD_SPINE, "攻击时，根据局部温度对目标施加缓慢效果（持续3秒）");

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
        addOrganSkill(IceAndFireOrgans.HYDRA_INTESTINE, "每个器官增加50%的食物效果时长");

        // 幻想种器官翻译
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

        // 布织泰迪熊器官翻译
        addItem(WAICOrgans.CLOTH_TEDDY_BEAR, "布织泰迪熊");
        addOrganDescription(WAICOrgans.CLOTH_TEDDY_BEAR, "胸腔关闭时，将胸腔内的羊毛转换为随机布织器官");
        addOrganSkill(WAICOrgans.CLOTH_TEDDY_BEAR, "缝补：消耗羊毛来回复生命值");
        addItem(WAICOrgans.CLOTH_HEART, "布织心脏");
        addItem(WAICOrgans.CLOTH_LUNG, "布织肺脏");
        addItem(WAICOrgans.CLOTH_LIVER, "布织肝脏");
        addItem(WAICOrgans.CLOTH_INTESTINE, "布织肠子");
        addItem(WAICOrgans.CLOTH_STOMACH, "布织胃");
        addItem(WAICOrgans.CLOTH_KIDNEY, "布织肾脏");
        addItem(WAICOrgans.CLOTH_SPLEEN, "布织脾脏");
        addItem(WAICOrgans.CLOTH_SPINE, "布织脊柱");
        addItem(WAICOrgans.CLOTH_RIB, "布织肋骨");
        addItem(WAICOrgans.CLOTH_MUSCLE, "布织肌肉");
        addItem(WAICOrgans.CLOTH_APPENDIX, "布织阑尾");

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

        // 浮霜器官description描述
        add(AnvilCraftOrgans.MERCILESS_TRANSLATION, "无情：将所有附魔转换为器官属性");

        // 超限合金器官翻译
        addItem(AnvilCraftOrgans.TRANSCENDIUM_HEART, "超限合金心脏");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LUNG, "超限合金肺脏");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPINE, "超限合金脊柱");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_STOMACH, "超限合金胃");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE, "超限合金肠子");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY, "超限合金肾脏");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN, "超限合金脾脏");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_LIVER, "超限合金肝脏");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX, "超限合金阑尾");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_RIB, "超限合金肋骨");
        addItem(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE, "超限合金肌肉");

        // 超限合金器官description描述
        add(AnvilCraftOrgans.LOOTING_BURST_TRANSLATION, "战利品大爆发：+1抢夺和+1幸运");

        // 诅咒金器官翻译
        addItem(AnvilCraftOrgans.CURSED_GOLD_HEART, "诅咒金心脏");
        addItem(AnvilCraftOrgans.CURSED_GOLD_LUNG, "诅咒金肺脏");
        addItem(AnvilCraftOrgans.CURSED_GOLD_LIVER, "诅咒金肝脏");
        addItem(AnvilCraftOrgans.CURSED_GOLD_INTESTINE, "诅咒金肠子");
        add(AnvilCraftOrgans.CURSED_GOLD_TRANSLATION, "诅咒：胸腔中诅咒金器官越多，负面效果越重");

        // 余烬金属器官翻译
        addItem(AnvilCraftOrgans.EMBER_METAL_RIB, "余烬金属肋骨");
        addItem(AnvilCraftOrgans.EMBER_METAL_MUSCLE, "余烬金属肌肉");
        addOrganSkill(AnvilCraftOrgans.EMBER_METAL_MUSCLE, "余烬锻造：+0.5近战伤害");
        addItem(AnvilCraftOrgans.EMBER_METAL_SPINE, "余烬金属脊柱");
        addItem(AnvilCraftOrgans.EMBER_METAL_APPENDIX, "余烬金属阑尾");
        add(AnvilCraftOrgans.EMBER_ABSORPTION_TRANSLATION, "余烬吸收：火焰伤害转化为治疗");

        // 皇家钢器官翻译
        addItem(AnvilCraftOrgans.ROYAL_STEEL_RIB, "皇家钢肋骨");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_MUSCLE, "皇家钢肌肉");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_SPINE, "皇家钢脊柱");
        addItem(AnvilCraftOrgans.ROYAL_STEEL_APPENDIX, "皇家钢阑尾");
        add(AnvilCraftOrgans.ROYAL_STEEL_TRANSLATION, "坚实可靠：朴素的固定属性加成");

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
        addOrganSkill(WAICOrgans.INK_APPENDIX, "消耗墨水回复等量法力");
        addItem(WAICOrgans.INK_RIB, "墨水肋骨");
        addItem(WAICOrgans.INK_MUSCLE, "墨水肌肉");
        addOrganSkill(WAICOrgans.INK_MUSCLE, "受到伤害时为墨水瓶添加等同于伤害值的墨水");
        addItem(WAICOrgans.INK_BOTTLE, "墨水瓶");
        add(WAICOrgans.INK_BOTTLE_INK_TRANSLATION, "墨水: %s/1000");
        addItem(WAICOrgans.NIB, "钢笔尖");
        addOrganDescription(WAICOrgans.NIB, "释放法术时消耗墨水(5×等级)提升法术1级（优先触发）");

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
        add(WAICOrgans.PALETTE_DYE_TRANSLATION, "%s: %s");

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
        addOrganDescription(WAICOrgans.FRANKENSTEIN_HEART, "缝合心脏。继承存储心脏的属性加成。");
        addItem(WAICOrgans.FRANKENSTEIN_LUNG, "弗兰肯斯坦肺脏");
        addItem(WAICOrgans.FRANKENSTEIN_STOMACH, "弗兰肯斯坦胃");
        addItem(WAICOrgans.FRANKENSTEIN_INTESTINE, "弗兰肯斯坦肠子");
        addItem(WAICOrgans.FRANKENSTEIN_KIDNEY, "弗兰肯斯坦肾脏");
        addItem(WAICOrgans.FRANKENSTEIN_SPLEEN, "弗兰肯斯坦脾脏");
        addItem(WAICOrgans.FRANKENSTEIN_LIVER, "弗兰肯斯坦肝脏");
        addItem(WAICOrgans.FRANKENSTEIN_APPENDIX, "弗兰肯斯坦阑尾");
        addItem(WAICOrgans.FRANKENSTEIN_MUSCLE, "弗兰肯斯坦肌肉");

        // 病变器官翻译
        addItem(WAICOrgans.LESION_HEART, "病变心脏");
        addOrganDescription(WAICOrgans.LESION_HEART, "一颗在腐化中跳动的心脏");
        addOrganSkill(WAICOrgans.LESION_HEART, "将自身所有效果传播给10格范围内的生物");
        addItem(WAICOrgans.LESION_LUNG, "病变肺脏");
        addItem(WAICOrgans.LESION_STOMACH, "病变胃");
        addItem(WAICOrgans.LESION_INTESTINE, "病变肠子");
        addItem(WAICOrgans.LESION_KIDNEY, "病变肾脏");
        addItem(WAICOrgans.LESION_SPLEEN, "病变脾脏");
        addItem(WAICOrgans.LESION_LIVER, "病变肝脏");
        addItem(WAICOrgans.LESION_APPENDIX, "病变阑尾");
        addItem(WAICOrgans.LESION_MUSCLE, "病变肌肉");
        addOrganSkill(WAICOrgans.LESION_MUSCLE, 0, "每有一个负面效果，+1力量和+1速度");
        addOrganSkill(WAICOrgans.LESION_MUSCLE, 1, "对目标额外造成等同于目标所有负面效果(等级+1)之和的伤害");

        // 九狱器官翻译
        addItem(WAICOrgans.LIMBO, "灵薄");
        addOrganSkill(WAICOrgans.LIMBO, 0, "罪业1：每秒获得1点经验");
        addOrganSkill(WAICOrgans.LIMBO, 1, "罪业2：每秒获得3点经验");
        addOrganSkill(WAICOrgans.LIMBO, 2, "罪业3：每秒获得5点经验");
        addItem(WAICOrgans.LUST, "色欲");
        addOrganSkill(WAICOrgans.LUST, 0, "罪业1：攻击回复造成伤害10%的生命");
        addOrganSkill(WAICOrgans.LUST, 1, "罪业2：攻击回复造成伤害20%的生命");
        addOrganSkill(WAICOrgans.LUST, 2, "罪业3：攻击回复造成伤害30%的生命");
        addItem(WAICOrgans.GLUTTONY, "暴食");
        addOrganSkill(WAICOrgans.GLUTTONY, 0, "可以食用任何食物");
        addOrganSkill(WAICOrgans.GLUTTONY, 1, "食用获得饥饿值×N的黄心（上限N×20）");
        addOrganSkill(WAICOrgans.GLUTTONY, 2, "食用额外回复N点生命");
        addItem(WAICOrgans.GREED, "贪婪");
        addOrganSkill(WAICOrgans.GREED, 0, "罪业1：+1抢夺, +1时运");
        addOrganSkill(WAICOrgans.GREED, 1, "罪业2：+2抢夺, +2时运");
        addOrganSkill(WAICOrgans.GREED, 2, "罪业3：+3抢夺, +3时运");
        addItem(WAICOrgans.WRATH, "愤怒");
        addOrganSkill(WAICOrgans.WRATH, 0, "罪业1：+1力量, +1速度");
        addOrganSkill(WAICOrgans.WRATH, 1, "罪业2：+2力量, +2速度");
        addOrganSkill(WAICOrgans.WRATH, 2, "罪业3：+3力量, +3速度");
        addItem(WAICOrgans.HERESY, "异端");
        addOrganSkill(WAICOrgans.HERESY, 0, "罪业1：药水持续时间+50%");
        addOrganSkill(WAICOrgans.HERESY, 1, "罪业2：药水持续时间+100%");
        addOrganSkill(WAICOrgans.HERESY, 2, "罪业3：药水等级+1");
        addItem(WAICOrgans.VIOLENCE, "暴力");
        addOrganSkill(WAICOrgans.VIOLENCE, 0, "罪业1：暴击伤害×2");
        addOrganSkill(WAICOrgans.VIOLENCE, 1, "罪业2：暴击伤害×2");
        addOrganSkill(WAICOrgans.VIOLENCE, 2, "罪业3：攻击永远暴击");
        addItem(WAICOrgans.FRAUD, "欺诈");
        addOrganSkill(WAICOrgans.FRAUD, 0, "罪业1：村民交易获得额外经验");
        addOrganSkill(WAICOrgans.FRAUD, 1, "罪业2：村民交易打折");
        addOrganSkill(WAICOrgans.FRAUD, 2, "罪业3：村民交易不缺货");
        addItem(WAICOrgans.TREACHERY, "背叛");
        addOrganSkill(WAICOrgans.TREACHERY, 0, "罪业1：攻击额外造成目标1%最大生命值伤害");
        addOrganSkill(WAICOrgans.TREACHERY, 1, "罪业2：攻击额外造成目标3%最大生命值伤害");
        addOrganSkill(WAICOrgans.TREACHERY, 2, "罪业3：攻击额外造成目标5%最大生命值伤害");

        // 双子魔眼器官翻译
        addItem(WAICOrgans.STRANGE_EYEBALL, "奇怪的眼球");
        addItem(WAICOrgans.EERIE_EYEBALL, "诡异的眼球");
        addItem(WAICOrgans.STRANGE_MECHANICAL_EYEBALL, "奇怪的机械眼球");
        addItem(WAICOrgans.EERIE_MECHANICAL_EYEBALL, "诡异的机械眼球");

        // 拟态器官翻译
        addItem(WAICOrgans.MIMIC_HEART, "拟态心脏");
        addOrganSkill(WAICOrgans.MIMIC_HEART, "拟态：生命恢复效果提升50%");
        addItem(WAICOrgans.MIMIC_LIVER, "拟态肝脏");
        addOrganSkill(WAICOrgans.MIMIC_LIVER, "拟态：生命恢复效果提升50%");
        addItem(WAICOrgans.MIMIC_LUNG, "拟态肺脏");
        addOrganSkill(WAICOrgans.MIMIC_LUNG, "拟态：生命恢复效果提升50%");

        // 单个器官翻译
        addItem(WAICOrgans.HAUNTED_BONE, "闹鬼的骨头");
        addOrganDescription(WAICOrgans.HAUNTED_BONE, "嘟嘟哒嘟嘟哒");
        addItem(WAICOrgans.SWORD_BONE, "剑骨头");
        addOrganDescription(WAICOrgans.SWORD_BONE, "剑一般锋利");
        addItem(WAICOrgans.STRAIGHT_INTESTINE, "直肠子");
        addOrganDescription(WAICOrgans.STRAIGHT_INTESTINE, "食用食物后30%几率在3秒后掉落该食物");
        addItem(WAICOrgans.SQUASH, "窝瓜");
        addItem(WAICOrgans.EXPERIENCE_HEART, "经验之心");
        addOrganDescription(WAICOrgans.EXPERIENCE_HEART, "一颗脉动着积累经验的心脏");
        addOrganDescription(WAICOrgans.EXPERIENCE_HEART, 0, "每10级经验等级+1健康值");
        addOrganDescription(WAICOrgans.EXPERIENCE_HEART, 1, "从经验球获取的经验×（魔法器官数量+1）倍率");

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
        addAttribute(WAICAttribute.LOOTING, "抢夺");
        addAttribute(WAICAttribute.FORTUNE, "时运");

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

        // 龙血药剂翻译
        addItem(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION, "火龙血药剂");
        addItem(WAICItem.ICE_DRAGON_BLOOD_PREPARATION, "冰龙血药剂");
        addItem(WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION, "电龙血药剂");
        addItem(WAICItem.DRAGON_BLOOD_PREPARATION_GROUP, "龙血药剂组");

        // 龙之力效果翻译
        addEffect(WAICEffect.FIRE_DRAGON_POWER::value, "火龙之力");
        addEffect(WAICEffect.ICE_DRAGON_POWER::value, "冰龙之力");
        addEffect(WAICEffect.LIGHTNING_DRAGON_POWER::value, "电龙之力");
        addEffect(WAICEffect.DRAGON_POWER::value, "龙之力");

        // 甜蜜效果翻译
        addEffect(WAICEffect.SWEETNESS::value, "甜蜜");

        // 超频效果翻译
        addEffect(WAICEffect.OVERLOAD::value, "超频");

        // 电磁义体器官翻译
        addItem(WAICOrgans.COMPUTING_CORE, "演算核心");
        addOrganDescription(WAICOrgans.COMPUTING_CORE, "中央处理器，管理电荷再生");
        addOrganSkill(WAICOrgans.COMPUTING_CORE, 0, "信号再生：每tick回复1点电荷（不超过上限）。超频期间暂停");
        addItem(WAICOrgans.CURRENT_RIB, "导流肋骨");
        addOrganDescription(WAICOrgans.CURRENT_RIB, "导流元件，消耗电荷形成防护");
        addOrganSkill(WAICOrgans.CURRENT_RIB, 0, "并联优化：对称位置存在导流肋骨时，护盾消耗减半");
        addOrganSkill(WAICOrgans.CURRENT_RIB, 1, "导流护盾：每10电荷抵消1点伤害，上限4点（超频时8点）");
        addItem(WAICOrgans.CHARGED_MUSCLE, "充能肌束");
        addOrganDescription(WAICOrgans.CHARGED_MUSCLE, "圆柱电容，优化能量效率并回收余电");
        addOrganSkill(WAICOrgans.CHARGED_MUSCLE, 0, "回路返还：消耗电荷时，25%概率返还（超频时50%）");
        addOrganSkill(WAICOrgans.CHARGED_MUSCLE, 1, "余电回收：电荷流失量的10%转化为生命回复（超频时20%）");
        addOrganSkill(WAICOrgans.CHARGED_MUSCLE, 2, "电流推动：冲刺时每 tick 产生 1 电荷");
        addItem(WAICOrgans.CONDUCTIVE_SPINE, "传导链节");
        addOrganDescription(WAICOrgans.CONDUCTIVE_SPINE, "信号传导通道，激活超频模式");
        addOrganSkill(WAICOrgans.CONDUCTIVE_SPINE, "超频模式：消耗maxCharge/2电荷激活，持续10秒。冷却20秒");
        addItem(WAICOrgans.ENERGY_MODULE, "蓄能模块");
        addOrganDescription(WAICOrgans.ENERGY_MODULE, "物理电池仓，每个提供500电荷存储上限，允许50%超载");
        add(WAICOrgans.ENERGY_MODULE_CHARGE_TRANSLATION, "电荷: %s/%s");
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

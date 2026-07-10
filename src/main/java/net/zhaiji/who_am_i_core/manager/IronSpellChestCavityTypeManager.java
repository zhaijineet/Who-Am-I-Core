package net.zhaiji.who_am_i_core.manager;

import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.Map;

public class IronSpellChestCavityTypeManager {
    // 死者之王胸腔
    public static final ChestCavityType DEAD_KING = register("dead_king")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(4, IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get())
        .setSecondRow(4, IronSpellOrgans.DEAD_KING_SPINE.get())
        .setFirstRow(1, IronSpellOrgans.DEAD_KING_RIB.get())
        .setFirstRow(7, IronSpellOrgans.DEAD_KING_RIB.get())
        .setSecondRow(1, IronSpellOrgans.DEAD_KING_RIB.get())
        .setSecondRow(7, IronSpellOrgans.DEAD_KING_RIB.get())
        .addValueBonuses(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 死灵法师胸腔
    public static final ChestCavityType NECROMANCER = register("necromancer")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setFirstRow(1, IronSpellOrgans.NECROMANCER_RIB.get())
        .setFirstRow(7, IronSpellOrgans.NECROMANCER_RIB.get())
        .setSecondRow(1, IronSpellOrgans.NECROMANCER_RIB.get())
        .setSecondRow(4, IronSpellOrgans.NECROMANCER_SPINE.get())
        .setSecondRow(7, IronSpellOrgans.NECROMANCER_RIB.get())
        .addValueBonuses(IronSpellOrgans.NECROMANCER_SPINE.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 原初之火胸腔
    public static final ChestCavityType FIRE_BOSS = register("fire_boss")
        .copyWith(ChestCavityTypeManager.FIREPROOF)
        .setFirstRow(4, IronSpellOrgans.PRIMORDIAL_FLAME.get());

    // 诅咒盔甲架胸腔
    public static final ChestCavityType CURSED_ARMOR_STAND = register("cursed_armor_stand")
        .copyWith(ChestCavityTypeManager.ARMOR_STAND)
        .addValueBonuses(Items.STICK, Map.of(
            InitAttribute.HEALTH, 0.5,
            InitAttribute.NERVES, 0.5,
            InitAttribute.BREATH_CAPACITY, 0.5,
            InitAttribute.BREATH_RECOVERY, 0.5
        ));

    // 牧师胸腔
    public static final ChestCavityType PRIEST = register("priest")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setFirstRow(4, WAICOrgans.DIVINE_CORE.get());

    // 炽焰术士胸腔
    public static final ChestCavityType PYROMANCER = register("pyromancer")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setFirstRow(4, WAICOrgans.FLAME_CORE.get());

    // 冰霜术士胸腔
    public static final ChestCavityType CRYOMANCER = register("cryomancer")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setFirstRow(4, WAICOrgans.FROST_CORE.get());

    // 药剂师胸腔
    public static final ChestCavityType APOTHECARIST = register("apothecarist")
        .copyWith(ChestCavityTypeManager.FIREPROOF)
        .setFirstRow(4, WAICOrgans.NATURE_CORE.get());

    // 高位唤魔者胸腔
    public static final ChestCavityType ARCHEVOKER = register("archevoker")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setSecondRow(4, IronSpellOrgans.EMERALD_SKULL.get());

    /**
     * 注册铁魔法 Mod 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // 死者之王
        event.registerEntity(EntityRegistry.DEAD_KING.get(), DEAD_KING);
        // 亡灵术士
        event.registerEntity(EntityRegistry.NECROMANCER.get(), NECROMANCER);
        // 提洛斯回响·原初受火者
        event.registerEntity(EntityRegistry.FIRE_BOSS.get(), FIRE_BOSS);
        // 高位唤魔者
        event.registerEntity(EntityRegistry.ARCHEVOKER.get(), ARCHEVOKER);
        // 炽焰术士
        event.registerEntity(EntityRegistry.PYROMANCER.get(), PYROMANCER);
        // 冰霜术士
        event.registerEntity(EntityRegistry.CRYOMANCER.get(), CRYOMANCER);
        // 狩魔人卫道士
        event.registerEntity(EntityRegistry.MAGEHUNTER_VINDICATOR.get(), ChestCavityTypeManager.HUMAN);
        // 远古骑士
        event.registerEntity(EntityRegistry.KEEPER.get(), ChestCavityTypeManager.WITHER_SKELETON);
        // 药剂师
        event.registerEntity(EntityRegistry.APOTHECARIST.get(), APOTHECARIST);
        // 邪教徒
        event.registerEntity(EntityRegistry.CULTIST.get(), ChestCavityTypeManager.HUMAN);
        // 牧师
        event.registerEntity(EntityRegistry.PRIEST.get(), PRIEST);
        // 测试法师
        event.registerEntity(EntityRegistry.DEBUG_WIZARD.get(), ChestCavityTypeManager.HUMAN);

        // 古墓僵尸
        event.registerEntity(EntityRegistry.CATACOMBS_ZOMBIE.get(), ChestCavityTypeManager.UNDEAD);
        // 召唤出的僵尸
        event.registerEntity(EntityRegistry.SUMMONED_ZOMBIE.get(), ChestCavityTypeManager.UNDEAD);
        // 召唤出的骷髅
        event.registerEntity(EntityRegistry.SUMMONED_SKELETON.get(), ChestCavityTypeManager.SKELETON);
        // 召唤出的恼鬼
        event.registerEntity(EntityRegistry.SUMMONED_VEX.get(), ChestCavityTypeManager.SMALL_ANIMAL);
        // 幽冥骏马
        event.registerEntity(EntityRegistry.SPECTRAL_STEED.get(), WAICChestCavityTypeManager.FANTASTICAL);
        // 召唤出的北极熊
        event.registerEntity(EntityRegistry.SUMMONED_POLAR_BEAR.get(), ChestCavityTypeManager.ANIMAL);
        // 诅咒盔甲架
        event.registerEntity(EntityRegistry.CURSED_ARMOR_STAND.get(), CURSED_ARMOR_STAND);
        // 冰霜蜘蛛
        event.registerEntity(EntityRegistry.ICE_SPIDER.get(), ChestCavityTypeManager.SPIDER);
    }

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(ResourceLocation.fromNamespaceAndPath("irons_spellbooks", path));
    }
}

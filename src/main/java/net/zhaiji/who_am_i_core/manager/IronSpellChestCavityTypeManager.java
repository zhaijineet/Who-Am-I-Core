package net.zhaiji.who_am_i_core.manager;

import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;

public class IronSpellChestCavityTypeManager {
    // ==================== 尸王胸腔 ====================
    // 亡灵君主，使用亡灵底座承载腐败魂灯
    public static final ChestCavityType DEAD_KING = ChestCavityTypeManager.register("ispell_dead_king")
        .copyWith(ChestCavityTypeManager.UNDEAD)
        .setFirstRow(4, IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get())
        .setSecondRow(4, IronSpellOrgans.DEAD_KING_SPINE.get())
        .setFirstRow(1, IronSpellOrgans.DEAD_KING_RIB.get())
        .setFirstRow(7, IronSpellOrgans.DEAD_KING_RIB.get())
        .setSecondRow(1, IronSpellOrgans.DEAD_KING_RIB.get())
        .setSecondRow(7, IronSpellOrgans.DEAD_KING_RIB.get());

    // ==================== 死灵法师胸腔 ====================
    // 人形法师，使用人类底座承载脊柱与肋骨
    public static final ChestCavityType NECROMANCER = ChestCavityTypeManager.register("ispell_necromancer")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setSecondRow(4, IronSpellOrgans.NECROMANCER_SPINE.get())
        .setFirstRow(1, IronSpellOrgans.NECROMANCER_RIB.get())
        .setFirstRow(7, IronSpellOrgans.NECROMANCER_RIB.get())
        .setSecondRow(1, IronSpellOrgans.NECROMANCER_RIB.get())
        .setSecondRow(7, IronSpellOrgans.NECROMANCER_RIB.get());

    // ==================== 火焰Boss胸腔 ====================
    // 烈焰构装，使用烈焰底座承载原初之火
    public static final ChestCavityType FIRE_BOSS = ChestCavityTypeManager.register("ispell_fire_boss")
        .copyWith(ChestCavityTypeManager.BLAZE)
        .setFirstRow(4, IronSpellOrgans.PRIMORDIAL_FLAME.get());

    // ==================== 唤魔者胸腔 ====================
    // 人形施法者，使用人类底座承载绿宝石头骨
    public static final ChestCavityType ARCHEVOKER = ChestCavityTypeManager.register("ispell_archevoker")
        .copyWith(ChestCavityTypeManager.HUMAN)
        .setSecondRow(4, IronSpellOrgans.EMERALD_SKULL.get());

    /**
     * 注册铁魔法 Mod 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // --- 有专属器官的实体 ---
        // 死者之王
        event.registerEntity(EntityRegistry.DEAD_KING.get(), DEAD_KING);
        // 亡灵术士
        event.registerEntity(EntityRegistry.NECROMANCER.get(), NECROMANCER);
        // 提洛斯回响·原初受火者
        event.registerEntity(EntityRegistry.FIRE_BOSS.get(), FIRE_BOSS);
        // 高位唤魔者
        event.registerEntity(EntityRegistry.ARCHEVOKER.get(), ARCHEVOKER);

        // --- 施法者与灾厄变体 ---
        // 炽焰术士
        event.registerEntity(EntityRegistry.PYROMANCER.get(), ChestCavityTypeManager.HUMAN);
        // 冰霜术士
        event.registerEntity(EntityRegistry.CRYOMANCER.get(), ChestCavityTypeManager.HUMAN);
        // 狩魔人卫道士
        event.registerEntity(EntityRegistry.MAGEHUNTER_VINDICATOR.get(), ChestCavityTypeManager.HUMAN);
        // 远古骑士
        event.registerEntity(EntityRegistry.KEEPER.get(), ChestCavityTypeManager.HUMAN);
        // 药剂师
        event.registerEntity(EntityRegistry.APOTHECARIST.get(), ChestCavityTypeManager.HUMAN);
        // 邪教徒
        event.registerEntity(EntityRegistry.CULTIST.get(), ChestCavityTypeManager.HUMAN);
        // 牧师
        event.registerEntity(EntityRegistry.PRIEST.get(), ChestCavityTypeManager.HUMAN);
        // 测试法师
        event.registerEntity(EntityRegistry.DEBUG_WIZARD.get(), ChestCavityTypeManager.HUMAN);

        // --- 衍生怪物/召唤物 ---
        // 古墓僵尸
        event.registerEntity(EntityRegistry.CATACOMBS_ZOMBIE.get(), ChestCavityTypeManager.UNDEAD);
        // 召唤出的僵尸
        event.registerEntity(EntityRegistry.SUMMONED_ZOMBIE.get(), ChestCavityTypeManager.UNDEAD);
        // 召唤出的骷髅
        event.registerEntity(EntityRegistry.SUMMONED_SKELETON.get(), ChestCavityTypeManager.SKELETON);
        // 召唤出的恼鬼
        event.registerEntity(EntityRegistry.SUMMONED_VEX.get(), ChestCavityTypeManager.UNDEAD);
        // 幽冥骏马
        event.registerEntity(EntityRegistry.SPECTRAL_STEED.get(), ChestCavityTypeManager.UNDEAD);
        // 召唤出的北极熊
        event.registerEntity(EntityRegistry.SUMMONED_POLAR_BEAR.get(), ChestCavityTypeManager.CARNIVORE);
        // 诅咒盔甲架
        event.registerEntity(EntityRegistry.CURSED_ARMOR_STAND.get(), ChestCavityTypeManager.ARMOR_STAND);
        // 冰霜蜘蛛
        event.registerEntity(EntityRegistry.ICE_SPIDER.get(), ChestCavityTypeManager.SPIDER);
    }
}

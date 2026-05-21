package net.zhaiji.who_am_i_core.manager;

import com.finderfeed.fdbosses.init.BossEntities;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;

import java.util.Map;

public class FDBossesChestCavityTypeManager {
    // 王国
    public static final ChestCavityType MALKUTH = ChestCavityTypeManager.register("fdbosses_malkuth")
        .setFirstRow(4, FDBossesOrgans.MALKUTH.get())
        .addValueBonuses(FDBossesOrgans.MALKUTH.get(), Map.of(
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 严厉
    public static final ChestCavityType GEBURAH = ChestCavityTypeManager.register("fdbosses_geburah")
        .setFirstRow(4, FDBossesOrgans.GEBURAH.get())
        .addValueBonuses(FDBossesOrgans.GEBURAH.get(), Map.of(
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 慈悲
    public static final ChestCavityType CHESED = ChestCavityTypeManager.register("fdbosses_chesed")
        .setFirstRow(4, FDBossesOrgans.CHESED.get())
        .addValueBonuses(FDBossesOrgans.CHESED.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 火焰王国战士
    public static final ChestCavityType FIRE_MALKUTH_WARRIOR = ChestCavityTypeManager.register("fire_malkuth_warrior")
        .setFirstRow(4, FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get())
        .addValueBonuses(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get(), Map.of(
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 冰霜王国战士
    public static final ChestCavityType ICE_MALKUTH_WARRIOR = ChestCavityTypeManager.register("ice_malkuth_warrior")
        .setFirstRow(4, FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get())
        .addValueBonuses(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get(), Map.of(
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    /**
     * 注册 FDBosses 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // 王国
        event.registerEntity(BossEntities.MALKUTH.get(), MALKUTH);
        // 严厉
        event.registerEntity(BossEntities.GEBURAH.get(), GEBURAH);
        // 慈悲
        event.registerEntity(BossEntities.CHESED.get(), CHESED);

        // 火焰王国战士
        event.registerEntity(BossEntities.FIRE_MALKUTH_WARRIOR.get(), FIRE_MALKUTH_WARRIOR);
        // 冰霜王国战士
        event.registerEntity(BossEntities.ICE_MALKUTH_WARRIOR.get(), ICE_MALKUTH_WARRIOR);

        // TODO 审判之鸟
        event.registerEntity(BossEntities.JUDGEMENT_BIRD.get(), ChestCavityTypeManager.ANIMAL);
    }
}

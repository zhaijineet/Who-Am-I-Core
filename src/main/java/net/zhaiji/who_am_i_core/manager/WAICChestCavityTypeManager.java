package net.zhaiji.who_am_i_core.manager;

import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

public class WAICChestCavityTypeManager {
    // 魔法生物胸腔
    public static final ChestCavityType FANTASTICAL = register("fantastical")
        .setFirstRow(0, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setFirstRow(1, WAICOrgans.FANTASTICAL_RIB.get())
        .setFirstRow(2, WAICOrgans.FANTASTICAL_APPENDIX.get())
        .setFirstRow(3, WAICOrgans.FANTASTICAL_LUNG.get())
        .setFirstRow(4, WAICOrgans.FANTASTICAL_HEART.get())
        .setFirstRow(5, WAICOrgans.FANTASTICAL_LUNG.get())
        .setFirstRow(7, WAICOrgans.FANTASTICAL_RIB.get())
        .setFirstRow(8, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setSecondRow(0, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setSecondRow(1, WAICOrgans.FANTASTICAL_RIB.get())
        .setSecondRow(2, WAICOrgans.FANTASTICAL_SPLEEN.get())
        .setSecondRow(3, WAICOrgans.FANTASTICAL_KIDNEY.get())
        .setSecondRow(4, WAICOrgans.FANTASTICAL_SPINE.get())
        .setSecondRow(5, WAICOrgans.FANTASTICAL_KIDNEY.get())
        .setSecondRow(6, WAICOrgans.FANTASTICAL_LIVER.get())
        .setSecondRow(7, WAICOrgans.FANTASTICAL_RIB.get())
        .setSecondRow(8, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(0, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(1, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(2, WAICOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(3, WAICOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(4, WAICOrgans.FANTASTICAL_STOMACH.get())
        .setThirdRow(5, WAICOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(6, WAICOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(7, WAICOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(8, WAICOrgans.FANTASTICAL_MUSCLE.get());

    // 木制胸腔
    public static final ChestCavityType WOODEN = register("wooden")
        .setNeedBreath(false)
        .setFirstRow(0, WAICOrgans.WOODEN_MUSCLE.get())
        .setFirstRow(2, WAICOrgans.WOODEN_APPENDIX.get())
        .setFirstRow(3, WAICOrgans.WOODEN_LUNG.get())
        .setFirstRow(4, WAICOrgans.WOODEN_HEART.get())
        .setFirstRow(5, WAICOrgans.WOODEN_LUNG.get())
        .setFirstRow(8, WAICOrgans.WOODEN_MUSCLE.get())
        .setSecondRow(0, WAICOrgans.WOODEN_MUSCLE.get())
        .setSecondRow(2, WAICOrgans.WOODEN_SPLEEN.get())
        .setSecondRow(3, WAICOrgans.WOODEN_KIDNEY.get())
        .setSecondRow(5, WAICOrgans.WOODEN_KIDNEY.get())
        .setSecondRow(6, WAICOrgans.WOODEN_LIVER.get())
        .setSecondRow(8, WAICOrgans.WOODEN_MUSCLE.get())
        .setThirdRow(0, WAICOrgans.WOODEN_MUSCLE.get())
        .setThirdRow(1, WAICOrgans.WOODEN_MUSCLE.get())
        .setThirdRow(2, WAICOrgans.WOODEN_INTESTINE.get())
        .setThirdRow(3, WAICOrgans.WOODEN_INTESTINE.get())
        .setThirdRow(4, WAICOrgans.WOODEN_STOMACH.get())
        .setThirdRow(5, WAICOrgans.WOODEN_INTESTINE.get())
        .setThirdRow(6, WAICOrgans.WOODEN_INTESTINE.get())
        .setThirdRow(7, WAICOrgans.WOODEN_MUSCLE.get())
        .setThirdRow(8, WAICOrgans.WOODEN_MUSCLE.get());

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(WhoAmICore.of(path));
    }
}

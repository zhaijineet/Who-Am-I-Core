package net.zhaiji.who_am_i_core.manager;

import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

public class WAICChestCavityTypeManager {
    // 幻想种胸腔
    public static final ChestCavityType FANTASTICAL = ChestCavityTypeManager.register("fantastical")
        .setFirstRow(0, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.FANTASTICAL_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.FANTASTICAL_APPENDIX.get())
        .setFirstRow(3, IceAndFireOrgans.FANTASTICAL_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.FANTASTICAL_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.FANTASTICAL_LUNG.get())
        .setFirstRow(7, IceAndFireOrgans.FANTASTICAL_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.FANTASTICAL_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.FANTASTICAL_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.FANTASTICAL_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.FANTASTICAL_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.FANTASTICAL_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.FANTASTICAL_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.FANTASTICAL_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.FANTASTICAL_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.FANTASTICAL_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.FANTASTICAL_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.FANTASTICAL_MUSCLE.get());
}

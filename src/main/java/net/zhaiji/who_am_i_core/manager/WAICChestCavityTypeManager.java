package net.zhaiji.who_am_i_core.manager;

import net.minecraft.network.chat.Component;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

public class WAICChestCavityTypeManager {
    // 幻想种胸腔
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

    /**
     * 获取胸腔类型的翻译键
     *
     * @param type 胸腔类型
     * @return 翻译键
     */
    public static String getTranslationKey(ChestCavityType type) {
        return "chest_cavity_type." + type.getId().getNamespace() + "." + type.getId().getPath();
    }

    /**
     * 获取胸腔类型的翻译组件
     *
     * @param type 胸腔类型
     * @return 国际化的胸腔类型名称
     */
    public static Component getTranslatedName(ChestCavityType type) {
        return Component.translatable(getTranslationKey(type));
    }

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(WhoAmICore.of(path));
    }
}

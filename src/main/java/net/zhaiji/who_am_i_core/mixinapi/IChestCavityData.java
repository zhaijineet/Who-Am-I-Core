package net.zhaiji.who_am_i_core.mixinapi;

public interface IChestCavityData {
    int BIT_CHESED = 1;
    int BIT_GEBURAH = 2;
    int BIT_MALKUTH = 4;

    boolean isTrophyUsed(int flag);

    void setTrophyUsed(int flag, boolean used);

    int getExpansionLevel();
}

package net.zhaiji.who_am_i_core.mixinapi;

public interface IChestCavityData {
    int BIT_FIRE_DRAGON = 1;
    int BIT_ICE_DRAGON = 2;
    int BIT_LIGHTNING_DRAGON = 4;
    int ALL_DRAGON_BLOOD_BITS = BIT_FIRE_DRAGON | BIT_ICE_DRAGON | BIT_LIGHTNING_DRAGON;
    int MAX_EXPANSION_LEVEL = 3;

    boolean isDragonBloodUsed(int flag);

    /**
     * 获取原始龙血标记位值，供网络同步使用
     */
    int getDragonBloodFlags();

    /**
     * 设置原始龙血标记位值，供网络同步使用
     *
     * @param flags 位值
     */
    void setDragonBloodFlags(int flags);

    int getExpansionLevel();
}

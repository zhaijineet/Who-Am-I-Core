package net.zhaiji.who_am_i_core.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;

/**
 * 四体液学说数据
 * <p>
 * 包含四种体液：血液、黄胆汁、黑胆汁、粘液，每种体液有当前值和最大值。
 * </p>
 */
public class HumoursData implements INBTSerializable<CompoundTag> {
    public static final float DEFAULT_MAX = 100;

    public static final float DEFAULT_BASE = 0;

    /**
     * 用于 NeoForge Attachment 内置客户端同步的 StreamCodec
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, HumoursData> STREAM_CODEC = StreamCodec.of(
        (buffer, value) -> {
            buffer.writeFloat(value.maxBlood);
            buffer.writeFloat(value.blood);
            buffer.writeFloat(value.maxYellowBile);
            buffer.writeFloat(value.yellowBile);
            buffer.writeFloat(value.maxBlackBile);
            buffer.writeFloat(value.blackBile);
            buffer.writeFloat(value.maxPhlegm);
            buffer.writeFloat(value.phlegm);
        },
        buffer -> {
            HumoursData data = new HumoursData(null);
            data.maxBlood = buffer.readFloat();
            data.blood = buffer.readFloat();
            data.maxYellowBile = buffer.readFloat();
            data.yellowBile = buffer.readFloat();
            data.maxBlackBile = buffer.readFloat();
            data.blackBile = buffer.readFloat();
            data.maxPhlegm = buffer.readFloat();
            data.phlegm = buffer.readFloat();
            return data;
        }
    );
    // 血液
    private float maxBlood;
    private float blood;
    // 黄胆汁
    private float maxYellowBile;
    private float yellowBile;
    // 黑胆汁
    private float maxBlackBile;
    private float blackBile;
    // 粘液
    private float maxPhlegm;
    private float phlegm;

    public HumoursData(IAttachmentHolder holder) {
        maxBlood = DEFAULT_MAX;
//        blood = DEFAULT_BASE;
        maxYellowBile = DEFAULT_MAX;
//        yellowBile = DEFAULT_BASE;
        maxBlackBile = DEFAULT_MAX;
//        blackBile = DEFAULT_BASE;
        maxPhlegm = DEFAULT_MAX;
//        phlegm = DEFAULT_BASE;
    }

    // ===== 血液 =====

    public float getMaxBlood() {
        return maxBlood;
    }

    public void setMaxBlood(float max) {
        this.maxBlood = max;
        this.blood = Math.min(blood, max);
    }

    public void resetMaxBlood() {
        this.maxBlood = DEFAULT_MAX;
    }

    public float getBlood() {
        return blood;
    }

    public void setBlood(float current) {
        this.blood = Math.min(current, maxBlood);
    }

    public float getBloodRatio() {
        if (maxBlood <= 0) return 0;
        return blood / maxBlood;
    }

    // ===== 黄胆汁 =====

    public float getMaxYellowBile() {
        return maxYellowBile;
    }

    public void setMaxYellowBile(float max) {
        this.maxYellowBile = max;
        this.yellowBile = Math.min(yellowBile, max);
    }

    public void resetMaxYellowBile() {
        this.maxYellowBile = DEFAULT_MAX;
    }

    public float getYellowBile() {
        return yellowBile;
    }

    public void setYellowBile(float current) {
        this.yellowBile = Math.min(current, maxYellowBile);
    }

    public float getYellowBileRatio() {
        if (maxYellowBile <= 0) return 0;
        return yellowBile / maxYellowBile;
    }

    // ===== 黑胆汁 =====

    public float getMaxBlackBile() {
        return maxBlackBile;
    }

    public void setMaxBlackBile(float max) {
        this.maxBlackBile = max;
        this.blackBile = Math.min(blackBile, max);
    }

    public void resetMaxBlackBile() {
        this.maxBlackBile = DEFAULT_MAX;
    }

    public float getBlackBile() {
        return blackBile;
    }

    public void setBlackBile(float current) {
        this.blackBile = Math.min(current, maxBlackBile);
    }

    public float getBlackBileRatio() {
        if (maxBlackBile <= 0) return 0;
        return blackBile / maxBlackBile;
    }

    // ===== 粘液 =====

    public float getMaxPhlegm() {
        return maxPhlegm;
    }

    public void setMaxPhlegm(float max) {
        this.maxPhlegm = max;
        this.phlegm = Math.min(phlegm, max);
    }

    public void resetMaxPhlegm() {
        this.maxPhlegm = DEFAULT_MAX;
    }

    public float getPhlegm() {
        return phlegm;
    }

    public void setPhlegm(float current) {
        this.phlegm = Math.min(current, maxPhlegm);
    }

    public float getPhlegmRatio() {
        if (maxPhlegm <= 0) return 0;
        return phlegm / maxPhlegm;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("max_blood", maxBlood);
        tag.putFloat("blood", blood);
        tag.putFloat("max_yellow_bile", maxYellowBile);
        tag.putFloat("yellow_bile", yellowBile);
        tag.putFloat("max_black_bile", maxBlackBile);
        tag.putFloat("black_bile", blackBile);
        tag.putFloat("max_phlegm", maxPhlegm);
        tag.putFloat("phlegm", phlegm);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        maxBlood = tag.getFloat("max_blood");
        blood = tag.getFloat("blood");
        maxYellowBile = tag.getFloat("max_yellow_bile");
        yellowBile = tag.getFloat("yellow_bile");
        maxBlackBile = tag.getFloat("max_black_bile");
        blackBile = tag.getFloat("black_bile");
        maxPhlegm = tag.getFloat("max_phlegm");
        phlegm = tag.getFloat("phlegm");
    }
}

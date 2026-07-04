package net.zhaiji.who_am_i_core.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.zhaiji.who_am_i_core.register.WAICAttachment;

/**
 * 四体液学说数据
 * <p>
 * 包含四种体液：血液、黄胆汁、黑胆汁、粘液，每种体液有当前值和最大值。
 * </p>
 */
public class HumoursData implements INBTSerializable<CompoundTag> {
    public static final float DEFAULT_MAX = 100;

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
        maxYellowBile = DEFAULT_MAX;
        maxBlackBile = DEFAULT_MAX;
        maxPhlegm = DEFAULT_MAX;
    }

    // ===== 静态操作方法 =====

    /**
     * 便捷获取实体的四体液数据
     */
    public static HumoursData get(LivingEntity entity) {
        return entity.getData(WAICAttachment.HUMOURS);
    }

    /**
     * 插入血液
     */
    public static float insertBlood(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.insertBlood(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 提取血液
     */
    public static float extractBlood(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.extractBlood(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 设置血液当前值
     */
    public static void setBlood(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        data.setBlood(value);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 设置血液最大值
     */
    public static void setMaxBlood(LivingEntity entity, float max) {
        HumoursData data = get(entity);
        data.setMaxBlood(max);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 增加血液最大值
     */
    public static void addMaxBlood(LivingEntity entity, float delta) {
        HumoursData data = get(entity);
        data.setMaxBlood(data.maxBlood + delta);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 重置血液最大值为默认值
     */
    public static void resetMaxBlood(LivingEntity entity) {
        HumoursData data = get(entity);
        data.resetMaxBlood();
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 插入黄胆汁
     */
    public static float insertYellowBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.insertYellowBile(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 提取黄胆汁
     */
    public static float extractYellowBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.extractYellowBile(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 设置黄胆汁当前值
     */
    public static void setYellowBile(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        data.setYellowBile(value);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 设置黄胆汁最大值
     */
    public static void setMaxYellowBile(LivingEntity entity, float max) {
        HumoursData data = get(entity);
        data.setMaxYellowBile(max);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 增加黄胆汁最大值
     */
    public static void addMaxYellowBile(LivingEntity entity, float delta) {
        HumoursData data = get(entity);
        data.setMaxYellowBile(data.maxYellowBile + delta);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 重置黄胆汁最大值为默认值
     */
    public static void resetMaxYellowBile(LivingEntity entity) {
        HumoursData data = get(entity);
        data.resetMaxYellowBile();
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 插入黑胆汁
     */
    public static float insertBlackBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.insertBlackBile(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 提取黑胆汁
     */
    public static float extractBlackBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.extractBlackBile(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 设置黑胆汁当前值
     */
    public static void setBlackBile(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        data.setBlackBile(value);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 设置黑胆汁最大值
     */
    public static void setMaxBlackBile(LivingEntity entity, float max) {
        HumoursData data = get(entity);
        data.setMaxBlackBile(max);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 增加黑胆汁最大值
     */
    public static void addMaxBlackBile(LivingEntity entity, float delta) {
        HumoursData data = get(entity);
        data.setMaxBlackBile(data.maxBlackBile + delta);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 重置黑胆汁最大值为默认值
     */
    public static void resetMaxBlackBile(LivingEntity entity) {
        HumoursData data = get(entity);
        data.resetMaxBlackBile();
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 插入粘液
     */
    public static float insertPhlegm(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.insertPhlegm(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 提取粘液
     */
    public static float extractPhlegm(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float result = data.extractPhlegm(amount, simulate);
        if (!simulate && result > 0) entity.setData(WAICAttachment.HUMOURS, data);
        return result;
    }

    /**
     * 设置粘液当前值
     */
    public static void setPhlegm(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        data.setPhlegm(value);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 设置粘液最大值
     */
    public static void setMaxPhlegm(LivingEntity entity, float max) {
        HumoursData data = get(entity);
        data.setMaxPhlegm(max);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 增加粘液最大值
     */
    public static void addMaxPhlegm(LivingEntity entity, float delta) {
        HumoursData data = get(entity);
        data.setMaxPhlegm(data.maxPhlegm + delta);
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 重置粘液最大值为默认值
     */
    public static void resetMaxPhlegm(LivingEntity entity) {
        HumoursData data = get(entity);
        data.resetMaxPhlegm();
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    /**
     * 清空所有体液的当前值，保留上限
     */
    public static void clearCurrentValues(LivingEntity entity) {
        HumoursData data = get(entity);
        data.clearCurrentValues();
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    // ===== 血液 =====

    public float getMaxBlood() {
        return maxBlood;
    }

    private void setMaxBlood(float max) {
        this.maxBlood = max;
        this.blood = Math.min(blood, max);
    }

    private void resetMaxBlood() {
        this.maxBlood = DEFAULT_MAX;
    }

    public float getBlood() {
        return blood;
    }

    private void setBlood(float current) {
        this.blood = Math.min(current, maxBlood);
    }

    public float getBloodRatio() {
        if (maxBlood <= 0) return 0;
        return blood / maxBlood;
    }

    public float getBloodSpace() {
        return Math.max(0, maxBlood - blood);
    }

    public boolean isBloodEmpty() {
        return blood <= 0;
    }

    public boolean isBloodFull() {
        return maxBlood <= 0 || blood >= maxBlood;
    }

    private float insertBlood(float amount, boolean simulate) {
        if (amount <= 0 || isBloodFull()) return 0;
        float toInsert = Math.min(amount, maxBlood - blood);
        if (!simulate) {
            blood += toInsert;
        }
        return toInsert;
    }

    private float extractBlood(float amount, boolean simulate) {
        if (amount <= 0 || isBloodEmpty()) return 0;
        float toExtract = Math.min(amount, blood);
        if (!simulate) {
            blood -= toExtract;
        }
        return toExtract;
    }

    private float fillBlood(boolean simulate) {
        if (maxBlood <= 0) return 0;
        float toFill = Math.max(0, maxBlood - blood);
        if (!simulate) {
            blood = maxBlood;
        }
        return toFill;
    }

    private float drainBlood(boolean simulate) {
        if (blood <= 0) return 0;
        float toDrain = blood;
        if (!simulate) {
            blood = 0;
        }
        return toDrain;
    }

    // ===== 黄胆汁 =====

    public float getMaxYellowBile() {
        return maxYellowBile;
    }

    private void setMaxYellowBile(float max) {
        this.maxYellowBile = max;
        this.yellowBile = Math.min(yellowBile, max);
    }

    private void resetMaxYellowBile() {
        this.maxYellowBile = DEFAULT_MAX;
    }

    public float getYellowBile() {
        return yellowBile;
    }

    private void setYellowBile(float current) {
        this.yellowBile = Math.min(current, maxYellowBile);
    }

    public float getYellowBileRatio() {
        if (maxYellowBile <= 0) return 0;
        return yellowBile / maxYellowBile;
    }

    public float getYellowBileSpace() {
        return Math.max(0, maxYellowBile - yellowBile);
    }

    public boolean isYellowBileEmpty() {
        return yellowBile <= 0;
    }

    public boolean isYellowBileFull() {
        return maxYellowBile <= 0 || yellowBile >= maxYellowBile;
    }

    private float insertYellowBile(float amount, boolean simulate) {
        if (amount <= 0 || isYellowBileFull()) return 0;
        float toInsert = Math.min(amount, maxYellowBile - yellowBile);
        if (!simulate) {
            yellowBile += toInsert;
        }
        return toInsert;
    }

    private float extractYellowBile(float amount, boolean simulate) {
        if (amount <= 0 || isYellowBileEmpty()) return 0;
        float toExtract = Math.min(amount, yellowBile);
        if (!simulate) {
            yellowBile -= toExtract;
        }
        return toExtract;
    }

    private float fillYellowBile(boolean simulate) {
        if (maxYellowBile <= 0) return 0;
        float toFill = Math.max(0, maxYellowBile - yellowBile);
        if (!simulate) {
            yellowBile = maxYellowBile;
        }
        return toFill;
    }

    private float drainYellowBile(boolean simulate) {
        if (yellowBile <= 0) return 0;
        float toDrain = yellowBile;
        if (!simulate) {
            yellowBile = 0;
        }
        return toDrain;
    }

    // ===== 黑胆汁 =====

    public float getMaxBlackBile() {
        return maxBlackBile;
    }

    private void setMaxBlackBile(float max) {
        this.maxBlackBile = max;
        this.blackBile = Math.min(blackBile, max);
    }

    private void resetMaxBlackBile() {
        this.maxBlackBile = DEFAULT_MAX;
    }

    public float getBlackBile() {
        return blackBile;
    }

    private void setBlackBile(float current) {
        this.blackBile = Math.min(current, maxBlackBile);
    }

    public float getBlackBileRatio() {
        if (maxBlackBile <= 0) return 0;
        return blackBile / maxBlackBile;
    }

    public float getBlackBileSpace() {
        return Math.max(0, maxBlackBile - blackBile);
    }

    public boolean isBlackBileEmpty() {
        return blackBile <= 0;
    }

    public boolean isBlackBileFull() {
        return maxBlackBile <= 0 || blackBile >= maxBlackBile;
    }

    private float insertBlackBile(float amount, boolean simulate) {
        if (amount <= 0 || isBlackBileFull()) return 0;
        float toInsert = Math.min(amount, maxBlackBile - blackBile);
        if (!simulate) {
            blackBile += toInsert;
        }
        return toInsert;
    }

    private float extractBlackBile(float amount, boolean simulate) {
        if (amount <= 0 || isBlackBileEmpty()) return 0;
        float toExtract = Math.min(amount, blackBile);
        if (!simulate) {
            blackBile -= toExtract;
        }
        return toExtract;
    }

    private float fillBlackBile(boolean simulate) {
        if (maxBlackBile <= 0) return 0;
        float toFill = Math.max(0, maxBlackBile - blackBile);
        if (!simulate) {
            blackBile = maxBlackBile;
        }
        return toFill;
    }

    private float drainBlackBile(boolean simulate) {
        if (blackBile <= 0) return 0;
        float toDrain = blackBile;
        if (!simulate) {
            blackBile = 0;
        }
        return toDrain;
    }

    // ===== 粘液 =====

    public float getMaxPhlegm() {
        return maxPhlegm;
    }

    private void setMaxPhlegm(float max) {
        this.maxPhlegm = max;
        this.phlegm = Math.min(phlegm, max);
    }

    private void resetMaxPhlegm() {
        this.maxPhlegm = DEFAULT_MAX;
    }

    public float getPhlegm() {
        return phlegm;
    }

    private void setPhlegm(float current) {
        this.phlegm = Math.min(current, maxPhlegm);
    }

    public float getPhlegmRatio() {
        if (maxPhlegm <= 0) return 0;
        return phlegm / maxPhlegm;
    }

    public float getPhlegmSpace() {
        return Math.max(0, maxPhlegm - phlegm);
    }

    public boolean isPhlegmEmpty() {
        return phlegm <= 0;
    }

    public boolean isPhlegmFull() {
        return maxPhlegm <= 0 || phlegm >= maxPhlegm;
    }

    private float insertPhlegm(float amount, boolean simulate) {
        if (amount <= 0 || isPhlegmFull()) return 0;
        float toInsert = Math.min(amount, maxPhlegm - phlegm);
        if (!simulate) {
            phlegm += toInsert;
        }
        return toInsert;
    }

    private float extractPhlegm(float amount, boolean simulate) {
        if (amount <= 0 || isPhlegmEmpty()) return 0;
        float toExtract = Math.min(amount, phlegm);
        if (!simulate) {
            phlegm -= toExtract;
        }
        return toExtract;
    }

    private float fillPhlegm(boolean simulate) {
        if (maxPhlegm <= 0) return 0;
        float toFill = Math.max(0, maxPhlegm - phlegm);
        if (!simulate) {
            phlegm = maxPhlegm;
        }
        return toFill;
    }

    private float drainPhlegm(boolean simulate) {
        if (phlegm <= 0) return 0;
        float toDrain = phlegm;
        if (!simulate) {
            phlegm = 0;
        }
        return toDrain;
    }

    private void clearCurrentValues() {
        blood = 0;
        yellowBile = 0;
        blackBile = 0;
        phlegm = 0;
    }

    public boolean isAnyHumourEmpty() {
        return blood <= 0 || yellowBile <= 0 || blackBile <= 0 || phlegm <= 0;
    }

    public boolean isAllHumourEmpty() {
        return blood <= 0 && yellowBile <= 0 && blackBile <= 0 && phlegm <= 0;
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

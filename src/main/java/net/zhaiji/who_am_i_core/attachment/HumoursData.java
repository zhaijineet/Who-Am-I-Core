package net.zhaiji.who_am_i_core.attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.zhaiji.who_am_i_core.register.WAICAttachment;
import net.zhaiji.who_am_i_core.register.WAICAttribute;

/**
 * 保存四体液的当前值。
 */
public class HumoursData implements INBTSerializable<CompoundTag> {
    public static final StreamCodec<RegistryFriendlyByteBuf, HumoursData> STREAM_CODEC = StreamCodec.of(
        (buffer, value) -> {
            buffer.writeFloat(value.blood);
            buffer.writeFloat(value.yellowBile);
            buffer.writeFloat(value.blackBile);
            buffer.writeFloat(value.phlegm);
        },
        buffer -> {
            HumoursData data = new HumoursData(null);
            data.blood = buffer.readFloat();
            data.yellowBile = buffer.readFloat();
            data.blackBile = buffer.readFloat();
            data.phlegm = buffer.readFloat();
            return data;
        }
    );

    private float blood;
    private float yellowBile;
    private float blackBile;
    private float phlegm;

    public HumoursData(IAttachmentHolder holder) {
    }

    public static HumoursData get(LivingEntity entity) {
        return entity.getData(WAICAttachment.HUMOURS);
    }

    /**
     * 按实体当前属性上限夹紧四体液当前值，并在有变化时同步一次Attachment。
     */
    public static void clampCurrentValues(LivingEntity entity) {
        HumoursData data = get(entity);
        float clampedBlood = Math.clamp(data.blood, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_BLOOD));
        float clampedYellowBile = Math.clamp(data.yellowBile, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE));
        float clampedBlackBile = Math.clamp(data.blackBile, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_BLACK_BILE));
        float clampedPhlegm = Math.clamp(data.phlegm, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_PHLEGM));
        if (data.blood == clampedBlood && data.yellowBile == clampedYellowBile && data.blackBile == clampedBlackBile && data.phlegm == clampedPhlegm) {
            return;
        }
        data.blood = clampedBlood;
        data.yellowBile = clampedYellowBile;
        data.blackBile = clampedBlackBile;
        data.phlegm = clampedPhlegm;
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    public static float getBloodRatio(LivingEntity entity) {
        return ratio(get(entity).blood, (float) entity.getAttributeValue(WAICAttribute.MAX_BLOOD));
    }

    public static float getYellowBileRatio(LivingEntity entity) {
        return ratio(get(entity).yellowBile, (float) entity.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE));
    }

    public static float getBlackBileRatio(LivingEntity entity) {
        return ratio(get(entity).blackBile, (float) entity.getAttributeValue(WAICAttribute.MAX_BLACK_BILE));
    }

    public static float getPhlegmRatio(LivingEntity entity) {
        return ratio(get(entity).phlegm, (float) entity.getAttributeValue(WAICAttribute.MAX_PHLEGM));
    }

    public static float getBloodSpace(LivingEntity entity) {
        return Math.max(0, (float) entity.getAttributeValue(WAICAttribute.MAX_BLOOD) - get(entity).blood);
    }

    public static float getYellowBileSpace(LivingEntity entity) {
        return Math.max(0, (float) entity.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE) - get(entity).yellowBile);
    }

    public static float getBlackBileSpace(LivingEntity entity) {
        return Math.max(0, (float) entity.getAttributeValue(WAICAttribute.MAX_BLACK_BILE) - get(entity).blackBile);
    }

    public static float getPhlegmSpace(LivingEntity entity) {
        return Math.max(0, (float) entity.getAttributeValue(WAICAttribute.MAX_PHLEGM) - get(entity).phlegm);
    }

    public static boolean isBloodFull(LivingEntity entity) {
        return isFull(get(entity).blood, (float) entity.getAttributeValue(WAICAttribute.MAX_BLOOD));
    }

    public static boolean isYellowBileFull(LivingEntity entity) {
        return isFull(get(entity).yellowBile, (float) entity.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE));
    }

    public static boolean isBlackBileFull(LivingEntity entity) {
        return isFull(get(entity).blackBile, (float) entity.getAttributeValue(WAICAttribute.MAX_BLACK_BILE));
    }

    public static boolean isPhlegmFull(LivingEntity entity) {
        return isFull(get(entity).phlegm, (float) entity.getAttributeValue(WAICAttribute.MAX_PHLEGM));
    }

    public static float insertBlood(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float inserted = data.insertBlood(amount, (float) entity.getAttributeValue(WAICAttribute.MAX_BLOOD), simulate);
        syncChanged(entity, data, inserted, simulate);
        return inserted;
    }

    public static float extractBlood(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float extracted = data.extractBlood(amount, simulate);
        syncChanged(entity, data, extracted, simulate);
        return extracted;
    }

    public static void setBlood(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        float clampedValue = Math.clamp(value, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_BLOOD));
        if (data.blood == clampedValue) return;
        data.blood = clampedValue;
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    public static float insertYellowBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float inserted = data.insertYellowBile(amount, (float) entity.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE), simulate);
        syncChanged(entity, data, inserted, simulate);
        return inserted;
    }

    public static float extractYellowBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float extracted = data.extractYellowBile(amount, simulate);
        syncChanged(entity, data, extracted, simulate);
        return extracted;
    }

    public static void setYellowBile(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        float clampedValue = Math.clamp(value, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_YELLOW_BILE));
        if (data.yellowBile == clampedValue) return;
        data.yellowBile = clampedValue;
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    public static float insertBlackBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float inserted = data.insertBlackBile(amount, (float) entity.getAttributeValue(WAICAttribute.MAX_BLACK_BILE), simulate);
        syncChanged(entity, data, inserted, simulate);
        return inserted;
    }

    public static float extractBlackBile(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float extracted = data.extractBlackBile(amount, simulate);
        syncChanged(entity, data, extracted, simulate);
        return extracted;
    }

    public static void setBlackBile(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        float clampedValue = Math.clamp(value, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_BLACK_BILE));
        if (data.blackBile == clampedValue) return;
        data.blackBile = clampedValue;
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    public static float insertPhlegm(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float inserted = data.insertPhlegm(amount, (float) entity.getAttributeValue(WAICAttribute.MAX_PHLEGM), simulate);
        syncChanged(entity, data, inserted, simulate);
        return inserted;
    }

    public static float extractPhlegm(LivingEntity entity, float amount, boolean simulate) {
        HumoursData data = get(entity);
        float extracted = data.extractPhlegm(amount, simulate);
        syncChanged(entity, data, extracted, simulate);
        return extracted;
    }

    public static void setPhlegm(LivingEntity entity, float value) {
        HumoursData data = get(entity);
        float clampedValue = Math.clamp(value, 0, (float) entity.getAttributeValue(WAICAttribute.MAX_PHLEGM));
        if (data.phlegm == clampedValue) return;
        data.phlegm = clampedValue;
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    public static void clearCurrentValues(LivingEntity entity) {
        HumoursData data = get(entity);
        if (data.isAllHumourEmpty()) return;
        data.blood = 0;
        data.yellowBile = 0;
        data.blackBile = 0;
        data.phlegm = 0;
        entity.setData(WAICAttachment.HUMOURS, data);
    }

    private static float ratio(float current, float maximum) {
        return maximum <= 0 ? 0 : current / maximum;
    }

    private static boolean isFull(float current, float maximum) {
        return maximum <= 0 || current >= maximum;
    }

    private static void syncChanged(LivingEntity entity, HumoursData data, float changedAmount, boolean simulate) {
        if (!simulate && changedAmount > 0) {
            entity.setData(WAICAttachment.HUMOURS, data);
        }
    }

    public float getBlood() {
        return blood;
    }

    public boolean isBloodEmpty() {
        return blood <= 0;
    }

    public float getYellowBile() {
        return yellowBile;
    }

    public boolean isYellowBileEmpty() {
        return yellowBile <= 0;
    }

    public float getBlackBile() {
        return blackBile;
    }

    public boolean isBlackBileEmpty() {
        return blackBile <= 0;
    }

    public float getPhlegm() {
        return phlegm;
    }

    public boolean isPhlegmEmpty() {
        return phlegm <= 0;
    }

    public boolean isAnyHumourEmpty() {
        return blood <= 0 || yellowBile <= 0 || blackBile <= 0 || phlegm <= 0;
    }

    public boolean isAllHumourEmpty() {
        return blood <= 0 && yellowBile <= 0 && blackBile <= 0 && phlegm <= 0;
    }

    private float insertBlood(float amount, float maximum, boolean simulate) {
        if (amount <= 0 || isFull(blood, maximum)) return 0;
        float inserted = Math.min(amount, maximum - blood);
        if (!simulate) blood += inserted;
        return inserted;
    }

    private float extractBlood(float amount, boolean simulate) {
        if (amount <= 0 || blood <= 0) return 0;
        float extracted = Math.min(amount, blood);
        if (!simulate) blood -= extracted;
        return extracted;
    }

    private float insertYellowBile(float amount, float maximum, boolean simulate) {
        if (amount <= 0 || isFull(yellowBile, maximum)) return 0;
        float inserted = Math.min(amount, maximum - yellowBile);
        if (!simulate) yellowBile += inserted;
        return inserted;
    }

    private float extractYellowBile(float amount, boolean simulate) {
        if (amount <= 0 || yellowBile <= 0) return 0;
        float extracted = Math.min(amount, yellowBile);
        if (!simulate) yellowBile -= extracted;
        return extracted;
    }

    private float insertBlackBile(float amount, float maximum, boolean simulate) {
        if (amount <= 0 || isFull(blackBile, maximum)) return 0;
        float inserted = Math.min(amount, maximum - blackBile);
        if (!simulate) blackBile += inserted;
        return inserted;
    }

    private float extractBlackBile(float amount, boolean simulate) {
        if (amount <= 0 || blackBile <= 0) return 0;
        float extracted = Math.min(amount, blackBile);
        if (!simulate) blackBile -= extracted;
        return extracted;
    }

    private float insertPhlegm(float amount, float maximum, boolean simulate) {
        if (amount <= 0 || isFull(phlegm, maximum)) return 0;
        float inserted = Math.min(amount, maximum - phlegm);
        if (!simulate) phlegm += inserted;
        return inserted;
    }

    private float extractPhlegm(float amount, boolean simulate) {
        if (amount <= 0 || phlegm <= 0) return 0;
        float extracted = Math.min(amount, phlegm);
        if (!simulate) phlegm -= extracted;
        return extracted;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("blood", blood);
        tag.putFloat("yellow_bile", yellowBile);
        tag.putFloat("black_bile", blackBile);
        tag.putFloat("phlegm", phlegm);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        blood = Math.max(0, tag.getFloat("blood"));
        yellowBile = Math.max(0, tag.getFloat("yellow_bile"));
        blackBile = Math.max(0, tag.getFloat("black_bile"));
        phlegm = Math.max(0, tag.getFloat("phlegm"));
    }
}

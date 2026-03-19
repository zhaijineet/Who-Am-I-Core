package net.zhaiji.who_am_i_core.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.zhaiji.who_am_i_core.manager.EdibleConditionManager;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * 可食用条件
 * <p>
 * 通过 Builder 模式构造，定义哪些物品可以被食用以及食用后的效果
 * </p>
 */
public class EdibleCondition {
    private static final BiConsumer<LivingEntity, ItemStack> EMPTY_ON_EAT = (entity, stack) -> {
    };
    private final Predicate<ItemStack> itemCheck;
    private final Predicate<LivingEntity> entityCheck;
    private final BiConsumer<LivingEntity, ItemStack> onEat;
    private final UseAnim useAnimation;
    private final int useDuration;

    private EdibleCondition(Builder builder) {
        this.itemCheck = builder.itemCheck;
        this.entityCheck = builder.entityCheck;
        this.onEat = builder.onEat;
        this.useAnimation = builder.useAnimation;
        this.useDuration = builder.useDuration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean checkItem(ItemStack stack) {
        return itemCheck.test(stack);
    }

    public boolean checkEntity(LivingEntity entity) {
        return entityCheck.test(entity);
    }

    public boolean canEat(LivingEntity entity, ItemStack stack) {
        return checkEntity(entity) && checkItem(stack);
    }

    public void onEat(LivingEntity entity, ItemStack stack) {
        onEat.accept(entity, stack);
    }

    public UseAnim getUseAnimation() {
        return useAnimation;
    }

    public int getUseDuration() {
        return useDuration;
    }

    public static class Builder {
        private Predicate<ItemStack> itemCheck = stack -> false;
        private Predicate<LivingEntity> entityCheck = entity -> true;
        private BiConsumer<LivingEntity, ItemStack> onEat = EMPTY_ON_EAT;
        private UseAnim useAnimation = UseAnim.EAT;
        private int useDuration = 32;

        private Builder() {
        }

        /**
         * 设置物品匹配条件
         *
         * @param itemCheck 物品谓词
         */
        public Builder itemCheck(Predicate<ItemStack> itemCheck) {
            this.itemCheck = itemCheck;
            return this;
        }

        /**
         * 设置实体匹配条件
         *
         * @param entityCheck 实体谓词
         */
        public Builder entityCheck(Predicate<LivingEntity> entityCheck) {
            this.entityCheck = entityCheck;
            return this;
        }

        /**
         * 设置食用完成后的回调
         * <pre>
         * note:
         *     由于需要遵守各种api的使用
         *     所以此处要么自己创建FoodProperties调用entity的eat方法
         *     要么在执行完效果之后自己播放食用音效
         * </pre>
         *
         * @param onEat 食用回调
         */
        public Builder onEat(BiConsumer<LivingEntity, ItemStack> onEat) {
            this.onEat = onEat;
            return this;
        }

        /**
         * 设置食用动画，默认为 {@link UseAnim#EAT}
         *
         * @param useAnimation 食用动画类型
         */
        public Builder useAnimation(UseAnim useAnimation) {
            this.useAnimation = useAnimation;
            return this;
        }

        /**
         * 设置食用时长（ticks），默认为 32
         */
        public Builder useDuration(int useDuration) {
            this.useDuration = useDuration;
            return this;
        }

        /**
         * 快速食用 16 ticks
         */
        public Builder fastEat() {
            this.useDuration = 16;
            return this;
        }

        public EdibleCondition build() {
            EdibleCondition edibleCondition = new EdibleCondition(this);
            EdibleConditionManager.register(edibleCondition);
            return edibleCondition;
        }
    }
}

package net.zhaiji.who_am_i_core.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.zhaiji.who_am_i_core.manager.EdibleConditionManager;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 可食用条件
 * <p>
 * 通过 Builder 模式构造，定义哪些物品可以被食用以及食用后的效果。
 * </p>
 * 示例用法：
 * <pre>
 * {@code
 * EdibleCondition.builder()
 *     .matchesItem(stack -> stack.is(Items.DIRT))
 *     .matchesEntity(entity -> entity.hasEffect(MobEffects.HUNGER))
 *     .onEat((entity, stack) -> {
 *         entity.eat(
 *         entity.level(),
 *         stack,
 *         new FoodProperties.Builder().nutrition(1).build()
 *         );
 *     })
 *     .eatAnimation()
 *     .useDuration(32)
 *     .build();
 * }
 * </pre>
 *
 * @apiNote 禁止在回调中使用 {@link ItemStack} 的 use、finishUsingItem、getUseAnimation、getUseDuration 方法
 * <p>
 * 应该使用 {@link Item} 的对应方法，否则会导致无限循环
 * </p>
 */
public class EdibleCondition {
    private static final Predicate<ItemStack> DEFAULT_ITEM_CHECK = stack -> false;
    private static final Predicate<LivingEntity> DEFAULT_ENTITY_CHECK = entity -> true;
    private static final BiFunction<LivingEntity, ItemStack, ItemStack> DEFAULT_ON_EAT = (entity, stack) ->
        stack.getItem().finishUsingItem(stack, entity.level(), entity);
    private static final Function<ItemStack, UseAnim> DEFAULT_USE_ANIMATION = stack -> stack.getItem().getUseAnimation(stack);
    private static final BiFunction<LivingEntity, ItemStack, Integer> DEFAULT_USE_DURATION = (entity, stack) -> {
        int duration = stack.getItem().getUseDuration(stack, entity);
        return duration > 0 ? duration : 32;
    };

    private final Predicate<ItemStack> itemCheck;
    private final Predicate<LivingEntity> entityCheck;
    private final BiFunction<LivingEntity, ItemStack, ItemStack> onEat;
    private final Function<ItemStack, UseAnim> useAnimation;
    private final BiFunction<LivingEntity, ItemStack, Integer> useDuration;

    private EdibleCondition(Builder builder) {
        this.itemCheck = builder.itemCheck;
        this.entityCheck = builder.entityCheck;
        this.onEat = builder.onEat;
        this.useAnimation = builder.useAnimation;
        this.useDuration = builder.useDuration;
    }

    /**
     * 创建可食用条件的 Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public boolean matchesItem(ItemStack stack) {
        return itemCheck.test(stack);
    }

    public boolean matchesEntity(LivingEntity entity) {
        return entityCheck.test(entity);
    }

    public boolean canEat(LivingEntity entity, ItemStack stack) {
        return matchesEntity(entity) && matchesItem(stack);
    }

    public ItemStack onEat(LivingEntity entity, ItemStack stack) {
        return onEat.apply(entity, stack);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return useAnimation.apply(stack);
    }

    public int getUseDuration(LivingEntity entity, ItemStack stack) {
        return useDuration.apply(entity, stack);
    }

    public static class Builder {
        private Predicate<ItemStack> itemCheck = DEFAULT_ITEM_CHECK;
        private Predicate<LivingEntity> entityCheck = DEFAULT_ENTITY_CHECK;
        private BiFunction<LivingEntity, ItemStack, ItemStack> onEat = DEFAULT_ON_EAT;
        private Function<ItemStack, UseAnim> useAnimation = DEFAULT_USE_ANIMATION;
        private BiFunction<LivingEntity, ItemStack, Integer> useDuration = DEFAULT_USE_DURATION;

        private Builder() {
        }

        /**
         * 设置物品匹配条件
         *
         * @param itemCheck 物品谓词
         */
        public Builder matchesItem(Predicate<ItemStack> itemCheck) {
            this.itemCheck = itemCheck;
            return this;
        }

        /**
         * 设置实体匹配条件
         *
         * @param entityCheck 实体谓词
         */
        public Builder matchesEntity(Predicate<LivingEntity> entityCheck) {
            this.entityCheck = entityCheck;
            return this;
        }

        /**
         * 设置食用完成后的回调
         * <p>
         * 默认调用 {@link Item#finishUsingItem} 并返回结果
         * </p>
         * 禁止使用ItemStack的finishUsingItem，会导致无限循环
         * <pre>
         * note:
         *     由于需要遵守各种api的使用，加上mixin了ItemStack
         *     所以此处要么自己创建FoodProperties调用entity的eat方法
         *     要么在执行完效果之后自己播放食用音效，以及物品消耗
         * </pre>
         *
         * @param onEat 食用回调，返回处理后的 ItemStack
         */
        public Builder onEat(BiFunction<LivingEntity, ItemStack, ItemStack> onEat) {
            this.onEat = onEat;
            return this;
        }

        /**
         * 设置使用动画获取函数
         *
         * @param useAnimation 动画获取函数
         */
        public Builder useAnimation(Function<ItemStack, UseAnim> useAnimation) {
            this.useAnimation = useAnimation;
            return this;
        }

        /**
         * 设置固定的使用动画类型
         *
         * @param useAnimation 食用动画类型
         */
        public Builder useAnimation(UseAnim useAnimation) {
            this.useAnimation = stack -> useAnimation;
            return this;
        }

        /**
         * 设置动画为食用（UseAnim.EAT）
         */
        public Builder eatAnimation() {
            this.useAnimation = stack -> UseAnim.EAT;
            return this;
        }

        /**
         * 设置动画为饮用（UseAnim.DRINK）
         */
        public Builder drinkAnimation() {
            this.useAnimation = stack -> UseAnim.DRINK;
            return this;
        }

        /**
         * 设置使用时长获取函数（ticks）
         * <p>
         * 默认调用 {@link Item#getUseDuration}
         * </p>
         *
         * @param useDuration 时长获取函数
         */
        public Builder useDuration(BiFunction<LivingEntity, ItemStack, Integer> useDuration) {
            this.useDuration = useDuration;
            return this;
        }

        /**
         * 设置固定的使用时长（ticks）
         * <p>
         * 默认调用 {@link Item#getUseDuration}
         * </p>
         *
         * @param useDuration 使用时长
         */
        public Builder useDuration(int useDuration) {
            this.useDuration = (entity, stack) -> useDuration;
            return this;
        }

        /**
         * 快速食用，设置使用时长为 16 ticks
         */
        public Builder fastEat() {
            this.useDuration = (entity, stack) -> 16;
            return this;
        }

        /**
         * 构建并注册可食用条件
         */
        public EdibleCondition build() {
            EdibleCondition edibleCondition = new EdibleCondition(this);
            EdibleConditionManager.register(edibleCondition);
            return edibleCondition;
        }
    }
}

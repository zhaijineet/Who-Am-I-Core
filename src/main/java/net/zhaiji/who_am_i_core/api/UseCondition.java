package net.zhaiji.who_am_i_core.api;

import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.zhaiji.who_am_i_core.api.function.FinishUsingItemFunction;
import net.zhaiji.who_am_i_core.api.function.UseFunction;
import net.zhaiji.who_am_i_core.manager.UseConditionManager;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 可使用条件，通过 Builder 模式构造。
 * <p>
 * 三条互斥路径（按优先级）：持续使用（onFinishUsingItem）→ 瞬发使用（onUse）→ 仅覆盖属性。
 *
 * @apiNote 回调中禁止使用 {@link ItemStack} 的 use/finishUsingItem/getUseAnimation/getUseDuration，应使用 {@link Item} 的对应方法，否则会无限循环
 */
public class UseCondition {
    private static final Predicate<ItemStack> DEFAULT_ITEM_CHECK = stack -> false;
    private static final Predicate<LivingEntity> DEFAULT_ENTITY_CHECK = entity -> true;
    private static final Function<ItemStack, UseAnim> DEFAULT_USE_ANIMATION = stack -> stack.getItem().getUseAnimation(stack);
    private static final BiFunction<LivingEntity, ItemStack, Integer> DEFAULT_USE_DURATION = (entity, stack) -> {
        int duration = stack.getItem().getUseDuration(stack, entity);
        return duration > 0 ? duration : 32;
    };
    private final int priority;
    private final Predicate<ItemStack> itemCheck;
    private final Predicate<LivingEntity> entityCheck;
    private final FinishUsingItemFunction onFinishUsingItem;
    private final UseFunction onUse;
    private final Function<ItemStack, UseAnim> useAnimation;
    private final BiFunction<LivingEntity, ItemStack, Integer> useDuration;
//    @Nullable
//    private final FoodProperties foodProperties;

    private UseCondition(Builder builder) {
        this.priority = builder.priority;
        this.itemCheck = builder.itemCheck;
        this.entityCheck = builder.entityCheck;
        this.onFinishUsingItem = builder.onFinishUsingItem;
        this.onUse = builder.onUse;
        this.useAnimation = builder.useAnimation;
        this.useDuration = builder.useDuration;
//        this.foodProperties = builder.foodProperties;
    }

    /**
     * 创建可使用条件的 Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public boolean matchesItem(ItemStack stack) {
        return itemCheck.test(stack);
    }

    /**
     * 优先级，值越高越优先
     */
    public int priority() {
        return priority;
    }

    public boolean matchesEntity(LivingEntity entity) {
        return entityCheck.test(entity);
    }

    public boolean canUse(LivingEntity entity, ItemStack stack) {
        return matchesEntity(entity) && matchesItem(stack);
    }

    /**
     * 是否设置了 onFinishUsingItem 回调（持续使用路径）
     */
    public boolean hasOnFinishUsingItem() {
        return onFinishUsingItem != null;
    }

    /**
     * 是否设置了 onUse 回调（瞬发使用路径）
     */
    public boolean hasOnUse() {
        return onUse != null;
    }

//    /**
//     * 是否设置了食物属性
//     */
//    public boolean hasFoodProperties() {
//        return foodProperties != null;
//    }

    /**
     * 执行持续使用完成回调
     *
     * @throws IllegalStateException 如果未设置 onFinishUsingItem
     */
    public ItemStack onFinishUsingItem(LivingEntity entity, ItemStack stack) {
        if (onFinishUsingItem == null) {
            throw new IllegalStateException("onFinishUsingItem 未设置，请先通过 hasOnFinishUsingItem() 检查");
        }
        return onFinishUsingItem.apply(entity, stack, this);
    }

    /**
     * 执行瞬发使用回调
     *
     * @throws IllegalStateException 如果未设置 onUse
     */
    public InteractionResultHolder<ItemStack> onUse(Player player, ItemStack stack) {
        if (onUse == null) {
            throw new IllegalStateException("onUse 未设置，请先通过 hasOnUse() 检查");
        }
        return onUse.apply(player, stack, this);
    }

//    /**
//     * 获取此条件为该物品提供的食物属性
//     *
//     * @return 食物属性，如果没有提供则返回 null
//     */
//    @Nullable
//    public FoodProperties getFoodProperties() {
//        return foodProperties;
//    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return useAnimation.apply(stack);
    }

    public int getUseDuration(LivingEntity entity, ItemStack stack) {
        return useDuration.apply(entity, stack);
    }

    public static class Builder {
        private int priority;
        private Predicate<ItemStack> itemCheck = DEFAULT_ITEM_CHECK;
        private Predicate<LivingEntity> entityCheck = DEFAULT_ENTITY_CHECK;
        private FinishUsingItemFunction onFinishUsingItem = null;
        private UseFunction onUse = null;
        private Function<ItemStack, UseAnim> useAnimation = DEFAULT_USE_ANIMATION;
        private BiFunction<LivingEntity, ItemStack, Integer> useDuration = DEFAULT_USE_DURATION;
//        @Nullable
//        private FoodProperties foodProperties = null;

        private Builder() {
        }

        /**
         * 设置优先级，值越高越优先匹配
         * <p>
         * 当多个条件同时匹配时，优先级最高的条件胜出。默认 0。
         * </p>
         *
         * @param priority 优先级数值
         */
        public Builder priority(int priority) {
            this.priority = priority;
            return this;
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
         * 设置持续使用完成后的回调（持续使用路径）
         * <p>
         * 设置此项后，右键物品会进入持续使用状态，使用完成后调用此回调。
         * </p>
         * <p>
         * 禁止使用 ItemStack 的 finishUsingItem，会导致无限循环
         * </p>
         * <pre>
         * note:
         *     由于需要遵守各种api的使用，加上mixin了ItemStack
         *     所以此处要么自己创建FoodProperties调用entity的eat方法
         *     要么在执行完效果之后自己播放使用音效，以及物品消耗
         * </pre>
         *
         * @param onFinishUsingItem 使用完成回调，返回处理后的 ItemStack
         */
        public Builder onFinishUsingItem(FinishUsingItemFunction onFinishUsingItem) {
            this.onFinishUsingItem = onFinishUsingItem;
            return this;
        }

        /**
         * 设置瞬发使用回调（瞬发使用路径）
         * <p>
         * 设置此项后，右键物品会立即调用此回调并返回结果，不进入持续使用状态。
         * </p>
         *
         * @param onUse 瞬发使用回调，返回交互结果
         */
        public Builder onUse(UseFunction onUse) {
            this.onUse = onUse;
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
         * @param useAnimation 使用动画类型
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

//        /**
//         * 设置食物属性
//         * <p>
//         * 设置后可在回调中通过 {@code condition.getFoodProperties()} 获取。
//         * </p>
//         *
//         * @param foodProperties 食物属性
//         */
//        public Builder foodProperties(FoodProperties foodProperties) {
//            this.foodProperties = foodProperties;
//            return this;
//        }

//        /**
//         * 通过回调构建食物属性
//         * <p>
//         * 回调参数为 {@link FoodProperties.Builder}，配置完成后自动 build。
//         * </p>
//         *
//         * <pre>
//         * {@code
//         * .foodProperties(builder -> builder.nutrition(4).saturationModifier(0.3f))
//         * }
//         * </pre>
//         *
//         * @param builderConsumer 食物属性构建器回调
//         */
//        public Builder foodProperties(Consumer<FoodProperties.Builder> builderConsumer) {
//            FoodProperties.Builder builder = new FoodProperties.Builder();
//            builderConsumer.accept(builder);
//            this.foodProperties = builder.build();
//            return this;
//        }

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
         * 快速使用，设置使用时长为 16 ticks
         */
        public Builder fastEat() {
            this.useDuration = (entity, stack) -> 16;
            return this;
        }

        /**
         * 构建并注册可使用条件
         */
        public UseCondition build() {
            UseCondition useCondition = new UseCondition(this);
            UseConditionManager.register(useCondition);
            return useCondition;
        }
    }
}

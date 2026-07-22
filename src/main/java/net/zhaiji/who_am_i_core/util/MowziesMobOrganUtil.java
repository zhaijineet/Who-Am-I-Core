package net.zhaiji.who_am_i_core.util;

import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;

import java.util.List;

public class MowziesMobOrganUtil {
    /**
     * 荧光核心 tick：持续赋予夜视
     */
    public static void glowingCoreTick(ChestCavitySlotContext context) {
        if (context.entity().tickCount % 300 != 0) return;
        applyNightVisionEffect(context.entity());
    }

    /**
     * 荧光核心 added：植入时立即生效一次，避免玩家疑惑初始未触发
     */
    public static void glowingCoreAdded(ChestCavitySlotContext context) {
        applyNightVisionEffect(context.entity());
    }

    /**
     * 荧光核心 removed：仅清除器官自身施加的夜视，保护外部来源
     */
    public static void glowingCoreRemoved(ChestCavitySlotContext context) {
        MobEffectInstance nightVision = context.entity().getEffect(MobEffects.NIGHT_VISION);
        if (nightVision != null
            && nightVision.getAmplifier() == 0
            && nightVision.getDuration() <= 600) {
            context.entity().removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    private static void applyNightVisionEffect(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0, false, false));
    }

    /**
     * 钢铁守护者护心镜 - 属性修饰符：根据防御开平方向下取整提供格挡
     */
    public static void ferrousWroughtnautHeartMirrorModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        double defense = context.entity().getAttributeValue(InitAttribute.DEFENSE);
        double block = Math.floor(Math.sqrt(Math.max(0, defense)));
        modifiers.put(WAICAttribute.BLOCK, OrganAttributeUtil.createAddValueModifier(context.id(), block));
    }

    /**
     * 钢铁守护者护心镜 - 攻击
     */
    public static void ferrousWroughtnautHeartMirrorAttack(
        ChestCavitySlotContext context,
        LivingEntity target,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        LivingEntity entity = context.entity();
        // 玩家加冷却，实体直接加缓慢5
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(context.stack().getItem(), 20 * 3);
        } else {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 4));
        }
    }

    /**
     * 胸中新星 - 胸腔关闭
     */
    public static void chestNovaChestCavityClose(ChestCavitySlotContext slotContext) {
        ChestCavityData data = slotContext.data();
        List<Integer> adjacentSlots = ChestCavityUtil.getAdjacentSlots(slotContext.index(), slotContext.data().getSlots());
        for (int slot : adjacentSlots) {
            ItemStack adjacentStack = data.getStackInSlot(slot);
            if (adjacentStack.isEmpty()) continue;
            // 检查是否为器官
            if (!ChestCavityUtil.isOrgan(adjacentStack)) continue;
            // 检查是否为机械器官
            if (adjacentStack.is(WAICItemTagManager.MECHANICAL)) continue;
            // 检查是否为魔法器官
            if (adjacentStack.is(WAICItemTagManager.MAGIC)) continue;
            // 烧毁器官
            data.setStackInSlot(slot, ItemStack.EMPTY);
        }
        // 检查是否已有 ChestNovaTask，并且 task 不会被删除
        if (data.hasTaskIf(task -> task instanceof ChestNovaTask && !task.canRemove(slotContext.entity()))) {
            return;
        }
        // 创建并添加任务
        data.addTask(new ChestNovaTask(data, slotContext.index()));
    }

    /**
     * 胸中新星 - 移除
     */
    public static void chestNovaRemoved(ChestCavitySlotContext slotContext) {
        slotContext.data().getFirstTaskIf(task ->
            task instanceof ChestNovaTask novaTask && novaTask.isSlotEquals(slotContext.index())
        ).ifPresent(task -> {
            ((ChestNovaTask) task).setRemove();
            if (slotContext.entity() instanceof Player player) {
                // 添加30秒
                player.getCooldowns().addCooldown(slotContext.stack().getItem(), 20 * 30);
            }
        });
    }

    /**
     * 统计泥峭系列器官数量
     */
    private static int getBluffOrganCount(ChestCavityData data) {
        return data.getOrganCount(WAICItemTagManager.BLUFF);
    }

    /**
     * 泥峭铭文板 modifier：为 MAX_ABSORPTION 提供上限
     */
    public static void bluffTabletModifier(ChestCavitySlotContext context, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        int bluffOrganCount = getBluffOrganCount(context.data());
        modifiers.put(Attributes.MAX_ABSORPTION, OrganAttributeUtil.createAddValueModifier(context.id(), bluffOrganCount * 8));
    }

    // 泥峭核心 — 吃泥土方块（底层逻辑，接收方块坐标）
    public static boolean bluffCore(ChestCavitySlotContext slotContext, BlockPos pos) {
        LivingEntity entity = slotContext.entity();
        Level level = entity.level();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        if (!isDirtBlock(block)) return false;
        eatDirt(entity, block.asItem().getDefaultInstance());
        level.levelEvent(2001, pos, Block.getId(blockState));
        level.removeBlock(pos, false);
        return true;
    }

    /**
     * 食用泥土
     * <p>
     * 通过构造 FoodProperties 并调用 {@link LivingEntity#eat} 走标准食物消化流程，
     * 使九头蛇器肠的效果时长加成、CCB 的 NUTRITION/DIGESTION 属性等注入能够正常生效。
     * </p>
     */
    public static ItemStack eatDirt(LivingEntity entity, ItemStack dirt) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 铭文板吸收效果（非食物效果，保持直接设置），setAbsorptionAmount 会自动按 MAX_ABSORPTION 属性截断
        int tabletCount = data.getOrganCount(MowziesMobOrgans.BLUFF_TABLET.get());
        if (tabletCount > 0) {
            float currentAbsorption = entity.getAbsorptionAmount();
            entity.setAbsorptionAmount(currentAbsorption + tabletCount * 2);
        }

        // 构造 FoodProperties，走标准食物路径
        int rodCount = data.getOrganCount(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        FoodProperties.Builder builder = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier((float) rodCount / 2)
            .alwaysEdible();

        // 泥峭核心 buff 效果作为 FoodProperties 的 effects
        if (data.hasOrgan(MowziesMobOrgans.BLUFF_CORE.get()) && dirt.getItem() instanceof BlockItem item) {
            Block block = item.getBlock();
            if (block == Blocks.GRASS_BLOCK || block == Blocks.MOSS_BLOCK || block == Blocks.MYCELIUM) {
                builder.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 1), 1.0F);
            } else if (block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.MUD) {
                builder.effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 30, 1), 1.0F);
            } else if (block == Blocks.ROOTED_DIRT || block == Blocks.MUDDY_MANGROVE_ROOTS) {
                builder.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 30, 1), 1.0F);
            }
        }

        entity.eat(entity.level(), dirt.copyWithCount(1), builder.build());
        // 在最后执行物品消耗
        dirt.consume(1, entity);
        return dirt;
    }

    /**
     * 检查是否拥有泥峭器官
     */
    public static boolean hasBluffOrgan(LivingEntity entity) {
        return getBluffOrganCount(ChestCavityUtil.getData(entity)) > 0;
    }

    /**
     * 检查是否为泥土物品
     */
    public static boolean isDirtItem(ItemStack stack) {
        return stack.is(Items.DIRT) ||
               stack.is(Items.GRASS_BLOCK) ||
               stack.is(Items.MOSS_BLOCK) ||
               stack.is(Items.MYCELIUM) ||
               stack.is(Items.COARSE_DIRT) ||
               stack.is(Items.PODZOL) ||
               stack.is(Items.MUD) ||
               stack.is(Items.ROOTED_DIRT) ||
               stack.is(Items.MUDDY_MANGROVE_ROOTS);
    }

    /**
     * 检查是否为泥土方块
     */
    public static boolean isDirtBlock(Block block) {
        return block == Blocks.DIRT ||
               block == Blocks.GRASS_BLOCK ||
               block == Blocks.MOSS_BLOCK ||
               block == Blocks.MYCELIUM ||
               block == Blocks.COARSE_DIRT ||
               block == Blocks.PODZOL ||
               block == Blocks.MUD ||
               block == Blocks.ROOTED_DIRT ||
               block == Blocks.MUDDY_MANGROVE_ROOTS;
    }
}

package net.zhaiji.who_am_i_core.util;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;

public class MowziesMobOrganSkillUtil {
    /**
     * 钢铁守护者护心镜 - 受到伤害前
     */
    public static void ferrousWroughtnautHeartMirrorIncomingDamage(ChestCavitySlotContext slotContext, LivingIncomingDamageEvent event) {
        LivingEntity entity = slotContext.entity();
        DamageSource source = event.getSource();
        // 只抵挡来自实体的伤害
        if (source.getEntity() == null) return;
        // 检查伤害方向是否来自前方
        Vec3 sourcePosition = source.getSourcePosition();
        if (sourcePosition != null) {
            Vec3 viewVector = entity.calculateViewVector(0.0F, entity.getYHeadRot());
            Vec3 toEntity = sourcePosition.vectorTo(entity.position());
            Vec3 damageDirection = new Vec3(toEntity.x, 0.0, toEntity.z).normalize();
            // 当点积小于0时，伤害来自前方
            if (damageDirection.dot(viewVector) < 0) {
                // 播放钢铁守护者的抵挡音效
                entity.level()
                    .playSound(null, entity.getOnPos(), MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), SoundSource.PLAYERS, 0.4F, 2.0F);
                // 取消伤害
                event.setCanceled(true);
            }
        }
    }

    /**
     * 钢铁守护者护心镜 - 攻击
     */
    public static void ferrousWroughtnautHeartMirrorAttack(
        ChestCavitySlotContext slotContext,
        LivingEntity entity,
        DamageSource source,
        DamageContainer damageContainer
    ) {
        LivingEntity livingEntity = slotContext.entity();
        // 玩家加冷却，实体直接加缓慢5
        if (livingEntity instanceof Player player) {
            player.getCooldowns().addCooldown(slotContext.stack().getItem(), 20 * 3);
        } else {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 4));
        }
    }

    /**
     * 胸中新星 - 胸腔关闭
     */
    public static void chestNovaChestCavityClose(ChestCavitySlotContext slotContext) {
        ChestCavityData data = slotContext.data();
        int[] adjacentSlots = WAICOrganUtil.getAdjacentSlots(slotContext.index());
        for (int slot : adjacentSlots) {
            if (slot < 0 || slot >= 27) continue;
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
        // 检查是否已有 ChestNovaTask,并且task不会被不会被删除
        if (data.hasTask(task -> task instanceof ChestNovaTask && !task.canRemove(slotContext.entity()))) {
            return;
        }
        // 创建并添加任务
        data.addTask(new ChestNovaTask(data, slotContext.index()));
    }

    /**
     * 胸中新星 - 移除
     */
    public static void chestNovaRemoved(ChestCavitySlotContext slotContext) {
        slotContext.data().getFirstTask(task ->
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
     * 泥峭核心 - 技能
     */
    public static void bluffCoreSkill(ChestCavitySlotContext slotContext) {
        LivingEntity entity = slotContext.entity();
        // 射线检测泥土方块
        Vec3 from = entity.getEyePosition();
        Vec3 to = from.add(entity.getLookAngle()
            .normalize()
            .scale(entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).getValue()));
        ClipContext clipContext = new ClipContext(
            from, to,
            ClipContext.Block.OUTLINE,
            ClipContext.Fluid.NONE,
            CollisionContext.empty()
        );
        Level level = entity.level();
        BlockHitResult hitResult = level.clip(clipContext);
        if (hitResult.getType() != HitResult.Type.BLOCK) return;
        BlockPos pos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        // 检查是否为泥土方块
        if (!isDirtBlock(block)) return;
        // 播放音效和粒子效果
        eatDirt(entity, block.asItem().getDefaultInstance());
        level.levelEvent(2001, pos, Block.getId(blockState));
        level.removeBlock(pos, false);
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
        // 铭文板吸收效果（非食物效果，保持直接设置）
        int tabletCount = data.getOrganCount(MowziesMobOrgans.BLUFF_TABLET.get());
        if (tabletCount > 0) {
            int bluffOrganCount = data.getOrganCount(
                organ -> organ.is(MowziesMobOrgans.BLUFF_CORE.get()) ||
                         organ.is(MowziesMobOrgans.BLUFF_TABLET.get()) ||
                         organ.is(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
            );
            int maxAbsorption = bluffOrganCount * 8;
            float currentAbsorption = entity.getAbsorptionAmount();
            float newAbsorption = Math.min(currentAbsorption + tabletCount * 2, maxAbsorption);
            entity.setAbsorptionAmount(newAbsorption);
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
        return ChestCavityUtil.getData(entity).hasOrgan(
            organ -> organ.is(MowziesMobOrgans.BLUFF_CORE.get()) ||
                     organ.is(MowziesMobOrgans.BLUFF_TABLET.get()) ||
                     organ.is(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        );
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

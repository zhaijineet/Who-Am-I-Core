package net.zhaiji.who_am_i_core.organ;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import net.zhaiji.who_am_i_core.util.WAICTooltipUtil;

import java.util.List;
import java.util.function.Supplier;

public class MowziesMobOrgans {
    /**
     * 钢铁守护者的护心镜
     *
     * <pre>
     * #金属 #传说 #唯一被动
     *
     * -80%最终移动速度
     * 移除FOV的修改
     * 抵挡来自正面的实体的攻击
     * 攻击后的3秒内不能移动
     * </pre>
     */
    public static final Supplier<Item> FERROUS_WROUGHTNAUT_HEART_MIRROR = WAICItem.ITEM.register(
        "ferrous_wroughtnaut_heart_mirror",
        () -> OrganBuilder.builder()
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organSkill(stack.getItem(), 0),
                        WAICTooltipUtil.organSkill(stack.getItem(), 1),
                        WAICTooltipUtil.organSkill(stack.getItem(), 2)
                    )
                );
            })
            // -80%最终移动速度
            .modifier((id, modifiers) -> {
                modifiers.put(Attributes.MOVEMENT_SPEED, OrganAttributeUtil.createMultipliedTotalModifier(id, -0.8));
            })
            .incomingDamage((slotContext, event) -> {
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
            })
            .attack((slotContext, entity, source, container) -> {
                LivingEntity livingEntity = slotContext.entity();
                // 玩家加冷却，实体直接加缓慢5
                if (livingEntity instanceof Player player) {
                    player.getCooldowns().addCooldown(slotContext.stack().getItem(), 20 * 3);
                } else {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 4));
                }
            })
            .build()
    );

    /**
     * 胸中新星
     * <pre>
     * #魔法 #召唤 #传说 #唯一被动
     * 当胸腔关闭时，烧毁周围3x3范围内的器官（机械系和魔法系器官除外）
     * 周围3x3范围内的乌姆塔纳面具会召唤对应的追随者
     * 面具会为主人提供其药水效果
     * </pre>
     */
    public static final Supplier<Item> CHEST_NOVA = WAICItem.ITEM.register(
        "chest_nova",
        () -> OrganBuilder.builder()
            .descriptionTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organDescription(stack.getItem())
                    )
                );
            })
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organSkill(stack.getItem(), 0),
                        WAICTooltipUtil.organSkill(stack.getItem(), 1),
                        WAICTooltipUtil.organSkill(stack.getItem(), 2)
                    )
                );
            })
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(id, 2));
            })
            // 胸腔关闭时烧毁周围器官以及尝试创建任务
            .chestCavityClose(slotContext -> {
                ChestCavityData data = slotContext.data();
                int[] adjacentSlots = WAICOrganUtil.getAdjacentSlots(slotContext.index());
                for (int slot : adjacentSlots) {
                    if (slot < 0 || slot >= 27) continue;
                    ItemStack adjacentStack = data.getStackInSlot(slot);
                    if (adjacentStack.isEmpty()) continue;
                    // 检查是否为器官
                    if (!WAICOrganUtil.isOrgan(adjacentStack)) continue;
                    // 检查是否为机械器官
                    if (adjacentStack.is(WAICItemTagManager.MECHANICAL)) continue;
                    // 检查是否为魔法器官
                    if (adjacentStack.is(WAICItemTagManager.MAGIC)) continue;
                    // 烧毁器官
                    data.setStackInSlot(slot, ItemStack.EMPTY);
                }
                // 检查是否已有 ChestNovaTask,并且task不会被不会被删除
                for (var task : data.getTasks()) {
                    if (task instanceof ChestNovaTask && !task.canRemove(slotContext.entity())) {
                        return;
                    }
                }
                // 创建并添加任务
                data.addTask(new ChestNovaTask(data, slotContext.index()));
            })
            // 器官移除时清理任务
            .removed(slotContext -> {
                for (IChestCavityTask task : slotContext.data().getTasks()) {
                    if (task instanceof ChestNovaTask followerTask && followerTask.isSlotEquals(slotContext.index())) {
                        followerTask.setRemove();
                        if (slotContext.entity() instanceof Player player) {
                            // 添加30秒
                            player.getCooldowns().addCooldown(slotContext.stack().getItem(), 20 * 30);
                        }
                    }
                }
            })
            .build()
    );

    /**
     * 制御棒
     *
     * <pre>
     * #机械 #传说 #唯一被动
     *
     * 当制御棒在胸中新星的3x3范围内时：
     * - 乌姆塔纳追随者重新召唤冷却时间从30秒缩短为10秒
     * - 面具也会给追随者提供效果
     * </pre>
     */
    public static final Supplier<Item> CONTROL_ROD = WAICItem.ITEM.register(
        "control_rod",
        () -> OrganBuilder.builder()
            .descriptionTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organDescription(stack.getItem())
                    )
                );
            })
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organSkill(stack.getItem(), 0),
                        WAICTooltipUtil.organSkill(stack.getItem(), 1)
                    )
                );
            })
            .build()
    );

    // 衰老心脏
    public static final Supplier<Item> AGED_HEART = WAICItem.ITEM.register(
        "aged_heart",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老肺脏
    public static final Supplier<Item> AGED_LUNG = WAICItem.ITEM.register(
        "aged_lung",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.BREATH_RECOVERY, OrganAttributeUtil.createAddValueModifier(id, 0.5));
                modifiers.put(InitAttribute.BREATH_CAPACITY, OrganAttributeUtil.createAddValueModifier(id, 0.5));
                modifiers.put(InitAttribute.ENDURANCE, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老脊柱
    public static final Supplier<Item> AGED_SPINE = WAICItem.ITEM.register(
        "aged_spine",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.NERVES, OrganAttributeUtil.createAddValueModifier(id, 0.5));
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(id, 0.25));
            })
            .build()
    );

    // 衰老胃
    public static final Supplier<Item> AGED_STOMACH = WAICItem.ITEM.register(
        "aged_stomach",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.DIGESTION, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老肠子
    public static final Supplier<Item> AGED_INTESTINE = WAICItem.ITEM.register(
        "aged_intestine",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.NUTRITION, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老肾脏
    public static final Supplier<Item> AGED_KIDNEY = WAICItem.ITEM.register(
        "aged_kidney",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.FILTRATION, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    //衰老脾脏
    public static final Supplier<Item> AGED_SPLEEN = WAICItem.ITEM.register(
        "aged_spleen",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.METABOLISM, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老肝脏
    public static final Supplier<Item> AGED_LIVER = WAICItem.ITEM.register(
        "aged_liver",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.DETOXIFICATION, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老阑尾
    public static final Supplier<Item> AGED_APPENDIX = WAICItem.ITEM.register(
        "aged_appendix",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(Attributes.LUCK, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老肋骨
    public static final Supplier<Item> AGED_RIB = WAICItem.ITEM.register(
        "aged_rib",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.DEFENSE, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    // 衰老肌肉
    public static final Supplier<Item> AGED_MUSCLE = WAICItem.ITEM.register(
        "aged_muscle",
        () -> OrganBuilder.builder()
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.STRENGTH, OrganAttributeUtil.createAddValueModifier(id, 0.5));
                modifiers.put(InitAttribute.SPEED, OrganAttributeUtil.createAddValueModifier(id, 0.5));
            })
            .build()
    );

    /**
     * 禅心
     * TODO
     */
    public static final Supplier<Item> ZEN_HEART = WAICItem.ITEM.register(
        "zen_heart",
        () -> OrganBuilder.builder().build()
    );


    /**
     * 泥峭铭文板
     * <pre>
     * #魔法
     * 右键长按食用泥土物品，恢复饥饿值并获得吸收效果
     * </pre>
     */
    public static final Supplier<Item> BLUFF_TABLET = WAICItem.ITEM.register(
        "bluff_tablet",
        () -> OrganBuilder.builder()
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organSkill(stack.getItem(), 0),
                        WAICTooltipUtil.organSkill(stack.getItem(), 1)
                    )
                );
            })
            .build()
    );

    /**
     * 活性泥峭棒
     * <pre>
     * #魔法
     * 右键长按食用泥土物品，恢复饥饿值并增加饱和度
     * </pre>
     */
    public static final Supplier<Item> ACTIVE_BLUFF_ROD = WAICItem.ITEM.register(
        "active_bluff_rod",
        () -> OrganBuilder.builder()
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organSkill(stack.getItem(), 0),
                        WAICTooltipUtil.organSkill(stack.getItem(), 1)
                    )
                );
            })
            .build()
    );

    /**
     * 泥峭核心
     * <pre>
     * #魔法
     * 食用视线方向的泥土方块
     * </pre>
     */
    public static final Supplier<Item> BLUFF_CORE = WAICItem.ITEM.register(
        "bluff_core",
        () -> OrganBuilder.builder()
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        WAICTooltipUtil.organSkill(stack.getItem(), 0),
                        WAICTooltipUtil.organSkill(stack.getItem(), 1),
                        WAICTooltipUtil.organSkill(stack.getItem(), 2),
                        WAICTooltipUtil.organSkill(stack.getItem(), 3),
                        WAICTooltipUtil.organSkill(stack.getItem(), 4),
                        WAICTooltipUtil.organSkill(stack.getItem(), 5)
                    )
                );
            })
            .skill(slotContext -> {
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
                eatDirt(entity, block.asItem());
                entity.gameEvent(GameEvent.EAT);
                level.levelEvent(2001, pos, Block.getId(blockState));
                level.removeBlock(pos, false);
            })
            .build()
    );

    public static void eatDirt(LivingEntity entity, Item dirt) {
        ChestCavityData data = ChestCavityUtil.getData(entity);
        // 铭文板吸收效果
        int tabletCount = WAICOrganUtil.countOrgan(data, MowziesMobOrgans.BLUFF_TABLET.get());
        if (tabletCount > 0) {
            int bluffOrganCount = WAICOrganUtil.countOrgan(data, WAICItemTagManager.BLUFF);
            int maxAbsorption = bluffOrganCount * 8;
            float currentAbsorption = entity.getAbsorptionAmount();
            float newAbsorption = Math.min(currentAbsorption + tabletCount * 2, maxAbsorption);
            entity.setAbsorptionAmount(newAbsorption);
        }
        if (entity instanceof Player player) {
            // 泥峭棒
            int rodCount = WAICOrganUtil.countOrgan(data, MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
            player.getFoodData().eat(4, (float) rodCount / 2);
        }

        if (data.hasOrgan(BLUFF_CORE.get()) && dirt instanceof BlockItem item) {
            Block block = item.getBlock();
            // 根据方块类型应用buff
            if (block == Blocks.GRASS_BLOCK || block == Blocks.MOSS_BLOCK || block == Blocks.MYCELIUM) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 1));
            } else if (block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.MUD) {
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 30, 1));
            } else if (block == Blocks.ROOTED_DIRT || block == Blocks.MUDDY_MANGROVE_ROOTS) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 30, 1));
            }
        }
    }

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

    /**
     * 检查是否为泥土物品（用于右键食用）
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

    public static void register() {
    }
}

package net.zhaiji.who_am_i_core.organ;

import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.chestcavitybeyond.util.TooltipUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.register.WAICItem;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;
import net.zhaiji.who_am_i_core.util.HAICOrganUtil;
import net.zhaiji.who_am_i_core.util.HAICTooltipUtil;

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
                        HAICTooltipUtil.organSkill(stack.getItem(), 0),
                        HAICTooltipUtil.organSkill(stack.getItem(), 1),
                        HAICTooltipUtil.organSkill(stack.getItem(), 2)
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
                        HAICTooltipUtil.organDescription(stack.getItem())
                    )
                );
            })
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        HAICTooltipUtil.organSkill(stack.getItem(), 0),
                        HAICTooltipUtil.organSkill(stack.getItem(), 1),
                        HAICTooltipUtil.organSkill(stack.getItem(), 2)
                    )
                );
            })
            .modifier((id, modifiers) -> {
                modifiers.put(InitAttribute.HEALTH, OrganAttributeUtil.createAddValueModifier(id, 2));
            })
            // 胸腔关闭时烧毁周围器官
            .chestCavityClose(slotContext -> {
                ChestCavityData data = slotContext.data();
                int[] adjacentSlots = HAICOrganUtil.getAdjacentSlots(slotContext.index());
                for (int slot : adjacentSlots) {
                    if (slot < 0 || slot >= 27) continue;
                    ItemStack adjacentStack = data.getStackInSlot(slot);
                    if (adjacentStack.isEmpty()) continue;
                    // 检查是否为器官
                    if (!HAICOrganUtil.isOrgan(adjacentStack)) continue;
                    // 检查是否为机械器官
                    if (adjacentStack.is(WAICItemTagManager.MECHANICAL)) continue;
                    // 检查是否为魔法器官
                    if (adjacentStack.is(WAICItemTagManager.MAGIC)) continue;
                    // 烧毁器官
                    data.setStackInSlot(slot, ItemStack.EMPTY);
                }
            })
            // 器官添加时创建任务
            .added(slotContext -> {
                ChestCavityData data = slotContext.data();
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
                        HAICTooltipUtil.organDescription(stack.getItem())
                    )
                );
            })
            .skillTooltip((data, stack, keyContext, context, tooltipComponents, tooltipFlag) -> {
                TooltipUtil.simpleTooltipAdd(
                    tooltipComponents,
                    List.of(
                        HAICTooltipUtil.organSkill(stack.getItem(), 0),
                        HAICTooltipUtil.organSkill(stack.getItem(), 1)
                    )
                );
            })
            .build()
    );

    public static void register() {
    }
}

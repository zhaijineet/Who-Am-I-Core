package net.zhaiji.who_am_i_core.mixin;

import dev.dubhe.anvilcraft.init.ModCriterionTriggers;
import dev.dubhe.anvilcraft.init.ModDispenserBehavior;
import dev.dubhe.anvilcraft.util.PlayerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ModDispenserBehavior.class)
public abstract class ModDispenserBehaviorMixin {
    @Shadow
    @Final
    private static DefaultDispenseItemBehavior DEFAULT_BEHAVIOUR;

    /**
     * 在原方法筛选铁傀儡前介入，将筛选范围扩展到任何拥有铁修复属性且未满血的 LivingEntity，
     * <p>
     * 并按铁修复属性值计算治疗量，与 CCB 的铁修复公式（2.5 × 属性值）保持一致
     * </p>
     */
    @Inject(
        method = "ironIngot",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;getEntities(Lnet/minecraft/world/level/entity/EntityTypeTest;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
        ),
        cancellable = true
    )
    private static void whoAmICore$ironIngot(BlockSource source, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        BlockPos blockPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        ServerLevel level = source.level();
        List<LivingEntity> entities = level
            .getEntities(EntityTypeTest.forClass(LivingEntity.class), new AABB(blockPos), Entity::isAlive)
            .stream()
            .filter(entity -> entity.getHealth() < entity.getMaxHealth() && entity.getAttributeValue(InitAttribute.IRON_REPAIR) > 0)
            .toList();
        if (entities.isEmpty()) {
            cir.setReturnValue(DEFAULT_BEHAVIOUR.dispense(source, stack));
            return;
        }
        LivingEntity entity = entities.get(level.random.nextInt(0, entities.size()));
        entity.heal((float) (2.5 * entity.getAttributeValue(InitAttribute.IRON_REPAIR)));
        float pitch = 1.0f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f;
        entity.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, pitch);
        ItemStack stack1 = stack.copy();
        stack1.shrink(1);
        for (ServerPlayer player : PlayerUtil.searchPlayerByPos(level, blockPos, 5)) {
            ModCriterionTriggers.REPAIR_IRON_GOLEM.get().trigger(player);
        }
        cir.setReturnValue(stack1);
    }
}

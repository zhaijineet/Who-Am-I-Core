package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EnchantedCountIncreaseFunction.class)
public class EnchantedCountIncreaseFunctionMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    /**
     * 将抢夺实体属性叠加到抢夺附魔等级上
     */
    @ModifyVariable(
        method = "run",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/LivingEntity;)I",
            ordinal = 0
        ),
        index = 5
    )
    private int whoAmICore$applyLootingAttribute(int enchantmentLevel, ItemStack stack, LootContext lootContext) {
        if (enchantment.is(Enchantments.LOOTING)) {
            Entity entity = lootContext.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
            if (entity instanceof LivingEntity living) {
                return Math.max(0, enchantmentLevel + (int) living.getAttributeValue(WAICAttribute.LOOTING));
            }
        }
        return enchantmentLevel;
    }
}

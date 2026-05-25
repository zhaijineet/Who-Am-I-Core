package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantedCountIncreaseFunction.class)
public class EnchantedCountIncreaseFunctionMixin {
    /**
     * 将抢夺实体属性叠加到抢夺附魔等级上
     */
    @Redirect(
        method = "run",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/LivingEntity;)I"
        )
    )
    public int whoAmICore$run(Holder<Enchantment> enchantment, LivingEntity entity) {
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(enchantment, entity);
        if (enchantment.is(Enchantments.LOOTING)) {
            if (entity instanceof LivingEntity living) {
                return Math.max(0, enchantmentLevel + (int) living.getAttributeValue(WAICAttribute.LOOTING));
            }
        }
        return enchantmentLevel;
    }
}

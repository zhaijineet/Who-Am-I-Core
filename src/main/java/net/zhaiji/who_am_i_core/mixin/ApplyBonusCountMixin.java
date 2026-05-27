package net.zhaiji.who_am_i_core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {
    /**
     * 将时运实体属性叠加到时运附魔等级上
     */
    @Redirect(
        method = "run",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ItemStack;)I"
        )
    )
    public int whoAmICore$run(Holder<Enchantment> enchantment, ItemStack stack, @Local(argsOnly = true) LootContext context) {
        int enchantmentLevel = stack.getEnchantmentLevel(enchantment);
        if (enchantment.is(Enchantments.FORTUNE)) {
            if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof LivingEntity entity) {
                return Math.max(0, enchantmentLevel + (int) entity.getAttributeValue(WAICAttribute.FORTUNE));
            }
        }
        return enchantmentLevel;
    }
}

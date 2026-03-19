package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    /**
     * 将时运实体属性叠加到时运附魔等级上
     */
    @ModifyVariable(
        method = "run",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ItemStack;)I",
            ordinal = 0
        ),
        index = 4
    )
    private int whoAmICore$applyFortuneAttribute(int enchantmentLevel, ItemStack stack, LootContext lootContext) {
        if (enchantment.is(Enchantments.FORTUNE)) {
            Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (entity instanceof LivingEntity living) {
                return Math.max(0, enchantmentLevel + (int) living.getAttributeValue(WAICAttribute.FORTUNE));
            }
        }
        return enchantmentLevel;
    }
}

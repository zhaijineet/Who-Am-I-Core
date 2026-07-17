package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.food.FoodData;
import net.zhaiji.chestcavitybeyond.mixinapi.IFoodData;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {
    /**
     * 拦截直接恢复饥饿值的路径（如巨兽熔炉饮用岩浆桶），触发暴食吸收效果
     */
    @Inject(
        method = "eat(IF)V",
        at = @At("RETURN")
    )
    public void whoAmICore$eat(int foodLevelModifier, float saturationLevelModifier, CallbackInfo ci) {
        WAICOrganUtil.gluttonyEatEffect(((IFoodData) (Object) this).getPlayer(), foodLevelModifier);
    }
}

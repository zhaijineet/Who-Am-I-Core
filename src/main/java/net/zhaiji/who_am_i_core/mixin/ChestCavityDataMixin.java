package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.register.WAICAttribute;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestCavityData.class)
public abstract class ChestCavityDataMixin {
    @Shadow
    @Nullable
    public abstract LivingEntity getOwner();

    @Shadow
    public abstract double getCurrentValue(Holder<Attribute> attribute);

    @Inject(
        method = "tick",
        at = @At("HEAD")
    )
    public void whoAmICore$tick(CallbackInfo ci) {
        if (getOwner().level().isClientSide() && getOwner().tickCount % 20 == 0) {
            // 应用治愈属性效果
            double heal = getCurrentValue(WAICAttribute.HEAL);
            if (heal > 0) {
                getOwner().heal((float) heal);
            }
        }
    }
}

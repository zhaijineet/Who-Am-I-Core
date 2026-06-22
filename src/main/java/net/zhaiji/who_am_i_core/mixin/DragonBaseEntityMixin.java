package net.zhaiji.who_am_i_core.mixin;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DragonBaseEntity.class)
public abstract class DragonBaseEntityMixin {
    @Shadow
    public abstract ItemLike getHeartItem();

    @Shadow
    public abstract Item getFleshItem();

    @Redirect(
        method = "mobInteract",
        at = @At(
            value = "INVOKE",
            target = "Lcom/iafenvoy/iceandfire/entity/DragonBaseEntity;getHeartItem()Lnet/minecraft/world/level/ItemLike;"
        )
    )
    public ItemLike whoAmICore$mobInteract(DragonBaseEntity instance) {
        return ChestCavityUtil.getData(instance).hasOrgan(getHeartItem().asItem()) ? getHeartItem() : getFleshItem();
    }
}

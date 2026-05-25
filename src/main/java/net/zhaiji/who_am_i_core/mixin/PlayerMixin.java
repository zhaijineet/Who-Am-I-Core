package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private Player whoAmICore$self() {
        return (Player) (Object) this;
    }

    /**
     * 猩红心脏 Mixin：当玩家拥有猩红心脏且血液未满时，强制 isHurt() 返回 true。
     * 使满血状态也能持续触发饱食度回血，从而持续产血。
     * 血液满时不拦截，正常返回。
     */
    @Inject(method = "isHurt", at = @At("HEAD"), cancellable = true)
    public void whoAmICore$crimsonIsHurt(CallbackInfoReturnable<Boolean> cir) {
        Player player = whoAmICore$self();
        // 检查是否拥有猩红心脏且血液未满
        if (ChestCavityUtil.getData(player).hasOrgan(WAICOrgans.CRIMSON_HEART.get()) && !HumoursData.get(player).isBloodFull()) {
            cir.setReturnValue(true);
        }
    }
}

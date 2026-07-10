package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract boolean isSpectator();

    @Shadow
    public abstract Abilities getAbilities();

    @Unique
    private Player whoAmICore$self() {
        return (Player) (Object) this;
    }

    /**
     * 当玩家拥有猩红心脏且血液未满时，强制 isHurt() 返回 true
     */
    @Inject(method = "isHurt", at = @At("HEAD"), cancellable = true)
    public void whoAmICore$isHurt(CallbackInfoReturnable<Boolean> cir) {
        Player player = whoAmICore$self();
        // 检查是否拥有猩红心脏且血液未满
        if (ChestCavityUtil.getData(player).hasOrgan(WAICOrgans.CRIMSON_HEART.get()) && !HumoursData.get(player).isBloodFull()) {
            cir.setReturnValue(true);
        }
    }

    /**
     * 在 {@code experienceLevel} 字段实际更新完成后刷新经验之心属性。
     */
    @Inject(method = "giveExperienceLevels(I)V", at = @At("RETURN"))
    public void whoAmICore$giveExperienceLevels(int levels, CallbackInfo ci) {
        WAICOrganUtil.updateExperienceHeartAttribute(whoAmICore$self());
    }

    /**
     * 拥有鬼火器官且处于飞行状态时才能穿墙
     */
    @Inject(
        method = "tick()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z",
            ordinal = 1
        )
    )
    public void whoAmICore$tick(CallbackInfo ci) {
        if (ChestCavityUtil.getData(whoAmICore$self()).hasOrgan(IceAndFireOrgans.GHOST_FIRE.get()) && getAbilities().flying && !isSpectator()) {
            noPhysics = true;
            setOnGround(false);
        }
    }
}

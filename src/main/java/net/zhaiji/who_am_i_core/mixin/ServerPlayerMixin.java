package net.zhaiji.who_am_i_core.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zhaiji.who_am_i_core.util.WAICOrganUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Unique
    private ServerPlayer whoAmICore$self() {
        return (ServerPlayer) (Object) this;
    }

    /**
     * 经验之心 Mixin：/xp set levels 指令通过 setExperienceLevels 直接修改 experienceLevel 字段，
     * 该方法不触发任何事件，在此主动刷新经验之心属性。
     */
    @Inject(method = "setExperienceLevels(I)V", at = @At("RETURN"))
    public void whoAmICore$setExperienceLevels(int level, CallbackInfo ci) {
        WAICOrganUtil.updateExperienceHeartAttribute(whoAmICore$self());
    }
}


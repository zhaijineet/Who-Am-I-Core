package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.content.entities.chesed_boss.ChesedEntity;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.FDMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 慈悲死亡时额外掉落慈悲器官
 */
@Mixin(ChesedEntity.class)
public abstract class ChesedEntityMixin extends FDMob {
    public ChesedEntityMixin(
        EntityType<? extends Mob> type,
        Level level
    ) {
        super(type, level);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        ChesedEntity self = (ChesedEntity) (Object) this;
        if (ChestCavityUtil.getData(self).hasOrgan(FDBossesOrgans.CHESED.get())) {
            self.spawnAtLocation(FDBossesOrgans.CHESED.get());
        }
    }
}

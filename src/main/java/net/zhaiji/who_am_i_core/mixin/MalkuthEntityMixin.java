package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.content.entities.malkuth_boss.MalkuthEntity;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.FDMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 王国死亡时额外掉落王国器官
 */
@Mixin(MalkuthEntity.class)
public abstract class MalkuthEntityMixin extends FDMob {
    public MalkuthEntityMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        MalkuthEntity self = (MalkuthEntity) (Object) this;
        if (ChestCavityUtil.getData(self).hasOrgan(FDBossesOrgans.MALKUTH.get())) {
            self.spawnAtLocation(FDBossesOrgans.MALKUTH.get());
        }
    }
}

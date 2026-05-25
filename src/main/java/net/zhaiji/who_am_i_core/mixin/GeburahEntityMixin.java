package net.zhaiji.who_am_i_core.mixin;

import com.finderfeed.fdbosses.content.entities.geburah.GeburahEntity;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.FDLivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import org.spongepowered.asm.mixin.Mixin;

/**
 * 严厉死亡时额外掉落严厉器官

 */
@Mixin(GeburahEntity.class)
public abstract class GeburahEntityMixin extends FDLivingEntity {
    public GeburahEntityMixin(
        EntityType<? extends LivingEntity> type,
        Level level
    ) {
        super(type, level);
    }

    @Override
    public void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);
        GeburahEntity self = (GeburahEntity) (Object) this;
        if (ChestCavityUtil.getData(self).hasOrgan(FDBossesOrgans.GEBURAH.get())) {
            self.spawnAtLocation(FDBossesOrgans.GEBURAH.get());
        }
    }
}

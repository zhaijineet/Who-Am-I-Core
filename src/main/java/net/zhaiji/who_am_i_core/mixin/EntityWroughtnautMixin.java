package net.zhaiji.who_am_i_core.mixin;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

/**
 * 钢铁守护者死亡时额外掉落护心镜
 */
@Mixin(EntityWroughtnaut.class)
public abstract class EntityWroughtnautMixin extends MowzieLLibraryEntity {
    public EntityWroughtnautMixin(
        EntityType<? extends MowzieEntity> type,
        Level world
    ) {
        super(type, world);
    }

    @Override
    protected void dropAllDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source) {
        if (!this.dropAfterDeathAnim || this.deathTime > 0) {
            super.dropAllDeathLoot(level, source);
            spawnAtLocation(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get());
        }
    }
}

package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.entity.HydraVenomBreathProjectile;
import net.zhaiji.who_am_i_core.entity.RailgunProjectileEntity;

/**
 * 实体注册类
 * 注册模组的所有实体类型
 */
public class WAICEntity {
    /**
     * 实体类型注册器
     */
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WhoAmICore.MOD_ID);

    /**
     * 九头蛇毒物吐息投射物实体类型
     */
    public static final DeferredHolder<EntityType<?>, EntityType<HydraVenomBreathProjectile>> HYDRA_VENOM_BREATH = ENTITY.register(
        "hydra_venom_breath",
        () -> EntityType.Builder
            .<HydraVenomBreathProjectile>of(HydraVenomBreathProjectile::new, MobCategory.MISC)
            .sized(1f, 1f)
            .clientTrackingRange(64)
            .build(WhoAmICore.of("hydra_venom_breath").toString())
    );

    /**
     * 电磁炮弹射物实体类型
     */
    public static final DeferredHolder<EntityType<?>, EntityType<RailgunProjectileEntity>> RAILGUN_PROJECTILE = ENTITY.register(
        "railgun_projectile",
        () -> EntityType.Builder
            .<RailgunProjectileEntity>of(RailgunProjectileEntity::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(64)
            .build(WhoAmICore.of("railgun_projectile").toString())
    );
}

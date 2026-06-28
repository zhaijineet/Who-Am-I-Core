package net.zhaiji.who_am_i_core.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.zhaiji.chestcavitybeyond.util.OrganSkillUtil;
import net.zhaiji.who_am_i_core.register.WAICDamageType;
import net.zhaiji.who_am_i_core.register.WAICEntity;

/**
 * 电磁炮弹射物实体
 * <p>
 * 无重力直线高速飞行，命中后造成自定义伤害类型的伤害，落地后迅速消失
 * </p>
 */
public class RailgunProjectileEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(
        RailgunProjectileEntity.class,
        EntityDataSerializers.ITEM_STACK
    );

    private float railgunDamage;

    public RailgunProjectileEntity(EntityType<? extends RailgunProjectileEntity> entityType, Level level) {
        super(entityType, level);
        this.railgunDamage = 0;
    }

    public static RailgunProjectileEntity create(Level level, LivingEntity owner, ItemStack ammoStack, float damage) {
        RailgunProjectileEntity projectile = new RailgunProjectileEntity(WAICEntity.RAILGUN_PROJECTILE.get(), level);
        projectile.setOwner(owner);
        projectile.setPos(owner.getX(), owner.getY() + OrganSkillUtil.effectiveEyeHeight(owner) * 0.7F, owner.getZ());
        ItemStack ammo = ammoStack.copyWithCount(1);
        projectile.setItemStack(ammo);
        projectile.setPickupItemStack(ammo);
        projectile.railgunDamage = damage;
        projectile.pickup = Pickup.DISALLOWED;
        projectile.setNoGravity(true);
        return projectile;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ITEM_STACK, Items.IRON_NUGGET.getDefaultInstance());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround && this.inGroundTime > 4) {
            this.discard();
        }
        if (this.level().isClientSide && !this.inGround) {
            Vec3 movement = this.getDeltaMovement();
            double speed = movement.length();
            int particleCount = Math.max(1, (int) Math.ceil(speed / 0.5));
            for (int i = 0; i < particleCount; i++) {
                double progress = (double) i / particleCount;
                this.level().addParticle(
                    ParticleTypes.END_ROD,
                    this.getX() - movement.x * progress,
                    this.getY() - movement.y * progress + 0.15,
                    this.getZ() - movement.z * progress,
                    0.0, 0.0, 0.0
                );
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity hitEntity = result.getEntity();
        if (hitEntity instanceof LivingEntity target) {
            DamageSource damageSource = this.level().damageSources().source(WAICDamageType.RAILGUN, this.getOwner(), this);
            target.hurt(damageSource, this.railgunDamage);
        }
        this.spawnImpactParticles();
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.spawnImpactParticles();
    }

    /**
     * 命中目标时向周围玩家广播爆炸粒子
     */
    private void spawnImpactParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                ParticleTypes.ELECTRIC_SPARK,
                this.getX(), this.getY(), this.getZ(),
                16, 0.3, 0.3, 0.3, 0.1
            );
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return Items.IRON_NUGGET.getDefaultInstance();
    }

    public void setItemStack(ItemStack stack) {
        this.entityData.set(DATA_ITEM_STACK, stack);
    }

    @Override
    public ItemStack getItem() {
        return this.entityData.get(DATA_ITEM_STACK);
    }
}

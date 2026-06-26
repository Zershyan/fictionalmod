package io.zershyan.fictional.common.registry.entities;

import io.zershyan.fictional.common.registry.FictionalEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.function.Consumer;

public class BladeBeam extends Projectile {
    private static final EntityDataAccessor<Float> ALPHA;
    private static final EntityDataAccessor<Float> DAMAGE;
    private static final EntityDataAccessor<Float> DISTANCE;
    private static final EntityDataAccessor<Float> ZP_ROTATION;
    private static final EntityDataAccessor<Integer> COLOR;
    private static final EntityDataAccessor<Integer> FADE_TICK;
    private static final EntityDataAccessor<Integer> IN_FADE_TICK;

    public BladeBeam(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    public BladeBeam(Level pLevel) {
        this(FictionalEntities.BLADE_BEAM.get(), pLevel);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick() {
        Entity entity = this.getOwner();
        boolean ownerExist = entity != null && !entity.isRemoved() && entity.distanceToSqr(this) <= getDistance() * getDistance();
        super.tick();
        int inFadeTick = getInFadeTick();
        if(inFadeTick > 0) this.entityData.set(IN_FADE_TICK, --inFadeTick);
        if (!this.level().isClientSide) {
            int fadeTick = getFadeTick();
            if(ownerExist && this.level().hasChunkAt(this.blockPosition())) {
                HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                }
            } else if(fadeTick == -1) this.fadeDiscard(10);
            if(inFadeTick == 0) this.discard();
        }
        if(getFadeProgress() == -1) {
            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.setDeltaMovement(vec3);
            this.setPos(d0, d1, d2);
        }
    }

    public final float getFadeProgress() {
        return getFadeProgress(0.0f);
    }

    public final float getFadeProgress(float pPartialTick) {
        float fadeTick = getFadeTick();
        float inFadeTick = getInFadeTick() + pPartialTick;
        return fadeTick == -1 ? -1.0f : (1.0f - inFadeTick / fadeTick);
    }

    public final void fadeDiscard(int fadeTick) {
        this.entityData.set(FADE_TICK, fadeTick);
        this.entityData.set(IN_FADE_TICK, fadeTick);
    }

    private int getFadeTick() {
        return this.entityData.get(FADE_TICK);
    }

    private int getInFadeTick() {
        return this.entityData.get(IN_FADE_TICK);
    }

    public final void fadeDiscard() {
        fadeDiscard(20);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity pTarget) {
        return super.canHitEntity(pTarget) && !pTarget.noPhysics;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void push(@NotNull Entity pEntity) { }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    public void push(double pX, double pY, double pZ) { }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    protected void onHit(HitResult ray) {
        HitResult.Type hitresult$type = ray.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)ray);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, ray.getLocation(), GameEvent.Context.of(this, null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)ray;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
            if (!this.level().isClientSide) {
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            Entity entity = pResult.getEntity();
            if(getOwner() instanceof LivingEntity owner) {
                if (owner != entity && entity.canBeHitByProjectile()) {
                    entity.hurt(damageSources().mobAttack(owner), this.getDamage());
                }
            } else fadeDiscard();
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        return new ClientboundAddEntityPacket(getId(), getUUID(), getX(), getY(), getZ(), getXRot(), getYRot(), getType(), i, getDeltaMovement(), 0.0F);
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.setXRot(this.getXRot());
        this.setYRot(this.getYRot());
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ALPHA, 0.5F);
        this.entityData.define(DAMAGE, 0.0F);
        this.entityData.define(DISTANCE, 6.0F);
        RandomSource randomSource = this.level().getRandom();
        this.entityData.define(ZP_ROTATION, randomSource.nextInt(-45, 45) + randomSource.nextFloat());
        this.entityData.define(COLOR, 0x8BC9FF);
        this.entityData.define(FADE_TICK, -1);
        this.entityData.define(IN_FADE_TICK, -1);
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public float getAlpha() {
        return this.entityData.get(ALPHA);
    }

    public void setAlpha(float alpha) {
        this.entityData.set(ALPHA, Mth.clamp(alpha, 0.0f, 1.0f));
    }

    public float[] getColor() {
        Integer colorNumber = this.entityData.get(COLOR);
        return new Color(colorNumber).getColorComponents(new float[3]);
    }

    public void setColor(int color) {
        this.entityData.set(COLOR, Mth.clamp(color, 0x000000, 0xFFFFFF));
    }

    public float getDistance() {
        return this.entityData.get(DISTANCE);
    }

    public void setDistance(float distance) {
        this.entityData.set(DISTANCE, distance);
    }

    public float getZpRotation() {
        return this.entityData.get(ZP_ROTATION);
    }

    public void setZpRotation(float rotation) {
        this.entityData.set(ZP_ROTATION, rotation);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected float getEyeHeight(@NotNull Pose pPose, EntityDimensions pDimensions) {
        return pDimensions.height * 0.5f;
    }

    public static BladeBeamBuilder spawn(Player owner, float damage) {
        return spawn(owner, damage, (float) owner.getEntityReach());
    }

    public static BladeBeamBuilder spawn(LivingEntity owner, float damage, float distance) {
        return spawn(owner).damage(damage).distance(distance);
    }

    private static BladeBeamBuilder spawn(LivingEntity owner) {
        Level level = owner.level();
        BladeBeam bladeBeam = new BladeBeam(level);
        if(level.isClientSide) return new BladeBeamBuilder(bladeBeam);
        return new BladeBeamBuilder(bladeBeam).owner(owner);
    }

    static {
        ALPHA = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.FLOAT);
        DAMAGE = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.FLOAT);
        DISTANCE = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.FLOAT);
        ZP_ROTATION = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.FLOAT);
        COLOR = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.INT);
        FADE_TICK = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.INT);
        IN_FADE_TICK = SynchedEntityData.defineId(BladeBeam.class, EntityDataSerializers.INT);
    }

    public static class BladeBeamBuilder {
        private final BladeBeam bladeBeam;
        @Nullable
        private Consumer<BladeBeam> beamConsumer;
        public BladeBeamBuilder(BladeBeam bladeBeam) {
            this.bladeBeam = bladeBeam;
        }

        public BladeBeamBuilder custom(Consumer<BladeBeam> beamConsumer) {
            this.beamConsumer = beamConsumer;
            return this;
        }

        public BladeBeamBuilder color(int rgb) {
            bladeBeam.setColor(rgb);
            return this;
        }

        public BladeBeamBuilder alpha(float alpha) {
            bladeBeam.setAlpha(alpha);
            return this;
        }

        public BladeBeamBuilder distance(float distance) {
            bladeBeam.setDistance(distance);
            return this;
        }

        public BladeBeamBuilder damage(float damage) {
            bladeBeam.setDamage(damage);
            return this;
        }

        public BladeBeamBuilder zpRotation(float rotation) {
            bladeBeam.setZpRotation(rotation);
            return this;
        }

        public BladeBeamBuilder owner(Entity owner) {
            bladeBeam.setOwner(owner);
            return this;
        }

        public BladeBeam build(float speed) {
            Entity owner = bladeBeam.getOwner();
            if(owner == null) throw new RuntimeException("Owner of blade beam doesn't exist.");
            Vec3 viewVector = owner.getViewVector(0.0f);
            bladeBeam.setDeltaMovement(viewVector.scale(speed));
            bladeBeam.setPos(owner.getEyePosition().add(viewVector));
            bladeBeam.setXRot(owner.getXRot());
            bladeBeam.setYRot(owner.getYRot());
            if(beamConsumer != null) beamConsumer.accept(bladeBeam);
            owner.level().addFreshEntity(bladeBeam);
            return bladeBeam;
        }

        public BladeBeam build() {
            return build(1.0f);
        }
    }
}

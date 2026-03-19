package paulevs.edenring.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LilWormEntity extends PathfinderMob {
	private static final double WALL_SLIDE_SPEED = -0.045D;

	public final AnimationState moveAnimationState = new AnimationState();
	public final AnimationState deathAnimationState = new AnimationState();

	public LilWormEntity(EntityType<? extends LilWormEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 1;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0.35D));
		this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 2.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.24D)
			.add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide()) {
			applyWallSlide();
		}
		if (this.level().isClientSide()) {
			double dx = this.getX() - this.xo;
			double dy = this.getY() - this.yo;
			double dz = this.getZ() - this.zo;
			boolean isMoving = (dx * dx + dz * dz) > 1.0E-7D || Math.abs(dy) > 1.0E-4D;
			this.moveAnimationState.animateWhen(isMoving, this.tickCount);
			this.deathAnimationState.animateWhen(this.isDeadOrDying(), this.tickCount);
		}
	}

	private void applyWallSlide() {
		if (this.onGround() || this.isInWaterOrBubble() || this.isPassenger() || this.isDeadOrDying()) {
			return;
		}

		Vec3 velocity = this.getDeltaMovement();
		if (velocity.y >= 0.0D || !hasNearbyWall() || !isPathLeadingDownEdge()) {
			return;
		}

		this.setDeltaMovement(velocity.x * 0.92D, Math.max(velocity.y, WALL_SLIDE_SPEED), velocity.z * 0.92D);
		this.fallDistance = 0.0F;
	}

	private boolean isPathLeadingDownEdge() {
		if (!this.getNavigation().isInProgress()) {
			return false;
		}

		Path path = this.getNavigation().getPath();
		if (path == null || path.isDone()) {
			return false;
		}

		BlockPos next = path.getNextNodePos();
		BlockPos current = this.blockPosition();
		if (next.getY() >= current.getY()) {
			return false;
		}

		double dx = (next.getX() + 0.5D) - this.getX();
		double dz = (next.getZ() + 0.5D) - this.getZ();
		double horizontalDistSq = dx * dx + dz * dz;
		if (horizontalDistSq > 4.0D || horizontalDistSq < 1.0E-6D) {
			return false;
		}

		Vec3 velocity = this.getDeltaMovement();
		double vx = velocity.x;
		double vz = velocity.z;
		double vSq = vx * vx + vz * vz;
		if (vSq < 1.0E-8D) {
			return true;
		}

		double dot = vx * dx + vz * dz;
		return dot > 0.0D;
	}

	private boolean hasNearbyWall() {
		Level level = this.level();
		double sampleY = this.getY() + Math.max(this.getBbHeight() * 0.5D, 0.2D);
		double offset = Math.max(this.getBbWidth() * 0.55D, 0.25D);

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			double sampleX = this.getX() + direction.getStepX() * offset;
			double sampleZ = this.getZ() + direction.getStepZ() * offset;

			BlockPos posA = BlockPos.containing(sampleX, sampleY, sampleZ);
			BlockPos posB = posA.below();
			if (!level.getBlockState(posA).getCollisionShape(level, posA).isEmpty()) {
				return true;
			}
			if (!level.getBlockState(posB).getCollisionShape(level, posB).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canBeLeashed(net.minecraft.world.entity.player.Player player) {
		return false;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SILVERFISH_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SILVERFISH_DEATH;
	}
}

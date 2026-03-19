package paulevs.edenring.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.edenring.EdenRing;
import paulevs.edenring.world.GravityController;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;getValue()D", ordinal = 0))
	private double eden_changeGravity(AttributeInstance attribute) {
		LivingEntity entity = LivingEntity.class.cast(this);
		return attribute.getValue() * eden_getGravityMultiplier(entity);
	}
	
	private static double eden_getGravityMultiplier(LivingEntity entity) {
		double gravityMultiplier = entity.level().dimension() == EdenRing.EDEN_RING_KEY ?
			GravityController.getGravityMultiplier(entity.getY()) : 1.0;
		double compressor = GravityController.getCompressorMultiplier(entity);
		if (compressor != 1.0) {
			return gravityMultiplier * compressor;
		}
		double gravilite = GravityController.getGraviliteMultiplier(entity);
		return gravityMultiplier * gravilite;
	}
	
	@Inject(method = "checkFallDamage", at = @At("TAIL"))
	private void eden_checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos, CallbackInfo info) {
		LivingEntity entity = LivingEntity.class.cast(this);
		entity.fallDistance *= eden_getGravityMultiplier(entity);
	}
}

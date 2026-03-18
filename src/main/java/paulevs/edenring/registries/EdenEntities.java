package paulevs.edenring.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.RegisterEvent;

import org.betterx.bclib.api.v2.spawning.SpawnRuleBuilder;
import org.betterx.bclib.config.PathConfig;

import paulevs.edenring.EdenRing;
import paulevs.edenring.entities.DiskwingEntity;
import paulevs.edenring.entities.EdenPainting;
import paulevs.edenring.entities.LilWormEntity;
import paulevs.edenring.entities.LightningRayEntity;

public class EdenEntities {
	private static final PathConfig ENTITY_CONFIG = new PathConfig(EdenRing.MOD_ID, "entities");

	private static final ResourceLocation DISKWING_ID = EdenRing.makeID("diskwing");
	private static final ResourceLocation LIL_WORM_ID = EdenRing.makeID("lil_worm");
	private static final ResourceLocation LIGHTNING_RAY_ID = EdenRing.makeID("lightning_ray");
	private static final ResourceLocation LIMPHIUM_PAINTING_ID = EdenRing.makeID("limphium_painting");

	private static final boolean DISKWING_ENABLED = ENTITY_CONFIG.getBooleanRoot(DISKWING_ID.getPath(), true);
	private static final boolean LIL_WORM_ENABLED = ENTITY_CONFIG.getBooleanRoot(LIL_WORM_ID.getPath(), true);

	// Living //
	public static final EntityType<DiskwingEntity> DISKWING = build("diskwing", MobCategory.AMBIENT, 0.9F, 0.25F, DiskwingEntity::new, true);
	public static final EntityType<LilWormEntity> LIL_WORM = build("lil_worm", MobCategory.AMBIENT, 0.5F, 0.2F, LilWormEntity::new, true);

	// Technical //
	public static final EntityType<LightningRayEntity> LIGHTNING_RAY = build("lightning_ray", MobCategory.MISC, 1.0F, 1.0F, LightningRayEntity::new, false);
	public static final EntityType<EdenPainting> LIMPHIUM_PAINTING = EntityType.Builder
			.<EdenPainting>of((EntityFactory<EdenPainting>) EdenPainting::new, MobCategory.MISC)
			.noSummon()
			.sized(0.5F, 0.5F)
			.clientTrackingRange(10)
			.updateInterval(Integer.MAX_VALUE)
			.build(LIMPHIUM_PAINTING_ID.toString());

	private static boolean spawnEggsRegistered;
	private static boolean spawnRulesRegistered;

	public static void init() {
		setupSpawnRules();
	}

	public static void onRegister(RegisterEvent event) {
		if (!event.getRegistryKey().equals(Registries.ENTITY_TYPE)) {
			return;
		}
		event.register(Registries.ENTITY_TYPE, helper -> {
			if (DISKWING_ENABLED) {
				helper.register(DISKWING_ID, DISKWING);
			}
			if (LIL_WORM_ENABLED) {
				helper.register(LIL_WORM_ID, LIL_WORM);
			}
			helper.register(LIGHTNING_RAY_ID, LIGHTNING_RAY);
			helper.register(LIMPHIUM_PAINTING_ID, LIMPHIUM_PAINTING);
			setupSpawnRules();
		});
	}

	public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
		if (DISKWING_ENABLED) {
			event.put(DISKWING, DiskwingEntity.createMobAttributes().build());
		}
		if (LIL_WORM_ENABLED) {
			event.put(LIL_WORM, LilWormEntity.createMobAttributes().build());
		}
	}

	public static void registerSpawnEggs() {
		if (spawnEggsRegistered) {
			return;
		}
		spawnEggsRegistered = true;
		if (DISKWING_ENABLED) {
			EdenItems.REGISTRY.registerEgg(EdenRing.makeID("spawn_egg_diskwing"), DISKWING, 0x5b3e52, 0x978090);
		}
		if (LIL_WORM_ENABLED) {
			EdenItems.REGISTRY.registerEgg(EdenRing.makeID("spawn_egg_lil_worm"), LIL_WORM, 0xE2C98D, 0x78674F);
		}
	}

	private static <T extends Entity> EntityType<T> build(
			String name,
			MobCategory group,
			float width,
			float height,
			EntityFactory<T> entity,
			boolean summonable
	) {
		EntityType.Builder<T> builder = EntityType.Builder
				.<T>of(entity, group)
				.sized(width, height);
		if (!summonable) {
			builder = builder.noSummon();
		}
		return builder.build(EdenRing.makeID(name).toString());
	}

	private static void setupSpawnRules() {
		if (spawnRulesRegistered || (!DISKWING_ENABLED && !LIL_WORM_ENABLED)) {
			return;
		}
		if (DISKWING_ENABLED) {
			SpawnRuleBuilder.start(DISKWING).aboveGround(2).maxNearby(6, 48).buildNoRestrictions(Types.MOTION_BLOCKING);
		}
		if (LIL_WORM_ENABLED) {
			SpawnRuleBuilder.start(LIL_WORM).maxNearby(16, 64).buildOnGround(Types.MOTION_BLOCKING_NO_LEAVES);
		}
		spawnRulesRegistered = true;
	}
}

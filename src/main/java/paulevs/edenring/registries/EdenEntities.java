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
import paulevs.edenring.entities.LightningRayEntity;

public class EdenEntities {
	private static final PathConfig ENTITY_CONFIG = new PathConfig(EdenRing.MOD_ID, "entities");

	private static final ResourceLocation DISKWING_ID = EdenRing.makeID("diskwing");
	private static final ResourceLocation LIGHTNING_RAY_ID = EdenRing.makeID("lightning_ray");
	private static final ResourceLocation LIMPHIUM_PAINTING_ID = EdenRing.makeID("limphium_painting");

	private static final boolean DISKWING_ENABLED = ENTITY_CONFIG.getBooleanRoot(DISKWING_ID.getPath(), true);

	// Living //
	public static final EntityType<DiskwingEntity> DISKWING = build("diskwing", MobCategory.AMBIENT, 0.9F, 0.25F, DiskwingEntity::new);

	// Technical //
	public static final EntityType<LightningRayEntity> LIGHTNING_RAY = build("lightning_ray", MobCategory.MISC, 1.0F, 1.0F, LightningRayEntity::new);
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
			helper.register(LIGHTNING_RAY_ID, LIGHTNING_RAY);
			helper.register(LIMPHIUM_PAINTING_ID, LIMPHIUM_PAINTING);
			setupSpawnRules();
		});
	}

	public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
		if (DISKWING_ENABLED) {
			event.put(DISKWING, DiskwingEntity.createMobAttributes().build());
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
	}

	private static <T extends Entity> EntityType<T> build(
			String name,
			MobCategory group,
			float width,
			float height,
			EntityFactory<T> entity
	) {
		return EntityType.Builder
				.<T>of(entity, group)
				.noSummon()
				.sized(width, height)
				.build(EdenRing.makeID(name).toString());
	}

	private static void setupSpawnRules() {
		if (spawnRulesRegistered || !DISKWING_ENABLED) {
			return;
		}
		SpawnRuleBuilder.start(DISKWING).maxNearby(8).buildNoRestrictions(Types.MOTION_BLOCKING);
		spawnRulesRegistered = true;
	}
}

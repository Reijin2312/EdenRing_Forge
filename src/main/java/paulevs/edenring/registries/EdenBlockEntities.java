package paulevs.edenring.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.registries.RegisterEvent;

import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.entities.EdenPortalBlockEntity;

public class EdenBlockEntities {
	private static final ResourceLocation EDEN_PORTAL_ID = EdenRing.makeID("eden_portal");

	public static final BlockEntityType<EdenPortalBlockEntity> EDEN_PORTAL = build(BlockEntityType.Builder.of(
			EdenPortalBlockEntity::new,
			EdenBlocks.PORTAL_CENTER
	));

	private static <T extends BlockEntity> BlockEntityType<T> build(BlockEntityType.Builder<T> builder) {
		return builder.build(null);
	}

	public static void onRegister(RegisterEvent event) {
		if (!event.getRegistryKey().equals(Registries.BLOCK_ENTITY_TYPE)) {
			return;
		}
		event.register(Registries.BLOCK_ENTITY_TYPE, helper -> helper.register(EDEN_PORTAL_ID, EDEN_PORTAL));
	}

	public static void init() {
	}
}

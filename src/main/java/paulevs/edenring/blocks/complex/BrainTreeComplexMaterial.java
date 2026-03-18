package paulevs.edenring.blocks.complex;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import paulevs.edenring.EdenRing;

public class BrainTreeComplexMaterial extends EdenWoodenComplexMaterial {
	public BrainTreeComplexMaterial(String baseName) {
		super(EdenRing.MOD_ID, baseName, "eden", MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY);
	}
	
	@Override
	protected BlockBehaviour.Properties getBlockSettings() {
		return BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.NETHERITE_BLOCK).mapColor(planksColor);
	}
}

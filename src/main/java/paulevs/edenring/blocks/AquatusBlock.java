package paulevs.edenring.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import org.betterx.bclib.blocks.BaseRotatedPillarBlock;

public class AquatusBlock extends BaseRotatedPillarBlock {
	public AquatusBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WART_BLOCK));
	}
}

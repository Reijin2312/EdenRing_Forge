package paulevs.edenring.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import org.betterx.bclib.blocks.BaseBlock;

public class GraviliteLampBlock extends BaseBlock {
	public GraviliteLampBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel(state -> 15));
	}
}

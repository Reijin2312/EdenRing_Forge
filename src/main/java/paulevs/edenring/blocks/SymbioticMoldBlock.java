package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.client.render.BCLRenderLayer;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class SymbioticMoldBlock extends CeilPlantBlock {
	public SymbioticMoldBlock(int emission) {
		super(BlockBehaviour.Properties.copy(Blocks.WARPED_ROOTS).lightLevel(state -> emission).offsetType(OffsetType.NONE));
	}
	
	@Override
	@Nullable
	@OnlyIn(Dist.CLIENT)
	public BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		String modId = blockId.getNamespace();
		String name = blockId.getPath();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/" + name);
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.BLOCK_TRANSLUCENT_PLANT, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	public BCLRenderLayer getRenderLayer() {
		return BCLRenderLayer.CUTOUT;
	}
}

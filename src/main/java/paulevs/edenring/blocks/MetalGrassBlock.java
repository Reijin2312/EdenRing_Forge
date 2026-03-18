package paulevs.edenring.blocks;

import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.betterx.bclib.client.models.BasePatterns;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.interfaces.BlockModelProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MetalGrassBlock extends GrassBlock implements BlockModelProvider {
	public MetalGrassBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).sound(SoundType.COPPER));
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getItemModel(ResourceLocation blockId) {
		return this.getBlockModel(blockId, this.defaultBlockState());
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getBlockModel(ResourceLocation blockId, BlockState blockState) {
		Map<String, String> textures = Maps.newHashMap();
		String modId = blockId.getNamespace();
		String name = blockId.getPath();
		textures.put("%top%", modId + ":block/" + name + "_top");
		textures.put("%side%", modId + ":block/" + name + "_side");
		textures.put("%bottom%", "minecraft:block/dirt");
		Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_TOP_SIDE_BOTTOM, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public UnbakedModel getModelVariant(ResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		ResourceLocation modelId = new ResourceLocation(stateId.getNamespace(), "block/" + stateId.getPath());
		this.registerBlockModel(stateId, modelId, blockState, modelCache);
		return ModelsHelper.createRandomTopModel(modelId);
	}
}
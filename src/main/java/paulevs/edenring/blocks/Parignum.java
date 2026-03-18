package paulevs.edenring.blocks;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.math.Transformation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.client.models.ModelsHelper;
import org.betterx.bclib.client.models.ModelsHelper.MultiPartBuilder;
import org.betterx.bclib.client.models.PatternsHelper;
import org.betterx.bclib.util.BlocksHelper;

import java.util.Map;
import java.util.Optional;

public class Parignum extends SixSidePlant {
	public Parignum() {
		super(BlockBehaviour.Properties.copy(Blocks.VINE));
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public UnbakedModel getModelVariant(ResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		MultiPartBuilder model = MultiPartBuilder.create(stateDefinition);
		for (Direction dir: BlocksHelper.DIRECTIONS) {
			String keyPrefix = "block/" + stateId.getPath() + "_parignum_";
			ResourceLocation noFlowers = new ResourceLocation(stateId.getNamespace(), keyPrefix + "no_flowers_" + dir.getName());
			ResourceLocation flowers1 = new ResourceLocation(stateId.getNamespace(), keyPrefix + "flowers_1_" + dir.getName());
			ResourceLocation flowers2 = new ResourceLocation(stateId.getNamespace(), keyPrefix + "flowers_2_" + dir.getName());
			ResourceLocation flowers3 = new ResourceLocation(stateId.getNamespace(), keyPrefix + "flowers_3_" + dir.getName());
			ResourceLocation flowers4 = new ResourceLocation(stateId.getNamespace(), keyPrefix + "flowers_4_" + dir.getName());
			
			modelCache.put(noFlowers, makeModel(stateId, null));
			modelCache.put(flowers1, makeModel(stateId, "_flowers_1"));
			modelCache.put(flowers2, makeModel(stateId, "_flowers_2"));
			modelCache.put(flowers3, makeModel(stateId, "_flowers_3"));
			modelCache.put(flowers4, makeModel(stateId, "_flowers_4"));
			
			Transformation transformation = new Transformation(null, dir.getOpposite().getRotation(), null, null);
			
			ResourceLocation stateModel = new ResourceLocation(
				stateId.getNamespace(),
				keyPrefix + "state_" + dir.getName()
			);
			modelCache.put(stateModel, new MultiVariant(Lists.newArrayList(
				new Variant(noFlowers, transformation, false, 2),
				new Variant(flowers1, transformation, false, 1),
				new Variant(flowers2, transformation, false, 1),
				new Variant(flowers3, transformation, false, 1),
				new Variant(flowers4, transformation, false, 1)
			)));
			
			int index = dir.get3DDataValue();
			model.part(stateModel).setCondition(state -> state.getValue(DIRECTIONS[index])).add();
		}
		
		return model.build();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		String modId = itemID.getNamespace();
		String name = itemID.getPath();
		Map<String, String> textures = Maps.newHashMap();
		textures.put("%texture%", modId + ":block/" + name);
		textures.put("%overlay%", modId + ":block/" + name + "_flowers_1");
		Optional<String> pattern = PatternsHelper.createJson(EdenPatterns.ITEM_TINTED_OVERLAY, textures);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@OnlyIn(Dist.CLIENT)
	private BlockModel makeModel(ResourceLocation stateId, String overlay) {
		Map<String, String> textures = Maps.newHashMap();
		ResourceLocation patternID;
		if (overlay == null) {
			textures.put("%texture%", stateId.getNamespace() + ":block/" + stateId.getPath());
			patternID = EdenPatterns.BLOCK_PLANE_TINT;
		}
		else {
			textures.put("%texture%", stateId.getNamespace() + ":block/" + stateId.getPath());
			textures.put("%overlay%", stateId.getNamespace() + ":block/" + stateId.getPath() + overlay);
			patternID = EdenPatterns.BLOCK_PLANE_OVERLAY;
		}
		Optional<String> pattern = PatternsHelper.createJson(patternID, textures);
		return ModelsHelper.fromPattern(pattern);
	}
}

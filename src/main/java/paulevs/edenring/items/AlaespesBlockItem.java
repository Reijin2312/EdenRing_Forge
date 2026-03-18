package paulevs.edenring.items;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.blocks.BaseDoublePlantBlock;
import paulevs.edenring.registries.EdenBlocks;

public class AlaespesBlockItem extends BlockItem {
	public AlaespesBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState clickedState = level.getBlockState(pos);
		if (clickedState.is(EdenBlocks.ALAESPES_SMALL)) {
			return growToTall(context, pos);
		}
		return super.useOn(context);
	}

	@Override
	protected BlockState getPlacementState(BlockPlaceContext context) {
		return EdenBlocks.ALAESPES_SMALL.getStateForPlacement(context);
	}

	private InteractionResult growToTall(UseOnContext context, BlockPos pos) {
		Level level = context.getLevel();
		BlockPos above = pos.above();
		if (!level.getBlockState(above).isAir()) {
			return InteractionResult.FAIL;
		}

		BlockState bottom = getBlock().defaultBlockState().setValue(BaseDoublePlantBlock.TOP, false);
		if (!bottom.canSurvive(level, pos)) {
			return InteractionResult.FAIL;
		}

		if (!level.isClientSide()) {
			BlockState top = bottom.setValue(BaseDoublePlantBlock.TOP, true);
			level.setBlock(pos, bottom, 3);
			level.setBlock(above, top, 3);

			Player player = context.getPlayer();
			ItemStack stack = context.getItemInHand();
			if (player == null || !player.getAbilities().instabuild) {
				stack.shrink(1);
			}

			SoundType soundType = bottom.getSoundType(level, pos, player);
			level.playSound(
				player,
				pos,
				soundType.getPlaceSound(),
				SoundSource.BLOCKS,
				(soundType.getVolume() + 1.0F) * 0.5F,
				soundType.getPitch() * 0.8F
			);
		}

		return InteractionResult.sidedSuccess(level.isClientSide());
	}
}

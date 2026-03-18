package paulevs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.betterx.bclib.api.v3.datagen.TagDataProvider;
import org.betterx.worlds.together.tag.v3.CommonBlockTags;
import org.betterx.worlds.together.tag.v3.TagManager;
import paulevs.edenring.EdenRing;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EdenRingBlockTagDataProvider extends TagDataProvider<Block> {
    public EdenRingBlockTagDataProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture,
            ExistingFileHelper existingFileHelper
    ) {
        super(
                TagManager.BLOCKS,
                List.of(EdenRing.MOD_ID),
                Set.of(CommonBlockTags.NETHER_MYCELIUM),
                output,
                registriesFuture,
                existingFileHelper
        );
    }
}

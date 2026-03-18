package paulevs.datagen.recipes;

import org.betterx.bclib.api.v3.datagen.BlockLootTableProvider;
import paulevs.edenring.EdenRing;

import net.minecraft.data.PackOutput;

import java.util.List;

public class EdenRingBlockLootTableProvider extends BlockLootTableProvider {
    public EdenRingBlockLootTableProvider(PackOutput output) {
        super(output, List.of(EdenRing.MOD_ID));
    }
}

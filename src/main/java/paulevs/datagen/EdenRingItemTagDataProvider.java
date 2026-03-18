package paulevs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.betterx.bclib.api.v3.datagen.TagDataProvider;
import org.betterx.worlds.together.tag.v3.TagManager;
import paulevs.edenring.EdenRing;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EdenRingItemTagDataProvider extends TagDataProvider<Item> {
    public EdenRingItemTagDataProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture,
            ExistingFileHelper existingFileHelper
    ) {
        super(TagManager.ITEMS, List.of(EdenRing.MOD_ID), output, registriesFuture, existingFileHelper);
    }
}

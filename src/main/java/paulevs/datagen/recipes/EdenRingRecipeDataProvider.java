package paulevs.datagen.recipes;

import org.betterx.bclib.api.v3.datagen.RecipeDataProvider;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenRecipes;

import net.minecraft.data.PackOutput;

import java.util.List;

public class EdenRingRecipeDataProvider extends RecipeDataProvider {
    public EdenRingRecipeDataProvider(PackOutput output) {
        super(List.of(EdenRing.MOD_ID), output);
    }

    public static void buildRecipes() {
        EdenRecipes.register();
    }
}

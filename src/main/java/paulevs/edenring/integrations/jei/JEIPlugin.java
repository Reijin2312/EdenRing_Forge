package paulevs.edenring.integrations.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;
import paulevs.edenring.registries.EdenItems;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
	@Override
	public ResourceLocation getPluginUid() {
		return EdenRing.makeID("jei_plugin");
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addIngredientInfo(
			EdenItems.GUIDE_BOOK,
			Component.translatable("edenring.jei.info.guide_book.1")
		);
		registration.addIngredientInfo(
			EdenBlocks.GRAVITY_COMPRESSOR,
			Component.translatable("edenring.jei.info.gravity_compressor.1"),
			Component.translatable("edenring.jei.info.gravity_compressor.2")
		);
	}
}

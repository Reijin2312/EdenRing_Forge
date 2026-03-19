package paulevs.edenring.registries;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import org.betterx.bclib.api.v2.ComposterAPI;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.registry.ItemRegistry;
import paulevs.edenring.EdenRing;
import paulevs.edenring.config.Configs;
import paulevs.edenring.items.AstralliumItem;
import paulevs.edenring.items.EdenPaintingItem;
import paulevs.edenring.items.GuideBookItem;
import paulevs.edenring.items.material.AstralliumArmorMaterial;
import paulevs.edenring.items.material.AstralliumToolMaterial;

public class EdenItems {
	public static final ItemRegistry REGISTRY = new ItemRegistry(Configs.ITEMS);
	
	public static final Item GUIDE_BOOK = register("guide_book", new GuideBookItem(REGISTRY.makeItemSettings().stacksTo(16)));
	public static final Item LIMPHIUM_LEAF = register("limphium_leaf", new ModelProviderItem(REGISTRY.makeItemSettings()));
	public static final Item LIMPHIUM_LEAF_DRYED = register("limphium_leaf_dryed", new ModelProviderItem(REGISTRY.makeItemSettings()));
	public static final Item LIMPHIUM_PAINTING = register("limphium_painting", new EdenPaintingItem(EntityType.PAINTING, REGISTRY.makeItemSettings()));
	public static final Item RAW_ASTRALLIUM = register("raw_astrallium", new ModelProviderItem(REGISTRY.makeItemSettings()));
	public static final Item ASTRALLIUM = register("astrallium", new AstralliumItem(REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_SWORD = register("astrallium_sword", new SwordItem(AstralliumToolMaterial.ASTRALLIUM, 3, -2.4F, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_PICKAXE = register("astrallium_pickaxe", new PickaxeItem(AstralliumToolMaterial.ASTRALLIUM, 1, -2.8F, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_AXE = register("astrallium_axe", new AxeItem(AstralliumToolMaterial.ASTRALLIUM, 5.0F, -3.0F, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_SHOVEL = register("astrallium_shovel", new ShovelItem(AstralliumToolMaterial.ASTRALLIUM, 1.5F, -3.0F, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_HOE = register("astrallium_hoe", new HoeItem(AstralliumToolMaterial.ASTRALLIUM, -2, -1.0F, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_HELMET = register("astrallium_helmet", new ArmorItem(AstralliumArmorMaterial.ASTRALLIUM, ArmorItem.Type.HELMET, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_CHESTPLATE = register("astrallium_chestplate", new ArmorItem(AstralliumArmorMaterial.ASTRALLIUM, ArmorItem.Type.CHESTPLATE, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_LEGGINGS = register("astrallium_leggings", new ArmorItem(AstralliumArmorMaterial.ASTRALLIUM, ArmorItem.Type.LEGGINGS, REGISTRY.makeItemSettings()));
	// public static final Item ASTRALLIUM_BOOTS = register("astrallium_boots", new ArmorItem(AstralliumArmorMaterial.ASTRALLIUM, ArmorItem.Type.BOOTS, REGISTRY.makeItemSettings()));
	public static final Item ASTRALLIUM_SWORD = null;
	public static final Item ASTRALLIUM_PICKAXE = null;
	public static final Item ASTRALLIUM_AXE = null;
	public static final Item ASTRALLIUM_SHOVEL = null;
	public static final Item ASTRALLIUM_HOE = null;
	public static final Item ASTRALLIUM_HELMET = null;
	public static final Item ASTRALLIUM_CHESTPLATE = null;
	public static final Item ASTRALLIUM_LEGGINGS = null;
	public static final Item ASTRALLIUM_BOOTS = null;
	
	public static void init() {
		ComposterAPI.allowCompost(0.3F, LIMPHIUM_LEAF);
		ComposterAPI.allowCompost(0.3F, LIMPHIUM_LEAF_DRYED);
	}
	
	private static Item register(String name, Item item) {
		REGISTRY.register(EdenRing.makeID(name), item);
		return item;
	}

	public static ItemRegistry getItemRegistry() {
		return REGISTRY;
	}
}

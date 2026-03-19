package paulevs.edenring;

import org.betterx.bclib.api.v2.datafixer.DataFixerAPI;
import org.betterx.bclib.api.v2.datafixer.ForcedLevelPatch;
import org.betterx.bclib.api.v2.datafixer.MigrationProfile;
import org.betterx.bclib.creativetab.BCLCreativeTabManager;
import org.betterx.bclib.registry.BaseRegistry;
import org.betterx.worlds.together.util.Logger;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import org.betterx.worlds.together.world.WorldConfig;
import paulevs.edenring.config.Configs;
import paulevs.edenring.registries.*;
import paulevs.edenring.world.EdenPortal;
import paulevs.edenring.world.generator.GeneratorOptions;

public class EdenRing {
public static final String MOD_ID = "edenring";
public static final Logger LOGGER = new Logger(MOD_ID);

public static final ResourceKey<DimensionType> EDEN_RING_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, makeID(MOD_ID));
public static final ResourceKey<Level> EDEN_RING_KEY = ResourceKey.create(Registries.DIMENSION, makeID(MOD_ID));
private static final ResourceLocation[] GUIDE_BOOK_LOOT_TABLES = new ResourceLocation[] {
	new ResourceLocation("minecraft", "chests/end_city_treasure"),
	new ResourceLocation("minecraft", "chests/buried_treasure"),
	new ResourceLocation("minecraft", "chests/desert_pyramid"),
	new ResourceLocation("minecraft", "chests/jungle_temple"),
	new ResourceLocation("minecraft", "chests/pillager_outpost"),
	new ResourceLocation("minecraft", "chests/shipwreck_treasure"),
	new ResourceLocation("minecraft", "chests/simple_dungeon")
};
	
public void onInitialize() {
  WorldConfig.registerModCache(MOD_ID); //idk

  GeneratorOptions.init();
  EdenBlocks.init();
  EdenBiomes.register();
  EdenEntities.init();
  EdenItems.init();
  // EdenRecipes.register(); Use data generation
  EdenParticles.ensureStaticallyLoadedServerside();
  Configs.saveConfigs();

  EdenPortal.init();
  
  DataFixerAPI.registerPatch(() -> new ForcedLevelPatch(MOD_ID, "0.2.1") {
    @Override
    protected Boolean runLevelDatPatch(CompoundTag root, MigrationProfile profile) {
      CompoundTag worldGenSettings = root.getCompound("Data").getCompound("WorldGenSettings");
      CompoundTag dimensions = worldGenSettings.getCompound("dimensions");
      String dimensionKey = EDEN_RING_KEY.location().toString();
      
      if (!dimensions.contains(dimensionKey)) {
        long seed = worldGenSettings.getLong("seed");
        CompoundTag dimRoot = new CompoundTag();
        dimensions.put(dimensionKey, dimRoot);
        dimRoot.putString("type", dimensionKey);
        
        CompoundTag generator = new CompoundTag();
        dimRoot.put("generator", generator);
        
        generator.putString("settings", "minecraft:floating_islands");
        generator.putString("type", "minecraft:noise");
        generator.putLong("seed", seed);
        
        CompoundTag biomeSource = new CompoundTag();
        generator.put("biome_source", biomeSource);
        biomeSource.putString("type", "edenring:biome_source");
        
        return true;
      }
      else {
        CompoundTag dimRoot = dimensions.getCompound(dimensionKey);
        boolean updated = false;
        if (!dimensionKey.equals(dimRoot.getString("type"))) {
          dimRoot.putString("type", dimensionKey);
          updated = true;
        }

        CompoundTag generator = dimRoot.getCompound("generator");
        if (!dimRoot.contains("generator", Tag.TAG_COMPOUND)) {
          dimRoot.put("generator", generator);
          updated = true;
        }

        if (!"minecraft:noise".equals(generator.getString("type"))) {
          generator.putString("type", "minecraft:noise");
          updated = true;
        }
        if (!"minecraft:floating_islands".equals(generator.getString("settings"))) {
          generator.putString("settings", "minecraft:floating_islands");
          updated = true;
        }

        CompoundTag biomeSource = generator.getCompound("biome_source");
        if (!generator.contains("biome_source", Tag.TAG_COMPOUND)) {
          generator.put("biome_source", biomeSource);
          updated = true;
        }
        if (!"edenring:biome_source".equals(biomeSource.getString("type"))) {
          biomeSource.putString("type", "edenring:biome_source");
          updated = true;
        }

        return updated;
      }
    }
  });
  
}

public static void registerCreativeTabs() {
  BCLCreativeTabManager.create(MOD_ID)
      .createTab("eden_tab")
      .setPredicate(
          item -> BaseRegistry.getModBlockItems(MOD_ID).contains(item)
              || BaseRegistry.getModItems(MOD_ID).contains(item)
      )
      .setIcon(EdenBlocks.GRAVILITE_SHARDS)
      .build()
      .processBCLRegistry()
      .register();
}

public static void onLootTableLoad(LootTableLoadEvent event) {
  ResourceLocation tableId = event.getName();
  for (ResourceLocation resourceLocation: GUIDE_BOOK_LOOT_TABLES) {
    if (!tableId.equals(resourceLocation)) {
      continue;
    }

    LootPool.Builder builder = LootPool.lootPool();
    builder.setRolls(ConstantValue.exactly(1));
    builder.when(LootItemRandomChanceCondition.randomChance(0.4f));
    builder.add(LootItem.lootTableItem(EdenItems.GUIDE_BOOK));
    event.getTable().addPool(builder.build());
    return;
  }
}

public static ResourceLocation makeID(String path) {
  return new ResourceLocation(MOD_ID, path);
}
}

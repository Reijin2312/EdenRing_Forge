package paulevs.edenring.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.registries.RegisterEvent;

import paulevs.edenring.EdenRing;

public class EdenSounds {
	public static SoundEvent BLOCK_ELECTRIC;
	public static Holder<SoundEvent> MUSIC_COMMON;

	public static Holder<SoundEvent> AMBIENCE_BRAINSTORM;
	public static Holder<SoundEvent> AMBIENCE_GOLDEN_FOREST;
	public static Holder<SoundEvent> AMBIENCE_LAKESIDE_DESSERT;
	public static Holder<SoundEvent> AMBIENCE_MYCOTIC_FOREST;
	public static Holder<SoundEvent> AMBIENCE_PULSE_FOREST;
	public static Holder<SoundEvent> AMBIENCE_WIND_VALLEY;

	public static SoundEvent DISKWING_AMBIENT;
	public static SoundEvent DISKWING_DAMAGE;

	private static final ResourceKey<SoundEvent> KEY_BLOCK_ELECTRIC = key(path("block", "electric"));
	private static final ResourceKey<SoundEvent> KEY_MUSIC_COMMON = key(path("music", "common"));
	private static final ResourceKey<SoundEvent> KEY_AMBIENCE_BRAINSTORM = key(path("ambience", "brainstorm"));
	private static final ResourceKey<SoundEvent> KEY_AMBIENCE_GOLDEN_FOREST = key(path("ambience", "golden_forest"));
	private static final ResourceKey<SoundEvent> KEY_AMBIENCE_LAKESIDE_DESSERT = key(path("ambience", "lakeside_dessert"));
	private static final ResourceKey<SoundEvent> KEY_AMBIENCE_MYCOTIC_FOREST = key(path("ambience", "mycotic_forest"));
	private static final ResourceKey<SoundEvent> KEY_AMBIENCE_PULSE_FOREST = key(path("ambience", "pulse_forest"));
	private static final ResourceKey<SoundEvent> KEY_AMBIENCE_WIND_VALLEY = key(path("ambience", "wind_valley"));
	private static final ResourceKey<SoundEvent> KEY_DISKWING_AMBIENT = key(path("entity", "diskwing", "ambient"));
	private static final ResourceKey<SoundEvent> KEY_DISKWING_DAMAGE = key(path("entity", "diskwing", "damage"));

	public static void onRegister(RegisterEvent event) {
		if (!event.getRegistryKey().equals(Registries.SOUND_EVENT)) {
			return;
		}

		event.register(Registries.SOUND_EVENT, helper -> {
			BLOCK_ELECTRIC = register(helper, KEY_BLOCK_ELECTRIC);
			MUSIC_COMMON = registerForHolder(helper, KEY_MUSIC_COMMON);

			AMBIENCE_BRAINSTORM = registerForHolder(helper, KEY_AMBIENCE_BRAINSTORM);
			AMBIENCE_GOLDEN_FOREST = registerForHolder(helper, KEY_AMBIENCE_GOLDEN_FOREST);
			AMBIENCE_LAKESIDE_DESSERT = registerForHolder(helper, KEY_AMBIENCE_LAKESIDE_DESSERT);
			AMBIENCE_MYCOTIC_FOREST = registerForHolder(helper, KEY_AMBIENCE_MYCOTIC_FOREST);
			AMBIENCE_PULSE_FOREST = registerForHolder(helper, KEY_AMBIENCE_PULSE_FOREST);
			AMBIENCE_WIND_VALLEY = registerForHolder(helper, KEY_AMBIENCE_WIND_VALLEY);

			DISKWING_AMBIENT = register(helper, KEY_DISKWING_AMBIENT);
			DISKWING_DAMAGE = register(helper, KEY_DISKWING_DAMAGE);
		});
	}

	private static SoundEvent register(RegisterEvent.RegisterHelper<SoundEvent> helper, ResourceKey<SoundEvent> key) {
		SoundEvent event = SoundEvent.createVariableRangeEvent(key.location());
		helper.register(key.location(), event);
		return event;
	}

	private static Holder<SoundEvent> registerForHolder(
			RegisterEvent.RegisterHelper<SoundEvent> helper,
			ResourceKey<SoundEvent> key
	) {
		helper.register(key.location(), SoundEvent.createVariableRangeEvent(key.location()));
		return BuiltInRegistries.SOUND_EVENT.getHolder(key).orElseThrow();
	}

	private static ResourceKey<SoundEvent> key(String id) {
		return ResourceKey.create(Registries.SOUND_EVENT, EdenRing.makeID(id));
	}

	private static String path(String... parts) {
		StringBuilder builder = new StringBuilder(EdenRing.MOD_ID);
		for (String part : parts) {
			builder.append('.');
			builder.append(part);
		}
		return builder.toString();
	}

	public static void init() {
	}
}

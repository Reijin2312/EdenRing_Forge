package paulevs.edenring.items;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class AstralliumItem extends Item {
    public AstralliumItem(Properties properties) {
        super(properties.rarity(Rarity.UNCOMMON));
    }
}

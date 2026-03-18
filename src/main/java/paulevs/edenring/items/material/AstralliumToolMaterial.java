package paulevs.edenring.items.material;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import paulevs.edenring.registries.EdenItems;

import java.util.function.Supplier;

public enum AstralliumToolMaterial implements Tier {
    ASTRALLIUM(4, 1792, 9.0F, 3.0F, 18, () -> Ingredient.of(EdenItems.ASTRALLIUM));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damageBonus;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    AstralliumToolMaterial(
            int level,
            int uses,
            float speed,
            float damageBonus,
            int enchantability,
            Supplier<Ingredient> repairIngredient
    ) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damageBonus = damageBonus;
        this.enchantability = enchantability;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damageBonus;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}

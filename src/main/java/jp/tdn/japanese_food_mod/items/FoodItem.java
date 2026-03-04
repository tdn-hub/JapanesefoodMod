package jp.tdn.japanese_food_mod.items;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class FoodItem extends Item {
    public FoodItem(int hunger, float saturation){
        super(new Properties().food(new FoodProperties.Builder().nutrition(hunger).saturationModifier(saturation).build()));
    }

    public FoodItem(Item.Properties properties){
        super(properties);
    }
}

package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class FoodItem extends Item {
    public FoodItem(int hunger, float saturation){
        super(new Properties().group(JPItems.ItemGroup_Japanese).food(new Food.Builder().hunger(hunger).saturation(saturation).build()));
    }

    public FoodItem(Item.Properties properties){
        super(properties);
    }
}

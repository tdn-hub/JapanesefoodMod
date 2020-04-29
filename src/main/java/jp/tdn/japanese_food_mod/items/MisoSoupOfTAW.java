package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class MisoSoupOfTAW extends MisoSoupItem {
    public MisoSoupOfTAW(){
        super(new Item.Properties().group(JPItems.ItemGroup_Japanese).food(new Food.Builder().hunger(6).saturation(4.0f).build()));
    }
}

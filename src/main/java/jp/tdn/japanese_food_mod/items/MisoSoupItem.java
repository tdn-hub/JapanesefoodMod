package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.JapaneseFoodItemGroup;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.lists.ItemList;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class MisoSoupItem extends Item {
    public MisoSoupItem(){
        super(new Item.Properties()
                .food(new Food.Builder().hunger(3).saturation(2.5f).build())
                .group(ItemList.ItemGroup_Japanese));

        this.setRegistryName(JapaneseFoodMod.MOD_ID, "miso_soup");
    }
}

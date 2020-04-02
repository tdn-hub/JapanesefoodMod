package jp.tdn.japanese_food_mod;

import jp.tdn.japanese_food_mod.lists.ItemList;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class JapaneseFoodItemGroup extends ItemGroup{
    public JapaneseFoodItemGroup(){
        super("japanese_food_mod");
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(ItemList.TYAWAN);
    }
}

package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CockWareItem extends Item {
    public CockWareItem(){
        super(new Item.Properties().maxStackSize(1).group(JPItems.ItemGroup_Japanese));
    }

    @Override
    public ItemStack getContainerItem(ItemStack item){
        return new ItemStack(this);
    }

    @Override
    public boolean hasContainerItem(){
        return true;
    }
}

package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class YeastCellItem extends Item {
    private static Item container;
    public YeastCellItem(Item container){
        super(new Properties().group(JPItems.ItemGroup_Japanese));
        YeastCellItem.container = container;
    }

    @Override
    public ItemStack getContainerItem(ItemStack item){
        return new ItemStack(container);
    }

    @Override
    public boolean hasContainerItem(){
        return true;
    }
}

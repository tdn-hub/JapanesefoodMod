package jp.tdn.japanese_food_mod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CookWareItem extends Item {
    public CookWareItem(){
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack item){
        return new ItemStack(this);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack item){
        return true;
    }
}

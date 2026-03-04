package jp.tdn.japanese_food_mod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ContainerItem extends Item {
    private static Item container;
    public ContainerItem(Item container){
        super(new Properties());
        ContainerItem.container = container;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack item){
        return new ItemStack(container);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack item){
        return true;
    }
}

package jp.tdn.japanese_food_mod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

import javax.annotation.Nonnull;

public class DrinkItem extends FoodItem {
    public DrinkItem(int hunger, float saturation){
        super(hunger, saturation);
    }

    public DrinkItem(Item.Properties properties){
        super(properties);
    }

    @Override
    @Nonnull
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}

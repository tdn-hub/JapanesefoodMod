package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class MisoSoupItem extends DrinkItem {
    public MisoSoupItem(int hunger, float saturation){
        super(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(hunger).saturationModifier(saturation).build()));
    }

    public MisoSoupItem(Item.Properties properties){
        super(properties);
    }

    @Override
    @Nonnull
    public ItemStack finishUsingItem(@Nonnull ItemStack item, @Nonnull Level level, @Nonnull LivingEntity entity) {
        super.finishUsingItem(item, level, entity);
        return new ItemStack(JPItems.TYAWAN.get());
    }
}

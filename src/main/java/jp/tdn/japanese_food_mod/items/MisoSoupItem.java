package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MisoSoupItem extends DrinkItem {
    public MisoSoupItem(int hunger, float saturation){
        super(new Item.Properties()
                .food(new Food.Builder().hunger(hunger).saturation(saturation).build())
                .group(JPItems.ItemGroup_Japanese));
    }

    public MisoSoupItem(Item.Properties properties){
        super(properties);
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack item, @Nonnull World world, @Nonnull LivingEntity entity) {
        super.onItemUseFinish(item, world, entity);
        return new ItemStack(JPItems.TYAWAN);
    }
}

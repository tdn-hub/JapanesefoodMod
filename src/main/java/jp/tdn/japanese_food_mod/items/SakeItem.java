package jp.tdn.japanese_food_mod.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SakeItem extends DrinkItem {
    public SakeItem(int hunger, float saturation){
        super(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(hunger)
                .saturationModifier(saturation)
                .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600), 0.5f)
                .build()));
    }
}

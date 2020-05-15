package jp.tdn.japanese_food_mod.items;

import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SakeItem extends DrinkItem {
    public SakeItem(int hunger, float saturation){
        super(new Item.Properties().group(JPItems.ItemGroup_Japanese).food(new Food.Builder().hunger(hunger).saturation(saturation).effect(new EffectInstance(Effects.NAUSEA, 600), 0.5f).build()));
    }
}

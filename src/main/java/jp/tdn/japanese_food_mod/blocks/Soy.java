package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.lists.ItemList;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IItemProvider;

public class Soy extends CropsBlock{
    public Soy(){
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0f).sound(SoundType.CROP));
        this.setRegistryName(JapaneseFoodMod.MOD_ID, "soy");
    }

    @Override
    protected IItemProvider getSeedsItem(){
        return ItemList.SOY;
    }

    @Override
    public int getMaxAge(){
        return 5;
    }
}

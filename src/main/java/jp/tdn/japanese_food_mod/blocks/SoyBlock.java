package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.lists.ItemList;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

public class SoyBlock extends CropsBlock{
    public SoyBlock(){
        super(Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0f).sound(SoundType.CROP));
        this.setRegistryName(JapaneseFoodMod.MOD_ID, "soy");
    }

    @Override
    protected IItemProvider getSeedsItem(){
        return ItemList.SOY_SEEDS;
    }

    @Override
    public int getMaxAge(){
        return 6;
    }


}

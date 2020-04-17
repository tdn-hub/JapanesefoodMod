package jp.tdn.japanese_food_mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class RockSaltBlock extends Block {
    public RockSaltBlock(){
        super(Properties.create(Material.ROCK, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5f));
    }
}

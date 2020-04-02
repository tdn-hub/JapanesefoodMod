package jp.tdn.japanese_food_mod.lists;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockList {
    public static final Block SOY_BLOCK = new Block(Block.Properties
    .create(Material.PLANTS, MaterialColor.GREEN)
    .hardnessAndResistance(1.0f, 1.0f)
    .sound(SoundType.PLANT))
    .setRegistryName(JapaneseFoodMod.MOD_ID, "soy_block");
}

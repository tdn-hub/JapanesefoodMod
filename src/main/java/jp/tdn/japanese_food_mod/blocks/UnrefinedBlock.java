package jp.tdn.japanese_food_mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnrefinedBlock extends Block {
    public static BooleanProperty SAUCE = BooleanProperty.create("sauce");

    public UnrefinedBlock(){
        super(Properties.create(Material.SAND, MaterialColor.BROWN).hardnessAndResistance(1.0f).doesNotBlockMovement().tickRandomly());
        this.setDefaultState(this.getDefaultState().with(SAUCE, true));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public boolean hasUpSideBlock(World world, BlockPos pos){
        BlockPos upSide = pos.up();
        Block up = world.getBlockState(upSide).getBlock();

        return BlockTags.getCollection().get(new ResourceLocation("japanese_food_mod", "heavy")).contains(up);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(SAUCE);
    }
}

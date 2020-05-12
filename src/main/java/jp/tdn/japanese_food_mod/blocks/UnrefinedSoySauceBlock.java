package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.UnrefinedSoySauceTileEntity;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UnrefinedSoySauceBlock extends Block {
    public static BooleanProperty SAUCE = BooleanProperty.create("sauce");

    public UnrefinedSoySauceBlock(){
        super(Properties.create(Material.SAND, MaterialColor.BROWN).hardnessAndResistance(1.0f).doesNotBlockMovement().tickRandomly());
        this.setDefaultState(this.getDefaultState().with(SAUCE, true));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return JPTileEntities.UNREFINED_SOY_SAUCE.create();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isRemote){
            if(state.get(SAUCE) && hasUpSideBlock(world, pos)){
                ItemStack insert = new ItemStack(JPItems.SOY_SAUCE);
                TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof UnrefinedSoySauceTileEntity){
                    if(((UnrefinedSoySauceTileEntity) tileEntity).useSauce() >= 0){
                        entity.inventory.addItemStackToInventory(insert);
                    }else{
                        world.setBlockState(pos, state.with(SAUCE, false));
                    }
                }
            }
        }
        return true;
    }

    public boolean hasUpSideBlock(World world, BlockPos pos){
        BlockPos upSide = pos.up();
        Block up = world.getBlockState(upSide).getBlock();

        return up != Blocks.AIR;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(SAUCE);
    }
}

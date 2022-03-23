package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.OysterShellTileEntity;
import jp.tdn.japanese_food_mod.init.JPItems;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class OysterShellBlock extends HorizontalBlock implements ILiquidContainer {
    public static IntegerProperty NORI = IntegerProperty.create("nori", 0, 2);
    public static DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    private static VoxelShape SHAPE = VoxelShapes.or(Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 10.0D, 3.0D, 10.0D));

    public OysterShellBlock(){
        super(Properties.create(Material.ROCK, MaterialColor.CLAY).doesNotBlockMovement().hardnessAndResistance(2.5f).tickRandomly());
        this.setDefaultState(this.getStateContainer().getBaseState().with(NORI, 0));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return JPTileEntities.OYSTER_SHELL.create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return world.hasWater(pos);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
        if(!worldIn.isRemote()){
            int level = 0;
            TileEntity entity = worldIn.getTileEntity(pos);
            if(entity instanceof OysterShellTileEntity){
                if(!((OysterShellTileEntity) entity).isEmpty()){
                    ((OysterShellTileEntity) entity).useNori();
                    player.inventory.addItemStackToInventory(new ItemStack(JPItems.NORI.get()));

                    setNoriLevel(worldIn, pos, state, getLevel(((OysterShellTileEntity) entity).getnoriRemaining()));
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        TileEntity entity = worldIn.getTileEntity(pos);
        if(entity instanceof OysterShellTileEntity){
            setNoriLevel(worldIn, pos, state, getLevel(((OysterShellTileEntity) entity).getnoriRemaining()));
        }
    }

    public int getLevel(int x){
        int rec;
        if(x < 0) {
            rec = 0;
        }else if(x < 8){
            rec = 1;
        }else{
            rec = 2;
        }
        return rec;
    }

    public void setNoriLevel(World world, BlockPos pos, BlockState state, int level){
        world.setBlockState(pos, state.with(NORI, level));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(DIRECTION, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(DIRECTION, rot.rotate(state.get(DIRECTION)));
    }

    @Override
    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(DIRECTION)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(NORI, DIRECTION);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public FluidState getFluidState(BlockState p_204507_1_) {
        return Fluids.WATER.getStillFluidState(false);
    }

    public boolean canContainFluid(IBlockReader p_204510_1_, BlockPos p_204510_2_, BlockState p_204510_3_, Fluid p_204510_4_) {
        return false;
    }

    public boolean receiveFluid(IWorld p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, FluidState p_204509_4_) {
        return false;
    }
}

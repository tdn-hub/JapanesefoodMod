package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import jp.tdn.japanese_food_mod.blocks.tileentity.OysterShellTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OysterShellBlock extends HorizontalDirectionalBlock implements EntityBlock, LiquidBlockContainer {
    public static final MapCodec<OysterShellBlock> CODEC = simpleCodec(p -> new OysterShellBlock());

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    public static IntegerProperty NORI = IntegerProperty.create("nori", 0, 2);
    public static DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    private static VoxelShape SHAPE = Shapes.or(Block.box(4.0D, 0.0D, 4.0D, 10.0D, 3.0D, 10.0D));

    public OysterShellBlock(){
        super(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).noCollission().strength(2.5f).randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(NORI, 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.OYSTER_SHELL.get().create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.isWaterAt(pos);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult rayTrace) {
        if(!worldIn.isClientSide()){
            BlockEntity entity = worldIn.getBlockEntity(pos);
            if(entity instanceof OysterShellTileEntity oysterShellBlockEntity){
                if(!oysterShellBlockEntity.isEmpty()){
                    oysterShellBlockEntity.useNori();
                    player.getInventory().add(new ItemStack(JPItems.NORI.get()));
                    setNoriLevel(worldIn, pos, state, getLevel(oysterShellBlockEntity.getnoriRemaining()));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.randomTick(state, worldIn, pos, rand);
        BlockEntity entity = worldIn.getBlockEntity(pos);
        if(entity instanceof OysterShellTileEntity oysterShellBlockEntity){
            setNoriLevel(worldIn, pos, state, getLevel(oysterShellBlockEntity.getnoriRemaining()));
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

    public void setNoriLevel(Level world, BlockPos pos, BlockState state, int level){
        world.setBlock(pos, state.setValue(NORI, level), 3);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(DIRECTION, context.getHorizontalDirection().getOpposite());
    }

    @Override
    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(DIRECTION, rot.rotate(state.getValue(DIRECTION)));
    }

    @Override
    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return this.rotate(state, mirrorIn.getRotation(state.getValue(DIRECTION)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORI, DIRECTION);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }
}

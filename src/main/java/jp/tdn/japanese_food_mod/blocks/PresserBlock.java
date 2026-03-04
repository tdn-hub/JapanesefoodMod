package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import jp.tdn.japanese_food_mod.blocks.tileentity.PresserTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PresserBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<PresserBlock> CODEC = simpleCodec(p -> new PresserBlock());

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = Shapes.or(Block.box(2.0D, 0.0D, 1.0D, 14.0D, 13.0D, 14.0D));
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty OIL = IntegerProperty.create("level", 0, 3);
    public static final BooleanProperty PRESSING = BooleanProperty.create("pressing");

    public PresserBlock(){
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).requiresCorrectToolForDrops().strength(2.0f));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(DIRECTION, net.minecraft.core.Direction.NORTH)
                .setValue(OIL, 0)
                .setValue(PRESSING, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.PRESSER.get().create(pos, state);
    }

    @Nonnull
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if(!worldIn.isClientSide){
            final BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if(tileEntity instanceof PresserTileEntity presserBlockEntity)
                ((ServerPlayer) player).openMenu(presserBlockEntity, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState oldState, @Nonnull Level worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof PresserTileEntity presserBlockEntity) {
                final ItemStackHandler inventory = presserBlockEntity.inventory;
                for(int index = 0; index < inventory.getSlots(); ++index){
                    Block.popResource(worldIn, pos, inventory.getStackInSlot(index));
                }
            }
            super.onRemove(oldState, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(DIRECTION, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if(tileEntity instanceof PresserTileEntity presserBlockEntity)
            return ItemHandlerHelper.calcRedstoneFromInventory(presserBlockEntity.inventory);
        return super.getAnalogOutputSignal(blockState, worldIn, pos);
    }

    public void setOil(Level worldIn, BlockPos pos, BlockState state, int level){
        worldIn.setBlock(pos, state.setValue(OIL, Mth.clamp(level, 0, 3)), 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIRECTION, OIL, PRESSING);
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
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}

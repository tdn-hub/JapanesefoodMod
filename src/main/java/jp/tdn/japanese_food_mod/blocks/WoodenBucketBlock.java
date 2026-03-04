package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WoodenBucketBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<WoodenBucketBlock> CODEC = simpleCodec(p -> new WoodenBucketBlock());

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = Shapes.or(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D));
    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty FER = BooleanProperty.create("active");

    public WoodenBucketBlock(){
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).requiresCorrectToolForDrops().strength(2.0f));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(DIRECTION, Direction.NORTH)
                .setValue(FER, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.WOODEN_BUCKET.get().create(pos, state);
    }

    @Nonnull
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Nonnull
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if(!worldIn.isClientSide){
            final BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            worldIn.playSound((Player) null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5f, worldIn.getRandom().nextFloat() * 0.1f + 0.9f);
            if(tileEntity instanceof WoodenBucketTileEntity woodenBucketBlockEntity)
                ((ServerPlayer) player).openMenu(woodenBucketBlockEntity, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState oldState, @Nonnull Level worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof WoodenBucketTileEntity woodenBucketBlockEntity) {
                final ItemStackHandler inventory = woodenBucketBlockEntity.inventory;
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
        if(tileEntity instanceof WoodenBucketTileEntity woodenBucketBlockEntity)
            return ItemHandlerHelper.calcRedstoneFromInventory(woodenBucketBlockEntity.inventory);
        return super.getAnalogOutputSignal(blockState, worldIn, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIRECTION, FER);
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
    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {
        if(state.getValue(FER)){
            double posX = (double) pos.getX();
            double posY = (double) pos.getY() + 1.0D;
            double posZ = (double) pos.getZ();
            worldIn.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}

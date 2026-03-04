package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.FurnaceCauldronTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FurnaceCauldronBlock extends JPHorizontalBlock implements EntityBlock {
    protected static final VoxelShape SHAPE = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D));
    protected static final VoxelShape COLLISION = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D));
    public static final IntegerProperty WATER = IntegerProperty.create("level", 0, 3);

    public FurnaceCauldronBlock(){
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2.0f).noOcclusion());
        registerDefaultState(this.defaultBlockState()
                .setValue(DIRECTION, net.minecraft.core.Direction.NORTH)
                .setValue(WATER, 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.FURNACE_CAULDRON.get().create(pos, state);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return COLLISION;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player playerEntity, BlockHitResult rayTraceResult) {
        if(!world.isClientSide()){
            ItemStack heldItem = playerEntity.getMainHandItem();
            if(heldItem.is(JPItemTags.WATER)){
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if(blockEntity instanceof FurnaceCauldronTileEntity furnaceBlockEntity){
                    if(furnaceBlockEntity.canAddWater()) {
                        furnaceBlockEntity.addWater(heldItem);
                        if(!playerEntity.getAbilities().instabuild){
                            playerEntity.setItemInHand(InteractionHand.MAIN_HAND, heldItem.getCraftingRemainingItem());
                        }
                        world.playSound((Player)null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    setWaterLevel(world, pos, state, furnaceBlockEntity.getWaterRemaining(), furnaceBlockEntity.getMaxWater());
                }
            }else{
                final BlockEntity tileEntity = world.getBlockEntity(pos);
                if (tileEntity instanceof FurnaceCauldronTileEntity furnaceBlockEntity)
                    ((net.minecraft.server.level.ServerPlayer) playerEntity).openMenu(furnaceBlockEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public void setWaterLevel(Level worldIn, BlockPos pos, BlockState state, int water, int max){
        float per = ((float)water / (float)max);
        int level = 0;
        if(per == 0f){
            level = 0;
        }else if(per >= 0.01f && per < 0.5f){
            level = 1;
        }else if(per >= 0.5f && per < 0.95f){
            level = 2;
        }else if(per >= 0.95f && per <= 1.0f){
            level = 3;
        }
        worldIn.setBlock(pos, state.setValue(WATER, level), 3);
    }

    @Override
    public void onRemove(BlockState oldState, @Nonnull Level worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof FurnaceCauldronTileEntity furnaceBlockEntity) {
                final net.neoforged.neoforge.items.ItemStackHandler inventory = furnaceBlockEntity.inventory;
                for(int index = 0; index < inventory.getSlots(); ++index){
                    Block.popResource(worldIn, pos, inventory.getStackInSlot(index));
                }
            }
            super.onRemove(oldState, worldIn, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(DIRECTION, context.getHorizontalDirection().getOpposite())
                .setValue(WATER, 0);
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        final BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if(tileEntity instanceof FurnaceCauldronTileEntity furnaceBlockEntity)
            return net.neoforged.neoforge.items.ItemHandlerHelper.calcRedstoneFromInventory(furnaceBlockEntity.inventory);
        return super.getAnalogOutputSignal(blockState, worldIn, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATER);
    }
}

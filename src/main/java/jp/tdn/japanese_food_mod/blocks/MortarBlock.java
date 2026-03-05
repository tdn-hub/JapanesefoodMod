package jp.tdn.japanese_food_mod.blocks;

import com.mojang.serialization.MapCodec;
import jp.tdn.japanese_food_mod.blocks.tileentity.MortarTileEntity;
import jp.tdn.japanese_food_mod.init.JPBlockEntities;
import jp.tdn.japanese_food_mod.init.JPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MortarBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<MortarBlock> CODEC = simpleCodec(p -> new MortarBlock());

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    public static final DirectionProperty DIRECTION = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty POUNDING = IntegerProperty.create("pounding", 0, 3);

    private static final VoxelShape SHAPE = Shapes.or(Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D));

    public MortarBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2.0f));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(DIRECTION, net.minecraft.core.Direction.NORTH)
                .setValue(POUNDING, 0));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JPBlockEntities.MORTAR.get().create(pos, state);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                               Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof MortarTileEntity mortar)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int poundingState = state.getValue(POUNDING);

        // Put rice into mortar
        if (poundingState == 0 && stack.is(JPItems.RICE.get())) {
            if (!level.isClientSide) {
                mortar.setRice(true);
                stack.shrink(1);
                level.setBlock(pos, state.setValue(POUNDING, 1), 3);
                level.playSound(null, pos, SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        // Pound with pestle
        if (poundingState >= 1 && poundingState <= 2 && stack.is(JPItems.PESTLE.get())) {
            if (player.getCooldowns().isOnCooldown(stack.getItem())) {
                return ItemInteractionResult.CONSUME;
            }
            if (!level.isClientSide) {
                mortar.pound();
                player.getCooldowns().addCooldown(stack.getItem(), 10);
                int newStage = mortar.getPoundingStage();
                if (newStage != poundingState) {
                    level.setBlock(pos, state.setValue(POUNDING, newStage), 3);
                }
                level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS,
                        1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            }
            // Particles on client
            if (level.isClientSide) {
                RandomSource random = level.random;
                for (int i = 0; i < 5; i++) {
                    level.addParticle(ParticleTypes.SNOWFLAKE,
                            pos.getX() + 0.3 + random.nextDouble() * 0.4,
                            pos.getY() + 0.6,
                            pos.getZ() + 0.3 + random.nextDouble() * 0.4,
                            0.0, 0.05, 0.0);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                                Player player, BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof MortarTileEntity mortar)) {
            return InteractionResult.PASS;
        }

        int poundingState = state.getValue(POUNDING);

        if (poundingState == 3) {
            // Collect completed mochi
            if (!level.isClientSide) {
                Block.popResource(level, pos, new ItemStack(JPItems.RICE_CAKE.get(), 4));
                mortar.reset();
                level.setBlock(pos, state.setValue(POUNDING, 0), 3);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (poundingState >= 1) {
            // Return rice if in progress
            if (!level.isClientSide) {
                Block.popResource(level, pos, new ItemStack(JPItems.RICE.get()));
                mortar.reset();
                level.setBlock(pos, state.setValue(POUNDING, 0), 3);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState oldState, @Nonnull Level level, @Nonnull BlockPos pos,
                          BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MortarTileEntity mortar) {
                if (mortar.isComplete()) {
                    Block.popResource(level, pos, new ItemStack(JPItems.RICE_CAKE.get()));
                } else if (mortar.hasRice()) {
                    Block.popResource(level, pos, new ItemStack(JPItems.RICE.get()));
                }
            }
            super.onRemove(oldState, level, pos, newState, isMoving);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(POUNDING) == 3) {
            level.addParticle(ParticleTypes.SNOWFLAKE,
                    pos.getX() + 0.3 + random.nextDouble() * 0.4,
                    pos.getY() + 0.7,
                    pos.getZ() + 0.3 + random.nextDouble() * 0.4,
                    0.0, 0.02, 0.0);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(DIRECTION, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIRECTION, POUNDING);
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

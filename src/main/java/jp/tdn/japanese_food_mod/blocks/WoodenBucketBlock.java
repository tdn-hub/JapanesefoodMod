package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.WoodenBucketTileEntity;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Properties;
import java.util.Random;

public class WoodenBucketBlock extends ContainerBlock {
    protected static final VoxelShape SHAPE;
    public static final DirectionProperty FACING;
    public static final BooleanProperty FER;

    public WoodenBucketBlock(Properties properties){
        super(properties);
        this.registerDefaultState(
                (BlockState)this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FER, false)
        );
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader iBlockReader) {
        return new WoodenBucketTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return JPTileEntities.WOODEN_BUCKET.create();
    }

    @Nonnull
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);
            worldIn.playSound((PlayerEntity) null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), SoundEvents.BARREL_OPEN, SoundCategory.BLOCKS, 0.5f, worldIn.random.nextFloat() * 0.1f + 0.9f);
            if (tileEntity instanceof WoodenBucketTileEntity) {
                player.openMenu((INamedContainerProvider)tileEntity);
            }

            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void animateTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if(state.getValue(FER)){
            double posX = (double) pos.getX();
            double posY = (double) pos.getY() + 1.0D;
            double posZ = (double) pos.getZ();
            Direction direction = Direction.UP;
            worldIn.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    static {
        SHAPE = VoxelShapes.or(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D));
        FACING = HorizontalBlock.FACING;
        FER = BooleanProperty.create("active");
    }
}

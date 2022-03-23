package jp.tdn.japanese_food_mod.blocks;

import jp.tdn.japanese_food_mod.blocks.tileentity.MicroScopeTileEntity;
import jp.tdn.japanese_food_mod.init.JPTileEntities;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
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
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MicroScopeBlock extends ContainerBlock {
    protected static final VoxelShape SHAPE;
    public static final DirectionProperty FACING;
    public static final BooleanProperty IDENTIFY;

    public MicroScopeBlock(Properties properties){
        super(properties);
        this.registerDefaultState(
                (BlockState) this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(IDENTIFY, false)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader iBlockReader) {
        return new MicroScopeTileEntity();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return JPTileEntities.MICROSCOPE.create();
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    @Nonnull
    public VoxelShape getCollisionShape(@Nonnull BlockState p_220071_1_, @Nonnull IBlockReader p_220071_2_, @Nonnull BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return VoxelShapes.empty();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(worldIn.isClientSide){
            return ActionResultType.SUCCESS;
        } else {
            final TileEntity tileEntity = worldIn.getBlockEntity(pos);
            if(tileEntity instanceof MicroScopeTileEntity) {
                player.openMenu((INamedContainerProvider)tileEntity);
            }
        }

        return ActionResultType.CONSUME;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    static {
        SHAPE = VoxelShapes.or(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D,12.0D));
        FACING = HorizontalBlock.FACING;
        IDENTIFY = BooleanProperty.create("active");
    }
}

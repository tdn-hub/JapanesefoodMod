package jp.tdn.japanese_food_mod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

public class UnrefinedBlock extends Block {
    public static BooleanProperty SAUCE = BooleanProperty.create("sauce");

    private static final TagKey<Block> HEAVY_TAG = TagKey.create(
            net.minecraft.core.registries.Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath("japanese_food_mod", "heavy")
    );

    public UnrefinedBlock(){
        super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(1.0f).noCollission().randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(SAUCE, true));
    }

    public boolean hasUpSideBlock(Level world, BlockPos pos){
        BlockPos upSide = pos.above();
        return world.getBlockState(upSide).is(HEAVY_TAG);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SAUCE);
    }
}

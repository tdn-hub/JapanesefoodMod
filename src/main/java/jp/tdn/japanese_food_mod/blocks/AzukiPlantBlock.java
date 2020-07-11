package jp.tdn.japanese_food_mod.blocks;

public class AzukiPlantBlock extends SoyPlantBlock{
    public AzukiPlantBlock(){
        super();
    }

//    @Override
//    @Nonnull
//    protected IItemProvider getSeedsItem(){
//        return JPItems.SOY_BEANS;
//    }

//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
//        if(!world.isRemote){
//            if(this.isMaxAge(state)){
//                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(JPItems.SOY_BEANS,1)));
//                world.setBlockState(pos, this.withAge(0));
//                return ActionResultType.SUCCESS;
//            }else if(player.getHeldItem(hand).getItem() != Items.BONE_MEAL && this.getAge(state) >= 5 && this.getAge(state) <= 6){
//                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(JPItems.EDAMAME, 1)));
//                world.setBlockState(pos, this.withAge(0));
//                return ActionResultType.SUCCESS;
//            }
//        }
//        return ActionResultType.FAIL;
//    }
}

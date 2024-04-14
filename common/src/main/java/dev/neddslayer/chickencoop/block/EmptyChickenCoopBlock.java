package dev.neddslayer.chickencoop.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class EmptyChickenCoopBlock extends Block {
	public EmptyChickenCoopBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.union(
				VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1),
				VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.1875, 0.875, 0.9375),
				VoxelShapes.cuboid(0.8125, 0, 0.0625, 0.9375, 0.875, 0.9375),
				VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.875, 0.1875),
				VoxelShapes.cuboid(0.0625, 0, 0.8125, 0.9375, 0.875, 0.9375)
		);
	}
}

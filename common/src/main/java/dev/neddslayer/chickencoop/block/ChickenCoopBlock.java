package dev.neddslayer.chickencoop.block;

import dev.neddslayer.chickencoop.ChickenCoop;
import dev.neddslayer.chickencoop.block.entity.ChickenCoopBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChickenCoopBlock extends BlockWithEntity {

	// store it here, we use this because we can't access the block entity once it's broken
	public int lastKnownChickens = 0;

	public ChickenCoopBlock(Settings settings) {
		super(settings);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
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

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, ChickenCoop.CHICKEN_COOP_BLOCK_ENTITY.get(), ChickenCoopBlockEntity::tick);
	}
	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		ItemStack handStack = player.getStackInHand(Hand.MAIN_HAND);
		NbtList enchantments = handStack.getEnchantments();
		if (!world.isClient && !enchantments.contains(EnchantmentHelper.createNbt(new Identifier("silk_touch"), 1))) {
			for (int i = 0; i < lastKnownChickens; i++) {
				ChickenEntity chicken = new ChickenEntity(EntityType.CHICKEN, world);
				Vec3d centerPos = pos.toCenterPos();
				chicken.setPos(centerPos.x - 0.5f + world.random.nextFloat(), centerPos.y - 0.5f + world.random.nextFloat(), centerPos.z - 0.5f + world.random.nextFloat());
				chicken.setVelocity(
						-0.5 + world.random.nextFloat(),
						-0.5 + world.random.nextFloat(),
						-0.5 + world.random.nextFloat()
				);
				world.spawnEntity(chicken);
			}
		}
		super.onBreak(world, pos, state, player);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ChickenCoopBlockEntity(pos, state);
	}
}

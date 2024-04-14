package dev.neddslayer.chickencoop.block.entity;

import dev.neddslayer.chickencoop.ChickenCoop;
import dev.neddslayer.chickencoop.block.ChickenCoopBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ChickenCoopBlockEntity extends BlockEntity {

	public int chickens = 0;
	public int eggLayTime = -1;

	public ChickenCoopBlockEntity(BlockPos pos, BlockState state) {
		super(ChickenCoop.CHICKEN_COOP_BLOCK_ENTITY.get(), pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, ChickenCoopBlockEntity blockEntity) {
		if (blockEntity.chickens > 0) {
			if (blockEntity.eggLayTime == -1)
				blockEntity.eggLayTime = world.random.nextInt(6000 / blockEntity.chickens) + (6000 / blockEntity.chickens);

			if (!world.isClient && --blockEntity.eggLayTime <= 0) {
				world.playSound(null, pos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 1.0f, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);
				dropItem(Items.EGG, world, pos);
				world.emitGameEvent(GameEvent.ENTITY_PLACE, pos, GameEvent.Emitter.of(state));
				blockEntity.eggLayTime = world.random.nextInt(6000 / blockEntity.chickens) + (6000 / blockEntity.chickens);
			}
		}
		((ChickenCoopBlock)state.getBlock()).lastKnownChickens = blockEntity.chickens;
	}

	public static void dropItem(Item item, World world, BlockPos pos) {
		ItemEntity itemEntity = new ItemEntity(world, pos.toCenterPos().getX(), pos.toCenterPos().getY() - 0.25f, pos.toCenterPos().getZ(), new ItemStack(item));
		itemEntity.setToDefaultPickupDelay();
		world.spawnEntity(itemEntity);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("Chickens", chickens);

		super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		chickens = nbt.getInt("Chickens");

		super.readNbt(nbt);
	}

}

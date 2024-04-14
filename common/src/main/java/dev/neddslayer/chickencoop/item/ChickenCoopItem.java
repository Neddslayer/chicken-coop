package dev.neddslayer.chickencoop.item;

import dev.neddslayer.chickencoop.ChickenCoop;
import dev.neddslayer.chickencoop.block.entity.ChickenCoopBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ChickenCoopItem extends BlockItem {
	public ChickenCoopItem(Block block, Settings settings) {
		super(block, settings);
	}

	@Override
	public ActionResult place(ItemPlacementContext context) {
		if (context.getHand() != Hand.MAIN_HAND) return ActionResult.FAIL;
		return super.place(context);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		ItemStack chickenCoopStack = user.getStackInHand(hand);
		int chickens = chickenCoopStack.getOrCreateNbt().getInt("Chickens");
		if (entity instanceof ChickenEntity chicken && chickens < 128) {
			chicken.discard();
			chickenCoopStack.getOrCreateNbt().putInt("Chickens", chickens + 1);
			user.playSound(SoundEvents.BLOCK_BARREL_OPEN, 1.0f, 2.0f);
		}
		return ActionResult.SUCCESS;
	}

	@Override
	protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
		int chickens = stack.getOrCreateNbt().getInt("Chickens");
		if (world.getBlockEntity(pos) != null)
			((ChickenCoopBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).chickens = chickens;
		return super.postPlacement(pos, world, player, stack, state);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		int chickens = stack.getOrCreateNbt().getInt("Chickens");
		tooltip.add(Text.literal("x" + chickens + " chickens").formatted(Formatting.GREEN));
	}
}

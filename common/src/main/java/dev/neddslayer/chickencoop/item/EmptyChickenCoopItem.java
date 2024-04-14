package dev.neddslayer.chickencoop.item;

import dev.neddslayer.chickencoop.ChickenCoop;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EmptyChickenCoopItem extends BlockItem {
	public EmptyChickenCoopItem(Block block, Settings settings) {
		super(block, settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		if (entity instanceof ChickenEntity chicken) {
			ItemStack oldStack = player.getStackInHand(hand);
			ItemStack newStack = new ItemStack(ChickenCoop.CHICKEN_COOP_ITEM.get());
			newStack.getOrCreateNbt().putInt("Chickens", 1);

			if (player.getInventory().getEmptySlot() == -1) {
				player.dropItem(newStack, true);
			} else {
				if (oldStack.getCount() == 1) player.setStackInHand(hand, newStack);
				else {
					oldStack.decrement(1);
					player.giveItemStack(newStack);
				}
			}

			player.playSound(SoundEvents.BLOCK_BARREL_OPEN, 1.0f, 2.0f);

			chicken.discard();
			return ActionResult.success(player.getWorld().isClient);
		}
		return super.useOnEntity(stack, player, entity, hand);
	}
}

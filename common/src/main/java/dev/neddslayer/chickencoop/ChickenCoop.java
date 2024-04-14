package dev.neddslayer.chickencoop;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.neddslayer.chickencoop.block.ChickenCoopBlock;
import dev.neddslayer.chickencoop.block.EmptyChickenCoopBlock;
import dev.neddslayer.chickencoop.block.entity.ChickenCoopBlockEntity;
import dev.neddslayer.chickencoop.item.ChickenCoopItem;
import dev.neddslayer.chickencoop.item.EmptyChickenCoopItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChickenCoop {

	public static final String MOD_ID = "chickencoop";
	private static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM_GROUP);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, RegistryKeys.BLOCK);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

	public static final RegistrySupplier<ItemGroup> CHICKEN_COOP_GROUP = TABS.register("chicken_coop", () ->
		CreativeTabRegistry.create(Text.translatable("itemGroup.chicken_coop"), () -> ChickenCoop.CHICKEN_COOP_ITEM.get().getDefaultStack())
	);

	public static final RegistrySupplier<Block> EMPTY_CHICKEN_COOP_BLOCK = BLOCKS.register("empty_chicken_coop", () ->
			new EmptyChickenCoopBlock(AbstractBlock.Settings.create().burnable().strength(1.0f).sounds(BlockSoundGroup.WOOD)));
	public static final RegistrySupplier<Block> CHICKEN_COOP_BLOCK = BLOCKS.register("chicken_coop", () ->
			new ChickenCoopBlock(AbstractBlock.Settings.create().burnable().strength(1.0f).sounds(BlockSoundGroup.WOOD)));
	public static final RegistrySupplier<BlockEntityType<ChickenCoopBlockEntity>> CHICKEN_COOP_BLOCK_ENTITY = BLOCK_ENTITIES.register("chicken_coop", () ->
			BlockEntityType.Builder.create(ChickenCoopBlockEntity::new, CHICKEN_COOP_BLOCK.get()).build(null));

	public static final RegistrySupplier<Item> EMPTY_CHICKEN_COOP = ITEMS.register("empty_chicken_coop", () ->
			new EmptyChickenCoopItem(EMPTY_CHICKEN_COOP_BLOCK.get(), new Item.Settings().maxCount(16).arch$tab(CHICKEN_COOP_GROUP)));
	public static final RegistrySupplier<Item> CHICKEN_COOP_ITEM = ITEMS.register("chicken_coop", () ->
			new ChickenCoopItem(CHICKEN_COOP_BLOCK.get(), new Item.Settings().maxCount(1)));

	public static void register() {
		BLOCKS.register();
		BLOCK_ENTITIES.register();
		ITEMS.register();
		TABS.register();
	}

}

package net.landofrails.stellwand.content.entities.function;

import java.util.Map;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.Player.Hand;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.util.Facing;
import net.landofrails.stellwand.content.entities.storage.BlockSignalStorageEntity;
import net.landofrails.stellwand.content.items.CustomItems;
import net.landofrails.stellwand.content.network.ChangeSignalMode;
import net.landofrails.stellwand.storage.RunTimeStorage;
import net.landofrails.stellwand.utils.compact.LoSPlayer;

public abstract class BlockSignalFunctionEntity extends BlockEntity {

	private BlockSignalStorageEntity entity;

	@SuppressWarnings("java:S112")
	public BlockSignalFunctionEntity() {
		if (this instanceof BlockSignalStorageEntity)
			entity = (BlockSignalStorageEntity) this;
		else
			throw new RuntimeException(
					"This should be a subclass of BlockSignalStorageEntity!");
	}

	@Override
	public ItemStack onPick() {
		ItemStack is = new ItemStack(CustomItems.ITEMBLOCKSIGNAL, 1);
		TagCompound tag = is.getTagCompound();
		tag.setString("itemId", entity.getContentBlockId());
		is.setTagCompound(tag);
		return is;
	}

	@Override
	public boolean onClick(Player player, Hand hand, Facing facing, Vec3d hit) {
		ItemStack item = player.getHeldItem(hand);
		if (isAir(item)) {
			if (player.getWorld().isServer) {
				LoSPlayer p = new LoSPlayer(player);
				p.direct("Signal: " + entity.getPos().toString());
				String nextMode = entity.renderEntity.nextMode();
				entity.renderEntity.setMode(nextMode);
				ChangeSignalMode packet = new ChangeSignalMode(getPos(), nextMode);
				packet.sendToAll();
				p.direct("New mode: " + nextMode);
			}
			return true;
		}
		return false;
	}

	@Override
	public void onBreak() {
		RunTimeStorage.removeSignal(entity.getPos());
	}

	public void update() {
		Map<String, String> blockModes = entity.renderEntity.getModes();
		Map<Vec3i, String> senderModes = entity.modes;

		String actualMode = entity.renderEntity.getMode();
		for (String mode : blockModes.values()) {
			if (senderModes.containsValue(mode))
				actualMode = mode;
		}

		entity.renderEntity.setMode(actualMode);
	}

	private boolean isAir(ItemStack item) {
		return item.is(ItemStack.EMPTY) || item.equals(ItemStack.EMPTY);
	}

}

package net.landofrails.catenary.content.items.connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cam72cam.mod.block.BlockTypeEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.Player.Hand;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.model.obj.OBJModel;
import cam72cam.mod.render.ItemRender;
import cam72cam.mod.render.OpenGL;
import cam72cam.mod.render.StandardModel;
import cam72cam.mod.render.obj.OBJRender;
import cam72cam.mod.resource.Identifier;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.text.PlayerMessage;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import net.landofrails.catenary.Catenary;
import net.landofrails.catenary.content.blocks.CustomBlocks;
import net.landofrails.catenary.content.entities.connector.BlockEntityTestConnector;
import net.landofrails.catenary.content.tabs.CustomTabs;

public class ItemTestConnector extends CustomItem {

	private static OBJModel model;
	private static OBJRender renderer;

	public ItemTestConnector() {
		super(Catenary.DOMAIN, Catenary.DOMAIN + ".itemtestconnector");
	}

	@Override
	public List<CreativeTab> getCreativeTabs() {
		return Collections.singletonList(CustomTabs.CATENARY_TAB);
	}
	
	@Override
	public List<String> getTooltip(ItemStack itemStack) {
		List<String> list = new ArrayList<>();
		TagCompound tag = itemStack.getTagCompound();
		if (!tag.isEmpty() && tag.hasKey("connectorPos")) {
			Vec3i pos = tag.getVec3i("connectorPos");
			list.add("Connector: " + pos.toString());
		} else {
			list.add("No connection");
		}
		return list;
	}

	@Override
	public ClickResult onClickBlock(Player player, World world, Vec3i pos, Hand hand, Facing facing, Vec3d inBlockPos) {

		if (world.isBlock(pos, CustomBlocks.BLOCKTESTCONNECTOR)) {
			connect(player, hand, world, pos);
			return ClickResult.ACCEPTED;
		}

		Vec3i target = world.isReplaceable(pos) ? pos : pos.offset(facing);

		if (isStandingInBlock(player.getBlockPosition().subtract(target)))
			return ClickResult.REJECTED;

		if (world.isAir(target) || world.isReplaceable(target)) {

			BlockTypeEntity block = CustomBlocks.BLOCKTESTCONNECTOR;

			world.setBlock(target, block);
			if (!player.isCreative()) {
				ItemStack is = player.getHeldItem(hand);
				is.shrink(1);
				player.setHeldItem(hand, is);
			}
			//

			return ClickResult.ACCEPTED;
		}

		return ClickResult.REJECTED;
	}

	private void connect(Player player, Hand hand, World world, Vec3i pos2) {
		ItemStack heldItem = player.getHeldItem(hand);
		TagCompound tag = heldItem.getTagCompound();
		if (!tag.isEmpty() && tag.hasKey("connectorPos")) {
			Vec3i pos1 = tag.getVec3i("connectorPos");
			BlockEntityTestConnector connector1 = world.getBlockEntity(pos1, BlockEntityTestConnector.class);
			BlockEntityTestConnector connector2 = world.getBlockEntity(pos2, BlockEntityTestConnector.class);
			if (connector1 != null && connector2 != null) {
				connector1.connect(pos2);
				player.sendMessage(PlayerMessage.direct("Connected!"));
			} else {
				player.sendMessage(PlayerMessage.direct("Couldn't find selected connectors"));
			}
			tag.remove("connectorPos");
			heldItem.setTagCompound(tag);
			player.setHeldItem(hand, heldItem);
		} else {
			player.sendMessage(PlayerMessage.direct("Selected!"));
			tag.setVec3i("connectorPos", pos2);
			heldItem.setTagCompound(tag);
			player.setHeldItem(hand, heldItem);
		}
	}

	private boolean isStandingInBlock(Vec3i vec3i) {
		return vec3i.x == 0 && vec3i.z == 0 && (vec3i.y == 0 || vec3i.y == -1);
	}

	private static void init() {
		if (renderer == null) {
			Identifier id = new Identifier(Catenary.DOMAIN, "models/block/testconnector/testconnector.obj");
			if(!id.canLoad())
				throw new RuntimeException("Cant find " + id.getPath());

			try {
				model = new OBJModel(id, 0);
				renderer = new OBJRender(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Rendering
	@SuppressWarnings({"java:S3776", "java:S112"})
	public static ItemRender.IItemModel getModelFor() {

		return (world, stack) -> new StandardModel().addCustom(() -> {

			ItemTestConnector.init();
			try (OpenGL.With ignored = OpenGL.matrix(); OpenGL.With ignored1 = renderer.bindTexture()) {
				GL11.glTranslated(0.5, 0.5, 0.5);
				renderer.draw();
			}

		});
	}

}

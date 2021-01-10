package net.landofrails.landofsignals;

import cam72cam.mod.ModCore;
import cam72cam.mod.ModEvent;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.net.Packet;
import cam72cam.mod.net.PacketDirection;
import cam72cam.mod.render.BlockRender;
import cam72cam.mod.render.ItemRender;
import cam72cam.mod.resource.Identifier;
import net.landofrails.landofsignals.packet.SignalBoxGuiPacket;
import net.landofrails.landofsignals.render.block.TileGroundRender;
import net.landofrails.landofsignals.render.block.TileMidRender;
import net.landofrails.landofsignals.render.block.TileSignalBoxRender;
import net.landofrails.landofsignals.render.block.TileSignalLeverRender;
import net.landofrails.landofsignals.render.block.TileSignalSO12Render;
import net.landofrails.landofsignals.render.block.TileTicketMachineDBRender;
import net.landofrails.landofsignals.render.block.TileTicketMachineSBBRender;
import net.landofrails.landofsignals.render.block.TileTopRender;
import net.landofrails.landofsignals.render.item.ObjItemRender;
import net.landofrails.landofsignals.tile.TileGround;
import net.landofrails.landofsignals.tile.TileMid;
import net.landofrails.landofsignals.tile.TileSignalBox;
import net.landofrails.landofsignals.tile.TileSignalLever;
import net.landofrails.landofsignals.tile.TileSignalSO12;
import net.landofrails.landofsignals.tile.TileTicketMachineDB;
import net.landofrails.landofsignals.tile.TileTicketMachineSBB;
import net.landofrails.landofsignals.tile.TileTop;
import net.landofrails.landofsignals.utils.Static;
import net.landofrails.stellwand.Stellwand;

@SuppressWarnings("java:S112")
//1.7.10
//@cpw.mods.fml.common.Mod(modid = LandOfSignals.MODID, name = "LandOfSignals", version = "0.0.2", dependencies = "required-after:universalmodcore@[1.0,1.1)", acceptedMinecraftVersions = "[1.7.10,1.10)")

//1.12.2
@net.minecraftforge.fml.common.Mod(modid = LandOfSignals.MODID, name = "LandOfSignals", version = LandOfSignals.VERSION, dependencies = "required-after:universalmodcore@[1.0,1.1)", acceptedMinecraftVersions = "[1.12,1.13)")
public class LandOfSignals extends ModCore.Mod {
	@SuppressWarnings("java:S1845")
	public static final String MODID = "landofsignals";
	public static final String VERSION = "0.0.2";

	static {
		try {
			ModCore.register(new LandOfSignals());
		} catch (Exception e) {
			throw new RuntimeException("Could not load mod " + MODID, e);
		}
	}

	@Override
	public String modID() {
		return MODID;
	}

	@Override
	public void commonEvent(ModEvent event) {

		if (event == ModEvent.CONSTRUCT) {
			ModCore.Mod.info("Thanks for using LandOfSignals. Starting common construct now...");

			// Stellwand
			Stellwand.commonEvent();

			LOSBlocks.register();
			LOSItems.register();
			Packet.register(SignalBoxGuiPacket::new, PacketDirection.ClientToServer);
		}

	}

	@Override
	public void clientEvent(ModEvent event) {
		switch (event) {
		case CONSTRUCT:
			ModCore.Mod.info("Starting client construct...");

			// Stellwand
			Stellwand.clientEvent();

			// Block
			BlockRender.register(LOSBlocks.BLOCK_SIGNAL_SO_12, TileSignalSO12Render::render, TileSignalSO12.class);
			BlockRender.register(LOSBlocks.BLOCK_SIGNAL_LEVER, TileSignalLeverRender::render, TileSignalLever.class);
			BlockRender.register(LOSBlocks.BLOCK_TICKET_MACHINE_DB, TileTicketMachineDBRender::render,
					TileTicketMachineDB.class);
			BlockRender.register(LOSBlocks.BLOCK_TICKET_MACHINE_SBB, TileTicketMachineSBBRender::render,
					TileTicketMachineSBB.class);
			BlockRender.register(LOSBlocks.BLOCK_SIGNAL_BOX, TileSignalBoxRender::render, TileSignalBox.class);

			BlockRender.register(LOSBlocks.BLOCK_GROUND_VORSIGNAL, TileGroundRender::render, TileGround.class);
			BlockRender.register(LOSBlocks.BLOCK_GROUND_HAUPTSIGNAL, TileGroundRender::render, TileGround.class);
			BlockRender.register(LOSBlocks.BLOCK_GROUND_GAMERTV, TileGroundRender::render, TileGround.class);

			BlockRender.register(LOSBlocks.BLOCK_MID_VORSIGNAL_MAST, TileMidRender::render, TileMid.class);
			BlockRender.register(LOSBlocks.BLOCK_MID_HAUPTSIGNAL_SCHILD, TileMidRender::render, TileMid.class);
			BlockRender.register(LOSBlocks.BLOCK_MID_GAMERTV, TileMidRender::render, TileMid.class);

			BlockRender.register(LOSBlocks.BLOCK_TOP_VORSIGNAL_KOPF, TileTopRender::render, TileTop.class);
			BlockRender.register(LOSBlocks.BLOCK_TOP_HAUPTSIGNAL_KOPF, TileTopRender::render, TileTop.class);
			BlockRender.register(LOSBlocks.BLOCK_TOP_GAMERTV_VORSIGNAL, TileTopRender::render, TileTop.class);
			BlockRender.register(LOSBlocks.BLOCK_TOP_GAMERTV_HVHP, TileTopRender::render, TileTop.class);
			BlockRender.register(LOSBlocks.BLOCK_TOP_GAMERTV_HVERSATZ, TileTopRender::render, TileTop.class);

			// Items
			ItemRender.register(LOSItems.ITEM_SIGNALSO12,
					ObjItemRender.getModelFor(
							new Identifier(LandOfSignals.MODID, "models/block/landofsignals/so12/signalso12.obj"),
							new Vec3d(0.5, 0, 0.5), 2));
			ItemRender.register(LOSItems.ITEM_SIGNAL_LEVER,
					ObjItemRender.getModelFor(
							new Identifier(LandOfSignals.MODID,
									"models/block/landofsignals/signalslever/signalslever.obj"),
							new Vec3d(0.5, 0.6, 0.5), 1));
			ItemRender.register(LOSItems.ITEM_TICKET_MACHINE_DB,
					ObjItemRender.getModelFor(
							new Identifier(LandOfSignals.MODID,
									"models/block/landofsignals/fahrkartenautomat_db/fahrkartenautomat_db.obj"),
							new Vec3d(0.5, 0, 0.5), 0.5f));
			ItemRender.register(LOSItems.ITEM_TICKET_MACHINE_SBB,
					ObjItemRender.getModelFor(
							new Identifier(LandOfSignals.MODID,
									"models/block/landofsignals/fahrkartenautomat_sbb/ticketautomat.obj"),
							new Vec3d(0.5, 0, 0.5), 0.3f));
			ItemRender.register(LOSItems.ITEM_SIGNAL_BOX,
					ObjItemRender.getModelFor(
							new Identifier(LandOfSignals.MODID, "models/block/landofsignals/signalbox/untitled.obj"),
							new Vec3d(0.5, 0, 0.5), 0.25f));
			ItemRender.register(LOSItems.ITEM_CONNECTOR, new Identifier(LandOfSignals.MODID, "items/itemconnector1"));

			ItemRender.register(LOSItems.ITEM_GROUND_VORSIGNAL, ObjItemRender.getModelFor(
					Static.listGroundModels.get("BLOCK_GROUND_VORSIGNAL")._1(), new Vec3d(0.5, -0.9, 0.5), 0.63f));
			ItemRender.register(LOSItems.ITEM_GROUND_HAUPTSIGNAL, ObjItemRender.getModelFor(
					Static.listGroundModels.get("BLOCK_GROUND_HAUPTSIGNAL")._1(), new Vec3d(0.5, -0.9, 0.5), 0.63f));
			ItemRender.register(LOSItems.ITEM_GROUND_GAMERTV, ObjItemRender
					.getModelFor(Static.listGroundModels.get("BLOCK_GROUND_GAMERTV")._1(), new Vec3d(0.5, 0, 0.5), 1f));

			ItemRender.register(LOSItems.ITEM_MID_VORSIGNAL_MAST, ObjItemRender.getModelFor(
					Static.listMidModels.get("BLOCK_MID_VORSIGNAL_MAST")._1(), new Vec3d(0.5, -0.9, 0.5), 0.63f));
			ItemRender.register(LOSItems.ITEM_MID_HAUPTSIGNAL_SCHILD, ObjItemRender.getModelFor(
					Static.listMidModels.get("BLOCK_MID_HAUPTSIGNAL_SCHILD")._1(), new Vec3d(0.5, -0.9, 0.5), 0.63f));
			ItemRender.register(LOSItems.ITEM_MID_GAMERTV, ObjItemRender
					.getModelFor(Static.listMidModels.get("BLOCK_MID_GAMERTV")._1(), new Vec3d(0.5, 0, 0.5), 1f));

			ItemRender.register(LOSItems.ITEM_TOP_VORSIGNAL_KOPF,
					ObjItemRender.getModelFor(Static.listTopModels.get("BLOCK_TOP_VORSIGNAL_KOPF")._1(),
							new Vec3d(0.5, -0.9, 0.5), Vec3d.ZERO,
							Static.listTopModels.get("BLOCK_TOP_VORSIGNAL_KOPF")._4(), 0.63f));
			ItemRender.register(LOSItems.ITEM_TOP_HAUPTSIGNAL_KOPF,
					ObjItemRender.getModelFor(Static.listTopModels.get("BLOCK_TOP_HAUPTSIGNAL_KOPF")._1(),
							new Vec3d(0.5, -0.9, 0.5), Vec3d.ZERO,
							Static.listTopModels.get("BLOCK_TOP_HAUPTSIGNAL_KOPF")._4(), 0.63f));
			ItemRender.register(LOSItems.ITEM_TOP_GAMERTV_VORSIGNAL,
					ObjItemRender.getModelFor(Static.listTopModels.get("BLOCK_TOP_GAMERTV_VORSIGNAL")._1(),
							new Vec3d(0.5, 0, 0.5), Vec3d.ZERO,
							Static.listTopModels.get("BLOCK_TOP_GAMERTV_VORSIGNAL")._4(), 1f));
			ItemRender.register(LOSItems.ITEM_TOP_GAMERTV_HVHP,
					ObjItemRender.getModelFor(Static.listTopModels.get("BLOCK_TOP_GAMERTV_HVHP")._1(),
							new Vec3d(0.5, 0, 0.5), Vec3d.ZERO, Static.listTopModels.get("BLOCK_TOP_GAMERTV_HVHP")._4(),
							1f));
			ItemRender.register(LOSItems.ITEM_TOP_GAMERTV_HVERSATZ,
					ObjItemRender.getModelFor(Static.listTopModels.get("BLOCK_TOP_GAMERTV_HVERSATZ")._1(),
							new Vec3d(0.5, 0, 0.5), Vec3d.ZERO,
							Static.listTopModels.get("BLOCK_TOP_GAMERTV_HVERSATZ")._4(), 1f));
			break;
		case INITIALIZE:
		case SETUP:
		case RELOAD:
		case START:
		case FINALIZE:
			break;
		}
	}

	@Override
	public void serverEvent(ModEvent event) {
		// Do nothing for now
	}
}

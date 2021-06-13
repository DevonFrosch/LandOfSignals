package net.landofrails.catenary.content.blocks;

import cam72cam.mod.render.BlockRender;
import net.landofrails.catenary.content.blocks.connector.BlockTestConnector;
import net.landofrails.catenary.content.entities.connector.BlockEntityTestConnector;

public class CustomBlocks {

	public static final BlockTestConnector BLOCKTESTCONNECTOR = new BlockTestConnector();

	public static void register() {
		BlockRender.register(BLOCKTESTCONNECTOR, BlockEntityTestConnector::render, BlockEntityTestConnector.class);
	}

	public static void init() {
		// Loading
	}

}

package net.landofrails.catenary.content.items;

import cam72cam.mod.render.ItemRender;
import net.landofrails.catenary.content.items.connector.ItemTestConnector;

public class CustomItems {

	public static final ItemTestConnector ITEMTESTCONNECTOR = new ItemTestConnector();

	public static void register() {
		ItemRender.register(ITEMTESTCONNECTOR, ItemTestConnector.getModelFor());
	}

}

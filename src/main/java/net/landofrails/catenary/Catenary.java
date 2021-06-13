package net.landofrails.catenary;

import cam72cam.mod.ModCore;
import cam72cam.mod.ModEvent;
import net.landofrails.catenary.content.blocks.CustomBlocks;
import net.landofrails.catenary.content.items.CustomItems;
import net.landofrails.catenary.content.tabs.CustomTabs;

public class Catenary {
	public static final String DOMAIN = "catenary";

	public static final String ADDON_VERSION = "1";

	private Catenary() {

	}

	// Gets called before clientEvent and serverEvent
	public static void commonEvent(ModEvent event) {

		switch (event) {
			case CONSTRUCT :

				CustomTabs.register();
				CustomItems.register();

				// CustomGuis.register();
				// CustomTabs.register();

				// CustomRecipes.register();
				CustomBlocks.init();
				// CustomPackets.register();

				break;
			case INITIALIZE :
			case SETUP :
			case RELOAD :
			case START :
			case FINALIZE :
				break;
		}

	}

	public static void clientEvent(ModEvent event) {

		switch (event) {
			case CONSTRUCT :
				CustomBlocks.register();
				break;
			case INITIALIZE :
			case SETUP :
			case RELOAD :
			case START :
			case FINALIZE :
				break;
		}

	}

	public static void serverEvent(ModEvent event) {

		switch (event) {
			case CONSTRUCT :
			case INITIALIZE :
			case SETUP :
			case RELOAD :
			case START :
			case FINALIZE :
				break;
		}

	}

	public static void info(String msg, Object... params) {
		ModCore.info(msg, params);
	}

	public static void warn(String msg, Object... params) {
		ModCore.warn(msg, params);
	}

	public static void error(String msg, Object... params) {
		ModCore.error(msg, params);
	}

	public static void debug(String msg, Object... params) {
		ModCore.debug(msg, params);
	}
}

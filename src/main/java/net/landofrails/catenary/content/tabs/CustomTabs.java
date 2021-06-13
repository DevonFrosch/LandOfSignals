package net.landofrails.catenary.content.tabs;

import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.Fuzzy;
import net.landofrails.catenary.Catenary;
import net.landofrails.landofsignals.LandOfSignals;

public class CustomTabs {

	private CustomTabs() {

	}

	@SuppressWarnings("java:S3008")
	public static CreativeTab CATENARY_TAB;
	@SuppressWarnings("java:S3008")
	public static CreativeTab HIDDEN_TAB;

	public static void register() {
		CATENARY_TAB = new CreativeTab(LandOfSignals.MODID + "." + Catenary.DOMAIN, Fuzzy.EMERALD::example);
	}

}

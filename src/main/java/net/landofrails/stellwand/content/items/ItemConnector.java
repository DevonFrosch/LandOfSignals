package net.landofrails.stellwand.content.items;

import java.util.Arrays;
import java.util.List;

import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import net.landofrails.landofsignals.LandOfSignals;
import net.landofrails.stellwand.content.tabs.CustomTabs;
import net.landofrails.stellwand.utils.ICustomTexturePath;

public class ItemConnector extends CustomItem implements ICustomTexturePath {

	private int variation = -1;

	public ItemConnector() {
		this(1);
	}

	public ItemConnector(int variation) {
		super(LandOfSignals.MODID, "stellwand.itemConnector" + variation);
		this.variation = variation;
	}

	@Override
	public List<CreativeTab> getCreativeTabs() {
		return Arrays.asList(CustomTabs.STELLWAND_TAB);
	}

	@Override
	public String getTexturePath() {
		return "items/stellwand/connector/itemconnector" + variation;
	}

}
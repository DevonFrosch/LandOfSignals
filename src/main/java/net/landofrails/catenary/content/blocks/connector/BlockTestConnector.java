package net.landofrails.catenary.content.blocks.connector;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.block.BlockTypeEntity;
import cam72cam.mod.block.Material;
import net.landofrails.catenary.Catenary;
import net.landofrails.catenary.content.entities.connector.BlockEntityTestConnector;

public class BlockTestConnector extends BlockTypeEntity {

	public BlockTestConnector() {
		super(Catenary.DOMAIN, Catenary.DOMAIN + ".blocktestconnector");
	}

	@Override
	protected BlockEntity constructBlockEntity() {
		return new BlockEntityTestConnector();
	}

	@Override
	public boolean isConnectable() {
		return true;
	}

	@Override
	public Material getMaterial() {
		return Material.METAL;
	}

}
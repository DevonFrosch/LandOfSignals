package net.landofrails.catenary.utils.interfaces;

import cam72cam.mod.math.Vec3d;

public interface ICatenaryConnector {

	public Vec3d getConnectionPosition();

	public Vec3d getConnectionOffset();

	public Vec3d getModelTranslation();

}

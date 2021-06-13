package net.landofrails.catenary.utils.mapper;

import java.util.Map;

import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.serialization.SerializationException;
import cam72cam.mod.serialization.TagCompound;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.serialization.TagMapper;

public class MapVec3dMapper implements TagMapper<Map<Vec3i, Vec3d>> {

	private static final String KEYNAME = "entry";

	@SuppressWarnings("java:S1612")
	@Override
	public TagAccessor<Map<Vec3i, Vec3d>> apply(Class<Map<Vec3i, Vec3d>> type, String fieldName, TagField tag)
			throws SerializationException {
		// @formatter:off
		return new TagAccessor<>((nbt, map) -> {
			// From List to Tag
			if (map != null)
				nbt.setMap(fieldName, map, Vec3i::toString, vecD -> new TagCompound().setVec3d(KEYNAME, vecD));
		},
			// From Tag to List (Method reference for keys not working)
			nbt -> nbt.getMap(fieldName, k -> getVecI(k), v -> v.getVec3d(KEYNAME))
		);
		// @formatter:on
	}

	public Vec3i getVecI(String text) {
		if (text != null) {
			text = text.substring(1, text.length() - 1);
			String[] xyz = text.split(",");
			int x = Integer.parseInt(xyz[0].trim());
			int y = Integer.parseInt(xyz[1].trim());
			int z = Integer.parseInt(xyz[2].trim());
			return new Vec3i(x, y, z);
		}
		return null;
	}

}

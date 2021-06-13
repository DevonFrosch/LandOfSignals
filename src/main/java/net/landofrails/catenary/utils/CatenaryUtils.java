package net.landofrails.catenary.utils;

import cam72cam.mod.math.Vec3d;

public class CatenaryUtils {

	public static void main(String[] args) {
		Vec3d a = new Vec3d(0, 0, 0);
		Vec3d b = new Vec3d(2, 2, 2);
		Vec3d pos = getPositionBetweenVectors(a, b, 50);
		System.out.println(pos);
	}

	public static Vec3d getPositionBetweenVectors(Vec3d start, Vec3d end, double percentage) {
		Vec3d max = start.max(end);
		Vec3d min = start.min(end);
		Vec3d distance = max.subtract(min);

		final double x = (distance.x / 100) * percentage;
		final double y = (distance.y / 100) * percentage;
		final double z = (distance.z / 100) * percentage;

		Vec3d percDistance = new Vec3d(x, y, z);

		return min.add(percDistance);
	}

}
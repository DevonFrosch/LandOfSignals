package net.landofrails.catenary.content.entities.connector;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Vector3d;

import org.lwjgl.opengl.GL11;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.Player.Hand;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.model.obj.OBJModel;
import cam72cam.mod.render.OpenGL;
import cam72cam.mod.render.StandardModel;
import cam72cam.mod.render.obj.OBJRender;
import cam72cam.mod.resource.Identifier;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.text.PlayerMessage;
import cam72cam.mod.util.Facing;
import net.landofrails.catenary.Catenary;
import net.landofrails.catenary.content.items.CustomItems;
import net.landofrails.catenary.utils.interfaces.ICatenaryConnector;
import net.landofrails.catenary.utils.mapper.MapVec3dMapper;

public class BlockEntityTestConnector extends BlockEntity implements ICatenaryConnector {

	@TagField(mapper = MapVec3dMapper.class)
	private Map<Vec3i, Vec3d> connections = new HashMap<>();

	private static OBJRender renderer;
	private static OBJModel model;

	private static OBJModel connectionModel;
	private static OBJRender connectionRenderer;

	@Override
	public ItemStack onPick() {
		return new ItemStack(CustomItems.ITEMTESTCONNECTOR, 1);
	}

	@Override
	public Vec3d getConnectionPosition() {
		Vec3d iPos = new Vec3d(getPos());
		return iPos.add(getConnectionOffset());
	}

	@Override
	public Vec3d getConnectionOffset() {
		return new Vec3d(0.5, 0.5, 0.5);
	}

	@Override
	public Vec3d getModelTranslation() {
		return new Vec3d(0.5, 0.5, 0.5);
	}

	public static StandardModel render(BlockEntityTestConnector ts) {
		return new StandardModel().addCustom(() -> renderStuff(ts));
	}

	@SuppressWarnings("java:S1172")
	private static void renderStuff(BlockEntityTestConnector ts) {
		try {
			if (renderer == null || model == null) {
				model = new OBJModel(new Identifier(Catenary.DOMAIN, "models/block/testconnector/testconnector.obj"), 0);
				renderer = new OBJRender(model);

				connectionModel = new OBJModel(new Identifier(Catenary.DOMAIN, "models/block/testconnection/testconnection.obj"), 0);
				connectionRenderer = new OBJRender(connectionModel);
			}

			Vec3d translation = ts.getModelTranslation();

			try (OpenGL.With matrix = OpenGL.matrix(); OpenGL.With tex = renderer.bindTexture()) {
				GL11.glTranslated(translation.x, translation.y, translation.z);
				renderer.draw();

				GL11.glDisable(GL11.GL_TEXTURE_2D);

				GL11.glTranslated(-translation.x, -translation.y, -translation.z);
				GL11.glColor3f(1, 1, 1);
				GL11.glLineWidth(10f);

				Vec3d offset = ts.getConnectionOffset();

				for (Vec3d connection : ts.getConnections().values()) {
					GL11.glBegin(GL11.GL_LINE_STRIP);

					GL11.glVertex3d(offset.x, offset.y, offset.z);
					GL11.glVertex3d(connection.x, connection.y, connection.z);

					GL11.glEnd();

					// double partLength =
					// connectionModel.lengthOfGroups(connectionModel.groups());
					// double lengths = 1; // connection.length() / partLength;
					//
					// for (double i = 0; i < lengths; i++) {
					// Vec3d rot = getRotationBetween(connection);
					// GL11.glRotated(rot.x, 1, 0, 0);
					// GL11.glRotated(rot.y, 0, 1, 0);
					// GL11.glRotated(rot.z, 0, 0, 1);
					//
					// connectionRenderer.draw();
					// }

				}

				GL11.glEnable(GL11.GL_TEXTURE_2D);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Vec3d getRotationBetween(Vec3d target) {
		return getRotationBetween(new Vec3d(0, 0, 0), target);
	}

	public static Vec3d getRotationBetween(Vec3d start, Vec3d target) {
		if (start != null && target != null) {
			double angleX = new Vector3d(start.x, 0, 0).angle(new Vector3d(target.x, 0, 0));
			double angleY = new Vector3d(0, start.y, 0).angle(new Vector3d(0, target.y, 0));
			double angleZ = new Vector3d(0, 0, start.z).angle(new Vector3d(0, 0, target.z));
			return new Vec3d(angleX, angleY, angleZ);
		}
		return null;
	}

	@Override
	public boolean onClick(Player player, Hand hand, Facing facing, Vec3d hit) {
		if (player.getHeldItem(hand).isEmpty()) {
			player.sendMessage(PlayerMessage.direct("Connetions:"));
			connections.keySet().forEach(v -> player.sendMessage(PlayerMessage.direct(v.toString())));
			return true;
		}
		return super.onClick(player, hand, facing, hit);
	}

	public Map<Vec3i, Vec3d> getConnections() {
		return connections;
	}

	public void connect(Vec3i connectorPos) {

		if (!getPos().equals(connectorPos)) {
			
			// Check if distance is smaller than 24
			if (new Vec3d(getPos()).distanceTo(new Vec3d(connectorPos)) > 24) {
				return;
			}

			BlockEntityTestConnector connectorOther = getWorld().getBlockEntity(connectorPos, BlockEntityTestConnector.class);
			
			if (connectorOther != null) {
				Vec3d connectionPos = connectorOther.getConnectionPosition();
				if (connections.containsKey(connectorPos)) {
					connections.remove(connectorPos);
				} else {

					Vec3d offsetPosition = connectionPos.subtract(this.getConnectionPosition()).add(this.getModelTranslation());
					connections.put(connectorPos, offsetPosition);
				}
			}

		}

	}

}

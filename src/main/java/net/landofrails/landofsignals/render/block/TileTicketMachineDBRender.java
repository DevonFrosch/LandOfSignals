package net.landofrails.landofsignals.render.block;

import cam72cam.mod.model.obj.OBJModel;
import cam72cam.mod.render.OpenGL;
import cam72cam.mod.render.StandardModel;
import cam72cam.mod.render.obj.OBJRender;
import cam72cam.mod.resource.Identifier;
import net.landofrails.landofsignals.LandOfSignals;
import net.landofrails.landofsignals.tile.TileTicketMachineDB;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class TileTicketMachineDBRender {

    private static OBJRender renderer;
    private static OBJModel model;
    private static final List<String> groupNames = Arrays.asList(new String[]{"Polygon-Objekt"});

    public static StandardModel render(TileTicketMachineDB ts) {
        return new StandardModel().addCustom((partialTicks) -> renderStuff(ts, partialTicks));
    }

    private static void renderStuff(TileTicketMachineDB ts, float partialTicks) {
        if (renderer == null || model == null) {
            try {
                model = new OBJModel(new Identifier(LandOfSignals.MODID, "models/block/fahrkartenautomat_db/fahrkartenautomat_db.obj"), 0);
                renderer = new OBJRender(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try (OpenGL.With matrix = OpenGL.matrix(); OpenGL.With tex = renderer.bindTexture()) {
            GL11.glTranslated(0.5, 0, 0.5);
            GL11.glRotated(ts.getBlockRotate(), 0, 1, 0);
            renderer.draw();
        }
        ts.setFullHeight(model.heightOfGroups(groupNames));
        ts.setFullWidth(model.widthOfGroups(groupNames));
        ts.setFullLength(model.lengthOfGroups(groupNames));
    }
}
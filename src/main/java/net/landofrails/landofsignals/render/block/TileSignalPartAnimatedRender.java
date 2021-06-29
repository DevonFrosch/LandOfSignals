package net.landofrails.landofsignals.render.block;

import cam72cam.mod.math.Vec3d;
import cam72cam.mod.render.StandardModel;
import cam72cam.mod.resource.Identifier;
import friedrichlp.renderlib.library.RenderMode;
import friedrichlp.renderlib.model.ModelLoaderProperty;
import friedrichlp.renderlib.render.ViewBoxes;
import friedrichlp.renderlib.tracking.*;
import net.landofrails.landofsignals.LOSBlocks;
import net.landofrails.landofsignals.LandOfSignals;
import net.landofrails.landofsignals.tile.TileSignalPartAnimated;
import net.landofrails.landofsignals.utils.IdentifierFileContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileSignalPartAnimatedRender {

    private TileSignalPartAnimatedRender() {

    }

    private static final Map<String, RenderObject> cache = new HashMap<>();
    private static final List<String> groupNames = Arrays.asList("wing");

    public static StandardModel render(TileSignalPartAnimated tsp) {
        return new StandardModel().addCustom(partialTicks -> renderStuff(tsp, partialTicks));
    }

    private static void renderStuff(TileSignalPartAnimated tsp, float partialTicks) {
        String id = tsp.getId();
        if (!cache.containsKey(id)) {
            try {
                Identifier identifier = new Identifier(LandOfSignals.MODID, LOSBlocks.BLOCK_SIGNAL_PART_ANIMATED.getPath(id));
                IdentifierFileContainer identifierContainer = new IdentifierFileContainer(identifier);
                ModelInfo model = ModelManager.registerModel(identifierContainer, new ModelLoaderProperty(0.0f));
                RenderLayer layer = RenderManager.addRenderLayer(ViewBoxes.ALWAYS);
                RenderObject object = layer.addRenderObject(model);
                cache.put(id, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RenderObject object = cache.get(id);
        Vec3d scale = LOSBlocks.BLOCK_SIGNAL_PART.getScaling(id);
        object.transform.scale((float) scale.x, (float) scale.y, (float) scale.z);
        Vec3d trans = LOSBlocks.BLOCK_SIGNAL_PART.getTranslation(id).add(tsp.getOffset());
        object.transform.translate((float) trans.x, (float) trans.y, (float) trans.z);
        object.transform.rotate(0, tsp.getBlockRotate(), 0);
        RenderManager.render(object.layer, RenderMode.USE_CUSTOM_MATS);

//        OBJRender flareRenderer = cache.get("flare").getRight();
//
//        GL11.glPushMatrix();
//        {
//            GL11.glPushAttrib(GL11.GL_ALPHA_TEST_FUNC);
//            GL11.glPushAttrib(GL11.GL_ALPHA_TEST_REF);
//            GL11.glPushAttrib(GL11.GL_LIGHTING);
//            GL11.glPushAttrib(GL11.GL_BLEND);
//
//            RenderUtil.lightmapPush();
//            {
//                RenderUtil.lightmapBright();
//
//                // Disable lighting & enable alpha blending.
//                GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
//                GL11.glDisable(GL11.GL_LIGHTING);
//                GL11.glEnable(GL11.GL_BLEND);
//
//                OpenGL.With color = OpenGL.color(0.5F, 1.0F, 0.0F, 0.0F);
//
//                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
//
//                flareRenderer.draw();
//            }
//            RenderUtil.lightmapPop();
//
//            GL11.glPopAttrib(); /* GL11.GL_BLEND */
//            GL11.glPopAttrib(); /* GL11.GL_LIGHTING */
//            GL11.glPopAttrib(); /* GL11.GL_ALPHA_TEST_REF */
//            GL11.glPopAttrib(); /* GL11.GL_ALPHA_TEST_FUNC */
//        }
//        GL11.glPopMatrix();
    }

//    private Vec3d angleToDirection(Vec3d angle) {
//// Convert angle to radians
//        angle.x = angle.x * 3.14159265 / 180;
//        angle.y = angle.y * 3.14159265 / 180;
//
//        double sinYaw = Math.sin(angle.y);
//        double cosYaw = Math.cos(angle.y);
//
//        double sinPitch = Math.sin(angle.x);
//        double cosPitch = Math.cos(angle.x);
//
//        return new Vec3d(cosPitch * cosYaw, cosPitch * sinYaw, -sinPitch);
//    }
}
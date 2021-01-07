package net.landofrails.landofsignals.tile;

import cam72cam.mod.block.BlockEntity;
import cam72cam.mod.entity.Player;
import cam72cam.mod.entity.boundingbox.IBoundingBox;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.serialization.TagField;
import cam72cam.mod.util.Facing;
import net.landofrails.landofsignals.LOSGuis;
import net.landofrails.landofsignals.LOSItems;

public class TileTicketMachineDB extends BlockEntity {
    private double fullHeight = 0;
    private double fullWidth = 0;
    private double fullLength = 0;

    @TagField("Rotation")
    private float blockRotate;

    public TileTicketMachineDB(float rot) {
        this.blockRotate = rot;
    }

    @Override
    public ItemStack onPick() {
        return new ItemStack(LOSItems.ITEM_TICKET_MACHINE_DB, 1);
    }

    @Override
    public IBoundingBox getBoundingBox() {
        return IBoundingBox.BLOCK.expand(new Vec3d(0, 1, 0));
//        return IBoundingBox.ORIGIN.expand(new Vec3d(fullWidth, fullHeight, fullLength)).offset(new Vec3d(0.5 - fullWidth / 2, 0, 0.5 - fullLength / 2));
    }

    @Override
    public boolean onClick(Player player, Player.Hand hand, Facing facing, Vec3d hit) {
        LOSGuis.TICKET_MACHINE_DB.open(player);
        return true;
    }

    public void setFullHeight(double fullHeight) {
        this.fullHeight = fullHeight;
    }

    public void setFullWidth(double fullWidth) {
        this.fullWidth = fullWidth;
    }

    public void setFullLength(double fullLength) {
        this.fullLength = fullLength;
    }

    public float getBlockRotate() {
        return blockRotate;
    }

    public void setBlockRotate(float blockRotate) {
        this.blockRotate = blockRotate;
    }
}
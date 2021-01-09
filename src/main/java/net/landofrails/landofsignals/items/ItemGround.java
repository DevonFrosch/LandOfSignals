package net.landofrails.landofsignals.items;

import cam72cam.mod.entity.Player;
import cam72cam.mod.item.ClickResult;
import cam72cam.mod.item.CreativeTab;
import cam72cam.mod.item.CustomItem;
import cam72cam.mod.math.Vec3d;
import cam72cam.mod.math.Vec3i;
import cam72cam.mod.util.Facing;
import cam72cam.mod.world.World;
import net.landofrails.landofsignals.LOSTabs;
import net.landofrails.landofsignals.blocks.BlockGround;
import net.landofrails.landofsignals.utils.Static;

import java.util.Collections;
import java.util.List;

public class ItemGround extends CustomItem {
    private final String blockName;

    public ItemGround(String modID, String name, String block) {
        super(modID, name);
        this.blockName = block;
    }

    @Override
    public List<CreativeTab> getCreativeTabs() {
        return Collections.singletonList(LOSTabs.SIGNALS_TAB);
    }

    @Override
    public ClickResult onClickBlock(Player player, World world, Vec3i pos, Player.Hand hand, Facing facing, Vec3d inBlockPos) {
        BlockGround block = Static.listGroundModels.get(blockName)._2();
        int rot = -(Math.round(player.getRotationYawHead() / 10) * 10) + 180;
        block.setBlock(blockName);
        block.setRot(rot);
        world.setBlock(pos.offset(facing), block);
        return ClickResult.ACCEPTED;
    }
}
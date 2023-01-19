/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.item.food;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockLiquid;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.item.food.FoodNormal;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Utils;

public class FoodChorusFruit
extends FoodNormal {
    public FoodChorusFruit() {
        super(4, 2.4f);
        this.addRelative(432);
    }

    @Override
    protected boolean onEatenBy(Player player) {
        super.onEatenBy(player);
        int minX = player.getFloorX() - 8;
        int minY = player.getFloorY() - 8;
        int minZ = player.getFloorZ() - 8;
        int maxX = minX + 16;
        int maxY = minY + 16;
        int maxZ = minZ + 16;

        Level level = player.getLevel();
        if (level == null) return false;

        for (int attempts = 0; attempts < 16; attempts++) {
            int x = Utils.rand(minX, maxX);
            int y = Utils.rand(minY, maxY);
            int z = Utils.rand(minZ, maxZ);

            if (y < 0) continue;

            while (y >= 0 && !level.getBlock(new Vector3(x, y + 1, z)).isSolid()) {
                y--;
            }

            y++;

            Block blockUp = level.getBlock(new Vector3(x, y + 1, z));
            Block blockUp2 = level.getBlock(new Vector3(x, y + 2, z));

            if (blockUp.isSolid() || blockUp instanceof BlockLiquid || blockUp2.isSolid() || blockUp2 instanceof BlockLiquid) {
                continue;
            }

            level.addLevelSoundEvent(player, 118);
            if (!player.teleport(new Vector3((double)x + 0.5, y + 1, (double)z + 0.5), PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)) break;
            level.addLevelSoundEvent(player, 118);
            break;
        }
        return true;
    }
}


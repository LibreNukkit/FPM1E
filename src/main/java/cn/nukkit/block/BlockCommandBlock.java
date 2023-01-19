/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.block;

import cn.nukkit.block.BlockSolid;
import cn.nukkit.item.Item;

public class BlockCommandBlock
extends BlockSolid {
    @Override
    public int getId() {
        return 137;
    }

    @Override
    public double getHardness() {
        return -1.0;
    }

    @Override
    public double getResistance() {
        return 1.8E7;
    }

    @Override
    public String getName() {
        return "Command Block";
    }

    @Override
    public boolean isBreakable(Item item) {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.block;

import cn.nukkit.block.BlockBeehive;
import cn.nukkit.item.Item;
import cn.nukkit.utils.BlockColor;

public class BlockBeeNest
extends BlockBeehive {
    public BlockBeeNest() {
        this(0);
    }

    public BlockBeeNest(int n) {
        super(n);
    }

    @Override
    public int getId() {
        return 473;
    }

    @Override
    public String getName() {
        return "Bee Nest";
    }

    @Override
    public int getBurnChance() {
        return 30;
    }

    @Override
    public int getBurnAbility() {
        return 60;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public double getResistance() {
        return 1.5;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[0];
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.YELLOW_BLOCK_COLOR;
    }
}


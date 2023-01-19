/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.block;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockPressurePlateBase;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.utils.BlockColor;

public class BlockWeightedPressurePlateLight
extends BlockPressurePlateBase {
    public BlockWeightedPressurePlateLight() {
        this(0);
    }

    public BlockWeightedPressurePlateLight(int n) {
        super(n);
        this.onPitch = 0.90000004f;
        this.offPitch = 0.75f;
    }

    @Override
    public int getId() {
        return 147;
    }

    @Override
    public String getName() {
        return "Weighted Pressure Plate (Light)";
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public int getToolType() {
        return 3;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe()) {
            return new Item[]{this.toItem()};
        }
        return new Item[0];
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, 0);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.GOLD_BLOCK_COLOR;
    }

    @Override
    protected int computeRedstoneStrength() {
        int n = Math.min(this.level.getCollidingEntities(this.getCollisionBoundingBox()).length, this.getMaxWeight());
        if (n > 0) {
            float f2 = (float)Math.min(this.getMaxWeight(), n) / (float)this.getMaxWeight();
            return NukkitMath.ceilFloat(f2 * 15.0f);
        }
        return 0;
    }

    public int getMaxWeight() {
        return 15;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.item;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

public class ItemSignAcacia
extends Item {
    public ItemSignAcacia() {
        this(0, 1);
    }

    public ItemSignAcacia(Integer n) {
        this(n, 1);
    }

    public ItemSignAcacia(Integer n, int n2) {
        super(475, 0, n2, "Sign");
        this.block = Block.get(445);
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }
}


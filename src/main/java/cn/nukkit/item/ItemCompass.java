/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.item;

import cn.nukkit.item.Item;

public class ItemCompass
extends Item {
    public ItemCompass() {
        this(0, 1);
    }

    public ItemCompass(Integer n) {
        this(n, 1);
    }

    public ItemCompass(Integer n, int n2) {
        super(345, n, n2, "Compass");
    }
}


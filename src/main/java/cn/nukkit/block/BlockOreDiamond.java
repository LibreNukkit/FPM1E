/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.block;

import cn.nukkit.block.BlockSolid;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.Utils;

public class BlockOreDiamond
extends BlockSolid {
    @Override
    public int getId() {
        return 56;
    }

    @Override
    public double getHardness() {
        return 3.0;
    }

    @Override
    public double getResistance() {
        return 15.0;
    }

    @Override
    public int getToolType() {
        return 3;
    }

    @Override
    public String getName() {
        return "Diamond Ore";
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= 4) {
            if (item.hasEnchantment(16)) {
                return new Item[]{this.toItem()};
            }
            int n = 1;
            Enchantment enchantment = item.getEnchantment(18);
            if (enchantment != null && enchantment.getLevel() >= 1) {
                int n2 = Utils.random.nextInt(enchantment.getLevel() + 2) - 1;
                if (n2 < 0) {
                    n2 = 0;
                }
                n = n2 + 1;
            }
            return new Item[]{Item.get(264, 0, n)};
        }
        return new Item[0];
    }

    @Override
    public int getDropExp() {
        return Utils.rand(3, 7);
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }
}


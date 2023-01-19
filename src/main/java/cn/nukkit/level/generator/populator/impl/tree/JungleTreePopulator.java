/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.level.generator.populator.impl.tree;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.object.tree.NewJungleTree;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

public class JungleTreePopulator
extends Populator {
    private ChunkManager c;
    private int a;
    private int b;

    public JungleTreePopulator() {
        this(3);
    }

    public JungleTreePopulator(int n) {
    }

    public void setRandomAmount(int n) {
        this.a = n;
    }

    public void setBaseAmount(int n) {
        this.b = n;
    }

    @Override
    public void populate(ChunkManager chunkManager, int n, int n2, NukkitRandom nukkitRandom, FullChunk fullChunk) {
        this.c = chunkManager;
        int n3 = nukkitRandom.nextBoundedInt(this.a + 1) + this.b;
        Vector3 vector3 = new Vector3();
        for (int k = 0; k < n3; ++k) {
            int n4;
            int n5 = NukkitMath.randomRange(nukkitRandom, n << 4, (n << 4) + 15);
            int n6 = this.a(n5, n4 = NukkitMath.randomRange(nukkitRandom, n2 << 4, (n2 << 4) + 15));
            if (n6 == -1) continue;
            new NewJungleTree(4 + nukkitRandom.nextBoundedInt(7), 3).generate(chunkManager, nukkitRandom, vector3.setComponents(n5, n6, n4));
        }
    }

    private int a(int n, int n2) {
        int n3;
        int n4;
        for (n4 = 255; n4 > 0 && (n3 = this.c.getBlockIdAt(n, n4, n2)) != 3 && n3 != 2; --n4) {
            if (n3 == 0 || n3 == 78) continue;
            return -1;
        }
        return ++n4;
    }
}


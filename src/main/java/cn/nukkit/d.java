/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit;

import cn.nukkit.level.Level;
import java.util.HashMap;

class d
extends HashMap<Integer, Level> {
    final Server this$0;

    d(Server server) {
        this.this$0 = server;
    }

    @Override
    public Level put(Integer n, Level level) {
        Level level2 = super.put(n, level);
        Server.access$002(this.this$0, this.this$0.getLevels().values().toArray(new Level[0]));
        return level2;
    }

    @Override
    public boolean remove(Object object, Object object2) {
        boolean bl = super.remove(object, object2);
        Server.access$002(this.this$0, this.this$0.getLevels().values().toArray(new Level[0]));
        return bl;
    }

    @Override
    public Level remove(Object object) {
        Level level = super.remove(object);
        Server.access$002(this.this$0, this.this$0.getLevels().values().toArray(new Level[0]));
        return level;
    }
}


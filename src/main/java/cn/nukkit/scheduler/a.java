/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.scheduler;

import cn.nukkit.scheduler.AsyncPool;

class a
extends Thread {
    final AsyncPool this$0;

    a(AsyncPool asyncPool, Runnable runnable) {
        super(runnable);
        this.this$0 = asyncPool;
        this.setDaemon(true);
        this.setName(String.format("Nukkit Asynchronous Task Handler #%s", this.this$0.getPoolSize()));
    }
}


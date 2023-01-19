/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit;

class ConsoleThread
extends Thread
implements InterruptibleThread {
    final Server this$0;

    private ConsoleThread(Server server) {
        this.this$0 = server;
    }

    @Override
    public void run() {
        Server.getConsoleThread(this.this$0).start();
    }

    ConsoleThread(Server server, d d2) {
        this(server);
    }
}


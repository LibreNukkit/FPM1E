/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.network.rcon;

import cn.nukkit.Server;
import lombok.var;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RCONServer
extends Thread {
    private static final int i = 3;
    private static final int e = 2;
    private static final int j = 2;
    private static final int b = 0;
    private volatile boolean a;
    private final ServerSocketChannel f;
    private final Selector k;
    private final String g;
    private final Set<SocketChannel> c = new HashSet<>();
    private final List<RCONCommand> h = new ArrayList<>();
    private final Map<SocketChannel, List<RCONPacket>> d = new HashMap<>();

    public RCONServer(String string, int n, String string2) throws IOException {
        this.setName("RCON");
        this.a = true;
        this.f = ServerSocketChannel.open();
        this.f.configureBlocking(false);
        this.f.socket().bind(new InetSocketAddress(string, n));
        this.k = SelectorProvider.provider().openSelector();
        this.f.register(this.k, SelectionKey.OP_ACCEPT);
        this.g = string2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public RCONCommand receive() {
        synchronized (this.h) {
            if (!this.h.isEmpty()) {
                RCONCommand rCONCommand = this.h.get(0);
                this.h.remove(0);
                return rCONCommand;
            }
            return null;
        }
    }

    public void respond(SocketChannel socketChannel, int n, String string) {
        this.b(socketChannel, new RCONPacket(n, 0, string.getBytes()));
    }

    public void close() {
        this.a = false;
        this.k.wakeup();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (this.a) {
            try {
                synchronized (this.d) {
                    for (SocketChannel socketChannel : this.d.keySet()) {
                        socketChannel.keyFor(this.k).interestOps(SelectionKey.OP_WRITE);
                    }
                }
                this.k.select();
                var iterator = this.k.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.socket();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(this.k, SelectionKey.OP_READ);
                        continue;
                    }
                    if (selectionKey.isReadable()) {
                        this.b(selectionKey);
                        continue;
                    }
                    if (!selectionKey.isWritable()) continue;
                    this.a(selectionKey);
                }
            }
            catch (BufferUnderflowException ignored) {
            }
            catch (Exception exception) {
                Server.getInstance().getLogger().logException(exception);
            }
        }
        try {
            this.f.keyFor(this.k).cancel();
            this.f.close();
            this.k.close();
        }
        catch (IOException iOException) {
            Server.getInstance().getLogger().logException(iOException);
        }
        synchronized (this) {
            this.notify();
        }
    }

    private void b(SelectionKey selectionKey) throws IOException {
        int n;
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        try {
            n = socketChannel.read(byteBuffer);
        }
        catch (IOException iOException) {
            selectionKey.cancel();
            socketChannel.close();
            this.c.remove(socketChannel);
            this.d.remove(socketChannel);
            return;
        }
        if (n == -1) {
            selectionKey.cancel();
            socketChannel.close();
            this.c.remove(socketChannel);
            this.d.remove(socketChannel);
            return;
        }
        byteBuffer.flip();
        this.a(socketChannel, new RCONPacket(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(SocketChannel socketChannel, RCONPacket rCONPacket) {
        switch (rCONPacket.getType()) {
            case 3: {
                byte[] byArray = new byte[1];
                if (new String(rCONPacket.getPayload(), StandardCharsets.UTF_8).equals(this.g)) {
                    this.c.add(socketChannel);
                    this.b(socketChannel, new RCONPacket(rCONPacket.getId(), 2, byArray));
                    try {
                        Server.getInstance().getLogger().info("[RCON] " + socketChannel.getRemoteAddress().toString() + " connected");
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    return;
                }
                try {
                    Server.getInstance().getLogger().info("[RCON] Authentication failed for " + socketChannel.getRemoteAddress().toString());
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.b(socketChannel, new RCONPacket(-1, 2, byArray));
                break;
            }
            case 2: {
                if (!this.c.contains(socketChannel)) {
                    return;
                }
                String string = new String(rCONPacket.getPayload(), StandardCharsets.UTF_8).trim();
                synchronized (this.h) {
                    this.h.add(new RCONCommand(socketChannel, rCONPacket.getId(), string));
                    break;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        synchronized (this.d) {
            List<RCONPacket> list = this.d.get(socketChannel);
            ByteBuffer byteBuffer = list.get(0).toBuffer();
            try {
                socketChannel.write(byteBuffer);
                list.remove(0);
            }
            catch (IOException iOException) {
                selectionKey.cancel();
                socketChannel.close();
                this.c.remove(socketChannel);
                this.d.remove(socketChannel);
                return;
            }
            if (list.isEmpty()) {
                this.d.remove(socketChannel);
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(SocketChannel socketChannel2, RCONPacket rCONPacket) {
        if (!socketChannel2.keyFor(this.k).isValid()) {
            return;
        }
        synchronized (this.d) {
            List<RCONPacket> list = this.d.computeIfAbsent(socketChannel2, socketChannel -> new ArrayList<>());
            list.add(rCONPacket);
        }
        this.k.wakeup();
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}


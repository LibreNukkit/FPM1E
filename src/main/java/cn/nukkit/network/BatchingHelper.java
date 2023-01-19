/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.network;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.network.CompressionProvider;
import cn.nukkit.network.protocol.BatchPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.Binary;
import cn.nukkit.utils.BinaryStream;
import cn.nukkit.utils.Zlib;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.var;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchingHelper {
    private final ExecutorService a;

    public BatchingHelper() {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNameFormat("Batching Executor");
        threadFactoryBuilder.setUncaughtExceptionHandler((thread, throwable) -> Server.getInstance().getLogger().error("Failed to batch packets", throwable));
        this.a = Executors.newSingleThreadExecutor(threadFactoryBuilder.build());
    }

    public void batchPackets(Player[] playerArray, DataPacket[] dataPacketArray) {
        block0: {
            if (playerArray.length <= 0 || dataPacketArray.length <= 0) break block0;
            this.a.execute(() -> BatchingHelper.a(playerArray, dataPacketArray));
        }
    }

    private static void a(Player[] players, DataPacket[] packets) {
        Int2ObjectMap<ObjectList<Player>> targets = new Int2ObjectOpenHashMap<>();
        for (Player player : players) {
            targets.computeIfAbsent(player.protocol, i -> new ObjectArrayList<>()).add(player);
        }

        // Encoded packets by encoding protocol
        Int2ObjectMap<ObjectList<DataPacket>> encodedPackets = new Int2ObjectOpenHashMap<>();

        for (DataPacket packet : packets) {
            Int2IntMap encodingProtocols = new Int2IntOpenHashMap();
            for (int protocolId : targets.keySet()) {
                // TODO: encode only by encoding protocols
                // No need to have all versions here
                encodingProtocols.put(protocolId, protocolId);
            }

            Int2ObjectMap<DataPacket> encodedPacket = new Int2ObjectOpenHashMap<>();
            for (int encodingProtocol : encodingProtocols.values()) {
                if (!encodedPacket.containsKey(encodingProtocol)) {
                    DataPacket pk = packet.clone();
                    pk.protocol = encodingProtocol;
                    pk.tryEncode();
                    encodedPacket.put(encodingProtocol, pk);
                }
            }

            for (int protocolId : encodingProtocols.values()) {
                int encodingProtocol = encodingProtocols.get(protocolId);
                encodedPackets.computeIfAbsent(protocolId, i -> new ObjectArrayList<>()).add(encodedPacket.get(encodingProtocol));
            }
        }

        for (int protocolId : targets.keySet()) {
            ObjectList<DataPacket> packetList = encodedPackets.get(protocolId);
            ObjectList<Player> finalTargets = targets.get(protocolId);

            BinaryStream batched = new BinaryStream();
            for (DataPacket packet : packetList) {
                if (packet instanceof BatchPacket) {
                    throw new RuntimeException("Cannot batch BatchPacket");
                }
                packet.tryEncode();
                byte[] buf = packet.getBuffer();
                batched.putUnsignedVarInt(buf.length);
                batched.put(buf);
            }

            try {
                byte[] bytes = Binary.appendBytes(batched.getBuffer());
                BatchPacket pk = new BatchPacket();
                if (protocolId >= ProtocolInfo.v1_16_0) {
                    pk.payload = Zlib.deflateRaw(bytes, Server.getInstance().networkCompressionLevel);
                } else {
                    pk.payload = Zlib.deflate(bytes, Server.getInstance().networkCompressionLevel);
                }
                for (Player pl : finalTargets) {
                    pl.directDataPacket(pk);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdown() {
        this.a.shutdownNow();
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.level;

import cn.nukkit.NOBF;
import cn.nukkit.Server;
import cn.nukkit.level.BlockPalette;
import cn.nukkit.level.b;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.utils.BinaryStream;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import lombok.var;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalBlockPalette {
    private static final Logger p = LogManager.getLogger(GlobalBlockPalette.class);
    private static boolean G;
    private static final BlockPalette u;
    private static final BlockPalette B;
    private static final BlockPalette A;
    private static final BlockPalette b;
    private static final BlockPalette t;
    private static final BlockPalette q;
    private static final BlockPalette j;
    private static final BlockPalette D;
    private static final BlockPalette a;
    private static final BlockPalette i;
    public static final BlockPalette[] NEW_PALETTES;
    private static final Int2IntMap w;
    private static final Int2IntMap H;
    private static final Int2IntMap C;
    private static final Int2IntMap c;
    private static final Int2IntMap k;
    private static final Int2IntMap r;
    private static final Int2IntMap J;
    private static final Int2IntMap f;
    private static final Int2IntMap x;
    private static final Int2IntMap n;
    private static final Int2IntMap s;
    private static final Int2IntMap F;
    private static final Int2IntMap z;
    private static final Int2IntMap l;
    private static byte[] o;
    private static byte[] g;
    private static byte[] d;
    private static byte[] E;
    private static byte[] m;
    private static byte[] I;
    private static byte[] h;
    private static byte[] v;
    private static byte[] e;
    private static byte[] y;

    public static BlockPalette getPaletteByProtocol(int n) {
        if (n >= 560) {
            return i;
        }
        if (n >= 544) {
            return a;
        }
        if (n >= 524) {
            return D;
        }
        if (n >= 503) {
            return j;
        }
        if (n >= 485) {
            return q;
        }
        if (n >= 471) {
            return t;
        }
        if (n >= 465) {
            return b;
        }
        if (n >= 448) {
            return A;
        }
        if (n >= 440) {
            return B;
        }
        if (n >= 428) {
            return u;
        }
        throw new IllegalArgumentException("Tried to get blockPalette for unsupported protocol version: " + n);
    }

    @NOBF
    public static void init() {
        int n;
        if (G) {
            throw new IllegalStateException("GlobalBlockPalette was already generated!");
        }
        G = true;
        Gson gson = new Gson();
        p.debug("Loading block palette...");
        w.defaultReturnValue(-1);
        H.defaultReturnValue(-1);
        C.defaultReturnValue(-1);
        c.defaultReturnValue(-1);
        k.defaultReturnValue(-1);
        r.defaultReturnValue(-1);
        J.defaultReturnValue(-1);
        f.defaultReturnValue(-1);
        x.defaultReturnValue(-1);
        GlobalBlockPalette.n.defaultReturnValue(-1);
        s.defaultReturnValue(-1);
        F.defaultReturnValue(-1);
        z.defaultReturnValue(-1);
        l.defaultReturnValue(-1);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);
        AtomicInteger atomicInteger3 = new AtomicInteger(0);
        AtomicInteger atomicInteger4 = new AtomicInteger(0);
        AtomicInteger atomicInteger5 = new AtomicInteger(0);
        AtomicInteger atomicInteger6 = new AtomicInteger(0);
        AtomicInteger atomicInteger7 = new AtomicInteger(0);
        AtomicInteger atomicInteger8 = new AtomicInteger(0);
        AtomicInteger atomicInteger9 = new AtomicInteger(0);
        AtomicInteger atomicInteger10 = new AtomicInteger(0);
        {
            InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_223.json");
            if (inputStream == null) {
                throw new AssertionError("Unable to locate RuntimeID table 223");
            }
            Collection<Object> collection = gson.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), new TableEntryOldCollectionTypeToken(null).getType());
            for (Object object112 : collection) {
                w.put(((TableEntryOld) object112).id << 4 | ((TableEntryOld) object112).data, ((TableEntryOld) object112).runtimeID);
            }
        }
        {
            var inputStream2 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_261.json");
            if (inputStream2 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 261");
            }
            Iterable<TableEntryOld> object112 = gson.fromJson(new InputStreamReader(inputStream2, StandardCharsets.UTF_8), new TableEntryOldCollectionTypeToken(null).getType());
            for (TableEntryOld object10 : object112) {
                H.put(object10.id << 4 | object10.data, object10.runtimeID);
            }
        }
        {
            var object12 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_274.json");
            if (object12 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 274");
            }
            Iterable<TableEntryOld> object10 = gson.fromJson(new InputStreamReader(object12, StandardCharsets.UTF_8), new TableEntryOldCollectionTypeToken(null).getType());
            for (TableEntryOld object9 : object10) {
                C.put(object9.id << 4 | object9.data, object9.runtimeID);
            }

        }
        {
            var object13 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_282.json");
            if (object13 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 282");
            }
            List<TableEntry> object9 = gson.fromJson(new InputStreamReader(object13, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream = new BinaryStream();
            binaryStream.putUnsignedVarInt(object9.size());
            for (TableEntry object8 : object9) {
                c.put(object8.id << 4 | object8.data, atomicInteger.getAndIncrement());
                binaryStream.putString(object8.name);
                binaryStream.putLShort(object8.data);
            }
            o = binaryStream.getBuffer();
        }
        {
            var object14 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_291.json");
            if (object14 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 291");
            }
            List<TableEntry> object8 = gson.fromJson(new InputStreamReader(object14, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream2 = new BinaryStream();
            binaryStream2.putUnsignedVarInt(object8.size());
            for (TableEntry object7 : object8) {
                k.put(object7.id << 4 | object7.data, atomicInteger2.getAndIncrement());
                binaryStream2.putString(object7.name);
                binaryStream2.putLShort(object7.data);
            }

            g = binaryStream2.getBuffer();
        }
        {
            var object15 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_313.json");
            if (object15 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 313");
            }
            List<TableEntry> object7 = gson.fromJson(new InputStreamReader(object15, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream3 = new BinaryStream();
            binaryStream3.putUnsignedVarInt(object7.size());
            for (TableEntry object6 : object7) {
                r.put(object6.id << 4 | object6.data, atomicInteger3.getAndIncrement());
                binaryStream3.putString(object6.name);
                binaryStream3.putLShort(object6.data);
            }

            d = binaryStream3.getBuffer();
        }
        {
            var object16 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_332.json");
            if (object16 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 332");
            }
            List<TableEntry> object6 = gson.fromJson(new InputStreamReader(object16, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream4 = new BinaryStream();
            binaryStream4.putUnsignedVarInt(object6.size());
            for (TableEntry object5 : object6) {
                J.put(object5.id << 4 | object5.data, atomicInteger4.getAndIncrement());
                binaryStream4.putString(object5.name);
                binaryStream4.putLShort(object5.data);
            }

            E = binaryStream4.getBuffer();
        }
        {
            var object17 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_340.json");
            if (object17 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 340");
            }
            List<TableEntry> object5 = gson.fromJson(new InputStreamReader(object17, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream5 = new BinaryStream();
            binaryStream5.putUnsignedVarInt(object5.size());
            for (TableEntry object4 : object5) {
                f.put(object4.id << 4 | object4.data, atomicInteger5.getAndIncrement());
                binaryStream5.putString(object4.name);
                binaryStream5.putLShort(object4.data);
            }

            m = binaryStream5.getBuffer();
        }
        {
            var object18 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_354.json");
            if (object18 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 354");
            }
            List<TableEntry> object4 = gson.fromJson(new InputStreamReader(object18, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream6 = new BinaryStream();
            binaryStream6.putUnsignedVarInt(object4.size());
            for (TableEntry object3 : object4) {
                x.put(object3.id << 4 | object3.data, atomicInteger6.getAndIncrement());
                binaryStream6.putString(object3.name);
                binaryStream6.putLShort(object3.data);
            }

            I = binaryStream6.getBuffer();
        }
        {
            var object19 = Server.class.getClassLoader().getResourceAsStream("runtimeid_table_361.json");
            if (object19 == null) {
                throw new AssertionError("Unable to locate RuntimeID table 361");
            }
            List<TableEntry> object3 = gson.fromJson(new InputStreamReader(object19, StandardCharsets.UTF_8), new TableEntryCollectionTypeToken(null).getType());
            BinaryStream binaryStream7 = new BinaryStream();
            binaryStream7.putUnsignedVarInt(object3.size());
            for (TableEntry object2 : object3) {
                GlobalBlockPalette.n.put(object2.id << 4 | object2.data, atomicInteger7.getAndIncrement());
                binaryStream7.putString(object2.name);
                binaryStream7.putLShort(object2.data);
                binaryStream7.putLShort(object2.id);
            }

            h = binaryStream7.getBuffer();
        }

        var object20 = Server.class.getClassLoader().getResourceAsStream("runtime_block_states_388.dat");
        if (object20 == null) {
            throw new AssertionError("Unable to locate block state nbt 388");
        }
        try {
            v = ByteStreams.toByteArray(object20);
            var object2 = NBTIO.readNetwork(new ByteArrayInputStream(v));
            for (Tag tg : ((ListTag<Tag>)object2).getAll()) {
                int n3 = atomicInteger8.getAndIncrement();
                if (!((CompoundTag)tg).contains("meta")) continue;
                for (int n4 : ((CompoundTag)tg).getIntArray("meta")) {
                    s.put(((CompoundTag)tg).getShort("id") << 6 | n4, n3);
                }
                ((CompoundTag)tg).remove("meta");
            }
        } catch (IOException iOException) {
            throw new AssertionError(iOException);
        }

        {
            InputStream inputStream3 = Server.class.getClassLoader().getResourceAsStream("runtime_block_states_389.dat");
            if (inputStream3 == null) {
                throw new AssertionError("Unable to locate block state nbt 389");
            }
            ListTag<Tag> tag2;
            try {
                tag2 = (ListTag<Tag>) NBTIO.readTag(inputStream3, ByteOrder.LITTLE_ENDIAN, false);
            } catch (IOException iOException) {
                throw new AssertionError("Unable to load block palette 389", iOException);
            }

            for (Tag tag : tag2.getAll()) {
                var object = (CompoundTag) tag;
                int n5 = atomicInteger9.getAndIncrement();
                if (!object.contains("meta")) continue;
                for (int n22 : object.getIntArray("meta")) {
                    F.put(object.getShort("id") << 6 | n22, n5);
                }
                object.remove("meta");
            }
            try {
                e = NBTIO.write(tag2, ByteOrder.LITTLE_ENDIAN, true);
            } catch (IOException iOException) {
                throw new AssertionError("Unable to write block palette 389", iOException);
            }
        }

        {
            ListTag<CompoundTag> ttg;
            Throwable thr = null;
            try {
                var object = Server.class.getClassLoader().getResourceAsStream("runtime_block_states_407.dat");
                try {
                    if (object == null) {
                        throw new AssertionError("Unable to locate block state nbt 407");
                    }
                    ttg = (ListTag<CompoundTag>) NBTIO.readTag(new ByteArrayInputStream(ByteStreams.toByteArray(object)), ByteOrder.BIG_ENDIAN, false);
                } catch (Throwable throwable) {
                    thr = throwable;
                    throw throwable;
                } finally {
                    if (object != null) {
                        if (thr != null) {
                            try {
                                object.close();
                            } catch (Throwable throwable) {
                                thr.addSuppressed(throwable);
                            }
                        } else {
                            object.close();
                        }
                    }
                }
            } catch (IOException iOException) {
                throw new AssertionError("Unable to load block palette 407", iOException);
            }
            for (var tg : ttg.getAll()) {
                int n4;
                int n6 = tg.getInt("id");
                n4 = tg.getShort("data");
                n = atomicInteger10.getAndIncrement();
                var n22 = n6 << 6 | n4;
                z.put(n22, n);
                tg.remove("data");
            }
            try {
                y = NBTIO.write(ttg, ByteOrder.LITTLE_ENDIAN, true);
            } catch (IOException iOException) {
                throw new AssertionError("Unable to write block palette 407", iOException);
            }
        }

        {
            Throwable thr = null;
            ListTag<CompoundTag> tags = null;
            try {

                var data = Server.class.getClassLoader().getResourceAsStream("runtime_block_states_419.dat");
                try {
                    if (data == null) {
                        throw new AssertionError("Unable to locate block state nbt 419");
                    }
                    tags = (ListTag<CompoundTag>) NBTIO.readTag(new ByteArrayInputStream(ByteStreams.toByteArray((InputStream) data)), ByteOrder.BIG_ENDIAN, false);
                } catch (Throwable throwable) {
                    thr = throwable;
                    throw throwable;
                } finally {
                    if (data != null) {
                        if (thr != null) {
                            try {
                                data.close();
                            } catch (Throwable throwable) {
                                thr.addSuppressed(throwable);
                            }
                        } else {
                            data.close();
                        }
                    }
                }
            } catch (IOException iOException) {
                throw new AssertionError("Unable to load block palette 419", iOException);
            }
            for (var object22 : tags.getAll()) {
                int n7 = ((CompoundTag) object22).getInt("id");
                n = ((CompoundTag) object22).getShort("data");
                var n22 = ((CompoundTag) object22).getInt("runtimeId");
                int n8 = n7 << 6 | n;
                l.put(n8, n22);
            }
            for (BlockPalette blockPalette : NEW_PALETTES) {
                GlobalBlockPalette.a(GlobalBlockPalette.a(blockPalette.getProtocol()), blockPalette);
            }
        }
    }

    private static ListTag<CompoundTag> a(int n) {
        ListTag<CompoundTag> listTag;
        try (InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("runtime_block_states_" + n + ".dat");){
            if (inputStream == null) {
                throw new AssertionError("Unable to locate block state nbt " + n);
            }
            listTag = (ListTag<CompoundTag>)NBTIO.readTag(new BufferedInputStream(new GZIPInputStream(inputStream)), ByteOrder.BIG_ENDIAN, false);
        }
        catch (IOException iOException) {
            throw new AssertionError("Unable to load block palette " + n, iOException);
        }
        return listTag;
    }

    private static void a(ListTag<CompoundTag> listTag, BlockPalette blockPalette) {
        ObjectArrayList<CompoundTag> objectArrayList = new ObjectArrayList<>();
        for (CompoundTag compoundTag : listTag.getAll()) {
            if (GlobalBlockPalette.a(blockPalette, compoundTag, false)) continue;
            objectArrayList.add(compoundTag);
        }
        for (CompoundTag compoundTag : objectArrayList) {
            p.debug("[{}] Registering block palette overload: {}", blockPalette.getProtocol(), compoundTag.getString("name"));
            GlobalBlockPalette.a(blockPalette, compoundTag, true);
        }
        blockPalette.lock();
    }

    private static boolean a(BlockPalette blockPalette, CompoundTag compoundTag, boolean bl) {
        int n = compoundTag.getInt("id");
        int n2 = compoundTag.getShort("data");
        int n3 = compoundTag.getInt("runtimeId");
        boolean bl2 = compoundTag.getBoolean("stateOverload");
        if (bl2 && !bl) {
            return false;
        }
        CompoundTag compoundTag2 = compoundTag.remove("id").remove("data").remove("runtimeId").remove("stateOverload");
        blockPalette.registerState(n, n2, n3, compoundTag2);
        return true;
    }

    public static int getOrCreateRuntimeId(int n, int n2, int n3) {
        if (n >= 428) {
            return GlobalBlockPalette.getPaletteByProtocol(n).getRuntimeId(n2, n3);
        }
        if (n < 223) {
            throw new IllegalArgumentException("Tried to get block runtime id for unsupported protocol version: " + n);
        }
        int n4 = n >= 388 ? n2 << 6 | n3 : n2 << 4 | n3;
        switch (n) {
            case 223: 
            case 224: {
                int n5 = w.get(n4);
                if (n5 == -1) {
                    n5 = w.get(3968);
                }
                return n5;
            }
            case 261: {
                int n6 = H.get(n4);
                if (n6 == -1) {
                    n6 = H.get(3968);
                }
                return n6;
            }
            case 274: {
                int n7 = C.get(n4);
                if (n7 == -1) {
                    n7 = C.get(3968);
                }
                return n7;
            }
            case 281: 
            case 282: {
                int n8 = c.get(n4);
                if (n8 == -1) {
                    n8 = c.get(3968);
                }
                return n8;
            }
            case 291: {
                int n9 = k.get(n4);
                if (n9 == -1) {
                    n9 = k.get(3968);
                }
                return n9;
            }
            case 313: {
                int n10 = r.get(n4);
                if (n10 == -1) {
                    n10 = r.get(3968);
                }
                return n10;
            }
            case 332: {
                int n11 = J.get(n4);
                if (n11 == -1) {
                    n11 = J.get(3968);
                }
                return n11;
            }
            case 340: {
                int n12 = f.get(n4);
                if (n12 == -1) {
                    n12 = f.get(3968);
                }
                return n12;
            }
            case 354: {
                int n13 = x.get(n4);
                if (n13 == -1) {
                    n13 = x.get(3968);
                }
                return n13;
            }
            case 361: {
                int n14 = GlobalBlockPalette.n.get(n4);
                if (n14 == -1) {
                    n14 = GlobalBlockPalette.n.get(3968);
                }
                return n14;
            }
            case 388: {
                int n15 = s.get(n4);
                if (n15 == -1 && (n15 = s.get(n2 << 6)) == -1) {
                    n15 = s.get(15872);
                }
                return n15;
            }
            case 389: 
            case 390: {
                int n16 = F.get(n4);
                if (n16 == -1 && (n16 = F.get(n2 << 6)) == -1) {
                    n16 = F.get(15872);
                }
                return n16;
            }
            case 407: 
            case 408: 
            case 409: 
            case 410: 
            case 411: {
                int n17 = z.get(n4);
                if (n17 == -1 && (n17 = z.get(n2 << 6)) == -1) {
                    n17 = z.get(15872);
                }
                return n17;
            }
            case 419: 
            case 420: 
            case 422: 
            case 423: 
            case 424: {
                int n18 = l.get(n4);
                if (n18 == -1 && (n18 = l.get(n2 << 6)) == -1) {
                    p.info("(419) Missing block runtime id mappings for " + n2 + ':' + n3);
                    n18 = l.get(15872);
                }
                return n18;
            }
        }
        throw new IllegalArgumentException("Tried to get block runtime id for unsupported protocol version: " + n);
    }

    public static byte[] getCompiledTable(int n) {
        switch (n) {
            case 281: 
            case 282: {
                return o;
            }
            case 291: {
                return g;
            }
            case 313: {
                return d;
            }
            case 332: {
                return E;
            }
            case 340: {
                return m;
            }
            case 354: {
                return I;
            }
            case 361: {
                return h;
            }
            case 388: {
                return v;
            }
            case 389: 
            case 390: {
                return e;
            }
            case 407: 
            case 408: 
            case 409: 
            case 410: 
            case 411: {
                return y;
            }
        }
        throw new IllegalArgumentException("Tried to get compiled block runtime id table for unsupported protocol version: " + n);
    }

    public static int getOrCreateRuntimeId(int n, int n2) throws NoSuchElementException {
        if (n < 223) {
            throw new IllegalArgumentException("Tried to get block runtime id for unsupported protocol version: " + n);
        }
        switch (n) {
            case 223: 
            case 224: {
                int n3 = w.get(n2);
                if (n3 == -1) {
                    n3 = w.get(3968);
                }
                return n3;
            }
            case 261: {
                int n4 = H.get(n2);
                if (n4 == -1) {
                    n4 = H.get(3968);
                }
                return n4;
            }
            case 274: {
                int n5 = C.get(n2);
                if (n5 == -1) {
                    n5 = C.get(3968);
                }
                return n5;
            }
            case 281: 
            case 282: {
                int n6 = c.get(n2);
                if (n6 == -1) {
                    n6 = c.get(3968);
                }
                return n6;
            }
            case 291: {
                int n7 = k.get(n2);
                if (n7 == -1) {
                    n7 = k.get(3968);
                }
                return n7;
            }
            case 313: {
                int n8 = r.get(n2);
                if (n8 == -1) {
                    n8 = r.get(3968);
                }
                return n8;
            }
            case 332: {
                int n9 = J.get(n2);
                if (n9 == -1) {
                    n9 = J.get(3968);
                }
                return n9;
            }
            case 340: {
                int n10 = f.get(n2);
                if (n10 == -1) {
                    n10 = f.get(3968);
                }
                return n10;
            }
            case 354: {
                int n11 = x.get(n2);
                if (n11 == -1) {
                    n11 = x.get(3968);
                }
                return n11;
            }
            case 361: {
                int n12 = GlobalBlockPalette.n.get(n2);
                if (n12 == -1) {
                    n12 = GlobalBlockPalette.n.get(3968);
                }
                return n12;
            }
        }
        return GlobalBlockPalette.getOrCreateRuntimeId(n, n2 >> 4, n2 & 0xF);
    }

    public static int getLegacyFullId(int n, int n2) {
        return GlobalBlockPalette.getPaletteByProtocol(n).getLegacyFullId(n2);
    }

    public static int getOrCreateRuntimeId(int n) throws NoSuchElementException {
        Server.mvw("GlobalBlockPalette#getOrCreateRuntimeId(int)");
        return GlobalBlockPalette.getOrCreateRuntimeId(ProtocolInfo.CURRENT_PROTOCOL, n >> 4, n & 0xF);
    }

    public static int getLegacyFullId(int n) {
        Server.mvw("GlobalBlockPalette#getLegacyFullId(int)");
        return GlobalBlockPalette.getLegacyFullId(ProtocolInfo.CURRENT_PROTOCOL, n);
    }

    static {
        u = new BlockPalette(428);
        B = new BlockPalette(440);
        A = new BlockPalette(448);
        b = new BlockPalette(465);
        t = new BlockPalette(471);
        q = new BlockPalette(486);
        j = new BlockPalette(503);
        D = new BlockPalette(527);
        a = new BlockPalette(544);
        i = new BlockPalette(560);
        NEW_PALETTES = new BlockPalette[]{u, B, A, b, t, q, j, D, a, i};
        w = new Int2IntOpenHashMap();
        H = new Int2IntOpenHashMap();
        C = new Int2IntOpenHashMap();
        c = new Int2IntOpenHashMap();
        k = new Int2IntOpenHashMap();
        r = new Int2IntOpenHashMap();
        J = new Int2IntOpenHashMap();
        f = new Int2IntOpenHashMap();
        x = new Int2IntOpenHashMap();
        n = new Int2IntOpenHashMap();
        s = new Int2IntOpenHashMap();
        F = new Int2IntOpenHashMap();
        z = new Int2IntOpenHashMap();
        l = new Int2IntOpenHashMap();
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    private static class TableEntryOldCollectionTypeToken
    extends TypeToken<Collection<TableEntryOld>> {
        private TableEntryOldCollectionTypeToken() {
        }

        /* synthetic */ TableEntryOldCollectionTypeToken(b b2) {
            this();
        }
    }

    private static class TableEntryCollectionTypeToken
    extends TypeToken<Collection<TableEntry>> {
        private TableEntryCollectionTypeToken() {
        }

        /* synthetic */ TableEntryCollectionTypeToken(b b2) {
            this();
        }
    }

    @NOBF
    private static class TableEntryOld {
        @NOBF
        private int id;
        @NOBF
        private int data;
        @NOBF
        private int runtimeID;
        @NOBF
        private String name;

        private TableEntryOld() {
        }
    }

    @NOBF
    private static class TableEntry {
        @NOBF
        private int id;
        @NOBF
        private int data;
        @NOBF
        private String name;

        private TableEntry() {
        }
    }
}


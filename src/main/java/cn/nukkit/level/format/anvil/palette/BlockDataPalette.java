/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.level.format.anvil.palette;

import cn.nukkit.Server;
import cn.nukkit.math.MathHelper;
import cn.nukkit.utils.ThreadCache;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.BitSet;

public final class BlockDataPalette
        implements Cloneable {
    private static final int a = 4096;
    private volatile char[] rawData;
    private volatile BitArray4096 encodedData;
    private volatile CharPalette palette;

    public BlockDataPalette() {
        this(new char[4096]);
    }

    public BlockDataPalette(char[] cArray) {
        Preconditions.checkArgument(cArray.length == 4096, "Data is not 4096");
        this.rawData = cArray;
    }

    private char[] getCachedRaw() {
        char[] cArray = this.rawData;
        if (cArray != null) {
            return cArray;
        }
        if (!Server.getInstance().isPrimaryThread()) {
            return this.getRaw();
        }
        return this.rawData;
    }

    public synchronized char[] getRaw() {
        CharPalette charPalette = this.palette;
        BitArray4096 bitArray4096 = this.encodedData;
        this.encodedData = null;
        this.palette = null;
        char[] cArray = this.rawData;
        if (cArray == null && charPalette != null) {
            cArray = bitArray4096 != null ? bitArray4096.toRaw() : new char[4096];
            for (int k = 0; k < 4096; ++k) {
                cArray[k] = charPalette.getKey(cArray[k]);
            }
        } else {
            cArray = new char[4096];
        }
        this.rawData = cArray;
        return this.rawData;
    }

    private int getCachedRaw(int n, int n2, int n3) {
        return (n << 8) + (n3 << 4) + n2;
    }

    public int getBlockData(int n, int n2, int n3) {
        return this.getFullBlock(n, n2, n3) & 0xF;
    }

    public int getBlockId(int n, int n2, int n3) {
        return this.getFullBlock(n, n2, n3) >> 4;
    }

    public void setBlockId(int n, int n2, int n3, int n4) {
        this.setFullBlock(n, n2, n3, (char)(n4 << 4));
    }

    public synchronized void setBlockData(int n, int n2, int n3, int n4) {
        char c2;
        int n5 = this.getCachedRaw(n, n2, n3);
        char[] cArray = this.getCachedRaw();
        if (cArray != null) {
            c2 = cArray[n5];
            cArray[n5] = (char) (c2 & 0xFFF0 | n4);
        }
        if (this.palette != null && this.encodedData != null) {
            c2 = this.palette.getKey(this.encodedData.getAt(n5));
            if ((c2 & 0xF) != n4) {
                this.b(n5, (char) (c2 & 0xFFF0 | n4));
            }
        } else {
            throw new IllegalStateException("Raw data and pallete was null");
        }
    }

    public int getFullBlock(int n, int n2, int n3) {
        return this.getCachedRaw(this.getCachedRaw(n, n2, n3));
    }

    public void setFullBlock(int n, int n2, int n3, int n4) {
        this.c(this.getCachedRaw(n, n2, n3), (char) n4);
    }

    public int getAndSetFullBlock(int n, int n2, int n3, int n4) {
        return this.getCachedRaw(this.getCachedRaw(n, n2, n3), (char) n4);
    }

    private int getCachedRaw(int n, char c2) {
        char[] cArray = this.getCachedRaw();
        if (cArray != null) {
            char c3 = cArray[n];
            cArray[n] = c2;
            return c3;
        }
        if (this.palette != null && this.encodedData != null) {
            char c4 = this.palette.getKey(this.encodedData.getAt(n));
            if (c4 != c2) {
                this.b(n, c2);
            }
            return c4;
        }
        throw new IllegalStateException("Raw data and pallete was null");
    }

    private int getCachedRaw(int n) {
        char[] cArray = this.getCachedRaw();
        if (cArray != null) {
            return cArray[n];
        }
        if (this.palette != null && this.encodedData != null) {
            return this.palette.getKey(this.encodedData.getAt(n));
        }
        throw new IllegalStateException("Raw data and pallete was null");
    }

    private void c(int n, char c2) {
        char[] cArray = this.getCachedRaw();
        if (cArray != null) {
            cArray[n] = c2;
        } else if (!this.b(n, c2)) {
            throw new IllegalStateException("Raw data and pallete was null");
        }
    }

    private synchronized boolean b(int n, char c2) {
        CharPalette charPalette = this.palette;
        BitArray4096 bitArray4096 = this.encodedData;
        if (charPalette != null && bitArray4096 != null) {
            char c3 = charPalette.getValue(c2);
            if (c3 != '\uffff') {
                bitArray4096.setAt(n, c3);
            } else {
                char[] cArray = bitArray4096.toRaw();
                for (int k = 0; k < 4096; ++k) {
                    cArray[k] = charPalette.getKey(cArray[k]);
                }
                cArray[n] = c2;
                this.rawData = cArray;
                this.encodedData = null;
                this.palette = null;
            }
            return true;
        }
        return false;
    }

    public synchronized boolean compress() {
        char[] raw = this.getRaw();
        if (raw != null) {
            char unique = 0;

            BitSet countTable = ThreadCache.boolCache4096.get();
            char[] mapFullTable = ThreadCache.charCache4096.get();
            char[] mapBitTable = ThreadCache.charCache4096v2.get();
            countTable.clear();
            for (char c : raw) {
                if (countTable.get(c)) continue;
                mapBitTable[unique] = c;
                countTable.set(c);
                unique++;
            }

            char[] keys = Arrays.copyOfRange(mapBitTable, 0, unique);
            if (keys.length > 1) {
                Arrays.sort(keys);
                for (char c = 0; c < keys.length; c++) {
                    mapFullTable[keys[c]] = c;
                }
            } else {
                mapFullTable[keys[0]] = 0;
            }

            CharPalette palette = new CharPalette();
            palette.set(keys);

            int bits = MathHelper.log2(unique - 1);
            BitArray4096 encodedData = new BitArray4096(bits);

            for (int i = 0; i < raw.length; i++) {
                mapBitTable[i] = mapFullTable[raw[i]];
            }

            encodedData.fromRaw(mapBitTable);

            this.palette = palette;
            this.encodedData = encodedData;
            rawData = null;
            return true;
        }
        return false;
    }

    public synchronized BlockDataPalette clone() {
        return new BlockDataPalette(this.getRaw().clone());
    }

    private static IllegalStateException getCachedRaw(IllegalStateException illegalStateException) {
        return illegalStateException;
    }
}


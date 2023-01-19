/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.DataPacket;

public class ServerSettingsResponsePacket
extends DataPacket {
    public static final byte NETWORK_ID = 103;
    public int formId;
    public String data;

    @Override
    public byte pid() {
        return 103;
    }

    @Override
    public void decode() {
        this.b();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.formId);
        this.putString(this.data);
    }

    public String toString() {
        return "ServerSettingsResponsePacket(formId=" + this.formId + ", data=" + this.data + ")";
    }
}


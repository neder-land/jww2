package com.github.neder_land.jww2.packet;

import com.github.neder_land.jww2.NetworkContext;
import com.github.neder_land.jww2.protocol.Protocol;
import com.google.gson.JsonObject;

public interface PacketCodec {
    <T extends Packet> void registerPacket(String id, Class<T> pkt,
                                           PacketFactory<T> factory);
    void unregisterPacket(String id);
    void unregisterPacket(Class<? extends Packet> pkt);

    Packet createPacket(String id, NetworkContext ctx);
    <T extends Packet> T createPacket(Class<T> pkt, NetworkContext ctx);

    Class<? extends Packet> getPacket(String id);

    <T extends Packet>PacketFactory<T> getFactory(Class<T> pkt);
    PacketFactory<?> getFactory(String id);

    String getId(Class<? extends Packet> pkt);
    void setProtocol(Protocol p);
    Protocol getProtocol();

    JsonObject serializePacket(Packet pkt, NetworkContext ctx);
    Packet deserializePacket(JsonObject jo, NetworkContext ctx);
}

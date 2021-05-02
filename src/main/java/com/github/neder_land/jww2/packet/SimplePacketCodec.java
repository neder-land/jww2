package com.github.neder_land.jww2.packet;

import com.github.neder_land.jww2.NetworkContext;
import com.github.neder_land.jww2.protocol.Protocol;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import java.util.Map;

public class SimplePacketCodec implements PacketCodec {

    private final BiMap<String,Class<? extends Packet>> idClass = HashBiMap.create();
    private final Map<String, PacketFactory<?>> idFactory = Maps.newHashMap();
    private Protocol protocol;

    @Override
    public <T extends Packet> void registerPacket(String id, Class<T> pkt, PacketFactory<T> factory) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id));
        Preconditions.checkArgument(pkt != null);
        Preconditions.checkArgument(factory != null);
        Preconditions.checkArgument(!idClass.containsKey(id) && !idFactory.containsKey(id));
        Preconditions.checkArgument(!idClass.containsValue(pkt));
        idClass.put(id,pkt);
        idFactory.put(id,factory);
    }

    @Override
    public void unregisterPacket(String id) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id));
        Preconditions.checkArgument(idClass.containsKey(id) && idFactory.containsKey(id));
        idClass.remove(id);
        idFactory.remove(id);
    }

    @Override
    public void unregisterPacket(Class<? extends Packet> pkt) {
        unregisterPacket(getId(pkt));
    }

    @Override
    public Packet createPacket(String id, NetworkContext ctx) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id) && ctx != null);
        Preconditions.checkArgument(idFactory.containsKey(id));
        return idFactory.get(id).newPacket(ctx);
    }

    @Override
    public <T extends Packet> T createPacket(Class<T> pkt, NetworkContext ctx) {
        Preconditions.checkArgument(pkt != null && ctx != null);
        Preconditions.checkArgument(idClass.containsValue(pkt));
        return getFactory(pkt).newPacket(ctx);
    }

    @Override
    public Class<? extends Packet> getPacket(String id) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id));
        Preconditions.checkArgument(idClass.containsKey(id));
        return idClass.get(id);
    }

    @Override
    public <T extends Packet> PacketFactory<T> getFactory(Class<T> pkt) {
        Preconditions.checkArgument(pkt != null);
        Preconditions.checkArgument(idClass.containsValue(pkt));
        return (PacketFactory<T>) idFactory.get(idClass.inverse().get(pkt));
    }

    @Override
    public PacketFactory<?> getFactory(String id) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id));
        Preconditions.checkArgument(idFactory.containsKey(id));
        return idFactory.get(id);
    }

    @Override
    public String getId(Class<? extends Packet> pkt) {
        Preconditions.checkArgument(pkt != null);
        Preconditions.checkArgument(idClass.containsValue(pkt));
        return idClass.inverse().get(pkt);
    }

    @Override
    public void setProtocol(Protocol p) {
        this.protocol = p;
    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public JsonObject serializePacket(Packet pkt, NetworkContext ctx) {
        Preconditions.checkState(protocol != null && ctx != null);
        return protocol.getResolver().wrapPacket(new PacketInfo(getId(pkt.getClass()),pkt.serialize()));
    }

    @Override
    public Packet deserializePacket(JsonObject jo, NetworkContext ctx) {
        Preconditions.checkState(protocol != null);
        PacketInfo pi = protocol.getResolver().resolvePacket(jo);
        if(!idClass.containsKey(pi.getId())){
            throw new IllegalArgumentException("Unknown packet type:"+pi.getId());
        }
        Packet p = createPacket(pi.getId(),ctx);
        p.deserialize(pi.getContent());
        return p;
    }
}

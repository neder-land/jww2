package com.github.neder_land.jww2.packet;

import com.github.neder_land.jww2.NetworkContext;

import java.util.function.Supplier;

public interface PacketFactory<T extends Packet> {
    T newPacket(NetworkContext ctx);

    static<T extends Packet> PacketFactory<T> wrap(Supplier<T> pkt) {
        return unused -> pkt.get();
    }
}

package com.github.neder_land.jww2.protocol;

import com.github.neder_land.jww2.packet.PacketInfo;
import com.google.gson.JsonObject;

public interface PacketResolver {
    JsonObject wrapPacket(PacketInfo pi);
    PacketInfo resolvePacket(JsonObject jo);
}

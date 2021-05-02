package com.github.neder_land.jww2.protocol.v1;

import com.github.neder_land.jww2.packet.PacketInfo;
import com.github.neder_land.jww2.protocol.PacketResolver;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

public class PacketResolverV1 implements PacketResolver {
    @Override
    public JsonObject wrapPacket(PacketInfo pi) {
        JsonObject closure = new JsonObject();
        closure.addProperty("id",pi.getId());
        closure.add("content",pi.getContent());
        return closure;
    }

    @Override
    public PacketInfo resolvePacket(JsonObject closure) {
        Preconditions.checkArgument(closure.has("id"));
        Preconditions.checkArgument(closure.has("content"));
        return new PacketInfo(closure.getAsJsonPrimitive("id").getAsString(),closure.getAsJsonObject("content"));
    }
}

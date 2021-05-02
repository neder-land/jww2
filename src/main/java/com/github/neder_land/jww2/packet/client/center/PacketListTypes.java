package com.github.neder_land.jww2.packet.client.center;

import com.github.neder_land.jww2.packet.Packet;
import com.google.gson.JsonObject;

public class PacketListTypes implements Packet {

    private static final JsonObject EMPTY_CONTENT = new JsonObject();

    @Override
    public JsonObject serialize() {
        return EMPTY_CONTENT;
    }

    @Override
    public void deserialize(JsonObject jo) {}
}

package com.github.neder_land.jww2.packet.server.center;

import com.github.neder_land.jww2.content.RoomInfoDetailed;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketJoinSuccess implements Packet {

    public PacketJoinSuccess(RoomInfoDetailed info) {
        this.info = info;
    }

    public PacketJoinSuccess() {
    }

    private RoomInfoDetailed info;

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("room", info.serialize())
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("room",
                        ComplexFunctions.chain(JsonElement::getAsJsonObject, RoomInfoDetailed::deserialize),
                        this::setInfo)
                .finish();
    }

    public RoomInfoDetailed getInfo() {
        return info;
    }

    public void setInfo(RoomInfoDetailed info) {
        this.info = info;
    }
}

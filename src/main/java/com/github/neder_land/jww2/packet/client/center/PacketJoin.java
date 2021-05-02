package com.github.neder_land.jww2.packet.client.center;

import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketJoin implements Packet {

    String roomName;

    public PacketJoin(String roomName) {
        this.roomName = roomName;
    }

    public PacketJoin(){}

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("room_name",roomName)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("room_name", JsonElement::getAsString, this::setRoomName)
                .finish();
    }
}

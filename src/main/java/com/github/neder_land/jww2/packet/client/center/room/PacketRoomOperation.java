package com.github.neder_land.jww2.packet.client.center.room;

import com.github.neder_land.jww2.content.RoomStatus;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketRoomOperation implements Packet {

    private RoomStatus operation;

    public PacketRoomOperation(RoomStatus operation) {
        this.operation = operation;
    }

    public PacketRoomOperation() {}

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("intend_to",operation.serialize())
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("intend_to",
                        ComplexFunctions.chain(JsonElement::getAsString, RoomStatus::getOperation),
                        this::setOperation)
                .finish();
    }

    public RoomStatus getOperation() {
        return operation;
    }

    public void setOperation(RoomStatus operation) {
        this.operation = operation;
    }
}

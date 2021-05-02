package com.github.neder_land.jww2.packet.server.center.room;

import com.github.neder_land.jww2.content.RoomStatus;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketRoomStatusChange implements Packet {

    private RoomStatus status;

    public PacketRoomStatusChange(RoomStatus status) {
        Preconditions.checkArgument(status != null);
        this.status = status;
    }

    public PacketRoomStatusChange() {
    }

    @Override
    public JsonObject serialize() {
        Preconditions.checkState(status != null);
        return SerdeHelper.serializer()
                .add("status",status.serialize())
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("status",
                        ComplexFunctions.chain(JsonElement::getAsString, RoomStatus::getOperation),
                        this::setStatus)
                .finish();
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}

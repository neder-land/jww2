package com.github.neder_land.jww2.packet.server.center;

import com.github.neder_land.jww2.content.RoomInfo;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PacketListRoomsResponse implements Packet {

    private List<RoomInfo> info;

    public PacketListRoomsResponse(List<RoomInfo> info) {
        this.info = info;
    }

    public PacketListRoomsResponse() {}

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("rooms", info, RoomInfo::serialize)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .collectionValue("rooms",
                        ArrayList::new,
                        ComplexFunctions.chain(JsonElement::getAsJsonObject, RoomInfo::deserialize),
                        this::setInfo)
                .finish();
    }

    public List<RoomInfo> getInfo() {
        return info;
    }

    public void setInfo(List<RoomInfo> info) {
        this.info = info;
    }
}

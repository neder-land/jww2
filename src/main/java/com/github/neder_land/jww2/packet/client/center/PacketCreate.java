package com.github.neder_land.jww2.packet.client.center;

import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketCreate implements Packet {

    private String roomGame;
    private String name;

    public PacketCreate(String roomGame,String name) {
        this.roomGame = roomGame;
        this.name = name;
    }

    public PacketCreate(){}

    @Override
    public JsonObject serialize() {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(roomGame));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
        return SerdeHelper.serializer()
                .add("room_game",roomGame)
                .add("name",name)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("room_game", JsonElement::getAsString, this::setRoomGame)
                .value("name", JsonElement::getAsString, this::setName)
                .finish();
    }

    public void setRoomGame(String t) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(t));
        this.roomGame = t;
    }

    public void setName(String name) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
        this.name = name;
    }
}

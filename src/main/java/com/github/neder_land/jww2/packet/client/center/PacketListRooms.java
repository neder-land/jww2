package com.github.neder_land.jww2.packet.client.center;

import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketListRooms implements Packet {

    private String game;
    public PacketListRooms(String game) {
        Preconditions.checkArgument(game != null);
        this.game = game;
    }

    public PacketListRooms() {
        this.game = "";
    }

    public void setGame(String game) {
        Preconditions.checkArgument(game != null);
        this.game = game;
    }

    public String getGame() {
        return game;
    }

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("game",game)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("game", JsonElement::getAsString, this::setGame)
                .finish();
    }
}

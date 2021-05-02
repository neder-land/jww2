package com.github.neder_land.jww2.packet.server.center;

import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;

public class PacketCreateSuccess implements Packet {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    private String game;

    public PacketCreateSuccess(String name,String game) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name) && !Strings.isNullOrEmpty(game));
        this.name = name;
        this.game = game;
    }

    public PacketCreateSuccess() {}

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("room",name)
                .add("game",game)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .stringValue("room",this::setName)
                .stringValue("game",this::setGame)
                .finish();
    }
}

package com.github.neder_land.jww2.packet.client.center.room;

import com.github.neder_land.jww2.content.RoomUserOperation;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketUserOperation implements Packet {

    private RoomUserOperation userOperation;
    private String user;

    public PacketUserOperation(RoomUserOperation userOperation, String user) {
        this.userOperation = userOperation;
        this.user = user;
    }

    public PacketUserOperation(){}

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("intend_to",userOperation.serialize())
                .add("user",user)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("intend_to",
                        ComplexFunctions.chain(JsonElement::getAsString,RoomUserOperation::getOperation),
                        this::setUserOperation)
                .value("user", JsonElement::getAsString, this::setUser)
                .finish();
    }

    public RoomUserOperation getUserOperation() {
        return userOperation;
    }

    public void setUserOperation(RoomUserOperation userOperation) {
        this.userOperation = userOperation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

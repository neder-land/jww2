package com.github.neder_land.jww2.packet.client.center;

import com.github.neder_land.jww2.content.UserAction;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketUserAction implements Packet {

    UserAction action;

    public PacketUserAction(UserAction action) {
        Preconditions.checkArgument(action != null);
        this.action = action;
    }

    public PacketUserAction() {}

    @Override
    public JsonObject serialize() {
        Preconditions.checkState(action != null);
        return SerdeHelper.serializer()
                .add("intend_to",action.serialize())
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("intend_to",
                        ComplexFunctions.chain(JsonElement::getAsString,UserAction::getAction)
                        , this::setUserAction)
                .finish();
    }

    public void setUserAction(UserAction action) {
        this.action = action;
    }

    public UserAction getUserAction() {
        return action;
    }
}

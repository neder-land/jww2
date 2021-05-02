package com.github.neder_land.jww2.packet.server.center.room;

import com.github.neder_land.jww2.content.UserStatus;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.ComplexFunctions;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PacketRoomUserStatusChange implements Packet {

    private UserStatus status;
    private String performer;

    public PacketRoomUserStatusChange(UserStatus status, String performer) {
        Preconditions.checkArgument(status != null && !Strings.isNullOrEmpty(performer));
        this.status = status;
        this.performer = performer;
    }

    public PacketRoomUserStatusChange() {
    }

    @Override
    public JsonObject serialize() {
        Preconditions.checkState(performer != null);
        Preconditions.checkState(status != null);
        return SerdeHelper.serializer()
                .add("what", status.serialize())
                .add("who", performer)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .value("what",
                        ComplexFunctions.chain(JsonElement::getAsString, UserStatus::deserialize),
                        this::setStatus)
                .stringValue("who", this::setPerformer)
                .finish();
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}

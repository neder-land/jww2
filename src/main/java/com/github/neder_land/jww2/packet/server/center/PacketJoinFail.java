package com.github.neder_land.jww2.packet.server.center;

import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;

public class PacketJoinFail implements Packet {

    public PacketJoinFail(String reason) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(reason));
        this.reason = reason;
    }

    public PacketJoinFail() {
    }

    private String reason;

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("reason",reason)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .stringValue("reason",this::setReason)
                .finish();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package com.github.neder_land.jww2.packet.server.center;

import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PacketListTypesResponse implements Packet {

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    private List<String> types;

    public PacketListTypesResponse(LinkedList<String> types) {
        Preconditions.checkArgument(types != null);
        this.types = types;
    }

    public PacketListTypesResponse() {}

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("types", types, JsonPrimitive::new)
                .build();
    }

    @Override
    public void deserialize(JsonObject jo) {
        SerdeHelper.deserializer(jo)
                .collectionValue("types", ArrayList::new, JsonElement::getAsString, this::setTypes)
                .finish();
    }
}

package com.github.neder_land.jww2.packet;

import com.google.gson.JsonObject;

public interface Packet {
    JsonObject serialize();
    void deserialize(JsonObject jo);
}

package com.github.neder_land.jww2.packet;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;

public class PacketInfo {
    private final String id;
    private final JsonObject content;

    public PacketInfo(String id, JsonObject content) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(id));
        Preconditions.checkArgument(content != null);
        this.id = id;
        this.content = content;
    }

    public JsonObject getContent() {
        return content;
    }

    public String getId() {
        return id;
    }
}

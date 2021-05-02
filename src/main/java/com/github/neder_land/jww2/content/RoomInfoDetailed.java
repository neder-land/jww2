package com.github.neder_land.jww2.content;

import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashSet;
import java.util.Set;

public class RoomInfoDetailed extends RoomInfo {

    private final Set<String> participants;

    public RoomInfoDetailed(String name, String owner, Set<String> participants, boolean inGame, String game) {
        super(name, owner, (byte) participants.size(), inGame, game);
        this.participants = participants;
    }

    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("name",super.name)
                .add("owner",super.owner)
                .add("people", participants, JsonPrimitive::new)
                .add("in_game",inGame)
                .add("game",game)
                .build();
    }

    public static RoomInfoDetailed deserialize(JsonObject object) {
        Builder builder = new Builder();
        SerdeHelper.deserializer(object)
                .stringValue("name",builder::name)
                .stringValue("owner",builder::owner)
                .collectionValue("people", HashSet::new, JsonElement::getAsString, builder::player)
                .booleanValue("in_game", builder::inGame)
                .stringValue("game", builder::game)
                .finish();
        return builder.build();
    }

    public static class Builder {
        private String name;
        private String owner;
        private Set<String> player;
        private boolean inGame = false;
        private String game;

        private boolean inGameSet = false;

        public Builder name(String name) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
            this.name = name;
            return this;
        }

        public Builder owner(String owner) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(owner));
            this.owner = owner;
            return this;
        }

        public Builder player(Set<String> participants) {
            Preconditions.checkArgument(!participants.isEmpty());
            this.player = participants;
            return this;
        }

        public Builder inGame(boolean status) {
            this.inGame = status;
            inGameSet = true;
            return this;
        }

        public Builder game(String game) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(game));
            this.game = game;
            return this;
        }

        public RoomInfoDetailed build() {
            Preconditions.checkState(name != null);
            Preconditions.checkState(owner != null);
            Preconditions.checkState(player != null);
            Preconditions.checkState(inGameSet);
            Preconditions.checkState(game != null);
            return new RoomInfoDetailed(game,owner,player,inGame,game);
        }
    }
}

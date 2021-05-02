package com.github.neder_land.jww2.content;

import com.github.neder_land.jww2.util.ObjectSerializable;
import com.github.neder_land.jww2.util.SerdeHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.JsonObject;

public class RoomInfo implements ObjectSerializable<JsonObject> {
    final String name;
    final String owner;
    final byte playerCount;
    final boolean inGame;
    final String game;

    public RoomInfo(String name, String owner, byte playerCount, boolean inGame, String game) {
        this.name = name;
        this.owner = owner;
        this.playerCount = playerCount;
        this.inGame = inGame;
        this.game = game;
    }

    public byte getPlayerCount() {
        return playerCount;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public boolean isInGame() {
        return inGame;
    }

    public String getGame() {
        return game;
    }

    /**
     * Serialize the specific object into an string object,containing
     * all information that is necessary for reconstructing the object.
     * <br />It is recommended that the shorter, the better.
     * <br />The {@link ObjectSerializable} implementations must ensure that<br />
     * {@code if(o1.equals(o2)) assert o1.serialize().contentEquals(o2.serialize());}
     *
     * @return an string containing the information about the object
     */
    @Override
    public JsonObject serialize() {
        return SerdeHelper.serializer()
                .add("name",name)
                .add("owner",owner)
                .add("people",playerCount)
                .add("in_game",inGame)
                .add("game",game)
                .build();
    }

    public static RoomInfo deserialize(JsonObject jo) {
        Builder builder = new Builder();
        SerdeHelper.deserializer(jo)
                .stringValue("name",builder::name)
                .stringValue("owner",builder::owner)
                .byteValue("people",builder::playerCount)
                .booleanValue("in_game",builder::inGame)
                .stringValue("game",builder::game)
                .finish();
        return builder.build();
    }

    public static class Builder {
        private String name;
        private String owner;
        private byte playerCount = -128;
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

        public Builder playerCount(byte count) {
            Preconditions.checkArgument(count > 0);
            this.playerCount = count;
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

        public RoomInfo build() {
            Preconditions.checkState(name != null);
            Preconditions.checkState(owner != null);
            Preconditions.checkState(playerCount > 0);
            Preconditions.checkState(inGameSet);
            Preconditions.checkState(game != null);
            return new RoomInfo(game,owner,playerCount,inGame,game);
        }
    }
}

package com.github.neder_land.jww2.content;

import com.github.neder_land.jww2.util.ObjectSerializable;

import java.util.Set;

public enum RoomUserOperation implements UserStatus {
    KICK("kick"),
    MUTE("mute"),
    TRANSMIT("set_master");

    private static final Set<String> OPERATIONS = Set.of("kick","mute","set_master");
    private final String name;

    RoomUserOperation(String name) {
        this.name = name;
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
    public String serialize() {
        return name;
    }

    public static RoomUserOperation getOperation(String name) {
        switch (name) {
            case "kick":
                return KICK;
            case "mute":
                return MUTE;
            case "set_master":
                return TRANSMIT;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static boolean hasOperation(String name) {
        return OPERATIONS.contains(name);
    }
}

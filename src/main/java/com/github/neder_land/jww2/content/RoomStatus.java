package com.github.neder_land.jww2.content;

import com.github.neder_land.jww2.util.ObjectSerializable;

import java.util.Set;

public enum RoomStatus implements UserStatus {
    START("start"),
    BREAK("break");

    private final String name;
    private static final Set<String> OPERATIONS = Set.of("start","break");

    RoomStatus(String s) {
        this.name = s;
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

    public static RoomStatus getOperation(String name) {
        switch (name) {
            case "start":
                return START;
            case "break":
                return BREAK;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static boolean hasOperation(String name) {
        return OPERATIONS.contains(name);
    }
}

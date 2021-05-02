package com.github.neder_land.jww2.content;

import com.github.neder_land.jww2.util.ObjectSerializable;

public enum UserAction implements UserStatus {
    LEAVE("leave"),
    READY("ready"),
    UNREADY("unready");

    private final String name;

    UserAction(String name) {
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

    public static UserAction getAction(String name) {
        switch (name){
            case "leave":
                return LEAVE;
            case "ready":
                return READY;
            case "unready":
                return UNREADY;
            default:
                throw new IllegalArgumentException();
        }
    }
}

package com.github.neder_land.jww2.content;

import com.github.neder_land.jww2.util.ObjectSerializable;

public interface UserStatus extends ObjectSerializable<String> {
    UserStatus JOIN = ()->"join";

    static UserStatus deserialize(String name) {
        if("join".equals(name)){
            return JOIN;
        } else if(RoomStatus.hasOperation(name)) {
            return RoomStatus.getOperation(name);
        } else if(RoomUserOperation.hasOperation(name)) {
            return RoomUserOperation.getOperation(name);
        }
        throw new IllegalArgumentException();
    }
}

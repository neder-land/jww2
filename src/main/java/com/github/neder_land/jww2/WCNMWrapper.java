package com.github.neder_land.jww2;

import com.github.neder_land.jww2.packet.PacketCodec;
import com.github.neder_land.jww2.packet.PacketFactory;
import com.github.neder_land.jww2.packet.client.center.*;
import com.github.neder_land.jww2.packet.client.center.room.PacketRoomOperation;
import com.github.neder_land.jww2.packet.client.center.room.PacketUserOperation;
import com.github.neder_land.jww2.packet.server.center.*;
import com.github.neder_land.jww2.packet.server.center.room.PacketRoomStatusChange;
import com.github.neder_land.jww2.packet.server.center.room.PacketRoomUserStatusChange;
import com.github.neder_land.jww2.support.netty.NettyWCNMWrapper;

public class WCNMWrapper {
    private static final NettyWCNMWrapper NETTY_WRAPPER = new NettyWCNMWrapper();
    public static NettyWCNMWrapper nettyHelper() {
        return NETTY_WRAPPER;
    }

    public static void initCodec(PacketCodec codec) {
        //center
        codec.registerPacket("center.list.types", PacketListTypes.class, PacketFactory.wrap(PacketListTypes::new));
        codec.registerPacket("center.list.rooms", PacketListRooms.class, PacketFactory.wrap(PacketListRooms::new));
        codec.registerPacket("center.create", PacketCreate.class, PacketFactory.wrap(PacketCreate::new));
        codec.registerPacket("center.join", PacketJoin.class, PacketFactory.wrap(PacketJoin::new));
        codec.registerPacket("center.room.room_operation", PacketRoomOperation.class, PacketFactory.wrap(PacketRoomOperation::new));
        codec.registerPacket("center.room.user_operation", PacketUserOperation.class, PacketFactory.wrap(PacketUserOperation::new));
        codec.registerPacket("center.user_action", PacketUserAction.class, PacketFactory.wrap(PacketUserAction::new));
        codec.registerPacket("center.list.types.response", PacketListTypesResponse.class, PacketFactory.wrap(PacketListTypesResponse::new));
        codec.registerPacket("center.list.rooms.response", PacketListRoomsResponse.class, PacketFactory.wrap(PacketListRoomsResponse::new));
        codec.registerPacket("center.create.success", PacketCreateSuccess.class, PacketFactory.wrap(PacketCreateSuccess::new));
        codec.registerPacket("center.create.fail", PacketCreateFail.class, PacketFactory.wrap(PacketCreateFail::new));
        codec.registerPacket("center.join.success", PacketJoinSuccess.class, PacketFactory.wrap(PacketJoinSuccess::new));
        codec.registerPacket("center.join.fail", PacketJoinFail.class, PacketFactory.wrap(PacketJoinFail::new));
        codec.registerPacket("center.leave.fail", PacketLeaveFail.class, PacketFactory.wrap(PacketLeaveFail::new));
        codec.registerPacket("center.break.fail", PacketBreakFail.class, PacketFactory.wrap(PacketBreakFail::new));
        codec.registerPacket("center.room.status_change", PacketRoomStatusChange.class, PacketFactory.wrap(PacketRoomStatusChange::new));
        codec.registerPacket("center.room.user_status_change", PacketRoomUserStatusChange.class, PacketFactory.wrap(PacketRoomUserStatusChange::new));
    }
}

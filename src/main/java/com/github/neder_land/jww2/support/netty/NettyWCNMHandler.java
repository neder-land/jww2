package com.github.neder_land.jww2.support.netty;

import com.github.neder_land.jww2.NetworkContext;
import com.github.neder_land.jww2.packet.Packet;
import com.github.neder_land.jww2.packet.PacketCodec;
import com.github.neder_land.jww2.packet.SimplePacketCodec;
import com.github.neder_land.jww2.protocol.Protocol;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.lang.reflect.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NettyWCNMHandler extends ChannelDuplexHandler {

    private final PacketCodec codec;
    private final NetworkContext context;

    public NettyWCNMHandler(PacketCodec codec, NetworkContext ctx) {
        this.codec = codec;
        this.context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof TextWebSocketFrame) {
            JsonElement element = JsonParser.parseString(((TextWebSocketFrame) msg).text());
            if(!element.isJsonObject()){
                return;
            }
            JsonObject content = element.getAsJsonObject();
            if(isHandshake(content)) {
                handleHandshake(content);
            } else {
                ctx.fireChannelRead(deserializePacket(content));
            }
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if(msg instanceof Packet) {
            Packet pkt = (Packet) msg;
            if(!handshakeFinished()) {
                throw new IllegalStateException("Handshake not finished");
            }
            ctx.write(new TextWebSocketFrame(codec.serializePacket(pkt,context).toString()),promise);
        }
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
        codec.setProtocol(null);
        ctx.disconnect(promise);
    }

    private Packet deserializePacket(JsonObject closure) {
        checkHandshake();
        return codec.deserializePacket(closure,context);
    }

    private boolean isHandshake(JsonObject content) {
        return content.has("version");
    }

    private void handleHandshake(JsonObject content) {
        if(codec.getProtocol() != null) {
            codec.setProtocol(Protocol.resolveProtocol(content.get("version").getAsString()));
        } else {
            throw new IllegalStateException("Cannot handshake twice in a session!");
        }
    }

    public boolean handshakeFinished() {
        return codec.getProtocol() != null;
    }

    private void checkHandshake() {
        if(!handshakeFinished()) {
            throw new IllegalStateException("Handshake not finished");
        }
    }

    public static class Builder {
        private Supplier<PacketCodec> codec;
        private Supplier<NetworkContext> context;

        public Builder codec(PacketCodec codec) {
            this.codec = ()->codec;
            return this;
        }

        public Builder context(NetworkContext ctx) {
            this.context = ()->ctx;
            return this;
        }

        public Builder codec(Class<? extends PacketCodec> codecImpl) {
            Preconditions.checkArgument(codecImpl != null);
            Preconditions.checkArgument(PacketCodec.class.isAssignableFrom(codecImpl));
            List<Exception> cachedExceptions = Lists.newLinkedList();

            //1.No arg constructor(public only)
            try {
                Constructor<? extends PacketCodec> constructor = codecImpl.getConstructor();
                if(constructor.canAccess(null)) {
                    return codec(constructor.newInstance());
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                cachedExceptions.add(e);
            }

            //2.Static method instance() that returns an PacketCodec instance.
            try {
                Method instance = codecImpl.getMethod("instance");
                if(Modifier.isStatic(instance.getModifiers())
                        && PacketCodec.class.isAssignableFrom(instance.getReturnType())
                        && instance.canAccess(null)) {
                    cachedExceptions.clear();
                return codec((PacketCodec) instance.invoke(null));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                cachedExceptions.add(e);
            }

            //3.Constant static field INSTANCE that is an instance of PacketCodec
            try {
                Field instance = codecImpl.getField("INSTANCE");
                if(Modifier.isStatic(instance.getModifiers())
                        && PacketCodec.class.isAssignableFrom(instance.getType())
                        && instance.canAccess(null)) {
                    cachedExceptions.clear();
                    return codec((PacketCodec) instance.get(null));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                cachedExceptions.add(e);
            }

            //Fuck you
            RuntimeException re = new RuntimeException("Don't know how to instantiate "+codecImpl.getName()+" ...");
            cachedExceptions.forEach(re::addSuppressed);
            throw re;
        }

        public Builder emptyContext() {
            this.context = ()->NetworkContext.EMPTY;
            return this;
        }

        public Builder defaultCodec() {
            this.codec = SimplePacketCodec::new;
            return this;
        }

        public Builder initCodec(Consumer<PacketCodec> initializer) {
            final Supplier<PacketCodec> old = this.codec;
            this.codec = () -> {
                PacketCodec codec = old.get();
                initializer.accept(codec);
                return codec;
            };
            return this;
        }

        public NettyWCNMHandler build() {
            if(codec == null || context == null) {
                throw new IllegalStateException("Not fully initialized!");
            }
            return new NettyWCNMHandler(codec.get(),context.get());
        }
    }
}

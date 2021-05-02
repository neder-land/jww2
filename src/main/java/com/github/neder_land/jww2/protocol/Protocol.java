package com.github.neder_land.jww2.protocol;

import com.github.neder_land.jww2.protocol.v1.PacketResolverV1;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.function.Supplier;

public enum Protocol {
    V1(PacketResolverV1::new),
    UNSUPPORTED(()->{
        throw new IllegalArgumentException();
    });

    private final Supplier<PacketResolver> resolver;

    Protocol(Supplier<PacketResolver> resolver) {
        this.resolver = resolver;
    }

    public PacketResolver getResolver() {
        return resolver.get();
    }

    public static Protocol resolveProtocol(String version) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(version));
        switch (version) {
            case "1": return V1;
            default: return UNSUPPORTED;
        }
    }

    public static boolean isSupported(Protocol p) {
        return p != null && p != UNSUPPORTED;
    }
}

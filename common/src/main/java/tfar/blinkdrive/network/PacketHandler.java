package tfar.blinkdrive.network;

import net.minecraft.resources.ResourceLocation;

import tfar.blinkdrive.BlinkDrive;
import tfar.blinkdrive.network.server.C2SBlinkPacket;
import tfar.blinkdrive.platform.Services;

import java.util.Locale;

public class PacketHandler {

    public static void registerPackets() {

        Services.PLATFORM.registerServerPacket(C2SBlinkPacket.TYPE, C2SBlinkPacket.STREAM_CODEC);
    }

    public static ResourceLocation packet(Class<?> clazz) {
        return BlinkDrive.id(clazz.getName().toLowerCase(Locale.ROOT));
    }

}

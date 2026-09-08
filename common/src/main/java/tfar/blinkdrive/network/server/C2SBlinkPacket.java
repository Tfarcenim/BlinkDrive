package tfar.blinkdrive.network.server;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Vector3f;
import tfar.blinkdrive.BlinkDrive;
import tfar.blinkdrive.platform.Services;

public record C2SBlinkPacket(BlockPos blockEntityPos,Vector3f pos) implements C2SModPacket{

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SBlinkPacket> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,C2SBlinkPacket::blockEntityPos,
                    ByteBufCodecs.VECTOR3F,C2SBlinkPacket::pos,
                    C2SBlinkPacket::new);

    public static final Type<C2SBlinkPacket> TYPE = new Type<>(BlinkDrive.id("blink"));

    @Override
    public void handleServer(ServerPlayer player) {
        Services.PLATFORM.handle(this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

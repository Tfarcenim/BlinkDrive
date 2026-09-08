package tfar.blinkdrive;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class BlinkDriveNeoForge {

    public BlinkDriveNeoForge(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        eventBus.addListener(this::register);
        eventBus.addListener(Datagen::gather);
        eventBus.addListener(PacketHandlerNeoForge::register);
        BlinkDrive.init();
    }

    void register(RegisterEvent event) {
        Init.init();
    }
}
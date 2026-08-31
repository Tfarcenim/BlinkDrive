package tfar.blinkdrive;

import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Constants.MOD_ID,dist = Dist.CLIENT)
public class BlinkDriveClientNeoForge {
    public BlinkDriveClientNeoForge(IEventBus bus) {
        bus.addListener(this::setup);
    }
    void setup(FMLClientSetupEvent event) {
        MenuScreens.register(Init.MENU_TYPE,BlinkDriveScreen::new);
    }
}

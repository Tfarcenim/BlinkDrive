package tfar.blinkdrive;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Vector3f;
import tfar.blinkdrive.network.server.C2SBlinkPacket;
import tfar.blinkdrive.platform.Services;

public class BlinkDriveScreen extends AbstractContainerScreen<BlinkDriveMenu> {

    private static final ResourceLocation BACKGROUND = BlinkDrive.id("textures/gui/blink_drive.png");

    private EditBox xCoordinate;
    private EditBox yCoordinate;
    private EditBox zCoordinate;

    public BlinkDriveScreen(BlinkDriveMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        xCoordinate = subInit(BlinkDriveMenu.Coordinate.X);
        yCoordinate = subInit(BlinkDriveMenu.Coordinate.Y);
        zCoordinate = subInit(BlinkDriveMenu.Coordinate.Z);

        addRenderableWidget(Button.builder(Component.literal("Blink"),button -> {
            tryBlink(true);
        }).bounds(leftPos+8,topPos+20,32,16).build());
    }

    void tryBlink(boolean blink) {
        if (zCoordinate != null) {
            try {
                float x = Float.parseFloat(xCoordinate.getValue());
                float y = Float.parseFloat(yCoordinate.getValue());
                float z = Float.parseFloat(zCoordinate.getValue());
                Services.PLATFORM.sendToServer(new C2SBlinkPacket(new Vector3f(x, y, z), blink));
            } catch (NumberFormatException e) {

            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    protected EditBox subInit(BlinkDriveMenu.Coordinate coordinate) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int ordinal = coordinate.ordinal();
        EditBox editBox= new EditBox(this.font, i + 84, j + 15 + ordinal * 12, 64, 12, Component.translatable("container.repair"));
        editBox.setTextColor(-1);
        editBox.setTextColorUneditable(-1);
        //editBox.setBordered(false);
        editBox.setMaxLength(9);
        editBox.setResponder(s -> onCoordinateChange(coordinate,s));
        editBox.setValue("0");
        this.addRenderableWidget(editBox);
        return editBox;
    }

    void onCoordinateChange(BlinkDriveMenu.Coordinate coordinate,String s) {
        tryBlink(false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(font,"Pearls: "+menu.dataSlot.get(),5,42,0x404040,false);
    }
}

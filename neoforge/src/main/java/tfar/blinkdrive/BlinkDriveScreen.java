package tfar.blinkdrive;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
            tryBlink();
        }).bounds(leftPos+8,topPos+20,32,16).build());
    }

    void tryBlink() {
        float x = Float.parseFloat(xCoordinate.getValue());
        float y = Float.parseFloat(yCoordinate.getValue());
        float z = Float.parseFloat(zCoordinate.getValue());
        
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        xCoordinate.render(guiGraphics, mouseX, mouseY, partialTick);
        yCoordinate.render(guiGraphics, mouseX, mouseY, partialTick);
        zCoordinate.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    protected EditBox subInit(BlinkDriveMenu.Coordinate coordinate) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int ordinal = coordinate.ordinal();
        EditBox editBox= new EditBox(this.font, i + 55, j + 15 + ordinal * 12, 103, 12, Component.translatable("container.repair"));
        editBox.setCanLoseFocus(false);
        editBox.setTextColor(-1);
        editBox.setTextColorUneditable(-1);
        //editBox.setBordered(false);
        editBox.setMaxLength(9);
        editBox.setResponder(s -> onCoordinateChange(coordinate,s));
        editBox.setValue("0");
        this.addWidget(editBox);
        return editBox;
    }

    void onCoordinateChange(BlinkDriveMenu.Coordinate coordinate,String s) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, i, j, 0, 0, this.imageWidth, imageHeight);
    }
}

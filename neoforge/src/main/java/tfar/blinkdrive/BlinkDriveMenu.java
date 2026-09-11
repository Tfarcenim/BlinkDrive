package tfar.blinkdrive;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.joml.Vector3f;

public class BlinkDriveMenu extends AbstractContainerMenu {

    private final ItemStackHandler itemStackHandler;
    private final ContainerLevelAccess access;
    public final DataSlot dataSlot;

    protected BlinkDriveMenu(int containerId, Inventory inventory) {
        this(containerId,inventory,new BlinkDriveBlockEntity.DriveHandler(9),ContainerLevelAccess.NULL,DataSlot.standalone());
    }

    protected BlinkDriveMenu(int containerId, Inventory inventory, ItemStackHandler itemStackHandler, ContainerLevelAccess access,DataSlot dataSlot) {
        super(Init.MENU_TYPE, containerId);
        this.itemStackHandler = itemStackHandler;
        this.access = access;
        this.dataSlot = dataSlot;
        int containerRows = 1;
        int i = -54;

        for (int j = 0; j < containerRows; j++) {
            for (int k = 0; k < 9; k++) {
                this.addSlot(new SlotItemHandler(itemStackHandler, k + j * 9, 8 + k * 18, 54 + j * 18));
            }
        }

        int y = 138;

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, y + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 58 + y + i));
        }
        addDataSlot(dataSlot);
    }

    public void updateCoordinates(Vector3f destination) {
        access.execute((level, pos) -> {
            BlockEntity bte = level.getBlockEntity(pos);
            if (bte instanceof BlinkDriveBlockEntity blinkDriveBlockEntity) {
                blinkDriveBlockEntity.setDestination(destination);
                dataSlot.set(blinkDriveBlockEntity.getRequiredPearls());
            }
        });
    }

    public void blink(Vector3f destination) {

        access.execute((level, pos) -> {
            BlockEntity bte = level.getBlockEntity(pos);
            if (bte instanceof BlinkDriveBlockEntity blinkDriveBlockEntity) {
                blinkDriveBlockEntity.tryBlink();
            }
        });

    }

    public enum Coordinate{X,Y,Z}

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}

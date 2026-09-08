package tfar.blinkdrive;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BlinkDriveMenu extends AbstractContainerMenu {

    private final ItemStackHandler itemStackHandler;

    protected BlinkDriveMenu(int containerId, Inventory inventory) {
        this(containerId,inventory,new ItemStackHandler(9));
    }

    protected BlinkDriveMenu(int containerId, Inventory inventory, ItemStackHandler itemStackHandler) {
        super(Init.MENU_TYPE, containerId);
        this.itemStackHandler = itemStackHandler;
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

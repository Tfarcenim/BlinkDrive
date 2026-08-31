package tfar.blinkdrive;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class BlinkDriveMenu extends AbstractContainerMenu {

    protected BlinkDriveMenu(int containerId, Inventory inventory) {
        this(containerId,inventory,new ItemStackHandler(1));
    }

    protected BlinkDriveMenu(int containerId, Inventory inventory, ItemStackHandler itemStackHandler) {
        super(Init.MENU_TYPE, containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}

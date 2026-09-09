package tfar.blinkdrive;

import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.command.SableCommandHelper;
import dev.ryanhcode.sable.api.command.SubLevelArgumentType;
import dev.ryanhcode.sable.api.physics.PhysicsPipeline;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.Collection;

public class BlinkDriveMenu extends AbstractContainerMenu {

    private final ItemStackHandler itemStackHandler;
    private final ContainerLevelAccess access;

    protected BlinkDriveMenu(int containerId, Inventory inventory) {
        this(containerId,inventory,new ItemStackHandler(9),ContainerLevelAccess.NULL);
    }

    protected BlinkDriveMenu(int containerId, Inventory inventory, ItemStackHandler itemStackHandler, ContainerLevelAccess access) {
        super(Init.MENU_TYPE, containerId);
        this.itemStackHandler = itemStackHandler;
        this.access = access;
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

    public void blink(Vector3f destination) {

        access.execute((level, pos) -> {
            final SubLevel subLevel = Sable.HELPER.getContaining(level,pos);
            if (subLevel instanceof ServerSubLevel serverSubLevel) {
                ServerSubLevelContainer container = (ServerSubLevelContainer) ServerSubLevelContainer.getContainer(level);
                final PhysicsPipeline pipeline = container.physicsSystem().getPipeline();


                final Quaterniond orientation = new Quaterniond();

               /* final Vec2 rotation = angle != null ? angle.getRotation(ctx.getSource()) : null;
                if (angle != null) {
                    orientation.rotateY(-Math.toRadians(rotation.y));
                    orientation.rotateX(Math.toRadians(rotation.x));
                }*/


                pipeline.teleport(serverSubLevel, new Vector3d(destination), subLevel.logicalPose().orientation());

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

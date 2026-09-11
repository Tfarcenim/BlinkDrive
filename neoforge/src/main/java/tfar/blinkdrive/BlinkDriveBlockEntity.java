package tfar.blinkdrive;

import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.PhysicsPipeline;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.companion.math.BoundingBox3ic;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class BlinkDriveBlockEntity extends BlockEntity implements MenuProvider {
    @Nullable
    private Component name;
    private Vector3f destination =  new Vector3f();
    private int requiredPearls = 0;

    private DriveHandler itemStackHandler =  new DriveHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };

    public void tryBlink() {
        if (requiredPearls <=0 ) { requiredPearls = calculate();}
        final SubLevel subLevel = Sable.HELPER.getContaining(level,worldPosition);
        if (subLevel instanceof ServerSubLevel serverSubLevel) {
            ServerSubLevelContainer container = (ServerSubLevelContainer) ServerSubLevelContainer.getContainer(level);
            final PhysicsPipeline pipeline = container.physicsSystem().getPipeline();


            final Quaterniond orientation = new Quaterniond();

               /* final Vec2 rotation = angle != null ? angle.getRotation(ctx.getSource()) : null;
                if (angle != null) {
                    orientation.rotateY(-Math.toRadians(rotation.y));
                    orientation.rotateX(Math.toRadians(rotation.x));
                }*/


            if (consumePearls()){
                Pose3d pose3d = subLevel.logicalPose();
                Vector3d position = pose3d.position();
                level.playSound(null,position.x,position.y,position.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS,4,1);
                for (int i = 0; i < requiredPearls * 100; i++) {
                    ((ServerLevel)level).sendParticles(ParticleTypes.PORTAL,position.x,position.y,position.z,1,0,0,0,.5);
                }

                pipeline.teleport(serverSubLevel, new Vector3d(destination), pose3d.orientation());
                requiredPearls = 0;
            }
        }
    }

    boolean consumePearls() {
        int remainder = requiredPearls;
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack stack = itemStackHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                int remove = Math.min(stack.getCount(), remainder);
                stack.shrink(remove);
                remainder -= remove;
            }
        }
        return remainder == 0;
    }

    public int getRequiredPearls() {
        return requiredPearls;
    }

    public void setDestination(Vector3f destination) {
        this.destination = destination;
        requiredPearls = calculate();
    }

    //The Blink Drive functions as a vehicle teleporter.
    // Interacting with the Blink Drive opens a GUI where players can input coordinates and dump enderpearls into a small inventory.
    // A Redstone signal will activate the Blink Drive and teleport the contraption/vehicle to the input coordinates.
    // It will consume enderpearls from the mentioned inventory within the Blink Drive.
    // The amount of enderpearls consumed should scale with contraption size; Every 100 blocks requires 1 enderpearl.
    // So a 1,000 block contraption will consume 10 pearls when using the Blink drive.
    // If there are not enough enderpearls in the Blink Drive to teleport then the contraption won't move, but it will still consume the enderpearls.

    public int calculate() {
        final SubLevel subLevel = Sable.HELPER.getContaining(level,worldPosition);
        if (subLevel instanceof ServerSubLevel serverSubLevel) {
            BoundingBox3ic boundingBox = serverSubLevel.getPlot().getBoundingBox();
            int volume = boundingBox.volume();
            return Math.min(144,(int) Math.ceil(volume/100f));
        }
        return 1;
    }

    public static class DriveHandler extends ItemStackHandler {
        public DriveHandler(int size) {super(size);}

        public NonNullList<ItemStack> getStacks() {
            return stacks;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(Items.ENDER_PEARL);
        }
    }

    public BlinkDriveBlockEntity(BlockPos pos, BlockState blockState) {
        super(Init.BLOCK_ENTITY_TYPE, pos, blockState);
    }

    public Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Component getCustomName() {
        return this.name;
    }

    protected Component getDefaultName() {
        return getBlockState().getBlock().getName();
    }

    protected DataSlot dataSlot = new DataSlot() {
        @Override
        public int get() {
            return requiredPearls;
        }

        @Override
        public void set(int value) {
            requiredPearls =  value;
        }
    };

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BlinkDriveMenu(containerId,playerInventory,itemStackHandler, ContainerLevelAccess.create(level,worldPosition), dataSlot);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, itemStackHandler.getStacks(), registries);
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }

        CompoundTag destinationTag = tag.getCompound("destination");

        destination = new Vector3f(destinationTag.getFloat("x"), destinationTag.getFloat("y"), destinationTag.getFloat("z"));

        requiredPearls = calculate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, itemStackHandler.getStacks(), registries);

        CompoundTag compoundTag = new CompoundTag();

        compoundTag.putFloat("x", destination.x);
        compoundTag.putFloat("y", destination.y);
        compoundTag.putFloat("z", destination.z);
        tag.put("destination", compoundTag);

        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
        componentInput.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(itemStackHandler.getStacks());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.name);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(itemStackHandler.getStacks()));
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
        tag.remove("Items");
    }
}

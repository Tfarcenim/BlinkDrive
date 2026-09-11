package tfar.blinkdrive;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Init {

    public static final BlinkDriveBlock BLOCK = new BlinkDriveBlock(BlockBehaviour.Properties.of().strength(1));
    public static final BlockItem ITEM = new BlockItem(BLOCK,new Item.Properties());
    public static final BlockEntityType<BlinkDriveBlockEntity> BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(BlinkDriveBlockEntity::new,BLOCK).build(null);
    public static final MenuType<BlinkDriveMenu> MENU_TYPE = new MenuType<>(BlinkDriveMenu::new, FeatureFlags.VANILLA_SET);

    static {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blink_drive");
        Registry.register(BuiltInRegistries.BLOCK, id, BLOCK);
        Registry.register(BuiltInRegistries.ITEM, id, ITEM);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, BLOCK_ENTITY_TYPE);
        Registry.register(BuiltInRegistries.MENU, id, MENU_TYPE);
    }

    public static void init() {
    }
}

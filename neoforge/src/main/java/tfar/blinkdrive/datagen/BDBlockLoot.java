package tfar.blinkdrive.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import tfar.blinkdrive.Init;

import java.util.List;
import java.util.Set;

public class BDBlockLoot extends BlockLootSubProvider {
    protected BDBlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.VANILLA_SET, registries);
    }

    @Override
    protected void generate() {
        dropSelf(Init.BLOCK);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(Init.BLOCK);
    }
}

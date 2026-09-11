package tfar.blinkdrive.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.blinkdrive.Constants;
import tfar.blinkdrive.Init;

public class BDBlockStateProvider extends BlockStateProvider {
    public BDBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(Init.BLOCK,cubeAll(Init.BLOCK));
    }
}

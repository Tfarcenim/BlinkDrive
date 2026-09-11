package tfar.blinkdrive;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tfar.blinkdrive.datagen.BDBlockStateProvider;
import tfar.blinkdrive.datagen.BDLang;
import tfar.blinkdrive.datagen.BDLootTableProvider;
import tfar.blinkdrive.datagen.BDRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class Datagen {
    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        event.addProvider(BDLootTableProvider.create(packOutput,lookupProvider));
        event.addProvider(new BDBlockStateProvider(packOutput, existingFileHelper));
        event.addProvider(new BDRecipeProvider(packOutput,lookupProvider));
        event.addProvider(new BDLang(packOutput));
    }
}

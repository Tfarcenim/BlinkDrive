package tfar.blinkdrive.datagen;

import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import tfar.blinkdrive.Init;

import java.util.concurrent.CompletableFuture;

public class BDRecipeProvider extends RecipeProvider {
    public BDRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    //It is crafted with one enderman head in the middle slot, one chest below that, Redstone dust above, and the six side slots filled with brass sheets.
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,Init.BLOCK)
                .define('B',  AllItems.BRASS_SHEET.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('E', Items.ENDER_EYE)
                .define('C', Tags.Items.CHESTS_WOODEN)
                .pattern("BRB")
                .pattern("BEB")
                .pattern("BCB")
                .unlockedBy(getHasName(Items.ENDER_EYE),has(Items.ENDER_EYE))
                .save(recipeOutput);
    }
}

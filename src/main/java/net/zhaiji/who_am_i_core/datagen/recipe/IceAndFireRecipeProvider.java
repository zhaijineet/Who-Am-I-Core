package net.zhaiji.who_am_i_core.datagen.recipe;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

import java.util.concurrent.CompletableFuture;

public class IceAndFireRecipeProvider extends RecipeProvider {
    public IceAndFireRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        // 鬼火 → 魂质
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, IafItems.ECTOPLASM.get())
            .requires(IceAndFireOrgans.GHOST_FIRE.get())
            .unlockedBy(getHasName(IceAndFireOrgans.GHOST_FIRE.get()), has(IceAndFireOrgans.GHOST_FIRE.get()))
            .save(recipeOutput, WhoAmICore.of(getItemName(IafItems.ECTOPLASM.get())));
    }
}

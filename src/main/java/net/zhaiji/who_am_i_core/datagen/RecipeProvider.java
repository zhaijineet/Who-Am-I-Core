package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.zhaiji.who_am_i_core.datagen.recipe.AnvilCraftRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.CataclysmRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.CompanionsRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.FDBossesRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.IceAndFireRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.IronSpellRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.MowziesMobRecipeProvider;
import net.zhaiji.who_am_i_core.datagen.recipe.WAICRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        this.packOutput = output;
        this.registries = registries;
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        super.buildRecipes(output);
        new AnvilCraftRecipeProvider(packOutput, registries).buildRecipes(output);
        new IronSpellRecipeProvider(packOutput, registries).buildRecipes(output);
        new CataclysmRecipeProvider(packOutput, registries).buildRecipes(output);
        new IceAndFireRecipeProvider(packOutput, registries).buildRecipes(output);
        new MowziesMobRecipeProvider(packOutput, registries).buildRecipes(output);
        new FDBossesRecipeProvider(packOutput, registries).buildRecipes(output);
        new CompanionsRecipeProvider(packOutput, registries).buildRecipes(output);
        new WAICRecipeProvider(packOutput, registries).buildRecipes(output);
    }
}

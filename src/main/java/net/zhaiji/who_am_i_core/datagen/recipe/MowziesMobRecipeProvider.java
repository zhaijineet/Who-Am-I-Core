package net.zhaiji.who_am_i_core.datagen.recipe;

import dev.dubhe.anvilcraft.init.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;

import java.util.concurrent.CompletableFuture;

public class MowziesMobRecipeProvider extends RecipeProvider {
    public MowziesMobRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        // 制御棒：铅锭（辐射屏蔽）+ 钨锭（高熔点外壳）+ 电路板（控制逻辑）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MowziesMobOrgans.CONTROL_ROD.get())
            .pattern(" T ")
            .pattern("LCL")
            .pattern(" T ")
            .define('C', ModItems.CIRCUIT_BOARD)
            .define('T', ModItems.TUNGSTEN_INGOT)
            .define('L', ModItems.LEAD_INGOT)
            .unlockedBy(getHasName(ModItems.CIRCUIT_BOARD), has(ModItems.CIRCUIT_BOARD))
            .save(recipeOutput);
    }
}

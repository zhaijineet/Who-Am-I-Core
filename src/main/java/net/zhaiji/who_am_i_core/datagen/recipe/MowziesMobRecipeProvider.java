package net.zhaiji.who_am_i_core.datagen.recipe;

import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import dev.dubhe.anvilcraft.init.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;

import java.util.concurrent.CompletableFuture;

public class MowziesMobRecipeProvider extends RecipeProvider {
    public MowziesMobRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        // 制御棒：铅锭+ 钨锭+ 电路板
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MowziesMobOrgans.CONTROL_ROD.get())
            .pattern(" T ")
            .pattern("LCL")
            .pattern(" T ")
            .define('C', ModItems.CIRCUIT_BOARD)
            .define('T', ModItems.TUNGSTEN_INGOT)
            .define('L', ModItems.LEAD_INGOT)
            .unlockedBy(getHasName(ModItems.CIRCUIT_BOARD), has(ModItems.CIRCUIT_BOARD))
            .save(recipeOutput);

        // 活性泥峭棒 → 泥峭棒
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemHandler.BLUFF_ROD.get())
            .requires(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
            .unlockedBy(getHasName(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get()), has(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get()))
            .save(recipeOutput, WhoAmICore.of(getItemName(ItemHandler.BLUFF_ROD.get())));
    }
}

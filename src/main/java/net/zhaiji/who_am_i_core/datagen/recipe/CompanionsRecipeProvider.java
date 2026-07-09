package net.zhaiji.who_am_i_core.datagen.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.recipe.ClothTeddyBearCopyRecipe;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CompanionsRecipeProvider extends RecipeProvider {
    public CompanionsRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        // 布织泰迪熊复制配方：上3羊毛 / 剪刀+泰迪器官+皮革 / 下3羊毛，泰迪器官保留不消耗
        ShapedRecipePattern pattern = ShapedRecipePattern.of(
            Map.of(
                'W', Ingredient.of(ItemTags.WOOL),
                'S', Ingredient.of(Items.SHEARS),
                'T', Ingredient.of(CompanionsOrgans.CLOTH_TEDDY_BEAR.get()),
                'L', Ingredient.of(Items.LEATHER)
            ),
            "WWW",
            "STL",
            "WWW"
        );
        ClothTeddyBearCopyRecipe recipe = new ClothTeddyBearCopyRecipe(
            "",
            CraftingBookCategory.MISC,
            pattern,
            new ItemStack(CompanionsOrgans.CLOTH_TEDDY_BEAR.get()),
            true
        );
        recipeOutput.accept(
            ResourceLocation.fromNamespaceAndPath(WhoAmICore.MOD_ID, "cloth_teddy_bear_copy"),
            recipe,
            null
        );
    }
}

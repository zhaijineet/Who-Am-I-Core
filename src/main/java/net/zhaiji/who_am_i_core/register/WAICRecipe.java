package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.recipe.ClothTeddyBearCopyRecipe;

import java.util.function.Supplier;

public class WAICRecipe {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(
        BuiltInRegistries.RECIPE_SERIALIZER,
        WhoAmICore.MOD_ID
    );

    public static final Supplier<RecipeSerializer<ClothTeddyBearCopyRecipe>> CLOTH_TEDDY_BEAR_COPY = RECIPE_SERIALIZER.register(
        "cloth_teddy_bear_copy",
        ClothTeddyBearCopyRecipe.Serializer::new
    );
}

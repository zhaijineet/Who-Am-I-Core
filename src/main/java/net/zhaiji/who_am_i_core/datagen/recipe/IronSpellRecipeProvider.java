package net.zhaiji.who_am_i_core.datagen.recipe;

import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.iafenvoy.iceandfire.registry.IafItems;
import dev.dubhe.anvilcraft.init.block.ModFluids;
import dev.dubhe.anvilcraft.init.item.ModItems;
import io.redspace.ironsspellbooks.fluids.PotionFluid;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.BrewAlchemistCauldronRecipe;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.FillAlchemistCauldronRecipe;
import io.redspace.ironsspellbooks.registries.FluidRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class IronSpellRecipeProvider extends RecipeProvider {
    public IronSpellRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);
        crimsonOrganRecipes(recipeOutput);
        coreOrganRecipes(recipeOutput);
        inkOrganRecipes(recipeOutput);
        mimicOrganRecipes(recipeOutput);
        brewIceVenom(recipeOutput, InitItem.INTESTINE.get(), WAICOrgans.STRAIGHT_INTESTINE.get());
        emeraldSkullRecipes(recipeOutput);
        woodenOrganRecipes(recipeOutput);
        brewCrimson(recipeOutput, InitItems.GARAGE_KIT.get(), WAICOrgans.FLESH_IDOL.get());
        dragonBloodPreparationRecipes(recipeOutput);
    }

    // 龙血药剂：再生药水浸泡 IaF 龙血，三变体 × 三龙血 = 9 个浸泡配方
    private void dragonBloodPreparationRecipes(RecipeOutput output) {
        for (Holder<Potion> potion : List.of(Potions.REGENERATION, Potions.LONG_REGENERATION, Potions.STRONG_REGENERATION)) {
            brewDragonBlood(output, potion, IafItems.FIRE_DRAGON_BLOOD.get(), WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get());
            brewDragonBlood(output, potion, IafItems.ICE_DRAGON_BLOOD.get(), WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get());
            brewDragonBlood(output, potion, IafItems.LIGHTNING_DRAGON_BLOOD.get(), WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION.get());
        }
    }

    private void brewDragonBlood(RecipeOutput output, Holder<Potion> potion, Item reagent, Item result) {
        String resultName = BuiltInRegistries.ITEM.getKey(result).getPath();
        String potionName = BuiltInRegistries.POTION.getKey(potion.value()).getPath();
        BrewAlchemistCauldronRecipe.builder()
            .withInput(PotionFluid.of(1000, potion, PotionFluid.BottleType.REGULAR))
            .withReagent(reagent)
            .withByproduct(result)
            .save(output, WhoAmICore.of("alchemist_cauldron/soak_" + resultName + "_" + potionName));
    }

    // 木质器官：器官 + 250mb 橡肤药水 → 木质器官
    private void woodenOrganRecipes(RecipeOutput output) {
        brewOakskin(output, InitItem.HEART.get(), WAICOrgans.WOODEN_HEART.get());
        brewOakskin(output, InitItem.LUNG.get(), WAICOrgans.WOODEN_LUNG.get());
        brewOakskin(output, InitItem.STOMACH.get(), WAICOrgans.WOODEN_STOMACH.get());
        brewOakskin(output, InitItem.INTESTINE.get(), WAICOrgans.WOODEN_INTESTINE.get());
        brewOakskin(output, InitItem.KIDNEY.get(), WAICOrgans.WOODEN_KIDNEY.get());
        brewOakskin(output, InitItem.SPLEEN.get(), WAICOrgans.WOODEN_SPLEEN.get());
        brewOakskin(output, InitItem.LIVER.get(), WAICOrgans.WOODEN_LIVER.get());
        brewOakskin(output, InitItem.APPENDIX.get(), WAICOrgans.WOODEN_APPENDIX.get());
        brewOakskin(output, InitItem.MUSCLE.get(), WAICOrgans.WOODEN_MUSCLE.get());
    }

    // 猩红器官：器官 + 1000mb 血液 → 猩红器官
    private void crimsonOrganRecipes(RecipeOutput output) {
        brewCrimson(output, InitItem.HEART.get(), WAICOrgans.CRIMSON_HEART.get());
        brewCrimson(output, InitItem.LUNG.get(), WAICOrgans.CRIMSON_LUNG.get());
        brewCrimson(output, InitItem.STOMACH.get(), WAICOrgans.CRIMSON_STOMACH.get());
        brewCrimson(output, InitItem.INTESTINE.get(), WAICOrgans.CRIMSON_INTESTINE.get());
        brewCrimson(output, InitItem.KIDNEY.get(), WAICOrgans.CRIMSON_KIDNEY.get());
        brewCrimson(output, InitItem.SPLEEN.get(), WAICOrgans.CRIMSON_SPLEEN.get());
        brewCrimson(output, InitItem.LIVER.get(), WAICOrgans.CRIMSON_LIVER.get());
        brewCrimson(output, InitItem.APPENDIX.get(), WAICOrgans.CRIMSON_APPENDIX.get());
        brewCrimson(output, InitItem.MUSCLE.get(), WAICOrgans.CRIMSON_MUSCLE.get());
    }

    // 核心器官：对应法球 + 1000mb 永恒浆液 → 核心器官
    private void coreOrganRecipes(RecipeOutput output) {
        brewTimeless(output, ItemRegistry.HOLY_UPGRADE_ORB.get(), WAICOrgans.DIVINE_CORE.get());
        brewTimeless(output, ItemRegistry.ICE_UPGRADE_ORB.get(), WAICOrgans.FROST_CORE.get());
        brewTimeless(output, ItemRegistry.FIRE_UPGRADE_ORB.get(), WAICOrgans.FLAME_CORE.get());
        brewTimeless(output, ItemRegistry.NATURE_UPGRADE_ORB.get(), WAICOrgans.NATURE_CORE.get());
    }

    // 墨水器官：对应物品 + 1000mb 普通墨水 → 墨水器官
    private void inkOrganRecipes(RecipeOutput output) {
        brewInk(output, InitItem.HEART.get(), WAICOrgans.INK_HEART.get());
        brewInk(output, InitItem.LUNG.get(), WAICOrgans.INK_LUNG.get());
        brewInk(output, InitItem.SPINE.get(), WAICOrgans.INK_SPINE.get());
        brewInk(output, InitItem.STOMACH.get(), WAICOrgans.INK_STOMACH.get());
        brewInk(output, InitItem.INTESTINE.get(), WAICOrgans.INK_INTESTINE.get());
        brewInk(output, InitItem.KIDNEY.get(), WAICOrgans.INK_KIDNEY.get());
        brewInk(output, InitItem.SPLEEN.get(), WAICOrgans.INK_SPLEEN.get());
        brewInk(output, InitItem.LIVER.get(), WAICOrgans.INK_LIVER.get());
        brewInk(output, InitItem.RIB.get(), WAICOrgans.INK_RIB.get());
        brewInk(output, InitItem.APPENDIX.get(), WAICOrgans.INK_APPENDIX.get());
        brewInk(output, InitItem.MUSCLE.get(), WAICOrgans.INK_MUSCLE.get());
        brewInk(output, ItemRegistry.ARCANE_INGOT.get(), WAICOrgans.NIB.get());
        brewInk(output, ItemRegistry.INK_COMMON.get(), WAICOrgans.INK_BOTTLE.get());
    }

    // 拟态器官：对应猩红器官 + 1000mb 永恒浆液 → 拟态器官
    private void mimicOrganRecipes(RecipeOutput output) {
        brewTimeless(output, WAICOrgans.CRIMSON_HEART.get(), WAICOrgans.MIMIC_HEART.get());
        brewTimeless(output, WAICOrgans.CRIMSON_LUNG.get(), WAICOrgans.MIMIC_LUNG.get());
        brewTimeless(output, WAICOrgans.CRIMSON_LIVER.get(), WAICOrgans.MIMIC_LIVER.get());
    }

    // 绿宝石头骨：灌入熔融宝石 + 头颅浸泡
    private void emeraldSkullRecipes(RecipeOutput output) {
        // 熔融宝石桶 → 灌入 1000mb 熔融宝石 → 空桶
        new FillAlchemistCauldronRecipe.Builder()
            .withInput(ModItems.MELT_GEM_BUCKET.get())
            .withReturnItem(Items.BUCKET)
            .withFluid(new FluidStack(ModFluids.MELT_GEM, 1000))
            .withSound(SoundEvents.BUCKET_EMPTY)
            .mustFitAll(false)
            .save(output);
        // 任意头颅 + 1000mb 熔融宝石 → 绿宝石头骨
        BrewAlchemistCauldronRecipe.builder()
            .withInput(new FluidStack(ModFluids.MELT_GEM, 1000))
            .withReagent(ItemTags.SKULLS)
            .withByproduct(IronSpellOrgans.EMERALD_SKULL.get())
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：器官在血液中浸泡转化为猩红器官
     */
    private void brewCrimson(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.BLOOD, 1000)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：物品在永恒浆液中浸泡转化
     */
    private void brewTimeless(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.TIMELESS_SLURRY_FLUID, 1000)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：物品在普通墨水中浸泡转化为墨水器官
     */
    private void brewInk(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.COMMON_INK, 1000)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：物品在冰霜毒液中浸泡转化
     */
    private void brewIceVenom(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.ICE_VENOM_FLUID, 1000)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：器官在橡肤药水中浸泡转化为木质器官
     */
    private void brewOakskin(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.OAKSKIN_ELIXIR_FLUID, 250)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }
}

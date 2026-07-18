package net.zhaiji.who_am_i_core.datagen.recipe;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.finderfeed.fdbosses.init.BossItems;
import com.github.tartaricacid.touhoulittlemaid.init.InitItems;
import com.iafenvoy.iceandfire.registry.IafItems;
import dev.dubhe.anvilcraft.init.block.ModFluids;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.BrewAlchemistCauldronRecipe;
import io.redspace.ironsspellbooks.recipe_types.alchemist_cauldron.FillAlchemistCauldronRecipe;
import io.redspace.ironsspellbooks.registries.FluidRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICFluid;
import net.zhaiji.who_am_i_core.register.WAICItem;

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
        brewWater(recipeOutput, InitItem.INTESTINE.get(), WAICOrgans.STRAIGHT_INTESTINE.get());
        emeraldSkullRecipes(recipeOutput);
        woodenOrganRecipes(recipeOutput);
        brewCrimson(recipeOutput, InitItems.GARAGE_KIT.get(), WAICOrgans.FLESH_IDOL.get());
        dragonBloodFluidRecipes(recipeOutput);
    }

    // 龙血药剂：龙血倒入锅形成流体，再用灾变掉落物浸泡制成药剂
    private void dragonBloodFluidRecipes(RecipeOutput output) {
        fillDragonBlood(output, IafItems.FIRE_DRAGON_BLOOD.get(), WAICFluid.FIRE_DRAGON_BLOOD_FLUID.get(), "fire_dragon_blood");
        fillDragonBlood(output, IafItems.ICE_DRAGON_BLOOD.get(), WAICFluid.ICE_DRAGON_BLOOD_FLUID.get(), "ice_dragon_blood");
        fillDragonBlood(
            output,
            IafItems.LIGHTNING_DRAGON_BLOOD.get(),
            WAICFluid.LIGHTNING_DRAGON_BLOOD_FLUID.get(),
            "lightning_dragon_blood"
        );

        String fireName = BuiltInRegistries.ITEM.getKey(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get()).getPath();
        brewDragonBloodPreparation(
            output,
            WAICFluid.FIRE_DRAGON_BLOOD_FLUID.get(),
            ModItems.MONSTROUS_HORN.get(),
            WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get(),
            fireName,
            "monstrous_horn"
        );
        brewDragonBloodPreparation(
            output,
            WAICFluid.FIRE_DRAGON_BLOOD_FLUID.get(),
            ModItems.IGNITIUM_INGOT.get(),
            WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get(),
            fireName,
            "ignitium_ingot"
        );
        brewDragonBloodPreparation(
            output,
            WAICFluid.FIRE_DRAGON_BLOOD_FLUID.get(),
            ModItems.ANCIENT_METAL_INGOT.get(),
            WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get(),
            fireName,
            "ancient_metal_ingot"
        );

        String iceName = BuiltInRegistries.ITEM.getKey(WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get()).getPath();
        brewDragonBloodPreparation(
            output,
            WAICFluid.ICE_DRAGON_BLOOD_FLUID.get(),
            ModItems.TIDAL_CLAWS.get(),
            WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get(),
            iceName,
            "tidal_claws"
        );
        brewDragonBloodPreparation(
            output,
            WAICFluid.ICE_DRAGON_BLOOD_FLUID.get(),
            ModItems.CURSIUM_INGOT.get(),
            WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get(),
            iceName,
            "cursium_ingot"
        );
        brewDragonBloodPreparation(
            output,
            WAICFluid.ICE_DRAGON_BLOOD_FLUID.get(),
            ModItems.ESSENCE_OF_THE_STORM.get(),
            WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get(),
            iceName,
            "essence_of_the_storm"
        );

        String lightningName = BuiltInRegistries.ITEM.getKey(WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION.get()).getPath();
        brewDragonBloodPreparation(
            output,
            WAICFluid.LIGHTNING_DRAGON_BLOOD_FLUID.get(),
            ModItems.WITHERITE_INGOT.get(),
            WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION.get(),
            lightningName,
            "witherite_ingot"
        );
        brewDragonBloodPreparation(
            output,
            WAICFluid.LIGHTNING_DRAGON_BLOOD_FLUID.get(),
            ModItems.GAUNTLET_OF_GUARD.get(),
            WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION.get(),
            lightningName,
            "gauntlet_of_guard"
        );

        // 任意核心或神圣灵魂碎片浸泡任意龙血流体制成对应龙血药剂
        Item[] reagents = {
            BossItems.LIGHTNING_CORE.get(),
            BossItems.FIRE_AND_ICE_CORE.get(),
            BossItems.JUSTICE_CORE.get(),
            ItemRegistry.DIVINE_SOULSHARD.get()
        };
        for (Item reagent : reagents) {
            String reagentName = BuiltInRegistries.ITEM.getKey(reagent).getPath();
            brewDragonBloodPreparation(
                output,
                WAICFluid.FIRE_DRAGON_BLOOD_FLUID.get(),
                reagent,
                WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get(),
                fireName,
                reagentName
            );
            brewDragonBloodPreparation(
                output,
                WAICFluid.ICE_DRAGON_BLOOD_FLUID.get(),
                reagent,
                WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get(),
                iceName,
                reagentName
            );
            brewDragonBloodPreparation(
                output,
                WAICFluid.LIGHTNING_DRAGON_BLOOD_FLUID.get(),
                reagent,
                WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION.get(),
                lightningName,
                reagentName
            );
        }
    }

    /**
     * 炼金锅 Fill 配方：龙血物品倒入锅 → 250mb 流体 + 空玻璃瓶
     */
    private void fillDragonBlood(RecipeOutput output, Item dragonBloodItem, Fluid dragonBloodFluid, String fluidName) {
        new FillAlchemistCauldronRecipe.Builder()
            .withInput(dragonBloodItem)
            .withReturnItem(Items.GLASS_BOTTLE)
            .withFluid(new FluidStack(dragonBloodFluid, 250))
            .save(output, WhoAmICore.of("alchemist_cauldron/fill_" + fluidName));
    }

    /**
     * 炼金锅 Brew 浸泡配方：250mb 龙血流体 + 灾变掉落物 → 龙血药剂制备物
     */
    private void brewDragonBloodPreparation(
        RecipeOutput output,
        Fluid dragonBloodFluid,
        Item reagent,
        Item result,
        String resultName,
        String reagentName
    ) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(new FluidStack(dragonBloodFluid, 250))
            .withReagent(reagent)
            .withByproduct(result)
            .save(output, WhoAmICore.of("alchemist_cauldron/soak_" + resultName + "_" + reagentName));
    }

    // 木质器官：器官 + 250mb 橡肤药水 → 木质器官
    private void woodenOrganRecipes(RecipeOutput output) {
        brewOakskin(output, ItemTagManager.HEART, WAICOrgans.WOODEN_HEART.get());
        brewOakskin(output, ItemTagManager.LUNG, WAICOrgans.WOODEN_LUNG.get());
        brewOakskin(output, ItemTagManager.STOMACH, WAICOrgans.WOODEN_STOMACH.get());
        brewOakskin(output, ItemTagManager.INTESTINE, WAICOrgans.WOODEN_INTESTINE.get());
        brewOakskin(output, ItemTagManager.KIDNEY, WAICOrgans.WOODEN_KIDNEY.get());
        brewOakskin(output, ItemTagManager.SPLEEN, WAICOrgans.WOODEN_SPLEEN.get());
        brewOakskin(output, ItemTagManager.LIVER, WAICOrgans.WOODEN_LIVER.get());
        brewOakskin(output, ItemTagManager.APPENDIX, WAICOrgans.WOODEN_APPENDIX.get());
        brewOakskin(output, ItemTagManager.MUSCLE, WAICOrgans.WOODEN_MUSCLE.get());
    }

    // 猩红器官：器官 + 1000mb 血液 → 猩红器官
    private void crimsonOrganRecipes(RecipeOutput output) {
        brewCrimson(output, ItemTagManager.HEART, WAICOrgans.CRIMSON_HEART.get());
        brewCrimson(output, ItemTagManager.LUNG, WAICOrgans.CRIMSON_LUNG.get());
        brewCrimson(output, ItemTagManager.STOMACH, WAICOrgans.CRIMSON_STOMACH.get());
        brewCrimson(output, ItemTagManager.INTESTINE, WAICOrgans.CRIMSON_INTESTINE.get());
        brewCrimson(output, ItemTagManager.KIDNEY, WAICOrgans.CRIMSON_KIDNEY.get());
        brewCrimson(output, ItemTagManager.SPLEEN, WAICOrgans.CRIMSON_SPLEEN.get());
        brewCrimson(output, ItemTagManager.LIVER, WAICOrgans.CRIMSON_LIVER.get());
        brewCrimson(output, ItemTagManager.APPENDIX, WAICOrgans.CRIMSON_APPENDIX.get());
        brewCrimson(output, ItemTagManager.MUSCLE, WAICOrgans.CRIMSON_MUSCLE.get());
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
        brewInk(output, ItemTagManager.HEART, WAICOrgans.INK_HEART.get());
        brewInk(output, ItemTagManager.LUNG, WAICOrgans.INK_LUNG.get());
        brewInk(output, ItemTagManager.SPINE, WAICOrgans.INK_SPINE.get());
        brewInk(output, ItemTagManager.STOMACH, WAICOrgans.INK_STOMACH.get());
        brewInk(output, ItemTagManager.INTESTINE, WAICOrgans.INK_INTESTINE.get());
        brewInk(output, ItemTagManager.KIDNEY, WAICOrgans.INK_KIDNEY.get());
        brewInk(output, ItemTagManager.SPLEEN, WAICOrgans.INK_SPLEEN.get());
        brewInk(output, ItemTagManager.LIVER, WAICOrgans.INK_LIVER.get());
        brewInk(output, ItemTagManager.RIB, WAICOrgans.INK_RIB.get());
        brewInk(output, ItemTagManager.APPENDIX, WAICOrgans.INK_APPENDIX.get());
        brewInk(output, ItemTagManager.MUSCLE, WAICOrgans.INK_MUSCLE.get());
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
            .withInput(dev.dubhe.anvilcraft.init.item.ModItems.MELT_GEM_BUCKET.get())
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
     * 炼金锅浸泡配方：物品在血液中浸泡转化
     */
    private void brewCrimson(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.BLOOD, 1000)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    private void brewCrimson(RecipeOutput output, TagKey<Item> reagent, Item result) {
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

    private void brewInk(RecipeOutput output, TagKey<Item> reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.COMMON_INK, 1000)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：物品在水中浸泡转化
     */
    private void brewWater(RecipeOutput output, Item reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(new FluidStack(Fluids.WATER, 1000))
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }

    /**
     * 炼金锅浸泡配方：器官（按 tag 匹配）在橡肤药水中浸泡转化为木质器官
     */
    private void brewOakskin(RecipeOutput output, TagKey<Item> reagent, Item result) {
        BrewAlchemistCauldronRecipe.builder()
            .withInput(FluidRegistry.OAKSKIN_ELIXIR_FLUID, 250)
            .withReagent(reagent)
            .withByproduct(result)
            .saveSoak(output);
    }
}

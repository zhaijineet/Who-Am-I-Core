package net.zhaiji.who_am_i_core.datagen.recipe;

import dev.dubhe.anvilcraft.api.data.ItemEnchantmentsData;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.StampingRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.TimeWarpRecipe;
import dev.dubhe.anvilcraft.recipe.multiple.TwoToOneSmithingRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.AnvilCraftOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AnvilCraftRecipeProvider extends RecipeProvider {
    public AnvilCraftRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        super.buildRecipes(output);
        royalSteelOrganRecipes(output);
        cursedGoldOrganRecipes(output);
        emberMetalOrganRecipes(output);
        frostMetalOrganRecipes(output);
        transcendiumOrganRecipes(output);
        cyberneticOrganRecipes(output);
        railgunRecipe(output);
        squashRecipe(output);
        lesionOrganRecipes(output);
        elderOrganRecipes(output);
        petiteChestOpenerRecipe(output);
    }

    // 皇家钢器官
    private void royalSteelOrganRecipes(RecipeOutput output) {
        Ingredient template = Ingredient.of(ModItems.ROYAL_STEEL_UPGRADE_SMITHING_TEMPLATE);
        Ingredient addition = Ingredient.of(ModItems.ROYAL_STEEL_INGOT);
        Criterion<?> criterion = has(ModItems.ROYAL_STEEL_INGOT);

        smithingTransform(output, template, Ingredient.of(ItemTagManager.RIB), addition, AnvilCraftOrgans.ROYAL_STEEL_RIB.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.MUSCLE), addition, AnvilCraftOrgans.ROYAL_STEEL_MUSCLE.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.SPINE), addition, AnvilCraftOrgans.ROYAL_STEEL_SPINE.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.APPENDIX), addition, AnvilCraftOrgans.ROYAL_STEEL_APPENDIX.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.HEART), addition, AnvilCraftOrgans.ROYAL_STEEL_HEART.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.LUNG), addition, AnvilCraftOrgans.ROYAL_STEEL_LUNG.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.STOMACH), addition, AnvilCraftOrgans.ROYAL_STEEL_STOMACH.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.INTESTINE), addition, AnvilCraftOrgans.ROYAL_STEEL_INTESTINE.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.KIDNEY), addition, AnvilCraftOrgans.ROYAL_STEEL_KIDNEY.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.SPLEEN), addition, AnvilCraftOrgans.ROYAL_STEEL_SPLEEN.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.LIVER), addition, AnvilCraftOrgans.ROYAL_STEEL_LIVER.get(), criterion);
    }

    // 诅咒金器官
    private void cursedGoldOrganRecipes(RecipeOutput output) {
        Ingredient template = Ingredient.of(ModItems.ROYAL_STEEL_UPGRADE_SMITHING_TEMPLATE);
        Ingredient addition = Ingredient.of(ModItems.CURSED_GOLD_INGOT);
        Criterion<?> criterion = has(ModItems.CURSED_GOLD_INGOT);

        smithingTransform(output, template, Ingredient.of(ItemTagManager.HEART), addition, AnvilCraftOrgans.CURSED_GOLD_HEART.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.LUNG), addition, AnvilCraftOrgans.CURSED_GOLD_LUNG.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.LIVER), addition, AnvilCraftOrgans.CURSED_GOLD_LIVER.get(), criterion);
        smithingTransform(output, template, Ingredient.of(ItemTagManager.INTESTINE), addition, AnvilCraftOrgans.CURSED_GOLD_INTESTINE.get(), criterion);
    }

    // 余烬金属器官
    private void emberMetalOrganRecipes(RecipeOutput output) {
        Ingredient template = Ingredient.of(ModItems.EMBER_METAL_UPGRADE_SMITHING_TEMPLATE);
        Ingredient addition = Ingredient.of(ModItems.EMBER_METAL_INGOT);
        Criterion<?> criterion = has(ModItems.EMBER_METAL_INGOT);

        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_RIB.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_RIB.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_MUSCLE.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_MUSCLE.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_SPINE.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_SPINE.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_APPENDIX.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_APPENDIX.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_HEART.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_HEART.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_LUNG.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_LUNG.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_STOMACH.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_STOMACH.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_INTESTINE.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_INTESTINE.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_KIDNEY.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_KIDNEY.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_SPLEEN.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_SPLEEN.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_LIVER.get(),
            addition,
            AnvilCraftOrgans.EMBER_METAL_LIVER.get(),
            criterion
        );
    }

    // 浮霜器官
    private void frostMetalOrganRecipes(RecipeOutput output) {
        Ingredient template = Ingredient.of(ModItems.FROST_METAL_UPGRADE_SMITHING_TEMPLATE);
        Ingredient addition = Ingredient.of(ModItems.FROST_METAL_INGOT);
        Criterion<?> criterion = has(ModItems.FROST_METAL_INGOT);

        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_RIB.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_RIB.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_MUSCLE.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_MUSCLE.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_SPINE.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_SPINE.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_APPENDIX.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_APPENDIX.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_HEART.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_HEART.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_LUNG.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_LUNG.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_STOMACH.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_STOMACH.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_INTESTINE.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_INTESTINE.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_KIDNEY.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_KIDNEY.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_SPLEEN.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_SPLEEN.get(),
            criterion
        );
        smithingTransform(
            output,
            template,
            AnvilCraftOrgans.ROYAL_STEEL_LIVER.get(),
            addition,
            AnvilCraftOrgans.FROST_METAL_LIVER.get(),
            criterion
        );
    }

    // 超限合金器官
    private void transcendiumOrganRecipes(RecipeOutput output) {
        Criterion<?> criterion = has(ModItems.MULTIPHASE_TRANSCENDIUM);

        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_RIB.get(),
            AnvilCraftOrgans.EMBER_METAL_RIB.get(),
            AnvilCraftOrgans.TRANSCENDIUM_RIB.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_MUSCLE.get(),
            AnvilCraftOrgans.EMBER_METAL_MUSCLE.get(),
            AnvilCraftOrgans.TRANSCENDIUM_MUSCLE.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_SPINE.get(),
            AnvilCraftOrgans.EMBER_METAL_SPINE.get(),
            AnvilCraftOrgans.TRANSCENDIUM_SPINE.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_APPENDIX.get(),
            AnvilCraftOrgans.EMBER_METAL_APPENDIX.get(),
            AnvilCraftOrgans.TRANSCENDIUM_APPENDIX.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_HEART.get(),
            AnvilCraftOrgans.EMBER_METAL_HEART.get(),
            AnvilCraftOrgans.TRANSCENDIUM_HEART.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_LUNG.get(),
            AnvilCraftOrgans.EMBER_METAL_LUNG.get(),
            AnvilCraftOrgans.TRANSCENDIUM_LUNG.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_STOMACH.get(),
            AnvilCraftOrgans.EMBER_METAL_STOMACH.get(),
            AnvilCraftOrgans.TRANSCENDIUM_STOMACH.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_INTESTINE.get(),
            AnvilCraftOrgans.EMBER_METAL_INTESTINE.get(),
            AnvilCraftOrgans.TRANSCENDIUM_INTESTINE.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_KIDNEY.get(),
            AnvilCraftOrgans.EMBER_METAL_KIDNEY.get(),
            AnvilCraftOrgans.TRANSCENDIUM_KIDNEY.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_SPLEEN.get(),
            AnvilCraftOrgans.EMBER_METAL_SPLEEN.get(),
            AnvilCraftOrgans.TRANSCENDIUM_SPLEEN.get(),
            criterion
        );
        twoToOneSmithing(
            output,
            ModItems.MULTIPHASE_TRANSCENDIUM,
            AnvilCraftOrgans.FROST_METAL_LIVER.get(),
            AnvilCraftOrgans.EMBER_METAL_LIVER.get(),
            AnvilCraftOrgans.TRANSCENDIUM_LIVER.get(),
            criterion
        );
    }

    // 电磁义体器官：冲压合成
    private void cyberneticOrganRecipes(RecipeOutput output) {
        // 演算核心：电路板 + 磁盘 → 演算核心
        StampingRecipe.builder()
            .requires(ModItems.CIRCUIT_BOARD)
            .requires(ModItems.DISK)
            .result(WAICOrgans.COMPUTING_CORE.get())
            .save(output, WhoAmICore.of(getItemName(WAICOrgans.COMPUTING_CORE.get())));

        // 导流肋骨：皇家钢肋骨 + 小型能量转换器 → 导流肋骨
        StampingRecipe.builder()
            .requires(AnvilCraftOrgans.ROYAL_STEEL_RIB.get())
            .requires(ModBlocks.POWER_CONVERTER_SMALL)
            .result(WAICOrgans.CURRENT_RIB.get())
            .save(output, WhoAmICore.of(getItemName(WAICOrgans.CURRENT_RIB.get())));

        // 充能肌束：皇家钢肌肉 + 磁电核心 → 充能肌束
        StampingRecipe.builder()
            .requires(AnvilCraftOrgans.ROYAL_STEEL_MUSCLE.get())
            .requires(ModBlocks.MAGNETO_ELECTRIC_CORE_BLOCK)
            .result(WAICOrgans.CHARGED_MUSCLE.get())
            .save(output, WhoAmICore.of(getItemName(WAICOrgans.CHARGED_MUSCLE.get())));

        // 传导链节：皇家钢脊柱 + 小型能量转换器 → 传导链节
        StampingRecipe.builder()
            .requires(AnvilCraftOrgans.ROYAL_STEEL_SPINE.get())
            .requires(ModBlocks.POWER_CONVERTER_SMALL)
            .result(WAICOrgans.CONDUCTIVE_SPINE.get())
            .save(output, WhoAmICore.of(getItemName(WAICOrgans.CONDUCTIVE_SPINE.get())));

        // 蓄能模块：电容器 + 小型能量转换器 → 蓄能模块
        StampingRecipe.builder()
            .requires(ModItems.CAPACITOR)
            .requires(ModBlocks.POWER_CONVERTER_SMALL)
            .result(WAICOrgans.ENERGY_MODULE.get())
            .save(output, WhoAmICore.of(getItemName(WAICOrgans.ENERGY_MODULE.get())));
    }

    // 加速环 → 电磁炮器官
    private void railgunRecipe(RecipeOutput output) {
        StampingRecipe.builder()
            .requires(ModBlocks.ACCELERATION_RING)
            .result(AnvilCraftOrgans.RAILGUN.get())
            .save(output, WhoAmICore.of(getItemName(AnvilCraftOrgans.RAILGUN.get())));
    }

    /**
     * 构建原版 smithing_transform 配方，路径自动取结果物品注册名
     */
    private void smithingTransform(
        RecipeOutput output,
        Ingredient template,
        Item base,
        Ingredient addition,
        Item result,
        Criterion<?> criterion
    ) {
        SmithingTransformRecipeBuilder.smithing(template, Ingredient.of(base), addition, RecipeCategory.MISC, result)
            .unlocks(getHasName(result), criterion)
            .save(output, WhoAmICore.of(getItemName(result)));
    }

    /**
     * 构建原版 smithing_transform 配方（base 为 Ingredient），路径自动取结果物品注册名
     */
    private void smithingTransform(
        RecipeOutput output,
        Ingredient template,
        Ingredient base,
        Ingredient addition,
        Item result,
        Criterion<?> criterion
    ) {
        SmithingTransformRecipeBuilder.smithing(template, base, addition, RecipeCategory.MISC, result)
            .unlocks(getHasName(result), criterion)
            .save(output, WhoAmICore.of(getItemName(result)));
    }

    /**
     * 构建 anvilcraft:two_to_one_smithing 二合一配方，路径自动取结果物品注册名
     */
    private void twoToOneSmithing(
        RecipeOutput output, ItemLike material, Item inputA, Item inputB, Item result, Criterion<?> criterion
    ) {
        TwoToOneSmithingRecipe.Builder builder = TwoToOneSmithingRecipe.builder();
        builder.material(material);
        builder.input(inputA);
        builder.input(inputB);
        builder.resultMerge(
            result,
            ItemEnchantmentsData.enchantments(0),
            ItemEnchantmentsData.enchantments(1)
        );
        builder.unlockedBy(getHasName(result), criterion);
        builder.save(output, WhoAmICore.of(getItemName(result)));
    }

    // 窝瓜：南瓜 + 重锤 → 窝瓜
    private void squashRecipe(RecipeOutput output) {
        StampingRecipe.builder()
            .requires(Items.CARVED_PUMPKIN)
            .requires(Items.MACE)
            .result(WAICOrgans.SQUASH.get())
            .save(output, WhoAmICore.of(getItemName(WAICOrgans.SQUASH.get())));
    }

    // 病变器官：时辐射照配方
    // 普通器官 + 铀锭 → 腐化信标辐照 → 病变器官
    private void lesionOrganRecipes(RecipeOutput output) {
        Criterion<?> criterion = has(ModItems.URANIUM_INGOT);

        lesionTimeWarp(output, ItemTagManager.HEART, WAICOrgans.LESION_HEART.get(), criterion);
        lesionTimeWarp(output, ItemTagManager.MUSCLE, WAICOrgans.LESION_MUSCLE.get(), criterion);
    }

    private void lesionTimeWarp(
        RecipeOutput output,
        TagKey<Item> base,
        Item result,
        Criterion<?> criterion
    ) {
        TimeWarpRecipe.builder()
            .requires(base)
            .requires(ModItems.URANIUM_INGOT)
            .result(result)
            .unlockedBy(getHasName(result), criterion)
            .save(output, WhoAmICore.of(getItemName(result)));
    }

    // 远古器官：普通器官 → 时移 → 远古器官
    private void elderOrganRecipes(RecipeOutput output) {
        elderTimeWarp(output, InitItem.HEART, InitItem.ELDER_HEART);
        elderTimeWarp(output, InitItem.LUNG, InitItem.ELDER_LUNG);
        elderTimeWarp(output, InitItem.GILL, InitItem.ELDER_GILL);
        elderTimeWarp(output, InitItem.APPENDIX, InitItem.ELDER_APPENDIX);
        elderTimeWarp(output, InitItem.LIVER, InitItem.ELDER_LIVER);
        elderTimeWarp(output, InitItem.SPLEEN, InitItem.ELDER_SPLEEN);
        elderTimeWarp(output, InitItem.KIDNEY, InitItem.ELDER_KIDNEY);
        elderTimeWarp(output, InitItem.STOMACH, InitItem.ELDER_STOMACH);
        elderTimeWarp(output, InitItem.MUSCLE, InitItem.ELDER_MUSCLE);
        elderTimeWarp(output, InitItem.FISH_MUSCLE, InitItem.ELDER_FISH_MUSCLE);
        elderTimeWarp(output, InitItem.SPINE, InitItem.ELDER_SPINE);
        elderTimeWarp(output, InitItem.RIB, InitItem.ELDER_RIB);
        elderTimeWarp(output, InitItem.FISH_SPINE, InitItem.ELDER_FISH_SPINE);
        elderTimeWarp(output, InitItem.FISH_BONE, InitItem.ELDER_FISH_BONE);
        elderTimeWarp(output, InitItem.INTESTINE, InitItem.ELDER_INTESTINE);
        elderTimeWarp(output, InitItem.FISH_INTESTINE, InitItem.ELDER_FISH_INTESTINE);
        elderTimeWarp(output, InitItem.MANA_REACTOR, InitItem.ELDER_MANA_REACTOR);
        elderTimeWarp(output, InitItem.GUARDIAN_EYE, InitItem.ELDER_GUARDIAN_EYE);
    }

    private void elderTimeWarp(
        RecipeOutput output, Supplier<Item> base, Supplier<Item> result
    ) {
        TimeWarpRecipe.builder()
            .requires(base.get())
            .result(result.get())
            .unlockedBy(getHasName(result.get()), has(base.get()))
            .save(output, WhoAmICore.of(getItemName(result.get())));
    }

    // 娇小开胸器：冲压开胸器 → 娇小开胸器
    private void petiteChestOpenerRecipe(RecipeOutput output) {
        StampingRecipe.builder()
            .requires(InitItem.CHEST_OPENER.get())
            .result(WAICItem.PETITE_CHEST_OPENER.get())
            .save(output, WhoAmICore.of(getItemName(WAICItem.PETITE_CHEST_OPENER.get())));
    }
}

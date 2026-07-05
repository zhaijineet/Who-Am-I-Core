package net.zhaiji.who_am_i_core.datagen.recipe;

import com.iafenvoy.iceandfire.registry.IafItems;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.xylonity.companions.registry.CompanionsItems;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.concurrent.CompletableFuture;

public class WAICRecipeProvider extends RecipeProvider {
    public WAICRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        // 弗兰肯斯坦心脏：心脏 + 巫毒针 + 动物心脏
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.FRANKENSTEIN_HEART.get())
            .pattern("HNA")
            .define('H', InitItem.HEART.get())
            .define('N', CompanionsItems.NEEDLE.get())
            .define('A', InitItem.ANIMAL_HEART.get())
            .unlockedBy(getHasName(CompanionsItems.NEEDLE.get()), has(CompanionsItems.NEEDLE.get()))
            .save(recipeOutput);

        // 调色盘：四角奥术源质，中木板，上绿左蓝右黄下红
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.PALETTE.get())
            .pattern("AGA")
            .pattern("BPY")
            .pattern("ARA")
            .define('A', ItemRegistry.ARCANE_ESSENCE.get())
            .define('P', ItemTags.PLANKS)
            .define('G', Tags.Items.DYES_GREEN)
            .define('B', Tags.Items.DYES_BLUE)
            .define('Y', Tags.Items.DYES_YELLOW)
            .define('R', Tags.Items.DYES_RED)
            .unlockedBy(getHasName(ItemRegistry.ARCANE_ESSENCE.get()), has(ItemRegistry.ARCANE_ESSENCE.get()))
            .save(recipeOutput);

        // 经验之心：上下左右附魔之瓶，中间灵魂宝石
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.EXPERIENCE_HEART.get())
            .pattern(" B ")
            .pattern("BSB")
            .pattern(" B ")
            .define('B', Items.EXPERIENCE_BOTTLE)
            .define('S', CompanionsItems.SOUL_GEM.get())
            .unlockedBy(getHasName(CompanionsItems.SOUL_GEM.get()), has(CompanionsItems.SOUL_GEM.get()))
            .save(recipeOutput);

        // 闹鬼的骨头：骨质器官 + 魂质
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WAICOrgans.HAUNTED_BONE.get())
            .requires(ItemTagManager.BONE)
            .requires(IafItems.ECTOPLASM.get())
            .unlockedBy(getHasName(IafItems.ECTOPLASM.get()), has(IafItems.ECTOPLASM.get()))
            .save(recipeOutput);

        // 龙血药剂组：火 + 冰 + 电 三种龙血药剂无序合成
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WAICItem.DRAGON_BLOOD_PREPARATION_GROUP.get())
            .requires(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get())
            .requires(WAICItem.ICE_DRAGON_BLOOD_PREPARATION.get())
            .requires(WAICItem.LIGHTNING_DRAGON_BLOOD_PREPARATION.get())
            .unlockedBy(getHasName(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get()), has(WAICItem.FIRE_DRAGON_BLOOD_PREPARATION.get()))
            .save(recipeOutput);

        nineHellOrganRecipes(recipeOutput);
    }

    // ==================== 九狱器官 ====================

    private void nineHellOrganRecipes(RecipeOutput recipeOutput) {
        // Cataclysm 物品引用（与 AnvilCraft ModItems 命名冲突，使用全限定名）
        Item voidJaw = com.github.L_Ender.cataclysm.init.ModItems.VOID_JAW.get();
        Item burningAshes = com.github.L_Ender.cataclysm.init.ModItems.BURNING_ASHES.get();

        // 第1层 · 灵薄（阑尾）：抗火阑尾 + 虚空之颚
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.LIMBO.get())
            .pattern(" V ")
            .pattern("VAV")
            .pattern(" V ")
            .define('A', InitItem.FIREPROOF_APPENDIX.get())
            .define('V', voidJaw)
            .unlockedBy(getHasName(voidJaw), has(voidJaw))
            .save(recipeOutput);

        // 第2层 · 色欲（肠子）：抗火肠子 + 结晶血液 + 猩红精华瓶
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.LUST.get())
            .pattern(" B ")
            .pattern("CIC")
            .pattern(" B ")
            .define('I', InitItem.FIREPROOF_INTESTINE.get())
            .define('C', CompanionsItems.CRYSTALLIZED_BLOOD.get())
            .define('B', ItemRegistry.BLOOD_VIAL.get())
            .unlockedBy(getHasName(CompanionsItems.CRYSTALLIZED_BLOOD.get()), has(CompanionsItems.CRYSTALLIZED_BLOOD.get()))
            .save(recipeOutput);

        // 第3层 · 暴食（胃）：抗火胃 + 恶魔之肉 + 突变之肉
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.GLUTTONY.get())
            .pattern(" M ")
            .pattern("DSD")
            .pattern(" M ")
            .define('S', InitItem.FIREPROOF_STOMACH.get())
            .define('D', CompanionsItems.DEMON_FLESH.get())
            .define('M', CompanionsItems.MUTANT_FLESH.get())
            .unlockedBy(getHasName(CompanionsItems.DEMON_FLESH.get()), has(CompanionsItems.DEMON_FLESH.get()))
            .save(recipeOutput);

        // 第4层 · 贪婪（肺脏）：抗火肺脏 + 诅咒金锭
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.GREED.get())
            .pattern(" G ")
            .pattern("GLG")
            .pattern(" G ")
            .define('L', InitItem.FIREPROOF_LUNG.get())
            .define('G', ModItems.CURSED_GOLD_INGOT)
            .unlockedBy(getHasName(ModItems.CURSED_GOLD_INGOT), has(ModItems.CURSED_GOLD_INGOT))
            .save(recipeOutput);

        // 第5层 · 愤怒（肝脏）：抗火肝脏 + 炽燃余烬
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.WRATH.get())
            .pattern(" A ")
            .pattern("ALA")
            .pattern(" A ")
            .define('L', InitItem.FIREPROOF_LIVER.get())
            .define('A', burningAshes)
            .unlockedBy(getHasName(burningAshes), has(burningAshes))
            .save(recipeOutput);

        // 第6层 · 异端（脾脏）：抗火脾脏 + 邪术手稿 + 手稿
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.HERESY.get())
            .pattern(" E ")
            .pattern("KSK")
            .pattern(" E ")
            .define('S', InitItem.FIREPROOF_SPLEEN.get())
            .define('E', ItemRegistry.ELDRITCH_PAGE.get())
            .define('K', IafItems.MANUSCRIPT.get())
            .unlockedBy(getHasName(ItemRegistry.ELDRITCH_PAGE.get()), has(ItemRegistry.ELDRITCH_PAGE.get()))
            .save(recipeOutput);

        // 第7层 · 暴力（肌肉）：抗火肌肉 + 炽燃余烬
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.VIOLENCE.get())
            .pattern(" M ")
            .pattern("MUM")
            .pattern(" M ")
            .define('U', InitItem.FIREPROOF_MUSCLE.get())
            .define('M', burningAshes)
            .unlockedBy(getHasName(burningAshes), has(burningAshes))
            .save(recipeOutput);

        // 第8层 · 欺诈（肾脏）：抗火肾脏 + 鹦鹉螺壳 + 悚怖碎片
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.FRAUD.get())
            .pattern(" S ")
            .pattern("DKD")
            .pattern(" S ")
            .define('K', InitItem.FIREPROOF_KIDNEY.get())
            .define('S', Items.NAUTILUS_SHELL)
            .define('D', IafItems.DREAD_SHARD.get())
            .unlockedBy(getHasName(Items.NAUTILUS_SHELL), has(Items.NAUTILUS_SHELL))
            .save(recipeOutput);

        // 第9层 · 背叛（心脏）：抗火心脏 + 灵魂宝石 + 神圣灵魂碎片 + 下界之星
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WAICOrgans.TREACHERY.get())
            .pattern(" D ")
            .pattern("GCG")
            .pattern(" N ")
            .define('C', InitItem.FIREPROOF_HEART.get())
            .define('G', CompanionsItems.SOUL_GEM.get())
            .define('D', ItemRegistry.DIVINE_SOULSHARD.get())
            .define('N', Items.NETHER_STAR)
            .unlockedBy(getHasName(ItemRegistry.DIVINE_SOULSHARD.get()), has(ItemRegistry.DIVINE_SOULSHARD.get()))
            .save(recipeOutput);
    }
}

package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zhaiji.chestcavitybeyond.manager.ItemTagManager;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.AnvilCraftOrgans;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICItem;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends IntrinsicHolderTagsProvider<Item> {
    public ItemTagProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key(), WhoAmICore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // 开胸器
        tag(ItemTagManager.CHEST_OPENERS).add(WAICItem.PETITE_CHEST_OPENER.get());

        fdBossesOrgansTags();
        cataclysmOrgansTags();
        ironSpellOrgansTags();
        companionsOrgansTags();
        mowziesMobSpecialOrgansTags();
        bluffOrgansTags();
        sculptorOrgansTags();
        WAICOrgansTags();
        dragonOrgansTags();
        mimicOrgansTags();
        inkOrgansTags();
        clothOrgansTags();
        crimsonOrgansTags();
        elementOrgansTags();
        pigmentOrgansTags();
        woodenOrgansTags();
        frankensteinOrgansTags();
        lesionOrgansTags();
        nineHellOrgansTags();
        cyberneticOrgansTags();
        dreadOrgansTags();
        hydraOrgansTags();
        royalSteelOrgansTags();
        cursedGoldOrgansTags();
        emberMetalOrgansTags();
        frostMetalOrgansTags();
        transcendiumOrgansTags();
        fantasticalOrgansTags();
    }

    // ==================== Mowzie's Mobs 特殊器官 ====================

    private void mowziesMobSpecialOrgansTags() {
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.SUMMON).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.CHEST_NOVA.get());

        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.MECHANICAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.CONTROL_ROD.get());

        // 钢铁守护者护心镜
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR.get());
    }

    // ==================== 泥峭器官 ====================

    private void bluffOrgansTags() {
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.BLUFF_CORE.get());
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.BLUFF_CORE.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.BLUFF_CORE.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.BLUFF_CORE.get());

        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.BLUFF_TABLET.get());

        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
    }

    // ==================== 雕刻家—通臂大师器官 ====================

    private void sculptorOrgansTags() {
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.ZEN_HEART.get());
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.ZEN_HEART.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.ZEN_HEART.get());

        tag(ItemTagManager.ORGANS).add(
            MowziesMobOrgans.AGED_HEART.get(),
            MowziesMobOrgans.AGED_LUNG.get(),
            MowziesMobOrgans.AGED_SPINE.get(),
            MowziesMobOrgans.AGED_STOMACH.get(),
            MowziesMobOrgans.AGED_INTESTINE.get(),
            MowziesMobOrgans.AGED_KIDNEY.get(),
            MowziesMobOrgans.AGED_SPLEEN.get(),
            MowziesMobOrgans.AGED_LIVER.get(),
            MowziesMobOrgans.AGED_APPENDIX.get(),
            MowziesMobOrgans.AGED_RIB.get(),
            MowziesMobOrgans.AGED_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.AGED_HEART.get());
        tag(ItemTagManager.LUNG).add(MowziesMobOrgans.AGED_LUNG.get());
        tag(ItemTagManager.SPINE).add(MowziesMobOrgans.AGED_SPINE.get());
        tag(ItemTagManager.STOMACH).add(MowziesMobOrgans.AGED_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(MowziesMobOrgans.AGED_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(MowziesMobOrgans.AGED_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(MowziesMobOrgans.AGED_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(MowziesMobOrgans.AGED_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(MowziesMobOrgans.AGED_APPENDIX.get());
        tag(ItemTagManager.RIB).add(MowziesMobOrgans.AGED_RIB.get());
        tag(ItemTagManager.MUSCLE).add(MowziesMobOrgans.AGED_MUSCLE.get());
    }

    // ==================== WAIC 独立器官 ====================

    // ==================== 龙器官 ====================

    private void dragonOrgansTags() {
        // 火龙
        tag(ItemTagManager.ORGANS).add(
            IceAndFireOrgans.FIRE_DRAGON_HEART.get(),
            IceAndFireOrgans.FIRE_DRAGON_LUNG.get(),
            IceAndFireOrgans.FIRE_DRAGON_SPINE.get(),
            IceAndFireOrgans.FIRE_DRAGON_STOMACH.get(),
            IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.FIRE_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.FIRE_DRAGON_LIVER.get(),
            IceAndFireOrgans.FIRE_DRAGON_GEM.get(),
            IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.FIRE_DRAGON_RIB.get(),
            IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(IceAndFireOrgans.FIRE_DRAGON_HEART.get());
        tag(ItemTagManager.LUNG).add(IceAndFireOrgans.FIRE_DRAGON_LUNG.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.FIRE_DRAGON_SPINE.get());
        tag(ItemTagManager.STOMACH).add(IceAndFireOrgans.FIRE_DRAGON_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(IceAndFireOrgans.FIRE_DRAGON_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(IceAndFireOrgans.FIRE_DRAGON_LIVER.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.FIRE_DRAGON_GEM.get());
        tag(WAICItemTagManager.MAGIC).add(IceAndFireOrgans.FIRE_DRAGON_GEM.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.FIRE_DRAGON_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get());
        tag(WAICItemTagManager.FIRE).add(
            IceAndFireOrgans.FIRE_DRAGON_HEART.get(),
            IceAndFireOrgans.FIRE_DRAGON_LUNG.get(),
            IceAndFireOrgans.FIRE_DRAGON_SPINE.get(),
            IceAndFireOrgans.FIRE_DRAGON_STOMACH.get(),
            IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.FIRE_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.FIRE_DRAGON_LIVER.get(),
            IceAndFireOrgans.FIRE_DRAGON_GEM.get(),
            IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.FIRE_DRAGON_RIB.get(),
            IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get()
        );
        tag(WAICItemTagManager.FIRE_DRAGON).add(
            IceAndFireOrgans.FIRE_DRAGON_HEART.get(),
            IceAndFireOrgans.FIRE_DRAGON_LUNG.get(),
            IceAndFireOrgans.FIRE_DRAGON_SPINE.get(),
            IceAndFireOrgans.FIRE_DRAGON_STOMACH.get(),
            IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.FIRE_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.FIRE_DRAGON_LIVER.get(),
            IceAndFireOrgans.FIRE_DRAGON_GEM.get(),
            IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.FIRE_DRAGON_RIB.get(),
            IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get()
        );

        // 冰龙
        tag(ItemTagManager.ORGANS).add(
            IceAndFireOrgans.ICE_DRAGON_HEART.get(),
            IceAndFireOrgans.ICE_DRAGON_LUNG.get(),
            IceAndFireOrgans.ICE_DRAGON_SPINE.get(),
            IceAndFireOrgans.ICE_DRAGON_STOMACH.get(),
            IceAndFireOrgans.ICE_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.ICE_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.ICE_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.ICE_DRAGON_LIVER.get(),
            IceAndFireOrgans.ICE_DRAGON_GEM.get(),
            IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.ICE_DRAGON_RIB.get(),
            IceAndFireOrgans.ICE_DRAGON_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(IceAndFireOrgans.ICE_DRAGON_HEART.get());
        tag(ItemTagManager.LUNG).add(IceAndFireOrgans.ICE_DRAGON_LUNG.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.ICE_DRAGON_SPINE.get());
        tag(ItemTagManager.STOMACH).add(IceAndFireOrgans.ICE_DRAGON_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(IceAndFireOrgans.ICE_DRAGON_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(IceAndFireOrgans.ICE_DRAGON_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(IceAndFireOrgans.ICE_DRAGON_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(IceAndFireOrgans.ICE_DRAGON_LIVER.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.ICE_DRAGON_GEM.get());
        tag(WAICItemTagManager.MAGIC).add(IceAndFireOrgans.ICE_DRAGON_GEM.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.ICE_DRAGON_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.ICE_DRAGON_MUSCLE.get());
        tag(WAICItemTagManager.ICE).add(
            IceAndFireOrgans.ICE_DRAGON_HEART.get(),
            IceAndFireOrgans.ICE_DRAGON_LUNG.get(),
            IceAndFireOrgans.ICE_DRAGON_SPINE.get(),
            IceAndFireOrgans.ICE_DRAGON_STOMACH.get(),
            IceAndFireOrgans.ICE_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.ICE_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.ICE_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.ICE_DRAGON_LIVER.get(),
            IceAndFireOrgans.ICE_DRAGON_GEM.get(),
            IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.ICE_DRAGON_RIB.get(),
            IceAndFireOrgans.ICE_DRAGON_MUSCLE.get()
        );
        tag(WAICItemTagManager.ICE_DRAGON).add(
            IceAndFireOrgans.ICE_DRAGON_HEART.get(),
            IceAndFireOrgans.ICE_DRAGON_LUNG.get(),
            IceAndFireOrgans.ICE_DRAGON_SPINE.get(),
            IceAndFireOrgans.ICE_DRAGON_STOMACH.get(),
            IceAndFireOrgans.ICE_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.ICE_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.ICE_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.ICE_DRAGON_LIVER.get(),
            IceAndFireOrgans.ICE_DRAGON_GEM.get(),
            IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.ICE_DRAGON_RIB.get(),
            IceAndFireOrgans.ICE_DRAGON_MUSCLE.get()
        );

        // 电龙
        tag(ItemTagManager.ORGANS).add(
            IceAndFireOrgans.LIGHTNING_DRAGON_HEART.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_SPINE.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_LIVER.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(IceAndFireOrgans.LIGHTNING_DRAGON_HEART.get());
        tag(ItemTagManager.LUNG).add(IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.LIGHTNING_DRAGON_SPINE.get());
        tag(ItemTagManager.STOMACH).add(IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(IceAndFireOrgans.LIGHTNING_DRAGON_LIVER.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get());
        tag(WAICItemTagManager.MAGIC).add(IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get());
        tag(WAICItemTagManager.LIGHTNING_DRAGON).add(
            IceAndFireOrgans.LIGHTNING_DRAGON_HEART.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_SPINE.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_LIVER.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get(),
            IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get()
        );
    }

    private void WAICOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.HAUNTED_BONE.get(),
            WAICOrgans.SWORD_BONE.get(),
            WAICOrgans.STRAIGHT_INTESTINE.get(),
            WAICOrgans.SQUASH.get(),
            WAICOrgans.EXPERIENCE_HEART.get(),
            WAICOrgans.FLESH_IDOL.get()
        );
        tag(ItemTagManager.RIB).add(WAICOrgans.HAUNTED_BONE.get());
        tag(ItemTagManager.SPINE).add(WAICOrgans.SWORD_BONE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.STRAIGHT_INTESTINE.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.STRAIGHT_INTESTINE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.SQUASH.get());
        tag(ItemTagManager.HEART).add(WAICOrgans.EXPERIENCE_HEART.get());
        tag(WAICItemTagManager.MAGIC).add(WAICOrgans.EXPERIENCE_HEART.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.EXPERIENCE_HEART.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.FLESH_IDOL.get());

        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.STRANGE_EYEBALL.get(),
            WAICOrgans.EERIE_EYEBALL.get(),
            WAICOrgans.STRANGE_MECHANICAL_EYEBALL.get(),
            WAICOrgans.EERIE_MECHANICAL_EYEBALL.get()
        );
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.STRANGE_EYEBALL.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.EERIE_EYEBALL.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.STRANGE_MECHANICAL_EYEBALL.get());
        tag(WAICItemTagManager.MECHANICAL).add(WAICOrgans.STRANGE_MECHANICAL_EYEBALL.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.EERIE_MECHANICAL_EYEBALL.get());
        tag(WAICItemTagManager.MECHANICAL).add(WAICOrgans.EERIE_MECHANICAL_EYEBALL.get());
    }

    // ==================== 拟态器官 ====================

    private void mimicOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.MIMIC_HEART.get(),
            WAICOrgans.MIMIC_LIVER.get(),
            WAICOrgans.MIMIC_LUNG.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.MIMIC_HEART.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.MIMIC_LIVER.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.MIMIC_LUNG.get());
    }

    // ==================== 墨水器官 ====================

    private void inkOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.INK_HEART.get(),
            WAICOrgans.INK_LUNG.get(),
            WAICOrgans.INK_SPINE.get(),
            WAICOrgans.INK_STOMACH.get(),
            WAICOrgans.INK_INTESTINE.get(),
            WAICOrgans.INK_KIDNEY.get(),
            WAICOrgans.INK_SPLEEN.get(),
            WAICOrgans.INK_LIVER.get(),
            WAICOrgans.INK_RIB.get(),
            WAICOrgans.INK_MUSCLE.get(),
            WAICOrgans.INK_APPENDIX.get(),
            WAICOrgans.INK_BOTTLE.get(),
            WAICOrgans.NIB.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.INK_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.INK_LUNG.get());
        tag(ItemTagManager.SPINE).add(WAICOrgans.INK_SPINE.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.INK_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.INK_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.INK_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.INK_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.INK_LIVER.get());
        tag(ItemTagManager.RIB).add(WAICOrgans.INK_RIB.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.INK_MUSCLE.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.INK_APPENDIX.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.INK_BOTTLE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.NIB.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.NIB.get());
        tag(WAICItemTagManager.MAGIC).add(WAICOrgans.NIB.get());
    }

    // ==================== 颜料器官 ====================

    private void pigmentOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.PIGMENT_HEART.get(),
            WAICOrgans.PIGMENT_LUNG.get(),
            WAICOrgans.PIGMENT_SPINE.get(),
            WAICOrgans.PIGMENT_STOMACH.get(),
            WAICOrgans.PIGMENT_INTESTINE.get(),
            WAICOrgans.PIGMENT_KIDNEY.get(),
            WAICOrgans.PIGMENT_SPLEEN.get(),
            WAICOrgans.PIGMENT_LIVER.get(),
            WAICOrgans.PIGMENT_APPENDIX.get(),
            WAICOrgans.PIGMENT_RIB.get(),
            WAICOrgans.PIGMENT_MUSCLE.get(),
            WAICOrgans.PALETTE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.PIGMENT_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.PIGMENT_LUNG.get());
        tag(ItemTagManager.SPINE).add(WAICOrgans.PIGMENT_SPINE.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.PIGMENT_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.PIGMENT_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.PIGMENT_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.PIGMENT_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.PIGMENT_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.PIGMENT_APPENDIX.get());
        tag(ItemTagManager.RIB).add(WAICOrgans.PIGMENT_RIB.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.PIGMENT_MUSCLE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.PALETTE.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.PALETTE.get());
        tag(WAICItemTagManager.MAGIC).add(WAICOrgans.PALETTE.get());
    }

    // ==================== 病变器官 ====================

    private void lesionOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.LESION_HEART.get(),
            WAICOrgans.LESION_LUNG.get(),
            WAICOrgans.LESION_STOMACH.get(),
            WAICOrgans.LESION_INTESTINE.get(),
            WAICOrgans.LESION_KIDNEY.get(),
            WAICOrgans.LESION_SPLEEN.get(),
            WAICOrgans.LESION_LIVER.get(),
            WAICOrgans.LESION_APPENDIX.get(),
            WAICOrgans.LESION_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.LESION_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.LESION_LUNG.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.LESION_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.LESION_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.LESION_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.LESION_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.LESION_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.LESION_APPENDIX.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.LESION_MUSCLE.get());
        tag(WAICItemTagManager.LESION).add(
            WAICOrgans.LESION_HEART.get(),
            WAICOrgans.LESION_LUNG.get(),
            WAICOrgans.LESION_STOMACH.get(),
            WAICOrgans.LESION_INTESTINE.get(),
            WAICOrgans.LESION_KIDNEY.get(),
            WAICOrgans.LESION_SPLEEN.get(),
            WAICOrgans.LESION_LIVER.get(),
            WAICOrgans.LESION_APPENDIX.get(),
            WAICOrgans.LESION_MUSCLE.get()
        );
    }

    // ==================== 猩红器官 ====================

    private void crimsonOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.CRIMSON_HEART.get(),
            WAICOrgans.CRIMSON_LUNG.get(),
            WAICOrgans.CRIMSON_STOMACH.get(),
            WAICOrgans.CRIMSON_INTESTINE.get(),
            WAICOrgans.CRIMSON_KIDNEY.get(),
            WAICOrgans.CRIMSON_SPLEEN.get(),
            WAICOrgans.CRIMSON_LIVER.get(),
            WAICOrgans.CRIMSON_APPENDIX.get(),
            WAICOrgans.CRIMSON_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.CRIMSON_HEART.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.CRIMSON_HEART.get());
        tag(WAICItemTagManager.MAGIC).add(WAICOrgans.CRIMSON_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.CRIMSON_LUNG.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.CRIMSON_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.CRIMSON_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.CRIMSON_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.CRIMSON_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.CRIMSON_LIVER.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.CRIMSON_LIVER.get());
        tag(WAICItemTagManager.MAGIC).add(WAICOrgans.CRIMSON_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.CRIMSON_APPENDIX.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.CRIMSON_MUSCLE.get());
    }

    // ==================== 元素器官 ====================

    private void elementOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.DIVINE_CORE.get(),
            WAICOrgans.FROST_CORE.get(),
            WAICOrgans.FLAME_CORE.get(),
            WAICOrgans.NATURE_CORE.get()
        );
        tag(WAICItemTagManager.MAGIC).add(
            WAICOrgans.DIVINE_CORE.get(),
            WAICOrgans.FROST_CORE.get(),
            WAICOrgans.FLAME_CORE.get(),
            WAICOrgans.NATURE_CORE.get()
        );
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.DIVINE_CORE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.FROST_CORE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.FLAME_CORE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.NATURE_CORE.get());
    }

    // ==================== 木质器官 ====================

    private void woodenOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.WOODEN_HEART.get(),
            WAICOrgans.WOODEN_LUNG.get(),
            WAICOrgans.WOODEN_STOMACH.get(),
            WAICOrgans.WOODEN_INTESTINE.get(),
            WAICOrgans.WOODEN_KIDNEY.get(),
            WAICOrgans.WOODEN_SPLEEN.get(),
            WAICOrgans.WOODEN_LIVER.get(),
            WAICOrgans.WOODEN_APPENDIX.get(),
            WAICOrgans.WOODEN_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.WOODEN_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.WOODEN_LUNG.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.WOODEN_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.WOODEN_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.WOODEN_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.WOODEN_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.WOODEN_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.WOODEN_APPENDIX.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.WOODEN_MUSCLE.get());
    }

    // ==================== 弗兰肯斯坦器官 ====================

    private void frankensteinOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.FRANKENSTEIN_HEART.get(),
            WAICOrgans.FRANKENSTEIN_LUNG.get(),
            WAICOrgans.FRANKENSTEIN_STOMACH.get(),
            WAICOrgans.FRANKENSTEIN_INTESTINE.get(),
            WAICOrgans.FRANKENSTEIN_KIDNEY.get(),
            WAICOrgans.FRANKENSTEIN_SPLEEN.get(),
            WAICOrgans.FRANKENSTEIN_LIVER.get(),
            WAICOrgans.FRANKENSTEIN_APPENDIX.get(),
            WAICOrgans.FRANKENSTEIN_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.FRANKENSTEIN_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.FRANKENSTEIN_LUNG.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.FRANKENSTEIN_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.FRANKENSTEIN_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.FRANKENSTEIN_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.FRANKENSTEIN_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.FRANKENSTEIN_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.FRANKENSTEIN_APPENDIX.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.FRANKENSTEIN_MUSCLE.get());
    }

    // ==================== 九狱器官 ====================

    private void nineHellOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.LIMBO.get(),
            WAICOrgans.LUST.get(),
            WAICOrgans.GLUTTONY.get(),
            WAICOrgans.GREED.get(),
            WAICOrgans.WRATH.get(),
            WAICOrgans.HERESY.get(),
            WAICOrgans.VIOLENCE.get(),
            WAICOrgans.FRAUD.get(),
            WAICOrgans.TREACHERY.get()
        );
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.LIMBO.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.LUST.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.GLUTTONY.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.GREED.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.WRATH.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.HERESY.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.VIOLENCE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.FRAUD.get());
        tag(ItemTagManager.HEART).add(WAICOrgans.TREACHERY.get());
        tag(WAICItemTagManager.NINE_HELL).add(
            WAICOrgans.LIMBO.get(),
            WAICOrgans.LUST.get(),
            WAICOrgans.GLUTTONY.get(),
            WAICOrgans.GREED.get(),
            WAICOrgans.WRATH.get(),
            WAICOrgans.HERESY.get(),
            WAICOrgans.VIOLENCE.get(),
            WAICOrgans.FRAUD.get(),
            WAICOrgans.TREACHERY.get()
        );
        tag(WAICItemTagManager.UNIQUE).add(
            WAICOrgans.GLUTTONY.get(),
            WAICOrgans.HERESY.get(),
            WAICOrgans.VIOLENCE.get(),
            WAICOrgans.FRAUD.get()
        );
    }

    // ==================== 布织器官 ====================

    private void clothOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            CompanionsOrgans.CLOTH_TEDDY_BEAR.get(),
            CompanionsOrgans.CLOTH_HEART.get(),
            CompanionsOrgans.CLOTH_LUNG.get(),
            CompanionsOrgans.CLOTH_LIVER.get(),
            CompanionsOrgans.CLOTH_INTESTINE.get(),
            CompanionsOrgans.CLOTH_STOMACH.get(),
            CompanionsOrgans.CLOTH_KIDNEY.get(),
            CompanionsOrgans.CLOTH_SPLEEN.get(),
            CompanionsOrgans.CLOTH_SPINE.get(),
            CompanionsOrgans.CLOTH_RIB.get(),
            CompanionsOrgans.CLOTH_MUSCLE.get(),
            CompanionsOrgans.CLOTH_APPENDIX.get()
        );
        tag(ItemTagManager.SPECIAL).add(CompanionsOrgans.CLOTH_TEDDY_BEAR.get());
        tag(ItemTagManager.HEART).add(CompanionsOrgans.CLOTH_HEART.get());
        tag(ItemTagManager.LUNG).add(CompanionsOrgans.CLOTH_LUNG.get());
        tag(ItemTagManager.LIVER).add(CompanionsOrgans.CLOTH_LIVER.get());
        tag(ItemTagManager.INTESTINE).add(CompanionsOrgans.CLOTH_INTESTINE.get());
        tag(ItemTagManager.STOMACH).add(CompanionsOrgans.CLOTH_STOMACH.get());
        tag(ItemTagManager.KIDNEY).add(CompanionsOrgans.CLOTH_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(CompanionsOrgans.CLOTH_SPLEEN.get());
        tag(ItemTagManager.SPINE).add(CompanionsOrgans.CLOTH_SPINE.get());
        tag(ItemTagManager.RIB).add(CompanionsOrgans.CLOTH_RIB.get());
        tag(ItemTagManager.MUSCLE).add(CompanionsOrgans.CLOTH_MUSCLE.get());
        tag(ItemTagManager.APPENDIX).add(CompanionsOrgans.CLOTH_APPENDIX.get());
        tag(WAICItemTagManager.CLOTH).add(
            CompanionsOrgans.CLOTH_TEDDY_BEAR.get(),
            CompanionsOrgans.CLOTH_HEART.get(),
            CompanionsOrgans.CLOTH_LUNG.get(),
            CompanionsOrgans.CLOTH_LIVER.get(),
            CompanionsOrgans.CLOTH_INTESTINE.get(),
            CompanionsOrgans.CLOTH_STOMACH.get(),
            CompanionsOrgans.CLOTH_KIDNEY.get(),
            CompanionsOrgans.CLOTH_SPLEEN.get(),
            CompanionsOrgans.CLOTH_SPINE.get(),
            CompanionsOrgans.CLOTH_RIB.get(),
            CompanionsOrgans.CLOTH_MUSCLE.get(),
            CompanionsOrgans.CLOTH_APPENDIX.get()
        );
    }

    // ==================== 电磁义体器官 ====================

    private void cyberneticOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.COMPUTING_CORE.get(),
            WAICOrgans.CURRENT_RIB.get(),
            WAICOrgans.CHARGED_MUSCLE.get(),
            WAICOrgans.CONDUCTIVE_SPINE.get(),
            WAICOrgans.ENERGY_MODULE.get()
        );
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.COMPUTING_CORE.get());
        tag(ItemTagManager.RIB).add(WAICOrgans.CURRENT_RIB.get());
        tag(WAICItemTagManager.UNIQUE).add(WAICOrgans.CURRENT_RIB.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.CHARGED_MUSCLE.get());
        tag(ItemTagManager.SPINE).add(WAICOrgans.CONDUCTIVE_SPINE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.ENERGY_MODULE.get());
        tag(WAICItemTagManager.MECHANICAL).add(
            WAICOrgans.COMPUTING_CORE.get(),
            WAICOrgans.CURRENT_RIB.get(),
            WAICOrgans.CHARGED_MUSCLE.get(),
            WAICOrgans.CONDUCTIVE_SPINE.get(),
            WAICOrgans.ENERGY_MODULE.get()
        );
    }

    // ==================== 悚怖器官 ====================

    private void dreadOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            IceAndFireOrgans.BITTER_FLESH.get(),
            IceAndFireOrgans.ICE_SHARD.get(),
            IceAndFireOrgans.FROSTBURN_SOUL.get(),
            IceAndFireOrgans.DREAD_PHYLACTERY.get(),
            IceAndFireOrgans.DREAD_RIB.get(),
            IceAndFireOrgans.DREAD_SPINE.get()
        );
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.BITTER_FLESH.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.ICE_SHARD.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.FROSTBURN_SOUL.get());
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.DREAD_PHYLACTERY.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.DREAD_RIB.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.DREAD_SPINE.get());
        tag(WAICItemTagManager.ICE).add(
            IceAndFireOrgans.BITTER_FLESH.get(),
            IceAndFireOrgans.ICE_SHARD.get(),
            IceAndFireOrgans.FROSTBURN_SOUL.get(),
            IceAndFireOrgans.DREAD_PHYLACTERY.get(),
            IceAndFireOrgans.DREAD_RIB.get(),
            IceAndFireOrgans.DREAD_SPINE.get()
        );
    }

    // ==================== 九头蛇器官 ====================

    private void hydraOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            IceAndFireOrgans.HYDRA_HEART.get(),
            IceAndFireOrgans.HYDRA_LUNG.get(),
            IceAndFireOrgans.HYDRA_SPINE.get(),
            IceAndFireOrgans.HYDRA_STOMACH.get(),
            IceAndFireOrgans.HYDRA_INTESTINE.get(),
            IceAndFireOrgans.HYDRA_SPLEEN.get(),
            IceAndFireOrgans.HYDRA_RIB.get(),
            IceAndFireOrgans.HYDRA_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(IceAndFireOrgans.HYDRA_HEART.get());
        tag(WAICItemTagManager.UNIQUE).add(IceAndFireOrgans.HYDRA_HEART.get());
        tag(ItemTagManager.LUNG).add(IceAndFireOrgans.HYDRA_LUNG.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.HYDRA_SPINE.get());
        tag(WAICItemTagManager.UNIQUE).add(IceAndFireOrgans.HYDRA_SPINE.get());
        tag(ItemTagManager.STOMACH).add(IceAndFireOrgans.HYDRA_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(IceAndFireOrgans.HYDRA_INTESTINE.get());
        tag(ItemTagManager.SPLEEN).add(IceAndFireOrgans.HYDRA_SPLEEN.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.HYDRA_RIB.get());
        tag(WAICItemTagManager.UNIQUE).add(IceAndFireOrgans.HYDRA_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.HYDRA_MUSCLE.get());
        tag(WAICItemTagManager.UNIQUE).add(IceAndFireOrgans.HYDRA_MUSCLE.get());
    }

    // ==================== FDBosses 逆卡巴拉器官 ====================

    private void fdBossesOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get(),
            FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get(),
            FDBossesOrgans.MALKUTH.get(),
            FDBossesOrgans.CHESED.get(),
            FDBossesOrgans.GEBURAH.get()
        );
        tag(ItemTagManager.HEART).add(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get());
        tag(WAICItemTagManager.UNIQUE).add(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get());
        tag(ItemTagManager.HEART).add(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get());
        tag(WAICItemTagManager.UNIQUE).add(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get());
        tag(ItemTagManager.SPECIAL).add(FDBossesOrgans.MALKUTH.get());
        tag(ItemTagManager.SPECIAL).add(FDBossesOrgans.CHESED.get());
        tag(ItemTagManager.SPECIAL).add(FDBossesOrgans.GEBURAH.get());
        tag(WAICItemTagManager.FIRE).add(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get());
        tag(WAICItemTagManager.ICE).add(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get());
        tag(WAICItemTagManager.QLIPHOTH).add(
            FDBossesOrgans.MALKUTH.get(),
            FDBossesOrgans.CHESED.get(),
            FDBossesOrgans.GEBURAH.get()
        );
    }

    // ==================== AnvilCraft 皇家钢器官 ====================

    private void royalSteelOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            AnvilCraftOrgans.ROYAL_STEEL_RIB.get(),
            AnvilCraftOrgans.ROYAL_STEEL_MUSCLE.get(),
            AnvilCraftOrgans.ROYAL_STEEL_SPINE.get(),
            AnvilCraftOrgans.ROYAL_STEEL_APPENDIX.get()
        );
        tag(ItemTagManager.RIB).add(AnvilCraftOrgans.ROYAL_STEEL_RIB.get());
        tag(ItemTagManager.MUSCLE).add(AnvilCraftOrgans.ROYAL_STEEL_MUSCLE.get());
        tag(ItemTagManager.SPINE).add(AnvilCraftOrgans.ROYAL_STEEL_SPINE.get());
        tag(ItemTagManager.APPENDIX).add(AnvilCraftOrgans.ROYAL_STEEL_APPENDIX.get());
    }

    // ==================== AnvilCraft 诅咒金器官 ====================

    private void cursedGoldOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            AnvilCraftOrgans.CURSED_GOLD_HEART.get(),
            AnvilCraftOrgans.CURSED_GOLD_LUNG.get(),
            AnvilCraftOrgans.CURSED_GOLD_LIVER.get(),
            AnvilCraftOrgans.CURSED_GOLD_INTESTINE.get()
        );
        tag(ItemTagManager.HEART).add(AnvilCraftOrgans.CURSED_GOLD_HEART.get());
        tag(ItemTagManager.LUNG).add(AnvilCraftOrgans.CURSED_GOLD_LUNG.get());
        tag(ItemTagManager.LIVER).add(AnvilCraftOrgans.CURSED_GOLD_LIVER.get());
        tag(ItemTagManager.INTESTINE).add(AnvilCraftOrgans.CURSED_GOLD_INTESTINE.get());
        tag(WAICItemTagManager.CURSED).add(
            AnvilCraftOrgans.CURSED_GOLD_HEART.get(),
            AnvilCraftOrgans.CURSED_GOLD_LUNG.get(),
            AnvilCraftOrgans.CURSED_GOLD_LIVER.get(),
            AnvilCraftOrgans.CURSED_GOLD_INTESTINE.get()
        );
    }

    // ==================== AnvilCraft 余烬金属器官 ====================

    private void emberMetalOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            AnvilCraftOrgans.EMBER_METAL_RIB.get(),
            AnvilCraftOrgans.EMBER_METAL_MUSCLE.get(),
            AnvilCraftOrgans.EMBER_METAL_SPINE.get(),
            AnvilCraftOrgans.EMBER_METAL_APPENDIX.get()
        );
        tag(ItemTagManager.RIB).add(AnvilCraftOrgans.EMBER_METAL_RIB.get());
        tag(ItemTagManager.MUSCLE).add(AnvilCraftOrgans.EMBER_METAL_MUSCLE.get());
        tag(ItemTagManager.SPINE).add(AnvilCraftOrgans.EMBER_METAL_SPINE.get());
        tag(ItemTagManager.APPENDIX).add(AnvilCraftOrgans.EMBER_METAL_APPENDIX.get());
        tag(WAICItemTagManager.EMBER).add(
            AnvilCraftOrgans.EMBER_METAL_RIB.get(),
            AnvilCraftOrgans.EMBER_METAL_MUSCLE.get(),
            AnvilCraftOrgans.EMBER_METAL_SPINE.get(),
            AnvilCraftOrgans.EMBER_METAL_APPENDIX.get()
        );
        tag(WAICItemTagManager.FIRE).add(
            AnvilCraftOrgans.EMBER_METAL_RIB.get(),
            AnvilCraftOrgans.EMBER_METAL_MUSCLE.get(),
            AnvilCraftOrgans.EMBER_METAL_SPINE.get(),
            AnvilCraftOrgans.EMBER_METAL_APPENDIX.get()
        );
    }

    // ==================== AnvilCraft 浮霜金属器官 ====================

    private void frostMetalOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            AnvilCraftOrgans.FROST_METAL_HEART.get(),
            AnvilCraftOrgans.FROST_METAL_LUNG.get(),
            AnvilCraftOrgans.FROST_METAL_SPINE.get(),
            AnvilCraftOrgans.FROST_METAL_STOMACH.get(),
            AnvilCraftOrgans.FROST_METAL_INTESTINE.get(),
            AnvilCraftOrgans.FROST_METAL_KIDNEY.get(),
            AnvilCraftOrgans.FROST_METAL_SPLEEN.get(),
            AnvilCraftOrgans.FROST_METAL_LIVER.get(),
            AnvilCraftOrgans.FROST_METAL_APPENDIX.get(),
            AnvilCraftOrgans.FROST_METAL_RIB.get(),
            AnvilCraftOrgans.FROST_METAL_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(AnvilCraftOrgans.FROST_METAL_HEART.get());
        tag(ItemTagManager.LUNG).add(AnvilCraftOrgans.FROST_METAL_LUNG.get());
        tag(ItemTagManager.SPINE).add(AnvilCraftOrgans.FROST_METAL_SPINE.get());
        tag(ItemTagManager.STOMACH).add(AnvilCraftOrgans.FROST_METAL_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(AnvilCraftOrgans.FROST_METAL_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(AnvilCraftOrgans.FROST_METAL_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(AnvilCraftOrgans.FROST_METAL_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(AnvilCraftOrgans.FROST_METAL_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(AnvilCraftOrgans.FROST_METAL_APPENDIX.get());
        tag(ItemTagManager.RIB).add(AnvilCraftOrgans.FROST_METAL_RIB.get());
        tag(ItemTagManager.MUSCLE).add(AnvilCraftOrgans.FROST_METAL_MUSCLE.get());
        tag(WAICItemTagManager.ICE).add(
            AnvilCraftOrgans.FROST_METAL_HEART.get(),
            AnvilCraftOrgans.FROST_METAL_LUNG.get(),
            AnvilCraftOrgans.FROST_METAL_SPINE.get(),
            AnvilCraftOrgans.FROST_METAL_STOMACH.get(),
            AnvilCraftOrgans.FROST_METAL_INTESTINE.get(),
            AnvilCraftOrgans.FROST_METAL_KIDNEY.get(),
            AnvilCraftOrgans.FROST_METAL_SPLEEN.get(),
            AnvilCraftOrgans.FROST_METAL_LIVER.get(),
            AnvilCraftOrgans.FROST_METAL_APPENDIX.get(),
            AnvilCraftOrgans.FROST_METAL_RIB.get(),
            AnvilCraftOrgans.FROST_METAL_MUSCLE.get()
        );
    }

    // ==================== AnvilCraft 超限合金器官 ====================

    private void transcendiumOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            AnvilCraftOrgans.TRANSCENDIUM_HEART.get(),
            AnvilCraftOrgans.TRANSCENDIUM_LUNG.get(),
            AnvilCraftOrgans.TRANSCENDIUM_SPINE.get(),
            AnvilCraftOrgans.TRANSCENDIUM_STOMACH.get(),
            AnvilCraftOrgans.TRANSCENDIUM_INTESTINE.get(),
            AnvilCraftOrgans.TRANSCENDIUM_KIDNEY.get(),
            AnvilCraftOrgans.TRANSCENDIUM_SPLEEN.get(),
            AnvilCraftOrgans.TRANSCENDIUM_LIVER.get(),
            AnvilCraftOrgans.TRANSCENDIUM_APPENDIX.get(),
            AnvilCraftOrgans.TRANSCENDIUM_RIB.get(),
            AnvilCraftOrgans.TRANSCENDIUM_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(AnvilCraftOrgans.TRANSCENDIUM_HEART.get());
        tag(ItemTagManager.LUNG).add(AnvilCraftOrgans.TRANSCENDIUM_LUNG.get());
        tag(ItemTagManager.SPINE).add(AnvilCraftOrgans.TRANSCENDIUM_SPINE.get());
        tag(ItemTagManager.STOMACH).add(AnvilCraftOrgans.TRANSCENDIUM_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(AnvilCraftOrgans.TRANSCENDIUM_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(AnvilCraftOrgans.TRANSCENDIUM_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(AnvilCraftOrgans.TRANSCENDIUM_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(AnvilCraftOrgans.TRANSCENDIUM_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(AnvilCraftOrgans.TRANSCENDIUM_APPENDIX.get());
        tag(ItemTagManager.RIB).add(AnvilCraftOrgans.TRANSCENDIUM_RIB.get());
        tag(ItemTagManager.MUSCLE).add(AnvilCraftOrgans.TRANSCENDIUM_MUSCLE.get());
    }

    // ==================== 幻想种器官 ====================

    private void fantasticalOrgansTags() {
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.FANTASTICAL_HEART.get(),
            WAICOrgans.FANTASTICAL_LUNG.get(),
            WAICOrgans.FANTASTICAL_SPINE.get(),
            WAICOrgans.FANTASTICAL_STOMACH.get(),
            WAICOrgans.FANTASTICAL_INTESTINE.get(),
            WAICOrgans.FANTASTICAL_KIDNEY.get(),
            WAICOrgans.FANTASTICAL_SPLEEN.get(),
            WAICOrgans.FANTASTICAL_LIVER.get(),
            WAICOrgans.FANTASTICAL_APPENDIX.get(),
            WAICOrgans.FANTASTICAL_RIB.get(),
            WAICOrgans.FANTASTICAL_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.FANTASTICAL_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.FANTASTICAL_LUNG.get());
        tag(ItemTagManager.SPINE).add(WAICOrgans.FANTASTICAL_SPINE.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.FANTASTICAL_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.FANTASTICAL_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.FANTASTICAL_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.FANTASTICAL_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.FANTASTICAL_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.FANTASTICAL_APPENDIX.get());
        tag(ItemTagManager.RIB).add(WAICOrgans.FANTASTICAL_RIB.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.FANTASTICAL_MUSCLE.get());
    }

    // ==================== Cataclysm 器官 ====================

    private void cataclysmOrgansTags() {
        // 利维坦系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.LEVIATHAN_HEART.get(),
            CataclysmOrgans.LEVIATHAN_MUSCLE.get(),
            CataclysmOrgans.LEVIATHAN_INTESTINE.get(),
            CataclysmOrgans.LEVIATHAN_STOMACH.get(),
            CataclysmOrgans.LEVIATHAN_GILL.get(),
            CataclysmOrgans.LEVIATHAN_SPINE.get(),
            CataclysmOrgans.LEVIATHAN_FISHBONE.get()
        );
        tag(ItemTagManager.HEART).add(CataclysmOrgans.LEVIATHAN_HEART.get());
        tag(ItemTagManager.MUSCLE).add(CataclysmOrgans.LEVIATHAN_MUSCLE.get());
        tag(ItemTagManager.INTESTINE).add(CataclysmOrgans.LEVIATHAN_INTESTINE.get());
        tag(ItemTagManager.STOMACH).add(CataclysmOrgans.LEVIATHAN_STOMACH.get());
        tag(ItemTagManager.LUNG).add(CataclysmOrgans.LEVIATHAN_GILL.get());
        tag(ItemTagManager.SPINE).add(CataclysmOrgans.LEVIATHAN_SPINE.get());
        tag(ItemTagManager.RIB).add(CataclysmOrgans.LEVIATHAN_FISHBONE.get());

        // 魂尸系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.DRAUGR_SPINE.get(),
            CataclysmOrgans.DRAUGR_RIB.get()
        );
        tag(ItemTagManager.SPINE).add(CataclysmOrgans.DRAUGR_SPINE.get());
        tag(ItemTagManager.RIB).add(CataclysmOrgans.DRAUGR_RIB.get());

        // 咒翼灵骸系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.MALEDICTUS_SPINE.get(),
            CataclysmOrgans.MALEDICTUS_RIB.get(),
            CataclysmOrgans.PHANTOM_HEART.get(),
            CataclysmOrgans.PHANTOM_SHARD.get(),
            CataclysmOrgans.SEALING_STONE_SLAB.get()
        );
        tag(ItemTagManager.SPINE).add(CataclysmOrgans.MALEDICTUS_SPINE.get());
        tag(ItemTagManager.RIB).add(CataclysmOrgans.MALEDICTUS_RIB.get());
        tag(ItemTagManager.HEART).add(CataclysmOrgans.PHANTOM_HEART.get());
        tag(ItemTagManager.SPECIAL).add(CataclysmOrgans.PHANTOM_SHARD.get());
        tag(ItemTagManager.SPECIAL).add(CataclysmOrgans.SEALING_STONE_SLAB.get());
        tag(WAICItemTagManager.ICE).add(
            CataclysmOrgans.PHANTOM_HEART.get(),
            CataclysmOrgans.PHANTOM_SHARD.get()
        );

        // 斯库拉系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.TIDAL_LANTERN.get(),
            CataclysmOrgans.STORM_SPINE.get(),
            CataclysmOrgans.STORM_RIB.get()
        );
        tag(ItemTagManager.SPECIAL).add(CataclysmOrgans.TIDAL_LANTERN.get());
        tag(ItemTagManager.SPINE).add(CataclysmOrgans.STORM_SPINE.get());
        tag(WAICItemTagManager.UNIQUE).add(CataclysmOrgans.STORM_SPINE.get());
        tag(ItemTagManager.RIB).add(CataclysmOrgans.STORM_RIB.get());

        // 焰魔系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.UNDYING_EMBER.get(),
            CataclysmOrgans.IGNITED_RIB_PLATING.get(),
            CataclysmOrgans.BLAZING_VISAGE.get()
        );
        tag(ItemTagManager.HEART).add(CataclysmOrgans.UNDYING_EMBER.get());
        tag(ItemTagManager.RIB).add(CataclysmOrgans.IGNITED_RIB_PLATING.get());
        tag(ItemTagManager.SPECIAL).add(CataclysmOrgans.BLAZING_VISAGE.get());
        tag(WAICItemTagManager.FIRE).add(
            CataclysmOrgans.UNDYING_EMBER.get(),
            CataclysmOrgans.IGNITED_RIB_PLATING.get(),
            CataclysmOrgans.BLAZING_VISAGE.get()
        );

        // 下界合金巨兽系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.MONSTROSITY_CORE.get(),
            CataclysmOrgans.MONSTROSITY_CIRCUIT.get(),
            CataclysmOrgans.MONSTROSITY_FURNACE.get()
        );
        tag(ItemTagManager.HEART).add(CataclysmOrgans.MONSTROSITY_CORE.get());
        tag(ItemTagManager.SPINE).add(CataclysmOrgans.MONSTROSITY_CIRCUIT.get());
        tag(ItemTagManager.STOMACH).add(CataclysmOrgans.MONSTROSITY_FURNACE.get());
        tag(WAICItemTagManager.UNIQUE).add(CataclysmOrgans.MONSTROSITY_FURNACE.get());
        tag(WAICItemTagManager.FIRE).add(
            CataclysmOrgans.MONSTROSITY_CORE.get(),
            CataclysmOrgans.MONSTROSITY_CIRCUIT.get(),
            CataclysmOrgans.MONSTROSITY_FURNACE.get()
        );
        tag(WAICItemTagManager.MONSTROSITY).add(
            CataclysmOrgans.MONSTROSITY_CORE.get(),
            CataclysmOrgans.MONSTROSITY_CIRCUIT.get(),
            CataclysmOrgans.MONSTROSITY_FURNACE.get()
        );
        tag(WAICItemTagManager.MECHANICAL).add(
            CataclysmOrgans.MONSTROSITY_CORE.get(),
            CataclysmOrgans.MONSTROSITY_CIRCUIT.get(),
            CataclysmOrgans.MONSTROSITY_FURNACE.get()
        );

        // 远古工厂系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.TACTICAL_DISK.get(),
            CataclysmOrgans.REINFORCED_FRAME.get(),
            CataclysmOrgans.POWER_CELL.get(),
            CataclysmOrgans.COMPUTE_CHIP.get(),
            CataclysmOrgans.MECHANICAL_STAR.get(),
            CataclysmOrgans.DEATH_LENS.get()
        );
        tag(ItemTagManager.SPECIAL).add(
            CataclysmOrgans.TACTICAL_DISK.get(),
            CataclysmOrgans.REINFORCED_FRAME.get(),
            CataclysmOrgans.POWER_CELL.get(),
            CataclysmOrgans.COMPUTE_CHIP.get(),
            CataclysmOrgans.MECHANICAL_STAR.get(),
            CataclysmOrgans.DEATH_LENS.get()
        );
        tag(WAICItemTagManager.MECHANICAL).add(
            CataclysmOrgans.TACTICAL_DISK.get(),
            CataclysmOrgans.REINFORCED_FRAME.get(),
            CataclysmOrgans.POWER_CELL.get(),
            CataclysmOrgans.COMPUTE_CHIP.get(),
            CataclysmOrgans.MECHANICAL_STAR.get(),
            CataclysmOrgans.DEATH_LENS.get()
        );

        // 末影守卫系列
        tag(ItemTagManager.ORGANS).add(
            CataclysmOrgans.GUARDIAN_STONE.get(),
            CataclysmOrgans.VOID_CRYSTAL_SPINE.get()
        );
        tag(ItemTagManager.SPECIAL).add(
            CataclysmOrgans.GUARDIAN_STONE.get(),
            CataclysmOrgans.VOID_CRYSTAL_SPINE.get()
        );
        tag(WAICItemTagManager.MECHANICAL).add(
            CataclysmOrgans.GUARDIAN_STONE.get(),
            CataclysmOrgans.VOID_CRYSTAL_SPINE.get()
        );
    }

    // ==================== IronSpell 器官 ====================

    private void ironSpellOrgansTags() {
        // 死灵法师
        tag(ItemTagManager.ORGANS).add(
            IronSpellOrgans.NECROMANCER_SPINE.get(),
            IronSpellOrgans.NECROMANCER_RIB.get()
        );
        tag(ItemTagManager.SPINE).add(IronSpellOrgans.NECROMANCER_SPINE.get());
        tag(ItemTagManager.RIB).add(IronSpellOrgans.NECROMANCER_RIB.get());

        // 原初受火者
        tag(ItemTagManager.ORGANS).add(IronSpellOrgans.PRIMORDIAL_FLAME.get());
        tag(ItemTagManager.SPECIAL).add(IronSpellOrgans.PRIMORDIAL_FLAME.get());
        tag(WAICItemTagManager.MAGIC).add(IronSpellOrgans.PRIMORDIAL_FLAME.get());
        tag(WAICItemTagManager.FIRE).add(IronSpellOrgans.PRIMORDIAL_FLAME.get());
        tag(WAICItemTagManager.UNIQUE).add(IronSpellOrgans.PRIMORDIAL_FLAME.get());

        // 高位唤魔者
        tag(ItemTagManager.ORGANS).add(IronSpellOrgans.EMERALD_SKULL.get());
        tag(ItemTagManager.SPINE).add(IronSpellOrgans.EMERALD_SKULL.get());
        tag(WAICItemTagManager.MAGIC).add(IronSpellOrgans.EMERALD_SKULL.get());
        tag(WAICItemTagManager.UNIQUE).add(IronSpellOrgans.EMERALD_SKULL.get());

        // 死者之王
        tag(ItemTagManager.ORGANS).add(
            IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get(),
            IronSpellOrgans.DEAD_KING_SPINE.get(),
            IronSpellOrgans.DEAD_KING_RIB.get()
        );
        tag(ItemTagManager.SPECIAL).add(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get());
        tag(WAICItemTagManager.UNIQUE).add(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get());
        tag(WAICItemTagManager.MAGIC).add(IronSpellOrgans.CORRUPTED_SOUL_LANTERN.get());
        tag(ItemTagManager.SPINE).add(IronSpellOrgans.DEAD_KING_SPINE.get());
        tag(WAICItemTagManager.UNIQUE).add(IronSpellOrgans.DEAD_KING_SPINE.get());
        tag(WAICItemTagManager.MAGIC).add(IronSpellOrgans.DEAD_KING_SPINE.get());
        tag(ItemTagManager.RIB).add(IronSpellOrgans.DEAD_KING_RIB.get());
        tag(WAICItemTagManager.MAGIC).add(IronSpellOrgans.DEAD_KING_RIB.get());
    }

    // ==================== Companions 器官 ====================

    private void companionsOrgansTags() {
        // 教宗系列
        tag(ItemTagManager.ORGANS).add(
            CompanionsOrgans.PONTIFF_HEART.get(),
            CompanionsOrgans.PONTIFF_LUNG.get(),
            CompanionsOrgans.PONTIFF_STOMACH.get(),
            CompanionsOrgans.PONTIFF_INTESTINE.get(),
            CompanionsOrgans.PONTIFF_KIDNEY.get(),
            CompanionsOrgans.PONTIFF_SPLEEN.get(),
            CompanionsOrgans.PONTIFF_LIVER.get(),
            CompanionsOrgans.PONTIFF_APPENDIX.get(),
            CompanionsOrgans.PONTIFF_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(CompanionsOrgans.PONTIFF_HEART.get());
        tag(ItemTagManager.LUNG).add(CompanionsOrgans.PONTIFF_LUNG.get());
        tag(ItemTagManager.STOMACH).add(CompanionsOrgans.PONTIFF_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(CompanionsOrgans.PONTIFF_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(CompanionsOrgans.PONTIFF_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(CompanionsOrgans.PONTIFF_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(CompanionsOrgans.PONTIFF_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(CompanionsOrgans.PONTIFF_APPENDIX.get());
        tag(ItemTagManager.MUSCLE).add(CompanionsOrgans.PONTIFF_MUSCLE.get());
        // 蛋糕系列
        tag(ItemTagManager.ORGANS).add(
            CompanionsOrgans.CAKE_HEART.get(),
            CompanionsOrgans.CAKE_LUNG.get(),
            CompanionsOrgans.CAKE_STOMACH.get(),
            CompanionsOrgans.CAKE_LIVER.get()
        );
        tag(ItemTagManager.HEART).add(CompanionsOrgans.CAKE_HEART.get());
        tag(ItemTagManager.LUNG).add(CompanionsOrgans.CAKE_LUNG.get());
        tag(ItemTagManager.STOMACH).add(CompanionsOrgans.CAKE_STOMACH.get());
        tag(WAICItemTagManager.UNIQUE).add(CompanionsOrgans.CAKE_STOMACH.get());
        tag(ItemTagManager.LIVER).add(CompanionsOrgans.CAKE_LIVER.get());
        tag(WAICItemTagManager.UNIQUE).add(CompanionsOrgans.CAKE_LIVER.get());
        tag(WAICItemTagManager.CAKE).add(
            CompanionsOrgans.CAKE_HEART.get(),
            CompanionsOrgans.CAKE_LUNG.get(),
            CompanionsOrgans.CAKE_STOMACH.get(),
            CompanionsOrgans.CAKE_LIVER.get()
        );
    }
}

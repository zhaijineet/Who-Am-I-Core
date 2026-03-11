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
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends IntrinsicHolderTagsProvider<Item> {
    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key(), WhoAmICore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // 胸中新星器官标签
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.SUMMON).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.LEGEND).add(MowziesMobOrgans.CHEST_NOVA.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.CHEST_NOVA.get());

        // 制御棒器官标签
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.MECHANICAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.LEGEND).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.CONTROL_ROD.get());

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

        // 为每个衰老器官添加对应的类型标签
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

        // 禅心器官标签
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.ZEN_HEART.get());
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.ZEN_HEART.get());

        // 泥峭核心
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.BLUFF_CORE.get());
        tag(ItemTagManager.HEART).add(MowziesMobOrgans.BLUFF_CORE.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.BLUFF_CORE.get());

        // 泥峭铭文板
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.BLUFF_TABLET.get());

        // 活性泥峭棒
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());

        // ==================== 火龙器官标签 ====================
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
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.FIRE_DRAGON_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get());

        // ==================== 冰龙器官标签 ====================
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
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.ICE_DRAGON_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.ICE_DRAGON_MUSCLE.get());

        // ==================== 电龙器官标签 ====================
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
        tag(ItemTagManager.SPECIAL).add(IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get());
    }
}

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
        tag(WAICItemTagManager.LEGEND).add(MowziesMobOrgans.CONTROL_ROD.get());

        // 制御棒器官标签
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.MECHANICAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.LEGEND).add(MowziesMobOrgans.CONTROL_ROD.get());

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
        tag(WAICItemTagManager.BLUFF).add(MowziesMobOrgans.BLUFF_CORE.get());

        // 泥峭铭文板
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.BLUFF_TABLET.get());
        tag(WAICItemTagManager.BLUFF).add(MowziesMobOrgans.BLUFF_TABLET.get());

        // 活性泥峭棒
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(WAICItemTagManager.MAGIC).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
        tag(WAICItemTagManager.BLUFF).add(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get());
    }
}

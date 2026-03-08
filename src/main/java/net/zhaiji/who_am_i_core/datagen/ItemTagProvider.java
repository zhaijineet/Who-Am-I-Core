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
    }
}

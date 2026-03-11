package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.function.Supplier;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WhoAmICore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        WAICItem.ITEM.getEntries().stream().map(Supplier::get).forEach(this::basicItem);
    }
}

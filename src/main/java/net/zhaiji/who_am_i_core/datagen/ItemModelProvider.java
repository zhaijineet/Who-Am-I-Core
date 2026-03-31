package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import net.zhaiji.who_am_i_core.register.WAICItem;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WhoAmICore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (var entry : WAICItem.ITEM.getEntries()) {
            var item = entry.get();
            if (item == WAICOrgans.FRANKENSTEIN_HEART.get()) {
                // 先生成 active 和 super_active 的基础模型（这样 getExistingFile 不会报错）
                basicItem(ResourceLocation.fromNamespaceAndPath(WhoAmICore.MOD_ID, "frankenstein_heart_active"));
                basicItem(ResourceLocation.fromNamespaceAndPath(WhoAmICore.MOD_ID, "frankenstein_heart_super_active"));

                // 弗兰肯斯坦心脏：带 overrides 的模型
                getBuilder("frankenstein_heart")
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", modLoc("item/frankenstein_heart"))
                    // 激活
                    .override()
                    .predicate(WhoAmICore.of("frankenstein_heart_active"), 1.0F)
                    .model(getExistingFile(modLoc("item/frankenstein_heart_active")))
                    .end()
                    // 超级激活
                    .override()
                    .predicate(WhoAmICore.of("frankenstein_heart_super_active"), 1.0F)
                    .model(getExistingFile(modLoc("item/frankenstein_heart_super_active")))
                    .end();
            } else {
                basicItem(item);
            }
        }
    }
}

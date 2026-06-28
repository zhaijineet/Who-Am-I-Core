package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.register.WAICDamageType;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = WhoAmICore.MOD_ID)
public class DataGenHandler {
    @SubscribeEvent
    public static void handlerGatherDataEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Datapack 内建注册（伤害类型等）
        DatapackBuiltinEntriesProvider datapackProvider = createDatapackBuiltinEntriesProvider(packOutput, lookupProvider);
        generator.addProvider(event.includeServer(), datapackProvider);
        lookupProvider = datapackProvider.getRegistryProvider();

        // 配方（单一入口，内部分派到各模组 Provider）
        generator.addProvider(event.includeServer(), new RecipeProvider(packOutput, lookupProvider));

        // 标签
        generator.addProvider(event.includeServer(), new ItemTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new DamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

        // 客户端
        generator.addProvider(event.includeClient(), new ItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new LanguageProvider(packOutput, LanguageProvider.EN_US));
        generator.addProvider(event.includeClient(), new LanguageProvider(packOutput, LanguageProvider.ZH_CN));
    }

    private static DatapackBuiltinEntriesProvider createDatapackBuiltinEntriesProvider(
        PackOutput packOutput,
        CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, WAICDamageType::bootstrap);
        return new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, registrySetBuilder, Set.of(WhoAmICore.MOD_ID));
    }
}

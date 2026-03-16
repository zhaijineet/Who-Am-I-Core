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
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
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
        tag(WAICItemTagManager.UNIQUE).add(MowziesMobOrgans.CHEST_NOVA.get());

        // 制御棒器官标签
        tag(ItemTagManager.ORGANS).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(ItemTagManager.SPECIAL).add(MowziesMobOrgans.CONTROL_ROD.get());
        tag(WAICItemTagManager.MECHANICAL).add(MowziesMobOrgans.CONTROL_ROD.get());
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

        // ==================== FDBosses器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get(),
            FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get(),
            FDBossesOrgans.MALKUTH.get(),
            FDBossesOrgans.CHESED.get(),
            FDBossesOrgans.GEBURAH.get()
        );
        tag(ItemTagManager.HEART).add(FDBossesOrgans.FIRE_MALKUTH_WARRIOR_HEART.get());
        tag(ItemTagManager.HEART).add(FDBossesOrgans.ICE_MALKUTH_WARRIOR_HEART.get());
        tag(ItemTagManager.SPECIAL).add(FDBossesOrgans.MALKUTH.get());
        tag(ItemTagManager.SPECIAL).add(FDBossesOrgans.CHESED.get());
        tag(ItemTagManager.SPECIAL).add(FDBossesOrgans.GEBURAH.get());

        // ==================== WAIC核心器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.DIVINE_CORE.get(),
            WAICOrgans.FROST_CORE.get(),
            WAICOrgans.FLAME_CORE.get(),
            WAICOrgans.NATURE_CORE.get()
        );
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.DIVINE_CORE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.FROST_CORE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.FLAME_CORE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.NATURE_CORE.get());

        // ==================== 悚恐怖官标签 ====================
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

        // ==================== 九头蛇器官标签 ====================
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
        tag(ItemTagManager.LUNG).add(IceAndFireOrgans.HYDRA_LUNG.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.HYDRA_SPINE.get());
        tag(ItemTagManager.STOMACH).add(IceAndFireOrgans.HYDRA_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(IceAndFireOrgans.HYDRA_INTESTINE.get());
        tag(ItemTagManager.SPLEEN).add(IceAndFireOrgans.HYDRA_SPLEEN.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.HYDRA_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.HYDRA_MUSCLE.get());

        // 火龙器官聚合标签（用于计算火龙吐息阶段）
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

        // 冰龙器官聚合标签（用于计算冰龙吐息阶段）
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

        // 电龙器官聚合标签（用于计算电龙吐息阶段）
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

        // ==================== 幻想种器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            IceAndFireOrgans.FANTASTICAL_HEART.get(),
            IceAndFireOrgans.FANTASTICAL_LUNG.get(),
            IceAndFireOrgans.FANTASTICAL_SPINE.get(),
            IceAndFireOrgans.FANTASTICAL_STOMACH.get(),
            IceAndFireOrgans.FANTASTICAL_INTESTINE.get(),
            IceAndFireOrgans.FANTASTICAL_KIDNEY.get(),
            IceAndFireOrgans.FANTASTICAL_SPLEEN.get(),
            IceAndFireOrgans.FANTASTICAL_LIVER.get(),
            IceAndFireOrgans.FANTASTICAL_APPENDIX.get(),
            IceAndFireOrgans.FANTASTICAL_RIB.get(),
            IceAndFireOrgans.FANTASTICAL_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(IceAndFireOrgans.FANTASTICAL_HEART.get());
        tag(ItemTagManager.LUNG).add(IceAndFireOrgans.FANTASTICAL_LUNG.get());
        tag(ItemTagManager.SPINE).add(IceAndFireOrgans.FANTASTICAL_SPINE.get());
        tag(ItemTagManager.STOMACH).add(IceAndFireOrgans.FANTASTICAL_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(IceAndFireOrgans.FANTASTICAL_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(IceAndFireOrgans.FANTASTICAL_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(IceAndFireOrgans.FANTASTICAL_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(IceAndFireOrgans.FANTASTICAL_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(IceAndFireOrgans.FANTASTICAL_APPENDIX.get());
        tag(ItemTagManager.RIB).add(IceAndFireOrgans.FANTASTICAL_RIB.get());
        tag(ItemTagManager.MUSCLE).add(IceAndFireOrgans.FANTASTICAL_MUSCLE.get());

        // ==================== 浮霜器官标签 ====================
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

        // ==================== 墨水器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.INK_HEART.get(),
            WAICOrgans.INK_LUNG.get(),
            WAICOrgans.INK_SPINE.get(),
            WAICOrgans.INK_STOMACH.get(),
            WAICOrgans.INK_INTESTINE.get(),
            WAICOrgans.INK_KIDNEY.get(),
            WAICOrgans.INK_SPLEEN.get(),
            WAICOrgans.INK_LIVER.get(),
            WAICOrgans.INK_APPENDIX.get(),
            WAICOrgans.INK_RIB.get(),
            WAICOrgans.INK_MUSCLE.get(),
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
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.INK_APPENDIX.get());
        tag(ItemTagManager.RIB).add(WAICOrgans.INK_RIB.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.INK_MUSCLE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.INK_BOTTLE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.NIB.get());

        // ==================== 颜料器官标签 ====================
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

        // ==================== 木质器官标签 ====================
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

        // ==================== 弗兰肯斯坦器官标签 ====================
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

        // ==================== 肿瘤器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.TUMOR_HEART.get(),
            WAICOrgans.TUMOR_LUNG.get(),
            WAICOrgans.TUMOR_STOMACH.get(),
            WAICOrgans.TUMOR_INTESTINE.get(),
            WAICOrgans.TUMOR_KIDNEY.get(),
            WAICOrgans.TUMOR_SPLEEN.get(),
            WAICOrgans.TUMOR_LIVER.get(),
            WAICOrgans.TUMOR_APPENDIX.get(),
            WAICOrgans.TUMOR_MUSCLE.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.TUMOR_HEART.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.TUMOR_LUNG.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.TUMOR_STOMACH.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.TUMOR_INTESTINE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.TUMOR_KIDNEY.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.TUMOR_SPLEEN.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.TUMOR_LIVER.get());
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.TUMOR_APPENDIX.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.TUMOR_MUSCLE.get());

        // ==================== 九狱器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.LIMBO.get(),
            WAICOrgans.LUST.get(),
            WAICOrgans.GLUTTONY.get(),
            WAICOrgans.GREED.get(),
            WAICOrgans.WRATH.get(),
            WAICOrgans.HERESY.get(),
            WAICOrgans.VIOLENCE.get(),
            WAICOrgans.FRAUD.get(),
            WAICOrgans.TREASON.get()
        );
        tag(ItemTagManager.APPENDIX).add(WAICOrgans.LIMBO.get());
        tag(ItemTagManager.INTESTINE).add(WAICOrgans.LUST.get());
        tag(ItemTagManager.STOMACH).add(WAICOrgans.GLUTTONY.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.GREED.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.WRATH.get());
        tag(ItemTagManager.SPLEEN).add(WAICOrgans.HERESY.get());
        tag(ItemTagManager.MUSCLE).add(WAICOrgans.VIOLENCE.get());
        tag(ItemTagManager.KIDNEY).add(WAICOrgans.FRAUD.get());
        tag(ItemTagManager.HEART).add(WAICOrgans.TREASON.get());

        // ==================== 双子魔眼器官标签 ====================
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

        // ==================== 拟态器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.MIMIC_HEART.get(),
            WAICOrgans.MIMIC_LIVER.get(),
            WAICOrgans.MIMIC_LUNG.get()
        );
        tag(ItemTagManager.HEART).add(WAICOrgans.MIMIC_HEART.get());
        tag(ItemTagManager.LIVER).add(WAICOrgans.MIMIC_LIVER.get());
        tag(ItemTagManager.LUNG).add(WAICOrgans.MIMIC_LUNG.get());

        // ==================== 单个器官标签 ====================
        tag(ItemTagManager.ORGANS).add(
            WAICOrgans.HAUNTED_BONE.get(),
            WAICOrgans.SWORD_BONE.get(),
            WAICOrgans.STRAIGHT_INTESTINE.get(),
            WAICOrgans.SQUASH.get()
        );
        tag(ItemTagManager.RIB).add(WAICOrgans.HAUNTED_BONE.get());
        tag(ItemTagManager.SPINE).add(WAICOrgans.SWORD_BONE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.STRAIGHT_INTESTINE.get());
        tag(ItemTagManager.SPECIAL).add(WAICOrgans.SQUASH.get());
    }
}

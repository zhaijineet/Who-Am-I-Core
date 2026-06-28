package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.manager.WAICDamageTagManager;
import net.zhaiji.who_am_i_core.register.WAICDamageType;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * 伤害类型标签数据生成器
 */
public class DamageTypeTagsProvider extends TagsProvider<DamageType> {
    public DamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, WhoAmICore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // 近战伤害标签
        // 包含所有属于近战攻击类型的伤害
        this.tag(WAICDamageTagManager.IS_MELEE)
                // 原版近战伤害
                .add(DamageTypes.PLAYER_ATTACK)    // 玩家攻击
                .add(DamageTypes.MOB_ATTACK)       // 生物攻击
                .add(DamageTypes.THORNS);          // 荆棘反伤

        // 投射物伤害标签
        this.tag(DamageTypeTags.IS_PROJECTILE)
                .add(WAICDamageType.RAILGUN);      // 电磁炮
    }
}

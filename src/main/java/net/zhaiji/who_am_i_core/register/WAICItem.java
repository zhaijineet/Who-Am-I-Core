package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.chestcavitybeyond.item.ChestOpenerItem;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.item.ExistenceDisplacerItem;
import net.zhaiji.who_am_i_core.item.DragonBloodPreparationItem;
import net.zhaiji.who_am_i_core.mixinapi.IChestCavityData;
import net.zhaiji.who_am_i_core.organ.AnvilCraftOrgans;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.organ.CompanionsOrgans;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.IronSpellOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.function.Supplier;

public class WAICItem {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, WhoAmICore.MOD_ID);

    // 娇小开胸器
    public static final Supplier<Item> PETITE_CHEST_OPENER = ITEM.register(
        "petite_chest_opener",
        () -> new ChestOpenerItem(new Item.Properties().stacksTo(1), 1.0F)
    );

    // 火龙血药剂
    public static final Supplier<Item> FIRE_DRAGON_BLOOD_PREPARATION = ITEM.register(
        "fire_dragon_blood_preparation",
        () -> new DragonBloodPreparationItem(
            new Item.Properties(),
            IChestCavityData.BIT_FIRE_DRAGON
        )
    );

    // 冰龙血药剂
    public static final Supplier<Item> ICE_DRAGON_BLOOD_PREPARATION = ITEM.register(
        "ice_dragon_blood_preparation",
        () -> new DragonBloodPreparationItem(
            new Item.Properties(),
            IChestCavityData.BIT_ICE_DRAGON
        )
    );

    // 电龙血药剂
    public static final Supplier<Item> LIGHTNING_DRAGON_BLOOD_PREPARATION = ITEM.register(
        "lightning_dragon_blood_preparation",
        () -> new DragonBloodPreparationItem(
            new Item.Properties(),
            IChestCavityData.BIT_LIGHTNING_DRAGON
        )
    );

    // 龙血药剂组
    public static final Supplier<Item> DRAGON_BLOOD_PREPARATION_GROUP = ITEM.register(
        "dragon_blood_preparation_group",
        () -> new DragonBloodPreparationItem(
            new Item.Properties(),
            0
        )
    );

    // 存在置换器
    public static final Supplier<Item> EXISTENCE_DISPLACER = ITEM.register(
        "existence_displacer",
        () -> new ExistenceDisplacerItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
    );

    static {
        WAICOrgans.register();
        MowziesMobOrgans.register();
        IceAndFireOrgans.register();
        FDBossesOrgans.register();
        AnvilCraftOrgans.register();
        CataclysmOrgans.register();
        IronSpellOrgans.register();
        CompanionsOrgans.register();
    }
}

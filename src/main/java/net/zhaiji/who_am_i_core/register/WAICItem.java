package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.item.DragonBloodPreparationItem;
import net.zhaiji.who_am_i_core.organ.AnvilCraftOrgans;
import net.zhaiji.who_am_i_core.organ.CataclysmOrgans;
import net.zhaiji.who_am_i_core.organ.FDBossesOrgans;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

import java.util.function.Supplier;

public class WAICItem {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(BuiltInRegistries.ITEM, WhoAmICore.MOD_ID);

    // ==================== 龙之血药剂 ====================
    // 火龙之血
    public static final Supplier<Item> FIRE_DRAGON_BLOOD_PREPARATION = ITEM.register(
        "fire_dragon_blood_preparation",
        () -> new DragonBloodPreparationItem(
            new Item.Properties().stacksTo(1),
            WAICEffect.FIRE_DRAGON_POWER,
            1200, // 60秒
            0
        )
    );

    // 冰龙之血
    public static final Supplier<Item> ICE_DRAGON_BLOOD_PREPARATION = ITEM.register(
        "ice_dragon_blood_preparation",
        () -> new DragonBloodPreparationItem(
            new Item.Properties().stacksTo(1),
            WAICEffect.ICE_DRAGON_POWER,
            1200, 0
        )
    );

    // 电龙之血
    public static final Supplier<Item> LIGHTNING_DRAGON_BLOOD_PREPARATION = ITEM.register(
        "lightning_dragon_blood_preparation",
        () -> new DragonBloodPreparationItem(
            new Item.Properties().stacksTo(1),
            WAICEffect.LIGHTNING_DRAGON_POWER,
            1200, 0
        )
    );

    // 龙之血（组合）
    public static final Supplier<Item> DRAGON_BLOOD_PREPARATION_GROUP = ITEM.register(
        "dragon_blood_preparation_group",
        () -> new DragonBloodPreparationItem(
            new Item.Properties().stacksTo(1),
            WAICEffect.DRAGON_POWER,
            1200, 0
        )
    );

    static {
        WAICOrgans.register();
        MowziesMobOrgans.register();
        IceAndFireOrgans.register();
        FDBossesOrgans.register();
        AnvilCraftOrgans.register();
        CataclysmOrgans.register();
    }
}

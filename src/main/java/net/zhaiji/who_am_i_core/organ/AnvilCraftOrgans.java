package net.zhaiji.who_am_i_core.organ;

import net.minecraft.world.item.Item;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.who_am_i_core.register.WAICItem;

import java.util.function.Supplier;

public class AnvilCraftOrgans {
    // ==================== 浮霜器官 ====================
    // 浮霜心脏
    public static final Supplier<Item> FROST_METAL_HEART = WAICItem.ITEM.register(
        "frost_metal_heart",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜肺脏
    public static final Supplier<Item> FROST_METAL_LUNG = WAICItem.ITEM.register(
        "frost_metal_lung",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜脊柱
    public static final Supplier<Item> FROST_METAL_SPINE = WAICItem.ITEM.register(
        "frost_metal_spine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜胃
    public static final Supplier<Item> FROST_METAL_STOMACH = WAICItem.ITEM.register(
        "frost_metal_stomach",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜肠子
    public static final Supplier<Item> FROST_METAL_INTESTINE = WAICItem.ITEM.register(
        "frost_metal_intestine",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜肾脏
    public static final Supplier<Item> FROST_METAL_KIDNEY = WAICItem.ITEM.register(
        "frost_metal_kidney",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜脾脏
    public static final Supplier<Item> FROST_METAL_SPLEEN = WAICItem.ITEM.register(
        "frost_metal_spleen",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜肝脏
    public static final Supplier<Item> FROST_METAL_LIVER = WAICItem.ITEM.register(
        "frost_metal_liver",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜阑尾
    public static final Supplier<Item> FROST_METAL_APPENDIX = WAICItem.ITEM.register(
        "frost_metal_appendix",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜肋骨
    public static final Supplier<Item> FROST_METAL_RIB = WAICItem.ITEM.register(
        "frost_metal_rib",
        () -> OrganBuilder.builder()
            .build()
    );

    // 浮霜肌肉
    public static final Supplier<Item> FROST_METAL_MUSCLE = WAICItem.ITEM.register(
        "frost_metal_muscle",
        () -> OrganBuilder.builder()
            .build()
    );

    public static void register() {
    }
}

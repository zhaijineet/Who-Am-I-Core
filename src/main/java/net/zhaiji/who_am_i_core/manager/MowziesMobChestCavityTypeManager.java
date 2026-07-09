package net.zhaiji.who_am_i_core.manager;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;

import java.util.Map;

public class MowziesMobChestCavityTypeManager {
    // 衰老胸腔
    public static final ChestCavityType AGED = register("aged")
        .setFirstRow(0, MowziesMobOrgans.AGED_MUSCLE.get())
        .setFirstRow(1, MowziesMobOrgans.AGED_RIB.get())
        .setFirstRow(2, MowziesMobOrgans.AGED_APPENDIX.get())
        .setFirstRow(3, MowziesMobOrgans.AGED_LUNG.get())
        .setFirstRow(4, MowziesMobOrgans.AGED_HEART.get())
        .setFirstRow(5, MowziesMobOrgans.AGED_LUNG.get())
        .setFirstRow(7, MowziesMobOrgans.AGED_RIB.get())
        .setFirstRow(8, MowziesMobOrgans.AGED_MUSCLE.get())
        .setSecondRow(0, MowziesMobOrgans.AGED_MUSCLE.get())
        .setSecondRow(1, MowziesMobOrgans.AGED_RIB.get())
        .setSecondRow(2, MowziesMobOrgans.AGED_SPLEEN.get())
        .setSecondRow(3, MowziesMobOrgans.AGED_KIDNEY.get())
        .setSecondRow(4, MowziesMobOrgans.AGED_SPINE.get())
        .setSecondRow(5, MowziesMobOrgans.AGED_KIDNEY.get())
        .setSecondRow(6, MowziesMobOrgans.AGED_LIVER.get())
        .setSecondRow(7, MowziesMobOrgans.AGED_RIB.get())
        .setSecondRow(8, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(0, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(1, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(2, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(3, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(4, MowziesMobOrgans.AGED_STOMACH.get())
        .setThirdRow(5, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(6, MowziesMobOrgans.AGED_INTESTINE.get())
        .setThirdRow(7, MowziesMobOrgans.AGED_MUSCLE.get())
        .setThirdRow(8, MowziesMobOrgans.AGED_MUSCLE.get());

    // 太阳鸟
    public static final ChestCavityType UMVUTHI = register("umvuthi")
        .setSecondRow(4, MowziesMobOrgans.CHEST_NOVA.get())

        .addValueBonuses(MowziesMobOrgans.CHEST_NOVA.get(), Map.of(
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 泥峭胸腔
    public static final ChestCavityType BLUFF = register("bluff")
        .setFirstRow(1, MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        .setFirstRow(4, MowziesMobOrgans.BLUFF_TABLET.get())

        .setSecondRow(3, MowziesMobOrgans.BLUFF_TABLET.get())
        .setSecondRow(4, MowziesMobOrgans.BLUFF_CORE.get())
        .setSecondRow(5, MowziesMobOrgans.BLUFF_TABLET.get())
        .setSecondRow(6, MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())

        .setThirdRow(3, MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        .setThirdRow(4, MowziesMobOrgans.BLUFF_TABLET.get())

        .addValueBonuses(MowziesMobOrgans.BLUFF_TABLET.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 荧光浮灯
    public static final ChestCavityType LANTERN = register("lantern")
        .setSecondRow(4, ItemHandler.GLOWING_JELLY.get())

        .addValueBonuses(ItemHandler.GLOWING_JELLY.get(), Map.of(
            InitAttribute.HEALTH, 1.0,
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    // 巨噬叶
    public static final ChestCavityType FOLIAATH = register("foliaath")
        .setFirstRow(4, InitItem.CREEPER_LEAF.get())
        .setSecondRow(4, InitItem.CREEPER_LEAF.get())
        .setThirdRow(4, InitItem.CREEPER_LEAF.get())

        .addValueBonuses(InitItem.CREEPER_LEAF.get(), Map.of(
            InitAttribute.HEALTH, 1.0,
            InitAttribute.NERVES, 1.0,
            InitAttribute.BREATH_CAPACITY, 1.0,
            InitAttribute.BREATH_RECOVERY, 1.0
        ));

    /**
     * 注册 Mowzie's Mobs 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {
        // 巨噬叶
        event.registerEntity(EntityHandler.FOLIAATH.get(), FOLIAATH);
        // 巨噬叶幼苗
        event.registerEntity(EntityHandler.BABY_FOLIAATH.get(), FOLIAATH);
        // 霜冻巨兽
        event.registerEntity(EntityHandler.FROSTMAW.get(), ChestCavityTypeManager.ANIMAL);
        // 荧光浮灯
        event.registerEntity(EntityHandler.LANTERN.get(), LANTERN);
        // 岩壳居蟹
        event.registerEntity(EntityHandler.GROTTOL.get(), ChestCavityTypeManager.SMALL_ANIMAL);
        // 飞蛇
        event.registerEntity(EntityHandler.NAGA.get(), ChestCavityTypeManager.CARNIVORE);
        // 雕刻家-通臂大师
        event.registerEntity(EntityHandler.SCULPTOR.get(), AGED);

        // 乌姆塔纳猛禽
        event.registerEntity(EntityHandler.UMVUTHANA_RAPTOR.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳食人鹤
        event.registerEntity(EntityHandler.UMVUTHANA_CRANE.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳追随者
        event.registerEntity(EntityHandler.UMVUTHANA_MINION.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳猛禽追随者
        event.registerEntity(EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳追随者
        event.registerEntity(EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(), ChestCavityTypeManager.ANIMAL);
        // 乌姆塔纳食人鹤追随者
        event.registerEntity(EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(), ChestCavityTypeManager.ANIMAL);

        // 钢铁守护者
        event.registerEntity(EntityHandler.WROUGHTNAUT.get(), ChestCavityTypeManager.HUMAN);
        // 太阳鸟-乌姆武提
        event.registerEntity(EntityHandler.UMVUTHI.get(), UMVUTHI);
        // 泥峭人
        event.registerEntity(EntityHandler.BLUFF.get(), BLUFF);
        // 山魁-嚎叫者
        event.registerEntity(EntityHandler.ELOKOSA_HOWLER.get(), ChestCavityTypeManager.ANIMAL);
        // 山魁-追随者
        event.registerEntity(EntityHandler.ELOKOSA_FOLLOWER_TO_HOWLER.get(), ChestCavityTypeManager.ANIMAL);
    }

    private static ChestCavityType register(String path) {
        return ChestCavityTypeManager.register(ResourceLocation.fromNamespaceAndPath("mowziesmobs", path));
    }
}

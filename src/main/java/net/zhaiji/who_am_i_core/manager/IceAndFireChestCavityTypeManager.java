package net.zhaiji.who_am_i_core.manager;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.chestcavitybeyond.api.ChestCavityType;
import net.zhaiji.chestcavitybeyond.api.event.ChestCavityRegisterEvent;
import net.zhaiji.chestcavitybeyond.manager.ChestCavityTypeManager;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;
import net.zhaiji.chestcavitybeyond.register.InitItem;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;

import java.util.Map;

public class IceAndFireChestCavityTypeManager {
    // 火龙胸腔
    public static final ChestCavityType FIRE_DRAGON = ChestCavityTypeManager.register("fire_dragon")
        .setFirstRow(0, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.FIRE_DRAGON_GEM.get())
        .setFirstRow(3, IceAndFireOrgans.FIRE_DRAGON_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.FIRE_DRAGON_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.FIRE_DRAGON_LUNG.get())
        .setFirstRow(6, IceAndFireOrgans.FIRE_DRAGON_BREATH_SAC.get())
        .setFirstRow(7, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.FIRE_DRAGON_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.FIRE_DRAGON_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.FIRE_DRAGON_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.FIRE_DRAGON_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.FIRE_DRAGON_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.FIRE_DRAGON_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.FIRE_DRAGON_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.FIRE_DRAGON_MUSCLE.get())
        .setCanOpen(IceAndFireChestCavityTypeManager::canOpenDragonRibcage);

    // 冰龙胸腔
    public static final ChestCavityType ICE_DRAGON = ChestCavityTypeManager.register("ice_dragon")
        .setFirstRow(0, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.ICE_DRAGON_GEM.get())
        .setFirstRow(3, IceAndFireOrgans.ICE_DRAGON_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.ICE_DRAGON_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.ICE_DRAGON_LUNG.get())
        .setFirstRow(6, IceAndFireOrgans.ICE_DRAGON_BREATH_SAC.get())
        .setFirstRow(7, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.ICE_DRAGON_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.ICE_DRAGON_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.ICE_DRAGON_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.ICE_DRAGON_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.ICE_DRAGON_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.ICE_DRAGON_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.ICE_DRAGON_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.ICE_DRAGON_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.ICE_DRAGON_MUSCLE.get())
        .setCanOpen(IceAndFireChestCavityTypeManager::canOpenDragonRibcage);

    // 电龙胸腔
    public static final ChestCavityType LIGHTNING_DRAGON = ChestCavityTypeManager.register("lightning_dragon")
        .setFirstRow(0, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setFirstRow(2, IceAndFireOrgans.LIGHTNING_DRAGON_GEM.get())
        .setFirstRow(3, IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.LIGHTNING_DRAGON_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.LIGHTNING_DRAGON_LUNG.get())
        .setFirstRow(6, IceAndFireOrgans.LIGHTNING_DRAGON_BREATH_SAC.get())
        .setFirstRow(7, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.LIGHTNING_DRAGON_SPLEEN.get())
        .setSecondRow(3, IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get())
        .setSecondRow(4, IceAndFireOrgans.LIGHTNING_DRAGON_SPINE.get())
        .setSecondRow(5, IceAndFireOrgans.LIGHTNING_DRAGON_KIDNEY.get())
        .setSecondRow(6, IceAndFireOrgans.LIGHTNING_DRAGON_LIVER.get())
        .setSecondRow(7, IceAndFireOrgans.LIGHTNING_DRAGON_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.LIGHTNING_DRAGON_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.LIGHTNING_DRAGON_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.LIGHTNING_DRAGON_MUSCLE.get())
        .setCanOpen(IceAndFireChestCavityTypeManager::canOpenDragonRibcage);

    // 九头蛇胸腔
    public static final ChestCavityType HYDRA = ChestCavityTypeManager.register("hydra")
        .setFirstRow(0, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setFirstRow(1, IceAndFireOrgans.HYDRA_RIB.get())
        .setFirstRow(3, IceAndFireOrgans.HYDRA_LUNG.get())
        .setFirstRow(4, IceAndFireOrgans.HYDRA_HEART.get())
        .setFirstRow(5, IceAndFireOrgans.HYDRA_LUNG.get())
        .setFirstRow(7, IceAndFireOrgans.HYDRA_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setSecondRow(0, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setSecondRow(1, IceAndFireOrgans.HYDRA_RIB.get())
        .setSecondRow(2, IceAndFireOrgans.HYDRA_SPLEEN.get())
        .setSecondRow(4, IceAndFireOrgans.HYDRA_SPINE.get())
        .setSecondRow(7, IceAndFireOrgans.HYDRA_RIB.get())
        .setSecondRow(8, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(0, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(1, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(2, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(3, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(4, IceAndFireOrgans.HYDRA_STOMACH.get())
        .setThirdRow(5, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(6, IceAndFireOrgans.HYDRA_INTESTINE.get())
        .setThirdRow(7, IceAndFireOrgans.HYDRA_MUSCLE.get())
        .setThirdRow(8, IceAndFireOrgans.HYDRA_MUSCLE.get());

    // 悚怖骷髅
    public static final ChestCavityType DREAD_SKELETON = ChestCavityTypeManager.register("dread_skeleton")
        .copyWith(ChestCavityTypeManager.SKELETON)
        .setNeedHealth(false)
        .setFirstRow(1, IceAndFireOrgans.DREAD_RIB.get())
        .setFirstRow(4, IceAndFireOrgans.FROSTBURN_SOUL.get())
        .setFirstRow(7, IceAndFireOrgans.DREAD_RIB.get())
        .setSecondRow(1, IceAndFireOrgans.DREAD_RIB.get())
        .setSecondRow(4, IceAndFireOrgans.DREAD_SPINE.get())
        .setSecondRow(7, IceAndFireOrgans.DREAD_RIB.get())
        .addValueBonuses(IceAndFireOrgans.DREAD_SPINE.get(), Map.of(
            InitAttribute.BREATH_CAPACITY, 0.5,
            InitAttribute.BREATH_RECOVERY, 0.5
        ));

    // 悚怖亡灵
    public static final ChestCavityType DREAD_UNDEAD = ChestCavityTypeManager.register("dread_undead")
        .copyWith(ChestCavityTypeManager.UNDEAD)
        .setNeedHealth(false)
        .setFirstRow(0, IceAndFireOrgans.BITTER_FLESH.get())
        .setFirstRow(1, IceAndFireOrgans.DREAD_RIB.get())
        .setFirstRow(3, IceAndFireOrgans.ICE_SHARD.get())
        .setFirstRow(7, IceAndFireOrgans.DREAD_RIB.get())
        .setFirstRow(8, IceAndFireOrgans.BITTER_FLESH.get())
        .setSecondRow(0, IceAndFireOrgans.BITTER_FLESH.get())
        .setSecondRow(1, IceAndFireOrgans.DREAD_RIB.get())
        .setSecondRow(4, IceAndFireOrgans.DREAD_SPINE.get())
        .setSecondRow(7, IceAndFireOrgans.DREAD_RIB.get())
        .setThirdRow(0, IceAndFireOrgans.BITTER_FLESH.get())
        .setThirdRow(8, IceAndFireOrgans.BITTER_FLESH.get());

    // 悚怖尸巫
    public static final ChestCavityType DREAD_LICH = ChestCavityTypeManager.register("dread_lich")
        .copyWith(DREAD_SKELETON)
        .setFirstRow(4, IceAndFireOrgans.DREAD_PHYLACTERY.get());

    // 幽灵
    public static final ChestCavityType GHOST = ChestCavityTypeManager.register("ghost")
        .setNeedHealth(false)
        .setSecondRow(4, IafItems.ECTOPLASM.get())
        .addValueBonuses(IafItems.ECTOPLASM.get(), Map.of(
            InitAttribute.NERVES, 0.5,
            InitAttribute.BREATH_CAPACITY, 0.5,
            InitAttribute.BREATH_RECOVERY, 0.5
        ));

    // 独眼巨人
    public static final ChestCavityType CYCLOPS = ChestCavityTypeManager.register("cyclops")
        .set6RowsSize()
        .setFirstRow(0, InitItem.MUSCLE.get())
        .setFirstRow(1, InitItem.RIB.get())
        .setFirstRow(2, InitItem.LUNG.get())
        .setFirstRow(3, InitItem.HEART.get())
        .setFirstRow(4, InitItem.HEART.get())
        .setFirstRow(5, InitItem.HEART.get())
        .setFirstRow(6, InitItem.LUNG.get())
        .setFirstRow(7, InitItem.RIB.get())
        .setFirstRow(8, InitItem.MUSCLE.get())
        .setSecondRow(0, InitItem.MUSCLE.get())
        .setSecondRow(1, InitItem.RIB.get())
        .setSecondRow(2, InitItem.LUNG.get())
        .setSecondRow(3, InitItem.KIDNEY.get())
        .setSecondRow(4, InitItem.SPINE.get())
        .setSecondRow(5, InitItem.KIDNEY.get())
        .setSecondRow(6, InitItem.LUNG.get())
        .setSecondRow(7, InitItem.RIB.get())
        .setSecondRow(8, InitItem.MUSCLE.get())
        .setThirdRow(0, InitItem.MUSCLE.get())
        .setThirdRow(1, InitItem.RIB.get())
        .setThirdRow(2, InitItem.SPLEEN.get())
        .setThirdRow(3, InitItem.KIDNEY.get())
        .setThirdRow(4, InitItem.SPINE.get())
        .setThirdRow(5, InitItem.KIDNEY.get())
        .setThirdRow(6, InitItem.SPLEEN.get())
        .setThirdRow(7, InitItem.RIB.get())
        .setThirdRow(8, InitItem.MUSCLE.get())
        .setOrgan(27, InitItem.MUSCLE.get())
        .setOrgan(28, InitItem.RIB.get())
        .setOrgan(29, InitItem.LIVER.get())
        .setOrgan(30, InitItem.INTESTINE.get())
        .setOrgan(31, InitItem.STOMACH.get())
        .setOrgan(32, InitItem.INTESTINE.get())
        .setOrgan(33, InitItem.LIVER.get())
        .setOrgan(34, InitItem.RIB.get())
        .setOrgan(35, InitItem.MUSCLE.get())
        .setOrgan(36, InitItem.MUSCLE.get())
        .setOrgan(37, InitItem.MUSCLE.get())
        .setOrgan(38, InitItem.MUSCLE.get())
        .setOrgan(39, InitItem.INTESTINE.get())
        .setOrgan(40, InitItem.STOMACH.get())
        .setOrgan(41, InitItem.INTESTINE.get())
        .setOrgan(42, InitItem.MUSCLE.get())
        .setOrgan(43, InitItem.MUSCLE.get())
        .setOrgan(44, InitItem.MUSCLE.get())
        .setOrgan(45, InitItem.MUSCLE.get())
        .setOrgan(46, InitItem.MUSCLE.get())
        .setOrgan(47, InitItem.INTESTINE.get())
        .setOrgan(48, InitItem.INTESTINE.get())
        .setOrgan(49, InitItem.INTESTINE.get())
        .setOrgan(50, InitItem.INTESTINE.get())
        .setOrgan(51, InitItem.INTESTINE.get())
        .setOrgan(52, InitItem.MUSCLE.get())
        .setOrgan(53, InitItem.MUSCLE.get());

    /**
     * 注册冰与火 Mod 所有实体的胸腔类型
     */
    public static void registerEntities(ChestCavityRegisterEvent event) {

        // 火龙
        event.registerEntity(IafEntities.FIRE_DRAGON.get(), FIRE_DRAGON);
        // 冰龙
        event.registerEntity(IafEntities.ICE_DRAGON.get(), ICE_DRAGON);
        // 电龙
        event.registerEntity(IafEntities.LIGHTNING_DRAGON.get(), LIGHTNING_DRAGON);

        // 九头蛇
        event.registerEntity(IafEntities.HYDRA.get(), HYDRA);

        // 悚怖尸奴
        event.registerEntity(IafEntities.DREAD_THRALL.get(), DREAD_SKELETON);
        // 悚怖尸骑
        event.registerEntity(IafEntities.DREAD_KNIGHT.get(), DREAD_SKELETON);
        // 悚怖尸骑战马
        event.registerEntity(IafEntities.DREAD_HORSE.get(), DREAD_SKELETON);
        // 悚怖食尸鬼
        event.registerEntity(IafEntities.DREAD_GHOUL.get(), DREAD_UNDEAD);
        // 悚怖尸兽
        event.registerEntity(IafEntities.DREAD_BEAST.get(), DREAD_UNDEAD);
        // 悚怖劫蛛
        event.registerEntity(IafEntities.DREAD_SCUTTLER.get(), ChestCavityTypeManager.ARTHROPOD);
        // 悚怖尸巫
        event.registerEntity(IafEntities.DREAD_LICH.get(), DREAD_LICH);

        // 海马
        event.registerEntity(IafEntities.HIPPOCAMPUS.get(), ChestCavityTypeManager.AQUATIC);
        // 海蟒
        event.registerEntity(IafEntities.SEA_SERPENT.get(), ChestCavityTypeManager.AQUATIC);
        // 塞壬
        event.registerEntity(IafEntities.SIREN.get(), ChestCavityTypeManager.SALTWATER);

        // 骏鹰
        event.registerEntity(IafEntities.HIPPOGRYPH.get(), ChestCavityTypeManager.ANIMAL);
        // 死亡蠕虫
        event.registerEntity(IafEntities.DEATH_WORM.get(), ChestCavityTypeManager.ARTHROPOD);
        // 鸡蛇
        event.registerEntity(IafEntities.COCKATRICE.get(), ChestCavityTypeManager.ANIMAL);
        // 翼蚺
        event.registerEntity(IafEntities.AMPHITHERE.get(), ChestCavityTypeManager.CARNIVORE);
        // 小精灵
        event.registerEntity(IafEntities.PIXIE.get(), WAICChestCavityTypeManager.FANTASTICAL);
        // 独眼巨人
        event.registerEntity(IafEntities.CYCLOPS.get(), CYCLOPS);
        // 铜羽泽鹗
        event.registerEntity(IafEntities.STYMPHALIAN_BIRD.get(), ChestCavityTypeManager.ANIMAL);
        // 食人妖
        event.registerEntity(IafEntities.TROLL.get(), ChestCavityTypeManager.HUMAN);
        // 蛇发女妖
        event.registerEntity(IafEntities.GORGON.get(), ChestCavityTypeManager.HUMAN);
        // 幽灵
        event.registerEntity(IafEntities.GHOST.get(), GHOST);
    }

    // 判断是否可以开启龙胸腔（活体龙和尸体可开胸，骨架不可开胸）
    private static boolean canOpenDragonRibcage(Player player, LivingEntity entity) {
        if (entity instanceof DragonBaseEntity dragon) {
            if (!dragon.isModelDead()) return true;
            // 骨架状态，不可开胸
            return !dragon.isSkeletal();
        }
        return false;
    }
}

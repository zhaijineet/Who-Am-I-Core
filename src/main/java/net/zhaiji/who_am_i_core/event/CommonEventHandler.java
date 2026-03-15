package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.zhaiji.chestcavitybeyond.api.event.OrganChangeEvent;
import net.zhaiji.chestcavitybeyond.api.event.RegisterChestCavityEvent;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.IceAndFireChestCavityTypeManager;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.manager.WAICChestCavityTypeManager;
import net.zhaiji.who_am_i_core.organ.IceAndFireOrgans;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class CommonEventHandler {
    public static void handlerFMLCommonSetupEvent(FMLCommonSetupEvent event) {
        IceAndFireOrgans.setupOrgans();
    }

    /**
     * 注册可序列化的任务类型
     */
    public static void handlerRegisterChestCavityEvent(RegisterChestCavityEvent event) {
        event.registerTask(ChestNovaTask.TYPE, ChestNovaTask::new);

        // 注册龙类胸腔
        event.registerEntity(IafEntities.FIRE_DRAGON.get(), IceAndFireChestCavityTypeManager.FIRE_DRAGON);
        event.registerEntity(IafEntities.ICE_DRAGON.get(), IceAndFireChestCavityTypeManager.ICE_DRAGON);
        event.registerEntity(IafEntities.LIGHTNING_DRAGON.get(), IceAndFireChestCavityTypeManager.LIGHTNING_DRAGON);

        // 注册幻想种和九头蛇胸腔
        event.registerEntity(IafEntities.PIXIE.get(), WAICChestCavityTypeManager.FANTASTICAL);
        event.registerEntity(IafEntities.HYDRA.get(), IceAndFireChestCavityTypeManager.HYDRA);
    }

    /**
     * 当胸腔中的器官被移除时，检查是否是乌姆塔纳面具
     * 如果是，通知对应的任务移除召唤的生物
     */
    public static void handlerOrganChangeEvent(OrganChangeEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        // 检查被移除的是否是乌姆塔纳面具
        if (!(event.getOldStack().getItem() instanceof ItemUmvuthanaMask)) return;
        // 通知 ChestNovaTask 移除对应槽位的生物
        for (IChestCavityTask task : event.getData().getTasks()) {
            if (task instanceof ChestNovaTask umvuthanaTask) {
                umvuthanaTask.onMaskRemoved(event.getIndex());
                // 应当有且只有一个task，提前返回
                break;
            }
        }
    }

    /**
     * 九头蛇心脏多首免死效果
     * 死亡时触发复活，给一个九头蛇器官设置3分钟冷却，回复未冷却器官种类×10%生命值
     */
    public static void handlerLivingDeathEvent(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        LivingEntity entity = event.getEntity();
        ChestCavityData data = ChestCavityUtil.getData(entity);
        if (data == null) return;
        // 检查是否有九头蛇心脏
        if (!data.hasOrgan(IafItems.HYDRA_HEART.get())) return;
        // 统计未冷却器官数量
        Set<Item> uniqueOrgans = new HashSet<>();
        for (ItemStack organ : data.getOrgans()) {
            if (!organ.is(WAICItemTagManager.HYDRA)) continue;
            Item item = organ.getItem();
            if (entity instanceof Player player && !player.getCooldowns().isOnCooldown(item)) {
                uniqueOrgans.add(item);
            }
        }
        int uncooledTypes = uniqueOrgans.size();
        if (uncooledTypes == 0) return;
        // 取消死亡并复活
        entity.setHealth(entity.getMaxHealth() * uncooledTypes * 0.1f);
        // 给一个未冷却的器官设置冷却（仅玩家）
        if (entity instanceof Player player) {
            for (ItemStack organ : data.getOrgans()) {
                Item item = organ.getItem();
                if (organ.is(WAICItemTagManager.HYDRA) && !player.getCooldowns().isOnCooldown(item)) {
                    player.getCooldowns().addCooldown(item, 20 * 60 * 3);
                    break;
                }
            }
        }
        event.setCanceled(true);
    }
}

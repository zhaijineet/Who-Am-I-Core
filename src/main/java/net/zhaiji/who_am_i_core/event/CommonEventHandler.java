package net.zhaiji.who_am_i_core.event;

import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import net.zhaiji.chestcavitybeyond.api.event.OrganChangeEvent;
import net.zhaiji.chestcavitybeyond.api.event.RegisterChestCavityEvent;
import net.zhaiji.chestcavitybeyond.api.task.IChestCavityTask;
import net.zhaiji.who_am_i_core.task.ChestNovaTask;

public class CommonEventHandler {
    /**
     * 注册可序列化的任务类型
     */
    public static void handlerRegisterChestCavityEvent(RegisterChestCavityEvent event) {
        event.registerTask(ChestNovaTask.TYPE, ChestNovaTask::new);
    }

    /**
     * 当胸腔中的器官被移除时，检查是否是乌姆塔纳面具
     * 如果是，通知对应的任务移除召唤的生物
     */
    public static void handlerOrganChangeEvent(OrganChangeEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        // 检查被移除的是否是乌姆塔纳面具
        if (!(event.getOldStack().getItem() instanceof ItemUmvuthanaMask)) return;
        // 通知所有 ChestNovaTask 移除对应槽位的生物
        for (IChestCavityTask task : event.getData().getTasks()) {
            if (task instanceof ChestNovaTask umvuthanaTask) {
                umvuthanaTask.onMaskRemoved(event.getIndex());
                // 应当有且只有一个task，提前返回
                break;
            }
        }
    }
}

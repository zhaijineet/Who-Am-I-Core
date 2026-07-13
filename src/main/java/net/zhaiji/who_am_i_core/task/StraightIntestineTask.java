package net.zhaiji.who_am_i_core.task;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.chestcavitybeyond.api.task.ISerializableTask;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;

/**
 * 直肠子任务
 * <p>
 * 食用食物后3秒（60 ticks），在实体位置掉落该食物
 * </p>
 * 注意：30%几率检测在事件处理器中进行，只有触发才会创建此Task
 */
public class StraightIntestineTask implements ISerializableTask {
    public static final ResourceLocation TYPE = WhoAmICore.of("straight_intestine");
    private final ChestCavityData data;
    private final ItemStack food;
    private int delayTicks = 3 * 20;

    /**
     * 构造函数
     *
     * @param data 胸腔数据
     * @param food 被食用的食物物品
     */
    public StraightIntestineTask(ChestCavityData data, ItemStack food) {
        this.data = data;
        this.food = food;
    }

    /**
     * 反序列化构造函数
     * 用于从 NBT 数据恢复 StraightIntestineTask
     *
     * @param data     胸腔数据
     * @param provider HolderLookup.Provider
     * @param nbt      NBT 数据
     */
    public StraightIntestineTask(ChestCavityData data, HolderLookup.Provider provider, CompoundTag nbt) {
        this.data = data;
        this.delayTicks = nbt.getInt("delayTicks");
        this.food = ItemStack.parse(provider, nbt.getCompound("food")).orElse(ItemStack.EMPTY);
    }

    @Override
    public void tick(LivingEntity entity) {
        delayTicks--;
    }

    @Override
    public void onRemoved(LivingEntity entity) {
        if (delayTicks <= 0) {
            entity.spawnAtLocation(food);
        }
    }

    @Override
    public boolean canRemove(LivingEntity entity) {
        // 延迟结束或器官被移除时移除任务
        return delayTicks <= 0 || !data.hasOrgan(WAICOrgans.STRAIGHT_INTESTINE.get());
    }

    @Override
    public ResourceLocation getType() {
        return TYPE;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("delayTicks", delayTicks);
        tag.put("food", food.save(provider));
        return tag;
    }
}

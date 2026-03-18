package net.zhaiji.who_am_i_core.edible;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.api.IEdibleCondition;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.util.MowziesMobOrganSkillUtil;

/**
 * 泥峭器官的可食用条件
 * <p>
 * 当实体拥有泥峭器官（泥峭核心、泥峭铭文板、活性泥峭棒）时，
 * 允许食用泥土类物品
 * </p>
 */
public class BluffEdibleCondition implements IEdibleCondition {
    public static final BluffEdibleCondition INSTANCE = new BluffEdibleCondition();

    private BluffEdibleCondition() {
    }

    @Override
    public boolean canEat(LivingEntity entity, ItemStack stack) {
        return isDirtItem(stack) && hasBluffOrgan(entity);
    }

    @Override
    public boolean isTargetItem(ItemStack stack) {
        return isDirtItem(stack);
    }

    @Override
    public void onEat(LivingEntity entity, ItemStack stack) {
        MowziesMobOrganSkillUtil.eatDirt(entity, stack.getItem());
    }

    @Override
    public int getUseDuration() {
        return 36;
    }

    /**
     * 检查是否拥有泥峭器官
     */
    private boolean hasBluffOrgan(LivingEntity entity) {
        return ChestCavityUtil.getData(entity).hasOrgan(
            organ -> organ.is(MowziesMobOrgans.BLUFF_CORE.get()) ||
                     organ.is(MowziesMobOrgans.BLUFF_TABLET.get()) ||
                     organ.is(MowziesMobOrgans.ACTIVE_BLUFF_ROD.get())
        );
    }

    /**
     * 检查是否为泥土物品
     */
    private boolean isDirtItem(ItemStack stack) {
        return stack.is(Items.DIRT) ||
               stack.is(Items.GRASS_BLOCK) ||
               stack.is(Items.MOSS_BLOCK) ||
               stack.is(Items.MYCELIUM) ||
               stack.is(Items.COARSE_DIRT) ||
               stack.is(Items.PODZOL) ||
               stack.is(Items.MUD) ||
               stack.is(Items.ROOTED_DIRT) ||
               stack.is(Items.MUDDY_MANGROVE_ROOTS);
    }
}

package net.zhaiji.who_am_i_core.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.chestcavitybeyond.util.OrganAttributeUtil;
import net.zhaiji.who_am_i_core.inventory.tooltip.FrankensteinHeartTooltip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 弗兰肯斯坦物品
 * <p>
 * 继承自 BundleItem，限制放入器官的标签。
 * 不允许放入自身（防止嵌套）。
 * 最多只能放入 2 个器官（按槽位数计算，不受堆叠数影响）。
 * </p>
 * TODO 自己写一个BundleContents
 */
public class FrankensteinItem extends BundleItem {
    public static final int MAX_ORGANS = 2;
    private final TagKey<Item> allowedTag;

    public FrankensteinItem(Properties properties, TagKey<Item> allowedTag) {
        super(properties);
        this.allowedTag = allowedTag;
    }

    private static boolean hasFreeSlot(BundleContents contents) {
        return contents == null || contents.size() < MAX_ORGANS;
    }

    private static void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private static void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    /**
     * 判断物品是否可以放入弗兰肯斯坦物品
     */
    private boolean canInsert(ItemStack stack) {
        return stack.is(allowedTag) && stack.getItem() != this;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (stack.getCount() != 1 || action != ClickAction.SECONDARY) return false;
        BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
        if (contents == null) return false;
        ItemStack target = slot.getItem();
        if (target.isEmpty()) {
            // 取出一个器官
            if (!contents.isEmpty()) {
                ArrayList<ItemStack> newItems = new ArrayList<>();
                contents.itemsCopy().forEach(newItems::add);
                ItemStack removed = newItems.removeFirst();
                playRemoveOneSound(player);
                ItemStack remainder = slot.safeInsert(removed);
                if (!remainder.isEmpty()) {
                    newItems.addFirst(remainder);
                }
                stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(newItems));
            }
        } else if (canInsert(target) && hasFreeSlot(contents)) {
            // 放入一个器官
            ItemStack taken = slot.safeTake(1, 1, player);
            if (!taken.isEmpty()) {
                ArrayList<ItemStack> newItems = new ArrayList<>();
                newItems.add(taken);
                contents.itemsCopy().forEach(newItems::add);
                playInsertSound(player);
                stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(newItems));
            }
        }

        return true;
    }

    @Override
    public boolean overrideOtherStackedOnMe(
        ItemStack stack,
        ItemStack other,
        Slot slot,
        ClickAction action,
        Player player,
        SlotAccess access
    ) {
        if (stack.getCount() != 1) return false;
        if (action != ClickAction.SECONDARY || !slot.allowModification(player)) return false;
        BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
        if (contents == null) return false;
        ItemStack oldStack = stack.copy();
        if (other.isEmpty()) {
            // 取出一个器官
            if (!contents.isEmpty()) {
                ArrayList<ItemStack> newItems = new ArrayList<>();
                contents.itemsCopy().forEach(newItems::add);
                ItemStack removed = newItems.removeFirst();
                playRemoveOneSound(player);
                access.set(removed);
                stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(newItems));
            }
        } else if (canInsert(other) && hasFreeSlot(contents)) {
            // 放入一个器官
            ArrayList<ItemStack> newItems = new ArrayList<>();
            newItems.add(other.split(1));
            contents.itemsCopy().forEach(newItems::add);
            playInsertSound(player);
            stack.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(newItems));
        }

        // 更新器官属性
        ChestCavityData data = ChestCavityUtil.getData(player);
        int index = -1;
        for (int i = 0; i < data.getOrgans().size(); i++) {
            if (stack == data.getStackInSlot(i)) {
                index = i;
            }
        }
        OrganAttributeUtil.updateOrganAttributeModifier(data, player, index, oldStack, stack);

        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP)
               ? Optional.ofNullable(stack.get(DataComponents.BUNDLE_CONTENTS)).map(FrankensteinHeartTooltip::new)
               : Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // 不添加
    }
}

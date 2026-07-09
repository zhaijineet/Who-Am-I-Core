package net.zhaiji.who_am_i_core.item;

import dev.xylonity.companions.registry.CompanionsEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Objects;

public class ClothTeddyBearItem extends BundleItem {
    public ClothTeddyBearItem(Properties properties) {
        super(properties);
    }

    /**
     * 右键方块生成野生泰迪
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return InteractionResult.SUCCESS;
        ItemStack stack = context.getItemInHand();
        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockState blockstate = level.getBlockState(clickedPos);
        BlockPos spawnPos = blockstate.getCollisionShape(level, clickedPos).isEmpty()
                            ? clickedPos
                            : clickedPos.relative(clickedFace);

        Entity entity = CompanionsEntities.TEDDY.get().spawn(
            serverLevel,
            stack,
            context.getPlayer(),
            spawnPos,
            MobSpawnType.SPAWN_EGG,
            true,
            !Objects.equals(clickedPos, spawnPos) && clickedFace == Direction.UP
        );
        if (entity != null) {
            BundleContents contents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
            if (!contents.isEmpty()) {
                contents.itemsCopy().forEach(item -> Block.popResource(serverLevel, spawnPos, item));
                stack.remove(DataComponents.BUNDLE_CONTENTS);
            }
            stack.consume(1, context.getPlayer());
            level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, clickedPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack target = slot.getItem();
        if (target.isEmpty() || target.is(ItemTags.WOOL)) {
            return super.overrideStackedOnOther(stack, slot, action, player);
        }
        return false;
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
        if (other.isEmpty() || other.is(ItemTags.WOOL)) {
            return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
        }
        return false;
    }
}

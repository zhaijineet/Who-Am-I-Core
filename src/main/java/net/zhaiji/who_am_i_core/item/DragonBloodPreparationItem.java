package net.zhaiji.who_am_i_core.item;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DragonBloodPreparationItem extends Item {
    private final Holder<MobEffect> effect;
    private final int duration;
    private final int amplifier;

    public DragonBloodPreparationItem(Properties properties, Holder<MobEffect> effect, int duration, int amplifier) {
        super(properties);
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.addEffect(new MobEffectInstance(effect, duration, amplifier));
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}

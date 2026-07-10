package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 在双端 onTake 中处理欺诈效果：
 * - 5倍经验（客户端和服务端各自独立累加，无需额外发包）
 * - N≥3 时重置使用次数（双端各自 reset，客户端自行同步）
 */
@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin {
    @Shadow
    @Final
    private Merchant merchant;

    @Shadow
    @Final
    private Player player;

    /**
     * 正确的累加村民职业经验
     */
    @Redirect(
        method = "onTake",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    private int whoAmICore$onTake$redirect(MerchantOffer offer) {
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (data.hasOrgan(WAICOrgans.FRAUD.get())) {
            return offer.getXp() * 5;
        }
        return offer.getXp();
    }

    /**
     * 重置交易存货数量
     */
    @Inject(
        method = "onTake",
        at = @At("RETURN")
    )
    public void whoAmICore$onTake$inject(Player player, ItemStack stack, CallbackInfo ci) {
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.FRAUD.get())) return;
        int n = data.getOrganCount(WAICItemTagManager.NINE_HELL);
        if (n >= 3) {
            for (MerchantOffer offer : merchant.getOffers()) {
                if (offer.getUses() > 0) {
                    offer.resetUses();
                }
            }
        }
    }
}

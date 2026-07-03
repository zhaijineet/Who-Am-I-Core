package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MerchantContainer.class)
public class MerchantContainerMixin {
    @Shadow
    @Final
    private Merchant merchant;

    /**
     * 用于交易界面的经验条预显示（提示交易后经验会涨多少）
     * 当交易玩家拥有欺诈器官时，返回10倍值
     */
    @Redirect(
        method = "updateSellItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    public int whoAmICore$updateSellItem(MerchantOffer offer) {
        Player player = merchant.getTradingPlayer();
        if (player != null) {
            ChestCavityData data = ChestCavityUtil.getData(player);
            if (data.hasOrgan(WAICOrgans.FRAUD.get())) {
                return offer.getXp() * 10;
            }
        }
        return offer.getXp();
    }
}

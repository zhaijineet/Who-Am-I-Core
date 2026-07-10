package net.zhaiji.who_am_i_core.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.manager.WAICItemTagManager;
import net.zhaiji.who_am_i_core.organ.WAICOrgans;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Villager 侧的欺诈效果 Mixin：
 * - rewardTradeXp 中 5 倍村民经验 + 5 倍经验球
 * - customServerAiStep 中连续升级
 * - updateSpecialPrices 中折扣
 * - 不再调用 resendOffersToTradingPlayer（xp 同步和 resetUses 由 MerchantResultSlotMixin 在双端各自处理）
 */

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected abstract boolean shouldIncreaseLevel();

    @Shadow
    protected abstract void increaseMerchantCareer();

    /**
     * 判断交易玩家是否拥有欺诈器官，是则返回九地狱数量，否则返回 0
     */
    @Unique
    public int whoAmICore$getFraudCount(Player player) {
        if (player == null) return 0;
        ChestCavityData data = ChestCavityUtil.getData(player);
        if (!data.hasOrgan(WAICOrgans.FRAUD.get())) return 0;
        return data.getOrganCount(WAICItemTagManager.NINE_HELL);
    }

    /**
     * 有欺诈器官时，让村民获得总共 5 倍经验
     */
    @Redirect(
        method = "rewardTradeXp",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/trading/MerchantOffer;getXp()I"
        )
    )
    public int whoAmICore$rewardTradeXp$redirect(MerchantOffer offer) {
        int fraudCount = whoAmICore$getFraudCount(getTradingPlayer());
        if (fraudCount > 0) {
            return offer.getXp() * 5;
        }
        return offer.getXp();
    }

    /**
     * 有欺诈器官时经验球数量 ×5
     */
    @ModifyArg(
        method = "rewardTradeXp",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/ExperienceOrb;<init>(Lnet/minecraft/world/level/Level;DDDI)V"
        ),
        index = 4
    )
    public int whoAmICore$rewardTradeXp$modifyArg(int value) {
        int fraudCount = whoAmICore$getFraudCount(getTradingPlayer());
        if (fraudCount > 0) {
            return value * 5;
        }
        return value;
    }

    /**
     * 原版每次只升一级，5倍经验可能让 villagerXp 远超当前升级线，
     * 在原版升级时机处连续升级直到不再满足条件
     */
    @Redirect(
        method = "customServerAiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/npc/Villager;increaseMerchantCareer()V"
        )
    )
    public void whoAmICore$customServerAiStep(Villager instance) {
        while (shouldIncreaseLevel()) {
            increaseMerchantCareer();
        }
    }

    /**
     * N≥2: 交易打折 30%×(N-1)
     */
    @Inject(
        method = "updateSpecialPrices",
        at = @At("RETURN")
    )
    public void whoAmICore$updateSpecialPrices(Player player, CallbackInfo ci) {
        int n = whoAmICore$getFraudCount(player);
        if (n < 2) return;
        double discountRate = 0.3 * (n - 1);
        for (MerchantOffer offer : getOffers()) {
            int discount = (int) Math.floor(discountRate * offer.getBaseCostA().getCount());
            offer.addToSpecialPriceDiff(-Math.max(discount, 1));
        }
    }
}

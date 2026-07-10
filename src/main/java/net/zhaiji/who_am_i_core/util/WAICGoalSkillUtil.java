package net.zhaiji.who_am_i_core.util;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.zhaiji.chestcavitybeyond.api.goal.GoalSkillIntent;
import net.zhaiji.chestcavitybeyond.api.goal.GoalSkillMetadata;
import net.zhaiji.chestcavitybeyond.api.goal.GoalSkillTargetResolver;
import net.zhaiji.chestcavitybeyond.attachment.ChestCavityData;
import net.zhaiji.chestcavitybeyond.util.ChestCavityUtil;
import net.zhaiji.who_am_i_core.attachment.HumoursData;

import java.util.List;

public class WAICGoalSkillUtil {
    // 火龙吐息
    public static GoalSkillMetadata fireDragonBreathSacGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> IceAndFireOrganUtil.fireDragonBreathSac(slotContext)
        ).build();
    }

    // 冰龙吐息
    public static GoalSkillMetadata iceDragonBreathSacGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> IceAndFireOrganUtil.iceDragonBreathSac(slotContext)
        ).build();
    }

    // 电龙吐息
    public static GoalSkillMetadata lightningDragonBreathSacGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> IceAndFireOrganUtil.lightningDragonBreathSac(slotContext)
        ).build();
    }

    // 九头蛇吐息（消耗中毒释放吐息）
    public static GoalSkillMetadata hydraLungGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> IceAndFireOrganUtil.hydraLung(slotContext)
        ).canUse((mob, skillEntry) -> {
            MobEffectInstance poison = mob.getEffect(MobEffects.POISON);
            return poison != null && poison.getDuration() > 0;
        }).build();
    }

    // 教宗脾脏 — 圣火环阵
    public static GoalSkillMetadata pontiffSpleenGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> CompanionsOrganUtil.pontiffSpleen(slotContext)
        ).build();
    }

    // 教宗阑尾 — 圣星裁决
    public static GoalSkillMetadata pontiffAppendixGoalSkill() {
        return GoalSkillMetadata.targetedAttack(
            GoalSkillMetadata::defaultRange,
            (goalContext, slotContext) -> CompanionsOrganUtil.pontiffAppendix(slotContext, goalContext.target())
        ).build();
    }

    // 布织泰迪熊 — 缝补
    public static GoalSkillMetadata clothTeddyBearGoalSkill() {
        return GoalSkillMetadata.recovery(
            (goalContext, slotContext) -> CompanionsOrganUtil.clothTeddyBear(slotContext)
        ).canUse((mob, skillEntry) -> {
            if (mob.getHealth() >= mob.getMaxHealth()) return false;
            BundleContents contents = skillEntry.stack().getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
            for (int j = 0; j < contents.size(); j++) {
                if (contents.getItemUnsafe(j).is(ItemTags.WOOL)) {
                    return true;
                }
            }
            return false;
        }).build();
    }

    // 封印石板 — 破封·幻戟阵
    public static GoalSkillMetadata sealingStoneSlabGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> CataclysmOrganUtil.sealingStoneSlab(slotContext)
        ).canUse((mob, skillEntry) -> mob.onGround()).build();
    }

    // 巨兽回路 — 地震践踏
    public static GoalSkillMetadata monstrosityCircuitGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> CataclysmOrganUtil.monstrosityCircuit(slotContext)
        ).canUse((mob, skillEntry) -> HumoursData.extractYellowBile(mob, 100, true) >= 100).build();
    }

    // 机械之星 — 凋零追踪导弹
    public static GoalSkillMetadata mechanicalStarGoalSkill() {
        return GoalSkillMetadata.targetedAttack(
            GoalSkillMetadata::defaultRange,
            (goalContext, slotContext) -> CataclysmOrganUtil.mechanicalStar(slotContext, goalContext.target())
        ).build();
    }

    // 死亡透镜 — 死亡激光
    public static GoalSkillMetadata deathLensGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> CataclysmOrganUtil.deathLens(slotContext)
        ).build();
    }

    // 虚空晶脊 — 虚空践踏
    public static GoalSkillMetadata voidCrystalSpineGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> CataclysmOrganUtil.voidCrystalSpine(slotContext)
        ).canUse((mob, skillEntry) -> mob.onGround()).build();
    }

    // 利维坦鳃 — 深海怒吼
    public static GoalSkillMetadata leviathanGillGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> CataclysmOrganUtil.leviathanGill(slotContext)
        ).build();
    }

    // 沙釉心脏 — 沙暴怒吼
    public static GoalSkillMetadata sandGlazeHeartGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> CataclysmOrganUtil.sandGlazeHeart(slotContext)
        ).build();
    }

    // 墨水阑尾 — 消耗墨水回复法力
    public static GoalSkillMetadata inkAppendixGoalSkill() {
        return GoalSkillMetadata.recovery(
            (goalContext, slotContext) -> WAICOrganUtil.inkAppendix(slotContext)
        ).canUse((mob, skillEntry) -> {
            MagicData magicData = MagicData.getPlayerMagicData(mob);
            float maxMana = (float) mob.getAttributeValue(AttributeRegistry.MAX_MANA);
            return magicData.getMana() < maxMana;
        }).weightOverride((mob, combatContext, skillEntry) -> {
            MagicData magicData = MagicData.getPlayerMagicData(mob);
            float maxMana = (float) mob.getAttributeValue(AttributeRegistry.MAX_MANA);
            if (maxMana <= 0) return 0;
            float manaPercent = Math.max(0, magicData.getMana() / maxMana);
            return -10 + (1 - manaPercent) * 100
                   + (combatContext.lastUsedIntent() == GoalSkillIntent.RECOVERY ? -50 : 0);
        }).build();
    }

    // 病变心脏 — 传播效果
    public static GoalSkillMetadata lesionHeartGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> WAICOrganUtil.lesionHeart(slotContext)
        ).canUse((mob, skillEntry) -> !mob.getActiveEffects().isEmpty()).build();
    }

    // 猩红阑尾 — 消耗血液回血
    public static GoalSkillMetadata crimsonAppendixGoalSkill() {
        return GoalSkillMetadata.recovery(
            (goalContext, slotContext) -> WAICOrganUtil.crimsonAppendix(slotContext)
        ).canUse((mob, skillEntry) -> {
            float missingHealth = mob.getMaxHealth() - mob.getHealth();
            if (missingHealth <= 0) return false;
            return HumoursData.extractBlood(mob, missingHealth * 5, true) > 0;
        }).build();
    }

    // 血肉偶像 — 清除负面折半血量
    public static GoalSkillMetadata fleshIdolGoalSkill() {
        return GoalSkillMetadata.recovery(
            (goalContext, slotContext) -> WAICOrganUtil.fleshIdol(slotContext)
        ).canUse((mob, skillEntry) -> {
            for (MobEffectInstance effect : mob.getActiveEffects()) {
                if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) return true;
            }
            return false;
        }).weightOverride((mob, combatContext, skillEntry) -> {
            int harmfulCount = 0;
            for (MobEffectInstance effect : mob.getActiveEffects()) {
                if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) harmfulCount++;
            }
            if (harmfulCount == 0) return 0;
            return 20 + harmfulCount * 30 + (combatContext.lastUsedIntent() == GoalSkillIntent.RECOVERY ? -50 : 0);
        }).build();
    }

    // 传导链节 — 激活超频模式
    public static GoalSkillMetadata conductiveSpineGoalSkill() {
        return GoalSkillMetadata.attack(
            (goalContext, slotContext) -> WAICOrganUtil.conductiveSpine(slotContext)
        ).canUse((mob, skillEntry) -> {
            ChestCavityData data = ChestCavityUtil.getData(mob);
            List<ItemStack> modules = WAICOrganUtil.collectEnergyModules(data);
            if (modules.isEmpty()) return false;
            return WAICOrganUtil.getCharge(modules) > 0;
        }).build();
    }

    // 泥峭核心 — 吃泥土
    public static GoalSkillMetadata bluffCoreGoalSkill() {
        return GoalSkillMetadata.blockInteract(
            (goalContext, slotContext) -> MowziesMobOrganUtil.bluffCore(slotContext, goalContext.blockTarget())
        ).blockTargetResolver(
            (mob, skillEntry) -> GoalSkillTargetResolver.DEFAULT_BLOCK_RESOLVER.apply(
                mob, state -> MowziesMobOrganUtil.isDirtBlock(state.getBlock())
            )
        ).build();
    }

    // 电磁炮 — 远程发射金属粒
    public static GoalSkillMetadata railgunGoalSkill() {
        return GoalSkillMetadata.targetedAttack(
            GoalSkillMetadata::defaultRange,
            AnvilCraftOrganUtil::railgunGoal
        ).build();
    }

    // ==================== 紫水晶巨蟹器官 ====================

    // 花岩核心 — 晶簇环爆
    public static GoalSkillMetadata bloomStoneCoreGoalSkill() {
        return GoalSkillMetadata.aoeAttack(
            (goalContext, slotContext) -> CataclysmOrganUtil.bloomStoneCore(slotContext)
        ).build();
    }
}

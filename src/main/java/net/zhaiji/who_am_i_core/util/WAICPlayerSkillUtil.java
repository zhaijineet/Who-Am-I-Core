package net.zhaiji.who_am_i_core.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zhaiji.chestcavitybeyond.api.ChestCavitySlotContext;
import net.zhaiji.who_am_i_core.manager.RailgunAmmoManager;

public class WAICPlayerSkillUtil {
    // 火龙吐息
    public static boolean fireDragonBreathSac(ChestCavitySlotContext context) {
        return IceAndFireOrganUtil.fireDragonBreathSac(context);
    }

    // 冰龙吐息
    public static boolean iceDragonBreathSac(ChestCavitySlotContext context) {
        return IceAndFireOrganUtil.iceDragonBreathSac(context);
    }

    // 电龙吐息
    public static boolean lightningDragonBreathSac(ChestCavitySlotContext context) {
        return IceAndFireOrganUtil.lightningDragonBreathSac(context);
    }

    // 九头蛇吐息
    public static boolean hydraLung(ChestCavitySlotContext context) {
        return IceAndFireOrganUtil.hydraLung(context);
    }

    // 教宗脾脏 — 圣火环阵
    public static boolean pontiffSpleen(ChestCavitySlotContext context) {
        return CompanionsOrganUtil.pontiffSpleen(context);
    }

    // 教宗阑尾 — 圣星裁决（视线索敌）
    public static boolean pontiffAppendix(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        int distance = 16;
        HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(entity, checkEntity -> checkEntity != entity, distance);
        if (!(hitResult instanceof EntityHitResult entityHitResult)) return false;
        if (!(entityHitResult.getEntity() instanceof LivingEntity target)) return false;
        return CompanionsOrganUtil.pontiffAppendix(context, target);
    }

    // 布织泰迪熊 — 缝补
    public static boolean clothTeddyBear(ChestCavitySlotContext context) {
        return CompanionsOrganUtil.clothTeddyBear(context);
    }

    // 封印石板 — 破封·幻戟阵
    public static boolean sealingStoneSlab(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.sealingStoneSlab(context);
    }

    // 巨兽回路 — 地震践踏
    public static boolean monstrosityCircuit(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.monstrosityCircuit(context);
    }

    // 机械之星 — 凋零追踪导弹（锁定视线方向的敌人）
    public static boolean mechanicalStar(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        int distance = 32;
        HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(entity, checkEntity -> checkEntity != entity, distance);
        if (!(hitResult instanceof EntityHitResult entityHitResult)) return false;
        if (!(entityHitResult.getEntity() instanceof LivingEntity target)) return false;
        return CataclysmOrganUtil.mechanicalStar(context, target);
    }

    // 死亡透镜 — 死亡激光
    public static boolean deathLens(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.deathLens(context);
    }

    // 虚空晶脊 — 虚空践踏
    public static boolean voidCrystalSpine(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.voidCrystalSpine(context);
    }

    // 利维坦鳃 — 深海怒吼
    public static boolean leviathanGill(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.leviathanGill(context);
    }

    // 沙釉心脏 — 沙暴怒吼
    public static boolean sandGlazeHeart(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.sandGlazeHeart(context);
    }

    // 墨水阑尾 — 消耗墨水回复法力
    public static boolean inkAppendix(ChestCavitySlotContext context) {
        return WAICOrganUtil.inkAppendix(context);
    }

    // 病变心脏 — 传播效果
    public static boolean lesionHeart(ChestCavitySlotContext context) {
        return WAICOrganUtil.lesionHeart(context);
    }

    // 猩红阑尾 — 消耗血液回血
    public static boolean crimsonAppendix(ChestCavitySlotContext context) {
        return WAICOrganUtil.crimsonAppendix(context);
    }

    // 血肉偶像 — 清除负面折半血量
    public static boolean fleshIdol(ChestCavitySlotContext context) {
        return WAICOrganUtil.fleshIdol(context);
    }

    // 传导链节 — 激活超频模式
    public static boolean conductiveSpine(ChestCavitySlotContext context) {
        return WAICOrganUtil.conductiveSpine(context);
    }

    // 泥峭核心 — 吃泥土（视线射线检测方块）
    public static boolean bluffCore(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        Vec3 from = entity.getEyePosition();
        Vec3 to = from.add(entity.getLookAngle()
            .normalize()
            .scale(entity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE)));
        ClipContext clipContext = new ClipContext(
            from, to,
            ClipContext.Block.OUTLINE,
            ClipContext.Fluid.NONE,
            CollisionContext.empty()
        );
        Level level = entity.level();
        BlockHitResult hitResult = level.clip(clipContext);
        if (hitResult.getType() != HitResult.Type.BLOCK) return false;
        return MowziesMobOrganUtil.bluffCore(context, hitResult.getBlockPos());
    }

    // 电磁炮 — 从手中取金属粒发射
    public static boolean railgun(ChestCavitySlotContext context) {
        LivingEntity entity = context.entity();
        ItemStack mainHand = entity.getMainHandItem();
        ItemStack offHand = entity.getOffhandItem();
        ItemStack ammoStack = RailgunAmmoManager.isValidAmmo(mainHand) ? mainHand
            : RailgunAmmoManager.isValidAmmo(offHand) ? offHand
            : ItemStack.EMPTY;
        if (ammoStack.isEmpty()) return false;

        if (!AnvilCraftOrganUtil.fireRailgun(context, ammoStack)) return false;

        if (!(entity instanceof Player player && player.isCreative())) {
            ammoStack.shrink(1);
        }
        return true;
    }

    // ==================== 紫水晶巨蟹器官 ====================

    // 花岩核心 — 晶簇环爆
    public static boolean bloomStoneCore(ChestCavitySlotContext context) {
        return CataclysmOrganUtil.bloomStoneCore(context);
    }
}

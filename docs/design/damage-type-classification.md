# 伤害类型分类方案设计文档 v1.0

**项目**: Who Am I Core
**设计日期**: 2026-03-16
**设计师**: Design Manager
**版本**: 1.0
**状态**: 设计完成

---

## 设计概述

### 为什么要进行伤害类型分类？

#### 现有问题

1. **伤害类型散乱**: 各模组伤害类型缺乏统一分类标准
2. **扩展性不足**: 难以实现基于伤害类型的加成/抗性系统
3. **平衡性困难**: 无法有效设计针对特定伤害类型的效果
4. **兼容性挑战**: 不同模组的伤害类型机制不统一

#### 设计目标

1. **统一分类标准**: 建立清晰、可扩展的伤害类型分类体系
2. **代码复用基础**: 为伤害类型相关的代码逻辑提供统一接口
3. **支持扩展**: 为未来的伤害相关机制扩展预留空间
4. **平衡性工具**: 提供伤害类型克制/平衡的设计框架

### 核心理念

**三大伤害类型分类**

所有伤害可以分为三大类型：

1. **近战伤害 (Melee)** - 实体直接接触攻击
2. **远程伤害 (Ranged)** - 投射物、射线、弹丸
3. **魔法伤害 (Magic)** - 具有魔法属性的特殊伤害

### 设计原则

1. **互斥性**: 每个伤害类型只能归属到一个分类
2. **可判定**: 必须有明确的技术判定规则
3. **可扩展**: 可以轻松添加新的伤害类型
4. **模组兼容**: 支持所有主流模组的伤害类型
5. **主题性**: 分类应符合玩家的直观认知

---

## 伤害类型分类体系

### 近战伤害 (Melee Damage)

#### 定义

攻击者必须直接接触目标才能造成的伤害。

#### 判定规则

```java
boolean isMeleeDamage(DamageSource source) {
    // 有实体来源 且 是近战攻击类型
    return source.getEntity() != null
        && !source.is(DamageTypeTags.IS_PROJECTILE)
        && !isMagicDamage(source)
        && (source.is(DamageTypeTags.MOB_ATTACK)
            || source.is(DamageTypes.PLAYER_ATTACK)
            || source.is(DamageTypes.THORNS)
            || isDirectContactAttack(source));
}
```

#### 包含的伤害类型

**原版伤害**:
- `player_attack` - 玩家攻击
- `mob_attack` - 生物攻击
- `thorns` - 荆棘反伤

**模组伤害**:
- `organ_loss` - 器官流失 (CCB)
- `open_chest` - 打开胸腔 (CCB)
- `barbaro_ambush` - 野蛮人伏击
- 各模组的近战技能伤害

---

### 远程伤害 (Ranged Damage)

#### 定义

通过投射物、射线或能量束等非接触方式造成的伤害。

#### 判定规则

```java
boolean isRangedDamage(DamageSource source) {
    // 是投射物 或 音爆 或 有间接实体
    return source.is(DamageTypeTags.IS_PROJECTILE)
        || source.is(DamageTypes.SONIC_BOOM)
        || source.getDirectEntity() != null
        && !isMagicDamage(source);
}
```

#### 包含的伤害类型

**原版伤害**:
- `arrow` - 箭矢
- `trident` - 三叉戟
- `fireball` - 火球
- `sonic_boom` - 音爆
- `wind_charge` - 风弹

**模组伤害**:
- `chesed_electric_sphere` - 电球投射物 (FDBosses)
- `malkuth_slashes` - 斩击弹幕 (FDBosses)
- `touhou_danmaku` - 东方弹幕 (TouhouLittleMaid)
- 各模组的投射物伤害

---

### 魔法伤害 (Magic Damage)

#### 定义

具有魔法属性标签的伤害类型。

#### 判定规则

```java
boolean isMagicDamage(DamageSource source) {
    // 是魔法标签 或 魔法伤害类型
    return source.is(Tags.DamageTypes.IS_MAGIC)
        || source.is(DamageTypes.MAGIC)
        || source.is(DamageTypes.INDIRECT_MAGIC)
        || isMagicSchoolDamage(source);
}

boolean isMagicSchoolDamage(DamageSource source) {
    // Irons Spells n Spellbooks 学派检测
    return source.is(WAICDamageTypeTags.FIRE_MAGIC)
        || source.is(WAICDamageTypeTags.ICE_MAGIC)
        || source.is(WAICDamageTypeTags.LIGHTNING_MAGIC)
        || source.is(WAICDamageTypeTags.NATURE_MAGIC)
        || source.is(WAICDamageTypeTags.SOUL_MAGIC)
        || source.is(WAICDamageTypeTags.ARCANE_MAGIC)
        // Ice and Fire 龙类魔法
        || source.is(WAICDamageTypeTags.DRAGON_FIRE)
        || source.is(WAICDamageTypeTags.DRAGON_ICE)
        || source.is(WAICDamageTypeTags.DRAGON_LIGHTNING);
}
```

#### 包含的伤害类型

**Irons Spells n Spellbooks**:
- `fire_magic` - 火焰学派
- `ice_magic` - 冰霜学派
- `lightning_magic` - 雷电学派
- `nature_magic` - 自然学派
- `soul_magic` - 灵魂学派
- `evocation_magic` - 召唤学派
- `blood_magic` - 血魔法 (某些模组)

**Ice and Fire**:
- `dragon_fire` - 龙火
- `dragon_ice` - 龙冰
- `dragon_lightning` - 龙雷

**其他模组**:
- `lost_in_time` - 时间迷失 (AnvilCraft)
- `magic_damage` - 通用魔法伤害
- 任何标记为 IS_MAGIC 的伤害

---

## 各模组伤害类型映射表

### 原版 (Vanilla)

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| player_attack | 近战 | 玩家攻击 |
| mob_attack | 近战 | 生物攻击 |
| arrow | 远程 | 投射物 |
| trident | 远程 | 投射物 |
| fireball | 远程 | 投射物 |
| sonic_boom | 远程 | 监守者音爆 |
| wind_charge | 远程 | 风弹 |
| thorns | 近战 | 荆棘反伤 |
| on_fire | 魔法 | 燃烧(魔法属性) |
| magic | 魔法 | 魔法伤害 |
| indirect_magic | 魔法 | 间接魔法 |
| drowned_attack | 近战 | 溺尸攻击 |
| goat_ram | 近战 | 山羊冲撞 |
| anvil_smash | 近战 | 铁砧坠落 |
| falling_block | 近战 | 落块 |

### Irons Spells n Spellbooks

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| fire_magic | 魔法 | 火焰学派 |
| ice_magic | 魔法 | 冰霜学派 |
| lightning_magic | 魔法 | 雷电学派 |
| nature_magic | 魔法 | 自然学派 |
| blood_magic | 魔法 | 血魔法 |
| evocation_magic | 魔法 | 召唤学派 |
| eldritch_magic | 魔法 | 古怪魔法 |
| ender_magic | 魔法 | 末影魔法 |
| holy_magic | 魔法 | 神圣魔法 |
| heartstop | 魔法 | 心脏停搏（特殊效果） |
| dragon_breath_pool | 魔法 | 龙息池（区域伤害） |
| blood_cauldron | 魔法 | 血釜（区域伤害） |
| fire_field | 魔法 | 火焰领域（区域伤害） |
| poison_cloud | 魔法 | 毒云（区域伤害） |

### Ice and Fire (CE)

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| dragon_fire | 魔法 | 龙火 |
| dragon_ice | 魔法 | 龙冰 |
| dragon_lightning | 魔法 | 龙雷 |
| gorgon | 近战 | 美杜莎石化 |
| bonus | 魔法 | 奖励伤害 |

### FDBosses

**Chesed（慈悲）BOSS**:
| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| chesed_attack | 近战 | 慈悲近战 |
| chesed_lor_attack | 近战 | LOR彩蛋近战 |
| chesed_ba_attack | 近战 | BA彩蛋近战 |
| chesed_electric_sphere | 远程 | 电球投射物 |
| chesed_falling_block | 近战 | 落块伤害 |
| chesed_vertical_ray | 远程 | 垂直射线 |
| chesed_earthquake | 近战 | 地震（范围） |
| chesed_roll_attack | 近战 | 翻滚攻击 |
| chesed_rock_attack | 远程 | 岩石投射物 |

**Malkuth（王国）BOSS**:
| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| malkuth_cowardice | 近战 | 懦夫惩罚 |
| malkuth_cannons | 远程 | 火炮投射物 |
| malkuth_chainpunch | 近战 | 连环拳 |
| malkuth_earthshatter | 近战 | 地面破碎 |
| malkuth_hellshaper | 远程 | 地狱塑形者 |
| malkuth_impaling_doom | 远程 | 穿刺毁灭 |
| malkuth_side_rocks | 远程 | 侧翼岩石 |
| malkuth_slashes | 远程 | 斩击弹幕 |
| malkuth_tsars_wrath | 远程 | 沙皇之怒 |

**Geburah（严厉）BOSS**:
| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| geburah_judgement_ball | 远程 | 审判之球 |
| geburah_ray_strike | 远程 | 射线打击 |
| geburah_laser_strike | 远程 | 激光打击 |
| geburah_earthquake | 近战 | 地震 |
| geburah_justice_hammer | 近战 | 正义之锤 |
| sinned_too_much | 魔法 | 罪孽深重（特殊效果） |

### Chest Cavity Beyond

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| organ_loss | 近战 | 器官流失（内部伤害） |
| open_chest | 近战 | 打开胸腔 |

### TouhouLittleMaid

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| danmaku | 远程 | 弹幕（标记为is_projectile） |
| danmaku_ender_killer | 远程 | 末影杀手弹幕 |
| bullet | 远程 | 弹幕（通用） |
| maid_attack | 近战 | 女仆近战（原版mob_attack） |

### Mowzie's Mobs

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| *无自定义伤害类型* | - | 使用原版伤害类型（mob_attack, arrow等） |

**说明**：Mowzie's Mobs没有定义自己的伤害类型，所有生物使用原版的`mob_attack`（近战）和投射物使用`arrow`或自定义`EntityProjectile`的攻击。

### AnvilCraft

| 伤害类型 | 分类 | 备注 |
|----------|------|------|
| lost_in_time | 魔法 | 时间迷失（标记为is_magic） |
| laser | 远程 | 激光（带燃烧效果） |

---

## 技术实现建议

### 核心文件

需要创建以下核心文件：

1. **伤害类型判断工具** - `DamageTypeUtil.java`
2. **伤害类型标签管理** - `WAICDamageTypeTags.java`

### 工具类设计

```java
// DamageTypeUtil.java - 伤害类型判断工具
package net.zhaiji.who_am_i_core.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.Tags;

public class DamageTypeUtil {

    /**
     * 伤害分类枚举
     */
    public enum DamageCategory {
        MELEE,   // 近战
        RANGED,  // 远程
        MAGIC    // 魔法
    }

    /**
     * 获取伤害类型的分类
     * @param source 伤害源
     * @return 伤害分类
     */
    public static DamageCategory getDamageCategory(DamageSource source) {
        if (isMagicDamage(source)) return DamageCategory.MAGIC;
        if (isRangedDamage(source)) return DamageCategory.RANGED;
        return DamageCategory.MELEE;
    }

    /**
     * 判断是否为魔法伤害
     * @param source 伤害源
     * @return 是否为魔法伤害
     */
    public static boolean isMagicDamage(DamageSource source) {
        return source.is(Tags.DamageTypes.IS_MAGIC)
            || source.is(DamageTypes.MAGIC)
            || source.is(DamageTypes.INDIRECT_MAGIC)
            || isMagicSchoolDamage(source);
    }

    /**
     * 判断是否为特定学派魔法
     * @param source 伤害源
     * @return 是否为学派魔法
     */
    public static boolean isMagicSchoolDamage(DamageSource source) {
        return source.is(WAICDamageTypeTags.FIRE_MAGIC)
            || source.is(WAICDamageTypeTags.ICE_MAGIC)
            || source.is(WAICDamageTypeTags.LIGHTNING_MAGIC)
            || source.is(WAICDamageTypeTags.NATURE_MAGIC)
            || source.is(WAICDamageTypeTags.BLOOD_MAGIC)
            || source.is(WAICDamageTypeTags.EVOCATION_MAGIC)
            || source.is(WAICDamageTypeTags.ELDRITCH_MAGIC)
            || source.is(WAICDamageTypeTags.ENDER_MAGIC)
            || source.is(WAICDamageTypeTags.HOLY_MAGIC)
            || source.is(WAICDamageTypeTags.DRAGON_FIRE)
            || source.is(WAICDamageTypeTags.DRAGON_ICE)
            || source.is(WAICDamageTypeTags.DRAGON_LIGHTNING);
    }

    /**
     * 判断是否为远程伤害
     * @param source 伤害源
     * @return 是否为远程伤害
     */
    public static boolean isRangedDamage(DamageSource source) {
        return source.is(DamageTypeTags.IS_PROJECTILE)
            || source.is(DamageTypes.SONIC_BOOM)
            || (source.getDirectEntity() != null && !isMagicDamage(source));
    }

    /**
     * 判断是否为近战伤害
     * @param source 伤害源
     * @return 是否为近战伤害
     */
    public static boolean isMeleeDamage(DamageSource source) {
        return getDamageCategory(source) == DamageCategory.MELEE;
    }

    /**
     * 判断是否为物理伤害（非魔法）
     * @param source 伤害源
     * @return 是否为物理伤害
     */
    public static boolean isPhysicalDamage(DamageSource source) {
        return !isMagicDamage(source);
    }

    /**
     * 获取伤害类型的友好名称
     * @param category 伤害分类
     * @return 友好名称
     */
    public static String getCategoryName(DamageCategory category) {
        return switch (category) {
            case MELEE -> "近战";
            case RANGED -> "远程";
            case MAGIC -> "魔法";
        };
    }
}
```

### 标签管理器设计

```java
// WAICDamageTypeTags.java - 伤害类型标签管理
package net.zhaiji.who_am_i_core.manager;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICDamageTypeTags {

    // Irons Spells n Spellbooks 学派标签
    public static final TagKey<DamageType> FIRE_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "fire_magic"));

    public static final TagKey<DamageType> ICE_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "ice_magic"));

    public static final TagKey<DamageType> LIGHTNING_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "lightning_magic"));

    public static final TagKey<DamageType> NATURE_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "nature_magic"));

    public static final TagKey<DamageType> BLOOD_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "blood_magic"));

    public static final TagKey<DamageType> EVOCATION_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "evocation_magic"));

    public static final TagKey<DamageType> ELDRITCH_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "eldritch_magic"));

    public static final TagKey<DamageType> ENDER_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "ender_magic"));

    public static final TagKey<DamageType> HOLY_MAGIC =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "holy_magic"));

    // Ice and Fire 龙类标签
    public static final TagKey<DamageType> DRAGON_FIRE =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon_fire"));

    public static final TagKey<DamageType> DRAGON_ICE =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon_ice"));

    public static final TagKey<DamageType> DRAGON_LIGHTNING =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("iceandfire", "dragon_lightning"));

    // 自定义分类标签（可选，用于更精细的分类）
    public static final TagKey<DamageType> MELEE_DAMAGE =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(WhoAmICore.MODID, "melee_damage"));

    public static final TagKey<DamageType> RANGED_DAMAGE =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(WhoAmICore.MODID, "ranged_damage"));

    public static final TagKey<DamageType> MAGIC_DAMAGE =
        TagKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(WhoAmICore.MODID, "magic_damage"));
}
```

---

## 平衡性考虑

### 伤害类型克制关系

| 攻击类型 | 对近战 | 对远程 | 对魔法 |
|----------|--------|--------|--------|
| 近战伤害 | 100% | 100% | 80% |
| 远程伤害 | 100% | 100% | 100% |
| 魔法伤害 | 120% | 100% | 100% |

**设计说明**:
- 魔法伤害对重甲敌人（近战类型）有额外克制效果（+20%）
- 近战伤害对魔法类型略微降低（80%）
- 远程伤害对所有类型平衡

---

## 扩展性设计

### 添加新伤害类型的流程

#### 步骤1: 定义新伤害类型

在对应模组的数据包中定义新的伤害类型：

```json
// data/your_mod/damage_type/necrotic.json
{
  "message_id": "your_mod.necrotic",
  "exhaustion": 0.1,
  "scaling": "when_caused_by_living_non_player"
}
```

#### 步骤2: 创建对应的标签

如果新伤害类型需要特殊处理，在 `WAICDamageTypeTags` 中添加：

```java
public static final TagKey<DamageType> NECROTIC_MAGIC =
    TagKey.create(Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath("your_mod", "necrotic"));
```

#### 步骤3: 映射到三大分类之一

在 `DamageTypeUtil` 中添加判定逻辑：

```java
public static boolean isNecroticDamage(DamageSource source) {
    return source.is(WAICDamageTypeTags.NECROTIC_MAGIC);
}

// 在 isMagicSchoolDamage 中添加
|| source.is(WAICDamageTypeTags.NECROTIC_MAGIC)
```

#### 步骤4: 更新文档表格

在本设计文档的映射表中添加新伤害类型的信息。

### 未来可能的扩展

#### 伤害类型子分类

可以进一步细分伤害类型，例如：

**火魔法子分类**:
- 火焰魔法 (fire_magic)
- 岩浆魔法 (magma_magic)
- 地狱火魔法 (hellfire_magic)

**效果**: 同为火系，但可能相互克制或增强。

#### 伤害类型相克系统

类似于元素相克：

```
水克火 → 水魔法伤害对火系敌人 +50%
火克冰 → 火魔法伤害对冰系敌人 +50%
冰克自然 → 冰魔法伤害对自然系敌人 +50%
```

#### 复合伤害类型

某些攻击可能同时具有多种伤害类型：

```java
public enum DamageCategory {
    MELEE,
    RANGED,
    MAGIC,
    FIRE_MAGIC,      // 火焰魔法 = MAGIC + FIRE
    ICE_MAGIC,       // 冰霜魔法 = MAGIC + ICE
    PHYSICAL_MELEE,  // 物理近战 = MELEE + PHYSICAL
    PHYSICAL_RANGED, // 物理远程 = RANGED + PHYSICAL
}
```

---

## 实现阶段

### 阶段1: 基础系统 (1周)

- 创建 `DamageTypeUtil` 工具类
- 创建 `WAICDamageTypeTags` 标签管理
- 实现三大伤害类型的判定逻辑
- 编写单元测试验证判定准确性

### 阶段2: 模组兼容测试 (1周)

- 测试各模组伤害类型的判定准确性
- 验证标签映射的正确性
- 修复兼容性问题

### 阶段3: 平衡与优化 (1周)

- 测试伤害类型克制系统
- 性能优化和bug修复
- 完善文档

---

## 总结

### 设计优势

1. **统一标准**: 所有模组伤害类型都有统一的分类
2. **易于扩展**: 新伤害类型可以轻松添加
3. **平衡工具**: 提供伤害类型克制/平衡的设计框架
4. **技术可行**: 基于现有伤害类型标签系统
5. **模组兼容**: 支持所有主流模组的伤害类型

### 设计风险

1. **标签依赖**: 依赖模组正确使用伤害类型标签
2. **平衡复杂**: 多种伤害类型可能使平衡变得复杂
3. **维护成本**: 新模组需要添加对应的标签映射

### 后续工作

1. **测试验证**: 测试各模组伤害类型的判定准确性
2. **平衡调整**: 根据测试结果调整数值
3. **UI设计**: 设计直观的伤害类型显示（如需要）
4. **文档完善**: 为开发者和用户提供伤害类型系统说明

---

## 附录：伤害类型速查表

### 按分类速查

**近战伤害**:
- player_attack, mob_attack, thorns
- organ_loss, open_chest
- chesed_attack, chesed_roll_attack, chesed_earthquake, chesed_falling_block
- malkuth_cowardice, malkuth_chainpunch, malkuth_earthshatter
- geburah_earthquake, geburah_justice_hammer
- gorgon

**远程伤害**:
- arrow, trident, fireball, sonic_boom, wind_charge
- chesed_electric_sphere, chesed_vertical_ray, chesed_rock_attack
- malkuth_cannons, malkuth_slashes, malkuth_hellshaper, malkuth_impaling_doom, malkuth_side_rocks, malkuth_tsars_wrath
- geburah_judgement_ball, geburah_ray_strike, geburah_laser_strike
- danmaku, danmaku_ender_killer, bullet
- anvilcraft:laser

**魔法伤害**:
- magic, indirect_magic, on_fire
- fire_magic, ice_magic, lightning_magic, nature_magic, blood_magic
- evocation_magic, eldritch_magic, ender_magic, holy_magic
- dragon_fire, dragon_ice, dragon_lightning
- lost_in_time, bonus
- heartstop, dragon_breath_pool, blood_cauldron, fire_field, poison_cloud
- sinned_too_much

### 按模组速查

| 模组 | 近战 | 远程 | 魔法 | 备注 |
|------|------|------|------|------|
| 原版 | ✓ | ✓ | ✓ | 基础伤害类型 |
| Irons Spells | - | - | ✓ | 纯魔法模组 |
| Ice and Fire | ✓ | - | ✓ | 龙类魔法+石化 |
| FDBosses | ✓ | ✓ | ✓ | 三大BOSS群 |
| CCB | ✓ | - | - | 器官伤害 |
| Touhou | ✓ | ✓ | - | 弹幕系统 |
| Mowzie's | ✓ | ✓ | - | 使用原版类型 |
| AnvilCraft | - | ✓ | ✓ | 激光+时间魔法 |

---

**文档结束**

**状态**: 设计完成，可直接用于开发

**版本历史**:
- v1.1: 更新伤害类型映射 - 添加遗漏的Iron's Spells魔法类型(blood_magic, eldritch_magic, ender_magic, holy_magic)，添加FDBosses完整伤害类型列表，添加Ice and Fire新类型(gorgon, bonus)，添加Touhou弹幕类型，添加AnvilCraft激光类型，修正Mowzie's Mobs无自定义伤害类型说明，移除不存在的CCB伤害类型
- v1.0: 初始设计 - 三大伤害类型分类体系，完整的模组映射表，技术实现方案

# 伤害类型与伤害类型标签完整文档

**项目**: Who Am I Core
**整理日期**: 2026-03-17
**版本**: 1.0
**状态**: 整理完成

---

## 概述

本文档整理了所有工作区中定义的伤害类型（DamageType）和伤害类型标签（DamageTypeTags），为模组开发提供完整的参考。

---

## 一、原版伤害类型标签

Minecraft原版定义的伤害类型标签位于 `net.minecraft.tags.DamageTypeTags`：

| 标签名称                         | 说明      |
|------------------------------|---------|
| `BYPASSES_ARMOR`             | 忽略护甲    |
| `BYPASSES_INVULNERABILITY`   | 忽略无敌    |
| `BYPASSES_MAGIC`             | 忽略魔法保护  |
| `BYPASSES_SHIELD`            | 忽略盾牌    |
| `BYPASSES_WOLF_ARMOR`        | 忽略狼护甲   |
| `BYPASSES_RESISTANCE`        | 忽略抗性    |
| `NO_KNOCKBACK`               | 无击退     |
| `NO_IMPACT`                  | 无冲击     |
| `IS_FIRE`                    | 是火焰伤害   |
| `IS_DROWNING`                | 是溺水伤害   |
| `IS_FALL`                    | 是跌落伤害   |
| `IS_FREEZING`                | 是冻结伤害   |
| `IS_PROJECTILE`              | 是投射物伤害  |
| `ALWAYS_HURTS_ENDER_DRAGONS` | 总是伤害末影龙 |
| `IS_EXPLOSION`               | 是爆炸伤害   |
| `MOB_ATTACK`                 | 生物攻击    |
| `IS_PHYSICAL`                | 是物理伤害   |

---

## 二、NeoForge伤害类型标签

NeoForge提供的扩展标签位于 `net.neoforged.neoforge.common.Tags.DamageTypes`：

| 标签名称       | 说明    |
|------------|-------|
| `IS_MAGIC` | 是魔法伤害 |

---

## 三、各模组伤害类型

### 1. Chest Cavity Beyond

**模组ID**: `chestcavitybeyond`
**注册文件**: `net.zhaiji.chestcavitybeyond.register.InitDamageType`

| 伤害类型         | 资源位置                           | 标签                                                                                      |
|--------------|--------------------------------|-----------------------------------------------------------------------------------------|
| `organ_loss` | `chestcavitybeyond:organ_loss` | BYPASSES_ARMOR, BYPASSES_SHIELD, BYPASSES_WOLF_ARMOR, BYPASSES_RESISTANCE, NO_KNOCKBACK |
| `open_chest` | `chestcavitybeyond:open_chest` | BYPASSES_ARMOR, BYPASSES_SHIELD, BYPASSES_WOLF_ARMOR, NO_KNOCKBACK                      |

**说明**:

- `organ_loss` - 器官流失伤害，完全忽略防御和抗性
- `open_chest` - 打开胸腔伤害，忽略大部分防御

---

### 2. Ice and Fire (CE)

**模组ID**: `iceandfire`
**注册文件**: `com.iafenvoy.iceandfire.registry.IafDamageTypes`

| 伤害类型               | 资源位置                          | 说明      |
|--------------------|-------------------------------|---------|
| `bonus`            | `iceandfire:bonus`            | 奖励伤害    |
| `gorgon`           | `iceandfire:gorgon`           | 美杜莎石化伤害 |
| `dragon_fire`      | `iceandfire:dragon_fire`      | 龙火伤害    |
| `dragon_ice`       | `iceandfire:dragon_ice`       | 龙冰伤害    |
| `dragon_lightning` | `iceandfire:dragon_lightning` | 龙雷伤害    |

**说明**:

- 龙类伤害类型（火、冰、雷）用于不同种类的龙攻击
- `gorgon` 用于美杜莎的石化攻击
- `bonus` 用于额外的奖励性伤害

---

### 3. Irons Spells n Spellbooks

**模组ID**: `irons_spellbooks`
**注册文件**: `io.redspace.ironsspellbooks.damage.ISSDamageTypes`

#### 魔法学派伤害类型

| 伤害类型              | 资源位置                               | 标签                                           |
|-------------------|------------------------------------|----------------------------------------------|
| `fire_magic`      | `irons_spellbooks:fire_magic`      | IS_MAGIC, `irons_spellbooks:fire_magic`      |
| `ice_magic`       | `irons_spellbooks:ice_magic`       | IS_MAGIC, `irons_spellbooks:ice_magic`       |
| `lightning_magic` | `irons_spellbooks:lightning_magic` | IS_MAGIC, `irons_spellbooks:lightning_magic` |
| `holy_magic`      | `irons_spellbooks:holy_magic`      | IS_MAGIC, `irons_spellbooks:holy_magic`      |
| `ender_magic`     | `irons_spellbooks:ender_magic`     | IS_MAGIC, `irons_spellbooks:ender_magic`     |
| `blood_magic`     | `irons_spellbooks:blood_magic`     | IS_MAGIC, `irons_spellbooks:blood_magic`     |
| `evocation_magic` | `irons_spellbooks:evocation_magic` | IS_MAGIC, `irons_spellbooks:evocation_magic` |
| `eldritch_magic`  | `irons_spellbooks:eldritch_magic`  | IS_MAGIC, `irons_spellbooks:eldritch_magic`  |
| `nature_magic`    | `irons_spellbooks:nature_magic`    | IS_MAGIC, `irons_spellbooks:nature_magic`    |

#### 特殊伤害类型

| 伤害类型                 | 资源位置                                  | 标签             |
|----------------------|---------------------------------------|----------------|
| `blood_cauldron`     | `irons_spellbooks:blood_cauldron`     | -              |
| `heartstop`          | `irons_spellbooks:heartstop`          | BYPASS_EVASION |
| `dragon_breath_pool` | `irons_spellbooks:dragon_breath_pool` | -              |
| `fire_field`         | `irons_spellbooks:fire_field`         | -              |
| `poison_cloud`       | `irons_spellbooks:poison_cloud`       | -              |

#### 模组自定义标签

| 标签名称                                | 说明     |
|-------------------------------------|--------|
| `irons_spellbooks:bypass_evasion`   | 忽略闪避   |
| `irons_spellbooks:long_cast_ignore` | 长施法忽略  |
| `irons_spellbooks:fire_magic`       | 火焰学派魔法 |
| `irons_spellbooks:ice_magic`        | 冰霜学派魔法 |
| `irons_spellbooks:lightning_magic`  | 雷电学派魔法 |
| `irons_spellbooks:holy_magic`       | 神圣学派魔法 |
| `irons_spellbooks:ender_magic`      | 末影学派魔法 |
| `irons_spellbooks:blood_magic`      | 血魔法派魔法 |
| `irons_spellbooks:evocation_magic`  | 召唤学派魔法 |
| `irons_spellbooks:eldritch_magic`   | 古怪学派魔法 |
| `irons_spellbooks:nature_magic`     | 自然学派魔法 |

**说明**:

- 所有学派魔法伤害都标记为 `IS_MAGIC`
- `heartstop` 有特殊的 `bypass_evasion` 标签
- 所有魔法学派伤害类型都属于 `IS_MAGIC` 标签组

---

### 4. FDBosses

**模组ID**: `fdbosses`
**注册文件**: `com.finderfeed.fdbosses.init.BossDamageSources`

#### Chesed（慈悲）BOSS 伤害类型

| 伤害类型                     | 资源位置                            | 说明      |
|--------------------------|---------------------------------|---------|
| `chesed_attack`          | `fdbosses:chesed_attack`        | 慈悲近战攻击  |
| `chesed_lor_attack`      | `fdbosses:chesed_lor_attack`    | LOR彩蛋近战 |
| `chesed_ba_attack`       | `fdbosses:chesed_ba_attack`     | BA彩蛋近战  |
| `chesed_electric_sphere` | `fdbosses:electric_sphere`      | 电球投射物   |
| `chesed_falling_block`   | `fdbosses:chesed_falling_block` | 落块伤害    |
| `chesed_vertical_ray`    | `fdbosses:chesed_vertical_ray`  | 垂直射线    |
| `chesed_earthquake`      | `fdbosses:chesed_earthquake`    | 地震伤害    |
| `chesed_roll_attack`     | `fdbosses:chesed_roll_attack`   | 翻滚攻击    |
| `chesed_rock_attack`     | `fdbosses:chesed_rock_attack`   | 岩石攻击    |

#### Malkuth（王国）BOSS 伤害类型

| 伤害类型                    | 资源位置                             | 说明    |
|-------------------------|----------------------------------|-------|
| `malkuth_cowardice`     | `fdbosses:malkuth_cowardice`     | 懦夫惩罚  |
| `malkuth_cannons`       | `fdbosses:malkuth_cannons`       | 火炮    |
| `malkuth_chainpunch`    | `fdbosses:malkuth_chainpunch`    | 连环拳   |
| `malkuth_earthshatter`  | `fdbosses:malkuth_earthshatter`  | 地面破碎  |
| `malkuth_hellshaper`    | `fdbosses:malkuth_hellshaper`    | 地狱塑形者 |
| `malkuth_impaling_doom` | `fdbosses:malkuth_impaling_doom` | 穿刺毁灭  |
| `malkuth_side_rocks`    | `fdbosses:malkuth_side_rocks`    | 侧翼岩石  |
| `malkuth_slashes`       | `fdbosses:malkuth_slashes`       | 斩击弹幕  |
| `malkuth_tsars_wrath`   | `fdbosses:malkuth_tsars_wrath`   | 沙皇之怒  |

#### Geburah（严厉）BOSS 伤害类型

| 伤害类型                     | 资源位置                              | 说明   |
|--------------------------|-----------------------------------|------|
| `geburah_judgement_ball` | `fdbosses:geburah_judgement_ball` | 审判之球 |
| `geburah_ray_strike`     | `fdbosses:geburah_ray_strike`     | 射线打击 |
| `geburah_laser_strike`   | `fdbosses:geburah_laser_strike`   | 激光打击 |
| `geburah_earthquake`     | `fdbosses:geburah_earthquake`     | 地震   |
| `geburah_justice_hammer` | `fdbosses:geburah_justice_hammer` | 正义之锤 |
| `sinned_too_much`        | `fdbosses:sinned_too_much`        | 罪孽深重 |

**说明**:

- FDBosses定义了32种不同的伤害类型，对应3个BOSS的不同攻击方式
- 大部分伤害类型未使用特殊标签，依赖默认行为

---

### 5. Touhou Little Maid

**模组ID**: `touhou_little_maid`
**注册文件**: `com.github.tartaricacid.touhoulittlemaid.init.InitDamage`

| 伤害类型                   | 资源位置                                      | 标签                                        |
|------------------------|-------------------------------------------|-------------------------------------------|
| `danmaku`              | `touhou_little_maid:danmaku`              | IS_PROJECTILE                             |
| `danmaku_ender_killer` | `touhou_little_maid:danmaku_ender_killer` | IS_PROJECTILE, ALWAYS_HURTS_ENDER_DRAGONS |

**说明**:

- `danmaku` - 普通弹幕伤害，标记为投射物
- `danmaku_ender_killer` - 特殊弹幕，可以伤害末影龙

---

### 6. AnvilCraft

**模组ID**: `anvilcraft`
**注册文件**: `dev.dubhe.anvilcraft.init.entity.ModDamageTypes`

| 伤害类型           | 资源位置                      | 标签                                                          | 效果        |
|----------------|---------------------------|-------------------------------------------------------------|-----------|
| `laser`        | `anvilcraft:laser`        | -                                                           | 燃烧效果(0.1) |
| `lost_in_time` | `anvilcraft:lost_in_time` | BYPASSES_ARMOR, BYPASSES_RESISTANCE, NO_KNOCKBACK, IS_MAGIC | -         |

**说明**:

- `laser` - 激光伤害，带有燃烧效果
- `lost_in_time` - 时间迷失伤害，完全忽略防御，标记为魔法伤害

---

### 7. Mowzie's Mobs

**模组ID**: `mowziesmobs`

**自定义伤害类型**: 无

**说明**:

- Mowzie's Mobs 没有定义自己的伤害类型
- 所有生物攻击使用原版的 `mob_attack`
- 投射物攻击使用原版伤害类型或自定义投射物处理
- 混合伤害处理使用 `DamageUtil.dealMixedDamage()` 方法

---

### 8. Who Am I Core

**模组ID**: `who_am_i_core`

**自定义伤害类型**: 无（当前版本）

**说明**:

- 本模组当前未定义自定义伤害类型
- 使用原版和其他模组的伤害类型
- 可参考 `damage-type-classification.md` 了解伤害类型分类体系

---

## 四、伤害类型标签总览

### 原版标签

```java
// 通用防御相关
BYPASSES_ARMOR           // 忽略护甲
    BYPASSES_SHIELD          // 忽略盾牌
BYPASSES_WOLF_ARMOR      // 忽略狼护甲
    BYPASSES_RESISTANCE      // 忽略抗性
BYPASSES_INVULNERABILITY // 忽略无敌
    BYPASSES_MAGIC           // 忽略魔法保护

// 效果相关
NO_KNOCKBACK             // 无击退
    NO_IMPACT                // 无冲击

// 类型判定
IS_FIRE                  // 是火焰伤害
    IS_DROWNING              // 是溺水伤害
IS_FALL                  // 是跌落伤害
    IS_FREEZING              // 是冻结伤害
IS_PROJECTILE            // 是投射物伤害
    IS_EXPLOSION             // 是爆炸伤害
IS_PHYSICAL              // 是物理伤害

// 特殊目标
    ALWAYS_HURTS_ENDER_DRAGONS // 总是伤害末影龙

// 攻击者类型
MOB_ATTACK               // 生物攻击
```

### NeoForge扩展标签

```java
IS_MAGIC                 // 是魔法伤害
```

### Irons Spells n Spellbooks 自定义标签

```java
// 功能性标签
irons_spellbooks:bypass_evasion    // 忽略闪避
irons_spellbooks:long_cast_ignore  // 长施法忽略

// 学派标签
irons_spellbooks:fire_magic        // 火焰学派
irons_spellbooks:ice_magic         // 冰霜学派
irons_spellbooks:lightning_magic   // 雷电学派
irons_spellbooks:holy_magic        // 神圣学派
irons_spellbooks:ender_magic       // 末影学派
irons_spellbooks:blood_magic       // 血魔法派
irons_spellbooks:evocation_magic   // 召唤学派
irons_spellbooks:eldritch_magic    // 古怪学派
irons_spellbooks:nature_magic      // 自然学派
```

---

## 五、伤害类型分类速查

根据 `damage-type-classification.md` 中的分类体系：

### 近战伤害

- player_attack
- mob_attack
- thorns
- organ_loss
- open_chest
- gorgon
- chesed_attack/chesed_roll_attack/chesed_earthquake/chesed_falling_block
- malkuth_cowardice/malkuth_chainpunch/malkuth_earthshatter
- geburah_earthquake/geburah_justice_hammer

### 远程伤害

- arrow/trident/fireball/sonic_boom/wind_charge
- chesed_electric_sphere/chesed_vertical_ray/chesed_rock_attack
- malkuth_cannons/malkuth_slashes/malkuth_hellshaper/malkuth_impaling_doom
- geburah_judgement_ball/geburah_ray_strike/geburah_laser_strike
- danmaku/danmaku_ender_killer
- anvilcraft:laser

### 魔法伤害

- magic/indirect_magic/on_fire
- 所有 irons_spellboards 魔法学派伤害
- dragon_fire/dragon_ice/dragon_lightning
- lost_in_time/bonus
- heartstop/dragon_breath_pool/blood_cauldron/fire_field/poison_cloud
- sinned_too_much

---

## 六、使用建议

### 1. 判断伤害类型

```java
// 判断是否为魔法伤害
boolean isMagic = source.is(Tags.DamageTypes.IS_MAGIC)
                  || source.is(DamageTypes.MAGIC);

// 判断是否为投射物
boolean isProjectile = source.is(DamageTypeTags.IS_PROJECTILE);

// 判断是否忽略护甲
boolean bypassArmor = source.is(DamageTypeTags.BYPASSES_ARMOR);

// 判断特定学派魔法
boolean isFireMagic = source.is(
    ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "fire_magic")
);
```

### 2. 创建自定义伤害类型

```java
// 1. 定义ResourceKey
public static final ResourceKey<DamageType> MY_DAMAGE =
    ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("mymod", "my_damage"));

// 2. 注册到bootstrap
context.

register(
    MY_DAMAGE, new DamageType("mymod.my_damage",
    DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f
));

// 3. 创建伤害源
Registry<DamageType> registry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
DamageSource source = new DamageSource(registry.getHolderOrThrow(MY_DAMAGE), attacker);

// 4. (可选) 添加标签
// 在 datagen 中创建标签生成器并添加对应标签
```

### 3. 创建自定义伤害类型标签

```java
// 1. 定义TagKey
public static final TagKey<DamageType> MY_TAG =
    TagKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath("mymod", "my_tag")
    );

// 2. 在datagen中添加标签
@Override
protected void addTags(HolderLookup.Provider provider) {
    tag(MY_TAG).add(MyDamageTypes.MY_DAMAGE);
}
```

---

## 七、总结

### 统计数据

| 模组                        | 伤害类型数量  | 自定义标签数量 |
|---------------------------|---------|---------|
| 原版                        | ~30     | ~20     |
| Chest Cavity Beyond       | 2       | 0       |
| Ice and Fire              | 5       | 0       |
| Irons Spells n Spellbooks | 13      | 11      |
| FDBosses                  | 32      | 0       |
| Touhou Little Maid        | 2       | 0       |
| AnvilCraft                | 2       | 0       |
| Mowzie's Mobs             | 0       | 0       |
| **总计**                    | **86+** | **31+** |

### 关键发现

1. **Irons Spells n Spellbooks** 拥有最完善的标签体系，为每个魔法学派定义了专用标签
2. **FDBosses** 定义了最多的伤害类型（32种），但未使用自定义标签
3. **Mowzie's Mobs** 完全依赖原版伤害类型系统
4. 大多数模组倾向于创建新的伤害类型而非使用原版标签
5. 魔法伤害类型的标签化程度最高，便于统一处理

### 最佳实践建议

1. **优先使用原版标签** - 在可能的情况下，使用原版标签（如 `IS_MAGIC`, `IS_PROJECTILE`）
2. **为学派/系列创建标签** - 如 Irons Spells 为每个魔法学派创建标签，便于统一处理
3. **清晰的命名规范** - 使用 `modid:damage_type` 格式，避免冲突
4. **适当的标签使用** - 为具有相同特性的伤害类型创建标签（如 `bypass_evasion`）
5. **文档同步更新** - 添加新伤害类型时更新相关文档

---

**文档版本历史**:

- v1.0 (2026-03-17): 初始版本，整理所有工作区伤害类型和标签

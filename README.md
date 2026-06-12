# Who Am I: Core - 项目架构文档

## 项目概述

项目名：**Who Am I: Core**（我非我：核心）
- **Mod ID**：`who_am_i_core`
- **包名**：`net.zhaiji.who_am_i_core`
- **Minecraft**：1.21.1
- **NeoForge**：21.1.219
- **ModDevGradle**：2.0.140
- **Parchment Mappings**：2024.11.17
- **Java**：21
- **版本**：1.0.7

前置模组：**Chest Cavity Beyond**（胸腔：超越），本模组为 CCB 的 Addon，核心机制是为不提供胸腔的原版/其他 mod 的生物添加可植入胸腔的器官。

---

## 项目结构

```
src/main/java/net/zhaiji/who_am_i_core/
├── WhoAmICore.java              # 主模组入口（@Mod），完成注册和事件总线绑定
├── WhoAmICoreClient.java        # 客户端入口（@Mod Dist.CLIENT），注册客户端事件
│
├── api/
│   └── UseCondition.java     # 可使用条件 API。Builder 模式定义哪些物品可被使用及使用效果
│
├── attachment/
│   └── HumoursData.java         # 四体液学说数据（Attachment）：血液/黄胆汁/黑胆汁/粘液，各有当前值+上限
│
├── client/
│   ├── event/
│   │   ├── ClientEventManager.java  # 客户端事件总线注册（双总线：modBus + gameBus）
│   │   └── ClientEventHandler.java  # 客户端事件处理器实现
│   ├── overlay/
│   │   └── HumoursOverlay.java      # 四体液 HUD 叠加层
│   └── tooltip/
│       └── ClientFrankensteinHeartTooltip.java  # 弗兰肯斯坦心脏客户端工具提示
│
├── config/
│   └── WhoAmIClientConfig.java  # 客户端配置
│
├── datagen/
│   ├── DataGenHandler.java      # 数据生成入口（监听 GatherDataEvent）
│   ├── ItemModelProvider.java   # 物品模型 JSON（遍历 WAICItem.ITEM.getEntries()）
│   ├── ItemTagProvider.java     # 物品标签 JSON（器官类型标签 + 自定义标签）
│   ├── LanguageProvider.java    # 双语语言 JSON（EN_US + ZH_CN）
│   └── DamageTypeTagsProvider.java  # 伤害类型标签
│
├── effect/
│   └── DragonPowerEffect.java   # 龙之力药水效果（全部使用同一类，不同颜色区分）
│
├── entity/
│   └── HydraVenomBreathProjectile.java  # 九头蛇毒物吐息投射物实体
│
├── event/
│   ├── CommonEventManager.java  # 服务端事件总线注册（双总线：modBus + gameBus）
│   └── CommonEventHandler.java  # 核心业务逻辑：器官注册、伤害处理、效果监听、胸腔注册等
│
├── inventory/tooltip/
│   └── FrankensteinHeartTooltip.java   # 弗兰肯斯坦心脏服务端工具提示
│
├── item/
│   ├── ClothTeddyBearItem.java         # 布织泰迪熊特殊物品
│   ├── DragonBloodPreparationItem.java # 龙之血药剂物品
│   ├── FrankensteinItem.java           # 弗兰肯斯坦收纳袋物品（Bundle 类）
│   └── PaletteItem.java                # 调色盘特殊物品
│
├── manager/
│   ├── UseConditionManager.java         # 可使用条件管理器
│   ├── IceAndFireChestCavityTypeManager.java  # 龙类和九头蛇胸腔类型定义
│   ├── WAICChestCavityTypeManager.java     # 幻想种胸腔类型定义
│   ├── WAICDamageTagManager.java           # 伤害类型标签常量（IS_MELEE）
│   └── WAICItemTagManager.java             # 自定义物品标签常量（MAGIC, MECHANICAL, SUMMON, UNIQUE, 龙种, ICE, FIRE, LESION, CURSED_GOLD, EMBER_METAL, CLOTH_ORGAN 等）
│
├── mixin/
│   ├── ApplyBonusCountMixin.java           # 抢夺/时运奖励次数（使用 WAIC 属性）
│   ├── ChestCavityDataMixin.java           # 胸腔数据 mixin
│   ├── EnchantedCountIncreaseFunctionMixin.java  # 附魔数量增加函数
│   ├── EntityUmvuthanaFollowerToPlayerMixin.java # 乌姆塔纳追随者关系转玩家
│   ├── ItemStackMixin.java                 # ItemStack mixin（UseCondition 使用流程支持）
│   ├── LivingEntityMixin.java              # LivingEntity mixin
│   ├── MalkuthCannonEntityMixin.java       # FDBosses 王国炮台 mixin
│   ├── MalkuthWeaknessHandlerMixin.java    # 王国弱点处理器 mixin
│   ├── MalkuthWeaknessOverlayMixin.java    # 王国弱点 HUD mixin
│   └── PoisonMobEffectMixin.java           # 中毒效果 mixin（Access Transformer 访问）
│
├── mixinapi/
│   └── IEntityUmvuthanaFollowerToPlayer.java  # Mixin API 接口
│
├── organ/                     # ⚠ 核心目录：所有器官定义
│   ├── WAICOrgans.java        # 本模组核心器官（墨水、颜料、木质、弗兰肯斯坦、病变、九狱、双子魔眼、拟态、幻想种、布织、独立器官）
│   ├── IceAndFireOrgans.java  # 冰与火：火龙/冰龙/电龙器官、悚怖器官、九头蛇器官
│   ├── MowziesMobOrgans.java  # Mowzie's Mobs：胸中新星、制御棒、衰老器官、禅心、泥峭系列
│   ├── FDBossesOrgans.java    # FDBosses 逆卡巴拉：王国战士之心、王国、慈悲、严厉
│   ├── AnvilCraftOrgans.java  # AnvilCraft：皇家钢、诅咒金、余烬金属、浮霜金属、超限合金
│   ├── CataclysmOrgans.java   # 灾变：利维坦、冥行武弁、咒翼灵骸、斯库拉
│   └── IronSpellOrgans.java   # Iron's Spells：死灵法师、死者之王
│
├── register/
│   ├── WAICAttachment.java    # Attachment 注册器（四体液 HUMOURS）
│   ├── WAICAttribute.java     # 自定义属性注册器（TEMPERATURE, BLOCK, COUNTER_ATTACK, HEAL, MELEE_DAMAGE, RANGED_DAMAGE, MAGIC_DAMAGE, 各种百分比属性, LOOTING, FORTUNE）
│   ├── WAICCreativeModeTab.java  # 创造模式标签页
│   ├── WAICEffect.java        # 药水效果注册器（4种龙之力）
│   ├── WAICEntity.java        # 实体注册器（九头蛇毒物吐息）
│   └── WAICItem.java          # ⚠ 核心注册器：所有物品/器官的 DeferredRegister，static 块中调用所有 Organs.register()
│
├── task/
│   ├── ChestNovaTask.java     # 胸中新星任务（燃烧周围器官、召唤乌姆塔纳追随者）
│   ├── DragonBreathCastingTask.java # 龙息施法任务
│   ├── HydraLungBreathTask.java    # 九头蛇肺吐息任务
│   └── StraightIntestineTask.java  # 直肠子掉落食物任务
│
└── util/
    ├── OrganUtil.java              # 跨 mod 通用工具（邻接槽位计算、无情机制、温度系统、弗兰肯斯坦聚合、几率判定等）
    ├── WAICOrganUtil.java          # WAIC 器官工具类（墨水、病变、经验之心、布织泰迪熊、猩红、九狱、电荷系统、FDBosses 等）
    ├── WAICTooltipUtil.java        # 工具提示生成工具
    ├── AnvilCraftOrganUtil.java    # AnvilCraft 器官工具类（浮霜金属、超限合金）
    ├── CataclysmOrganUtil.java     # 灾变器官工具类（涛浪提灯、风暴脊柱/肋骨、焰魔、巨兽、远古工厂、咒翼灵骸等）
    ├── CompanionsOrganUtil.java    # Companions 器官工具类（教宗心脏/脾脏/阑尾）
    ├── IceAndFireOrganUtil.java    # 冰火器官工具类（龙宝玉、吐息、悚怖命匣、九头蛇全套技能）
    ├── IronSpellOrganUtil.java     # 铁魔法器官工具类（腐败魂灯、尸王脊柱/肋骨）
    └── MowziesMobOrganUtil.java    # Mowzie 器官工具类（护心镜、胸中新星、泥峭等）
```

---

## 核心架构

### 双总线事件模式（Manager + Handler 分层）

本模组采用 `EventManager` + `EventHandler` 分层架构：
- **Manager**：仅负责通过 `@SubscribeEvent` 或 `addListener` 将 Handler 方法绑定到对应总线
- **Handler**：纯静态方法，实现具体业务逻辑
- **双总线**：`modEventBus`（模组加载事件）+ `gameBus`/`NeoForge.EVENT_BUS`（游戏运行时事件）

### 器官定义模式

所有器官通过 `Organ.builder()` 构建，最终注册到 `WAICItem.ITEM`（一个 `DeferredRegister<Item>`）。

关键方法：
- `Organ.builder()` — 默认构建器
- `Organ.builder(Supplier<Item>)` — 指定自定义 Item 类
- `Organ.builder(Item existing)` — 将已有物品包装为器官（如 IafItems 的心脏）
- `.addValueAttribute(Holder<Attribute>, double)` — 添加属性加成
- `.totalMultipliedAttribute(Holder<Attribute>, double)` — 总乘算属性
- `.baseMultipliedAttribute(Holder<Attribute>, double)` — 基础乘算属性
- `.skill(SkillFunction)` — 设置主动技能（右键长按释放）
- `.skillOnCooldown(CooldownFunction)` — 技能冷却中回调
- `.cooldown(int)` — 技能冷却时间（tick）
- `.attack(BiConsumer)` — 攻击时回调
- `.hurt(BiConsumer)` — 受伤时回调
- `.heal(BiConsumer)` — 治疗时回调
- `.tick(TickFunction)` — 每 tick 回调
- `.incomingDamage(IncomingDamageConsumer)` — 即将受伤回调
- `.modifier(ModifierFunction)` — 动态属性修改器
- `.totalMultiplierModifier(ModifierFunction)` — 总乘算动态属性
- `.chestCavityOpen/skill/close(Consumer)` — 胸腔生命周期回调
- `.added/removed(Consumer)` — 器官添加/移除回调
- `.otherChange(OtherChangeConsumer)` — 其他槽位器官变化回调
- `.skillTooltip(TooltipFunction)` / `.descriptionTooltip(TooltipFunction)` — 工具提示
- `.properties(Function)` — 自定义 Item.Properties

器官在 `WAICItem` 的 `static {}` 块中按顺序注册：
1. WAICOrgans.register()
2. MowziesMobOrgans.register()
3. IceAndFireOrgans.register()
4. FDBossesOrgans.register()
5. AnvilCraftOrgans.register()
6. CataclysmOrgans.register()
7. IronSpellOrgans.register()

每个 Organ 类提供一个空的 `register()` 方法，用于触发类加载和字段初始化。

### IceAndFire 特殊处理

冰与火的龙类心脏（火龙/冰龙/电龙/九头蛇）使用 IafItems 中已有的物品作为器官。这些在 `IceAndFireOrgans.setupOrgans()` 中通过 `Organ.builder(IafItems.XXX_HEART.get()).build()` 包装，该方法在 `OrganRegisterEvent` 中调用。

---

## 自定义属性系统

### RangedAttribute（整数范围）
- **TEMPERATURE** — 温度，用于龙类和无情的温度机制
- **BLOCK** — 格挡，等值减少伤害
- **COUNTER_ATTACK** — 反击，受伤时对攻击者造成荆棘伤害
- **HEAL** — 治疗，定期恢复生命值
- **MELEE_DAMAGE** — 近战伤害加值（受武器伤害倍率影响）
- **RANGED_DAMAGE** — 远程伤害加值
- **MAGIC_DAMAGE** — 魔法伤害加值
- **LOOTING** — 抢夺等级
- **FORTUNE** — 时运等级

### PercentageAttribute（百分比）
- **MELEE_DAMAGE_PERCENTAGE** — 近战伤害最终倍率
- **RANGED_DAMAGE_PERCENTAGE** — 远程伤害最终倍率
- **MAGIC_DAMAGE_PERCENTAGE** — 魔法伤害最终倍率

---

## 伤害系统（CommonEventHandler.handlerLivingDamageEvent$Pre）

伤害计算流程：
1. 获取 `BLOCK`（格挡）和 `COUNTER_ATTACK`（反击）属性
2. 反击：对攻击者造成荆棘类型伤害
3. 根据伤害类型确定加伤属性：
    - IS_MELEE → MELEE_DAMAGE + MELEE_DAMAGE_PERCENTAGE
    - IS_MAGIC → MAGIC_DAMAGE + MAGIC_DAMAGE_PERCENTAGE（来自 Tags.DamageTypes.IS_MAGIC）
    - IS_PROJECTILE → RANGED_DAMAGE + RANGED_DAMAGE_PERCENTAGE
4. 器官特定回调（九头蛇肋骨/肌肉、尸王脊柱、风暴脊柱）
5. 最终计算：`max(0, damage - block + extraDamage) * finalMultiplier`

---

## 温度系统

温度是全局属性，通过 TEMPERATURE 属性值控制：
- 火龙器官 → 正温度（+1 或 +2）
- 冰龙器官/悚怖 → 负温度（-1 或 -2）
- 王国器官（MALKUTH）→ 全局温度强制为 0
- `getEffectiveTemperature()` — 获取有效全局温度（malkuth 则为 0）
- `getLocalTemperature(slotContext)` — 获取以某槽位为中心的九宫格内局部温度
- 温度影响：冰魂残片、冻结魂火、悚怖命匣根据全局负温度提供健康加成

---

## 四体液系统（HumoursData）

NeoForge Attachment 数据，每种体液有当前值和最大值（默认 100）：
- **血液（Blood）** — 暂未大规模使用
- **黄胆汁（Yellow Bile）** — 暂未大规模使用
- **黑胆汁（Black Bile）** — 腐败魂灯（消耗增级）、尸王脊柱（吸收伤害）、尸王肋骨（+上限）
- **粘液（Phlegm）** — 涛浪提灯（消耗增伤）、风暴脊柱（吸收伤害转粘液）、风暴肋骨（+上限）

提供 `insertXxx`/`extractXxx` 便捷方法，支持 `simulate` 模式。NeoForge 自动客户端同步（StreamCodec）。

---

## 可使用条件系统（UseCondition）

通过 Builder 模式定义哪些物品可以被玩家右键使用：
- 物品属性由 `IItemExtensionMixin` 接管，使用流程由 `ItemStackMixin` + `LivingEntityMixin` 接管，不再依赖事件结算。
- `matchesItem(Predicate<ItemStack>)` — 物品匹配条件
- `matchesEntity(Predicate<LivingEntity>)` — 实体匹配条件（通常检查是否有某器官）
- `onUse(BiFunction)` — 主使用逻辑
- `afterUse(BiFunction)` — 使用完成后的额外收尾
- `foodProperties(Function)` — 挂载 `FoodProperties`，提供后就会进入原版食物管线
- `eatAnimation()` / `drinkAnimation()` — 使用动画类型
- `useDuration(int)` / `fastUse()` / `instantUse()` — 使用时长与使用模式
- 只要条件提供了 `FoodProperties`，它就会被视为食物，后续是否为食物的判断都走 `getFoodProperties(...)`

已注册的条件（在 ChestCavityRegisterEvent 中）：
- 泥峭器官 → 可以使用泥土类物品
- 暴食器官 → 可以使用任何食物，速度减半
- 墨水瓶器官 → 可以喝铁魔法的墨水

---

## 标签系统（Item Tags）

### CCB 器官类型标签
`ItemTagManager.ORGANS` / `HEART` / `LUNG` / `SPINE` / `STOMACH` / `INTESTINE` / `KIDNEY` / `SPLEEN` / `LIVER` / `APPENDIX` / `RIB` / `MUSCLE` / `SPECIAL`

### WAIC 自定义标签（WAICItemTagManager）
- **MAGIC** — 魔法器官（经验之心倍率、胸中新星不烧毁）
- **MECHANICAL** — 机械器官（胸中新星不烧毁）
- **SUMMON** — 召唤类
- **UNIQUE** — 唯一
- **FIRE_DRAGON** / **ICE_DRAGON** / **LIGHTNING_DRAGON** — 龙种聚合标签
- **FIRE** — 炽焰元素（火龙 + 余烬金属 + 火焰王国）
- **ICE** — 冰霜元素（冰龙 + 悚怖 + 浮霜 + 冰霜王国）
- **LESION** — 病变器官
- **CURSED_GOLD** — 诅咒金
- **EMBER_METAL** — 余烬金属
- **CLOTH_ORGAN** — 布织器官

### 伤害类型标签
- **IS_MELEE**（WAICDamageTagManager）— 近战伤害，用于区分近战/远程/魔法

---

## 胸腔类型（ChestCavityType）

- **WAICChestCavityTypeManager.FANTASTICAL** → 注册给 IafEntities.PIXIE（妖精）
- **IceAndFireChestCavityTypeManager.FIRE_DRAGON** → IafEntities.FIRE_DRAGON
- **IceAndFireChestCavityTypeManager.ICE_DRAGON** → IafEntities.ICE_DRAGON
- **IceAndFireChestCavityTypeManager.LIGHTNING_DRAGON** → IafEntities.LIGHTNING_DRAGON
- **IceAndFireChestCavityTypeManager.HYDRA** → IafEntities.HYDRA

---

## 任务系统（Task）

- **ChestNovaTask** — 胸腔关闭时烧毁周围器官（魔法/机械除外），检测乌姆塔纳面具并召唤追随者
- **StraightIntestineTask** — 食用食物后 30% 几率在 3 秒后掉落该食物

> 九头蛇脾脏治疗效果已从独立 Task 改为器官 `.tick()` 回调（`IceAndFireOrganUtil.hydraSpleenTick`），不再使用 Task 系统。

---

## Mixin 列表

1. `ApplyBonusCountMixin` — 抢夺/时运使用 WAIC 自定义属性
2. `ChestCavityDataMixin` — 胸腔数据扩展
3. `EnchantedCountIncreaseFunctionMixin` — 附魔计算
4. `EntityUmvuthanaFollowerToPlayerMixin` — 追随者归属改为玩家
5. `IItemExtensionMixin` — 使 UseCondition 的食物属性注入生效
6. `ItemStackMixin` — 使 UseCondition 的使用流程生效
7. `LivingEntityMixin` — 生物实体扩展
8. `MalkuthCannonEntityMixin` — FDBosses 王国炮台
9. `MalkuthWeaknessHandlerMixin` + `MalkuthWeaknessOverlayMixin` — 王国弱点系统
10. `PoisonMobEffectMixin` — 中毒效果（需要 Access Transformer）

---

## 资源文件

### 源码资源（src/main/resources）
- `who_am_i_core.mixins.json` — Mixin 配置文件
- `META-INF/accesstransformer.cfg` — 仅一行：`public net.minecraft.world.effect.PoisonMobEffect`
- `assets/who_am_i_core/textures/` — GUI/物品/效果贴图
- `assets/who_am_i_core/textures/item/` — 约 200+ 个器官贴图

### 生成资源（src/generated/resources）
- `assets/who_am_i_core/lang/` — en_us.json + zh_cn.json
- `assets/who_am_i_core/models/item/` — 所有物品模型 JSON
- `data/chestcavitybeyond/tags/item/organs/` — 器官类型标签
- `data/who_am_i_core/tags/` — 自定义物品/伤害类型标签

---

## 依赖模组

| 模组                          | 用途                     | CurseForge ID |
|-----------------------------|------------------------|---------------|
| Chest Cavity Beyond（前置）     | 器官系统核心 API             | 1427715       |
| Ice and Fire: CE            | 龙类器官、九头蛇、悚怖            | 1040076       |
| Mowzie's Mobs               | 乌姆塔纳、泥峭、衰老器官           | 250498        |
| Cataclysm（灾变）               | 利维坦、冥行武弁、斯库拉           | 551586        |
| FDBosses（逆卡巴拉）              | 王国器官（Malkuth 温度系统）     | 1271707       |
| Iron's Spells 'n Spellbooks | 墨水、法术流派、死灵法师           | 855414        |
| AnvilCraft                  | 金属器官（浮霜/超限/诅咒金/余烬/皇家钢） | 986251        |
| Touhou Little Maid          | 车万女仆                   | 355044        |
| Companions                  | 同伴                     | 1300341       |
| Spice of Life: Carrot       | 生活调味料                  | 277616        |

库依赖（non-mod）：GeckoLib, Architectury API, Curios, Player Animator, Knight Lib, FD Lib, Uranus, Jupiter, Lionfish API

---

## 工具方法速查

### OrganUtil
- `getAdjacentSlots(int slotIndex)` — 获取 3×9 网格中某槽位周围 8 格的索引列表
- `mercilessBonus(ChestCavitySlotContext)` — `floor(sqrt(总附魔等级))`，浮霜和超限的核心机制
- `rollChance(entity)` — 幸运判定次数：每 5 点幸运 = 1 次，余数每点 20% 几率
- `rollResult(entity, chance)` — 幸运加权概率判定
- `getEffectiveTemperature(entity)` — 有效全局温度（王国则为 0）
- `getLocalTemperature(slotContext)` — 九宫格内局部温度总和
- `aggregateFrankensteinHeartAttributes(context, modifiers)` — 弗兰肯斯坦心脏聚合内部器官属性
- `isInChest(entity, stack)` — 引用比较判断是否在胸腔中
- `getSymmetricRibIndex(int index)` — 获取对称槽位索引

### WAICOrganUtil
- 墨水阑尾/肌肉、病变心脏/肌肉、经验之心、布织泰迪熊、猩红、九狱、电荷系统、FDBosses 等器官实现
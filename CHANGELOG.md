# 更新日志 (Changelog)

### 1.2.1

- 新增炼金锅龙血空取配方：空玻璃瓶可从锅中取出龙血流体制成龙血瓶（火/冰/电三系）
- 火龙血药剂的远古金属锭浸泡配方替换为瓶中沙暴
- 巨兽熔炉新增支持饮用熔岩动力电池
- 咒魂心脏新增冲刺攻击命中后保留冲刺状态

---

- Added Alchemist Cauldron empty recipes: empty glass bottles can now extract dragon blood fluids from the cauldron to craft dragon blood bottles (Fire/Ice/Lightning)
- Fire Dragon Blood Preparation: Ancient Metal Ingot soak recipe replaced with Sandstorm in a Bottle
- Monstrosity Furnace now also supports drinking Lava Power Cells
- Phantom Heart now retains sprinting state after landing sprint attacks

### 1.2.0

- 三系龙宝玉（火/冰/电）法术强度加成由每器官5%下调至1%，并补充动态公式Tooltip
- 弗兰肯斯坦心脏合成配方重做

---

- Three dragon gems (Fire/Ice/Lightning) spell power bonus reduced from 5% to 1% per organ, added dynamic formula tooltips
- Reworked Frankenstein Heart crafting recipe

### 1.1.11

- 修复铁魔法锥形吐息法术（刺骨寒风、火焰吐息、毒雾喷射）弹道方向未使用 Mob 头部朝向的问题
- 修复电磁炮器官发射方向未使用 Mob 头部朝向的问题

---

- Fixed Iron's Spells cone breath projectiles (Cone of Cold, Fire Breath, Poison Breath) not using Mob head rotation for direction
- Fixed Railgun organ not using Mob head rotation for firing direction

### 1.1.10

- 神圣灵魂碎片浸泡龙血流体制成龙血药剂
- 修复弗兰肯斯坦心脏贴图注册在客户端设置阶段的线程安全问题
- 调色盘器官新增25点最大法力值

---

- Divine Soulshard now soaks in dragon blood fluids to craft Dragon Blood Preparations
- Fixed thread-safety issue with Frankenstein Heart item property registration during client setup
- Palette organ now grants +25 Max Mana

### 1.1.9

- 护心镜格挡公式由「力量÷2」重做为「floor(√防御)」，并补充动态公式 Tooltip
- 护心镜攻击后3秒惩罚期内新增无法转动视角
- 暴食吸收效果扩展至直接恢复饥饿值的途径（如巨兽熔炉饮用岩浆桶）
- 原初之火生命值上调并新增法术强度/火焰法术强度/最大法力
- 四系核心（神圣核心/冰霜核心/炽焰核心/自然核心）法术强度下调，新增最大法力
- 木质系列器官属性值统一上调
- 新增 FDBosses 核心（闪电核心/冰火核心/正义核心）浸泡龙血流体制成龙血药剂的配方
- 巨兽熔炉饮用岩浆桶后空桶改为进入背包合并

---

- Heart Mirror block formula reworked from Strength÷2 to floor(√Defense), with dynamic formula tooltip added
- Heart Mirror post-attack penalty now also locks view turning for 3 seconds
- Gluttony absorption effect extended to direct hunger-restoring sources (e.g. Monstrosity Furnace drinking lava)
- Primordial Flame health increased, gains Spell Power / Fire Spell Power / Max Mana
- Four elemental cores (Divine / Frost / Flame / Nature) spell power reduced, gains Max Mana
- Wooden organ series attribute values uniformly increased
- Added Dragon Blood Preparation recipes using FDBosses cores (Lightning / Fire-and-Ice / Justice) soaked in dragon blood fluids
- Monstrosity Furnace lava drinking now sends empty bucket to inventory instead of keeping in hand

### 1.1.8

- 新增火/冰/电龙血自定义流体，龙血药剂配方改为龙血流体浸泡灾变掉落物
- 九头蛇心脏重做：取消中毒伤害并按九头蛇器官数比例回血，移除中毒免疫 Mixin
- 皇家钢/诅咒金/病变/木质/猩红/墨水器官配方统一改用器官标签
- 新增浮霜金属、超限合金系列器官锻造配方
- 诅咒金器官惩罚效果刷新间隔缩短，持续时间延长
- 铁魔法死灵法师脊柱补法术强度，死灵法师肋骨补最大法力
- 利维坦体腔类型补全远古器官
- 升级 Chest Cavity Beyond 依赖

---

- Added Fire/Ice/Lightning dragon blood custom fluids; Dragon Blood Preparation recipes now soak Cataclysm drops in fluid
- Hydra Heart rework: cancels poison damage and heals proportional to Hydra organ count; removed poison immunity Mixin
- Royal Steel / Cursed Gold / Lesion / Wooden / Crimson / Ink organ recipes switched to organ tags
- Added Frost Metal and Transcendium organ smithing recipes
- Cursed Gold penalty refresh interval shortened, duration extended
- Iron's Spells: Necromancer Spine gains spell power, Necromancer Rib gains max mana
- Leviathan chest cavity type filled with Elder organs
- Updated Chest Cavity Beyond dependency

### 1.1.7

- 诅咒金器官惩罚效果重做：效果顺序调整为饥饿/缓慢/虚弱，刷新间隔延长，器官增减时即时更新
- 鬼火生命与力量削减由-0.8加深至-0.9
- 泥峭核心生命值1→2，补全泥峭标签翻译

---

- Reworked Cursed Gold organ penalties: effect order adjusted to Hunger/Slowness/Weakness, extended refresh interval, instant update on organ changes
- Ghost Fire health and strength penalty deepened from -0.8 to -0.9
- Bluff Core health 1→2, added Bluff tag translations

### 1.1.6

- 剑骨头支持附魔台附魔与铁砧合并附魔
- 修复鬼火穿墙模式下因碰撞箱冲突被强制压成趴下姿态的问题

---

- Sword Bone now supports enchanting table and anvil enchantment merging
- Fixed forced prone pose during Ghost Fire noclip due to bounding box collision

### 1.1.5

- 新增浮霜金属、超限合金、冰火联动器官与远古工厂器官的动态详细公式 Tooltip
- 冰火动态公式统一复用实际计数规则，正确支持王国器官令炽焰与冰霜双向计数
- 巨兽回路的最大生命值伤害倍率由 1% 提升至 5%，巨兽炉心黄胆汁生成倍率由 5% 提升至 10%
- 不灭薪火力量加成移除平方根缩放，改为直接按炽焰与冰霜差值计算
- 诅咒金器官施加的虚弱、缓慢与饥饿效果刷新时长由 100 tick 延长至 200 tick
- 优化龙吐息袋、胸中新星、导流脊柱、南瓜与多组器官效果描述
- 为 CCB 原版火/冰主题器官补充炽焰与冰霜标签（抗火全套、烈焰系列、岩浆怪、恶魂胃与气囊、雪之核心）
- 冰火联动器官补全火焰/冰霜抗性：火龙全套 +4、冰龙全套 +4、悚怖系列 +2
- 铁砧工艺余烬金属全套补火焰抗性 3、浮霜金属全套补冰霜抗性 3
- 灾变器官补全抗性：焰魔/巨兽系列火焰抗性 5、咒魂心脏冰霜抗性 5
- 铁魔法原初受火者补火焰抗性 10
- FDBosses 火焰/冰霜王国战士之心抗性 2→4，王国心脏冰火双向抗性 5→10
- 余烬金属器官机制重做：移除受击火焰减伤回血，改为着火或身处火源时每个器官每秒恢复0.5点生命值
- 尸王肋骨黑胆汁上限 10→50
- 直肠炼金锅浸泡配方由冰霜毒液改为水
- 多个器官补充属性：电磁炮速度+3、死亡透镜方块/实体交互距离+3、控制杆神经+2、能量模块耐力+2
- 缠骨灵偶速度/幸运 1→2，直肠营养 1→2
- 灵薄器官效果重做

---

- Added dynamic detailed formula tooltips for Frost Metal, Transcendium, elemental-interaction, and Ancient Factory organs
- Unified elemental tooltip formulas with the gameplay counting rules, including Malkuth's additive Fire/Ice interaction
- Increased Monstrosity Circuit's maximum-health damage scaling from 1% to 5%; Monstrosity Core yellow bile generation from 5% to 10%
- Undying Ember Strength removed square-root scaling, now uses direct fire-minus-ice count
- Increased the refresh duration of Weakness, Slowness, and Hunger applied by Cursed Gold organs from 100 to 200 ticks
- Clarified descriptions for dragon breath sacs, Chest Nova, Conductive Spine, Squash, and multiple organ effects
- Added Fire/Ice tags to CCB vanilla fire/ice-themed organs (Fireproof set, Blaze series, Magma Cube, Ghast Stomach & Gas Sac, Snow Core)
- Ice and Fire organs: Fire Dragon set +4 Fire Resistance, Ice Dragon set +4 Frost Resistance, Dread series +2
- Anvil Craft: Ember Metal set +3 Fire Resistance, Frost Metal set +3 Frost Resistance
- Cataclysm organs: Ignited/Monstrosity series +5 Fire Resistance, PhantomHeart +5 Frost Resistance
- Iron's Spells: Primordial Flame +10 Fire Resistance
- FDBosses: Fire/Ice Malkuth Warrior Heart resistance 2→4, Malkuth heart dual resistance 5→10
- Ember Metal organ rework: removed on-hit fire damage reduction/healing; now restores 0.5 HP per second per organ while on fire or in a fire source
- Dead King Rib max black bile 10→50
- Straight Intestine Alchemist Cauldron soak recipe changed from Ice Venom to Water
- Added attributes: Railgun +3 Speed, Death Lens +3 Block/Entity interaction range, Control Rod +2 Nerves, Energy Module +2 Endurance
- Haunted Bone Speed/Luck 1→2, Straight Intestine Nutrition 1→2
- Limbo organ rework

### 1.1.4

- 适配 Chest Cavity Beyond 1.8.5，动态属性器官 `refreshOnOrganChange` 全面替换为 `refreshDynamicAttribute`
- 九狱器官色欲/背叛效果由 attack 回调迁移至 LivingDamageEvent 事件驱动
- 色欲/背叛加入唯一器官标签，护心镜移出唯一器官标签
- 异端器官药水等级增强新增 Mixin 标记，防止 amplifier 重复叠加
- 暴食效果重构：修复缺少 MAX_ABSORPTION 属性导致吸收生命值完全无法添加的问题，吸收值改为依赖属性上限自动截断，移除生命回复效果
- 愤怒器官力量/速度由 1/2/3 强化至 3/6/9
- 泥峭系列器官新增 BLUFF 物品标签替代硬编码判断，铭文板改用 MAX_ABSORPTION 属性提供吸收上限
- 经验之心健康值公式由每10级+1改为 floor(√等级)，提取公共方法并补充动态公式 Tooltip
- UseCondition 新增 forceStartUsingItemWhen 谓词，暴食 N≥3 时绕过饥饿限制进食
- UseCondition.build 新增 onFinishUsingItem 与 onUse 互斥校验
- 诅咒金肺属性值 1.5→2

---

- Adapted to Chest Cavity Beyond 1.8.5: dynamic-attribute organs switched from `refreshOnOrganChange` to `refreshDynamicAttribute`
- Lust/Treachery effects migrated from attack callbacks to LivingDamageEvent-driven logic
- Lust/Treachery added to UNIQUE tag; Heart Mirror removed from UNIQUE tag
- Heresy potion amplifier enhancement now uses a Mixin flag to prevent repeated stacking
- Gluttony effect rework: fixed absorption hearts being completely unappliable due to missing MAX_ABSORPTION attribute; absorption now auto-clamped via the attribute cap; removed HP heal effect
- Wrath Strength/Speed increased from 1/2/3 to 3/6/9
- Bluff organ series: added BLUFF item tag replacing hardcoded checks; Bluff Tablet now provides MAX_ABSORPTION attribute
- Experience Heart health formula changed from +1 per 10 levels to floor(√level), extracted shared method and added dynamic formula tooltip
- UseCondition: added forceStartUsingItemWhen predicate; Gluttony N≥3 bypasses hunger restriction
- UseCondition.build now validates onFinishUsingItem and onUse mutual exclusivity
- Cursed Gold Lung attribute value 1.5→2

### 1.1.3

- 全系列器官属性数值大幅上调（皇家钢/余烬金属/龙族/灾变/布织/墨汁/木制等）
- 浮霜金属器官基础属性值 1→3，超限合金器官基础值保留 5 并移除 BLOCK 属性
- 经验之心经验倍率简化为固定 ×2，欺诈器官经验获取由 10 倍削弱至 5 倍
- 超限合金二合一锻造合并两个输入的附魔
- 铁魔法新增牧师/炽焰术士/冰霜术士/药剂师/高位唤魔者自定义胸腔类型
- 布织泰迪熊补充健康值属性，笔尖补充神经/防御属性
- 新增鬼火器官（Ice and Fire）：赋予创造飞行与穿墙能力，大幅削弱生命与力量
- 新增荧光核心器官（Mowzie's Mobs）：持续赋予发光与夜视效果
- 幽灵/荧光浮灯体腔类型改用自定义器官物品替代原版物品
- 新增配方：鬼火→魂质，荧光核心→荧光果冻×2

---

- Organ attribute values significantly increased across all series (Royal Steel / Ember Metal / Dragon / Cataclysm / Cloth / Ink / Wooden, etc.)
- Frost Metal organ base attribute value 1→3; Transcendium organ base value kept at 5 with BLOCK attribute removed
- Experience Heart XP multiplier simplified to flat ×2; Fraud organ XP gain reduced from 10× to 5×
- Transcendium two-to-one smithing now merges enchantments from both inputs
- Iron's Spells: added custom chest cavity types for Priest / Pyromancer / Cryomancer / Apothecarist / Archevoker
- Cloth Teddy Bear gains health attribute; Nib gains nerves and defense attributes
- Added Ghost Fire organ (Ice and Fire): grants creative flight and noclip, heavily reduces health and strength
- Added Glowing Core organ (Mowzie's Mobs): continuously applies Glowing and Night Vision
- Ghost / Lantern chest cavity types now use custom organ items instead of vanilla items
- Added recipes: Ghost Fire → Ectoplasm, Glowing Core → Glowing Jelly ×2

### 1.1.2

- 布织泰迪熊新增右键方块放出野生泰迪功能，释放时掉落 bundle 内容物
- 新增布织泰迪熊复制配方（泰迪器官不消耗）
- 泰迪剪回条件放宽：未驯服或属于自己的泰迪均可剪回
- 泥峭核心/铭文板/活性泥峭棒补充基础属性
- 新增活性泥峭棒→泥峭棒还原配方
- 龙吐息 GoalSkill 过滤同族龙实体，避免龙本体自带吐息与器官附加吐息重复释放
- 修复 VillagerMixin 欺诈折扣在 updateSpecialPrices 时取不到交易玩家的问题

---

- Cloth Teddy Bear can now be placed on blocks to release a wild teddy, dropping bundle contents on spawn
- Added Cloth Teddy Bear copy recipe (teddy organ is not consumed)
- Teddy shearing condition relaxed: untamed or player-owned teddies can be sheared back
- Added base attributes to Bluff Core / Bluff Tablet / Active Bluff Rod
- Added Active Bluff Rod → Bluff Rod shapeless recipe
- Dragon breath GoalSkills now filter same-species dragon entities to prevent duplicate breath attacks from organ skill stacking with the dragon's native breath
- Fixed VillagerMixin fraud discount failing in updateSpecialPrices due to trading player not yet set

### 1.1.1

- 诅咒金器官锻造模板由余烬金属改为皇家钢
- 存在置换器的附魔粒子数量支持客户端配置（默认 2887，可设为 0 关闭）
- 客户端配置项补充中文说明
- 修复 Mowzie's Mobs 动画播放与铁砧工艺冲突的问题

---

- Cursed Gold organ smithing template changed from Ember Metal to Royal Steel
- Existence Displacer enchantment particle count now configurable on the client (default 2887, set 0 to disable)
- Added Chinese descriptions for client config entries
- Fixed Mowzie's Mobs animation conflict with Anvil Craft

### 1.1.0

- 奖杯扩容机制重做为龙血药剂：火龙/冰龙/电龙三色龙血药剂各提供一级胸腔扩容，龙血药剂组一次性扩至最大
- 龙血药剂支持潜行右键对其他生物注射，通过射线检测拦截交互
- 新增存在置换器：长按右键交换玩家与目标的胸腔器官（含容量与龙血标记），敌对生物和玩家需血量低于 30%
- 体液数据（HumoursData）接入 NeoForge Attachment 死亡保留
- 温度系统重构为炽焰/冰霜器官标签双向计数，拥有王国器官时冰火互相计入而非抵消
- 焰魔肋甲/护心镜/死亡透镜重构为基于 CCB 动态属性修饰符与 `refreshOnOrganChange`
- AoE 技能过滤统一替换为 `EntityRelationUtil.shouldAoeDamage`，修复宠物友伤
- 新增利维坦系列器官：利维坦鳃 AoE 伤害 + 黑暗 + 击退，水中释放时获得加成
- 新增远古遗魂系列器官：沙釉心脏召唤 3 个沙暴龙卷风环绕自身，对沙漠诅咒目标增伤
- 新增紫水晶巨蟹花岩系列器官：苔化紫水晶按魔法器官种类数提供防御，花岩核心环形发射 16 发紫水晶簇
- 新增沙暴跟随 Mixin：由玩家器官召唤的沙暴龙卷风绕施法者轨道运动
- WAIC 物品标签迁移至 CCB 命名空间
- 新增龙血药剂炼金锅配方：再生药水浸泡 Ice and Fire 龙血
- 新增存在置换器合成配方（诅咒金锭 + 下界之星）
- 修复 BundleContents 内物品消耗后未重建不可变列表导致的序列化异常
- 适配 Chest Cavity Beyond 1.8.4 器官 API 重构（`refreshOnOrganChange`、`ChestCavitySlotContext` 精简）
- 动态公式 Tooltip 重构为静态常量字段，统一 `TooltipUtil.formulaOperator` 用法

---

- Trophy expansion reworked into Dragon Blood Preparation: Fire/Ice/Lightning dragon blood each grants one chest cavity expansion level; the group variant expands to maximum instantly
- Dragon Blood Preparation now supports shift-right-click injection into other entities via ray-cast interaction override
- Added Existence Displacer: hold right-click to swap chest cavity organs (including capacity and dragon blood flags) with a target; hostile mobs and players require health below 30%
- Humours data (HumoursData) now persists through death via NeoForge Attachment `copyOnDeath`
- Temperature system reworked into Fire/Ice organ tag dual-counting; with Malkuth organ both tags contribute additively instead of cancelling
- Ignited Rib Plating / Heart Mirror / Death Lens reworked to use CCB dynamic attribute modifiers with `refreshOnOrganChange`
- AoE skill filtering unified to `EntityRelationUtil.shouldAoeDamage`, fixing friendly fire on tamed entities
- Added Leviathan organ series: Leviathan Gill — AoE damage + Darkness + knockback, empowered when submerged
- Added Ancient Remnant organ series: Sand-Glaze Heart summons 3 orbiting sandstorm tornadoes; deals bonus damage to targets with Curse of the Desert
- Added Amethyst Crab Bloom Stone organ series: Mossy Amethyst grants defense per distinct magic organ type; Bloom Stone Core fires 16 amethyst cluster projectiles in a ring
- Added Sandstorm follow Mixin: sandstorm tornadoes summoned by player organs orbit around the caster
- WAIC item tags migrated to CCB namespace
- Added Dragon Blood Preparation Alchemist Cauldron recipes: soak Ice and Fire dragon blood in Regeneration potions
- Added Existence Displacer crafting recipe (Cursed Gold Ingots + Nether Star)
- Fixed BundleContents serialization crash when consumed items were not rebuilt into an immutable list
- Adapted to Chest Cavity Beyond 1.8.4 organ API refactor (`refreshOnOrganChange`, simplified `ChestCavitySlotContext`)
- Dynamic formula tooltips refactored into static constant fields, unified `TooltipUtil.formulaOperator` usage

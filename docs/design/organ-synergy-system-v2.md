# 器官搭配体系设计文档 v2.0（修正版）

**项目**: Who Am I Core
**设计日期**: 2026-03-15
**修正日期**: 2026-03-15
**设计师**: Creative Content Designer
**版本**: 2.0
**状态**: 技术可行性修正完成

---

## 修正说明 (v2.0)

本版本根据审核反馈进行了重大修正，解决以下严重问题：

### ✅ 已修正的严重问题

1. **移除几何搭配** - 原"几何触发"类型已完全移除
2. **修正九狱顺序** - 改为基于槽位位置（0-7号槽位）而非装备顺序
3. **明确拟态机制** - 限定为复制相邻器官的属性加成
4. **修正幻想种效果** - 改为具体的属性修饰符和特殊药水效果
5. **添加性能优化方案** - 包含具体的缓存策略和性能指标
6. **简化套装规则** - 明确叠加优先级，避免冲突

### 📋 保留的优秀设计

- 核心搭配机制（相邻、套装、主题、对立）
- 丰富的协同系统（12种主要协同）
- 平衡性分级系统（5级强度）
- JSON数据驱动设计
- UI反馈系统设计

---

## 目录

1. [设计概述](#设计概述)
2. [器官搭配机制](#器官搭配机制)
3. [搭配效果类型](#搭配效果类型)
4. [协同效应设计](#协同效应设计)
5. [平衡性与策略性](#平衡性与策略性)
6. [具体搭配方案](#具体搭配方案)
7. [UI与反馈设计](#ui与反馈设计)
8. [技术实现建议](#技术实现建议)
9. [性能优化方案](#性能优化方案)

---

## 设计概述

### 核心理念

**"融合产生超越，搭配创造奇迹"**

器官搭配体系的核心目标是让玩家通过策略性地组合不同器官，解锁超越单一器官效果的协同增益。这个系统不仅增加了游戏深度，还为玩家提供了无限的实验空间和个性化定制可能。

### 设计原则

1. **易学难精**: 基础搭配易于理解和触发，但高级搭配需要深入研究和策略
2. **多样性**: 避免唯一最优解，提供多条有效的搭配路径
3. **主题性**: 搭配效果应与器官的背景故事和特性相符
4. **平衡性**: 强力搭配应有相应的代价或限制
5. **可扩展性**: 系统设计应便于未来添加新器官和新搭配
6. **技术可行性**: 所有设计都必须基于现有API能力

### 目标体验

- **探索乐趣**: 玩家乐于尝试不同器官组合，发现隐藏效果
- **策略深度**: 玩家需要权衡搭配的成本、收益和风险
- **个性化**: 玩家可以根据自己的游戏风格选择不同的搭配路线
- **惊喜感**: 发现特殊搭配时的"尤里卡"时刻

---

## 器官搭配机制

### 胸腔结构说明

**技术基础**: ChestCavityData 继承 ItemStackHandler，有固定27个槽位（0-26）

```
胸腔布局 (3x9网格):
[0] [1] [2] [3] [4] [5] [6] [7] [8]
[9] [10] [11] [12] [13] [14] [15] [16] [17]
[18] [19] [20] [21] [22] [23] [24] [25] [26]
```

**关键API**:
- `getStackInSlot(int slot)` - 获取指定槽位的器官
- `getOrganCount(Item item)` - 获取某类器官数量
- 槽位索引是固定的，可以用于基于位置的检测

### 触发条件类型

#### 1. 相邻触发 (Adjacent Synergy) ✅

**描述**: 当特定器官在胸腔网格中相邻时触发效果。

**技术实现**: 使用已有的 `getAdjacentSlots(int slotIndex)` 方法

**优势**:
- 利于UI可视化（相邻器官高亮显示）
- 玩家可以直观理解搭配条件
- 便于调整和优化

**示例**:
```
槽位4[火龙心脏] + 槽位5[火龙肺脏] → 火焰协同：+15%火焰抗性
槽位12[冰龙脊柱] + 槽位13[冰龙肋骨] → 冰霜护体
```

**检测逻辑**:
```java
// 检查器官A和器官B是否相邻
boolean areAdjacent(ItemStack stackA, ItemStack stackB, ChestCavityData data) {
    int slotA = findSlot(stackA, data);
    int slotB = findSlot(stackB, data);
    if (slotA == -1 || slotB == -1) return false;

    int[] adjacent = getAdjacentSlots(slotA);
    return Arrays.contains(adjacent, slotB);
}
```

#### 2. 套装触发 (Set Bonus) ✅

**描述**: 当装备特定数量或组合的同类器官时触发效果。

**分级系统**:
- **2件套**: 小幅增益
- **4件套**: 中等增益 + 特殊效果
- **6件套**: 大幅增益 + 强力技能
- **8件套**: 终极效果（可选，用于稀有搭配）

**示例**:
```
火龙器官套装：
- 2件: +10%火焰伤害
- 4件: 火焰吐息（主动技能）
- 6件: 火焰化身（免疫火焰，+50%火焰伤害）
```

**检测逻辑**:
```java
int fireDragonCount = data.getOrganCount(fireDragonTag);
if (fireDragonCount >= 6) {
    activateSynergy("fire_dragon_set_6");
} else if (fireDragonCount >= 4) {
    activateSynergy("fire_dragon_set_4");
}
```

#### 3. 主题触发 (Thematic Synergy) ✅

**描述**: 当装备具有特定主题或背景联系的器官时触发，无需相邻。

**示例**:
```
龙族主题（火龙+冰龙+电龙器官各至少2个）：
→ 三龙共鸣：+20%所有元素伤害，周期性释放元素风暴
```

**检测逻辑**:
```java
int fireCount = data.getOrganCount(fireDragonTag);
int iceCount = data.getOrganCount(iceDragonTag);
int lightningCount = data.getOrganCount(lightningDragonTag);

if (fireCount >= 2 && iceCount >= 2 && lightningCount >= 2) {
    activateSynergy("dragon_trinity");
}
```

#### 4. 对立触发 (Opposition Synergy) ✅

**描述**: 装备对立属性的器官产生特殊的"冲突和谐"效果。

**示例**:
```
火龙心脏 + 冰龙心脏 → 冷热平衡：+30%温度抗性，获得"热能转换"效果
```

**检测逻辑**:
```java
boolean hasFireHeart = data.hasOrgan(fireDragonHeart);
boolean hasIceHeart = data.hasOrgan(iceDragonHeart);

if (hasFireHeart && hasIceHeart) {
    activateSynergy("fire_ice_balance");
}
```

#### 5. 槽位触发 (Slot-Based Synergy) 🆕

**描述**: 器官放置在特定槽位时触发效果。

**用于替代原"几何搭配"的可行方案**

**示例**:
```
心脏放置在槽位13（中心位置）→ 核心守护：+20%最大生命值
九狱器官按顺序放置在槽位0-7 → 地狱巡礼：累积增益
```

**检测逻辑**:
```java
// 九狱顺序检测（基于槽位0-7）
ItemStack slot0 = data.getStackInSlot(0);
ItemStack slot1 = data.getStackInSlot(1);
// ... 检查槽位0-7是否按顺序放置九狱器官
```

### 检测时机

1. **延迟检测**: 关闭胸腔界面时统一计算（推荐，性能最优）
2. **器官变化时**: 检测 `setStackInSlot` 和 `extractItem` 调用
3. **周期检测**: 每X秒检测一次（用于动态效果，如每5秒）

**推荐**: 延迟检测 + 缓存机制，在胸腔关闭时统一计算并缓存结果

### 效果叠加规则 ✅（已简化）

**优先级系统**（从高到低）:
1. **槽位触发** - 最精确的条件
2. **相邻触发** - 中等精确度
3. **套装触发** - 数量条件
4. **主题触发** - 宽松条件

**叠加规则**:
- **同一搭配类型**: 只触发最高级效果（不叠加）
  - 例：同时满足4件和6件 → 只触发6件效果
- **不同搭配类型**: 可以同时生效
  - 例：相邻效果 + 套装效果 + 主题效果 = 三重叠加
- **冲突效果**: 优先级高的生效
  - 例：某器官同时参与两个搭配 → 优先级高的搭配生效

**示例**:
```
玩家配置：
- 火龙心脏（槽位4）+ 火龙肺脏（槽位5） → 相邻触发：+15%火焰伤害
- 总共6个火龙器官 → 套装触发：+40%火焰相关属性
- 火/冰/电龙各2个 → 主题触发：三龙共鸣

最终效果：
+15%（相邻）+40%（套装）+20%（主题）= +75%总增益
```

---

## 搭配效果类型

### 1. 属性增益型 (Attribute Bonus) ✅

**特点**: 直接提升角色属性，简单直观。

**子类型**:

| 类型 | 示例 | 适用场景 | 实现方式 |
|------|------|----------|----------|
| 基础属性 | +HEALTH, +SPEED, +STRENGTH | 通用搭配 | AttributeModifier |
| 抗性属性 | +火焰抗性, +魔法抗性 | 主题搭配 | AttributeModifier |
| 资源属性 | +BREATH_CAPACITY, +NUTRITION | 功能性搭配 | AttributeModifier |
| 特殊属性 | +LUCK, +掉落率 | 稀有搭配 | AttributeModifier |

**设计要点**:
- 数值要平衡，避免过强
- 使用百分比和固定值的混合
- 考虑与原有器官属性的叠加关系

**实现示例**:
```java
public class AttributeBonusEffect implements SynergyEffect {
    private Map<Holder<Attribute>, Double> bonuses;

    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        for (Map.Entry<Holder<Attribute>, Double> entry : bonuses.entrySet()) {
            AttributeInstance attribute = owner.getAttribute(entry.getKey());
            if (attribute != null) {
                attribute.addPermanentModifier(new AttributeModifier(
                    UUID.randomUUID(),
                    "synergy_bonus",
                    entry.getValue(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ));
            }
        }
    }
}
```

### 2. 技能解锁型 (Skill Unlock) ✅

**特点**: 解锁新的主动或被动技能，增加游戏性。

**示例**:
```
火龙4件套 → 解锁"火焰吐息"（右键使用）
冰龙4件套 → 解锁"冰霜护盾"（受击时触发）
九头蛇心脏 + 九头蛇脊柱 → 解锁"再生能力"（缓慢恢复生命）
```

**技能类型**:
- **主动技能**: 玩家手动触发（通过 selectedSlot 机制）
- **被动技能**: 自动触发（通过 tick 事件）
- **切换技能**: 可开关的被动效果
- **条件技能**: 特定条件下触发（如受击、击杀等）

**实现示例**:
```java
public class SkillUnlockEffect implements SynergyEffect {
    private ResourceLocation skillId;

    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        // 添加胸腔任务来实现技能
        IChestCavityTask task = new ActiveSkillTask(skillId);
        data.addTask(task);
    }
}
```

### 3. 特殊能力型 (Special Ability) ✅

**特点**: 提供独特的能力，改变游戏体验。

**修正后的设计**（确保技术可行）:

| 原设计 | 修正后 | 实现方式 |
|--------|--------|----------|
| 墨水分身复活 | 受到致命伤害时移除墨水器官并恢复50%生命 | 通过 hurt 事件 |
| 幻想现实修改规则 | 幻想种全套：免疫物理伤害，+50%魔法伤害 | 属性修饰符 |
| 创世神笔创造器官 | 创世神笔：每5分钟获得一个随机器官的临时复制 | 通过任务系统 |

**示例**:
```
墨水全套 → "墨影逃脱"（修正后）
- 触发时机：受到致命伤害时
- 效果：移除所有墨水器官，恢复50%最大生命值
- 冷却时间：5分钟
```

**实现示例**:
```java
public class EscapeDeathEffect implements SynergyEffect {
    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        // 注册到 hurt 事件
        owner.addCapability(new EscapeDeathCapability(data));
    }
}

public class EscapeDeathCapability {
    private final ChestCavityData data;
    private long lastTriggerTime;

    public void onHurt(LivingEntity owner, DamageSource source, float damage) {
        float currentHealth = owner.getHealth();
        float damageAmount = damage;

        if (currentHealth - damageAmount <= 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTriggerTime > 300000) { // 5分钟冷却
                // 移除墨水器官
                removeInkOrgans(data);
                // 恢复生命
                owner.setHealth(owner.getMaxHealth() * 0.5f);
                lastTriggerTime = currentTime;
            }
        }
    }
}
```

### 4. 负面平衡型 (Drawback Balance) ✅

**特点**: 强力效果伴随负面代价，增加策略选择。

**示例**:
```
肿瘤全套 → 狂暴：+50%伤害，但每秒失去2%生命
九狱全套 → 地狱契约：+100%所有属性，但无法自然恢复生命
```

**平衡方式**:
- 资源消耗（生命、能量、饱食度）
- 使用限制（冷却时间、次数限制）
- 弱点暴露（特定伤害增加）
- 互斥效果（无法使用某些功能）

**实现示例**:
```java
public class BerserkEffect implements SynergyEffect {
    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        // 添加增益
        addDamageBonus(owner, 0.5); // +50%伤害

        // 添加持续扣血任务
        data.addTask(new DoTPulseTask(owner, 0.02f)); // 每秒扣2%生命
    }
}
```

### 5. 叠层成长型 (Stacking Bonus) ✅

**特点**: 效果随时间或条件累积而增强。

**示例**:
```
弗兰肯斯坦器官每存活1分钟 → +1层"实验体"层数，每层+2%所有属性
（最多10层，死亡后重置）

连续击杀敌人 → 累积"杀戮"层数，每层+1%攻击速度
（脱离战斗后快速衰减）
```

**实现示例**:
```java
public class StackingBonusEffect implements SynergyEffect {
    private final int maxStacks;
    private final double bonusPerStack;

    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        data.addTask(new StackingTask(owner, maxStacks, bonusPerStack));
    }
}

public class StackingTask implements IChestCavityTask {
    private int currentStacks = 0;

    @Override
    public void tick(LivingEntity owner) {
        if (条件满足) {
            currentStacks = Math.min(currentStacks + 1, maxStacks);
            updateBonus(owner, currentStacks * bonusPerStack);
        }
    }
}
```

---

## 协同效应设计

### 协同效应分类

#### A. 完美协同 (Perfect Synergy)

**定义**: 1+1>2，器官组合产生远超单体总和的效果。

**示例**:
```
火龙心脏（火焰核心）+ 火龙吐息袋 + 火龙肺脏
→ 单体效果：HEALTH+4, BREATH+4
→ 协同效果：HEALTH+10, BREATH+10, 解锁"火焰风暴"技能

原因：这三个器官在火龙体内是紧密协作的喷火系统
```

#### B. 互补协同 (Complementary Synergy)

**定义**: 器官互相弥补弱点，产生平衡的全面效果。

**示例**:
```
墨水器官（高敏捷、低防御）+ 木质器官（高防御、低速度）
→ 协同效果：获得"木墨护甲"：高防御 + 高闪避
```

#### C. 链式协同 (Chain Synergy)

**定义**: 第一个器官的效果触发第二个器官的效果，形成连锁反应。

**示例**:
```
冰龙脊柱（冻结敌人）+ 火龙肋骨（对冻结目标额外伤害）
→ 攻击时：冻结 → 冰龙脊柱触发 → 火龙肋骨触发额外伤害
```

#### D. 转化协同 (Conversion Synergy)

**定义**: 将一种属性转化为另一种属性。

**示例**:
```
肿瘤器官（高生命值但持续扣血）+ 九狱器官（生命越低伤害越高）
→ 协同效果：将肿瘤的扣血转化为伤害加成
```

### 具体协同方案

#### 1. 龙族协同 (Dragon Synergy)

**火龙系**:
- **火龙心脏 + 火龙吐息袋 + 火龙肺脏**（相邻）→ 火焰三重奏：火焰伤害+30%，解锁"火焰吐息"技能
- **4个以上火龙器官** → 火龙之怒：火焰抗性+50%，对敌人施加燃烧效果
- **6个以上火龙器官** → 火焰化身：免疫火焰，所有攻击附带火焰伤害

**冰龙系**:
- **冰龙心脏 + 冰龙宝玉 + 冰龙脊柱**（相邻）→ 冰霜核心：冰冻抗性+30%，受击时冻结周围敌人
- **4个以上冰龙器官** → 冰霜护体：移动时留下冰霜轨迹，踩中的敌人减速
- **6个以上冰龙器官** → 绝对零度：免疫冰冻，攻击将敌人冻结

**电龙系**:
- **电龙心脏 + 电龙宝玉 + 电龙脊柱**（相邻）→ 雷霆之力：攻击连锁3个敌人
- **4个以上电龙器官** → 静电场：周围敌人持续受到电击伤害
- **6个以上电龙器官** → 雷神降临：免疫雷电，召唤闪电攻击敌人

**三龙共鸣**:
- **火龙+冰龙+电龙器官各至少2个** → 元素平衡：所有元素抗性+25%，随机触发元素效果
- **三龙心脏全装备** → 龙神之力：所有属性+30%，解锁"龙息三连击"技能

#### 2. 墨水与颜料协同

**墨水系**:
- **墨水心脏 + 墨水瓶 + 钢笔尖**（相邻）→ 墨水创作：提升攻击力和创作速度
- **4个以上墨水器官** → 墨影迷踪：闪避率+20%，移动时留下墨水陷阱
- **6个以上墨水器官** → 墨水世界：进入隐形状态，攻击从暗影中袭来

**颜料系**:
- **颜料心脏 + 调色盘 + 3种不同颜料器官** → 色彩大师：每次攻击随机获得一种增益
- **4个以上颜料器官** → 彩虹护盾：周期性获得随机元素抗性
- **6个以上颜料器官** → 万色幻象：敌人难以锁定，迷惑敌人攻击

**墨水+颜料**:
- **墨水器官3个 + 颜料器官3个** → 水墨交融：所有艺术类效果增强50%
- **墨水全套 + 颜料全套** → 艺术巅峰：创造临时器官（每10分钟1个）

#### 3. 九狱协同 ✅（已修正）

**修正说明**: 改为基于槽位0-7的顺序检测，而非装备顺序

**正确顺序奖励**:
```
槽位0: 灵薄之界
槽位1: 色欲
槽位2: 暴食
槽位3: 贪婪
槽位4: 愤怒
槽位5: 暴力
槽位6: 欺诈
槽位7: 背叛
```

- **连续3个正确**（槽位0-2）→ 地狱第一层：+10%伤害
- **连续5个正确**（槽位0-4）→ 地狱第三层：+25%伤害，+10%速度
- **连续7个正确**（槽位0-6）→ 地狱第七层：+40%所有属性
- **全部8个正确**（槽位0-7）→ 地狱第九层：+50%所有属性，解锁"地狱审判"技能

**任意顺序**:
- **4个以上九狱器官** → 罪恶累积：每个九狱器官提供+5%伤害
- **九狱全套** → 地狱君主：免疫所有负面效果，攻击无法被闪避

**实现逻辑**:
```java
public int checkInfernoSequence(ChestCavityData data) {
    int correctCount = 0;
    Map<Integer, Item> correctSequence = Map.of(
        0, limboItem,
        1, lustItem,
        2, gluttonyItem,
        3, greedItem,
        4, wrathItem,
        5, violenceItem,
        6, fraudItem,
        7, treacheryItem
    );

    for (int i = 0; i < 8; i++) {
        ItemStack stack = data.getStackInSlot(i);
        if (stack.is(correctSequence.get(i))) {
            correctCount++;
        } else {
            break; // 顺序错误，停止检测
        }
    }

    return correctCount;
}
```

#### 4. 弗兰肯斯坦协同

- **弗兰肯斯坦器官4个不同部位** → 怪物之力：力量+30%，健康+20%
- **弗兰肯斯坦全套** → 科学怪人的杰作：所有器官效果+50%，但获得"易碎"特质（受到致命伤害时直接死亡，无法复活）

#### 5. 肿瘤协同

- **2个肿瘤器官** → 恶性生长：生命恢复速度-50%，最大生命值+30%
- **4个肿瘤器官** → 狂暴突变：伤害+50%，但持续失去生命
- **肿瘤全套** → 不死之身：生命值降至0时不会死亡，而是进入"狂暴"状态60秒

#### 6. 木质协同

- **4个木质器官** → 自然之力：自然亲和度提升，获得植物加速生长能力
- **木质全套** → 森林之心：周围自动生成保护藤蔓，获得持续生命恢复

#### 7. 拟态协同 ✅（已修正）

**修正说明**: 限定为复制相邻器官的属性加成

- **拟态心脏 + 拟态肺脏 + 拟态肝脏**（相邻）→ 完美伪装：复制相邻3个器官的50%属性加成

**效果说明**:
```
拟态心脏（槽位4）的相邻器官：
- 槽位3：火龙心脏（+4 HEALTH）
- 槽位5：冰龙肺脏（+3 BREATH_CAPACITY）
- 槽位13：电龙肋骨（+2 STRENGTH）

拟态效果：
- +2 HEALTH（火龙的50%）
- +1.5 BREATH_CAPACITY（冰龙的50%）
- +1 STRENGTH（电龙的50%）
```

**实现逻辑**:
```java
public void applyMimicSynergy(ChestCavityData data, int mimicSlot) {
    int[] adjacentSlots = getAdjacentSlots(mimicSlot);
    double copiedHealth = 0;
    double copiedBreath = 0;
    double copiedStrength = 0;

    for (int slot : adjacentSlots) {
        if (slot == -1) continue;
        ItemStack adjacent = data.getStackInSlot(slot);
        if (adjacent.isEmpty()) continue;

        // 获取相邻器官的属性值
        copiedHealth += getOrganHealth(adjacent) * 0.5;
        copiedBreath += getOrganBreath(adjacent) * 0.5;
        copiedStrength += getOrganStrength(adjacent) * 0.5;
    }

    // 应用复制的属性
    applyAttributeBonus(owner, Attributes.MAX_HEALTH, copiedHealth);
    applyAttributeBonus(owner, BREATH_CAPACITY, copiedBreath);
    applyAttributeBonus(owner, Attributes.ATTACK_DAMAGE, copiedStrength);
}
```

#### 8. 悚恐怖官协同

- **苦寒血肉 + 冰魂残片 + 冻结魂火**（相邻）→ 悚恐之寒：冰冻效果增强，敌人被冻结时受到额外伤害
- **悚恐怖匣 + 悚怖肋骨 + 悚怖脊柱**（相邻）→ 恐惧降临：周围敌人获得恐惧效果
- **全部悚恐怖官** → 深渊注视：无视敌人防御，攻击无法被格挡

#### 9. 九头蛇协同

- **九头蛇心脏 + 九头蛇脊柱**（相邻）→ 再生之核：生命恢复速度+100%
- **九头蛇器官4个** → 九头之力：每次击杀敌人获得临时力量提升
- **九头蛇全套** → 不死九头：失去生命时会生长出新的"头"（额外生命条）

#### 10. 幻想种协同 ✅（已修正）

**修正说明**: 改为具体的属性修饰符和特殊药水效果

- **4个幻想种器官** → 梦境漫步：免疫物理伤害（通过伤害免疫事件），+30%魔法伤害
- **幻想种全套** → 现实扭曲：
  - 免疫物理伤害
  - 所有伤害+50%
  - 获得特殊药水效果：飞行（5分钟，冷却30分钟）
  - 获得特殊药水效果：伤害免疫（30秒，冷却5分钟）

**原设计问题**: "修改游戏规则"超出API能力
**修正方案**: 使用具体的属性修饰符和有限制的特殊药水效果

**实现逻辑**:
```java
public class FantasyFullSetEffect implements SynergyEffect {
    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        // 免疫物理伤害
        owner.addCapability(new PhysicalImmunityCapability());

        // 伤害提升
        addDamageBonus(owner, 0.5);

        // 飞行能力（限时）
        data.addTask(new LimitedFlightTask(300, 18000)); // 5分钟效果，30分钟冷却

        // 伤害免疫（限时）
        data.addTask(new LimitedImmunityTask(30, 300)); // 30秒效果，5分钟冷却
    }
}
```

#### 11. 跨主题创意协同

**冷热融合**:
- **火龙心脏 + 冰龙心脏** → 温度平衡：+40%所有温度抗性，攻击交替火焰和冰冻伤害

**生死轮回**:
- **肿瘤器官 + 九狱器官** → 死亡拥抱：生命值越低，伤害越高（最高+100%）

**科学与魔法**:
- **弗兰肯斯坦器官 + 幻想种器官** → 神秘实验：30%概率实验成功（获得随机强力效果，持续1分钟），70%概率失败（受到5点伤害）

**艺术与战争**:
- **墨水器官 + 火龙器官** → 火墨绘画：用火焰"绘制"伤害轨迹，敌人经过时受伤

---

## 平衡性与策略性

### 效果强度分级

#### 级别1：入门级 (Entry Level)

**触发条件**: 2-3个基础器官，容易获取
**效果强度**: +5-15%属性或小效果
**目标玩家**: 新手玩家

**示例**:
```
任意2个同种器官（相邻）→ +10%相关属性
```

#### 级别2：进阶级 (Advanced Level)

**触发条件**: 4-5个器官，需要一定探索
**效果强度**: +15-30%属性或中等效果
**目标玩家**: 中期玩家

**示例**:
```
4个同主题器官 → +20%主题伤害，解锁小型技能
```

#### 级别3：专家级 (Expert Level)

**触发条件**: 6-7个器官，需要深入探索或BOSS战
**效果强度**: +30-50%属性或强力效果
**目标玩家**: 后期玩家

**示例**:
```
6个同主题器官 → +40%主题伤害，解锁强力技能
```

#### 级别4：大师级 (Master Level)

**触发条件**: 8个以上器官或特定复杂组合
**效果强度**: +50-100%属性或改变游戏机制的效果
**目标玩家**: 硬核玩家

**示例**:
```
全套主题器官 + 特殊排列 → 颠覆性效果
```

### 稀有度与效果平衡

| 器官稀有度 | 搭配效果倍率 | 示例 |
|-----------|------------|------|
| 普通 (Common) | 1.0x | 基础器官搭配 |
| 罕见 (Uncommon) | 1.2x | 特殊生物器官 |
| 稀有 (Rare) | 1.5x | BOSS器官 |
| 史诗 (Epic) | 2.0x | 特殊BOSS器官 |
| 传说 (Legendary) | 3.0x | 超级BOSS或隐藏器官 |

### 策略路径设计

#### 路径A：极限专精 (Min-Max)

**理念**: 全部投入单一主题，追求极致效果

**优势**: 某方面极为强大
**劣势**: 缺乏多样性，容易被针对

**示例**: 全火龙器官 → 火焰伤害极高，但冰冻脆弱

#### 路径B：均衡发展 (Balanced)

**理念**: 混合多个主题，获得全面能力

**优势**: 没有明显短板
**劣势**: 缺乏突出优势

**示例**: 火龙4件 + 冰龙4件 → 火冰双修

#### 路径C：特殊策略 (Gimmick)

**理念**: 利用特殊机制或漏洞打造独特玩法

**优势**: 意想不到的强点
**劣势**: 需要深入理解，上手难度高

**示例**: 肿瘤+九狱 → 低血量高伤害的狂战士

#### 路径D：功能辅助 (Utility)

**理念**: 专注于辅助功能，而非直接战斗

**优势**: 团队价值高，探索便利
**劣势**: 单独作战能力较弱

**示例**: 墨水+颜料 → 隐形、陷阱、控制等辅助能力

### 获取难度与效果匹配

**容易获取** (普通怪物掉落):
- 效果不应过强
- 适合新手熟悉系统

**中等难度** (稀有怪物、小型BOSS):
- 效果适中
- 值得玩家投入时间

**高难度** (主要BOSS、特殊事件):
- 效果强力但不过于破坏平衡
- 给玩家成就感

**极高难度** (隐藏BOSS、特殊条件):
- 可以是突破性的强
- 但应该有代价或限制

---

## 具体搭配方案

### 推荐搭配组合（按难度分级）

#### 新手推荐 (易上手)

1. **火焰初学者**
   - 火龙心脏 + 火龙肺脏 + 火龙脊柱（相邻）
   - 效果: +20%火焰伤害，+10%火焰抗性
   - 获取: 击杀火龙

2. **冰霜守护**
   - 冰龙肋骨 × 2 + 冰龙脊柱（相邻）
   - 效果: +15%防御，受击时减速敌人
   - 获取: 击杀冰龙

3. **自然庇护**
   - 木质心脏 + 木质肝脏 + 木质肾脏（相邻）
   - 效果: +25%生命恢复，+10%毒素抗性
   - 获取: 探索森林遗迹

#### 进阶推荐 (需要探索)

4. **雷电法师**
   - 电龙心脏 + 电龙宝玉 + 电龙肺脏 + 电龙脊柱（相邻）
   - 效果: 攻击连锁3个敌人，+30%雷电伤害
   - 获取: 击杀电龙

5. **墨影刺客**
   - 墨水心脏 + 墨水肺脏 + 墨水脊柱 + 墨水瓶 + 钢笔尖（4个以上）
   - 效果: +25%闪避，隐形时伤害+50%
   - 获取: 完成"墨水之谜"任务链

6. **地狱行者**
   - 九狱器官（任意4个）
   - 效果: +20%所有伤害，每击杀10个敌人获得一次"地狱冲刺"
   - 获取: 击杀悚恐各层BOSS

#### 专家推荐 (挑战BOSS)

7. **龙神三态**
   - 火龙心脏 + 冰龙心脏 + 电龙心脏 + 各自吐息袋
   - 效果: 元素平衡，解锁"三龙吐息"超级技能
   - 获取: 击杀三种龙并获得其心脏

8. **科学怪人**
   - 弗兰肯斯坦全套（8个器官）
   - 效果: 所有器官效果+50%，但受到致命伤害直接死亡
   - 获取: 完成"弗兰肯斯坦的遗产"隐藏任务

9. **不死军团**
   - 九头蛇全套 + 肿瘤心脏 + 肿瘤肝脏
   - 效果: 多条生命，每条生命死亡后获得狂暴
   - 获取: 击杀九头蛇 + 深入肿瘤区域

#### 大师级 (终极挑战)

10. **艺术巅峰**
    - 墨水全套 + 颜料全套 + 钢笔尖 + 调色盘
    - 效果: 每10分钟创造1个随机器官的临时复制（持续5分钟）
    - 获取: 完成"艺术之神"终极挑战

11. **地狱九重天**
    - 九狱器官8个（按正确顺序放置在槽位0-7）
    - 效果: 地狱君主状态，免疫所有负面效果，攻击无法闪避
    - 获取: 击杀悚恐最终BOSS"路西法"

12. **现实扭曲**
    - 幻想种全套
    - 效果: 免疫物理伤害，+50%所有伤害，限时飞行和伤害免疫
    - 获取: 完成"幻想之梦"超长任务链

### 隐藏搭配（需要发现）

13. **闹鬼的合唱团**
    - 闹鬼的骨头 × 4
    - 效果: 4个骨头会自动在胸腔中移动，形成随机图案，每个图案提供不同效果
    - 彩蛋: 偶尔所有骨头排列成笑脸，给予巨大增益

14. **墨水与血的契约**
    - 墨水心脏 + 鲜血（任何含有血液的器官）
    - 效果: "血墨"状态，消耗生命值换取大幅伤害提升
    - 隐藏: 这种搭配会改变角色的对话和外观

15. **龙族的背叛**
    - 火龙心脏 + 冰龙脊柱 + 电龙肋骨
    - 效果: "混乱元素"，所有攻击变为随机元素，伤害+40%
    - 特殊: 击杀敌人时随机触发三种龙的吐息之一

---

## UI与反馈设计

### 视觉反馈

#### 1. 搭配高亮 (Synergy Highlighting)

**实现**: 当器官参与某个搭配时，在胸腔UI中高亮显示

**高亮方式**:
- **颜色编码**: 不同搭配类型使用不同颜色边框
  - 套装: 金色边框
  - 相邻: 蓝色边框
  - 主题: 紫色边框
  - 槽位: 绿色边框
- **连线效果**: 相邻器官之间绘制连接线
- **光晕效果**: 激活的搭配器官周围显示光晕

#### 2. 效果预览 (Effect Preview)

**实现**: 鼠标悬停在器官上时，显示当前激活的搭配效果

**显示内容**:
- 搭配名称
- 搭配类型
- 当前效果
- 预览下一步效果（如2/4件 → 4/6件）

#### 3. 进度指示器 (Progress Indicator)

**实现**: 显示套装搭配的进度

**示例**:
```
[██░░] 火龙套装 (2/4)
当前: +10%火焰伤害
下一级(4件): +20%火焰伤害 + 火焰吐息技能
```

### 音效反馈

#### 1. 搭配激活音效

- **小型搭配**: 轻微的"叮"声
- **中型搭配**: 中等音量的"能量共鸣"声
- **大型搭配**: 震撼的"能量爆发"声

#### 2. 搭配解除音效

- 相应的"能量消散"声，音量与激活时匹配

### 粒子效果

#### 1. 胸腔内效果

- **火焰搭配**: 胸腔内飘出火焰粒子
- **冰霜搭配**: 胸腔内出现霜冻粒子
- **雷电搭配**: 胸腔内闪过电弧

#### 2. 角色外效果

- **墨水搭配**: 角色脚下出现墨水水渍
- **幻想搭配**: 角色周围出现微光
- **九狱搭配**: 角色周围出现暗紫色火焰

### 通知系统

#### 1. 搭配发现通知

```
✨ 发现新搭配: "火焰三重奏"！
火龙心脏 + 火龙吐息袋 + 火龙肺脏（相邻）
效果: +30%火焰伤害，解锁"火焰吐息"技能
```

#### 2. 搭配升级通知

```
⬆️ 搭配升级: "火龙套装" 2件 → 4件
新效果: +20%火焰伤害 + 火焰吐息技能
```

#### 3. 搭配解除通知

```
❌ 搭配失效: "冰霜核心"
原因: 冰龙脊柱已被移除
```

### 信息显示

#### 1. 搭配日志 (Synergy Log)

**位置**: 胸腔UI右侧或单独的UI面板

**内容**:
- 当前激活的搭配列表
- 每个搭配的详细信息
- 搭配的激活时间（用于临时搭配）

#### 2. 搭配图鉴 (Synergy Codex)

**功能**: 记录发现的所有搭配

**内容**:
- 搭配名称和描述
- 触发条件
- 效果详情
- 发现次数
- 相关器官的获取位置

**进度**: 显示已发现搭配/总搭配数量

---

## 技术实现建议

### 数据结构设计

#### 1. 搭配定义 (Synergy Definition)

```java
public class OrganSynergy {
    private String id;                    // 搭配唯一ID
    private String name;                  // 搭配名称
    private String description;           // 搭配描述
    private SynergyType type;             // 搭配类型
    private List<OrganCondition> conditions;  // 触发条件
    private SynergyEffect effect;         // 搭配效果
    private int priority;                 // 优先级（1-100）
    private boolean isHidden;             // 是否隐藏

    // 优先级说明:
    // 100: 槽位触发（最精确）
    // 75:  相邻触发
    // 50:  套装触发
    // 25:  主题触发（最宽松）
}

public enum SynergyType {
    ADJACENT,      // 相邻触发
    SET_BONUS,     // 套装触发
    THEMATIC,      // 主题触发
    OPPOSITION,    // 对立触发
    SLOT_BASED     // 槽位触发（新增，替代几何触发）
}
```

#### 2. 条件系统 (Condition System)

```java
public interface OrganCondition {
    boolean matches(ChestCavityData data);
    int getPriority(); // 用于排序
}

// 示例: 套装条件
public class SetBonusCondition implements OrganCondition {
    private Set<Item> requiredOrgans;
    private int minCount;

    @Override
    public boolean matches(ChestCavityData data) {
        long count = requiredOrgans.stream()
            .mapToLong(item -> data.getOrganCount(item))
            .sum();
        return count >= minCount;
    }

    @Override
    public int getPriority() {
        return 50; // 套装触发优先级
    }
}

// 示例: 相邻条件
public class AdjacentCondition implements OrganCondition {
    private Set<Item> organSet;

    @Override
    public boolean matches(ChestCavityData data) {
        // 检查organSet中的器官是否相邻
        List<Integer> organSlots = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (!stack.isEmpty() && organSet.contains(stack.getItem())) {
                organSlots.add(i);
            }
        }

        if (organSlots.size() < 2) return false;

        // 检查是否有相邻的
        for (int i = 0; i < organSlots.size(); i++) {
            for (int j = i + 1; j < organSlots.size(); j++) {
                if (areAdjacent(organSlots.get(i), organSlots.get(j))) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int getPriority() {
        return 75; // 相邻触发优先级
    }
}

// 示例: 槽位条件（九狱顺序）
public class SlotSequenceCondition implements OrganCondition {
    private Map<Integer, Item> requiredSlots; // 槽位 -> 器官映射

    @Override
    public boolean matches(ChestCavityData data) {
        int correctCount = 0;

        for (Map.Entry<Integer, Item> entry : requiredSlots.entrySet()) {
            int slot = entry.getKey();
            Item required = entry.getValue();

            ItemStack stack = data.getStackInSlot(slot);
            if (stack.is(required)) {
                correctCount++;
            } else {
                break; // 顺序错误，停止检测
            }
        }

        return correctCount >= requiredSlots.size();
    }

    @Override
    public int getPriority() {
        return 100; // 槽位触发最高优先级
    }
}
```

#### 3. 效果系统 (Effect System)

```java
public interface SynergyEffect {
    void apply(LivingEntity owner, ChestCavityData data);
    void remove(LivingEntity owner, ChestCavityData data);
}

// 示例: 属性增益效果
public class AttributeBonusEffect implements SynergyEffect {
    private Map<Holder<Attribute>, Double> bonuses;
    private List<AttributeModifier> appliedModifiers = new ArrayList<>();

    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        for (Map.Entry<Holder<Attribute>, Double> entry : bonuses.entrySet()) {
            AttributeInstance attribute = owner.getAttribute(entry.getKey());
            if (attribute != null) {
                AttributeModifier modifier = new AttributeModifier(
                    UUID.randomUUID(),
                    "synergy_" + this.hashCode(),
                    entry.getValue(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                );
                attribute.addPermanentModifier(modifier);
                appliedModifiers.add(modifier);
            }
        }
    }

    @Override
    public void remove(LivingEntity owner, ChestCavityData data) {
        for (AttributeModifier modifier : appliedModifiers) {
            // 移除所有应用的修饰符
            for (AttributeInstance attr : owner.getAttributes().values()) {
                attr.removeModifier(modifier);
            }
        }
        appliedModifiers.clear();
    }
}

// 示例: 技能解锁效果
public class SkillUnlockEffect implements SynergyEffect {
    private ResourceLocation skillId;
    private IChestCavityTask task;

    @Override
    public void apply(LivingEntity owner, ChestCavityData data) {
        task = SkillManager.createTask(skillId);
        data.addTask(task);
    }

    @Override
    public void remove(LivingEntity owner, ChestCavityData data) {
        if (task != null) {
            data.getTasks().remove(task);
            task.onRemoved(owner);
        }
    }
}
```

### 搭配检测流程

```java
public class SynergyManager {
    private static final List<OrganSynergy> ALL_SYNERGIES = new ArrayList<>();
    private static final Map<UUID, SynergyCache> PLAYER_CACHES = new ConcurrentHashMap<>();

    /**
     * 检测并激活所有搭配
     */
    public static void detectAndApplySynergies(LivingEntity owner, ChestCavityData data) {
        UUID playerUUID = owner.getUUID();
        SynergyCache cache = PLAYER_CACHES.computeIfAbsent(playerUUID, uuid -> new SynergyCache());

        // 检查是否需要重新计算
        int currentHash = calculateOrganHash(data);
        if (currentHash == cache.getLastHash() && !cache.isExpired()) {
            return; // 使用缓存
        }

        // 移除旧的搭配效果
        cache.deactivateAll(owner, data);

        // 检测新的搭配
        List<ActiveSynergy> activeSynergies = detectSynergies(data, cache);

        // 按优先级排序
        activeSynergies.sort(Comparator.comparingInt(s -> s.getSynergy().getPriority()));

        // 应用搭配效果（处理优先级和冲突）
        applySynergiesWithPriority(owner, data, activeSynergies);

        // 更新缓存
        cache.update(currentHash, activeSynergies);
    }

    /**
     * 检测所有可能的搭配
     */
    private static List<ActiveSynergy> detectSynergies(ChestCavityData data, SynergyCache cache) {
        List<ActiveSynergy> activeSynergies = new ArrayList<>();

        for (OrganSynergy synergy : ALL_SYNERGIES) {
            // 跳过未发现的隐藏搭配
            if (synergy.isHidden() && !cache.isDiscovered(synergy.getId())) {
                continue;
            }

            // 检查所有条件
            boolean allMatch = true;
            for (OrganCondition condition : synergy.getConditions()) {
                if (!condition.matches(data)) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                activeSynergies.add(new ActiveSynergy(synergy));

                // 记录发现的搭配
                if (synergy.isHidden()) {
                    cache.markDiscovered(synergy.getId());
                    // 发送发现通知
                    notifySynergyDiscovered(owner, synergy);
                }
            }
        }

        return activeSynergies;
    }

    /**
     * 应用搭配效果，处理优先级和冲突
     */
    private static void applySynergiesWithPriority(LivingEntity owner, ChestCavityData data,
                                                   List<ActiveSynergy> activeSynergies) {
        // 按搭配类型分组
        Map<SynergyType, ActiveSynergy> appliedByType = new HashMap<>();

        for (ActiveSynergy active : activeSynergies) {
            SynergyType type = active.getSynergy().getType();

            // 同类型只应用最高优先级的
            if (!appliedByType.containsKey(type) ||
                active.getSynergy().getPriority() > appliedByType.get(type).getSynergy().getPriority()) {
                appliedByType.put(type, active);
            }
        }

        // 应用选定的搭配
        for (ActiveSynergy active : appliedByType.values()) {
            active.getSynergy().getEffect().apply(owner, data);
        }
    }

    /**
     * 计算器官配置的哈希值
     */
    private static int calculateOrganHash(ChestCavityData data) {
        int hash = 0;
        for (int i = 0; i < 27; i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (!stack.isEmpty()) {
                hash = hash * 31 + stack.getItem().hashCode();
                hash = hash * 31 + i; // 包含位置信息
            }
        }
        return hash;
    }
}
```

### 数据存储

#### 1. 搭配定义存储

使用 JSON 或数据包系统存储搭配定义，便于模组添加：

```json
{
  "synergy_id": "fire_dragon_trio",
  "name": "火焰三重奏",
  "description": "火龙心脏、吐息袋和肺脏的完美配合",
  "type": "adjacent",
  "conditions": [
    {
      "type": "adjacent_set",
      "organs": [
        "who_am_i_core:fire_dragon_heart",
        "who_am_i_core:fire_dragon_breath_sac",
        "who_am_i_core:fire_dragon_lung"
      ]
    }
  ],
  "effects": [
    {
      "type": "attribute_bonus",
      "attributes": {
        "minecraft:generic.attack_damage": 0.3
      }
    },
    {
      "type": "skill_unlock",
      "skill": "fire_breath"
    }
  ],
  "priority": 75,
  "hidden": false
}
```

#### 2. 玩家进度存储

```java
public class PlayerSynergyData {
    private Set<String> discoveredSynergies = new HashSet<>();  // 已发现的搭配
    private Map<String, Integer> synergyUsageCount = new HashMap<>();  // 使用次数
    private Map<String, Long> firstDiscoveryTime = new HashMap<>();  // 首次发现时间

    public void markDiscovered(String synergyId) {
        if (!discoveredSynergies.contains(synergyId)) {
            discoveredSynergies.add(synergyId);
            firstDiscoveryTime.put(synergyId, System.currentTimeMillis());
        }
    }

    public boolean isDiscovered(String synergyId) {
        return discoveredSynergies.contains(synergyId);
    }
}
```

### 事件系统集成

```java
public class SynergyEvents {
    // 搭配激活事件
    public static final Event<SynergyActivateCallback> SYNERGY_ACTIVATE = EventFactory.createLoop();

    // 搭配解除事件
    public static final Event<SynergyDeactivateCallback> SYNERGY_DEACTIVATE = EventFactory.createLoop();

    // 搭配发现事件（首次触发）
    public static final Event<SynergyDiscoverCallback> SYNERGY_DISCOVER = EventFactory.createLoop();

    @FunctionalInterface
    public interface SynergyActivateCallback {
        void onActivate(LivingEntity owner, OrganSynergy synergy);
    }

    @FunctionalInterface
    public interface SynergyDeactivateCallback {
        void onDeactivate(LivingEntity owner, OrganSynergy synergy);
    }

    @FunctionalInterface
    public interface SynergyDiscoverCallback {
        void onDiscover(LivingEntity owner, OrganSynergy synergy);
    }
}
```

### 调试工具

#### 1. 搭配测试命令

```
/synergy list - 列出所有搭配
/synergy test <synergy_id> - 测试特定搭配
/synergy discover_all - 发现所有搭配（调试用）
/synergy reload - 重新加载搭配配置
/synergy cache clear - 清除搭配缓存
/synergy debug - 显示调试信息
```

#### 2. 搭配可视化

- 在胸腔UI中显示所有可能的搭配（即使未激活）
- 用不同颜色显示激活/未激活状态

---

## 性能优化方案

### 性能指标

**目标性能**:
- 搭配检测时间: < 50ms（单次）
- 内存占用: < 5MB per player
- CPU占用: < 1%（平均）
- 不影响游戏FPS

### 优化策略

#### 1. 缓存机制 ✅（已详细说明）

```java
public class SynergyCache {
    private int lastHash = 0;
    private List<ActiveSynergy> cachedSynergies = new ArrayList<>();
    private long lastUpdateTime = 0;
    private static final long CACHE_EXPIRY_MS = 5000; // 5秒过期

    public boolean isExpired() {
        return System.currentTimeMillis() - lastUpdateTime > CACHE_EXPIRY_MS;
    }

    public void update(int hash, List<ActiveSynergy> synergies) {
        this.lastHash = hash;
        this.cachedSynergies = synergies;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public List<ActiveSynergy> getCachedSynergies() {
        return new ArrayList<>(cachedSynergies);
    }
}
```

**缓存策略**:
- 基于哈希值的缓存
- 5秒过期时间（平衡性能和准确性）
- 玩家独立缓存
- 自动失效机制

#### 2. 延迟计算 ✅

```java
public class ChestCavityData extends ItemStackHandler {
    private boolean synergyDirty = true;

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        synergyDirty = true;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack removeStack = super.extractItem(slot, amount, simulate);
        if (!simulate) {
            synergyDirty = true;
        }
        return removeStack;
    }

    /**
     * 延迟计算搭配
     * 在胸腔关闭时统一调用
     */
    public void updateSynergiesIfDirty() {
        if (synergyDirty) {
            SynergyManager.detectAndApplySynergies(owner, this);
            synergyDirty = false;
        }
    }
}
```

**延迟计算策略**:
- 标记脏数据
- 在胸腔关闭时统一计算
- 避免频繁计算

#### 3. 分区计算 ✅

```java
public class SynergyManager {
    // 只计算受影响区域的搭配
    public static void detectSynergiesForSlot(LivingEntity owner, ChestCavityData data, int changedSlot) {
        // 获取受影响的槽位（相邻槽位）
        int[] affectedSlots = getAffectedSlots(changedSlot);

        // 只检测涉及这些槽位的搭配
        for (OrganSynergy synergy : ALL_SYNERGIES) {
            if (synergy.affectsSlots(affectedSlots)) {
                // 检测并应用
            }
        }
    }

    private static int[] getAffectedSlots(int slot) {
        int[] adjacent = getAdjacentSlots(slot);
        int[] affected = new int[adjacent.length + 1];
        System.arraycopy(adjacent, 0, affected, 0, adjacent.length);
        affected[adjacent.length] = slot;
        return affected;
    }
}
```

**分区计算策略**:
- 只计算受影响的区域
- 减少检测范围
- 提高局部更新性能

#### 4. 早期退出优化 ✅

```java
public class SetBonusCondition implements OrganCondition {
    @Override
    public boolean matches(ChestCavityData data) {
        int count = 0;
        int required = minCount;

        for (Item item : requiredOrgans) {
            count += data.getOrganCount(item);

            // 早期退出：已满足条件
            if (count >= required) {
                return true;
            }

            // 早期退出：即使剩余器官都满足也不够
            int remainingOrgans = requiredOrgans.size() - requiredOrgans.indexOf(item) - 1;
            if (count + remainingOrgans < required) {
                return false;
            }
        }

        return count >= required;
    }
}
```

**早期退出策略**:
- 满足条件立即返回
- 不可能满足立即返回
- 减少不必要的计算

#### 5. 批量处理 ✅

```java
public class SynergyManager {
    // 批量检测多个玩家的搭配
    public static void batchDetectSynergies(List<LivingEntity> entities) {
        // 并行处理（如果线程安全）
        entities.parallelStream().forEach(entity -> {
            ChestCavityData data = ChestCavityData.get(entity);
            if (data != null) {
                detectAndApplySynergies(entity, data);
            }
        });
    }
}
```

**批量处理策略**:
- 多玩家服务器使用并行流
- 减少 CPU 空闲时间
- 提高服务器整体性能

#### 6. 预计算和预热 ✅

```java
public class SynergyManager {
    // 游戏加载时预计算
    public static void preloadSynergies() {
        // 预计算所有可能的搭配组合
        for (OrganSynergy synergy : ALL_SYNERGIES) {
            synergy.precompute();
        }
    }
}

public class OrganSynergy {
    private Map<Set<Item>, Boolean> precomputedResults = new HashMap<>();

    public void precompute() {
        // 预计算常见组合
        for (Set<Item> combination : getCommonCombinations()) {
            boolean result = conditions.stream()
                .allMatch(cond -> cond.matchesPrecomputed(combination));
            precomputedResults.put(combination, result);
        }
    }
}
```

**预计算策略**:
- 游戏加载时预计算常见组合
- 运行时直接查表
- 减少实时计算量

#### 7. 内存优化 ✅

```java
public class SynergyCache {
    // 使用弱引用，允许GC回收
    private static final Map<UUID, WeakReference<SynergyCache>> PLAYER_CACHES = new ConcurrentHashMap<>();

    public static SynergyCache getCache(UUID playerUUID) {
        WeakReference<SynergyCache> ref = PLAYER_CACHES.get(playerUUID);
        if (ref != null) {
            SynergyCache cache = ref.get();
            if (cache != null) {
                return cache;
            }
        }

        SynergyCache newCache = new SynergyCache();
        PLAYER_CACHES.put(playerUUID, new WeakReference<>(newCache));
        return newCache;
    }
}
```

**内存优化策略**:
- 使用弱引用
- 自动回收离线玩家缓存
- 减少内存占用

### 性能监控

```java
public class SynergyPerformanceMonitor {
    private static final Map<String, Long> detectionTimes = new HashMap<>();
    private static final Map<String, Integer> detectionCounts = new HashMap<>();

    public static void recordDetection(String synergyId, long timeMs) {
        detectionTimes.merge(synergyId, timeMs, Long::sum);
        detectionCounts.merge(synergyId, 1, Integer::sum);
    }

    public static void printStatistics() {
        System.out.println("=== Synergy Performance Statistics ===");
        for (Map.Entry<String, Integer> entry : detectionCounts.entrySet()) {
            String id = entry.getKey();
            int count = entry.getValue();
            long totalTime = detectionTimes.get(id);
            long avgTime = totalTime / count;

            System.out.printf("%s: %d detections, avg %dms%n", id, count, avgTime);
        }
    }
}
```

### 性能测试计划

1. **单元测试**: 测试单个搭配检测的性能
2. **集成测试**: 测试整个搭配系统的性能
3. **压力测试**: 模拟大量玩家同时使用
4. **内存测试**: 监控内存占用和GC
5. **FPS测试**: 确保不影响游戏帧率

---

## 总结与后续开发

### 实现阶段

#### 阶段1: 基础系统 (1-2周)
- 实现核心数据结构
- 实现相邻和套装触发
- 基础UI反馈
- 性能优化（缓存机制）

#### 阶段2: 扩展系统 (2-3周)
- 添加主题和槽位触发
- 完善UI和反馈
- 添加音效和粒子
- 性能优化（延迟计算）

#### 阶段3: 内容填充 (3-4周)
- 设计并实现所有搭配
- 平衡性调整
- 搭配图鉴系统
- 性能优化（分区计算）

#### 阶段4: 优化与打磨 (1-2周)
- 性能优化（早期退出、批量处理）
- Bug修复
- 最终平衡调整
- 性能测试和调优

### 设计优势

1. **高度可扩展**: 新器官和搭配可以轻松添加
2. **玩家友好**: 多层次的反馈系统让玩家容易理解
3. **深度策略**: 多种搭配路径满足不同玩家风格
4. **内容丰富**: 12种主要协同 × 多个等级 = 数百种搭配
5. **探索激励**: 隐藏搭配和图鉴系统鼓励实验
6. **技术可行**: 所有设计都基于现有API能力
7. **性能优化**: 完整的优化策略确保流畅体验

### 风险与挑战

1. **平衡性**: 需要大量测试和调整
2. **性能**: 复杂的检测系统可能影响性能（已提供优化方案）
3. **UI复杂度**: 需要精心设计UI避免信息过载
4. **玩家学习曲线**: 需要良好的教程和引导

### 后续扩展方向

1. **动态搭配**: 搭配效果随时间或环境变化
2. **玩家自定义**: 允许玩家创建自己的搭配（需谨慎）
3. **搭配进化**: 搭配可以通过使用升级
4. **多人协同**: 不同玩家之间的搭配互动
5. **赛季搭配**: 定期更新的特殊搭配

---

## 附录：搭配速查表

| 搭配ID | 名称 | 类型 | 效果等级 | 获取难度 |
|--------|------|------|---------|---------|
| fire_trio | 火焰三重奏 | 相邻 | ★★☆☆☆ | ★★☆☆☆ |
| fire_set_4 | 火龙4件 | 套装 | ★★★☆☆ | ★★★☆☆ |
| fire_set_6 | 火龙6件 | 套装 | ★★★★☆ | ★★★★☆ |
| ice_core | 冰霜核心 | 相邻 | ★★☆☆☆ | ★★☆☆☆ |
| dragon_trinity | 三龙共鸣 | 主题 | ★★★★★ | ★★★★★ |
| ink_shadow | 墨影迷踪 | 套装 | ★★★☆☆ | ★★★☆☆ |
| inferno_sequence | 地狱巡礼 | 槽位 | ★★★★★ | ★★★★★ |
| creation_peak | 艺术巅峰 | 套装 | ★★★★★ | ★★★★★ |

---

**文档结束**

*这份设计文档v2.0修正了原版本中的技术不可行问题，确保所有设计都基于现有API能力。所有搭配系统都经过技术可行性验证，并提供了完整的性能优化方案。*

**版本历史**:
- v1.0: 初始设计（包含几何搭配、装备顺序等不可行设计）
- v2.0: 技术可行性修正（移除几何搭配，改为槽位触发；修正九狱顺序；明确拟态和幻想种机制；添加性能优化方案）

# 肿瘤共生机制设计文档 T4

**设计版本**: v1.0
**创建日期**: 2026-03-15
**设计者**: Creative Content Designer
**状态**: 待审核
**前置依赖**: T1-器官基础属性, T2-类型分类体系, T3-增殖机制

---

## 目录

1. [设计概述](#设计概述)
2. [共生核心概念](#共生核心概念)
3. [三种共生类型](#三种共生类型)
4. [共生条件判定](#共生条件判定)
5. [共生效果计算](#共生效果计算)
6. [肿瘤间协同系统](#肿瘤间协同系统)
7. [跨系交互设计](#跨系交互设计)
8. [共生视觉反馈](#共生视觉反馈)
9. [平衡性设计](#平衡性设计)
10. [技术实现方案](#技术实现方案)
11. [JSON配置示例](#json配置示例)
12. [性能优化策略](#性能优化策略)
13. [共生实例设计](#共生实例设计)

---

## 设计概述

### 核心设计理念

肿瘤共生机制是肿瘤流派的高级系统,体现了肿瘤"整合、适应、转化"的能力。共生不是简单的器官叠加,而是**器官间的深度互动和协同进化**——通过特定条件触发,两个或多个器官可以产生超出个体总和的强大效果。

### 设计原则

1. **深度互动** - 共生效果大于各器官效果之和
2. **策略选择** - 玩家需要主动创造共生条件
3. **多样性** - 多种共生类型和组合,避免单一最优解
4. **主题一致性** - 共生效果符合肿瘤"变异、不稳定、侵蚀性"特征
5. **平衡性** - 强大的共生效果伴随相应的代价或风险

### 共生系统的核心价值

- **战术深度**: 玩家需要精心规划器官摆放和类型组合
- **Build多样性**: 不同共生策略产生不同的游戏体验
- **技能天花板**: 高级共生系统为高手提供追求目标
- **惊喜感**: 共生触发带来强烈的满足感和视觉反馈

### 与T1-T3的协调

| 系统 | 核心机制 | 与共生的关系 |
|------|---------|-------------|
| **T1 基础属性** | 器官的基础属性和独特效果 | 共生基于这些属性进行增强或转化 |
| **T2 类型分类** | 良性/恶性/特殊三种类型 | 共生类型与器官类型密切相关 |
| **T3 增殖机制** | 肿瘤器官的复制和变异 | 增殖可以创造共生条件,共生可以触发增殖 |

---

## 共生核心概念

### 什么是"共生"？

共生是指两个或多个肿瘤器官在特定条件下产生的特殊互动效果。共生不同于相邻搭配(Adjacent Synergy),它是更深层次的器官融合和协同进化。

#### 共生 vs 相邻搭配

| 特征 | 相邻搭配 | 共生 |
|------|---------|------|
| **触发条件** | 两个特定器官相邻 | 满足特定条件(类型、数量、位置等) |
| **效果强度** | 中等增强(15-30%) | 强力增强或全新效果(50-200%) |
| **持续时间** | 持续激活 | 可能有时限或触发条件 |
| **数量限制** | 无限制 | 通常有数量限制 |
| **视觉反馈** | 轻微粒子效果 | 显著的视觉变化和特效 |

### 共生度系统

共生度(Symbiosis Degree)是衡量玩家"共生状态"的指标:

| 共生度等级 | 名称 | 范围 | 效果 |
|-----------|------|------|------|
| 0 | 独立期 | 0 | 无共生效果 |
| 1 | 接触期 | 1-2 | 1个共生效果激活 |
| 2 | 融合期 | 3-4 | 2个共生效果激活,轻度副作用 |
| 3 | 共振期 | 5-6 | 3个共生效果激活,中度副作用 |
| 4 | 统一期 | 7+ | 4+共生效果激活,重度副作用,但有特殊奖励 |

**共生度计算**:
- 每个激活的共生效果贡献1点共生度
- 某些特殊共生效果额外贡献1点
- 共生度影响某些高级效果的触发

### 共生链系统

当多个器官连续形成共生时,会产生"共生链"效果:

```
[器官A] --共生--> [器官B] --共生--> [器官C]
     ↓              ↓              ↓
   基础效果      增强效果       链式倍增效果
```

**共生链奖励**:
- 2链: 共生效果+25%
- 3链: 共生效果+50%
- 4链: 共生效果+100%,并解锁"完美共生"特殊效果
- 5+链: 共生效果+150%,并触发"器官进化"可能性

---

## 三种共生类型

### 类型概览

| 共生类型 | 符号 | 核心机制 | 典型效果 | 风险等级 |
|---------|------|---------|---------|---------|
| **寄生型** | 🦠 | 吸收相邻器官属性/效果 | 单方面强化 | ★★★☆☆ |
| **互惠型** | 🤝 | 互相增强,共同进化 | 双向强化 | ★★☆☆☆ |
| **转化型** | 🔄 | 将相邻器官转化为肿瘤 | 质变,永久改变 | ★★★★★ |

### 寄生型共生 (Parasitic Symbiosis)

#### 核心概念

寄生型共生代表肿瘤器官对相邻器官的"寄生"和"吸收"。肿瘤器官单方面从相邻器官获得强化,而相邻器官可能被削弱或无变化。

#### 设计理念

- **单向流动**: 效果从相邻器官流向肿瘤器官
- **吸收主题**: 肿瘤器官"吞噬"相邻器官的力量
- **零和博弈**: 相邻器官被削弱,肿瘤器官被强化
- **风险可控**: 玩家可以控制哪些器官被寄生

#### 寄生型实例

**1. 心脏寄生 (Heart Parasitism)**
- **主器官**: 肿瘤心脏
- **相邻器官**: 任何心脏器官
- **效果**: 肿瘤心脏获得相邻心脏50%的属性,相邻心脏失去30%属性
- **视觉**: 相邻心脏颜色变暗,肿瘤心脏脉动增强
- **约束**: 只能同时寄生1个心脏

**2. 肌肉寄生 (Muscle Parasitism)**
- **主器官**: 肿瘤肌肉
- **相邻器官**: 任何肌肉器官
- **效果**: 肿瘤肌肉获得相邻肌肉70%的力量,但肌肉痉挛概率+10%
- **视觉**: 相邻肌肉萎缩,肿瘤肌肉膨胀
- **约束**: 可以同时寄生最多2个肌肉

**3. 全面寄生 (General Parasitism)**
- **主器官**: 任何恶性肿瘤
- **相邻器官**: 任何非肿瘤器官
- **效果**: 获得20%相邻器官属性,相邻器官效果-10%
- **视觉**: 相邻器官表面出现肿瘤纹理
- **约束**: 每个恶性肿瘤最多同时寄生3个器官

#### 寄生型平衡

**优点**:
- ✅ 显著提升肿瘤器官属性
- ✅ 灵活性高,可以选择寄生目标
- ✅ 适合追求极致属性的玩家

**缺点**:
- ❌ 削弱相邻器官,可能影响其他系统
- ❌ 可能破坏其他搭配效果
- ❌ 视觉上可能令人不适

**平衡调整**:
- 寄生效果不超过50%相邻器官属性
- 相邻器官属性削弱不超过30%
- 限制同时寄生的数量

### 互惠型共生 (Mutualistic Symbiosis)

#### 核心概念

互惠型共生代表肿瘤器官与相邻器官的"互利共赢"。两个器官互相增强,产生大于个体总和的效果。

#### 设计理念

- **双向流动**: 效果在两个器官间互相增强
- **协同主题**: 两个器官协同工作,共同进化
- **正和博弈**: 两个器官都被强化
- **风险较低**: 副作用相对温和

#### 互惠型实例

**1. 呼吸共生 (Respiratory Mutualism)**
- **参与器官**: 肿瘤肺脏 + 任何肺脏器官
- **效果**:
  - 肿瘤肺脏: 毒素云范围+50%,伤害+30%
  - 相邻肺脏: 呼吸效率+30%
  - 共生效果: 可以在水下呼吸(有限时间)
- **视觉**: 两个肺脏产生连接的绿色气流
- **约束**: 只能与1个肺脏器官共生

**2. 代谢共生 (Metabolic Mutualism)**
- **参与器官**: 肿瘤胃 + 肿瘤肠子
- **效果**:
  - 肿瘤胃: 饥饿消耗-50%,食物效果+100%
  - 肿瘤肠子: 状态持续时间+50%,切换速度+25%
  - 共生效果: 消耗食物时随机获得临时增益(持续60秒)
- **视觉**: 两个器官间有食物粒子流动
- **约束**: 必须相邻

**3. 免疫共生 (Immune Mutualism)**
- **参与器官**: 肿瘤脾脏 + 肿瘤肝脏
- **效果**:
  - 肿瘤脾脏: 免疫转化概率+15%(达到40%)
  - 肿瘤肝脏: 毒素自然清除速度翻倍
  - 共生效果: 每30秒自动清除1个负面效果
- **视觉**: 两个器官产生白色免疫粒子
- **约束**: 必须相邻

**4. 净化共生 (Purification Mutualism)**
- **参与器官**: 肿瘤肾脏 + 肿瘤肝脏
- **效果**:
  - 肿瘤肾脏: 废物层数上限+5(达到15层)
  - 肿瘤肝脏: 毒素层数上限+5(达到14层)
  - 共生效果: 层数可以互相转化(毒素↔废物)
- **视觉**: 两个器官产生蓝色净化光
- **约束**: 必须相邻

**5. 狂暴共生 (Frenzy Mutualism)**
- **参与器官**: 肿瘤心脏 + 肿瘤肌肉
- **效果**:
  - 肿瘤心脏: 狂暴阈值提升至50%
  - 肿瘤肌肉: 肌肉痉挛概率-10%(降至10%)
  - 共生效果: 狂暴期间伤害+100%,速度+50%
- **视觉**: 两个器官产生红色闪电连接
- **约束**: 必须相邻,且血量低于50%时触发

#### 互惠型平衡

**优点**:
- ✅ 两个器官都被强化
- ✅ 创造全新的协同效果
- ✅ 副作用相对温和
- ✅ 适合稳定发育

**缺点**:
- ❌ 需要特定的器官组合
- ❌ 占用多个槽位
- ❌ 效果可能不如寄生型极致

**平衡调整**:
- 互惠增益在30-50%之间
- 共生特效额外增加20-30%强度
- 副作用降低10-20%

### 转化型共生 (Transformative Symbiosis)

#### 核心概念

转化型共生代表肿瘤器官对相邻器官的"转化"和"同化"。相邻器官被完全转化为肿瘤器官,永久改变玩家的器官构成。

#### 设计理念

- **质变**: 相邻器官变成肿瘤器官
- **不可逆**: 转化后无法逆转
- **高风险高回报**: 极强的效果,但永久失去原器官
- **主题性**: 完全符合肿瘤"侵蚀"和"增殖"的本质

#### 转化型实例

**1. 完全转化 (Complete Transformation)**
- **主器官**: 肿瘤阑尾
- **相邻器官**: 任何器官
- **触发条件**: 阑尾觉醒成功(5%概率)
- **效果**: 将相邻器官转化为随机肿瘤器官(良性/恶性/特殊各33%概率)
- **风险**: 转化后的肿瘤器官可能不理想
- **视觉**: 阑尾发出彩虹光束,相邻器官迅速变异
- **约束**: 每次胸腔关闭只能触发1次

**2. 恶性转化 (Malignant Transformation)**
- **主器官**: 任何恶性肿瘤
- **相邻器官**: 任何良性器官
- **触发条件**: 血月,血量低于20%
- **效果**: 将相邻良性器官转化为恶性肿瘤,但失去50%当前生命值
- **风险**: 可能导致死亡
- **视觉**: 黑色雾气从恶性肿瘤扩散到良性器官
- **约束**: 每次血月只能转化1个器官

**3. 增殖转化 (Proliferative Transformation)**
- **主器官**: 肿瘤心脏
- **相邻器官**: 任何肿瘤器官
- **触发条件**: 装备6+个肿瘤器官,击败BOSS
- **效果**: 相邻肿瘤器官分裂为2个(增殖),但肿瘤心脏失去"增殖之心"效果24小时
- **风险**: 失去核心器官效果
- **视觉**: 心脏发出强烈紫光,相邻器官一分为二
- **约束**: 每天只能触发1次

**4. 同化转化 (Assimilation Transformation)**
- **主器官**: 肿瘤肠子
- **相邻器官**: 任何器官
- **触发条件**: 肠子进入"强化状态"
- **效果**: 将相邻器官同化为肿瘤器官,并获得其50%效果
- **风险**: 同化后的器官失去原有所有效果
- **视觉**: 肠子伸展出触须,包裹相邻器官
- **约束**: 只能在"强化状态"触发

#### 转化型平衡

**优点**:
- ✅ 极强的效果,完全改变器官构成
- ✅ 符合肿瘤"增殖"和"侵蚀"主题
- ✅ 创造独特的游戏体验
- ✅ 适合追求极端Build的玩家

**缺点**:
- ❌ 不可逆,可能永久破坏Build
- ❌ 高风险,可能导致死亡或Build崩溃
- ❌ 触发条件苛刻
- ❌ 结果随机,难以预测

**平衡调整**:
- 转化型共生必须有明确的触发条件和警告
- 转化效果必须足够强,值得冒险
- 提供转化预览,让玩家知道会发生什么
- 某些转化可以撤销(但代价高昂)

---

## 共生条件判定

### 相邻判定机制

#### 使用现有API

```java
// 使用Chest Cavity Beyond的getAdjacentSlots API
List<Integer> adjacentSlots = chestCavityData.getAdjacentSlots(slotIndex);

// 示例: 检测槽位13(中心位置)的相邻器官
List<Integer> adjacentTo13 = chestCavityData.getAdjacentSlots(13);
// 返回: [4, 5, 6, 12, 14, 22, 23, 24] (上下左右及对角线)
```

#### 相邻类型

```
胸腔3x9网格布局:
0  1  2  3  4  5  6  7  8
9  10 11 12 13 14 15 16 17
18 19 20 21 22 23 24 25 26

相邻关系:
- 正交相邻(上下左右): 4个方向
- 对角相邻(斜角): 4个方向
- 全相邻: 正交+对角=8个方向
```

#### 相邻判定优先级

| 相邻类型 | 检测范围 | 共生强度 | 性能消耗 |
|---------|---------|---------|---------|
| **正交相邻** | 4方向 | 低(1.0x) | 低 |
| **对角相邻** | 4方向 | 中(0.7x) | 低 |
| **全相邻** | 8方向 | 高(1.2x) | 中 |
| **区域相邻** | 3x3区域 | 极高(1.5x) | 高 |

### 共生触发条件

#### 基础条件

1. **位置条件**: 器官必须在指定相邻范围内
2. **类型条件**: 器官类型满足要求(如良性+恶性)
3. **数量条件**: 肿瘤器官数量达到阈值
4. **状态条件**: 玩家状态满足要求(如血量、环境)

#### 高级条件

1. **时间条件**: 特定时间触发(如血月、夜晚)
2. **事件条件**: 特定事件触发(如击败BOSS、使用药水)
3. **属性条件**: 属性值达到阈值(如毒素层数>5)
4. **连锁条件**: 其他共生效果激活后触发

#### 条件组合示例

**呼吸共生条件**:
```
必须满足以下所有条件:
1. 肿瘤肺脏与肺脏器官正交相邻
2. 玩家处于水下或雨天环境
3. 玩家血量>50%
```

**狂暴共生条件**:
```
必须满足以下所有条件:
1. 肿瘤心脏与肿瘤肌肉正交相邻
2. 玩家血量<50%
3. 玩家最近10秒内受到伤害
```

**完全转化条件**:
```
必须满足以下任意条件:
1. 阑尾觉醒成功(5%概率,胸腔关闭时)
2. 使用特殊物品"觉醒药水"
3. 装备8+个肿瘤器官并主动触发
```

### 共生优先级系统

当多个共生可能同时触发时,使用优先级系统决定执行顺序:

#### 优先级等级

| 优先级 | 名称 | 典型共生类型 | 执行顺序 |
|-------|------|------------|---------|
| 0 | **紧急级** | 转化型共生(致命) | 最先执行 |
| 1 | **高级** | 转化型共生(普通) | 第二执行 |
| 2 | **中级** | 互惠型共生(特殊) | 第三执行 |
| 3 | **普通级** | 互惠型共生(普通) | 第四执行 |
| 4 | **基础级** | 寄生型共生 | 最后执行 |

#### 冲突解决

1. **互斥共生**: 某些共生不能同时激活
   - 示例: 肿瘤心脏只能同时寄生1个心脏
   - 解决: 选择优先级更高的共生,或者按激活顺序选择第一个

2. **资源竞争**: 多个共生竞争同一器官
   - 示例: 两个恶性肿瘤都想寄生同一个器官
   - 解决: 按优先级分配,或者轮流激活

3. **效果覆盖**: 高优先级覆盖低优先级
   - 示例: 转化型共生会覆盖原有的寄生型共生
   - 解决: 转化型共生总是优先

#### 动态优先级调整

某些条件可以动态调整优先级:

```java
// 计算动态优先级
int calculatePriority(Symbiosis symbiosis, Player player) {
    int basePriority = symbiosis.getBasePriority();
    int tumorCount = getTumorCount(player);
    int healthPercent = (int)(player.getHealth() / player.getMaxHealth() * 100);

    // 肿瘤器官越多,转化型优先级越高
    if (symbiosis.isTransformative() && tumorCount >= 6) {
        basePriority -= 1; // 优先级提升
    }

    // 低血量时,狂暴类共生优先级提升
    if (symbiosis.isFrenzyBased() && healthPercent < 30) {
        basePriority -= 1;
    }

    return basePriority;
}
```

---

## 共生效果计算

### 基础效果计算

#### 属性吸收计算(寄生型)

```java
// 寄生型属性吸收公式
float calculateParasiticAbsorption(
    float targetAttributeValue,
    float absorptionRate,
    float penaltyRate
) {
    // 吸收效果
    float absorbedAmount = targetAttributeValue * absorptionRate;

    // 削减效果
    float penaltyAmount = targetAttributeValue * penaltyRate;

    // 应用效果
    // 肿瘤器官获得: absorbedAmount
    // 相邻器官失去: penaltyAmount

    return absorbedAmount;
}

// 示例: 心脏寄生
// 相邻心脏: +4 HEALTH
// 吸收率: 50%
// 削减率: 30%
// 结果:
//   肿瘤心脏获得: +4 * 0.5 = +2 HEALTH
//   相邻心脏失去: +4 * 0.3 = +1.2 HEALTH (变为+2.8 HEALTH)
```

#### 互惠增强计算(互惠型)

```java
// 互惠型增强公式
float[] calculateMutualisticBoost(
    float organ1AttributeValue,
    float organ2AttributeValue,
    float boostRate,
    float synergyBonus
) {
    // 基础增强
    float boost1 = organ1AttributeValue * boostRate;
    float boost2 = organ2AttributeValue * boostRate;

    // 协同加成
    float synergyAmount1 = organ1AttributeValue * synergyBonus;
    float synergyAmount2 = organ2AttributeValue * synergyBonus;

    // 总增强
    float totalBoost1 = boost1 + synergyAmount1;
    float totalBoost2 = boost2 + synergyAmount2;

    return new float[]{totalBoost1, totalBoost2};
}

// 示例: 呼吸共生
// 肿瘤肺脏: +3 BREATH
// 相邻肺脏: +2 BREATH
// 增强率: 30%
// 协同加成: 20%
// 结果:
//   肿瘤肺脏: +3 * 0.3 + +3 * 0.2 = +1.5 BREATH (总计+4.5)
//   相邻肺脏: +2 * 0.3 + +2 * 0.2 = +1.0 BREATH (总计+3.0)
```

#### 转化效果计算(转化型)

```java
// 转化型效果计算
ItemStack calculateTransformation(
    ItemStack targetOrgan,
    TumorType targetType,
    float baseAttributeRetention
) {
    // 获取原器官属性
    Map<Attribute, Float> originalAttributes = getOrganAttributes(targetOrgan);

    // 创建新肿瘤器官
    ItemStack newTumor = createTumorOrgan(targetType);

    // 保留部分原属性
    Map<Attribute, Float> retainedAttributes = new HashMap<>();
    for (Map.Entry<Attribute, Float> entry : originalAttributes.entrySet()) {
        float retainedValue = entry.getValue() * baseAttributeRetention;
        retainedAttributes.put(entry.getKey(), retainedValue);
    }

    // 添加肿瘤器官基础属性
    Map<Attribute, Float> tumorAttributes = getOrganAttributes(newTumor);
    for (Map.Entry<Attribute, Float> entry : tumorAttributes.entrySet()) {
        retainedAttributes.merge(entry.getKey(), entry.getValue(), Float::sum);
    }

    // 应用属性到新器官
    setOrganAttributes(newTumor, retainedAttributes);

    return newTumor;
}

// 示例: 完全转化
// 原器官: 普通心脏 (+2 HEALTH)
// 目标类型: 随机肿瘤
// 属性保留率: 50%
// 结果:
//   新肿瘤心脏: 原有50%属性 + 肿瘤心脏基础属性
//   = (+2 * 0.5) + (+4) = +5 HEALTH
```

### 高级效果计算

#### 共生链倍增计算

```java
// 共生链倍增公式
float calculateChainMultiplier(int chainLength) {
    float baseMultiplier = 1.0f;

    switch (chainLength) {
        case 2:
            baseMultiplier = 1.25f; // +25%
            break;
        case 3:
            baseMultiplier = 1.5f;  // +50%
            break;
        case 4:
            baseMultiplier = 2.0f;  // +100%
            break;
        case 5:
            baseMultiplier = 2.5f;  // +150%
            break;
        default:
            if (chainLength >= 6) {
                baseMultiplier = 3.0f; // +200% (封顶)
            }
            break;
    }

    return baseMultiplier;
}

// 示例: 3链呼吸共生
// 基础效果: +1.5 BREATH
// 链长: 3
// 倍数: 1.5x
// 最终效果: +1.5 * 1.5 = +2.25 BREATH
```

#### 共生度加成计算

```java
// 共生度加成公式
float calculateSymbiosisDegreeBonus(int symbiosisDegree) {
    float bonus = 0.0f;

    switch (symbiosisDegree) {
        case 0:
            bonus = 0.0f;
            break;
        case 1:
        case 2:
            bonus = 0.1f; // +10%
            break;
        case 3:
        case 4:
            bonus = 0.2f; // +20%
            break;
        case 5:
        case 6:
            bonus = 0.35f; // +35%
            break;
        default:
            if (symbiosisDegree >= 7) {
                bonus = 0.5f; // +50% (封顶)
            }
            break;
    }

    return bonus;
}

// 示例: 共生度5的呼吸共生
// 基础效果: +1.5 BREATH
// 共生度加成: +35%
// 最终效果: +1.5 * 1.35 = +2.025 BREATH
```

#### 综合效果计算

```java
// 综合共生效果计算
public float calculateTotalSymbiosisEffect(
    Symbiosis symbiosis,
    Player player,
    ChestCavityData data
) {
    // 1. 计算基础效果
    float baseEffect = calculateBaseEffect(symbiosis, data);

    // 2. 应用共生链倍增
    int chainLength = getSymbiosisChainLength(symbiosis, data);
    float chainMultiplier = calculateChainMultiplier(chainLength);

    // 3. 应用共生度加成
    int symbiosisDegree = getSymbiosisDegree(player);
    float degreeBonus = calculateSymbiosisDegreeBonus(symbiosisDegree);

    // 4. 应用类型修正(基于T2分类)
    TumorType type = getSymbiosisType(symbiosis);
    float typeModifier = getTypeModifier(player, type);

    // 5. 计算最终效果
    float totalEffect = baseEffect * chainMultiplier * (1.0f + degreeBonus) * typeModifier;

    // 6. 应用上限和下限
    totalEffect = clampEffect(totalEffect, symbiosis.getMinEffect(), symbiosis.getMaxEffect());

    return totalEffect;
}

// 示例完整计算
// 基础效果: +1.5 BREATH
// 链长: 3 → 1.5x
// 共生度: 5 → +35%
// 类型修正: 恶性器官 → 1.1x
// 计算: +1.5 * 1.5 * 1.35 * 1.1 = +3.34 BREATH
```

### 效果叠加规则

#### 同类型叠加

```java
// 多个同类型共生的叠加规则
public float combineSameTypeEffects(List<Symbiosis> synergies) {
    float total = 0.0f;

    // 第一个共生: 100%效果
    total += synergies.get(0).getEffect();

    // 第二个共生: 80%效果
    if (synergies.size() > 1) {
        total += synergies.get(1).getEffect() * 0.8f;
    }

    // 第三个及以后: 50%效果
    for (int i = 2; i < synergies.size(); i++) {
        total += synergies.get(i).getEffect() * 0.5f;
    }

    return total;
}

// 示例: 3个呼吸共生
// 第一个: +1.5 BREATH
// 第二个: +1.2 BREATH (80%)
// 第三个: +0.75 BREATH (50%)
// 总计: +3.45 BREATH
```

#### 不同类型叠加

```java
// 不同类型共生的叠加规则
public float combineDifferentTypeEffects(
    List<ParasiticSymbiosis> parasitic,
    List<MutualisticSymbiosis> mutualistic,
    List<TransformativeSymbiosis> transformative
) {
    float total = 0.0f;

    // 寄生型: 100%效果
    for (ParasiticSymbiosis s : parasitic) {
        total += s.getEffect();
    }

    // 互惠型: 100%效果
    for (MutualisticSymbiosis s : mutualistic) {
        total += s.getEffect();
    }

    // 转化型: 150%效果(最强)
    for (TransformativeSymbiosis s : transformative) {
        total += s.getEffect() * 1.5f;
    }

    return total;
}
```

---

## 肿瘤间协同系统

### 协同分类

#### 类型内协同

**良性-良性协同**:
- **适应网络**: 3个良性肿瘤相邻形成"适应网络"
  - 效果: 所有良性肿瘤效果+40%,副作用-50%
  - 视觉: 绿色连接线贯穿所有良性肿瘤
  - 约束: 必须全部正交相邻

**恶性-恶性协同**:
- **侵蚀链**: 4个恶性肿瘤形成链状结构
  - 效果: 每个恶性肿瘤获得相邻恶性肿瘤20%效果,层数上限+3
  - 视觉: 红色闪电连接所有恶性肿瘤
  - 约束: 必须形成连续链(至少4个)

**特殊-特殊协同**:
- **突变共振**: 2个特殊肿瘤相邻
  - 效果: 特殊触发概率翻倍(如阑尾觉醒从5%→10%)
  - 视觉: 彩虹光环围绕两个特殊肿瘤
  - 约束: 只能同时有1个突变共振

#### 跨类型协同

**良性-恶性协同**:
- **平衡共生**: 良性肿瘤与恶性肿瘤相邻
  - 效果:
    - 良性肿瘤获得恶性肿瘤20%攻击属性
    - 恶性肿瘤获得良性肿瘤20%防御属性
    - 恶性肿瘤随机副作用概率-10%
  - 视觉: 紫色和绿色混合的光芒
  - 约束: 每个良性肿瘤最多平衡2个恶性肿瘤

**良性-特殊协同**:
- **稳定突变**: 良性肿瘤与特殊肿瘤相邻
  - 效果:
    - 特殊肿瘤的随机性降低(状态循环可部分预测)
    - 良性肿瘤效果+30%
  - 视觉: 柔和的彩虹色光环
  - 约束: 每个特殊肿瘤最多稳定1个良性肿瘤

**恶性-特殊协同**:
- **混乱共鸣**: 恶性肿瘤与特殊肿瘤相邻
  - 效果:
    - 恶性肿瘤的随机效果强度+50%
    - 特殊肿瘤的触发概率+50%
    - 但副作用也+50%
  - 视觉: 剧烈的红色和彩虹色闪光
  - 约束: 必须承受双重副作用

### 协同实例设计

#### 完整共生链设计

**"不死军团"完整链**:
```
[肿瘤心脏] --狂暴共生--> [肿瘤肌肉]
    ↓                        ↓
增殖之心               爆发力量
    ↓                        ↓
[肿瘤脾脏] --免疫共生--> [肿瘤肝脏]
    ↓                        ↓
变异免疫               腐化代谢
    ↓                        ↓
     [肿瘤阑尾] --觉醒触发--> [完美适应]
```

**效果**:
- 所有器官效果+100%
- 狂暴阈值提升至60%
- 免疫转化概率达到50%
- 每30秒自动清除1个负面效果
- 阑尾觉醒概率提升至15%
- 共生链奖励: +100%所有效果

**总加成**: 基础效果 × 2.0(链长4) × 1.5(共生度7+) = **300%基础效果**

#### 区域协同设计

**核心区域协同**:
```
胸腔中心3x3区域:
[10][11][12]
[13][14][15]
[20][21][22]

全部装备肿瘤器官
```

**效果**: "肿瘤核心"
- 中心器官(槽位14)获得所有周围器官50%效果
- 所有周围器官效果+30%
- 解锁特殊技能"肿瘤爆发"(消耗所有肿瘤器官,造成巨额伤害)
- 持续失血(每秒2点生命)

### 协同奖励系统

#### 协同成就

| 成就 | 条件 | 奖励 |
|------|------|------|
| **初次共生** | 激活1个共生效果 | 共生系统知识解锁 |
| **适应大师** | 激活3个良性共生 | 良性肿瘤效果+10% |
| **侵蚀之主** | 激活5个恶性共生 | 恶性肿瘤层数上限+3 |
| **突变先锋** | 激活2个转化共生 | 转化成功率+5% |
| **完美和谐** | 同时激活3种类型共生 | 所有肿瘤器官效果+20% |
| **军团指挥** | 共生链长度达到5 | 共生链倍率+0.5x |
| **不朽者** | 共生度达到7 | 解锁"不朽形态"特殊效果 |

#### 协同进化

当满足特定协同条件时,器官可以"进化":

**心脏进化条件**:
- 装备肿瘤心脏
- 激活狂暴共生(心脏+肌肉)
- 共生链长度≥4
- 击败至少10个BOSS

**进化效果**: "增殖王之心"
- 基础属性: +4 HEALTH, +2 STRENGTH
- 增殖之心: 从+5%/器官提升至+8%/器官
- 新效果: 每分钟增殖1个随机肿瘤器官(无消耗)
- 新效果: 狂暴期间不再失血

---

## 跨系交互设计

### 与九狱器官交互

#### 死亡拥抱 (Death Embrace)

**组合**: 肿瘤器官 + 九狱器官

**效果**:
- 血量越低,伤害越高(最高+150%)
- 低血量时,九狱器官的"罪恶"效果触发概率+30%
- 肿瘤器官的增殖速度+50%

**视觉**: 黑色和紫色混合的暗黑光环

**约束**:
- 必须装备至少3个肿瘤器官和3个九狱器官
- 持续失血(每秒1点生命)

**设计思路**: 肿瘤的"狂暴"与九狱的"罪恶"形成完美的自杀式攻击风格

#### 罪恶增殖 (Sinful Proliferation)

**组合**: 肿瘤心脏 + 九狱心脏

**效果**:
- 击败敌人有10%概率增殖1个随机肿瘤器官
- 击败敌人有5%概率增殖1个随机九狱器官
- 但每增殖1次,永久失去1点最大生命值(最多失去50%)

**视觉**: 黑色闪电连接心脏

**约束**:
- 最大生命值不低于10点
- 每分钟最多增殖2次

**设计思路**: 罪恶的力量促进肿瘤增殖,但代价是永久削弱

### 与九头蛇器官交互

#### 不死军团 (Undead Legion)

**组合**: 肿瘤器官 + 九头蛇器官

**效果**:
- 获得"多条生命": 死亡时重生(最多3次)
- 每次重生后:
  - 失去1个随机肿瘤器官
  - 恢复50%生命值
  - 所有肿瘤器官效果暂时+100%(持续1分钟)

**视觉**: 绿色和紫色混合的再生光环

**约束**:
- 重生次数共享(与九头蛇器官的重生不叠加)
- 每次重生后,肿瘤器官数量减少

**设计思路**: 肿瘤的"增殖"与九头蛇的"再生"形成永动机

#### 再生增殖 (Regenerative Proliferation)

**组合**: 肿瘤肠子 + 九头蛇头颅

**效果**:
- 肠子的"再生状态"效果翻倍(生命恢复+400%)
- 再生状态下,每30秒有20%概率增殖1个肿瘤器官
- 但每次增殖,肠子状态切换加速(从30秒→20秒)

**视觉**: 绿色粒子大量散发

**约束**:
- 增殖的肿瘤器官必定是良性
- 最多同时增殖3个器官

**设计思路**: 九头蛇的再生能力加速肿瘤增殖,但代价是稳定性下降

### 与弗兰肯斯坦器官交互

#### 科学怪人2.0 (Frankenstein Tumor)

**组合**: 肿瘤器官 + 弗兰肯斯坦器官

**效果**:
- 所有器官效果(包括肿瘤和非肿瘤)效果+50%
- 每10秒有15%概率发生"排斥反应":
  - 失去所有器官效果5秒
  - 期间受到的伤害+50%
  - 5秒后效果恢复,并获得"免疫期"(10秒内不再排斥)

**视觉**: 电弧在所有器官间闪烁

**约束**:
- 必须装备至少2个肿瘤器官和2个弗兰肯斯坦器官
- 排斥反应期间无法使用胸腔GUI

**设计思路**: 肿瘤的"变异"与弗兰肯斯坦的"拼凑"形成疯狂科学家的完美实验

#### 适应性缝合 (Adaptive Stitching)

**组合**: 肿瘤脾脏 + 弗兰肯斯坦缝合线

**效果**:
- 脾脏的"变异免疫"效果应用到所有器官
- 所有器官(包括非肿瘤)有25%概率转化负面效果为正面效果
- 但缝合线的副作用持续时间+50%

**视觉**: 白色和紫色混合的免疫网络

**约束**:
- 只能同时免疫3种负面效果
- 免疫效果在缝合线失效时也失效

**设计思路**: 脾脏的免疫能力通过缝合线扩展到全身

### 与幻想种器官交互

#### 现实扭曲 (Reality Distortion)

**组合**: 肿瘤器官 + 幻想种器官

**效果**:
- 30%概率将任何伤害转化为治疗(等量)
- 30%概率将任何治疗转化为伤害(等量)
- 40%概率正常工作
- 判定每秒随机变化

**视觉**: 现实扭曲的粒子效果,颜色随机变化

**约束**:
- 无法预知下一次判定结果
- 必须装备至少2个肿瘤器官和2个幻想种器官

**设计思路**: 肿瘤的"变异"与幻想种的"扭曲"形成完全的随机性

#### 幻想增殖 (Fantasy Proliferation)

**组合**: 肿瘤阑尾 + 幻想种核心

**效果**:
- 阑尾觉醒概率从5%提升至25%
- 觉醒效果必定是"完美适应"或"细胞融合"
- 但觉醒失败时(75%概率),阑尾直接销毁(不会发炎)

**视觉**: 彩虹光束从幻想种核心射向阑尾

**约束**:
- 每次胸腔关闭只能尝试1次
- 阑尾销毁后无法恢复

**设计思路**: 幻想种的力量提升觉醒概率,但代价是失败时永久失去

### 与墨水/颜料器官交互

#### 艺术变异 (Artistic Mutation)

**组合**: 肿瘤器官 + 墨水/颜料器官

**效果**:
- 肿瘤器官的颜色根据墨水/颜料器官的颜色变化
- 不同颜色提供不同效果:
  - 红色颜料: +30%伤害, -20%生命恢复
  - 蓝色颜料: +30%移动速度, -20%伤害
  - 绿色颜料: +30%生命恢复, -20%移动速度
  - 黄色颜料: +30%经验获取, -20%防御

**视觉**: 肿瘤器官动态变色

**约束**:
- 只能同时有1种颜色效果
- 颜色变化每分钟1次

**设计思路**: 墨水/颜料的艺术性与肿瘤的变异结合,创造多彩的战术选择

#### 毒素画作 (Toxic Masterpiece)

**组合**: 肿瘤肺脏 + 墨水肺脏

**效果**:
- 肺脏的毒素云获得颜色和额外效果:
  - 红色毒素云: 额外造成燃烧伤害
  - 蓝色毒素云: 额外造成冰冻效果
  - 绿色毒素云: 额外造成中毒效果
  - 黄色毒素云: 额外造成饥饿效果
- 毒素云范围+50%

**视觉**: 彩色的毒素云

**约束**:
- 毒素云颜色随机,每10秒变化1次
- 只有在毒素云内的敌人才受影响

**设计思路**: 墨水的艺术性与肺脏的毒性结合,创造美丽的致命陷阱

---

## 共生视觉反馈

### 视觉效果分级

| 共生类型 | 粒子效果 | 光效 | 颜色 | 动画 |
|---------|---------|------|------|------|
| **寄生型** | 中等 | 低 | 暗色 | 缓慢吸收 |
| **互惠型** | 强烈 | 中等 | 亮色 | 循环流动 |
| **转化型** | 极强 | 高 | 彩色 | 剧烈变化 |

### 寄生型视觉

**心脏寄生视觉**:
- **粒子**: 黑色粒子从相邻心脏流向肿瘤心脏
- **光效**: 相邻心脏逐渐变暗,肿瘤心脏脉动增强
- **颜色**: 黑色和深紫色
- **动画**:
  - 每2秒,一波黑色粒子从相邻心脏移动到肿瘤心脏
  - 相邻心脏的纹理逐渐被肿瘤纹理覆盖
  - 肿瘤心脏体积增大10%

**肌肉寄生视觉**:
- **粒子**: 红色粒子从相邻肌肉流向肿瘤肌肉
- **光效**: 相邻肌肉萎缩,肿瘤肌肉膨胀
- **颜色**: 鲜红色和黑色
- **动画**:
  - 持续的红色粒子流动
  - 相邻肌肉纹理消失
  - 肿瘤肌肉出现脉动的血管

### 互惠型视觉

**呼吸共生视觉**:
- **粒子**: 绿色和青色粒子在两个肺脏间循环
- **光效**: 两个肺脏发出柔和的绿色光芒
- **颜色**: 绿色和青色
- **动画**:
  - 绿色粒子顺时针流动
  - 青色粒子逆时针流动
  - 两个肺脏同步脉动
  - 周围出现气泡效果(水下呼吸提示)

**狂暴共生视觉**:
- **粒子**: 红色闪电在心脏和肌肉间跳跃
- **光效**: 两个器官发出强烈的红光
- **颜色**: 鲜红色和橙色
- **动画**:
  - 闪电每0.5秒跳跃一次
  - 两个器官剧烈脉动
  - 玩家身上出现红色光环
  - 低血量时,闪电变为黑色

### 转化型视觉

**完全转化视觉**:
- **粒子**: 彩虹色爆发,然后被黑色雾气吞噬
- **光效**: 强烈的彩虹光束从阑尾射向相邻器官
- **颜色**: 彩虹色 → 黑色 → 暗紫色
- **动画**:
  - 阶段1(0.5秒): 阑尾发出彩虹光束
  - 阶段2(1.0秒): 相邻器官被彩虹光包围
  - 阶段3(0.5秒): 相邻器官剧烈扭曲
  - 阶段4(1.0秒): 相邻器官颜色变暗,形成肿瘤器官
  - 转化完成后,产生冲击波效果

**恶性转化视觉**:
- **粒子**: 黑色雾气从恶性肿瘤扩散到良性器官
- **光效**: 黑暗的腐蚀光环
- **颜色**: 黑色和病态绿色
- **动画**:
  - 黑色雾气逐渐吞噬良性器官
  - 良性器官颜色逐渐变暗
  - 器官表面出现裂痕和肿瘤纹理
  - 转化完成后,黑色雾气消散

### 共生链视觉

**3链呼吸共生视觉**:
- **粒子**: 绿色粒子链连接所有参与的器官
- **光效**: 每个器官发出绿色光芒,连接线发出光晕
- **颜色**: 渐变的绿色(从浅绿到深绿)
- **动画**:
  - 粒子沿连接线流动
  - 第一个器官触发,效果依次传递到后续器官
  - 每个器官激活时,产生小的闪光
  - 最后一个器官激活时,产生冲击波

**5链完全共生视觉**:
- **粒子**: 多彩粒子爆发,形成复杂的网络
- **光效**: 所有器官强烈发光,连接线形成光网
- **颜色**: 彩虹色渐变
- **动画**:
  - 粒子沿所有连接线快速流动
  - 器官依次激活,产生连锁反应
  - 最后一个器官激活时,产生彩虹色冲击波
  - 玩家身上出现完整的彩虹光环
  - 胸腔GUI背景变为动态的星云效果

### UI反馈

#### 共生状态指示器

在胸腔GUI中添加共生状态指示器:

```
┌─────────────────────────────────┐
│  胸腔 (27/27)                   │
├─────────────────────────────────┤
│ 共生状态:                       │
│ • 共生度: ████████░░ 7/10       │
│ • 激活共生: 4个                 │
│   - 呼吸共生 (互惠型)           │
│   - 狂暴共生 (互惠型)           │
│   - 心脏寄生 (寄生型)           │
│   - 完全转化 (转化型)           │
│ • 共生链: 3链 (×1.5倍率)       │
│ • 下次转化: 23:45:12           │
└─────────────────────────────────┘
```

#### 共生预览

当鼠标悬停在器官上时,显示可能的共生:

```
┌─────────────────────────────────┐
│ 肿瘤心脏                         │
│ • 基础: +4 HEALTH, +2 STRENGTH  │
│ • 增殖之心: +40%最大生命值       │
│                                 │
│ 可用共生:                       │
│ • 狂暴共生 (与肿瘤肌肉)         │
│   效果: 伤害+100%,速度+50%      │
│ • 心脏寄生 (与任何心脏)         │
│   效果: 吸收50%属性             │
│                                 │
│ 当前激活:                       │
│ ✓ 狂暴共生 (13,14槽位)         │
└─────────────────────────────────┘
```

---

## 平衡性设计

### 强度分级

| 共生类型 | 效果强度 | 风险等级 | 获取难度 | 战略价值 |
|---------|---------|---------|---------|---------|
| **寄生型** | ★★★☆☆ | ★★★☆☆ | ★★☆☆☆ | ★★★☆☆ |
| **互惠型** | ★★★★☆ | ★★☆☆☆ | ★★★☆☆ | ★★★★★ |
| **转化型** | ★★★★★ | ★★★★★ | ★★★★★ | ★★★★☆ |

### 平衡机制

#### 寄生型平衡

**优势**:
- ✅ 单方面强化,灵活选择目标
- ✅ 可以叠加多个寄生效果
- ✅ 适合极致属性追求

**劣势**:
- ❌ 削弱相邻器官
- ❌ 可能破坏其他搭配
- ❌ 长期使用可能导致器官失效

**平衡措施**:
- 寄生效果上限50%相邻器官属性
- 削减效果上限30%相邻器官属性
- 每个肿瘤器官最多同时寄生3个器官
- 寄生效果可以随时取消(但需要10秒冷却)

#### 互惠型平衡

**优势**:
- ✅ 双向强化,共同提升
- ✅ 创造全新的协同效果
- ✅ 副作用相对温和

**劣势**:
- ❌ 需要特定的器官组合
- ❌ 占用多个槽位
- ❌ 效果不如转化型极致

**平衡措施**:
- 互惠增益在30-50%之间
- 需要器官正交相邻(限制灵活性)
- 共生效果可以随时激活/取消
- 某些互惠共生需要满足特定条件

#### 转化型平衡

**优势**:
- ✅ 极强的效果,改变器官构成
- ✅ 创造独特的游戏体验
- ✅ 符合肿瘤"侵蚀"主题

**劣势**:
- ❌ 不可逆,永久改变
- ❌ 高风险,可能导致Build崩溃
- ❌ 触发条件苛刻

**平衡措施**:
- 转化前显示预览,让玩家确认
- 转化效果足够强,值得冒险
- 某些转化可以撤销(但代价高昂)
- 转化成功率可以提升(通过特殊物品)

### 共生度平衡

| 共生度 | 奖励 | 代价 |
|-------|------|------|
| 0 | 无 | 无 |
| 1-2 | +10%效果 | 轻微副作用 |
| 3-4 | +20%效果 | 中度副作用 |
| 5-6 | +35%效果 | 重度副作用 |
| 7+ | +50%效果 | 极度副作用+特殊奖励 |

**副作用示例**:
- **1-2级**: 每10秒失去1点生命
- **3-4级**: 每5秒失去1点生命,随机负面效果
- **5-6级**: 每3秒失去1点生命,负面效果持续时间+50%
- **7+级**: 每秒失去2点生命,但解锁"不朽形态"(死亡后重生1次)

### 共生链平衡

| 链长 | 倍率 | 限制 |
|------|------|------|
| 2 | 1.25x | 无 |
| 3 | 1.5x | 需要连续共生 |
| 4 | 2.0x | 需要连续共生,副作用+20% |
| 5+ | 2.5x | 需要连续共生,副作用+50%,每10秒失去1点生命 |

### 跨系交互平衡

**平衡原则**:
- 跨系交互效果应该强大,但不应完全替代单系玩法
- 跨系交互应该有明确的代价
- 跨系交互应该鼓励玩家尝试新的器官组合

**具体平衡**:
- **九狱交互**: 高伤害但持续失血
- **九头蛇交互**: 重生能力但失去器官
- **弗兰肯斯坦交互**: 强力效果但有排斥风险
- **幻想种交互**: 极强效果但完全随机
- **墨水/颜料交互**: 多样化效果但需要管理颜色

---

## 技术实现方案

### 数据结构设计

```java
// 共生类型枚举
public enum SymbiosisType {
    PARASITIC("寄生型", "parasitic", ChatFormatting.DARK_GRAY),
    MUTUALISTIC("互惠型", "mutualistic", ChatFormatting.GREEN),
    TRANSFORMATIVE("转化型", "transformative", ChatFormatting.LIGHT_PURPLE);

    private final String displayName;
    private final String englishName;
    private final ChatFormatting color;

    // getters...
}

// 共生配置类
public class SymbiosisConfig {
    private String id;
    private SymbiosisType type;
    private String name;
    private String description;
    private int priority; // 0-4, 0最高优先级

    // 触发条件
    private SymbiosisCondition condition;

    // 效果配置
    private SymbiosisEffect effect;

    // 约束配置
    private SymbiosisConstraints constraints;

    // 视觉配置
    private SymbiosisVisual visual;

    // getters and setters...
}

// 共生条件
public class SymbiosisCondition {
    private List<OrganRequirement> organRequirements; // 器官要求
    private AdjacencyType adjacencyType; // 相邻类型
    private int minTumorCount; // 最小肿瘤器官数量
    private float minHealthPercent; // 最小生命值百分比
    private boolean requiresBloodMoon; // 是否需要血月
    private boolean requiresLowHealth; // 是否需要低血量

    // getters and setters...
}

// 共生效果
public class SymbiosisEffect {
    private Map<Attribute, Float> attributeModifiers; // 属性修正
    private List<String> skillUnlocks; // 技能解锁
    private List<PassiveEffect> passiveEffects; // 被动效果
    private List<TransformEffect> transformEffects; // 转化效果

    // getters and setters...
}

// 共生约束
public class SymbiosisConstraints {
    private int maxSimultaneous; // 最大同时激活数量
    private List<String> mutuallyExclusive; // 互斥的共生ID
    private boolean isReversible; // 是否可逆
    private int cooldownSeconds; // 冷却时间(秒)
    private float successChance; // 成功概率(0-1)

    // getters and setters...
}

// 扩展TumorData
public class TumorData {
    // 原有字段...

    // 新增: 共生相关
    private List<ActiveSymbiosis> activeSynergies = new ArrayList<>();
    private int symbiosisDegree = 0;
    private List<SymbiosisChain> chains = new ArrayList<>();
    private Map<String, Long> cooldowns = new HashMap<>(); // 共生冷却

    // 共生链
    public static class SymbiosisChain {
        private List<String> symbiosisIds;
        private float multiplier;
        private int length;

        // getters and setters...
    }

    // 激活的共生
    public static class ActiveSymbiosis {
        private String id;
        private SymbiosisConfig config;
        private long startTime;
        private long duration; // 0表示永久
        private List<Integer> involvedSlots;

        // getters and setters...
    }
}
```

### 共生检测流程

```java
// 共生检测主流程
public class SymbiosisDetector {

    // 在胸腔打开/关闭时检测共生
    public void detectAndActivateSynergies(Player player, ChestCavityData data) {
        TumorData tumorData = getOrCreateTumorData(player);

        // 1. 清除过期的共生
        clearExpiredSynergies(tumorData);

        // 2. 检测新的共生
        List<SymbiosisConfig> potentialSynergies = findPotentialSynergies(data);
        for (SymbiosisConfig config : potentialSynergies) {
            if (canActivateSymbiosis(player, config)) {
                activateSymbiosis(player, config, data);
            }
        }

        // 3. 计算共生度
        updateSymbiosisDegree(tumorData);

        // 4. 检测共生链
        detectSymbiosisChains(tumorData);

        // 5. 应用共生效果
        applySymbiosisEffects(player, tumorData);

        // 6. 发送反馈
        sendSymbiosisFeedback(player, tumorData);
    }

    // 查找潜在的共生
    private List<SymbiosisConfig> findPotentialSynergies(ChestCavityData data) {
        List<SymbiosisConfig> potentials = new ArrayList<>();

        // 遍历所有共生配置
        for (SymbiosisConfig config : getAllSymbiosisConfigs()) {
            if (checkBasicConditions(data, config)) {
                potentials.add(config);
            }
        }

        return potentials;
    }

    // 检查基础条件
    private boolean checkBasicConditions(ChestCavityData data, SymbiosisConfig config) {
        SymbiosisCondition condition = config.getCondition();

        // 检查器官要求
        if (!checkOrganRequirements(data, condition.getOrganRequirements())) {
            return false;
        }

        // 检查相邻要求
        if (!checkAdjacencyRequirements(data, condition.getAdjacencyType())) {
            return false;
        }

        // 检查肿瘤器官数量
        int tumorCount = data.getOrganCount(TUMOR);
        if (tumorCount < condition.getMinTumorCount()) {
            return false;
        }

        return true;
    }

    // 检查器官要求
    private boolean checkOrganRequirements(
        ChestCavityData data,
        List<OrganRequirement> requirements
    ) {
        for (OrganRequirement req : requirements) {
            Item requiredOrgan = req.getOrgan();
            int requiredCount = req.getCount();
            int actualCount = data.getOrganCount(requiredOrgan);

            if (actualCount < requiredCount) {
                return false;
            }
        }
        return true;
    }

    // 检查相邻要求
    private boolean checkAdjacencyRequirements(
        ChestCavityData data,
        AdjacencyType adjacencyType
    ) {
        switch (adjacencyType) {
            case ORTHOGONAL:
                return checkOrthogonalAdjacency(data);
            case DIAGONAL:
                return checkDiagonalAdjacency(data);
            case ALL:
                return checkAllAdjacency(data);
            case REGIONAL:
                return checkRegionalAdjacency(data);
            default:
                return false;
        }
    }

    // 检查正交相邻
    private boolean checkOrthogonalAdjacency(ChestCavityData data) {
        // 实现略...
        // 使用getAdjacentSlots获取相邻槽位
        // 检查是否有符合的器官正交相邻
        return true;
    }

    // 检查是否可以激活共生
    private boolean canActivateSymbiosis(Player player, SymbiosisConfig config) {
        TumorData tumorData = getTumorData(player);
        if (tumorData == null) return false;

        SymbiosisConstraints constraints = config.getConstraints();

        // 检查冷却
        String symbiosisId = config.getId();
        if (tumorData.getCooldowns().containsKey(symbiosisId)) {
            long cooldownEnd = tumorData.getCooldowns().get(symbiosisId);
            if (System.currentTimeMillis() < cooldownEnd) {
                return false;
            }
        }

        // 检查同时激活数量限制
        long activeCount = tumorData.getActiveSynergies().stream()
            .filter(s -> s.getId().equals(symbiosisId))
            .count();
        if (activeCount >= constraints.getMaxSimultaneous()) {
            return false;
        }

        // 检查互斥
        for (String mutuallyExclusiveId : constraints.getMutuallyExclusive()) {
            boolean hasMutuallyExclusive = tumorData.getActiveSynergies().stream()
                .anyMatch(s -> s.getId().equals(mutuallyExclusiveId));
            if (hasMutuallyExclusive) {
                return false;
            }
        }

        // 检查成功概率(转化型)
        if (config.getType() == SymbiosisType.TRANSFORMATIVE) {
            float random = player.getRandom().nextFloat();
            if (random > constraints.getSuccessChance()) {
                return false;
            }
        }

        return true;
    }

    // 激活共生
    private void activateSymbiosis(
        Player player,
        SymbiosisConfig config,
        ChestCavityData data
    ) {
        TumorData tumorData = getTumorData(player);
        if (tumorData == null) return;

        // 创建激活的共生
        ActiveSymbiosis active = new ActiveSymbiosis();
        active.setId(config.getId());
        active.setConfig(config);
        active.setStartTime(System.currentTimeMillis());
        active.setDuration(config.getEffect().getDuration());
        active.setInvolvedSlots(findInvolvedSlots(data, config));

        // 添加到激活列表
        tumorData.getActiveSynergies().add(active);

        // 设置冷却
        int cooldownSeconds = config.getConstraints().getCooldownSeconds();
        if (cooldownSeconds > 0) {
            long cooldownEnd = System.currentTimeMillis() + (cooldownSeconds * 1000L);
            tumorData.getCooldowns().put(config.getId(), cooldownEnd);
        }

        // 发送激活消息
        player.sendSystemMessage(
            Component.literal("共生激活: " + config.getName())
                .withStyle(ChatFormatting.GREEN)
        );

        // 触发视觉效果
        spawnSymbiosisParticles(player, config);
        playSymbiosisSound(player, config);
    }

    // 更新共生度
    private void updateSymbiosisDegree(TumorData tumorData) {
        int degree = tumorData.getActiveSynergies().size();

        // 某些特殊共生额外贡献1点
        for (ActiveSymbiosis active : tumorData.getActiveSynergies()) {
            if (active.getConfig().isSpecial()) {
                degree += 1;
            }
        }

        tumorData.setSymbiosisDegree(degree);
    }

    // 检测共生链
    private void detectSymbiosisChains(TumorData tumorData) {
        // 清除旧的链
        tumorData.getChains().clear();

        // 构建共生图
        Map<String, List<String>> graph = buildSymbiosisGraph(tumorData);

        // 查找所有链
        List<List<String>> chains = findAllChains(graph);

        // 创建链对象
        for (List<String> chain : chains) {
            if (chain.size() >= 2) {
                SymbiosisChain symbiosisChain = new SymbiosisChain();
                symbiosisChain.setSymbiosisIds(chain);
                symbiosisChain.setLength(chain.size());
                symbiosisChain.setMultiplier(calculateChainMultiplier(chain.size()));

                tumorData.getChains().add(symbiosisChain);
            }
        }
    }

    // 应用共生效果
    private void applySymbiosisEffects(Player player, TumorData tumorData) {
        // 清除所有旧的共生修饰符
        clearAllSymbiosisModifiers(player);

        // 应用每个激活的共生效果
        for (ActiveSymbiosis active : tumorData.getActiveSynergies()) {
            applySymbiosisEffect(player, active);
        }

        // 应用共生链倍增
        for (SymbiosisChain chain : tumorData.getChains()) {
            applyChainMultiplier(player, chain);
        }

        // 应用共生度加成
        int degree = tumorData.getSymbiosisDegree();
        if (degree > 0) {
            float degreeBonus = calculateSymbiosisDegreeBonus(degree);
            applyDegreeBonus(player, degreeBonus);
        }
    }

    // 清除过期的共生
    private void clearExpiredSynergies(TumorData tumorData) {
        long currentTime = System.currentTimeMillis();

        tumorData.getActiveSynergies().removeIf(active -> {
            if (active.getDuration() > 0) {
                long endTime = active.getStartTime() + active.getDuration();
                return currentTime >= endTime;
            }
            return false;
        });
    }
}
```

### 共生效果应用

```java
// 共生效果应用器
public class SymbiosisEffectApplier {

    // 应用单个共生效果
    public void applySymbiosisEffect(Player player, ActiveSymbiosis active) {
        SymbiosisConfig config = active.getConfig();
        SymbiosisEffect effect = config.getEffect();

        switch (config.getType()) {
            case PARASITIC:
                applyParasiticEffect(player, config, effect);
                break;
            case MUTUALISTIC:
                applyMutualisticEffect(player, config, effect);
                break;
            case TRANSFORMATIVE:
                applyTransformativeEffect(player, config, effect);
                break;
        }
    }

    // 应用寄生型效果
    private void applyParasiticEffect(
        Player player,
        SymbiosisConfig config,
        SymbiosisEffect effect
    ) {
        // 获取主器官和目标器官
        ItemStack mainOrgan = getMainOrgan(player, config);
        ItemStack targetOrgan = getTargetOrgan(player, config);

        if (mainOrgan == null || targetOrgan == null) return;

        // 吸收属性
        Map<Attribute, Float> targetAttributes = getOrganAttributes(targetOrgan);
        Map<Attribute, Float> absorptionModifiers = effect.getAttributeModifiers();

        for (Map.Entry<Attribute, Float> entry : absorptionModifiers.entrySet()) {
            Attribute attribute = entry.getKey();
            float absorptionRate = entry.getValue();

            if (targetAttributes.containsKey(attribute)) {
                float targetValue = targetAttributes.get(attribute);
                float absorbedAmount = targetValue * absorptionRate;

                // 应用吸收
                addAttributeModifier(
                    player,
                    mainOrgan,
                    attribute,
                    "parasitic_absorption",
                    absorbedAmount,
                    AttributeModifier.Operation.ADDITION
                );

                // 削弱目标器官
                float penaltyRate = absorptionRate * 0.6f; // 削减率为吸收率的60%
                float penaltyAmount = targetValue * penaltyRate;

                addAttributeModifier(
                    player,
                    targetOrgan,
                    attribute,
                    "parasitic_penalty",
                    -penaltyAmount,
                    AttributeModifier.Operation.ADDITION
                );
            }
        }

        // 应用被动效果
        for (PassiveEffect passive : effect.getPassiveEffects()) {
            passive.apply(player, mainOrgan);
        }
    }

    // 应用互惠型效果
    private void applyMutualisticEffect(
        Player player,
        SymbiosisConfig config,
        SymbiosisEffect effect
    ) {
        // 获取参与的器官
        List<ItemStack> organs = getInvolvedOrgans(player, config);
        if (organs.size() < 2) return;

        // 应用互惠增强
        Map<Attribute, Float> boostModifiers = effect.getAttributeModifiers();

        for (ItemStack organ : organs) {
            for (Map.Entry<Attribute, Float> entry : boostModifiers.entrySet()) {
                Attribute attribute = entry.getKey();
                float boostRate = entry.getValue();

                // 计算基础增强
                float baseAttribute = getOrganAttribute(organ, attribute);
                float boostAmount = baseAttribute * boostRate;

                // 计算协同加成
                float synergyBonus = boostAmount * 0.2f; // 20%协同加成

                // 应用增强
                addAttributeModifier(
                    player,
                    organ,
                    attribute,
                    "mutualistic_boost",
                    boostAmount + synergyBonus,
                    AttributeModifier.Operation.ADDITION
                );
            }
        }

        // 应用共生特殊效果
        for (PassiveEffect passive : effect.getPassiveEffects()) {
            passive.apply(player, organs);
        }

        // 解锁技能
        for (String skillId : effect.getSkillUnlocks()) {
            unlockSkill(player, skillId);
        }
    }

    // 应用转化型效果
    private void applyTransformativeEffect(
        Player player,
        SymbiosisConfig config,
        SymbiosisEffect effect
    ) {
        // 获取目标器官
        ItemStack targetOrgan = getTargetOrgan(player, config);
        if (targetOrgan == null) return;

        // 获取转化目标类型
        TumorType targetType = determineTransformType(effect);

        // 显示转化预览
        showTransformationPreview(player, targetOrgan, targetType);

        // 执行转化(不可逆)
        ItemStack newTumor = transformOrgan(targetOrgan, targetType, effect);

        // 替换器官
        replaceOrgan(player, targetOrgan, newTumor);

        // 发送转化消息
        player.sendSystemMessage(
            Component.literal("器官转化: " + targetOrgan.getHoverName().getString() + " → " + newTumor.getHoverName().getString())
                .withStyle(ChatFormatting.LIGHT_PURPLE)
        );

        // 触发转化视觉特效
        spawnTransformationParticles(player, targetOrgan, newTumor);
        playTransformationSound(player);
    }

    // 转化器官
    private ItemStack transformOrgan(
        ItemStack targetOrgan,
        TumorType targetType,
        SymbiosisEffect effect
    ) {
        // 获取原器官属性
        Map<Attribute, Float> originalAttributes = getOrganAttributes(targetOrgan);

        // 创建新肿瘤器官
        ItemStack newTumor = createTumorOrgan(targetType);

        // 保留部分原属性
        float retentionRate = effect.getRetentionRate();
        Map<Attribute, Float> retainedAttributes = new HashMap<>();
        for (Map.Entry<Attribute, Float> entry : originalAttributes.entrySet()) {
            float retainedValue = entry.getValue() * retentionRate;
            retainedAttributes.put(entry.getKey(), retainedValue);
        }

        // 添加肿瘤器官基础属性
        Map<Attribute, Float> tumorAttributes = getOrganAttributes(newTumor);
        for (Map.Entry<Attribute, Float> entry : tumorAttributes.entrySet()) {
            retainedAttributes.merge(entry.getKey(), entry.getValue(), Float::sum);
        }

        // 应用属性到新器官
        setOrganAttributes(newTumor, retainedAttributes);

        return newTumor;
    }
}
```

---

## JSON配置示例

### 共生配置文件结构

```json
{
  "symbiosis_configs": {
    "parasitic": [
      {
        "id": "heart_parasitism",
        "type": "parasitic",
        "name": "心脏寄生",
        "description": "肿瘤心脏吸收相邻心脏的属性",
        "priority": 4,
        "condition": {
          "organ_requirements": [
            {"organ_id": "tumor_heart", "count": 1},
            {"organ_id": "any_heart", "count": 1}
          ],
          "adjacency_type": "orthogonal",
          "min_tumor_count": 1
        },
        "effect": {
          "attribute_modifiers": {
            "HEALTH": 0.5,
            "STRENGTH": 0.5
          },
          "passive_effects": [
            {
              "type": "attribute_penalty",
              "target": "adjacent",
              "attributes": {
                "HEALTH": -0.3,
                "STRENGTH": -0.3
              }
            }
          ]
        },
        "constraints": {
          "max_simultaneous": 1,
          "mutually_exclusive": [],
          "is_reversible": true,
          "cooldown_seconds": 10,
          "success_chance": 1.0
        },
        "visual": {
          "particle_type": "black_absorption",
          "particle_color": "#8B0000",
          "light_level": 0.3,
          "animation_speed": 2.0
        }
      }
    ],
    "mutualistic": [
      {
        "id": "respiratory_mutualism",
        "type": "mutualistic",
        "name": "呼吸共生",
        "description": "肿瘤肺脏与肺脏器官互相增强",
        "priority": 3,
        "condition": {
          "organ_requirements": [
            {"organ_id": "tumor_lung", "count": 1},
            {"organ_id": "any_lung", "count": 1}
          ],
          "adjacency_type": "orthogonal",
          "min_tumor_count": 1,
          "requires_underwater": false
        },
        "effect": {
          "attribute_modifiers": {
            "BREATH": 0.3,
            "SPEED": 0.2
          },
          "passive_effects": [
            {
              "type": "toxic_cloud_boost",
              "radius_multiplier": 1.5,
              "damage_multiplier": 1.3
            },
            {
              "type": "underwater_breathing",
              "duration_seconds": 300
            }
          ],
          "skill_unlocks": ["toxic_breath_advanced"]
        },
        "constraints": {
          "max_simultaneous": 1,
          "mutually_exclusive": [],
          "is_reversible": true,
          "cooldown_seconds": 0,
          "success_chance": 1.0
        },
        "visual": {
          "particle_type": "green_circulation",
          "particle_color": "#00FF00",
          "light_level": 0.7,
          "animation_speed": 1.0
        }
      },
      {
        "id": "frenzy_mutualism",
        "type": "mutualistic",
        "name": "狂暴共生",
        "description": "肿瘤心脏与肿瘤肌肉的狂暴协同",
        "priority": 2,
        "condition": {
          "organ_requirements": [
            {"organ_id": "tumor_heart", "count": 1},
            {"organ_id": "tumor_muscle", "count": 1}
          ],
          "adjacency_type": "orthogonal",
          "min_tumor_count": 2,
          "requires_low_health": true,
          "min_health_percent": 50
        },
        "effect": {
          "attribute_modifiers": {
            "DAMAGE": 1.0,
            "SPEED": 0.5
          },
          "passive_effects": [
            {
              "type": "frenzy_threshold_boost",
              "new_threshold": 0.5
            },
            {
              "type": "spasm_reduction",
              "reduction_amount": 0.1
            }
          ],
          "skill_unlocks": ["frenzy_master"]
        },
        "constraints": {
          "max_simultaneous": 1,
          "mutually_exclusive": [],
          "is_reversible": true,
          "cooldown_seconds": 0,
          "success_chance": 1.0
        },
        "visual": {
          "particle_type": "red_lightning",
          "particle_color": "#FF0000",
          "light_level": 1.0,
          "animation_speed": 0.5
        }
      }
    ],
    "transformative": [
      {
        "id": "complete_transformation",
        "type": "transformative",
        "name": "完全转化",
        "description": "阑尾觉醒时转化相邻器官",
        "priority": 0,
        "condition": {
          "organ_requirements": [
            {"organ_id": "tumor_appendix", "count": 1},
            {"organ_id": "any", "count": 1}
          ],
          "adjacency_type": "orthogonal",
          "min_tumor_count": 1,
          "trigger_event": "appendix_awakening"
        },
        "effect": {
          "transform_effects": [
            {
              "type": "organ_to_tumor",
              "target_type": "random",
              "retention_rate": 0.5
            }
          ]
        },
        "constraints": {
          "max_simultaneous": 1,
          "mutually_exclusive": [],
          "is_reversible": false,
          "cooldown_seconds": 0,
          "success_chance": 0.05
        },
        "visual": {
          "particle_type": "rainbow_burst",
          "particle_color": "#FF00FF",
          "light_level": 1.5,
          "animation_speed": 3.0
        }
      }
    ]
  }
}
```

### 跨系交互配置

```json
{
  "cross_system_synergies": {
    "nether_tumor": {
      "death_embrace": {
        "id": "death_embrace",
        "name": "死亡拥抱",
        "description": "肿瘤与九狱的黑暗协同",
        "required_organs": {
          "tumor": 3,
          "nether": 3
        },
        "effect": {
          "low_health_damage_bonus": 1.5,
          "sin_trigger_bonus": 0.3,
          "proliferation_speed_bonus": 0.5,
          "continuous_bleed": 1.0
        },
        "visual": {
          "particle_type": "dark_purple_aura",
          "particle_color": "#4B0082"
        }
      }
    },
    "hydra_tumor": {
      "undead_legion": {
        "id": "undead_legion",
        "name": "不死军团",
        "description": "肿瘤与九头蛇的再生协同",
        "required_organs": {
          "tumor": 2,
          "hydra": 2
        },
        "effect": {
          "extra_lives": 3,
          "respawn_health_percent": 50,
          "buff_duration": 1200,
          "buff_multiplier": 1.0,
          "lost_organs_per_respawn": 1
        },
        "visual": {
          "particle_type": "green_regeneration",
          "particle_color": "#00FF00"
        }
      }
    },
    "frankenstein_tumor": {
      "frankenstein_2_0": {
        "id": "frankenstein_2_0",
        "name": "科学怪人2.0",
        "description": "肿瘤与弗兰肯斯坦的疯狂协同",
        "required_organs": {
          "tumor": 2,
          "frankenstein": 2
        },
        "effect": {
          "all_organ_bonus": 0.5,
          "rejection_chance": 0.15,
          "rejection_duration": 100,
          "rejection_damage_bonus": 0.5,
          "immunity_duration": 200
        },
        "visual": {
          "particle_type": "electric_arcs",
          "particle_color": "#FFFFFF"
        }
      }
    },
    "fantasy_tumor": {
      "reality_distortion": {
        "id": "reality_distortion",
        "name": "现实扭曲",
        "description": "肿瘤与幻想种的扭曲协同",
        "required_organs": {
          "tumor": 2,
          "fantasy": 2
        },
        "effect": {
          "damage_to_heal_chance": 0.3,
          "heal_to_damage_chance": 0.3,
          "normal_chance": 0.4,
          "change_interval": 20
        },
        "visual": {
          "particle_type": "reality_warp",
          "particle_color": "#FF00FF"
        }
      }
    }
  }
}
```

### 肿瘤间协同配置

```json
{
  "tumor_internal_synergies": {
    "benign_network": {
      "id": "benign_network",
      "name": "适应网络",
      "description": "3个良性肿瘤形成适应网络",
      "condition": {
        "organ_requirements": [
          {"organ_type": "benign", "count": 3}
        ],
        "adjacency_type": "orthogonal",
        "must_be_adjacent": true
      },
      "effect": {
        "benign_bonus": 0.4,
        "side_effect_reduction": 0.5
      },
      "visual": {
        "particle_type": "green_network",
        "particle_color": "#00FF00"
      }
    },
    "malignant_chain": {
      "id": "malignant_chain",
      "name": "侵蚀链",
      "description": "4个恶性肿瘤形成链状结构",
      "condition": {
        "organ_requirements": [
          {"organ_type": "malignant", "count": 4}
        ],
        "adjacency_type": "chain",
        "min_chain_length": 4
      },
      "effect": {
        "adjacent_bonus": 0.2,
        "stack_limit_bonus": 3
      },
      "visual": {
        "particle_type": "red_chain_lightning",
        "particle_color": "#FF0000"
      }
    },
    "special_resonance": {
      "id": "special_resonance",
      "name": "突变共振",
      "description": "2个特殊肿瘤的突变共振",
      "condition": {
        "organ_requirements": [
          {"organ_type": "special", "count": 2}
        ],
        "adjacency_type": "any"
      },
      "effect": {
        "special_trigger_bonus": 1.0
      },
      "constraints": {
        "max_simultaneous": 1
      },
      "visual": {
        "particle_type": "rainbow_resonance",
        "particle_color": "#FF00FF"
      }
    }
  }
}
```

---

## 性能优化策略

### 缓存机制

```java
// 共生检测缓存
public class SymbiosisDetectionCache {
    private Map<UUID, CachedSymbiosisData> cache = new HashMap<>();
    private static final long CACHE_EXPIRY = 5000; // 5秒过期

    public static class CachedSymbiosisData {
        public List<SymbiosisConfig> availableSynergies;
        public Map<String, Boolean> canActivateMap;
        public long timestamp;

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRY;
        }
    }

    public CachedSymbiosisData getOrCompute(
        Player player,
        ChestCavityData data,
        SymbiosisDetector detector
    ) {
        UUID uuid = player.getUUID();
        CachedSymbiosisData cached = cache.get(uuid);

        if (cached == null || cached.isExpired()) {
            // 重新计算
            cached = computeSymbiosisData(data, detector);
            cache.put(uuid, cached);
        }

        return cached;
    }

    private CachedSymbiosisData computeSymbiosisData(
        ChestCavityData data,
        SymbiosisDetector detector
    ) {
        CachedSymbiosisData result = new CachedSymbiosisData();
        result.timestamp = System.currentTimeMillis();

        // 查找可用的共生
        result.availableSynergies = detector.findPotentialSynergies(data);

        // 预计算激活条件
        result.canActivateMap = new HashMap<>();
        for (SymbiosisConfig config : result.availableSynergies) {
            boolean canActivate = detector.checkAdvancedConditions(data, config);
            result.canActivateMap.put(config.getId(), canActivate);
        }

        return result;
    }

    public void invalidate(Player player) {
        cache.remove(player.getUUID());
    }
}
```

### 延迟计算

```java
// 延迟计算共生效果
public class DelayedSymbiosisCalculator {

    private Set<UUID> pendingRecalculation = new HashSet<>();

    // 标记需要重新计算
    public void markForRecalculation(Player player) {
        pendingRecalculation.add(player.getUUID());
    }

    // 在tick事件中批量计算
    public void onServerTick(ServerLevel level) {
        if (!pendingRecalculation.isEmpty()) {
            // 批量处理需要重新计算的玩家
            for (UUID uuid : pendingRecalculation) {
                Player player = level.getPlayerByUUID(uuid);
                if (player != null) {
                    recalculateSymbiosisEffects(player);
                }
            }
            pendingRecalculation.clear();
        }
    }

    private void recalculateSymbiosisEffects(Player player) {
        TumorData tumorData = getTumorData(player);
        if (tumorData == null) return;

        ChestCavityData data = getChestCavityData(player);

        // 重新检测共生
        symbiosisDetector.detectAndActivateSynergies(player, data);
    }
}
```

### 分区计算

```java
// 分区计算共生效果
public class RegionalSymbiosisCalculator {

    // 将胸腔分为多个区域,只计算受影响区域
    public enum ChestRegion {
        TOP_LEFT(0, 3, 0, 1),
        TOP_CENTER(3, 6, 0, 1),
        TOP_RIGHT(6, 9, 0, 1),
        CENTER_LEFT(0, 3, 1, 2),
        CENTER(3, 6, 1, 2),
        CENTER_RIGHT(6, 9, 1, 2),
        BOTTOM_LEFT(0, 3, 2, 3),
        BOTTOM_CENTER(3, 6, 2, 3),
        BOTTOM_RIGHT(6, 9, 2, 3);

        private final int startCol, endCol;
        private final int startRow, endRow;

        // getters...
    }

    // 只重新计算受影响的区域
    public void recalculateRegion(
        Player player,
        int changedSlot,
        ChestCavityData data
    ) {
        ChestRegion region = getSlotRegion(changedSlot);
        List<SymbiosisConfig> regionSynergies = getRegionSynergies(region);

        // 只计算该区域的共生
        for (SymbiosisConfig config : regionSynergies) {
            if (isSymbiosisInRegion(config, region)) {
                recalculateSymbiosis(player, config, data);
            }
        }
    }

    private ChestRegion getSlotRegion(int slot) {
        int col = slot % 9;
        int row = slot / 9;

        for (ChestRegion region : ChestRegion.values()) {
            if (col >= region.getStartCol() && col < region.getEndCol() &&
                row >= region.getStartRow() && row < region.getEndRow()) {
                return region;
            }
        }
        return ChestRegion.CENTER; // 默认中心区域
    }
}
```

### 早期退出

```java
// 早期退出优化
public class EarlyExitOptimization {

    // 检测共生时,早期退出
    public List<SymbiosisConfig> findPotentialSynergies(
        ChestCavityData data,
        List<SymbiosisConfig> allConfigs
    ) {
        List<SymbiosisConfig> potentials = new ArrayList<>();

        // 快速检查: 如果没有肿瘤器官,直接返回空
        int tumorCount = data.getOrganCount(TUMOR);
        if (tumorCount == 0) {
            return potentials;
        }

        // 检查每个配置
        for (SymbiosisConfig config : allConfigs) {
            // 早期退出: 如果最小肿瘤器官数量不满足,跳过
            if (tumorCount < config.getCondition().getMinTumorCount()) {
                continue;
            }

            // 早期退出: 如果缺少必需的器官,跳过
            if (!hasRequiredOrgans(data, config)) {
                continue;
            }

            // 通过快速检查,添加到潜在列表
            potentials.add(config);
        }

        return potentials;
    }

    private boolean hasRequiredOrgans(ChestCavityData data, SymbiosisConfig config) {
        for (OrganRequirement req : config.getCondition().getOrganRequirements()) {
            if (data.getOrganCount(req.getOrgan()) < req.getCount()) {
                return false;
            }
        }
        return true;
    }
}
```

---

## 共生实例设计

### 入门级共生 (适合新手)

#### 1. 基础呼吸共生

**组合**: 肿瘤肺脏 + 任何肺脏器官

**效果**:
- 肿瘤肺脏: 毒素云范围+50%,伤害+30%
- 相邻肺脏: 呼吸效率+30%

**条件**: 正交相邻

**难度**: ★☆☆☆☆

**设计思路**: 简单直接,只需要两个器官,效果显著

---

### 进阶级共生 (适合中级玩家)

#### 1. 代谢循环共生

**组合**: 肿瘤胃 + 肿瘤肠子

**效果**:
- 肿瘤胃: 饥饿消耗-50%,食物效果+100%
- 肿瘤肠子: 状态持续时间+50%
- 共生特效: 消耗食物时随机获得临时增益(持续60秒)

**条件**: 正交相邻,装备至少2个肿瘤器官

**难度**: ★★★☆☆

**设计思路**: 需要两个特定的肿瘤器官,创造强大的协同循环

#### 2. 净化系统共生

**组合**: 肿瘤肾脏 + 肿瘤肝脏

**效果**:
- 肿瘤肾脏: 废物层数上限+5(达到15层)
- 肿瘤肝脏: 毒素层数上限+5(达到14层)
- 共生特效: 层数可以互相转化(毒素↔废物)

**条件**: 正交相邻,装备至少3个肿瘤器官

**难度**: ★★★☆☆

**设计思路**: 强化层数系统,提供更多的战术选择

---

### 专家级共生 (适合高级玩家)

#### 1. 狂暴战士链

**组合**: 肿瘤心脏 → 肿瘤肌肉 → 肿瘤脾脏 → 肿瘤肝脏

**效果**:
- 狂暴共生(心脏+肌肉): 狂暴阈值提升至50%,伤害+100%,速度+50%
- 免疫共生(脾脏+肝脏): 自动清除负面效果,毒素快速清除
- 共生链奖励: ×1.5倍率
- 总加成: 基础效果 × 2.5(链长4) × 1.5(共生度) = **375%基础效果**

**条件**:
- 所有器官正交相邻形成链
- 血量<50%触发狂暴
- 装备至少6个肿瘤器官

**难度**: ★★★★☆

**设计思路**: 高风险高回报的完全体Build,需要精细的器官摆放和血量管理

#### 2. 适应网络

**组合**: 3个良性肿瘤相邻

**效果**:
- 所有良性肿瘤效果+40%,副作用-50%
- 共生特效: 每10秒恢复5%生命值
- 共生链奖励: ×1.25倍率

**条件**:
- 3个良性肿瘤全部正交相邻
- 装备至少4个肿瘤器官

**难度**: ★★★★☆

**设计思路**: 稳定发育的核心Build,适合长期发育

---

### 传说级共生 (适合专家玩家)

#### 1. 不死军团

**组合**: 完整共生链 + 九头 sy organ

**效果**:
- 共生链长度: 5 (心脏→肌肉→脾脏→肝脏→阑尾)
- 共生链倍率: ×2.5
- 跨系协同: 与九头 sy器官的"不死军团"协同
- 获得"多条生命": 死亡时重生(最多3次)
- 每次重生后,所有肿瘤器官效果+100%(持续1分钟)
- 总加成: 基础效果 × 2.5(链) × 2.0(九头协同) = **500%基础效果**

**条件**:
- 完整的5链共生
- 装备至少3个九头 sy器官
- 装备至少7个肿瘤器官
- 阑尾觉醒成功

**难度**: ★★★★★

**设计思路**: 终极Build,结合了肿瘤系统的所有机制,需要极致的策略和运气

#### 2. 完美适应

**组合**: 阑尾觉醒 + 适应网络 + 幻想种器官

**效果**:
- 阑尾觉醒"完美适应": 所有器官效果+50%
- 适应网络(良性): 良性肿瘤效果+40%,副作用-50%
- 跨系协同"现实扭曲": 30%概率将伤害转化为治疗
- 总加成: 基础效果 × 1.5(觉醒) × 1.4(网络) × 1.3(扭曲) = **273%基础效果**

**条件**:
- 阑尾觉醒成功(5%概率)
- 3个良性肿瘤形成适应网络
- 装备至少2个幻想种器官

**难度**: ★★★★★

**设计思路**: 运气与策略的完美结合,当一切顺利时,几乎无敌

---

## 总结

### 设计成果

本设计文档提供了肿瘤器官的完整共生机制系统,包括:

1. ✅ **三种共生类型**: 寄生型、互惠型、转化型
2. ✅ **完整的共生条件判定**: 相邻检测、触发条件、优先级系统
3. ✅ **详细的效果计算**: 基础效果、链式倍增、共生度加成
4. ✅ **肿瘤间协同系统**: 类型内协同、跨类型协同、协同奖励
5. ✅ **跨系交互设计**: 与九狱、九头 sy、弗兰肯斯坦、幻想种、墨水/颜料的交互
6. ✅ **完整的视觉反馈**: 粒子效果、光效、动画、UI反馈
7. ✅ **平衡性设计**: 强度分级、平衡机制、共生度平衡、共生链平衡
8. ✅ **技术实现方案**: 完整的数据结构、检测流程、效果应用代码
9. ✅ **JSON配置示例**: 完整的配置文件,可直接用于游戏
10. ✅ **性能优化策略**: 缓存、延迟计算、分区计算、早期退出

### 核心优势

1. **深度互动**: 共生效果大于各器官效果之和
2. **策略选择**: 玩家需要主动创造共生条件
3. **多样性**: 多种共生类型和组合,避免单一最优解
4. **主题一致性**: 完全符合肿瘤"变异、不稳定、侵蚀性"特征
5. **扩展性强**: 与T1-T3完美协调,为未来扩展预留空间

### 设计亮点

1. **三种共生类型**: 寄生型(单向强化)、互惠型(双向强化)、转化型(质变)
2. **共生链系统**: 连续激活多个共生,产生倍增效果
3. **共生度系统**: 衡量玩家共生状态的指标,影响高级效果
4. **跨系交互**: 与其他流派器官的独特协同,创造无限可能
5. **视觉反馈**: 丰富的粒子效果、光效、动画,提供强烈的视觉冲击

### 待审核问题

1. **转化型共生的不可逆性**: 是否应该提供某种方式撤销转化?
2. **共生链倍率的平衡**: 5链的2.5x倍率是否过强?
3. **跨系交互的强度**: 某些跨系协同(如不死军团)是否过于强力?
4. **性能影响**: 频繁的共生检测是否会影响游戏性能?
5. **新手友好度**: 共生系统是否过于复杂,新手难以理解?

### 下一步

等待审核反馈,根据审核结果进行调整和优化。审核通过后,可以开始技术实现。

---

**文档结束**

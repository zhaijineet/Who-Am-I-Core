# 肿瘤器官基础属性设计文档 T1

**设计版本**: v1.0
**创建日期**: 2026-03-15
**设计者**: Creative Content Designer
**状态**: 待审核

---

## 目录

1. [设计概述](#设计概述)
2. [肿瘤主题定位](#肿瘤主题定位)
3. [器官属性总表](#器官属性总表)
4. [器官详细设计](#器官详细设计)
5. [获取难度系统](#获取难度系统)
6. [技术实现建议](#技术实现建议)
7. [JSON配置示例](#json配置示例)
8. [搭配潜力分析](#搭配潜力分析)

---

## 设计概述

### 核心设计理念

肿瘤器官的设计围绕**"风险与收益并存"**的核心主题展开。每个器官都提供强大的属性增益，但伴随着独特的负面效果或代价。这要求玩家在装备时进行战略性的权衡和搭配。

### 设计原则

1. **高收益高代价** - 属性高于同类器官，但有副作用
2. **增殖主题** - 多个肿瘤器官互相增强
3. **不稳定性** - 随机性、变异、生长等机制
4. **黑暗奇幻** - 符合Chest Cavity Beyond的整体风格
5. **可扩展性** - 为后续共生、进化系统预留空间

### 器官列表

| 中文名 | 物品ID | 标签 | 主要功能 |
|--------|--------|------|----------|
| 肿瘤心脏 | tumor_heart | #心脏 | 生命相关核心 |
| 肿瘤肺脏 | tumor_lung | #肺 | 呼吸、移动 |
| 肿瘤胃 | tumor_stomach | #胃 | 营养、消化 |
| 肿瘤肠子 | tumor_intestine | #肠 | 代谢、恢复 |
| 肿瘤肾脏 | tumor_kidney | #肾 | 过滤、耐力 |
| 肿瘤脾脏 | tumor_spleen | #脾 | 免疫、防御 |
| 肿瘤肝脏 | tumor_liver | #肝 | 解毒、增益 |
| 肿瘤阑尾 | tumor_appendix | #阑尾 | 隐藏效果、触发 |
| 肿瘤肌肉 | tumor_muscle | #肌肉 | 力量、速度 |

---

## 肿瘤主题定位

### 背景故事

肿瘤器官来源于古老的"增殖诅咒"，这些器官具有无限增殖和变异的能力。它们寄生在宿主体内，提供超乎寻常的力量，但代价是宿主的理智和生命。

### 核心特征

1. **增殖性**: 装备越多肿瘤器官，效果越强，但代价也越大
2. **变异性**: 随机性效果，每次使用可能不同
3. **侵蚀性**: 长期使用会对玩家造成不可逆的影响
4. **狂暴性**: 低血量时进入狂暴状态，大幅增强

### 视觉风格

- 颜色：暗紫色、深红色、病态绿色
- 质感：脉动、滴血、不规则形状
- 粒子效果：毒素、腐化、变异孢子

---

## 器官属性总表

### 属性速查表

| 器官 | HEALTH | BREATH | STRENGTH | SPEED | 获取难度 | 副作用 |
|------|--------|--------|----------|-------|----------|--------|
| 肿瘤心脏 | +4 | - | +2 | - | ★★★☆☆ | 生命流失 |
| 肿瘤肺脏 | - | +3 | - | +2 | ★★☆☆☆ | 氧气消耗 |
| 肿瘤胃 | +2 | - | +1 | - | ★☆☆☆☆ | 饥饿加速 |
| 肿瘤肠子 | +1 | - | - | - | ★★☆☆☆ | 随机效果 |
| 肿瘤肾脏 | - | - | +3 | +1 | ★★★☆☆ | 耐力消耗 |
| 肿瘤脾脏 | +3 | - | - | - | ★★★☆☆ | 易感疾病 |
| 肿瘤肝脏 | +2 | - | +1 | +1 | ★★★★☆ | 毒素积累 |
| 肿瘤阑尾 | +1 | - | - | - | ★★★★★ | 随机增益/负面 |
| 肿瘤肌肉 | - | - | +4 | +3 | ★★★★☆ | 体力消耗 |

### 属性说明

- **HEALTH**: 最大生命值
- **BREATH**: 呼吸容量和恢复速度
- **STRENGTH**: 攻击力和负重能力
- **SPEED**: 移动速度和攻击速度

---

## 器官详细设计

### 1. 肿瘤心脏 (tumor_heart)

**基本信息**
- **标签**: #心脏
- **获取难度**: ★★★☆☆ (困难)
- **稀有度**: 史诗

**基础属性**
- **+4 HEALTH** (显著高于普通心脏)
- **+2 STRENGTH** (提供额外力量)

**独特被动效果**
- **增殖之心**: 每装备1个肿瘤器官，最大生命值额外+5%（最多+50%）
- **恶性跳动**: 每10秒有20%概率触发一次生命流失（失去2点生命）
- **狂暴触发**: 生命值低于30%时，所有伤害+50%，但持续失血

**技术实现建议**
```java
// 事件：chestCavityOpen
- 计算胸腔内肿瘤器官数量
- 应用增殖之心效果

// 事件：tick（服务器端，每10秒）
- 随机检测，触发恶性跳动

// 事件：tick（服务器端，低血量检测）
- 应用狂暴效果
```

**搭配潜力**
- 与其他肿瘤器官形成"增殖链"
- 与九狱器官搭配：血越低伤害越高
- 与九头蛇器官搭配：狂暴+再生

---

### 2. 肿瘤肺脏 (tumor_lung)

**基本信息**
- **标签**: #肺
- **获取难度**: ★★☆☆☆ (罕见)
- **稀有度**: 稀有

**基础属性**
- **+3 BREATH** (呼吸容量和恢复速度)
- **+2 SPEED** (移动速度)

**独特被动效果**
- **毒性呼吸**: 呼吸时向周围释放毒素云（伤害：2点/秒，半径3格）
- **变异气息**: 水下呼吸时间-50%，但移动速度在水下+30%
- **窒息适应**: 缺氧时不会立即死亡，而是进入狂暴状态（持续5秒）

**技术实现建议**
```java
// 事件：tick（每秒）
- 检测周围实体，应用毒素伤害
- 检测水下环境，应用移动速度修正

// 事件：tick（缺氧检测）
- 覆盖默认窒息机制
```

**搭配潜力**
- 与龙类器官搭配：增强吐息类技能
- 与墨水/颜料器官搭配：散布毒素云
- 相邻搭配：肿瘤肺脏 + 肿瘤胃 → 毒素范围+50%

---

### 3. 肿瘤胃 (tumor_stomach)

**基本信息**
- **标签**: #胃
- **获取难度**: ★☆☆☆☆ (普通)
- **稀有度**: 普通

**基础属性**
- **+2 HEALTH**
- **+1 STRENGTH**

**独特被动效果**
- **无尽饥饿**: 饥饿值消耗速度+100%
- **腐化消化**: 可以食用任何物品（包括有毒物品），获得额外效果
- **营养转化**: 消耗食物时，有50%概率获得临时增益（持续30秒）：
  - 力量+1
  - 速度+1
  - 抗性+1
  - 或随机负面效果（中毒、虚弱）

**技术实现建议**
```java
// 事件：tick（饥饿检测）
- 加速饥饿值消耗

// 事件：itemEaten（食物食用事件）
- 检测食物类型，应用腐化消化
- 随机生成临时增益或负面效果
```

**搭配潜力**
- 与弗兰肯斯坦器官搭配：增强消化能力
- 相邻搭配：肿瘤胃 + 肿瘤肠子 → 食物效果+100%

---

### 4. 肿瘤肠子 (tumor_intestine)

**基本信息**
- **标签**: #肠
- **获取难度**: ★★☆☆☆ (罕见)
- **稀有度**: 稀有

**基础属性**
- **+1 HEALTH**

**独特被动效果**
- **变异代谢**: 每30秒随机改变一次代谢状态：
  1. **强化状态**: +20%伤害，持续30秒
  2. **虚弱状态**: -10%速度，持续30秒
  3. **再生状态**: 生命恢复+200%，持续30秒
  4. **中毒状态**: 持续受到中毒伤害，持续30秒
- **养分吸收**: 生命恢复效果+30%，但饥饿值消耗+20%

**技术实现建议**
```java
// 事件：chestCavityOpen
- 初始化代谢状态

// 事件：tick（每30秒）
- 随机切换代谢状态
- 应用对应效果

// 属性修正：lifeRegenMultiplier
- +30%生命恢复效果

// 属性修正：hungerMultiplier
- +20%饥饿值消耗
```

**搭配潜力**
- 与九头蛇器官搭配：增强再生状态效果
- 与肿瘤胃搭配：形成"消化-代谢"循环
- 相邻搭配：肿瘤肠子 + 肿瘤肝脏 → 变异状态持续时间+50%

---

### 5. 肿瘤肾脏 (tumor_kidney)

**基本信息**
- **标签**: #肾
- **获取难度**: ★★★☆☆ (困难)
- **稀有度**: 史诗

**基础属性**
- **+3 STRENGTH** (显著的力量提升)
- **+1 SPEED** (速度小幅提升)

**独特被动效果**
- **毒素过滤**: 毒素持续时间-50%
- **过滤超载**: 使用耐力时（冲刺、跳跃），有30%概率触发"过滤爆发"：
  - 效果：接下来5秒内所有属性+20%
  - 代价：之后10秒内所有属性-10%
- **废物堆积**: 每60秒积累一层"毒素"，每层：
  - 好处：伤害+5%
  - 坏处：生命恢复-5%
  - 最多叠加10层

**技术实现建议**
```java
// 事件：tick（每60秒）
- 增加毒素层数
- 应用层数效果

// 事件：livingUpdate（耐力使用检测）
- 检测冲刺、跳跃等行为
- 30%概率触发过滤爆发

// 属性修正：poisonDurationMultiplier
- -50%毒素持续时间
```

**搭配潜力**
- 与肝脏器官搭配：增强过滤系统
- 与龙类器官搭配：高爆发伤害
- 相邻搭配：肿瘤肾脏 + 肿瘤脾脏 → 免疫系统强化

---

### 6. 肿瘤脾脏 (tumor_spleen)

**基本信息**
- **标签**: #脾
- **获取难度**: ★★★☆☆ (困难)
- **稀有度**: 史诗

**基础属性**
- **+3 HEALTH** (高生命值提升)

**独特被动效果**
- **变异免疫**: 受到负面效果时，有25%概率将其转化为正面效果：
  - 中毒 → 生命恢复加速（等量）
  - 虚弱 → 力量提升（等量）
  - 缓慢 → 速度提升（等量）
- **脆弱防线**: 受到伤害时，有10%概率额外受到50%伤害
- **免疫记忆**: 成功转化负面效果后，该效果永久免疫（持续到死亡）

**技术实现建议**
```java
// 事件：effectApplicable（药水效果应用前）
- 检测负面效果
- 25%概率转化为正面效果
- 记录免疫的药水效果

// 事件：hurt（受伤事件）
- 10%概率触发额外伤害
```

**搭配潜力**
- 与肝脏搭配：形成强大的解毒系统
- 与九狱器官搭配：转化地狱类负面效果
- 相邻搭配：肿瘤脾脏 + 肿瘤肝脏 → 免疫系统完全体

---

### 7. 肿瘤肝脏 (tumor_liver)

**基本信息**
- **标签**: #肝
- **获取难度**: ★★★★☆ (极难)
- **稀有度**: 传说

**基础属性**
- **+2 HEALTH**
- **+1 STRENGTH**
- **+1 SPEED** (全能型属性)

**独特被动效果**
- **腐化代谢**: 所有药水效果持续时间+50%
- **毒素积累**: 每使用一次药水，积累一层"毒素"
  - 1层：无效果
  - 2-3层：伤害+10%，生命恢复-5%
  - 4-5层：伤害+20%，生命恢复-10%
  - 6+层：伤害+30%，生命恢复-15%，持续失血
- **肝脏再生**: 每分钟清除1层毒素（不能完全清除）

**技术实现建议**
```java
// 事件：potionApplied（药水应用时）
- 增加药水持续时间（+50%）
- 增加毒素层数

// 属性修正：potionDurationMultiplier
- +50%所有药水持续时间

// 事件：tick（每分钟）
- 减少1层毒素
```

**搭配潜力**
- 与脾脏搭配：形成药水-免疫循环
- 与墨水/颜料器官搭配：延长艺术类效果
- 相邻搭配：肿瘤肝脏 + 肿瘤肾脏 → 毒素系统增强

---

### 8. 肿瘤阑尾 (tumor_appendix)

**基本信息**
- **标签**: #阑尾
- **获取难度**: ★★★★★ (传说)
- **稀有度**: 传说

**基础属性**
- **+1 HEALTH** (看似弱小，实则...)

**独特被动效果**
- **潜伏变异**: 阑尾看似无用，但实际上隐藏着强大的力量
- **随机觉醒**: 每次胸腔关闭时有5%概率觉醒，随机获得以下效果之一：
  1. **完美适应**: 接下来5分钟内，所有器官效果+50%
  2. **细胞融合**: 将相邻器官的属性复制给自己
  3. **变异爆发**: 周围10格内所有敌人受到大量毒素伤害（20点）
  4. **致命缺陷**: 接下来3分钟内，所有器官效果-50%（负面）
- **阑尾炎**: 如果觉醒失败（95%概率），阑尾会发炎：
  - 效果：每秒失去1点生命
  - 持续时间：随机10-60秒
  - 治疗：等待自然消退，或食用特殊物品

**技术实现建议**
```java
// 事件：chestCavityClose
- 5%概率觉醒
- 随机选择觉醒效果
- 或触发阑尾炎（95%）

// 事件：tick（阑尾炎期间）
- 持续失去生命值

// 事件：itemEaten（特殊物品检测）
- 检测并治疗阑尾炎
```

**搭配潜力**
- 高风险高回报的"彩票器官"
- 与其他肿瘤器官搭配：觉醒时收益翻倍
- 与九狱器官搭配：阑尾炎时触发地狱类效果

---

### 9. 肿瘤肌肉 (tumor_muscle)

**基本信息**
- **标签**: #肌肉
- **获取难度**: ★★★★☆ (极难)
- **稀有度**: 传说

**基础属性**
- **+4 STRENGTH** (极高的力量提升)
- **+3 SPEED** (极高的速度提升)

**独特被动效果**
- **肌肉痉挛**: 有20%概率触发肌肉痉挛：
  - 效果：无法移动或攻击，持续1-3秒
  - 触发频率：随机（平均每30秒一次）
- **爆发力量**: 正常状态下，所有伤害+30%
- **体力透支**: 冲刺和跳跃消耗2倍耐力
- **肾上腺素**: 生命值低于50%时：
  - 肌肉痉挛概率降至5%
  - 所有伤害+50%（可叠加）
  - 但每秒失去1点生命

**技术实现建议**
```java
// 事件：tick（每30秒）
- 20%概率触发肌肉痉挛
- 应用无法移动/攻击效果

// 属性修正：damageMultiplier
- +30%所有伤害（正常状态）
- +50%所有伤害（低血量状态）

// 属性修正：staminaMultiplier
- 冲刺、跳跃消耗2倍

// 事件：tick（低血量检测）
- 应用肾上腺素效果
- 持续失血
```

**搭配潜力**
- 与心脏搭配：爆发力量的核心
- 与肾脏搭配：缓解体力透支
- 相邻搭配：肿瘤肌肉 + 肿瘤心脏 → 狂暴战士

---

## 获取难度系统

### 难度分级

| 级别 | 星级 | 描述 | 获取方式示例 |
|------|------|------|--------------|
| 普通 | ★☆☆☆☆ | 常见 | 普通怪物、宝箱 |
| 罕见 | ★★☆☆☆ | 较少 | 精英怪、小BOSS |
| 困难 | ★★★☆☆ | 困难 | 主要BOSS、特殊事件 |
| 极难 | ★★★★☆ | 极难 | 特殊BOSS、隐藏挑战 |
| 传说 | ★★★★★ | 传说 | 终极挑战、特殊条件 |

### 肿瘤器官获取设计

#### 普通器官 (1个)
- **肿瘤胃**: 从被腐化感染的僵尸、骷髅等常见怪物掉落

#### 罕见器官 (2个)
- **肿瘤肺脏**: 从腐化蜘蛛、洞穴蜘蛛等稀有怪物掉落
- **肿瘤肠子**: 从腐化苦力怕、沼泽怪物掉落

#### 困难器官 (3个)
- **肿瘤心脏**: 从腐化女巫、守卫者等BOSS掉落
- **肿瘤肾脏**: 从深渊守卫、监守者等深海怪物掉落
- **肿瘤脾脏**: 从凋灵骷髅、下界怪物掉落

#### 极难器官 (2个)
- **肿瘤肝脏**: 从被腐化的末影龙、远古守卫者等特殊BOSS掉落
- **肿瘤肌肉**: 从被腐化的劫掠兽、Boss级怪物掉落

#### 传说器官 (1个)
- **肿瘤阑尾**: 极低概率从任何BOSS掉落（0.1%），或完成特殊隐藏事件获得

---

## 技术实现建议

### 1. 数据结构设计

```java
// 肿瘤器官专属数据类
public class TumorData {
    // 肿瘤器官数量
    private int tumorCount;

    // 毒素层数（肝脏）
    private int toxinLayers;

    // 废物层数（肾脏）
    private int wasteLayers;

    // 阑尾状态
    private boolean appendixInflamed;
    private int appendixInflammationTimer;

    // 代谢状态（肠子）
    private MetabolismState metabolismState;
    private int metabolismTimer;

    // 免疫记录（脾脏）
    private Set<MobEffect> immuneEffects;

    // 觉醒效果（阑尾）
    private AwakeningEffect awakeningEffect;
    private int awakeningTimer;
}

enum MetabolismState {
    ENHANCED,    // 强化状态
    WEAKENED,    // 虚弱状态
    REGENERATING, // 再生状态
    POISONED     // 中毒状态
}

enum AwakeningEffect {
    NONE,
    PERFECT_ADAPTATION,
    CELL_FUSION,
    MUTATION_BURST,
    FATAL_FLAW
}
```

### 2. 事件处理流程

```java
// 胸腔打开事件
public void onChestCavityOpen(Player player, ChestCavityData data) {
    TumorData tumorData = getOrCreateTumorData(player);

    // 计算肿瘤器官数量
    int count = data.getOrganCount(TUMOR_TAG);
    tumorData.setTumorCount(count);

    // 应用增殖之心效果
    if (data.hasOrgan(TUMOR_HEART)) {
        float healthBonus = 1.0f + (count * 0.05f);
        applyHealthMultiplier(player, Math.min(healthBonus, 1.5f));
    }

    // 初始化代谢状态
    if (data.hasOrgan(TUMOR_INTESTINE)) {
        tumorData.setMetabolismState(randomMetabolismState());
        tumorData.setMetabolismTimer(30 * 20); // 30秒
    }
}

// 胸腔关闭事件
public void onChestCavityClose(Player player, ChestCavityData data) {
    // 检测阑尾觉醒
    if (data.hasOrgan(TUMOR_APPENDIX)) {
        if (Math.random() < 0.05) { // 5%概率
            awakenAppendix(player);
        } else {
            triggerAppendicitis(player);
        }
    }
}

// Tick事件（每秒）
public void onServerTick(Player player) {
    TumorData tumorData = getTumorData(player);
    if (tumorData == null) return;

    // 恶性跳动（心脏）
    if (player.tickCount % (10 * 20) == 0) { // 每10秒
        if (Math.random() < 0.2) { // 20%概率
            player.hurt(player.damageSources().magic(), 2.0f);
        }
    }

    // 代谢状态切换（肠子）
    if (data.hasOrgan(TUMOR_INTESTINE)) {
        tumorData.metabolismTimer--;
        if (tumorData.metabolismTimer <= 0) {
            tumorData.setMetabolismState(randomMetabolismState());
            tumorData.setMetabolismTimer(30 * 20);
        }
        applyMetabolismEffect(player, tumorData.getMetabolismState());
    }

    // 毒素积累（肝脏）
    if (tumorData.getToxinLayers() > 0) {
        player.tickCount % (60 * 20) == 0) { // 每分钟
            tumorData.setToxinLayers(Math.max(0,
                tumorData.getToxinLayers() - 1));
        }
        applyToxinEffects(player, tumorData.getToxinLayers());
    }

    // 阑尾炎处理
    if (tumorData.isAppendixInflamed()) {
        tumorData.appendixInflammationTimer--;
        if (player.tickCount % 20 == 0) { // 每秒
            player.hurt(player.damageSources().magic(), 1.0f);
        }
        if (tumorData.appendixInflammationTimer <= 0) {
            tumorData.setAppendixInflamed(false);
        }
    }
}

// 药水应用事件
public void onPotionApplied(Player player, MobEffect effect) {
    ChestCavityData data = getChestCavityData(player);

    // 腐化代谢（肝脏）
    if (data.hasOrgan(TUMOR_LIVER)) {
        // 延长药水持续时间
        effect.extendDuration(0.5f); // +50%

        // 积累毒素
        TumorData tumorData = getTumorData(player);
        tumorData.setToxinLayers(tumorData.getToxinLayers() + 1);
    }

    // 变异免疫（脾脏）
    if (data.hasOrgan(TUMOR_SPLEEN) && isNegativeEffect(effect)) {
        if (Math.random() < 0.25) { // 25%概率
            transformNegativeToPositive(player, effect);
        }
    }
}

// 受伤事件
public void onHurt(Player player, DamageSource source, float amount) {
    ChestCavityData data = getChestCavityData(player);

    // 脆弱防线（脾脏）
    if (data.hasOrgan(TUMOR_SPLEEN)) {
        if (Math.random() < 0.1) { // 10%概率
            player.hurt(source, amount * 0.5f); // 额外50%伤害
        }
    }

    // 狂暴触发（心脏）
    if (data.hasOrgan(TUMOR_HEART)) {
        if (player.getHealth() < player.getMaxHealth() * 0.3f) {
            applyFrenzyEffect(player);
        }
    }

    // 肾上腺素（肌肉）
    if (data.hasOrgan(TUMOR_MUSCLE)) {
        if (player.getHealth() < player.getMaxHealth() * 0.5f) {
            applyAdrenalineEffect(player);
        }
    }
}
```

### 3. 属性修正系统

```java
// 属性修正接口
public interface TumorAttributeModifier {
    float modifyHealth(Player player, float base);
    float modifyBreath(Player player, float base);
    float modifyStrength(Player player, float base);
    float modifySpeed(Player player, float base);
    float modifyDamage(Player player, float base);
    float modifyRegen(Player player, float base);
}

// 肿瘤心脏修正
public class TumorHeartModifier implements TumorAttributeModifier {
    @Override
    public float modifyHealth(Player player, float base) {
        ChestCavityData data = getChestCavityData(player);
        int tumorCount = data.getOrganCount(TUMOR_TAG);
        float multiplier = 1.0f + Math.min(tumorCount * 0.05f, 0.5f);
        return base * multiplier;
    }

    @Override
    public float modifyDamage(Player player, float base) {
        if (player.getHealth() < player.getMaxHealth() * 0.3f) {
            return base * 1.5f; // +50%伤害
        }
        return base;
    }
}

// 肿瘤肌肉修正
public class TumorMuscleModifier implements TumorAttributeModifier {
    @Override
    public float modifyDamage(Player player, float base) {
        // 爆发力量
        float multiplier = 1.3f; // +30%

        // 肾上腺素
        if (player.getHealth() < player.getMaxHealth() * 0.5f) {
            multiplier += 0.5f; // +50%
        }

        return base * multiplier;
    }

    @Override
    public float modifyStaminaCost(Player player, float base) {
        return base * 2.0f; // 2倍耐力消耗
    }
}

// 肿瘤肝脏修正
public class TumorLiverModifier implements TumorAttributeModifier {
    @Override
    public float modifyPotionDuration(Player player, float base) {
        return base * 1.5f; // +50%药水持续时间
    }

    @Override
    public float modifyRegen(Player player, float base) {
        TumorData tumorData = getTumorData(player);
        int toxinLayers = tumorData.getToxinLayers();

        if (toxinLayers >= 6) {
            return base * 0.85f; // -15%生命恢复
        } else if (toxinLayers >= 4) {
            return base * 0.9f; // -10%生命恢复
        } else if (toxinLayers >= 2) {
            return base * 0.95f; // -5%生命恢复
        }
        return base;
    }

    @Override
    public float modifyDamage(Player player, float base) {
        TumorData tumorData = getTumorData(player);
        int toxinLayers = tumorData.getToxinLayers();

        if (toxinLayers >= 6) {
            return base * 1.3f; // +30%伤害
        } else if (toxinLayers >= 4) {
            return base * 1.2f; // +20%伤害
        } else if (toxinLayers >= 2) {
            return base * 1.1f; // +10%伤害
        }
        return base;
    }
}
```

### 4. 性能优化建议

1. **数据缓存**: 为每个玩家缓存TumorData，避免重复计算
2. **延迟计算**: 大部分效果在tick事件中统一处理
3. **早期退出**: 如果没有肿瘤器官，直接跳过相关逻辑
4. **批量处理**: 多玩家使用并行流处理
5. **事件节流**: 避免在tick事件中执行过于复杂的计算

---

## JSON配置示例

### 肿瘤器官基础配置

```json
{
  "tumor_organs": {
    "tumor_heart": {
      "id": "tumor_heart",
      "tag": "#心脏",
      "rarity": "EPIC",
      "difficulty": 3,
      "base_attributes": {
        "HEALTH": 4,
        "STRENGTH": 2
      },
      "unique_effects": [
        {
          "name": "proliferation_heart",
          "type": "passive",
          "description": "每装备1个肿瘤器官，最大生命值+5%（最多+50%）",
          "implementation": "onChestCavityOpen"
        },
        {
          "name": "malignant_beat",
          "type": "random_tick",
          "description": "每10秒有20%概率触发生命流失（失去2点生命）",
          "probability": 0.2,
          "interval": 200,
          "effect": "damage:2:magic"
        },
        {
          "name": "frenzy_trigger",
          "type": "conditional",
          "condition": "health < 30%",
          "effect": {
            "damage_multiplier": 1.5,
            "bleed": true
          }
        }
      ]
    },
    "tumor_lung": {
      "id": "tumor_lung",
      "tag": "#肺",
      "rarity": "RARE",
      "difficulty": 2,
      "base_attributes": {
        "BREATH": 3,
        "SPEED": 2
      },
      "unique_effects": [
        {
          "name": "toxic_breath",
          "type": "aura",
          "radius": 3.0,
          "damage": 2.0,
          "interval": 20
        },
        {
          "name": "mutated_breath",
          "type": "environmental",
          "underwater": {
            "breath_multiplier": 0.5,
            "speed_bonus": 0.3
          }
        }
      ]
    },
    "tumor_stomach": {
      "id": "tumor_stomach",
      "tag": "#胃",
      "rarity": "COMMON",
      "difficulty": 1,
      "base_attributes": {
        "HEALTH": 2,
        "STRENGTH": 1
      },
      "unique_effects": [
        {
          "name": "endless_hunger",
          "type": "passive",
          "hunger_multiplier": 2.0
        },
        {
          "name": "corrupted_digestion",
          "type": "item_consumed",
          "can_eat_anything": true,
          "random_effect": {
            "probability": 0.5,
            "duration": 600,
            "effects": [
              "strength:1",
              "speed:1",
              "resistance:1",
              "poison:1",
              "weakness:1"
            ]
          }
        }
      ]
    },
    "tumor_intestine": {
      "id": "tumor_intestine",
      "tag": "#肠",
      "rarity": "RARE",
      "difficulty": 2,
      "base_attributes": {
        "HEALTH": 1
      },
      "unique_effects": [
        {
          "name": "mutated_metabolism",
          "type": "state_cycle",
          "interval": 600,
          "states": [
            {
              "name": "enhanced",
              "damage_multiplier": 1.2,
              "duration": 600
            },
            {
              "name": "weakened",
              "speed_multiplier": 0.9,
              "duration": 600
            },
            {
              "name": "regenerating",
              "regen_multiplier": 3.0,
              "duration": 600
            },
            {
              "name": "poisoned",
              "poison": true,
              "duration": 600
            }
          ]
        },
        {
          "name": "nutrient_absorption",
          "type": "passive",
          "regen_multiplier": 1.3,
          "hunger_multiplier": 1.2
        }
      ]
    },
    "tumor_kidney": {
      "id": "tumor_kidney",
      "tag": "#肾",
      "rarity": "EPIC",
      "difficulty": 3,
      "base_attributes": {
        "STRENGTH": 3,
        "SPEED": 1
      },
      "unique_effects": [
        {
          "name": "toxin_filtration",
          "type": "passive",
          "poison_duration_multiplier": 0.5
        },
        {
          "name": "filtration_overload",
          "type": "stamina_use",
          "probability": 0.3,
          "effect": {
            "buff_duration": 100,
            "buff_multiplier": 1.2,
            "debuff_duration": 200,
            "debuff_multiplier": 0.9
          }
        },
        {
          "name": "waste_accumulation",
          "type": "stacking",
          "interval": 1200,
          "max_stacks": 10,
          "per_stack": {
            "damage_bonus": 0.05,
            "regen_penalty": 0.05
          }
        }
      ]
    },
    "tumor_spleen": {
      "id": "tumor_spleen",
      "tag": "#脾",
      "rarity": "EPIC",
      "difficulty": 3,
      "base_attributes": {
        "HEALTH": 3
      },
      "unique_effects": [
        {
          "name": "mutated_immunity",
          "type": "effect_transform",
          "probability": 0.25,
          "transformations": {
            "poison": "regen",
            "weakness": "strength",
            "slowness": "speed"
          }
        },
        {
          "name": "fragile_defense",
          "type": "on_hurt",
          "probability": 0.1,
          "extra_damage": 0.5
        },
        {
          "name": "immune_memory",
          "type": "passive",
          "remember_transformed": true,
          "reset_on_death": true
        }
      ]
    },
    "tumor_liver": {
      "id": "tumor_liver",
      "tag": "#肝",
      "rarity": "LEGENDARY",
      "difficulty": 4,
      "base_attributes": {
        "HEALTH": 2,
        "STRENGTH": 1,
        "SPEED": 1
      },
      "unique_effects": [
        {
          "name": "corrupted_metabolism",
          "type": "passive",
          "potion_duration_multiplier": 1.5
        },
        {
          "name": "toxin_accumulation",
          "type": "stacking",
          "trigger": "potion_used",
          "max_stacks": 99,
          "effects": {
            "2-3": {
              "damage_bonus": 0.1,
              "regen_penalty": 0.05
            },
            "4-5": {
              "damage_bonus": 0.2,
              "regen_penalty": 0.1
            },
            "6+": {
              "damage_bonus": 0.3,
              "regen_penalty": 0.15,
              "bleed": true
            }
          }
        },
        {
          "name": "liver_regeneration",
          "type": "passive",
          "detox_interval": 1200,
          "detox_amount": 1
        }
      ]
    },
    "tumor_appendix": {
      "id": "tumor_appendix",
      "tag": "#阑尾",
      "rarity": "LEGENDARY",
      "difficulty": 5,
      "base_attributes": {
        "HEALTH": 1
      },
      "unique_effects": [
        {
          "name": "latent_mutation",
          "type": "on_chest_close",
          "awaken_probability": 0.05,
          "awaken_effects": [
            {
              "name": "perfect_adaptation",
              "organ_bonus": 0.5,
              "duration": 6000
            },
            {
              "name": "cell_fusion",
              "copy_adjacent": true
            },
            {
              "name": "mutation_burst",
              "aoe_damage": 20,
              "radius": 10,
              "damage_type": "poison"
            },
            {
              "name": "fatal_flaw",
              "organ_penalty": 0.5,
              "duration": 3600
            }
          ]
        },
        {
          "name": "appendicitis",
          "type": "failed_awaken",
          "probability": 0.95,
          "effect": {
            "damage": 1,
            "interval": 20,
            "duration": {
              "min": 200,
              "max": 1200
            }
          }
        }
      ]
    },
    "tumor_muscle": {
      "id": "tumor_muscle",
      "tag": "#肌肉",
      "rarity": "LEGENDARY",
      "difficulty": 4,
      "base_attributes": {
        "STRENGTH": 4,
        "SPEED": 3
      },
      "unique_effects": [
        {
          "name": "muscle_spasm",
          "type": "random_tick",
          "probability": 0.2,
          "interval": 600,
          "effect": {
            "immobilize": true,
            "disable_attack": true,
            "duration": {
              "min": 20,
              "max": 60
            }
          }
        },
        {
          "name": "burst_strength",
          "type": "passive",
          "damage_multiplier": 1.3
        },
        {
          "name": "stamina_exhaustion",
          "type": "passive",
          "stamina_cost_multiplier": 2.0
        },
        {
          "name": "adrenaline",
          "type": "conditional",
          "condition": "health < 50%",
          "effects": {
            "spasm_probability": 0.05,
            "damage_bonus": 0.5,
            "bleed": true,
            "bleed_damage": 1
          }
        }
      ]
    }
  }
}
```

### 肿瘤搭配配置

```json
{
  "tumor_synergies": {
    "adjacent": [
      {
        "id": "tumor_lung_stomach",
        "name": "毒素扩散",
        "organs": ["tumor_lung", "tumor_stomach"],
        "effect": {
          "toxic_cloud_radius": 1.5,
          "poison_duration": 1.5
        }
      },
      {
        "id": "tumor_stomach_intestine",
        "name": "完美代谢",
        "organs": ["tumor_stomach", "tumor_intestine"],
        "effect": {
          "food_effect_bonus": 1.0,
          "hunger_penalty_reduction": 0.25
        }
      },
      {
        "id": "tumor_intestine_liver",
        "name": "变异循环",
        "organs": ["tumor_intestine", "tumor_liver"],
        "effect": {
          "metabolism_duration_bonus": 0.5,
          "potion_duration_bonus": 0.25
        }
      },
      {
        "id": "tumor_kidney_spleen",
        "name": "免疫系统",
        "organs": ["tumor_kidney", "tumor_spleen"],
        "effect": {
          "negative_effect_duration": 0.7,
          "transform_probability_bonus": 0.1
        }
      },
      {
        "id": "tumor_spleen_liver",
        "name": "免疫网络",
        "organs": ["tumor_spleen", "tumor_liver"],
        "effect": {
          "immune_effect_count": 3,
          "potion_neutral_cost": 0
        }
      },
      {
        "id": "tumor_liver_kidney",
        "name": "净化系统",
        "organs": ["tumor_liver", "tumor_kidney"],
        "effect": {
          "toxin_decay_rate": 2.0,
          "waste_decay_rate": 2.0
        }
      },
      {
        "id": "tumor_heart_muscle",
        "name": "狂暴战士",
        "organs": ["tumor_heart", "tumor_muscle"],
        "effect": {
          "frenzy_threshold": 0.5,
          "frenzy_damage_bonus": 0.3,
          "spasm_probability_reduction": 0.1
        }
      }
    ],
    "set": [
      {
        "id": "tumor_set_2",
        "name": "恶性生长",
        "count": 2,
        "effects": [
          {
            "regen_multiplier": 0.5,
            "max_health_multiplier": 1.3
          }
        ]
      },
      {
        "id": "tumor_set_4",
        "name": "狂暴突变",
        "count": 4,
        "effects": [
          {
            "damage_multiplier": 1.5,
            "bleed": true,
            "bleed_damage": 1
          }
        ]
      },
      {
        "id": "tumor_set_6",
        "name": "肿瘤军团",
        "count": 6,
        "effects": [
          {
            "all_attributes": 1.2,
            "regen_penalty": 0.3,
            "frenzy_threshold": 0.4
          }
        ]
      },
      {
        "id": "tumor_set_full",
        "name": "不死之身",
        "count": 9,
        "effects": [
          {
            "immortal_duration": 1200,
            "trigger_health": 0,
            "during_immortal": {
              "damage_multiplier": 2.0,
              "speed_bonus": 1.0,
              "invulnerable": false
            }
          }
        ]
      }
    ]
  }
}
```

---

## 搭配潜力分析

### 相邻搭配设计

| 搭配ID | 名称 | 参与器官 | 效果描述 |
|--------|------|----------|----------|
| tumor_lung_stomach | 毒素扩散 | 肿瘤肺脏+肿瘤胃 | 毒素云范围+50%，中毒持续时间+50% |
| tumor_stomach_intestine | 完美代谢 | 肿瘤胃+肿瘤肠子 | 食物效果+100%，饥饿惩罚-25% |
| tumor_intestine_liver | 变异循环 | 肿瘤肠子+肿瘤肝脏 | 代谢状态持续时间+50%，药水效果+25% |
| tumor_kidney_spleen | 免疫系统 | 肿瘤肾脏+肿瘤脾脏 | 负面效果持续时间-30%，转化概率+10% |
| tumor_spleen_liver | 免疫网络 | 肿瘤脾脏+肿瘤肝脏 | 可记录3个免疫效果，使用药水不消耗毒素层数 |
| tumor_liver_kidney | 净化系统 | 肿瘤肝脏+肿瘤肾脏 | 毒素和废物层数自然衰减速度翻倍 |
| tumor_heart_muscle | 狂暴战士 | 肿瘤心脏+肿瘤肌肉 | 狂暴阈值提升至50%，狂暴期间伤害+30%，肌肉痉挛概率-10% |

### 套装搭配设计

| 搭配ID | 名称 | 需求数量 | 效果描述 |
|--------|------|----------|----------|
| tumor_set_2 | 恶性生长 | 2个肿瘤器官 | -50%生命恢复，+30%最大生命值 |
| tumor_set_4 | 狂暴突变 | 4个肿瘤器官 | +50%所有伤害，持续失血（每秒1点） |
| tumor_set_6 | 肿瘤军团 | 6个肿瘤器官 | 所有属性+20%，生命恢复-30%，狂暴阈值40% |
| tumor_set_full | 不死之身 | 9个肿瘤器官（全套） | 生命值归零时进入"不死状态"60秒，期间伤害+100%，速度+100%，但仍然可以受到伤害 |

### 跨系搭配潜力

#### 与九狱器官搭配

**死亡拥抱** (death_embrace)
- **组合**: 肿瘤器官 + 九狱器官
- **效果**: 血量越低，伤害越高（最高+100%）
- **设计思路**: 肿瘤的"狂暴"与九狱的"罪恶"形成完美的自杀式攻击风格

#### 与九头蛇器官搭配

**不死军团** (undead_legion)
- **组合**: 肿瘤器官 + 九头蛇器官
- **效果**: 获得"多条生命"，每次死亡后重生（最多3次），但每次重生后失去1个肿瘤器官
- **设计思路**: 肿瘤的"增殖"与九头蛇的"再生"形成永动机

#### 与弗兰肯斯坦器官搭配

**科学怪人2.0** (frankenstein_tumor)
- **组合**: 肿瘤器官 + 弗兰肯斯坦器官
- **效果**: 器官效果+50%，但每10秒有10%概率发生"排斥反应"（失去所有器官效果5秒）
- **设计思路**: 肿瘤的"变异"与弗兰肯斯坦的"拼凑"形成疯狂科学家的完美实验

#### 与幻想种器官搭配

**现实扭曲** (reality_tumor)
- **组合**: 肿瘤器官 + 幻想种器官
- **效果**: 30%概率将任何伤害转化为治疗（等量），30%概率将任何治疗转化为伤害（等量）
- **设计思路**: 肿瘤的"变异"与幻想种的"扭曲"形成完全的随机性

---

## 后续扩展方向

### T2: 器官共生系统

为肿瘤器官添加"共生"机制，允许两个相邻的肿瘤器官融合成更强的器官：
- **心脏+肺脏**: 增殖核心（提供持续的生命恢复和伤害）
- **肝脏+肾脏**: 净化系统（免疫所有毒素和负面效果）
- **肌肉+胃**: 力量源泉（无限体力，但饥饿值消耗+300%）

### T3: 器官进化系统

为肿瘤器官添加"进化"机制，通过特殊条件触发：
- **条件**: 装备6个以上肿瘤器官，击败特定BOSS
- **效果**: 所有肿瘤器官进化为"变异肿瘤器官"，属性翻倍，副作用减半

### T4: 肿瘤Boss战

设计专属的肿瘤BOSS，掉落特殊的肿瘤器官：
- **增殖女王**: 掉落"女王肿瘤心脏"
- **变异领主**: 掉落"领主肿瘤肌肉"
- **不朽守望者**: 掉落"守望肿瘤阑尾"

---

## 平衡性考虑

### 优点

1. **高收益**: 属性明显高于同类器官
2. **独特性**: 每个器官都有独特的机制
3. **可扩展**: 为后续系统预留了空间
4. **策略性**: 玩家需要权衡收益和代价

### 潜在问题

1. **过强**: 阑尾的随机觉醒可能过于强力
2. **过弱**: 肠子的随机状态可能过于不稳定
3. **复杂**: 多个叠加的机制可能难以理解
4. **RNG过重**: 过多的随机性可能导致挫败感

### 平衡调整建议

1. **阑尾觉醒**: 降低概率到3%，或者让觉醒效果可预测
2. **肠子状态**: 允许玩家通过特定物品影响状态转换
3. **肝脏毒素**: 增加清除毒素的方法（如特殊物品）
4. **整体平衡**: 增加套装效果的代价，避免纯收益

---

## 风格一致性检查

### 与现有器官的一致性

- **属性范围**: 肿瘤器官的属性范围与现有器官一致（1-4点）
- **效果强度**: 独特效果的强度与现有器官的特殊效果相当
- **技术实现**: 所有效果都可以通过现有API实现
- **主题性**: 完全符合Chest Cavity Beyond的黑暗奇幻风格

### 与搭配系统的一致性

- **相邻搭配**: 符合v2.0搭配系统的设计原则
- **套装搭配**: 效果强度分级合理（2件→4件→6件→全套）
- **跨系搭配**: 与其他器官类型的搭配有趣且平衡
- **性能影响**: 所有效果都可以通过缓存和优化实现

---

## 总结

### 设计成果

本设计文档提供了肿瘤器官的完整基础属性系统，包括：

1. ✅ **9个肿瘤器官的完整属性**: 每个器官都有基础属性和独特效果
2. ✅ **获取难度系统**: 5级难度分级，从普通到传说
3. ✅ **技术实现建议**: 详细的数据结构、事件处理和代码示例
4. ✅ **JSON配置示例**: 完整的配置文件，可直接用于游戏
5. ✅ **搭配潜力分析**: 7个相邻搭配，4个套装搭配，4个跨系搭配
6. ✅ **后续扩展方向**: 为T2、T3、T4系统预留了空间

### 核心优势

1. **主题鲜明**: 围绕"风险与收益并存"的设计理念
2. **深度丰富**: 每个器官都有独特的机制和玩法
3. **平衡可行**: 既有高收益，也有相应的代价
4. **扩展性强**: 为后续系统预留了充足的空间

### 待审核问题

1. **阑尾的随机性**: 5%觉醒概率是否合理？
2. **肝脏的毒素积累**: 毒素层的效果是否平衡？
3. **肌肉的肌肉痉挛**: 20%概率是否太高？
4. **套装效果的代价**: 6件和9件的代价是否足够？

---

**文档结束**

**下一步**: 等待审核反馈，根据审核结果进行调整和优化。

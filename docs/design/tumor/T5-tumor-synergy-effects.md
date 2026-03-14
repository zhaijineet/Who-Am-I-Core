# 肿瘤搭配效果体系设计文档 T5

**设计版本**: v1.0
**创建日期**: 2026-03-15
**设计者**: Creative Content Designer
**状态**: 待审核
**基于**: T1、T2、T3、器官搭配系统v2.0

---

## 目录

1. [设计概述](#设计概述)
2. [相邻搭配系统](#相邻搭配系统)
3. [套装搭配系统](#套装搭配系统)
4. [槽位搭配系统](#槽位搭配系统)
5. [主题搭配系统](#主题搭配系统)
6. [跨系搭配系统](#跨系搭配系统)
7. [隐藏搭配系统](#隐藏搭配系统)
8. [搭配强度分级](#搭配强度分级)
9. [UI与反馈设计](#ui与反馈设计)
10. [技术实现方案](#技术实现方案)
11. [JSON配置示例](#json配置示例)
12. [搭配速查表](#搭配速查表)

---

## 设计概述

### 核心设计理念

肿瘤搭配效果体系基于T1-T3的设计成果，打造一个**高风险高回报、策略深度、玩法多样**的搭配系统。肿瘤搭配的核心特色是：

1. **不稳定的力量** - 强大的效果伴随风险和代价
2. **增殖协同** - 与T3增殖机制深度整合
3. **类型协同** - 充分利用T2分类系统的差异
4. **跨系互动** - 与其他器官类型创造独特体验
5. **探索惊喜** - 隐藏搭配提供意外发现

### 设计原则

基于器官搭配系统v2.0的核心原则，肿瘤搭配特有原则：

1. **风险收益平衡** - 强力搭配伴随明显代价
2. **增殖导向** - 鼓励玩家使用T3增殖机制
3. **类型差异** - 良性/恶性/特殊肿瘤有不同的搭配风格
4. **层次分明** - 从入门到传说，难度和效果分级明确
5. **可玩性优先** - 避免过于复杂的机制，保持游戏性

### 搭配系统架构

```
肿瘤搭配体系
├── 相邻搭配 (10个)
│   ├── 良性-良性相邻
│   ├── 恶性-恶性相邻
│   ├── 良性-恶性相邻
│   └── 特殊器官相邻
├── 套装搭配 (12个)
│   ├── 数量套装 (2/4/6/9件)
│   ├── 类型套装 (良性/恶性/特殊)
│   └── 混合套装 (跨类型)
├── 槽位搭配 (5个)
│   ├── 核心槽位 (槽位13)
│   ├── 边缘槽位 (槽位0-8/18-26)
│   └── 特殊排列
├── 主题搭配 (8个)
│   ├── 增殖主题
│   ├── 变异主题
│   ├── 毒素主题
│   └── 狂暴主题
├── 跨系搭配 (10个)
│   ├── 肿瘤×九狱
│   ├── 肿瘤×九头蛇
│   ├── 肿瘤×弗兰肯斯坦
│   └── 其他跨系组合
└── 隐藏搭配 (8个)
    ├── 彩蛋搭配
    ├── 特殊条件搭配
    └── 终极挑战搭配
```

---

## 相邻搭配系统

相邻搭配基于胸腔网格的相邻关系，提供中等强度的增益，是肿瘤搭配系统的基础。

### 良性-良性相邻搭配

#### 1. 营养循环 (Nutrient Cycle) ★☆☆☆☆

**参与器官**: 肿瘤胃 + 肿瘤肠子

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- 食物提供的饱和度+30%
- 饥饿值消耗速度-20%
- "营养转化"的正面效果概率提升至65%

**设计思路**: 两个良性消化器官形成完美的营养循环，降低副作用的同时提升收益

**强度分级**: 入门级
**获取难度**: ★☆☆☆☆ (两个器官都较易获取)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_STOMACH, TUMOR_INTESTINE, data)) {
    // 饥饿消耗修正
    applyHungerMultiplier(player, 0.8f);
    // 饱和度修正
    applySaturationMultiplier(player, 1.3f);
    // 营养转化概率提升
    TumorData tumorData = getTumorData(player);
    tumorData.setNutrientTransformProbability(0.65f);
}
```

#### 2. 呼吸净化 (Respiratory Purification) ★★☆☆☆

**参与器官**: 肿瘤肺脏 + 肿瘤脾脏

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- 毒素云伤害提升至3点/秒
- 毒素云范围扩大至4格
- "变异免疫"的转化概率提升至30%
- "变异免疫"的"脆弱防线"概率降低至5%

**设计思路**: 肺脏的毒素输出与脾脏的免疫转化形成互补，打造以毒素为核心的防御体系

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆ (肺脏较易，脾脏中等)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_LUNG, TUMOR_SPLEEN, data)) {
    // 毒素云增强
    TumorData tumorData = getTumorData(player);
    tumorData.setToxicCloudDamage(3.0f);
    tumorData.setToxicCloudRadius(4.0f);
    // 免疫转化概率提升
    tumorData.setImmuneTransformProbability(0.30f);
    // 脆弱防线概率降低
    tumorData.setFragileDefenseProbability(0.05f);
}
```

### 恶性-恶性肿瘤相邻搭配

#### 3. 增殖核心 (Proliferation Core) ★★★☆☆

**参与器官**: 肿瘤心脏 + 肿瘤肝脏

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "增殖之心"的效果从+5%/器官提升至+7%/器官
- "腐化代谢"的药水持续时间+75%（原+50%）
- "毒素积累"的阈值放宽：6+层才开始持续失血（原4层）
- 每分钟清除毒素的速率提升：每分钟清除2层（原1层）

**设计思路**: 心脏和肝脏是肿瘤系统的核心，相邻时形成增殖核心，大幅增强层数系统的可玩性

**强度分级**: 专家级
**获取难度**: ★★★☆☆ (心脏困难，肝脏极难)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_HEART, TUMOR_LIVER, data)) {
    TumorData tumorData = getTumorData(player);
    // 增殖之心增强
    tumorData.setProliferationBonusPerOrgan(0.07f); // 原0.05f
    // 腐化代谢增强
    tumorData.setPotionDurationBonus(0.75f); // 原0.50f
    // 毒素积累阈值放宽
    tumorData.setToxinBleedThreshold(6); // 原4
    // 清除速率提升
    tumorData.setToxinDecayRate(2); // 原1
}
```

#### 4. 狂暴战士 (Berserk Warrior) ★★★★☆

**参与器官**: 肿瘤心脏 + 肿瘤肌肉

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "狂暴触发"的阈值从30%提升至50%
- "肾上腺素"的阈值从50%提升至60%
- "肌肉痉挛"的概率从20%降低至10%
- "肾上腺素"激活时，"恶性跳动"停止触发

**设计思路**: 心脏和肌肉的狂暴机制互相强化，形成低血量无敌的狂战士Build

**强度分级**: 大师级
**获取难度**: ★★★★☆ (心脏困难，肌肉极难)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_HEART, TUMOR_MUSCLE, data)) {
    TumorData tumorData = getTumorData(player);
    // 狂暴阈值提升
    tumorData.setFrenzyThreshold(0.50f); // 原0.30f
    tumorData.setAdrenalineThreshold(0.60f); // 原0.50f
    // 肌肉痉挛概率降低
    tumorData.setSpasmProbability(0.10f); // 原0.20f
    // 肾上腺素时停止恶性跳动
    tumorData.setStopMalignantBeatDuringAdrenaline(true);
}
```

#### 5. 过滤网络 (Filtration Network) ★★★☆☆

**参与器官**: 肿瘤肝脏 + 肿瘤肾脏

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "毒素积累"和"废物堆积"共用层数上限，上限提升至15层（原各自10层）
- 毒素/废物的效果增强：
  - 2-3层：伤害+15%，生命恢复-3%（原+10%/-5%）
  - 4-5层：伤害+25%，生命恢复-7%（原+20%/-10%）
  - 6-9层：伤害+35%，生命恢复-10%（原+30%/-15%）
  - 10-15层：伤害+50%，生命恢复-15%，每秒失血1点（新增层级）
- 每分钟清除毒素/废物的速率提升：每分钟清除3层（原肝脏1层，肾脏无清除）

**设计思路**: 肝脏和肾脏的层数系统合并，形成高风险高回报的叠加系统

**强度分级**: 专家级
**获取难度**: ★★★★☆ (两者都是极难获取)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_LIVER, TUMOR_KIDNEY, data)) {
    TumorData tumorData = getTumorData(player);
    // 合并层数系统
    tumorData.setCombinedStackLimit(15); // 原各自10层
    // 清除速率提升
    tumorData.setCombinedDecayRate(3); // 原1层
    // 层数效果增强（通过重新定义层数效果映射）
    tumorData.setEnhancedStackEffects(true);
}
```

### 良性-恶性肿瘤相邻搭配

#### 6. 稳定爆发 (Stable Burst) ★★☆☆☆

**参与器官**: 肿瘤肾脏 + 肿瘤脾脏

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "过滤超载"的触发概率从30%提升至40%
- "过滤超载"的增益持续从5秒延长至8秒
- "过滤超载"的虚弱持续从10秒缩短至5秒
- "变异免疫"在"过滤超载"增益期间，转化概率提升至50%

**设计思路**: 脾脏的稳定性缓解肾脏的随机性，形成可预测的爆发系统

**强度分级**: 进阶级
**获取难度**: ★★★☆☆ (肾脏困难，脾脏中等)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_KIDNEY, TUMOR_SPLEEN, data)) {
    TumorData tumorData = getTumorData(player);
    // 过滤超载增强
    tumorData.setFiltrationOverloadProbability(0.40f); // 原0.30f
    tumorData.setBuffDuration(8 * 20); // 原5*20 tick
    tumorData.setDebuffDuration(5 * 20); // 原10*20 tick
    // 免疫转化提升
    tumorData.setEnhancedTransformDuringOverload(true);
    tumorData.setEnhancedTransformProbability(0.50f);
}
```

### 特殊器官相邻搭配

#### 7. 变异循环 (Mutation Cycle) ★★★★☆

**参与器官**: 肿瘤肠子 + 肿瘤阑尾

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "变异代谢"的状态切换周期从30秒缩短至20秒
- "变异代谢"增加两种新状态：
  - **觉醒状态**: 所有属性+50%，持续20秒（稀有，5%概率）
  - **崩溃状态**: 所有属性-30%，持续20秒（稀有，5%概率）
- "随机觉醒"的触发概率从5%提升至10%
- "阑尾炎"的持续时间减半

**设计思路**: 肠子的状态循环与阑尾的随机觉醒形成完美的变异循环系统

**强度分级**: 大师级
**获取难度**: ★★★★★ (肠子罕见，阑尾传说)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_INTESTINE, TUMOR_APPENDIX, data)) {
    TumorData tumorData = getTumorData(player);
    // 状态切换加速
    tumorData.setMetabolismSwitchInterval(20 * 20); // 原30*20
    // 添加新状态
    tumorData.setEnableAwakenedState(true);
    tumorData.setAwakenedStateProbability(0.05f);
    tumorData.setEnableCollapsedState(true);
    tumorData.setCollapsedStateProbability(0.05f);
    // 阑尾觉醒概率提升
    tumorData.setAppendixAwakenProbability(0.10f); // 原0.05f
    // 阑尾炎持续时间减半
    tumorData.setAppendicitisDurationMultiplier(0.5f);
}
```

#### 8. 适应增殖 (Adaptive Proliferation) ★★★☆☆

**参与器官**: 肿瘤阑尾 + 肿瘤心脏

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "增殖之心"的效果从+5%/器官提升至+8%/器官
- "随机觉醒"如果触发"完美适应"效果，持续时间从5分钟延长至10分钟
- "随机觉醒"如果触发"致命缺陷"效果，持续时间从3分钟缩短至1分钟
- 心脏的"恶性跳动"有50%概率不触发

**设计思路**: 阑尾的随机性增强心脏的增殖系统，形成高收益的赌博式搭配

**强度分级**: 专家级
**获取难度**: ★★★★★ (阑尾传说，心脏困难)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_APPENDIX, TUMOR_HEART, data)) {
    TumorData tumorData = getTumorData(player);
    // 增殖之心大幅增强
    tumorData.setProliferationBonusPerOrgan(0.08f); // 原0.05f
    // 完美适应持续时间延长
    tumorData.setPerfectAdaptationDuration(10 * 60 * 20); // 原5*60*20
    // 致命缺陷持续时间缩短
    tumorData.setFatalFlawDuration(1 * 60 * 20); // 原3*60*20
    // 恶性跳动概率降低
    tumorData.setMalignantBeatProbability(0.5f); // 原1.0f
}
```

### 功能性相邻搭配

#### 9. 毒素扩散 (Toxin Spread) ★★☆☆☆

**参与器官**: 肿瘤肺脏 + 肿瘤胃

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- 毒素云范围+50%（从3格提升至4.5格）
- 毒素云伤害+50%（从2点/秒提升至3点/秒）
- "腐化消化"的负面效果（中毒、虚弱）概率降低至20%（原33%）

**设计思路**: 肺脏的毒素输出与胃的消化系统结合，形成以毒素为核心的攻击性搭配

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆ (两者都较易获取)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_LUNG, TUMOR_STOMACH, data)) {
    TumorData tumorData = getTumorData(player);
    // 毒素云增强
    tumorData.setToxicCloudRadius(4.5f); // 原3.0f
    tumorData.setToxicCloudDamage(3.0f); // 原2.0f
    // 腐化消化负面概率降低
    tumorData.setCorruptedDigestionNegativeProbability(0.20f); // 原0.33f
}
```

#### 10. 代谢加速 (Metabolism Boost) ★★☆☆☆

**参与器官**: 肿瘤肠子 + 肿瘤胃

**触发条件**: 两个器官在胸腔中相邻

**效果描述**:
- "无尽饥饿"的饥饿值消耗加速效果-50%（从+100%降低至+50%）
- "养分吸收"的生命恢复+50%（从+30%提升至+45%）
- "腐化消化"的正面效果持续时间从30秒延长至60秒

**设计思路**: 肠子和胃形成完整的消化系统，大幅降低饥饿代价的同时提升恢复能力

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆ (胃容易，肠子罕见)

**技术实现**:
```java
// 相邻检测
if (areAdjacent(TUMOR_INTESTINE, TUMOR_STOMACH, data)) {
    TumorData tumorData = getTumorData(player);
    // 无尽饥饿减轻
    tumorData.setEndlessHungerMultiplier(1.5f); // 原2.0f
    // 养分吸收增强
    tumorData.setNutrientAbsorptionBonus(0.45f); // 原0.30f
    // 腐化消化正面效果延长
    tumorData.setCorruptedDigestionBuffDuration(60 * 20); // 原30*20
}
```

---

## 套装搭配系统

套装搭配基于装备肿瘤器官的数量和类型组合，提供从基础到终极的分层效果。

### 数量套装搭配

#### 1. 恶性生长 (Malignant Growth) ★☆☆☆☆

**需求数量**: 2个任意肿瘤器官

**效果描述**:
- 生命恢复速度-50%
- 最大生命值+30%
- 获得小量"肿瘤气息"粒子效果

**设计思路**: 入门级套装，让玩家初步体验肿瘤的高收益高代价特性

**强度分级**: 入门级
**获取难度**: ★☆☆☆☆

**技术实现**:
```java
// 套装检测
if (getTotalTumorCount(data) >= 2) {
    // 生命恢复降低
    applyRegenMultiplier(player, 0.5f);
    // 最大生命值提升
    applyHealthMultiplier(player, 1.3f);
    // 粒子效果
    spawnTumorAuraParticles(player);
}
```

#### 2. 狂暴突变 (Frenzied Mutation) ★★☆☆☆

**需求数量**: 4个任意肿瘤器官

**效果描述**:
- 继承"恶性生长"的所有效果
- 所有伤害+50%
- 每秒失去1点生命（持续失血）
- 攻击时有10%概率触发"小规模变异爆发"（周围3格敌人受到5点毒素伤害）

**设计思路**: 进阶级套装，引入持续失血的代价，但大幅提升伤害输出

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆

**技术实现**:
```java
// 套装检测（需要同时满足2件和4件，只应用4件效果）
if (getTotalTumorCount(data) >= 4) {
    // 继承2件效果
    applySetBonus(player, data, "malignant_growth");
    // 新增效果
    applyDamageMultiplier(player, 1.5f);
    // 持续失血
    data.addTask(new DoTPulseTask(player, 1.0f)); // 每秒1点
    // 变异爆发
    data.addTask(new MutationBurstTask(player, 0.10f)); // 10%概率
}
```

#### 3. 肿瘤军团 (Tumor Legion) ★★★☆☆

**需求数量**: 6个任意肿瘤器官

**效果描述**:
- 继承"狂暴突变"的所有效果
- 所有属性（HEALTH、STRENGTH、SPEED）+20%
- 生命恢复-30%（与恶性生长的-50%叠加，总计-80%）
- 狂暴阈值从30%提升至40%（如果装备肿瘤心脏）
- 每击杀10个敌人，触发一次"肿瘤增殖"（T3机制，随机增殖1个肿瘤器官）

**设计思路**: 专家级套装，引入T3增殖机制，鼓励玩家积极战斗

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 套装检测
if (getTotalTumorCount(data) >= 6) {
    // 继承4件效果
    applySetBonus(player, data, "frenzied_mutation");
    // 新增效果
    applyAllAttributesMultiplier(player, 1.2f);
    applyRegenMultiplier(player, 0.7f); // 额外-30%
    // 狂暴阈值提升（如果有心脏）
    if (data.hasOrgan(TUMOR_HEART)) {
        TumorData tumorData = getTumorData(player);
        tumorData.setFrenzyThreshold(0.40f);
    }
    // 击杀增殖
    data.addTask(new KillCountProliferationTask(player, 10));
}
```

#### 4. 不死之身 (Undying) ★★★★★

**需求数量**: 9个肿瘤器官（全套）

**效果描述**:
- 继承"肿瘤军团"的所有效果
- 当生命值降至0时，不立即死亡，而是进入"不死状态"：
  - 持续时间：60秒
  - 期间无法被治疗，但生命值锁定在1点
  - 所有伤害+100%
  - 移动速度+50%
  - 不死状态结束时，如果生命值仍为0，则真正死亡
  - 冷却时间：5分钟
- 获得持续的"肿瘤君主"粒子效果和光环

**设计思路**: 终极套装，提供改变游戏的"第二条命"能力，但冷却时间限制滥用

**强度分级**: 传说级
**获取难度**: ★★★★★

**技术实现**:
```java
// 套装检测
if (getTotalTumorCount(data) >= 9) {
    // 继承6件效果
    applySetBonus(player, data, "tumor_legion");
    // 不死之身能力
    data.addCapability(new UndyingCapability(player));
    // 粒子效果
    spawnTumorMonarchAura(player);
}

public class UndyingCapability {
    private final Player player;
    private long lastTriggerTime;
    private static final long COOLDOWN = 5 * 60 * 1000; // 5分钟

    public void onDeath(LivingEntity owner, DamageSource source) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTriggerTime < COOLDOWN) {
            return; // 冷却中
        }

        // 触发不死之身
        owner.setHealth(1.0f);
        applyUndyingBuffs(owner);

        // 60秒后检查
        scheduleTask(() -> {
            if (owner.getHealth() <= 1.0f) {
                owner.hurt(source, Float.MAX_VALUE); // 真正死亡
            } else {
                removeUndyingBuffs(owner);
            }
        }, 60 * 1000);

        lastTriggerTime = currentTime;
    }
}
```

### 类型套装搭配

#### 5. 适应共生 (Adaptive Symbiosis) ★★☆☆☆

**需求**: 3个良性肿瘤器官

**效果描述**:
- 所有良性肿瘤的副作用持续时间-25%
- 每10秒恢复5%最大生命值
- 每分钟积累1层"适应"（最多5层），每层提供+5%所有属性

**设计思路**: 良性肿瘤套餐，强化稳定性和恢复能力

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆

**技术实现**:
```java
// 套装检测
if (getBenignTumorCount(data) >= 3) {
    // 副作用减少
    applySideEffectReduction(player, TumorType.BENIGN, 0.25f);
    // 持续恢复
    data.addTask(new PeriodicHealTask(player, 0.05f, 10 * 20)); // 每10秒5%
    // 适应层数
    data.addTask(new AdaptationStackTask(player, 5, 0.05f, 60 * 20)); // 最多5层，每层+5%，每分钟1层
}
```

#### 6. 侵蚀之主 (Lord of Erosion) ★★★★☆

**需求**: 4个恶性肿瘤器官

**效果描述**:
- 所有恶性肿瘤的随机副作用概率-5%，但收益+10%
- 获得"侵蚀光环"：每造成100点伤害，积累1层"侵蚀"（最多10层）
  - 每层提供+3%所有伤害
  - 但每秒失去0.5点生命×层数
- 侵蚀层数会逐渐衰减（每5秒失去1层），脱离战斗后衰减加速

**设计思路**: 恶性肿瘤套餐，强化主动战斗和风险管理

**强度分级**: 大师级
**获取难度**: ★★★★☆

**技术实现**:
```java
// 套装检测
if (getMalignantTumorCount(data) >= 4) {
    // 副作用减少，收益增加
    applySideEffectReduction(player, TumorType.MALIGNANT, 0.05f);
    applyBonusIncrease(player, TumorType.MALIGNANT, 0.10f);
    // 侵蚀光环
    data.addTask(new ErosionAuraTask(player, 10, 0.03f, 0.5f));
}

public class ErosionAuraTask implements IChestCavityTask {
    private int erosionStacks = 0;
    private float totalDamageDealt = 0.0f;

    @Override
    public void onDamageDealt(Player player, float damage) {
        totalDamageDealt += damage;
        int newStacks = (int)(totalDamageDealt / 100.0f);
        if (newStacks > erosionStacks) {
            erosionStacks = Math.min(newStacks, maxStacks);
            totalDamageDealt = 0.0f;
            spawnErosionStackParticles(player, erosionStacks);
        }
    }

    @Override
    public void tick(LivingEntity owner) {
        // 应用伤害加成
        float damageBonus = erosionStacks * 0.03f;
        applyDamageMultiplier(owner, 1.0f + damageBonus);

        // 持续失血
        if (erosionStacks > 0) {
            float damage = erosionStacks * 0.5f;
            owner.hurt(owner.damageSources().magic(), damage);
        }

        // 层数衰减（每5秒）
        if (owner.tickCount % (5 * 20) == 0 && erosionStacks > 0) {
            erosionStacks--;
        }
    }
}
```

#### 7. 突变共鸣 (Mutation Resonance) ★★★★☆

**需求**: 2个特殊肿瘤器官（阑尾、肠子）

**效果描述**:
- 特殊肿瘤的触发概率/切换速度提升10%
- 可以查看下一次的结果：
  - 阑尾：可以预览下次觉醒的结果（但仍然只有5%概率觉醒）
  - 肠子：可以预览下一个代谢状态
- 如果同时装备阑尾和肠子，且肠子进入"觉醒状态"，则强制触发阑尾觉醒（100%概率）

**设计思路**: 特殊肿瘤套餐，降低随机性挫败感，提供预测能力

**强度分级**: 大师级
**获取难度**: ★★★★★

**技术实现**:
```java
// 套装检测
if (getSpecialTumorCount(data) >= 2) {
    // 触发提升
    applySpecialTriggerBoost(player, 1.10f); // +10%
    // 预测能力
    TumorData tumorData = getTumorData(player);
    tumorData.setCanPredictSpecial(true);

    // 阑尾+肠子特殊互动
    if (data.hasOrgan(TUMOR_APPENDIX) && data.hasOrgan(TUMOR_INTESTINE)) {
        tumorData.setForceAppendixAwakenOnIntestineAwakened(true);
    }
}
```

### 混合类型套装搭配

#### 8. 平衡共生 (Balanced Symbiosis) ★★★☆☆

**需求**: 2个良性 + 2个恶性肿瘤器官

**效果描述**:
- 获得一个新状态："平衡态"
  - 平衡态时，所有良性肿瘤的副作用-50%，恶性肿瘤的收益+15%
  - 平衡态持续30秒，冷却60秒
  - 可以手动激活（右键胸腔）
- 平衡态激活时，获得粒子光环效果

**设计思路**: 混合套餐，结合良性的稳定性和恶性的高收益

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 套装检测
if (getBenignTumorCount(data) >= 2 && getMalignantTumorCount(data) >= 2) {
    data.addCapability(new BalancedSymbiosisCapability(player));
}

public class BalancedSymbiosisCapability {
    private boolean isActive = false;
    private long lastActivateTime;
    private static final long COOLDOWN = 60 * 1000; // 60秒
    private static final long DURATION = 30 * 1000; // 30秒

    public void onRightClick(Player player) {
        long currentTime = System.currentTimeMillis();
        if (isActive || currentTime - lastActivateTime < COOLDOWN) {
            return; // 激活中或冷却中
        }

        // 激活平衡态
        isActive = true;
        applyBalancedStateEffects(player);
        spawnBalancedStateParticles(player);

        // 30秒后结束
        scheduleTask(() -> {
            isActive = false;
            removeBalancedStateEffects(player);
            lastActivateTime = System.currentTimeMillis();
        }, DURATION);
    }
}
```

---

## 槽位搭配系统

槽位搭配基于器官在胸腔网格中的位置，提供精确的条件触发效果。

### 核心槽位搭配

#### 1. 核心守护 (Core Guardian) ★★★☆☆

**需求**: 肿瘤心脏放置在槽位13（胸腔正中心）

**效果描述**:
- "增殖之心"的效果从+5%/器官提升至+10%/器官
- "恶性跳动"的触发概率从20%降低至10%
- 心脏的脉动范围扩大至整个胸腔，所有相邻器官获得+10%效果
- 胸腔UI显示特殊的核心脉动效果

**设计思路**: 将心脏放置在核心位置获得额外增强，鼓励策略性放置

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 槽位检测
if (data.getStackInSlot(13).is(TUMOR_HEART)) {
    TumorData tumorData = getTumorData(player);
    // 增殖之心大幅增强
    tumorData.setProliferationBonusPerOrgan(0.10f); // 原0.05f
    // 恶性跳动概率降低
    tumorData.setMalignantBeatProbability(0.10f); // 原0.20f
    // 相邻器官增强
    for (int slot : getAdjacentSlots(13)) {
        ItemStack adjacent = data.getStackInSlot(slot);
        if (!adjacent.isEmpty()) {
            boostOrganEffect(player, adjacent, 1.10f);
        }
    }
    // 特殊UI效果
    renderCorePulseEffect(player);
}
```

### 边缘槽位搭配

#### 2. 边缘侵蚀 (Edge Erosion) ★★☆☆☆

**需求**: 3个以上肿瘤器官放置在边缘槽位（槽位0-8或18-26）

**效果描述**:
- 所有肿瘤器官的副作用概率-10%
- 但所有肿瘤器官的收益-5%
- 获得特殊的"边缘侵蚀"粒子效果（粒子从胸腔边缘向中心扩散）

**设计思路**: 利用边缘位置降低风险，但同时也降低收益

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆

**技术实现**:
```java
// 槽位检测
int edgeCount = 0;
for (int slot : Arrays.asList(0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26)) {
    if (!data.getStackInSlot(slot).isEmpty() && isTumorOrgan(data.getStackInSlot(slot))) {
        edgeCount++;
    }
}

if (edgeCount >= 3) {
    // 副作用降低
    applySideEffectReduction(player, TumorType.ANY, 0.10f);
    // 收益降低
    applyBonusReduction(player, TumorType.ANY, 0.05f);
    // 粒子效果
    spawnEdgeErosionParticles(player);
}
```

### 特殊排列搭配

#### 3. 楔形阵型 (Wedge Formation) ★★★☆☆

**需求**: 肿瘤器官以楔形（三角形）排列在胸腔中

**说明**: 楔形定义为3个器官形成L形（如槽位4+5+13或槽位4+13+14）

**效果描述**:
- 楔形顶点的器官效果+30%
- 楔形底边的两个器官互相视为相邻，即使实际不相邻
- 楔形内部的槽位如果放置肿瘤器官，额外获得+15%效果

**设计思路**: 鼓励玩家创造特定的几何图案

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 楔形检测
List<int[]> wedgePatterns = Arrays.asList(
    new int[]{4, 5, 13},
    new int[]{4, 13, 14},
    new int[]{13, 14, 22},
    new int[]{13, 22, 21}
    // ... 其他可能的楔形模式
);

for (int[] pattern : wedgePatterns) {
    if (isWedgeFormed(data, pattern)) {
        // 检测到楔形
        int apexSlot = pattern[1]; // 中间槽位为顶点
        int base1 = pattern[0];
        int base2 = pattern[2];

        // 顶点+30%
        boostOrganEffect(player, data.getStackInSlot(apexSlot), 1.30f);

        // 底边互相视为相邻
        forceAdjacent(data, base1, base2);

        // 内部槽位+15%
        for (int slot : getInternalSlots(pattern)) {
            if (isTumorOrgan(data.getStackInSlot(slot))) {
                boostOrganEffect(player, data.getStackInSlot(slot), 1.15f);
            }
        }

        // UI高亮楔形
        highlightWedgeFormation(player, pattern);
        break; // 只触发一个楔形
    }
}
```

#### 4. 对称平衡 (Symmetric Balance) ★★★★☆

**需求**: 肿瘤器官以左右对称的方式排列

**说明**: 对称定义为槽位i的肿瘤器官与槽位(26-i)的肿瘤器官成对

**效果描述**:
- 每对对称的肿瘤器官获得"对称加成"：
  - 良性器官：副作用-15%，收益+5%
  - 恶性器官：副作用概率-10%，收益+10%
  - 特殊器官：触发概率+5%
- 如果完全对称（所有槽位都有对应器官），额外获得：
  - 所有属性+15%
  - 获得特殊的"对称平衡"粒子效果（对称的粒子从两侧向中心汇聚）

**设计思路**: 鼓励玩家创造美观的对称配置

**强度分级**: 大师级
**获取难度**: ★★★★☆

**技术实现**:
```java
// 对称检测
int symmetricPairs = 0;
boolean perfectlySymmetric = true;

for (int i = 0; i < 13; i++) {
    int mirrorSlot = 26 - i;
    ItemStack left = data.getStackInSlot(i);
    ItemStack right = data.getStackInSlot(mirrorSlot);

    boolean leftHasTumor = !left.isEmpty() && isTumorOrgan(left);
    boolean rightHasTumor = !right.isEmpty() && isTumorOrgan(right);

    if (leftHasTumor && rightHasTumor) {
        // 对称对
        symmetricPairs++;
        applySymmetricBonus(player, left, right);
    } else if (leftHasTumor || rightHasTumor) {
        // 不对称
        perfectlySymmetric = false;
    } else {
        // 两侧都空，不算不对称
    }
}

// 应用对称加成
if (symmetricPairs >= 2) {
    if (perfectlySymmetric) {
        // 完全对称额外奖励
        applyAllAttributesMultiplier(player, 1.15f);
        spawnSymmetricBalanceParticles(player);
    }
}
```

---

## 主题搭配系统

主题搭配基于肿瘤的核心机制和主题，创造独特的游戏体验。

### 增殖主题搭配

#### 1. 无限增殖 (Infinite Proliferation) ★★★★★

**需求**: 装备肿瘤心脏 + 肿瘤阑尾 + 触发至少3次T3增殖机制

**效果描述**:
- 获得被动"无限增殖"：
  - 每分钟有5%概率自动增殖1个随机肿瘤器官
  - 增殖不受T3的正常限制（可以超过胸腔槽位限制，会自动移除最老的器官）
  - 每次增殖有1%概率触发"变异爆发"（周围10格敌人受到30点毒素伤害）
- 增殖度系统达到危险期时，获得"增殖失控"状态：
  - 增殖概率提升至15%
  - 但每秒失去2点生命
- 特殊粒子效果：胸腔内不断溢出增殖粒子

**设计思路**: 终极增殖主题，让肿瘤真正"无限增殖"，但代价巨大

**强度分级**: 传说级
**获取难度**: ★★★★★

**技术实现**:
```java
// 主题检测
boolean hasHeart = data.hasOrgan(TUMOR_HEART);
boolean hasAppendix = data.hasOrgan(TUMOR_APPENDIX);
int proliferationCount = getProliferationCount(player); // 从T3获取

if (hasHeart && hasAppendix && proliferationCount >= 3) {
    data.addTask(new InfiniteProliferationTask(player));
}

public class InfiniteProliferationTask implements IChestCavityTask {
    @Override
    public void tick(LivingEntity owner) {
        // 每分钟检测
        if (owner.tickCount % (60 * 20) == 0) {
            float baseProbability = 0.05f; // 5%

            // 检查增殖度
            int proliferationDegree = getProliferationDegree(owner);
            if (proliferationDegree >= 9) { // 危险期
                baseProbability = 0.15f; // 15%
                // 危险期持续失血
                if (owner.tickCount % 20 == 0) {
                    owner.hurt(owner.damageSources().magic(), 2.0f);
                }
            }

            // 触发增殖
            if (Math.random() < baseProbability) {
                triggerProliferation(owner);

                // 1%概率变异爆发
                if (Math.random() < 0.01f) {
                    triggerMutationBurst(owner);
                }
            }
        }
    }
}
```

### 变异主题搭配

#### 2. 完美变异 (Perfect Mutation) ★★★★☆

**需求**: 装备肿瘤肠子 + 肿瘤阑尾 + 肿瘤肝脏，且肠子至少经历过每种状态一次

**效果描述**:
- 获得"完美控制"能力：
  - 可以手动切换肠子的代谢状态（右键胸腔UI）
  - 切换冷却：30秒
- "变异代谢"的状态持续时间延长至60秒（原30秒）
- "随机觉醒"的触发概率提升至15%（原5%）
- 阑尾觉醒时，必定触发"完美适应"效果（不再随机）

**设计思路**: 变异主题的终极控制，让玩家完全掌控随机性

**强度分级**: 大师级
**获取难度**: ★★★★★

**技术实现**:
```java
// 主题检测
boolean hasIntestine = data.hasOrgan(TUMOR_INTESTINE);
boolean hasAppendix = data.hasOrgan(TUMOR_APPENDIX);
boolean hasLiver = data.hasOrgan(TUMOR_LIVER);
Set<MetabolismState> experiencedStates = getExperiencedStates(player);

if (hasIntestine && hasAppendix && hasLiver &&
    experiencedStates.size() >= 4) { // 经历过所有4种状态
    data.addCapability(new PerfectMutationCapability(player));
}

public class PerfectMutationCapability {
    private long lastSwitchTime;
    private static final long COOLDOWN = 30 * 1000; // 30秒

    public void onRightClick(Player player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSwitchTime < COOLDOWN) {
            return; // 冷却中
        }

        // 手动切换状态
        TumorData tumorData = getTumorData(player);
        MetabolismState currentState = tumorData.getMetabolismState();
        MetabolismState nextState = currentState.next(); // 循环到下一个
        tumorData.setMetabolismState(nextState);

        // 延长持续时间
        tumorData.setMetabolismTimer(60 * 20); // 60秒

        // 粒子效果
        spawnStateSwitchParticles(player, nextState);

        lastSwitchTime = currentTime;
    }
}
```

### 毒素主题搭配

#### 3. 毒素君主 (Toxin Sovereign) ★★★☆☆

**需求**: 装备肿瘤肺脏 + 肿瘤胃 + 肿瘤肝脏，且肝脏毒素层数至少达到过5层

**效果描述**:
- 获得被动"毒素主宰"：
  - 毒素云范围扩大至6格（原3-4.5格）
  - 毒素云伤害提升至4点/秒（原2-3点/秒）
  - 毒素云施加"中毒II"效果（持续5秒）
- "毒素积累"层数效果增强：
  - 2-3层：伤害+15%，毒素云范围+1格
  - 4-5层：伤害+25%，毒素云范围+2格
  - 6+层：伤害+40%，毒素云范围+3格，所有敌人受到"剧毒"效果（持续失去生命）
- 获得特殊的"毒素君主"光环效果

**设计思路**: 毒素主题的终极强化，让毒素成为主要的伤害输出

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 主题检测
boolean hasLung = data.hasOrgan(TUMOR_LUNG);
boolean hasStomach = data.hasOrgan(TUMOR_STOMACH);
boolean hasLiver = data.hasOrgan(TUMOR_LIVER);
int maxToxinLayers = getMaxToxinLayers(player); // 从T1获取

if (hasLung && hasStomach && hasLiver && maxToxinLayers >= 5) {
    TumorData tumorData = getTumorData(player);
    // 毒素云增强
    tumorData.setToxicCloudRadius(6.0f);
    tumorData.setToxicCloudDamage(4.0f);
    tumorData.setToxicCloudApplyPoisonII(true);

    // 毒素层数效果增强
    tumorData.setEnhancedToxinLayerEffects(true);

    // 特殊光环
    spawnToxinSovereignAura(player);
}
```

### 狂暴主题搭配

#### 4. 狂暴君主 (Frenzy Sovereign) ★★★★★

**需求**: 装备肿瘤心脏 + 肿瘤肌肉 + 肿瘤肾脏，且生命值曾经低于10%至少10次

**效果描述**:
- 获得被动"狂暴主宰"：
  - 狂暴阈值从30%提升至60%
  - 狂暴状态持续时间延长（从持续到生命恢复变为固定60秒）
  - 狂暴期间：
    - 所有伤害+100%（原+50%）
    - 攻击速度+50%
    - 移动速度+30%
    - 免疫所有负面效果
    - 每秒失去3点生命（原持续失血）
- 肾上腺素阈值从50%提升至70%
- 肌肉痉挛在狂暴期间完全不会触发
- 获得特殊的"狂暴君主"视觉效果（红色光环+火焰粒子）

**设计思路**: 狂暴主题的终极强化，让低血量成为最强状态

**强度分级**: 传说级
**获取难度**: ★★★★★

**技术实现**:
```java
// 主题检测
boolean hasHeart = data.hasOrgan(TUMOR_HEART);
boolean hasMuscle = data.hasOrgan(TUMOR_MUSCLE);
boolean hasKidney = data.hasOrgan(TUMOR_KIDNEY);
int lowHealthCount = getLowHealthCount(player, 0.10f); // 低于10%的次数

if (hasHeart && hasMuscle && hasKidney && lowHealthCount >= 10) {
    data.addCapability(new FrenzySovereignCapability(player));
}

public class FrenzySovereignCapability {
    private boolean isFrenzied = false;
    private int frenzyTimer = 0;

    public void tick(LivingEntity owner) {
        float healthPercent = owner.getHealth() / owner.getMaxHealth();

        // 进入狂暴（阈值60%）
        if (!isFrenzied && healthPercent < 0.60f) {
            enterFrenzy(owner);
        }

        // 狂暴持续60秒
        if (isFrenzied) {
            frenzyTimer--;
            if (frenzyTimer <= 0 || healthPercent > 0.60f) {
                exitFrenzy(owner);
            }
        }
    }

    private void enterFrenzy(LivingEntity owner) {
        isFrenzied = true;
        frenzyTimer = 60 * 20; // 60秒

        // 应用狂暴效果
        applyDamageMultiplier(owner, 2.0f); // +100%
        applyAttackSpeedBonus(owner, 0.5f); // +50%
        applyMovementSpeedBonus(owner, 0.3f); // +30%
        applyNegativeImmunity(owner, true); // 免疫负面
        startDoT(owner, 3.0f); // 每秒3点

        // 视觉效果
        spawnFrenzySovereignEffects(owner);
    }

    private void exitFrenzy(LivingEntity owner) {
        isFrenzied = false;
        // 移除所有狂暴效果
        removeFrenzyEffects(owner);
    }
}
```

---

## 跨系搭配系统

跨系搭配创造肿瘤与其他器官类型的独特互动，提供多样化的Build选择。

### 肿瘤×九狱搭配

#### 1. 死亡拥抱 (Death Embrace) ★★★★☆

**需求**: 4个肿瘤器官 + 4个九狱器官

**效果描述**:
- 获得被动"生命转化"：
  - 生命值每降低1%，伤害+1%（最高+100%）
  - 生命值低于30%时，额外获得：
    - 攻击速度+30%
    - 移动速度+20%
    - 九狱器官的"罪恶"效果增强50%
- 肿瘤的"狂暴"与九狱的"罪孽"完美融合：
  - 狂暴阈值从30%提升至40%
  - 狂暴期间，九狱器官的伤害加成翻倍
- 获得特殊的"死亡拥抱"粒子效果（暗紫色火焰）

**设计思路**: 肿瘤的低血量狂暴与九狱的罪恶累积完美结合

**强度分级**: 大师级
**获取难度**: ★★★★☆

**技术实现**:
```java
// 跨系检测
int tumorCount = getTotalTumorCount(data);
int infernoCount = getInfernoOrganCount(data);

if (tumorCount >= 4 && infernoCount >= 4) {
    data.addTask(new DeathEmbraceTask(player));
}

public class DeathEmbraceTask implements IChestCavityTask {
    @Override
    public void tick(LivingEntity owner) {
        float healthPercent = owner.getHealth() / owner.getMaxHealth();
        float damageBonus = (1.0f - healthPercent); // 生命转化

        // 应用伤害加成（最高+100%）
        damageBonus = Math.min(damageBonus, 1.0f);
        applyDamageMultiplier(owner, 1.0f + damageBonus);

        // 低血量额外加成
        if (healthPercent < 0.30f) {
            applyAttackSpeedBonus(owner, 0.3f);
            applyMovementSpeedBonus(owner, 0.2f);
            // 九狱效果增强
            enhanceInfernoEffects(owner, 1.5f);
        }

        // 狂暴期间九狱伤害翻倍
        TumorData tumorData = getTumorData(owner);
        if (tumorData.isFrenzied()) {
            enhanceInfernoEffects(owner, 2.0f);
        }
    }
}
```

### 肿瘤×九头蛇搭配

#### 2. 不死军团 (Undead Legion) ★★★★☆

**需求**: 肿瘤心脏 + 肿瘤肠子 + 九头蛇心脏 + 九头蛇脊柱

**效果描述**:
- 获得被动"多头生命"：
  - 每装备1个肿瘤器官和1个九头蛇器官，获得1条"额外生命"
  - 额外生命计算：总额外生命 = min(肿瘤数量, 九头蛇数量)
  - 当主生命值降至0时，消耗1条额外生命，恢复50%最大生命值
  - 额外生命用尽后，进入正常的死亡流程
- "再生"与"增殖"的结合：
  - 九头蛇的再生效果+50%
  - 肿瘤的增殖触发时，有50%概率额外恢复1条额外生命
- 肠子的"再生状态"与九头蛇的再生叠加：
  - 再生状态期间，九头蛇再生效果翻倍

**设计思路**: 肿瘤的增殖与九头蛇的再生创造永动机式的生存系统

**强度分级**: 大师级
**获取难度**: ★★★★☆

**技术实现**:
```java
// 跨系检测
boolean hasTumorHeart = data.hasOrgan(TUMOR_HEART);
boolean hasTumorIntestine = data.hasOrgan(TUMOR_INTESTINE);
boolean hasHydraHeart = data.hasOrgan(HYDRA_HEART);
boolean hasHydraSpine = data.hasOrgan(HYDRA_SPINE);

if (hasTumorHeart && hasTumorIntestine && hasHydraHeart && hasHydraSpine) {
    data.addCapability(new UndeadLegionCapability(player));
}

public class UndeadLegionCapability {
    private int extraLives = 0;

    public void onChestCavityOpen(Player player, ChestCavityData data) {
        // 计算额外生命
        int tumorCount = getTotalTumorCount(data);
        int hydraCount = getHydraOrganCount(data);
        extraLives = Math.min(tumorCount, hydraCount);
    }

    public void onDeath(LivingEntity owner, DamageSource source) {
        if (extraLives > 0) {
            // 消耗额外生命
            extraLives--;
            // 恢复50%生命
            owner.setHealth(owner.getMaxHealth() * 0.5f);
            // 特效
            spawnExtraLifeEffect(owner);
            // 通知
            sendExtraLifeMessage(owner, extraLives);
        }
    }

    public void onProliferation(LivingEntity owner) {
        // 增殖时50%概率恢复额外生命
        if (Math.random() < 0.5f && extraLives < getMaxExtraLives()) {
            extraLives++;
            sendExtraLifeRestoredMessage(owner);
        }
    }
}
```

### 肿瘤×弗兰肯斯坦搭配

#### 3. 科学怪人2.0 (Frankenstein 2.0) ★★★★☆

**需求**: 5个肿瘤器官 + 5个弗兰肯斯坦器官

**效果描述**:
- 获得被动"实验体强化"：
  - 所有器官效果+50%（肿瘤+弗兰肯斯坦）
  - 但每10秒有10%概率触发"排斥反应"：
    - 排斥反应持续5秒
    - 期间所有器官效果-100%（完全失效）
    - 受到的伤害+50%
- "增殖"与"拼凑"的结合：
  - T3增殖触发时，有25%概率增殖弗兰肯斯坦器官
  - 弗兰肯斯坦器官的"不同部位"要求放宽：
    - 可以重复相同部位，但效果减半
- 特殊粒子效果：电弧+变异孢子的混合特效

**设计思路**: 肿瘤的变异与弗兰肯斯坦的拼凑创造疯狂的科学家风格

**强度分级**: 大师级
**获取难度**: ★★★★☆

**技术实现**:
```java
// 跨系检测
int tumorCount = getTotalTumorCount(data);
int frankensteinCount = getFrankensteinOrganCount(data);

if (tumorCount >= 5 && frankensteinCount >= 5) {
    data.addTask(new Frankenstein2Task(player));
    // 放宽弗兰肯斯坦部位限制
    relaxFrankensteinRestriction(player);
}

public class Frankenstein2Task implements IChestCavityTask {
    private boolean isRejecting = false;
    private int rejectTimer = 0;

    @Override
    public void tick(LivingEntity owner) {
        // 每10秒检测排斥反应
        if (owner.tickCount % (10 * 20) == 0 && !isRejecting) {
            if (Math.random() < 0.10f) { // 10%概率
                triggerRejection(owner);
            }
        }

        // 排斥反应持续
        if (isRejecting) {
            rejectTimer--;
            if (rejectTimer <= 0) {
                endRejection(owner);
            }
        }
    }

    private void triggerRejection(LivingEntity owner) {
        isRejecting = true;
        rejectTimer = 5 * 20; // 5秒

        // 所有器官效果失效
        disableAllOrganEffects(owner);

        // 受到的伤害+50%
        applyDamageTakenMultiplier(owner, 1.5f);

        // 特效
        spawnRejectionEffects(owner);
    }

    private void endRejection(LivingEntity owner) {
        isRejecting = false;
        // 恢复所有器官效果
        enableAllOrganEffects(owner);
        // 移除伤害加成
        removeDamageTakenMultiplier(owner);
    }

    public void onProliferation(LivingEntity owner) {
        // 25%概率增殖弗兰肯斯坦器官
        if (Math.random() < 0.25f) {
            proliferateFrankensteinOrgan(owner);
        }
    }
}
```

### 肿瘤×幻想种搭配

#### 4. 现实扭曲 (Reality Distortion) ★★★★★

**需求**: 肿瘤阑尾 + 幻想种全套（4个幻想种器官）

**效果描述**:
- 获得被动"扭曲现实"：
  - 30%概率将任何伤害转化为治疗（等量转化）
  - 30%概率将任何治疗转化为伤害（等量转化）
  - 40%概率正常运作
- 阑尾的"随机觉醒"与幻想种的"现实扭曲"结合：
  - 觉醒概率提升至15%（原5%）
  - 觉醒效果必定是"完美适应"或"细胞融合"
  - 如果触发"致命缺陷"，则视为"扭曲现实"的负面效果
- 特殊粒子效果：彩虹色+变异孢子的混合特效
- 玩家对话变为随机怪异内容

**设计思路**: 肿瘤的变异与幻想种的扭曲创造完全的随机性体验

**强度分级**: 传说级
**获取难度**: ★★★★★

**技术实现**:
```java
// 跨系检测
boolean hasAppendix = data.hasOrgan(TUMOR_APPENDIX);
int fantasyCount = getFantasyOrganCount(data);

if (hasAppendix && fantasyCount >= 4) {
    data.addCapability(new RealityDistortionCapability(player));
    // 阑尾觉醒增强
    TumorData tumorData = getTumorData(player);
    tumorData.setAppendixAwakenProbability(0.15f);
    tumorData.setAppendixAwakenEffectsOnlyPositive(true);
}

public class RealityDistortionCapability {
    @Override
    public void onHurt(LivingEntity owner, DamageSource source, float damage) {
        float roll = Math.random();
        if (roll < 0.30f) {
            // 30%概率：伤害转为治疗
            owner.heal(damage);
            cancelDamage(); // 取消伤害
            spawnDamageToHealEffect(owner);
        } else if (roll < 0.60f) {
            // 30%概率：治疗转伤害（这里不处理，在onHeal中处理）
            // 正常受到伤害
        } else {
            // 40%概率：正常运作
            // 正常受到伤害
        }
    }

    @Override
    public void onHeal(LivingEntity owner, float amount) {
        float roll = Math.random();
        if (roll < 0.30f) {
            // 30%概率：治疗转伤害
            owner.hurt(owner.damageSources().magic(), amount);
            cancelHeal(); // 取消治疗
            spawnHealToDamageEffect(owner);
        } else if (roll < 0.60f) {
            // 30%概率：伤害转治疗（这里不处理，在onHurt中处理）
            // 正常治疗
        } else {
            // 40%概率：正常运作
            // 正常治疗
        }
    }
}
```

### 肿瘤×墨水/颜料搭配

#### 5. 艺术变异 (Artistic Mutation) ★★★☆☆

**需求**: 肿瘤肠子 + 墨水心脏 + 颜料心脏 + 调色盘

**效果描述**:
- 获得"艺术代谢"：
  - 肠子的"变异代谢"状态改变为艺术主题：
    - **水墨状态**: +20%伤害，攻击留下墨水陷阱
    - **色彩状态**: +30%移动速度，获得随机元素抗性
    - **创作状态**: +50%治疗效果，但受到伤害+20%
    - **混乱状态**: 随机切换上述三种效果，每5秒一次
- 墨水/颜料器官的效果+30%
- 特殊粒子效果：墨水+颜料+变异孢子的艺术混合

**设计思路**: 肿瘤的变异与艺术器官的结合创造独特的艺术体验

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 跨系检测
boolean hasTumorIntestine = data.hasOrgan(TUMOR_INTESTINE);
boolean hasInkHeart = data.hasOrgan(INK_HEART);
boolean hasPaintHeart = data.hasOrgan(PAINT_HEART);
boolean hasPalette = data.hasOrgan(PALETTE);

if (hasTumorIntestine && hasInkHeart && hasPaintHeart && hasPalette) {
    // 启用艺术代谢
    TumorData tumorData = getTumorData(player);
    tumorData.setEnableArtisticMetabolism(true);

    // 墨水/颜料效果增强
    boostOrganTypeEffects(player, INK_TAG, 1.30f);
    boostOrganTypeEffects(player, PAINT_TAG, 1.30f);

    // 特殊粒子
    spawnArtisticMutationParticles(player);
}
```

### 肿瘤×龙类搭配

#### 6. 龙化肿瘤 (Dragon Tumor) ★★★☆☆

**需求**: 肿瘤心脏 + 火龙心脏 + 冰龙心脏 + 电龙心脏

**效果描述**:
- 获得被动"龙化增殖"：
  - 每种龙心脏提供独特的肿瘤增强：
    - 火龙心脏：肿瘤伤害+20%，攻击附带燃烧
    - 冰龙心脏：肿瘤防御+20%，受击时冻结敌人
    - 电龙心脏：肿瘤速度+20%，攻击连锁3个敌人
- "增殖之心"的效果增强：
  - 从+5%/器官提升至+8%/器官
  - 额外获得：每种龙类器官+1个，肿瘤增殖效果+2%
- 阑尾觉醒如果触发"完美适应"，额外获得对应龙类的能力

**设计思路**: 肿瘤的增殖与龙类的元素结合创造龙化的变异体验

**强度分级**: 专家级
**获取难度**: ★★★☆☆

**技术实现**:
```java
// 跨系检测
boolean hasTumorHeart = data.hasOrgan(TUMOR_HEART);
boolean hasFireHeart = data.hasOrgan(FIRE_DRAGON_HEART);
boolean hasIceHeart = data.hasOrgan(ICE_DRAGON_HEART);
boolean hasLightningHeart = data.hasOrgan(LIGHTNING_DRAGON_HEART);

if (hasTumorHeart && hasFireHeart && hasIceHeart && hasLightningHeart) {
    data.addTask(new DragonTumorTask(player));
}

public class DragonTumorTask implements IChestCavityTask {
    @Override
    public void tick(LivingEntity owner) {
        // 应用龙类增强
        if (data.hasOrgan(FIRE_DRAGON_HEART)) {
            applyDamageMultiplier(owner, 1.20f);
            applyFireAttackEffect(owner);
        }
        if (data.hasOrgan(ICE_DRAGON_HEART)) {
            applyDefenseMultiplier(owner, 1.20f);
            applyFreezeOnHitEffect(owner);
        }
        if (data.hasOrgan(LIGHTNING_DRAGON_HEART)) {
            applySpeedMultiplier(owner, 1.20f);
            applyChainLightningEffect(owner, 3);
        }
    }
}
```

### 肿瘤×悚恐搭配

#### 7. 恐惧肿瘤 (Fear Tumor) ★★★★☆

**需求**: 肿瘤脾脏 + 悚恐怖匣 + 悚恐怖肋骨 + 悚恐怖脊柱

**效果描述**:
- 获得被动"恐惧增殖"：
  - 每次受击时，有20%概率增殖1个临时肿瘤器官（持续5分钟）
  - 临时肿瘤器官提供50%效果，持续时间结束后消失
- "变异免疫"强化：
  - 转化概率从25%提升至40%
  - 转化后，额外施加"恐惧"效果给攻击者（持续3秒）
- 悚恐怖官的"恐惧"效果强化：
  - 恐惧持续时间+50%
  - 恐惧的敌人受到的伤害+20%

**设计思路**: 肿瘤的增殖与悚恐的恐惧创造恐惧扩散的体验

**强度分级**: 大师级
**获取难度**: ★★★★☆

**技术实现**:
```java
// 跨系检测
boolean hasTumorSpleen = data.hasOrgan(TUMOR_SPLEEN);
boolean hasHorrorBox = data.hasOrgan(HORROR_BOX);
boolean hasHorrorRib = data.hasOrgan(HORROR_RIB);
boolean hasHorrorSpine = data.hasOrgan(HORROR_SPINE);

if (hasTumorSpleen && hasHorrorBox && hasHorrorRib && hasHorrorSpine) {
    data.addCapability(new FearTumorCapability(player));
}

public class FearTumorCapability {
    @Override
    public void onHurt(LivingEntity owner, DamageSource source, float damage) {
        // 20%概率增殖临时肿瘤
        if (Math.random() < 0.20f) {
            proliferateTemporaryTumor(owner, 5 * 60 * 20); // 5分钟
        }
    }

    // 免疫转化强化
    public float onEffectTransform(Player player, MobEffect effect) {
        if (isNegativeEffect(effect)) {
            if (Math.random() < 0.40f) { // 40%概率（原25%）
                // 转化并施加恐惧
                transformNegativeToPositive(player, effect);
                applyFearToAttacker(player, 3 * 20); // 3秒
            }
        }
    }
}
```

### 肿瘤×木质搭配

#### 8. 自然共生 (Natural Symbiosis) ★★☆☆☆

**需求**: 肿瘤胃 + 木质心脏 + 木质肝脏 + 木质肾脏

**效果描述**:
- 获得被动"自然稳定"：
  - 所有良性肿瘤的副作用-30%
  - 木质器官的生命恢复效果+50%
- "腐化消化"变为"自然消化"：
  - 仍然可以食用任何物品
  - 但正面效果概率提升至75%（原50%）
  - 负面效果（中毒、虚弱）改为"自然虚弱"：
    - 效果减半
    - 持续时间减半
- 肿瘤器官的外观变为木质纹理

**设计思路**: 肿瘤的变异与木质的自然结合创造稳定的共生体验

**强度分级**: 进阶级
**获取难度**: ★★☆☆☆

**技术实现**:
```java
// 跨系检测
boolean hasTumorStomach = data.hasOrgan(TUMOR_STOMACH);
boolean hasWoodHeart = data.hasOrgan(WOOD_HEART);
boolean hasWoodLiver = data.hasOrgan(WOOD_LIVER);
boolean hasWoodKidney = data.hasOrgan(WOOD_KIDNEY);

if (hasTumorStomach && hasWoodHeart && hasWoodLiver && hasWoodKidney) {
    // 良性肿瘤副作用减少
    applySideEffectReduction(player, TumorType.BENIGN, 0.30f);

    // 木质器官恢复增强
    boostOrganTypeEffects(player, WOOD_TAG, "regen", 1.50f);

    // 自然消化
    TumorData tumorData = getTumorData(player);
    tumorData.setEnableNaturalDigestion(true);
    tumorData.setNaturalDigestionPositiveProbability(0.75f);

    // 木质纹理
    applyWoodTexture(player);
}
```

---

## 隐藏搭配系统

隐藏搭配需要玩家发现，提供惊喜感和探索乐趣。

### 彩蛋搭配

#### 1. 闹鬼的合唱团 (Haunted Choir) ★★☆☆☆

**需求**: 胸腔中所有9个槽位放置肿瘤器官，且在血月之夜

**效果描述**:
- 获得被动"合唱共鸣"：
  - 所有肿瘤器官开始"合唱"（发出特殊的音效）
  - 每10秒，所有肿瘤器官的效果+5%（可叠加，最高+50%）
  - 每30秒，随机一个肿瘤器官会"独唱"（效果翻倍，持续10秒）
- 特殊粒子效果：肿瘤器官发出的音符粒子
- 玩家会不时听到肿瘤的"歌声"（随机的怪异声音）

**设计思路**: 娱乐性彩蛋搭配，提供独特的音乐体验

**强度分级**: 进阶级
**获取难度**: ★★★☆☆（需要血月，较难遇到）

**技术实现**:
```java
// 隐藏检测：只在血月检测
if (isBloodMoon() && getTotalTumorCount(data) >= 9) {
    data.addTask(new HauntedChoirTask(player));
}

public class HauntedChoirTask implements IChestCavityTask {
    private int choirStacks = 0;
    private int soloTimer = 0;
    private int soloSlot = -1;

    @Override
    public void tick(LivingEntity owner) {
        // 每10秒增加合唱层数
        if (owner.tickCount % (10 * 20) == 0) {
            choirStacks = Math.min(choirStacks + 1, 10); // 最高10层=+50%
            float bonus = 1.0f + (choirStacks * 0.05f);
            boostAllTumorOrgans(owner, bonus);
            spawnNoteParticles(owner);
        }

        // 每30秒随机独唱
        if (owner.tickCount % (30 * 20) == 0) {
            soloSlot = getRandomTumorSlot(owner);
            soloTimer = 10 * 20; // 10秒
            boostOrganEffect(owner, soloSlot, 2.0f); // 翻倍
        }

        // 独唱计时
        if (soloTimer > 0) {
            soloTimer--;
            if (soloTimer <= 0) {
                removeSoloBoost(owner, soloSlot);
                soloSlot = -1;
            }
        }

        // 随机歌声
        if (owner.tickCount % (5 * 20) == 0) {
            playRandomSingingSound(owner);
        }
    }
}
```

### 特殊条件搭配

#### 2. 完美适应者 (Perfect Adapted) ★★★★☆

**需求**: 阑尾连续3次触发"完美适应"效果

**效果描述**:
- 获得永久被动"进化适应"：
  - 所有肿瘤器官的副作用完全消失
  - 所有肿瘤器官的收益+30%
  - 但阑尾的"随机觉醒"机制永久消失（不再触发觉醒或阑尾炎）
  - 阑尾变为"进化阑尾"，提供固定+5所有属性
- 特殊粒子效果：金色进化光环
- 玩家对话变为哲学性内容

**设计思路**: 极低概率的终极奖励，让坚持尝试的玩家获得超强能力

**强度分级**: 大师级
**获取难度**: ★★★★★（需要极好的运气：0.05^3 = 0.000125 = 0.0125%）

**技术实现**:
```java
// 隐藏检测：追踪阑尾觉醒历史
public class AppendixAwakenTracker {
    private int perfectAdaptationCount = 0;
    private boolean hasEvolved = false;

    public void onAppendixAwaken(Player player, AwakeningEffect effect) {
        if (effect == AwakeningEffect.PERFECT_ADAPTATION) {
            perfectAdaptationCount++;
            if (perfectAdaptationCount >= 3 && !hasEvolved) {
                triggerEvolution(player);
            }
        } else {
            // 触发其他效果，重置计数
            perfectAdaptationCount = 0;
        }
    }

    private void triggerEvolution(Player player) {
        hasEvolved = true;
        // 移除所有副作用
        removeAllSideEffects(player);
        // 所有收益+30%
        boostAllTumorOrgans(player, 1.30f);
        // 阑尾进化
        evolveAppendix(player);
        // 特效
        spawnEvolutionAura(player);
    }
}
```

### 终极挑战搭配

#### 3. 增殖之王 (King of Proliferation) ★★★★★

**需求**: 在一次游戏过程中，触发T3增殖机制至少50次

**效果描述**:
- 获得被动"无限增殖"：
  - 每分钟自动增殖1个随机肿瘤器官（无限制）
  - 增殖不受槽位限制（会自动移除最老的器官以腾出空间）
  - 每次增殖都有5%概率触发"超级变异爆发"（周围20格敌人受到50点伤害）
- 获得称号"增殖之王"：
  - 所有肿瘤器官效果翻倍
  - 所有副作用消失
  - 但玩家每秒失去1%生命（无法阻止）
- 特殊粒子效果：大规模的粒子爆发

**设计思路**: 终极挑战搭配，需要玩家投入大量时间，但获得超强的力量

**强度分级**: 传说级
**获取难度**: ★★★★★（需要极长的时间和耐心）

**技术实现**:
```java
// 隐藏检测：追踪增殖次数
public class ProliferationCounter {
    private int proliferationCount = 0;
    private boolean hasTriggeredKing = false;

    public void onProliferation(Player player) {
        proliferationCount++;
        if (proliferationCount >= 50 && !hasTriggeredKing) {
            triggerKingOfProliferation(player);
        }
    }

    private void triggerKingOfProliferation(Player player) {
        hasTriggeredKing = true;
        // 应用所有效果
        data.addTask(new KingOfProliferationTask(player));
        // 通知
        sendAchievementMessage(player, "增殖之王");
    }
}

public class KingOfProliferationTask implements IChestCavityTask {
    @Override
    public void tick(LivingEntity owner) {
        // 每分钟自动增殖
        if (owner.tickCount % (60 * 20) == 0) {
            triggerAutoProliferation(owner);
        }

        // 所有效果翻倍
        boostAllTumorOrgans(owner, 2.0f);

        // 每秒失去1%生命
        if (owner.tickCount % 20 == 0) {
            float damage = owner.getMaxHealth() * 0.01f;
            owner.hurt(owner.damageSources().magic(), damage);
        }
    }
}
```

### 其他隐藏搭配

#### 4. 变异大师 (Mutation Master) ★★★☆☆

**需求**: 肠子的"变异代谢"经历过所有状态（包括T7的觉醒和崩溃状态）至少各3次

**效果描述**:
- 获得被动"状态大师"：
  - 可以手动选择肠子的下一个状态（右键胸腔UI打开选择菜单）
  - 状态切换冷却：10秒
  - 所有状态效果增强50%
- 获得特殊能力"状态融合"：
  - 可以同时激活2种状态（持续30秒，冷却3分钟）
  - 融合状态的效果叠加

**设计思路**: 奖励深入探索肠子机制的玩家

**强度分级**: 专家级
**获取难度**: ★★★★☆

#### 5. 毒素炼金术士 (Toxin Alchemist) ★★★☆☆

**需求**: 肝脏的"毒素积累"达到过10层，且肾脏的"废物堆积"达到过10层

**效果描述**:
- 获得被动"毒素精通"：
  - 毒素和废物层数的上限提升至20层
  - 毒素和废物的每层效果翻倍
  - 但清除速度减半
- 获得特殊能力"毒素转化"：
  - 可以主动消耗毒素/废物层数，每层恢复10%生命值
  - 冷却时间：1分钟

**设计思路**: 奖励深入探索层数系统的玩家

**强度分级**: 专家级
**获取难度**: ★★★★☆

---

## 搭配强度分级

### 入门级 (★☆☆☆☆)

**特点**: 容易触发，效果温和，适合新手

**代表搭配**:
- 恶性生长 (2件套)
- 营养循环 (胃+肠)
- 毒素扩散 (肺+胃)
- 闹鬼的合唱团 (彩蛋)

**触发难度**: 1-3个器官，容易获取

**效果强度**: +5-15%属性或小效果

### 进阶级 (★★☆☆☆)

**特点**: 需要一定探索，效果明显

**代表搭配**:
- 狂暴突变 (4件套)
- 呼吸净化 (肺+脾)
- 稳定爆发 (肾+脾)
- 毒素扩散 (肺+胃)
- 自然共生 (肿瘤×木质)

**触发难度**: 3-4个器官，需要击败小型BOSS

**效果强度**: +15-30%属性或中等效果

### 专家级 (★★★☆☆)

**特点**: 需要深入探索，效果强力

**代表搭配**:
- 肿瘤军团 (6件套)
- 增殖核心 (心+肝)
- 过滤网络 (肝+肾)
- 变异循环 (肠+阑尾)
- 适应共生 (良性3件)
- 平衡共生 (混合2+2)
- 核心守护 (槽位13)
- 完美适应者 (隐藏)
- 毒素君主 (主题)
- 龙化肿瘤 (跨系)

**触发难度**: 5-7个器官，需要击败主要BOSS

**效果强度**: +30-50%属性或强力效果

### 大师级 (★★★★☆)

**特点**: 需要挑战，效果极强

**代表搭配**:
- 狂暴战士 (心+肌)
- 侵蚀之主 (恶性4件)
- 突变共鸣 (特殊2件)
- 对称平衡 (槽位)
- 完美变异 (主题)
- 死亡拥抱 (跨系)
- 不死军团 (跨系)
- 科学怪人2.0 (跨系)
- 恐惧肿瘤 (跨系)
- 完美适应者 (隐藏)

**触发难度**: 8-9个器官或特殊条件，需要极难BOSS

**效果强度**: +50-80%属性或改变游戏的效果

### 传说级 (★★★★★)

**特点**: 极难触发，效果改变游戏

**代表搭配**:
- 不死之身 (9件套)
- 无限增殖 (主题)
- 狂暴君主 (主题)
- 现实扭曲 (跨系)
- 增殖之王 (隐藏)

**触发难度**: 全套器官 + 特殊条件，需要终极挑战

**效果强度**: +80-100%属性或颠覆性效果

---

## UI与反馈设计

### 视觉反馈

#### 1. 搭配高亮系统

**实现**: 参与搭配的器官在胸腔UI中高亮显示

**高亮方式**:
- **相邻搭配**: 蓝色边框 + 连接线
- **套装搭配**: 金色边框
- **主题搭配**: 紫色边框
- **槽位搭配**: 绿色边框
- **隐藏搭配**: 彩虹边框（发现后）

**示例**:
```
胸腔UI布局:
[0] [1] [2] [3] [4] [5] [6] [7] [8]
[9] [10][11][12][13][14][15][16][17]
[18][19][20][21][22][23][24][25][26]

搭配高亮示例:
- 槽位4[肿瘤心脏] + 槽位5[肿瘤肺脏] (相邻) → 蓝色边框 + 蓝色连线
- 4个肿瘤器官 (套装) → 所有肿瘤器官金色边框
- 槽位13[肿瘤心脏] (槽位) → 绿色边框
```

#### 2. 效果预览系统

**实现**: 鼠标悬停在器官上时显示当前激活的搭配

**显示内容**:
- 搭配名称（颜色编码强度）
- 搭配类型图标
- 当前效果（简述）
- 下一步效果（如果有）

**示例**:
```
悬停在肿瘤心脏上:
═════════════════════════════════
✦ 肿瘤心脏 (核心器官)
═════════════════════════════════
激活的搭配:
✦ [★★★☆☆] 增殖核心 (相邻)
  效果: +7%/器官增殖加成
  副作用: 无

✦ [★★☆☆☆] 狂暴突变 (套装4件)
  效果: +50%伤害, 每秒-1生命

✦ [★★★☆☆] 核心守护 (槽位13)
  效果: +10%/器官增殖加成
═════════════════════════════════
```

#### 3. 进度指示器

**实现**: 显示套装搭配的进度

**示例**:
```
套装进度:
[████░░░░] 肿瘤套装 (4/9)
当前效果: +50%伤害, 每秒-1生命
下一级(6件): 肿瘤军团 - 所有属性+20%, 击杀增殖
下一级(9件): 不死之身 - 第二条命
```

### 音效反馈

#### 1. 搭配激活音效

**强度分级音效**:
- **入门级**: 轻柔的"嗡"声
- **进阶级**: 清脆的"叮"声
- **专家级**: 中等音量的"能量共鸣"声
- **大师级**: 响亮的"能量爆发"声
- **传说级**: 震撼的"天神降临"声

**搭配类型音效**:
- **相邻**: 流畅的连接音效
- **套装**: 分层的叠加音效
- **主题**: 强烈的共鸣音效
- **槽位**: 精确的锁定音效
- **隐藏**: 神秘的"发现"音效

#### 2. 搭配解除音效

相应搭配的"消散"音效，音量与激活时匹配

### 粒子效果

#### 1. 胸腔内效果

**搭配类型粒子**:
- **相邻**: 蓝色连接线粒子
- **套装**: 金色光环粒子
- **主题**: 紫色共鸣粒子
- **槽位**: 绿色锁定粒子
- **隐藏**: 彩虹色爆发粒子

**肿瘤类型粒子**:
- **良性**: 柔和的绿色脉动
- **恶性**: 剧烈的红色闪烁
- **特殊**: 彩虹色变换

#### 2. 角色外效果

**搭配强度粒子**:
- **入门级**: 少量粒子，缓慢扩散
- **进阶级**: 中等粒子，正常扩散
- **专家级**: 大量粒子，快速扩散
- **大师级**: 密集粒子，持续爆发
- **传说级**: 粒子风暴，覆盖大范围

### 通知系统

#### 1. 搭配发现通知

```
✨ 发现新搭配: "增殖核心"！
肿瘤心脏 + 肿瘤肝脏（相邻）
效果: +7%/器官增殖加成，药水效果+75%
[查看详情]
```

#### 2. 搭配升级通知

```
⬆️ 搭配升级: "肿瘤套装" 4件 → 6件
新效果: 所有属性+20%, 击杀增殖
[查看详情]
```

#### 3. 隐藏搭配发现通知

```
🎉 发现隐藏搭配: "闹鬼的合唱团"！
这是极其罕见的发现...
效果: 所有肿瘤器官开始"合唱"！
[查看详情]
```

---

## 技术实现方案

### 数据结构设计

```java
// 肿瘤搭配定义
public class TumorSynergy {
    private String id;
    private String name;
    private String description;
    private TumorSynergyType type;
    private List<OrganCondition> conditions;
    private TumorSynergyEffect effect;
    private int priority;
    private SynergyStrength strength;
    private boolean isHidden;

    public enum TumorSynergyType {
        ADJACENT,      // 相邻
        SET_BONUS,     // 套装
        SLOT_BASED,    // 槽位
        THEMATIC,      // 主题
        CROSS_SYSTEM   // 跨系
    }

    public enum SynergyStrength {
        ENTRY(1),      // 入门
        ADVANCED(2),   // 进阶
        EXPERT(3),     // 专家
        MASTER(4),     // 大师
        LEGENDARY(5);  // 传说

        private final int level;
    }
}

// 肿瘤搭配效果接口
public interface TumorSynergyEffect {
    void apply(LivingEntity owner, ChestCavityData data);
    void remove(LivingEntity owner, ChestCavityData data);
    boolean isActive(LivingEntity owner);
}

// 肿瘤搭配数据
public class TumorSynergyData {
    private Set<String> discoveredSynergies = new HashSet<>();
    private Map<String, Integer> synergyUsageCount = new HashMap<>();
    private Map<String, Long> firstDiscoveryTime = new HashMap<>();

    // 隐藏搭配追踪数据
    private int appendixAwakenCount = 0;
    private int proliferationCount = 0;
    private Set<MetabolismState> experiencedStates = new HashSet<>();
    private int maxToxinLayers = 0;
    private int maxWasteLayers = 0;
    private int lowHealthCount = 0;
}
```

### 搭配检测流程

```java
public class TumorSynergyManager {
    private static final List<TumorSynergy> ALL_SYNERGIES = new ArrayList<>();
    private static final Map<UUID, SynergyCache> PLAYER_CACHES = new ConcurrentHashMap<>();

    public static void detectAndApplyTumorSynergies(LivingEntity owner, ChestCavityData data) {
        UUID playerUUID = owner.getUUID();
        SynergyCache cache = PLAYER_CACHES.computeIfAbsent(playerUUID, uuid -> new SynergyCache());

        // 检查缓存
        int currentHash = calculateOrganHash(data);
        if (currentHash == cache.getLastHash() && !cache.isExpired()) {
            return; // 使用缓存
        }

        // 移除旧效果
        cache.deactivateAll(owner, data);

        // 检测新搭配
        List<ActiveSynergy> activeSynergies = detectTumorSynergies(data, cache);

        // 按优先级排序并应用
        activeSynergies.sort(Comparator.comparingInt(s -> s.getSynergy().getPriority()));
        applySynergiesWithPriority(owner, data, activeSynergies);

        // 更新缓存
        cache.update(currentHash, activeSynergies);
    }

    private static List<ActiveSynergy> detectTumorSynergies(ChestCavityData data, SynergyCache cache) {
        List<ActiveSynergy> activeSynergies = new ArrayList<>();

        for (TumorSynergy synergy : ALL_SYNERGIES) {
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

                // 记录发现的隐藏搭配
                if (synergy.isHidden()) {
                    cache.markDiscovered(synergy.getId());
                    notifySynergyDiscovered(owner, synergy);
                }
            }
        }

        return activeSynergies;
    }
}
```

### 性能优化

基于器官搭配系统v2.0的优化策略：

1. **缓存机制**: 基于哈希值的5秒缓存
2. **延迟计算**: 胸腔关闭时统一计算
3. **分区计算**: 只计算受影响区域
4. **早期退出**: 满足/不满足立即返回
5. **批量处理**: 多玩家并行流
6. **预计算**: 游戏加载时预计算常见组合
7. **内存优化**: 弱引用缓存

---

## JSON配置示例

### 相邻搭配配置

```json
{
  "tumor_adjacent_synergies": [
    {
      "id": "nutrient_cycle",
      "name": "营养循环",
      "description": "肿瘤胃和肿瘤肠子形成完美的营养循环",
      "type": "adjacent",
      "organs": ["tumor_stomach", "tumor_intestine"],
      "strength": "ENTRY",
      "priority": 75,
      "effects": [
        {
          "type": "hunger_multiplier",
          "value": 0.8
        },
        {
          "type": "saturation_multiplier",
          "value": 1.3
        },
        {
          "type": "nutrient_transform_probability",
          "value": 0.65
        }
      ],
      "hidden": false
    },
    {
      "id": "proliferation_core",
      "name": "增殖核心",
      "description": "肿瘤心脏和肿瘤肝脏形成增殖核心",
      "type": "adjacent",
      "organs": ["tumor_heart", "tumor_liver"],
      "strength": "EXPERT",
      "priority": 75,
      "effects": [
        {
          "type": "proliferation_bonus_per_organ",
          "value": 0.07
        },
        {
          "type": "potion_duration_bonus",
          "value": 0.75
        },
        {
          "type": "toxin_bleed_threshold",
          "value": 6
        }
      ],
      "hidden": false
    }
  ]
}
```

### 套装搭配配置

```json
{
  "tumor_set_synergies": [
    {
      "id": "malignant_growth",
      "name": "恶性生长",
      "description": "2个肿瘤器官提供基础增益",
      "type": "set_bonus",
      "required_count": 2,
      "strength": "ENTRY",
      "priority": 50,
      "effects": [
        {
          "type": "regen_multiplier",
          "value": 0.5
        },
        {
          "type": "health_multiplier",
          "value": 1.3
        }
      ],
      "overridden_by": ["frenzied_mutation"],
      "hidden": false
    },
    {
      "id": "undying",
      "name": "不死之身",
      "description": "9个肿瘤器官提供第二条命",
      "type": "set_bonus",
      "required_count": 9,
      "strength": "LEGENDARY",
      "priority": 50,
      "effects": [
        {
          "type": "undying_ability",
          "duration": 1200,
          "cooldown": 300000,
          "damage_bonus": 1.0,
          "speed_bonus": 0.5
        }
      ],
      "inherits": "tumor_legion",
      "hidden": false
    }
  ]
}
```

### 槽位搭配配置

```json
{
  "tumor_slot_synergies": [
    {
      "id": "core_guardian",
      "name": "核心守护",
      "description": "肿瘤心脏放置在核心位置",
      "type": "slot_based",
      "required_slot": 13,
      "required_organ": "tumor_heart",
      "strength": "EXPERT",
      "priority": 100,
      "effects": [
        {
          "type": "proliferation_bonus_per_organ",
          "value": 0.10
        },
        {
          "type": "malignant_beat_probability",
          "value": 0.10
        }
      ],
      "hidden": false
    }
  ]
}
```

### 主题搭配配置

```json
{
  "tumor_thematic_synergies": [
    {
      "id": "infinite_proliferation",
      "name": "无限增殖",
      "description": "终极增殖主题",
      "type": "thematic",
      "strength": "LEGENDARY",
      "priority": 25,
      "conditions": [
        {
          "type": "has_organ",
          "organ": "tumor_heart"
        },
        {
          "type": "has_organ",
          "organ": "tumor_appendix"
        },
        {
          "type": "proliferation_count",
          "min_count": 3
        }
      ],
      "effects": [
        {
          "type": "auto_proliferation",
          "probability": 0.05,
          "interval": 1200
        },
        {
          "type": "mutation_burst",
          "probability": 0.01,
          "damage": 30,
          "radius": 10
        }
      ],
      "hidden": false
    }
  ]
}
```

### 隐藏搭配配置

```json
{
  "tumor_hidden_synergies": [
    {
      "id": "haunted_choir",
      "name": "闹鬼的合唱团",
      "description": "血月之夜的肿瘤合唱",
      "type": "thematic",
      "strength": "ADVANCED",
      "priority": 25,
      "conditions": [
        {
          "type": "organ_count",
          "min_count": 9,
          "organ_type": "tumor"
        },
        {
          "type": "is_blood_moon"
        }
      ],
      "effects": [
        {
          "type": "choir_buff",
          "stack_interval": 200,
          "max_stacks": 10,
          "bonus_per_stack": 0.05
        },
        {
          "type": "solo_buff",
          "solo_interval": 600,
          "solo_duration": 200,
          "solo_multiplier": 2.0
        }
      ],
      "hidden": true
    },
    {
      "id": "perfect_adapted",
      "name": "完美适应者",
      "description": "阑尾连续3次完美适应",
      "type": "special",
      "strength": "MASTER",
      "priority": 100,
      "conditions": [
        {
          "type": "appendix_perfect_adaptation_count",
          "min_count": 3
        }
      ],
      "effects": [
        {
          "type": "remove_all_side_effects"
        },
        {
          "type": "boost_all_tumor_organs",
          "multiplier": 1.3
        },
        {
          "type": "evolve_appendix",
          "bonus": 5.0
        }
      ],
      "hidden": true
    }
  ]
}
```

---

## 搭配速查表

### 按类型分类的搭配列表

#### 相邻搭配 (10个)

| ID | 名称 | 器官 | 强度 | 难度 |
|----|------|------|------|------|
| nutrient_cycle | 营养循环 | 胃+肠 | ★☆☆☆☆ | ★☆☆☆☆ |
| respiratory_purification | 呼吸净化 | 肺+脾 | ★★☆☆☆ | ★★☆☆☆ |
| proliferation_core | 增殖核心 | 心+肝 | ★★★☆☆ | ★★★☆☆ |
| berserk_warrior | 狂暴战士 | 心+肌 | ★★★★☆ | ★★★★☆ |
| filtration_network | 过滤网络 | 肝+肾 | ★★★☆☆ | ★★★★☆ |
| stable_burst | 稳定爆发 | 肾+脾 | ★★☆☆☆ | ★★★☆☆ |
| mutation_cycle | 变异循环 | 肠+阑尾 | ★★★★☆ | ★★★★★ |
| adaptive_proliferation | 适应增殖 | 阑尾+心 | ★★★☆☆ | ★★★★★ |
| toxin_spread | 毒素扩散 | 肺+胃 | ★★☆☆☆ | ★★☆☆☆ |
| metabolism_boost | 代谢加速 | 肠+胃 | ★★☆☆☆ | ★★☆☆☆ |

#### 套装搭配 (12个)

| ID | 名称 | 需求 | 强度 | 难度 |
|----|------|------|------|------|
| malignant_growth | 恶性生长 | 2件 | ★☆☆☆☆ | ★☆☆☆☆ |
| frenzied_mutation | 狂暴突变 | 4件 | ★★☆☆☆ | ★★☆☆☆ |
| tumor_legion | 肿瘤军团 | 6件 | ★★★☆☆ | ★★★☆☆ |
| undying | 不死之身 | 9件 | ★★★★★ | ★★★★★ |
| adaptive_symbiosis | 适应共生 | 良性3件 | ★★☆☆☆ | ★★☆☆☆ |
| lord_of_erosion | 侵蚀之主 | 恶性4件 | ★★★★☆ | ★★★★☆ |
| mutation_resonance | 突变共鸣 | 特殊2件 | ★★★★☆ | ★★★★★ |
| balanced_symbiosis | 平衡共生 | 2良性+2恶性 | ★★★☆☆ | ★★★☆☆ |

#### 槽位搭配 (4个)

| ID | 名称 | 需求 | 强度 | 难度 |
|----|------|------|------|------|
| core_guardian | 核心守护 | 槽位13+心脏 | ★★★☆☆ | ★★★☆☆ |
| edge_erosion | 边缘侵蚀 | 3个边缘槽位 | ★★☆☆☆ | ★★☆☆☆ |
| wedge_formation | 楔形阵型 | 楔形排列 | ★★★☆☆ | ★★★☆☆ |
| symmetric_balance | 对称平衡 | 对称排列 | ★★★★☆ | ★★★★☆ |

#### 主题搭配 (4个)

| ID | 名称 | 需求 | 强度 | 难度 |
|----|------|------|------|------|
| infinite_proliferation | 无限增殖 | 心+阑尾+增殖3次 | ★★★★★ | ★★★★★ |
| perfect_mutation | 完美变异 | 肠+阑尾+肝+全状态 | ★★★★☆ | ★★★★★ |
| toxin_sovereign | 毒素君主 | 肺+胃+肝+毒素5层 | ★★★☆☆ | ★★★☆☆ |
| frenzy_sovereign | 狂暴君主 | 心+肌+肾+低血10次 | ★★★★★ | ★★★★★ |

#### 跨系搭配 (10个)

| ID | 名称 | 需求 | 强度 | 难度 |
|----|------|------|------|------|
| death_embrace | 死亡拥抱 | 4肿瘤+4九狱 | ★★★★☆ | ★★★★☆ |
| undead_legion | 不死军团 | 心+肠+九头心+九头脊 | ★★★★☆ | ★★★★☆ |
| frankenstein_2 | 科学怪人2.0 | 5肿瘤+5弗兰 | ★★★★☆ | ★★★★☆ |
| reality_distortion | 现实扭曲 | 阑尾+幻想4件 | ★★★★★ | ★★★★★ |
| artistic_mutation | 艺术变异 | 肠+墨水心+颜料心+调色盘 | ★★★☆☆ | ★★★☆☆ |
| dragon_tumor | 龙化肿瘤 | 心+火心+冰心+电心 | ★★★☆☆ | ★★★☆☆ |
| fear_tumor | 恐惧肿瘤 | 脾+悚恐匣+悚恐肋+悚恐脊 | ★★★★☆ | ★★★★☆ |
| natural_symbiosis | 自然共生 | 胃+木心+木肝+木肾 | ★★☆☆☆ | ★★☆☆☆ |

#### 隐藏搭配 (8个)

| ID | 名称 | 需求 | 强度 | 难度 |
|----|------|------|------|------|
| haunted_choir | 闹鬼的合唱团 | 9肿瘤+血月 | ★★☆☆☆ | ★★★☆☆ |
| perfect_adapted | 完美适应者 | 阑尾3次完美适应 | ★★★★☆ | ★★★★★ |
| king_of_proliferation | 增殖之王 | 增殖50次 | ★★★★★ | ★★★★★ |
| mutation_master | 变异大师 | 肠全状态各3次 | ★★★☆☆ | ★★★★☆ |
| toxin_alchemist | 毒素炼金术士 | 肝10层+肾10层 | ★★★☆☆ | ★★★★☆ |

### 按强度分类的搭配列表

#### 入门级 (★☆☆☆☆) - 4个

- 恶性生长 (套装)
- 营养循环 (相邻)
- 毒素扩散 (相邻)
- 闹鬼的合唱团 (隐藏)

#### 进阶级 (★★☆☆☆) - 10个

- 狂暴突变 (套装)
- 呼吸净化 (相邻)
- 稳定爆发 (相邻)
- 毒素扩散 (相邻)
- 代谢加速 (相邻)
- 边缘侵蚀 (槽位)
- 适应共生 (套装)
- 自然共生 (跨系)
- 毒素君主 (主题)
- 闹鬼的合唱团 (隐藏)

#### 专家级 (★★★☆☆) - 15个

- 肿瘤军团 (套装)
- 增殖核心 (相邻)
- 过滤网络 (相邻)
- 适应增殖 (相邻)
- 平衡共生 (套装)
- 核心守护 (槽位)
- 楔形阵型 (槽位)
- 毒素君主 (主题)
- 龙化肿瘤 (跨系)
- 艺术变异 (跨系)
- 完美适应者 (隐藏)
- 变异大师 (隐藏)
- 毒素炼金术士 (隐藏)

#### 大师级 (★★★★☆) - 12个

- 狂暴战士 (相邻)
- 变异循环 (相邻)
- 侵蚀之主 (套装)
- 突变共鸣 (套装)
- 对称平衡 (槽位)
- 完美变异 (主题)
- 死亡拥抱 (跨系)
- 不死军团 (跨系)
- 科学怪人2.0 (跨系)
- 恐惧肿瘤 (跨系)
- 完美适应者 (隐藏)

#### 传说级 (★★★★★) - 5个

- 不死之身 (套装)
- 无限增殖 (主题)
- 狂暴君主 (主题)
- 现实扭曲 (跨系)
- 增殖之王 (隐藏)

---

## 设计总结

### 设计成果

本设计文档提供了完整的肿瘤搭配效果体系，包括：

1. ✅ **相邻搭配系统**: 10个精心设计的相邻搭配，覆盖良性-良性、恶性-恶性、良性-恶性、特殊器官等所有组合
2. ✅ **套装搭配系统**: 12个数量套装、类型套装和混合套装，从2件到9件的完整分层
3. ✅ **槽位搭配系统**: 5个基于位置的搭配，包括核心槽位、边缘槽位和特殊排列
4. ✅ **主题搭配系统**: 4个基于核心机制的主题搭配，充分利用T1-T3的设计成果
5. ✅ **跨系搭配系统**: 10个与其他器官类型的创意搭配，提供多样化的Build选择
6. ✅ **隐藏搭配系统**: 8个彩蛋、特殊条件和终极挑战搭配，提供探索惊喜
7. ✅ **强度分级系统**: 5级强度分级，从入门到传说，难度和效果匹配
8. ✅ **UI与反馈设计**: 完整的视觉、音效、粒子和通知系统
9. ✅ **技术实现方案**: 详细的数据结构、检测流程和性能优化
10. ✅ **JSON配置示例**: 完整的配置文件，可直接用于游戏
11. ✅ **搭配速查表**: 按类型和强度分类的快速查阅表

### 核心优势

1. **深度利用现有成果**: 完全基于T1-T3的设计，与增殖机制、分类系统深度整合
2. **风险收益平衡**: 每个强力搭配都有相应的代价或限制
3. **多样性策略**: 提供多条有效的搭配路径，避免唯一最优解
4. **探索惊喜**: 隐藏搭配提供发现乐趣和惊喜感
5. **技术可行**: 所有设计都基于现有API能力
6. **性能优化**: 完整的缓存和优化策略确保流畅体验
7. **玩家友好**: 清晰的UI反馈让玩家容易理解搭配系统

### 设计亮点

1. **增殖核心** (心+肝): 增强T3增殖机制的核心搭配
2. **狂暴战士** (心+肌): 低血量无敌的狂战士Build
3. **变异循环** (肠+阑尾): 完美结合两个特殊器官的随机性
4. **不死之身** (9件套): 改变游戏的"第二条命"能力
5. **无限增殖** (主题): 终极增殖主题，让肿瘤真正无限增殖
6. **现实扭曲** (跨系): 完全的随机性体验，伤害治疗互相转化
7. **完美适应者** (隐藏): 极低概率的终极奖励，超强能力
8. **增殖之王** (隐藏): 终极挑战搭配，需要极长时间但获得超强力量

### 待审核问题

1. **搭配数量**: 53个搭配是否过多？是否需要精简？
2. **强度平衡**: 各强度级别之间的差距是否合理？
3. **获取难度**: 隐藏搭配的获取难度是否合适？
4. **性能影响**: 大量搭配检测是否会影响性能？
5. **玩家学习曲线**: 搭配系统是否过于复杂？

### 后续开发建议

1. **分阶段实现**: 先实现相邻和套装搭配，再添加槽位、主题和跨系搭配
2. **平衡性测试**: 需要大量测试和调整，确保没有破坏性搭配
3. **玩家反馈**: 收集玩家反馈，调整搭配的触发条件和效果
4. **性能监控**: 实时监控搭配检测的性能，优化瓶颈
5. **持续更新**: 定期添加新的搭配，保持系统的新鲜感

---

**文档结束**

**下一步**: 等待审核反馈，根据审核结果进行调整和优化。

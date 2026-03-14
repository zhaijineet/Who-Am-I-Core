# 肿瘤增殖机制设计文档 T3

**设计版本**: v1.0
**创建日期**: 2026-03-15
**设计者**: Creative Content Designer
**状态**: 待审核
**前置依赖**: T1-器官基础属性设计

---

## 目录

1. [设计概述](#设计概述)
2. [增殖核心概念](#增殖核心概念)
3. [增殖触发条件](#增殖触发条件)
4. [增殖方式系统](#增殖方式系统)
5. [增殖限制机制](#增殖限制机制)
6. [增殖视觉反馈](#增殖视觉反馈)
7. [平衡性设计](#平衡性设计)
8. [技术实现方案](#技术实现方案)
9. [JSON配置示例](#json配置示例)
10. [性能优化策略](#性能优化策略)
11. [与搭配系统交互](#与搭配系统交互)
12. [增殖实例设计](#增殖实例设计)

---

## 设计概述

### 核心设计理念

肿瘤增殖机制是肿瘤流派的核心特色系统，体现了肿瘤"无限增殖、变异、不稳定"的本质特征。增殖系统不是简单的器官复制，而是一个**策略性的风险管理游戏**——玩家需要主动控制和引导增殖过程，在收益和代价之间寻找平衡点。

### 设计原则

1. **主动控制** - 增殖不是完全随机的，玩家可以通过策略引导增殖方向
2. **风险递增** - 增殖次数越多，收益越高，但失控风险也越大
3. **多样性** - 多种增殖方式和触发条件，避免单一最优解
4. **主题性** - 完全符合肿瘤"变异、不稳定、侵蚀性"的核心特征
5. **平衡性** - 高收益伴随高风险，避免强制使用增殖机制

### 增殖系统的核心价值

- **战术深度**: 玩家需要根据战斗情况决定是否触发增殖
- **Build多样性**: 不同增殖策略产生不同的Build方向
- **节奏控制**: 增殖的冷却和限制机制控制游戏节奏
- **惊喜感**: 随机变异带来意想不到的游戏体验

---

## 增殖核心概念

### 什么是"增殖"？

增殖是指肿瘤器官在特定条件下生成新的肿瘤器官或增强现有器官的过程。增殖不是简单的器官复制，而是包含以下几种形式：

1. **复制增殖** - 生成与原器官相同的肿瘤器官
2. **变异增殖** - 生成其他类型的肿瘤器官
3. **临时增殖** - 生成短期存在的临时肿瘤器官
4. **升级增殖** - 现有器官进化为更强形态

### 增殖度系统

增殖度（Proliferation Degree）是衡量玩家"增殖状态"的核心指标：

| 增殖度等级 | 名称 | 范围 | 效果 |
|-----------|------|------|------|
| 0 | 稳定期 | 0-2 | 无特殊效果 |
| 1 | 活跃期 | 3-5 | 增殖触发概率+10% |
| 2 | 爆发期 | 6-8 | 增殖触发概率+20%，变异概率+10% |
| 3 | 危险期 | 9-11 | 增殖触发概率+30%，变异概率+20%，但每次增殖失去2点生命 |
| 4 | 失控期 | 12+ | 增殖触发概率+40%，变异概率+30%，但每10秒失去1点生命，且增殖器官有20%概率直接摧毁 |

**增殖度计算**:
- 每个肿瘤器官基础贡献1点增殖度
- 某些特殊器官（如阑尾）额外贡献1点增殖度
- 增殖事件完成后增殖度重置为当前肿瘤器官数量

---

## 增殖触发条件

### 1. 时间增殖（被动触发）

**核心概念**: 肿瘤器官会随时间自然增殖，体现其"无限生长"的特性。

**触发机制**:
```
基础触发间隔: 180秒（3分钟）
每个肿瘤器官减少间隔: 10秒
最短触发间隔: 60秒（1分钟）
```

**触发概率**:
```
基础概率: 30%
增殖度修正: 每级增殖度+10%概率
最大概率: 70%（避免过于频繁）
```

**增殖方式**:
- 优先选择胸腔内已存在的肿瘤器官类型进行复制
- 如果胸腔内没有肿瘤器官，随机生成一个基础肿瘤器官（胃、肠、肺）
- 变异概率随增殖度提升（0%/10%/20%/30%/30%）

**代码实现建议**:
```java
// 事件：tick（服务器端）
public void onServerTick(Player player) {
    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    if (tumorCount == 0) return;

    // 计算触发间隔
    int interval = Math.max(60, 180 - (tumorCount * 10));

    // 检查是否应该触发
    if (player.tickCount % (interval * 20) == 0) {
        // 计算触发概率
        int proliferationDegree = calculateProliferationDegree(data);
        float probability = Math.min(0.3f + (proliferationDegree * 0.1f), 0.7f);

        if (Math.random() < probability) {
            triggerProliferation(player, data, "TIME");
        }
    }
}
```

### 2. 击杀增殖（战斗触发）

**核心概念**: 击败敌人会刺激肿瘤增殖，体现"吞噬、生长"的特性。

**触发机制**:
```
基础触发概率: 10%
每个肿瘤器官+2%概率
最大概率: 30%
增殖度修正: 每级增殖度+5%概率
```

**特殊规则**:
- 击杀BOSS级怪物时，增殖概率翻倍
- 击杀多个敌人在短时间内（5秒），触发概率累积（每次+5%，最高+20%）
- 连续击杀（10秒内击杀3个以上敌人）触发"增殖狂暴"状态：

**增殖狂暴状态**:
```
持续时间: 10秒
效果: 接下来3次增殖必定触发，且增殖时不消耗增殖度
代价: 狂暴结束后，增殖度强制提升1级
```

**增殖方式**:
- 优先复制攻击性器官（心脏、肌肉、肾脏）
- 击杀BOSS时有30%概率变异为随机肿瘤器官
- 连续击杀时有20%概率生成临时肿瘤器官

**代码实现建议**:
```java
// 事件：kill（击杀实体）
public void onKill(Player player, Entity target) {
    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    if (tumorCount == 0) return;

    // 计算触发概率
    int proliferationDegree = calculateProliferationDegree(data);
    float probability = Math.min(0.1f + (tumorCount * 0.02f) + (proliferationDegree * 0.05f), 0.3f);

    // BOSS级怪物概率翻倍
    if (isBoss(target)) {
        probability *= 2.0f;
    }

    // 检查连续击杀
    TumorData tumorData = getTumorData(player);
    long currentTime = System.currentTimeMillis();
    if (currentTime - tumorData.getLastKillTime() < 5000) { // 5秒内
        tumorData.setComboCount(tumorData.getComboCount() + 1);
        probability += Math.min(tumorData.getComboCount() * 0.05f, 0.2f);

        // 触发增殖狂暴
        if (tumorData.getComboCount() >= 3 && !tumorData.isInFrenzy()) {
            tumorData.setInFrenzy(true);
            tumorData.setFrenzyEndTime(currentTime + 10000); // 10秒
            player.sendSystemMessage(Component.translatable("tumor.frenzy.start"));
        }
    } else {
        tumorData.setComboCount(1);
    }
    tumorData.setLastKillTime(currentTime);

    // 触发增殖
    if (Math.random() < probability || tumorData.isInFrenzy()) {
        triggerProliferation(player, data, "KILL");

        // 增殖狂暴状态下不消耗增殖次数
        if (tumorData.isInFrenzy()) {
            tumorData.setFrenzyProliferationCount(tumorData.getFrenzyProliferationCount() + 1);
            if (tumorData.getFrenzyProliferationCount() >= 3) {
                tumorData.setInFrenzy(false);
                increaseProliferationDegree(player, 1); // 强制提升1级
                player.sendSystemMessage(Component.translatable("tumor.frenzy.end"));
            }
        }
    }
}
```

### 3. 伤害增殖（双向触发）

**核心概念**: 伤害（无论造成还是承受）都会刺激肿瘤增殖，体现"应激反应"。

#### 3.1 造成伤害触发

**触发机制**:
```
基础触发概率: 5%
每100点伤害+5%概率
单次最大概率: 25%
增殖度修正: 每级增殖度+3%概率
冷却时间: 5秒（避免过于频繁）
```

**增殖方式**:
- 优先复制攻击性器官（心脏、肌肉、肾脏）
- 高伤害单次（200+）有20%概率变异为随机肿瘤器官
- 暴击时有15%概率生成临时肿瘤器官

#### 3.2 承受伤害触发

**触发机制**:
```
基础触发概率: 8%
每50点承受伤害+5%概率
单次最大概率: 30%
增殖度修正: 每级增殖度+5%概率
冷却时间: 10秒（承伤触发频率较低）
```

**增殖方式**:
- 优先复制防御性器官（脾脏、肝脏、心脏）
- 承受大量伤害（单次150+）有30%概率变异为随机肿瘤器官
- 低血量（<30%）时有25%概率生成临时肿瘤器官

**代码实现建议**:
```java
// 事件：hurt（承受伤害）
public void onHurt(Player player, DamageSource source, float amount) {
    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    if (tumorCount == 0) return;

    TumorData tumorData = getTumorData(player);

    // 检查冷却时间
    if (System.currentTimeMillis() - tumorData.getLastDamageTakenProliferationTime() < 10000) {
        return;
    }

    // 计算触发概率
    int proliferationDegree = calculateProliferationDegree(data);
    float probability = Math.min(0.08f + (amount / 50.0f * 0.05f) + (proliferationDegree * 0.05f), 0.3f);

    // 低血量加成
    if (player.getHealth() < player.getMaxHealth() * 0.3f) {
        probability += 0.1f;
    }

    // 触发增殖
    if (Math.random() < probability) {
        triggerProliferation(player, data, "DAMAGE_TAKEN");
        tumorData.setLastDamageTakenProliferationTime(System.currentTimeMillis());
    }
}

// 事件：attack（造成伤害）
public void onAttack(Player player, Entity target, float amount) {
    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    if (tumorCount == 0) return;

    TumorData tumorData = getTumorData(player);

    // 检查冷却时间
    if (System.currentTimeMillis() - tumorData.getLastDamageDealtProliferationTime() < 5000) {
        return;
    }

    // 计算触发概率
    int proliferationDegree = calculateProliferationDegree(data);
    float probability = Math.min(0.05f + (amount / 100.0f * 0.05f) + (proliferationDegree * 0.03f), 0.25f);

    // 触发增殖
    if (Math.random() < probability) {
        triggerProliferation(player, data, "DAMAGE_DEALT");
        tumorData.setLastDamageDealtProliferationTime(System.currentTimeMillis());
    }
}
```

### 4. 特定事件增殖

**核心概念**: 某些特殊事件会触发增殖，体现肿瘤的"不稳定性"。

#### 4.1 胸腔关闭时增殖

**触发机制**:
```
基础触发概率: 15%
增殖度修正: 每级增殖度+5%概率
胸腔关闭时装备新肿瘤器官: 触发概率翻倍
```

**增殖方式**:
- 优先复制胸腔内新增的器官类型
- 有20%概率随机变异为任何肿瘤器官
- 有10%概率触发"增殖爆发"（同时增殖3个器官）

#### 4.2 药水使用时增殖

**触发机制**:
```
基础触发概率: 10%
增殖度修正: 每级增殖度+3%概率
使用强力药水（II级、延长版）: 概率翻倍
```

**增殖方式**:
- 根据药水类型决定增殖器官：
  - 治疗类药水 → 心脏、脾脏
  - 力量类药水 → 肌肉、肾脏
  - 速度类药水 → 肺脏、肌肉
  - 抗性类药水 → 肝脏、脾脏
  - 负面药水 → 随机肿瘤器官（变异概率+20%）

#### 4.3 低血量时增殖（紧急增殖）

**触发机制**:
```
触发条件: 生命值低于20%
触发概率: 40%
冷却时间: 30秒（避免战斗中频繁触发）
```

**增殖方式**:
- 优先生成防御性器官（心脏、脾脏、肝脏）
- 有30%概率生成临时肿瘤器官（持续60秒）
- 有20%概率触发"狂暴增殖"（随机生成3个肿瘤器官，但之后30秒内持续失血）

**代码实现建议**:
```java
// 事件：chestCavityClose
public void onChestCavityClose(Player player, ChestCavityData data) {
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    if (tumorCount == 0) return;

    // 计算触发概率
    int proliferationDegree = calculateProliferationDegree(data);
    float probability = Math.min(0.15f + (proliferationDegree * 0.05f), 0.4f);

    // 检查是否装备了新肿瘤器官
    if (hasNewTumorOrgans(data)) {
        probability *= 2.0f;
    }

    // 触发增殖
    if (Math.random() < probability) {
        triggerProliferation(player, data, "CHEST_CLOSE");

        // 10%概率触发增殖爆发
        if (Math.random() < 0.1f) {
            player.sendSystemMessage(Component.translatable("tumor.proliferation.burst"));
            for (int i = 0; i < 3; i++) {
                triggerProliferation(player, data, "BURST");
            }
        }
    }
}

// 事件：lowHealth（低血量检测）
public void onLowHealth(Player player) {
    if (player.getHealth() > player.getMaxHealth() * 0.2f) return;

    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    if (tumorCount == 0) return;

    TumorData tumorData = getTumorData(player);

    // 检查冷却时间
    if (System.currentTimeMillis() - tumorData.getLastEmergencyProliferationTime() < 30000) {
        return;
    }

    // 触发增殖
    if (Math.random() < 0.4f) {
        triggerProliferation(player, data, "EMERGENCY");
        tumorData.setLastEmergencyProliferationTime(System.currentTimeMillis());

        // 20%概率触发狂暴增殖
        if (Math.random() < 0.2f) {
            player.sendSystemMessage(Component.translatable("tumor.proliferation.frenzy"));
            for (int i = 0; i < 3; i++) {
                triggerProliferation(player, data, "FRENZY");
            }
            // 30秒内持续失血
            applyBleedingEffect(player, 30, 1.0f);
        }
    }
}
```

---

## 增殖方式系统

### 1. 复制增殖（Replication）

**描述**: 生成与原器官完全相同的肿瘤器官。

**触发条件**:
- 所有增殖触发条件的默认方式
- 概率最高（相对于其他增殖方式）

**机制**:
```
1. 从胸腔内随机选择一个肿瘤器官（非临时器官）
2. 在胸腔空槽位生成该器官的副本
3. 如果胸腔已满，增殖失败，触发"拥挤效应"
4. 复制的器官属性完全相同，包括NBT标签
```

**拥挤效应**:
```
触发条件: 胸腔已满时尝试增殖
效果:
- 10%概率随机摧毁一个肿瘤器官（可以为被复制的器官）
- 90%概率增殖失败，但增殖度+1
```

### 2. 变异增殖（Mutation）

**描述**: 将原器官变异为其他类型的肿瘤器官。

**触发条件**:
- 基础变异概率随增殖度提升（0%/10%/20%/30%/30%）
- 某些特殊条件额外增加变异概率：
  - 肿瘤阑尾觉醒：变异概率+50%
  - 肿瘤肠子处于"中毒状态"：变异概率+20%
  - 肿瘤肝脏毒素层数≥6：变异概率+30%

**机制**:
```
1. 选择一个源肿瘤器官（或随机选择）
2. 根据变异权重随机选择目标器官类型
3. 源器官有50%概率保留，有50%概率被替换
4. 生成的目标器官属性为标准值（不继承源器官的NBT标签）
```

**变异权重表**:

| 目标器官 | 权重 | 条件修正 |
|---------|------|----------|
| 肿瘤胃 | 15 | 最常见的变异结果 |
| 肿瘤肺脏 | 12 | 较常见 |
| 肿瘤肠子 | 12 | 较常见 |
| 肿瘤肾脏 | 10 | 中等概率 |
| 肿瘤脾脏 | 10 | 中等概率 |
| 肿瘤肝脏 | 8 | 较少见 |
| 肿瘤肌肉 | 8 | 较少见 |
| 肿瘤心脏 | 5 | 稀有变异 |
| 肿瘤阑尾 | 2 | 极稀有变异（传说！） |

**代码实现建议**:
```java
public Item rollMutationOrgan(Item sourceOrgan, ChestCavityData data) {
    // 定义变异权重
    Map<Item, Integer> weights = new HashMap<>();
    weights.put(TUMOR_STOMACH, 15);
    weights.put(TUMOR_LUNG, 12);
    weights.put(TUMOR_INTESTINE, 12);
    weights.put(TUMOR_KIDNEY, 10);
    weights.put(TUMOR_SPLEEN, 10);
    weights.put(TUMOR_LIVER, 8);
    weights.put(TUMOR_MUSCLE, 8);
    weights.put(TUMOR_HEART, 5);
    weights.put(TUMOR_APPENDIX, 2);

    // 条件修正
    if (data.hasOrgan(TUMOR_APPENDIX)) {
        TumorData tumorData = getTumorData(player);
        if (tumorData.getAwakeningEffect() == AwakeningEffect.PERFECT_ADAPTATION) {
            // 传说器官觉醒时，变异权重重新分配
            weights.put(TUMOR_HEART, 20); // 大幅提升稀有器官权重
            weights.put(TUMOR_MUSCLE, 15);
            weights.put(TUMOR_LIVER, 15);
            weights.put(TUMOR_APPENDIX, 10);
        }
    }

    // 计算总权重
    int totalWeight = weights.values().stream().mapToInt(Integer::intValue).sum();

    // 随机选择
    int random = (int)(Math.random() * totalWeight);
    int currentWeight = 0;

    for (Map.Entry<Item, Integer> entry : weights.entrySet()) {
        currentWeight += entry.getValue();
        if (random < currentWeight) {
            return entry.getKey();
        }
    }

    return TUMOR_STOMACH; // 默认
}
```

### 3. 临时增殖（Temporary）

**描述**: 生成短期存在的临时肿瘤器官，持续时间结束后自动消失。

**触发条件**:
- 击杀增殖连续击杀时有20%概率
- 伤害增殖低血量时有25%概率
- 紧急增殖时有30%概率
- 肿瘤阑尾觉醒时有50%概率

**机制**:
```
1. 生成一个临时肿瘤器官（标记为temporary）
2. 临时器官持续60秒（3600 tick）
3. 临时器官提供正常器官100%的效果
4. 持续时间结束后，临时器官自动消失
5. 临时器官不参与增殖度计算
6. 临时器官可以被正常器官替换
```

**临时器官标记**:
```java
// NBT标签
{
    "temporary": true,
    "duration": 3600, // tick
    "creation_time": 当前时间tick
}
```

**临时器官优先级**:
```
1. 优先复制胸腔内已有的器官类型
2. 如果胸腔内没有肿瘤器官，随机生成基础器官
3. 临时器官优先占据空槽位
4. 临时器官可以替换其他临时器官
5. 临时器官不能替换永久器官
```

**代码实现建议**:
```java
public void createTemporaryOrgan(Player player, ChestCavityData data, Item organType) {
    // 创建临时器官
    ItemStack temporaryOrgan = new ItemStack(organType);

    // 添加NBT标签
    CompoundTag nbt = new CompoundTag();
    nbt.putBoolean("temporary", true);
    nbt.putInt("duration", 3600); // 60秒
    nbt.putLong("creation_time", player.level().getGameTime());
    temporaryOrgan.setTag(nbt);

    // 寻找空槽位
    int emptySlot = findEmptySlot(data);
    if (emptySlot == -1) {
        // 查找可以替换的临时器官
        int temporarySlot = findTemporarySlot(data);
        if (temporarySlot != -1) {
            data.insertItem(temporarySlot, temporaryOrgan, false);
            player.sendSystemMessage(Component.translatable("tumor.temporary.replaced"));
        } else {
            player.sendSystemMessage(Component.translatable("tumor.proliferation.failed.full"));
            return;
        }
    } else {
        data.insertItem(emptySlot, temporaryOrgan, false);
    }

    // 播放音效和粒子效果
    playProliferationEffects(player, organType, true);
    player.sendSystemMessage(Component.translatable("tumor.temporary.created",
        Component.translatable(organType.getDescriptionId())));
}

// 事件：tick（检查临时器官持续时间）
public void onServerTick(Player player) {
    ChestCavityData data = getChestCavityData(player);
    TumorData tumorData = getTumorData(player);

    // 检查所有槽位
    for (int i = 0; i < data.getSlots(); i++) {
        ItemStack stack = data.getStackInSlot(i);
        if (stack.isEmpty() || !stack.hasTag()) continue;

        CompoundTag nbt = stack.getTag();
        if (!nbt.getBoolean("temporary")) continue;

        // 检查持续时间
        int duration = nbt.getInt("duration");
        long creationTime = nbt.getLong("creation_time");
        long currentTime = player.level().getGameTime();

        if (currentTime - creationTime >= duration) {
            // 移除临时器官
            data.setStackInSlot(i, ItemStack.EMPTY);
            player.sendSystemMessage(Component.translatable("tumor.temporary.expired",
                Component.translatable(stack.getDescriptionId())));

            // 播放消失音效和粒子效果
            playTemporaryOrganExpireEffects(player, stack.getItem());
        }
    }
}
```

### 4. 升级增殖（Evolution）

**描述**: 现有器官进化为更强形态，属性提升，副作用降低。

**触发条件**:
- 增殖度达到"危险期"（等级3）时有10%概率
- 增殖度达到"失控期"（等级4）时有20%概率
- 装备6个以上肿瘤器官且击败BOSS时有15%概率
- 肿瘤阑尾觉醒"完美适应"时有30%概率

**机制**:
```
1. 随机选择一个可进化的肿瘤器官
2. 将其替换为"进化版本"（evolved标记）
3. 进化器官属性提升50%
4. 进化器官副作用降低30%
5. 进化器官有独特的视觉特效
```

**进化器官属性**:

| 器官 | 原始属性 | 进化属性 | 进化副作用 |
|------|---------|---------|-----------|
| 肿瘤心脏 | +4 HEALTH, +2 STRENGTH | +6 HEALTH, +3 STRENGTH | 恶性跳动概率20%→14% |
| 肿瘤肺脏 | +3 BREATH, +2 SPEED | +4 BREATH, +3 SPEED | 氧气消耗-50%→-35% |
| 肿瘤胃 | +2 HEALTH, +1 STRENGTH | +3 HEALTH, +2 STRENGTH | 饥饿消耗+100%→+70% |
| 肿瘤肠子 | +1 HEALTH | +2 HEALTH | 代谢状态切换周期30秒→40秒 |
| 肿瘤肾脏 | +3 STRENGTH, +1 SPEED | +4 STRENGTH, +2 SPEED | 过滤超载概率30%→21% |
| 肿瘤脾脏 | +3 HEALTH | +5 HEALTH | 脆弱防线概率10%→7% |
| 肿瘤肝脏 | +2 HEALTH, +1 STRENGTH, +1 SPEED | +3 HEALTH, +2 STRENGTH, +2 SPEED | 毒素积累速度-30% |
| 肿瘤阑尾 | +1 HEALTH | +3 HEALTH | 觉醒概率5%→8%，但阑尾炎持续时间-50% |
| 肿瘤肌肉 | +4 STRENGTH, +3 SPEED | +6 STRENGTH, +4 SPEED | 肌肉痉挛概率20%→14% |

**进化器官标记**:
```java
// NBT标签
{
    "evolved": true,
    "generation": 1, // 进化代数（理论上可以多次进化）
    "evolution_bonus": 0.5 // 属性加成50%
}
```

**代码实现建议**:
```java
public void evolveOrgan(Player player, ChestCavityData data, int slot) {
    ItemStack originalOrgan = data.getStackInSlot(slot);
    if (originalOrgan.isEmpty()) return;

    // 创建进化器官
    ItemStack evolvedOrgan = originalOrgan.copy();
    CompoundTag nbt = evolvedOrgan.getOrCreateTag();

    // 标记为进化器官
    nbt.putBoolean("evolved", true);
    nbt.putInt("generation", nbt.getInt("generation") + 1);
    nbt.putDouble("evolution_bonus", 1.5); // 属性提升50%

    // 替换器官
    data.setStackInSlot(slot, evolvedOrgan);

    // 播放进化特效
    playEvolutionEffects(player, originalOrgan.getItem());
    player.sendSystemMessage(Component.translatable("tumor.evolution.success",
        Component.translatable(evolvedOrgan.getDescriptionId())));

    // 重新计算属性
    recalculateOrganAttributes(player, data);
}
```

---

## 增殖限制机制

### 1. 最大增殖数量限制

**核心概念**: 防止肿瘤器官无限增殖导致游戏崩溃或数值失控。

**限制规则**:
```
基础最大数量: 15个肿瘤器官
每个"肿瘤肌肉"额外+1个最大数量
每个"肿瘤心脏"额外+1个最大数量
进化器官额外+1个最大数量
最大硬上限: 27个（胸腔总槽位数）
```

**达到上限时的处理**:
```
1. 增殖触发时，如果已达到上限：
   - 20%概率随机摧毁一个肿瘤器官
   - 80%概率增殖失败，但触发"拥挤效应"
2. "拥挤效应":
   - 增殖度+1
   - 所有肿瘤器官效果-10%（持续30秒）
   - 玩家受到3点魔法伤害
```

**代码实现建议**:
```java
public int getMaxTumorCount(Player player, ChestCavityData data) {
    int baseMax = 15;

    // 肌肉和心脏额外增加上限
    baseMax += data.getOrganCount(TUMOR_MUSCLE);
    baseMax += data.getOrganCount(TUMOR_HEART);

    // 进化器官额外增加上限
    for (int i = 0; i < data.getSlots(); i++) {
        ItemStack stack = data.getStackInSlot(i);
        if (!stack.isEmpty() && stack.hasTag()) {
            CompoundTag nbt = stack.getTag();
            if (nbt.getBoolean("evolved")) {
                baseMax++;
            }
        }
    }

    return Math.min(baseMax, 27); // 硬上限27
}

public boolean canProliferate(Player player, ChestCavityData data) {
    int currentCount = data.getOrganCount(TUMOR_TAG);
    int maxCount = getMaxTumorCount(player, data);

    if (currentCount < maxCount) return true;

    // 达到上限，处理拥挤效应
    if (Math.random() < 0.2f) {
        // 随机摧毁一个器官
        destroyRandomTumorOrgan(player, data);
        return true;
    } else {
        // 触发拥挤效应
        applyCrowdingEffect(player, data);
        return false;
    }
}

public void destroyRandomTumorOrgan(Player player, ChestCavityData data) {
    List<Integer> tumorSlots = new ArrayList<>();
    for (int i = 0; i < data.getSlots(); i++) {
        ItemStack stack = data.getStackInSlot(i);
        if (!stack.isEmpty() && isTumorOrgan(stack.getItem())) {
            tumorSlots.add(i);
        }
    }

    if (tumorSlots.isEmpty()) return;

    // 随机选择一个器官
    int slot = tumorSlots.get((int)(Math.random() * tumorSlots.size()));
    ItemStack destroyedOrgan = data.getStackInSlot(slot);

    // 摧毁器官
    data.setStackInSlot(slot, ItemStack.EMPTY);

    // 播放摧毁特效
    playOrganDestroyEffects(player, destroyedOrgan.getItem());
    player.sendSystemMessage(Component.translatable("tumor.destroy",
        Component.translatable(destroyedOrgan.getDescriptionId())));
}

public void applyCrowdingEffect(Player player, ChestCavityData data) {
    TumorData tumorData = getTumorData(player);

    // 增加增殖度
    tumorData.setProliferationDegree(tumorData.getProliferationDegree() + 1);

    // 所有肿瘤器官效果-10%
    tumorData.setCrowdingPenaltyEndTime(System.currentTimeMillis() + 30000);

    // 玩家受到魔法伤害
    player.hurt(player.damageSources().magic(), 3.0f);

    player.sendSystemMessage(Component.translatable("tumor.crowding.effect"));
}
```

### 2. 增殖冷却时间

**核心概念**: 防止增殖过于频繁，给玩家喘息和决策的时间。

**冷却规则**:
```
基础冷却时间: 10秒
每个肿瘤器官减少0.5秒冷却
最短冷却时间: 3秒
```

**特殊触发条件的独立冷却**:
```
时间增殖: 独立冷却（基于触发间隔）
击杀增殖: 独立冷却5秒
伤害增殖: 独立冷却5秒（造成）/10秒（承受）
紧急增殖: 独立冷却30秒
```

**冷却时间提示**:
```
- 冷却期间，玩家会收到视觉提示（胸腔图标变灰）
- 鼠标悬停在胸腔界面时，显示冷却倒计时
- 冷却结束时播放提示音效
```

**代码实现建议**:
```java
public boolean isProliferationOnCooldown(Player player) {
    TumorData tumorData = getTumorData(player);
    long currentTime = System.currentTimeMillis();

    // 计算冷却时间
    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);
    int cooldownTime = Math.max(3000, 10000 - (tumorCount * 500));

    // 检查冷却
    if (currentTime - tumorData.getLastProliferationTime() < cooldownTime) {
        return true;
    }

    return false;
}

public long getProliferationCooldownRemaining(Player player) {
    TumorData tumorData = getTumorData(player);
    ChestCavityData data = getChestCavityData(player);
    int tumorCount = data.getOrganCount(TUMOR_TAG);
    int cooldownTime = Math.max(3000, 10000 - (tumorCount * 500));

    long elapsedTime = System.currentTimeMillis() - tumorData.getLastProliferationTime();
    return Math.max(0, cooldownTime - elapsedTime);
}
```

### 3. 增殖代价

**核心概念**: 增殖不是免费的，需要付出代价，体现"高风险高收益"的设计。

**代价类型**:

#### 3.1 生命代价
```
每次增殖失去2-5点生命（随机）
增殖度越高，生命代价越高：
- 稳定期: 2点
- 活跃期: 3点
- 爆发期: 4点
- 危险期: 5点
- 失控期: 6点
```

#### 3.2 饥饿代价
```
每次增殖消耗4点饥饿值
增殖度越高，饥饿代价越高：
- 稳定期: 4点
- 活跃期: 5点
- 爆发期: 6点
- 危险期: 8点
- 失控期: 10点
```

#### 3.3 增殖度提升
```
每次增殖后，增殖度有50%概率+1
如果增殖方式是"变异增殖"，增殖度必定+1
如果增殖方式是"升级增殖"，增殖度-1
```

#### 3.4 副作用激活
```
每次增殖有10%概率触发一个肿瘤器官的副作用
增殖度越高，副作用激活概率越高：
- 稳定期: 5%
- 活跃期: 10%
- 爆发期: 15%
- 危险期: 20%
- 失控期: 30%
```

**代码实现建议**:
```java
public void applyProliferationCost(Player player, ChestCavityData data, String proliferationType) {
    TumorData tumorData = getTumorData(player);
    int proliferationDegree = calculateProliferationDegree(data);

    // 1. 生命代价
    float healthCost = 2.0f + proliferationDegree; // 2-6点
    player.hurt(player.damageSources().magic(), healthCost);

    // 2. 饥饿代价
    int hungerCost = 4 + proliferationDegree; // 4-8点
    FoodData foodData = player.getFoodData();
    foodData.setFoodLevel(Math.max(0, foodData.getFoodLevel() - hungerCost));

    // 3. 增殖度提升
    if (proliferationType.equals("MUTATION")) {
        tumorData.setProliferationDegree(tumorData.getProliferationDegree() + 1);
    } else if (proliferationType.equals("EVOLUTION")) {
        tumorData.setProliferationDegree(Math.max(0, tumorData.getProliferationDegree() - 1));
    } else {
        if (Math.random() < 0.5f) {
            tumorData.setProliferationDegree(tumorData.getProliferationDegree() + 1);
        }
    }

    // 4. 副作用激活
    float sideEffectChance = 0.05f + (proliferationDegree * 0.05f);
    if (Math.random() < sideEffectChance) {
        triggerRandomSideEffect(player, data);
    }
}

public void triggerRandomSideEffect(Player player, ChestCavityData data) {
    List<Item> tumorOrgans = new ArrayList<>();
    for (int i = 0; i < data.getSlots(); i++) {
        ItemStack stack = data.getStackInSlot(i);
        if (!stack.isEmpty() && isTumorOrgan(stack.getItem())) {
            tumorOrgans.add(stack.getItem());
        }
    }

    if (tumorOrgans.isEmpty()) return;

    // 随机选择一个器官触发副作用
    Item organ = tumorOrgans.get((int)(Math.random() * tumorOrgans.size()));

    if (organ == TUMOR_HEART) {
        // 恶性跳动
        player.hurt(player.damageSources().magic(), 2.0f);
        player.sendSystemMessage(Component.translatable("tumor.side_effect.malignant_beat"));
    } else if (organ == TUMOR_STOMACH) {
        // 剧烈饥饿
        FoodData foodData = player.getFoodData();
        foodData.setFoodLevel(Math.max(0, foodData.getFoodLevel() - 6));
        player.sendSystemMessage(Component.translatable("tumor.side_effect.ravenous_hunger"));
    } else if (organ == TUMOR_MUSCLE) {
        // 肌肉痉挛
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
        player.sendSystemMessage(Component.translatable("tumor.side_effect.muscle_spasm"));
    }
    // ... 其他器官的副作用
}
```

### 4. 增殖度衰减

**核心概念**: 如果玩家不主动增殖，增殖度会随时间自然衰减，避免被迫永远处于高风险状态。

**衰减规则**:
```
基础衰减速度: 每120秒（2分钟）-1级增殖度
每个肿瘤心脏减少12秒衰减时间
每个肿瘤脾脏减少12秒衰减时间
最短衰减时间: 30秒
```

**特殊条件**:
```
- 胸腔打开时：增殖度衰减暂停
- 战斗中（受到或造成伤害）：增殖度衰减暂停
- 增殖度达到"失控期"时：增殖度不衰减（必须手动降低）
```

**主动降低增殖度**:
```
1. 移除肿瘤器官：每个移除的器官降低1级增殖度
2. 升级增殖：增殖度-1
3. 使用特殊物品（如"抑制剂"）：增殖度-2（冷却时间5分钟）
```

**代码实现建议**:
```java
// 事件：tick（每秒检查一次）
public void onServerTick(Player player) {
    TumorData tumorData = getTumorData(player);

    // 如果增殖度为0，跳过
    if (tumorData.getProliferationDegree() <= 0) return;

    ChestCavityData data = getChestCavityData(player);

    // 检查是否应该衰减
    if (shouldDecayProliferationDegree(player, data)) {
        long currentTime = System.currentTimeMillis();

        // 计算衰减间隔
        int decayInterval = Math.max(30000, 120000 -
            (data.getOrganCount(TUMOR_HEART) * 12000) -
            (data.getOrganCount(TUMOR_SPLEEN) * 12000));

        // 检查是否应该衰减
        if (currentTime - tumorData.getLastProliferationDecayTime() >= decayInterval) {
            tumorData.setProliferationDegree(tumorData.getProliferationDegree() - 1);
            tumorData.setLastProliferationDecayTime(currentTime);

            player.sendSystemMessage(Component.translatable("tumor.proliferation_decay"));
        }
    }
}

public boolean shouldDecayProliferationDegree(Player player, ChestCavityData data) {
    // 胸腔打开时不衰减
    if (isChestCavityOpen(player, data)) return false;

    // 战斗中不衰减
    TumorData tumorData = getTumorData(player);
    long currentTime = System.currentTimeMillis();
    if (currentTime - tumorData.getLastCombatTime() < 10000) { // 10秒内
        return false;
    }

    // 失控期不衰减
    if (tumorData.getProliferationDegree() >= 4) return false;

    return true;
}
```

---

## 增殖视觉反馈

### 1. 粒子效果设计

**增殖触发时**:
```
粒子类型: ITEM（肿瘤器官的图标粒子）
生成位置: 玩家胸部位置
粒子数量: 20-30个
扩散范围: 1.5格半径
持续时间: 1秒
颜色:
- 复制增殖: 暗紫色（类似源器官）
- 变异增殖: 鲜红色（强调突变）
- 临时增殖: 半透明白色（强调临时性）
- 升级增殖: 金色（强调进化）
```

**增殖完成时**:
```
粒子类型: POOF（膨胀消失效果）
生成位置: 胸腔界面中新器官的位置
粒子数量: 15个
扩散范围: 0.5格半径
持续时间: 0.5秒
颜色: 与增殖类型对应
```

**临时器官消失时**:
```
粒子类型: SQUID_INK（墨汁粒子）
生成位置: 玩家胸部位置
粒子数量: 25个
扩散范围: 2格半径
持续时间: 1.5秒
颜色: 暗灰色（强调消失）
```

**升级增殖时**:
```
粒子类型: TOTEM_OF_UNDYING（图腾粒子效果）
生成位置: 玩家全身
粒子数量: 50个
扩散范围: 3格半径
持续时间: 2秒
颜色: 金色→红色渐变
```

**代码实现建议**:
```java
public void playProliferationEffects(Player player, Item organType, boolean isTemporary) {
    Level level = player.level();

    // 1. 增殖触发粒子
    ParticleColor color = getProliferationParticleColor(isTemporary);
    for (int i = 0; i < 25; i++) {
        double offsetX = (Math.random() - 0.5) * 3.0;
        double offsetY = (Math.random() - 0.5) * 3.0;
        double offsetZ = (Math.random() - 0.5) * 3.0;

        level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(organType)),
            player.getX() + offsetX, player.getY() + 1.5 + offsetY, player.getZ() + offsetZ,
            0, 0, 0);
    }

    // 2. 音效
    player.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0f, 0.8f);

    // 3. 玩家闪烁效果
    if (!level.isClientSide) {
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 0, false, false));
    }
}

public ParticleColor getProliferationParticleColor(boolean isTemporary) {
    if (isTemporary) {
        return new ParticleColor(200, 200, 200); // 白色
    } else {
        return new ParticleColor(128, 0, 128); // 暗紫色
    }
}

public void playTemporaryOrganExpireEffects(Player player, Item organType) {
    Level level = player.level();

    // 1. 消失粒子
    for (int i = 0; i < 25; i++) {
        double offsetX = (Math.random() - 0.5) * 4.0;
        double offsetY = (Math.random() - 0.5) * 4.0;
        double offsetZ = (Math.random() - 0.5) * 4.0;

        level.addParticle(ParticleTypes.SQUID_INK,
            player.getX() + offsetX, player.getY() + 1.5 + offsetY, player.getZ() + offsetZ,
            0, 0, 0);
    }

    // 2. 音效
    player.playSound(SoundEvents.GENERIC_EXTINGUISH, 1.0f, 1.0f);
}

public void playEvolutionEffects(Player player, Item organType) {
    Level level = player.level();

    // 1. 图腾粒子效果
    for (int i = 0; i < 50; i++) {
        double offsetX = (Math.random() - 0.5) * 6.0;
        double offsetY = (Math.random() - 0.5) * 6.0;
        double offsetZ = (Math.random() - 0.5) * 6.0;

        level.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
            player.getX() + offsetX, player.getY() + 1.5 + offsetY, player.getZ() + offsetZ,
            0, 1.0, 0);
    }

    // 2. 音效
    player.playSound(SoundEvents.TOTEM_USE, 1.5f, 1.0f);

    // 3. 玩家高亮
    player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60, 0, false, false));
}
```

### 2. 音效设计

**增殖触发音效**:
```
音效: EVOKER_CAST_SPELL（唤术者施法音效）
音量: 1.0
音调: 0.8（略低于标准音调）
播放位置: 玩家位置
可听范围: 16格
```

**临时器官消失音效**:
```
音效: GENERIC_EXTINGUISH（熄灭音效）
音量: 1.0
音调: 1.0
播放位置: 玩家位置
可听范围: 8格
```

**升级增殖音效**:
```
音效: TOTEM_USE（图腾使用音效）
音量: 1.5
音调: 1.0
播放位置: 玩家位置
可听范围: 32格
```

**增殖失败音效**:
```
音效: VILLAGER_NO（村民拒绝音效）
音量: 0.8
音调: 1.2
播放位置: 玩家位置
可听范围: 8格
```

### 3. UI通知设计

**增殖成功通知**:
```
位置: 游戏界面右上角（成就通知位置）
标题: "肿瘤增殖！"
描述: "{器官名称} 已生成"
颜色: 暗紫色
持续时间: 3秒
图标: 新生成的器官图标
```

**增殖失败通知**:
```
位置: 游戏界面右上角
标题: "增殖失败"
描述: "{失败原因}"
颜色: 红色
持续时间: 2秒
图标: 叉号图标
```

**增殖度变化通知**:
```
位置: 游戏界面右侧中部（.ActionBar）
内容: "增殖度: {等级} ({等级名称})"
颜色: 根据等级变化
- 稳定期: 白色
- 活跃期: 绿色
- 爆发期: 黄色
- 危险期: 橙色
- 失控期: 红色
持续时间: 3秒
```

**临时器官即将消失警告**:
```
位置: 游戏界面顶部中央
标题: "临时器官即将消失"
描述: "{器官名称} 将在 {秒} 秒后消失"
颜色: 橙色
持续时间: 显示到消失为止
倒计时: 最后10秒开始闪烁
```

**代码实现建议**:
```java
public void showProliferationSuccessNotification(Player player, Item organType, boolean isTemporary) {
    Component title = Component.translatable("tumor.proliferation.success");
    Component description = Component.translatable(organType.getDescriptionId());

    if (isTemporary) {
        description = Component.literal("").append(description)
            .append(Component.translatable("tumor.temporary.suffix").withStyle(ChatFormatting.GRAY));
    }

    // 发送客户端包显示通知
    PacketHandler.sendToPlayer(new ProliferationNotificationPacket(title, description, organType, isTemporary), player);
}

public void showProliferationFailureNotification(Player player, Component reason) {
    Component title = Component.translatable("tumor.proliferation.failed").withStyle(ChatFormatting.RED);
    Component description = reason;

    PacketHandler.sendToPlayer(new ProliferationNotificationPacket(title, description, null, false), player);
}

public void showProliferationDegreeNotification(Player player, int degree) {
    Component message = Component.translatable("tumor.proliferation_degree",
        Component.literal(String.valueOf(degree)),
        Component.translatable("tumor.proliferation_degree." + degree));

    ChatFormatting color = switch (degree) {
        case 0 -> ChatFormatting.WHITE;
        case 1 -> ChatFormatting.GREEN;
        case 2 -> ChatFormatting.YELLOW;
        case 3 -> ChatFormatting.GOLD;
        case 4 -> ChatFormatting.RED;
        default -> ChatFormatting.DARK_RED;
    };

    player.sendSystemMessage(message.withStyle(color));
}

public void showTemporaryOrganWarning(Player player, Item organType, int remainingSeconds) {
    Component title = Component.translatable("tumor.temporary.expire_soon").withStyle(ChatFormatting.GOLD);
    Component description = Component.translatable("tumor.temporary.expire_soon.desc",
        Component.translatable(organType.getDescriptionId()),
        Component.literal(String.valueOf(remainingSeconds)));

    // 发送客户端包显示警告
    PacketHandler.sendToPlayer(new TemporaryOrganWarningPacket(title, description, remainingSeconds), player);
}
```

---

## 平衡性设计

### 1. 增殖收益分析

**短期收益**:
```
单个肿瘤器官平均价值:
- 基础属性: 2-4点属性
- 独特效果: 相当于1-2个附魔
- 搭配效果: 每个器官+5-10%属性

增殖收益:
- 复制增殖: 获得一个标准肿瘤器官（价值100%）
- 变异增殖: 获得一个随机肿瘤器官（价值50-150%）
- 临时增殖: 获得一个临时器官（价值50%，但免费）
- 升级增殖: 现有器官价值+50%
```

**长期收益**:
```
增殖度提升收益:
- 每级增殖度: +10%增殖触发概率
- 活跃期（等级1）: 增殖频率+20%
- 爆发期（等级2）: 增殖频率+40%，变异概率+10%
- 危险期（等级3）: 增殖频率+60%，变异概率+20%
- 失控期（等级4）: 增殖频率+80%，变异概率+30%
```

### 2. 增殖代价分析

**直接代价**:
```
每次增殖代价:
- 生命值: 2-6点（相当于1-3次普通攻击）
- 饥饿值: 4-10点（相当于1-2块食物）
- 增殖度提升: 50%概率+1级
- 副作用激活: 5-30%概率（取决于增殖度）
```

**长期代价**:
```
增殖度过高代价:
- 活跃期（等级1）: 无额外代价
- 爆发期（等级2）: 每次增殖额外-1点生命
- 危险期（等级3）: 每次增殖额外-2点生命，每10秒-1点生命
- 失控期（等级4）: 每次增殖额外-3点生命，每10秒-2点生命，20%概率器官摧毁
```

**风险收益比**:
```
最佳增殖范围: 活跃期（等级1）→ 爆发期（等级2）
- 收益: 增殖频率+20-40%，变异概率+10%
- 代价: 可接受的直接代价
- 风险: 低-中等

危险增殖范围: 危险期（等级3）→ 失控期（等级4）
- 收益: 增殖频率+60-80%，变异概率+20-30%
- 代价: 高额持续生命流失
- 风险: 高-极高（可能导致器官摧毁）
```

### 3. 不同器官的增殖差异

**高收益器官**:
```
肿瘤心脏:
- 增殖价值: 极高（每个肿瘤器官+5%最大生命）
- 增殖优先级: 高
- 增殖后果: 增殖度快速提升

肿瘤肌肉:
- 增殖价值: 极高（+4 STRENGTH, +3 SPEED）
- 增殖优先级: 高
- 增殖后果: 增殖度快速提升

肿瘤肝脏:
- 增殖价值: 高（全能型器官，+2/+1/+1）
- 增殖优先级: 中
- 增殖后果: 毒素系统复杂度增加
```

**低收益器官**:
```
肿瘤胃:
- 增殖价值: 低（+2/+1，但饥饿消耗+100%）
- 增殖优先级: 低
- 增殖后果: 饥饿管理困难

肿瘤肠子:
- 增殖价值: 中（+1，但随机状态）
- 增殖优先级: 低
- 增殖后果: 随机性增加

肿瘤阑尾:
- 增殖价值: 不确定（5%概率觉醒，95%概率阑尾炎）
- 增殖优先级: 极低
- 增殖后果: 完全随机
```

### 4. 与搭配系统的交互

**增殖与相邻搭配**:
```
正面交互:
- 增殖可以快速完成相邻搭配需求
- 例如: 需要肿瘤肺脏+肿瘤胃的"毒素扩散"搭配，增殖可以快速提供缺失的器官

负面交互:
- 增殖可能破坏已有的相邻搭配（如果增殖发生在相邻槽位）
- 例如: 肿瘤肺脏和肿瘤胃相邻时，如果增殖发生在它们之间，搭配会中断
```

**增殖与套装搭配**:
```
正面交互:
- 增殖可以快速达到套装数量要求
- 例如: 需要6个肿瘤器官的"肿瘤军团"套装，增殖可以快速达到

负面交互:
- 增殖可能导致超过套装最优数量
- 例如: 9个肿瘤器官的"不死之身"套装虽然强大，但代价极高，增殖可能导致不慎触发
```

**增殖与主题搭配**:
```
中性交互:
- 增殖可以帮助完成跨器官类型的主题搭配
- 例如: "肿瘤+九狱"的死亡拥抱搭配，增殖可以快速提供所需器官
```

### 5. 平衡性调整建议

**如果增殖过强**:
```
1. 降低增殖触发概率: 所有触发概率-20%
2. 增加增殖代价: 每次增殖生命代价+2点
3. 增加增殖冷却: 基础冷却时间+5秒
4. 降低最大增殖数量: 基础最大数量-3个
5. 增加增殖度衰减速度: 衰减间隔-30秒
```

**如果增殖过弱**:
```
1. 提高增殖触发概率: 所有触发概率+20%
2. 降低增殖代价: 每次增殖生命代价-2点
3. 降低增殖冷却: 基础冷却时间-5秒
4. 提高最大增殖数量: 基础最大数量+3个
5. 降低增殖度衰减速度: 衰减间隔+30秒
```

**如果变异过少**:
```
1. 提高变异概率: 每级增殖度变异概率+5%
2. 增加变异触发条件: 击杀BOSS时必定变异
3. 降低复制概率: 复制概率-10%，变异概率+10%
4. 添加变异催化剂: 特殊物品使用后下次增殖必定变异
```

**如果临时器官过少**:
```
1. 提高临时器官概率: 所有临时器官概率+5%
2. 增加临时器官持续时间: 60秒→90秒
3. 临时器官可以进化: 临时器官有10%概率变为永久器官
4. 临时器官效果提升: 100%→120%
```

---

## 技术实现方案

### 1. 数据结构设计

```java
// 肿瘤增殖数据类
public class TumorProliferationData {
    // 增殖度（0-4级）
    private int proliferationDegree;

    // 上次增殖时间（用于冷却检测）
    private long lastProliferationTime;

    // 上次增殖衰减时间
    private long lastProliferationDecayTime;

    // 上次战斗时间
    private long lastCombatTime;

    // 上次击杀时间
    private long lastKillTime;

    // 连续击杀计数
    private int comboCount;

    // 是否处于增殖狂暴状态
    private boolean inFrenzy;

    // 狂暴结束时间
    private long frenzyEndTime;

    // 狂暴期间增殖计数
    private int frenzyProliferationCount;

    // 上次紧急增殖时间
    private long lastEmergencyProliferationTime;

    // 上次造成伤害增殖时间
    private long lastDamageDealtProliferationTime;

    // 上次承受伤害增殖时间
    private long lastDamageTakenProliferationTime;

    // 拥挤惩罚结束时间
    private long crowdingPenaltyEndTime;

    // 当前增殖总数（用于统计和限制）
    private int totalProliferationCount;

    // 临时器官列表（存储槽位和过期时间）
    private Map<Integer, Long> temporaryOrgans;
}

// 增殖触发类型枚举
public enum ProliferationTrigger {
    TIME,           // 时间增殖
    KILL,           // 击杀增殖
    DAMAGE_DEALT,   // 造成伤害增殖
    DAMAGE_TAKEN,   // 承受伤害增殖
    CHEST_CLOSE,    // 胸腔关闭增殖
    EMERGENCY,      // 紧急增殖
    BURST,          // 增殖爆发
    FRENZY,         // 狂暴增殖
    MANUAL          // 手动增殖（未来可能的特殊物品）
}

// 增殖类型枚举
public enum ProliferationType {
    REPLICATION,    // 复制增殖
    MUTATION,       // 变异增殖
    TEMPORARY,      // 临时增殖
    EVOLUTION       // 升级增殖
}
```

### 2. 核心增殖逻辑

```java
public class TumorProliferationSystem {

    // 主增殖触发方法
    public void triggerProliferation(Player player, ChestCavityData data, String trigger) {
        // 1. 检查冷却
        if (isProliferationOnCooldown(player)) {
            return;
        }

        // 2. 检查最大数量限制
        if (!canProliferate(player, data)) {
            showProliferationFailureNotification(player,
                Component.translatable("tumor.proliferation.failed.max_count"));
            return;
        }

        // 3. 检查胸腔是否有空槽位
        if (!hasEmptySlot(data)) {
            handleCrowdedCavity(player, data, trigger);
            return;
        }

        // 4. 确定增殖类型
        ProliferationType type = determineProliferationType(player, data, trigger);

        // 5. 选择源器官（如果需要）
        Optional<Item> sourceOrgan = selectSourceOrgan(player, data, type, trigger);
        if (sourceOrgan.isEmpty() && type != ProliferationType.MUTATION) {
            // 如果没有源器官且不是变异增殖，取消增殖
            return;
        }

        // 6. 执行增殖
        ItemStack newOrgan = createNewOrgan(player, data, type, sourceOrgan, trigger);

        // 7. 插入新器官
        int slot = findOptimalSlot(data, newOrgan, type);
        data.insertItem(slot, newOrgan, false);

        // 8. 应用增殖代价
        applyProliferationCost(player, data, type.name());

        // 9. 更新增殖时间
        TumorProliferationData proliferationData = getProliferationData(player);
        proliferationData.setLastProliferationTime(System.currentTimeMillis());
        proliferationData.setTotalProliferationCount(proliferationData.getTotalProliferationCount() + 1);

        // 10. 播放视觉和音效
        boolean isTemporary = type == ProliferationType.TEMPORARY;
        playProliferationEffects(player, newOrgan.getItem(), isTemporary);

        // 11. 显示成功通知
        showProliferationSuccessNotification(player, newOrgan.getItem(), isTemporary);

        // 12. 重新计算器官属性
        recalculateOrganAttributes(player, data);

        // 13. 检查搭配效果
        checkSynergies(player, data);
    }

    // 确定增殖类型
    private ProliferationType determineProliferationType(Player player, ChestCavityData data,
            String trigger) {
        TumorProliferationData proliferationData = getProliferationData(player);
        int degree = calculateProliferationDegree(data);

        // 特殊条件：升级增殖
        if (degree >= 3 && Math.random() < 0.1f) return ProliferationType.EVOLUTION;
        if (degree >= 4 && Math.random() < 0.2f) return ProliferationType.EVOLUTION;
        if (proliferationData.isInFrenzy() && Math.random() < 0.15f) return ProliferationType.EVOLUTION;

        // 特殊条件：临时增殖
        if (trigger.equals("KILL") && proliferationData.getComboCount() >= 3) {
            if (Math.random() < 0.2f) return ProliferationType.TEMPORARY;
        }
        if (trigger.equals("DAMAGE_TAKEN") && player.getHealth() < player.getMaxHealth() * 0.3f) {
            if (Math.random() < 0.25f) return ProliferationType.TEMPORARY;
        }
        if (trigger.equals("EMERGENCY")) {
            if (Math.random() < 0.3f) return ProliferationType.TEMPORARY;
        }

        // 默认：变异增殖
        float mutationChance = 0.0f + (degree * 0.1f);
        if (degree >= 4) mutationChance = 0.3f;

        // 检查肿瘤阑尾觉醒
        if (data.hasOrgan(TUMOR_APPENDIX)) {
            TumorData tumorData = getTumorData(player);
            if (tumorData.getAwakeningEffect() == AwakeningEffect.PERFECT_ADAPTATION) {
                mutationChance += 0.5f;
            }
        }

        if (Math.random() < mutationChance) {
            return ProliferationType.MUTATION;
        }

        // 默认：复制增殖
        return ProliferationType.REPLICATION;
    }

    // 选择源器官
    private Optional<Item> selectSourceOrgan(Player player, ChestCavityData data,
            ProliferationType type, String trigger) {
        List<Item> tumorOrgans = new ArrayList<>();
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (!stack.isEmpty() && isTumorOrgan(stack.getItem()) && !isTemporaryOrgan(stack)) {
                tumorOrgans.add(stack.getItem());
            }
        }

        if (tumorOrgans.isEmpty()) {
            // 如果胸腔内没有肿瘤器官，返回空（变异增殖会处理这种情况）
            return Optional.empty();
        }

        // 根据触发条件和增殖类型选择源器官
        if (type == ProliferationType.REPLICATION || type == ProliferationType.EVOLUTION) {
            // 复制和升级增殖：根据触发条件优先选择特定器官
            if (trigger.equals("KILL") || trigger.equals("DAMAGE_DEALT")) {
                // 优先选择攻击性器官
                return selectOrganByPriority(tumorOrgans,
                    List.of(TUMOR_HEART, TUMOR_MUSCLE, TUMOR_KIDNEY));
            } else if (trigger.equals("DAMAGE_TAKEN") || trigger.equals("EMERGENCY")) {
                // 优先选择防御性器官
                return selectOrganByPriority(tumorOrgans,
                    List.of(TUMOR_SPLEEN, TUMOR_LIVER, TUMOR_HEART));
            } else {
                // 随机选择
                return Optional.of(tumorOrgans.get((int)(Math.random() * tumorOrgans.size())));
            }
        } else if (type == ProliferationType.MUTATION) {
            // 变异增殖：随机选择一个器官作为源
            return Optional.of(tumorOrgans.get((int)(Math.random() * tumorOrgans.size())));
        }

        return Optional.empty();
    }

    // 根据优先级选择器官
    private Optional<Item> selectOrganByPriority(List<Item> organs, List<Item> priority) {
        for (Item priorityOrgan : priority) {
            if (organs.contains(priorityOrgan)) {
                return Optional.of(priorityOrgan);
            }
        }
        // 如果没有优先器官，随机选择
        return Optional.of(organs.get((int)(Math.random() * organs.size())));
    }

    // 创建新器官
    private ItemStack createNewOrgan(Player player, ChestCavityData data, ProliferationType type,
            Optional<Item> sourceOrgan, String trigger) {
        Item organType;

        switch (type) {
            case REPLICATION -> {
                // 复制源器官
                if (sourceOrgan.isPresent()) {
                    organType = sourceOrgan.get();
                } else {
                    // 如果没有源器官，选择基础器官
                    organType = selectBasicTumorOrgan();
                }
                return new ItemStack(organType);
            }

            case MUTATION -> {
                // 变异为随机器官
                if (sourceOrgan.isPresent()) {
                    organType = rollMutationOrgan(sourceOrgan.get(), data);
                } else {
                    organType = rollMutationOrgan(null, data);
                }
                ItemStack newOrgan = new ItemStack(organType);

                // 50%概率移除源器官
                if (sourceOrgan.isPresent() && Math.random() < 0.5f) {
                    removeOrganFromCavity(data, sourceOrgan.get());
                }

                return newOrgan;
            }

            case TEMPORARY -> {
                // 创建临时器官
                if (sourceOrgan.isPresent()) {
                    organType = sourceOrgan.get();
                } else {
                    organType = selectBasicTumorOrgan();
                }
                ItemStack temporaryOrgan = new ItemStack(organType);

                // 添加临时标记
                CompoundTag nbt = new CompoundTag();
                nbt.putBoolean("temporary", true);
                nbt.putInt("duration", 3600); // 60秒
                nbt.putLong("creation_time", player.level().getGameTime());
                temporaryOrgan.setTag(nbt);

                return temporaryOrgan;
            }

            case EVOLUTION -> {
                // 升级现有器官
                if (sourceOrgan.isPresent()) {
                    ItemStack evolvedOrgan = new ItemStack(sourceOrgan.get());
                    CompoundTag nbt = evolvedOrgan.getOrCreateTag();
                    nbt.putBoolean("evolved", true);
                    nbt.putInt("generation", nbt.getInt("generation") + 1);
                    nbt.putDouble("evolution_bonus", 1.5);
                    evolvedOrgan.setTag(nbt);

                    // 移除未升级的器官
                    removeOrganFromCavity(data, sourceOrgan.get());

                    return evolvedOrgan;
                } else {
                    // 如果没有源器官，降级为复制增殖
                    return createNewOrgan(player, data, ProliferationType.REPLICATION, sourceOrgan, trigger);
                }
            }

            default -> {
                // 默认：复制增殖
                return createNewOrgan(player, data, ProliferationType.REPLICATION, sourceOrgan, trigger);
            }
        }
    }

    // 选择基础肿瘤器官（当胸腔内没有肿瘤器官时）
    private Item selectBasicTumorOrgan() {
        Item[] basicOrgans = {TUMOR_STOMACH, TUMOR_LUNG, TUMOR_INTESTINE};
        return basicOrgans[(int)(Math.random() * basicOrgans.length)];
    }

    // 变异器官选择（已在前面详细定义）
    private Item rollMutationOrgan(Item sourceOrgan, ChestCavityData data) {
        // 使用前面定义的变异权重系统
        // ...（详细代码见前面）
        return TUMOR_STOMACH; // 简化示例
    }

    // 处理拥挤胸腔
    private void handleCrowdedCavity(Player player, ChestCavityData data, String trigger) {
        TumorProliferationData proliferationData = getProliferationData(player);

        if (Math.random() < 0.2f) {
            // 20%概率随机摧毁一个器官
            destroyRandomTumorOrgan(player, data);

            // 增殖成功
            triggerProliferation(player, data, trigger);
        } else {
            // 80%概率触发拥挤效应
            applyCrowdingEffect(player, data);
            showProliferationFailureNotification(player,
                Component.translatable("tumor.proliferation.failed.crowded"));
        }
    }

    // 查找最优槽位
    private int findOptimalSlot(ChestCavityData data, ItemStack organ, ProliferationType type) {
        if (type == ProliferationType.TEMPORARY) {
            // 临时器官优先查找可以替换的临时器官槽位
            int temporarySlot = findTemporarySlot(data);
            if (temporarySlot != -1) {
                return temporarySlot;
            }
        }

        // 查找第一个空槽位
        return findEmptySlot(data);
    }

    // 检查是否是临时器官
    private boolean isTemporaryOrgan(ItemStack stack) {
        if (!stack.hasTag()) return false;
        CompoundTag nbt = stack.getTag();
        return nbt.getBoolean("temporary");
    }

    // 移除器官
    private void removeOrganFromCavity(ChestCavityData data, Item organ) {
        for (int i = 0; i < data.getSlots(); i++) {
            ItemStack stack = data.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == organ) {
                data.setStackInSlot(i, ItemStack.EMPTY);
                return;
            }
        }
    }
}
```

### 3. 事件系统集成

```java
// 增殖系统事件处理器
@EventBusSubscriber(modid = MODID, bus = EventBus.Bus.FORGE)
public class TumorProliferationEventHandler {

    private static final TumorProliferationSystem proliferationSystem = new TumorProliferationSystem();

    // Tick事件（服务器端）
    @SubscribeEvent
    public static void onServerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide) return;

        Player player = event.player;
        ChestCavityData data = getChestCavityData(player);
        if (data == null) return;

        int tumorCount = data.getOrganCount(TUMOR_TAG);
        if (tumorCount == 0) return;

        // 1. 时间增殖检测
        proliferationSystem.checkTimeProliferation(player, data);

        // 2. 临时器官持续时间检测
        proliferationSystem.checkTemporaryOrgans(player, data);

        // 3. 增殖度衰减检测
        proliferationSystem.checkProliferationDecay(player, data);

        // 4. 增殖狂暴状态检测
        proliferationSystem.checkFrenzyState(player, data);
    }

    // 击杀事件
    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestCavityData data = getChestCavityData(player);
            if (data == null) return;

            int tumorCount = data.getOrganCount(TUMOR_TAG);
            if (tumorCount == 0) return;

            proliferationSystem.handleKillProliferation(player, data, event.getEntity());
        }
    }

    // 造成伤害事件
    @SubscribeEvent
    public static void onDamageDealt(AttackEntityEvent event) {
        Player player = event.getEntity();
        ChestCavityData data = getChestCavityData(player);
        if (data == null) return;

        int tumorCount = data.getOrganCount(TUMOR_TAG);
        if (tumorCount == 0) return;

        proliferationSystem.handleDamageDealtProliferation(player, data, event.getTarget());
    }

    // 承受伤害事件
    @SubscribeEvent
    public static void onDamageTaken(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            ChestCavityData data = getChestCavityData(player);
            if (data == null) return;

            int tumorCount = data.getOrganCount(TUMOR_TAG);
            if (tumorCount == 0) return;

            proliferationSystem.handleDamageTakenProliferation(player, data, event.getAmount());

            // 记录战斗时间
            TumorProliferationData proliferationData = proliferationSystem.getProliferationData(player);
            proliferationData.setLastCombatTime(System.currentTimeMillis());
        }
    }

    // 胸腔关闭事件
    @SubscribeEvent
    public static void onChestCavityClose(ChestCavityCloseEvent event) {
        Player player = event.getPlayer();
        ChestCavityData data = event.getChestCavityData();

        int tumorCount = data.getOrganCount(TUMOR_TAG);
        if (tumorCount == 0) return;

        proliferationSystem.handleChestCloseProliferation(player, data);
    }

    // 药水使用事件
    @SubscribeEvent
    public static void onPotionUsed(MobEffectEvent.Added event) {
        if (event.getEntity() instanceof Player player) {
            ChestCavityData data = getChestCavityData(player);
            if (data == null) return;

            int tumorCount = data.getOrganCount(TUMOR_TAG);
            if (tumorCount == 0) return;

            proliferationSystem.handlePotionProliferation(player, data, event.getEffect());
        }
    }
}
```

---

## JSON配置示例

### 增殖系统主配置

```json
{
  "tumor_proliferation": {
    "enabled": true,
    "version": "1.0",

    "general_settings": {
      "max_tumor_count": 15,
      "max_tumor_count_hard_limit": 27,
      "base_cooldown_time": 10000,
      "min_cooldown_time": 3000,
      "cooldown_reduction_per_organ": 500
    },

    "proliferation_degree": {
      "levels": {
        "0": {
          "name": "stable",
          "display_name": "稳定期",
          "color": "WHITE",
          "health_cost": 2,
          "hunger_cost": 4,
          "side_effect_chance": 0.05
        },
        "1": {
          "name": "active",
          "display_name": "活跃期",
          "color": "GREEN",
          "health_cost": 3,
          "hunger_cost": 5,
          "side_effect_chance": 0.10,
          "trigger_bonus": 0.10,
          "mutation_bonus": 0.00
        },
        "2": {
          "name": "burst",
          "display_name": "爆发期",
          "color": "YELLOW",
          "health_cost": 4,
          "hunger_cost": 6,
          "side_effect_chance": 0.15,
          "trigger_bonus": 0.20,
          "mutation_bonus": 0.10,
          "extra_health_cost": 1
        },
        "3": {
          "name": "danger",
          "display_name": "危险期",
          "color": "GOLD",
          "health_cost": 5,
          "hunger_cost": 8,
          "side_effect_chance": 0.20,
          "trigger_bonus": 0.30,
          "mutation_bonus": 0.20,
          "extra_health_cost": 2,
          "continuous_damage": 1,
          "continuous_damage_interval": 200
        },
        "4": {
          "name": "out_of_control",
          "display_name": "失控期",
          "color": "RED",
          "health_cost": 6,
          "hunger_cost": 10,
          "side_effect_chance": 0.30,
          "trigger_bonus": 0.40,
          "mutation_bonus": 0.30,
          "extra_health_cost": 3,
          "continuous_damage": 2,
          "continuous_damage_interval": 200,
          "organ_destruction_chance": 0.20,
          "no_decay": true
        }
      },
      "decay_settings": {
        "base_decay_interval": 120000,
        "min_decay_interval": 30000,
        "decay_reduction_per_heart": 12000,
        "decay_reduction_per_spleen": 12000,
        "decay_pause_combat": true,
        "decay_pause_chest_open": true
      }
    },

    "triggers": {
      "time_proliferation": {
        "enabled": true,
        "base_interval": 180000,
        "interval_reduction_per_organ": 10000,
        "min_interval": 60000,
        "base_chance": 0.30,
        "degree_chance_bonus": 0.10,
        "max_chance": 0.70
      },
      "kill_proliferation": {
        "enabled": true,
        "base_chance": 0.10,
        "organ_chance_bonus": 0.02,
        "max_chance": 0.30,
        "degree_chance_bonus": 0.05,
        "boss_multiplier": 2.0,
        "combo_window": 5000,
        "combo_bonus_per_kill": 0.05,
        "max_combo_bonus": 0.20,
        "frenzy_combo_required": 3,
        "frenzy_duration": 10000,
        "frenzy_guaranteed_count": 3,
        "temporary_chance_combo": 0.20
      },
      "damage_dealt_proliferation": {
        "enabled": true,
        "base_chance": 0.05,
        "damage_per_bonus": 100.0,
        "damage_bonus": 0.05,
        "max_chance": 0.25,
        "degree_chance_bonus": 0.03,
        "cooldown": 5000,
        "mutation_chance_high_damage": 0.20,
        "high_damage_threshold": 200.0,
        "temporary_chance_crit": 0.15
      },
      "damage_taken_proliferation": {
        "enabled": true,
        "base_chance": 0.08,
        "damage_per_bonus": 50.0,
        "damage_bonus": 0.05,
        "max_chance": 0.30,
        "degree_chance_bonus": 0.05,
        "cooldown": 10000,
        "mutation_chance_high_damage": 0.30,
        "high_damage_threshold": 150.0,
        "low_health_bonus": 0.10,
        "low_health_threshold": 0.30,
        "temporary_chance_low_health": 0.25
      },
      "chest_close_proliferation": {
        "enabled": true,
        "base_chance": 0.15,
        "degree_chance_bonus": 0.05,
        "new_organ_multiplier": 2.0,
        "mutation_chance": 0.20,
        "burst_chance": 0.10,
        "burst_count": 3
      },
      "potion_proliferation": {
        "enabled": true,
        "base_chance": 0.10,
        "degree_chance_bonus": 0.03,
        "strong_potion_multiplier": 2.0,
        "organ_mapping": {
          "healing": ["tumor_heart", "tumor_spleen"],
          "strength": ["tumor_muscle", "tumor_kidney"],
          "speed": ["tumor_lung", "tumor_muscle"],
          "resistance": ["tumor_liver", "tumor_spleen"],
          "negative": ["random"]
        }
      },
      "emergency_proliferation": {
        "enabled": true,
        "health_threshold": 0.20,
        "trigger_chance": 0.40,
        "cooldown": 30000,
        "defensive_priority": true,
        "defensive_organs": ["tumor_heart", "tumor_spleen", "tumor_liver"],
        "temporary_chance": 0.30,
        "frenzy_chance": 0.20,
        "frenzy_count": 3,
        "frenzy_bleeding_duration": 600,
        "frenzy_bleeding_damage": 1.0
      }
    },

    "proliferation_types": {
      "replication": {
        "priority": 1,
        "description": "复制现有器官",
        "inherit_nbt": true,
        "source_required": true
      },
      "mutation": {
        "priority": 2,
        "description": "变异为随机器官",
        "inherit_nbt": false,
        "source_required": false,
        "source_destroy_chance": 0.50,
        "organ_weights": {
          "tumor_stomach": 15,
          "tumor_lung": 12,
          "tumor_intestine": 12,
          "tumor_kidney": 10,
          "tumor_spleen": 10,
          "tumor_liver": 8,
          "tumor_muscle": 8,
          "tumor_heart": 5,
          "tumor_appendix": 2
        }
      },
      "temporary": {
        "priority": 3,
        "description": "生成临时器官",
        "duration": 3600,
        "effect_multiplier": 1.0,
        "counts_for_degree": false,
        "replace_temporary": true,
        "cannot_replace_permanent": true
      },
      "evolution": {
        "priority": 4,
        "description": "升级现有器官",
        "attribute_bonus": 0.5,
        "side_effect_reduction": 0.3,
        "degree_cost": -1,
        "base_chance": 0.10,
        "degree_chance_bonus": {
          "3": 0.10,
          "4": 0.20
        },
        "awakening_bonus": {
          "tumor_appendix_perfect_adaptation": 0.30
        },
        "max_count_bonus": 1
      }
    },

    "max_count_modifiers": {
      "tumor_muscle": 1,
      "tumor_heart": 1,
      "evolved_organ": 1,
      "hard_limit": 27,
      "crowding_effect": {
        "destroy_chance": 0.20,
        "fail_chance": 0.80,
        "fail_effects": {
          "degree_increase": 1,
          "organ_penalty": 0.10,
          "penalty_duration": 30000,
          "damage": 3.0
        }
      }
    },

    "visual_effects": {
      "particles": {
        "replication": {
          "type": "ITEM",
          "count": 25,
          "range": 1.5,
          "duration": 20,
          "color": {
            "r": 128,
            "g": 0,
            "b": 128
          }
        },
        "mutation": {
          "type": "ITEM",
          "count": 30,
          "range": 1.5,
          "duration": 20,
          "color": {
            "r": 255,
            "g": 0,
            "b": 0
          }
        },
        "temporary": {
          "type": "ITEM",
          "count": 20,
          "range": 1.5,
          "duration": 20,
          "color": {
            "r": 200,
            "g": 200,
            "b": 200
          }
        },
        "evolution": {
          "type": "TOTEM_OF_UNDYING",
          "count": 50,
          "range": 3.0,
          "duration": 40,
          "color": {
            "r": 255,
            "g": 215,
            "b": 0
          }
        },
        "temporary_expire": {
          "type": "SQUID_INK",
          "count": 25,
          "range": 2.0,
          "duration": 30,
          "color": {
            "r": 128,
            "g": 128,
            "b": 128
          }
        }
      },
      "sounds": {
        "proliferation": {
          "sound": "evoker_cast_spell",
          "volume": 1.0,
          "pitch": 0.8,
          "range": 16
        },
        "temporary_expire": {
          "sound": "generic_extinguish",
          "volume": 1.0,
          "pitch": 1.0,
          "range": 8
        },
        "evolution": {
          "sound": "totem_use",
          "volume": 1.5,
          "pitch": 1.0,
          "range": 32
        },
        "failed": {
          "sound": "villager_no",
          "volume": 0.8,
          "pitch": 1.2,
          "range": 8
        }
      },
      "notifications": {
        "success": {
          "position": "TOP_RIGHT",
          "duration": 60,
          "title_color": "DARK_PURPLE",
          "description_color": "WHITE"
        },
        "failed": {
          "position": "TOP_RIGHT",
          "duration": 40,
          "title_color": "RED",
          "description_color": "GRAY"
        },
        "degree_change": {
          "position": "ACTION_BAR",
          "duration": 60,
          "colors": {
            "0": "WHITE",
            "1": "GREEN",
            "2": "YELLOW",
            "3": "GOLD",
            "4": "RED"
          }
        },
        "temporary_warning": {
          "position": "TOP_CENTER",
          "show_at_seconds_remaining": 10,
          "blink_at_seconds_remaining": 3,
          "title_color": "GOLD",
          "description_color": "YELLOW"
        }
      }
    },

    "balance_settings": {
      "proliferation_value": {
        "average_organ_value": 100,
        "replication_value": 100,
        "mutation_value_min": 50,
        "mutation_value_max": 150,
        "temporary_value": 50,
        "evolution_value": 150
      },
      "cost_balance": {
        "health_cost_per_degree": 1,
        "hunger_cost_per_degree": 1,
        "degree_increase_chance": 0.50,
        "side_effect_base_chance": 0.05,
        "side_effect_per_degree": 0.05
      },
      "risk_reward_ratio": {
        "optimal_range": [1, 2],
        "dangerous_range": [3, 4],
        "recommended_max_degree": 2
      }
    }
  }
}
```

### 器官专属增殖配置

```json
{
  "tumor_organ_proliferation": {
    "tumor_heart": {
      "proliferation_priority": "HIGH",
      "proliferation_value": "VERY_HIGH",
      "max_count_modifier": 1,
      "evolution_benefit": 0.5,
      "mutation_weight": 5,
      "preferred_triggers": ["KILL", "DAMAGE_DEALT", "EMERGENCY"],
      "synergy_bonus": {
        "proliferation_trigger_chance": 0.05,
        "max_tumor_count": 1
      }
    },
    "tumor_muscle": {
      "proliferation_priority": "HIGH",
      "proliferation_value": "VERY_HIGH",
      "max_count_modifier": 1,
      "evolution_benefit": 0.5,
      "mutation_weight": 8,
      "preferred_triggers": ["KILL", "DAMAGE_DEALT"],
      "synergy_bonus": {
        "proliferation_trigger_chance": 0.03,
        "max_tumor_count": 1
      }
    },
    "tumor_liver": {
      "proliferation_priority": "MEDIUM",
      "proliferation_value": "HIGH",
      "max_count_modifier": 0,
      "evolution_benefit": 0.5,
      "mutation_weight": 8,
      "preferred_triggers": ["POTION", "TIME"],
      "synergy_bonus": {
        "toxin_decay_rate": 1.5
      }
    },
    "tumor_stomach": {
      "proliferation_priority": "LOW",
      "proliferation_value": "LOW",
      "max_count_modifier": 0,
      "evolution_benefit": 0.5,
      "mutation_weight": 15,
      "preferred_triggers": ["TIME", "CHEST_CLOSE"],
      "synergy_bonus": {
        "hunger_penalty_reduction": 0.1
      }
    },
    "tumor_appendix": {
      "proliferation_priority": "VERY_LOW",
      "proliferation_value": "RANDOM",
      "max_count_modifier": 0,
      "evolution_benefit": 0.5,
      "mutation_weight": 2,
      "preferred_triggers": ["CHEST_CLOSE"],
      "special_effects": {
        "awakening_mutation_bonus": 0.50,
        "awakening_temporary_chance": 0.50
      }
    }
  }
}
```

---

## 性能优化策略

### 1. 缓存机制

**增殖数据缓存**:
```java
// 为每个玩家缓存增殖数据，避免重复计算
public class TumorProliferationDataCache {
    private static final Map<UUID, TumorProliferationData> cache = new HashMap<>();
    private static final Map<UUID, Long> lastAccessTime = new HashMap<>();
    private static final long CACHE_EXPIRY_TIME = 300000; // 5分钟过期

    public static TumorProliferationData getData(Player player) {
        UUID uuid = player.getUUID();
        long currentTime = System.currentTimeMillis();

        // 检查缓存是否过期
        if (lastAccessTime.containsKey(uuid)) {
            if (currentTime - lastAccessTime.get(uuid) > CACHE_EXPIRY_TIME) {
                cache.remove(uuid);
                lastAccessTime.remove(uuid);
            }
        }

        // 获取或创建数据
        TumorProliferationData data = cache.computeIfAbsent(uuid,
            k -> new TumorProliferationData());
        lastAccessTime.put(uuid, currentTime);

        return data;
    }

    public static void removeData(Player player) {
        UUID uuid = player.getUUID();
        cache.remove(uuid);
        lastAccessTime.remove(uuid);
    }

    // 玩家退出时清理缓存
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        removeData(event.getEntity());
    }
}
```

### 2. 延迟计算

**胸腔关闭时统一计算**:
```java
// 避免在每次器官变化时立即计算，而是在胸腔关闭时统一计算
public class DelayedOrganCalculation {
    private static final Map<UUID, Boolean> needsRecalculation = new ConcurrentHashMap<>();

    public static void markForRecalculation(Player player) {
        needsRecalculation.put(player.getUUID(), true);
    }

    @SubscribeEvent
    public static void onChestCavityClose(ChestCavityCloseEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUUID();

        if (needsRecalculation.containsKey(uuid) && needsRecalculation.get(uuid)) {
            // 执行重新计算
            recalculateOrganAttributes(player, event.getChestCavityData());
            needsRecalculation.remove(uuid);
        }
    }
}
```

### 3. 批量处理

**多玩家并行流处理**:
```java
// 在服务器tick中，使用并行流处理多个玩家的增殖检测
@SubscribeEvent
public static void onServerTick(TickEvent.ServerTickEvent event) {
    if (event.phase != TickEvent.Phase.END) return;

    // 每20 tick（1秒）执行一次
    if (event.getServer().getTickCount() % 20 != 0) return;

    // 使用并行流处理所有在线玩家
    event.getServer().getPlayerList().getPlayers().parallelStream()
        .filter(player -> hasTumorOrgans(player))
        .forEach(player -> {
            try {
                proliferationSystem.checkTimeProliferation(player, getChestCavityData(player));
            } catch (Exception e) {
                LOGGER.error("Error checking proliferation for player {}", player.getName(), e);
            }
        });
}
```

### 4. 早期退出

**快速失败检测**:
```java
// 在增殖检测开始时，快速检查是否可以增殖
public boolean canQuickProliferate(Player player, ChestCavityData data) {
    // 1. 检查是否有肿瘤器官
    if (data.getOrganCount(TUMOR_TAG) == 0) return false;

    // 2. 检查冷却
    if (isProliferationOnCooldown(player)) return false;

    // 3. 检查最大数量
    if (data.getOrganCount(TUMOR_TAG) >= getMaxTumorCount(player, data)) return false;

    // 4. 检查空槽位
    if (!hasEmptySlot(data)) {
        // 检查是否有临时器官可以替换
        if (!hasTemporaryOrgan(data)) return false;
    }

    return true;
}
```

### 5. 事件节流

**限制高频事件的触发频率**:
```java
// 对于高频事件（如tick），使用节流限制处理频率
public class EventThrottler {
    private static final Map<UUID, Long> lastProcessTime = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> processSkipCount = new ConcurrentHashMap<>();

    public static boolean shouldProcess(Player player, int intervalTicks) {
        UUID uuid = player.getUUID();
        long currentTime = player.level().getGameTime();

        Long lastTime = lastProcessTime.get(uuid);
        if (lastTime == null || currentTime - lastTime >= intervalTicks) {
            lastProcessTime.put(uuid, currentTime);
            processSkipCount.put(uuid, 0);
            return true;
        }

        // 记录跳过次数
        processSkipCount.put(uuid, processSkipCount.getOrDefault(uuid, 0) + 1);

        // 如果跳过次数过多，强制处理一次
        if (processSkipCount.get(uuid) >= intervalTicks) {
            lastProcessTime.put(uuid, currentTime);
            processSkipCount.put(uuid, 0);
            return true;
        }

        return false;
    }
}
```

### 6. 内存优化

**弱引用缓存**:
```java
// 使用弱引用避免内存泄漏
public class WeakReferenceCache<K, V> {
    private final Map<K, WeakReference<V>> cache = new ConcurrentHashMap<>();

    public V get(K key) {
        WeakReference<V> ref = cache.get(key);
        if (ref != null) {
            V value = ref.get();
            if (value != null) {
                return value;
            } else {
                // 引用已被GC，清理缓存
                cache.remove(key);
            }
        }
        return null;
    }

    public void put(K key, V value) {
        cache.put(key, new WeakReference<>(value));
    }

    public void clear() {
        cache.clear();
    }
}
```

### 7. 性能监控

**增殖系统性能指标**:
```java
public class ProliferationPerformanceMetrics {
    private static final Map<String, Long> executionTimes = new ConcurrentHashMap<>();
    private static final Map<String, Integer> executionCounts = new ConcurrentHashMap<>();

    public static void recordExecution(String operation, long startTime) {
        long executionTime = System.currentTimeMillis() - startTime;
        executionTimes.merge(operation, executionTime, Long::sum);
        executionCounts.merge(operation, 1, Integer::sum);
    }

    public static void logMetrics() {
        for (Map.Entry<String, Long> entry : executionTimes.entrySet()) {
            String operation = entry.getKey();
            long totalTime = entry.getValue();
            int count = executionCounts.getOrDefault(operation, 0);
            long avgTime = count > 0 ? totalTime / count : 0;

            LOGGER.info("Proliferation metrics: {} - Total: {}ms, Count: {}, Avg: {}ms",
                operation, totalTime, count, avgTime);
        }
    }

    // 每5分钟输出一次性能指标
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (event.getServer().getTickCount() % (20 * 60 * 5) == 0) {
            logMetrics();
            executionTimes.clear();
            executionCounts.clear();
        }
    }
}
```

### 8. 性能目标

**增殖系统性能指标**:
```
单次增殖检测时间: < 5ms
单次增殖执行时间: < 20ms
临时器官检测频率: 每秒1次
增殖度衰减检测频率: 每10秒1次
内存占用: < 2MB per player
CPU占用: < 0.5%（平均）
不影响游戏FPS: 目标60 FPS稳定
```

---

## 与搭配系统交互

### 1. 搭配效果修正增殖

**某些搭配效果可以增强或减弱增殖**:

```json
{
  "synergy_proliferation_modifiers": {
    "tumor_set_2": {
      "name": "恶性生长",
      "proliferation_modifiers": {
        "trigger_chance_bonus": 0.10,
        "mutation_chance_bonus": 0.00,
        "max_count_bonus": 0,
        "cooldown_reduction": 0.10
      }
    },
    "tumor_set_4": {
      "name": "狂暴突变",
      "proliferation_modifiers": {
        "trigger_chance_bonus": 0.20,
        "mutation_chance_bonus": 0.10,
        "max_count_bonus": 2,
        "cooldown_reduction": 0.20,
        "health_cost_reduction": -1
      }
    },
    "tumor_set_6": {
      "name": "肿瘤军团",
      "proliferation_modifiers": {
        "trigger_chance_bonus": 0.30,
        "mutation_chance_bonus": 0.15,
        "max_count_bonus": 3,
        "cooldown_reduction": 0.30,
        "health_cost_reduction": -2,
        "temporary_duration_bonus": 0.20
      }
    },
    "tumor_adjacent_heart_muscle": {
      "name": "狂暴战士",
      "proliferation_modifiers": {
        "kill_proliferation_bonus": 0.15,
        "damage_dealt_proliferation_bonus": 0.10,
        "frenzy_duration_bonus": 0.20
      }
    },
    "tumor_adjacent_liver_kidney": {
      "name": "净化系统",
      "proliferation_modifiers": {
        "mutation_chance_penalty": -0.10,
        "degree_decay_rate_bonus": 0.50,
        "side_effect_chance_penalty": -0.05
      }
    }
  }
}
```

### 2. 增殖触发搭配检测

**增殖完成后自动检测搭配效果**:
```java
public void onProliferationComplete(Player player, ChestCavityData data, ItemStack newOrgan) {
    // 1. 检测相邻搭配
    checkAdjacentSynergies(player, data, newOrgan);

    // 2. 检测套装搭配
    checkSetSynergies(player, data);

    // 3. 检测主题搭配
    checkThematicSynergies(player, data);

    // 4. 应用新的搭配效果
    applySynergyEffects(player, data);
}
```

### 3. 搭配系统优化建议

**搭配效果缓存**:
```java
// 缓存当前激活的搭配效果，避免重复计算
public class SynergyEffectCache {
    private static final Map<UUID, Set<String>> activeSynergies = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> lastUpdate = new ConcurrentHashMap<>();

    public static Set<String> getActiveSynergies(Player player) {
        UUID uuid = player.getUUID();
        Long updateTime = lastUpdate.get(uuid);

        // 如果缓存是新的（1秒内），直接返回
        if (updateTime != null && System.currentTimeMillis() - updateTime < 1000) {
            return activeSynergies.getOrDefault(uuid, new HashSet<>());
        }

        // 重新计算搭配效果
        ChestCavityData data = getChestCavityData(player);
        Set<String> synergies = calculateSynergies(data);

        activeSynergies.put(uuid, synergies);
        lastUpdate.put(uuid, System.currentTimeMillis());

        return synergies;
    }
}
```

---

## 增殖实例设计

### 实例1: 基础增殖流程

**场景**: 玩家装备了1个肿瘤胃，游戏时间3分钟后触发时间增殖。

**流程**:
1. **触发检测**: 时间增殖检测通过，触发概率30%
2. **冷却检测**: 冷却时间已过（>10秒）
3. **限制检测**: 当前1个器官 < 15个上限
4. **槽位检测**: 胸腔有空槽位
5. **类型确定**: 随机选择增殖类型
   - 复制增殖（70%概率）
   - 变异增殖（0%概率，增殖度0级）
   - 临时增殖（0%概率）
   - 升级增殖（0%概率）
6. **源器官选择**: 选择肿瘤胃
7. **创建新器官**: 生成1个新的肿瘤胃
8. **插入槽位**: 放置到空槽位
9. **应用代价**: 失去2点生命，消耗4点饥饿值
10. **增殖度更新**: 50%概率增殖度+1
11. **视觉反馈**: 播放暗紫色粒子和音效
12. **搭配检测**: 检测新的相邻搭配
13. **结果**: 玩家现在有2个肿瘤胃

### 实例2: 变异增殖流程

**场景**: 玩家装备了5个肿瘤器官，增殖度为2级（爆发期），击杀BOSS后触发增殖。

**流程**:
1. **触发检测**: BOSS击杀增殖检测通过，触发概率30%×2=60%
2. **冷却检测**: 冷却时间已过
3. **限制检测**: 当前5个器官 < 15个上限
4. **槽位检测**: 胸腔有空槽位
5. **类型确定**: 随机选择增殖类型
   - 复制增殖（50%概率）
   - **变异增殖（30%+10%=40%概率）** ← 触发
   - 临时增殖（0%概率）
   - 升级增殖（10%概率）
6. **源器官选择**: 随机选择肿瘤心脏
7. **目标器官确定**: 根据变异权重，选中肿瘤肌肉（8%权重）
8. **源器官处理**: 50%概率移除肿瘤心脏 ← 触发移除
9. **创建新器官**: 生成1个肿瘤肌肉
10. **插入槽位**: 放置到空槽位
11. **应用代价**: 失去4点生命，消耗6点饥饿值
12. **增殖度更新**: 变异增殖必定增殖度+1 → 增殖度3级（危险期）
13. **视觉反馈**: 播放鲜红色粒子和音效
14. **搭配检测**: 检测新的相邻搭配
15. **结果**: 玩家失去肿瘤心脏，获得肿瘤肌肉，增殖度提升到3级

### 实例3: 临时增殖流程

**场景**: 玩家装备了8个肿瘤器官，增殖度为3级（危险期），生命值低于20%触发紧急增殖。

**流程**:
1. **触发检测**: 紧急增殖检测通过，触发概率40%
2. **冷却检测**: 冷却时间已过（>30秒）
3. **限制检测**: 当前8个器官 < 15个上限
4. **槽位检测**: 胸腔有空槽位
5. **类型确定**: 随机选择增殖类型
   - 复制增殖（30%概率）
   - 变异增殖（40%概率）
   - **临时增殖（30%概率）** ← 触发
   - 升级增殖（0%概率）
6. **源器官选择**: 优先选择防御性器官 → 肿瘤脾脏
7. **创建临时器官**: 生成1个临时肿瘤脾脏
8. **添加临时标记**: 持续时间60秒
9. **插入槽位**: 放置到空槽位
10. **应用代价**: 失去5点生命，消耗8点饥饿值
11. **增殖度更新**: 50%概率增殖度+1
12. **视觉反馈**: 播放半透明白色粒子和音效
13. **搭配检测**: 检测新的相邻搭配
14. **临时警告**: 50秒后显示即将消失警告
15. **结果**: 玩家获得临时肿瘤脾脏，60秒后自动消失

### 实例4: 升级增殖流程

**场景**: 玩家装备了10个肿瘤器官，增殖度为4级（失控期），击败敌人后触发增殖狂暴。

**流程**:
1. **触发检测**: 连续击杀3个敌人，触发增殖狂暴
2. **狂暴状态**: 进入狂暴状态，持续10秒，接下来3次增殖必定触发
3. **第1次增殖**:
   - 类型确定: 升级增殖（狂暴期间30%概率）
   - 源器官选择: 肿瘤心脏
   - 升级处理: 肿瘤心脏 → 进化肿瘤心脏（属性+50%，副作用-30%）
   - 应用代价: 无（狂暴期间不消耗增殖次数）
   - 增殖度更新: 升级增殖增殖度-1 → 增殖度3级（危险期）
4. **第2次增殖**:
   - 类型确定: 复制增殖
   - 源器官选择: 进化肿瘤心脏
   - 创建新器官: 生成1个新的进化肿瘤心脏
   - 应用代价: 无（狂暴期间）
5. **第3次增殖**:
   - 类型确定: 变异增殖
   - 源器官选择: 肿瘤肝脏
   - 目标器官确定: 肿瘤阑尾（2%权重，极稀有！）
   - 创建新器官: 生成1个肿瘤阑尾
   - 应用代价: 无（狂暴期间）
6. **狂暴结束**: 狂暴状态结束，增殖度强制+1 → 增殖度4级（失控期）
7. **结果**: 玩家获得1个进化肿瘤心脏、1个新的进化肿瘤心脏、1个肿瘤阑尾，但增殖度重新回到4级

### 实例5: 拥挤效应流程

**场景**: 玩家装备了15个肿瘤器官（达到上限），尝试再次增殖。

**流程**:
1. **触发检测**: 时间增殖检测通过，触发概率70%
2. **限制检测**: 当前15个器官 >= 15个上限
3. **拥挤处理**:
   - 20%概率随机摧毁一个器官 → 随机选择肿瘤肠子并摧毁
   - 80%概率触发拥挤效应 ← 触发
4. **拥挤效应应用**:
   - 增殖度+1 → 增殖度可能提升到5级（超过上限）
   - 所有肿瘤器官效果-10%（持续30秒）
   - 玩家受到3点魔法伤害
5. **视觉反馈**: 播放失败音效和红色粒子
6. **失败通知**: 显示"胸腔拥挤，增殖失败"
7. **结果**: 增殖失败，玩家受到惩罚

---

## 总结

### 设计成果

本设计文档提供了完整的肿瘤增殖机制系统，包括：

1. ✅ **增殖核心概念**: 增殖度系统、增殖方式分类
2. ✅ **4种增殖触发条件**: 时间、击杀、伤害、特定事件
3. ✅ **4种增殖方式**: 复制、变异、临时、升级
4. ✅ **4种增殖限制**: 最大数量、冷却时间、增殖代价、增殖度衰减
5. ✅ **完整的视觉反馈**: 粒子效果、音效设计、UI通知
6. ✅ **平衡性设计**: 收益分析、代价分析、风险收益比
7. ✅ **技术实现方案**: 数据结构、核心逻辑、事件系统
8. ✅ **JSON配置示例**: 完整的配置文件，可直接用于游戏
9. ✅ **性能优化策略**: 8种优化方法，确保系统性能
10. ✅ **搭配系统交互**: 与搭配系统的完整集成
11. ✅ **增殖实例设计**: 5个详细实例，展示完整流程

### 核心优势

1. **策略深度**: 玩家需要主动控制增殖，在收益和代价之间寻找平衡
2. **主题一致**: 完全符合肿瘤"增殖、变异、不稳定"的核心特征
3. **技术可行**: 所有设计都基于现有API，提供详细的实现方案
4. **性能优化**: 提供多种优化策略，确保不影响游戏性能
5. **可扩展性**: 为未来添加新的增殖类型和触发条件预留空间

### 待审核问题

1. **增殖频率**: 时间增殖3分钟基础间隔是否合适？
2. **增殖代价**: 每次增殖2-6点生命是否过于严厉？
3. **增殖度衰减**: 2分钟基础衰减时间是否合理？
4. **临时器官**: 60秒持续时间是否足够？
5. **升级概率**: 10-20%的升级概率是否过低？

---

**文档结束**

**下一步**: 等待审核反馈，根据审核结果进行调整和优化。

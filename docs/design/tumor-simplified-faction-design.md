# 肿瘤派系简化设计方案 v2.0

**项目**: Who Am I Core
**设计日期**: 2026-03-15
**设计师**: Creative Content Designer
**版本**: Simplified v2.0 (修订版)
**状态**: 设计完成 - 技术可行性修正

---

## 设计概述

### 简化目标

基于对现有肿瘤派系设计(T1-T7)的深度分析,我们发现系统层次过多、机制过于复杂。本次简化的核心目标是:

1. **统一核心机制** - 参考冰火派系的温度系统,建立肿瘤派系的核心层数系统
2. **简化器官列表** - 从9个器官精简到6个核心器官
3. **删除冗余系统** - 移除增殖(T3)、共生(T4)等过于复杂的机制
4. **简化搭配规则** - 基于器官数量和位置的简单搭配系统
5. **保持主题性** - 保留肿瘤"风险与收益并存"的核心主题
6. **确保技术可行性** - 所有设计都有明确的技术实现方案

### 核心设计理念

**"层数积累,风险递增"**

肿瘤派系的核心玩法是装备肿瘤器官积累"侵蚀层数",层数越高越强,但风险也越大。这个系统类似冰火派系的温度系统,但更加直接和暴力。

**设计原则** (参考冰火派系):
- **非惩罚性**: 层数系统是增益机制,不是惩罚机制
- **策略性**: 玩家需要主动控制层数,在强度和风险间权衡
- **可理解性**: 机制简单直观,层数=强度=风险
- **平衡性**: 高收益伴随明确的风险代价
- **技术可行**: 所有设计都基于NeoForge Attachment系统实现

---

## 核心机制:侵蚀层数系统

### 侵蚀层数 (Erosion Stacks)

侵蚀层数是肿瘤派系的核心资源系统,类似冰火派系的温度。

**获取方式**:
- 装备1个肿瘤器官 = 基础1层侵蚀
- 装备多个肿瘤器官 = 侵蚀层数累加
- 特定器官提供额外层数(如心脏提供+2层)

**层数上限**: 10层 (固定,不可扩展)

**层数效果**:

| 层数范围 | 名称 | 主要效果 | 代价 |
|---------|------|---------|------|
| 0-2层 | 适应期 | +5%所有属性 | 无 |
| 3-5层 | 活跃期 | +15%所有属性, +10%伤害 | 每5秒失去1点生命 |
| 6-8层 | 危险期 | +30%所有属性, +25%伤害 | 每3秒失去2点生命 |
| 9-10层 | 极限期 | +50%所有属性, +50%伤害 | 每秒失去3点生命, 无法自然恢复 |

**核心特点**:
- 层数越高,增益越强
- 层数越高,生命流失越快
- 玩家需要通过装备/卸载器官来控制层数
- 层数不会自动衰减(除非卸载器官)

**与冰火温度系统的对比**:

| 特征 | 冰火派系-温度 | 肿瘤派系-侵蚀层数 |
|------|-------------|-----------------|
| 核心机制 | 全局温度+局部温度 | 统一的侵蚀层数 |
| 获取方式 | 器官影响温度 | 器官数量=层数 |
| 效果类型 | 三种状态(活跃/抑制/休眠) | 四个阶段(适应/活跃/危险/极限) |
| 风险机制 | 极端温度有惩罚 | 层数越高生命流失越快 |
| 控制方式 | 装备不同器官调整温度 | 装备/卸载器官控制层数 |
| 非惩罚性 | 温度本身不是惩罚 | 层数本身是增益 |

---

## 简化器官列表(6个)

从原有的9个器官精简到6个核心器官,删除了冗余和过于复杂的器官。

### 保留的器官

#### 1. 肿瘤心脏 (Tumor Heart) - 核心

**属性**: HEALTH +4, STRENGTH +2
**侵蚀层数**: +2层 (每个心脏)
**稀有度**: 史诗
**难度**: ★★★☆☆

**独特效果**:
- **增殖之心**: 每个肿瘤器官提供额外+5%最大生命值(最多+50%)
- **核心驱动**: 心脏提供的侵蚀层数翻倍(即+2层而非+1层)

**设计理由**: 肿瘤派系的核心器官,驱动整个侵蚀层数系统。

#### 2. 肿瘤肌肉 (Tumor Muscle) - 伤害

**属性**: STRENGTH +4, SPEED +3
**侵蚀层数**: +1层
**稀有度**: 传说
**难度**: ★★★★☆

**独特效果**:
- **爆发力量**: 基础伤害+30%
- **肌肉痉挛**: 受到伤害时10%概率 无法移动/攻击 1秒
  - 痉挛触发后进入5秒冷却期
  - 侵蚀层数≥7时,痉挛概率降至3%
- **肾上腺素**: 生命值低于30%时,伤害+20%

**设计理由**: 最高的属性收益,典型的风险回报设计。简化了痉挛机制,改为伤害触发并增加冷却。

#### 3. 肿瘤肝脏 (Tumor Liver) - 药水

**属性**: HEALTH +2, STRENGTH +1, SPEED +1
**侵蚀层数**: +1层
**稀有度**: 史诗
**难度**: ★★★☆☆

**独特效果**:
- **药水强化**: 药水效果持续时间+50%
- **侵蚀阈值**: 基于侵蚀层数的额外效果:
  - 侵蚀层数≥3: 药水效果+15%
  - 侵蚀层数≥6: 药水效果+30%, 消耗药水时有10%概率不消耗
  - 侵蚀层数≥9: 药水效果+50%, 消耗药水时有20%概率不消耗

**设计理由**: 简化了肝脏机制,删除独立的毒素层数系统,改为基于侵蚀层数的阈值效果。

#### 4. 肿瘤肺脏 (Tumor Lung) - 持续伤害

**属性**: BREATH_CAPACITY +3, SPEED +2
**侵蚀层数**: +1层
**稀有度**: 稀有
**难度**: ★★☆☆☆

**独特效果**:
- **毒性呼吸**: 周围3格内敌人每秒受到2点毒素伤害
- **环境适应**: 水下速度+30%, 水下呼吸-50%

**设计理由**: 简单直接的持续伤害器官,适合新手。

#### 5. 肿瘤脾脏 (Tumor Spleen) - 防御

**属性**: HEALTH +3
**侵蚀层数**: +1层
**稀有度**: 史诗
**难度**: ★★★☆☆

**独特效果**:
- **变异免疫**: 受到负面效果时 25%概率 转化为正面效果
  - 成功转化后进入30秒冷却
  - 转化效果: 负面效果 → 同类正面效果(如: 中毒 → 再生)
- **脆弱防线**: 受到伤害时 10%概率 额外受到50%伤害

**设计理由**: 防御型器官,随机性机制。添加冷却避免过于频繁触发。

#### 6. 肿瘤阑尾 (Tumor Appendix) - 特殊

**属性**: HEALTH +1
**侵蚀层数**: +1层
**稀有度**: 传说
**难度**: ★★★★☆

**独特效果**:
- **变异赌注**: 装备时触发(仅一次):
  - **70%概率** - 良性变异: 所有肿瘤器官效果+25%,持续3分钟
  - **30%概率** - 恶性变异: 所有肿瘤器官效果-25%,持续1分钟
- **手术后悔**: 可以右键点击卸载阑尾来重新触发(有30秒冷却)

**设计理由**: 调整了觉醒机制,降低了极端随机性,增加了可重试性。

### 删除的器官

| 器官 | 删除理由 |
|------|---------|
| 肿瘤胃 | 功能过于简单,效果与肺脏重叠 |
| 肿瘤肠子 | 状态循环机制过于复杂,不适合简化设计 |
| 肿瘤肾脏 | 层数系统与肝脏重复,增加复杂度 |

---

## 简化系统架构

### 移除的复杂系统

#### ❌ 删除: 增殖机制 (T3)
**删除理由**: 过于复杂,技术实现困难,与核心层数系统冲突

#### ❌ 删除: 共生机制 (T4)
**删除理由**: 器官融合机制过于复杂,不适合简化设计

#### ❌ 删除: 类型分类 (T2的良/恶/特殊分类)
**删除理由**: 分类系统增加了层次但减少了灵活性,简化设计不需要严格的分类

#### ❌ 删除: 多重层数系统
**删除理由**: 只保留"侵蚀层数"一个核心层数系统,移除毒素积累、废物堆积、适应层数等

#### ❌ 删除: 复杂的搭配系统
**删除理由**: 简化为基于数量和位置的简单搭配

### 保留的核心系统

#### ✅ 保留: 侵蚀层数系统
**简化后的核心系统**,类似冰火的温度系统。

#### ✅ 保留: 基础器官属性
每个器官提供明确的属性修正。

#### ✅ 保留: 简单的随机机制
保留阑尾的变异机制、脾脏的免疫转化、肌肉的痉挛等简单随机效果。

#### ✅ 保留: 套装搭配系统
简化为基于器官数量的简单套装效果。

---

## 简化搭配系统

### 套装搭配 (基于数量)

**简单直观的数量触发**,不需要复杂的分类和条件。

#### 2个肿瘤器官: 恶性生长

**效果**:
- 最大生命值+30%
- 生命恢复速度-50%

**设计思路**: 鼓励玩家开始使用肿瘤器官,提供基础的生命强化。

#### 4个肿瘤器官: 狂暴突变

**效果**:
- 所有伤害+50%
- 每秒失去1点生命
- 移动速度+20%

**设计思路**: 中级搭配,明确的风险回报设计。

#### 6个肿瘤器官: 肿瘤军团

**效果**:
- 所有属性+20%
- 所有伤害+30%
- 受到伤害时10%概率恢复5点生命(3秒冷却)
- 每3秒失去2点生命

**设计思路**: 高级搭配,提供强大的综合增益但伴随持续生命流失。

### 相邻搭配 (基于位置)

**简单的相邻触发**,只需要器官相邻即可。

#### 心脏 + 肌肉 (相邻)

**效果**: 狂暴战士
- 狂暴阈值从30%血量提升到50%血量
- 伤害+30%
- 肌肉痉挛概率-5%

#### 肺脏 + 肝脏 (相邻)

**效果**: 毒性强化
- 毒素云范围+50%
- 药水效果+25%

#### 脾脏 + 阑尾 (相邻)

**效果**: 变异免疫
- 阑尾良性变异概率提升至85%
- 脾脏免疫转化概率提升至35%

### 特殊搭配

#### 全套肿瘤 (6个全部装备)

**效果**: 临终爆发
- 受到致命伤害时触发
- 进入10秒的"不死"状态: 伤害+100%, 速度+50%, 无敌
- 状态结束后生命值恢复到1点生命
- 冷却时间: 5分钟

**设计思路**: 终极搭配,改为更平衡的"临终爆发"而非改变死亡机制。

---

## 平衡性设计

### 侵蚀层数平衡

**层数控制策略**:
- 玩家可以通过装备/卸载器官精确控制层数
- 0-5层: 安全区间,适合探索和日常
- 6-8层: 战斗区间,适合BOSS战
- 9-10层: 极限区间,适合短期爆发

**生命流失平衡**:
- 0-2层: 无生命流失
- 3-5层: 每5秒1点(可通过自然恢复抵消)
- 6-8层: 每3秒2点(需要额外的恢复手段)
- 9-10层: 每秒3点(无法自然恢复,需要强大的恢复手段)

**风险回报比**:
- 低层数(0-5): 低风险,中等收益
- 中层数(6-8): 中等风险,高收益
- 高层数(9-10): 高风险,极高收益

### 器官获取难度

| 器官 | 难度 | 掉落来源 | 掉落率 |
|------|------|---------|--------|
| 肺脏 | ★★☆☆☆ | 腐化蜘蛛、腐化洞穴蜘蛛 | 3% |
| 脾脏 | ★★★☆☆ | 腐化女巫 | 2% |
| 心脏 | ★★★☆☆ | 凋灵、末影龙、监守者 | 1% |
| 肝脏 | ★★★☆☆ | 被腐化的末影龙、远古守卫者 | 0.8% |
| 肌肉 | ★★★★☆ | 劫掠兽、远古守卫者 | 1.5% |
| 阑尾 | ★★★★☆ | 任何BOSS 低概率 / 隐藏事件 | 0.3% |

---

## UI与反馈设计

### 侵蚀层数显示

**位置**: 胸腔UI顶部或经验条上方

**显示内容**:
```
[侵蚀层数: 6/10] 危险期
[████████░░] 60%
效果: +30%所有属性, +25%伤害
代价: 每3秒失去2点生命
```

**颜色编码**:
- 0-2层: 绿色 (安全)
- 3-5层: 黄色 (活跃)
- 6-8层: 橙色 (危险)
- 9-10层: 红色 (极限)

### 套装效果提示

**当满足套装条件时**,在胸腔UI中显示:
```
✓ 恶性生长 (2个器官)
✓ 狂暴突变 (4个器官)
✗ 肿瘤军团 (6个器官) - 还需2个
```

### 视觉反馈

**器官脉动**:
- 侵蚀层数越高,器官脉动越快
- 0-5层: 缓慢脉动(2秒周期)
- 6-8层: 快速脉动(1秒周期)
- 9-10层: 剧烈脉动(0.5秒周期)

**粒子效果**:
- 侵蚀层数≥3: 周围出现稀薄的暗紫色烟雾(每5秒生成1-2个粒子)
- 侵蚀层数≥6: 烟雾变得更浓,颜色转为病态绿色(每3秒生成2-3个粒子)
- 侵蚀层数≥9: 出现明显的绿色和红色混合粒子(每2秒生成3-5个粒子)

**性能优化**: 粒子效果使用距离剔除,只对玩家半径16格内渲染。

**音效反馈**:
- 侵蚀层数每变化: 心跳音效变化
- 0-2层: 正常心跳(60 bpm)
- 3-5层: 快速心跳(80 bpm)
- 6-8层: 急促心跳(100 bpm)
- 9-10层: 极速心跳(120 bpm) + 不规律节奏

---

## 技术实现建议

### 数据持久化方案

#### 方案: 扩展 ChestCavityData

**推荐方案**: 在 ChestCavityData 中添加肿瘤专属数据字段。

**优势**:
- 利用现有 Attachment 系统,数据自动持久化
- 与胸腔数据同步,无需额外网络包
- 代码结构清晰,易于维护

**实现方式**:

```java
public class ChestCavityData extends ItemStackHandler {
    // ... 现有字段 ...

    // 肿瘤派系专属数据
    private int erosionStacks = 0; // 当前侵蚀层数
    private final int maxErosionStacks = 10; // 最大侵蚀层数(固定)

    // 套装激活状态缓存
    private boolean set2Active = false;
    private boolean set4Active = false;
    private boolean set6Active = false;

    // 阑尾变异状态
    private boolean appendixMutated = false;
    private long appendixMutationEndTime = 0;
    private boolean appendixIsBenign = false;
    private long appendixLastTriggerTime = 0;

    // 肌肉痉挛冷却
    private long muscleSpasmLastTriggerTime = 0;

    // 脾脏免疫转化冷却
    private long spleenImmunityLastTriggerTime = 0;

    // 临终爆发状态
    private boolean finalExplosionActive = false;
    private long finalExplosionEndTime = 0;
    private long finalExplosionCooldown = 0;

    /**
     * 获取当前侵蚀层数
     */
    public int getErosionStacks() {
        return erosionStacks;
    }

    /**
     * 设置侵蚀层数(仅供内部调用)
     */
    private void setErosionStacks(int stacks) {
        this.erosionStacks = Mth.clamp(stacks, 0, maxErosionStacks);
    }

    /**
     * 获取最大侵蚀层数
     */
    public int getMaxErosionStacks() {
        return maxErosionStacks;
    }

    // 其他 getter/setter ...
}
```

**序列化支持**:

由于 ChestCavityData 继承自 ItemStackHandler,而 NeoForge 的 Attachment 系统会自动序列化所有字段,所以无需额外实现序列化逻辑。

但为了保险起见,可以在 saveAdditional 和 deserializeNBT 中添加:

```java
@Override
public CompoundTag serializeNBT() {
    CompoundTag tag = super.serializeNBT();
    tag.putInt("ErosionStacks", erosionStacks);
    tag.putBoolean("Set2Active", set2Active);
    tag.putBoolean("Set4Active", set4Active);
    tag.putBoolean("Set6Active", set6Active);
    tag.putBoolean("AppendixMutated", appendixMutated);
    tag.putLong("AppendixMutationEndTime", appendixMutationEndTime);
    tag.putBoolean("AppendixIsBenign", appendixIsBenign);
    tag.putLong("AppendixLastTriggerTime", appendixLastTriggerTime);
    tag.putLong("MuscleSpasmLastTriggerTime", muscleSpasmLastTriggerTime);
    tag.putLong("SpleenImmunityLastTriggerTime", spleenImmunityLastTriggerTime);
    tag.putBoolean("FinalExplosionActive", finalExplosionActive);
    tag.putLong("FinalExplosionEndTime", finalExplosionEndTime);
    tag.putLong("FinalExplosionCooldown", finalExplosionCooldown);
    return tag;
}

@Override
public void deserializeNBT(CompoundTag tag) {
    super.deserializeNBT(tag);
    erosionStacks = tag.getInt("ErosionStacks");
    set2Active = tag.getBoolean("Set2Active");
    set4Active = tag.getBoolean("Set4Active");
    set6Active = tag.getBoolean("Set6Active");
    appendixMutated = tag.getBoolean("AppendixMutated");
    appendixMutationEndTime = tag.getLong("AppendixMutationEndTime");
    appendixIsBenign = tag.getBoolean("AppendixIsBenign");
    appendixLastTriggerTime = tag.getLong("AppendixLastTriggerTime");
    muscleSpasmLastTriggerTime = tag.getLong("MuscleSpasmLastTriggerTime");
    spleenImmunityLastTriggerTime = tag.getLong("SpleenImmunityLastTriggerTime");
    finalExplosionActive = tag.getBoolean("FinalExplosionActive");
    finalExplosionEndTime = tag.getLong("FinalExplosionEndTime");
    finalExplosionCooldown = tag.getLong("FinalExplosionCooldown");
}
```

### 事件驱动的层数更新机制

#### 设计原则

**事件驱动 + 延迟计算 + 缓存机制**

- **事件驱动**: 只在器官变化时重新计算
- **延迟计算**: 在胸腔关闭时统一计算,避免频繁更新
- **缓存机制**: 缓存计算结果,避免重复计算

#### 实现流程

```java
public class TumorEventHandler {

    /**
     * 器官装备/卸载事件 - 标记需要更新
     */
    @SubscribeEvent
    public void onOrganChange(OrganChangeEvent event) {
        Player player = event.getPlayer();
        ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

        // 检查是否有肿瘤器官变化
        ItemStack changedStack = event.getChangedStack();
        ItemStack oldStack = event.getOldStack();

        boolean hasTumorChanged = changedStack.is(TUMOR_TAG) || oldStack.is(TUMOR_TAG);

        if (hasTumorChanged) {
            // 标记需要更新侵蚀层数
            data.setErosionStacksDirty(true);
        }
    }

    /**
     * 胸腔UI关闭事件 - 统一计算更新
     */
    @SubscribeEvent
    public void onChestCavityClose(ChestCavityCloseEvent event) {
        Player player = event.getPlayer();
        ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

        if (data.isErosionStacksDirty()) {
            // 重新计算侵蚀层数
            recalculateErosionStacks(player, data);

            // 检测套装变化
            detectSetBonuses(player, data);

            // 清除脏标记
            data.setErosionStacksDirty(false);

            // 同步到客户端
            syncTumorData(player, data);
        }
    }

    /**
     * 玩家登录事件 - 初始化肿瘤数据
     */
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

        // 初始化侵蚀层数
        recalculateErosionStacks(player, data);
        detectSetBonuses(player, data);
    }
}
```

#### ChestCavityData 扩展字段

```java
public class ChestCavityData extends ItemStackHandler {
    // ... 现有字段 ...

    // 脏标记
    private boolean erosionStacksDirty = false;

    public boolean isErosionStacksDirty() {
        return erosionStacksDirty;
    }

    public void setErosionStacksDirty(boolean dirty) {
        this.erosionStacksDirty = dirty;
    }
}
```

#### 侵蚀层数计算

```java
/**
 * 重新计算侵蚀层数
 */
public void recalculateErosionStacks(Player player, ChestCavityData data) {
    int oldStacks = data.getErosionStacks();
    int newStacks = 0;

    // 计算侵蚀层数
    for (int i = 0; i < data.getSlots(); i++) {
        ItemStack stack = data.getStackInSlot(i);
        if (stack.is(TUMOR_TAG)) {
            // 基础+1层
            newStacks++;

            // 心脏额外+1层
            if (stack.is(TUMOR_HEART)) {
                newStacks++;
            }
        }
    }

    // 限制在最大层数
    newStacks = Math.min(newStacks, data.getMaxErosionStacks());

    // 只有层数变化时才更新
    if (newStacks != oldStacks) {
        data.setErosionStacks(newStacks);

        // 应用侵蚀层数效果
        applyErosionEffects(player, data);

        // 发送层数变化消息
        if (!player.level().isClientSide()) {
            int stage = TumorErosionStage.getStage(newStacks);
            String stageName = TumorErosionStage.getStageName(stage);
            player.sendSystemMessage(Component.literal("侵蚀层数: " + newStacks + " - " + stageName));
        }
    }
}
```

#### 性能优化策略

**1. 脏标记机制**:
- 只在器官变化时标记脏
- 只在胸腔关闭时重新计算
- 避免每次器官操作都计算

**2. 缓存机制**:
- 缓存当前侵蚀层数
- 缓存当前激活的套装
- 只在必要时重新计算

**3. 事件节流**:
- 使用脏标记避免频繁计算
- 在胸腔关闭时统一更新
- 玩家登录时初始化一次

**4. 批量更新**:
- 一次性更新所有相关效果
- 避免多次应用属性修饰符

### 侵蚀层数效果应用

```java
public void applyErosionEffects(Player player, ChestCavityData data) {
    int stacks = data.getErosionStacks();
    int stage = TumorErosionStage.getStage(stacks);

    // 移除旧的效果修饰符
    clearErosionModifiers(player);

    // 应用阶段效果
    switch (stage) {
        case TumorErosionStage.ADAPTATION:
            // +5%所有属性,无代价
            applyAllAttributesMultiplier(player, 1.05f);
            break;

        case TumorErosionStage.ACTIVE:
            // +15%所有属性, +10%伤害
            applyAllAttributesMultiplier(player, 1.15f);
            applyDamageMultiplier(player, 1.10f);
            // 代价: 每5秒失去1点生命
            addErosionDamageTask(player, 1.0f, 100); // 100 ticks = 5秒
            break;

        case TumorErosionStage.DANGER:
            // +30%所有属性, +25%伤害
            applyAllAttributesMultiplier(player, 1.30f);
            applyDamageMultiplier(player, 1.25f);
            // 代价: 每3秒失去2点生命
            addErosionDamageTask(player, 2.0f, 60); // 60 ticks = 3秒
            break;

        case TumorErosionStage.EXTREME:
            // +50%所有属性, +50%伤害
            applyAllAttributesMultiplier(player, 1.50f);
            applyDamageMultiplier(player, 1.50f);
            // 代价: 每秒失去3点生命, 无法自然恢复
            addErosionDamageTask(player, 3.0f, 20); // 20 ticks = 1秒
            disableNaturalRegeneration(player);
            break;
    }
}

public class TumorErosionStage {
    public static final int ADAPTATION = 0; // 0-2层
    public static final int ACTIVE = 1;     // 3-5层
    public static final int DANGER = 2;     // 6-8层
    public static final int EXTREME = 3;    // 9-10层

    public static int getStage(int stacks) {
        if (stacks <= 2) return ADAPTATION;
        if (stacks <= 5) return ACTIVE;
        if (stacks <= 8) return DANGER;
        return EXTREME;
    }

    public static String getStageName(int stage) {
        return switch (stage) {
            case ADAPTATION -> "适应期";
            case ACTIVE -> "活跃期";
            case DANGER -> "危险期";
            case EXTREME -> "极限期";
            default -> "未知";
        };
    }
}
```

### 套装检测

```java
public void detectSetBonuses(Player player, ChestCavityData data) {
    int tumorCount = data.getOrganCount(TUMOR_TAG);

    // 2件套
    updateSetBonus(player, data, tumorCount, 2, data.isSet2Active(),
        () -> {
            data.setSet2Active(true);
            applyMaxHealthMultiplier(player, 1.30f);
            applyRegenMultiplier(player, 0.50f);
            sendSetBonusMessage(player, "恶性生长", "最大生命+30%, 恢复-50%");
        },
        () -> {
            data.setSet2Active(false);
            removeMaxHealthMultiplier(player);
            removeRegenMultiplier(player);
        }
    );

    // 4件套
    updateSetBonus(player, data, tumorCount, 4, data.isSet4Active(),
        () -> {
            data.setSet4Active(true);
            applyDamageMultiplier(player, 1.50f);
            addDoTTask(player, 1.0f, 20); // 每秒1点
            applySpeedMultiplier(player, 1.20f);
            sendSetBonusMessage(player, "狂暴突变", "伤害+50%, 每秒失血, 速度+20%");
        },
        () -> {
            data.setSet4Active(false);
            removeDamageMultiplier(player);
            removeDoTTask(player);
            removeSpeedMultiplier(player);
        }
    );

    // 6件套
    updateSetBonus(player, data, tumorCount, 6, data.isSet6Active(),
        () -> {
            data.setSet6Active(true);
            applyAllAttributesMultiplier(player, 1.20f);
            applyDamageMultiplier(player, 1.30f);
            addLifeStealChance(player, 0.10f, 5.0f); // 10%概率恢复5点生命
            addDoTTask(player, 2.0f, 60); // 每3秒2点
            sendSetBonusMessage(player, "肿瘤军团", "所有属性+20%, 伤害+30%, 生命偷取");
        },
        () -> {
            data.setSet6Active(false);
            removeAllAttributesMultiplier(player);
            removeDamageMultiplier(player);
            removeLifeStealChance(player);
            removeDoTTask(player);
        }
    );
}

private void updateSetBonus(Player player, ChestCavityData data, int tumorCount,
                            int required, boolean wasActive,
                            Runnable onActivate, Runnable onDeactivate) {
    boolean shouldBeActive = tumorCount >= required;

    if (shouldBeActive && !wasActive) {
        onActivate.run();
    } else if (!shouldBeActive && wasActive) {
        onDeactivate.run();
    }
}
```

### 客户端同步

```java
/**
 * 同步肿瘤数据到客户端
 */
public void syncTumorData(Player player, ChestCavityData data) {
    if (player.level().isClientSide()) return;

    // 使用现有胸腔同步机制
    // ChestCavityData 已经有 sync 方法,可以复用
    // 只需添加肿瘤相关数据到同步包中

    PacketManager.sendToPlayer(new SyncTumorDataPacket(
        data.getErosionStacks(),
        data.isSet2Active(),
        data.isSet4Active(),
        data.isSet6Active()
    ), player);
}
```

### 独特器官效果实现

#### 肌肉痉挛

```java
@SubscribeEvent
public void onPlayerAttacked(LivingDamageEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

    // 检查是否装备肿瘤肌肉
    if (!data.hasOrgan(TUMOR_MUSCLE)) return;

    // 检查冷却
    long currentTime = player.level().getGameTime();
    if (currentTime - data.getMuscleSpasmLastTriggerTime() < 100) { // 5秒冷却
        return;
    }

    int erosionStacks = data.getErosionStacks();
    float spasmChance = erosionStacks >= 7 ? 0.03f : 0.10f;

    if (player.getRandom().nextFloat() < spasmChance) {
        // 触发痉挛
        data.setMuscleSpasmLastTriggerTime(currentTime);

        // 添加无法移动/攻击效果
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 5, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 5, false, false));

        // 发送消息
        player.sendSystemMessage(Component.literal("肿瘤肌肉痉挛!"));
    }
}
```

#### 肝脏侵蚀阈值

```java
public void applyLiverEffects(Player player, ChestCavityData data) {
    if (!data.hasOrgan(TUMOR_LIVER)) return;

    int erosionStacks = data.getErosionStacks();

    if (erosionStacks >= 9) {
        // 药水效果+50%, 20%概率不消耗
        applyPotionDurationMultiplier(player, 1.50f);
        applyPotionSaveChance(player, 0.20f);
    } else if (erosionStacks >= 6) {
        // 药水效果+30%, 10%概率不消耗
        applyPotionDurationMultiplier(player, 1.30f);
        applyPotionSaveChance(player, 0.10f);
    } else if (erosionStacks >= 3) {
        // 药水效果+15%
        applyPotionDurationMultiplier(player, 1.15f);
    }
}
```

#### 阑尾变异

```java
public void onAppendixEquipped(Player player, ChestCavityData data) {
    long currentTime = player.level().getGameTime();

    // 检查冷却
    if (currentTime - data.getAppendixLastTriggerTime() < 600) { // 30秒冷却
        return;
    }

    data.setAppendixLastTriggerTime(currentTime);

    // 触发变异
    boolean isBenign = player.getRandom().nextFloat() < 0.70f; // 70%良性

    data.setAppendixMutated(true);
    data.setAppendixIsBenign(isBenign);

    int duration = isBenign ? 3600 : 1200; // 良性3分钟, 恶性1分钟
    data.setAppendixMutationEndTime(currentTime + duration);

    if (isBenign) {
        // 良性变异: 所有肿瘤器官效果+25%
        applyTumorOrganEffectMultiplier(player, 1.25f);
        player.sendSystemMessage(Component.literal("良性变异! 肿瘤器官效果+25%, 持续3分钟"));
    } else {
        // 恶性变异: 所有肿瘤器官效果-25%
        applyTumorOrganEffectMultiplier(player, 0.75f);
        player.sendSystemMessage(Component.literal("恶性变异! 肿瘤器官效果-25%, 持续1分钟"));
    }
}

public void onAppendixRemoved(Player player, ChestCavityData data) {
    // 允许右键点击卸载来重新触发
    // 无需特殊处理
}
```

#### 脾脏免疫转化

```java
@SubscribeEvent
public void onEffectApplied(MobEffectEvent.Added event) {
    if (!(event.getEntity() instanceof Player player)) return;

    ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

    // 检查是否装备肿瘤脾脏
    if (!data.hasOrgan(TUMOR_SPLEEN)) return;

    MobEffectInstance effect = event.getEffectInstance();

    // 检查是否是负面效果
    if (!effect.getEffect().value().isBeneficial()) {
        // 检查冷却
        long currentTime = player.level().getGameTime();
        if (currentTime - data.getSpleenImmunityLastTriggerTime() < 600) { // 30秒冷却
            return;
        }

        // 25%概率转化
        if (player.getRandom().nextFloat() < 0.25f) {
            data.setSpleenImmunityLastTriggerTime(currentTime);

            // 移除负面效果
            player.removeEffect(effect.getEffect());

            // 添加对应的正面效果
            MobEffect positiveEffect = getPositiveEffect(effect.getEffect());
            if (positiveEffect != null) {
                player.addEffect(new MobEffectInstance(positiveEffect,
                    effect.getDuration(), effect.getAmplifier(), false, false));

                player.sendSystemMessage(Component.literal("变异免疫! " +
                    effect.getEffect().value().getDisplayName().getString() +
                    " 转化为 " +
                    positiveEffect.value().getDisplayName().getString()));
            }
        }
    }
}

private MobEffect getPositiveEffect(MobEffect negativeEffect) {
    // 简化的映射
    return switch (negativeEffect.value().getRegisteredName()) {
        case "poison" -> MobEffects.REGENERATION;
        case "wither" -> MobEffects.ABSORPTION;
        case "slowness" -> MobEffects.SPEED;
        case "weakness" -> MobEffects.DAMAGE_BOOST;
        case "mining_fatigue" -> MobEffects.DIG_SPEED;
        default -> null;
    };
}
```

#### 临终爆发

```java
@SubscribeEvent
public void onLivingDeath(LivingDeathEvent event) {
    if (!(event.getEntity() instanceof Player player)) return;

    ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

    // 检查是否装备全套肿瘤
    if (data.getOrganCount(TUMOR_TAG) < 6) return;

    // 检查冷却
    long currentTime = player.level().getGameTime();
    if (currentTime < data.getFinalExplosionCooldown()) return;

    // 检查是否可以触发
    // 使用 Forge 的 canCancel 来判断是否是真正的死亡
    if (!event.isCancelable()) return;

    // 取消死亡
    event.setCanceled(true);

    // 设置临终爆发状态
    data.setFinalExplosionActive(true);
    data.setFinalExplosionEndTime(currentTime + 200); // 10秒
    data.setFinalExplosionCooldown(currentTime + 6000); // 5分钟冷却

    // 应用效果
    player.setHealth(1.0f);
    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 4, false, false));
    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2, false, false));
    player.addEffect(new MobEffectInstance(MobEffects.INVULNERABILITY, 200, 0, false, false));

    player.sendSystemMessage(Component.literal("临终爆发! 10秒无敌状态!"));
}

@SubscribeEvent
public void onPlayerTick(PlayerTickEvent.Post event) {
    Player player = event.getEntity();
    ChestCavityData data = ChestCavityUtil.getChestCavityData(player);

    if (data.isFinalExplosionActive()) {
        long currentTime = player.level().getGameTime();

        // 检查是否结束
        if (currentTime >= data.getFinalExplosionEndTime()) {
            data.setFinalExplosionActive(false);

            // 移除无敌,恢复到1点生命
            player.removeAllEffects();
            player.setHealth(1.0f);

            player.sendSystemMessage(Component.literal("临终爆发结束!"));
        }
    }
}
```

### UI 集成方案

#### 选项1: 扩展现有胸腔UI

在胸腔UI顶部添加侵蚀层数显示区域:

```java
public class ChestCavityScreen extends AbstractContainerScreen<ChestCavityMenu> {

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);

        // 渲染侵蚀层数显示
        renderErosionDisplay(guiGraphics);
    }

    private void renderErosionDisplay(GuiGraphics guiGraphics) {
        ChestCavityData data = getChestCavityData();
        int erosionStacks = data.getErosionStacks();
        int maxStacks = data.getMaxErosionStacks();

        // 获取阶段信息
        int stage = TumorErosionStage.getStage(erosionStacks);
        String stageName = TumorErosionStage.getStageName(stage);
        int color = getStageColor(stage);

        // 渲染文本
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(leftPos + 10, topPos + 10, 0);

        // 渲染层数文本
        String stacksText = String.format("侵蚀层数: %d/%d - %s", erosionStacks, maxStacks, stageName);
        guiGraphics.drawString(font, stacksText, 0, 0, color);

        // 渲染进度条
        int barWidth = 100;
        int barHeight = 10;
        int filledWidth = (int) ((float) erosionStacks / maxStacks * barWidth);

        // 背景
        guiGraphics.fill(0, 15, barWidth, 15 + barHeight, 0xFF333333);
        // 填充
        guiGraphics.fill(0, 15, filledWidth, 15 + barHeight, color);

        guiGraphics.pose().popPose();
    }

    private int getStageColor(int stage) {
        return switch (stage) {
            case TumorErosionStage.ADAPTATION -> 0x55FF55; // 绿色
            case TumorErosionStage.ACTIVE -> 0xFFFF55; // 黄色
            case TumorErosionStage.DANGER -> 0xFFAA00; // 橙色
            case TumorErosionStage.EXTREME -> 0xFF5555; // 红色
            default -> 0xFFFFFF;
        };
    }
}
```

#### 选项2: 使用经验条显示

在胸腔UI打开时,临时使用经验条显示侵蚀层数:

```java
public class TumorClientHandler {

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (!ChestCavityMenu.isChestCavityOpen()) return;

        ChestCavityData data = getChestCavityData();
        int erosionStacks = data.getErosionStacks();
        int maxStacks = data.getMaxErosionStacks();

        // 渲染经验条覆盖层
        GuiGraphics guiGraphics = guiGraphics;
        int x = minecraft.getWindow().getGuiScaledWidth() / 2 - 91;
        int y = minecraft.getWindow().getGuiScaledHeight() - 29 + 3;

        // 渲染侵蚀层数进度条
        int filledWidth = (int) ((float) erosionStacks / maxStacks * 182);
        int color = getStageColor(TumorErosionStage.getStage(erosionStacks));

        guiGraphics.fill(x, y, x + filledWidth, y + 5, color);
    }
}
```

### 性能考虑

#### 计算频率

- **侵蚀层数计算**: 仅在器官变化时(胸腔关闭时计算)
- **套装检测**: 仅在器官变化时
- **效果应用**: 仅在层数/套装变化时
- **Tick检查**: 每秒1次(生命流失效果)

#### 内存占用

- 每个玩家额外数据: ~200 bytes
- 对于100个玩家: ~20 KB
- 可忽略不计

#### 网络流量

- 器官变化时同步: ~50 bytes
- 估算每个玩家每分钟1-2次变化
- 对于100个玩家: ~10 KB/min

---

## 与冰火派系的对比

### 设计相似性

| 特征 | 冰火派系 | 肿瘤派系(简化v2.0) |
|------|---------|------------------|
| **核心机制** | 温度系统 | 侵蚀层数系统 |
| **资源获取** | 器官影响温度 | 器官数量=层数 |
| **效果阶段** | 3种状态 | 4个阶段 |
| **风险机制** | 极端温度惩罚 | 高层数生命流失 |
| **控制方式** | 装备不同器官 | 装备/卸载器官 |
| **核心器官** | Malkuth(温度平衡) | 心脏(层数驱动) |
| **套装系统** | 基于器官类型 | 基于器官数量 |
| **相邻搭配** | 局部温度协同 | 简单相邻效果 |
| **数据持久化** | Attachment | Attachment(扩展ChestCavityData) |
| **事件驱动** | ✓ | ✓ |
| **缓存机制** | ✓ | ✓ |

### 设计差异

| 特征 | 冰火派系 | 肿瘤派系(简化v2.0) |
|------|---------|------------------|
| **复杂度** | 中等(全局+局部温度) | 低(单一层数系统) |
| **策略性** | 温度平衡 | 层数控制 |
| **主题** | 冰与火的对立与平衡 | 风险与回报的权衡 |
| **玩法风格** | 精细调整 | 大起大落 |
| **适合玩家** | 喜欢精细管理的玩家 | 喜欢高风险高回报的玩家 |
| **技术难度** | 中等 | 低 |

---

## 总结

### 简化成果

**从原设计(T1-T7)简化为**:
1. ✅ **统一核心机制**: 侵蚀层数系统(类似冰火的温度系统)
2. ✅ **简化器官列表**: 从9个减少到6个核心器官
3. ✅ **删除冗余系统**: 移除增殖、共生、多重层数系统
4. ✅ **简化搭配规则**: 基于数量的套装和简单的相邻搭配
5. ✅ **保持主题性**: 保留"风险与回报并存"的核心主题
6. ✅ **确保技术可行**: 所有设计都有明确的实现方案

### v2.0 修订内容

**解决严重问题**:
1. ✅ **数据持久化**: 明确使用扩展 ChestCavityData 方案,利用现有 Attachment 系统
2. ✅ **层数计算**: 设计了事件驱动+脏标记+延迟计算的完整机制
3. ✅ **肝脏简化**: 删除毒素层数系统,改为基于侵蚀层数的阈值效果

**解决建议问题**:
1. ✅ **阑尾觉醒**: 调整为70/30概率,增加可重试机制
2. ✅ **6件套平衡**: 从"不死之身"改为"临终爆发"
3. ✅ **层数上限**: 删除15层设计,固定10层上限
4. ✅ **UI集成**: 提供两种UI集成方案
5. ✅ **肌肉痉挛**: 简化为伤害触发+冷却机制
6. ✅ **粒子效果**: 添加距离剔除和频率限制
7. ✅ **器官难度**: 调整阑尾为4星难度

### 核心优势

1. **易学难精**: 简单的层数系统,但层数控制需要策略
2. **清晰直观**: 层数=强度=风险,一目了然
3. **技术可行**: 所有效果都可以通过现有API实现
4. **平衡良好**: 低中高风险层次分明
5. **主题鲜明**: 肿瘤的"侵蚀"主题贯穿始终
6. **性能优化**: 事件驱动+缓存机制,性能影响最小化

### 与冰火派系的定位差异

- **冰火派系**: 温度平衡策略,适合精细管理型玩家
- **肿瘤派系**: 风险回报博弈,适合追求极限的玩家

两个派系提供了不同的游戏体验,丰富了游戏内容。

### 技术实现要点

1. **数据持久化**: 扩展 ChestCavityData,自动序列化
2. **事件驱动**: 器官变化时标记脏,胸腔关闭时统一计算
3. **性能优化**: 脏标记+缓存+批量更新
4. **UI集成**: 扩展胸腔UI或使用经验条显示
5. **网络同步**: 复用现有胸腔同步机制

---

**文档结束**

**状态**: 技术可行性修正完成,可直接用于开发

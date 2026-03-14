# 肿瘤类型分类体系设计文档 T2

**设计版本**: v1.0
**创建日期**: 2026-03-15
**设计者**: Creative Content Designer
**状态**: 待审核
**基于**: T1-organ-base-attributes.md

---

## 目录

1. [设计概述](#设计概述)
2. [分类体系总览](#分类体系总览)
3. [良性肿瘤设计](#良性肿瘤设计)
4. [恶性肿瘤设计](#恶性肿瘤设计)
5. [特殊肿瘤设计](#特殊肿瘤设计)
6. [类型差异化机制](#类型差异化机制)
7. [分类标签系统](#分类标签系统)
8. [视觉与特效设计](#视觉与特效设计)
9. [获取方式设计](#获取方式设计)
10. [技术实现建议](#技术实现建议)
11. [与T3/T4的兼容性](#与t3t4的兼容性)

---

## 设计概述

### 设计目标

创建肿瘤器官的三级分类体系,实现以下目标:

1. **差异化体验**: 三种类型提供截然不同的游戏体验
2. **策略深度**: 玩家需要根据玩法风格选择合适的类型组合
3. **可扩展性**: 为后续T3(增殖机制)和T4(共生机制)预留空间
4. **平衡性**: 每种类型都有其优势和劣势,无绝对优劣

### 分类原则

#### 稳定性原则
- **良性肿瘤**: 高稳定性,效果可预测
- **恶性肿瘤**: 低稳定性,高随机性
- **特殊肿瘤**: 条件触发,机制独特

#### 风险收益原则
- **良性肿瘤**: 低风险,中等收益
- **恶性肿瘤**: 高风险,高收益
- **特殊肿瘤**: 极端风险或条件限制,极端收益

#### 主题一致性原则
- **良性肿瘤**: "适应"主题 - 肿瘤与身体的和谐共存
- **恶性肿瘤**: "侵蚀"主题 - 肿瘤对身体的侵蚀和变异
- **特殊肿瘤**: "突变"主题 - 肿瘤的极端变异和进化

### 设计约束

1. **基于T1特性**: 必须基于T1已设计的器官特性
2. **保留原有机制**: 不改变T1已设计的效果,只进行分类
3. **兼容搭配系统**: 分类不影响T1设计的搭配系统
4. **支持未来扩展**: 为T3增殖机制和T4共生机制预留接口

---

## 分类体系总览

### 三类肿瘤对比表

| 特征维度 | 良性肿瘤 | 恶性肿瘤 | 特殊肿瘤 |
|---------|---------|---------|---------|
| **核心主题** | 适应 | 侵蚀 | 突变 |
| **稳定性** | 高 (95%+) | 低 (50-70%) | 极端 (0-100%) |
| **风险等级** | ★★☆☆☆ | ★★★★☆ | ★★★★★ |
| **收益等级** | ★★★☆☆ | ★★★★★ | ★★★★☆ |
| **机制复杂度** | 简单 | 复杂 | 独特 |
| **副作用** | 可控 | 严重 | 极端或无 |
| **战略价值** | 基础组件 | 核心组件 | 特殊战略 |
| **玩家识别度** | 容易理解 | 需要学习 | 难以预测 |
| **视觉风格** | 柔和脉动 | 剧烈波动 | 异常特效 |

### 器官分配方案

#### 良性肿瘤 (3个)
| 器官 | 英文ID | 主要理由 |
|------|--------|---------|
| 肿瘤胃 | tumor_stomach | 副作用可控(饥饿),效果稳定(食物增益) |
| 肿瘤肺脏 | tumor_lung | 效果持续且可预测,副作用明确(水下削弱) |
| 肿瘤脾脏 | tumor_spleen | 被动触发,概率合理(25%),效果正面 |

#### 恶性肿瘤 (4个)
| 器官 | 英文ID | 主要理由 |
|------|--------|---------|
| 肿瘤心脏 | tumor_heart | 核心器官,随机负面效果(恶性跳动),狂暴机制 |
| 肿瘤肌肉 | tumor_muscle | 高收益(力量速度),严重副作用(痉挛),体力透支 |
| 肿瘤肝脏 | tumor_liver | 层叠系统(毒素积累),风险递增,药水依赖 |
| 肿瘤肾脏 | tumor_kidney | 随机爆发(过滤超载),层数积累(废物堆积) |

#### 特殊肿瘤 (2个)
| 器官 | 英文ID | 主要理由 |
|------|--------|---------|
| 肿瘤阑尾 | tumor_appendix | 极端随机机制(5%觉醒),改变游戏规则(细胞融合) |
| 肿瘤肠子 | tumor_intestine | 状态循环系统(4种状态),完全随机切换 |

### 分配逻辑说明

#### 良性肿瘤分配逻辑
1. **肿瘤胃**: 尽管有"无尽饥饿"副作用,但效果是100%可预测的,玩家可以通过管理饥饿值来控制
2. **肿瘤肺脏**: 毒素云是持续被动效果,水下削弱是明确的权衡,无随机性
3. **肿瘤脾脏**: 虽然有"脆弱防线"的随机负面,但主要效果是被动触发的免疫转化,整体稳定

#### 恶性肿瘤分配逻辑
1. **肿瘤心脏**: 作为增殖系统核心,其"恶性跳动"的随机性(20%)和狂暴机制的高风险使其成为典型的恶性肿瘤
2. **肿瘤肌肉**: 极高的属性收益(力量+4,速度+3)伴随严重的随机负面(肌肉痉挛20%),符合高风险高收益特征
3. **肿瘤肝脏**: 毒素积累系统是典型的"越用越强但越用越危险"的恶性循环设计
4. **肿瘤肾脏**: 过滤超载的随机爆发(30%)和废物堆积的长期风险,体现了恶性肿瘤的不稳定性

#### 特殊肿瘤分配逻辑
1. **肿瘤阑尾**: 5%的极端低概率觉醒机制,以及觉醒后可能完全改变游戏规则的效果(如完美适应+50%所有器官效果)
2. **肿瘤肠子**: 30秒一次的完全随机状态切换,在4种截然不同的状态间循环,这是独特的机制而非简单的随机性

---

## 良性肿瘤设计

### 核心特征

**"适应"主题**: 良性肿瘤代表身体对肿瘤的适应和包容。它们提供了相对稳定的增益,副作用可控且可预测。

**设计理念**:
- 可预测性: 玩家可以准确预期何时获得收益、何时承受代价
- 可控性: 玩家可以通过策略和准备来最大化收益、最小化代价
- 稳定性: 效果不会突然变化,适合作为构建的基础

### 肿瘤胃 (tumor_stomach) - 良性

**分类理由**:
尽管"无尽饥饿"会加速饥饿值消耗,但这是完全可预测的。玩家可以通过携带更多食物或使用自动喂食器来应对。"腐化消化"和"营养转化"虽然随机,但概率高达50%,且负面效果(中毒、虚弱)是短期的。

**良性特征**:
- ✅ 效果触发时机明确(食用食物时)
- ✅ 副作用可以通过准备来管理(带更多食物)
- ✅ 收益稳定(+2 HEALTH, +1 STRENGTH)
- ✅ 随机性偏向正面(50%正面效果 vs 33%负面效果)

**良性优化建议**:
- 保持T1设计不变
- 可以添加"饱腹度管理工具"的配合物品
- 为良性套餐添加额外稳定性效果

**战略定位**:
- **新手友好**: 适合新手玩家的第一个肿瘤器官
- **稳定发育**: 提供稳定的属性提升,适合前期发育
- **搭配核心**: 与肠子形成"完美代谢"相邻搭配

### 肿瘤肺脏 (tumor_lung) - 良性

**分类理由**:
"毒性呼吸"是持续被动的光环效果,完全可预测。"变异气息"的权衡(水下呼吸-50%,水下速度+30%)是明确的策略选择,而非随机副作用。

**良性特征**:
- ✅ 持续被动效果,无随机性
- ✅ 副作用是明确的权衡(水下削弱)
- ✅ 效果范围可预测(周围3格)
- ✅ 收益稳定(+3 BREATH, +2 SPEED)

**良性优化建议**:
- 保持T1设计不变
- 可以添加"毒素云可视化"的粒子效果提示
- 为良性套餐添加"环境适应"效果

**战略定位**:
- **战斗辅助**: 持续的毒素云伤害适合近战战斗
- **探索利器**: 水下速度提升适合水下探索
- **搭配核心**: 与胃形成"毒素扩散"相邻搭配

### 肿瘤脾脏 (tumor_spleen) - 良性

**分类理由**:
"变异免疫"是25%概率触发,虽然不算100%稳定,但相对于恶性肿瘤的20%痉挛、30%爆发等,脾脏的概率较高且偏向正面。"免疫记忆"提供了长期积累的价值。

**良性特征**:
- ✅ 被动触发,不需要玩家操作
- ✅ 概率相对较高(25%)
- ✅ 效果偏向正面(转化负面为正面)
- ✅ 长期积累价值(免疫记忆)

**良性优化建议**:
- 保持T1设计不变
- 可以添加"免疫历史"的UI显示
- 为良性套餐添加"免疫强化"效果

**战略定位**:
- **防御核心**: 提供稳定的防御和负面效果转化
- **长期投资**: 免疫记忆随时间增强
- **搭配核心**: 与肝脏形成"免疫网络"相邻搭配

### 良性套餐设计

**2件: 适应共生**
- **效果**: 副作用可预测性提升,所有良性肿瘤的副作用持续时间-25%
- **设计思路**: 鼓励玩家使用多个良性器官,形成稳定的构建

**3件: 完美适应**
- **效果**: 获得"适应光环",每10秒恢复5%生命值,并在1分钟内积累1层"适应"(最多5层),每层提供+5%所有属性
- **设计思路**: 三件套提供稳定的恢复和成长,强化良性肿瘤的"适应"主题

---

## 恶性肿瘤设计

### 核心特征

**"侵蚀"主题**: 恶性肿瘤代表肿瘤对身体的侵蚀和变异。它们提供极高的收益,但伴随着严重的、不可控的副作用。

**设计理念**:
- 高风险高收益: 属性收益远高于良性,但副作用也更严重
- 不稳定性: 效果触发有随机性,无法完全预测
- 侵蚀性: 长期使用会导致负面效果积累
- 狂暴性: 低血量时进入高风险高回报状态

### 肿瘤心脏 (tumor_heart) - 恶性

**分类理由**:
作为肿瘤系统的核心器官,心脏具有典型的恶性肿瘤特征:
1. "恶性跳动": 每10秒20%概率流失生命,这是不可控的随机负面
2. "狂暴触发": 低血量时进入狂暴状态,持续失血
3. "增殖之心": 装备越多肿瘤器官越强,但副作用也越多

**恶性特征**:
- ⚠️ 随机负面效果(20%恶性跳动)
- ⚠️ 低血量狂暴伴随持续失血
- ⚠️ 增殖系统: 越多肿瘤器官,风险越高
- ✅ 极高收益(+4 HEALTH, +2 STRENGTH, +50%最大生命值)

**恶性强化建议**:
- 保持T1设计不变
- 可以添加"心跳音效"提示恶性跳动
- 为恶性肿瘤套餐添加"增殖加速"效果

**战略定位**:
- **核心组件**: 肿瘤构建的心脏,几乎所有肿瘤构建都建议装备
- **高风险高回报**: 适合追求极限属性的玩家
- **搭配核心**: 与肌肉形成"狂暴战士"相邻搭配

### 肿瘤肌肉 (tumor_muscle) - 恶性

**分类理由**:
肌肉是典型的恶性肿瘤:
1. 极高的属性收益: +4 STRENGTH, +3 SPEED(远超同类器官)
2. 严重的随机副作用: "肌肉痉挛"20%概率无法移动或攻击
3. 体力透支: 冲刺和跳跃消耗2倍耐力
4. 肾上腺素: 低血量时更强但持续失血

**恶性特征**:
- ⚠️ 随机瘫痪(20%肌肉痉挛,持续1-3秒)
- ⚠️ 体力消耗翻倍
- ⚠️ 低血量时持续失血
- ✅ 极高的攻击和速度(+4 STRENGTH, +3 SPEED)
- ✅ 爆发力量: 正常状态下+30%伤害

**恶性强化建议**:
- 保持T1设计不变
- 可以添加"痉挛预警"的视觉效果(如肌肉抖动)
- 为恶性肿瘤套餐添加"狂暴强化"效果

**战略定位**:
- **伤害核心**: 提供最高的攻击属性
- **战斗节奏**: 痉挛机制带来独特的战斗节奏
- **搭配核心**: 与心脏形成"狂暴战士"相邻搭配

### 肿瘤肝脏 (tumor_liver) - 恶性

**分类理由**:
肝脏的"毒素积累"系统是典型的恶性肿瘤设计:
1. 层叠系统: 使用药水积累毒素,毒素越多越强但也越危险
2. 风险递增: 6+层时开始持续失血
3. 药水依赖: 必须使用药水才能触发效果,但使用药水又会积累毒素
4. 衰减缓慢: 每分钟只清除1层毒素,无法快速清除

**恶性特征**:
- ⚠️ 层叠风险系统(毒素积累)
- ⚠️ 6+层时持续失血
- ⚠️ 清除缓慢(每分钟-1层)
- ✅ 药水效果增强(+50%持续时间)
- ✅ 高伤害潜力(6+层时+30%伤害)

**恶性强化建议**:
- 保持T1设计不变
- 可以添加"毒素层数UI"清晰显示当前风险
- 为恶性肿瘤套餐添加"毒素加速"效果

**战略定位**:
- **药水核心**: 与药水build搭配的核心
- **风险管理**: 玩家需要权衡毒素层数和收益
- **搭配核心**: 与肾脏形成"净化系统"相邻搭配

### 肿瘤肾脏 (tumor_kidney) - 恶性

**分类理由**:
肾脏具有恶性肿瘤的典型特征:
1. 随机爆发: "过滤超载"30%概率触发,之后10秒属性下降
2. 层数积累: "废物堆积"每60秒积累1层,最多10层
3. 长期风险: 废物层数越高,生命恢复越低
4. 爆发后虚弱: 过滤超载后10秒内属性-10%

**恶性特征**:
- ⚠️ 随机爆发(30%过滤超载)
- ⚠️ 爆发后虚弱(10秒-10%属性)
- ⚠️ 层数积累(废物堆积)
- ⚠️ 长期生命恢复下降(每层-5%)
- ✅ 短期爆发(5秒+20%所有属性)
- ✅ 高伤害潜力(10层+50%伤害)

**恶性强化建议**:
- 保持T1设计不变
- 可以添加"废物层数UI"和"爆发预警"
- 为恶性肿瘤套餐添加"过滤加速"效果

**战略定位**:
- **爆发核心**: 提供短期爆发能力
- **耐力管理**: 与耐力系统深度结合
- **搭配核心**: 与脾脏形成"免疫系统"相邻搭配

### 恶性套餐设计

**2件: 恶性共鸣**
- **效果**: 所有恶性肿瘤的随机副作用概率-5%,但收益+10%
- **设计思路**: 鼓励玩家使用多个恶性器官,降低随机性

**3件: 侵蚀之主**
- **效果**: 获得"侵蚀光环",每造成100点伤害,积累1层"侵蚀"(最多10层),每层提供+3%所有伤害,但每秒失去0.5点生命
- **设计思路**: 三件套提供主动积累的伤害强化,但伴随生命流失,强化恶性肿瘤的"侵蚀"主题

**4件: 肿瘤军团**
- **效果**: 所有恶性肿瘤的层数系统(肝脏毒素、肾脏废物)上限+5,并且清除速度翻倍
- **设计思路**: 四件套强化层数系统,允许玩家积累更多层数

---

## 特殊肿瘤设计

### 核心特征

**"突变"主题**: 特殊肿瘤代表肿瘤的极端变异和进化。它们具有独特的机制,能够改变游戏规则或提供完全不同的游戏体验。

**设计理念**:
- 独特性: 机制不同于任何其他器官
- 不可预测性: 效果完全随机或条件极端
- 战略价值: 在特定情况下可以决定胜负
- 两极性: 要么极度强大,要么几乎无用

### 肿瘤阑尾 (tumor_appendix) - 特殊

**分类理由**:
阑尾具有独特的"彩票"机制:
1. 极端低概率: 5%觉醒概率,95%阑尾炎
2. 游戏规则改变: 觉醒效果可能完全改变游戏体验(如完美适应+50%所有器官效果)
3. 两极分化: 要么极强(完美适应、细胞融合、变异爆发),要么极弱(致命缺陷)
4. 条件触发: 只在胸腔关闭时触发一次

**特殊特征**:
- 🎲 极端随机(5%觉醒,95%阑尾炎)
- 🎲 游戏规则改变(觉醒效果极其强大)
- 🎲 两极分化(最强或最弱)
- 🎲 条件触发(仅胸腔关闭时)
- ✅ 潜在的极限收益(+50%所有器官效果)

**特殊优化建议**:
- 保持T1设计不变
- 可以添加"觉醒尝试次数"统计
- 可以添加"觉醒历史"记录
- 考虑添加"觉醒药水"物品,提升觉醒概率

**战略定位**:
- **彩票器官**: 运气好可以逆转战局
- **娱乐性**: 为玩家提供惊喜和刺激
- **配菜**: 不作为构建核心,而是额外的运气成分

### 肿瘤肠子 (tumor_intestine) - 特殊

**分类理由**:
肠子具有独特的"状态循环"机制:
1. 完全随机: 每30秒在4种状态间随机切换
2. 截然不同: 4种状态完全不同(强化、虚弱、再生、中毒)
3. 状态记忆: 玩家可以预测当前状态,但无法预测下一个状态
4. 循环系统: 不是一次性的随机,而是持续的状态循环

**特殊特征**:
- 🔄 状态循环系统(30秒切换一次)
- 🔄 完全随机(4种状态等概率)
- 🔄 截然不同的效果(从+20%伤害到持续中毒)
- ✅ 多样性体验(每次战斗感觉都不同)
- ✅ 状态适应: 玩家可以根据当前状态调整战术

**特殊优化建议**:
- 保持T1设计不变
- 可以添加"状态倒计时"UI显示
- 可以添加"状态历史"记录
- 考虑添加"状态药水"物品,影响下次状态

**战略定位**:
- **适应战术**: 玩家需要根据当前状态调整战术
- **多样化体验**: 每次使用都有不同的体验
- **搭配核心**: 与胃形成"完美代谢"相邻搭配

### 特殊套餐设计

**2件: 突变共鸣**
- **效果**: 特殊肿瘤的触发概率/切换速度提升10%,并且可以查看下一次的结果(如阑尾觉醒结果、肠子下一个状态)
- **设计思路**: 降低随机性的挫败感,提供一定的可预测性

---

## 类型差异化机制

### 游戏机制差异

#### 属性修正差异

**良性肿瘤**:
- 属性修正稳定,无随机波动
- 副作用可预测,可以通过准备来管理
- 长期积累价值(如免疫记忆)

**恶性肿瘤**:
- 属性修正有随机波动
- 副作用随机触发,无法完全预测
- 层数系统,长期使用导致风险积累

**特殊肿瘤**:
- 属性修正极端(要么极高,要么极低)
- 效果改变游戏规则
- 状态循环,完全随机

#### 触发时机差异

**良性肿瘤**:
- 持续被动触发(如肺脏的毒素云)
- 明确条件触发(如胃的食物消化)
- 概率较高(如脾脏的25%)

**恶性肿瘤**:
- 随机时间触发(如心脏的每10秒)
- 概率较低(如肌肉的20%,肾脏的30%)
- 层数积累触发(如肝脏的毒素积累)

**特殊肿瘤**:
- 条件触发(如阑尾的胸腔关闭)
- 定时循环(如肠子的每30秒)
- 一次性的极端触发

#### 风险管理差异

**良性肿瘤**:
- 风险可控: 玩家可以通过准备来降低风险
- 风险稳定: 风险不会突然增加
- 风险管理: 通过物品和策略来管理风险

**恶性肿瘤**:
- 风险不可控: 随机触发无法完全避免
- 风险递增: 层数系统导致风险随时间增加
- 风险接受: 玩家需要接受风险并制定应对策略

**特殊肿瘤**:
- 风险极端: 要么无风险,要么极高风险
- 风险随机: 完全取决于运气
- 风险赌博: 玩家可以选择接受或避免风险

### 视觉与特效差异

#### 颜色方案

**良性肿瘤**:
- 主色调: 暗紫色 (#8B5A9D)
- 辅助色: 深红色 (#8B0000)
- 粒子效果: 柔和的紫色烟雾,缓慢脉动

**恶性肿瘤**:
- 主色调: 病态绿色 (#9ACD32) + 鲜红色 (#FF0000)
- 辅助色: 黑色 (#000000)
- 粒子效果: 剧烈的绿色和红色混合,快速闪烁

**特殊肿瘤**:
- 主色调: 彩虹色渐变 (全光谱)
- 辅助色: 白色 (#FFFFFF)
- 粒子效果: 多彩的彩虹粒子,随机变色

#### 动画效果

**良性肿瘤**:
- 脉动: 缓慢且柔和的脉动(2秒一个周期)
- 质感: 光滑,略带湿润
- 粒子: 少量,缓慢上升

**恶性肿瘤**:
- 脉动: 快速且剧烈的脉动(0.5秒一个周期)
- 质感: 粗糙,不规则,滴血
- 粒子: 大量,快速扩散

**特殊肿瘤**:
- 脉动: 不规律的脉动(随机0.5-3秒)
- 质感: 发光,半透明
- 粒子: 多彩,随机方向

#### 音效设计

**良性肿瘤**:
- 心跳: 稳定的低频心跳声(60 bpm)
- 音量: 中等,不刺耳
- 音调: 低沉,稳定

**恶性肿瘤**:
- 心跳: 不稳定的心跳声(80-120 bpm,随机)
- 音量: 大,略带刺耳
- 音调: 高亢,不规律

**特殊肿瘤**:
- 心跳: 特殊音效(如阑尾的"叮!"觉醒声,肠子的"咕噜"消化声)
- 音量: 随机变化
- 音调: 多变,有时尖锐,有时低沉

### UI显示差异

#### 物品提示差异

**良性肿瘤**:
- 标题颜色: 绿色文本
- 副作用提示: "副作用可控"
- 风险等级: ★★☆☆☆
- 推荐度: "适合新手"

**恶性肿瘤**:
- 标题颜色: 红色文本
- 副作用提示: "高风险高收益"
- 风险等级: ★★★★☆
- 推荐度: "适合高手"

**特殊肿瘤**:
- 标题颜色: 彩虹渐变文本
- 副作用提示: "改变游戏规则"
- 风险等级: ★★★★★
- 推荐度: "适合娱乐"

#### 胸腔UI差异

**良性肿瘤**:
- 槽位边框: 绿色
- 脉动: 缓慢
- 状态显示: 清晰明确

**恶性肿瘤**:
- 槽位边框: 红色
- 脉动: 快速
- 状态显示: 包括层数信息

**特殊肿瘤**:
- 槽位边框: 彩虹色
- 脉动: 不规律
- 状态显示: 包括特殊状态(如觉醒状态、当前代谢状态)

---

## 分类标签系统

### 标签设计

#### 基础标签

为每个肿瘤器官添加 `tumor_type` 标签:

```java
// 标签定义
public static final Tag<Item> BENIGN_TUMOR = Tag.create("benign_tumor");
public static final Tag<Item> MALIGNANT_TUMOR = Tag.create("malignant_tumor");
public static final Tag<Item> SPECIAL_TUMOR = Tag.create("special_tumor");

// 标签分配
tumor_stomach.addTag(BENIGN_TUMOR);
tumor_lung.addTag(BENIGN_TUMOR);
tumor_spleen.addTag(BENIGN_TUMOR);

tumor_heart.addTag(MALIGNANT_TUMOR);
tumor_muscle.addTag(MALIGNANT_TUMOR);
tumor_liver.addTag(MALIGNANT_TUMOR);
tumor_kidney.addTag(MALIGNANT_TUMOR);

tumor_appendix.addTag(SPECIAL_TUMOR);
tumor_intestine.addTag(SPECIAL_TUMOR);
```

#### 组合标签

为支持套餐效果,添加组合标签:

```java
// 标签定义
public static final Tag<Item> TUMOR = Tag.create("tumor");

// 组合检测
public int getBenignTumorCount(ChestCavityData data) {
    return data.getOrganCount(BENIGN_TUMOR);
}

public int getMalignantTumorCount(ChestCavityData data) {
    return data.getOrganCount(MALIGNANT_TUMOR);
}

public int getSpecialTumorCount(ChestCavityData data) {
    return data.getOrganCount(SPECIAL_TUMOR);
}

public int getTotalTumorCount(ChestCavityData data) {
    return data.getOrganCount(TUMOR);
}
```

### 搭配系统扩展

#### 类型内搭配

**良性套餐**:
```java
// 2件良性套餐
if (getBenignTumorCount(data) >= 2) {
    applyEffect("side_effect_duration_reduction", 0.75f);
}

// 3件良性套餐
if (getBenignTumorCount(data) >= 3) {
    applyEffect("adaptation_aura", ...);
}
```

**恶性套餐**:
```java
// 2件恶性套餐
if (getMalignantTumorCount(data) >= 2) {
    applyEffect("random_side_effect_reduction", 0.95f);
    applyEffect("malignant_bonus", 1.1f);
}

// 3件恶性套餐
if (getMalignantTumorCount(data) >= 3) {
    applyEffect("erosion_aura", ...);
}

// 4件恶性套餐
if (getMalignantTumorCount(data) >= 4) {
    applyEffect("stack_limit_increase", 5);
    applyEffect("stack_decay_rate", 2.0f);
}
```

**特殊套餐**:
```java
// 2件特殊套餐
if (getSpecialTumorCount(data) >= 2) {
    applyEffect("special_trigger_enhancement", 1.1f);
    applyEffect("special_prediction", true);
}
```

#### 跨类型搭配

**混合搭配**:
```java
// 良性+恶性搭配
if (getBenignTumorCount(data) >= 1 && getMalignantTumorCount(data) >= 1) {
    applyEffect("balanced_tumor", ...);
}

// 恶性+特殊搭配
if (getMalignantTumorCount(data) >= 1 && getSpecialTumorCount(data) >= 1) {
    applyEffect("chaos_tumor", ...);
}

// 全类型搭配
if (getBenignTumorCount(data) >= 1 &&
    getMalignantTumorCount(data) >= 1 &&
    getSpecialTumorCount(data) >= 1) {
    applyEffect("complete_tumor", ...);
}
```

### UI显示扩展

#### 物品标签显示

在物品的tooltip中显示类型标签:

```java
// 物品tooltip
@Override
public void appendHoverText(ItemStack stack, ... List<Component> tooltip) {
    // 添加类型标签
    if (stack.is(BENIGN_TUMOR)) {
        tooltip.add(new TextComponent("§a[良性肿瘤]").withStyle(ChatFormatting.GREEN));
        tooltip.add(new TextComponent("副作用可控,适合新手").withStyle(ChatFormatting.GRAY));
    } else if (stack.is(MALIGNANT_TUMOR)) {
        tooltip.add(new TextComponent("§c[恶性肿瘤]").withStyle(ChatFormatting.RED));
        tooltip.add(new TextComponent("高风险高收益,需要技巧").withStyle(ChatFormatting.GRAY));
    } else if (stack.is(SPECIAL_TUMOR)) {
        tooltip.add(new TextComponent("§d[特殊肿瘤]").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(new TextComponent("改变游戏规则,完全随机").withStyle(ChatFormatting.GRAY));
    }
}
```

#### 胸腔UI类型统计

在胸腔UI中显示各类型器官的数量:

```java
// 胸腔UI
public void renderChestCavityUI(GuiGraphics gui, ...) {
    // 显示类型统计
    int benignCount = getBenignTumorCount(data);
    int malignantCount = getMalignantTumorCount(data);
    int specialCount = getSpecialTumorCount(data);

    gui.drawString(font, "良性: " + benignCount, x, y, COLOR_GREEN);
    gui.drawString(font, "恶性: " + malignantCount, x, y + 10, COLOR_RED);
    gui.drawString(font, "特殊: " + specialCount, x, y + 20, COLOR_PURPLE);
}
```

#### 套餐效果提示

当满足套餐条件时,在UI中显示提示:

```java
// 套餐效果提示
public void renderSetBonusInfo(GuiGraphics gui, ...) {
    if (getBenignTumorCount(data) >= 3) {
        gui.drawString(font, "§a完美适应激活", x, y, COLOR_GREEN);
        gui.drawString(font, "§7每10秒恢复5%生命", x, y + 10, COLOR_GRAY);
    }

    if (getMalignantTumorCount(data) >= 3) {
        gui.drawString(font, "§c侵蚀之主激活", x, y + 20, COLOR_RED);
        gui.drawString(font, "§7伤害积累侵蚀层数", x, y + 30, COLOR_GRAY);
    }
}
```

---

## 获取方式设计

### 获取难度调整

根据分类调整获取难度,使其与类型特征匹配:

#### 良性肿瘤获取

**获取难度**: ★★☆☆☆ (简单-中等)

**获取方式**:
- **肿瘤胃**: 从腐化僵尸、腐化骷髅掉落 (5%)
- **肿瘤肺脏**: 从腐化蜘蛛、腐化洞穴蜘蛛掉落 (3%)
- **肿瘤脾脏**: 从腐化女巫掉落 (2%) 或 女巫小屋宝箱 (10%)

**设计理念**: 良性肿瘤应该相对容易获取,让新手能够接触到稳定的肿瘤器官

#### 恶性肿瘤获取

**获取难度**: ★★★★☆ (困难-极难)

**获取方式**:
- **肿瘤心脏**: 从凋灵、末影龙、监守者掉落 (1%)
- **肿瘤肌肉**: 从劫掠兽、远古守卫者掉落 (1.5%)
- **肿瘤肝脏**: 从被腐化的末影龙、远古守卫者掉落 (0.8%)
- **肿瘤肾脏**: 从深渊守卫、监守者掉落 (1.2%)

**设计理念**: 恶性肿瘤应该难以获取,匹配其强大的力量

#### 特殊肿瘤获取

**获取难度**: ★★★★★ (极难-传说)

**获取方式**:
- **肿瘤阑尾**: 任何BOSS极低概率掉落 (0.1%) 或 完成特殊隐藏事件获得
- **肿瘤肠子**: 从腐化苦力怕、沼泽怪物掉落 (1%) 或 完成特殊挑战获得

**设计理念**: 特殊肿瘤应该最难获取,匹配其独特和稀有的特性

### 特殊获取条件

为特殊肿瘤设计特殊的获取条件:

#### 肿瘤阑尾特殊获取

**隐藏事件 - "变异之夜"**:
1. **触发条件**: 血月之夜,击败至少20个腐化怪物
2. **特殊挑战**: 变异女王Boss战
3. **奖励**: 100%掉落肿瘤阑尾

**设计理念**: 为特殊肿瘤提供可预测的获取途径,即使掉落率极低,玩家也有明确的目标

#### 肿瘤肠子特殊获取

**挑战 - "代谢考验"**:
1. **触发条件**: 装备至少5个肿瘤器官
2. **挑战内容**: 在10分钟内击败100个怪物,且血量从未低于50%
3. **奖励**: 肿瘤肠子

**设计理念**: 挑战需要玩家已经有一定的肿瘤器官基础,符合肠子的"状态循环"机制

### 交易获取

允许通过特殊交易获取肿瘤器官:

**腐化商人**:
- **出现条件**: 血月之夜在村庄出现
- **交易内容**:
  - 良性肿瘤: 10-20个绿宝石
  - 恶性肿瘤: 30-50个绿宝石
  - 特殊肿瘤: 100个绿宝石 + 特殊物品(如龙蛋)

**设计理念**: 提供备选获取途径,特别是对于运气不好的玩家

---

## 技术实现建议

### 数据结构扩展

#### 肿瘤类型数据

```java
// 肿瘤类型枚举
public enum TumorType {
    BENIGN("良性", "benign", ChatFormatting.GREEN),
    MALIGNANT("恶性", "malignant", ChatFormatting.RED),
    SPECIAL("特殊", "special", ChatFormatting.LIGHT_PURPLE);

    private final String displayName;
    private final String englishName;
    private final ChatFormatting color;

    // getters...
}

// 肿瘤类型配置
public class TumorTypeConfig {
    private TumorType type;
    private int sideEffectReduction = 0; // 副作用减少
    private int bonusIncrease = 0; // 收益增加
    private boolean canPredict = false; // 是否可以预测

    // getters and setters...
}

// 扩展TumorData
public class TumorData {
    // 原有字段...

    // 新增: 类型统计
    private int benignCount = 0;
    private int malignantCount = 0;
    private int specialCount = 0;

    // 新增: 类型配置
    private Map<TumorType, TumorTypeConfig> typeConfigs = new HashMap<>();

    // 新增: 套餐激活状态
    private boolean benignSet3 = false;
    private boolean malignantSet3 = false;
    private boolean malignantSet4 = false;
    private boolean specialSet2 = false;

    // 新增: 特殊状态
    private AdaptationLevel adaptationLevel = AdaptationLevel.NONE; // 适应层数(良性)
    private int erosionStacks = 0; // 侵蚀层数(恶性)
    private boolean canPredictSpecial = false; // 是否可预测特殊肿瘤

    enum AdaptationLevel {
        NONE, LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5
    }
}
```

### 事件处理扩展

#### 胸腔打开事件

```java
public void onChestCavityOpen(Player player, ChestCavityData data) {
    TumorData tumorData = getOrCreateTumorData(player);

    // 原有逻辑: 计算总肿瘤器官数量
    int totalCount = data.getOrganCount(TUMOR);
    tumorData.setTumorCount(totalCount);

    // 新增: 计算各类型数量
    int benignCount = data.getOrganCount(BENIGN_TUMOR);
    int malignantCount = data.getOrganCount(MALIGNANT_TUMOR);
    int specialCount = data.getOrganCount(SPECIAL_TUMOR);

    tumorData.setBenignCount(benignCount);
    tumorData.setMalignantCount(malignantCount);
    tumorData.setSpecialCount(specialCount);

    // 应用类型套餐效果
    applyTumorTypeSetBonuses(player, tumorData);

    // 应用原有增殖之心效果
    if (data.hasOrgan(TUMOR_HEART)) {
        float healthBonus = 1.0f + (totalCount * 0.05f);
        applyHealthMultiplier(player, Math.min(healthBonus, 1.5f));
    }
}

private void applyTumorTypeSetBonuses(Player player, TumorData tumorData) {
    // 良性套餐
    if (tumorData.getBenignCount() >= 2) {
        // 副作用持续时间-25%
        tumorData.getTypeConfig(TumorType.BENIGN).setSideEffectReduction(25);
    }
    if (tumorData.getBenignCount() >= 3) {
        // 完美适应
        tumorData.setBenignSet3(true);
        applyAdaptationAura(player);
    }

    // 恶性套餐
    if (tumorData.getMalignantCount() >= 2) {
        // 随机副作用概率-5%,收益+10%
        TumorTypeConfig config = tumorData.getTypeConfig(TumorType.MALIGNANT);
        config.setSideEffectReduction(5);
        config.setBonusIncrease(10);
    }
    if (tumorData.getMalignantCount() >= 3) {
        // 侵蚀之主
        applyErosionAura(player);
    }
    if (tumorData.getMalignantCount() >= 4) {
        // 肿瘤军团
        tumorData.setMalignantSet4(true);
        applyTumorLegionEffects(player);
    }

    // 特殊套餐
    if (tumorData.getSpecialCount() >= 2) {
        // 突变共鸣
        tumorData.setCanPredictSpecial(true);
        applyMutationResonance(player);
    }
}
```

#### 伤害事件处理(恶性肿瘤 - 侵蚀之主)

```java
public void onDamageDealt(Player player, LivingEntity target, float damage) {
    TumorData tumorData = getTumorData(player);
    if (tumorData == null) return;

    // 恶性套餐3: 侵蚀之主
    if (tumorData.isMalignantSet3()) {
        // 积累侵蚀层数
        float totalDamage = tumorData.getTotalDamageDealt() + damage;
        tumorData.setTotalDamageDealt(totalDamage);

        // 每100点伤害积累1层侵蚀
        int newStacks = (int)(totalDamage / 100.0f);
        int stacksToAdd = newStacks - tumorData.getErosionStacks();

        if (stacksToAdd > 0) {
            tumorData.setErosionStacks(Math.min(newStacks, 10)); // 最多10层
            // 发送粒子效果提示
            spawnErosionParticles(player, stacksToAdd);
        }
    }
}

public void onServerTick(Player player) {
    TumorData tumorData = getTumorData(player);
    if (tumorData == null) return;

    // 恶性套餐3: 侵蚀之主 - 持续失血
    if (tumorData.getErosionStacks() > 0) {
        if (player.tickCount % 40 == 0) { // 每2秒
            float damage = tumorData.getErosionStacks() * 0.5f;
            player.hurt(player.damageSources().magic(), damage);
        }

        // 应用伤害加成
        float damageBonus = tumorData.getErosionStacks() * 0.03f; // 每层+3%
        applyDamageMultiplier(player, 1.0f + damageBonus);
    }

    // 良性套餐3: 完美适应 - 持续恢复
    if (tumorData.isBenignSet3()) {
        if (player.tickCount % 200 == 0) { // 每10秒
            float healAmount = player.getMaxHealth() * 0.05f;
            player.heal(healAmount);

            // 积累适应层数(每分钟1层,最多5层)
            if (player.tickCount % 1200 == 0) {
                AdaptationLevel current = tumorData.getAdaptationLevel();
                if (current != AdaptationLevel.LEVEL5) {
                    tumorData.setAdaptationLevel(current.next());
                    // 发送粒子效果和通知
                    spawnAdaptationParticles(player);
                    sendAdaptationMessage(player, current.next());
                }
            }
        }

        // 应用适应层数加成
        int level = tumorData.getAdaptationLevel().ordinal();
        float allAttributesBonus = level * 0.05f; // 每层+5%
        applyAllAttributesMultiplier(player, 1.0f + allAttributesBonus);
    }
}
```

#### 肿瘤类型修正

```java
// 肿瘤类型修正器
public class TumorTypeModifier {
    // 获取肿瘤类型的副作用减少百分比
    public static float getSideEffectReduction(Player player, TumorType type) {
        TumorData tumorData = getTumorData(player);
        if (tumorData == null) return 0.0f;

        return tumorData.getTypeConfig(type).getSideEffectReduction() / 100.0f;
    }

    // 获取肿瘤类型的收益增加百分比
    public static float getBonusIncrease(Player player, TumorType type) {
        TumorData tumorData = getTumorData(player);
        if (tumorData == null) return 0.0f;

        return tumorData.getTypeConfig(type).getBonusIncrease() / 100.0f;
    }

    // 修正随机概率
    public static float modifyRandomProbability(Player player, Item organ, float baseProbability) {
        TumorType type = getTumorType(organ);
        float reduction = getSideEffectReduction(player, type);

        // 副作用概率减少
        if (isSideEffect(organ)) {
            return baseProbability * (1.0f - reduction);
        }

        return baseProbability;
    }

    // 修正属性值
    public static float modifyAttributeValue(Player player, Item organ, float baseValue) {
        TumorType type = getTumorType(organ);
        float increase = getBonusIncrease(player, type);

        // 收益增加
        return baseValue * (1.0f + increase);
    }
}
```

### 性能优化建议

#### 缓存机制

```java
// 肿瘤类型缓存
public class TumorTypeCache {
    private Map<UUID, CachedTumorData> cache = new HashMap<>();
    private static final long CACHE_EXPIRY = 5000; // 5秒过期

    public static class CachedTumorData {
        public int benignCount;
        public int malignantCount;
        public int specialCount;
        public long timestamp;

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRY;
        }
    }

    public CachedTumorData getOrCompute(Player player, ChestCavityData data) {
        UUID uuid = player.getUUID();
        CachedTumorData cached = cache.get(uuid);

        if (cached == null || cached.isExpired()) {
            // 重新计算
            cached = computeTumorTypeData(data);
            cache.put(uuid, cached);
        }

        return cached;
    }

    private CachedTumorData computeTumorTypeData(ChestCavityData data) {
        CachedTumorData result = new CachedTumorData();
        result.timestamp = System.currentTimeMillis();
        result.benignCount = data.getOrganCount(BENIGN_TUMOR);
        result.malignantCount = data.getOrganCount(MALIGNANT_TUMOR);
        result.specialCount = data.getOrganCount(SPECIAL_TUMOR);
        return result;
    }
}
```

#### 延迟计算

```java
// 延迟计算套餐效果
public void onChestCavityClose(Player player, ChestCavityData data) {
    // 标记需要重新计算
    TumorData tumorData = getTumorData(player);
    tumorData.setNeedsRecalculation(true);
}

public void onServerTick(Player player) {
    TumorData tumorData = getTumorData(player);
    if (tumorData == null) return;

    // 在tick事件中统一计算
    if (tumorData.isNeedsRecalculation()) {
        recalculateTumorTypeEffects(player, tumorData);
        tumorData.setNeedsRecalculation(false);
    }
}
```

---

## 与T3/T4的兼容性

### 与T3(增殖机制)的兼容性

#### 增殖机制设计预览

**T3增殖机制**:
- 肿瘤器官可以"增殖",在胸腔中复制自己
- 增殖条件: 装备一定数量的同类型肿瘤器官
- 增殖效果: 产生新的肿瘤器官,占据额外槽位

#### 分类体系对T3的支持

**良性肿瘤增殖**:
- **增殖条件**: 装备3个良性肿瘤
- **增殖效果**: 每5分钟增殖1个良性肿瘤(随机类型)
- **增殖限制**: 最多增殖3个
- **设计理念**: 良性肿瘤的稳定增殖,提供长期的稳定发育

**恶性肿瘤增殖**:
- **增殖条件**: 装备4个恶性肿瘤,且血量低于30%
- **增殖效果**: 立即增殖1个恶性肿瘤(随机类型),但失去50%当前生命值
- **增殖限制**: 无限制,但每次增殖都消耗生命值
- **设计理念**: 恶性肿瘤的危险增殖,以生命为代价

**特殊肿瘤增殖**:
- **增殖条件**: 装备2个特殊肿瘤
- **增殖效果**: 1%概率增殖任何类型的肿瘤器官
- **增殖限制**: 无限制,完全随机
- **设计理念**: 特殊肿瘤的完全随机增殖

### 与T4(共生机制)的兼容性

#### 共生机制设计预览

**T4共生机制**:
- 两个相邻的肿瘤器官可以"共生",融合成更强的器官
- 共生条件: 两个肿瘤器官满足特定条件
- 共生效果: 产生共生器官,具有两者的特性

#### 分类体系对T4的支持

**良性-良性共生**:
- **组合**: 任意2个良性肿瘤
- **共生器官**: "适应共生体"
- **效果**: 两个良性肿瘤的效果+30%,副作用-50%
- **视觉**: 柔和的绿色脉动,两个器官融合

**恶性-恶性共生**:
- **组合**: 任意2个恶性肿瘤
- **共生器官**: "侵蚀共生体"
- **效果**: 两个恶性肿瘤的效果+50%,层数上限+5,副作用+20%
- **视觉**: 剧烈的红色和绿色混合,不规则形状

**特殊-特殊共生**:
- **组合**: 任意2个特殊肿瘤
- **共生器官**: "突变共生体"
- **效果**: 随机获得以下效果之一:
  - 两个特殊肿瘤的效果翻倍
  - 两个特殊肿瘤的效果取消
  - 产生全新的随机效果
- **视觉**: 彩虹色发光,形状随机变化

**跨类型共生**:
- **良性-恶性共生**: "平衡共生体" - 稳定性和高风险的结合
- **良性-特殊共生**: "稳定突变共生体" - 特殊效果但可预测
- **恶性-特殊共生**: "混乱共生体" - 完全不可预测的极端效果

### 扩展性设计

#### 未来类型扩展

分类体系支持未来添加新的肿瘤类型:

**第四类型: "共生肿瘤"** (如果需要)
- 主题: 肿瘤与其他器官系统的共生
- 特征: 与非肿瘤器官的互动和增强

**第五类型: "远古肿瘤"** (如果需要)
- 主题: 远古时期的肿瘤化石
- 特征: 极其强大但极其稀有

#### 子类型系统

在每个主类型下,可以添加子类型:

**良性肿瘤子类型**:
- **防御型**: 侧重生存和防御
- **辅助型**: 侧重增益和恢复
- **功能型**: 侧重特定功能(如呼吸、消化)

**恶性肿瘤子类型**:
- **攻击型**: 侧重伤害和爆发
- **狂暴型**: 侧重低血量强化
- **侵蚀型**: 侧重层数积累和长期风险

**特殊肿瘤子类型**:
- **随机型**: 完全随机效果(如阑尾)
- **循环型**: 状态循环(如肠子)
- **条件型**: 特定条件触发

---

## 总结

### 设计成果

本设计文档提供了肿瘤器官的完整三级分类体系,包括:

1. ✅ **三种类型定义**: 良性、恶性、特殊
2. ✅ **9个器官分类分配**: 3个良性,4个恶性,2个特殊
3. ✅ **类型特征详细说明**: 每种类型的主题、特征、战略定位
4. ✅ **类型差异化机制**: 游戏机制、视觉特效、UI显示的差异
5. ✅ **分类标签系统**: 完整的标签设计和套餐系统
6. ✅ **视觉与特效设计**: 颜色、动画、音效的差异化设计
7. ✅ **获取方式设计**: 根据类型调整获取难度和方式
8. ✅ **技术实现建议**: 完整的数据结构、事件处理、性能优化
9. ✅ **T3/T4兼容性**: 为未来的增殖和共生机制预留空间

### 核心优势

1. **差异化清晰**: 三种类型提供截然不同的游戏体验
2. **策略深度**: 玩家需要根据玩法风格选择合适的类型组合
3. **平衡性**: 每种类型都有其优势和劣势,无绝对优劣
4. **扩展性强**: 为T3增殖机制和T4共生机制预留了充足空间
5. **技术可行**: 所有效果都可以通过现有API实现

### 设计亮点

1. **良性套餐 "完美适应"**: 每10秒恢复5%生命,积累适应层数提供全属性加成
2. **恶性套餐 "侵蚀之主"**: 造成伤害积累侵蚀层数,提供伤害加成但持续失血
3. **特殊套餐 "突变共鸣"**: 提升特殊肿瘤的触发,并可以预测结果
4. **类型标签系统**: 完整的标签体系支持套餐和搭配系统
5. **视觉差异化**: 三种类型有鲜明的颜色、动画、音效差异

### 待审核问题

1. **分类合理性**: 9个器官的类型分配是否合理?
2. **套餐平衡性**: 三种类型的套餐效果是否平衡?
3. **获取难度**: 各类型的获取难度是否匹配其强度?
4. **T3/T4兼容性**: 增殖和共生机制的设计是否符合预期?

### 下一步

等待审核反馈,根据审核结果进行调整和优化。审核通过后,进入T3(增殖机制)设计阶段。

---

**文档结束**

**下一步**: 等待审核反馈,根据审核结果进行调整和优化。

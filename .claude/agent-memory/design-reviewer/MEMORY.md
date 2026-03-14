# 设计审核代理记忆

## 项目技术架构

### Who Am I Core
- **技术栈**: Minecraft NeoForge 21.1.219-merged
- **语言**: Java
- **项目位置**: `E:\Mod Project\Who-Am-I-Core`

### 前置Mod架构

#### Chest Cavity Beyond (主要前置)
- **位置**: `E:\Mod Project\Chest Cavity Beyond`
- **核心API**:
  - `IOrgan` 接口: 器官系统核心接口
  - `Organ` 类: 器官实现基类
  - `ChestCavityData`: 胸腔数据容器 (27槽位)
  - `OrganBuilder`: 器官构建器
  - `OrganChangeEvent`: 器官更换事件

- **器官系统特点**:
  - 基于 ItemStackHandler 的27槽位系统
  - 支持属性修饰符 (AttributeModifier)
  - 支持器官技能 (hasSkill, organSkill)
  - 支持多种事件回调:
    - tick: 每tick调用
    - organAdded/organRemoved: 器官添加/移除
    - attack/hurt/incomingDamage: 战斗相关
    - chestCavityOpen/chestCavityClose: UI相关

- **属性系统**:
  - BREATH_RECOVERY, BREATH_CAPACITY, ENDURANCE
  - NERVES, DEFENSE, DIGESTION, NUTRITION
  - FILTRATION, METABOLISM, DETOXIFICATION
  - 等等...

#### 其他前置Mod
- IceAndFire-CE: 龙类器官来源
- MowziesMobs-Public: 怪物器官来源
- FDBosses: Boss器官来源
- AnvilCraft: 机械类器官来源

## 器官设计原则

### 现有器官实现特点
1. **基于OrganBuilder构建**: 所有自定义器官都使用 `OrganBuilder.builder()` 模式
2. **属性增益为主**: 大部分器官提供基础属性增益
3. **事件驱动**: 通过OrganBuilder的Consumer接口实现各种效果
4. **无搭配系统**: Chest Cavity Beyond原生不支持器官搭配,这是本项目要扩展的核心功能

### 项目特色器官主题
- 龙族器官 (火龙、冰龙、电龙)
- 墨水与颜料器官
- 九狱器官 (基于但丁神曲地狱)
- 弗兰肯斯坦器官
- 肿瘤器官
- 木质器官
- 拟态器官
- 悚恐怖官
- 九头蛇器官
- 幻想种器官

## 设计审核重点关注领域

### 技术可行性
- 必须与现有 Chest Cavity Beyond API兼容
- 必须使用 NeoForge 事件系统
- 必须考虑性能影响 (27槽位实时检测)
- 必须支持数据序列化

### 平衡性考虑
- 器官获取难度 vs 搭配效果强度
- 避免出现唯一最优解
- 负面效果要合理
- 考虑多人游戏平衡

### 用户体验
- UI信息清晰易懂
- 反馈及时明确
- 学习曲线平缓
- 探索乐趣充足

## 审核标准

### 批准条件
- 设计满足所有核心需求和约束条件
- 技术方案可行且风险可控
- 文档完整、清晰、无矛盾
- 存在的问题均为非关键性问题

### 退回标准
- 存在重大技术缺陷或架构问题
- 核心功能需求未满足或遗漏
- 实现难度超出团队当前能力范围
- 设计存在严重的安全或性能风险
- 设计方案与项目目标或约束条件严重不符
- 文档不完整、描述不清或存在矛盾
- 违反强制性标准或合规要求

## 常见设计问题

### 性能问题
- 实时检测所有搭配可能造成性能问题
- 复杂的几何检测在27槽位系统中开销较大
- 粒子效果过度使用可能导致客户端卡顿

### 兼容性问题
- 某些效果可能与前置Mod的器官系统冲突
- 跨主题搭配可能产生意外组合
- 数据包系统需要考虑版本兼容性

### 平衡性问题
- 传说级搭配效果过于强大
- 某些搭配获取难度与效果不匹配
- 负面效果可能不够明显
- 隐藏搭配可能难以发现

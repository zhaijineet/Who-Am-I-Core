# 记忆

## 设计任务工作流

当用户下达设计任务时，必须遵循以下流程：

1. **接收设计任务** → 交给 `design-manager` 代理处理
2. **设计执行** → design-manager 根据实际情况启动 `creative-content-designer` 进行创作
3. **设计审核** → 设计文档完成后，启动 `design-reviewer` 进行审核
4. **审核循环**：
   - 审核通过 → 流程结束
   - 审核不通过 → 返回 `design-manager`，重新执行步骤2-3

**流程图**：
```
用户设计任务 → design-manager → creative-content-designer → 设计文档
                                                        ↓
                                                    design-reviewer
                                                        ↓
                                              审核通过 ✓ → 完成
                                              审核不通过 ✗ → design-manager (循环)
```

## 可用设计相关代理

- **design-manager**: 管理设计需求，拆解任务，协调设计创意人员
- **creative-content-designer**: 创意内容创作（品牌概念、广告文案、故事叙述、营销活动策划等）
- **design-reviewer**: 审核设计质量，评估设计可行性和是否符合要求

## 项目范围规则

### 主工作区
- **Who Am I Core** (`E:\Mod Project\Who-Am-I-Core`)
  - 所有添加、删除、更改操作必须在此工作区完成
  - 任何非此工作区的修改都是不被允许的

### 源码工作区
- **neoforge-21.1.219-merged** (`E:\Mod Project\neoforge-21.1.219-merged`)
  - Minecraft 和 NeoForge 的源码工作区
  - 任何涉及 MC 和 NF 的代码问题都应该优先搜索此工作区

### 前置 Mod 工作区（仅参考，不可修改）
- **Chest Cavity Beyond** (`E:\Mod Project\Chest Cavity Beyond`) - 最主要的前置 mod
- **MowziesMobs-Public** (`E:\Mod Project\MowziesMobs-Public`)
- **IceAndFire-CE** (`E:\Mod Project\IceAndFire-CE`)
- **FDBosses** (`E:\Mod Project\FDBosses`)
- **AnvilCraft** (`E:\Mod Project\AnvilCraft`)
- **Iron's Spells 'n Spellbooks** (`E:\Mod Project\irons-spells-n-spellbooks`)

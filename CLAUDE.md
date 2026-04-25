**不允许**使用完全限定名，应该先导入类在使用
**不考虑**任何向后兼容，因为此项目还在开发中

## Datagen 规则

模型 JSON 和语言 JSON 均通过 datagen 生成，**不要手动创建或编辑** `src/generated/resources/` 下的文件。

- **模型 JSON**：`ItemModelProvider` 遍历 `WAICItem.ITEM.getEntries()` 自动对所有物品调用 `basicItem()`。只有需要特殊覆盖（override）的物品（如弗兰肯斯坦心脏）才需在 `registerModels()` 中额外处理
- **语言 JSON**：`LanguageProvider` 在 `English()` 和 `Chinese()` 方法中分别定义双语翻译。通过 `addItem()`、`addOrganSkill()`、`addOrganDescription()` 等辅助方法生成翻译键
- **物品标签 JSON**：`ItemTagProvider` 的 `addTags()` 方法中注册所有器官的器官类型标签（HEART、LUNG 等）和自定义标签（MAGIC、FIRE、ICE 等）
- **运行 datagen**：`./gradlew runData`，输出到 `src/generated/resources/`

## 器官效果文档同步规则

修改器官代码时，必须同步更新根目录的 `器官效果.md` 文档。 规则如下：
1. **WAICOrgans 中的器官**：
   - 如果是系列器官（如墨水器官、颜料器官、木质器官等），写在"独立器官"部分**之前**，以独立的系列标题和分隔线分组
   - 如果是非系列器官（无法归入任何系列的独立器官），写在"独立器官"部分中
2. **其他位置的器官**（如 `FDBossesOrgans`、`AnvilCraftOrgans`、`MowziesMobOrgans`、`IceAndFireOrgans` 等）：
   - 系列标题前缀必须带有其所属 mod 的名称，例如："FDBosses 王国器官系列"、"AnvilCraft 浮霜器官系列"、"Mowzie's Mobs 器官系列"、"Ice and Fire 火龙器官系列"
   - 写在 WAICOrgans 所有器官（包括独立器官）之后

在父目录中，你可以找到neoforge的源码和多数mod的源码

NeoForge源码：
E:\Mod Project\neoforge-21.1.219-merged
主要前置：
E:\Mod Project\Chest Cavity Beyond
其他前置：
E:\Mod Project\MowziesMobs-Public
E:\Mod Project\IceAndFire-CE
E:\Mod Project\FDBosses
E:\Mod Project\cataclysm
E:\Mod Project\AnvilCraft
E:\Mod Project\irons-spells-n-spellbooks
E:\Mod Project\TouhouLittleMaid
## 项目规范

1. **不允许**使用完全限定名（FQN），必须先 import 再使用
2. **不考虑**任何向后兼容性（项目开发中，你有权利推倒重写任何架构）
3. 模型 JSON 和语言 JSON 均通过 datagen 生成，**不要手动创建或编辑** `src/generated/resources/` 下的文件
4. 运行 datagen：`./gradlew runData`
5. 修改器官代码时，**必须同步更**新根目录的 `器官效果.md` 文档
6. Windows Git Bash 环境：使用 Git Bash 语法命令，不要用 `findstr`、`dir` 等 Windows cmd 命令
7. 阅读 README.md ，了解项目解构

---

## 外部源码路径

- NeoForge：`E:\Mod Project\neoforge-21.1.219-merged`
- Chest Cavity Beyond：`E:\Mod Project\Chest Cavity Beyond`
- Ice and Fire：`E:\Mod Project\IceAndFire-CE`
- Mowzie's Mobs：`E:\Mod Project\MowziesMobs-Public`
- FDBosses：`E:\Mod Project\FDBosses`
- Cataclysm：`E:\Mod Project\cataclysm`
- AnvilCraft：`E:\Mod Project\AnvilCraft`
- Iron's Spells：`E:\Mod Project\irons-spells-n-spellbooks`
- Touhou Little Maid：`E:\Mod Project\TouhouLittleMaid`
- Companions：`E:\Mod Project\Companions`

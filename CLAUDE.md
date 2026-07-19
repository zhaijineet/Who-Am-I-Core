## 项目规范

1. **不允许**使用完全限定名（FQN），必须先 import 再使用
2. **不考虑**任何向后兼容性（项目开发中，你有权利推倒重写任何架构）
3. 模型 JSON 和语言 JSON 均通过 datagen 生成，**不要手动创建或编辑** `src/generated/resources/` 下的文件
4. 运行 datagen：`./gradlew runData`
5. Windows Git Bash 环境：使用 Git Bash 语法命令，不要用 `findstr`、`dir` 等 Windows cmd 命令
6. 阅读 README.md ，了解项目解构
7. 不允许使用变量名简称：例如如context写成ctx，不允许。 但多个单词使用其中一个单词命名是允许的：例如ItemStackData的变量名写成data。 不过如果有两种，例如ItemStackData和TooltipData，就需要把所有的单词写上了：例如ItemStackData的变量名应该是itemStackData
8. 不允许有致死量的注释，当信息足够自解释时，不需要添加注释。若要加javadoc需要一句话写出核心内容，不能有javadoc的注释头尾和注释内容加起来只占一行的情况，必须展开。若是为Minecraft或NeoForge的方法添加javadoc时，除非是渲染相关，否则不允许添加@param
9. 中文文本中，数值与其他字符之间**不加空格**（如写"0.5点生命值"而非"0.5 点生命值"、"2个器官"而非"2 个器官"）。仅英文/代码中的空格不受此规则约束

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

---

## Mixin 编写规范

1. **类声明**：`public abstract class XxxMixin`（Xxx为Target Class），需要访问父类 protected 成员（如 `Entity#tickCount`、`Entity#moveTo`、`level()`）时，`extends 目标类的父类` 并提供匹配的 `public` 构造器透传 super ，仅用 @Shadow、无需访问 protected 成员时可省略 extends
2. **自身引用**：在 @Inject / @Redirect / @ModifyArg 等注入方法中，若需要将 `this` 强转回目标类传给外部方法，统一定义 `@Unique whoAmICore$self()` 辅助方法返回 `(目标类)(Object)this`，禁止内联强转（@Override 覆写父类方法的死亡掉落类 mixin 除外，那种场景用局部变量 `XxxEntity self = (XxxEntity)(Object)this`）
3. **方法命名**：注入/重定向方法统一用 `whoAmICore$<原名>` 前缀，其中 `<原名>` 是 @Inject / @Redirect 等注解中 `method` 参数指定的目标方法名。同一 Mixin 类中若多个注入方法的方法名冲突，则追加 `$<被重定向的方法名>` 消歧（即 `whoAmICore$<method值>$<被重定向的方法名>`）；若仍冲突，继续追加 `$<用途>`，直到方法名不冲突为止
4. **@Shadow**：保留原可见性，字段按需加 `@Final`；方法用 `abstract`；`@Nullable` 等注解跟随原声明
5. **@Inject**：`method` 带完整签名（泛型/重载必需），`at` 明确，需要提前返回时 `cancellable = true`；参数表完整匹配原方法 + CallbackInfo / CallbackInfoReturnable；回调参数命名用 `ci` / `cir`
6. **注册**：`who_am_i_core.mixins.json` 的 `mixins` 数组按字母序插入；客户端专用进 `client` 数组
7. **命名一致性**：注入方法可见性首选 `public`（对齐 ChesedMiniRayMixin / LivingEntityMixin / PlayerMixin）；static 原方法对应 static 注入，且 static 注入方法必须用 `private`

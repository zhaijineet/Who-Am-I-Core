# Chest Cavity Beyond 器官属性与效果对应表

## 基础属性

### HEALTH (健康)
- **效果**: 影响最大生命值
- **计算方式**: 每点健康值 = 2点最大生命值
- **来源**: `OrganAttributeUtil.updateHealth()`
- **实现位置**: `OrganAttributeUtil.java:212-215`

### NERVES (神经效率)
- **效果**: 影响攻击速度、移动速度、挖掘速度
- **计算方式**:
  - 攻击速度: 使用基础值乘算 (ADD_MULTIPLIED_BASE)
  - 移动速度: 使用最终乘算 (ADD_MULTIPLIED_TOTAL)，当神经效率<=0时不允许移动
  - 挖掘速度: 对数缩放计算
- **来源**: `OrganAttributeUtil.updateNerves()`, `CommonEventHandler.handlerPlayerEvent$BreakSpeed()`
- **实现位置**: `OrganAttributeUtil.java:220-231`, `CommonEventHandler.java:370-378`

### DEFENSE (防御)
- **效果**: 减少非火焰、非护甲穿透伤害
- **计算方式**: 使用衰减缩放计算减伤
- **来源**: `CommonEventHandler.handlerLivingDamageEvent$Pre()`
- **实现位置**: `CommonEventHandler.java:335-338`

### STRENGTH (力量)
- **效果**: 增加攻击伤害
- **计算方式**: 正值使用直接缩放，负值使用对数缩放
- **来源**: `OrganAttributeUtil.updateStrength()`
- **实现位置**: `OrganAttributeUtil.java:236-246`

### SPEED (速度)
- **效果**: 影响移动速度
- **计算方式**: 使用基础值乘算，对数缩放
- **来源**: `OrganAttributeUtil.updateSpeed()`
- **实现位置**: `OrganAttributeUtil.java:251-258`

### LEAPING (跳跃力)
- **效果**: 影响跳跃力度和安全掉落距离
- **计算方式**:
  - 跳跃力度: 正值加算，负值基础值乘算
  - 安全掉落距离: 基础值乘算
- **来源**: `OrganAttributeUtil.updateLeaping()`
- **实现位置**: `OrganAttributeUtil.java:263-284`

## 食物消化属性

### DIGESTION (消化效率)
- **效果**: 影响食物获得的饥饿值
- **说明**: 当消化效率<=0时无法获得饥饿值
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:72-128`

### NUTRITION (营养获取效率)
- **效果**: 影响食物获得的饱食度
- **说明**: 当营养获取效率<=0时无法获得饱食度
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:72-128`

### CARNIVOROUS_DIGESTION (肉食消化)
- **效果**: 对肉类食物的消化加成
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:93-104`

### CARNIVOROUS_NUTRITION (肉食营养)
- **效果**: 对肉类食物的营养加成
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:97-107`

### HERBIVOROUS_DIGESTION (草食消化)
- **效果**: 对非肉类食物的消化加成
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:94-104`

### HERBIVOROUS_NUTRITION (草食营养)
- **效果**: 对非肉类食物的营养加成
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:98-107`

### SCAVENGER_DIGESTION (腐食消化)
- **效果**: 对有毒食物（如腐肉）的消化加成，并取消食物的负面效果
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`, `LivingEntityMixin.chestCavityBeyond$addEatEffect()`
- **实现位置**: `FoodDataMixin.java:95-111`, `LivingEntityMixin.java:67-79`

### SCAVENGER_NUTRITION (腐食营养)
- **效果**: 对有毒食物的营养加成，包括负面效果转化为饱食度
- **来源**: `FoodDataMixin.chestCavityBeyond$eat()`
- **实现位置**: `FoodDataMixin.java:100-118`

## 代谢属性

### ENDURANCE (耐力)
- **效果**: 减少活动消耗的饱食度
- **计算方式**: 使用反向缩放计算
- **来源**: `FoodDataMixin.chestCavityBeyond$modifyExhaustion()`
- **实现位置**: `FoodDataMixin.java:176-178`

### METABOLISM (新陈代谢效率)
- **效果**: 影响饥饿回复生命的速度
- **计算方式**: 正值加速恢复，负值延缓恢复
- **来源**: `FoodDataMixin.chestCavityBeyond$tick()`
- **实现位置**: `FoodDataMixin.java:141-151`

## 呼吸属性

### BREATH_CAPACITY (肺活量)
- **效果**: 决定是否能呼吸，以及呼吸速度
- **说明**: 当肺活量<=0时无法呼吸
- **来源**: `CommonHooksMixin.onLivingBreathe()`
- **实现位置**: `CommonHooksMixin.java:28-114`

### BREATH_RECOVERY (呼吸效率)
- **效果**: 影响在空气中的氧气恢复速度
- **来源**: `CommonHooksMixin.onLivingBreathe()`
- **实现位置**: `CommonHooksMixin.java:49-73`

### WATER_BREATH (水下呼吸)
- **效果**: 影响在水下的氧气恢复速度
- **来源**: `CommonHooksMixin.onLivingBreathe()`
- **实现位置**: `CommonHooksMixin.java:51-73`

## 抗性与免疫属性

### FIRE_RESISTANCE (火焰抗性)
- **效果**: 减少火焰伤害
- **计算方式**: 使用衰减缩放计算
- **来源**: `CommonEventHandler.handlerLivingDamageEvent$Pre()`
- **实现位置**: `CommonEventHandler.java:305-309`

### WATER_ALLERGY (水过敏)
- **效果**: 使生物对水敏感（类似末影人）
- **来源**: `LivingEntityMixin.chestCavityBeyond$aiStep()`
- **实现位置**: `LivingEntityMixin.java:30-39`

### DETOXIFICATION (解毒效率)
- **效果**: 减少有害药水效果的持续时间
- **计算方式**: 使用反向缩放计算
- **来源**: `CommonEventHandler.handlerMobEffectEvent$Applicable()`
- **实现位置**: `CommonEventHandler.java:247-264`

### FILTRATION (血液过滤效率)
- **效果**: 当<0时，每周期给予中毒效果
- **计算方式**: 负值越大概率越高，持续时间越长
- **来源**: `ChestCavityData.applyFiltration()`
- **实现位置**: `ChestCavityData.java:325-330`

### WITHERED (凋零化)
- **效果**:
  1. 攻击时给予目标凋零效果
  2. 对凋零效果免疫（根据值）
- **计算方式**: 每1点凋零化给予40 tick凋零效果，有下界之星则+200 tick和等级+1
- **来源**: `CommonEventHandler.handlerLivingDamageEvent$Pre()`, `CommonEventHandler.handlerMobEffectEvent$Applicable()`
- **实现位置**: `CommonEventHandler.java:318-331`, `CommonEventHandler.java:256-258`

## 末影属性

### ENDER (末影)
- **效果**:
  1. 溺水时随机传送
  2. 配合弹射物闪避使用
  3. 潜影贝传送需要
- **来源**: `CommonEventHandler.handlerLivingDamageEvent$Pre()`, `CommonEventHandler.handlerLivingIncomingDamageEvent()`
- **实现位置**: `CommonEventHandler.java:313-315`, `CommonEventHandler.java:282-286`

### PROJECTILE_DODGE (弹射物闪避)
- **效果**: 配合末影属性，受到弹射物攻击时随机传送闪避
- **说明**: 需要同时有末影属性才能触发
- **来源**: `CommonEventHandler.handlerLivingIncomingDamageEvent()`
- **实现位置**: `CommonEventHandler.java:280-287`

### LAUNCH (发射)
- **效果**: 攻击时将目标击飞到空中
- **来源**: `LivingEntityMixin.chestCavityBeyond$hurt()`
- **实现位置**: `LivingEntityMixin.java:44-61`

### CRYSTALLIZATION (结晶化)
- **效果**: 附近末影水晶爆炸时回复生命值
- **计算方式**: 每10 tick回复 crystallization/5 生命值
- **来源**: `EndCrystalMixin.onChanged()`, `EnderDragonMixin.chestCavityBeyond$checkCrystals()`
- **实现位置**: `EndCrystalMixin.java:46-54`, `EnderDragonMixin.java:26-30`

## 战斗属性

### EXPLOSIVE (爆炸)
- **效果**: 允许苦力怕爆炸和被点燃
- **说明**: 需要此属性>0才能进行爆炸相关行为
- **来源**: `CreeperMixin`
- **实现位置**: `CreeperMixin.java:23-65`

### VOMIT_FIREBALL (呕火)
- **效果**: 发射火球技能，增加烈焰人发射火球数量
- **来源**: `OrganSkillUtil.smallFireball()`, `BlazeMixin.chestCavityBeyond$tick()`
- **实现位置**: `InitItem.java:1153-1157`, `BlazeMixin.java:41`

### GHASTLY (可怖)
- **效果**: 允许恶魂发射大火球
- **说明**: 需要此属性>0才能发射
- **来源**: `GhastShootFireballGoalMixin.chestCavityBeyond$tick()`
- **实现位置**: `GhastShootFireballGoalMixin.java:28-32`

### IRON_REPAIR (铁修复)
- **效果**: 允许铁傀儡被铁锭修复生命值
- **计算方式**: 每点铁修复回复2.5生命值
- **来源**: `IronGolemMixin.chestCavityBeyond$mobInteract()`, `IronGolemMixin.chestCavityBeyond$modifyHeal()`
- **实现位置**: `IronGolemMixin.java:33-49`

### FURNACE_POWER (熔炉之力)
- **效果**: 定期回复饥饿值和饱食度
- **计算方式**: 根据效果等级决定频率
- **来源**: `FurnacePowerEffect.applyEffectTick()`
- **实现位置**: `FurnacePowerEffect.java:16-20`

### PHOTOSYNTHESIS (光合作用)
- **效果**: 白天在能看到天空时回复饥饿值和饱食度
- **计算方式**: 每800/photosynthesis tick回复1点饥饿或饱食
- **来源**: `FoodDataMixin.chestCavityBeyond$tick()`
- **实现位置**: `FoodDataMixin.java:152-165`

## 特殊机制

### 健康检查
- **说明**: 当健康值<=0时，每tick受到2点器官流失伤害
- **来源**: `ChestCavityData.applyHealth()`
- **实现位置**: `ChestCavityData.java:301-306`

### 神经效率移动限制
- **说明**: 当神经效率<=0时，移动速度被设置为-1（几乎无法移动）
- **来源**: `OrganAttributeUtil.updateNerves()`
- **实现位置**: `OrganAttributeUtil.java:227`

### 无重力伤害取消
- **说明**: 当重力<=0时，取消掉落伤害
- **来源**: `CommonEventHandler.handlerLivingIncomingDamageEvent()`
- **实现位置**: `CommonEventHandler.java:288-290`

## 计算方式说明

### MathUtil.getLog10Scale(double value)
对数缩放计算，用于平衡属性增长的收益

### MathUtil.getDirectScale(double value)
直接线性缩放

### MathUtil.getInverseScale(double value)
反向缩放，用于减少效果

### MathUtil.getAttenuationScale(double damage, double attribute)
衰减缩放，用于防御属性计算减伤

---

**生成时间**: 2026-03-15
**源码版本**: Chest Cavity Beyond (NeoForge 21.1.219)

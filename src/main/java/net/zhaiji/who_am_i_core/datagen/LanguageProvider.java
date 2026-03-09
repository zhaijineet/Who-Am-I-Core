package net.zhaiji.who_am_i_core.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.zhaiji.who_am_i_core.WhoAmICore;
import net.zhaiji.who_am_i_core.organ.MowziesMobOrgans;
import net.zhaiji.who_am_i_core.register.WAICCreativeModeTab;

import java.util.function.Supplier;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";

    public final String locale;

    public LanguageProvider(PackOutput output, String locale) {
        super(output, WhoAmICore.MOD_ID, locale);
        this.locale = locale;
    }

    public void English() {
        add(WAICCreativeModeTab.WHO_AM_I_CORE_TAB_TRANSLATABLE, "Who Am I");

        addItem(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, "Ferrous Wroughtnaut Heart Mirror");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 0, "Removes FOV modification");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 1, "Blocks entity attacks from the front");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 2, "Cannot move for 3 seconds after attacking");

        addItem(MowziesMobOrgans.CHEST_NOVA, "Chest Nova");
        addOrganDescription(MowziesMobOrgans.CHEST_NOVA, "It beats like a heart");
        addOrganSkill(
            MowziesMobOrgans.CHEST_NOVA,
            0,
            "When chest cavity is closed, burns organs in 3x3 area (except mechanical and magical organs)"
        );
        addOrganSkill(
            MowziesMobOrgans.CHEST_NOVA,
            1,
            "Umvuthana masks in 3x3 area summon corresponding followers, respawning 30 seconds after death"
        );
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 2, "Masks provide their potion effects to the owner");

        addItem(MowziesMobOrgans.CONTROL_ROD, "Control Rod");
        addOrganDescription(MowziesMobOrgans.CONTROL_ROD, "Which sun bird is this?");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 0, "When Control Rod is in 3x3 range of Chest Nova:");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 1, " ·Reduces Umvuthana follower respawn cooldown from 30s to 10s");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 2, " ·Masks also provide effects to followers");

        // 衰老器官翻译
        addItem(MowziesMobOrgans.AGED_HEART, "Aged Heart");
        addItem(MowziesMobOrgans.AGED_LUNG, "Aged Lung");
        addItem(MowziesMobOrgans.AGED_SPINE, "Aged Spine");
        addItem(MowziesMobOrgans.AGED_STOMACH, "Aged Stomach");
        addItem(MowziesMobOrgans.AGED_INTESTINE, "Aged Intestine");
        addItem(MowziesMobOrgans.AGED_KIDNEY, "Aged Kidney");
        addItem(MowziesMobOrgans.AGED_SPLEEN, "Aged Spleen");
        addItem(MowziesMobOrgans.AGED_LIVER, "Aged Liver");
        addItem(MowziesMobOrgans.AGED_APPENDIX, "Aged Appendix");
        addItem(MowziesMobOrgans.AGED_RIB, "Aged Rib");
        addItem(MowziesMobOrgans.AGED_MUSCLE, "Aged Muscle");
        addItem(MowziesMobOrgans.ZEN_HEART, "Zen Heart");

        // 泥峭器官翻译
        addItem(MowziesMobOrgans.BLUFF_CORE, "Bluff Core");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 0, "Allows eating dirt blocks, restores 4 hunger");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 1, "Use skill to use dirt blocks");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 2, "Different dirt types grant different effects:");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 3, " ·Grass/Moss/Mycelium → Strength II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 4, " ·Coarse Dirt/Podzol/Mud → Haste II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 5, " ·Rooted Dirt/Muddy Mangrove Roots → Resistance II");

        addItem(MowziesMobOrgans.BLUFF_TABLET, "Bluff Tablet");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 0, "Allows eating dirt items, restores 4 hunger");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 1, "Each tablet grants 2 absorption hearts (max: mud organs × 8)");

        addItem(MowziesMobOrgans.ACTIVE_BLUFF_ROD, "Active Bluff Rod");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 0, "Allows eating dirt items, restores 4 hunger");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 1, "Each rod grants 4 saturation when eating dirt");
    }

    public void Chinese() {
        add(WAICCreativeModeTab.WHO_AM_I_CORE_TAB_TRANSLATABLE, "我非我");

        addItem(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, "钢铁守护者护心镜");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 0, "移除FOV修改");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 1, "抵挡来自正面的实体的攻击");
        addOrganSkill(MowziesMobOrgans.FERROUS_WROUGHTNAUT_HEART_MIRROR, 2, "攻击后3秒内不能移动");


        addItem(MowziesMobOrgans.CHEST_NOVA, "胸中新星");
        addOrganDescription(MowziesMobOrgans.CHEST_NOVA, "它像心脏一样跳动着");
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 0, "当胸腔关闭时，烧毁3x3范围内的器官（机械系和魔法系器官除外）");
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 1, "3x3范围内的乌姆塔纳面具会召唤对应的追随者，追随者死亡30秒后重新召唤");
        addOrganSkill(MowziesMobOrgans.CHEST_NOVA, 2, "面具会为主人提供其药水效果");

        addItem(MowziesMobOrgans.CONTROL_ROD, "制御棒");
        addOrganDescription(MowziesMobOrgans.CONTROL_ROD, "这他妈是哪个太阳鸟？");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 0, "当制御棒在胸中新星的3x3范围内时：");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 1, " ·乌姆塔纳追随者重新召唤冷却时间从30秒缩短为10秒");
        addOrganSkill(MowziesMobOrgans.CONTROL_ROD, 2, " ·面具也会给追随者提供效果");

        // 衰老器官翻译
        addItem(MowziesMobOrgans.AGED_HEART, "衰老心脏");
        addItem(MowziesMobOrgans.AGED_LUNG, "衰老肺脏");
        addItem(MowziesMobOrgans.AGED_SPINE, "衰老脊柱");
        addItem(MowziesMobOrgans.AGED_STOMACH, "衰老胃");
        addItem(MowziesMobOrgans.AGED_INTESTINE, "衰老肠子");
        addItem(MowziesMobOrgans.AGED_KIDNEY, "衰老肾脏");
        addItem(MowziesMobOrgans.AGED_SPLEEN, "衰老脾脏");
        addItem(MowziesMobOrgans.AGED_LIVER, "衰老肝脏");
        addItem(MowziesMobOrgans.AGED_APPENDIX, "衰老阑尾");
        addItem(MowziesMobOrgans.AGED_RIB, "衰老肋骨");
        addItem(MowziesMobOrgans.AGED_MUSCLE, "衰老肌肉");
        addItem(MowziesMobOrgans.ZEN_HEART, "禅心");

        // 泥峭器官翻译
        addItem(MowziesMobOrgans.BLUFF_CORE, "泥峭核心");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 0, "允许食用泥土方块，恢复4点饥饿值");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 1, "使用技能可使用泥土方块");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 2, "不同泥土类型提供不同效果：");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 3, " ·草方块/苔藓块/菌丝：力量II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 4, " ·砂土/灰化土/泥巴：急迫II");
        addOrganSkill(MowziesMobOrgans.BLUFF_CORE, 5, " ·缠根泥土/含泥红树根：抗性提升II");

        addItem(MowziesMobOrgans.BLUFF_TABLET, "泥峭铭文板");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 0, "允许食用泥土物品，恢复4点饥饿值");
        addOrganSkill(MowziesMobOrgans.BLUFF_TABLET, 1, "每有一个铭文板，食用泥土时获得2点吸收生命值（上限为泥峭器官数量x8）");

        addItem(MowziesMobOrgans.ACTIVE_BLUFF_ROD, "活性泥峭棒");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 0, "允许食用泥土物品，恢复4点饥饿值");
        addOrganSkill(MowziesMobOrgans.ACTIVE_BLUFF_ROD, 1, "每有一个泥峭棒增加，食用泥土时增加4点饱和度");
    }

    private void addOrganSkill(Supplier<Item> item, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".skill", value);
    }

    private void addOrganSkill(Supplier<Item> item, int index, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".skill." + index, value);
    }

    private void addOrganDescription(Supplier<Item> item, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".description", value);
    }

    private void addOrganDescription(Supplier<Item> item, int index, String value) {
        add("organ." + WhoAmICore.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(item.get()).getPath() + ".description." + index, value);
    }

    @Override
    protected void addTranslations() {
        switch (locale) {
            case EN_US -> English();
            case ZH_CN -> Chinese();
        }
    }
}

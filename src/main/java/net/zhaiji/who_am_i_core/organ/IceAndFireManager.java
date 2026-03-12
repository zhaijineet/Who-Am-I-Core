package net.zhaiji.who_am_i_core.organ;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.zhaiji.chestcavitybeyond.builder.OrganBuilder;
import net.zhaiji.chestcavitybeyond.register.InitAttribute;

public class IceAndFireManager {
    public static void register() {
        // 火龙心脏
        OrganBuilder.builder(IafItems.FIRE_DRAGON_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .build();
        // 冰龙心脏
        OrganBuilder.builder(IafItems.ICE_DRAGON_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .build();
        // 电龙心脏
        OrganBuilder.builder(IafItems.LIGHTNING_DRAGON_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 2)
            .build();
        // 九头蛇心脏
        OrganBuilder.builder(IafItems.HYDRA_HEART.get())
            .addValueAttribute(InitAttribute.HEALTH, 1.5)
            .addValueAttribute(InitAttribute.METABOLISM, 10)
            .build();
    }
}

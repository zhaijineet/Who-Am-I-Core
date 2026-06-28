package net.zhaiji.who_am_i_core.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.zhaiji.chestcavitybeyond.builder.DamageTypeBuilder;
import net.zhaiji.who_am_i_core.WhoAmICore;

public class WAICDamageType {
    public static final ResourceKey<DamageType> RAILGUN = ResourceKey.create(
        Registries.DAMAGE_TYPE, WhoAmICore.of("railgun"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(RAILGUN, DamageTypeBuilder.builder(RAILGUN).build());
    }
}

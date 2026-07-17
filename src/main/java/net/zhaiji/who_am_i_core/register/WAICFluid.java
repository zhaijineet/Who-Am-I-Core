package net.zhaiji.who_am_i_core.register;

import io.redspace.ironsspellbooks.fluids.NoopFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zhaiji.who_am_i_core.WhoAmICore;

import java.util.function.Supplier;

public class WAICFluid {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, WhoAmICore.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, WhoAmICore.MOD_ID);

    public static final Supplier<FluidType> FIRE_DRAGON_BLOOD_TYPE = FLUID_TYPES.register("fire_dragon_blood", () -> new FluidType(FluidType.Properties.create()));
    public static final Supplier<FluidType> ICE_DRAGON_BLOOD_TYPE = FLUID_TYPES.register("ice_dragon_blood", () -> new FluidType(FluidType.Properties.create()));
    public static final Supplier<FluidType> LIGHTNING_DRAGON_BLOOD_TYPE = FLUID_TYPES.register("lightning_dragon_blood", () -> new FluidType(FluidType.Properties.create()));

    public static final Supplier<NoopFluid> FIRE_DRAGON_BLOOD_FLUID = registerNoop("fire_dragon_blood", FIRE_DRAGON_BLOOD_TYPE);
    public static final Supplier<NoopFluid> ICE_DRAGON_BLOOD_FLUID = registerNoop("ice_dragon_blood", ICE_DRAGON_BLOOD_TYPE);
    public static final Supplier<NoopFluid> LIGHTNING_DRAGON_BLOOD_FLUID = registerNoop("lightning_dragon_blood", LIGHTNING_DRAGON_BLOOD_TYPE);

    private static Supplier<NoopFluid> registerNoop(String name, Supplier<FluidType> fluidType) {
        DeferredHolder<Fluid, NoopFluid> holder = DeferredHolder.create(Registries.FLUID, WhoAmICore.of(name));
        BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(fluidType, holder::value, holder::value).bucket(() -> Items.AIR);
        FLUIDS.register(name, () -> new NoopFluid(properties));
        return holder;
    }
}

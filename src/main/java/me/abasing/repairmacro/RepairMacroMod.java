package com.abasing.repairmacro;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "abasing_repairmacro", name = "Repair Macro Mod", version = "1.0")
public class RepairMacroMod {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
    }
}

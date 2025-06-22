package com.epicwolf.sra.command;

import com.epicwolf.sra.config.ModConfigs;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ModCommands {

    public static void initialize() {
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, manager, b) -> {
            ModConfigs.assignConfigs();
        });
    }
}

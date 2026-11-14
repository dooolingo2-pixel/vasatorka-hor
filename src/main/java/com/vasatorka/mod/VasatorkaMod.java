package com.vasatorka.mod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(VasatorkaMod.MOD_ID)
public class VasatorkaMod {
    public static final String MOD_ID = "vasatorka";
    private static final Logger LOGGER = LogUtils.getLogger();

    public VasatorkaMod() {
        LOGGER.info("Vasatorka mod loaded");
    }
}

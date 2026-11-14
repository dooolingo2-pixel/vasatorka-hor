package com.vasatorka.mod;

import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientSetup {
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Василь виглядає точно як Варден, тому беремо ванільний рендерер
        event.registerEntityRenderer(VasatorkaMod.VASYL.get(), WardenRenderer::new);
    }
}

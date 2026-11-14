package com.vasatorka.mod;

import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientSetup {
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(VasatorkaMod.VASYL.get(), VasylRenderer::new);
        event.registerEntityRenderer(VasatorkaMod.GOPNIK.get(), GopnikRenderer::new);
    }
}

package com.vasatorka.mod;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

public class GopnikRenderer extends ZombieRenderer {
    private static final Identifier TEXTURE = VasatorkaMod.id("textures/entity/gopnik.png");

    public GopnikRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(ZombieRenderState state) {
        return TEXTURE;
    }
}

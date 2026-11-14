package com.vasatorka.mod;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.resources.Identifier;

public class VasylRenderer extends WardenRenderer {
    private static final Identifier TEXTURE = VasatorkaMod.id("textures/entity/vasyl.png");

    public VasylRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(WardenRenderState state) {
        return TEXTURE;
    }
}

/*
 * Internal private/static methods:
 *   Lnet/minecraft/client/gl/SimpleFramebuffer;resize(II)V
 */
package net.minecraft.client.gl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.Framebuffer;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class SimpleFramebuffer
extends Framebuffer {
    public SimpleFramebuffer(@Nullable String name, int width, int height, boolean useDepthAttachment) {
        super(name, useDepthAttachment);
        RenderSystem.assertOnRenderThread();
        this.resize(width, height);
    }
}


package net.minecraft.client.render.state;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WeatherRendering;

@Environment(value=EnvType.CLIENT)
public class WeatherRenderState {
    public final List<WeatherRendering.Piece> rainPieces = new ArrayList<WeatherRendering.Piece>();
    public final List<WeatherRendering.Piece> snowPieces = new ArrayList<WeatherRendering.Piece>();
    public float intensity;
    public int radius;

    public void clear() {
        this.rainPieces.clear();
        this.snowPieces.clear();
        this.intensity = 0.0f;
        this.radius = 0;
    }
}


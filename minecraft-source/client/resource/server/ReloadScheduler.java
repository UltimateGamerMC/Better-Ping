package net.minecraft.client.resource.server;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public interface ReloadScheduler {
    public void scheduleReload(ReloadContext var1);

    @Environment(value=EnvType.CLIENT)
    public static interface ReloadContext {
        public void onSuccess();

        public void onFailure(boolean var1);

        public List<PackInfo> getPacks();
    }

    @Environment(value=EnvType.CLIENT)
    public record PackInfo(UUID id, Path path) {
    }
}


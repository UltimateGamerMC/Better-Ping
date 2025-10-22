package net.minecraft.loot.provider.score;

import java.util.Set;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.score.LootScoreProviderType;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.util.context.ContextParameter;
import org.jetbrains.annotations.Nullable;

public interface LootScoreProvider {
    @Nullable
    public ScoreHolder getScoreHolder(LootContext var1);

    public LootScoreProviderType getType();

    public Set<ContextParameter<?>> getRequiredParameters();
}


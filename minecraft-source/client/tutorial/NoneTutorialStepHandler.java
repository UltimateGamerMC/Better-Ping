package net.minecraft.client.tutorial;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.tutorial.TutorialManager;
import net.minecraft.client.tutorial.TutorialStepHandler;

@Environment(value=EnvType.CLIENT)
public class NoneTutorialStepHandler
implements TutorialStepHandler {
    public NoneTutorialStepHandler(TutorialManager manager) {
    }
}


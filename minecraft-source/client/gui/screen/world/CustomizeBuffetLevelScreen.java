/*
 * External method calls:
 *   Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;
 *   Lnet/minecraft/client/world/GeneratorOptionsHolder;selectedDimensions()Lnet/minecraft/world/dimension/DimensionOptionsRegistryHolder;
 *   Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;vertical()Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;
 *   Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;spacing(I)Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;
 *   Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addHeader(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;
 *   Lnet/minecraft/client/gui/widget/Positioner;alignHorizontalCenter()Lnet/minecraft/client/gui/widget/Positioner;
 *   Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addBody(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;
 *   Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;horizontal()Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;
 *   Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addFooter(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;
 *   Lnet/minecraft/client/gui/widget/ButtonWidget;builder(Lnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;
 *   Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;build()Lnet/minecraft/client/gui/widget/ButtonWidget;
 *   Lnet/minecraft/client/gui/screen/world/CustomizeBuffetLevelScreen$BuffetBiomesListWidget;children()Ljava/util/List;
 *   Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;forEachChild(Ljava/util/function/Consumer;)V
 *   Lnet/minecraft/client/gui/screen/world/CustomizeBuffetLevelScreen$BuffetBiomesListWidget;position(ILnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;)V
 *   Lnet/minecraft/registry/Registry;streamEntries()Ljava/util/stream/Stream;
 *   Lnet/minecraft/text/MutableText;withColor(I)Lnet/minecraft/text/MutableText;
 */
package net.minecraft.client.gui.screen.world;

import com.ibm.icu.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class CustomizeBuffetLevelScreen
extends Screen {
    private static final Text BUFFET_BIOME_TEXT = Text.translatable("createWorld.customize.buffet.biome").withColor(Colors.GRAY);
    private static final int field_49494 = 8;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private final Screen parent;
    private final Consumer<RegistryEntry<Biome>> onDone;
    final Registry<Biome> biomeRegistry;
    private BuffetBiomesListWidget biomeSelectionList;
    RegistryEntry<Biome> biome;
    private ButtonWidget confirmButton;

    public CustomizeBuffetLevelScreen(Screen parent, GeneratorOptionsHolder generatorOptionsHolder, Consumer<RegistryEntry<Biome>> onDone) {
        super(Text.translatable("createWorld.customize.buffet.title"));
        this.parent = parent;
        this.onDone = onDone;
        this.biomeRegistry = generatorOptionsHolder.getCombinedRegistryManager().getOrThrow(RegistryKeys.BIOME);
        RegistryEntry lv = this.biomeRegistry.getOptional(BiomeKeys.PLAINS).or(() -> this.biomeRegistry.streamEntries().findAny()).orElseThrow();
        this.biome = generatorOptionsHolder.selectedDimensions().getChunkGenerator().getBiomeSource().getBiomes().stream().findFirst().orElse(lv);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        DirectionalLayoutWidget lv = this.layout.addHeader(DirectionalLayoutWidget.vertical().spacing(8));
        lv.getMainPositioner().alignHorizontalCenter();
        lv.add(new TextWidget(this.getTitle(), this.textRenderer));
        lv.add(new TextWidget(BUFFET_BIOME_TEXT, this.textRenderer));
        this.biomeSelectionList = this.layout.addBody(new BuffetBiomesListWidget());
        DirectionalLayoutWidget lv2 = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        this.confirmButton = lv2.add(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            this.onDone.accept(this.biome);
            this.close();
        }).build());
        lv2.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close()).build());
        this.biomeSelectionList.setSelected((BuffetBiomesListWidget.BuffetBiomeItem)this.biomeSelectionList.children().stream().filter(entry -> Objects.equals(entry.biome, this.biome)).findFirst().orElse(null));
        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    @Override
    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
        this.biomeSelectionList.position(this.width, this.layout);
    }

    void refreshConfirmButton() {
        this.confirmButton.active = this.biomeSelectionList.getSelectedOrNull() != null;
    }

    @Environment(value=EnvType.CLIENT)
    class BuffetBiomesListWidget
    extends AlwaysSelectedEntryListWidget<BuffetBiomeItem> {
        BuffetBiomesListWidget() {
            super(CustomizeBuffetLevelScreen.this.client, CustomizeBuffetLevelScreen.this.width, CustomizeBuffetLevelScreen.this.height - 77, 40, 16);
            Collator collator = Collator.getInstance(Locale.getDefault());
            CustomizeBuffetLevelScreen.this.biomeRegistry.streamEntries().map(entry -> new BuffetBiomeItem((RegistryEntry.Reference<Biome>)entry)).sorted(Comparator.comparing(biome -> biome.text.getString(), collator)).forEach(entry -> this.addEntry(entry));
        }

        @Override
        public void setSelected(@Nullable BuffetBiomeItem arg) {
            super.setSelected(arg);
            if (arg != null) {
                CustomizeBuffetLevelScreen.this.biome = arg.biome;
            }
            CustomizeBuffetLevelScreen.this.refreshConfirmButton();
        }

        @Environment(value=EnvType.CLIENT)
        class BuffetBiomeItem
        extends AlwaysSelectedEntryListWidget.Entry<BuffetBiomeItem> {
            final RegistryEntry.Reference<Biome> biome;
            final Text text;

            public BuffetBiomeItem(RegistryEntry.Reference<Biome> biome) {
                this.biome = biome;
                Identifier lv = biome.registryKey().getValue();
                String string = lv.toTranslationKey("biome");
                this.text = Language.getInstance().hasTranslation(string) ? Text.translatable(string) : Text.literal(lv.toString());
            }

            @Override
            public Text getNarration() {
                return Text.translatable("narrator.select", this.text);
            }

            @Override
            public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
                context.drawTextWithShadow(CustomizeBuffetLevelScreen.this.textRenderer, this.text, this.getContentX() + 5, this.getContentY() + 2, Colors.WHITE);
            }

            @Override
            public boolean mouseClicked(Click click, boolean doubled) {
                BuffetBiomesListWidget.this.setSelected(this);
                return super.mouseClicked(click, doubled);
            }
        }
    }
}


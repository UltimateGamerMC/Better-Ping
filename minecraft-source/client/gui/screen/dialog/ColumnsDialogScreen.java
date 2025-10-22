/*
 * External method calls:
 *   Lnet/minecraft/client/gui/screen/dialog/DialogScreen;initBody(Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;Lnet/minecraft/client/gui/screen/dialog/DialogControls;Lnet/minecraft/dialog/type/Dialog;Lnet/minecraft/client/gui/screen/dialog/DialogNetworkAccess;)V
 *   Lnet/minecraft/client/gui/screen/dialog/DialogScreen;initHeaderAndFooter(Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;Lnet/minecraft/client/gui/screen/dialog/DialogControls;Lnet/minecraft/dialog/type/Dialog;Lnet/minecraft/client/gui/screen/dialog/DialogNetworkAccess;)V
 *   Lnet/minecraft/dialog/type/ColumnsDialog;exitAction()Ljava/util/Optional;
 *   Lnet/minecraft/client/gui/screen/dialog/DialogControls;createButton(Lnet/minecraft/dialog/DialogActionButtonData;)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;
 *   Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;build()Lnet/minecraft/client/gui/widget/ButtonWidget;
 *   Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addFooter(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;
 *
 * Internal private/static methods:
 *   Lnet/minecraft/client/gui/screen/dialog/ColumnsDialogScreen;streamActionButtonData(Lnet/minecraft/dialog/type/ColumnsDialog;Lnet/minecraft/client/gui/screen/dialog/DialogNetworkAccess;)Ljava/util/stream/Stream;
 *   Lnet/minecraft/client/gui/screen/dialog/ColumnsDialogScreen;createGridWidget(Ljava/util/List;I)Lnet/minecraft/client/gui/widget/Widget;
 *   Lnet/minecraft/client/gui/screen/dialog/ColumnsDialogScreen;initHeaderAndFooter(Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;Lnet/minecraft/client/gui/screen/dialog/DialogControls;Lnet/minecraft/dialog/type/ColumnsDialog;Lnet/minecraft/client/gui/screen/dialog/DialogNetworkAccess;)V
 *   Lnet/minecraft/client/gui/screen/dialog/ColumnsDialogScreen;initBody(Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;Lnet/minecraft/client/gui/screen/dialog/DialogControls;Lnet/minecraft/dialog/type/ColumnsDialog;Lnet/minecraft/client/gui/screen/dialog/DialogNetworkAccess;)V
 */
package net.minecraft.client.gui.screen.dialog;

import java.util.List;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.dialog.DialogControls;
import net.minecraft.client.gui.screen.dialog.DialogNetworkAccess;
import net.minecraft.client.gui.screen.dialog.DialogScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.dialog.DialogActionButtonData;
import net.minecraft.dialog.type.ColumnsDialog;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public abstract class ColumnsDialogScreen<T extends ColumnsDialog>
extends DialogScreen<T> {
    public static final int field_61004 = 5;

    public ColumnsDialogScreen(@Nullable Screen parent, T dialog, DialogNetworkAccess networkAccess) {
        super(parent, dialog, networkAccess);
    }

    @Override
    protected void initBody(DirectionalLayoutWidget arg, DialogControls arg2, T arg3, DialogNetworkAccess arg4) {
        super.initBody(arg, arg2, arg3, arg4);
        List<ButtonWidget> list = this.streamActionButtonData(arg3, arg4).map(actionButtonData -> arg2.createButton((DialogActionButtonData)actionButtonData).build()).toList();
        arg.add(ColumnsDialogScreen.createGridWidget(list, arg3.columns()));
    }

    protected abstract Stream<DialogActionButtonData> streamActionButtonData(T var1, DialogNetworkAccess var2);

    @Override
    protected void initHeaderAndFooter(ThreePartsLayoutWidget arg, DialogControls arg2, T arg3, DialogNetworkAccess arg4) {
        super.initHeaderAndFooter(arg, arg2, arg3, arg4);
        arg3.exitAction().ifPresentOrElse(actionButtonData -> arg.addFooter(arg2.createButton((DialogActionButtonData)actionButtonData).build()), () -> arg.setFooterHeight(5));
    }
}


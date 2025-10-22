/*
 * External method calls:
 *   Lnet/minecraft/client/render/entity/model/ArmPosing;hold(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Z)V
 *   Lnet/minecraft/client/render/entity/model/ArmPosing;charge(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;FIZ)V
 *   Lnet/minecraft/client/render/entity/model/ArmPosing;meleeAttack(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/util/Arm;FF)V
 *   Lnet/minecraft/client/render/entity/model/PiglinBaseEntityModel;animateArms(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;F)V
 *
 * Internal private/static methods:
 *   Lnet/minecraft/client/render/entity/model/PiglinEntityModel;rotateMainArm(Lnet/minecraft/client/render/entity/state/PiglinEntityRenderState;)V
 *   Lnet/minecraft/client/render/entity/model/PiglinEntityModel;animateArms(Lnet/minecraft/client/render/entity/state/PiglinEntityRenderState;F)V
 */
package net.minecraft.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ArmPosing;
import net.minecraft.client.render.entity.model.PiglinBaseEntityModel;
import net.minecraft.client.render.entity.state.PiglinEntityRenderState;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(value=EnvType.CLIENT)
public class PiglinEntityModel
extends PiglinBaseEntityModel<PiglinEntityRenderState> {
    public PiglinEntityModel(ModelPart arg) {
        super(arg);
    }

    @Override
    public void setAngles(PiglinEntityRenderState arg) {
        super.setAngles(arg);
        float f = 0.5235988f;
        float g = arg.handSwingProgress;
        PiglinActivity lv = arg.activity;
        if (lv == PiglinActivity.DANCING) {
            float h = arg.age / 60.0f;
            this.rightEar.roll = 0.5235988f + (float)Math.PI / 180 * MathHelper.sin(h * 30.0f) * 10.0f;
            this.leftEar.roll = -0.5235988f - (float)Math.PI / 180 * MathHelper.cos(h * 30.0f) * 10.0f;
            this.head.originX += MathHelper.sin(h * 10.0f);
            this.head.originY += MathHelper.sin(h * 40.0f) + 0.4f;
            this.rightArm.roll = (float)Math.PI / 180 * (70.0f + MathHelper.cos(h * 40.0f) * 10.0f);
            this.leftArm.roll = this.rightArm.roll * -1.0f;
            this.rightArm.originY += MathHelper.sin(h * 40.0f) * 0.5f - 0.5f;
            this.leftArm.originY += MathHelper.sin(h * 40.0f) * 0.5f + 0.5f;
            this.body.originY += MathHelper.sin(h * 40.0f) * 0.35f;
        } else if (lv == PiglinActivity.ATTACKING_WITH_MELEE_WEAPON && g == 0.0f) {
            this.rotateMainArm(arg);
        } else if (lv == PiglinActivity.CROSSBOW_HOLD) {
            ArmPosing.hold(this.rightArm, this.leftArm, this.head, arg.mainArm == Arm.RIGHT);
        } else if (lv == PiglinActivity.CROSSBOW_CHARGE) {
            ArmPosing.charge(this.rightArm, this.leftArm, arg.piglinCrossbowPullTime, arg.itemUseTime, arg.mainArm == Arm.RIGHT);
        } else if (lv == PiglinActivity.ADMIRING_ITEM) {
            this.head.pitch = 0.5f;
            this.head.yaw = 0.0f;
            if (arg.mainArm == Arm.LEFT) {
                this.rightArm.yaw = -0.5f;
                this.rightArm.pitch = -0.9f;
            } else {
                this.leftArm.yaw = 0.5f;
                this.leftArm.pitch = -0.9f;
            }
        }
    }

    @Override
    protected void animateArms(PiglinEntityRenderState arg, float f) {
        float g = arg.handSwingProgress;
        if (g > 0.0f && arg.activity == PiglinActivity.ATTACKING_WITH_MELEE_WEAPON) {
            ArmPosing.meleeAttack(this.rightArm, this.leftArm, arg.mainArm, g, arg.age);
            return;
        }
        super.animateArms(arg, f);
    }

    private void rotateMainArm(PiglinEntityRenderState state) {
        if (state.mainArm == Arm.LEFT) {
            this.leftArm.pitch = -1.8f;
        } else {
            this.rightArm.pitch = -1.8f;
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
    }
}


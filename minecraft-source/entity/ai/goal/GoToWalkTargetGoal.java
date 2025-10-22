/*
 * External method calls:
 *   Lnet/minecraft/entity/ai/NoPenaltyTargeting;findTo(Lnet/minecraft/entity/mob/PathAwareEntity;IILnet/minecraft/util/math/Vec3d;D)Lnet/minecraft/util/math/Vec3d;
 *   Lnet/minecraft/entity/ai/pathing/EntityNavigation;startMovingTo(DDDD)Z
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class GoToWalkTargetGoal
extends Goal {
    private final PathAwareEntity mob;
    private double x;
    private double y;
    private double z;
    private final double speed;

    public GoToWalkTargetGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.mob.isInPositionTargetRange()) {
            return false;
        }
        Vec3d lv = NoPenaltyTargeting.findTo(this.mob, 16, 7, Vec3d.ofBottomCenter(this.mob.getPositionTarget()), 1.5707963705062866);
        if (lv == null) {
            return false;
        }
        this.x = lv.x;
        this.y = lv.y;
        this.z = lv.z;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed);
    }
}


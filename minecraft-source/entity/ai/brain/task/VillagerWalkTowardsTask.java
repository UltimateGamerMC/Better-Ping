/*
 * External method calls:
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer;task(Ljava/util/function/Function;)Lnet/minecraft/entity/ai/brain/task/SingleTickTask;
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer$TaskContext;queryMemoryOptional(Lnet/minecraft/entity/ai/brain/MemoryModuleType;)Lnet/minecraft/entity/ai/brain/task/TaskTriggerer;
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer$TaskContext;queryMemoryAbsent(Lnet/minecraft/entity/ai/brain/MemoryModuleType;)Lnet/minecraft/entity/ai/brain/task/TaskTriggerer;
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer$TaskContext;queryMemoryValue(Lnet/minecraft/entity/ai/brain/MemoryModuleType;)Lnet/minecraft/entity/ai/brain/task/TaskTriggerer;
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer$TaskContext;group(Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;Lcom/mojang/datafixers/kinds/App;)Lcom/mojang/datafixers/Products$P3;
 *   Lnet/minecraft/entity/passive/VillagerEntity;releaseTicketFor(Lnet/minecraft/entity/ai/brain/MemoryModuleType;)V
 *   Lnet/minecraft/entity/ai/brain/MemoryQueryResult;remember(Ljava/lang/Object;)V
 *   Lnet/minecraft/entity/ai/NoPenaltyTargeting;findTo(Lnet/minecraft/entity/mob/PathAwareEntity;IILnet/minecraft/util/math/Vec3d;D)Lnet/minecraft/util/math/Vec3d;
 */
package net.minecraft.entity.ai.brain.task;

import java.util.Optional;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;

public class VillagerWalkTowardsTask {
    public static SingleTickTask<VillagerEntity> create(MemoryModuleType<GlobalPos> destination, float speed, int completionRange, int maxDistance, int maxRunTime) {
        return TaskTriggerer.task(context -> context.group(context.queryMemoryOptional(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE), context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET), context.queryMemoryValue(destination)).apply(context, (cantReachWalkTargetSince, walkTarget, destinationResult) -> (world, entity, time) -> {
            GlobalPos lv = (GlobalPos)context.getValue(destinationResult);
            Optional optional = context.getOptionalValue(cantReachWalkTargetSince);
            if (lv.dimension() != world.getRegistryKey() || optional.isPresent() && world.getTime() - (Long)optional.get() > (long)maxRunTime) {
                entity.releaseTicketFor(destination);
                destinationResult.forget();
                cantReachWalkTargetSince.remember(time);
            } else if (lv.pos().getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                Vec3d lv2 = null;
                int m = 0;
                int n = 1000;
                while (lv2 == null || BlockPos.ofFloored(lv2).getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                    lv2 = NoPenaltyTargeting.findTo(entity, 15, 7, Vec3d.ofBottomCenter(lv.pos()), 1.5707963705062866);
                    if (++m != 1000) continue;
                    entity.releaseTicketFor(destination);
                    destinationResult.forget();
                    cantReachWalkTargetSince.remember(time);
                    return true;
                }
                walkTarget.remember(new WalkTarget(lv2, speed, completionRange));
            } else if (lv.pos().getManhattanDistance(entity.getBlockPos()) > completionRange) {
                walkTarget.remember(new WalkTarget(lv.pos(), speed, completionRange));
            }
            return true;
        }));
    }
}


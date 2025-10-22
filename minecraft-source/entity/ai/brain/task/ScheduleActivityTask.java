/*
 * External method calls:
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer;task(Ljava/util/function/Function;)Lnet/minecraft/entity/ai/brain/task/SingleTickTask;
 *   Lnet/minecraft/entity/ai/brain/task/TaskTriggerer$TaskContext;point(Ljava/lang/Object;)Lnet/minecraft/entity/ai/brain/task/TaskTriggerer;
 *   Lnet/minecraft/entity/ai/brain/Brain;refreshActivities(JJ)V
 */
package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;

public class ScheduleActivityTask {
    public static Task<LivingEntity> create() {
        return TaskTriggerer.task(context -> context.point((world, entity, time) -> {
            entity.getBrain().refreshActivities(world.getTimeOfDay(), world.getTime());
            return true;
        }));
    }
}


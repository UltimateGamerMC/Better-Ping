/*
 * External method calls:
 *   Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/Entity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V
 *   Lnet/minecraft/entity/projectile/ProjectileEntity;spawnWithVelocity(Lnet/minecraft/entity/projectile/ProjectileEntity$ProjectileCreator;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;FFF)Lnet/minecraft/entity/projectile/ProjectileEntity;
 *   Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V
 *   Lnet/minecraft/item/ItemStack;decrementUnlessCreative(ILnet/minecraft/entity/LivingEntity;)V
 */
package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EnderPearlItem
extends Item {
    public static float POWER = 1.5f;

    public EnderPearlItem(Item.Settings arg) {
        super(arg);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack lv = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (world instanceof ServerWorld) {
            ServerWorld lv2 = (ServerWorld)world;
            ProjectileEntity.spawnWithVelocity(EnderPearlEntity::new, lv2, lv, user, 0.0f, POWER, 1.0f);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        lv.decrementUnlessCreative(1, user);
        return ActionResult.SUCCESS;
    }
}


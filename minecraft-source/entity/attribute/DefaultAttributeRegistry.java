/*
 * External method calls:
 *   Lnet/minecraft/registry/DefaultedRegistry;stream()Ljava/util/stream/Stream;
 *   Lnet/minecraft/util/Util;logErrorOrPause(Ljava/lang/String;)V
 *   Lnet/minecraft/entity/passive/AllayEntity;createAllayAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;build()Lnet/minecraft/entity/attribute/DefaultAttributeContainer;
 *   Lnet/minecraft/entity/passive/ArmadilloEntity;createArmadilloAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/decoration/ArmorStandEntity;createArmorStandAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/AxolotlEntity;createAxolotlAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/BatEntity;createBatAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/BeeEntity;createBeeAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/BlazeEntity;createBlazeAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/BoggedEntity;createBoggedAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/CatEntity;createCatAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/CamelEntity;createCamelAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/CaveSpiderEntity;createCaveSpiderAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/ChickenEntity;createChickenAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/FishEntity;createFishAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/CopperGolemEntity;createCopperGolemAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/CowEntity;createCowAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/CreakingEntity;createCreakingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/CreeperEntity;createCreeperAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/DolphinEntity;createDolphinAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/AbstractDonkeyEntity;createAbstractDonkeyAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/DrownedEntity;createDrownedAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/ElderGuardianEntity;createElderGuardianAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/EndermanEntity;createEndermanAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/EndermiteEntity;createEndermiteAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/boss/dragon/EnderDragonEntity;createEnderDragonAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/EvokerEntity;createEvokerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/BreezeEntity;createBreezeAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/FoxEntity;createFoxAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/FrogEntity;createFrogAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/GhastEntity;createGhastAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/HappyGhastEntity;createHappyGhastAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/GiantEntity;createGiantAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/GlowSquidEntity;createSquidAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/GoatEntity;createGoatAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/GuardianEntity;createGuardianAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/HoglinEntity;createHoglinAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/AbstractHorseEntity;createBaseHorseAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/ZombieEntity;createZombieAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/IllusionerEntity;createIllusionerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/IronGolemEntity;createIronGolemAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/LlamaEntity;createLlamaAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/MagmaCubeEntity;createMagmaCubeAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/LivingEntity;createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/OcelotEntity;createOcelotAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/PandaEntity;createPandaAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/ParrotEntity;createParrotAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/HostileEntity;createHostileAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/PigEntity;createPigAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/PiglinEntity;createPiglinAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/PiglinBruteEntity;createPiglinBruteAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/PillagerEntity;createPillagerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/player/PlayerEntity;createPlayerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/PolarBearEntity;createPolarBearAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/RabbitEntity;createRabbitAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/RavagerEntity;createRavagerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/SheepEntity;createSheepAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/ShulkerEntity;createShulkerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/SilverfishEntity;createSilverfishAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/AbstractSkeletonEntity;createAbstractSkeletonAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/SkeletonHorseEntity;createSkeletonHorseAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/SnifferEntity;createSnifferAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/SnowGolemEntity;createSnowGolemAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/SpiderEntity;createSpiderAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/SquidEntity;createSquidAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/StriderEntity;createStriderAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/TadpoleEntity;createTadpoleAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/TurtleEntity;createTurtleAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/VexEntity;createVexAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/VillagerEntity;createVillagerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/VindicatorEntity;createVindicatorAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/WardenEntity;addAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/MobEntity;createMobAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/WitchEntity;createWitchAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/boss/WitherEntity;createWitherAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/passive/WolfEntity;createWolfAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/ZoglinEntity;createZoglinAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/ZombieHorseEntity;createZombieHorseAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 *   Lnet/minecraft/entity/mob/ZombifiedPiglinEntity;createZombifiedPiglinAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;
 */
package net.minecraft.entity.attribute;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.util.Map;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.CreakingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.ArmadilloEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CopperGolemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.passive.TadpoleEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Util;
import org.slf4j.Logger;

public class DefaultAttributeRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> DEFAULT_ATTRIBUTE_REGISTRY = ImmutableMap.builder().put(EntityType.ALLAY, AllayEntity.createAllayAttributes().build()).put(EntityType.ARMADILLO, ArmadilloEntity.createArmadilloAttributes().build()).put(EntityType.ARMOR_STAND, ArmorStandEntity.createArmorStandAttributes().build()).put(EntityType.AXOLOTL, AxolotlEntity.createAxolotlAttributes().build()).put(EntityType.BAT, BatEntity.createBatAttributes().build()).put(EntityType.BEE, BeeEntity.createBeeAttributes().build()).put(EntityType.BLAZE, BlazeEntity.createBlazeAttributes().build()).put(EntityType.BOGGED, BoggedEntity.createBoggedAttributes().build()).put(EntityType.CAT, CatEntity.createCatAttributes().build()).put(EntityType.CAMEL, CamelEntity.createCamelAttributes().build()).put(EntityType.CAVE_SPIDER, CaveSpiderEntity.createCaveSpiderAttributes().build()).put(EntityType.CHICKEN, ChickenEntity.createChickenAttributes().build()).put(EntityType.COD, FishEntity.createFishAttributes().build()).put(EntityType.COPPER_GOLEM, CopperGolemEntity.createCopperGolemAttributes().build()).put(EntityType.COW, CowEntity.createCowAttributes().build()).put(EntityType.CREAKING, CreakingEntity.createCreakingAttributes().build()).put(EntityType.CREEPER, CreeperEntity.createCreeperAttributes().build()).put(EntityType.DOLPHIN, DolphinEntity.createDolphinAttributes().build()).put(EntityType.DONKEY, AbstractDonkeyEntity.createAbstractDonkeyAttributes().build()).put(EntityType.DROWNED, DrownedEntity.createDrownedAttributes().build()).put(EntityType.ELDER_GUARDIAN, ElderGuardianEntity.createElderGuardianAttributes().build()).put(EntityType.ENDERMAN, EndermanEntity.createEndermanAttributes().build()).put(EntityType.ENDERMITE, EndermiteEntity.createEndermiteAttributes().build()).put(EntityType.ENDER_DRAGON, EnderDragonEntity.createEnderDragonAttributes().build()).put(EntityType.EVOKER, EvokerEntity.createEvokerAttributes().build()).put(EntityType.BREEZE, BreezeEntity.createBreezeAttributes().build()).put(EntityType.FOX, FoxEntity.createFoxAttributes().build()).put(EntityType.FROG, FrogEntity.createFrogAttributes().build()).put(EntityType.GHAST, GhastEntity.createGhastAttributes().build()).put(EntityType.HAPPY_GHAST, HappyGhastEntity.createHappyGhastAttributes().build()).put(EntityType.GIANT, GiantEntity.createGiantAttributes().build()).put(EntityType.GLOW_SQUID, GlowSquidEntity.createSquidAttributes().build()).put(EntityType.GOAT, GoatEntity.createGoatAttributes().build()).put(EntityType.GUARDIAN, GuardianEntity.createGuardianAttributes().build()).put(EntityType.HOGLIN, HoglinEntity.createHoglinAttributes().build()).put(EntityType.HORSE, AbstractHorseEntity.createBaseHorseAttributes().build()).put(EntityType.HUSK, ZombieEntity.createZombieAttributes().build()).put(EntityType.ILLUSIONER, IllusionerEntity.createIllusionerAttributes().build()).put(EntityType.IRON_GOLEM, IronGolemEntity.createIronGolemAttributes().build()).put(EntityType.LLAMA, LlamaEntity.createLlamaAttributes().build()).put(EntityType.MAGMA_CUBE, MagmaCubeEntity.createMagmaCubeAttributes().build()).put(EntityType.MANNEQUIN, LivingEntity.createLivingAttributes().build()).put(EntityType.MOOSHROOM, CowEntity.createCowAttributes().build()).put(EntityType.MULE, AbstractDonkeyEntity.createAbstractDonkeyAttributes().build()).put(EntityType.OCELOT, OcelotEntity.createOcelotAttributes().build()).put(EntityType.PANDA, PandaEntity.createPandaAttributes().build()).put(EntityType.PARROT, ParrotEntity.createParrotAttributes().build()).put(EntityType.PHANTOM, HostileEntity.createHostileAttributes().build()).put(EntityType.PIG, PigEntity.createPigAttributes().build()).put(EntityType.PIGLIN, PiglinEntity.createPiglinAttributes().build()).put(EntityType.PIGLIN_BRUTE, PiglinBruteEntity.createPiglinBruteAttributes().build()).put(EntityType.PILLAGER, PillagerEntity.createPillagerAttributes().build()).put(EntityType.PLAYER, PlayerEntity.createPlayerAttributes().build()).put(EntityType.POLAR_BEAR, PolarBearEntity.createPolarBearAttributes().build()).put(EntityType.PUFFERFISH, FishEntity.createFishAttributes().build()).put(EntityType.RABBIT, RabbitEntity.createRabbitAttributes().build()).put(EntityType.RAVAGER, RavagerEntity.createRavagerAttributes().build()).put(EntityType.SALMON, FishEntity.createFishAttributes().build()).put(EntityType.SHEEP, SheepEntity.createSheepAttributes().build()).put(EntityType.SHULKER, ShulkerEntity.createShulkerAttributes().build()).put(EntityType.SILVERFISH, SilverfishEntity.createSilverfishAttributes().build()).put(EntityType.SKELETON, AbstractSkeletonEntity.createAbstractSkeletonAttributes().build()).put(EntityType.SKELETON_HORSE, SkeletonHorseEntity.createSkeletonHorseAttributes().build()).put(EntityType.SLIME, HostileEntity.createHostileAttributes().build()).put(EntityType.SNIFFER, SnifferEntity.createSnifferAttributes().build()).put(EntityType.SNOW_GOLEM, SnowGolemEntity.createSnowGolemAttributes().build()).put(EntityType.SPIDER, SpiderEntity.createSpiderAttributes().build()).put(EntityType.SQUID, SquidEntity.createSquidAttributes().build()).put(EntityType.STRAY, AbstractSkeletonEntity.createAbstractSkeletonAttributes().build()).put(EntityType.STRIDER, StriderEntity.createStriderAttributes().build()).put(EntityType.TADPOLE, TadpoleEntity.createTadpoleAttributes().build()).put(EntityType.TRADER_LLAMA, LlamaEntity.createLlamaAttributes().build()).put(EntityType.TROPICAL_FISH, FishEntity.createFishAttributes().build()).put(EntityType.TURTLE, TurtleEntity.createTurtleAttributes().build()).put(EntityType.VEX, VexEntity.createVexAttributes().build()).put(EntityType.VILLAGER, VillagerEntity.createVillagerAttributes().build()).put(EntityType.VINDICATOR, VindicatorEntity.createVindicatorAttributes().build()).put(EntityType.WARDEN, WardenEntity.addAttributes().build()).put(EntityType.WANDERING_TRADER, MobEntity.createMobAttributes().build()).put(EntityType.WITCH, WitchEntity.createWitchAttributes().build()).put(EntityType.WITHER, WitherEntity.createWitherAttributes().build()).put(EntityType.WITHER_SKELETON, AbstractSkeletonEntity.createAbstractSkeletonAttributes().build()).put(EntityType.WOLF, WolfEntity.createWolfAttributes().build()).put(EntityType.ZOGLIN, ZoglinEntity.createZoglinAttributes().build()).put(EntityType.ZOMBIE, ZombieEntity.createZombieAttributes().build()).put(EntityType.ZOMBIE_HORSE, ZombieHorseEntity.createZombieHorseAttributes().build()).put(EntityType.ZOMBIE_VILLAGER, ZombieEntity.createZombieAttributes().build()).put(EntityType.ZOMBIFIED_PIGLIN, ZombifiedPiglinEntity.createZombifiedPiglinAttributes().build()).build();

    public static DefaultAttributeContainer get(EntityType<? extends LivingEntity> type) {
        return DEFAULT_ATTRIBUTE_REGISTRY.get(type);
    }

    public static boolean hasDefinitionFor(EntityType<?> type) {
        return DEFAULT_ATTRIBUTE_REGISTRY.containsKey(type);
    }

    public static void checkMissing() {
        Registries.ENTITY_TYPE.stream().filter(entityType -> entityType.getSpawnGroup() != SpawnGroup.MISC).filter(entityType -> !DefaultAttributeRegistry.hasDefinitionFor(entityType)).map(Registries.ENTITY_TYPE::getId).forEach(id -> Util.logErrorOrPause("Entity " + String.valueOf(id) + " has no attributes"));
    }
}


package net.fabricmc.fabric.impl.resource.conditions.conditions;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;

import net.minecraft.registry.RegistryOps;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.impl.resource.conditions.DefaultResourceConditionTypes;
import net.fabricmc.fabric.impl.resource.conditions.ResourceConditionsImpl;

public record OrResourceCondition(List<ResourceCondition> conditions) implements ResourceCondition {
	public static final MapCodec<OrResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceCondition.CODEC.listOf().fieldOf("values").forGetter(OrResourceCondition::conditions)
	).apply(instance, OrResourceCondition::new));

	@Override
	public ResourceConditionType<?> getType() {
		return DefaultResourceConditionTypes.OR;
	}

	@Override
	public boolean test(@Nullable RegistryOps.RegistryInfoGetter registryInfo) {
		return ResourceConditionsImpl.conditionsMet(this.conditions(), registryInfo, false);
	}
}

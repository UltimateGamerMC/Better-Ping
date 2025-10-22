package net.fabricmc.fabric.impl.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePackSource;

import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.item.v1.EnchantmentSource;
import net.fabricmc.fabric.impl.resource.loader.BuiltinModResourcePackSource;
import net.fabricmc.fabric.impl.resource.loader.FabricResource;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.fabricmc.fabric.mixin.item.EnchantmentBuilderAccessor;

public class EnchantmentUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnchantmentUtil.class);

	@SuppressWarnings("unchecked")
	@Nullable
	public static Enchantment modify(RegistryKey<Enchantment> key, Enchantment originalEnchantment, EnchantmentSource source) {
		Enchantment.Builder builder = Enchantment.builder(originalEnchantment.definition());
		EnchantmentBuilderAccessor accessor = (EnchantmentBuilderAccessor) builder;
		BuilderExtensions builderExtensions = (BuilderExtensions) builder;

		builder.exclusiveSet(originalEnchantment.exclusiveSet());
		accessor.getEffectMap().addAll(originalEnchantment.effects());

		originalEnchantment.effects().stream()
				.forEach(component -> {
					if (component.value() instanceof List<?> valueList) {
						// component type cast is checked by the value
						accessor.invokeGetEffectsList((ComponentType<List<Object>>) component.type())
								.addAll(valueList);
					}
				});

		// Reset the modified flag before invoking the event as we setup the builder above
		builderExtensions.fabric$resetModified();

		EnchantmentEvents.MODIFY.invoker().modify(key, builder, source);

		if (builderExtensions.fabric$didModify()) {
			LOGGER.debug("Enchantment {} was modified", key.getValue());

			return new Enchantment(
					originalEnchantment.description(),
					accessor.getDefinition(),
					accessor.getExclusiveSet(),
					accessor.getEffectMap().build()
			);
		}

		return null;
	}

	public static EnchantmentSource determineSource(Resource resource) {
		if (resource != null) {
			ResourcePackSource packSource = ((FabricResource) resource).getFabricPackSource();

			if (packSource == ResourcePackSource.BUILTIN) {
				return EnchantmentSource.VANILLA;
			} else if (packSource == ModResourcePackCreator.RESOURCE_PACK_SOURCE || packSource instanceof BuiltinModResourcePackSource) {
				return EnchantmentSource.MOD;
			}
		}

		// If not builtin or mod, assume external data pack.
		// It might also be a virtual enchantment injected via mixin instead of being loaded
		// from a resource, but we can't determine that here.
		return EnchantmentSource.DATA_PACK;
	}

	private EnchantmentUtil() { }

	public interface BuilderExtensions {
		void fabric$resetModified();
		boolean fabric$didModify();
	}
}

package net.fabricmc.fabric.api.item.v1;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.component.ComponentType;
import net.minecraft.item.tooltip.TooltipAppender;

import net.fabricmc.fabric.impl.item.ComponentTooltipAppenderRegistryImpl;

/**
 * A registry of {@link TooltipAppender} item components. Adding your item component to this registry will render the
 * item component to the tooltip of them item when it is present, in a location relative to other item components.
 */
@ApiStatus.NonExtendable
public interface ComponentTooltipAppenderRegistry {
	/**
	 * Adds the specified item component type to the list of tooltip appenders to be called first. The component will
	 * render at the top of the tooltip.
	 *
	 * @param componentType the component type to add
	 */
	static void addFirst(ComponentType<? extends TooltipAppender> componentType) {
		Preconditions.checkNotNull(componentType, "componentType");
		ComponentTooltipAppenderRegistryImpl.addFirst(componentType);
	}

	/**
	 * Adds the specified item component type to the list of tooltip appenders to be called last. The component will
	 * render at the bottom of the tooltip.
	 *
	 * @param componentType the component type to add
	 */
	static void addLast(ComponentType<? extends TooltipAppender> componentType) {
		Preconditions.checkNotNull(componentType, "componentType");
		ComponentTooltipAppenderRegistryImpl.addLast(componentType);
	}

	/**
	 * Adds the specified item component type to the list of tooltip appenders so that it will render
	 * before the tooltip appender associated with the specified anchor component type.
	 *
	 * @param anchor the component type before which the specified component type will be rendered
	 * @param componentType the component type to add
	 */
	static void addBefore(ComponentType<?> anchor, ComponentType<? extends TooltipAppender> componentType) {
		Preconditions.checkNotNull(anchor, "anchor");
		Preconditions.checkNotNull(componentType, "componentType");
		ComponentTooltipAppenderRegistryImpl.addBefore(anchor, componentType);
	}

	/**
	 * Adds the specified item component type to the list of tooltip appenders so that it will render
	 * after the tooltip appender associated with the specified anchor component type.
	 *
	 * @param anchor the component type after which the specified component type will be rendered
	 * @param componentType the component type to add
	 */
	static void addAfter(ComponentType<?> anchor, ComponentType<? extends TooltipAppender> componentType) {
		Preconditions.checkNotNull(anchor, "anchor");
		Preconditions.checkNotNull(componentType, "componentType");
		ComponentTooltipAppenderRegistryImpl.addAfter(anchor, componentType);
	}
}

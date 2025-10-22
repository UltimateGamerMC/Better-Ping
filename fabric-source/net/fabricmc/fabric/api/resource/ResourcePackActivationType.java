package net.fabricmc.fabric.api.resource;

/**
 * Represents the resource pack activation type.
 */
public enum ResourcePackActivationType {
	/**
	 * Normal activation. The user has full control over the activation of the resource pack.
	 */
	NORMAL,
	/**
	 * Enabled by default. The user has still full control over the activation of the resource pack.
	 */
	DEFAULT_ENABLED,
	/**
	 * Always enabled. The user cannot disable the resource pack.
	 */
	ALWAYS_ENABLED;

	/**
	 * Returns whether this resource pack will be enabled by default or not.
	 *
	 * @return {@code true} if enabled by default, else {@code false}
	 */
	public boolean isEnabledByDefault() {
		return this == DEFAULT_ENABLED || this == ALWAYS_ENABLED;
	}
}

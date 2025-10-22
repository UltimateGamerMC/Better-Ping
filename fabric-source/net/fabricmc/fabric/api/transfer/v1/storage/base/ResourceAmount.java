package net.fabricmc.fabric.api.transfer.v1.storage.base;

//CHECKSTYLE.OFF: MatchXpath

/**
 * An immutable object storing both a resource and an amount, provided for convenience.
 * @param <T> The type of the stored resource.
 */
public record ResourceAmount<T>(T resource, long amount) {
}

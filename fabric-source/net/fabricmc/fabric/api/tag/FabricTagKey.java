package net.fabricmc.fabric.api.tag;

import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * General-purpose Fabric-provided extensions for {@link TagKey} subclasses.
 *
 * <p>These extensions were designed primarily for giving extra utility methods for TagKeys usages.
 * Getting a TagKey's translation key for example.
 *
 * <p>Note: This interface is automatically implemented on all {@link TagKey} instances via Mixin and interface injection.
 */
public interface FabricTagKey {
	/**
	 * Use this to get a TagKey's translation key safely on any side.
	 *
	 * <p>Format for vanilla registry TagKeys is:
	 * tag.(registry_path).(tag_namespace).(tag_path)
	 *
	 * <p>Format for modded registry TagKeys is:
	 * tag.(registry_namespace).(registry_path).(tag_namespace).(tag_path)
	 *
	 * <p>The registry's path and tag path's slashes will be converted to periods.
	 *
	 * @return the translation key for a TagKey
	 */
	default String getTranslationKey() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("tag.");

		TagKey<?> tagKey = (TagKey<?>) this;
		Identifier registryIdentifier = tagKey.registryRef().getValue();
		Identifier tagIdentifier = tagKey.id();

		if (!registryIdentifier.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
			stringBuilder.append(registryIdentifier.getNamespace())
					.append(".");
		}

		stringBuilder.append(registryIdentifier.getPath().replace("/", "."))
				.append(".")
				.append(tagIdentifier.getNamespace())
				.append(".")
				.append(tagIdentifier.getPath().replace("/", ".").replace(":", "."));

		return stringBuilder.toString();
	}

	/**
	 * Use this to get a TagKey's translatable text for display purposes.
	 *
	 * <p>The text uses the result of {@link TagKey#getTranslationKey} for the translation key
	 * and will fall back to displaying #tag_namespace:tag_path if no translation exists.
	 *
	 * @return the translatable text for a TagKey
	 */
	default Text getName() {
		return Text.translatableWithFallback(getTranslationKey(), "#" + ((TagKey<?>) this).id().toString());
	}
}

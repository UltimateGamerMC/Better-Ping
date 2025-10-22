package net.fabricmc.fabric.impl.gametest;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.test.TestEnvironmentDefinition;
import net.minecraft.test.TestInstance;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class FabricGameTestModInitializer implements ModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FabricGameTestModInitializer.class);
	private static TestAnnotationLocator locator = new TestAnnotationLocator(FabricLoader.getInstance());

	@Override
	public void onInitialize() {
		if (!(FabricGameTestRunner.ENABLED || FabricLoader.getInstance().isDevelopmentEnvironment())) {
			// Don't try to load the tests if the game test runner is disabled or we are not in a development environment
			return;
		}

		for (TestAnnotationLocator.TestMethod testMethod : locator.getTestMethods()) {
			LOGGER.debug("Registering test method: {}", testMethod.identifier());
			Registry.register(Registries.TEST_FUNCTION, testMethod.identifier(), testMethod.testFunction());
		}
	}

	public static void registerDynamicEntries(List<RegistryLoader.Loader<?>> registriesList) {
		Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries = new IdentityHashMap<>(registriesList.size());

		for (RegistryLoader.Loader<?> entry : registriesList) {
			registries.put(entry.registry().getKey(), entry.registry());
		}

		Registry<TestInstance> testInstances = (Registry<TestInstance>) registries.get(RegistryKeys.TEST_INSTANCE);
		Registry<TestEnvironmentDefinition> testEnvironmentDefinitionRegistry = (Registry<TestEnvironmentDefinition>) Objects.requireNonNull(registries.get(RegistryKeys.TEST_ENVIRONMENT));

		for (TestAnnotationLocator.TestMethod testMethod : locator.getTestMethods()) {
			TestInstance testInstance = testMethod.testInstance(testEnvironmentDefinitionRegistry);
			Registry.register(testInstances, testMethod.identifier(), testInstance);
		}
	}
}

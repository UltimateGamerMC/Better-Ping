package net.fabricmc.fabric.impl.gametest;

import java.io.File;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.test.TestFailureLogger;
import net.minecraft.test.TestServer;
import net.minecraft.world.level.storage.LevelStorage;

public final class FabricGameTestRunner {
	public static final boolean ENABLED = System.getProperty(GameTestSystemProperties.ENABLED) != null;

	private static final Logger LOGGER = LoggerFactory.getLogger(FabricGameTestRunner.class);
	private static final String GAMETEST_STRUCTURE_PATH = "gametest/structure";

	public static final ResourceFinder GAMETEST_STRUCTURE_FINDER = new ResourceFinder(GAMETEST_STRUCTURE_PATH, ".snbt");

	private FabricGameTestRunner() {
	}

	public static void runHeadlessServer(LevelStorage.Session session, ResourcePackManager resourcePackManager) {
		String reportPath = System.getProperty(GameTestSystemProperties.REPORT_FILE);

		if (reportPath != null) {
			try {
				TestFailureLogger.setCompletionListener(new SavingXmlReportingTestCompletionListener(new File(reportPath)));
			} catch (ParserConfigurationException e) {
				throw new RuntimeException(e);
			}
		}

		LOGGER.info("Starting test server");

		Optional<String> filter = Optional.ofNullable(System.getProperty(GameTestSystemProperties.FILTER));
		boolean verify = Boolean.getBoolean(GameTestSystemProperties.VERIFY);
		MinecraftServer.startServer((thread) -> TestServer.create(thread, session, resourcePackManager, filter, verify));
	}
}

package jp.tdn.japanese_food_mod.config;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec SERVER;

    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    @SuppressWarnings("unused")
    private static final ModConfigSpec CLIENT;

    static {
        SERVER_BUILDER.comment("Server-related options. Please ignore file if you are not running a server.");
        OregenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        GrassgenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        OystergenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        FishingConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        SERVER = SERVER_BUILDER.build();
        CLIENT = CLIENT_BUILDER.build();
    }

    public static void loadConfig(ModContainer container) {
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path jpConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "japanesefoodmod");

        try {
            Files.createDirectory(jpConfigPath);
        } catch (FileAlreadyExistsException e) {
            // Do nothing
        } catch (IOException e) {
            JapaneseFoodMod.LOGGER.error("Failed to create japanesefoodmod config directory", e);
        }

        container.registerConfig(ModConfig.Type.COMMON, Config.SERVER, "japanesefoodmod/server.toml");
    }
}

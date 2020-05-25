package jp.tdn.japanese_food_mod.config;

import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mod.EventBusSubscriber
public class Config {
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec SERVER;

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec CLIENT;

    static{
        SERVER_BUILDER.comment("Server-related options. Please ignore file if you are not running a server.");
        OregenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        GrassgenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        OystergenConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        //FishingConfig.init(SERVER_BUILDER, CLIENT_BUILDER);
        SERVER = SERVER_BUILDER.build();
        CLIENT = CLIENT_BUILDER.build();
    }

    public static void loadConfig(){
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path jpConfigPath = Paths.get(configPath.toAbsolutePath().toString(), "japanesefoodmod");

        try{
            Files.createDirectory(jpConfigPath);
        }catch (FileAlreadyExistsException e){
            // Do nothing
        }catch (IOException e){
            JapaneseFoodMod.LOGGER.error("Failed to create japanesefoodmod config directory", e);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SERVER, "japanesefoodmod/server.toml");
    }
}

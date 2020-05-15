package jp.tdn.japanese_food_mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GrassgenConfig {
    public static ForgeConfigSpec.IntValue crop_grass_chance;
    public static ForgeConfigSpec.BooleanValue generate_overworld;
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client){
        server.push("grass");
        server.comment("Grassgen Config");
        generate_overworld = server.comment("Decide if you want JapaneseFood Mod grass to spawn in the overworld")
                .define("generate_overworld", true);
        server.pop();
    }
}

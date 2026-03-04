package jp.tdn.japanese_food_mod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class GrassgenConfig {
    //public static ModConfigSpec.IntValue crop_grass_chance;
    public static ModConfigSpec.BooleanValue generate_overworld;

    public static void init(ModConfigSpec.Builder server, ModConfigSpec.Builder client) {
        server.push("grass");
        server.comment("Grassgen Config");
        generate_overworld = server.comment("Decide if you want JapaneseFood Mod grass to spawn in the overworld")
                .define("generate_overworld", true);
        server.pop();
    }
}

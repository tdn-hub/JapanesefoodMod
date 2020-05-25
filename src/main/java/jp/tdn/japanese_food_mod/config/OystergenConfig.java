package jp.tdn.japanese_food_mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OystergenConfig {
    //public static ForgeConfigSpec.IntValue oyster_chance;
    public static ForgeConfigSpec.BooleanValue generate_overworld;
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client){
        server.push("oyster");
        server.comment("Oystergen Config");
        generate_overworld = server.comment("Decide if you want oyster to spawn in the overworld")
                .define("generate_overworld", true);
        server.pop();
    }
}

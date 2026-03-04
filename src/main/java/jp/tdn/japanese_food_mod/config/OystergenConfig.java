package jp.tdn.japanese_food_mod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class OystergenConfig {
    //public static ModConfigSpec.IntValue oyster_chance;
    public static ModConfigSpec.BooleanValue generate_overworld;

    public static void init(ModConfigSpec.Builder server, ModConfigSpec.Builder client) {
        server.push("oyster");
        server.comment("Oystergen Config");
        generate_overworld = server.comment("Decide if you want oyster to spawn in the overworld")
                .define("generate_overworld", true);
        server.pop();
    }
}

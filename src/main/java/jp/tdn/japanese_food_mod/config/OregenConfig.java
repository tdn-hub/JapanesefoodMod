package jp.tdn.japanese_food_mod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class OregenConfig {
    public static ModConfigSpec.IntValue rock_salt_chance;
    public static ModConfigSpec.BooleanValue generate_overworld;

    public static void init(ModConfigSpec.Builder server, ModConfigSpec.Builder client) {
        server.push("ore");
        server.comment("Oregen Config");
        generate_overworld = server.comment("Decide if you want JapaneseFood Mod ores to spawn in the overworld")
                .define("generate_overworld", true);
        rock_salt_chance = server.comment("Maximum number of ore veins of the rock salt that can spawn in one chunk")
                .defineInRange("rock_salt_chance", 7, 1, 10);
        server.pop();
    }
}

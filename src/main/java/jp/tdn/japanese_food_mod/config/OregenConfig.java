package jp.tdn.japanese_food_mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OregenConfig {
    public static ForgeConfigSpec.IntValue rock_salt_chance;
    public static ForgeConfigSpec.BooleanValue generate_overworld;
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client){
        server.comment("Oregen Config");
        rock_salt_chance = server.comment("Maximum number of ore veins of the rock salt that can spawn in one chunk")
                .defineInRange("oregen.rock_salt_chance", 7, 1, 10);

        generate_overworld = server.comment("Decide if you want JapaneseFood Mod ores to spawn in the overworld")
                .define("oregen.generate_overworld", true);
    }
}

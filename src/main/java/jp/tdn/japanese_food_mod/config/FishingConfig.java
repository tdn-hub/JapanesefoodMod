package jp.tdn.japanese_food_mod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FishingConfig {
    public static ForgeConfigSpec.BooleanValue fishing_overworld;
    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client){
        server.push("fishing");
        server.comment("Fishing Config");
        fishing_overworld = server.comment("Decide if you want to allow JapaneseFood Mod fish to be caught in Overworld")
                .define("can_fishing_overworld", true);
        server.pop();
    }
}

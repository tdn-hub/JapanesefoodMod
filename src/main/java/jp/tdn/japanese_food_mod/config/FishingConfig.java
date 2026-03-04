package jp.tdn.japanese_food_mod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FishingConfig {
    public static ModConfigSpec.BooleanValue fishing_overworld;

    public static void init(ModConfigSpec.Builder server, ModConfigSpec.Builder client) {
        server.push("fishing");
        server.comment("Fishing Config");
        fishing_overworld = server.comment("Decide if you want to allow JapaneseFood Mod fish to be caught in Overworld")
                .define("can_fishing_overworld", true);
        server.pop();
    }
}

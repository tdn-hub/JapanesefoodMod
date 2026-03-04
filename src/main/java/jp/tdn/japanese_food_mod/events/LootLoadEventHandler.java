package jp.tdn.japanese_food_mod.events;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import jp.tdn.japanese_food_mod.JapaneseFoodMod;
import jp.tdn.japanese_food_mod.config.FishingConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 * グローバルルートモディファイア実装。
 *
 * 旧 LootTableLoadEvent で行っていた以下の処理を再現する:
 *  - minecraft:blocks/seagrass に japanese_food_mod のプールを追加
 *  - minecraft:entities/squid     に japanese_food_mod のプールを追加
 *  - minecraft:entities/pig       に japanese_food_mod のプールを追加
 *  - (config有効時) minecraft:gameplay/fishing に japanese_food_mod の釣りプールを追加
 *
 * 登録は loot_modifiers/global_loot_modifiers.json と
 * loot_modifiers/<modifier_name>.json で行う。
 */
public class LootLoadEventHandler extends LootModifier {

    // --- Codec ---
    public static final MapCodec<LootLoadEventHandler> CODEC =
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, LootLoadEventHandler::new));

    /**
     * 参照するサブルートテーブルの ResourceLocation。
     * Data-driven な modifier JSON からではなく、Java 側で直接決める。
     */
    private static final ResourceLocation RL_SEAGRASS_INJECT =
            ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "blocks/seagrass");
    private static final ResourceLocation RL_SQUID_INJECT =
            ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "entities/squid");
    private static final ResourceLocation RL_PIG_INJECT =
            ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "entities/pig");
    private static final ResourceLocation RL_FISHING_INJECT =
            ResourceLocation.fromNamespaceAndPath(JapaneseFoodMod.MOD_ID, "gameplay/fishing/fish");

    // ターゲット loot table の ResourceLocation
    private static final ResourceLocation RL_SEAGRASS  = ResourceLocation.fromNamespaceAndPath("minecraft", "blocks/seagrass");
    private static final ResourceLocation RL_SQUID     = ResourceLocation.fromNamespaceAndPath("minecraft", "entities/squid");
    private static final ResourceLocation RL_PIG       = ResourceLocation.fromNamespaceAndPath("minecraft", "entities/pig");
    private static final ResourceLocation RL_FISHING   = ResourceLocation.fromNamespaceAndPath("minecraft", "gameplay/fishing");

    protected LootLoadEventHandler(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // LootTableIdCondition で対象テーブルを絞り込む
        ResourceLocation tableId = context.getQueriedLootTableId();

        if (tableId.equals(RL_SEAGRASS)) {
            appendFromTable(generatedLoot, context, RL_SEAGRASS_INJECT);
        } else if (tableId.equals(RL_SQUID)) {
            appendFromTable(generatedLoot, context, RL_SQUID_INJECT);
        } else if (tableId.equals(RL_PIG)) {
            appendFromTable(generatedLoot, context, RL_PIG_INJECT);
        } else if (tableId.equals(RL_FISHING) && FishingConfig.fishing_overworld.get()) {
            appendFromTable(generatedLoot, context, RL_FISHING_INJECT);
        }

        return generatedLoot;
    }

    /**
     * 別の loot table を解決してアイテムを generatedLoot へ追加する。
     *
     * context をそのまま渡すと queriedLootTableId が元のテーブルのままになり、
     * グローバルルートモディファイアが再び同じ条件で発火して無限再帰になる。
     * getRandomItems(LootParams) を使うと新しい LootContext が生成され、
     * queriedLootTableId が注入テーブルの ID になるため再帰が止まる。
     */
    private static void appendFromTable(ObjectArrayList<ItemStack> loot,
                                        LootContext context,
                                        ResourceLocation tableId) {
        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, tableId);
        LootTable lootTable = context.getLevel().getServer()
                .reloadableRegistries()
                .getLootTable(key);
        // getRandomItemsRaw はグローバルルートモディファイアを再適用しないため無限再帰にならない
        lootTable.getRandomItemsRaw(context, loot::add);
    }
}

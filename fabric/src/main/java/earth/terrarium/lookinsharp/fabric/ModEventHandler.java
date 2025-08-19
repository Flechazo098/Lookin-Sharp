package earth.terrarium.lookinsharp.fabric;

import earth.terrarium.lookinsharp.fabric.datagen.ModLootTables;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class ModEventHandler {
    public static void register() {
        LootTableEvents.MODIFY.register(ModEventHandler::initLootTables);
    }

    private static void initLootTables(ResourceKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, HolderLookup.Provider registries) {
        if (source.isBuiltin()) {
            if (key.equals(EntityType.IRON_GOLEM.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.IRON_GOLEM_LOOT)));
            }
            else if (key.equals(EntityType.CAVE_SPIDER.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.CAVE_SPIDER_LOOT)));
            }
            else if (key.equals(EntityType.SNOW_GOLEM.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.SNOW_GOLEM_LOOT)));
            }
            else if (key.equals(EntityType.SLIME.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.SLIME_LOOT)));
            }
            else if (key.equals(EntityType.WITCH.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.WITCH_LOOT)));
            }
            else if (key.equals(EntityType.HUSK.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.HUSK_LOOT)));
            }
            else if (key.equals(EntityType.WITHER_SKELETON.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.WITHER_SKELETON_LOOT)));
            }
            else if (key.equals(EntityType.GHAST.getDefaultLootTable())) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(NestedLootTable.lootTableReference(ModLootTables.GHAST_LOOT)));
            }
        }
    }
}

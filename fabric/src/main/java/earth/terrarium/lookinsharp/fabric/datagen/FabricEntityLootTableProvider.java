package earth.terrarium.lookinsharp.fabric.datagen;

import earth.terrarium.lookinsharp.common.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FabricEntityLootTableProvider extends SimpleFabricLootTableProvider {
    public FabricEntityLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        // Iron Golem - Battering Artifact (0.5% chance)
        consumer.accept(ModLootTables.IRON_GOLEM_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.005f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.BATTERING_ARTIFACT.get()))));

        // Cave Spider - Deadly Artifact (5% chance)
        consumer.accept(ModLootTables.CAVE_SPIDER_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.DEADLY_ARTIFACT.get()))));

        // Snow Golem - Glacial Artifact (35% chance)
        consumer.accept(ModLootTables.SNOW_GOLEM_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.35f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.GLACIAL_ARTIFACT.get()))));

        // Slime - Sticky Artifact (5% chance)
        consumer.accept(ModLootTables.SLIME_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.05f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.STICKY_ARTIFACT.get()))));

        // Witch - Tonic Artifact (25% chance)
        consumer.accept(ModLootTables.WITCH_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.TONIC_ARTIFACT.get()))));

        // Husk - Weakening Artifact (10% chance)
        consumer.accept(ModLootTables.HUSK_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.WEAKENING_ARTIFACT.get()))));

        // Wither Skeleton - Withering Artifact (15% chance)
        consumer.accept(ModLootTables.WITHER_SKELETON_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.15f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.WITHERING_ARTIFACT.get()))));

        // Ghast - Vampiric Artifact (25% chance)
        consumer.accept(ModLootTables.GHAST_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        .add(LootItem.lootTableItem(ModItems.VAMPIRIC_ARTIFACT.get()))));
    }
}
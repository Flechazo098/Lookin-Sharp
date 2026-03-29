package earth.terrarium.lookinsharp.fabric.datagen;

import earth.terrarium.lookinsharp.LookinSharp;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static final ResourceKey<LootTable> IRON_GOLEM_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/iron_golem"));
    public static final ResourceKey<LootTable> CAVE_SPIDER_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/cave_spider"));
    public static final ResourceKey<LootTable> SNOW_GOLEM_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/snow_golem"));
    public static final ResourceKey<LootTable> SLIME_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/slime"));
    public static final ResourceKey<LootTable> WITCH_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/witch"));
    public static final ResourceKey<LootTable> HUSK_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/husk"));
    public static final ResourceKey<LootTable> WITHER_SKELETON_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/wither_skeleton"));
    public static final ResourceKey<LootTable> GHAST_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(LookinSharp.MOD_ID, "entities/ghast"));
}
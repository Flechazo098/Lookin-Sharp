package earth.terrarium.lookinsharp.fabric;

import com.mafuyu404.oelib.fabric.data.DataRegistry;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityData;
import earth.terrarium.lookinsharp.api.traits.ToolTraitData;
import earth.terrarium.lookinsharp.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LookinSharpFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DataRegistry.register(ToolRarityData.class);
        DataRegistry.register(ToolTraitData.class);

        LookinSharp.init();
        LookinSharp.DROPS.forEach((item, chance) -> {
            LootTableEvents.MODIFY.register(((key, tableBuilder, source, registries) -> {
                if (source.isBuiltin() && chance.entityType().getDefaultLootTable().equals(key)) {
                    LootPool.Builder builder = new LootPool.Builder()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemRandomChanceCondition.randomChance(chance.chance()))
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .add(LootItem.lootTableItem(item.get()))
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))
                                    .build());
                    tableBuilder.pool(builder.build());
                }
            } ));
        });
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(Utils::onEntityHit);
    }
}

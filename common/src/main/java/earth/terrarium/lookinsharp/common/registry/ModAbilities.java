package earth.terrarium.lookinsharp.common.registry;

import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import earth.terrarium.lookinsharp.api.abilities.ToolAbilityManager;
import earth.terrarium.lookinsharp.common.abilities.*;

public class ModAbilities {
    public static ToolAbility GOLEM_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:golem", new GolemAbility());
    public static ToolAbility FREEZE_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:freeze", new FreezeAbility());
    public static ToolAbility SLOWNESS_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:slowness", new SlownessAbility());
    public static ToolAbility WEAKNESS_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:weakness", new WeaknessAbility());
    public static ToolAbility POISON_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:poison", new PoisonAbility());
    public static ToolAbility RANDOM_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:random", new RandomEffectAbility());
    public static ToolAbility WITHER_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:wither", new WitherAbility());
    public static ToolAbility LIFE_STEAL_ABILITY = ToolAbilityManager.registerAbility("lookinsharp:life_steal", new LifeStealAbility());

    public static void init() {
        // NO-OP
    }
}

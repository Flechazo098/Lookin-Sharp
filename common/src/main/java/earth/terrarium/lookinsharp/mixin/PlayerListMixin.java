package earth.terrarium.lookinsharp.mixin;

import earth.terrarium.lookinsharp.common.recipe.ForgingRecipeAccess;
import earth.terrarium.lookinsharp.network.ForgingRecipesSyncPacket;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    @Final
    private List<ServerPlayer> players;

    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void lookinsharp$syncForgingRecipesOnJoin(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        this.lookinsharp$sendForgingRecipes(player);
    }

    @Inject(method = "reloadResources", at = @At("TAIL"))
    private void lookinsharp$syncForgingRecipesOnReload(CallbackInfo ci) {
        for (ServerPlayer player : this.players) {
            this.lookinsharp$sendForgingRecipes(player);
        }
    }

    private void lookinsharp$sendForgingRecipes(ServerPlayer player) {
        if (this.server.getRecipeManager() instanceof ForgingRecipeAccess access) {
            ForgingRecipesSyncPacket.from(access.lookinsharp$forgingRecipes()).sendTo(player);
        }
    }
}

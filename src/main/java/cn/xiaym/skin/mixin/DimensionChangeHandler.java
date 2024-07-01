package cn.xiaym.skin.mixin;

import cn.xiaym.skin.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.xiaym.skin.Main.MC;

@Mixin(MinecraftClient.class)
public class DimensionChangeHandler {
    @Inject(method = "setWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("RETURN"))
    private void onWorldChange(ClientWorld clientWorld, CallbackInfo info) {
        if (clientWorld == null || MC.player == null) {
            return;
        }

        Main.refreshSkinParts();

        //#if MC >= 12006
        Identifier dimID = clientWorld.getRegistryKey().getValue();
        //#else
        //$$ Identifier dimID = clientWorld.getDimensionKey().getValue();
        //#endif

        Main.LOGGER.info("[SkinParts] Dimension change detected - Refreshing the player's skin layers. ({})", dimID);
    }
}

package cn.xiaym.spr.mixin;

import cn.xiaym.spr.Config;
import cn.xiaym.spr.SkinPRMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.xiaym.spr.SkinPRMain.MC;

@Mixin(MinecraftClient.class)
public class DimensionChangeHandler {
    @Inject(method = "setWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("RETURN"))
    private void onWorldChange(ClientWorld clientWorld, CallbackInfo info) {
        if (clientWorld == null || !Config.refreshWhenChangeDim || MC.player == null) {
            return;
        }

        SkinPRMain.refreshSkinParts();

        Identifier dimID = clientWorld.getDimensionKey().getValue();
        SkinPRMain.getLogger().info("[SkinParts] Dimension change detected - Refreshing the player's skin parts. (" + dimID + ")");
    }
}

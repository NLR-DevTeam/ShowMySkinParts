package cn.xiaym.skin.mixin;

import cn.xiaym.skin.Main;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.xiaym.skin.Main.MC;

@Mixin(ClientPlayNetworkHandler.class)
public class RespawnHandler {
    @Unique
    private boolean dead = false;

    @Inject(method = "onPlayerRespawn(Lnet/minecraft/network/packet/s2c/play/PlayerRespawnS2CPacket;)V", at = @At("RETURN"))
    public void onPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo info) {
        if (MC.player == null || !dead) {
            return;
        }

        Main.refreshSkinParts();
        Main.LOGGER.info("[SkinParts] Respawning detected - Refreshing the player's skin layers.");

        dead = false;
    }

    @Inject(method = "onDeathMessage(Lnet/minecraft/network/packet/s2c/play/DeathMessageS2CPacket;)V", at = @At("RETURN"))
    public void onPlayerDeath(DeathMessageS2CPacket packet, CallbackInfo info) {
        if (MC.player == null) {
            return;
        }

        dead = true;
    }
}

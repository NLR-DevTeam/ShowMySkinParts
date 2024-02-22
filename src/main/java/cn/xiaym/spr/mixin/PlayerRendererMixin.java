package cn.xiaym.spr.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static cn.xiaym.spr.SkinPRMain.MC;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {
    @Redirect(method = "setModelPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isPartVisible(Lnet/minecraft/client/render/entity/PlayerModelPart;)Z"))
    private boolean setModelPose(AbstractClientPlayerEntity instance, PlayerModelPart modelPart) {
        if (instance != MC.player) {
            return instance.isPartVisible(modelPart);
        }

        // Just respect my options.
        return MC.options.isPlayerModelPartEnabled(modelPart);
    }
}

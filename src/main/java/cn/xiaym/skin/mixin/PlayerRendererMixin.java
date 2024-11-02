package cn.xiaym.skin.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static cn.xiaym.skin.Main.MC;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {
    @Redirect(method =
            //#if MC >= 12300
            "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
            //#else
            //$$ "setModelPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)V",
            //#endif

            at = @At(value = "INVOKE",
                    //#if MC >= 12100
                    target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isPartVisible(Lnet/minecraft/entity/player/PlayerModelPart;)Z"
                    //#else
                    //$$ target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isPartVisible(Lnet/minecraft/client/render/entity/PlayerModelPart;)Z"
                    //#endif
            ))
    private boolean setModelPose(AbstractClientPlayerEntity instance, PlayerModelPart modelPart) {
        // Won't care about others
        if (instance != MC.player) {
            return instance.isPartVisible(modelPart);
        }

        // Just respect my options.
        return MC.options.isPlayerModelPartEnabled(modelPart);
    }
}

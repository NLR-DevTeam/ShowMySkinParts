package cn.xiaym.skin.mixin;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static cn.xiaym.skin.Main.MC;

//#if MC >= 12110
import net.minecraft.entity.PlayerLikeEntity;
//#else
//$$ import net.minecraft.client.network.AbstractClientPlayerEntity;
//#endif

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {
    @Redirect(method =
            //#if MC >= 12110
            "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
            //#elseif MC >= 12103
            //$$ "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V",
            //#else
            //$$ "setModelPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)V",
            //#endif

            at = @At(value = "INVOKE",
                    //#if MC >= 12110
                    target = "Lnet/minecraft/entity/PlayerLikeEntity;isModelPartVisible(Lnet/minecraft/entity/player/PlayerModelPart;)Z"
                    //#elseif MC >= 12100
                    //$$ target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isPartVisible(Lnet/minecraft/entity/player/PlayerModelPart;)Z"
                    //#else
                    //$$ target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isPartVisible(Lnet/minecraft/client/render/entity/PlayerModelPart;)Z"
                    //#endif
            ))
    //#if MC >= 12110
    private boolean setModelPose(PlayerLikeEntity instance, PlayerModelPart modelPart) {
        //#else
        //$$ private boolean setModelPose(AbstractClientPlayerEntity instance, PlayerModelPart modelPart) {
        //#endif

        // Won't care about others
        if (instance != MC.player) {
            //#if MC >= 12110
            return instance.isModelPartVisible(modelPart);
            //#else
            //$$ return instance.isPartVisible(modelPart);
            //#endif
        }

        // Just respect my options.
        return MC.options.isPlayerModelPartEnabled(modelPart);
    }
}

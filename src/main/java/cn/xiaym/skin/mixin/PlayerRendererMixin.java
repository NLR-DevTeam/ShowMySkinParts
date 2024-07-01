package cn.xiaym.skin.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
//#if MC >= 12100
import net.minecraft.entity.player.PlayerModelPart;
//#else
//$$ import net.minecraft.client.render.entity.PlayerModelPart;
//#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static cn.xiaym.skin.Main.MC;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {
    @Redirect(method = "setModelPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)V",
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

package cn.xiaym.spr.mixin;

import cn.xiaym.spr.SkinPRMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class KeyPressHandler {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(at = @At("RETURN"), method = "tick")
    private void onEndTick(CallbackInfo info) {
        if (!SkinPRMain.MANUAL_KEY.wasPressed() || player == null) {
            return;
        }

        SkinPRMain.refreshSkinParts();
        player.sendMessage(Text.translatable("message.skinpr.manualRefreshed"));
    }
}

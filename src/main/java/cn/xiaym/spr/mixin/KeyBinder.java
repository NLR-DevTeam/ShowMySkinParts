package cn.xiaym.spr.mixin;

import cn.xiaym.spr.SkinPRMain;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class KeyBinder {
    @Shadow
    @Final
    @Mutable
    public KeyBinding[] allKeys;

    @Inject(method = "load()V", at = @At("HEAD"))
    public void registerKey(CallbackInfo info) {
        KeyBinding[] bindings = new KeyBinding[allKeys.length + 1];
        System.arraycopy(allKeys, 0, bindings, 0, allKeys.length);
        bindings[bindings.length - 1] = SkinPRMain.MANUAL_KEY;
        allKeys = bindings;
    }
}

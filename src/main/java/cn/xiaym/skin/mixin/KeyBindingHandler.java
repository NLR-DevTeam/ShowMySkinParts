package cn.xiaym.skin.mixin;

import cn.xiaym.skin.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

public class KeyBindingHandler {
    @Mixin(KeyBinding.class)
    public interface CategoryAccessor {
        @Accessor("CATEGORY_ORDER_MAP")
        static Map<String, Integer> getCategoryMap() {
            throw new AssertionError();
        }
    }

    @Mixin(GameOptions.class)
    public static class Binder {
        @Shadow
        @Final
        @Mutable
        public KeyBinding[] allKeys;

        @SuppressWarnings("all")
        @Inject(method = "load()V", at = @At("HEAD"))
        public void registerKey(CallbackInfo info) {
            Map<String, Integer> categories = CategoryAccessor.getCategoryMap();
            categories.put(Main.MANUAL_KEY.getCategory(),
                    categories.values().stream().max(Integer::compareTo).orElse(0) + 1);

            KeyBinding[] bindings = new KeyBinding[allKeys.length + 1];
            System.arraycopy(allKeys, 0, bindings, 0, allKeys.length);
            bindings[bindings.length - 1] = Main.MANUAL_KEY;
            allKeys = bindings;
        }
    }

    @Mixin(MinecraftClient.class)
    public static class PressHandler {
        @Shadow
        @Nullable
        public ClientPlayerEntity player;

        @Inject(at = @At("RETURN"), method = "tick")
        private void onEndTick(CallbackInfo info) {
            if (!Main.MANUAL_KEY.wasPressed() || player == null) {
                return;
            }

            Main.refreshSkinParts();
            player.sendMessage(Text.translatable("message.skinpr.manualRefreshed")
                    //#if MC >= 12300
                    , false
                    //#endif
            );
        }
    }
}

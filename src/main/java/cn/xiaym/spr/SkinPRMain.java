package cn.xiaym.spr;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkinPRMain implements ClientModInitializer {
    public static final KeyBinding MANUAL_KEY = new KeyBinding(
            "keybinding.skinpr.refreshManually",
            GLFW.GLFW_KEY_N,
            "category.skinpr.keybindings"
    );
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    private static final Logger logger = LoggerFactory.getLogger("SkinParts");

    public static void refreshSkinParts() {
        MC.options.sendClientSettings();
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void onInitializeClient() {
        Config.prepare();
    }
}

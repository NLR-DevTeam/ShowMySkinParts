package cn.xiaym.skin;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {
    public static final KeyBinding MANUAL_KEY = new KeyBinding(
            "keybinding.skinpr.refreshManually",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "category.skinpr.keybindings"
    );
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger("SkinParts");

    public static void refreshSkinParts() {
        MC.options.sendClientSettings();
    }

    @Override
    public void onInitializeClient() {
        Config.initialize();
    }
}

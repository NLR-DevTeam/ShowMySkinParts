package cn.xiaym.spr;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkinPRMain implements ClientModInitializer {
    public static final KeyBinding MANUAL_KEY = new KeyBinding(
            "keybinding.skinpr.refreshManually",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "category.skinpr.keybindings"
    );
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    private static final Logger logger = LoggerFactory.getLogger("SkinParts");

    public static void refreshSkinParts() {
        // Change 2 times
        GameOptions options = MC.options;
        PlayerModelPart part = PlayerModelPart.HAT;
        options.togglePlayerModelPart(part, !options.isPlayerModelPartEnabled(part));
        options.togglePlayerModelPart(part, !options.isPlayerModelPartEnabled(part));
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void onInitializeClient() {
        Config.prepare();
    }
}

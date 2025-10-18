package cn.xiaym.skin;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//#if MC >= 12110
import net.minecraft.util.Identifier;
//#endif

public class Main implements ClientModInitializer {
    public static final KeyBinding MANUAL_KEY;
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger("SkinParts");

    static {
        //#if MC >= 12110
        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("skinpr", "main"));
        MANUAL_KEY = new KeyBinding("key.skinpr.refreshManually", GLFW.GLFW_KEY_N, category);
        //#else
        //$$ MANUAL_KEY = new KeyBinding("key.skinpr.refreshManually", GLFW.GLFW_KEY_N, "key.category.skinpr.main");
        //#endif
    }

    public static void refreshSkinParts() {
        MC.options.sendClientSettings();
    }

    @Override
    public void onInitializeClient() {
        Config.initialize();
    }
}

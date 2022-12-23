package cn.xiaym.spr;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SkinPRMain implements ClientModInitializer {
    private static final Logger logger = LoggerFactory.getLogger("SkinPR");
    private static final MinecraftClient MC = MinecraftClient.getInstance();
    private static String dimID;
    private static boolean isDead;

    @Override
    public void onInitializeClient() {
        Config.prepare();

        // Bind keys
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "keybinding.skinpr.refreshManually",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.skinpr.keybindings"
        ));

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (keyBinding.wasPressed()) {
                refreshSkinParts();

                if (client.player == null) return;

                client.player.sendMessage(Text.translatable("message.skinpr.manualRefreshed"));
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            if (client.player == null) return;
            if (!Config.refreshWhenRespawning) return;

            boolean newDead = client.player.isDead();
            if (Objects.equals(isDead, newDead)) return;

            isDead = newDead;
            if (isDead) return;

            refreshSkinParts();
            logger.info("[SkinPR] Refreshing the player's skin parts because of respawning.");
        });

        ClientTickEvents.START_WORLD_TICK.register((world) -> {
            if (MC.player == null) return;
            if (!Config.refreshWhenChangingDim) return;

            String newDimID = world.getDimensionKey().getValue().toString();
            if (Objects.equals(dimID, newDimID)) return;

            dimID = newDimID;

            logger.info("[SkinPR] Refreshing the player's skin parts because entering dim: " + dimID);

            refreshSkinParts();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            dimID = null;
            logger.info("[SkinPR] Dim ID reset because of disconnecting.");
        });
    }

    public static void refreshSkinParts() {
        for (PlayerModelPart part : PlayerModelPart.values()) {
            // Change 2 times
            MC.options.togglePlayerModelPart(part, !MC.options.isPlayerModelPartEnabled(part));
            MC.options.togglePlayerModelPart(part, !MC.options.isPlayerModelPartEnabled(part));
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}

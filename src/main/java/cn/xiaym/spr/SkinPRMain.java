package cn.xiaym.spr;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
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

    public static void refreshSkinParts() {
        // Change 2 times
        GameOptions options = MC.options;
        PlayerModelPart part = PlayerModelPart.HAT;
        options.togglePlayerModelPart(part, !options.isPlayerModelPartEnabled(part));
        options.togglePlayerModelPart(part, !options.isPlayerModelPartEnabled(part));

        if (MC.player == null) {
            return;
        }

        // Respect my options.
        PlayerEntityRenderer renderer = (PlayerEntityRenderer) MC.getEntityRenderDispatcher().getRenderer(MC.player);
        PlayerEntityModel<AbstractClientPlayerEntity> model = renderer.getModel();
        model.jacket.visible = options.isPlayerModelPartEnabled(PlayerModelPart.JACKET);
        model.leftSleeve.visible = options.isPlayerModelPartEnabled(PlayerModelPart.LEFT_SLEEVE);
        model.rightSleeve.visible = options.isPlayerModelPartEnabled(PlayerModelPart.RIGHT_SLEEVE);
        model.leftPants.visible = options.isPlayerModelPartEnabled(PlayerModelPart.LEFT_PANTS_LEG);
        model.rightPants.visible = options.isPlayerModelPartEnabled(PlayerModelPart.RIGHT_PANTS_LEG);
        model.hat.visible = options.isPlayerModelPartEnabled(PlayerModelPart.HAT);
    }

    public static Logger getLogger() {
        return logger;
    }

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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!keyBinding.wasPressed() || client.player == null) {
                return;
            }

            refreshSkinParts();
            client.player.sendMessage(Text.translatable("message.skinpr.manualRefreshed"));
        });

        // Respawn
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            if (MC.player == null || !Config.refreshWhenRespawning) {
                return;
            }

            boolean newDead = MC.player.isDead();
            if (Objects.equals(isDead, newDead)) {
                return;
            }

            isDead = newDead;
            if (isDead) {
                return;
            }

            refreshSkinParts();
            logger.info("[SkinPR] Respawning detected - Refreshing the player's skin parts.");
        });

        // Change DIM
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            if (MC.player == null || !Config.refreshWhenChangingDim) {
                return;
            }

            String newDimID = world.getDimensionKey().getValue().toString();
            if (Objects.equals(dimID, newDimID)) {
                return;
            }

            dimID = newDimID;

            refreshSkinParts();
            logger.info("[SkinPR] Dimension change detected - Refreshing the player's skin parts. (" + dimID + ")");
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            isDead = false;
            dimID = null;
        });
    }
}

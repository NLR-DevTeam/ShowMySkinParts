package cn.xiaym.skin;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    public static boolean refreshWhenRespawning = true;
    public static boolean refreshWhenChangingDim = true;
    private static Path configPath;
    private static JsonObject data = new JsonObject();

    public static void initialize() {
        configPath = FabricLoader.getInstance().getConfigDir().resolve("ShowMySkinParts.json");

        if (Files.notExists(configPath)) {
            try {
                Files.createFile(configPath);
                save();
            } catch (IOException e) {
                Main.LOGGER.error("Error occurred while creating the config file: ", e);
            }

            return;
        }

        try {
            String jsonStr = new String(Files.readAllBytes(configPath));
            data = JsonParser.parseString(jsonStr).getAsJsonObject();

            refreshWhenRespawning = data.getAsJsonPrimitive("refreshWhenRespawning").getAsBoolean();
            refreshWhenChangingDim = data.getAsJsonPrimitive("refreshWhenChangingDim").getAsBoolean();
        } catch (Exception e) {
            Main.LOGGER.error("Error occurred while reading the config file: ", e);
        }
    }

    public static void save() {
        data.addProperty("refreshWhenRespawning", refreshWhenRespawning);
        data.addProperty("refreshWhenChangingDim", refreshWhenChangingDim);

        try {
            Files.writeString(configPath, new GsonBuilder().setPrettyPrinting().create()
                    .toJson(JsonParser.parseString(data.toString())));
        } catch (IOException e) {
            Main.LOGGER.error("Error occurred while saving the config file: ", e);
        }
    }
}

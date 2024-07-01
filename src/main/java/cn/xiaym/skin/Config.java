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
    private static Path configFile;
    private static JsonObject confObj = new JsonObject();

    public static void initialize() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("ShowMySkinParts.json");

        if (Files.notExists(configFile)) {
            try {
                Files.createFile(configFile);
                save();
            } catch (IOException e) {
                Main.LOGGER.error("Error occurred while creating the config file: ", e);
            }

            return;
        }

        try {
            String jsonStr = new String(Files.readAllBytes(configFile));
            confObj = JsonParser.parseString(jsonStr).getAsJsonObject();

            refreshWhenRespawning = confObj.getAsJsonPrimitive("refreshWhenRespawning").getAsBoolean();
            refreshWhenChangingDim = confObj.getAsJsonPrimitive("refreshWhenChangingDim").getAsBoolean();
        } catch (Exception e) {
            Main.LOGGER.error("Error occurred while reading the config file: ", e);
        }
    }

    public static void save() {
        confObj.addProperty("refreshWhenRespawning", refreshWhenRespawning);
        confObj.addProperty("refreshWhenChangingDim", refreshWhenChangingDim);

        try {
            Files.writeString(configFile,
                    new GsonBuilder().setPrettyPrinting().create().toJson(
                            JsonParser.parseString(confObj.toString()))
            );
        } catch (IOException e) {
            Main.LOGGER.error("Error occurred while saving the config file: ", e);
        }
    }
}

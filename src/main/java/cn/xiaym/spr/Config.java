package cn.xiaym.spr;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    public static boolean refreshAfterRespawn = true;
    public static boolean refreshWhenChangeDim = true;
    private static Path configFile;
    private static JsonObject confObj = new JsonObject();

    public static void initialize() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("ShowMySkinParts.json");

        if (Files.notExists(configFile)) {
            try {
                Files.createFile(configFile);
                save();
            } catch (IOException e) {
                SkinPRMain.getLogger().error("[SkinParts] Error occurred while creating the config file: ", e);
            }

            return;
        }

        try {
            confObj = JsonParser.parseString(
                    Files.readString(configFile)
            ).getAsJsonObject();

            refreshAfterRespawn = confObj.getAsJsonPrimitive("refreshWhenRespawning").getAsBoolean();
            refreshWhenChangeDim = confObj.getAsJsonPrimitive("refreshWhenChangingDim").getAsBoolean();
        } catch (Exception e) {
            SkinPRMain.getLogger().error("[SkinParts] Error occurred while reading the config file: ", e);
        }
    }

    public static void save() {
        confObj.addProperty("refreshWhenRespawning", refreshAfterRespawn);
        confObj.addProperty("refreshWhenChangingDim", refreshWhenChangeDim);

        try {
            Files.writeString(configFile,
                    new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(confObj.toString())));
        } catch (IOException e) {
            SkinPRMain.getLogger().error("[SkinParts] Error occurred while saving the config file: ", e);
        }
    }
}

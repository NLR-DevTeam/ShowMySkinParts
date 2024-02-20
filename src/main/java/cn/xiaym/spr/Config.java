package cn.xiaym.spr;

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
    private static JsonObject jsonObject = new JsonObject();

    public static void prepare() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("SkinPartsRefresher.json");

        if (Files.notExists(configFile)) {
            try {
                Files.createFile(configFile);
                save();
            } catch (IOException e) {
                SkinPRMain.getLogger().error("Error occurred while creating the config file: ", e);
            }

            return;
        }

        try {
            String jsonStr = new String(Files.readAllBytes(configFile));
            jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();

            refreshWhenRespawning = jsonObject.getAsJsonPrimitive("refreshWhenRespawning").getAsBoolean();
            refreshWhenChangingDim = jsonObject.getAsJsonPrimitive("refreshWhenChangingDim").getAsBoolean();
        } catch (Exception e) {
            SkinPRMain.getLogger().error("Error occurred while reading the config file: ", e);
        }
    }

    public static void save() {
        jsonObject.addProperty("refreshWhenRespawning", refreshWhenRespawning);
        jsonObject.addProperty("refreshWhenChangingDim", refreshWhenChangingDim);

        try {
            Files.writeString(configFile,
                    new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString(jsonObject.toString())));
        } catch (IOException e) {
            SkinPRMain.getLogger().error("Error occurred while saving the config file: ", e);
        }
    }
}

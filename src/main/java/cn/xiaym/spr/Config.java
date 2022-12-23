package cn.xiaym.spr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {
    private static File configFile;
    private static JsonObject jsonObject = new JsonObject();
    public static boolean refreshWhenRespawning = true;
    public static boolean refreshWhenChangingDim = true;

    public static void prepare() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("SkinPartsRefresher.json").toFile();

        if (!configFile.exists()) {
            try {
                boolean test = configFile.createNewFile();
                if (!test) throw new IOException("File create failed");

                save();
            } catch (IOException e) {
                SkinPRMain.getLogger().error("Error occurred while creating the config file: ", e);
            }

            return;
        }

        try {
            String jsonStr = new String(Files.readAllBytes(configFile.toPath()));

            jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();

            refreshWhenRespawning = jsonObject.getAsJsonPrimitive("refreshWhenRespawning").getAsBoolean();
            refreshWhenChangingDim = jsonObject.getAsJsonPrimitive("refreshWhenChangingDim").getAsBoolean();
        } catch (Exception e) {
            SkinPRMain.getLogger().error("Error occurred while reading the config file: ", e);
        }
    }

    public static void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        jsonObject.addProperty("refreshWhenRespawning", refreshWhenRespawning);
        jsonObject.addProperty("refreshWhenChangingDim", refreshWhenChangingDim);

        try {
            Files.writeString(configFile.toPath(), gson.toJson(JsonParser.parseString(jsonObject.toString())));
        } catch (IOException e) {
            SkinPRMain.getLogger().error("Error occurred while saving the config file: ", e);
        }
    }
}

package cn.xiaym.spr.compat.modmenu;

import cn.xiaym.spr.Config;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

public class ModMenuEntry implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return null;
        }

        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("config.skinpr.title"))
                    .setSavingRunnable(Config::save);

            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.skinpr.category.general"));
            ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();

            // Respawn
            general.addEntry(
                    entryBuilder.startBooleanToggle(Text.translatable("config.skinpr.category.general.toggle_respawn"),
                                    Config.refreshWhenRespawning)
                            .setDefaultValue(true)
                            .setSaveConsumer(b -> Config.refreshWhenRespawning = b)
                            .build()
            );

            // Change dim
            general.addEntry(
                    entryBuilder.startBooleanToggle(Text.translatable("config.skinpr.category.general.toggle_dim"),
                                    Config.refreshWhenChangingDim)
                            .setDefaultValue(true)
                            .setSaveConsumer(b -> Config.refreshWhenChangingDim = b)
                            .build()
            );

            return builder.build();
        };
    }
}

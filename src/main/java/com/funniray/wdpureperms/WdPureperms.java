package com.funniray.wdpureperms;

import com.funniray.wdpureperms.datasources.Datasource;
import com.funniray.wdpureperms.datasources.SQLSource;
import com.funniray.wdpureperms.listeners.JoinListener;
import dev.waterdog.event.EventPriority;
import dev.waterdog.event.defaults.PlayerLoginEvent;
import dev.waterdog.plugin.Plugin;
import dev.waterdog.utils.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class WdPureperms extends Plugin {

    public static Datasource getSource() {
        return source;
    }

    private void createDefaultConfig() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceFile("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Datasource source;

    @Override
    public void onEnable() {
        // Plugin startup logic

        createDefaultConfig();

        Configuration config = getConfig();

        source = new SQLSource(config.getString("jdbcurl"),config.getString("dbusername"),config.getString("dbpassword"));

        this.getProxy().getEventManager().subscribe(PlayerLoginEvent.class, new JoinListener(), EventPriority.HIGH);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

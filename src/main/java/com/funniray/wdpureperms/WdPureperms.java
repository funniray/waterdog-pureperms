/*
 *    WD-PurePerms - A WaterDogPE plugin that attempts at being fully compatible with PurePerms
 *    Copyright (C) 2021  Funniray
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    I am available for any questions/requests: funniray10@gmail.com
 */

package com.funniray.wdpureperms;

import com.funniray.wdpureperms.datasources.Datasource;
import com.funniray.wdpureperms.datasources.SQLSource;
import com.funniray.wdpureperms.listeners.JoinListener;
import dev.waterdog.waterdogpe.event.EventPriority;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;

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

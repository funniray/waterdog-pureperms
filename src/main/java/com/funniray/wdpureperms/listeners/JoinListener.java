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

package com.funniray.wdpureperms.listeners;

import com.funniray.wdpureperms.WdPureperms;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.utils.types.Permission;

import java.util.Set;
import java.util.function.Consumer;

public class JoinListener implements Consumer<PlayerLoginEvent> {
    @Override
    public void accept(PlayerLoginEvent e) {
        Set<String> permissions = WdPureperms.getSource().getPlayer(e.getPlayer().getName()).getPermissions();

        for (String p : permissions) {
            boolean a = !p.startsWith("-");
            String realName = p;
            if (!a) {
                realName = p.substring(1);
            }
            //System.out.println("Permission: "+realName+" Active: "+(a?"true":"false"));
            e.getPlayer().addPermission(new Permission(realName, a));
        }
    }
}

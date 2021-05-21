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

package com.funniray.wdpureperms.data;

import java.util.HashSet;
import java.util.Set;

public class PPPlayer {

    public Group group;
    public String username;
    public Set<String> permissions;

    public PPPlayer(String username, Group group, Set<String> permissions) {
        this.group = group;
        this.username = username;
        this.permissions = permissions;
    }

    public Group getParent() {
        return group;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getPermissions() {
        if (this.group == null) return this.permissions;
        Set<String> allPermissions = new HashSet<>(this.permissions);
        allPermissions.addAll(group.getPermissions());
        return allPermissions;
    }

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission) || group.hasPermission(permission) || this.hasPermission("*");
    }
}

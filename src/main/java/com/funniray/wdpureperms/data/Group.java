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
import java.util.List;
import java.util.Set;

public class Group {

    private final String groupname;
    private final String alias;
    private final boolean isDefault;
    private final List<Group> parents;
    private final Set<String> permissions;

    public Group(String groupname, String alias, boolean isDefault, List<Group> parents, Set<String> permissions) {
        this.groupname = groupname;
        this.alias = alias;
        this.isDefault = isDefault;
        this.parents = parents;
        this.permissions = permissions;
    }

    @SuppressWarnings("unused") // api usage
    public String getGroupname() {
        return groupname;
    }

    @SuppressWarnings("unused") // api usage
    public String getAlias() {
        return alias;
    }

    @SuppressWarnings("unused") // api usage
    public boolean isDefault() {
        return isDefault;
    }

    @SuppressWarnings("unused") // api usage
    public List<Group> getParents() {
        return parents;
    }

    @SuppressWarnings("unused") // api usage
    public Set<String> getPermissions() {
        if (this.parents == null) return this.permissions;

        Set<String> allPermissions = new HashSet<>(this.permissions);

        for (Group parent : this.parents) {
            if (parent == null) continue;
            allPermissions.addAll(parent.getPermissions());
        }

        return allPermissions;
    }

    @SuppressWarnings("unused") // api usage
    public boolean hasPermission(String permission) {
        for (Group parent : this.parents) {
            if (parent.permissions.contains(permission)) return true;
        }
        return permissions.contains(permission);
    }
}

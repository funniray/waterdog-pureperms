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

package com.funniray.wdpureperms.listeners;

import com.funniray.wdpureperms.WdPureperms;
import dev.waterdog.event.defaults.PlayerLoginEvent;
import dev.waterdog.utils.types.Permission;

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

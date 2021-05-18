package com.funniray.wdpureperms.datasources;

import com.funniray.wdpureperms.data.Group;
import com.funniray.wdpureperms.data.PPPlayer;

public interface Datasource {

    Group getGroup(String name);
    PPPlayer getPlayer(String name);
    void clearCache();

}

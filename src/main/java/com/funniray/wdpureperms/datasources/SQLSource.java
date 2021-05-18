package com.funniray.wdpureperms.datasources;

import com.funniray.wdpureperms.data.Group;
import com.funniray.wdpureperms.data.PPPlayer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SQLSource implements Datasource {

    public static final String GET_GROUP = "SELECT * FROM groups WHERE groupName = ?";
    public static final String GET_DEFAULT = "SELECT * FROM groups WHERE isDefault = 1";
    public static final String GET_PLAYER = "SELECT * FROM players WHERE userName = ?";

    private static HikariDataSource ds;

    public HashMap<String, Group> groupCache = new HashMap<>();

    public SQLSource(String jdbcURL, String username, String password) {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl(jdbcURL);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    @Override
    public void clearCache() {
        this.groupCache = new HashMap<>();
    }

    @Override
    public Group getGroup(String name) {
        if (this.groupCache.containsKey(name)) {
            return this.groupCache.get(name);
        }

        try(Connection conn = ds.getConnection()){
            PreparedStatement statement = conn.prepareStatement(name.equals("default") ? GET_DEFAULT : GET_GROUP);
            if (!name.equals("default")) statement.setString(1,name);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                String[] groupNames = res.getString("inheritance").split(",");
                List<Group> groups = new ArrayList<>();
                for (String string : groupNames) {
                    groups.add(getGroup(string));
                }
                Set<String> permissions = Arrays.stream(res.getString("permissions").split(",")).collect(Collectors.toSet());
                return new Group(name, res.getString("alias"), res.getBoolean("isDefault"), groups, permissions);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PPPlayer getPlayer(String name) {
        try(Connection conn = ds.getConnection()){
            PreparedStatement statement = conn.prepareStatement(GET_PLAYER);
            statement.setString(1,name);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                Set<String> permissions = Arrays.stream(res.getString("permissions").split(",")).collect(Collectors.toSet());
                Group group = getGroup(res.getString("userGroup"));
                if (group == null) group = getGroup("default");
                return new PPPlayer(name, group, permissions);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

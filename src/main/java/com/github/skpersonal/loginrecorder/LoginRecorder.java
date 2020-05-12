package com.github.skpersonal.loginrecorder;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class LoginRecorder extends JavaPlugin {
    private SQL sql;

    @Override
    public void onEnable() {
        // Plugin startup logicz
        saveResource("database.db", false);
        sql = SQL.getInstance();
        sql.connectDatabase();
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
        getCommand("checkentry").setExecutor(new CheckEntry());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (sql.isConnected()) {
            try {
                sql.getStatement().close();
                sql.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

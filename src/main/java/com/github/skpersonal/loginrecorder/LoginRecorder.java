package com.github.skpersonal.loginrecorder;

import com.github.skpersonal.loginrecorder.tabcomplete.CheckEntryTabComplete;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class LoginRecorder extends JavaPlugin {
    private SQL sql;

    @Override
    public void onEnable() {
        // Plugin startup logicz
        saveDefaultConfig();
        File dbFile = new File(this.getDataFolder().getPath() + "/database.db");
        if (!dbFile.exists()) {
            saveResource("database.db", false);
        }
        sql = SQL.getInstance();
        sql.connectDatabase();
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
        PluginCommand checkEntry = getCommand("checkentry");
        checkEntry.setExecutor(new CheckEntry());
        checkEntry.setTabCompleter(new CheckEntryTabComplete());

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

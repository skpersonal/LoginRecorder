package com.github.skpersonal.loginrecorder;

import org.bukkit.Bukkit;

import java.sql.*;

public class SQL {
    //CREATE TABLE list(name,uuid,date,ip);
    private Connection connection;
    private Statement statement;
    private boolean isConnected = false;
    private static final SQL sql = new SQL();

    private SQL() {
    }

    public static SQL getInstance() {
        return sql;
    }

    void connectDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            String path = (LoginRecorder.getPlugin(LoginRecorder.class).getDataFolder().getAbsolutePath() + "/database.db").replace("\\", "/");
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            statement = connection.createStatement();
            isConnected = true;
            Bukkit.getServer().getLogger().info("[LoginRecorder] Database Connected.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendCommand(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized ResultSet sendCommandFeedback(String sql) {
        ResultSet result = null;
        try {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}

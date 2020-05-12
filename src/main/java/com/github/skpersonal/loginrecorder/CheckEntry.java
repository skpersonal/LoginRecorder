package com.github.skpersonal.loginrecorder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEntry implements CommandExecutor {
    private SQL sql = SQL.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            ResultSet result = sql.sendCommandFeedback("SELECT * FROM list;");
            while (result.next()) {
                sender.sendMessage("Name='" + result.getString("name") + "'\nUUID='" + result.getString("uuid") + "'\nTime='" + result.getString("date") + "'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}

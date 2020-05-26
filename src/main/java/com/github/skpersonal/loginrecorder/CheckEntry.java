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
            StringBuilder builder = new StringBuilder();
            if (args.length == 0) {
                while (result.next()) {
                    builder.append(result.getString("name")).append(",");
                }
                int size = builder.length();
                if (size != 0) {
                    builder.delete(size - 1, size);
                }
            } else {
                while (result.next()) {
                    for (String arg : args) {
                        if (result.getString("name").equalsIgnoreCase(arg)) {
                            builder.append("\nname='")
                                    .append(result.getString("name"))
                                    .append("'\nuuid='")
                                    .append(result.getString("uuid"))
                                    .append("'\ndate='")
                                    .append(result.getString("date"))
                                    .append("'\nip  ='")
                                    .append(result.getString("ip"))
                                    .append("'");
                        }
                    }
                }
            }
            if (builder.length() == 0) {
                sender.sendMessage("Search results could not be found.");
            } else {
                sender.sendMessage(builder.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}

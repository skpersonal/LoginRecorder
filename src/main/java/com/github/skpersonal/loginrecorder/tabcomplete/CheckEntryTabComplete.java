package com.github.skpersonal.loginrecorder.tabcomplete;

import com.github.skpersonal.loginrecorder.SQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CheckEntryTabComplete implements TabCompleter {
    private SQL sql;

    public CheckEntryTabComplete() {
        sql = SQL.getInstance();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("checkentry")) {
            try {
                List<String> list = new ArrayList<>();
                ResultSet result = sql.sendCommandFeedback("SELECT * FROM list;");
                while (result.next()) {
                    list.add(result.getString("name"));
                }
                list.sort(Comparator.naturalOrder());
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

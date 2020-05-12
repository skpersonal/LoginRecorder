package com.github.skpersonal.loginrecorder;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Listeners implements Listener {
    private SQL sql = SQL.getInstance();

    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(cal.getTime());
        ResultSet result = sql.sendCommandFeedback("SELECT * FROM list;");
        try {
            boolean isRegistered = false;
            boolean isNameChanged = false;
            String rName = "";
            while (result.next()) {
                if (player.getUniqueId().toString().equalsIgnoreCase(result.getString("uuid"))) {
                    isRegistered = true;
                    rName = result.getString("name");
                    if (!player.getName().equalsIgnoreCase(rName)) {
                        isNameChanged = true;
                    }
                    break;
                }
            }
            if (isRegistered) {
                String date = result.getString("date");
                if (isNameChanged) {
                    e.setJoinMessage(ChatColor.YELLOW + player.getName() + " joined the game\nThis player is recorded as '" + rName + "'\nLast entry is '" + date + "'");
                } else {
                    e.setJoinMessage(ChatColor.YELLOW + player.getName() + " joined the game\nLast entry is '" + date + "'");
                }
                sql.sendCommand("UPDATE list SET name='" + player.getName() + "',date='" + time + "' WHERE uuid='" + player.getUniqueId().toString() + "';");
            } else {
                sql.sendCommand("INSERT INTO list VALUES('" + player.getName() + "','" + player.getUniqueId().toString() + "','" + time + "');");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

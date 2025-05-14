package org.ibenrm01.ecoSyX.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ibenrm01.ecoSyX.Utility;
import org.ibenrm01.ecoSyX.YAMLconfig.lang;
import org.ibenrm01.ecoSyX.YAMLconfig.settings;
import org.ibenrm01.ecoSyX.api.EcoSystem;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        settings setting = settings.getInstance();
        lang langs = lang.getInstance();
        String prefix = ChatColor.translateAlternateColorCodes('&', setting.getConfig().getString("serverName"));

        switch (args.length > 0 ? args[0].toLowerCase() : "") {
            case "add":
            case "set":
            case "reduce": {
                if (args.length < 3) {
                    sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString(args[0] + ".usage")));
                    return false;
                }
                if (!sender.hasPermission(setting.getConfig().getString("permissions." + args[0]))) {
                    sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.no-permission")));
                    return false;
                }

                String targetName = args[1];
                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(prefix + " Invalid number.");
                    return false;
                }

                Player onlinePlayer = Bukkit.getPlayerExact(targetName);
                OfflinePlayer target = (onlinePlayer != null) ? onlinePlayer : Bukkit.getOfflinePlayer(targetName);

                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("username", target.getName());
                placeholders.put("amount", Utility.getInstance().formatToRupiah(Integer.parseInt(args[2])));
                placeholders.put("fromusername", sender.getName());

                String[] result;
                if (args[0].equals("add")) {
                    result = EcoSystem.getInstance().giveMoney(target.getUniqueId().toString(), amount);
                } else if (args[0].equals("set")) {
                    result = EcoSystem.getInstance().setMoney(target.getUniqueId().toString(), amount);
                } else {
                    result = EcoSystem.getInstance().reduceMoney(target.getUniqueId().toString(), amount);
                }

                if (result[0].equals("success")) {
                    if (onlinePlayer != null) {
                        onlinePlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString(args[0] + ".title")),
                                ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString(args[0] + ".subtitle")), 20, 60, 20);
                        onlinePlayer.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString(args[0] + ".getmoney")), placeholders));
                    }
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString(args[0] + ".success")), placeholders));
                } else {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.notfound")), placeholders));
                }
                break;
            }

            case "pay": {
                if (args.length < 3) {
                    sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.usage")));
                    return false;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.no-player")));
                    return false;
                }

                Player fromPlayer = (Player) sender;
                Player toPlayer = Bukkit.getPlayerExact(args[1]);
                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(prefix + " Invalid number.");
                    return false;
                }

                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("username", args[1]);
                placeholders.put("amount", Utility.getInstance().formatToRupiah(Integer.parseInt(args[2])));
                placeholders.put("fromusername", sender.getName());

                if (toPlayer == null) {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.notfound")), placeholders));
                    return false;
                }

                String[] result = EcoSystem.getInstance().payMoney(fromPlayer.getUniqueId().toString(), toPlayer.getUniqueId().toString(), amount);
                if (result[0].equals("success")) {
                    toPlayer.sendTitle(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.title")),
                            ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.subtitle")), 20, 60, 20);
                    toPlayer.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.getmoney")), placeholders));
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.success")), placeholders));
                } else if (result[0].equals("notenough")) {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.notenough")), placeholders));
                } else if (result[0].equals("samepeople")) {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("pay.samepeople")), placeholders));
                } else {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.notfound")), placeholders));
                }
                break;
            }

            case "see": {
                if (args.length < 2) {
                    sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("getmoney.usage")));
                    return false;
                }
                Player toPlayer = Bukkit.getPlayerExact(args[1]);
                if (toPlayer == null) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    String[] result = EcoSystem.getInstance().getMoney(target.getUniqueId().toString());
                    Map<String, String> placeholders = new HashMap<>();
                    placeholders.put("username", target.getName());
                    if (result[0].equals("success")) {
                        placeholders.put("amount", Utility.getInstance().formatToRupiah(Integer.parseInt(result[1])));
                        sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("getmoney.success")), placeholders));
                    } else {
                        sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.notfound")), placeholders));
                    }
                }
                String[] result = EcoSystem.getInstance().getMoney(toPlayer.getUniqueId().toString());
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("username", toPlayer.getName());
                if (result[0].equals("success")) {
                    placeholders.put("amount", Utility.getInstance().formatToRupiah(Integer.parseInt(result[1])));
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("getmoney.success")), placeholders));
                } else {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.notfound")), placeholders));
                }
                break;
            }

            default: {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + " " + ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.no-player")));
                    return false;
                }
                Player self = (Player) sender;
                String[] result = EcoSystem.getInstance().myMoney(self.getUniqueId().toString());
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("username", self.getName());
                if (result[0].equals("success")) {
                    placeholders.put("amount", Utility.getInstance().formatToRupiah(Integer.parseInt(result[1])));
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("mymoney.success")), placeholders));
                } else {
                    sender.sendMessage(prefix + " " + Utility.getInstance().replace(ChatColor.translateAlternateColorCodes('&', langs.getConfig().getString("general.notfound")), placeholders));
                }
                break;
            }
        }
        return true;
    }

}

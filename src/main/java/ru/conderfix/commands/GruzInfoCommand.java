package ru.conderfix.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.conderfix.LDCargo;
import ru.conderfix.utils.ConfigUtil;
import ru.conderfix.utils.HexUtil;

public class GruzInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 0) {
            if (strings[0].equals("gps")) {
                if (LDCargo.instance.cargo.isSpawnedFalse) {
                    commandSender.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.no-cargo-gps")));
                    return true;
                }
                if (LDCargo.instance.cargo.isSpawned) {
                    for (String s2 : ConfigUtil.Messages.cargoGps) {
                        s2 = s2.replace("%x%", String.valueOf(Math.round(LDCargo.instance.cargo.gruzLocation.getX())));
                        s2 = s2.replace("%y%", String.valueOf(Math.round(LDCargo.instance.cargo.gruzLocation.getY())));
                        s2 = s2.replace("%z%", String.valueOf(Math.round(LDCargo.instance.cargo.gruzLocation.getZ())));
                        commandSender.sendMessage(HexUtil.translate(s2));
                    }
                    return true;
                }
            } else if (strings[0].equals("delay")) {
                for (String s2 : LDCargo.instance.getConfig().getStringList("messages.infoGruz")) {
                    commandSender.sendMessage(HexUtil.translate(s2));
                }
                return true;
            }
            } else {
                for (String s3 : LDCargo.instance.getConfig().getStringList("messages.commandHelpCargo")) {
                    commandSender.sendMessage(HexUtil.translate(s3));
                }
            }


        return true;
    }


}

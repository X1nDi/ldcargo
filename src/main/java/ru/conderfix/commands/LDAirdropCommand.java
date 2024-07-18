package ru.conderfix.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.conderfix.LDCargo;
import ru.conderfix.lootinventory.LootInventory;
import ru.conderfix.menu.OpeningAirDrop;
import ru.conderfix.utils.HexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LDAirdropCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("cargo.use")) {
            return true;
        }
        if (strings[0].equals("reload")) {
            Player player = (Player) commandSender;
            player.sendMessage("Done");
            LDCargo.instance.reloadConfig();
            return true;
        }
        if (strings[0].equals("start")) {
            LDCargo.instance.cargo.spawnGruz();
            return true;
        }
        if (strings[0].equals("stop")) {
            Player player = (Player) commandSender;
            player.sendMessage("Done");
            LDCargo.instance.cargo.despawnGruz();
            return true;
        }
        if (strings[0].equals("menu")) {
            Player player = (Player) commandSender;
            OpeningAirDrop openingAirDrop = new OpeningAirDrop();
            openingAirDrop.openMenu(player);
            return true;
        }
        if (strings[0].equals("menu_loot")) {
            Player player = (Player) commandSender;
            LootInventory openingAirDrop = new LootInventory();
            openingAirDrop.openMenu(player);
            return true;
        }
        if (strings[0].equals("regen_loot")) {
            Player player = (Player) commandSender;
            LootInventory openingAirDrop = new LootInventory();
            openingAirDrop.regenerationLoot();
            openingAirDrop.openMenu(player);
            return true;
        }
        if (strings[0].equals("tp")) {
            Player player = (Player) commandSender;
            player.sendMessage("Done");
            player.teleport(LDCargo.instance.cargo.gruzLocation);
            return true;
        }
        if (strings[0].equals("additem")) {
            Player player = (Player) commandSender;
            ItemStack hand = player.getItemInHand();
            if (hand == null || hand.getType() == Material.AIR) {
                player.sendMessage("У вас в руках воздух, его нельзя добавить в лут");
                return false;
            } else {
                int chance = Integer.parseInt(strings[1]);
                Integer random = Integer.valueOf(strings[2]);
                String uuid = UUID.randomUUID().toString();
                LDCargo.addItem(uuid, hand, chance, random);
                player.sendMessage("Успешно");
                return true;
            }
        } else {
            Player player = (Player) commandSender;
            for (String s3 : LDCargo.instance.getConfig().getStringList("messages.commandHelp")) {
                player.sendMessage(HexUtil.translate(s3));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> tabs = new ArrayList<>();
            tabs.add("start");
            tabs.add("stop");
            tabs.add("additem");
            tabs.add("menu");
            tabs.add("regen_loot");
            tabs.add("menu_loot");
            tabs.add("tp");
            tabs.add("reload");
            return tabs;
        }
        return null;
    }
}

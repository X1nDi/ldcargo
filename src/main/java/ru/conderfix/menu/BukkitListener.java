package ru.conderfix.menu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ru.conderfix.LDCargo;
import ru.conderfix.classes.Cargo;
import ru.conderfix.lootinventory.LootInventory;
import ru.conderfix.utils.ConfigUtil;
import ru.conderfix.utils.HexUtil;

import java.util.*;

public class BukkitListener implements Listener {

    private Map<UUID, Long> delayOnTrap = new HashMap<>();

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (LDCargo.openingAirDrop.viewing.get(event.getWhoClicked().getUniqueId()) != event.getInventory()) return;
        event.setCancelled(true);
        if (delayOnTrap.containsKey(event.getWhoClicked().getUniqueId())) {
            long secondsLeft = 1 - (System.currentTimeMillis() - delayOnTrap.get(event.getWhoClicked().getUniqueId())) / 1000;
            if (secondsLeft > 0) {
                event.getWhoClicked().sendMessage("Пожалуйста подождите...");
                event.setCancelled(true);
                return;
            }
        }
        delayOnTrap.put(event.getWhoClicked().getUniqueId(), System.currentTimeMillis());

        final Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == ConfigUtil.getInt("slots.one")) {
            ItemStack itemStack = OpeningAirDrop.itemStack1;
            if (event.getInventory().getItem(ConfigUtil.getInt("slots.one")).getType() == itemStack.getType()) {
                if (player.getInventory().containsAtLeast(itemStack, 1)) {
                    player.getInventory().removeItem(itemStack);
                    OpeningAirDrop.inventoryAirDrop.setItem(ConfigUtil.getInt("slots.one"), LDCargo.itemsInMenu.doneItem());
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 10);
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 10);
                } else {
                    player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.no-item")));
                }
            }
        }
        if (event.getSlot() == ConfigUtil.getInt("slots.two")) {
            ItemStack itemStack = OpeningAirDrop.itemStack2;
            if (event.getInventory().getItem(ConfigUtil.getInt("slots.two")).getType() == itemStack.getType()) {
                if (OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.one")).getType() == Material.LIME_STAINED_GLASS_PANE) {
                    if (player.getInventory().containsAtLeast(itemStack, 1)) {
                        player.getInventory().removeItem(itemStack);
                        OpeningAirDrop.inventoryAirDrop.setItem(ConfigUtil.getInt("slots.two"), LDCargo.itemsInMenu.doneItem());
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 10);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 10);
                    } else {
                        player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.no-item")));
                    }
                } else {
                    player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.gabela")));
                }
            }
        }
        if (event.getSlot() == ConfigUtil.getInt("slots.three")) {
            ItemStack itemStack = OpeningAirDrop.itemStack3;
            if (event.getInventory().getItem(ConfigUtil.getInt("slots.three")).getType() == itemStack.getType()) {
                if (OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.two")).getType() == Material.LIME_STAINED_GLASS_PANE) {
                    if (player.getInventory().containsAtLeast(itemStack, 1)) {
                        player.getInventory().removeItem(itemStack);
                        OpeningAirDrop.inventoryAirDrop.setItem(ConfigUtil.getInt("slots.three"), LDCargo.itemsInMenu.doneItem());
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 10);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 10);
                    } else {
                        player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.no-item")));
                    }
                } else {
                    player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.gabela")));
                }
            }
        }
        if (event.getSlot() == ConfigUtil.getInt("slots.four")) {
            ItemStack itemStack = OpeningAirDrop.itemStack4;
            if (event.getInventory().getItem(ConfigUtil.getInt("slots.four")).getType() == itemStack.getType()) {
                if (OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.three")).getType() == Material.LIME_STAINED_GLASS_PANE) {
                    if (player.getInventory().containsAtLeast(itemStack, 1)) {
                        player.getInventory().removeItem(itemStack);
                        OpeningAirDrop.inventoryAirDrop.setItem(ConfigUtil.getInt("slots.four"), LDCargo.itemsInMenu.doneItem());
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 10);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 10);
                    } else {
                        player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.no-item")));
                    }
                } else {
                    player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.gabela")));
                }
            }
        }
        if (event.getSlot() == ConfigUtil.getInt("slots.five")) {
            ItemStack itemStack = OpeningAirDrop.itemStack5;
            if (event.getInventory().getItem(ConfigUtil.getInt("slots.five")).getType() == itemStack.getType()) {
                if (OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.four")).getType() == Material.LIME_STAINED_GLASS_PANE) {
                    if (player.getInventory().containsAtLeast(itemStack, 1)) {
                        player.getInventory().removeItem(itemStack);
                        OpeningAirDrop.inventoryAirDrop.setItem(ConfigUtil.getInt("slots.five"), LDCargo.itemsInMenu.doneItem());
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 10);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10, 10);

                        if (OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.one")).getType() == Material.LIME_STAINED_GLASS_PANE &&
                                OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.two")).getType() == Material.LIME_STAINED_GLASS_PANE &&
                                OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.three")).getType() == Material.LIME_STAINED_GLASS_PANE &&
                                OpeningAirDrop.inventoryAirDrop.getItem(ConfigUtil.getInt("slots.four")).getType() == Material.LIME_STAINED_GLASS_PANE) {

                            for (Map.Entry<UUID, Inventory> allHashMap : OpeningAirDrop.viewing.entrySet()) {
                                Bukkit.getPlayer(allHashMap.getKey()).closeInventory();
                                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                                    for (String list : LDCargo.instance.getConfig().getStringList("messages.gruz-opeing")) {
                                        onlinePlayer.sendMessage(HexUtil.translate(list));
                                    }
                                });
                                OpeningAirDrop.viewing.remove(allHashMap.getKey());
                                LootInventory lootInventory = new LootInventory();
                                lootInventory.openMenu(player);
                                lootInventory.regenerationLoot();
                                Cargo.isOpenCargo = false;
                            }
                            Location location = player.getLocation();
                            for (Player bukkitplayer : Bukkit.getOnlinePlayers()) {
                                int raduis = 7;
                                if (bukkitplayer.getWorld() == location.getWorld() && bukkitplayer.getLocation().distance(location) <= raduis) {
                                    final int velocity = ConfigUtil.getInt("settings.velocity");
                                    bukkitplayer.setVelocity(new Vector(velocity, velocity, velocity));
                                }
                            }
                        }

                    } else {
                        player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.no-item")));
                    }
                } else {
                    player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.gabela")));
                }
            }
        }
    }

    @EventHandler
    private void onCloseMenu(InventoryCloseEvent event) {
        final UUID playerUuid = event.getPlayer().getUniqueId();
        if (LDCargo.openingAirDrop.viewing.get(playerUuid) != event.getInventory()) return;
        LDCargo.openingAirDrop.viewing.remove(playerUuid);
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        final Block placedBlock = event.getBlockPlaced();
        if (placedBlock.getType() == Material.OBSIDIAN || placedBlock.getType() == Material.CRYING_OBSIDIAN) {
            final int x = placedBlock.getX();
            final int y = placedBlock.getY();
            final int z = placedBlock.getZ();
            final int restrictedX = Cargo.gruzLocation.getBlockX();
            final int restrictedY = Cargo.gruzLocation.getBlockY();
            final int restrictedZ = Cargo.gruzLocation.getBlockZ();
            if (Math.abs(x - restrictedX) <= 25 && Math.abs(y - restrictedY) <= 25 && Math.abs(z - restrictedZ) <= 25) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    private void on(PlayerInteractEvent event) {
        final ItemStack placedBlock = event.getPlayer().getItemInHand();
        if (placedBlock.getType() == Material.WATER_BUCKET || placedBlock.getType() == Material.LAVA_BUCKET) {
            if (Cargo.gruzLocation != null) {
                final int x = event.getClickedBlock().getX();
                final int y = event.getClickedBlock().getY();
                final int z = event.getClickedBlock().getZ();
                final int restrictedX = Cargo.gruzLocation.getBlockX();
                final int restrictedY = Cargo.gruzLocation.getBlockY();
                final int restrictedZ = Cargo.gruzLocation.getBlockZ();
                if (Math.abs(x - restrictedX) <= 15 && Math.abs(y - restrictedY) <= 15 && Math.abs(z - restrictedZ) <= 15) {
                    event.setCancelled(true);
                }
            }
        }
    }

}

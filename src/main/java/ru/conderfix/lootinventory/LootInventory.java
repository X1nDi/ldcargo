package ru.conderfix.lootinventory;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.connection.GeyserConnection;
import ru.conderfix.LDCargo;
import ru.conderfix.classes.Cargo;
import ru.conderfix.utils.HexUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LootInventory implements Listener {

    public static final String nameTitle = HexUtil.translate(LDCargo.instance.getConfig().getString("gui.title"));
    public static final Integer slots = LDCargo.instance.getConfig().getInt("gui.size");
    public static final Inventory inventoryLoot = Bukkit.createInventory(null, slots, nameTitle);
    private Map<UUID, Long> delayOnTrap = new HashMap<>();

    public void regenerationLoot() {
        Cargo cargo = new Cargo();
        inventoryLoot.clear();
        cargo.fillInventoryWithRandomLoot(inventoryLoot);
    }

    public void openMenu(Player player) {
        player.openInventory(inventoryLoot);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        double cooldown = LDCargo.instance.getConfig().getDouble("antics.cooldown");
        String message = LDCargo.instance.getConfig().getString("antics.message");
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            FloodgateApi api = FloodgateApi.getInstance();
            if (!api.isFloodgatePlayer(event.getWhoClicked().getUniqueId())) {
                if (event.getWhoClicked().getOpenInventory().getTitle().contains(LDCargo.instance.getConfig().getString("menu.title"))) {
                    if (delayOnTrap.containsKey(event.getWhoClicked().getUniqueId())) {
                        double secondsLeft = cooldown - (double) (System.currentTimeMillis() - delayOnTrap.get(event.getWhoClicked().getUniqueId())) / 1000;
                        if (secondsLeft > 0) {
                            event.getWhoClicked().sendMessage(message);
                            event.setCancelled(true);
                        }
                    }
                    delayOnTrap.put(event.getWhoClicked().getUniqueId(), System.currentTimeMillis());
                } else if (event.getWhoClicked().getOpenInventory().getTitle().contains("Аукцион")) {
                    if (delayOnTrap.containsKey(event.getWhoClicked().getUniqueId())) {
                        double secondsLeft = cooldown - (double) (System.currentTimeMillis() - delayOnTrap.get(event.getWhoClicked().getUniqueId())) / 1000;
                        if (secondsLeft > 0) {
                            event.getWhoClicked().sendMessage(message);
                            event.getWhoClicked().closeInventory();
                        }
                    }
                }
                delayOnTrap.put(event.getWhoClicked().getUniqueId(), System.currentTimeMillis());
            }
        }
    }
}

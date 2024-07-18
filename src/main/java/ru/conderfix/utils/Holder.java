package ru.conderfix.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.conderfix.LDCargo;

public class Holder implements InventoryHolder {
    int size = LDCargo.instance.getConfig().getInt("gui.size");
    String title = LDCargo.instance.getConfig().getString("gui.title");
    private final Inventory inventory = Bukkit.createInventory(this, size, title);


    @NotNull
    public Inventory getInventory() {
        return this.inventory;
    }
}


package ru.conderfix;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.conderfix.classes.Cargo;
import ru.conderfix.commands.CargoTabCompleter;
import ru.conderfix.commands.GruzInfoCommand;
import ru.conderfix.commands.LDAirdropCommand;
import ru.conderfix.lootinventory.LootInventory;
import ru.conderfix.menu.BukkitListener;
import ru.conderfix.menu.ItemsInMenu;
import ru.conderfix.menu.OpeningAirDrop;
import ru.conderfix.menu.RandomItemStackInMenu;
import ru.conderfix.utils.ConfigUtil;
import ru.conderfix.utils.HexUtil;
import ru.conderfix.utils.Storage;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public final class LDCargo extends JavaPlugin {

    public static LDCargo instance;
    public static Storage items;

    public static void addItem(String id, ItemStack item, int chance, int maxAmount) {
        ConfigurationSection itemsSection = items.getConfig().getConfigurationSection("items");
        if (itemsSection == null) {
            items.getConfig().createSection("items");
            addItem(id, item, chance, maxAmount);
        } else {
            itemsSection = itemsSection.createSection(String.valueOf(id));
            itemsSection.set("item", item);
            itemsSection.set("chance", chance);
            itemsSection.set("random", maxAmount);

            items.save();
        }
    }

    public static Cargo cargo;
    public HolographicDisplaysAPI api;
    public static RandomItemStackInMenu randomItemStackInMenu;
    public static ItemsInMenu itemsInMenu;

    public static OpeningAirDrop openingAirDrop;
    @Override
    public void onEnable() {
        instance = this;
        api                   = HolographicDisplaysAPI.get(instance);
        randomItemStackInMenu = new RandomItemStackInMenu();
        itemsInMenu           = new ItemsInMenu();
        cargo                 = new Cargo();
        openingAirDrop        = new OpeningAirDrop();
        items                 = new Storage("items.yml");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new BukkitListener(), instance);
        getServer().getPluginManager().registerEvents(cargo, instance);
        getServer().getPluginManager().registerEvents(new LootInventory(), this);
        ConfigUtil.parseMessage();
        ConfigUtil.parseSettings();
        getCommand("ldairdrop").setExecutor(new LDAirdropCommand());
        getCommand("cargo").setExecutor(new GruzInfoCommand());
        getCommand("cargo").setTabCompleter(new CargoTabCompleter());
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                TimeZone moscowTimeZone = TimeZone.getTimeZone("Europe/Moscow");
                Calendar calendar = Calendar.getInstance(moscowTimeZone);

                List<String> autoSpawnList = getConfig().getStringList("auto-spawn");
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String currentTime = dateFormat.format(new Date(calendar.getTimeInMillis()));
                if (autoSpawnList.contains(currentTime)) {
                    cargo.spawnGruz();
                }
            }
        }, 0L, 1200L);
    }

    @Override
    public void onDisable() {
        if (cargo.isSpawned = true) {
            cargo.despawnGruz();
        }
    }
}

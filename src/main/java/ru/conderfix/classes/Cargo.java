package ru.conderfix.classes;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.history.change.BlockChange;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import ru.conderfix.LDCargo;
import ru.conderfix.lootinventory.LootInventory;
import ru.conderfix.menu.OpeningAirDrop;
import ru.conderfix.utils.ConfigUtil;
import ru.conderfix.utils.DiscordWebhook;
import ru.conderfix.utils.HexUtil;

import javax.net.ssl.HttpsURLConnection;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

public class Cargo implements Listener {

    public boolean isSpawned = false;
    public boolean isSpawnedFalse = true;
    public boolean isClosed = true;
    public int remainingTime;
    public static Location gruzLocation;
    public Location orignalLocation;
    public RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    public ProtectedCuboidRegion gruzRegion;
    public Hologram hologram;

    private String url = LDCargo.instance.getConfig().getString("webhook");



    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Location randomSecurityLocation(World worldGruz){

    Location locTest = null;
    for (int i = 1; i <= ConfigUtil.Settings.maxSearchSecurityLocation; i++){
        int x = getRandomNumber(ConfigUtil.Settings.minXSpawnGruz, ConfigUtil.Settings.maxXSpawnGruz);
        int z = getRandomNumber(ConfigUtil.Settings.minZSpawnGruz, ConfigUtil.Settings.maxZSpawnGruz);
        Location loc = new Location(worldGruz, x, 0, z);
        int y = worldGruz.getHighestBlockYAt(loc);
        locTest = new Location(worldGruz, x, y, z);
        if (locTest.getBlock().getType() == Material.WATER
                || locTest.getBlock().getType() == Material.LAVA
                || locTest.getBlock().getType() == Material.AIR){
            locTest = null;
        } else {
            break;
        }
    }
    final WorldGuard wg = WorldGuard.getInstance();
    final RegionContainer container = wg.getPlatform().getRegionContainer();
    final RegionQuery query = container.createQuery();
    final com.sk89q.worldedit.util.Location worldEditLocation = BukkitAdapter.adapt(locTest);
    if (query.getApplicableRegions(worldEditLocation).size() > 0) {
        randomSecurityLocation(Bukkit.getWorld("world"));
    }
    return locTest;


    }

    public void spawnGruz(){
        if (isSpawned == true)
            return;
        isSpawned = true;
        if (isSpawnedFalse == false)
            return;
        isSpawnedFalse = false;
        World worldGruz = Bukkit.getWorld(ConfigUtil.Settings.worldSpawnGruz);
        gruzLocation = randomSecurityLocation(worldGruz);
        if (gruzLocation == null)
            return;
        gruzLocation = gruzLocation.add(0, 1, 0);
        orignalLocation = gruzLocation.clone();
        createRegion((int) gruzLocation.getX(), (int) gruzLocation.getZ(), gruzLocation.getWorld());
        gruzLocation.getBlock().setType(Material.valueOf(ConfigUtil.Settings.materialGruz));
        hologram = LDCargo.instance.api.createHologram(gruzLocation.add(0.5D, 1.75D, 0.5D));
        String tokenWebhook = LDCargo.instance.getConfig().getString("webhook.url");
        String title = LDCargo.instance.getConfig().getString("webhook.title");
        String message = LDCargo.instance.getConfig().getString("webhook.message-start");
        String  color = LDCargo.instance.getConfig().getString("webhook.color");
        String jsonBrut = "";
        jsonBrut += "{\"embeds\": [{"
                + "\"title\": \""+ title +"\","
                + "\"description\": \""+ message +"\","
                + "\"color\": \""+ color + "\""  // добавлено закрытие кавычки и скобки
                + "}]}";
        try {
            URL url = new URL(tokenWebhook);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.addRequestProperty("Content-Type", "application/json");
            con.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStream stream = con.getOutputStream();
            stream.write(jsonBrut.getBytes());
            stream.flush();
            stream.close();
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (Player player : Bukkit.getOnlinePlayers()){
            for (String s : ConfigUtil.Messages.startGruz){
                s = s.replace("%x%", String.valueOf(Math.round(gruzLocation.getX())));
                s = s.replace("%y%", String.valueOf(Math.round(gruzLocation.getY())));
                s = s.replace("%z%", String.valueOf(Math.round(gruzLocation.getZ())));
                player.sendMessage(HexUtil.translate(s));
            }
        }
        startTimer();


    }

    public void despawnGruz(){
        isSpawned = false;
        if (isSpawnedFalse == true)
            return;
        isSpawnedFalse = true;
        orignalLocation.getBlock().setType(Material.AIR);
        deleteRegion(orignalLocation.getWorld());
        hologram.delete();
        orignalLocation = null;
        gruzLocation = null;
        isClosed = true;
        String tokenWebhook = LDCargo.instance.getConfig().getString("webhook.url");
        String title = LDCargo.instance.getConfig().getString("webhook.title");
        String message = LDCargo.instance.getConfig().getString("webhook.message-stop");
        String  color = LDCargo.instance.getConfig().getString("webhook.color");
        String jsonBrut = "";
        jsonBrut += "{\"embeds\": [{"
                + "\"title\": \""+ title +"\","
                + "\"description\": \""+ message +"\","
                + "\"color\": \""+ color + "\""  // добавлено закрытие кавычки и скобки
                + "}]}";
        try {
            URL url = new URL(tokenWebhook);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.addRequestProperty("Content-Type", "application/json");
            con.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStream stream = con.getOutputStream();
            stream.write(jsonBrut.getBytes());
            stream.flush();
            stream.close();
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createRegion(int x, int z, World worldBukkit) {
        int radius = 0; // Размер региона в блоках
        RegionManager regionManager = container.get(BukkitAdapter.adapt(worldBukkit));

        BlockVector3 minPoint = BlockVector3.at(x - radius, 0, z - radius);
        BlockVector3 maxPoint = BlockVector3.at(x + radius, 255, z + radius);
        String uuid = UUID.randomUUID().toString();
        gruzRegion = new ProtectedCuboidRegion(uuid, minPoint, maxPoint);

        gruzRegion.setFlag(Flags.PVP, StateFlag.State.ALLOW);
        gruzRegion.setFlag(Flags.LAVA_FIRE, StateFlag.State.DENY);
        gruzRegion.setFlag(Flags.WITHER_DAMAGE, StateFlag.State.DENY);
        gruzRegion.setFlag(Flags.CREEPER_EXPLOSION, StateFlag.State.DENY);
        gruzRegion.setFlag(Flags.OTHER_EXPLOSION, StateFlag.State.DENY);
        gruzRegion.setFlag(Flags.ENDERDRAGON_BLOCK_DAMAGE, StateFlag.State.DENY);
        gruzRegion.setPriority(5);

        regionManager.addRegion(gruzRegion);
    }

    private void deleteRegion(World worldBukkit){
        RegionManager regionManager = container.get(BukkitAdapter.adapt(worldBukkit));

        regionManager.removeRegion(gruzRegion.getId());
    }

    public void startTimer(){
        if (gruzLocation == null)
            return;


        BukkitRunnable timer = new BukkitRunnable() {
            int seconds = ConfigUtil.Settings.openGruzSeconds;


            @Override
            public void run() {
                if(isSpawned == false)
                    cancel();

                remainingTime = seconds;

                if (seconds <= 0) {
                    OpeningAirDrop.generateItems();
                    orignalLocation.getBlock().setType(Material.valueOf(ConfigUtil.Settings.materialGruz));
                    gruzLocation.getWorld().spawnParticle(Particle.valueOf(ConfigUtil.Settings.exploseParticle), gruzLocation, 5);
                    gruzLocation.getNearbyPlayers(8).forEach(player -> {
                        player.damage(10);
                        final int numberInConfig = ConfigUtil.getInt("settings.velocity");
                        player.setVelocity(new Vector(numberInConfig, numberInConfig, numberInConfig));
                    });

                    hologram.getLines().clear();
                    isClosed = false;
                    isOpenCargo = true;
                    for (String s : ConfigUtil.Messages.hologramOpenGruz){
                        hologram.getLines().appendText(HexUtil.translate(s));
                    }
                    BukkitRunnable timer = new BukkitRunnable() {
                        @Override
                        public void run() {
                            despawnGruz();
                        }
                    };
                    timer.runTaskLater(LDCargo.instance, 20L * 60 * 30);
                    cancel();
                } else {
                    if (gruzLocation == null)
                        cancel();
                    int minutes = seconds / 60;
                    int remainingSeconds = seconds % 60;


                    if (hologram.getLines().size() == 0){
                        for (String s : ConfigUtil.Messages.hologramGruz){
                            s = s.replace("{min}", String.valueOf(minutes));
                            s = s.replace("{sec}", String.valueOf(remainingSeconds));
                            hologram.getLines().appendText(HexUtil.translate(s));
                        }
                    } else {
                        hologram.getLines().clear();
                        for (String s : ConfigUtil.Messages.hologramGruz){
                            s = s.replace("{min}", String.valueOf(minutes));
                            s = s.replace("{sec}", String.valueOf(remainingSeconds));
                            hologram.getLines().appendText(HexUtil.translate(s));
                        }
                    }
                }
                seconds--;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld() == gruzLocation.getWorld() && player.getLocation().distance(gruzLocation) <= 15) {
                        player.playSound(gruzLocation, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 10, 20);
                    }
                }
            }
        };

        timer.runTaskTimer(LDCargo.instance, 0L, 20L);
    }


    public void fillInventoryWithRandomLoot(Inventory inventory) {
        ConfigurationSection itemsSection = LDCargo.items.getConfig().getConfigurationSection("items");
        if (itemsSection != null) {
            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = getRandomItem(itemsSection);
                if (item != null) {
                    inventory.setItem(i, item);
                }
            }
        } else {
            LDCargo.items.getConfig().createSection("items");
        }
    }

    private ItemStack getRandomItem(ConfigurationSection itemsSection) {
        ItemStack item = null;
        for (String itemId : itemsSection.getKeys(false)) {
            ConfigurationSection itemSection = itemsSection.getConfigurationSection(itemId);
            double chance = itemSection.getDouble("chance");
            double isRandom = itemSection.getDouble("random");
            if (Math.random() * 100.0D < chance) {
                item = itemSection.getItemStack("item");
                int randomNumber = new Random().nextInt((int) isRandom) + 1;
                item.setAmount(randomNumber);
                break; // Once we find an item, we break the loop
            }
        }
        return item;
    }

    public static boolean isOpenCargo = false;

    @EventHandler
    public void onChestClick(PlayerInteractEvent event) {
        if (isSpawned == false)
            return;
        if (event.getClickedBlock() == null)
            return;
        if (isClosed == false && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getX() == this.gruzLocation.getBlockX() && event.getClickedBlock().getY() == this.gruzLocation.getBlockY() - 1 && event.getClickedBlock().getZ() == this.gruzLocation.getBlockZ()){
            event.setCancelled(true);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 60, 1);
            event.getPlayer().getLocation().getWorld().spawnParticle(Particle.valueOf(ConfigUtil.Settings.openParticle), gruzLocation, 1);
            if (isOpenCargo == true) {
                OpeningAirDrop openingAirDrop = new OpeningAirDrop();
                openingAirDrop.openMenu(event.getPlayer());
            }
            if (isOpenCargo == false) {
                LootInventory lootInventory = new LootInventory();
                lootInventory.openMenu(event.getPlayer());

            }

        } else if (event.getClickedBlock().getX() == this.gruzLocation.getBlockX() && event.getClickedBlock().getY() == this.gruzLocation.getBlockY() - 1 && event.getClickedBlock().getZ() == this.gruzLocation.getBlockZ()){
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.valueOf(LDCargo.instance.getConfig().getString("settings.closedGruz")), 60, 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (isSpawned == false)
            return;
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1);
        if (command.equals("sethome")) {
            if (gruzLocation == null)
                return;
            if (player.getLocation().distance(gruzLocation) <= LDCargo.instance.getConfig().getInt("settings.commandDistance")) {
                player.sendMessage(HexUtil.translate(LDCargo.instance.getConfig().getString("messages.dontCommand")));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (!isSpawned)
            return;
        if (e.getLocation().getBlockX() == gruzLocation.getBlockX() && e.getLocation().getBlockY() == gruzLocation.getBlockY() && e.getLocation().getBlockZ() == gruzLocation.getBlockZ() && e.getLocation().getWorld().getName().equals(gruzLocation.getWorld().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent e) {
        if (!isSpawned)
            return;
        for (Block block : e.getBlocks()) {
            if (block.getLocation().getBlockX() == gruzLocation.getBlockX() && block.getLocation().getBlockY() == gruzLocation.getBlockY()-1 && block.getLocation().getBlockZ() == gruzLocation.getBlockZ() && block.getLocation().getWorld().getName().equals(gruzLocation.getWorld().getName())) {
                e.setCancelled(true);
        }
        }
    }

}

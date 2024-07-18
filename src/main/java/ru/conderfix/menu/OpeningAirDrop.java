package ru.conderfix.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.conderfix.LDCargo;
import ru.conderfix.utils.ConfigUtil;
import ru.conderfix.utils.HexUtil;

import java.util.*;

public class OpeningAirDrop {

    public static final Map<UUID, Inventory> viewing = new HashMap<>();
    public static final String nameTitle = HexUtil.translate(LDCargo.instance.getConfig().getString("menu.title"));
    public static final Integer slots = LDCargo.instance.getConfig().getInt("menu.size");
    public static ItemStack itemStack1 = LDCargo.randomItemStackInMenu.getRandomItemOne();
    public static ItemStack itemStack2 = LDCargo.randomItemStackInMenu.getRandomItemOne();
    public static ItemStack itemStack3 = LDCargo.randomItemStackInMenu.getRandomItemOne();
    public static ItemStack itemStack4 = LDCargo.randomItemStackInMenu.getRandomItemOne();
    public static ItemStack itemStack5 = LDCargo.randomItemStackInMenu.getRandomItemOne();
    public static final Inventory inventoryAirDrop = Bukkit.createInventory(null, slots, nameTitle);

    static {
        ItemStack help = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = help.getItemMeta();
        itemMeta.setDisplayName(HexUtil.translate("&aПомощь"));
        List<String> loreItem = new ArrayList<>();
        loreItem.add(HexUtil.translate("&a"));
        loreItem.add(HexUtil.translate("&fВы продержались до &aактивации.."));
        loreItem.add(HexUtil.translate("&fМолодцы, но этого к сожалению мало!"));
        loreItem.add(HexUtil.translate("&a"));
        loreItem.add(HexUtil.translate("&fНеизвестные &aзашифровали груз&f,"));
        loreItem.add(HexUtil.translate("&fА для его открытия придется разгадать шифр!"));
        loreItem.add(HexUtil.translate("&a"));
        loreItem.add(HexUtil.translate("&fАктивируйте &a5 артефактов&f, чтобы"));
        loreItem.add(HexUtil.translate("&fОткрыть &aзакрытый груз &fпервее других!"));
        itemMeta.setLore(loreItem);

        help.setItemMeta(itemMeta);
        inventoryAirDrop.setItem(25, help);

        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.one"), itemStack1);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.two"), itemStack2);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.three"), itemStack3);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.four"), itemStack4);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.five"), itemStack5);

        inventoryAirDrop.setItem(0, LDCargo.itemsInMenu.decor());
        inventoryAirDrop.setItem(9, LDCargo.itemsInMenu.decor());
        inventoryAirDrop.setItem(18, LDCargo.itemsInMenu.decor());
        inventoryAirDrop.setItem(8, LDCargo.itemsInMenu.decor());
        inventoryAirDrop.setItem(17, LDCargo.itemsInMenu.decor());
        inventoryAirDrop.setItem(26, LDCargo.itemsInMenu.decor());
    }

    public void openMenu(Player player) {
        viewing.put(player.getUniqueId(), inventoryAirDrop);
        player.openInventory(inventoryAirDrop);
    }
    public static void generateItems() {
        itemStack1 = LDCargo.randomItemStackInMenu.getRandomItemOne();
        itemStack2 = LDCargo.randomItemStackInMenu.getRandomItemOne();
        itemStack3 = LDCargo.randomItemStackInMenu.getRandomItemOne();
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.one"), itemStack1);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.two"), itemStack2);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.three"), itemStack3);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.four"), itemStack4);
        inventoryAirDrop.setItem(ConfigUtil.getInt("slots.five"), itemStack5);
    }
}

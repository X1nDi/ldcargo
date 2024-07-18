package ru.conderfix.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.conderfix.utils.HexUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemsInMenu {

    public ItemStack doneItem() {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(HexUtil.translate("&f"));
        List<String> loreItem = new ArrayList<>();
        loreItem.add(HexUtil.translate("&a» &fЭтот предмет уже сдан,"));
        loreItem.add(HexUtil.translate("&a» &fприступайте к следующему предмету!"));
        loreItem.add(HexUtil.translate("&f"));
        itemMeta.setLore(loreItem);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack decor() {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(HexUtil.translate("&f"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}

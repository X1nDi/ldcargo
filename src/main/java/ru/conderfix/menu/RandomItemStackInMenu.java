package ru.conderfix.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItemStackInMenu {

    public static List<ItemStack> items = new ArrayList<>();

    static {
        items.add(new ItemStack(Material.FLINT, 1));
        items.add(new ItemStack(Material.DIAMOND, 1));
        items.add(new ItemStack(Material.GOLD_INGOT, 1));
        items.add(new ItemStack(Material.GOLDEN_CARROT, 1));
        items.add(new ItemStack(Material.SWEET_BERRIES, 1));
        items.add(new ItemStack(Material.CHORUS_FRUIT, 1));
        items.add(new ItemStack(Material.ENDER_PEARL, 1));
        items.add(new ItemStack(Material.GLISTERING_MELON_SLICE, 1));
        items.add(new ItemStack(Material.MAGMA_CREAM, 1));
        items.add(new ItemStack(Material.COD, 1));
        items.add(new ItemStack(Material.MILK_BUCKET, 1));
        items.add(new ItemStack(Material.NETHER_WART, 1));
        items.add(new ItemStack(Material.SALMON, 1));
        items.add(new ItemStack(Material.BLAZE_POWDER, 1));

    }

    public ItemStack getRandomItemOne() {
        Random random = new Random();
        int index = random.nextInt(items.size());
        return items.get(index);
    }
}

package ru.conderfix.utils;

import java.util.List;
import java.util.Random;
import org.bukkit.inventory.ItemStack;

public class RandomItemSelector {
    private List<Object> items;
    private Random random;

    public RandomItemSelector(List<Object> items) {
        this.items = items;
        this.random = new Random();
    }

    public ItemStack getRandomItem() {
        int index = this.random.nextInt(this.items.size());
        return (ItemStack)this.items.get(index);
    }
}

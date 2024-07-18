package ru.conderfix.utils;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.conderfix.LDCargo;


public class Storage {
    private File file;
    private FileConfiguration config;

    public Storage(String name) {
        this.file = new File(LDCargo.instance.getDataFolder(), name);
        try {
            if (!this.file.exists() && !this.file.createNewFile()) throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file: ", e);
        }

        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }
    public FileConfiguration getConfig() {
        return this.config;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file:", e);
        }
    }
}


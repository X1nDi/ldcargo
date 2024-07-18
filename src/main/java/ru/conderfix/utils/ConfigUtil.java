package ru.conderfix.utils;

import ru.conderfix.LDCargo;

import java.util.List;

public class ConfigUtil {
    public static class Messages {
        public static List<String> noPermession;
        public static List<String> startGruz;
        public static List<String> cargoGps;
        public static List<String> infoGruz;
        public static List<String> commandHelp;
        public static List<String> startGruzCommand;
        public static List<String> stopGruzCommand;
        public static List<String> addItemCommand;
        public static List<String> hologramGruz;
        public static List<String> hologramOpenGruz;

    }

    public static class Settings {
        public static String worldSpawnGruz;
        public static int minXSpawnGruz;
        public static int maxXSpawnGruz;
        public static int minZSpawnGruz;
        public static int maxZSpawnGruz;
        public static String materialGruz;
        public static String exploseParticle;
        public static String openParticle;
        public static int maxSearchSecurityLocation;
        public static int openGruzSeconds;
        public static int lootGruz;

    }

    public static void parseMessage(){
        Messages.noPermession = LDCargo.instance.getConfig().getStringList("messages.noPermession");
        Messages.startGruz = LDCargo.instance.getConfig().getStringList("messages.startGruz");
        Messages.cargoGps = LDCargo.instance.getConfig().getStringList("messages.cargoGps");
        Messages.infoGruz = LDCargo.instance.getConfig().getStringList("messages.infoGruz");
        Messages.commandHelp = LDCargo.instance.getConfig().getStringList("messages.commandHelp");
        Messages.startGruzCommand = LDCargo.instance.getConfig().getStringList("messages.startGruzCommand");
        Messages.stopGruzCommand = LDCargo.instance.getConfig().getStringList("messages.stopGruzCommand");
        Messages.addItemCommand = LDCargo.instance.getConfig().getStringList("messages.addItemCommand");
        Messages.hologramGruz = LDCargo.instance.getConfig().getStringList("messages.hologramGruz");
        Messages.hologramOpenGruz = LDCargo.instance.getConfig().getStringList("messages.hologramOpenGruz");
    }

    public static void parseSettings(){
        Settings.worldSpawnGruz = LDCargo.instance.getConfig().getString("settings.worldSpawnGruz");
        Settings.minXSpawnGruz = LDCargo.instance.getConfig().getInt("settings.minXSpawnGruz");
        Settings.maxXSpawnGruz = LDCargo.instance.getConfig().getInt("settings.maxXSpawnGruz");
        Settings.minZSpawnGruz = LDCargo.instance.getConfig().getInt("settings.minZSpawnGruz");
        Settings.maxZSpawnGruz = LDCargo.instance.getConfig().getInt("settings.maxZSpawnGruz");
        Settings.materialGruz = LDCargo.instance.getConfig().getString("settings.materialGruz");
        Settings.exploseParticle = LDCargo.instance.getConfig().getString("settings.exploseParticle");
        Settings.openParticle = LDCargo.instance.getConfig().getString("settings.openParticle");
        Settings.maxSearchSecurityLocation = LDCargo.instance.getConfig().getInt("settings.maxSearchSecurityLocation");
        Settings.openGruzSeconds = LDCargo.instance.getConfig().getInt("settings.openGruzSeconds");
        Settings.lootGruz = LDCargo.instance.getConfig().getInt("settings.lootGruz");

    }

    public static String getString(String path) {
        return HexUtil.translate(LDCargo.instance.getConfig().getString(path));
    }

    public static int getInt(String path) {
        return LDCargo.instance.getConfig().getInt(path);
    }
}
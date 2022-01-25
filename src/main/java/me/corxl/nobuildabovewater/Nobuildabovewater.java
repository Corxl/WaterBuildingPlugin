package me.corxl.nobuildabovewater;

import me.corxl.nobuildabovewater.Listeners.BlockPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Nobuildabovewater extends JavaPlugin {

    public static Nobuildabovewater plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

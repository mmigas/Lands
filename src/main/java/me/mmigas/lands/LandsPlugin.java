package me.mmigas.lands;

import me.mmigas.lands.commands.LandsCMD;
import me.mmigas.lands.lands.LandsManager;
import me.mmigas.lands.lands.OwnersManager;
import me.mmigas.lands.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class LandsPlugin extends JavaPlugin {


    private static LandsPlugin instance;
    private LandsManager landsManager;
    private OwnersManager ownersManager;

    @Override
    public void onEnable() {
        instance = this;
        this.landsManager = new LandsManager();
        this.ownersManager = new OwnersManager();
        registerListeners();
        registerCommands();

    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("lands")).setExecutor(new LandsCMD());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }


    public LandsManager getLandsManager() {
        return landsManager;
    }

    public OwnersManager getOwnersManager() {
        return ownersManager;
    }

    public static LandsPlugin getInstance() {
        return instance;
    }
}

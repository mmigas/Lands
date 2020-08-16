package me.mmigas;

import me.mmigas.commands.LandsCMD;
import org.bukkit.plugin.java.JavaPlugin;

public class LandsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new LandsCMD();

    }
}

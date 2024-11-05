package novyXtreme;

import net.milkbowl.vault.economy.Economy;

import novyXtreme.Listeners.gateLeverListener;
import novyXtreme.Listeners.portalEnterListener;
import novyXtreme.Listeners.portalTeleportListener;
import novyXtreme.commands.*;
import novyXtreme.utils.dbFunctions;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class NovyXtreme extends JavaPlugin {

    private static NovyXtreme plugin;

    public static NovyXtreme getPlugin() {
        return plugin;
    }

    private static Economy econ = null;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new portalTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new gateLeverListener(), this);
        getServer().getPluginManager().registerEvents(new portalEnterListener(), this);
        getCommand("nxremove").setExecutor(new nxremove());
        getCommand("nxforce").setExecutor(new nxforce());
        getCommand("nxlist").setExecutor(new nxlist());
        getCommand("nxcomplete").setExecutor(new nxcomplete());
        getCommand("dial").setExecutor(new dial());
        getCommand("nxgo").setExecutor(new nxgo());
        getCommand("nxreload").setExecutor(new nxreload());
        try {
            dbFunctions.loadStargates();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //VaultAPI setup
        if (!setupEconomy()) {
            System.out.println(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
        }

    }
    //Set up economy based on Vault
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {

            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        try {
            dbFunctions.saveStargates();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

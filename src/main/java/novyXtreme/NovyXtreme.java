package novyXtreme;
import novyXtreme.Listeners.gateLeverListener;
import novyXtreme.Listeners.portalEnterListener;
import novyXtreme.commands.*;
import novyXtreme.utils.dbFunctions;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;

public final class NovyXtreme extends JavaPlugin {

    private static NovyXtreme plugin;
    public static NovyXtreme getPlugin()
    {
        return plugin;
    }

    @Override
    public void onEnable()
    {
        plugin = this;
        getServer().getPluginManager().registerEvents(new gateLeverListener(), this);
        getServer().getPluginManager().registerEvents(new portalEnterListener(), this);
        getCommand("nxremove").setExecutor(new nxremove());
        getCommand("nxlist").setExecutor(new nxlist());
        getCommand("nxcomplete").setExecutor(new nxcomplete());
        getCommand("dial").setExecutor(new dial());
        getCommand("nxgo").setExecutor(new nxgo());
        try
        {
            dbFunctions.loadStargates();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable()
    {
        try
        {
            dbFunctions.saveStargates();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

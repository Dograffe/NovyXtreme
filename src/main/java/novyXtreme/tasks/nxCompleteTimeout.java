package novyXtreme.tasks;

import novyXtreme.NovyXtreme;
import novyXtreme.utils.activationUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class nxCompleteTimeout extends BukkitRunnable
{
    NovyXtreme plugin;
    Player player;

    public nxCompleteTimeout(NovyXtreme plugin, Player player) {
        this.player = player;
        this.plugin = plugin;
    }
//An async task which manages the timeout after a valid gate has been activated
    @Override
    public void run()
    {
        if (player.hasMetadata("NxCompleteActive"))
        {
            player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "NxComplete Timed out");
            activationUtil.nxcompleteEnd(player);
        }
    }
}



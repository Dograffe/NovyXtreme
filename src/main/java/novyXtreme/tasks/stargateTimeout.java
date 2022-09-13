package novyXtreme.tasks;

import novyXtreme.NovyXtreme;
import novyXtreme.Stargate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class stargateTimeout extends BukkitRunnable
    {
        NovyXtreme plugin;
        Stargate stargate;
        Player player;

        public stargateTimeout(NovyXtreme plugin, Stargate stargate, Player player)
        {
            this.player = player;
            this.stargate = stargate;
            this.plugin = plugin;
        }

        @Override
        public void run()
        {
            if(stargate.isActive())
            {
                stargate.setActive(false);
                player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate: " + stargate.getName() + " Timed out");
            }

        }

    }

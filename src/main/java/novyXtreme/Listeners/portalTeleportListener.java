package novyXtreme.Listeners;

import novyXtreme.Stargate;
import novyXtreme.utils.dbFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class portalTeleportListener implements Listener
{
    @EventHandler
    public void onPortalTeleportEvent(PlayerPortalEvent event)
    {
        Location origin;

        for(Stargate activePortal : dbFunctions.activeStargates)
        {
            if(activePortal != null)
            {

                for (Location checkBlock : activePortal.getPortalBlocks())
                {
                    //add a method to get portal blocks +-.5 so as to stop accidental teleportation
                    World world = activePortal.getLeverBlock().getWorld();
                    origin = new Location(world, event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY(), event.getPlayer().getLocation().getBlockZ());

                    if (origin.equals(checkBlock))
                    {
                        try
                        {

                            event.setCancelled(true);
                        } catch (NullPointerException e)
                        {

                            event.setCancelled(true);
                        }
                        return;
                        //add code to allow for multiple teleports
                    }
                }
            }
        }
    }


}
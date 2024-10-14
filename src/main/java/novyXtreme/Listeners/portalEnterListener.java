package novyXtreme.Listeners;

import novyXtreme.Stargate;
import novyXtreme.utils.activationUtil;
import novyXtreme.utils.dbFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class portalEnterListener implements Listener
{
    @EventHandler
    public void onPortal(EntityPortalEnterEvent event)
    {
       /* if(!(event.getEntity() instanceof Player))
        {
            return;
        }*/
       // Player player = (Player) event.getEntity();
        Location origin;
            for(Stargate activePortal: dbFunctions.activeStargates)
            {
                if(activePortal != null)
                {
                    for (Location checkBlock : activePortal.getPortalBlocks())
                    {
                        World world = activePortal.getLeverBlock().getWorld();
                        origin = new Location(world, event.getLocation().getBlockX(), event.getLocation().getBlockY(), event.getLocation().getBlockZ());
                        if (origin.equals(checkBlock))
                        {
                            try
                            {
                                event.getEntity().setPortalCooldown(50);
                                event.getEntity().teleport(activePortal.getDestinationGate().getTpCoordinates(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                //System.out.println("NewTP Coords: " + activePortal.getDestinationGate().getTpCoordinates());

                            } catch (NullPointerException e)
                            {
                            }

                            // activationUtil.deactivateGate(activePortal, player);
                            return;
                            //add code to allow for multiple teleports
                        }
                    }
                }
            }
        }
}

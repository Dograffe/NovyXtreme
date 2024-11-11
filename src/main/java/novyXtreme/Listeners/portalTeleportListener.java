package novyXtreme.Listeners;

import novyXtreme.Stargate;
import novyXtreme.utils.dbFunctions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class portalTeleportListener implements Listener {
    @EventHandler
    public void onPortalTeleportEvent(PlayerPortalEvent event) {
        Location origin;
        // Check all active stargate portal blocks for event origin
        for (Stargate activePortal : dbFunctions.activeStargates) {
            if (activePortal != null) {

                for (Location checkBlock : activePortal.getPortalBlocks()) {

                    World world = activePortal.getLeverBlock().getWorld();
                    origin = new Location(world, event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY(), event.getPlayer().getLocation().getBlockZ());

                    // Cancel vanilla teleport if portal is part of a stargate
                    if (origin.equals(checkBlock)) {
                        try {

                            event.setCancelled(true);
                        } catch (NullPointerException e) {

                            event.setCancelled(true);
                        }
                        return;
                    }
                }
            }
        }
    }
}
package novyXtreme.Listeners;

import novyXtreme.Stargate;
import novyXtreme.utils.dbFunctions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

// Called when an entity enters any type of Portal
public class portalEnterListener implements Listener {
    @EventHandler
    public void onPortal(EntityPortalEnterEvent event) {

        Location origin;
        // Check if portal is part of active stargate.
        for (Stargate activePortal : dbFunctions.activeStargates) {
            if (activePortal != null) {
                for (Location checkBlock : activePortal.getPortalBlocks()) {
                    World world = activePortal.getLeverBlock().getWorld();
                    origin = new Location(world, event.getLocation().getBlockX(), event.getLocation().getBlockY(), event.getLocation().getBlockZ());

                    if (origin.equals(checkBlock)) {
                        try {
                            // Set portal cooldown to stop multiple teleport attempts
                            event.getEntity().setPortalCooldown(50);
                            // Teleport player to destination stargate
                            event.getEntity().teleport(activePortal.getDestinationGate().getTpCoordinates(), PlayerTeleportEvent.TeleportCause.PLUGIN);

                        } catch (NullPointerException e) {
                            // TODO add console debug code
                        }
                        return;
                    }
                }
            }
        }
    }
}

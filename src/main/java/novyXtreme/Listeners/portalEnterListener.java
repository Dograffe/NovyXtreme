package novyXtreme.Listeners;

import novyXtreme.Stargate;
import novyXtreme.utils.dbFunctions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

// Called when an entity enters any type of Portal
public class portalEnterListener implements Listener {
    @EventHandler
    public void onPortal(EntityPortalEnterEvent event) {

        Location origin;
        // Check if portal is part of active stargate.
        Entity entity = event.getEntity();
        for (Stargate activePortal : dbFunctions.activeStargates) {
            if (activePortal != null) {
                for (Location checkBlock : activePortal.getPortalBlocks()) {
                    World world = activePortal.getLeverBlock().getWorld();
                    origin = new Location(world, event.getLocation().getBlockX(), event.getLocation().getBlockY(), event.getLocation().getBlockZ());

                    if (origin.equals(checkBlock)) {
                        try {
                            // Set portal cooldown to stop multiple teleport attempts
                            entity.setPortalCooldown(80);
                            // Teleport player to destination stargate
                            Location dest = activePortal.getDestinationGate().getTpCoordinates();
                            if (!dest.getWorld().isChunkLoaded(dest.getBlockX() >> 4, dest.getBlockZ() >> 4)) {
                                dest.getWorld().loadChunk(dest.getBlockX() >> 4, dest.getBlockZ() >> 4);
                            }
                            entity.teleport(dest, PlayerTeleportEvent.TeleportCause.PLUGIN);
                            entity.setVelocity(new Vector(0.0, 0.1, 0.0));

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

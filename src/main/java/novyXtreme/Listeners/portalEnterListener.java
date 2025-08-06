package novyXtreme.Listeners;

import novyXtreme.NovyXtreme;
import novyXtreme.Stargate;
import novyXtreme.utils.activationUtil;
import novyXtreme.utils.dbFunctions;
import novyXtreme.utils.teleportUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
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
                        event.setCancelled(true);
                        try {
                            Vector arriveSpeed = new Vector();
                            // Set portal cooldown to stop multiple teleport attempts
                            entity.setPortalCooldown(80);
                            // Teleport player to destination stargate
                            Location dest = activePortal.getDestinationGate().getTpCoordinates();
                            if (!dest.getWorld().isChunkLoaded(dest.getBlockX() >> 4, dest.getBlockZ() >> 4)) {
                                dest.getWorld().loadChunk(dest.getBlockX() >> 4, dest.getBlockZ() >> 4);
                            }
                            if (entity instanceof Player){
                                Player player = (Player) entity;
                                NovyXtreme.getPlugin().getLogger().info("Teleporting Player: "+ player.getName() + " to stargate: " + activePortal.getDestinationGate().getName());
                                player.teleport(dest);
                                activationUtil.deactivateGate(activePortal, player);
                                player.setVelocity(teleportUtils.getExitPortalVector(activePortal.getDestinationGate().getFacing()));
                            } else {
                                entity.teleport(dest);
                                entity.setVelocity(teleportUtils.getExitPortalVector(activePortal.getDestinationGate().getFacing()));
                            }


                        } catch (NullPointerException e) {
                            // Player is in destination portal
                        }
                        return;
                    }
                }
            }
        }
    }
}

package novyXtreme.utils;
import novyXtreme.NovyXtreme;
import novyXtreme.Stargate;
import novyXtreme.tasks.nxCompleteTimeout;
import novyXtreme.tasks.stargateTimeout;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;

public class activationUtil
{
    public static void activateGate(Stargate stargate, Player player)
    {
        if(!stargate.setActive(true))
        {
            player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Gate structure no longer valid.. Please reconstruct and reactivate.");
            dbFunctions.removeGateByName(stargate.getName());
            return;
        }

        player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate Activated!");
       // player.setMetadata("StargateActive", new FixedMetadataValue(NovyXtreme.getPlugin(), stargate.getName()));
        stargate.setActivatedby(player.getName());
        BukkitTask gateTimeout = new stargateTimeout(NovyXtreme.getPlugin(), stargate, player).runTaskLater(NovyXtreme.getPlugin(), 500L);

    }
    public static void activatePortal(Stargate stargate)
    {
        if(!stargate.isActive()){stargate.setActive(true);}

        World world = stargate.getLeverBlock().getWorld();
        Location[] portalBlocks = stargate.getPortalBlocks();

        for(int i = 0; i <portalBlocks.length; i++)
        {
            Block portalBlock = world.getBlockAt(portalBlocks[i]);
            if(stargate.getGateOrientation().equals(BlockFace.EAST) || stargate.getGateOrientation().equals(BlockFace.WEST))
            {
                portalBlock.setType(Material.NETHER_PORTAL);
                BlockData portalBlockData = portalBlock.getBlockData();
                Orientable orientable = (Orientable) portalBlockData;
                orientable.setAxis(Axis.Z);
                portalBlock.setBlockData(orientable);

            } else {portalBlock.setType(Material.NETHER_PORTAL);}
        }
    }
    public static void deactivateGate(Stargate stargate, @Nullable Player player)
    {
        if(!stargate.setActive(false))
        {
            player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Gate structure no longer valid.. Please reconstruct and reactivate.");
            dbFunctions.removeGateByName(stargate.getName());
            return;
        }
        if(stargate.getDestinationGate() != null){stargate.getDestinationGate().setActive(false);}
        if(stargate.getDestinationGate() == null)
        {
            for(Stargate activeGate: dbFunctions.activeStargates)
            {
                try
                {
                    if (activeGate.getDestinationGate().getName().equals(stargate.getName())) {
                        activeGate.setActive(false);
                        break;
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
        stargate.setActivatedby(null);
    }
    public static void nxcompleteStart(Block leverblock, Player player)
    {
        player.setMetadata("NxCompleteActive", new FixedMetadataValue(NovyXtreme.getPlugin(), leverblock));
        BukkitTask gateTimeout = new nxCompleteTimeout(NovyXtreme.getPlugin(), player).runTaskLater(NovyXtreme.getPlugin(), 800L);
    }
    public static void nxcompleteEnd(Player player)
    {
        player.removeMetadata("NxCompleteActive", NovyXtreme.getPlugin());
    }
}

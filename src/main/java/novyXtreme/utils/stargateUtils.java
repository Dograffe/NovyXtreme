package novyXtreme.utils;

import novyXtreme.NovyXtreme;
import novyXtreme.Stargate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class stargateUtils
{
    Plugin plugin = NovyXtreme.getPlugin(NovyXtreme.class);
    int stargateCost = plugin.getConfig().getInt("StargateCost");

    public static Location calcTeleportBlock(Location leverBlock, BlockFace leverBlockOrientation)
    {Location teleportBlock = null;
       World world = leverBlock.getWorld();
       switch(leverBlockOrientation)
       {
           case NORTH:
               teleportBlock = new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
               teleportBlock.add(+2.5, 0, +4.5);
               teleportBlock.setYaw(-180);
               teleportBlock.setPitch(0);
               break;
           case SOUTH:
               teleportBlock = new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
               teleportBlock.add(-1.5, 0, -3.5);
               teleportBlock.setYaw(0);
               teleportBlock.setPitch(0);
               break;
           case EAST:
               teleportBlock = new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
               teleportBlock.add(-3.5, 0, +2.5);
               teleportBlock.setYaw(270);
               teleportBlock.setPitch(0);
               break;
           case WEST:
               teleportBlock = new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
               teleportBlock.add(+4.5, 0, -1.5);
               teleportBlock.setYaw(90);
               teleportBlock.setPitch(0);
               break;

       }
        return teleportBlock;
    }
    public static Location calcGateSignLocation(Location leverBlock, BlockFace orientation)
    {
        World world = leverBlock.getWorld();
        Location signLocation = null;
        switch(orientation)
        {
            case NORTH:
                signLocation =  new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
                signLocation.add(5, 1, 3);
                break;
            case SOUTH:
                signLocation =  new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
                signLocation.add(-5, 1, -3);
                break;
            case EAST:
                signLocation =  new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
                signLocation.add(-3, 1, 5);
                break;
            case WEST:
                signLocation =  new Location(world, leverBlock.getX(), leverBlock.getY(), leverBlock.getZ());
                signLocation.add(3, 1, -5);
                break;

        }
        return signLocation;
    }
    public static Location[] calcIrisBlocks(Location leverBlock, BlockFace orientation)
    {
                World world = leverBlock.getWorld();
                Location[] irisBlocks = new Location[7];
                switch(orientation)
                {
                    case NORTH:
                        irisBlocks[0] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY(), leverBlock.getBlockZ()+4);
                        irisBlocks[1] = new Location(world, leverBlock.getBlockX()+5, leverBlock.getBlockY()+2, leverBlock.getBlockZ()+4);
                        irisBlocks[2] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY()+4, leverBlock.getBlockZ()+4);
                        irisBlocks[3] = new Location(world, leverBlock.getBlockX()+2, leverBlock.getBlockY()+5, leverBlock.getBlockZ()+4);
                        irisBlocks[4] = new Location(world, leverBlock.getBlockX(), leverBlock.getBlockY()+4, leverBlock.getBlockZ()+4);
                        irisBlocks[5] = new Location(world, leverBlock.getBlockX()-1, leverBlock.getBlockY()+2, leverBlock.getBlockZ()+4);
                        irisBlocks[6] = new Location(world, leverBlock.getBlockX(), leverBlock.getBlockY(), leverBlock.getBlockZ()+4);
                        break;
                    case SOUTH:
                        irisBlocks[0] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY(), leverBlock.getBlockZ()-4);
                        irisBlocks[1] = new Location(world, leverBlock.getBlockX()-5, leverBlock.getBlockY()+2, leverBlock.getBlockZ()-4);
                        irisBlocks[2] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY()+4, leverBlock.getBlockZ()-4);
                        irisBlocks[3] = new Location(world, leverBlock.getBlockX()-2, leverBlock.getBlockY()+5, leverBlock.getBlockZ()-4);
                        irisBlocks[4] = new Location(world, leverBlock.getBlockX(), leverBlock.getBlockY()+4, leverBlock.getBlockZ()-4);
                        irisBlocks[5] = new Location(world, leverBlock.getBlockX()+1, leverBlock.getBlockY()+2, leverBlock.getBlockZ()-4);
                        irisBlocks[6] = new Location(world, leverBlock.getBlockX(), leverBlock.getBlockY(), leverBlock.getBlockZ()-4);
                        break;
                    case EAST:
                        irisBlocks[0] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY(), leverBlock.getBlockZ()+4);
                        irisBlocks[1] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY()+2, leverBlock.getBlockZ()+5);
                        irisBlocks[2] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY()+4, leverBlock.getBlockZ()+4);
                        irisBlocks[3] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY()+5, leverBlock.getBlockZ()+2);
                        irisBlocks[4] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY()+4, leverBlock.getBlockZ());
                        irisBlocks[5] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY()+2, leverBlock.getBlockZ()-1);
                        irisBlocks[6] = new Location(world, leverBlock.getBlockX()-4, leverBlock.getBlockY(), leverBlock.getBlockZ());

                        break;
                    case WEST:
                        irisBlocks[0] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY(), leverBlock.getBlockZ()-4);
                        irisBlocks[1] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY()+2, leverBlock.getBlockZ()-5);
                        irisBlocks[2] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY()+4, leverBlock.getBlockZ()-4);
                        irisBlocks[3] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY()+5, leverBlock.getBlockZ()-2);
                        irisBlocks[4] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY()+4, leverBlock.getBlockZ());
                        irisBlocks[5] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY()+2, leverBlock.getBlockZ()+1);
                        irisBlocks[6] = new Location(world, leverBlock.getBlockX()+4, leverBlock.getBlockY(), leverBlock.getBlockZ());
                        break;
                }
                return irisBlocks;
            }
    public static Location[] calcPortalBlocks(Location leverBlock, BlockFace orientation)
    {
        World world = leverBlock.getWorld();
        Location[] portalBlocks = new Location[21];
        ArrayList<int[]> cornerBlocks = new ArrayList<>();
        int cornerBlock1[] = {0, 0};
        int cornerBlock2[] = {0, 4};
        int cornerBlock3[] = {4, 0};
        int cornerBlock4[] = {4, 4};
        Location originBlock;
        int index = 0;

        Boolean isValidPortalBlock = true;
        cornerBlocks.add(cornerBlock1);
        cornerBlocks.add(cornerBlock2);
        cornerBlocks.add(cornerBlock3);
        cornerBlocks.add(cornerBlock4);

        switch (orientation)
        {
            case NORTH:
                originBlock = new Location(world, leverBlock.getBlockX() + 4, leverBlock.getBlockY(), leverBlock.getBlockZ() + 4);
                for (int y = 0; y < 5; y++)
                {
                    for (int x = 0; x < 5; x++)
                    {
                        isValidPortalBlock = true;
                        int testForCornerBlock[] = {x, y};
                        for(int z = 0; z<cornerBlocks.size(); z++)
                        {
                            int[] cornerBlock = cornerBlocks.get(z);
                            if(cornerBlock[0] == testForCornerBlock[0] && cornerBlock[1] == testForCornerBlock[1])
                            {
                                isValidPortalBlock = false;
                            }

                        }
                            if(isValidPortalBlock)
                            {
                                portalBlocks[index] = new Location(world, originBlock.getBlockX() - x, originBlock.getBlockY() + y, originBlock.getBlockZ());
                                index += 1;
                            }

                    }
                }
                break;
            case SOUTH:
               originBlock = new Location(world, leverBlock.getBlockX() - 4, leverBlock.getBlockY(), leverBlock.getBlockZ() -4);
                for (int y = 0; y < 5; y++)
                {
                    for (int x = 0; x < 5; x++)
                    {
                        isValidPortalBlock = true;
                        int testForCornerBlock[] = {x, y};
                        for(int z = 0; z<cornerBlocks.size(); z++)
                        {
                            int[] cornerBlock = cornerBlocks.get(z);
                            if(cornerBlock[0] == testForCornerBlock[0] && cornerBlock[1] == testForCornerBlock[1])
                            {
                                isValidPortalBlock = false;
                            }

                        }
                        if(isValidPortalBlock)
                        {
                            portalBlocks[index] = new Location(world, originBlock.getBlockX() + x, originBlock.getBlockY() + y, originBlock.getBlockZ());
                            index += 1;
                        }

                    }
                }

                break;

            case EAST:
                originBlock = new Location(world, leverBlock.getBlockX() - 4, leverBlock.getBlockY(), leverBlock.getBlockZ() +4);
                for (int y = 0; y < 5; y++)
                {
                    for (int z = 0; z < 5; z++)
                    {
                        isValidPortalBlock = true;
                        int testForCornerBlock[] = {z, y};
                        for(int x = 0; x<cornerBlocks.size();  x++)
                        {
                            int[] cornerBlock = cornerBlocks.get(x);
                            if(cornerBlock[0] == testForCornerBlock[0] && cornerBlock[1] == testForCornerBlock[1])
                            {
                                isValidPortalBlock = false;
                            }

                        }
                        if(isValidPortalBlock)
                        {
                            portalBlocks[index] = new Location(world, originBlock.getBlockX(), originBlock.getBlockY() + y, originBlock.getBlockZ()-z);
                            index += 1;
                        }

                    }
                }

                break;
            case WEST:
                originBlock = new Location(world, leverBlock.getBlockX() + 4, leverBlock.getBlockY(), leverBlock.getBlockZ() -4);
                for (int y = 0; y < 5; y++)
                {
                    for (int z = 0; z < 5; z++)
                    {
                        isValidPortalBlock = true;
                        int testForCornerBlock[] = {z, y};
                        for(int x = 0; x<cornerBlocks.size(); x++)
                        {
                            int[] cornerBlock = cornerBlocks.get(x);
                            if(cornerBlock[0] == testForCornerBlock[0] && cornerBlock[1] == testForCornerBlock[1])
                            {
                                isValidPortalBlock = false;
                            }

                        }
                        if(isValidPortalBlock)
                        {
                            portalBlocks[index] = new Location(world, originBlock.getBlockX() , originBlock.getBlockY() + y, originBlock.getBlockZ()+z);
                            index += 1;
                        }

                    }
                }
                break;
        }
        //if(portalBlocks == null){}
        return portalBlocks;
    }
    public static void promptDial(Player player, Stargate stargate)
    {
        activationUtil.activateGate(stargate, player);
        player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "/dial [gatename]");
    }
    public static void promptNxComplete(Player player, Block leverblock)
    {
        Plugin plugin = NovyXtreme.getPlugin(NovyXtreme.class);
        int stargateCost = plugin.getConfig().getInt("StargateCost");
        player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "/nxcomplete [gatename]");
        if(stargateCost>0){player.sendMessage(ChatColor.RED + "[WARNING]" + ChatColor.GRAY + ": " + stargateCost + "p Will be deducted from your account upon gate completion");}
        activationUtil.nxcompleteStart(leverblock,player);
    }
    public static void forceBlockUpdate(Location location)
    {
        Block block = location.getBlock();
        BlockState state = block.getState();
        state.update(true, true);
    }
    public static Stargate getClosestGate(Location locationToCheck){
        double closestGateDistance = 10000000;
        Stargate closestStargate = null;
        for (Stargate stargate : dbFunctions.getAllStargates()) {
            if (locationToCheck.distance(stargate.getTpCoordinates()) < closestGateDistance) {
                closestGateDistance = locationToCheck.distance(stargate.getTpCoordinates());
                closestStargate = stargate;
            }
        }
        return closestStargate;
    }
}

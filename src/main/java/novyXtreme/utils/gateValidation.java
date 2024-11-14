package novyXtreme.utils;

import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

// TODO Instead of 4 Structure arrays, have one which uses placeholders defined outside the structure definition Ie. "IrisBlock", "PortalBlock", "AnyBlock","GateBlock"
// TODO I'd like to define lever location within the gatestucture, this may require changing the data structure
/*
Example of configurable gate structure
3d char array from config
KEY:
- O = Obsidian portal blocks
- C = Chevron blocks TODO need to rename IRIS to Chevron
- P = Portal blocks
- L = Dial Lever Block
- D = Dial pedestal blocks
- A = Any blocks
- W = Whoosh blocks
how an old school novylen minigate would look in the config file (Each newline is a new layer going from back forwards, declaration enclosed with <[structurename]></>):
<Minigate>
[A][A][C][A][A]
[A][O][P][O][A]
[A][C][P][C][A]
[C][O][P][O][C]

[A][A][A][A][A]
[A][A][A][A][A]
[A][A][A][L][A]
[A][A][A][A][A]
</>
// get leverblock facing direction. set /Y as +1/-1 +1/+1 -1/+1 -1/-1 depending on direction
// get leverblock laver height 0 = first etc..
leverblocklayer = 1
// start at layer 0
currentLayer = 0
layerWidth = length(gatestructure[0][0][])
layerHeight = length(gatestructure[0][][]
//calculate top left of lowestlayer (originBlock)
//currentBlockLocation = getOriginBlockLocationFromLever();
//nested for loop, height then rows
for (int z; z<layercount;z++)
    for (int height;height<layerHeight;x++)
        for(int y?; y<layerWidth;y++)
            if(!getBlockFromKey(gatestructure[currentLayer][height][y]) == currentBlockLocation)
                //fail gate validation
            else continue
//successful gate validation


for (
if(





 */

public class gateValidation
{
    public static final Material[][] gateStructure =
            {
                    {Material.CAVE_AIR, Material.CAVE_AIR, Material.OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, Material.CAVE_AIR, Material.CAVE_AIR},
                    {Material.CAVE_AIR, Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN, Material.CAVE_AIR},
                    {Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN},
                    {Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN},
                    {Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN},
                    {Material.CAVE_AIR, Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN, Material.CAVE_AIR},
                    {Material.CAVE_AIR, Material.CAVE_AIR, Material.OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, Material.CAVE_AIR, Material.CAVE_AIR}
            };
    public static final Material[][] activeGateStructure =
        {
                {Material.CAVE_AIR, Material.CAVE_AIR, Material.OBSIDIAN, Material.GLOWSTONE, Material.OBSIDIAN, Material.CAVE_AIR, Material.CAVE_AIR},
                {Material.CAVE_AIR, Material.GLOWSTONE, Material.AIR, Material.AIR, Material.AIR, Material.GLOWSTONE, Material.CAVE_AIR},
                {Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN},
                {Material.GLOWSTONE, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.GLOWSTONE},
                {Material.OBSIDIAN, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.AIR, Material.OBSIDIAN},
                {Material.CAVE_AIR, Material.GLOWSTONE, Material.AIR, Material.AIR, Material.AIR, Material.GLOWSTONE, Material.CAVE_AIR},
                {Material.CAVE_AIR, Material.CAVE_AIR, Material.OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, Material.CAVE_AIR, Material.CAVE_AIR}
        };
    public static final Material[][] activePortalGateStructure =
            {
                    {Material.CAVE_AIR, Material.CAVE_AIR, Material.OBSIDIAN, Material.GLOWSTONE, Material.OBSIDIAN, Material.CAVE_AIR, Material.CAVE_AIR},
                    {Material.CAVE_AIR, Material.GLOWSTONE, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.GLOWSTONE, Material.CAVE_AIR},
                    {Material.OBSIDIAN, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.AIR, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.OBSIDIAN},
                    {Material.GLOWSTONE, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.GLOWSTONE},
                    {Material.OBSIDIAN, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.OBSIDIAN},
                    {Material.CAVE_AIR, Material.GLOWSTONE, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.NETHER_PORTAL, Material.GLOWSTONE, Material.CAVE_AIR},
                    {Material.CAVE_AIR, Material.CAVE_AIR, Material.OBSIDIAN, Material.OBSIDIAN, Material.OBSIDIAN, Material.CAVE_AIR, Material.CAVE_AIR}
            };

    public static final Material[] allowedInteriorBlocks = {Material.VINE, Material.CAVE_VINES, Material.NETHER_PORTAL};
//TODO allow this list of blocks which are allowed in the "air" slots; vines etc

    public static Material[][] buildTestGate(Location leverBlock, BlockFace orientation) {
        int xModifier = 1;
        int zModifier = 1;

        // Checks if the leverBlock is part of a valid stargate configuration as specified in the 2D array AND is placed at the correct offset
        Material[][] portalAttempt = new Material[7][7];
        Location testLocation;
        World world = leverBlock.getWorld();

        if (orientation.equals(BlockFace.NORTH) || orientation.equals(BlockFace.SOUTH)) {
            if (orientation.equals(BlockFace.NORTH)) {
                testLocation = new Location(world, leverBlock.getX() + 5, leverBlock.getY() - 1, leverBlock.getZ() + 4);
                xModifier = -1;
            } else {
                testLocation = new Location(world, leverBlock.getX() - 5, leverBlock.getY() - 1, leverBlock.getZ() - 4);
                zModifier = -1;
            }
            for (int i = 0; i < gateStructure.length; i++) {
                for (int j = 0; j < gateStructure[0].length; j++) {

                    Block block = world.getBlockAt(testLocation.getBlockX() + j * xModifier,
                            testLocation.getBlockY() + i, testLocation.getBlockZ());
                    portalAttempt[i][j] = block.getType();

                    // String debugMessage = String.format("Block at (%d, %d, %d): %s, Direction: %s",
                            //block.getX(), block.getY(), block.getZ(), block.getType().toString(), orientation.toString());
                    //System.out.println(debugMessage);

                    if (portalAttempt[i][j].equals(Material.CAVE_AIR)) {
                        portalAttempt[i][j] = Material.AIR;
                    }
                }
            }

        } else {
            if (orientation.equals(BlockFace.EAST)) {
                testLocation = new Location(world, leverBlock.getX() - 4, leverBlock.getY() - 1, leverBlock.getZ() + 5);
                zModifier = -1;
            } else {
                testLocation = new Location(world, leverBlock.getX() + 4, leverBlock.getY() - 1, leverBlock.getZ() - 5);
            }

            for (int i = 0; i < gateStructure.length; i++) {
                for (int j = 0; j < gateStructure[0].length; j++) {

                    Block block = world.getBlockAt(testLocation.getBlockX(),
                            testLocation.getBlockY() + i, testLocation.getBlockZ() + j * zModifier);
                    portalAttempt[i][j] = block.getType();

                    //String debugMessage = String.format("Block at (%d, %d, %d): %s, Direction: %s",
                           // block.getX(), block.getY(), block.getZ(), block.getType().toString(), orientation.toString());
                    //System.out.println(debugMessage);

                    if (portalAttempt[i][j].equals(Material.CAVE_AIR)) {
                        portalAttempt[i][j] = Material.AIR;
                    }
                }
            }
        }
        return portalAttempt;
    }





    public static boolean checkTestStargate(Material testGate[][]) //add type in future for custom gates
    {

        boolean isValid = true;
        testingLoop:

        for(int y = 0; y<testGate.length; y++)
        {
            for(int x = 0; x < testGate[y].length; x++)
            {
                if(!gateStructure[y][x].equals(testGate[y][x]))
                {
                    //cave air is used as a placeholder, it accepts any block in this position.
                    //allow nether portal due to bug where half the stargate deactivates


                    if(!gateStructure[y][x].equals(Material.CAVE_AIR))
                    {
                        if(!testGate[y][x].equals(Material.NETHER_PORTAL))
                        {
                            isValid = false;
                            break testingLoop;
                        }

                    }
                   /* if(gateStructure[y][x].equals(Material.AIR)
                    {
                        //TODO check if block is in allowed list
                        for(Material testMaterial : allowedInteriorBlocks)
                        {
                            if(testMaterial == testGate[y][x])
                            {

                            }
                        }
                    }*/

                }
            }
        }
        return isValid;
    }


    public static boolean checkPedestal(Block leverBlock, BlockFace orientation)
    {
        World world = leverBlock.getWorld();
        switch(orientation)
        {
            case NORTH:
                if(world.getBlockAt(leverBlock.getLocation().add(0,0,1)).getType().equals(Material.OBSIDIAN) && world.getBlockAt(leverBlock.getLocation().add(0,-1,1)).getType().equals(Material.OBSIDIAN))
                {
                    return true;
                }else

                break;
            case SOUTH:
                if(world.getBlockAt(leverBlock.getLocation().add(0,0,-1)).getType().equals(Material.OBSIDIAN) && world.getBlockAt(leverBlock.getLocation().add(0,-1,-1)).getType().equals(Material.OBSIDIAN))
                {
                    return true;
                }else

                break;
            case EAST:
                if(world.getBlockAt(leverBlock.getLocation().add(-1,0,0)).getType().equals(Material.OBSIDIAN) && world.getBlockAt(leverBlock.getLocation().add(-1,-1,0)).getType().equals(Material.OBSIDIAN))
                {
                    return true;
                }else{}

                break;
            case WEST:
                if(world.getBlockAt(leverBlock.getLocation().add(1,0,0)).getType().equals(Material.OBSIDIAN) && world.getBlockAt(leverBlock.getLocation().add(1,-1,0)).getType().equals(Material.OBSIDIAN))
                {
                    return true;
                } else
                break;
        }
            return false;
    }
    public static boolean checkLockPedestal(){return true;} //TODO add code to check if lever is placed below existing stargate leverblock
}



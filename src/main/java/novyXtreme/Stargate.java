package novyXtreme;
import novyXtreme.utils.activationUtil;
import novyXtreme.utils.dbFunctions;
import novyXtreme.utils.gateValidation;
import novyXtreme.utils.stargateUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;

public class Stargate
{
   private String name;
   private String owner;
   private Location leverBlock;
   private BlockFace facing;
   private Location tpCoordinates;
   private transient boolean isActive;
   private Location portalBlocks[];
   private Location signBlockLocation;
   private Location irisBlocks[];
   private transient Stargate destinationGate;
   private int timesVisited;
   private String activatedby;

    public Stargate(String name, String owner, Location leverBlock, BlockFace facing) {
        this.name = name;
        this.owner = owner;
        this.leverBlock = leverBlock;
        this.facing = facing;
        this.tpCoordinates = stargateUtils.calcTeleportBlock(this);
        this.signBlockLocation = stargateUtils.calcGateSignLocation(leverBlock, facing);
        this.irisBlocks = stargateUtils.calcIrisBlocks(leverBlock, facing);
        this.portalBlocks = stargateUtils.calcPortalBlocks(leverBlock, facing);
        createGateSign();
        dbFunctions.addGateToList(this);
    }
    public Stargate(){}
    public String getActivatedby(){return activatedby;}
    public Location getLeverBlock()
    {
        return leverBlock;
    }
    public BlockFace getGateOrientation(){return facing;}
    public String getName(){return name;}
    public boolean isActive() {return isActive;}
    public Location getTpCoordinates() {return tpCoordinates;}
    public Location[] getPortalBlocks() {return portalBlocks;}
    public String getOwner() {return owner;}
    public void setDestinationGate(Stargate destinationGate) {this.destinationGate = destinationGate;}
    public Stargate getDestinationGate() {
        return destinationGate;
    }
    public void setName(String name) {this.name = name;}
    public void setOwner(String owner) {this.owner = owner;}
    public void setLeverBlock(Location leverBlock) {this.leverBlock = leverBlock;}
    public BlockFace getFacing() {return facing;}
    public void setFacing(BlockFace facing) {this.facing = facing;}
    public void setFacingString(String facing){this.facing = BlockFace.valueOf(facing);}
    public void setTpCoordinates(Location tpCoordinates)
    {this.tpCoordinates = tpCoordinates;}
    public void setPortalBlocks(Location[] portalBlocks){this.portalBlocks = portalBlocks;}
    public Location getSignBlockLocation(){return signBlockLocation;}
    public void setSignBlockLocation(Location signBlockLocation){this.signBlockLocation = signBlockLocation;}
    public Location[] getIrisBlocks(){return irisBlocks;}
    public void setIrisBlocks(Location[] irisBlocks){this.irisBlocks = irisBlocks;}
    public int getTimesVisited(){return timesVisited;}
    public void setTimesVisited(int timesVisited){this.timesVisited = timesVisited;}
    public void setActivatedby(String playername){this.activatedby= playername;}
    public void createGateSign()
    {
       Block signBlock = signBlockLocation.getBlock();
        signBlock.setType(Material.OAK_WALL_SIGN);
        Sign sign= (Sign) signBlock.getState();
        Directional signdata = (Directional) sign.getBlockData();
        signdata.setFacing(facing);
        sign.setBlockData(signdata);
        //Directional leverBlockData = (Directional) leverBlock.getBlockData();
        sign.setLine(1,"-" + name + "-");
        sign.setLine(2,"O:" + owner);
        sign.update(true, true);
    }
    public boolean setActive(boolean active)
    {
        //original
                if(active)
                {
                    for(Location irisBlockLocation : irisBlocks)
                    {
                        if(!irisBlockLocation.getBlock().getType().equals(Material.OBSIDIAN)){return false;}
                        irisBlockLocation.getBlock().setType(Material.GLOWSTONE);
                    }
                    dbFunctions.activeStargates.add(this);
                    if(this.destinationGate !=null)
                    {
                        dbFunctions.activeStargates.add(this.destinationGate);
                    }
                } else
                    {
                        for(Location irisBlockLocation : irisBlocks)
                        {
                            if(irisBlockLocation.getBlock().getType().equals(Material.GLOWSTONE))
                            {
                                irisBlockLocation.getBlock().setType(Material.OBSIDIAN);
                            }

                        }
                       // stargateUtils.forceBlockUpdate(getPortalBlocks()[0]);
                        this.setActivatedby(null);
                        dbFunctions.activeStargates.remove(this);
                        dbFunctions.activeStargates.remove(this.destinationGate);

                        if(this.getDestinationGate() != null)
                        {
                            this.getDestinationGate().setActive(false);
                            this.setDestinationGate(null);
                        }
                        if(!gateValidation.checkTestStargate(gateValidation.buildTestGate(this.getLeverBlock(), this.getGateOrientation()))){return false;}
                    }

        isActive = active;
        //activate/deactivate  iris blocks
        return true;
    }
    public void setPortal(boolean active, Stargate destinationGate)
    {
        World world = this.getLeverBlock().getWorld();
       //world.spawnParticle(Particle.WATER_DROP, this.getTpCoordinates().add(0,1,0), 600,1,1,1 );
        this.destinationGate = destinationGate;
        activationUtil.activatePortal(this);
        activationUtil.activatePortal(destinationGate);
    }

}

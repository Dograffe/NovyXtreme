package novyXtreme.Listeners;

import novyXtreme.utils.stargateUtils;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import novyXtreme.utils.gateValidation;
import org.bukkit.block.Block;
import novyXtreme.utils.dbFunctions;

public class gateLeverListener implements Listener
{
    @EventHandler
    public void onLeverPull(PlayerInteractEvent e)
    {
        Block leverBlock = e.getClickedBlock();
        if(e.getClickedBlock() == null){return;}
        if(e.getClickedBlock().getType() == Material.LEVER)
        {
            Directional leverBlockData = (Directional) leverBlock.getBlockData();
            if(gateValidation.checkPedestal(leverBlock, leverBlockData.getFacing()))
            {
                //checks is pedestal is correct
                if(!dbFunctions.isGateHere(e.getPlayer(), leverBlockData.getFacing(), leverBlock.getLocation()))
                {
                    //checks for existing gate at location
                    //prompts dial if there is
                   if(gateValidation.checkTestStargate(gateValidation.buildTestGate(leverBlock.getLocation(), leverBlockData.getFacing())))
                    {
                        //prompts user with /nxcomplete [gatename]
                        //adds nxactive metadata to player
                        stargateUtils.promptNxComplete(e.getPlayer(), leverBlock);
                    }
                }
            }

        }
        return;


    }
}

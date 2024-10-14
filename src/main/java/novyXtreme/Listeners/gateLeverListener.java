package novyXtreme.Listeners;

import novyXtreme.Stargate;
import novyXtreme.utils.activationUtil;
import novyXtreme.utils.stargateUtils;
import org.bukkit.ChatColor;
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

            //checks is pedestal is correct
            if(gateValidation.checkPedestal(leverBlock, leverBlockData.getFacing()))
            {
                String checkedGate = dbFunctions.isGateHere(e.getPlayer(), leverBlockData.getFacing(), leverBlock.getLocation());
                if(checkedGate == null)
                {
                    if(gateValidation.checkTestStargate(gateValidation.buildTestGate(leverBlock.getLocation(), leverBlockData.getFacing())))
                    {
                        //prompts user with /nxcomplete [gatename] if validation passes
                        //adds nxactive metadata to player
                        stargateUtils.promptNxComplete(e.getPlayer(), leverBlock);
                        return;
                    }
                    return;
                } //return if no gate found at location and no valid structure


                Stargate stargate = dbFunctions.getGatebyName(checkedGate);
                if(stargate.isActive())
                {
                    // if stargate is active, deactivate it and inform player
                    activationUtil.deactivateGate(stargate, e.getPlayer());
                    e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate Deactivated!");
                    return;
                }

                if(!gateValidation.checkTestStargate(gateValidation.buildTestGate(stargate.getLeverBlock(), stargate.getGateOrientation())))
                {
                    e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "This stargate does not have a valid structure.. please re-construct and activate");
                    dbFunctions.removeGateByName(stargate.getName());
                    return;
                }
                if(dbFunctions.getActivatedGate(e.getPlayer().getName()) != null)
                {
                    e.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You may only have one active stargate at a time!");
                    return;
                }
                stargateUtils.promptDial(e.getPlayer(), stargate);
                return;
            }

        }
        return;


    }
}

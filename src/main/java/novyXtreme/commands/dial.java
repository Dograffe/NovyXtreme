package novyXtreme.commands;


import novyXtreme.Stargate;
import novyXtreme.utils.gateValidation;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import novyXtreme.utils.dbFunctions;

public class dial implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
        {
            Stargate entranceGate = null;
            if (sender instanceof Player)
            {
                Player player = (Player) sender;

                //checks if player already has an active gate.
                entranceGate = dbFunctions.getActivatedGate(sender.getName());
                if(entranceGate != null)
                {
                    if(args.length != 1){sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Must specify stargate name!"); return true;}
                    if(entranceGate.getDestinationGate() != null){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You already have an active portal!");return true;}
                    Stargate destinationStargate = dbFunctions.getGatebyName(args[0]);
                    if(destinationStargate == entranceGate){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Destination gate cannot be origin gate"); return true;}
                    if(destinationStargate == null){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "No gate by that name found"); return true;}
                    if(destinationStargate.isActive()){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "That gate is currently in use!"); return true;}
                    if(!gateValidation.checkTestStargate(gateValidation.buildTestGate(destinationStargate.getLeverBlock(), destinationStargate.getGateOrientation())))
                    {
                        sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Destination gate no longer exists");
                        dbFunctions.removeGateByName(destinationStargate.getName());
                        return true;
                    }
                    //TODO breaking a stargate after activating it then /dialing will not destroy gate allowing for floating portals


                  /* if(!gateValidation.checkActiveTestStargate(gateValidation.buildTestGate(entranceGate.getLeverBlock(), entranceGate.getGateOrientation())))
                    {
                        sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate no longer exists");
                        dbFunctions.removeGateByName(entranceGate.getName());
                        return true;
                    }*/
                    destinationStargate.setActivatedby(sender.getName());
                    entranceGate.setPortal(true, destinationStargate);

                    player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Portal Connected!");
                    destinationStargate.setTimesVisited(destinationStargate.getTimesVisited()+1);

                } else
                {
                    sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You have not activated a stargate!");
                }

            }
            return true;
        }
}

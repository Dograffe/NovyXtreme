package novyXtreme.commands;

import novyXtreme.Stargate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import novyXtreme.utils.dbFunctions;
import org.bukkit.metadata.MetadataValue;
import java.util.List;

public class dial implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
        {
            Stargate entranceGate = null;
            if (sender instanceof Player)
            {
                Player player = (Player) sender;
                if((((Player) sender).hasMetadata("StargateActive")))
                {
                    if(args.length != 1){sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Must specify stargate name!"); return true;}
                    List< MetadataValue > metadata = player.getMetadata("StargateActive");
                    for(MetadataValue val : metadata)
                    {
                         entranceGate = dbFunctions.getGatebyName(val.value().toString());
                    }

                    Stargate destinationStargate = dbFunctions.getGatebyName(args[0]);
                    if(destinationStargate == entranceGate){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Destination gate cannot be origin gate"); return true;}
                    if(entranceGate == null){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "No gate by that name found"); return true;}
                    if(destinationStargate == null){player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "No gate by that name found"); return true;}
                    entranceGate.setPortal(true, destinationStargate);



                    player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Portal Connected!");

                } else
                {
                    sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You have not activated a stargate!");;
                }

            }
            return true;
        }
}

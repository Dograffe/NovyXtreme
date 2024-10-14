package novyXtreme.commands;

import novyXtreme.utils.dbFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class nxremove implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.hasPermission("novyxtreme.nxremoveany"))
        {
            if (args.length != 1)
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Must specify gatename!");
                return true;
            }
            if (dbFunctions.removeGateByName(args[0]))
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate: " + args[0] + " successfully removed.");
                return true;
            } else
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "No gate by that name found!");
            }
            return true;
        }else
        if (sender.hasPermission("novyxtreme.nxremoveown"))
        {
            if(!dbFunctions.getGatebyName(args[0]).getOwner().equals(sender.getName()))
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You do not have permission to remove a gate which you do not own!");
                return true;
            }
            if (args.length != 1)
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Must specify gatename!");
                return true;
            }
            if (dbFunctions.removeGateByName(args[0]))
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate: " + args[0] + " successfully removed.");
                return true;
            } else
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "No gate by that name found!");
            }
            return true;
        }
        else{sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You do not have permission to use that command!");}

        return true;
    }
}

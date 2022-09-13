package novyXtreme.commands;

import novyXtreme.utils.dbFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class nxlist  implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.hasPermission("novyXtreme.nxlistall"))
        {
            sender.sendMessage(dbFunctions.getStargateListToString());
            //dbFunctions.getStargateListToString();
            return true;
        }else
        if(sender.hasPermission("novyXtreme.nxlistown"))
        {
            sender.sendMessage(dbFunctions.getStargateListFromOwner(sender.getName()));
            //dbFunctions.getStargateListToString();
            return true;
        } else {sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You do not have permission to use that command!");}
        return true;
    }

}

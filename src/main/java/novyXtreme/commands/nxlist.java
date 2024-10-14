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
        if(sender.hasPermission("novyxtreme.nxlistall"))
        {
            if(args.length == 0) //list all stargates, non-verbose
                {
                    sender.sendMessage(dbFunctions.getStargateListToString());
                } else if(args.length == 1)
                {
                    if(args[0].contains("-v"))
                    {
                        sender.sendMessage(dbFunctions.getStargateListToStringVerbose(null));
                    } else
                    { sender.sendMessage(dbFunctions.getStargateListFromOwner(args[0]));}

                }else if(args.length >1)
                {
                    {
                        if(args[0].contains("-v"))
                        {
                            sender.sendMessage(dbFunctions.getStargateListToStringVerbose(args[0]));
                        } else if(args[1].contains("-v"))
                        {
                            sender.sendMessage(dbFunctions.getStargateListFromOwnerVerbose(args[0]));
                        }else {sender.sendMessage(dbFunctions.getStargateListFromOwner(args[0]));}
                    }
                }
            return true;
        }else
        if(sender.hasPermission("novyxtreme.nxlistown"))
        {
            if(args.length >=1 && args[0].contains("-v"))
            {
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + dbFunctions.getStargateListFromOwnerVerbose(sender.getName()));

            }else{sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + dbFunctions.getStargateListFromOwner(sender.getName()));}

            //dbFunctions.getStargateListToString();
            return true;
        } else {sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You do not have permission to use that command!");}
        return true;
    }

}

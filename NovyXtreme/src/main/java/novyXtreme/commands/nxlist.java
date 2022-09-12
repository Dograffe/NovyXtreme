package novyXtreme.commands;

import novyXtreme.utils.dbFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class nxlist  implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        sender.sendMessage(dbFunctions.getStargateListToString());
        dbFunctions.getStargateListToString();
        return true;
    }

}

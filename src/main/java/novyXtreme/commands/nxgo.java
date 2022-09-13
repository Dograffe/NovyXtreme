package novyXtreme.commands;

import novyXtreme.Stargate;
import novyXtreme.utils.dbFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class nxgo implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player) {
            if (sender.hasPermission("noxyXtreme.nxgo"))
            {
                try {
                    Stargate destinationStargate = dbFunctions.getGatebyName(args[0]);
                    ((Player) sender).teleport(destinationStargate.getTpCoordinates());

                } catch (NullPointerException e) {
                    sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "No gate by that name found!");
                }
            }else{
                sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You do not have permission to use that command!");
            }
        }
        return true;
    }
}

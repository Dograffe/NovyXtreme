/*
package novygate.commands;
import novygate.Stargate;
import novygate.utils.dbFunctions;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class nxnear implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        double closestGate = 0;
        Player player = (Player) sender;
        Location playerloc = player.getLocation();
        for (Stargate stargate : dbFunctions.getStargates())
        {
            if(playerloc.distance(stargate.getLeverBlock()) < closestGate)

            stargate.getLeverBlock();
        }
    }
}
*/
//commit test
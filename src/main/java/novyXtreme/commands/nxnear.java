
package novyXtreme.commands;
import novyXtreme.Stargate;
import novyXtreme.utils.dbFunctions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class nxnear implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        double closestGateDistance = 10000000;
        Stargate closestGateName = null;

        // Get player's current location
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();

        for (Stargate stargate : dbFunctions.getAllStargates()) {
            if (playerLocation.distance(stargate.getTpCoordinates()) < closestGateDistance) {
                closestGateName = stargate;
                closestGateDistance = playerLocation.distance(stargate.getTpCoordinates());
            }
        }
        sender.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "The closest stargate: " +"\n Name: "+ closestGateName.getName() + "\n Location: " + closestGateName.getTpCoordinates() + "\n Distance: "+ closestGateDistance + " blocks");
        return true;
    }
}
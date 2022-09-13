package novyXtreme.commands;

import novyXtreme.NovyXtreme;
import novyXtreme.Stargate;
import novyXtreme.utils.activationUtil;
import novyXtreme.utils.dbFunctions;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class nxcomplete implements CommandExecutor
{
    @Override
    public  boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
 if (sender instanceof Player)
        {
            Block leverblock = null;
            Player p = (Player) sender;
            String gateName = args[0];
            if(!p.hasMetadata("NxCompleteActive"))
            {
                p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]" + ChatColor.GRAY + "Stargate successfully created!");
                return true;
            }

            List<MetadataValue> metadata = p.getMetadata("NxCompleteActive");

            for(MetadataValue val : metadata)
            {

                if (NovyXtreme.getPlugin().equals(val.getOwningPlugin()))
                {
                    leverblock = (Block) val.value();
                }
            }
            String GateName = args[0];
            Directional leverBlockData = (Directional) leverblock.getBlockData();
            if(dbFunctions.getGatebyName(GateName) == null)
            {
                Stargate newStargate = new Stargate(GateName,p.getName(), leverblock.getLocation(), leverBlockData.getFacing());
                p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]" + ChatColor.GRAY + "Stargate successfully created!");
                activationUtil.nxcompleteEnd(p);
            } else {p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]" + ChatColor.GRAY + "There is already a gate by that name!");}



        }
        return true;
    }

}

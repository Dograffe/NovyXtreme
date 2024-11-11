package novyXtreme.commands;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
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
import org.bukkit.plugin.Plugin;

import java.util.List;

public class nxcomplete implements CommandExecutor {
    Plugin plugin = NovyXtreme.getPlugin(NovyXtreme.class);
    int stargateCost = plugin.getConfig().getInt("StargateCost");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Block leverblock = null;
            Player p = (Player) sender;
            //Player has not activated a stargate:
            if (!p.hasMetadata("NxCompleteActive")) {
                p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "You have not activated a stargate.");
                return true;
            }

            //Player has activated a stargate:
            List<MetadataValue> metadata = p.getMetadata("NxCompleteActive");

            for (MetadataValue val : metadata) {
                //get the metadata associated with NovyXtreme (NxCompleteActive) and it's associated block.
                if (NovyXtreme.getPlugin().equals(val.getOwningPlugin())) {
                    leverblock = (Block) val.value();
                }
            }
            String GateName = args[0];

            //Check gatename is alphanumeric (I'm not dumb enough to forget this and have some kid do a JSON injection and wipe the entire database right?.. RIGHT?)
            if (!GateName.matches("^[a-zA-Z0-9_-]+$")) {
                p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Gate name can only contain letters, numbers, hyphens, and underscores.");
                return true;
            }

            //Assign directional interface to leverblock for directional shenanigans
            Directional leverBlockData = (Directional) leverblock.getBlockData();

            //Stargate does not already exist
            if (dbFunctions.getGatebyName(GateName) == null) {
                Economy economy = NovyXtreme.getEconomy();
                EconomyResponse response = economy.withdrawPlayer(p, stargateCost);

                //Player transaction complete
                if (response.transactionSuccess()) {
                    p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate successfully created!");
                    Stargate newStargate = new Stargate(GateName, p.getName(), leverblock.getLocation(), leverBlockData.getFacing());
                    activationUtil.nxcompleteEnd(p);
                    if (stargateCost > 0) {
                        p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + stargateCost + "p was deducted from your account.");
                    }

                } else {
                    //Player doesn't have enough monies
                    p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Could not create stargate: You lack the required funds (" + stargateCost + "p)");
                    p.sendMessage(response.errorMessage);
                }
                return true;

            } else {
                //Gatename already exists
                p.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "There is already a gate by that name!");
            }


        }
        return true;
    }

}

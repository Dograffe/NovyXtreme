package novyXtreme.commands;


import novyXtreme.Stargate;
import novyXtreme.utils.gateValidation;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import novyXtreme.utils.dbFunctions;

public class nxtop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        int pagenumber = 1;
        if (sender instanceof Player){
            Player player = (Player) sender;
            if(args.length==0){
             player.sendMessage(dbFunctions.getTopGates(pagenumber));
            }

            else if(args.length>=1){
                try{
                    pagenumber = Integer.parseInt(args[0]);
                }catch(NumberFormatException e){pagenumber=1;}
                player.sendMessage(dbFunctions.getTopGates(pagenumber));
            }
        }

        return true;
    }

}
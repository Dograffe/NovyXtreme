package novyXtreme.utils;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import novyXtreme.NovyXtreme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import novyXtreme.Stargate;
import org.bukkit.metadata.MetadataValue;



public class dbFunctions
{
    private static ArrayList<Stargate> stargates = new ArrayList<Stargate>();
    public static ArrayList<Stargate> activeStargates = new ArrayList<>();
    public static boolean isGateHere(Player player, BlockFace orientation, Location leverBlock)
    {
        //This needs cleaning up; remove nested IF statements
        for(Stargate gate : stargates)
        {
            if(leverBlock.equals(gate.getLeverBlock()))
            {
                if(gate.isActive())
                {
                    if(player.hasMetadata("StargateActive"))
                    {
                        List<MetadataValue> metadata = player.getMetadata("StargateActive");
                        for(MetadataValue val : metadata)
                        {
                            if (gate.getName().equals(val.value()))
                            {
                                activationUtil.deactivateGate(gate, player);
                                player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargate Deactivated!");
                                return true;
                            }
                        }
                    }
                    player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "This Stargate has been activated by someone else!");
                    return true;
                }
                if(!gateValidation.checkTestStargate(gateValidation.buildTestGate(gate.getLeverBlock(), gate.getGateOrientation())))
                {
                    player.sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "This stargate does not have a valid structure.. please re-construct and activate");
                    stargates.remove(gate);
                    return true;
                }
                stargateUtils.promptDial(player, gate);
                return true;
            }
        }
        return false;
    }
    public static Stargate getGatebyName(String gateName)
    {
        Stargate foundGate = null;
        ArrayList<Stargate> gateList = stargates;
        for(Stargate stargate : gateList)
        {
            if(stargate.getName().contains(gateName))
            {
                foundGate = stargate;
                return foundGate;
            }
        }
        //catch error no gate found;
        return foundGate;
    }
    public static boolean addStargate(Stargate stargate)
    {
        //unused for now
        return true;
    }
    public static void loadStargates() throws IOException
    {
        File file = new File(NovyXtreme.getPlugin().getDataFolder().getAbsolutePath() + "/stargatesList.json");
        if(!file.exists()){Bukkit.broadcastMessage("File does not exist"); return;}
        if(stargates == null){Bukkit.broadcastMessage("Stargates is null"); return;}

        Reader reader = new FileReader(file);
        JsonReader jReader = new JsonReader(reader);
        stargates = stargateAdapter.read(jReader);
        jReader.close();
    }
    public static void saveStargates() throws IOException
    {
        final Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();

        File file = new File(NovyXtreme.getPlugin().getDataFolder().getAbsolutePath() + "/stargatesList.json");
        file.getParentFile().mkdir();
       file.createNewFile();
           if(!file.isFile()){Bukkit.broadcastMessage("File is null");}

        Writer writer = new FileWriter(file, false);
        JsonWriter jsonWriter = new JsonWriter(writer);
        gson.toJson(stargateAdapter.write(stargates, jsonWriter), jsonWriter);
         jsonWriter.close();
    }
    public static void addGateToList(Stargate stargate)
    {
        stargates.add(stargate);
    }
    public static String getStargateListToString()
    {
        String stargateListString = "Stargates:";
        for(Stargate stargate: stargates)
        {
            stargateListString = stargateListString + "\n" + "Name: " + stargate.getName() + " Owner: " + stargate.getOwner();
        }
        return stargateListString;
    }
    public static boolean removeGateByName(String gatename)
    {
        for(Stargate stargate : stargates)
        {

            if(stargate.getName().equals(gatename))
            {
                if(stargate.isActive()){stargate.setActive(false);}
                stargate.getSignBlockLocation().getBlock().setType(Material.AIR);
                stargates.remove(stargate);

                return true;
            }
        }
        return false;
    }
}

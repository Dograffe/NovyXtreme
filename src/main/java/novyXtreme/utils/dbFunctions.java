package novyXtreme.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import novyXtreme.NovyXtreme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import novyXtreme.Stargate;
import org.bukkit.plugin.Plugin;


public class dbFunctions {


    private static ArrayList<Stargate> stargates = new ArrayList<Stargate>();
    public static ArrayList<Stargate> activeStargates = new ArrayList<>();
    static int stargateCost = NovyXtreme.getPlugin(NovyXtreme.class).getConfig().getInt("StargateCost");

    public static ArrayList<Stargate> getAllStargates(){
        return stargates;
    }
    public static Stargate getActivatedGate(String playername) {
        for (Stargate gate : activeStargates) {
            if (playername.equals(gate.getActivatedby())) {
                return gate;
            }
        }
        return null;
    }

    public static String isGateHere(Player player, BlockFace orientation, Location leverBlock) {
        //This needs cleaning up; remove nested IF statements
        for (Stargate gate : stargates) {
            // Checks if clicked lever matches any gate in database, else return null
            if (leverBlock.equals(gate.getLeverBlock())) {
                // Checks if gate orientation matches lever orientation
                if (gate.getGateOrientation().toString() == orientation.toString()) {
                    //TODO Return gate.. name?
                    return gate.getName();
                }
            }
        }
        return null;
    }
    public static String getTopGates(Integer page)
    {
        // Set page to 1 if no page specified
        if(page == null){page=1;}
        int rank =1;

        // sets which rank page should start/end on
        int pageStart = (page*10)-9;
        int pageEnd = pageStart+10;
        Stargate currentGate;

        ArrayList<Stargate> sortedStargates = stargates;
        // Response Headers
        String topGatesList = ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY +"Top Stargates" + " (Rank | Name | Owner | Visited)";

            sortedStargates.sort(Comparator.comparingInt(Stargate::getTimesVisited).reversed());
            for(int i = pageStart;i<pageEnd;i++)
            {
                try {
                    currentGate = sortedStargates.get(i - 1);
                }catch(IndexOutOfBoundsException e){break;} //exit the loop when index is out of bounds

                topGatesList += "\n"+ChatColor.GREEN + "#" + i + ChatColor.GRAY + " | " + currentGate.getName() + " | " + currentGate.getOwner() + " | " + currentGate.getTimesVisited();
            }
            topGatesList += "\n"+"Page: "+page;


        return topGatesList;
    }

    public static Stargate getGatebyName(String gateName) {
        Stargate foundGate = null;
        ArrayList<Stargate> gateList = stargates;
        for (Stargate stargate : gateList) {
            if (stargate.getName().equals(gateName)) {
                foundGate = stargate;
                return foundGate;
            }
        }
        //TODO catch error no gate found;
        return foundGate;
    }

    public static void loadStargates() throws IOException {
        File file = new File(NovyXtreme.getPlugin().getDataFolder().getAbsolutePath() + "/stargatesList.json");
        if (!file.exists()) {
            Bukkit.broadcastMessage("File does not exist");
            return;
        }
        if (stargates == null) {
            Bukkit.broadcastMessage("Stargates is null");
            return;
        }

        Reader reader = new FileReader(file);
        JsonReader jReader = new JsonReader(reader);
        stargates = stargateAdapter.read(jReader);
        jReader.close();
    }

    // Saves stargate list to File
    public static void saveStargates() throws IOException {
        for (int i = 0; i < activeStargates.size(); i++) {
            activeStargates.get(i).setActive(false);
        }

        final Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();

        File file = new File(NovyXtreme.getPlugin().getDataFolder().getAbsolutePath() + "/stargatesList.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        if (!file.isFile()) {
            Bukkit.broadcastMessage("File is null");
        }

        Writer writer = new FileWriter(file, false);
        JsonWriter jsonWriter = new JsonWriter(writer);
        gson.toJson(stargateAdapter.write(stargates, jsonWriter), jsonWriter);
        jsonWriter.close();
    }

    public static void addGateToList(Stargate stargate) {
        stargates.add(stargate);
    }

    //TODO These should be combined into one function with optional parameters
    public static String getStargateListToStringVerbose(String ownerName) {
        String stargateListString = null;

        if (ownerName == null) {
            stargateListString = ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargates ( Name | Owner | Times Visited | TpCoords )" ;
            for (Stargate stargate : stargates) {
                stargateListString = stargateListString + "\n"+ChatColor.GRAY + stargate.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + stargate.getOwner() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + stargate.getTimesVisited() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "( " + stargate.getTpCoordinates().getBlockX() + "," + stargate.getTpCoordinates().getBlockY() + "," + stargate.getTpCoordinates().getBlockZ() + " )";
            }
        } else {
            stargateListString = ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargates Owned by " + ownerName + ":";
            for (Stargate stargate : stargates) {
                if (stargate.getOwner().equals(ownerName)) {
                    stargateListString = stargateListString + "\n"+ChatColor.GRAY + "Name: " + stargate.getName();
                }
            }
        }
        return stargateListString;
    }

    public static String getStargateListToString() {
        String stargateListString = ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargates ( Name | Owner )";
        for (Stargate stargate : stargates) {
            stargateListString = stargateListString + "\n" + ChatColor.GRAY + stargate.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + stargate.getOwner();
        }
        return stargateListString;
    }

    public static String getStargateListFromOwnerVerbose(String ownerName) {
        String stargateListString = ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargates Owned by " + ownerName + " ( Name | Times Visited | TpCoords )";
        for (Stargate stargate : stargates) {
            if (stargate.getOwner().equals(ownerName)) {
                stargateListString = stargateListString + "\n" + ChatColor.GRAY + stargate.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + stargate.getTimesVisited() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "( " + stargate.getTpCoordinates().getBlockX() + "," + stargate.getTpCoordinates().getBlockY() + "," + stargate.getTpCoordinates().getBlockZ() + " )";
            }
        }
        return stargateListString;
    }

    public static String getStargateListFromOwner(String ownerName) {
        String stargateListString = ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Stargates Owned by " + ownerName + ":";
        for (Stargate stargate : stargates) {
            if (stargate.getOwner().equals(ownerName)) {
                stargateListString = stargateListString + "\n" +ChatColor.GRAY + ChatColor.GRAY + stargate.getName();
            }
        }
        return stargateListString;
    }

    public static boolean removeGateByName(String gatename) {
        for (Stargate stargate : stargates) {
            EconomyResponse response;
            // Refund player when gate is destroyed/removed
            if (stargate.getName().equals(gatename)) {
                Economy economy = NovyXtreme.getEconomy();
                if (Bukkit.getPlayer(stargate.getOwner()) != null) {
                    response = economy.depositPlayer(Bukkit.getPlayer(stargate.getOwner()), stargateCost);
                } else {
                    response = economy.depositPlayer(Bukkit.getOfflinePlayer(stargate.getOwner()), stargateCost);
                }

                if (response.transactionSuccess()) {
                    if (stargate.isActive()) {
                        stargate.setActive(false);
                    }
                    // Break sign when stargate is destroyed/removed
                    stargate.getSignBlockLocation().getBlock().setType(Material.AIR);
                    if (Bukkit.getPlayer(stargate.getOwner()) != null) {
                        Bukkit.getPlayer(stargate.getOwner()).sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Gate: " + stargate.getName() + " destroyed, you received " + stargateCost + "p refund");
                    }

                    stargates.remove(stargate);

                } else {
                    Bukkit.getPlayer(stargate.getOwner()).sendMessage(ChatColor.DARK_PURPLE + "[NovyXTreme]: " + ChatColor.GRAY + "Transaction Error");
                }
                return true;
            }
        }
        return false;
    }
    // Disable all gates, used by /nxforce
    public static boolean deactivateAllGates() {
        Stargate activegate;
        int gates = activeStargates.size();
        for (int i = 0; i < gates; i++) {
            if (activeStargates.size() > 0) {
                activegate = activeStargates.get(0);
                activegate.setActive(false);
            }
        }
        return true;
    }

}

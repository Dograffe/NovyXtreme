package novyXtreme.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import novyXtreme.Stargate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class stargateAdapter
{
    final Type objectStringMapType = new TypeToken<Map<String, Object>>() {}.getType();


    public static JsonArray write(ArrayList<Stargate> stargates, JsonWriter writer)
    {

        JsonArray jsonarray = new JsonArray();
        for(Stargate stargate: stargates)
        {
            try{

            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
            JsonObject obj = new JsonObject();
            GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();
            Gson gson = builder.create();

            obj.addProperty("name", stargate.getName());
            if(stargate.getOwner().matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")){obj.addProperty("owner", stargate.getOwner());}
            else if(Bukkit.getPlayer(stargate.getOwner()) != null){obj.addProperty("owner", Bukkit.getPlayer(stargate.getOwner()).getUniqueId().toString());}
            else{obj.addProperty("owner", Bukkit.getOfflinePlayer(stargate.getOwner()).getUniqueId().toString());}

            obj.add("leverBlock", locToJson(stargate.getLeverBlock()));
            obj.addProperty("facing", stargate.getFacing().toString());
            obj.addProperty("timesVisited", stargate.getTimesVisited());
            String jstring = obj.toString();
            jsonarray.add(obj);
        }
        return jsonarray;

    }
    public static ArrayList<Stargate> read(JsonReader reader)
    {
        ArrayList<Stargate> stargates = new ArrayList<Stargate>();
        try
        {
            JsonArray jStargates = (JsonArray) JsonParser.parseReader(reader);
            JsonObject jObject;

            Iterator iterator = jStargates.iterator();
            while(iterator.hasNext())
            {
                jObject = (JsonObject)  iterator.next();
                Stargate stargate = new Stargate();
                stargate.setName(jObject.get("name").getAsString());
                String uuidString = jObject.get("owner").getAsString();


                try {
                    if (uuidString.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}"))
                    {
                        UUID uuid = UUID.fromString(uuidString);
                        if(Bukkit.getPlayer(uuid) != null){stargate.setOwner(Bukkit.getPlayer(uuid).getName());}
                        else if(Bukkit.getOfflinePlayer(uuid).getName() != null){stargate.setOwner(Bukkit.getOfflinePlayer(uuid).getName());}
                        else{stargate.setOwner(uuidString);}
                    } else
                    {
                        //name in database file does not match UUID format
                       stargate.setOwner(jObject.get("owner").getAsString());
                    }
                } catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                stargate.setLeverBlock(jsonToLoc(jObject.get("leverBlock").getAsJsonObject()));
                stargate.setFacingString(jObject.get("facing").getAsString());
                stargate.setTpCoordinates(stargateUtils.calcTeleportBlock(stargate));
                stargate.setPortalBlocks(stargateUtils.calcPortalBlocks(stargate.getLeverBlock(), stargate.getFacing()));
                stargate.setSignBlockLocation(stargateUtils.calcGateSignLocation(stargate.getLeverBlock(), stargate.getGateOrientation()));
                stargate.setIrisBlocks(stargateUtils.calcIrisBlocks(stargate.getLeverBlock(), stargate.getFacing()));
                stargate.setTimesVisited(jObject.get("timesVisited").getAsInt());
                stargates.add(stargate);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        };
        return stargates;
    }
    public static Location[] jsonArrayToLoc(JsonArray jArray)
    {
        Iterator iterator = jArray.iterator();
        Location locations[] = new Location[jArray.size()];
        int i = 0;
        while(iterator.hasNext())
        {
            JsonObject jObject = (JsonObject) iterator.next();
            locations[i] = jsonToLoc(jObject);
            i = i +1;
        }
        return locations;
    }
    public static Location jsonToLoc(JsonObject jObject)
    {
        World world =  Bukkit.getWorld(jObject.get("world").getAsString());
        double locx = jObject.get("x").getAsDouble();
        double locy = jObject.get("y").getAsDouble();
        double locz = jObject.get("z").getAsDouble();
        float yaw = jObject.get("yaw").getAsFloat();
        float pitch = jObject.get("pitch").getAsFloat();
        return new Location(world, locx, locy, locz, yaw, pitch);
    }


    public static JsonElement locToJson(Location location)
    {
        JsonElement jsonLocation = new JsonObject();
        jsonLocation.getAsJsonObject().addProperty("world", location.getWorld().getName());
        jsonLocation.getAsJsonObject().addProperty("x", location.getX());
        jsonLocation.getAsJsonObject().addProperty("y", location.getY());
        jsonLocation.getAsJsonObject().addProperty("z", location.getZ());
        jsonLocation.getAsJsonObject().addProperty("yaw", location.getYaw());
        jsonLocation.getAsJsonObject().addProperty("pitch", location.getPitch());
        return jsonLocation;
    }

    public static JsonArray locToJsonArray(Location[] locations)
    {
        JsonArray jsonarray = new JsonArray();
        for (Location location : locations)
        {
            jsonarray.add(locToJson(location));
        }
        return jsonarray;
    }









}



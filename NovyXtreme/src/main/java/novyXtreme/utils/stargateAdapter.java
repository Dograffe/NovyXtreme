package novyXtreme.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import novyXtreme.Stargate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class stargateAdapter
{
    final Type objectStringMapType = new TypeToken<Map<String, Object>>() {}.getType();


    public static JsonArray write(ArrayList<Stargate> stargates, JsonWriter writer)
    {

        JsonArray jsonarray = new JsonArray();
        for(Stargate stargate: stargates)
        {
            JsonObject obj = new JsonObject();
            GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();
            Gson gson = builder.create();

            obj.addProperty("name", stargate.getName());
            obj.addProperty("owner", stargate.getOwner());
            obj.add("leverBlock", locToJson(stargate.getLeverBlock()));
            //obj.addProperty("leverBlock", gson.toJson(locToJson(stargate.getLeverBlock())));
            obj.addProperty("facing", stargate.getFacing().toString());
            obj.add("tpCoordinates", locToJson(stargate.getTpCoordinates()));
            obj.add("portalBlocks", locToJsonArray(stargate.getPortalBlocks()));
            obj.add("signBlockLocation", locToJson(stargate.getSignBlockLocation()));
            obj.add("irisBlocks", locToJsonArray(stargate.getIrisBlocks()));
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
                stargate.setOwner(jObject.get("owner").getAsString());
                stargate.setLeverBlock(jsonToLoc(jObject.get("leverBlock").getAsJsonObject()));
                stargate.setFacingString(jObject.get("facing").getAsString());
                stargate.setTpCoordinates(jsonToLoc(jObject.get("tpCoordinates").getAsJsonObject()));
                stargate.setPortalBlocks((jsonArrayToLoc(jObject.get("portalBlocks").getAsJsonArray())));
                stargate.setSignBlockLocation(jsonToLoc(jObject.get("signBlockLocation").getAsJsonObject()));
                stargate.setIrisBlocks((jsonArrayToLoc(jObject.get("irisBlocks").getAsJsonArray())));
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



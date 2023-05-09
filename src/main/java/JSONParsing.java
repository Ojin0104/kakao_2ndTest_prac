import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class JSONParsing {
    public JSONParser jsonParser=new JSONParser();

    public static ArrayList<Truck> JsonToTruckArr(JSONObject response){
        ArrayList<Truck> trucks= new ArrayList<>();
        JSONArray responseArr=(JSONArray)response.get("trucks");

        for(int i=0;i<responseArr.size();i++){
            JSONObject truck=(JSONObject) responseArr.get(i);
            trucks.add(new Truck(ObjectToInt(truck.get("id")),ObjectToInt(truck.get("location_id")),ObjectToInt(truck.get("loaded_bikes_count"))));
        }
        return trucks;
    }

    public static ArrayList<Location> JsonToLocationArr(JSONObject response){
        ArrayList<Location> locations=new ArrayList<>();
        JSONArray responseArr=(JSONArray) response.get("locations");

        for(int i=0;i<responseArr.size();i++){
            JSONObject location=(JSONObject)responseArr.get(i);
            locations.add(new Location(ObjectToInt(location.get("id")),ObjectToInt(location.get("located_bikes_count"))));
        }
        return locations;
    }

    public static Integer ObjectToInt(Object o){
        return Integer.parseInt(String.valueOf(o));

    }
}

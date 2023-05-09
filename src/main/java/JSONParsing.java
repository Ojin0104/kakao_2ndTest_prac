import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class JSONParsing {
    public static JSONParser jsonParser=new JSONParser();

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

    public static JSONObject CommandsArrToJson(ArrayList<Command> commands){
        JSONArray jsonArray=new JSONArray();

        for(int i=0;i<commands.size();i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("truck_id",commands.get(i).getTruck_id());
            jsonObject.put("command",commands.get(i).getCommand());
            jsonArray.add(jsonObject);
        }
        JSONObject answer=new JSONObject();
        answer.put("commands",jsonArray);
        return answer;
    }

    public static Integer ObjectToInt(Object o){
        return Integer.parseInt(String.valueOf(o));

    }
}

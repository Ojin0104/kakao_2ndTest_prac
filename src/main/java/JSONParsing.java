import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class JSONParsing {

    public static ArrayList<Truck> JsonToTruckArr(JSONObject response){
        ArrayList<Truck> trucks= new ArrayList<>();
        JSONParser jsonParser=new JSONParser();
        JSONArray responseArr=(JSONArray)response.get("trucks");

        for(int i=0;i<responseArr.size();i++){
            JSONObject truck=(JSONObject) responseArr.get(i);
            trucks.add(new Truck(Integer.parseInt(String.valueOf(truck.get("id"))),Integer.parseInt(String.valueOf(truck.get("location_id"))),Integer.parseInt(String.valueOf(truck.get("loaded_bikes_count")))));


        }
        return trucks;
    }
}

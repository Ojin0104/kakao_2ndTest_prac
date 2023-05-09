import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String Auth_Key=Connector.startApi(1);
        //System.out.println(Connector.StartApi(2));


        for(int i=0;i<720;i++) {
            ArrayList<Location> locArr=Connector.locationsApi(Auth_Key);

            ArrayList<Truck> truckArr=Connector.trucksApi(Auth_Key);
            JSONArray jsonArray=makeCommands(locArr,truckArr);
            System.out.println(Connector.simulateApi(Auth_Key,jsonArray));
            break;
        }
        //System.out.println(Connector.scoreApi(Auth_Key));
    }
    static JSONArray makeCommands(ArrayList<Location> locations,ArrayList<Truck> trucks){
        JSONArray commands=new JSONArray();



        return commands;
    }
}

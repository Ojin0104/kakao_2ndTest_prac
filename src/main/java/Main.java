import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
        int average=4;
        System.out.println(locations.get(1).toString());
        //위치에 평균보다 2개이상 작으면 많은 거에서 옮겨줌
        Collections.sort(locations,(Location l1, Location l2)->{
            return l1.getLocated_bikes_count()-l2.getLocated_bikes_count();
                });

        ArrayList<Location> fillSpace=new ArrayList<>();

        for(int i=0;i<locations.size();i++){
            if(locations.get(i).getLocated_bikes_count()<=average-2){
                fillSpace.add(locations.get(i));
            }else{
                break;
            }
        }





        System.out.println(trucks.get(1).toString());

        return commands;
    }
}

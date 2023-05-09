import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static int average;
    public static void main(String[] args) {
	// write your code here
        String Auth_Key=Connector.startApi(1);
        //System.out.println(Connector.StartApi(2));
        average=4;

        for(int i=0;i<720;i++) {
            ArrayList<Location> locArr=Connector.locationsApi(Auth_Key);

            ArrayList<Truck> truckArr=Connector.trucksApi(Auth_Key);
            JSONArray jsonArray=makeCommands(locArr,truckArr);
            System.out.println(Connector.simulateApi(Auth_Key,jsonArray));
            break;
        }
        //System.out.println(Connector.scoreApi(Auth_Key));
    }
    static JSONArray makeCommands(ArrayList<Location> locations,ArrayList<Truck> trucks) {
        JSONArray JSONcommands = new JSONArray();

        System.out.println(locations.get(1).toString());
        //위치에 평균보다 2개이상 작으면 많은 거에서 옮겨줌
        Collections.sort(locations, (Location l1, Location l2) -> {
            return l1.getLocated_bikes_count() - l2.getLocated_bikes_count();
        });

        ArrayList<Location> fillSpace = new ArrayList<>();
        ArrayList<Location> takeSpace=new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLocated_bikes_count() <= average - 2) {
                fillSpace.add(locations.get(i));
            } else {
                break;
            }
        }
        for(int i=0;i<fillSpace.size();i++){
            takeSpace.add(locations.get(locations.size()-i-1));
        }

        ArrayList<Command> Commands=new ArrayList<>();
        for(int i=0;i<trucks.size();i++) {//트럭 id가 index인 Command
            Commands.add(new Command(trucks.get(i).getId()));
        }

        loadBike(takeSpace,trucks,Commands);


        for (int i = 0; i < fillSpace.size(); i++) {
            int finalI = i;
            Collections.sort(trucks, (Truck t1, Truck t2) -> {
                return getShortest(t1,5,fillSpace.get(finalI))-getShortest(t2,5,fillSpace.get(finalI));
            });

            for(int j=0;j<trucks.size();j++){
                int dist=getShortest(trucks.get(j),5,fillSpace.get(i));
                int move_time=dist*2*6;
                int can_bikenum=(trucks.get(j).getTime_remaining()-move_time)/12;

            }
        }






       // System.out.println(trucks.get(1).toString());

        return JSONcommands;
    }

    private static int getShortest(Truck truck,int row,Location location){
        int loc_id=location.getId();
        int loc_truck=truck.getLocation_id();
        int answer=Math.abs(loc_id/row-loc_truck/row)+Math.abs(loc_id%row-loc_truck%row);

        return answer;
    }

    private static void loadBike(ArrayList<Location> takeSpace,ArrayList<Truck> trucks,ArrayList<Command> Commands){
        for (int i = 0; i < takeSpace.size(); i++) {
            int finalI = i;
            Collections.sort(trucks, (Truck t1, Truck t2) -> {
                return getShortest(t1,5,takeSpace.get(finalI))-getShortest(t2,5,takeSpace.get(finalI));
            });

            for(int j=0;j<trucks.size();j++){
                if(trucks.get(j).getTime_remaining()<60)continue;
                Command truckCommand=Commands.get(trucks.get(j).getId());

                findRoute(trucks.get(j),takeSpace.get(i),truckCommand);//경로 커맨드 입력 및 트럭 쇼요시간 수정

                trucks.get(j).setLocation_id(takeSpace.get(i).getId());// 위치이동

                countBike(trucks.get(j),takeSpace.get(i),truckCommand);//트럭 소요시간 수정 및 트럭 싣고있는 바이크 수 증가


            }
        }
    }

    private static void countBike(Truck truck,Location location,Command command) {
        if(location.getLocated_bikes_count()-average>0){//바이크를 뺴야하는 경우 평균까지만
            while(location.getLocated_bikes_count()>average&&truck.getTime_remaining()>=6) {
                location.setLocated_bikes_count(location.getLocated_bikes_count() - 1);
                truck.useTime();
                truck.setLoaded_bikes_count(truck.getLoaded_bikes_count() + 1);
                command.getCommand().add(5);
            }
        }else{
            while(location.getLocated_bikes_count()<average&&truck.getTime_remaining()>=6) {
                location.setLocated_bikes_count(location.getLocated_bikes_count() + 1);
                truck.useTime();
                truck.setLoaded_bikes_count(truck.getLoaded_bikes_count() - 1);
                command.getCommand().add(6);
                }
            }
        }


    private static void findRoute(Truck truck, Location location, Command truckCommand) {
    int
    }
}

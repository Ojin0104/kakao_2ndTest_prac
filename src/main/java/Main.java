import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static int average;
    public static int n;
    public static void main(String[] args) {
	// write your code here
        String Auth_Key=Connector.startApi(1);
        //System.out.println(Connector.StartApi(2));
        average=4;
        n=5;
        for(int i=0;i<720;i++) {
            ArrayList<Location> locArr=Connector.locationsApi(Auth_Key);

            ArrayList<Truck> truckArr=Connector.trucksApi(Auth_Key);
           // System.out.println(locArr.toString());
            //System.out.println(truckArr.toString());

            JSONObject jsonObject=makeCommands(locArr,truckArr);

            System.out.println((i+1)+"분후 : "+ Connector.simulateApi(Auth_Key,jsonObject).toString());

        }
        System.out.println(Connector.scoreApi(Auth_Key));

        Auth_Key=Connector.startApi(2);
        average=3;
        n=60;
        for(int i=0;i<720;i++) {
            ArrayList<Location> locArr=Connector.locationsApi(Auth_Key);

            ArrayList<Truck> truckArr=Connector.trucksApi(Auth_Key);
            //System.out.println(locArr.toString());
           // System.out.println(truckArr.toString());

            JSONObject jsonObject=makeCommands(locArr,truckArr);

            System.out.println((i+1)+"분후 : "+ Connector.simulateApi(Auth_Key,jsonObject).toString());

        }
        System.out.println(Connector.scoreApi(Auth_Key));

    }
    static JSONObject makeCommands(ArrayList<Location> locations,ArrayList<Truck> trucks) {



        //위치에 평균보다 2개이상 작으면 많은 거에서 옮겨줌
        Collections.sort(locations, (Location l1, Location l2) -> {
            return l1.getLocated_bikes_count() - l2.getLocated_bikes_count();
        });

        ArrayList<Location> fillSpace = new ArrayList<>();
        ArrayList<Location> takeSpace=new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getLocated_bikes_count() < average/2) {
                fillSpace.add(locations.get(i));
            } else {
                break;
            }
        }



        for(int i=locations.size()-1;i>=0;i--){
            if (locations.get(i).getLocated_bikes_count() >= average/2+1) {
                takeSpace.add(locations.get(i));
            } else {
                break;
            }
        }
        System.out.println("takeSpace : "+ takeSpace.toString());
        ArrayList<Command> commands=new ArrayList<>();
        for(int i=0;i<trucks.size();i++) {//트럭 id가 index인 Command
            commands.add(new Command(trucks.get(i).getId()));
        }

        loadBike(takeSpace,trucks,commands);
        unloadBike(fillSpace,trucks,commands);

        return JSONParsing.CommandsArrToJson(commands);
    }

    private static int getShortest(Truck truck,int row,Location location){
        int loc_id=location.getId();
        int loc_truck=truck.getLocation_id();
        int answer=Math.abs(loc_id/row-loc_truck/row)+Math.abs(loc_id%row-loc_truck%row);

        return answer;
    }

    private static void loadBike(ArrayList<Location> spaces,ArrayList<Truck> trucks,ArrayList<Command> Commands){
        for (int i = 0; i < spaces.size(); i++) {
            int finalI = i;
            Collections.sort(trucks, (Truck t1, Truck t2) -> {
                return getShortest(t1,5,spaces.get(finalI))-getShortest(t2,5,spaces.get(finalI));
            });

            for(int j=0;j<trucks.size();j++){
                if(trucks.get(j).getTime_remaining()<60)continue;
                if(trucks.get(j).getLoaded_bikes_count()>=average)continue;
                Command truckCommand=Commands.get(trucks.get(j).getId());

                findRoute(trucks.get(j),spaces.get(i),truckCommand);//경로 커맨드 입력 및 트럭 쇼요시간 수정

                trucks.get(j).setLocation_id(spaces.get(i).getId());// 위치이동

                countBike(trucks.get(j),spaces.get(i),truckCommand);//트럭 소요시간 수정 및 트럭 싣고있는 바이크 수 증가
                break;

            }
        }
    }

    private static void unloadBike(ArrayList<Location> spaces,ArrayList<Truck> trucks,ArrayList<Command> Commands){
        for (int i = 0; i < spaces.size(); i++) {
            int finalI = i;
            Collections.sort(trucks, (Truck t1, Truck t2) -> {
                return getShortest(t1,5,spaces.get(finalI))-getShortest(t2,5,spaces.get(finalI));
            });

            for(int j=0;j<trucks.size();j++){
                if(spaces.get(i).getLocated_bikes_count()>=average/2+1)break;
                if(trucks.get(j).getLoaded_bikes_count()==0)continue;
                Command truckCommand=Commands.get(trucks.get(j).getId());

                findRoute(trucks.get(j),spaces.get(i),truckCommand);//경로 커맨드 입력 및 트럭 쇼요시간 수정

                trucks.get(j).setLocation_id(spaces.get(i).getId());// 위치이동

                countBike(trucks.get(j),spaces.get(i),truckCommand);//트럭 소요시간 수정 및 트럭 싣고있는 바이크 수 증가


            }
        }
    }


    private static void countBike(Truck truck,Location location,Command command) {
        if(location.getLocated_bikes_count()>=average/2+1){//바이크를 뺴야하는 경우 평균까지만
            while(location.getLocated_bikes_count()>=average/2+1&&truck.getTime_remaining()>=6) {
                location.setLocated_bikes_count(location.getLocated_bikes_count() - 1);
                truck.useTime();
                truck.setLoaded_bikes_count(truck.getLoaded_bikes_count() + 1);
                command.getCommand().add(5);
            }
        }else{
            while(location.getLocated_bikes_count()<average/2+1&&truck.getTime_remaining()>=6&&truck.getLoaded_bikes_count()>0) {
                location.setLocated_bikes_count(location.getLocated_bikes_count() + 1);
                truck.useTime();
                truck.setLoaded_bikes_count(truck.getLoaded_bikes_count() - 1);
                command.getCommand().add(6);
                }
            }
        }


    private static void findRoute(Truck truck, Location location, Command command) {
        int loc_id=location.getId();
        int loc_truck=truck.getLocation_id();

        int truck_c=loc_truck/n;
        int loc_c=loc_id/n;
        int truck_r=loc_truck%n;
        int loc_r=loc_id%n;

        while(truck_c!=loc_c&&truck.getTime_remaining()>=6){
            if(truck_c>loc_c){
                command.getCommand().add(4);
                truck_c--;
            }else{
                command.getCommand().add(2);
                truck_c++;
            }
            truck.useTime();
        }

        while(truck_r!=loc_r&&truck.getTime_remaining()>=6){
            if(truck_r>loc_r){
                command.getCommand().add(3);
                truck_r--;
            }else{
                command.getCommand().add(1);
                truck_r++;
            }
            truck.useTime();
        }
    }
}

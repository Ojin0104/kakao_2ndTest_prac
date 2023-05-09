import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Command {

    private int truck_id;
    private ArrayList<Integer> command;

    public Command(int truck_id,ArrayList<Integer> commands){
        this.truck_id=truck_id;
        this.command=commands;
    }

    public int getTruck_id() {
        return truck_id;
    }

    public void setTruck_id(int truck_id) {
        this.truck_id = truck_id;
    }

    @Override
    public String toString() {
        return "Command{" +
                "truck_id=" + truck_id +
                ", command=" + command +
                '}';
    }

    public ArrayList<Integer> getCommand() {
        return command;
    }

    public void setCommand(ArrayList<Integer> command) {
        this.command = command;
    }
}

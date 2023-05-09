public class Truck {

    private int id;
    private int location_id;
    private int loaded_bikes_count;
    private int time_remaining;

    public int getTime_remaining() {
        return time_remaining;
    }

    public void setTime_remaining(int time_remaining) {
        this.time_remaining = time_remaining;
    }

    public int useTime(){
        this.time_remaining-=6;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", location_id=" + location_id +
                ", loaded_bikes_count=" + loaded_bikes_count +
                '}';
    }

    public int getLoaded_bikes_count() {
        return loaded_bikes_count;
    }

    public void setLoaded_bikes_count(int loaded_bikes_count) {
        this.loaded_bikes_count = loaded_bikes_count;
    }


    public Truck(int id, int location_id, int loaded_bikes_count) {
        this.id = id;
        this.location_id = location_id;
        this.loaded_bikes_count = loaded_bikes_count;
        this.time_remaining=60;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

}

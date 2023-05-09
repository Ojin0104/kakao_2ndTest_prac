public class Simulate {

    private String status;
    private int time;
    private String distance;
    private int failed_requests_count;

    @Override
    public String toString() {
        return "Simulate{" +
                "status='" + status + '\'' +
                ", time=" + time +
                ", distance='" + distance + '\'' +
                ", failed_requests_count=" + failed_requests_count +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getFailed_requests_count() {
        return failed_requests_count;
    }

    public void setFailed_requests_count(int failed_requests_count) {
        this.failed_requests_count = failed_requests_count;
    }
}

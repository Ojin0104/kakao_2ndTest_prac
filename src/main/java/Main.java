public class Main {

    public static void main(String[] args) {
	// write your code here
        String Auth_Key=Connector.StartApi(1);
        //System.out.println(Connector.StartApi(2));

        System.out.println(Connector.LocationsApi(Auth_Key));
    }
}

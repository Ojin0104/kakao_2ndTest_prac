public class Main {

    public static void main(String[] args) {
	// write your code here
        String Auth_Key=Connector.startApi(1);
        //System.out.println(Connector.StartApi(2));

        System.out.println(Connector.locationsApi(Auth_Key));

        System.out.println(Connector.trucksApi(Auth_Key));
        for(int i=0;i<720;i++) {
            System.out.println(Connector.simulateApi(Auth_Key));
            break;
        }
        //System.out.println(Connector.scoreApi(Auth_Key));
    }
}

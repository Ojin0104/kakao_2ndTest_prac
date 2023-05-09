import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Connector {
    private static String jsonType = "application/json";

    public static JSONObject StartApi(int problemNum) {
        URL url;
        StringBuilder response = new StringBuilder();
        JSONObject result=null;
        {
            try {
                url = new URL(ConstantString.BASE_URL + ConstantString.START_API);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", jsonType);
                connection.setRequestProperty("X-Auth-Token", ConstantString.AUTH_TOKEN);
                connection.setDoOutput(true);

                JSONObject commands = new JSONObject();
                commands.put("problem", problemNum);

                System.out.println(commands.toString());
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(commands.toString());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                System.out.println("응답코드: " + responseCode);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 응답 내용 출력
                //System.out.println("응답 내용: " + response.toString());
                result = (JSONObject) new JSONParser().parse(response.toString());
                // 연결 종료
                connection.disconnect();
            }catch(Exception e){
                e.printStackTrace();
            }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
        return result;

    }
}


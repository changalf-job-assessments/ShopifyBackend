import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by alfredchang on 2017-05-01.
 */
public class Main {

    private static final int PAGE_LIMIT = 11;
    private static final String absolutePath = "https://backend-challenge-fall-2017.herokuapp.com";
    private static final String relativePath = "/orders.json";
    private static final String query = "?page=";

    private static JSONDataHandler jsonDataHandler;
    private static URIBuilder uriBuilder;
    private static String uri;

    public static void main(String[] args) {
        jsonDataHandler = new JSONDataHandler();
        for (int i = 1; i <= PAGE_LIMIT; i++) {
            uriBuilder = new URIBuilder(absolutePath, relativePath, query + String.valueOf(i));
            uri = uriBuilder.buildUri();
            System.out.println(uri);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonDataHandler.getJSONData(uri));
                System.out.println("JSON object: " + jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            parseJSONData(jsonObject);
        }
    }

    public static void parseJSONData(JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
    }

    public static boolean sortOrders(JSONObject jsonObject) {
        return false;
    }

    public static JSONObject createJSONOutput(JSONObject jsonObject) {
        return null;
    }
}

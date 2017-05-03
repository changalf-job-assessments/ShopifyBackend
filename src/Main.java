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

    private static JSONObject encapsulateObject = new JSONObject();
    private static JSONArray arrayWithRelevantInfo = new JSONArray();
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getRelevantInfoFromJSONObject(jsonObject);
        }
    }

    public static void getRelevantInfoFromJSONObject(JSONObject jsonObject) {
        try {
            JSONArray customerOrders = jsonObject.getJSONArray("orders");

            for (int i = 0; i < customerOrders.length(); i++) {
                JSONObject individualOrder = customerOrders.getJSONObject(i);
                JSONArray productInfo = individualOrder.getJSONArray("products");

                for (int j = 0; j < productInfo.length(); j++) {
                    JSONObject individualItem = productInfo.getJSONObject(j);
                    String nameOfItem = individualItem.getString("title");

                    if (nameOfItem.equals("Cookie")) {
                        jsonObject = removeItem(individualOrder.getInt("id"), j, productInfo);
                        System.out.println(jsonObject);
                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject removeItem(int id, int index, JSONArray productInfo) {
        return (JSONObject) productInfo.remove(index);
    }

    public static boolean sortOrders(JSONObject jsonObject) {
        return false;
    }
}

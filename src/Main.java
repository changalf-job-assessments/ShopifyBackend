import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by alfredchang on 2017-05-01.
 */
public class Main {

    private static final int PAGE_LIMIT = 3;
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
            JSONObject relevantOrders = new JSONObject();
            JSONArray arrayOfItems = new JSONArray();
            JSONArray customerOrders = jsonObject.getJSONArray("orders");
            int available_cookies = jsonObject.getInt("available_cookies");

            for (int i = 0; i < customerOrders.length(); i++) {
                JSONObject individualOrder = customerOrders.getJSONObject(i);
                JSONArray productInfo = individualOrder.getJSONArray("products");

                for (int j = 0; j < productInfo.length(); j++) {
                    JSONObject individualItem = productInfo.getJSONObject(j);
                    String nameOfItem = individualItem.getString("title");

                    if (nameOfItem.equals("Cookie")) {
                        jsonObject = removeRelevantItem(j, productInfo);
                        jsonObject.put("id", individualOrder.getInt("id"));
                        arrayOfItems.put(jsonObject);
                    }
                }
                relevantOrders.put("orders", arrayOfItems);
            }

            relevantOrders = sortOrders(relevantOrders);
            canFulfillOrder(relevantOrders, available_cookies);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void canFulfillOrder(JSONObject orders, int availableCookies) {
        JSONObject infoOfUnfulfilledOrders = new JSONObject();
        List<Integer> ids = new ArrayList<Integer>();

        try {
            JSONArray items = orders.getJSONArray("orders");

            for (int i = 0; i < items.length(); i++) {
                JSONObject individualItem = items.getJSONObject(i);
                int quantityOfOrder = individualItem.getInt("amount");
                if (availableCookies < quantityOfOrder) {
                    ids.add(individualItem.getInt("id"));
                } else {
                    availableCookies -= quantityOfOrder;
                }
            }

            displayFinalResults(infoOfUnfulfilledOrders, availableCookies, ids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void displayFinalResults(JSONObject infoOfUnfulfilledOrders, int availableCookies,
                                           List<Integer> ids) throws JSONException {
        infoOfUnfulfilledOrders.put("remaining_cookies", availableCookies);
        infoOfUnfulfilledOrders.put("unfulfilled_orders", ids);

        System.out.println(infoOfUnfulfilledOrders);
    }

    public static JSONObject removeRelevantItem(int index, JSONArray productInfo) {
        return (JSONObject) productInfo.remove(index);
    }

    public static JSONObject sortOrders(JSONObject jsonObject) {
        JSONObject relevantOrders = new JSONObject();
        JSONArray sortedOrders = new JSONArray();
        List<JSONObject> listOfIndividualOrders = new ArrayList<JSONObject>();

        try {
            JSONArray ordersArray = jsonObject.getJSONArray("orders");

            for (int i = 0; i < ordersArray.length(); i++) {
                listOfIndividualOrders.add(ordersArray.getJSONObject(i));
            }

            Collections.sort(listOfIndividualOrders, new Comparator<JSONObject>() {
                public int compare(JSONObject order1, JSONObject order2) {
                    int orderAmount1 = 0;
                    int orderAmount2 = 0;

                    try {
                        orderAmount1 = order1.getInt("amount");
                        orderAmount2 = order2.getInt("amount");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return Integer.compare(orderAmount2, orderAmount1);
                }
            });

            for (int i = 0; i < ordersArray.length(); i++) {
                sortedOrders.put(listOfIndividualOrders.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            relevantOrders.put("orders", sortedOrders);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return relevantOrders;
    }
}

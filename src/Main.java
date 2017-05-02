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
        for (int i = 0; i <= PAGE_LIMIT; i++) {
            uriBuilder = new URIBuilder(absolutePath, relativePath, query + String.valueOf(i));
            uri = uriBuilder.buildUri();
            System.out.println(uri);
        }
    }
}

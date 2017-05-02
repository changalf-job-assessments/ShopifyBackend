/**
 * Created by alfredchang on 2017-05-01.
 */
public class URIBuilder {

    private String absolutePath;
    private String relativePath;
    private String query;

    // https://backend-challenge-fall-2017.herokuapp.com/orders.json
    // https://backend-challenge-fall-2017.herokuapp.com/orders.json?page=2
    public URIBuilder(String absolutePath, String relativePath, String query) {
        this.absolutePath = absolutePath;
        this.relativePath = relativePath;
        this.query = query;
    }

    public String buildUri() {
        return absolutePath + relativePath + query;
    }
}

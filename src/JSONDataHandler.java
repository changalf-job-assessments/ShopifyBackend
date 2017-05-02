import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;
/**
 * Created by alfredchang on 2017-05-01.
 */
public class JSONDataHandler {

    private InputStream inputStream;

    public void getInputStream(String url) {
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStreamReader getInputStreamReader() {
        return new InputStreamReader(inputStream);
    }

    public String readJSON(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        int charIndex;

        try {
            while ((charIndex = bufferedReader.read()) != -1) {
                stringBuilder.append(charIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public JSONObject getJSONData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(getInputStreamReader());
            String jsonData = readJSON(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

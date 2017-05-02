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

    public InputStream getInputStream(String uri) {
        InputStream inputStream = null;

        try {
            inputStream = new URL(uri).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert(inputStream != null);
        return inputStream;
    }

    public InputStreamReader getInputStreamReader(InputStream inputStream) {
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

    public JSONObject getJSONData(InputStreamReader inputStreamReader) {
        JSONObject jsonObject = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String jsonData = readJSON(bufferedReader);
            jsonObject = new JSONObject(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert(jsonObject == null);
        return jsonObject;
    }
}

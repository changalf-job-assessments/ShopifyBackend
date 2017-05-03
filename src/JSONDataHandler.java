import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
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
        return new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    }

    public String readJSON(BufferedReader bufferedReader) {
        StringBuffer stringBuffer = new StringBuffer();
        int charIndex;
        char[] chars = new char[1024];

        try {
            while ((charIndex = bufferedReader.read(chars)) != -1) {
                stringBuffer.append(chars, 0, charIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    public String getJSONData(String uri) {
        String jsonData = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(getInputStreamReader(getInputStream(uri)));
            jsonData = readJSON(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonData;
    }
}

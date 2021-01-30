package hello;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class urlConnector {

    String loginPassword = "";

    private String getToken() {
        return System.getenv("Akey");
    }


    JSONObject getJsonFromUrl(URL url, String loginMail) throws IOException {

        loginPassword = getToken();


        URLConnection uc = url.openConnection();

        String userpass = loginMail + ":" + loginPassword;
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
        uc.setRequestProperty("Authorization", basicAuth);

        System.out.println(uc.getRequestProperties());
        // give it 15 seconds to respond
        uc.setReadTimeout(10 * 1000);
        uc.connect();


        InputStream in = null;

        in = uc.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line = "";

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }

        return new JSONObject(sb.toString());
    }
}

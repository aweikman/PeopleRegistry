package wsclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SimpleGetDemo {

    public static void main(String[] args) {
        // get a random anime quote
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet("https://animechan.vercel.app/api/random");
            CloseableHttpResponse response = httpclient.execute(getRequest);

            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            System.out.println(strResponse);

            JSONObject objResponse = new JSONObject(strResponse);
            System.out.println("Anime: " + objResponse.getString("anime"));
            System.out.println("Character: " + objResponse.getString("character"));
            System.out.println("Quote: " + objResponse.getString("quote"));

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

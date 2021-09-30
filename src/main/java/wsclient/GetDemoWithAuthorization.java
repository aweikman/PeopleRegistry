package wsclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GetDemoWithAuthorization {

    public static void main(String[] args) {
        // get a random anime quote
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            // HttpGet getRequest = new HttpGet("https://animechan.vercel.app/api/random");
            HttpGet getRequest = new HttpGet("http://localhost:8080/dogs");

            // attach the session id as an Authorization header
            getRequest.setHeader("Authorization", "i am a session token");

            CloseableHttpResponse response = httpclient.execute(getRequest);

            if(response.getStatusLine().getStatusCode() != 200) {
                System.out.println("Non 200 response code received: " + response.getStatusLine().getStatusCode());
                response.close();
                httpclient.close();
                return;
            }

//            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

//            System.out.println(strResponse);

            JSONArray dogList = new JSONArray(strResponse);
            for (Object rawDog: dogList) {
                JSONObject dog = (JSONObject) rawDog;
                System.out.println(dog);
                System.out.println(dog.getInt("id"));
                System.out.println(dog.getString("dogName"));

//                System.out.println(rawDog);
//                System.out.println(rawDog.getClass().getSimpleName());
//                JSONObject dog = new JSONObject((String rawDog);
//                System.out.println(dog);

            }
//            JSONObject objResponse = new JSONObject(strResponse);
//            System.out.println("Anime: " + objResponse.getString("anime"));
//            System.out.println("Character: " + objResponse.getString("character"));
//            System.out.println("Quote: " + objResponse.getString("quote"));

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

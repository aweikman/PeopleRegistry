//package wsclient;
//
//import model.Dog;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//
//public class DogGateway {
//    private String wsURL;
//    private String sessionId;
//
//    public DogGateway(String url, String sessionId) {
//        this.sessionId = sessionId;
//        this.wsURL = url;
//    }
//
//    public ArrayList<Dog> getDogs() {
//        ArrayList<Dog> dogs = new ArrayList<Dog>();
//
//        try {
//            // we know this is a GET request so create a get request and pass it to getResponseAsString
//            // build the request
//            HttpGet request = new HttpGet(wsURL);
//            // specify Authorization header
//            request.setHeader("Authorization", sessionId);
//
//            String response = waitForResponseAsString(request);
//
//            for(Object obj : new JSONArray(response)) {
//                JSONObject jsonObject = (JSONObject) obj;
//                Dog dog = new Dog(jsonObject.getString("dogName"), 0);
//                dog.setId(jsonObject.getInt("id"));
//                dogs.add(dog);
//            }
//        } catch (Exception e) {
//            throw new DogException(e);
//        }
//
//        return dogs;
//    }
//
//    private String waitForResponseAsString(HttpRequestBase request) throws IOException {
//        CloseableHttpClient httpclient = null;
//        CloseableHttpResponse response = null;
//
//        try {
//
//            httpclient = HttpClients.createDefault();
//            response = httpclient.execute(request);
//
//            switch(response.getStatusLine().getStatusCode()) {
//                case 200:
//                    // peachy
//                    break;
//                case 401:
//                    throw new DogException("401");
//                default:
//                    throw new DogException("Non-200 status code returned: " + response.getStatusLine());
//            }
//
//            return parseResponseToString(response);
//
//        } catch(Exception e) {
//            throw new DogException(e);
//        } finally {
//            if(response != null)
//                response.close();
//            if(httpclient != null)
//                httpclient.close();
//        }
//    }
//
//    private String parseResponseToString(CloseableHttpResponse response) throws IOException {
//        HttpEntity entity = response.getEntity();
//        // use org.apache.http.util.EntityUtils to read json as string
//        return EntityUtils.toString(entity, StandardCharsets.UTF_8);
//    }
//
//}
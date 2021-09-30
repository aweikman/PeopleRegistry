//package wsclient;
//
//import mvc.model.Person;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//
//public class FatPOJO {
//    private static final String WS_URL = "http://localhost:8080";
//    private static final Logger logger = LogManager.getLogger();
//
//    // swiped from https://hc.apache.org/httpcomponents-client-ga/quickstart.html
//    public static String getResponseAsString(CloseableHttpResponse response) throws IOException {
//        HttpEntity entity = response.getEntity();
//        // use org.apache.http.util.EntityUtils to read json as string
//        String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//        EntityUtils.consume(entity);
//
//        return strResponse;
//    }
//
//    // grab a list of people from the wiremock /people route
//    public static void main(String [] args) {
//        ArrayList<Person> people = new ArrayList<Person>();
//
//        // swiped from https://hc.apache.org/httpcomponents-client-ga/quickstart.html
//        CloseableHttpResponse response = null;
//        CloseableHttpClient httpclient = null;
//        try {
//
//            httpclient = HttpClients.createDefault();
//
//            // build the request
//            HttpGet httpGet = new HttpGet(WS_URL + "/people");
//            // specify Authorization header
//            httpGet.setHeader("Authorization", "i am a session token");
//
//            // execute the request and wait for a response
//            response = httpclient.execute(httpGet);
//
//            if(response.getStatusLine().getStatusCode() != 200) {
//                logger.error("Non-200 status code returned: " + response.getStatusLine());
//                httpclient.close();
//                return;
//            }
//
//            // get response as a string
//            String json = getResponseAsString(response);
//
//            logger.info("Response as string is " + json);
//
//            // use json.org library to parse response into json array
//            JSONArray jsonArray = new JSONArray(json);
//
//            // from the json array, parse out individual person objects
//            for(Object obj : jsonArray) {
//                JSONObject jsonObject = (JSONObject) obj;
//                Person person = new Person(jsonObject.getString("personName"), 0);
//                person.setId(jsonObject.getInt("id"));
//                people.add(person);
//            }
//
//            // do something with the people
//            System.out.println(people);
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                response.close();
//                httpclient.close();
//            } catch(IOException e) {
//                // ignore
//            }
//        }
//    }
//}

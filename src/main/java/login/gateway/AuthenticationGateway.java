package login.gateway;

import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AuthenticationGateway {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Session login(String userName, String password) throws UnauthorizedException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost("http://localhost:8080/login");

            // use this for submitting form data as raw json
            JSONObject formData = new JSONObject();
            formData.put("username", userName);
            formData.put("password", password);
            String formDataString = formData.toString();

            StringEntity reqEntity = new StringEntity(formDataString);
            postRequest.setEntity(reqEntity);

            postRequest.setHeader("Accept","application/json");
            postRequest.setHeader("Content-Type","application/json");

            response = httpclient.execute(postRequest);

            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    HttpEntity entity = response.getEntity();
                    // use org.apache.http.util.EntityUtils to read json as string
                    String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    EntityUtils.consume(entity);

                    //cannot find way to get the token from entity, so i get the whole response body,
                    //make a new json obj from that, then  get the token from the new obj and save it
                    LOGGER.info("strResponse = "+strResponse);
                    JSONObject j = new JSONObject(strResponse);
                    strResponse = (String) j.get("token");

                    Session session = new Session(strResponse, userName);
                    System.out.println(session.getSessionId());
                    return session;
                case 401:
                    throw new UnauthorizedException("login failed");
                default:
                    throw new UnknownException(response.getStatusLine().getReasonPhrase());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnauthorizedException(e);

        } finally {
            try {
                if(response != null) {
                    response.close();
                }
                if(httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new UnauthorizedException(e);
            }
        }
    }
}

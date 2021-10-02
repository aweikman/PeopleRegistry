package login.gateway;

import gateway.PersonGateway;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;
import mvc.model.Person;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PersonGatewayAPI implements PersonGateway{

    private static final String URL = "http://localhost:8080";

    private static String token;

    public PersonGatewayAPI()
    {

    }

    public PersonGatewayAPI(String token)
    {
        this.token = token;
    }

    static List<Person> people = new ArrayList<>();

    public List<Person> fetchPeople() throws UnauthorizedException, UnknownException {


        try {
            if(people.size() == 0) {
                String response = executeGetRequest(URL + "/people", token);
                JSONArray personList = new JSONArray(response);
                for (Object person : personList) {
                    people.add(Person.fromJSONObject((JSONObject) person));
                }
            }
            else {
                return people;
            }
        } catch(RuntimeException e) {
            throw new UnknownException(e);
        }

        return people;
    }


    public static void addPerson(Person person) {
        try {
            String response = executePostRequest(URL + "/people", token, person.getPersonFirstName(), person.getPersonLastName(), person.getDateOfBirth());
            people.add(person);

        } catch(RuntimeException e) {
            throw new UnknownException(e);
        }
    }

    public static void updatePerson(Person person) {
        try {
            String response = executePutRequest(URL + "/people/1", token, person.getPersonFirstName());

        } catch (RuntimeException e) {
            throw new UnknownException(e);
        }
    }



    private static String executePostRequest(String url, String token, String personFirstName, String personLastName, String personDateOfBirth) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(url);

            if(token != null && token.length() > 0)
                postRequest.setHeader("Authorization", token);
            // use this for submitting form data as raw json
            JSONObject formData = new JSONObject();
            formData.put("person_first_name", personFirstName);
            formData.put("person_last_name", personLastName);
            formData.put("dob", personDateOfBirth);
            String formDataString = formData.toString();

            StringEntity reqEntity = new StringEntity(formDataString);

            postRequest.setEntity(reqEntity);

            response = httpclient.execute(postRequest);

            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    return getStringFromResponse(response);
                case 401:
                    throw new UnauthorizedException(response.getStatusLine().getReasonPhrase());
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



    private static String executePutRequest(String url, String token, String personFirstName) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpPut putRequest = new HttpPut(url);

            if(token != null && token.length() > 0)
                putRequest.setHeader("Authorization", token);
            // use this for submitting form data as raw json
            JSONObject formData = new JSONObject();
            formData.put("person_first_name", personFirstName);
            String formDataString = formData.toString();

            StringEntity reqEntity = new StringEntity(formDataString);

            putRequest.setEntity(reqEntity);

            response = httpclient.execute(putRequest);

            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    return getStringFromResponse(response);
                case 401:
                    throw new UnauthorizedException(response.getStatusLine().getReasonPhrase());
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


    private static String executeDeleteRequest(String url, String token) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpDelete deleteRequest = new HttpDelete(url);

            if(token != null && token.length() > 0)
                deleteRequest.setHeader("Authorization", token);
            // use this for submitting form data as raw json

            response = httpclient.execute(deleteRequest);

            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    return getStringFromResponse(response);
                case 401:
                    throw new UnauthorizedException(response.getStatusLine().getReasonPhrase());
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

    public static void deletePerson(Person person) {
        try {

            String response = executeDeleteRequest(URL + "/people/1", token);

        } catch (RuntimeException e) {
            throw new UnknownException(e);
        }
    }

    private String executeGetRequest(String url, String token) throws UnauthorizedException, UnknownException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url);

            if(token != null && token.length() > 0)
                getRequest.setHeader("Authorization", token);

            response = httpclient.execute(getRequest);

            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    return getStringFromResponse(response);
                case 401:
                    throw new UnauthorizedException(response.getStatusLine().getReasonPhrase());
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

    private static String getStringFromResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        EntityUtils.consume(entity);
        return strResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

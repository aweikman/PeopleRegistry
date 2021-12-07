package login.gateway;

import gateway.PersonGateway;
import mvc.model.AuditTrail;
import mvc.model.FetchResults;
import mvc.model.Person;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;
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
import java.time.Instant;
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

    public List<AuditTrail> fetchAuditTrail(int id) throws UnauthorizedException, UnknownException {
        List<AuditTrail> auditTrail = new ArrayList<>();
        try {
                String response = executeGetRequest(URL + "/people/" + id +"/audittrail", token);
                JSONArray auditTrailList = new JSONArray(response);
                for (Object aTrail : auditTrailList) {
                    auditTrail.add(AuditTrail.fromJSONObject((JSONObject) aTrail));
                }

        } catch(RuntimeException e) {
            throw new UnknownException(e);
        }

        return auditTrail;
    }


    public FetchResults fetchPeople(int pageNum, String lastName) throws UnauthorizedException, UnknownException {
        ArrayList<Person> people = new ArrayList<>();
        FetchResults results = new FetchResults();

        try {
            if(people.size() == 0) {
                String response = executeGetRequest(URL + "/people?pageNum="+pageNum+"&lastName="+lastName, token);
                JSONObject personPage = new JSONObject(response);
//                System.out.println("HELLPP ME GOD " + personPage);
                JSONArray personList = personPage.getJSONArray("content");

                for (Object person : personList) {
                    people.add(Person.fromJSONObject((JSONObject) person));
                }
                results.setPeople(people);
                results.setTotalElements(personPage.getInt("totalElements"));
                results.setOffSet(personPage.getJSONObject("pageable").getInt("pageNumber"));
                results.setTotalPages(personPage.getInt("totalPages"));
                results.setFirstIndex(personPage.getJSONObject("pageable").getInt("offset"));
                results.setLastIndex(personPage.getInt("numberOfElements") + personPage.getJSONObject("pageable").getInt("offset"));
            }

        } catch(RuntimeException e) {
            throw new UnknownException(e);
        }

        return results;
    }

    public static void addPerson(Person person) {
        try {
            String response = executePostRequest(URL + "/people", token, person.getPersonFirstName(), person.getPersonLastName(), person.getDateOfBirth());
//            people.add(person);

        } catch(RuntimeException e) {
            throw new UnknownException(e);
        }
    }

    public static void deletePerson(Person person) {
        try {
            int id = person.getId();
            String response = executeDeleteRequest(URL + "/people/" + id, token);
//            people.remove(person);

        } catch (RuntimeException e) {
            throw new UnknownException(e);
        }
    }

    public static void updatePerson(Person person) {
        try {
            int id = person.getId();
            String response = executePutRequest(URL + "/people/" + id, token, person.getPersonFirstName(), person.getPersonLastName(), person.getDateOfBirth(), person.getLastModified());

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
            formData.put("firstName", personFirstName);
            formData.put("lastName", personLastName);
            formData.put("dateOfBirth", personDateOfBirth);
            String formDataString = formData.toString();

            StringEntity reqEntity = new StringEntity(formDataString);

            postRequest.setEntity(reqEntity);

            postRequest.setHeader("Accept","application/json");
            postRequest.setHeader("Content-Type","application/json");

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


    private static String executePutRequest(String url, String token, String personFirstName, String personLastName, String personDateOfBirth, Instant lastModified) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpPut putRequest = new HttpPut(url);

            if(token != null && token.length() > 0)
                putRequest.setHeader("Authorization", token);
            // use this for submitting form data as raw json
            JSONObject formData = new JSONObject();
            formData.put("firstName", personFirstName);
            formData.put("lastName", personLastName);
            formData.put("dateOfBirth", personDateOfBirth);
            formData.put("lastModified", lastModified);

            String formDataString = formData.toString();

            StringEntity reqEntity = new StringEntity(formDataString);

            putRequest.setEntity(reqEntity);

            putRequest.setHeader("Accept","application/json");
            putRequest.setHeader("Content-Type","application/json");

            response = httpclient.execute(putRequest);

            switch(response.getStatusLine().getStatusCode()) {
                case 200:
                    return getStringFromResponse(response);
                case 401:
                    throw new UnauthorizedException(response.getStatusLine().getReasonPhrase());
//                case 409:
//                    Alerts.infoAlert("Update Error", "Another user has just made a change to this record");
//
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

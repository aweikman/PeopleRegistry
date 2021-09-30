//package wsclient;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//// uses test web form at http://www.createafreewebsite.net/html_forms.html
//public class SimplePostDemo {
//
//    public static void main(String[] args) {
//        // get a random anime quote
//        try {
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//            HttpPost postRequest = new HttpPost("http://www.createafreewebsite.net/parse_form.php");
//
//            // use this for submitting form data as raw json
////            JSONObject formData = new JSONObject();
////            formData.put("firstname", "aaaaa");
////            formData.put("lastname", "bbbbb");
////            String formDataString = formData.toString();
//
////            StringEntity reqEntity = new StringEntity(formDataString);
////            postRequest.setEntity(reqEntity);
//
//            // use this for submitting plain old form data
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("firstname", "aaaaa"));
//            params.add(new BasicNameValuePair("lastname", "bbbbb"));
//            postRequest.setEntity(new UrlEncodedFormEntity(params));
//
//            // I don't think we need these 2 lines
////            postRequest.setHeader("Accept", "application/json");
////            postRequest.setHeader("Content-type", "application/json");
//
//            CloseableHttpResponse response = httpclient.execute(postRequest);
//
//            System.out.println(response.getStatusLine());
//
//            HttpEntity entity = response.getEntity();
//            // use org.apache.http.util.EntityUtils to read json as string
//            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//            EntityUtils.consume(entity);
//
//            System.out.println(strResponse);
//
//            response.close();
//            httpclient.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

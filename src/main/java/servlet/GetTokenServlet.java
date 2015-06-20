package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.util.JSON;
import domain.AccessToken;
import domain.User;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by freeemahn on 20.06.15.
 */
public class GetTokenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    MongoClient m = null;
    DB db;

    public void init(ServletConfig config) {
        try {
            m = new MongoClient("localhost");
            db = m.getDB("test");
            System.out.println("Connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // super.doGet(req, resp);
        String code = req.getParameter("code");
        String state = req.getParameter("state");
        String url = "https://esia-portal1.test.gosuslugi.ru/aas/oauth2/te";


        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);


        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("client_id", ""));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("client_secret", ""));
        urlParameters.add(new BasicNameValuePair("state", state));
        urlParameters.add(new BasicNameValuePair("redirect_uri", "/"));
        urlParameters.add(new BasicNameValuePair("scope", ""));
        urlParameters.add(new BasicNameValuePair("timestamp", ""));
        urlParameters.add(new BasicNameValuePair("token_type", "Bearer"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

      /*  HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }*/


        Gson gson = new GsonBuilder().create();
        DBCollection users = db.getCollection("users");
        //AccessToken token = gson.fromJson(result.toString(), AccessToken.class);
        AccessToken token = new AccessToken("test", "bbbbbb", "never", "STATE_NY", "Bearer", "000000");
        User user = new User(token.getUsersIdentifier(), token, token.getId_token(), "VASYA");//wow
        BasicDBObject obj1 = (BasicDBObject) JSON.parse(gson.toJson(user));
        users.insert(obj1);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

    }
}

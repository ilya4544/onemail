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

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
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
    PrintWriter out;

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
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        out = resp.getWriter();
        String code = req.getParameter("code");
        String state = req.getParameter("state");
        String https_url = "https://esia-portal1.test.gosuslugi.ru/aas/oauth2/te";


        String client_id = "C02G8416DRJM";
        String grant_type = "";
        String client_secret = "";
        String redirect_uri = "/browse";
        String scope = "openid";
        String timestamp = "";
        String token_type = "";

        String query = "";
        query += "client_id=" + client_id;
        query += "&code=" + code;
        query += "&grant_type=" + grant_type;
        query += "&client_secret=" + client_secret;
        query += "&state=" + state;
        query += "&redirect_uri=" + redirect_uri;
        query += "&scope=" + scope;
        query += "&timestamp=" + timestamp;
        query += "&token_type=" + token_type;


        URL url = new URL(https_url);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-length", String.valueOf(query.length()));
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);
        con.setDoInput(true);


        // out.println("****** Content of the URL ********");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String result;

        while ((result = br.readLine()) != null) {
            out.println(result);
        }
        br.close();


        Gson gson = new GsonBuilder().create();
        DBCollection users = db.getCollection("users");
        //AccessToken token = gson.fromJson(result, AccessToken.class);
        AccessToken token = new AccessToken("test", "bbbbbb", "never", "STATE_NY", "Bearer", "000000");
        User user = new User(token.getUsersIdentifier(), token, token.getId_token(), "VASYA", "gordon.pav@gmail.com");//wow
        BasicDBObject obj1 = (BasicDBObject) JSON.parse(gson.toJson(user));
        users.insert(obj1);
        resp.sendRedirect("/browse/test");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

    }


}

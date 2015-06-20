package servlet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by freeemahn on 20.06.15.
 */

/**
 * First request to ESIA
 * Redirects to /getAccessToken
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        /*String url = "https://esia-portal1.test.gosuslugi.ru/aas/oauth2/ac";

        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpParams params = get.getParams();
        params.setParameter("client_id", "C02G8416DRJM");
        params.setParameter("client_secret", "");
        params.setParameter("redirect_uri", "/getAccessToken");
        params.setParameter("scope", "openid");
        params.setParameter("responce_type", "");
        params.setParameter("state", "");
        params.setParameter("timestamp", "12345");
        params.setParameter("access_type", "12345");
        get.setParams(params);


        HttpResponse response = client.execute(get);
        /** Now we redirect to getAccessToken
         *
         */

        /*System.out.println("\nSending 'get' request to URL : " + url);
        System.out.println("Post parameters : " + get.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());*/

        /*BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);


    }
}


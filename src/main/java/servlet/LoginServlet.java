package servlet;

import org.apache.http.params.HttpParams;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.security.cert.Certificate;

/**
 * Created by freeemahn on 20.06.15.
 */

/**
 * First request to ESIA
 * Redirects to /getAccessToken
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PrintWriter out;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        resp.setContentType("text/html");
        // super.doGet(req, resp);
        String https_url = "https://esia-portal1.test.gosuslugi.ru/aas/oauth2/ac?";


        String client_id = "C02G8416DRJM";
        String client_secret = "";
        String redirect_uri = "/browse";
        String scope = "openid";
        String responce_type = "code";
        String state = "";
        String timestamp = "";
        String access_type = "online";
        https_url += "client_id=" + client_id;
//        params.setParameter("client_id", "C02G8416DRJM");
        https_url += "&client_secret=" + client_secret;
//        params.setParameter("client_secret", "");
        https_url += "&redirect_uri=" + redirect_uri;
//        params.setParameter("redirect_uri", "/get");
        https_url += "&scope=" + scope;
//        params.setParameter("scope", "openid");        ;
        https_url += "&responce_type=" + responce_type;
//        params.setParameter("responce_type", "");
        https_url += "&state=" + state;
//        params.setParameter("state", "");
        https_url += "&timestamp=" + timestamp;
//        params.setParameter("timestamp", "12345");
        https_url += "&access_type=" + access_type;
//        params.setParameter("access_type", "12345");
        URL url = new URL(https_url);

        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        //print_https_cert(con);

        //dump all the content
        print_content(con);

        /*String url = "https://esia-portal1.test.gosuslugi.ru/aas/oauth2/ac";

        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpParams params = get.getParams();
        params.setParameter("client_id", "C02G8416DRJM");
        params.setParameter("client_secret", "");
        params.setParameter("redirect_uri", "/get;
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

    private void print_https_cert(HttpsURLConnection con) {

        if (con != null) {

            try {

                out.println("Response Code : " + con.getResponseCode());
                out.println("Cipher Suite : " + con.getCipherSuite());
                out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    out.println("Cert Type : " + cert.getType());
                    out.println("Cert Hash Code : " + cert.hashCode());
                    out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void print_content(HttpsURLConnection con) {
        if (con != null) {

            try {

               // out.println("****** Content of the URL ********");
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null) {
                    out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}


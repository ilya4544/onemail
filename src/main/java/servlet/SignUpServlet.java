package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import domain.State;
import domain.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

/**
 * Created by Ilya on 21.03.2015.
 */
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    MongoClient m = null;
    DB db;

    public void init(ServletConfig config) {
        try {
            m = new MongoClient("localhost");
            db = m.getDB("test");
            System.out.println("Connected");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MongoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        String name = req.getParameter("name");

        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection users = db.getCollection("users");
        try {
            users.insert((DBObject) new User(email, pass, name));
            out.append(gson.toJson(new State("ok")));
        } catch (Exception e) {
            out.append(gson.toJson(new State(e.getMessage())));
        } finally {
            out.close();
        }
    }
}

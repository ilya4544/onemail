package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.util.JSON;
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
public class UpdateLocationServlet extends HttpServlet {
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
        String login = req.getParameter("login");
        String curLocation = req.getParameter("currentLocation");
        String nextLocation = req.getParameter("nextLocation");
        String date = req.getParameter("date");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection users = db.getCollection("users");
        BasicDBObject auth = new BasicDBObject("login", login);
        try {
            DBObject userObj = users.findOne(auth);
            userObj.removeField("_id");
            User user = gson.fromJson(userObj.toString(), User.class);
            user.setCurrentLocation(curLocation);
            user.setNextLocation(nextLocation);
            user.setDate(date);
            users.update(auth, (DBObject) JSON.parse(gson.toJson(user)));
            out.append(gson.toJson(new State("ok")));
        } catch (Exception e) {
            out.append(gson.toJson(new State(e.getMessage())));
        } finally {
            out.close();
        }
    }

}

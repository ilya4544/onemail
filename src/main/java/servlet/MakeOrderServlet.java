package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.util.JSON;
import domain.Order;
import domain.State;

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
public class MakeOrderServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String login = req.getParameter("login");
        String title = req.getParameter("title");
        String city = req.getParameter("city");
        String address = req.getParameter("address");
        String link = req.getParameter("link");
        String comment = req.getParameter("comment");
        String cost = req.getParameter("cost");
        String date = req.getParameter("date");

        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection users = db.getCollection("users");
        DBCollection orders = db.getCollection("orders");
        try {
            DBObject user = users.findOne(new BasicDBObject("login", login));

            orders.insert((DBObject) JSON.parse(gson.toJson(new Order(login, null, title, city, address,
                    link, comment, "wait", cost, user.get("currentLocation").toString(), date))));
            out.append(gson.toJson(new State("ok")));
        } catch (Exception e) {
            out.append(gson.toJson(new State(e.getMessage())));
        } finally {
            out.close();
        }
    }
}

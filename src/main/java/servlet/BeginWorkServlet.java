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
public class BeginWorkServlet extends HttpServlet {
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
        String title = req.getParameter("title");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection orders = db.getCollection("orders");
        BasicDBObject auth = new BasicDBObject("title", title);
        try {
            DBObject userObj = orders.findOne(auth);
            userObj.removeField("_id");
            Order order = gson.fromJson(userObj.toString(), Order.class);
            order.setStatus("working");
            order.setWorker(login);
            orders.update(auth, (DBObject) JSON.parse(gson.toJson(order)));
            out.append(gson.toJson(new State("ok")));
        } catch (Exception e) {
            out.append(gson.toJson(new State(e.getMessage())));
        } finally {
            out.close();
        }
    }


}

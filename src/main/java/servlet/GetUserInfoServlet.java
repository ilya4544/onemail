package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import domain.Order;
import domain.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 20.03.2015.
 */
public class GetUserInfoServlet extends HttpServlet {
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

    private class UserProfile {
        private User user;
        private List<Order> orderList;
        private List<Order> taskList;
        private List<Order> pendingList;

        public UserProfile(User user, List<Order> orderList, List<Order> taskList, List<Order> pendingList) {
            this.user = user;
            this.orderList = orderList;
            this.taskList = taskList;
            this.pendingList = pendingList;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection users = db.getCollection("users");
        DBCollection orders = db.getCollection("orders");

        User user = null;
        List<Order> orderList = new ArrayList<Order>();
        List<Order> taskList = new ArrayList<Order>();
        List<Order> pendingList = new ArrayList<Order>();

        BasicDBObject queryUser = new BasicDBObject("login", login);
        BasicDBObject queryOwner = new BasicDBObject("owner", login);
        BasicDBObject queryTask = new BasicDBObject("worker", login);
        DBCursor cursorUser = users.find(queryUser);
        DBCursor cursorOrder = orders.find(queryOwner);
        DBCursor cursorTask = orders.find(queryTask);
        try {
            while (cursorUser.hasNext()) {
                DBObject cur = cursorUser.next();
                cur.removeField("_id");
                user = gson.fromJson(cur.toString(), User.class);
            }
            while (cursorOrder.hasNext()) {
                DBObject cur = cursorOrder.next();
                cur.removeField("_id");
                orderList.add(gson.fromJson(cur.toString(), Order.class));
            }
            while (cursorTask.hasNext()) {
                DBObject cur = cursorTask.next();
                cur.removeField("_id");
                taskList.add(gson.fromJson(cur.toString(), Order.class));
            }
            BasicDBObject queryPending = new BasicDBObject("status", "wait");
            queryPending.append("city", user.getCurrentLocation());
            queryPending.append("destination", user.getNextLocation());
            queryPending.append("date", new BasicDBObject("$gt", user.getDate()));// if user.getDate() <= order date good
            DBCursor cursorPending = orders.find(queryPending);
            while (cursorPending.hasNext()) {
                DBObject cur = cursorPending.next();
                cur.removeField("_id");
                pendingList.add(gson.fromJson(cur.toString(), Order.class));
            }
            out.append(gson.toJson(new UserProfile(user,orderList,taskList,pendingList)));
        } finally {
            cursorOrder.close();
            cursorTask.close();
            cursorUser.close();
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

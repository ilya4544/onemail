package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import domain.State;
import org.bson.types.ObjectId;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

/**
 * Created by freeemahn on 21.06.15.
 */
public class SubscribeServlet extends HttpServlet {
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
      //  super.doGet(req, resp);
        String newEmail = req.getParameter("email");
        String token = req.getParameter("token");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        if (token == null || token.isEmpty()) {
            out.write(gson.toJson(new State("invalid token")));
            out.close();
            return;
        }
        DBCollection users = db.getCollection("users");
        BasicDBObject query = new BasicDBObject();
        try {
            query.put("id_token", token);
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.append("$set", new BasicDBObject().append("email", newEmail));
            users.update(query, newDocument);
            out.append(gson.toJson(new State("ok")));

        } catch (Exception e) {
            out.append(gson.toJson(new State("error")));
        } finally {
            out.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

    }
}

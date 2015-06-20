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
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    MongoClient m = null;
    DB db;
    Gson gson;

    public void init(ServletConfig config) {
        try {
            m = new MongoClient("localhost");
            db = m.getDB("test");
            System.out.println("Connected");
            gson = new GsonBuilder().create();
        } catch (UnknownHostException e) {
            e.printStackTrace();

        } catch (MongoException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");//wow
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String id = req.getParameter("id");//mail_to_delete_id
        String token = req.getParameter("token");//token
        if (token == null || token.isEmpty()) {
            out.append(gson.toJson(new State("invalid token")));
            out.close();
            return;
        }
        DBCollection emails = db.getCollection("mails");
        BasicDBObject query = new BasicDBObject();
        try {
            query.put("_id", new ObjectId(id));
            emails.remove(query);
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

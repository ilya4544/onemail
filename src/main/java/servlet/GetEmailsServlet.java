package servlet;

/**
 * Created by freeemahn on 19.06.15.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import domain.Mail;
import domain.User;
import org.bson.types.ObjectId;

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


public class GetEmailsServlet extends HttpServlet {

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
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (MongoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_token = req.getParameter("token");//id_token
        DBCollection emailsDB = db.getCollection("mails");
        DBCollection usersDB = db.getCollection("users");
        BasicDBObject queryEmail, queryUser;

        //TODO validation
        //TODO find users with this token => uid
        queryUser = new BasicDBObject("id_token", id_token);
        String uid = gson.fromJson(usersDB.findOne(queryUser).toString(), User.class).getUid();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();


        queryEmail = new BasicDBObject("to", uid);
        DBCursor cursorEmail = emailsDB.find(queryEmail);

        List<Mail> mailList = new ArrayList<Mail>();

        while (cursorEmail.hasNext()) {
            DBObject cur = cursorEmail.next();
            ObjectId t = (ObjectId) cur.removeField("_id");
            cur.put("_id", t.toString());
            mailList.add(gson.fromJson(cur.toString(), Mail.class));
        }

        out.append(gson.toJson(mailList));
        out.close();


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

package servlet;

/**
 * Created by freeemahn on 19.06.15.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import domain.Mail;

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
        //super.doGet(req, resp);

        String to = req.getParameter("email");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        DBCollection emails = db.getCollection("mails");
        BasicDBObject queryEmail = new BasicDBObject("to", to);

        DBCursor cursorEmail = emails.find(queryEmail);

        List<Mail> mailList = new ArrayList<Mail>();

        while (cursorEmail.hasNext()) {
            DBObject cur = cursorEmail.next();
            cur.removeField("_id");
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

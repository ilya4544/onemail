package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import domain.Company;
import domain.Document;
import domain.Mail;
import domain.State;
import org.bson.types.ObjectId;

import javax.print.Doc;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;

@WebServlet("/upload")
@MultipartConfig
public class SendMailServlet extends HttpServlet {
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

        String to = req.getParameter("to");
        String from = req.getParameter("from");
        String data = req.getParameter("data");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection mails = db.getCollection("mails");
        DBCollection documents = db.getCollection("documents");


        Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = getFileName(filePart);
        InputStream fileContent = filePart.getInputStream();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        for (int length; (length = fileContent.read(buffer)) > 0; ) output.write(buffer, 0, length);


        try {
            DBObject createdMail = (DBObject) new Mail(to, from, data);
            mails.insert(createdMail);
            ObjectId id = (ObjectId) createdMail.get("_id");
            DBObject createdDoc = (DBObject) new Document(id, output.toByteArray());
            documents.insert((DBObject) createdDoc);


            out.append(gson.toJson(new State("ok")));
        } catch (Exception e) {
            out.append(gson.toJson(new State(e.getMessage())));
        } finally {
            out.close();
        }
    }


    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}

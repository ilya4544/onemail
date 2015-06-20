package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;
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
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/sendMail")
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
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");//wow
        resp.setCharacterEncoding("UTF-8");

        String to = req.getParameter("to");
        String from = req.getParameter("from");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();

        DBCollection mails = db.getCollection("mails");
        Mail createdMail;
        ArrayList<Part> parts = (ArrayList<Part>) req.getParts();

        ObjectId id = ObjectId.get();
        String bucket = "a" + id.toString();
        GridFS gfsFiles = new GridFS(db, bucket);//namespace

        int filesCount = 0;
        for (int i = 4; i < req.getParts().size(); i++) {
            Part filePart = parts.get(i); // Retrieves <input type="file" name="file">
            //if(i  < 4 ) continue;//because of {to,from,data} - not needed info,we want to save only file
            String fileName = getFileName(filePart);
            //if (fileName == null) continue;


            InputStream fileContent = filePart.getInputStream();
            GridFSInputFile gfsFile = gfsFiles.createFile(fileContent);
            gfsFile.setFilename(fileName);
            gfsFile.save();
            filesCount++;

        }
        createdMail = new Mail(null, to, from, title, content, bucket, filesCount, new Date(), false);

        BasicDBObject obj1 = (BasicDBObject) JSON.parse(gson.toJson(createdMail));
        mails.insert(obj1);
        out.append(gson.toJson(new State("ok")));

        //out.append(gson.toJson(new State(e.getMessage())));


        out.close();

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

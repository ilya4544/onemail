package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import domain.Mail;
import domain.State;
import org.bson.types.ObjectId;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by freeemahn on 20.06.15.
 */
public class DownloadServlet extends HttpServlet {

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
        OutputStream outStream = resp.getOutputStream();
        Gson gson = new GsonBuilder().create();
        String bucketName = req.getParameter("bucket");//_id of email
        String token = req.getParameter("token");//token
        if (token == null || token.isEmpty()) {
            outStream.write(gson.toJson(new State("invalid token")).getBytes());
            outStream.close();
            return;
        }


        int cur = Integer.parseInt(req.getParameter("current"));//attachment number

        GridFS gfsAttachments = new GridFS(db, bucketName);//all of attachments
        DBCursor cursor = gfsAttachments.getFileList();
        outStream.write((cursor.count() + "\n").getBytes());
        int i = 0;

        while (cursor.hasNext() && i < cur) {
            cursor.next();
            i++;
        }

        ObjectId objId = (ObjectId) (cursor.curr().get("_id"));
        GridFSDBFile f = gfsAttachments.findOne(objId);
        resp.setHeader("Content-Disposition", "attachment; filename=" + f.getFilename());

        f.writeTo(outStream);
        outStream.close();


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
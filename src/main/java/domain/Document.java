package domain;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * Created by freeemahn on 19.06.15.
 */
public class Document {
    private String mail_id;
    private byte[] data;

    public Document(String mail_id, byte[] data) {
        this.mail_id = "ObjectId(" + mail_id + ")";
        this.data = data;
    }

    public String getMail_id() {

        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

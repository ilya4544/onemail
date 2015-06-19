package domain;

import org.bson.types.ObjectId;

/**
 * Created by freeemahn on 19.06.15.
 */
public class Document {
    private ObjectId mail_id;
    private byte[] data;

    public Document(ObjectId mail_id, byte[] data) {
        this.mail_id = mail_id;
        this.data = data;
    }

    public ObjectId getMail_id() {

        return mail_id;
    }

    public void setMail_id(ObjectId mail_id) {
        this.mail_id = mail_id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

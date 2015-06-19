package domain;

import com.mongodb.BasicDBObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya on 19.06.2015.
 */
public class Mail {
    private String to;
    private String from;
    private String data;
    private String files;
    private Integer count;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Mail(String to, String from, String data, String files,Integer count) {
        this.to = to;
        this.from = from;
        this.data = data;
        this.files = files;
        this.count = count;

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

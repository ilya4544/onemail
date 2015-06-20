package domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;

/**
 * Created by Ilya on 19.06.2015.
 */
public class Mail {
    private String _id;
    private String to;
    private String from;
    private String title;
    private String content;
    private String files;
    private Integer count;
    private Date date;
    private Boolean is_read;

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    public Mail(String _id, String to, String from, String title, String content, String files, Integer count, Date date, Boolean is_read) {
        this._id = _id;
        this.to = to;
        this.from = from;
        this.title = title;
        this.content = content;
        this.files = files;
        this.count = count;
        this.date = date;
        this.is_read = is_read;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package domain;

/**
 * Created by Ilya on 19.06.2015.
 */
public class Mail {
    private String to;
    private String from;
    private String data;

    public Mail(String to, String from, String data) {
        this.to = to;
        this.from = from;
        this.data = data;
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

package domain;

/**
 * Created by Ilya on 19.06.2015.
 */
public class Mail {
    private String email;
    private String data;

    public Mail(String email, String data) {
        this.email = email;
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
